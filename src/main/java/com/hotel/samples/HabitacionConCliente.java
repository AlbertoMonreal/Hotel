package com.hotel.samples;

public class HabitacionConCliente {

    private int id;
    private String tipo;
    private boolean estaReservada;
    private String clienteNombre;
    private String clienteApellido;

    public HabitacionConCliente(int id, String tipo, boolean estaReservada, String clienteNombre, String clienteApellido) {
        this.id = id;
        this.tipo = tipo;
        this.estaReservada = estaReservada;
        this.clienteNombre = clienteNombre;
        this.clienteApellido = clienteApellido;
    }

    // Getters
    public int getId() { return id; }
    public String getTipo() { return tipo; }
    public boolean isEstaReservada() { return estaReservada; }
    public String getClienteNombre() { return clienteNombre; }
    public String getClienteApellido() { return clienteApellido; }
}

