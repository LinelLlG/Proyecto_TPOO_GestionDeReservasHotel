package com.hotel.sistema.view;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class FrmMenu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public FrmMenu() {
		setTitle("Sistema de Reservas - Menú Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		// ===== TÍTULO =====
		JLabel lblTitulo = new JLabel("Sistema de Reservas de Hotel");
		lblTitulo.setBounds(120, 30, 300, 30);
		lblTitulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
		contentPane.add(lblTitulo);

		// ===== BARRA DE MENÚ =====
		JMenuBar barraMenu = new JMenuBar();

		// MENÚ SISTEMA
		JMenu menuSistema = new JMenu("Sistema");
		JMenuItem itemCerrarSesion = new JMenuItem("Cerrar sesión");
		JMenuItem itemSalir = new JMenuItem("Salir");

		menuSistema.add(itemCerrarSesion);
		menuSistema.add(itemSalir);

		// MENÚ GESTIÓN
		JMenu menuGestion = new JMenu("Gestión");
		JMenuItem itemHuesped = new JMenuItem("Huéspedes");
		JMenuItem itemHabitacion = new JMenuItem("Habitaciones");
		JMenuItem itemReserva = new JMenuItem("Reservas");

		menuGestion.add(itemHuesped);
		menuGestion.add(itemHabitacion);
		menuGestion.add(itemReserva);

		// AGREGAR MENÚS A LA BARRA
		barraMenu.add(menuSistema);
		barraMenu.add(menuGestion);

		setJMenuBar(barraMenu);

		// ===== EVENTOS =====

		// CERRAR SESIÓN
		itemCerrarSesion.addActionListener(e -> cerrarSesion());

		// SALIR DEL SISTEMA
		itemSalir.addActionListener(e -> System.exit(0));

		// MÓDULOS
		itemHuesped.addActionListener(e -> {
			new FrmHuesped().setVisible(true);
		});

		itemHabitacion.addActionListener(e -> {
			JOptionPane.showMessageDialog(this, "Abrir módulo Habitaciones");
		});

		itemReserva.addActionListener(e -> {
			JOptionPane.showMessageDialog(this, "Abrir módulo Reservas");
		});

		setLocationRelativeTo(null);

	}

	// ===== MÉTODO CERRAR SESIÓN =====
	private void cerrarSesion() {

		int opcion = JOptionPane.showConfirmDialog(
			this,
			"¿Desea cerrar sesión?",
			"Confirmar",
			JOptionPane.YES_NO_OPTION
		);

		if (opcion == JOptionPane.YES_OPTION) {

			new FrmLogin().setVisible(true); // volver al login
			dispose(); // cerrar menú actual
		}
	}
}
