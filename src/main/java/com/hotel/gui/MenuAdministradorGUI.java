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
        setLayout(new GridLayout(4, 1, 10, 10)); // Ajusto el diseño.

        // Botón para gestionar habitaciones
        JButton manageRoomsButton = new JButton("Gestionar Habitaciones");
        manageRoomsButton.setFont(new Font("Dialog", Font.PLAIN, 18));
        manageRoomsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManageRoomsGUI().setVisible(true);
            }
        });
        add(manageRoomsButton);

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
