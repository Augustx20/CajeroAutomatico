package org.Augusto.Machado.SistemaCajero.Repositorio.Usuario;

import java.util.List;

public interface UsuarioRepositorio<T> {
    List<T> listar();

    int porId(String usuario);

    boolean crearUsuario(T t);

    void eliminarUsuario(int id);

    boolean porUsuario(String usuario, char[] PIN);

    boolean ValidarUsuario(String usuario);

    int ValidarPIN(String usuario);

    Double MostrarSaldoUsuario(String usuario);

    Double Depositar(String usuario, Double saldo);

    Double Retirar (String usuario, Double saldo);

    int ResetearPIN (String usuario, int PIN);

    void AgregarMovimiento(String usuario,String descripcion, Double debito,Double credito,Double saldo);

    void AgregarTransacciones(String usuario,String destinatario,String categoria,Double saldo,int id_usuario);

    List<T> MostrarTransferencia(String usuario,int mostrar);

    List<T> MostrarMovimiento(String usuario,int mostrar);

}
