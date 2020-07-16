package com.example.sanapruebados.entidades;

import java.io.Serializable;

public class Centro implements Serializable {
    private Integer id;
    private String nombre;
    private String descripcion;
    private byte[] image;
    private String direccion;

    public  Centro(){}
    public Centro(Integer id, String nombre, String descripcion, byte[] image, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.image = image;
        this.direccion = direccion;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }



}
