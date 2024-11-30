package org.Augusto.Machado.SistemaCajero.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {
    private static String CONEXION_URL = "jdbc:mysql://localhost:3306/cajero";
    private static String CONEXION_USUARIO = "root";
    private static String CONEXION_PASS = "12345";
    private static Connection conn;

    public static Connection getInstance() throws SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection(CONEXION_URL,CONEXION_USUARIO,CONEXION_PASS);
        }
        return conn;
    }
}