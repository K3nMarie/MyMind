package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import calendar.CalendarFunc;

//Source - https://stackoverflow.com/a/17235350
//Posted by Gilbert Le Blanc
//Retrieved 2026-04-06, License - CC BY-SA 3.0

import java.awt.BorderLayout;

public class CalendarGUI extends JPanel {

 private JFrame  frame;

 private JButton backButton; // Boton para regresar
 
 public CalendarGUI(JFrame frame, Runnable onBack) {
     this.frame = frame;
     setLayout(new BorderLayout());

     // Month is zero based
     CalendarFunc panel = new CalendarFunc(3, 2026); // <- IMPLEMENTAR GETDATE AND GET TIME HERE!!!
     
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

 public void exitProcedure() {
     if (frame != null) {
         frame.dispose();
     }
     System.exit(0);
 }

 public static void main(String[] args) {
     SwingUtilities.invokeLater(() -> {
         JFrame frame = new JFrame();
         frame.setTitle("Calendar");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.getContentPane().add(new CalendarGUI(frame, null));
         frame.pack();
         frame.setVisible(true);
     });
 }

}