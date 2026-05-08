package com.hotel.sistema.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.hotel.sistema.controller.LoginController;

public class FrmLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JTextField txtUsuario;
	private JPasswordField txtPassword;

	/**
	 * Create the frame.
	 */
	public FrmLogin() {
		setTitle("Login - Sistema Hotel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null); // importante para posicionar
		setContentPane(contentPane);
		
		// ===== LABEL USUARIO =====
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(40, 40, 80, 25);
		contentPane.add(lblUsuario);

		// ===== TEXT USUARIO =====
		txtUsuario = new JTextField();
		txtUsuario.setBounds(130, 40, 150, 25);
		contentPane.add(txtUsuario);

		// ===== LABEL PASSWORD =====
		JLabel lblPassword = new JLabel("Contraseña:");
		lblPassword.setBounds(40, 80, 80, 25);
		contentPane.add(lblPassword);

		// ===== TEXT PASSWORD =====
		txtPassword = new JPasswordField();
		txtPassword.setBounds(130, 80, 150, 25);
		contentPane.add(txtPassword);

		// ===== BOTÓN LOGIN =====
		JButton btnLogin = new JButton("Ingresar");
		btnLogin.setBounds(100, 130, 120, 30);
		contentPane.add(btnLogin);

		// ===== EVENTO =====
		btnLogin.addActionListener(e -> validarLogin());

		setLocationRelativeTo(null);
	}
	
	// ===== MÉTODO LOGIN =====
	private void validarLogin() {

		String user = txtUsuario.getText();
		String pass = new String(txtPassword.getPassword());

		LoginController controller = new LoginController();

		if (controller.login(user, pass)) {

			JOptionPane.showMessageDialog(this, "Bienvenido");

			// abrir menú
			new FrmMenu().setVisible(true);
			dispose();

		} else {
			JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
		}
	}

}
