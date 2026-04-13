package gui;

import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;

import database.DatabaseManager;

/* INTERFAZ DEL JOURNAL (crear y editar entradas por fecha) */
public class JournalGUI extends JPanel {

    private JTextField titleField;
    private JTextArea contentArea;

    private LocalDate entryDate;
    private Integer entryId = null;

    private Runnable onBack;

    //Constructor (recibe accion de regreso y fecha de la entrada)
    public JournalGUI(Runnable onBack, LocalDate date) {

        this.onBack = onBack;
        this.entryDate = date;

        setLayout(new BorderLayout());

        createUI();
        loadEntryIfExists();
    }

    //Construccion de la UI
    private void createUI() {

        //Campo de titulo
        titleField = new JTextField();
        titleField.setBorder(BorderFactory.createTitledBorder("Titulo"));

        //Area de contenido
        contentArea = new JTextArea();
        JScrollPane scroll = new JScrollPane(contentArea);

        //Botones
        JButton save = new JButton("Guardar");
        JButton back = new JButton("Volver");

        //Guardar entrada (inserta o actualiza)
        save.addActionListener(e -> {

            String title = titleField.getText();
            String content = contentArea.getText();

            //Si no existe entrada, crea nueva
            if (entryId == null) {
                DatabaseManager.logEntry(title, content, entryDate);
            } else {
                //Si ya existe, actualiza
                DatabaseManager.updateEntry(entryId, title, content);
            }

            JOptionPane.showMessageDialog(this, "Guardado");

            //Regresa a la vista anterior
            if (onBack != null) onBack.run();
        });

        //Boton volver
        back.addActionListener(e -> {
            if (onBack != null) onBack.run();
        });

        add(titleField, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.add(save);
        bottom.add(back);

        add(bottom, BorderLayout.SOUTH);
    }

    //Carga entrada existente si existe
    private void loadEntryIfExists() {

        String sql = "SELECT id, title, content FROM journal WHERE entry_date = ? LIMIT 1";

        try (
            java.sql.Connection con = database.DatabaseConnector.getConnection();
            java.sql.PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setDate(1, java.sql.Date.valueOf(entryDate));
            java.sql.ResultSet rs = ps.executeQuery();

            //Si existe entrada, carga datos
            if (rs.next()) {

                entryId = rs.getInt("id");

                //Carga titulo y contenido en la UI
                titleField.setText(rs.getString("title"));
                contentArea.setText(rs.getString("content"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}