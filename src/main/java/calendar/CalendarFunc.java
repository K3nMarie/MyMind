package calendar;

//Source - https://stackoverflow.com/a/17235350
//Posted by Gilbert Le Blanc
//Retrieved 2026-04-06, License - CC BY-SA 3.0

import java.awt.*;
import java.util.Calendar;
import javax.swing.*;

/* FUNCIONALIDAD DEL CALENDARIO (muestra entradas del journal por día) */
public class CalendarFunc extends JPanel {

 private static final long serialVersionUID = 1L;

 protected int month;
 protected int year;
 private JPanel mainPanel;

 //Callback para manejar click en fechas
 private java.util.function.Consumer<java.time.LocalDate> onDateClick;

 //Index de los meses (0 = Enero, 11 = Diciembre)
 protected String[] monthNames = { 
        "Enero", "Febrero", "Marzo", 
        "Abril", "Mayo", "Junio", 
        "Julio", "Agosto", "Septiembre",
        "Octubre", "Noviembre", "Diciembre"       
 };

 //Index de los dias (empieza en Domingo)
 protected String[] dayNames = { "D", "L", "M", "M", "J", "V", "S" };

 //Constructor (inicializa calendario + accion al hacer click)
 public CalendarFunc(int month, int year,
     java.util.function.Consumer<java.time.LocalDate> onDateClick) {

     this.month = month;
     this.year = year;
     this.onDateClick = onDateClick;

     setLayout(new BorderLayout());
     mainPanel = createGUI();
     add(mainPanel, BorderLayout.CENTER);
 }

 //Refresca el calendario
 private void refreshCalendar() {

     remove(mainPanel);
     mainPanel = createGUI();
     add(mainPanel, BorderLayout.CENTER);
     revalidate();
     repaint();
 }

///////////////

 //Obtiene la entrada del journal para una fecha
 private String getJournalEntryForDate(int day, int month, int year) {

     String entry = null;

     String sql = "SELECT title FROM journal WHERE entry_date = ? LIMIT 1";

     try (
         java.sql.Connection con = database.DatabaseConnector.getConnection();
         java.sql.PreparedStatement ps = con.prepareStatement(sql)
     ) {

         java.time.LocalDate date = java.time.LocalDate.of(year, month + 1, day);

         ps.setDate(1, java.sql.Date.valueOf(date));

         java.sql.ResultSet rs = ps.executeQuery();

         if (rs.next()) {
             entry = rs.getString("title");
         }

     } catch (Exception e) {
         e.printStackTrace();
     }

     return entry;
 }

///////////////

 protected JPanel createGUI() {

     JPanel monthPanel = new JPanel(new BorderLayout());
     monthPanel.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaption));
     monthPanel.setBackground(Color.WHITE);

     monthPanel.add(createTitleGUI(), BorderLayout.NORTH);
     monthPanel.add(createDaysGUI(), BorderLayout.CENTER);

     return monthPanel;
 }

 protected JPanel createTitleGUI() {

     JPanel titlePanel = new JPanel(new BorderLayout());
     titlePanel.setBackground(Color.WHITE);

     JButton prevButton = new JButton("<");
     JButton nextButton = new JButton(">");

     JLabel label = new JLabel(monthNames[month] + " " + year, JLabel.CENTER);

     label.setForeground(SystemColor.activeCaption);
     label.setFont(new Font("Arial", Font.BOLD, 18));

     prevButton.addActionListener(e -> {
         month--;
         if (month < 0) { month = 11; year--; }
         refreshCalendar();
     });

     nextButton.addActionListener(e -> {
         month++;
         if (month > 11) { month = 0; year++; }
         refreshCalendar();
     });

     titlePanel.add(prevButton, BorderLayout.WEST);
     titlePanel.add(label, BorderLayout.CENTER);
     titlePanel.add(nextButton, BorderLayout.EAST);

     return titlePanel;
 }

 protected JPanel createDaysGUI() {

     JPanel dayPanel = new JPanel(new GridLayout(0, dayNames.length));

     Calendar today = Calendar.getInstance();
     int tMonth = today.get(Calendar.MONTH);
     int tYear = today.get(Calendar.YEAR);
     int tDay = today.get(Calendar.DAY_OF_MONTH);

     Calendar calendar = Calendar.getInstance();
     calendar.set(year, month, 1);

     Calendar iterator = (Calendar) calendar.clone();
     iterator.add(Calendar.DAY_OF_MONTH, -(iterator.get(Calendar.DAY_OF_WEEK) - 1));

     Calendar maximum = (Calendar) calendar.clone();
     maximum.add(Calendar.MONTH, +1);

     //Encabezados
     for (String dayName : dayNames) {

         JPanel dPanel = new JPanel();
         dPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

         JLabel dLabel = new JLabel(dayName, JLabel.CENTER);
         dLabel.setFont(new Font("Arial", Font.BOLD, 14));

         dPanel.setBackground(Color.LIGHT_GRAY);
         dPanel.add(dLabel);
         dayPanel.add(dPanel);
     }

     while (iterator.getTimeInMillis() < maximum.getTimeInMillis()) {

         int lMonth = iterator.get(Calendar.MONTH);
         int lYear = iterator.get(Calendar.YEAR);

         JPanel dPanel = new JPanel(new BorderLayout());
         dPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

         JLabel dayLabel = new JLabel("", JLabel.CENTER);

         if ((lMonth == month) && (lYear == year)) {

             int lDay = iterator.get(Calendar.DAY_OF_MONTH);
             dayLabel.setText(Integer.toString(lDay));

             //Muestra entrada
             String entry = getJournalEntryForDate(lDay, month, year);

             if (entry != null) {
                 JLabel entryLabel = new JLabel(entry);
                 entryLabel.setFont(new Font("Arial", Font.PLAIN, 9));
                 entryLabel.setHorizontalAlignment(JLabel.CENTER);
                 dPanel.add(entryLabel, BorderLayout.CENTER);
             }

             //Click en dia
             dPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
             dPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                 public void mouseClicked(java.awt.event.MouseEvent e) {

                     java.time.LocalDate selectedDate =
                         java.time.LocalDate.of(year, month + 1, lDay);

                     if (onDateClick != null) {
                         onDateClick.accept(selectedDate);
                     }
                 }
             });

             //Colores
             if ((tMonth == month) && (tYear == year) && (tDay == lDay)) {
                 dPanel.setBackground(Color.ORANGE);
             } else if (iterator.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                        iterator.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                 dPanel.setBackground(new Color(235, 235, 235));
             } else {
                 dPanel.setBackground(Color.WHITE);
             }

         } else {
             dPanel.setBackground(Color.WHITE);
         }

         dPanel.add(dayLabel, BorderLayout.NORTH);
         dayPanel.add(dPanel);

         iterator.add(Calendar.DAY_OF_YEAR, +1);
     }

     return dayPanel;
 }
}