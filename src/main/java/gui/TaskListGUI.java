package gui;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import database.DatabaseManager;

public class TaskListGUI extends JPanel {

    private JTextField txtTitulo, txtDesc, txtFecha, txtIdBorrar;
    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> comboTipo;

    private Runnable onBack;

    //Constructor (inicializa UI y carga datos)
    public TaskListGUI(Runnable onBack) {
        this.onBack = onBack;

        setLayout(new BorderLayout(8, 8));

        createUI();
        actualizarTabla();
    }

    // ================= UI =================
    private void createUI() {

        // ================= TOP PANEL =================
        JPanel topPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        // ---------- ROW 1 (ADD TASK) ----------
        JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));

        txtTitulo = new JTextField(8);
        txtDesc = new JTextField(8);
        txtFecha = new JTextField(8);

        comboTipo = new JComboBox<>(new String[]{"Fluida", "Repetitiva"});
        JButton btnAgregar = new JButton("Agregar");

        btnAgregar.addActionListener(e -> accionBotonAgregar());

        fila1.add(new JLabel("Título:"));
        fila1.add(txtTitulo);
        fila1.add(new JLabel("Desc:"));
        fila1.add(txtDesc);
        fila1.add(new JLabel("Fecha:"));
        fila1.add(txtFecha);
        fila1.add(new JLabel("Tipo:"));
        fila1.add(comboTipo);
        fila1.add(btnAgregar);

        // ---------- ROW 2 (DELETE TASK) ----------
        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));

        txtIdBorrar = new JTextField(5);
        JButton btnBorrar = new JButton("Eliminar");

        btnBorrar.addActionListener(e -> borrarTarea());

        fila2.add(new JLabel("ID:"));
        fila2.add(txtIdBorrar);
        fila2.add(btnBorrar);

        topPanel.add(fila1);
        topPanel.add(fila2);

        add(topPanel, BorderLayout.NORTH);

        // ================= TABLE =================
        model = new DefaultTableModel(
                new String[]{"ID", "Título", "Descripción", "Fecha", "Status", "Tipo"}, 0
        );

        table = new JTable(model);
        table.setRowHeight(22);
        table.setFillsViewportHeight(true);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ================= BOTTOM =================
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setPreferredSize(new Dimension(0, 45));

        JButton backButton = new JButton("Volver");
        backButton.addActionListener(e -> {
            if (onBack != null) onBack.run();
        });

        bottom.add(backButton);
        add(bottom, BorderLayout.SOUTH);
    }

    // ================= ADD TASK =================
    private void accionBotonAgregar() {

        String nombre = txtTitulo.getText();
        String desc = txtDesc.getText();
        String fecha = txtFecha.getText();
        String tipo = (String) comboTipo.getSelectedItem();

        String status = "sin hacer";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate dueDate;

        try {
            dueDate = LocalDate.parse(fecha, formatter);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Fecha inválida (DD/MM/YYYY)");
            return;
        }

        try {
            DatabaseManager db = new DatabaseManager();
            db.addTask(nombre, desc, status, tipo, dueDate);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar la tarea");
            return;
        }

        actualizarTabla();
        limpiarCampos();
    }

    // ================= DELETE TASK =================
    private void borrarTarea() {

        try {
            int id = Integer.parseInt(txtIdBorrar.getText());

            DatabaseManager db = new DatabaseManager();
            db.deleteTask(id);

            actualizarTabla();
            txtIdBorrar.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ID inválido");
        }
    }

    // ================= LOAD TABLE =================
    private void actualizarTabla() {

        model.setRowCount(0);

        DatabaseManager db = new DatabaseManager();
        db.loadTasks(model);

        //Refresca la UI despues de recargar datos
        model.fireTableDataChanged();
    }

    // ================= CLEAN FIELDS =================
    private void limpiarCampos() {
        txtTitulo.setText("");
        txtDesc.setText("");
        txtFecha.setText("");
    }
}