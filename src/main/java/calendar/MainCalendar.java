package calendar;

import java.awt.*;
import java.util.Calendar;
import javax.swing.*;

public class MainCalendar extends JPanel {

    private int weekOffset = 0; // Permite moverse entre semanas (offset relativo a la actual)
    private JPanel mainPanel;

    //Index de los dias (empieza en Domingo)
    String[] dayNames = { "D", "L", "M", "M", "J", "V", "S" };

    //Index de los meses (0 = Enero, 11 = Diciembre)
    String[] monthNames = { 
        "Enero", "Febrero", "Marzo", 
        "Abril", "Mayo", "Junio", 
        "Julio", "Agosto", "Septiembre",
        "Octubre", "Noviembre", "Diciembre"       
    };

    //Constructor (inicializa la vista semanal)
    public MainCalendar() {
        setLayout(new BorderLayout());

        mainPanel = createGUI();
        add(mainPanel, BorderLayout.CENTER);
    }

    //Refresca el calendario (cuando cambias de semana)
    private void refreshCalendar() {
        remove(mainPanel);
        mainPanel = createGUI();
        add(mainPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    //Crea la GUI principal (contenedor centrado)
    private JPanel createGUI() {

        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.WHITE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(500, 150));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        panel.add(createTitleGUI(), BorderLayout.NORTH);
        panel.add(createWeekGUI(), BorderLayout.CENTER);

        container.add(panel); //Se agrega centrado

        return container;
    }

    //Crea el titulo (Semana + Mes + Año)
    private JPanel createTitleGUI() {
        JPanel panel = new JPanel(new BorderLayout());

        JButton prev = new JButton("<"); //Boton para ir atras
        JButton next = new JButton(">"); //Boton para ir adelante

        Calendar today = Calendar.getInstance();

        //Clona la fecha actual y aplica el offset de semanas
        Calendar current = (Calendar) today.clone();
        current.add(Calendar.WEEK_OF_YEAR, weekOffset);

        int month = current.get(Calendar.MONTH);
        int year = current.get(Calendar.YEAR);

        //Calcula la semana dentro del mes (rango aproximado 1-5)
        int weekOfMonth = current.get(Calendar.WEEK_OF_MONTH);

        JLabel title = new JLabel(
            "Semana " + weekOfMonth + " - " + monthNames[month] + " " + year,
            JLabel.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 16));

        //Funcion del boton hacia atras (resta una semana)
        prev.addActionListener(e -> {
            weekOffset--;
            refreshCalendar();
        });

        //Funcion del boton hacia adelante (suma una semana)
        next.addActionListener(e -> {
            weekOffset++;
            refreshCalendar();
        });

        panel.add(prev, BorderLayout.WEST);
        panel.add(title, BorderLayout.CENTER);
        panel.add(next, BorderLayout.EAST);

        return panel;
    }

    //Crea los dias de la semana (vista semanal)
    private JPanel createWeekGUI() {

        //Grid de 2 filas: encabezados + dias
        JPanel panel = new JPanel(new GridLayout(2, 7));

        Calendar today = Calendar.getInstance();

        //Obtiene el inicio de la semana (Domingo como base)
        Calendar start = (Calendar) today.clone();
        start.add(Calendar.WEEK_OF_YEAR, weekOffset);
        start.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        //Encabezados (D L M M J V S)
        for (String d : dayNames) {
            JLabel label = new JLabel(d, JLabel.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 12));
            panel.add(label);
        }

        //Iterador para recorrer los 7 dias
        Calendar iter = (Calendar) start.clone();

        for (int i = 0; i < 7; i++) {

            //Cada panel representa un dia
            JPanel dayPanel = new JPanel(new BorderLayout());
            dayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            int day = iter.get(Calendar.DAY_OF_MONTH);
            int month = iter.get(Calendar.MONTH) + 1;
            int year = iter.get(Calendar.YEAR);

            JLabel label = new JLabel(String.valueOf(day), JLabel.CENTER);

            //Marca el dia actual
            if (iter.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                iter.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
                dayPanel.setBackground(Color.ORANGE);
            } else {
                dayPanel.setBackground(Color.WHITE);
            }

            dayPanel.add(label, BorderLayout.CENTER);

            //Permite hacer click en los dias (PLACEHOLDER)
            dayPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            dayPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {

                    //PLACEHOLDER
                    JOptionPane.showMessageDialog(dayPanel,
                        "Día clickeado: " + day + "/" + month + "/" + year);
                    //
                }
            });

            panel.add(dayPanel);
            iter.add(Calendar.DAY_OF_MONTH, 1);
        }

        return panel;
    }
}