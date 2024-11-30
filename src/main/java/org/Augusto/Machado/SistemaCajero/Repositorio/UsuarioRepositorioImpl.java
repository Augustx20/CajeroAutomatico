package org.Augusto.Machado.SistemaCajero.Repositorio;

import org.Augusto.Machado.SistemaCajero.Models.Usuario;
import org.Augusto.Machado.SistemaCajero.Util.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorioImpl implements UsuarioRepositorio <Usuario>{

    private Connection getConnection() throws SQLException {
        return ConexionBaseDatos.getInstance();
    }


    //LISTAR PARA EMPLEADO CAJERO Y SUPERVISOR CAJERO
    @Override
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        try(Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery("select * from usuario");) {
            while(rs.next()) {
                final Usuario u = BuscarUsuario(rs);
                usuarios.add(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }

    @Override
    public Usuario porId(int id) {
        Usuario u = null;
        try(PreparedStatement stmt = getConnection().
                prepareStatement("select * from usuario where id = ?")) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                u = BuscarUsuario(rs);

            }
            rs.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }



    //CREAMOS USUARIO NUEVO SI ES QUE NO SE ENCUENTRA REGISTRADO EN SISTEMA ACTUAL
    @Override
    public boolean crearUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario(usuario,PIN,fecha_registro,estado) VALUES(?,?,?,?)";
        try(PreparedStatement stmt = getConnection().prepareStatement(sql)){
            stmt.setString(1,usuario.getUsuario());
            stmt.setString(2, String.valueOf(usuario.getPIN()));
            stmt.setDate(3,new Date(usuario.getFecha_registro().getTime()));
            stmt.setInt(4,usuario.getEstado());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;

    }

    @Override
    public void eliminarUsuario(int id) {

    }

    @Override
    public boolean porUsuario(String usuario,char[] pin ) {
        boolean resultado = false;
        try(PreparedStatement stmt = getConnection().
                prepareStatement("select * from usuario where usuario = ? And PIN = ?")) {
            stmt.setString(1, usuario);
            stmt.setString(2, new String(pin));
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                resultado = true;
            }
        }catch (SQLException e) {
            resultado = false;
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public boolean ValidarUsuario(String usuario) {
        boolean resultado = false;
        try(PreparedStatement stmt = getConnection().
                prepareStatement("select * from usuario where usuario = ?")) {
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                resultado = true;
            }
        }catch (SQLException e) {
            resultado = false;
            e.printStackTrace();
        }
        return resultado;
    }


    private static Usuario BuscarUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getLong("id"));
        u.setUsuario(rs.getString("usuario"));
        u.setPIN(rs.getInt("PIN"));
        u.setFecha_registro(rs.getDate("fecha_registro"));
        u.setEstado(rs.getInt("estado"));
        return u;
    }
}
