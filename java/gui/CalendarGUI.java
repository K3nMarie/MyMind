package gui;

import javax.swing.*;
import java.awt.*;
import calendar.CalendarFunc;

public class CalendarGUI extends JPanel {

 private JFrame frame;
 private JButton backButton;

 public CalendarGUI(JFrame frame, Runnable onBack) {

     this.frame = frame;
     setLayout(new BorderLayout());

     java.util.Calendar now = java.util.Calendar.getInstance();
     int month = now.get(java.util.Calendar.MONTH);
     int year = now.get(java.util.Calendar.YEAR);

     CalendarFunc panel = new CalendarFunc(month, year);
     add(panel, BorderLayout.CENTER);

     backButton = new JButton("Volver");
     JPanel bottomPanel = new JPanel();
     bottomPanel.add(backButton);

     add(bottomPanel, BorderLayout.SOUTH);

     backButton.addActionListener(e -> {
         if (onBack != null) {
             onBack.run();
         }
     });
 }
}