package gui;

import javax.swing.*;
import java.awt.BorderLayout;
import calendar.CalendarFunc;

//Source - https://stackoverflow.com/a/17235350
//Posted by Gilbert Le Blanc
//Retrieved 2026-04-06, License - CC BY-SA 3.0

public class CalendarGUI extends JPanel {

    private JFrame frame;

    private JButton backButton; //Boton para volver a la vista anterior

    //Constructor (recibe la accion que se ejecuta al volver)
    public CalendarGUI(Runnable onBack) {
        setLayout(new BorderLayout());

        //Obtiene la fecha actual para inicializar el calendario
        java.util.Calendar now = java.util.Calendar.getInstance();
        int month = now.get(java.util.Calendar.MONTH);
        int year = now.get(java.util.Calendar.YEAR);

        //Crea el calendario interactivo con el mes actual
        CalendarFunc panel = new CalendarFunc(month, year);
        
        add(panel, BorderLayout.CENTER);

        backButton = new JButton("Volver");

        JPanel bottomPanel = new JPanel();
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