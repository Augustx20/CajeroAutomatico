package org.Augusto.Machado.SistemaCajero.Models;

import java.util.Date;

public class Usuario {
    private Long id;
    private String usuario;
    private Integer PIN;
    private int saldo;
    private String destinatario;
    private String categoria;
    private Date fecha_registro;
    private int estado;
    private String descripcion;
    private Double debito;
    private Double credito;


    public Usuario() {
    }

    public Usuario(Long id, String usuario, Integer PIN, Date fecha_registro,int estado,int saldo, String destinatario,
                   String categoria,String descripcion, Double debito, Double credito) {
        this.id = id;
        this.usuario = usuario;
        this.PIN = PIN;
        this.fecha_registro = fecha_registro;
        this.estado = estado;
        this.saldo = 0;
        this.destinatario = destinatario;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.debito = debito;
        this.credito = credito;

    }

    public String getDestinatario() {return destinatario;}
    public Object setDestinatario(String destinatario) {this.destinatario = destinatario;
        return null;
    }

    public String getCategoria() {return categoria;}
    public Object setCategoria(String categoria) {this.categoria = categoria;
        return null;
    }

    //GET Y SET DE ID
    public Long getId() {return id;}
    public Object setId(Long id) {this.id = id;
        return null;
    }

    //GET Y SET DE USUARIO
    public String getUsuario() {return usuario;}
    public Object setUsuario(String usuario) {this.usuario = usuario;
        return null;
    }

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
    public Object setSaldo(int saldo) {this.saldo = saldo;
        return null;
    }

    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public Double getDebito() {return debito;}
    public void setDebito(Double debito) {this.debito = debito;}

    public Double getCredito() {return credito;}
    public void setCredito(Double credito) {this.credito = credito;}
}
