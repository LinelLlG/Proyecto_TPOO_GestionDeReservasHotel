package com.hotel.sistema.view;

import java.awt.*;
import java.awt.EventQueue;

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
	private JTextField txtNumero, txtNombre, txtApellido, txtTelefono, txtCorreo;
    private JTable tabla;
    private DefaultTableModel modelo;
    
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
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(300, 100, 120, 30);
		contentPane.add(btnGuardar);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(430, 100, 120, 30);
		contentPane.add(btnEliminar);

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
		scroll.setBounds(20, 200, 690, 200);
		contentPane.add(scroll);

		// ===== EVENTOS =====
		btnGuardar.addActionListener(e -> guardar());
		btnEliminar.addActionListener(e -> eliminar());

		listar();

		setLocationRelativeTo(null);
	}

	private void guardar() {

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
            }
        } else {
    		JOptionPane.showMessageDialog(this, "Seleccione una fila");
    	}
    }
	
	private void limpiar() {
		txtNumero.setText("");
		txtNombre.setText("");
		txtApellido.setText("");
		txtTelefono.setText("");
		txtCorreo.setText("");
	}
}

