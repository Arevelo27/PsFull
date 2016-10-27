package com.example.andres_desarrollo2.psfull.Beans;

/**
 * Created by TSI on 24/05/2016.
 */
public class RadicadoBn {
    private int id;
    private String codigo;
    private String titulo;
    private String descripcion;
    private CentropeBn centropes;
    private TipologiaBn tipologias;


    public RadicadoBn(String titulo, String descripcion, TipologiaBn tipologias) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipologias = tipologias;
    }

    public RadicadoBn(String codigo, String titulo, String descripcion, CentropeBn centropes, TipologiaBn tipologias) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.centropes = centropes;
        this.tipologias = tipologias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public CentropeBn getCentropes() {return centropes;}

    public void setCentropes(CentropeBn centropes) {
        this.centropes = centropes;
    }

    public TipologiaBn getTipologias() {
        return tipologias;
    }

    public void setTipologias(TipologiaBn tipologias) {
        this.tipologias = tipologias;
    }

    @Override
    public String toString() {
        return titulo + "," + descripcion +"\n ["+tipologias.getDescripcion()+"]"+","+tipologias.getCodigo() ;
    }
}
