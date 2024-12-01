package org.Augusto.Machado.SistemaCajero.Models;

import java.util.Date;

public class Usuario {
    private Long id;
    private String usuario;
    private Integer PIN;
    private int saldo;
    private Long id_usuario;

    private Date fecha_registro;
    private int estado;

    public Usuario() {
    }

    public Usuario(Long id, String usuario, Integer PIN, Date fecha_registro,int estado, Long id_usuario,int saldo) {
        this.id = id;
        this.usuario = usuario;
        this.PIN = PIN;
        this.fecha_registro = fecha_registro;
        this.estado = estado;
        this.id_usuario = id_usuario;
        this.saldo = 0;

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


    //GET Y SET DE ESTADO
    public int getEstado() {return estado;}
    public void setEstado(int estado) {this.estado = estado;}


    //GET Y SET DE SALDO
    public int getSaldo() {return saldo;}
    public void setSaldo(int saldo) {this.saldo = saldo;}


    //GET Y SET ID_USUARIO
    public Long getId_usuario() {return id_usuario;}
    public void setId_usuario(Long id_usuario) {this.id_usuario = id_usuario;}
}
