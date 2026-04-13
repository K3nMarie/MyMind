package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import moodtracker.MoodTrackerFunc;
import moodtracker.MoodTrackerFunc.Mood;

public class MoodTrackerGUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JLabel imageLabel;
	private MoodTrackerFunc logic;
	
/*
	
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MoodTrackerGUI frame = new MoodTrackerGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	 * Create the frame.
	 
	public MoodTrackerGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

	}
*/
    public MoodTrackerGUI() {
        logic = new MoodTrackerFunc();

        setLayout(new GridLayout(1, 2));

        // PANEL IZQUIERDO
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        updateImage();

        leftPanel.add(imageLabel, BorderLayout.CENTER);

        // PANEL DERECHO
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        JLabel title = new JLabel("How do you feel now?");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));

        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(title);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Opciones
        rightPanel.add(createOption("I feel good", Mood.GOOD, Color.YELLOW));
        rightPanel.add(createOption("I feel sad", Mood.SAD, Color.BLUE));
        rightPanel.add(createOption("I feel angry", Mood.ANGRY, Color.RED));

        add(leftPanel);
        add(rightPanel);
    }

    private JPanel createOption(String text, Mood mood, Color color) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel(text);

        JButton btn = new JButton();
        btn.setPreferredSize(new Dimension(20, 20));
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setOpaque(true);

        btn.addActionListener(e -> {
            logic.setMood(mood);   // cambia estado
            updateImage();         // actualiza UI
        });

        panel.add(label);
        panel.add(btn);

        return panel;
    }

    private void updateImage() {
    	ImageIcon icon = new ImageIcon(getClass().getResource(logic.getMoodImage()));

        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(400, 550, Image.SCALE_SMOOTH);

        imageLabel.setIcon(new ImageIcon(scaledImg));
    	//imageLabel.setIcon(
    	//	    new ImageIcon(getClass().getResource(logic.getMoodImage()))
    	//	);
       //imageLabel.setIcon(new ImageIcon(logic.getMoodImage()));
    }
}
