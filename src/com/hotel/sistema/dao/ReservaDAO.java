package com.hotel.sistema.dao;

import com.hotel.sistema.model.Reserva;
import com.hotel.sistema.util.Conexion;

import java.sql.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

	// ===== VERIFICAR DISPONIBILIDAD - REGISTRAR Y EDITAR=====
    public boolean verificarDisponibilidad(int idHabitacion, LocalDate fechaInicio, LocalDate fechaFin) {

        String sql = """
            SELECT COUNT(*)
            FROM reserva
            WHERE id_habitacion = ?
            AND estado = 'Activa'
            AND (
                fecha_inicio <= ?
                AND fecha_fin >= ?
            )
        """;

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idHabitacion);
            ps.setDate(2, Date.valueOf(fechaFin));
            ps.setDate(3, Date.valueOf(fechaInicio));
            
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getInt(1) == 0;
            }

        } catch (Exception e) {

            System.out.println("Error verificando disponibilidad: " + e.getMessage());
        }

        return false;
    }
    
    public boolean verificarDisponibilidad(int idHabitacion, LocalDate fechaInicio, LocalDate fechaFin, int idReservaExcluir) {

    	String sql = """
    		SELECT COUNT(*)
    		FROM reserva
    		WHERE id_habitacion = ?
    		AND id <> ?
    		AND estado IN ('Activa', 'Hospedado')
    		AND (
    			fecha_inicio <= ?
    			AND fecha_fin >= ?
    		)
    	""";

    	try (Connection con = Conexion.getConexion();
    		 PreparedStatement ps = con.prepareStatement(sql)) {

    		ps.setInt(1, idHabitacion);
    		ps.setInt(2, idReservaExcluir);
    		ps.setDate(3, Date.valueOf(fechaFin));
    		ps.setDate(4, Date.valueOf(fechaInicio));
    		ResultSet rs = ps.executeQuery();

    		if (rs.next()) {

    			return rs.getInt(1) == 0;
    		}

    	} catch (Exception e) {

    		System.out.println("Error verificando disponibilidad: " + e.getMessage());
    	}

    	return false;
    }

    // ===== CALCULAR TOTAL =====
    public double calcularTotal(LocalDate inicio, LocalDate fin, double precioNoche) {

        long dias = ChronoUnit.DAYS.between(inicio, fin);

        return dias * precioNoche;
    }

    // ===== GUARDAR RESERVA =====
    public String guardar(Reserva r) {

        // VALIDAR DISPONIBILIDAD
        if (!verificarDisponibilidad(
                r.getIdHabitacion(),
                r.getFechaInicio(),
                r.getFechaFin())) {

            return "La habitación no está disponible en esas fechas";
        }

        String sql = """
            INSERT INTO reserva(
                id_huesped,
                id_habitacion,
                fecha_inicio,
                fecha_fin,
                cantidad_personas,
                precio_noche,
                total,
                estado
            )
            VALUES(?,?,?,?,?,?,?,?)
        """;

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, r.getIdHuesped());
            ps.setInt(2, r.getIdHabitacion());
            ps.setDate(3, Date.valueOf(r.getFechaInicio()));
            ps.setDate(4, Date.valueOf(r.getFechaFin()));
            ps.setInt(5, r.getCantidadPersonas());
            ps.setDouble(6, r.getPrecioNoche());
            ps.setDouble(7, r.getTotal());
            ps.setString(8, r.getEstado());

            int filas = ps.executeUpdate();

            if (filas > 0) {

                cambiarEstadoHabitacion(r.getIdHabitacion(), "En reserva");

                return "OK";
            }

        } catch (Exception e) {

            System.out.println("Error guardando reserva: " + e.getMessage());
        }

        return "Error al registrar reserva";
    }

    // ===== LISTAR RESERVAS =====
    public List<Reserva> listar() {

    	List<Reserva> lista = new ArrayList<>();

    	String sql = """
    		SELECT
    			r.id,
    			r.id_huesped,
    			r.id_habitacion,
    			r.fecha_inicio,
    			r.fecha_fin,
    			r.cantidad_personas,
    			r.precio_noche,
    			r.total,
    			r.estado,
    			h.numero_documento,
    			h.nombres,
    			h.apellidos,
    			ha.numero
    		FROM reserva r
    		INNER JOIN huesped h
    			ON r.id_huesped = h.id
    		INNER JOIN habitacion ha
    			ON r.id_habitacion = ha.id
    		ORDER BY
				CASE r.estado
					WHEN 'Activa' THEN 1
					WHEN 'Hospedado' THEN 2
					WHEN 'Finalizada' THEN 3
					WHEN 'Cancelada' THEN 4
					ELSE 5
				END,
				r.fecha_inicio ASC
    	""";

    	try (Connection con = Conexion.getConexion();
    		 PreparedStatement ps = con.prepareStatement(sql);
    		 ResultSet rs = ps.executeQuery()) {

    		while (rs.next()) {

    			Reserva r = new Reserva();
    			r.setId(rs.getInt("id"));
    			r.setIdHuesped(rs.getInt("id_huesped"));
    			r.setIdHabitacion(rs.getInt("id_habitacion"));
    			r.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
    			r.setFechaFin(rs.getDate("fecha_fin").toLocalDate());
    			r.setCantidadPersonas(rs.getInt("cantidad_personas"));
    			r.setPrecioNoche(rs.getDouble("precio_noche"));
    			r.setTotal(rs.getDouble("total"));
    			r.setEstado(rs.getString("estado"));

    			// ===== NUEVOS CAMPOS =====
    			r.setNombreHuesped(rs.getString("nombres") + " " + rs.getString("apellidos"));
    			r.setNumeroHabitacion(rs.getString("numero"));
    			r.setDocumentoHuesped(rs.getString("numero_documento"));

    			lista.add(r);
    		}

    	} catch (Exception e) {

    		System.out.println("Error listar reservas: " + e.getMessage());
    	}

    	return lista;
    }

    // ===== CANCELAR RESERVA =====
    public boolean cancelar(int idReserva, int idHabitacion) {

        String sql = """
            UPDATE reserva
            SET estado = 'Cancelada'
            WHERE id = ?
        """;

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idReserva);

            int filas = ps.executeUpdate();

            if (filas > 0) {

                cambiarEstadoHabitacion(idHabitacion, "Disponible");
                return true;
            }

        } catch (Exception e) {

            System.out.println("Error cancelando reserva: " + e.getMessage());
        }

        return false;
    }

    // ===== CAMBIAR ESTADO HABITACION =====
    private void cambiarEstadoHabitacion(int idHabitacion, String estado) {

        String sql = """
            UPDATE habitacion
            SET estado = ?
            WHERE id = ?
        """;

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado);
            ps.setInt(2, idHabitacion);

            ps.executeUpdate();

        } catch (Exception e) {

            System.out.println("Error cambiando estado habitación: " + e.getMessage());
        }
    }
    
    // ===== EDITAR RESERVA =====
    public String editar(Reserva r) {

    	// ===== VALIDAR ESTADO =====
    	if (!r.getEstado().equals("Activa")) {

    		return "Solo reservas activas pueden editarse";
    	}
    	
    	// VALIDAR FECHA MODIFICACION
    	LocalDate hoy = LocalDate.now();
    	long dias = ChronoUnit.DAYS.between(hoy, r.getFechaInicio());

    	if (dias <= 1) {

    		return """
    			No se puede modificar la reserva.
    			Solo se permite modificar con más de 1 día de anticipación.
    			""";
    	}

    	// VALIDAR DISPONIBILIDAD
    	if (!verificarDisponibilidad(
    			r.getIdHabitacion(),
    			r.getFechaInicio(),
    			r.getFechaFin(),
    			r.getId())) {

    		return "La habitación ya está reservada";
    	}

    	// ===== ACTUALIZAR =====
    	String sql = """
    		UPDATE reserva
    		SET
    			fecha_inicio = ?,
    			fecha_fin = ?,
    			cantidad_personas = ?,
    			total = ?
    		WHERE id = ?
    	""";

    	try (Connection con = Conexion.getConexion();
    		 PreparedStatement ps = con.prepareStatement(sql)) {

    		ps.setDate(1, Date.valueOf(r.getFechaInicio()));
    		ps.setDate(2, Date.valueOf(r.getFechaFin()));
    		ps.setInt(3, r.getCantidadPersonas());
    		ps.setDouble(4, r.getTotal());
    		ps.setInt(5, r.getId());

    		int filas = ps.executeUpdate();
    		if (filas > 0) {

    			return "OK";
    		}

    	} catch (Exception e) {

    		System.out.println("Error editando reserva: " + e.getMessage());
    	}

    	return "No se pudo editar";
    }
    
    // ===== BUSCAR POR ID =====
    public Reserva buscarPorId(int id) {

    	String sql = """
    		SELECT
    			r.*,
    			h.numero_documento,
    			h.nombres,
    			h.apellidos,
    			ha.numero
    		FROM reserva r
    		INNER JOIN huesped h
    			ON r.id_huesped = h.id
    		INNER JOIN habitacion ha
    			ON r.id_habitacion = ha.id
    		WHERE r.id = ?
    	""";

    	try (Connection con = Conexion.getConexion();
    		 PreparedStatement ps = con.prepareStatement(sql)) {

    		ps.setInt(1, id);
    		ResultSet rs = ps.executeQuery();

    		if (rs.next()) {

    			Reserva r = new Reserva();
    			r.setId(rs.getInt("id"));
    			r.setIdHuesped(rs.getInt("id_huesped"));
    			r.setIdHabitacion(rs.getInt("id_habitacion"));
    			r.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
    			r.setFechaFin(rs.getDate("fecha_fin").toLocalDate());
    			r.setCantidadPersonas(rs.getInt("cantidad_personas"));
    			r.setPrecioNoche(rs.getDouble("precio_noche"));
    			r.setTotal(rs.getDouble("total"));
    			r.setEstado(rs.getString("estado"));

    			// ===== EXTRA =====
    			r.setNombreHuesped(rs.getString("nombres") + " " + rs.getString("apellidos"));
    			r.setNumeroHabitacion(rs.getString("numero"));
    			r.setDocumentoHuesped(rs.getString("numero_documento"));

    			return r;
    		}

    	} catch (Exception e) {

    		System.out.println(e.getMessage());
    	}

    	return null;
    }
    
    // ===== CHECK - IN =====
    public boolean realizarCheckIn(int idReserva, int idHabitacion) {

    	String sql = """
    		UPDATE reserva
    		SET estado = 'Hospedado'
    		WHERE id = ?
    	""";

    	try (Connection con = Conexion.getConexion();
    		 PreparedStatement ps = con.prepareStatement(sql)) {

    		ps.setInt(1, idReserva);
    		int filas = ps.executeUpdate();
    		
    		if (filas > 0) {

    			cambiarEstadoHabitacion(idHabitacion, "Ocupada");
    			return true;
    		}

    	} catch (Exception e) {

    		System.out.println("Error check-in: " + e.getMessage());
    	}

    	return false;
    }
    
    // ===== CHECK - OUT =====
    public boolean realizarCheckOut(int idReserva, int idHabitacion) {

    	String sql = """
    		UPDATE reserva
    		SET estado = 'Finalizada'
    		WHERE id = ?
    	""";

    	try (Connection con = Conexion.getConexion();
    		 PreparedStatement ps = con.prepareStatement(sql)) {

    		ps.setInt(1, idReserva);
    		int filas = ps.executeUpdate();

    		if (filas > 0) {

    			cambiarEstadoHabitacion(idHabitacion, "Disponible");

    			return true;
    		}

    	} catch (Exception e) {

    		System.out.println("Error check-out: " + e.getMessage());
    	}

    	return false;
    }
}
