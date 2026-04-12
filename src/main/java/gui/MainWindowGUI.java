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

public class MainWindowGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private JPanel sidebar;
    private JPanel topbar;
    private JPanel mainPanel;

    private boolean isSidebarVisible = false;

    private JPanel placeholderPanel; // Placeholder panel to return to

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

    //Ventana principal
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

        contentPane.remove(sidebar); // Sidebar hidden by default
    }

    //Barra lateral
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

        //Botones de la barra lateral
        sidebar.add(createNavButton("Calendario"));
        sidebar.add(createNavButton("Tareas"));
        sidebar.add(createNavButton("Journal"));
        sidebar.add(createNavButton("Moodtracker"));
        sidebar.add(createNavButton("Timer"));
        sidebar.add(createNavButton("Configuracion"));

        contentPane.add(sidebar, BorderLayout.WEST);
    }

    //Botones de navegacion
    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Manejo practico de los botones
        btn.addActionListener(e -> handleNavigation(text));

        return btn;
    }

    //Centraliza la logica de navegacion (mejor mantenimiento)
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

    //Manejo de la barra superior
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

        // ⏰ Reloj
        clockLabel = new JLabel();
        clockLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        clockLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        startClock();

        topbar.add(toggleBtn, BorderLayout.WEST);
        topbar.add(welcomeLabel, BorderLayout.CENTER);
        topbar.add(clockLabel, BorderLayout.EAST);

        contentPane.add(topbar, BorderLayout.NORTH);
    }

    //Creacion del panel principal
    private void createMainPanel() {
        mainPanel = new JPanel(new BorderLayout());

        MainCalendar weeklyCalendar = new MainCalendar();
        mainPanel.add(weeklyCalendar, BorderLayout.CENTER);

        placeholderPanel = new JPanel(new BorderLayout());
        JLabel placeholder = new JLabel("Main Content Area", SwingConstants.CENTER);
        placeholder.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        placeholderPanel.add(placeholder, BorderLayout.CENTER);

        contentPane.add(mainPanel, BorderLayout.CENTER);
    }

    //Manejo de la barra lateral
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

    //Permite cambiar paneles
    private void switchPanel(JPanel panel) {
        mainPanel.removeAll();
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private void startClock() {
        Timer timer = new Timer(1000, e -> {
            java.time.LocalTime now = java.time.LocalTime.now();
            clockLabel.setText(now.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")));
        });
        timer.start();
    }
}