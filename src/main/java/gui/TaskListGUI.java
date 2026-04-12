package gui;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;

import tasklist.FluidTask;
import tasklist.RepeatingTask;
import tasklist.TaskListAbstract;

public class TaskListGUI extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextField txtTitulo, txtDesc, txtFecha, txtIdBorrar;
    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> comboTipo;

    private ArrayList<TaskListAbstract> tareas; //MEMORIA
    private Runnable onBack;

    public TaskListGUI(Runnable onBack) {
        this.onBack = onBack;
        tareas = new ArrayList<>();
        setLayout(new BorderLayout());
        createUI();
    }

    private void createUI() {

        JPanel panelSup = new JPanel(new GridLayout(2, 1));

        JPanel fila1 = new JPanel();
        txtTitulo = new JTextField(10);
        txtDesc = new JTextField(10);
        txtFecha = new JTextField(8);
        comboTipo = new JComboBox<>(new String[]{"Fluida", "Repetitiva"});
        JButton btnAgregar = new JButton("Agregar Tarea");

        fila1.add(new JLabel("Título:")); fila1.add(txtTitulo);
        fila1.add(new JLabel("Desc:")); fila1.add(txtDesc);
        fila1.add(new JLabel("Fecha:")); fila1.add(txtFecha);
        fila1.add(new JLabel("Tipo:")); fila1.add(comboTipo);
        fila1.add(btnAgregar);

        JPanel fila2 = new JPanel();
        txtIdBorrar = new JTextField(5);
        JButton btnBorrar = new JButton("Borrar por ID");

        fila2.add(new JLabel("ID:")); fila2.add(txtIdBorrar);
        fila2.add(btnBorrar);

        panelSup.add(fila1);
        panelSup.add(fila2);

        add(panelSup, BorderLayout.NORTH); // ✅ FIX

        model = new DefaultTableModel(
            new String[]{"ID", "Título", "Descripción", "Fecha", "Status"}, 0
        );

        table = new JTable(model);
        table.setRowHeight(25);
        table.getColumnModel().getColumn(4).setCellRenderer(new StatusRenderer());

        add(new JScrollPane(table), BorderLayout.CENTER); // ✅ FIX

        //EVENTOS
        btnAgregar.addActionListener(e -> accionBotonAgregar());
        btnBorrar.addActionListener(e -> borrarTarea());
        
        JButton backButton = new JButton("Volver");
        backButton.addActionListener(e -> {
            if (onBack != null) onBack.run();
        });

        add(backButton, BorderLayout.SOUTH); // ✅ FIX
    }

    private void accionBotonAgregar() {

        String nombre = txtTitulo.getText();
        String desc = txtDesc.getText();
        String fecha = txtFecha.getText();
        String tipo = (String) comboTipo.getSelectedItem();

        TaskListAbstract nuevaTarea;

        if (tipo.equals("Fluida")) {
            nuevaTarea = new FluidTask(nombre, desc, fecha);
        } else {
            nuevaTarea = new RepeatingTask(nombre, desc, fecha);
        }

        tareas.add(nuevaTarea);

        actualizarTabla();
        limpiarCampos();
    }

    private void borrarTarea() {
        try {
            int id = Integer.parseInt(txtIdBorrar.getText());

            if (id >= 0 && id < tareas.size()) {
                tareas.remove(id);
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "ID no existe");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ID inválido");
        }
    }

    private void actualizarTabla() {

        model.setRowCount(0);

        for (int i = 0; i < tareas.size(); i++) {

            TaskListAbstract t = tareas.get(i);

            String fecha = "";
            String status = "";

            if (t instanceof FluidTask) {
                fecha = ((FluidTask) t).getDueDate();
                status = ((FluidTask) t).getStatus();
            } else if (t instanceof RepeatingTask) {
                fecha = ((RepeatingTask) t).getDueDate();
                status = ((RepeatingTask) t).getStatus();
            }

            model.addRow(new Object[]{
                i,
                t.getTaskName(),
                t.getTaskDescription(),
                fecha,
                status
            });
        }
    }

    private void limpiarCampos() {
        txtTitulo.setText("");
        txtDesc.setText("");
        txtFecha.setText("");
    }

    class StatusRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable t, Object v, boolean s, boolean f, int r, int c) {

            JLabel label = (JLabel) super.getTableCellRendererComponent(t, v, s, f, r, c);

            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setOpaque(true);
            label.setBackground(Color.WHITE);

            if ("sin hacer".equals(v)) label.setBackground(Color.RED);
            else if ("en proceso".equals(v)) label.setBackground(Color.YELLOW);
            else if ("hecha".equals(v)) label.setBackground(Color.GREEN);

            return label;
        }
    }
}