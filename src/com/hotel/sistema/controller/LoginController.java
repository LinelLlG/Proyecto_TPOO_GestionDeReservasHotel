package com.hotel.sistema.controller;

import com.hotel.sistema.dao.UsuarioDAO;

public class LoginController {

	private UsuarioDAO dao = new UsuarioDAO();

    public boolean login(String user, String pass) {

        if (user == null || user.isEmpty() ||
            pass == null || pass.isEmpty()) {

            System.out.println("Campos vacíos");
            return false;
        }

        return dao.validar(user, pass);
    }
}
