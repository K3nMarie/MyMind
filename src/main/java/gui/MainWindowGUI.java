package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;

import calendar.MainCalendar;
//import gui.TaskListGUI;

public class MainWindowGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private JPanel sidebar;
    private JPanel topbar;
    private JPanel mainPanel;
    private JLabel placeholder ;

    private boolean isSidebarVisible = false;

    private JPanel placeholderPanel; //Panel placeholder para vistas no implementadas

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                FlatLightLaf.setup();
                MainWindowGUI frame = new MainWindowGUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    //Ventana principal (configuracion base)
    public MainWindowGUI() {
        setTitle("MyMind"); //Titulo de la app
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 640);
        setLocationRelativeTo(null);

        contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        createSidebar();
        createTopbar();
        createMainPanel();

        contentPane.remove(sidebar); //Sidebar oculta por defecto
    }

    //Barra lateral (menu de navegacion)
    private void createSidebar() {
        sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("MyMind");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(title);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));

        //Botones de la barra lateral (navegacion principal)
        sidebar.add(createNavButton("Calendario"));
        sidebar.add(createNavButton("Tareas"));
        sidebar.add(createNavButton("Journal"));
        sidebar.add(createNavButton("Moodtracker"));
        sidebar.add(createNavButton("Timer"));
        sidebar.add(createNavButton("Configuracion"));

        contentPane.add(sidebar, BorderLayout.WEST);
    }

    //Crea botones reutilizables para navegacion
    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Maneja el click
        btn.addActionListener(e -> handleNavigation(text));

        return btn;
    }

    //Logica de navegacion
    private void handleNavigation(String text) {

        switch (text) {
            case "Timer":
                switchPanel(new TimerGUI(() -> switchPanel(new MainCalendar())));
                break;

            case "Calendario":
                switchPanel(new CalendarGUI(() -> switchPanel(new MainCalendar())));
                break;

            case "Journal":
                switchPanel(new JournalGUI(() -> switchPanel(new MainCalendar())));
                break;

            case "Moodtracker":
                switchPanel(new MoodTrackerGUI(() -> switchPanel(new MainCalendar())));
                break;

            case "Tareas":
                switchPanel(new TaskListGUI(() -> switchPanel(new MainCalendar())));
                break;

            case "Configuracion":
                switchPanel(placeholderPanel);
                break;
        }
    }

    //Manejo de la barra superior (menu + titulo + reloj)
    private JLabel clockLabel;

    private void createTopbar() {
        topbar = new JPanel(new BorderLayout());
        topbar.setPreferredSize(new Dimension(0, 50));
        topbar.setBorder(new EmptyBorder(5, 10, 5, 10));

        JButton toggleBtn = new JButton("☰");
        toggleBtn.setFocusPainted(false);
        toggleBtn.addActionListener(e -> toggleSidebar());

        JLabel welcomeLabel = new JLabel("Bienvenido");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //Reloj en tiempo real (se actualiza cada segundo)
        clockLabel = new JLabel();
        clockLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        clockLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        startClock();

        topbar.add(toggleBtn, BorderLayout.WEST);
        topbar.add(welcomeLabel, BorderLayout.CENTER);
        topbar.add(clockLabel, BorderLayout.EAST);

        contentPane.add(topbar, BorderLayout.NORTH);
    }

    //Creacion del panel principal (contenido dinamico)
    private void createMainPanel() {
        mainPanel = new JPanel(new BorderLayout());

        //Vista inicial: calendario semanal
        MainCalendar weeklyCalendar = new MainCalendar();
        mainPanel.add(weeklyCalendar, BorderLayout.CENTER);

        //Panel placeholder para secciones no implementadas
        placeholderPanel = new JPanel(new BorderLayout());
        placeholder = new JLabel("Main Content Area", SwingConstants.CENTER); 
        placeholder.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        placeholderPanel.add(placeholder, BorderLayout.CENTER);

        contentPane.add(mainPanel, BorderLayout.CENTER);
    }

    //Muestra u oculta la barra lateral
    private void toggleSidebar() {
        if (isSidebarVisible) {
            contentPane.remove(sidebar);
        } else {
            contentPane.add(sidebar, BorderLayout.WEST);
        }

        isSidebarVisible = !isSidebarVisible;

        contentPane.revalidate();
        contentPane.repaint();
    }

    //Cambia el panel central (navegacion entre vistas)
    private void switchPanel(JPanel panel) {
        mainPanel.removeAll();
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    //Inicia el reloj que se actualiza cada segundo
    private void startClock() {
        Timer timer = new Timer(1000, e -> {
            java.time.LocalTime now = java.time.LocalTime.now();
            clockLabel.setText(now.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")));
        });
        timer.start();
    }
}