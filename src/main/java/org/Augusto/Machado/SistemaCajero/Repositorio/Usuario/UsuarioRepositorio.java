package org.Augusto.Machado.SistemaCajero.Repositorio.Usuario;

import java.util.List;

public interface UsuarioRepositorio<T> {
    List<T> listar();

    int porId(String usuario);

    boolean crearUsuario(T t);

    void eliminarUsuario(int id);

    boolean porUsuario(String usuario, char[] PIN);

    boolean ValidarUsuario(String usuario);

    int MostrarSaldoUsuario(String usuario);

    int Depositar(String usuario, int saldo);

    int Retirar (String usuario, int saldo);
}
