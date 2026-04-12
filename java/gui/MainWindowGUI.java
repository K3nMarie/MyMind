package gui;

import java.awt.*;
import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;

import calendar.MainCalendar;

/*VENTANA PRINCIPAL*/

public class MainWindowGUI extends JFrame {

    private JPanel contentPane;

    private JPanel sidebar;
    private JPanel topbar;
    private JPanel mainPanel;

    private boolean sidebarVisible = false;

    private JPanel placeholderPanel;

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

    public MainWindowGUI() {

        setTitle("MyMind");
        setSize(1024, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        createSidebar();
        createTopbar();
        createMainPanel();

        contentPane.add(mainPanel, BorderLayout.CENTER);
    }

    // =========================
    // SIDEBAR
    // =========================
    private void createSidebar() {

        sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        sidebar.add(new JLabel("MyMind"));
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));

        sidebar.add(createNavButton("Calendario"));
        sidebar.add(createNavButton("Tareas"));
        sidebar.add(createNavButton("Moodtracker"));
        sidebar.add(createNavButton("Timer"));

        contentPane.add(sidebar, BorderLayout.WEST);
    }

    // =========================
    // NAV BUTTONS
    // =========================
    private JButton createNavButton(String text) {

        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        btn.addActionListener(e -> {

            switch (text) {

                case "Calendario":
                    switchPanel(new MainCalendar());
                    break;

                case "Tareas":
                    switchPanel(new TaskListGUI());
                    break;

                case "Moodtracker":
                    switchPanel(new MoodTrackerGUI());
                    break;

                case "Timer":
                    switchPanel(new TimerGUI(new Runnable() {
                        @Override
                        public void run() {
                            switchPanel(placeholderPanel);
                        }
                    }));
                    break;

                default:
                    System.out.println("No handler: " + text);
            }
        });

        return btn;
    }

    // =========================
    // TOPBAR
    // =========================
    private void createTopbar() {

        topbar = new JPanel(new BorderLayout());
        topbar.setPreferredSize(new Dimension(0, 50));

        JButton toggle = new JButton("☰");

        toggle.addActionListener(e -> toggleSidebar());

        JLabel title = new JLabel("Bienvenido");

        topbar.add(toggle, BorderLayout.WEST);
        topbar.add(title, BorderLayout.CENTER);

        contentPane.add(topbar, BorderLayout.NORTH);
    }

    // =========================
    // MAIN PANEL
    // =========================
    private void createMainPanel() {

        mainPanel = new JPanel(new BorderLayout());

        mainPanel.add(new MainCalendar(), BorderLayout.CENTER);

        placeholderPanel = new JPanel(new BorderLayout());
        placeholderPanel.add(new JLabel("Main Area", JLabel.CENTER));

        contentPane.add(mainPanel, BorderLayout.CENTER);
    }

    // =========================
    // SWITCH PANEL
    // =========================
    private void switchPanel(JPanel panel) {

        mainPanel.removeAll();
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // =========================
    // SIDEBAR TOGGLE
    // =========================
    private void toggleSidebar() {

        if (sidebarVisible) {
            contentPane.remove(sidebar);
        } else {
            contentPane.add(sidebar, BorderLayout.WEST);
        }

        sidebarVisible = !sidebarVisible;

        contentPane.revalidate();
        contentPane.repaint();
    }
}