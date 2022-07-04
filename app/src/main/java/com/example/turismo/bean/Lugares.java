package com.example.turismo.bean;

public class Lugares {

    public int idlugar;
    public String nombre, ubigeo, costo, imagen;

    public Lugares(int idlugar, String nombre, String ubigeo, String costo, String imagen) {
        this.idlugar = idlugar;
        this.nombre = nombre;
        this.ubigeo = ubigeo;
        this.costo = costo;
        this.imagen = imagen;
    }
}
