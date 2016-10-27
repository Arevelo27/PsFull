package com.example.andres_desarrollo2.psfull.Beans;

/**
 * Created by TSI on 20/05/2016.
 */
public class CentropeBn {

    private String codigo;
    private String descripcion;

    public CentropeBn(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return codigo + ", " + descripcion;
    }
}
