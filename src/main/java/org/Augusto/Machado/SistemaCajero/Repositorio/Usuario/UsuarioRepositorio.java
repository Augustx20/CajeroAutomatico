package org.Augusto.Machado.SistemaCajero.Repositorio.Usuario;

import java.util.List;

public interface UsuarioRepositorio<T> {
    List<T> listar();

    T porId(int id);

    boolean crearUsuario(T t);

    void eliminarUsuario(int id);

    boolean porUsuario(String usuario, char[] PIN);

    boolean ValidarUsuario(String usuario);

    int MostrarSaldoUsuario(String usuario);
}
