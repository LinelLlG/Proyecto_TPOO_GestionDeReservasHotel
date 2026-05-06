package com.hotel.sistema.dao;

import com.hotel.sistema.model.Huesped;
import com.hotel.sistema.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HuespedDAO {

	public boolean registrar(Huesped h) {

        String sql = "INSERT INTO huesped(tipo_documento, numero_documento, nombres, apellidos, telefono, correo) VALUES (?,?,?,?,?,?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, h.getTipoDocumento());
            ps.setString(2, h.getNumeroDocumento());
            ps.setString(3, h.getNombres());
            ps.setString(4, h.getApellidos());
            ps.setString(5, h.getTelefono());
            ps.setString(6, h.getCorreo());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
	
	public List<Huesped> listar() {

        List<Huesped> lista = new ArrayList<>();

        String sql = "SELECT * FROM huesped";

        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Huesped h = new Huesped();
                h.setId(rs.getInt("id"));
                h.setTipoDocumento(rs.getString("tipo_documento"));
                h.setNumeroDocumento(rs.getString("numero_documento"));
                h.setNombres(rs.getString("nombres"));
                h.setApellidos(rs.getString("apellidos"));
                h.setTelefono(rs.getString("telefono"));
                h.setCorreo(rs.getString("correo"));

                lista.add(h);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
	
	public boolean eliminar(int id) {
        String sql = "DELETE FROM huesped WHERE id=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            return false;
        }
    }
}
