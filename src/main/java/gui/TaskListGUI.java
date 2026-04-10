package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import tasklist.FluidTask;
import tasklist.RepeatingTask;
import tasklist.TaskListAbstract;

public class TaskListGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtTitulo, txtDesc, txtFecha, txtIdBorrar;
	private JTable table;
	private DefaultTableModel model;
	private JComboBox<String> comboTipo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TaskListGUI frame = new TaskListGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TaskListGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		
		// --- PANEL SUPERIOR (Inputs) ---
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
				contentPane.add(panelSup, BorderLayout.NORTH);

				// --- TABLA ---
				model = new DefaultTableModel(new String[]{"ID", "Título", "Descripción", "Fecha", "Status"}, 0);
				table = new JTable(model);
				table.setRowHeight(25);
				
				// Aplicar colores según status
				table.getColumnModel().getColumn(4).setCellRenderer(new StatusRenderer());
				
				contentPane.add(new JScrollPane(table), BorderLayout.CENTER);

				// --- EVENTOS ---
				
				btnAgregar.addActionListener(e -> accionBotonAgregar());
				
				btnBorrar.addActionListener(e -> {
					try {
						borrarTareaDB(Integer.parseInt(txtIdBorrar.getText()));
						actualizarTabla();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(this, "ID Inválido");
					}
				});

				// Cargar datos iniciales
				actualizarTabla();
			}

			// Lógica de Agregar usando tus clases FluidTask y RepeatingTask
			private void accionBotonAgregar() {
				String nombre = txtTitulo.getText();
				String desc = txtDesc.getText();
				String fecha = txtFecha.getText();
				String tipo = (String) comboTipo.getSelectedItem();
				
				TaskListAbstract nuevaTarea;
				
				// Polimorfismo puro con tus clases
				if(tipo.equals("Fluida")) {
					nuevaTarea = new FluidTask(nombre, desc);
				} else {
					nuevaTarea = new RepeatingTask(nombre, desc);
				}
				
				// La tarea maneja su conexión interna y guardado
				nuevaTarea.saveToDatabase(fecha);
				
				actualizarTabla();
				limpiarCampos();
			}

			// Método para borrar desde la GUI
			public void borrarTareaDB(int id) {
				String sql = "DELETE FROM tareas WHERE id = ?";
				// Usamos FluidTask temporalmente solo para obtener la conexión heredada
				FluidTask temp = new FluidTask("", ""); 
				try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/task_db", "root", "");
					 PreparedStatement ps = con.prepareStatement(sql)) {
					ps.setInt(1, id);
					ps.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			// Refrescar la tabla con los datos de MySQL
			private void actualizarTabla() {
				model.setRowCount(0);
				try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/task_db", "root", "");
					 Statement st = con.createStatement();
					 ResultSet rs = st.executeQuery("SELECT * FROM tareas")) {
					
					while(rs.next()) {
						model.addRow(new Object[] {
							rs.getInt("id"), rs.getString("titulo"), 
							rs.getString("descripcion"), rs.getString("due_date"), 
							rs.getString("status")
						});
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			private void limpiarCampos() {
				txtTitulo.setText(""); txtDesc.setText(""); txtFecha.setText("");
			}

			// Renderizador para los rectángulos de colores
			class StatusRenderer extends DefaultTableCellRenderer {
				@Override
				public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
					JLabel label = (JLabel) super.getTableCellRendererComponent(t, v, s, f, r, c);
					label.setHorizontalAlignment(SwingConstants.CENTER);
					label.setOpaque(true);
					if ("sin hacer".equals(v)) label.setBackground(Color.RED);
					else if ("en proceso".equals(v)) label.setBackground(Color.YELLOW);
					else if ("hecha".equals(v)) label.setBackground(Color.GREEN);
					return label;
				}

	}

}
