package org.Augusto.Machado.SistemaCajero.Repositorio.Usuario;

import org.Augusto.Machado.SistemaCajero.Models.Usuario;
import org.Augusto.Machado.SistemaCajero.Util.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;

import java.util.List;

public class UsuarioRepositorioImpl implements UsuarioRepositorio <Usuario>{

    private boolean BOOLEAN_RESULTADO = false;
    private String CONSULTA_SQL_ID_USUARIO = "select id from usuario where usuario = ?";

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
    public int porId(String usuario) {
        int u = 0 ;
        try(PreparedStatement stmt = getConnection().
                prepareStatement(CONSULTA_SQL_ID_USUARIO)) {
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                u = rs.getInt("id");

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
        try(PreparedStatement stmt = getConnection().
                prepareStatement("select * from usuario where usuario = ? And PIN = ?")) {
            stmt.setString(1, usuario);
            stmt.setString(2, new String(pin));
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                BOOLEAN_RESULTADO = true;
            }
        }catch (SQLException e) {
            BOOLEAN_RESULTADO = false;
            e.printStackTrace();
        }
        return BOOLEAN_RESULTADO;
    }

    @Override
    public boolean ValidarUsuario(String usuario) {
        try(PreparedStatement stmt = getConnection().
                prepareStatement("select * from usuario where usuario = ?")) {
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                BOOLEAN_RESULTADO = true;
            }
        }catch (SQLException e) {
            BOOLEAN_RESULTADO = false;
            e.printStackTrace();
        }
        return BOOLEAN_RESULTADO;
    }

    @Override
    public int ValidarPIN(String usuario) {
        int result = 0;
        try(PreparedStatement stmt = getConnection()
                .prepareStatement("select PIN from usuario where usuario = ?")){
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                result = rs.getInt("PIN");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result ;
    }

    @Override
    public Double MostrarSaldoUsuario(String usuario) {
        Double resultado = (double) 0;
        try(PreparedStatement stmt = getConnection().
                prepareStatement("select * from saldo where id_usuario = ("+CONSULTA_SQL_ID_USUARIO +")")) {
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                resultado = rs.getDouble("saldo");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public Double Depositar(String usuario, Double saldo) {
        Double resultado = (double) 0;
        try(PreparedStatement stmt = getConnection()
                .prepareStatement("select * from saldo where id_usuario = ("+CONSULTA_SQL_ID_USUARIO+")")){
            stmt.setString(1,usuario);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                resultado = (double) rs.getInt("saldo");

                resultado += saldo;
                try(PreparedStatement stmt2 = getConnection().
                        prepareStatement("UPDATE saldo SET saldo = ? WHERE id_usuario = ("+CONSULTA_SQL_ID_USUARIO+")")) {
                    stmt2.setDouble(1,resultado);
                    stmt2.setString(2,usuario);
                    stmt2.executeUpdate();
                    AgregarMovimiento(usuario,"DEPOSITO EN CAJERO AUTOMATICO",0.0,saldo,resultado);
                }
                }else{
                try(PreparedStatement stmt3 = getConnection()
                        .prepareStatement("INSERT INTO saldo (saldo,id_usuario) VALUES (?,?)")) {
                    stmt3.setDouble(1,saldo);
                    stmt3.setInt(2,porId(usuario));
                    stmt3.executeUpdate();
                    AgregarMovimiento(usuario,"DEPOSITO EN CAJERO AUTOMATICO",0.0,saldo,resultado);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

    @Override
    public Double Retirar(String usuario, Double saldo) {
        double resultado_retirar = (double) 0;
        try (PreparedStatement stmt = getConnection()
                .prepareStatement("select * from saldo where id_usuario = ("+CONSULTA_SQL_ID_USUARIO+")")){
            stmt.setString(1,usuario);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                resultado_retirar = (double) rs.getInt("saldo");}
            if (resultado_retirar == 0) {
            resultado_retirar = (double) -2;
            return resultado_retirar;
            } else if (resultado_retirar <= 0) {
                resultado_retirar = (double) -1;
                return resultado_retirar ;
            }else{
                resultado_retirar -= saldo;
                try(PreparedStatement stmt2 = getConnection().
                        prepareStatement("UPDATE saldo SET saldo = ? WHERE id_usuario = ("+CONSULTA_SQL_ID_USUARIO+")")) {
                    stmt2.setDouble(1,resultado_retirar);
                    stmt2.setString(2,usuario);
                    stmt2.executeUpdate();
                    //Agregamos a nuestros moviemientos a la base de bancaria de moviemientos

                    AgregarMovimiento(usuario,"DEB PREA DEBIN",saldo,0.0,resultado_retirar);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado_retirar;
    }

    @Override
    public int ResetearPIN(String usuario, int PIN) {
        int nuevoPIN = 0;
        int viejoPIN;
        try(PreparedStatement stmt = getConnection().
                prepareStatement("select * from usuario where usuario = ?")) {
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                viejoPIN = rs.getInt("PIN");
                nuevoPIN = PIN;
                if (viejoPIN == nuevoPIN){
                        nuevoPIN = -1;
                }else{
                    try(PreparedStatement stmt2 = getConnection().
                            prepareStatement("UPDATE usuario SET PIN = ? WHERE usuario = ?")) {
                        stmt2.setInt(1,nuevoPIN);
                        stmt2.setString(2,usuario);
                        stmt2.executeUpdate();
                        nuevoPIN = 1;

                    }
                }
            }
        }catch (SQLException e) {e.printStackTrace();}
        return nuevoPIN;
    }

    @Override
    public void AgregarMovimiento(String usuario,String descripcion, Double debito,Double credito,Double saldo) {
        int u = 0;
        try(PreparedStatement stmt = getConnection()
                .prepareStatement(CONSULTA_SQL_ID_USUARIO)){
            stmt.setString(1,usuario);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                 u = rs.getInt("id");
            }
            try(PreparedStatement mov = getConnection()
                    .prepareStatement("INSERT INTO movimientos(fecha,descripcion,debito,credito,saldo,id_usuario) VALUES(?,?,?,?,?,?)")){
                mov.setDate(1,new Date(System.currentTimeMillis()));
                mov.setString(2,descripcion);
                mov.setDouble(3,debito);
                mov.setDouble(4,credito);
                mov.setDouble(5,saldo);
                mov.setInt(6,u);
                mov.executeUpdate();
            }
        } catch (SQLException e) {throw new RuntimeException(e);}
    }

    @Override
    public void AgregarTransacciones(String usuario, String destinatario, String categoria, Double saldo,int id_usuario) {
        int u = 0;
        try(PreparedStatement stmt = getConnection()
                .prepareStatement(CONSULTA_SQL_ID_USUARIO)){
            stmt.setString(1,usuario);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                u = rs.getInt("id");
            }
            try(PreparedStatement mov = getConnection()
                    .prepareStatement("INSERT INTO transacciones(fecha_transaccion,usuario,destinatario,categoria,valor,id_usuario) VALUES(?,?,?,?,?,?)")){
                mov.setDate(1,new Date(System.currentTimeMillis()));
                mov.setString(2,usuario);
                mov.setString(3,destinatario);
                mov.setString(4,categoria);
                mov.setDouble(5,saldo);
                mov.setInt(6,id_usuario);
                mov.executeUpdate();
            }
        } catch (SQLException e) {throw new RuntimeException(e);}
    }

    @Override
    public List<Usuario> MostrarTransferencia(String usuario) {
        List<Usuario> us = new ArrayList<>();
        try(PreparedStatement stmt = getConnection()
                .prepareStatement("SELECT usuario,destinatario,categoria,valor FROM transacciones WHERE id_usuario = ("+CONSULTA_SQL_ID_USUARIO+")")){
            stmt.setString(1,usuario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setUsuario(rs.getString("usuario"));
                u.setDestinatario(rs.getString("destinatario"));
                u.setCategoria(rs.getString("categoria"));
                u.setSaldo(rs.getInt("valor"));
                us.add(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return us;
    }

    @Override
    public List<Usuario> MostrarMovimiento(String usuario) {
        List<Usuario> us = new ArrayList<>();
        try(PreparedStatement stmt = getConnection()
                .prepareStatement("SELECT fecha,descripcion,debito,credito,saldo FROM movimientos WHERE id_usuario = ("+CONSULTA_SQL_ID_USUARIO+")")){
            stmt.setString(1,usuario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setFecha_registro(rs.getDate("fecha"));
                u.setDescripcion(rs.getString("descripcion"));
                u.setDebito(rs.getDouble("debito"));
                u.setCredito(rs.getDouble("credito"));
                u.setSaldo(rs.getInt("saldo"));
                us.add(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return us;
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