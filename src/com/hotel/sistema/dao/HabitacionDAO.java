package com.hotel.sistema.dao;

import com.hotel.sistema.model.Habitacion;
import com.hotel.sistema.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HabitacionDAO {

	public boolean registrar(Habitacion h) {
		String sql = "INSERT INTO habitacion(numero, tipo, precio, capacidad) VALUES (?,?,?,?)";
		try (Connection con = Conexion.getConexion();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, h.getNumero());
			ps.setString(2, h.getTipo());
			ps.setDouble(3, h.getPrecio());
			ps.setInt(4, h.getCapacidad());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean existeNumero(String numero) {
		String sql = "SELECT COUNT(*) FROM habitacion WHERE numero = ?";
		try (Connection con = Conexion.getConexion();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, numero);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) return rs.getInt(1) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean existeNumeroExcluyendo(String numero, int id) {
		String sql = "SELECT COUNT(*) FROM habitacion WHERE numero = ? AND id != ?";
		try (Connection con = Conexion.getConexion();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, numero);
			ps.setInt(2, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) return rs.getInt(1) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean editar(Habitacion h) {
		String sql = "UPDATE habitacion SET numero=?, tipo=?, precio=?, capacidad=? WHERE id=?";
		try (Connection con = Conexion.getConexion();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, h.getNumero());
			ps.setString(2, h.getTipo());
			ps.setDouble(3, h.getPrecio());
			ps.setInt(4, h.getCapacidad());
			ps.setInt(5, h.getId());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean tieneReservas(int id) {
		String sql = "SELECT COUNT(*) FROM reserva WHERE habitacion_id = ?";
		try (Connection con = Conexion.getConexion();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) return rs.getInt(1) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean eliminar(int id) {
		String sql = "DELETE FROM habitacion WHERE id=?";
		try (Connection con = Conexion.getConexion();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean cambiarEstado(int id, String estado) {
	    String sql = "UPDATE habitacion SET estado=? WHERE id=?";
	    try (Connection con = Conexion.getConexion();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setString(1, estado);
	        ps.setInt(2, id);
	        return ps.executeUpdate() > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public List<Habitacion> listar() {
		List<Habitacion> lista = new ArrayList<Habitacion>();
		String sql = "SELECT * FROM habitacion ORDER BY numero";
		try (Connection con = Conexion.getConexion();
			 Statement st = con.createStatement();
			 ResultSet rs = st.executeQuery(sql)) {
			while (rs.next()) {
				lista.add(mapear(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public List<Habitacion> listarPorTipo(String tipo) {
		List<Habitacion> lista = new ArrayList<Habitacion>();
		String sql = "SELECT * FROM habitacion WHERE tipo = ? ORDER BY numero";
		try (Connection con = Conexion.getConexion();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, tipo);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				lista.add(mapear(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public List<Habitacion> buscarDisponibles(String filtro) {
		List<Habitacion> lista = new ArrayList<Habitacion>();
		String sql = "SELECT * FROM habitacion WHERE numero LIKE ? OR tipo LIKE ? ORDER BY numero";
		try (Connection con = Conexion.getConexion();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, "%" + filtro + "%");
			ps.setString(2, "%" + filtro + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				lista.add(mapear(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	private Habitacion mapear(ResultSet rs) throws SQLException {
		Habitacion h = new Habitacion();
		h.setId(rs.getInt("id"));
		h.setNumero(rs.getString("numero"));
		h.setTipo(rs.getString("tipo"));
		h.setPrecio(rs.getDouble("precio"));
		h.setCapacidad(rs.getInt("capacidad"));
		h.setEstado(rs.getString("estado"));
		return h;
	}
	
	public Habitacion buscarPorNumero(String numero) {

		String sql = """
			SELECT *
			FROM habitacion
			WHERE numero = ?
		""";

		try (Connection con = Conexion.getConexion();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, numero);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				Habitacion h = new Habitacion();
				h.setId(rs.getInt("id"));
				h.setNumero(rs.getString("numero"));
				h.setTipo(rs.getString("tipo"));
				h.setPrecio(rs.getDouble("precio"));
				h.setCapacidad(rs.getInt("capacidad"));
				h.setEstado(rs.getString("estado"));

				return h;
			}

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}

		return null;
	}
}