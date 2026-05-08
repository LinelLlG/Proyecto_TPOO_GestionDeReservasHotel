package com.hotel.sistema.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.hotel.sistema.controller.HabitacionController;
import com.hotel.sistema.model.Habitacion;

import java.util.List;

public class FrmHabitacion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JTextField txtNumero;
	private JTextField txtCapacidad;
	private JTextField txtPrecio;
	private JTextField txtBuscar;
	private JComboBox<String> cmbTipo;
	private JComboBox<String> cmbEstado;
	private JComboBox<String> cmbFiltroTipo;
	private JButton btnNuevo;
	private JButton btnGuardar;
	private JButton btnEliminar;
	private JButton btnEditar;
	private JButton btnCambiarEstado;
	private JButton btnBuscar;
	private JTable tabla;
	private DefaultTableModel modeloTabla;

	private HabitacionController controller = new HabitacionController();
	private int idSeleccionado = -1;

	private static final String[] TIPOS   = {"Simple", "Doble", "Suite", "Familiar"};
	private static final String[] ESTADOS = {"Disponible", "Ocupada", "En reserva", "Mantenimiento"};
	private static final String[] FILTROS = {"Todos", "Simple", "Doble", "Suite", "Familiar"};

	/**
	 * Create the frame.
	 */
	public FrmHabitacion() {
		setTitle("Gestión de Habitaciones");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 780, 510);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// ===== FILA 1: NUMERO, TIPO, PRECIO, BOTON NUEVO =====
		JLabel lblNumero = new JLabel("N° Habitación:");
		lblNumero.setBounds(20, 20, 110, 25);
		contentPane.add(lblNumero);

		txtNumero = new JTextField();
		txtNumero.setBounds(135, 20, 80, 25);
		contentPane.add(txtNumero);
		txtNumero.setColumns(10);

		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(230, 20, 40, 25);
		contentPane.add(lblTipo);

		cmbTipo = new JComboBox<String>(TIPOS);
		cmbTipo.setBounds(275, 20, 110, 25);
		contentPane.add(cmbTipo);

		JLabel lblPrecio = new JLabel("Precio/Noche (S/):");
		lblPrecio.setBounds(400, 20, 120, 25);
		contentPane.add(lblPrecio);

		txtPrecio = new JTextField();
		txtPrecio.setBounds(525, 20, 80, 25);
		contentPane.add(txtPrecio);
		txtPrecio.setColumns(10);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setBounds(620, 20, 120, 25);
		contentPane.add(btnNuevo);

		// ===== FILA 2: CAPACIDAD, ESTADO, GUARDAR, ELIMINAR, EDITAR =====
		JLabel lblCapacidad = new JLabel("Capacidad:");
		lblCapacidad.setBounds(20, 60, 100, 25);
		contentPane.add(lblCapacidad);

		txtCapacidad = new JTextField();
		txtCapacidad.setBounds(135, 60, 80, 25);
		contentPane.add(txtCapacidad);
		txtCapacidad.setColumns(10);

		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(230, 60, 50, 25);
		contentPane.add(lblEstado);

		cmbEstado = new JComboBox<String>(ESTADOS);
		cmbEstado.setBounds(275, 60, 110, 25);
		contentPane.add(cmbEstado);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(400, 60, 90, 25);
		contentPane.add(btnGuardar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(500, 60, 90, 25);
		btnEliminar.setEnabled(false);
		contentPane.add(btnEliminar);

		btnEditar = new JButton("Editar");
		btnEditar.setBounds(600, 60, 90, 25);
		btnEditar.setEnabled(false);
		contentPane.add(btnEditar);

		// ===== FILA 3: FILTRO TIPO, CAMBIAR ESTADO, BUSCAR =====
		JLabel lblFiltroTipo = new JLabel("Filtrar por tipo:");
		lblFiltroTipo.setBounds(20, 105, 110, 25);
		contentPane.add(lblFiltroTipo);

		cmbFiltroTipo = new JComboBox<String>(FILTROS);
		cmbFiltroTipo.setBounds(135, 105, 110, 25);
		contentPane.add(cmbFiltroTipo);

		JLabel lblCambiarEstado = new JLabel("Cambiar estado:");
		lblCambiarEstado.setBounds(270, 105, 110, 25);
		contentPane.add(lblCambiarEstado);

		btnCambiarEstado = new JButton("Aplicar Estado");
		btnCambiarEstado.setBounds(385, 105, 120, 25);
		btnCambiarEstado.setEnabled(false);
		contentPane.add(btnCambiarEstado);

		JLabel lblBuscar = new JLabel("Buscar disp.:");
		lblBuscar.setBounds(525, 105, 90, 25);
		contentPane.add(lblBuscar);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(620, 105, 80, 25);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(710, 105, 50, 25);
		contentPane.add(btnBuscar);

		// ===== TABLA =====
		modeloTabla = new DefaultTableModel(
			new Object[]{"ID", "N° Hab.", "Tipo", "Precio/Noche", "Capacidad", "Estado"}, 0
		) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};

		tabla = new JTable(modeloTabla) {
			public Component prepareRenderer(javax.swing.table.TableCellRenderer r, int row, int col) {
				Component c = super.prepareRenderer(r, row, col);
				if (!isRowSelected(row)) {
					String estado = modeloTabla.getValueAt(row, 5).toString();
					switch (estado) {
						case "Disponible":
							c.setBackground(new Color(198, 239, 206));
							break;
						case "Ocupada":
							c.setBackground(new Color(255, 199, 206));
							break;
						case "En reserva":
							c.setBackground(new Color(255, 235, 156));
							break;
						case "Mantenimiento":
							c.setBackground(new Color(220, 220, 220));
							break;
						default:
							c.setBackground(Color.WHITE);
							break;
					}
				}
				return c;
			}
		};
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.getColumnModel().getColumn(0).setPreferredWidth(30);

		DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
		centro.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < 6; i++) {
			tabla.getColumnModel().getColumn(i).setCellRenderer(centro);
		}

		JScrollPane scrollPane = new JScrollPane(tabla);
		scrollPane.setBounds(20, 145, 735, 270);
		contentPane.add(scrollPane);

		// ===== LEYENDA DE COLORES =====
		JPanel panelLeyenda = new JPanel();
		panelLeyenda.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));
		panelLeyenda.setBounds(20, 425, 735, 22);
		panelLeyenda.setOpaque(false);
		agregarLeyenda(panelLeyenda, new Color(198, 239, 206), "Disponible");
		agregarLeyenda(panelLeyenda, new Color(255, 199, 206), "Ocupada");
		agregarLeyenda(panelLeyenda, new Color(255, 235, 156), "En reserva");
		agregarLeyenda(panelLeyenda, new Color(220, 220, 220), "Mantenimiento");
		contentPane.add(panelLeyenda);

		setLocationRelativeTo(null);

		// ===== EVENTOS =====
		btnNuevo.addActionListener(e -> limpiarFormulario());
		btnGuardar.addActionListener(e -> guardar());
		btnEliminar.addActionListener(e -> eliminar());
		btnEditar.addActionListener(e -> editar());
		btnCambiarEstado.addActionListener(e -> cambiarEstado());
		btnBuscar.addActionListener(e -> buscarDisponibles());
		cmbFiltroTipo.addActionListener(e -> filtrarPorTipo());

		tabla.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
				cargarFilaSeleccionada();
			}
		});

		// ===== CARGAR DATOS AL ABRIR =====
		cargarTabla();
	}

	// ===== MÉTODO LEYENDA =====
	private void agregarLeyenda(JPanel panel, Color color, String texto) {
		JLabel cuadro = new JLabel("  ");
		cuadro.setOpaque(true);
		cuadro.setBackground(color);
		cuadro.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		panel.add(cuadro);
		panel.add(new JLabel(texto));
	}

	// ===== MÉTODO CARGAR TABLA =====
	private void cargarTabla() {
		modeloTabla.setRowCount(0);
		List<Habitacion> lista = controller.listar();
		for (int i = 0; i < lista.size(); i++) {
			Habitacion h = lista.get(i);
			modeloTabla.addRow(new Object[]{
				h.getId(),
				h.getNumero(),
				h.getTipo(),
				String.format("S/ %.2f", h.getPrecio()),
				h.getCapacidad(),
				h.getEstado()
			});
		}
	}

	// ===== MÉTODO FILTRAR POR TIPO =====
	private void filtrarPorTipo() {
		String tipo = cmbFiltroTipo.getSelectedItem().toString();
		modeloTabla.setRowCount(0);
		List<Habitacion> lista = controller.listarPorTipo(tipo);
		for (int i = 0; i < lista.size(); i++) {
			Habitacion h = lista.get(i);
			modeloTabla.addRow(new Object[]{
				h.getId(),
				h.getNumero(),
				h.getTipo(),
				String.format("S/ %.2f", h.getPrecio()),
				h.getCapacidad(),
				h.getEstado()
			});
		}
	}

	// ===== MÉTODO BUSCAR DISPONIBLES =====
	private void buscarDisponibles() {
		String filtro = txtBuscar.getText().trim();
		modeloTabla.setRowCount(0);
		List<Habitacion> lista = controller.buscarDisponibles(filtro);
		for (int i = 0; i < lista.size(); i++) {
			Habitacion h = lista.get(i);
			modeloTabla.addRow(new Object[]{
				h.getId(),
				h.getNumero(),
				h.getTipo(),
				String.format("S/ %.2f", h.getPrecio()),
				h.getCapacidad(),
				h.getEstado()
			});
		}
	}

	// ===== MÉTODO GUARDAR =====
	private void guardar() {
		double precio;
		int capacidad;
		try {
			precio    = Double.parseDouble(txtPrecio.getText().trim());
			capacidad = Integer.parseInt(txtCapacidad.getText().trim());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Precio y capacidad deben ser valores numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		Habitacion h = new Habitacion(
			txtNumero.getText().trim(),
			cmbTipo.getSelectedItem().toString(),
			precio,
			capacidad,
			cmbEstado.getSelectedItem().toString()
		);

		String resultado = controller.guardar(h);
		if (resultado.equals("OK")) {
			JOptionPane.showMessageDialog(this, "Habitación registrada correctamente.");
			limpiarFormulario();
			cargarTabla();
		} else {
			JOptionPane.showMessageDialog(this, resultado, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// ===== MÉTODO EDITAR =====
	private void editar() {
		if (idSeleccionado == -1) return;
		double precio;
		int capacidad;
		try {
			precio    = Double.parseDouble(txtPrecio.getText().trim());
			capacidad = Integer.parseInt(txtCapacidad.getText().trim());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Precio y capacidad deben ser valores numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		Habitacion h = new Habitacion(
			txtNumero.getText().trim(),
			cmbTipo.getSelectedItem().toString(),
			precio,
			capacidad,
			cmbEstado.getSelectedItem().toString()
		);
		h.setId(idSeleccionado);

		String resultado = controller.editar(h);
		if (resultado.equals("OK")) {
			JOptionPane.showMessageDialog(this, "Habitación actualizada correctamente.");
			limpiarFormulario();
			cargarTabla();
		} else {
			JOptionPane.showMessageDialog(this, resultado, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// ===== MÉTODO ELIMINAR =====
	private void eliminar() {
		if (idSeleccionado == -1) return;
		int op = JOptionPane.showConfirmDialog(
			this,
			"¿Eliminar habitación N° " + txtNumero.getText() + "?",
			"Confirmar",
			JOptionPane.YES_NO_OPTION
		);
		if (op == JOptionPane.YES_OPTION) {
			String resultado = controller.eliminar(idSeleccionado);
			if (resultado.equals("OK")) {
				JOptionPane.showMessageDialog(this, "Habitación eliminada.");
				limpiarFormulario();
				cargarTabla();
			} else {
				JOptionPane.showMessageDialog(this, resultado, "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// ===== MÉTODO CAMBIAR ESTADO =====
	private void cambiarEstado() {
		if (idSeleccionado == -1) return;
		String nuevoEstado = cmbEstado.getSelectedItem().toString();
		if (controller.cambiarEstado(idSeleccionado, nuevoEstado)) {
			JOptionPane.showMessageDialog(this, "Estado cambiado a: " + nuevoEstado);
			cargarTabla();
		}
	}

	// ===== MÉTODO CARGAR FILA SELECCIONADA =====
	private void cargarFilaSeleccionada() {
		int fila = tabla.getSelectedRow();
		idSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
		txtNumero.setText(modeloTabla.getValueAt(fila, 1).toString());
		cmbTipo.setSelectedItem(modeloTabla.getValueAt(fila, 2).toString());
		txtPrecio.setText(modeloTabla.getValueAt(fila, 3).toString().replace("S/ ", ""));
		txtCapacidad.setText(modeloTabla.getValueAt(fila, 4).toString());
		cmbEstado.setSelectedItem(modeloTabla.getValueAt(fila, 5).toString());
		btnEliminar.setEnabled(true);
		btnEditar.setEnabled(true);
		btnCambiarEstado.setEnabled(true);
		btnGuardar.setEnabled(false);
	}

	// ===== MÉTODO LIMPIAR FORMULARIO =====
	private void limpiarFormulario() {
		idSeleccionado = -1;
		txtNumero.setText("");
		txtPrecio.setText("");
		txtCapacidad.setText("");
		txtBuscar.setText("");
		cmbTipo.setSelectedIndex(0);
		cmbEstado.setSelectedIndex(0);
		tabla.clearSelection();
		btnGuardar.setEnabled(true);
		btnEliminar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnCambiarEstado.setEnabled(false);
	}
}