package com.hotel.sistema.model;

public class Habitacion {

	private int id;
	private String numero;
	private String tipo;
	private double precio;
	private int capacidad;
	private String estado;

	public Habitacion() {
	}

	public Habitacion(String numero, String tipo, double precio, int capacidad) {
		this.numero = numero;
		this.tipo = tipo;
		this.precio = precio;
		this.capacidad = capacidad;
		this.estado = "";
	}

	public Habitacion(String numero, String tipo, double precio, int capacidad, String estado) {
		this.numero = numero;
		this.tipo = tipo;
		this.precio = precio;
		this.capacidad = capacidad;
		this.estado = estado;
	}

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getNumero() { return numero; }
	public void setNumero(String numero) { this.numero = numero; }

	public String getTipo() { return tipo; }
	public void setTipo(String tipo) { this.tipo = tipo; }

	public double getPrecio() { return precio; }
	public void setPrecio(double precio) { this.precio = precio; }

	public int getCapacidad() { return capacidad; }
	public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

	public String getEstado() { return estado != null ? estado : ""; }
	public void setEstado(String estado) { this.estado = estado; }
}