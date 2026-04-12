package calendar;

//Source - https://stackoverflow.com/a/17235350
//Posted by Gilbert Le Blanc
//Retrieved 2026-04-06, License - CC BY-SA 3.0

import java.awt.*;
import java.util.Calendar;
import javax.swing.*;

public class CalendarFunc extends JPanel {

 private static final long serialVersionUID = 1L;

 protected int month;
 protected int year;
 private JPanel mainPanel;

 //Index de los meses
 protected String[] monthNames = { 
        "Enero", "Febrero", "Marzo", 
        "Abril", "Mayo", "Junio", 
        "Julio", "Agosto", "Septiembre",
        "Octubre", "Noviembre", "Diciembre"       
 };

 //Index de los dias
 protected String[] dayNames = { "D", "L", "M", "M", "J", "V", "S" };

 //Constructor
 public CalendarFunc(int month, int year) {
     this.month = month;
     this.year = year;

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

 //⚠️ NOTA: Esto deberia ir en una capa DAO en el futuro
 private java.util.List<String> getTasksForDate(int day, int month, int year) {

     java.util.List<String> tareas = new java.util.ArrayList<>();

     String fechaBuscada = String.format("%02d/%02d/%04d", day, month + 1, year);

     String sql = "SELECT titulo FROM tareas WHERE due_date = ?";

     try (
         java.sql.Connection con = java.sql.DriverManager.getConnection(
             "jdbc:mysql://localhost:3306/task_db",
             "root",
             ""
         );
         java.sql.PreparedStatement ps = con.prepareStatement(sql)
     ) {

         ps.setString(1, fechaBuscada);
         java.sql.ResultSet rs = ps.executeQuery();

         while (rs.next()) {
             tareas.add(rs.getString("titulo"));
         }

     } catch (Exception e) {
         e.printStackTrace();
     }

     return tareas;
 }

///////////////

 //Crea la GUI de los meses
 protected JPanel createGUI() {
     JPanel monthPanel = new JPanel(new BorderLayout());
     monthPanel.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaption));
     monthPanel.setBackground(Color.WHITE);

     monthPanel.add(createTitleGUI(), BorderLayout.NORTH);
     monthPanel.add(createDaysGUI(), BorderLayout.CENTER);

     return monthPanel;
 }

 //Crea el titulo
 protected JPanel createTitleGUI() {
     JPanel titlePanel = new JPanel(new BorderLayout());
     titlePanel.setBackground(Color.WHITE);

     JButton prevButton = new JButton("<"); //Boton para ir atras
     JButton nextButton = new JButton(">"); //Boton para ir delante

     JLabel label = new JLabel(monthNames[month] + " " + year, JLabel.CENTER);
     label.setForeground(SystemColor.activeCaption);
     label.setFont(new Font("Arial", Font.BOLD, 18));

     prevButton.setFocusPainted(false);
     nextButton.setFocusPainted(false);

     //Funcion del boton hacia atras
     prevButton.addActionListener(e -> {
         month--;
         if (month < 0) {
             month = 11;
             year--;
         }
         refreshCalendar();
     });

     //Funcion del boton hacia alante
     nextButton.addActionListener(e -> {
         month++;
         if (month > 11) {
             month = 0;
             year++;
         }
         refreshCalendar();
     });

     titlePanel.add(prevButton, BorderLayout.WEST);
     titlePanel.add(label, BorderLayout.CENTER);
     titlePanel.add(nextButton, BorderLayout.EAST);

     return titlePanel;
 }

 //Creacion de los dias
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

     // Encabezados
     for (String dayName : dayNames) {
         JPanel dPanel = new JPanel();
         dPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
         JLabel dLabel = new JLabel(dayName, JLabel.CENTER);
         dLabel.setFont(new Font("Arial", Font.BOLD, 14));
         dPanel.setBackground(Color.LIGHT_GRAY);
         dPanel.add(dLabel);
         dayPanel.add(dPanel);
     }

     int count = 0;
     int limit = dayNames.length * 6;

     while (iterator.getTimeInMillis() < maximum.getTimeInMillis()) {

         int lMonth = iterator.get(Calendar.MONTH);
         int lYear = iterator.get(Calendar.YEAR);

         JPanel dPanel = new JPanel(new BorderLayout());
         dPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

         JLabel dayLabel = new JLabel("", JLabel.CENTER);

         if ((lMonth == month) && (lYear == year)) {

             int lDay = iterator.get(Calendar.DAY_OF_MONTH);
             dayLabel.setText(Integer.toString(lDay));

             java.util.List<String> tareasDelDia = getTasksForDate(lDay, month, year);

             if (!tareasDelDia.isEmpty()) {
                 JPanel tareasPanel = new JPanel();
                 tareasPanel.setLayout(new BoxLayout(tareasPanel, BoxLayout.Y_AXIS));
                 tareasPanel.setOpaque(false);

                 for (String tarea : tareasDelDia) {
                     JLabel tareaLabel = new JLabel(tarea);
                     tareaLabel.setFont(new Font("Arial", Font.PLAIN, 9));
                     tareaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                     tareasPanel.add(tareaLabel);
                 }

                 dPanel.add(tareasPanel, BorderLayout.CENTER);
             }

             //Click
             dPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
             dPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                 public void mouseClicked(java.awt.event.MouseEvent e) {
                     JOptionPane.showMessageDialog(dPanel,
                         "Día clickeado: " + lDay + "/" + (month+1) + "/" + year);
                 }
             });

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
         count++;
     }

     //Relleno final
     for (int i = count; i < limit; i++) {
         JPanel dPanel = new JPanel();
         dPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
         dayPanel.add(dPanel);
     }

     return dayPanel;
 }
}