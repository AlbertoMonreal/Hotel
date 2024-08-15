package com.hotel.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuAdministradorGUI extends BaseFrame {

    public MenuAdministradorGUI() {
        super("Menú Administrador", 500, 500);
    }

    @Override
    protected void addGuiComponents() {

        setLayout(new GridLayout(4, 1, 10, 10)); // Ajusto el diseño.(habrá que cambiarla?)

        // Botón para ver habitaciones
        JButton showRoomsButton = new JButton("Ver Habitaciones");
        showRoomsButton.setFont(new Font("Dialog", Font.PLAIN, 18));
        showRoomsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowRoomsGUI showRoomsGUI = new ShowRoomsGUI();
                // Llamar a refreshData para cargar los datos más recientes antes de hacerla visible.
                showRoomsGUI.refreshDataTabla();
                // La hago visible.
                showRoomsGUI.setVisible(true);
            }
        });
        add(showRoomsButton);
        
        // Botón para gestionar clientes(reservar y dejar habitacion)
        JButton gestionClientes = new JButton("Gestion clientes");
        gestionClientes.setFont(new Font("Dialog", Font.PLAIN, 18));
        gestionClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManageClientsGUI().setVisible(true);
            }
        });
        add(gestionClientes);
        
        // Botón para Informes
        JButton reportsButton = new JButton("Informes");
        reportsButton.setFont(new Font("Dialog", Font.PLAIN, 18));
        reportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReportsGUI().setVisible(true);
            }
        });
        add(reportsButton);

        // Botón para cerrar sesión y volver al menu.
        JButton logoutButton = new JButton("Cerrar Sesión");
        logoutButton.setFont(new Font("Dialog", Font.PLAIN, 18));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre la ventana de inicio de sesión y cierra la ventana actual
                new LoginGUI().setVisible(true);
                dispose();
            }
        });
        add(logoutButton);
    }
}
