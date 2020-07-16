package com.example.sanapruebados.entidades;

public class Establecimientos {
    private Integer id;
    private Integer idAdiccion;
    private Integer idCentro;
    private String descripcion;

    public Establecimientos(){}
    public Establecimientos(Integer id, Integer idAdiccion, Integer idCentro, String descripcion) {
        this.id = id;
        this.idAdiccion = idAdiccion;
        this.idCentro = idCentro;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdAdiccion() {
        return idAdiccion;
    }

    public void setIdAdiccion(Integer idAdiccion) {
        this.idAdiccion = idAdiccion;
    }

    public Integer getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(Integer idCentro) {
        this.idCentro = idCentro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
