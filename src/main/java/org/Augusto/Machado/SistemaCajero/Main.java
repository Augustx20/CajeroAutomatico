package org.Augusto.Machado.SistemaCajero;


import org.Augusto.Machado.SistemaCajero.Util.ConexionBaseDatos;

import java.sql.*;

public class Main {
    public static void main(String[] args) {

        try (Connection conn = ConexionBaseDatos.getInstance();){

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}