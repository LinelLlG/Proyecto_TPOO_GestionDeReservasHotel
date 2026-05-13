package com.hotel.sistema.view;

import java.util.List;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.hotel.sistema.controller.HuespedController;
import com.hotel.sistema.model.Huesped;

public class FrmHuesped extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JComboBox<String> cbTipoDoc;
	private JTextField txtNumero, txtNombre, txtApellido, txtTelefono, txtCorreo, txtBuscar;
    private JTable tabla;
    private DefaultTableModel modelo;
    private int idSeleccionado = -1;
    
    private JButton btnGuardar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnNuevo;
    
    private HuespedController controller = new HuespedController();

    
	/**
	 * Create the frame.
	 */
	public FrmHuesped() {
		setTitle("Gestión de Huéspedes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 750, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// ===== TIPO DOCUMENTO =====
		JLabel lblTipo = new JLabel("Tipo Doc:");
		lblTipo.setBounds(20, 20, 100, 25);
		contentPane.add(lblTipo);

		cbTipoDoc = new JComboBox<>(new String[]{"DNI", "Pasaporte"});
		cbTipoDoc.setBounds(120, 20, 150, 25);
		contentPane.add(cbTipoDoc);

		// ===== NUMERO DOCUMENTO =====
		JLabel lblNumero = new JLabel("N° Documento:");
		lblNumero.setBounds(20, 60, 100, 25);
		contentPane.add(lblNumero);

		txtNumero = new JTextField();
		txtNumero.setBounds(120, 60, 150, 25);
		contentPane.add(txtNumero);

		// ===== NOMBRES =====
		JLabel lblNombre = new JLabel("Nombres:");
		lblNombre.setBounds(20, 100, 100, 25);
		contentPane.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(120, 100, 150, 25);
		contentPane.add(txtNombre);

		// ===== APELLIDOS =====
		JLabel lblApellido = new JLabel("Apellidos:");
		lblApellido.setBounds(20, 140, 100, 25);
		contentPane.add(lblApellido);

		txtApellido = new JTextField();
		txtApellido.setBounds(120, 140, 150, 25);
		contentPane.add(txtApellido);

		// ===== TELEFONO =====
		JLabel lblTelefono = new JLabel("Teléfono:");
		lblTelefono.setBounds(300, 20, 100, 25);
		contentPane.add(lblTelefono);

		txtTelefono = new JTextField();
		txtTelefono.setBounds(400, 20, 150, 25);
		contentPane.add(txtTelefono);

		// ===== CORREO =====
		JLabel lblCorreo = new JLabel("Correo:");
		lblCorreo.setBounds(300, 60, 100, 25);
		contentPane.add(lblCorreo);

		txtCorreo = new JTextField();
		txtCorreo.setBounds(400, 60, 150, 25);
		contentPane.add(txtCorreo);
		
		// ===== BOTONES =====
		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(300, 100, 120, 30);
		contentPane.add(btnGuardar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(430, 100, 120, 30);
		btnEliminar.setEnabled(false);
		contentPane.add(btnEliminar);

		btnEditar = new JButton("Editar");
		btnEditar.setBounds(560, 100, 120, 30);
		btnEditar.setEnabled(false);
		contentPane.add(btnEditar);
		
		btnNuevo = new JButton("Nuevo");
		btnNuevo.setBounds(560, 20, 120, 30);
		contentPane.add(btnNuevo);
		
		// ===== BUSCAR =====
		JLabel lblBuscar = new JLabel("Buscar:");
		lblBuscar.setBounds(300, 140, 80, 25);
		contentPane.add(lblBuscar);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(370, 140, 180, 25);
		contentPane.add(txtBuscar);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(560, 140, 120, 25);
		contentPane.add(btnBuscar);
		
		// ===== TABLA =====
		modelo = new DefaultTableModel();
		modelo.addColumn("ID");
		modelo.addColumn("Tipo");
		modelo.addColumn("Documento");
		modelo.addColumn("Nombre");
		modelo.addColumn("Apellido");
		modelo.addColumn("Teléfono");
		modelo.addColumn("Correo");

		tabla = new JTable(modelo);

		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBounds(20, 190, 690, 200);
		contentPane.add(scroll);

		// ===== EVENTOS =====
		btnGuardar.addActionListener(e -> guardar());
		btnEliminar.addActionListener(e -> eliminar());
		btnEditar.addActionListener(e -> editar());
		btnBuscar.addActionListener(e -> buscar());
		btnNuevo.addActionListener(e -> limpiar());
		
		tabla.getSelectionModel().addListSelectionListener(e -> cargarDatosTabla());

		listar();

		setLocationRelativeTo(null);
	}

	private void guardar() {
		
		if (!validarCampos()) {
			return;
		}

		Huesped h = new Huesped();

		h.setTipoDocumento(cbTipoDoc.getSelectedItem().toString());
		h.setNumeroDocumento(txtNumero.getText());
		h.setNombres(txtNombre.getText());
		h.setApellidos(txtApellido.getText());
		h.setTelefono(txtTelefono.getText());
		h.setCorreo(txtCorreo.getText());

		if (controller.guardar(h)) {
			JOptionPane.showMessageDialog(this, "Guardado correctamente");
			limpiar();
			listar();
		} else {
			JOptionPane.showMessageDialog(this, "Error al guardar");
		}
    }
	
	private void listar() {

		modelo.setRowCount(0);

		List<Huesped> lista = controller.listar();

		for (Huesped h : lista) {
			modelo.addRow(new Object[]{
				h.getId(),
				h.getTipoDocumento(),
				h.getNumeroDocumento(),
				h.getNombres(),
				h.getApellidos(),
				h.getTelefono(),
				h.getCorreo()
			});
		}
    }
	
	private void eliminar() {

        int fila = tabla.getSelectedRow();

        if (fila >= 0) {

            int id = (int) tabla.getValueAt(fila, 0);

            if (controller.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Eliminado");
                listar();
                limpiar();
            }
        } else {
    		JOptionPane.showMessageDialog(this, "Seleccione una fila");
    	}
    }
	
	private void cargarDatosTabla() {

		int fila = tabla.getSelectedRow();

		if (fila >= 0) {

			idSeleccionado = (int) tabla.getValueAt(fila, 0);
			cbTipoDoc.setSelectedItem(tabla.getValueAt(fila, 1).toString());
			txtNumero.setText(tabla.getValueAt(fila, 2).toString());
			txtNombre.setText(tabla.getValueAt(fila, 3).toString());
			txtApellido.setText(tabla.getValueAt(fila, 4).toString());
			txtTelefono.setText(tabla.getValueAt(fila, 5).toString());
			txtCorreo.setText(tabla.getValueAt(fila, 6).toString());
			
			// BLOQUEAR CAMPOS
			txtNumero.setEditable(false);
			txtNombre.setEditable(false);
			txtApellido.setEditable(false);
			cbTipoDoc.setEnabled(false);
			
			// ESTADOS BOTONES
			btnGuardar.setEnabled(false);
			btnEditar.setEnabled(true);
			btnEliminar.setEnabled(true);
		}
	}
	
	private void editar() {

		if (idSeleccionado == -1) {

			JOptionPane.showMessageDialog(this, "Seleccione un huésped");
			return;
		}
		
		if (!validarCampos()) {
			return;
		}

		Huesped h = new Huesped();

		h.setId(idSeleccionado);
		h.setTelefono(txtTelefono.getText());
		h.setCorreo(txtCorreo.getText());

		if (controller.editar(h)) {

			JOptionPane.showMessageDialog(this, "Actualizado correctamente");
			listar();
			limpiar();

			idSeleccionado = -1;

		} else {

			JOptionPane.showMessageDialog(this, "Error al actualizar");
		}
	}
	
	private void buscar() {

		modelo.setRowCount(0);

		List<Huesped> lista = controller.buscar(txtBuscar.getText());

		for (Huesped h : lista) {

			modelo.addRow(new Object[] {
					h.getId(),
					h.getTipoDocumento(),
					h.getNumeroDocumento(),
					h.getNombres(),
					h.getApellidos(),
					h.getTelefono(),
					h.getCorreo()
			});
		}
	}
	
	private void limpiar() {
		txtNumero.setText("");
		txtNombre.setText("");
		txtApellido.setText("");
		txtTelefono.setText("");
		txtCorreo.setText("");
		txtBuscar.setText("");

		cbTipoDoc.setSelectedIndex(0);

		idSeleccionado = -1;

		tabla.clearSelection();
		
		// HABILITAR CAMPOS
		txtNumero.setEditable(true);
		txtNombre.setEditable(true);
		txtApellido.setEditable(true);
		cbTipoDoc.setEnabled(true);
		
		// ESTADO BOTONES
		btnGuardar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnEliminar.setEnabled(false);
	}
	
	private boolean validarCampos() {

		String tipoDoc = cbTipoDoc.getSelectedItem().toString();
		String numero = txtNumero.getText().trim();
		String nombre = txtNombre.getText().trim();
		String apellido = txtApellido.getText().trim();
		String telefono = txtTelefono.getText().trim();
		String correo = txtCorreo.getText().trim();

		// ===== DOCUMENTO =====
		if (numero.isEmpty()) {

			JOptionPane.showMessageDialog(this, "Ingrese número de documento");
			txtNumero.requestFocus();

			return false;
		}

		// DNI
		if (tipoDoc.equals("DNI")) {

			if (!numero.matches("\\d+")) {

				JOptionPane.showMessageDialog(this, "El DNI solo debe contener números");
				txtNumero.requestFocus();

				return false;
			}

			if (numero.length() != 8) {

				JOptionPane.showMessageDialog(this, "El DNI debe tener 8 dígitos");
				txtNumero.requestFocus();

				return false;
			}
		}

		// ===== NOMBRE =====
		if (nombre.isEmpty()) {

			JOptionPane.showMessageDialog(this, "Ingrese nombres");
			txtNombre.requestFocus();

			return false;
		}

		// ===== APELLIDO =====
		if (apellido.isEmpty()) {

			JOptionPane.showMessageDialog(this, "Ingrese apellidos");
			txtApellido.requestFocus();

			return false;
		}

		// ===== TELEFONO =====
		if (!telefono.isEmpty()) {

			if (!telefono.matches("\\d+")) {

				JOptionPane.showMessageDialog(this, "El teléfono solo debe contener números");
				txtTelefono.requestFocus();

				return false;
			}
		}

		// ===== CORREO =====
		if (!correo.isEmpty()) {

			if (!correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {

				JOptionPane.showMessageDialog(this, "Ingrese un correo válido");
				txtCorreo.requestFocus();

				return false;
			}
		}

		return true;
	}
}

