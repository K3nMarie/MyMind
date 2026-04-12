package gui;

import javax.swing.*;
import java.awt.BorderLayout;
import calendar.CalendarFunc;

//Source - https://stackoverflow.com/a/17235350
//Posted by Gilbert Le Blanc
//Retrieved 2026-04-06, License - CC BY-SA 3.0

public class CalendarGUI extends JPanel {

 private JFrame  frame;
private JPanel bottomPanel;
 private JButton backButton; // Boton para regresar
 
 public CalendarGUI(JFrame frame, Runnable onBack) {
     this.frame = frame;
     setLayout(new BorderLayout());

     // Usar fecha actual
     java.util.Calendar now = java.util.Calendar.getInstance();
     int month = now.get(java.util.Calendar.MONTH); //Mes
     int year = now.get(java.util.Calendar.YEAR); // Año

     CalendarFunc panel = new CalendarFunc(month, year); // calendario interactivo
     
     add(panel, BorderLayout.CENTER);

     backButton = new JButton("Volver");
     bottomPanel = new JPanel();
     bottomPanel.add(backButton);
     add(bottomPanel, BorderLayout.SOUTH);
   //Boton de regreso
     backButton.addActionListener(e -> {
         if (onBack != null) {
             onBack.run();
         }
     });
 }

}