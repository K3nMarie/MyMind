package gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.*;

public class JournalGUI extends JPanel {

    private JTextField titleField;
    private JTextArea contentArea;
    private JButton saveButton;
    private JButton backButton;

    private Runnable onBack;

    //Constructor (recibe la accion para volver a la vista anterior)
    public JournalGUI(Runnable onBack) {
        this.onBack = onBack;

        setLayout(new BorderLayout());
        createUI();
    }

    //Crea toda la interfaz del journal
    private void createUI() {

        //Titulo (campo para escribir el titulo de la entrada)
        titleField = new JTextField();
        titleField.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleField.setBorder(BorderFactory.createTitledBorder("Titulo"));

        //Contenido (area principal del texto con scroll)
        contentArea = new JTextArea();
        contentArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(contentArea);
        scroll.setBorder(BorderFactory.createTitledBorder("Contenido"));

        //Boton guardar (PLACEHOLDER)
        saveButton = new JButton("Guardar");

        saveButton.addActionListener(e -> {
            String title = titleField.getText();
            JOptionPane.showMessageDialog(this,
                "Entrada guardada:\n" + title);
        });

        //Boton volver
        backButton = new JButton("Volver");
        backButton.addActionListener(e -> {
            if (onBack != null) onBack.run();
        });

        JPanel bottom = new JPanel();
        bottom.add(saveButton);
        bottom.add(backButton);

        add(titleField, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }
}