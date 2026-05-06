package com.hotel.sistema.dao;

import com.hotel.sistema.util.Conexion;
import java.sql.*;

public class UsuarioDAO {

	public boolean validar(String user, String pass) {

        String sql = "SELECT * FROM usuario WHERE username=? AND password=?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user);
            ps.setString(2, pass);

            //System.out.println(ps); // debug
            
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            System.out.println("Error en login: " + e.getMessage());
            return false;
        }
    }
}
