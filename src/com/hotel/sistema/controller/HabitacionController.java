package com.hotel.sistema.controller;

import com.hotel.sistema.dao.HabitacionDAO;
import com.hotel.sistema.model.Habitacion;

import java.util.List;

public class HabitacionController {

	private HabitacionDAO dao = new HabitacionDAO();

	public String guardar(Habitacion h) {
		if (h.getNumero() == null || h.getNumero().isEmpty()) {
			return "El número de habitación es obligatorio.";
		}
		if (h.getNumero().length() > 10) {
			return "El número de habitación no puede exceder 10 caracteres.";
		}
		if (h.getTipo() == null || h.getTipo().isEmpty()) {
			return "El tipo de habitación es obligatorio.";
		}
		if (h.getPrecio() <= 0) {
			return "El precio debe ser un valor positivo.";
		}
		if (h.getCapacidad() <= 0) {
			return "La capacidad debe ser un valor positivo.";
		}
		if (dao.existeNumero(h.getNumero())) {
			return "Ya existe una habitación con el número " + h.getNumero() + ".";
		}
		return dao.registrar(h) ? "OK" : "Error al registrar en la base de datos.";
	}

	public String editar(Habitacion h) {
		if (h.getNumero() == null || h.getNumero().isEmpty()) {
			return "El número de habitación es obligatorio.";
		}
		if (h.getNumero().length() > 10) {
			return "El número de habitación no puede exceder 10 caracteres.";
		}
		if (h.getPrecio() <= 0) {
			return "El precio debe ser un valor positivo.";
		}
		if (h.getCapacidad() <= 0) {
			return "La capacidad debe ser un valor positivo.";
		}
		if (dao.existeNumeroExcluyendo(h.getNumero(), h.getId())) {
			return "Ya existe otra habitación con el número " + h.getNumero() + ".";
		}
		return dao.editar(h) ? "OK" : "Error al editar en la base de datos.";
	}

	public String eliminar(int id) {
		if (dao.tieneReservas(id)) {
			return "No se puede eliminar: la habitación tiene reservas asociadas.";
		}
		return dao.eliminar(id) ? "OK" : "Error al eliminar.";
	}

	public boolean cambiarEstado(int id, String estado) {
		return dao.cambiarEstado(id, estado);
	}

	public List<Habitacion> listar() {
		return dao.listar();
	}

	public List<Habitacion> listarPorTipo(String tipo) {
		if (tipo.equals("Todos")) {
			return dao.listar();
		}
		return dao.listarPorTipo(tipo);
	}

	public List<Habitacion> buscarDisponibles(String filtro) {
		return dao.buscarDisponibles(filtro);
	}
}