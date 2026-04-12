package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import database.DatabaseManager;
import tasklist.*;

public class TaskListGUI extends JPanel {

	private JTextField txtTitulo, txtDesc, txtFecha, txtIdBorrar;
	private JTable table;
	private DefaultTableModel model;
	private JComboBox<String> comboTipo;

	public TaskListGUI() {

		setLayout(new BorderLayout());

		// --- PANEL SUPERIOR ---
		JPanel panelSup = new JPanel(new GridLayout(2, 1));

		JPanel fila1 = new JPanel();

		txtTitulo = new JTextField(10);
		txtDesc = new JTextField(10);
		txtFecha = new JTextField(8);
		comboTipo = new JComboBox<>(new String[] {"Fluida", "Repetitiva"});
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

		add(panelSup, BorderLayout.NORTH);

		// TABLA
		model = new DefaultTableModel(new String[]{"ID", "Título", "Descripción", "Fecha", "Status"}, 0);
		table = new JTable(model);

		// esto colorea el estado (rojo amarillo verde)
		table.getColumnModel().getColumn(4).setCellRenderer(new StatusRenderer());

		add(new JScrollPane(table), BorderLayout.CENTER);

		// eventos
		btnAgregar.addActionListener(e -> agregar());
		btnBorrar.addActionListener(e -> borrar());

		actualizarTabla();
	}

	private void agregar() {

		String nombre = txtTitulo.getText();
		String desc = txtDesc.getText();
		String fecha = txtFecha.getText();
		String tipo = (String) comboTipo.getSelectedItem();

		TaskListAbstract t;

		// polimorfismo como ya lo tenias
		if (tipo.equals("Fluida")) {
			t = new FluidTask(nombre, desc);
		} else {
			t = new RepeatingTask(nombre, desc);
		}

		t.saveToDatabase(fecha);

		actualizarTabla();
	}

	private void borrar() {
		try {
			int id = Integer.parseInt(txtIdBorrar.getText());

			DatabaseManager.deleteTask(id); // ahora limpio usando manager

			actualizarTabla();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "ID inválido");
		}
	}

	private void actualizarTabla() {

		model.setRowCount(0);

		for (Object[] row : DatabaseManager.getAllTasks()) {
			model.addRow(row);
		}
	}

	// esto es para pintar colores segun estado (no es tan complicado como parece)
	class StatusRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {

			JLabel l = (JLabel) super.getTableCellRendererComponent(t, v, s, f, r, c);
			l.setOpaque(true);

			if ("sin hacer".equals(v)) l.setBackground(Color.RED);
			else if ("en proceso".equals(v)) l.setBackground(Color.YELLOW);
			else if ("hecha".equals(v)) l.setBackground(Color.GREEN);

			return l;
		}
	}
}