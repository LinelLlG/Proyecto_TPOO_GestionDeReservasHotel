package com.hotel.sistema.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
	private static final String URL = "jdbc:mysql://localhost:3306/hotel";
    private static final String USER = "hotel";
    private static final String PASS = "hotel123456789";

    public static Connection getConexion() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }
}
