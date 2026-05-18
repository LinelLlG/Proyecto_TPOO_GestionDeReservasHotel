package com.hotel.sistema.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Conexion {
    private static final Properties props = new Properties();

    static {
        try (InputStream in = Conexion.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) throw new IOException("db.properties no encontrado en classpath");
            props.load(in);
        } catch (IOException e) {
            throw new ExceptionInInitializerError("No se pudo cargar db.properties: " + e.getMessage());
        }
    }

    public static Connection getConexion() {
        try {
            return DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.user"),
                props.getProperty("db.password")
            );
        } catch (Exception e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }
}
