package com.hotel.sistema.model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Reserva {

	private int id;

    private int idHuesped;
    private int idHabitacion;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private int cantidadPersonas;

    private double precioNoche;
    private double total;

    private String estado;

    private Timestamp fechaRegistro;
    
    private String nombreHuesped;
    private String numeroHabitacion;

    // ===== CONSTRUCTOR VACIO =====
    public Reserva() {

    }

    // ===== CONSTRUCTOR COMPLETO =====
    public Reserva(int idHuesped, int idHabitacion, LocalDate fechaInicio, LocalDate fechaFin, int cantidadPersonas, double precioNoche,
                   double total, String estado) {

        this.idHuesped = idHuesped;
        this.idHabitacion = idHabitacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cantidadPersonas = cantidadPersonas;
        this.precioNoche = precioNoche;
        this.total = total;
        this.estado = estado;
    }

    // ===== GETTERS Y SETTERS =====

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdHuesped() {
        return idHuesped;
    }

    public void setIdHuesped(int idHuesped) {
        this.idHuesped = idHuesped;
    }

    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public double getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecioNoche(double precioNoche) {
        this.precioNoche = precioNoche;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    public String getNombreHuesped() {
    	return nombreHuesped;
    }

    public void setNombreHuesped(String nombreHuesped) {
    	this.nombreHuesped = nombreHuesped;
    }

    public String getNumeroHabitacion() {
    	return numeroHabitacion;
    }

    public void setNumeroHabitacion(String numeroHabitacion) {
    	this.numeroHabitacion = numeroHabitacion;
    }
}
