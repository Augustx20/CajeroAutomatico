package org.Augusto.Machado.SistemaCajero.Models;

import java.util.Date;

public class Usuario {
    private Long id;
    private String usuario;
    private Integer PIN;

    private Date fecha_registro;
    private int estado;

    public Usuario() {
    }

    public Usuario(Long id, String usuario, Integer PIN, Date fecha_registro,int estado) {
        this.id = id;
        this.usuario = usuario;
        this.PIN = PIN;
        this.fecha_registro = fecha_registro;
        this.estado = estado;
    }

    //GET Y SET DE ID
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    //GET Y SET DE USUARIO
    public String getUsuario() {return usuario;}
    public void setUsuario(String usuario) {this.usuario = usuario;}

    //GET Y SET DE PIN
    public Integer getPIN() {return PIN;}
    public void setPIN(int PIN) {this.PIN = PIN;}

    //GET Y SET DE FECHA REGISTRO
    public Date getFecha_registro() {return fecha_registro;}
    public void setFecha_registro(Date fecha_registro) {this.fecha_registro = fecha_registro;}

    public int getEstado() {return estado;}

    public void setEstado(int estado) {this.estado = estado;}

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                ", PIN=" + PIN +
                ", fecha_registro=" + fecha_registro +
                ", estado=" + estado +
                '}';
    }

}
