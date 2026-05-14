package com.hotel.sistema.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
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
		setResizable(false);
		setTitle("Login - Sistema Hotel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null); // important for positioning
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
		
		txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
		    public void keyPressed(java.awt.event.KeyEvent e) {
		        if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) validarLogin();
		    }
		});
		
		txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
		    public void keyPressed(java.awt.event.KeyEvent e) {
		        if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) validarLogin();
		    }
		});
	}
	
	// ===== MÉTODO LOGIN =====
	private void validarLogin() {

		String user = txtUsuario.getText();
		String pass = new String(txtPassword.getPassword());

		if (user.trim().isEmpty() || pass.trim().isEmpty()) {
		    JOptionPane.showMessageDialog(this,
		        "Debe ingresar usuario y contraseña.",
		        "Campos requeridos",
		        JOptionPane.WARNING_MESSAGE);
		    return;
		}
		
		LoginController controller = new LoginController();

		if (controller.login(user, pass)) {

			// open menu
			new FrmMenu().setVisible(true);
			dispose();
			
			javax.swing.Timer timer = new javax.swing.Timer(3000, null);
			JOptionPane pane = new JOptionPane("Bienvenido", JOptionPane.INFORMATION_MESSAGE);
			javax.swing.JDialog dialog = pane.createDialog(this, "Login");
			timer.addActionListener(t -> { dialog.dispose(); timer.stop(); });
			timer.start();
			dialog.setVisible(true);

		} else {
			JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
		}
	}

}
