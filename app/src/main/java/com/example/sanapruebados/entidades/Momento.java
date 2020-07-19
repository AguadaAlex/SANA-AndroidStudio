package com.example.sanapruebados.entidades;

import java.io.Serializable;

public class Momento implements Serializable {
    private Integer id;
    private String usuario;
    private String estado;
    private String fecha;
    private byte[] image;
    public Momento(){}
    public Momento(Integer id, String usuario, String estado, String fecha) {
        this.id = id;
        this.usuario = usuario;
        this.estado = estado;
        this.fecha = fecha;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
