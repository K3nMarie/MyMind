package gui;

import javax.swing.*;
import java.awt.*;

public class MoodTrackerGUI extends JPanel {

    public MoodTrackerGUI() {

        setLayout(new BorderLayout());

        JComboBox<String> mood = new JComboBox<>(
            new String[]{"😊 Feliz","😐 Neutral","😡 Enojado","😴 Cansado"}
        );

        JTextArea log = new JTextArea();

        JButton add = new JButton("Guardar");

        add.addActionListener(e -> log.append(mood.getSelectedItem()+"\n"));

        JPanel top = new JPanel();
        top.add(mood);
        top.add(add);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(log), BorderLayout.CENTER);
    }
}