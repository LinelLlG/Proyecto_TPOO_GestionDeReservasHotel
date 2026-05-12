package com.hotel.sistema.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.time.LocalDate;
import java.time.ZoneId;

import com.hotel.sistema.controller.HabitacionController;
import com.hotel.sistema.model.Habitacion;
import com.hotel.sistema.controller.HuespedController;
import com.hotel.sistema.model.Huesped;
import com.hotel.sistema.controller.ReservaController;
import com.hotel.sistema.model.Reserva;

import com.toedter.calendar.JDateChooser;


public class FrmReserva extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	// ===== HUESPED =====
		private JTextField txtDocumento;
		private JTextField txtNombre;
		private JButton btnBuscarHuesped;

		// ===== HABITACION =====
		private JTextField txtNumeroHabitacion;
		private JTextField txtTipoHabitacion;
		private JTextField txtPrecio;

		private JButton btnBuscarHabitacion;

		// ===== RESERVA =====
		private JDateChooser dcFechaInicio;
		private JDateChooser dcFechaFin;
		private JSpinner spCantidadPersonas;
		private JTextField txtTotal;

		// ===== BOTONES =====
		private JButton btnNuevo;
		private JButton btnReservar;
		private JButton btnCancelarReserva;
		private JButton btnEditar;
		private JButton btnCheckIn;
		private JButton btnCheckOut;

		// ===== TABLA =====
		private JTable tabla;
		private DefaultTableModel modelo;

		// ===== IDS =====
		private int idHuesped = -1;
		private int idHabitacion = -1;
		private int idReservaSeleccionada = -1;
		private int capacidadHabitacion = 0;

		// ===== CONTROLLER =====
		private ReservaController controller = new ReservaController();
		private HuespedController huespedController = new HuespedController();
		private HabitacionController habitacionController = new HabitacionController();
		
	/**
	 * Create the frame.
	 */
	public FrmReserva() {
		setTitle("Gestión de Reservas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 950, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		// =====================================================
		// TITULO
		// =====================================================

		JLabel lblTitulo = new JLabel("SISTEMA DE RESERVAS");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTitulo.setBounds(320, 10, 300, 30);
		contentPane.add(lblTitulo);

		// =====================================================
		// HUESPED
		// =====================================================

		JLabel lblHuesped = new JLabel("DATOS DEL HUÉSPED");
		lblHuesped.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblHuesped.setBounds(20, 60, 200, 25);
		contentPane.add(lblHuesped);

		JLabel lblDocumento = new JLabel("Documento:");
		lblDocumento.setBounds(20, 95, 100, 25);
		contentPane.add(lblDocumento);
		
		txtDocumento = new JTextField();
		txtDocumento.setBounds(120, 95, 150, 25);
		txtDocumento.setEditable(false);
		contentPane.add(txtDocumento);

		btnBuscarHuesped = new JButton("Buscar");
		btnBuscarHuesped.setBounds(280, 95, 100, 25);
		contentPane.add(btnBuscarHuesped);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(20, 130, 100, 25);
		contentPane.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(120, 130, 260, 25);
		txtNombre.setEditable(false);
		contentPane.add(txtNombre);

		// =====================================================
		// HABITACION
		// =====================================================

		JLabel lblHabitacion = new JLabel("DATOS DE HABITACIÓN");
		lblHabitacion.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblHabitacion.setBounds(470, 60, 220, 25);
		contentPane.add(lblHabitacion);

		JLabel lblNumeroHab = new JLabel("Habitación:");
		lblNumeroHab.setBounds(470, 95, 100, 25);
		contentPane.add(lblNumeroHab);
		
		txtNumeroHabitacion = new JTextField();
		txtNumeroHabitacion.setBounds(570, 95, 120, 25);
		txtNumeroHabitacion.setEditable(false);
		contentPane.add(txtNumeroHabitacion);

		btnBuscarHabitacion = new JButton("Buscar");
		btnBuscarHabitacion.setBounds(700, 95, 100, 25);
		contentPane.add(btnBuscarHabitacion);

		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(470, 130, 100, 25);
		contentPane.add(lblTipo);

		txtTipoHabitacion = new JTextField();
		txtTipoHabitacion.setBounds(570, 130, 120, 25);
		txtTipoHabitacion.setEditable(false);
		contentPane.add(txtTipoHabitacion);

		JLabel lblPrecio = new JLabel("Precio/Noche:");
		lblPrecio.setBounds(710, 130, 100, 25);
		contentPane.add(lblPrecio);

		txtPrecio = new JTextField();
		txtPrecio.setBounds(810, 130, 100, 25);
		txtPrecio.setEditable(false);
		contentPane.add(txtPrecio);

		// =====================================================
		// RESERVA
		// =====================================================

		JLabel lblReserva = new JLabel("DATOS DE RESERVA");

		lblReserva.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblReserva.setBounds(20, 200, 220, 25);
		contentPane.add(lblReserva);

		JLabel lblFechaInicio = new JLabel("Fecha Inicio:");
		lblFechaInicio.setBounds(20, 240, 100, 25);
		contentPane.add(lblFechaInicio);

		dcFechaInicio = new JDateChooser();
		dcFechaInicio.setBounds(120, 240, 150, 25);
		contentPane.add(dcFechaInicio);

		JLabel lblFechaFin = new JLabel("Fecha Fin:");
		lblFechaFin.setBounds(300, 240, 100, 25);
		contentPane.add(lblFechaFin);

		dcFechaFin = new JDateChooser();
		dcFechaFin.setBounds(390, 240, 150, 25);
		contentPane.add(dcFechaFin);

		JLabel lblCantidad = new JLabel("Cant. Personas:");
		lblCantidad.setBounds(570, 240, 120, 25);
		contentPane.add(lblCantidad);

		spCantidadPersonas = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
		spCantidadPersonas.setBounds(690, 240, 70, 25);
		contentPane.add(spCantidadPersonas);

		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setBounds(780, 240, 50, 25);
		contentPane.add(lblTotal);

		txtTotal = new JTextField();
		txtTotal.setBounds(830, 240, 80, 25);
		txtTotal.setEditable(false);
		contentPane.add(txtTotal);

		// =====================================================
		// BOTONES
		// =====================================================

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setBounds(120, 300, 120, 30);
		contentPane.add(btnNuevo);

		btnReservar = new JButton("Reservar");
		btnReservar.setBounds(260, 300, 120, 30);
		contentPane.add(btnReservar);

		btnEditar = new JButton("Editar");
		btnEditar.setBounds(400, 300, 120, 30);
		btnEditar.setEnabled(false);
		contentPane.add(btnEditar);

		btnCancelarReserva = new JButton("Cancelar");
		btnCancelarReserva.setBounds(540, 300, 120, 30);
		btnCancelarReserva.setEnabled(false);
		contentPane.add(btnCancelarReserva);

		btnCheckIn = new JButton("Check-In");
		btnCheckIn.setBounds(680, 300, 110, 30);
		btnCheckIn.setEnabled(false);
		contentPane.add(btnCheckIn);

		btnCheckOut = new JButton("Check-Out");
		btnCheckOut.setBounds(800, 300, 110, 30);
		btnCheckOut.setEnabled(false);
		contentPane.add(btnCheckOut);

		// =====================================================
		// TABLA
		// =====================================================

		modelo = new DefaultTableModel();
		modelo.addColumn("ID");
		modelo.addColumn("Huésped");
		modelo.addColumn("Habitación");
		modelo.addColumn("Inicio");
		modelo.addColumn("Fin");
		modelo.addColumn("Personas");
		modelo.addColumn("Total");
		modelo.addColumn("Estado");

		tabla = new JTable(modelo);

		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBounds(20, 360, 890, 180);
		contentPane.add(scroll);

		// =====================================================
		// EVENTOS
		// =====================================================

		btnNuevo.addActionListener(e -> nuevo());
		btnReservar.addActionListener(e -> reservar());
		btnCancelarReserva.addActionListener(e -> cancelarReserva());
		btnEditar.addActionListener(e -> editarReserva());
		btnBuscarHuesped.addActionListener(e -> buscarHuesped());
		btnBuscarHabitacion.addActionListener(e -> buscarHabitacion());
		btnCheckIn.addActionListener(e -> realizarCheckIn());
		btnCheckOut.addActionListener(e -> realizarCheckOut());

		tabla.getSelectionModel().addListSelectionListener(e -> cargarDatosTabla());

		listar();

		setLocationRelativeTo(null);
	}

	// =====================================================
	// METODOS
	// =====================================================

	private void nuevo() {

		limpiar();
	}

	private void reservar() {

		// ===== VALIDAR HUESPED =====

		if (idHuesped == -1) {

			JOptionPane.showMessageDialog(this, "Debe seleccionar un huésped");

			return;
		}

		// ===== VALIDAR HABITACION =====

		if (idHabitacion == -1) {

			JOptionPane.showMessageDialog(this, "Debe seleccionar una habitación");

			return;
		}

		// ===== VALIDAR FECHAS =====

		if (dcFechaInicio.getDate() == null || dcFechaFin.getDate() == null) {

			JOptionPane.showMessageDialog(this, "Debe seleccionar fechas");

			return;
		}

		// ===== CONVERTIR FECHAS =====

		LocalDate fechaInicio = dcFechaInicio.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate fechaFin = dcFechaFin.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		// ===== VALIDAR RANGO =====

		if (fechaFin.isBefore(fechaInicio) || fechaFin.isEqual(fechaInicio)) {

			JOptionPane.showMessageDialog(this, "La fecha fin debe ser mayor");

			return;
		}

		// ===== CANTIDAD PERSONAS =====

		int cantidad = (int) spCantidadPersonas.getValue();
		
		// ===== VALIDAR CAPACIDAD =====

		if (cantidad > capacidadHabitacion) {

			JOptionPane.showMessageDialog(this, "La cantidad de personas supera " + "la capacidad de la habitación");
			return;
		}

		// ===== PRECIO =====

		double precio = Double.parseDouble(txtPrecio.getText());

		// ===== CALCULAR TOTAL =====

		double total = controller.calcularTotal(fechaInicio, fechaFin, precio);
		txtTotal.setText(String.format("%.2f", total));

		// ===== CREAR OBJETO =====

		com.hotel.sistema.model.Reserva r = new com.hotel.sistema.model.Reserva();

		r.setIdHuesped(idHuesped);
		r.setIdHabitacion(idHabitacion);
		r.setFechaInicio(fechaInicio);
		r.setFechaFin(fechaFin);
		r.setCantidadPersonas(cantidad);
		r.setPrecioNoche(precio);
		r.setTotal(total);
		r.setEstado("Activa");

		// ===== GUARDAR =====

		String resultado = controller.guardar(r);

		if (resultado.equals("OK")) {

			JOptionPane.showMessageDialog(this, "Reserva registrada correctamente");
			
			listar();

			limpiar();

		} else {

			JOptionPane.showMessageDialog(this, resultado);
		}
	}

	private void cancelarReserva() {

		if (idReservaSeleccionada == -1) {

			JOptionPane.showMessageDialog(this, "Seleccione una reserva");

			return;
		}

		int op = JOptionPane.showConfirmDialog(this, "¿Desea cancelar la reserva?", "Confirmar", JOptionPane.YES_NO_OPTION);

		if (op != JOptionPane.YES_OPTION) {
			return;
		}

		boolean ok = controller.cancelar(idReservaSeleccionada, idHabitacion);

		if (ok) {

			JOptionPane.showMessageDialog(this, "Reserva cancelada");
			listar();
			limpiar();

		} else {

			JOptionPane.showMessageDialog(this, "No se pudo cancelar");
		}
	}

	private void limpiar() {

		idHuesped = -1;
		idHabitacion = -1;
		idReservaSeleccionada = -1;

		txtDocumento.setText("");
		txtNombre.setText("");
		txtNumeroHabitacion.setText("");
		txtTipoHabitacion.setText("");
		txtPrecio.setText("");
		txtTotal.setText("");
		spCantidadPersonas.setValue(1);
		
		dcFechaInicio.setDate(null);
		dcFechaFin.setDate(null);
		tabla.clearSelection();
		btnReservar.setEnabled(true);
		btnCancelarReserva.setEnabled(false);
		btnCheckIn.setEnabled(false);
		btnCheckOut.setEnabled(false);
		btnEditar.setEnabled(false);
	}
	
	private void editarReserva() {

		if (idReservaSeleccionada == -1) {

			JOptionPane.showMessageDialog(this, "Seleccione una reserva");

			return;
		}

		try {

			LocalDate inicio = dcFechaInicio.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
			LocalDate fin = dcFechaFin.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
			int cantidad = (int) spCantidadPersonas.getValue();

			// ===== VALIDAR CAPACIDAD =====

			if (cantidad > capacidadHabitacion) {

				JOptionPane.showMessageDialog(this, "La cantidad supera la capacidad");

				return;
			}

			double total = controller.calcularTotal(inicio, fin, Double.parseDouble(txtPrecio.getText()));

			Reserva r = new Reserva();

			r.setId(idReservaSeleccionada);
			r.setIdHuesped(idHuesped);
			r.setIdHabitacion(idHabitacion);
			r.setFechaInicio(inicio);
			r.setFechaFin(fin);
			r.setCantidadPersonas(cantidad);
			r.setPrecioNoche(Double.parseDouble(txtPrecio.getText()));
			r.setTotal(total);
			r.setEstado("Activa");

			String resultado = controller.editar(r);

			if (resultado.equals("OK")) {

				JOptionPane.showMessageDialog(this, "Reserva actualizada");
				listar();
				limpiar();

			} else {

				JOptionPane.showMessageDialog(this, resultado);
			}

		} catch (Exception e) {

			JOptionPane.showMessageDialog(this, "Datos inválidos");
		}
	}
	
	private void buscarHuesped() {

		String documento = JOptionPane.showInputDialog(this, "Ingrese documento:");

		if (documento == null || documento.trim().isEmpty()) {

			return;
		}

		Huesped h = huespedController.buscarPorDocumento(documento);

		if (h != null) {

			idHuesped = h.getId();
			txtDocumento.setText(h.getNumeroDocumento());
			txtNombre.setText(h.getNombres() + " " + h.getApellidos());

		} else {

			JOptionPane.showMessageDialog(this, "Huésped no encontrado");
		}
	}
	
	private void buscarHabitacion() {

		String numero = JOptionPane.showInputDialog(this, "Ingrese número habitación:");

		if (numero == null || numero.trim().isEmpty()) {

			return;
		}

		Habitacion h = habitacionController.buscarPorNumero(numero);

		if (h != null) {

			// VALIDAR DISPONIBLE

			if (!h.getEstado().equalsIgnoreCase("Disponible")) {

				JOptionPane.showMessageDialog(this, "La habitación no está disponible");
				return;
			}

			idHabitacion = h.getId();
			txtNumeroHabitacion.setText(h.getNumero());
			txtTipoHabitacion.setText(h.getTipo());
			txtPrecio.setText(String.valueOf(h.getPrecio()));
			capacidadHabitacion = h.getCapacidad();
			spCantidadPersonas.setModel(new SpinnerNumberModel(1, 1, capacidadHabitacion, 1));

			// VALIDAR CAPACIDAD

			int cantidad = (int) spCantidadPersonas.getValue();

			if (cantidad > h.getCapacidad()) {

				JOptionPane.showMessageDialog(this, "La cantidad supera la capacidad");

				limpiarHabitacion();
			}

		} else {

			JOptionPane.showMessageDialog(this, "Habitación no encontrada");
		}
	}
	
	private void realizarCheckIn() {

		if (idReservaSeleccionada == -1) {

			JOptionPane.showMessageDialog(this, "Seleccione una reserva");

			return;
		}

		boolean ok =
				controller.realizarCheckIn(idReservaSeleccionada, idHabitacion);

		if (ok) {

			JOptionPane.showMessageDialog(this, "Check-In realizado");
			listar();
			limpiar();

		} else {

			JOptionPane.showMessageDialog(this, "No se pudo realizar check-in");
		}
	}
	
	private void realizarCheckOut() {

		if (idReservaSeleccionada == -1) {

			JOptionPane.showMessageDialog(this, "Seleccione una reserva");
			return;
		}

		boolean ok = controller.realizarCheckOut(idReservaSeleccionada, idHabitacion);

		if (ok) {

			JOptionPane.showMessageDialog(this, "Check-Out realizado");
			listar();
			limpiar();

		} else {

			JOptionPane.showMessageDialog(this, "No se pudo realizar check-out");
		}
	}
	
	private void listar() {

		modelo.setRowCount(0);

		controller.listar().forEach(r -> {

			modelo.addRow(new Object[] {
					r.getId(),
					r.getNombreHuesped(),
					r.getNumeroHabitacion(),
					r.getFechaInicio(),
					r.getFechaFin(),
					r.getCantidadPersonas(),
					r.getTotal(),
					r.getEstado()
			});
		});
	}
	
	private void cargarDatosTabla() {

		int fila = tabla.getSelectedRow();

		if (fila == -1) { return; }

		idReservaSeleccionada = (int) modelo.getValueAt(fila, 0);

		// ===== BUSCAR RESERVA COMPLETA =====

		com.hotel.sistema.model.Reserva r = controller.buscarPorId(idReservaSeleccionada);

		if (r == null) { return; }

		idHuesped =r.getIdHuesped();
		idHabitacion =r.getIdHabitacion();
		txtDocumento.setText(r.getDocumentoHuesped());
		txtNombre.setText(r.getNombreHuesped());
		txtNumeroHabitacion.setText(r.getNumeroHabitacion());
		dcFechaInicio.setDate(java.sql.Date.valueOf(r.getFechaInicio()));
		dcFechaFin.setDate(java.sql.Date.valueOf(r.getFechaFin()));
		spCantidadPersonas.setValue(r.getCantidadPersonas());
		txtTotal.setText(String.valueOf(r.getTotal()));

		// ===== CARGAR DATOS COMPLETOS HABITACION =====
		Habitacion hab = habitacionController.buscarPorNumero(r.getNumeroHabitacion());

		if (hab != null) {

			txtTipoHabitacion.setText(hab.getTipo());
			txtPrecio.setText(String.valueOf(hab.getPrecio()));
			capacidadHabitacion = hab.getCapacidad();
			spCantidadPersonas.setModel(new SpinnerNumberModel(1, 1, capacidadHabitacion, 1));
			spCantidadPersonas.setValue(r.getCantidadPersonas());
		}
		
		// ===== DESHABILITAR TODO =====
		btnReservar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnCancelarReserva.setEnabled(false);
		btnCheckIn.setEnabled(false);
		btnCheckOut.setEnabled(false);

		// ===== ESTADO RESERVA =====
		String estado = r.getEstado();

		// ===== RESERVA ACTIVA =====
		if (estado.equals("Activa")) {

			// CANCELAR
			btnCancelarReserva.setEnabled(true);

			// VALIDAR EDICION
			long dias = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), r.getFechaInicio());
			btnEditar.setEnabled(dias > 1);

			// CHECK IN
			btnCheckIn.setEnabled(true);
		}

		// ===== HOSPEDADO =====
		if (estado.equals("Hospedado")) {

			btnCheckOut.setEnabled(true);
		}
	}
	
	private void limpiarHabitacion() {

		idHabitacion = -1;
		txtNumeroHabitacion.setText("");
		txtTipoHabitacion.setText("");
		txtPrecio.setText("");
	}
}
