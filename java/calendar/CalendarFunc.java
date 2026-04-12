package calendar;

import timemanagement.GetDate;
import timemanagement.GetTime;

//Source - https://stackoverflow.com/a/17235350
//Posted by Gilbert Le Blanc
//Retrieved 2026-04-06, License - CC BY-SA 3.0

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Font;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

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
 protected String[] dayNames =
     { "D", "L", "M", "M", "J", "V", "S"
 };

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

// quita las /* para que se ejecute, se los puse por que no me permite hacer el commit 

/// 

//Crea la GUI de los meses
 protected JPanel createGUI() {
     JPanel monthPanel = new JPanel(true);
     monthPanel.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaption));
     monthPanel.setLayout(new BorderLayout());
     monthPanel.setBackground(Color.WHITE);
     monthPanel.setForeground(Color.BLACK);

     monthPanel.add(createTitleGUI(), BorderLayout.NORTH);
     monthPanel.add(createDaysGUI(), BorderLayout.CENTER);

     return monthPanel;
 }

 //Crea el titulo
 protected JPanel createTitleGUI() {
     JPanel titlePanel = new JPanel();
     titlePanel.setLayout(new BorderLayout());
     titlePanel.setBackground(Color.WHITE);

     JButton prevButton = new JButton("<"); //Boton para ir atras
     JButton nextButton = new JButton(">"); //Boton para ir delante

     //Estilos
     JLabel label = new JLabel(monthNames[month] + " " + year, JLabel.CENTER);
     label.setForeground(SystemColor.activeCaption);
     label.setFont(new Font("Arial", Font.BOLD, 18));
     prevButton.setFont(new Font("Arial", Font.BOLD, 16));
     nextButton.setFont(new Font("Arial", Font.BOLD, 16));
     prevButton.setBackground(Color.LIGHT_GRAY);
     nextButton.setBackground(Color.LIGHT_GRAY);
     prevButton.setFocusPainted(false);
     nextButton.setFocusPainted(false);

     //Funcion del boton hacia atras
     prevButton.addActionListener(e -> {
         month--;
         if (month < 0) { //Se asegura de no ir a los negativos
             month = 11;
             year--;
         }
         refreshCalendar();
     });

     //Funcion del boton hacia alante
     nextButton.addActionListener(e -> {
         month++;
         if (month > 11) { //Se asegura de no ir mas alla del index
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

     // JPanel dayPanel = new JPanel(new BorderLayout());
     JPanel dayPanel = new JPanel(true);

     dayPanel.setLayout(new GridLayout(0, dayNames.length));

     //Calcula el dia de hoy
     Calendar today = Calendar.getInstance();
     int tMonth = today.get(Calendar.MONTH);
     int tYear = today.get(Calendar.YEAR);
     int tDay = today.get(Calendar.DAY_OF_MONTH);

     //Obtiene el primer dia del mes y año actual
     Calendar calendar = Calendar.getInstance();
     calendar.set(Calendar.MONTH, month);
     calendar.set(Calendar.YEAR, year);
     calendar.set(Calendar.DAY_OF_MONTH, 1);

     Calendar iterator = (Calendar) calendar.clone();
     iterator.add(Calendar.DAY_OF_MONTH,
             -(iterator.get(Calendar.DAY_OF_WEEK) - 1));

     //Calcula el limite del mes
     Calendar maximum = (Calendar) calendar.clone();
     maximum.add(Calendar.MONTH, +1);

     // Encabezados de días
     for (int i = 0; i < dayNames.length; i++) {

         JPanel dPanel = new JPanel(true);
         dPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

         JLabel dLabel = new JLabel(dayNames[i], JLabel.CENTER);

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
         dayLabel.setFont(new Font("Arial", Font.PLAIN, 14));

         if ((lMonth == month) && (lYear == year)) {

             int lDay = iterator.get(Calendar.DAY_OF_MONTH);
             dayLabel.setText(Integer.toString(lDay));

             //Permite hacer clicks en los dias (PARA EL DIARIO Y LISTA DE TAREAS)
             dPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

             dPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                 public void mouseClicked(java.awt.event.MouseEvent e) {

                     //PLACEHOLDER
                     JOptionPane.showMessageDialog(dPanel,
                         "Día clickeado: " + lDay + "/" + (month+1) + "/" + year);
                 }
             });

             // Marca el dia de hoy
             if ((tMonth == month) && (tYear == year) && (tDay == lDay)) {
                 dPanel.setBackground(Color.ORANGE);
             }

         } else {
             dayLabel.setText(" ");
             dPanel.setBackground(Color.WHITE);
         }

         dPanel.add(dayLabel);
         dayPanel.add(dPanel);

         iterator.add(Calendar.DAY_OF_YEAR, 1);
         count++;
     }

     //Crea los bordes vacios restantes
     for (int i = count; i < limit; i++) {

         JPanel dPanel = new JPanel(true);
         dPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

         JLabel dayLabel = new JLabel(" ", JLabel.CENTER);

         dPanel.setBackground(Color.WHITE);
         dPanel.add(dayLabel, BorderLayout.NORTH);

         dayPanel.add(dPanel);
     }

     return dayPanel;
 }
}