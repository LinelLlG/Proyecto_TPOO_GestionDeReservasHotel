package com.hotel.sistema.controller;

import java.time.LocalDate;
import java.util.List;

import com.hotel.sistema.dao.ReservaDAO;
import com.hotel.sistema.model.Reserva;

public class ReservaController {

	private ReservaDAO dao = new ReservaDAO();

    // ===== GUARDAR =====
    public String guardar(Reserva r) {

        return dao.guardar(r);
    }

    // ===== LISTAR =====
    public List<Reserva> listar() {

        return dao.listar();
    }

    // ===== CANCELAR =====
    public boolean cancelar(int idReserva, int idHabitacion) {

        return dao.cancelar(idReserva, idHabitacion);
    }
    
    // ===== EDITAR =====
    public String editar(Reserva r) {

    	return dao.editar(r);
    }

    // ===== VERIFICAR DISPONIBILIDAD =====
    public boolean verificarDisponibilidad(int idHabitacion, LocalDate fechaInicio, LocalDate fechaFin) {

        return dao.verificarDisponibilidad(idHabitacion, fechaInicio, fechaFin);
    }

    // ===== CALCULAR TOTAL =====
    public double calcularTotal(LocalDate inicio, LocalDate fin, double precioNoche) {

        return dao.calcularTotal(inicio, fin, precioNoche);
    }
    
    // ===== BUSQUEDA POR ID =====
    public Reserva buscarPorId(int id) {

    	return dao.buscarPorId(id);
    }
    
    // ===== CHECK - IN =====
    public boolean realizarCheckIn(int idReserva, int idHabitacion) {

    	return dao.realizarCheckIn(idReserva, idHabitacion);
    }
    
    // ===== CEHCK - OUT =====
    public boolean realizarCheckOut(int idReserva, int idHabitacion) {

    	return dao.realizarCheckOut(idReserva, idHabitacion);
    }
}
