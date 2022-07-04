package com.example.turismo.bean;

public class Reservas {

    public int idreserva;
    public String nombrelugar, costo, imagen, fecha_hora, estado, notas;

    public Reservas(int idreserva, String nombrelugar, String costo, String imagen, String fecha_hora, String estado, String notas) {
        this.idreserva = idreserva;
        this.nombrelugar = nombrelugar;
        this.costo = costo;
        this.imagen = imagen;
        this.fecha_hora = fecha_hora;
        this.estado = estado;
        this.notas = notas;
    }
}
