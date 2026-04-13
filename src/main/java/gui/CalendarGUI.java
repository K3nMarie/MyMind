package gui;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Container;

import calendar.CalendarFunc;

/* INTERFAZ DEL CALENDARIO (conecta con el journal) */
public class CalendarGUI extends JPanel {

    //Constructor (recibe accion de regreso)
    public CalendarGUI(Runnable onBack) {

        setLayout(new BorderLayout());

        java.util.Calendar now = java.util.Calendar.getInstance();
        int month = now.get(java.util.Calendar.MONTH);
        int year = now.get(java.util.Calendar.YEAR);

        //Calendario con accion al hacer click
        CalendarFunc panel = new CalendarFunc(month, year, date -> {

            //Abre journal dentro del mismo contenedor
            Container parent = this.getParent();

            parent.removeAll();
            parent.add(new JournalGUI(
                () -> {
                    parent.removeAll();
                    parent.add(new CalendarGUI(onBack));
                    parent.revalidate();
                    parent.repaint();
                },
                date
            ), BorderLayout.CENTER);

            parent.revalidate();
            parent.repaint();
        });

        add(panel, BorderLayout.CENTER);
    }
}