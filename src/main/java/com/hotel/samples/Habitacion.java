package com.hotel.samples;

public class Habitacion {
    private int id;
    private String tipo;
    private boolean estaReservada;
    private Integer clienteID; // Puede ser null, por eso se usa Integer en lugar de int
    private double precio;

    // Constructor vacío
    public Habitacion() {
    }

    // Constructor con todos los parámetros
    public Habitacion(int id, String tipo, boolean estaReservada, Integer clienteID, double precio) {
        this.id = id;
        this.tipo = tipo;
        this.estaReservada = estaReservada;
        this.clienteID = clienteID;
        this.precio = precio;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isEstaReservada() {
        return estaReservada;
    }

    public void setEstaReservada(boolean estaReservada) {
        this.estaReservada = estaReservada;
    }

    public Integer getClienteID() {
        return clienteID;
    }

    public void setClienteID(Integer clienteID) {
        this.clienteID = clienteID;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Habitacion{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", estaReservada=" + estaReservada +
                ", clienteID=" + clienteID +
                ", precio=" + precio +
                '}';
    }
}
