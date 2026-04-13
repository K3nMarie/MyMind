package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
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
        btn.addActionListener(e -> {
            if (text.equals("Timer")) {
                TimerGUI timerPanel = new TimerGUI(() -> switchPanel(placeholderPanel));
                switchPanel(timerPanel);
            }
        });
        btn.addActionListener(e -> {
        	if (text.equals("Calendario")) {
        	    CalendarGUI calendarPanel = new CalendarGUI(this, () -> {
        	        switchPanel(new MainCalendar());
        	    });
        	    switchPanel(calendarPanel);
        	}
        });
        
     // Manejo de la lista de tares
          ///////////
       btn.addActionListener(e -> {
           if (text.equals("Tareas")) {
               TaskListGUI taskList = new TaskListGUI(); 
                    switchPanel(placeholderPanel);
                 
                 switchPanel(taskList);
           }
        });
       btn.addActionListener(e->{
    	  if (text.equals("Moodtracker")) {
    		  MoodTrackerGUI moodPanel = new MoodTrackerGUI();
    		  switchPanel(moodPanel);
    	  }
       });

        

        return btn;
    }

    //Manejo de la barra superior
    private void createTopbar() {
        topbar = new JPanel(new BorderLayout());
        topbar.setPreferredSize(new Dimension(0, 50));
        topbar.setBorder(new EmptyBorder(5, 10, 5, 10));

        JButton toggleBtn = new JButton("☰");
        toggleBtn.setFocusPainted(false);
        toggleBtn.addActionListener(e -> toggleSidebar());

        JLabel welcomeLabel = new JLabel("Bienvenido");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        topbar.add(toggleBtn, BorderLayout.WEST);
        topbar.add(welcomeLabel, BorderLayout.CENTER);

        contentPane.add(topbar, BorderLayout.NORTH);
    }

    //Creacion del panel principal
    private void createMainPanel() {
        mainPanel = new JPanel(new BorderLayout());

        // Weekly calendar as default view
        MainCalendar weeklyCalendar = new MainCalendar();
        mainPanel.add(weeklyCalendar, BorderLayout.CENTER);

        // Placeholder saved for navigation
        placeholderPanel = new JPanel(new BorderLayout());
        placeholder = new JLabel("Main Content Area", SwingConstants.CENTER); 
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
}

	