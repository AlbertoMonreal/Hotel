package com.hotel.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Arrays;

import com.hotel.database.DatabaseHelper;
import com.hotel.samples.HabitacionConCliente;

public class ShowRoomsGUI extends BaseFrame {

    private static final String[] COLUMN_NAMES = {"ID", "Tipo", "EstaReservada", "Nombre-Cliente", "Apellido-Cliente"};
    private DefaultTableModel tableModel;

    public ShowRoomsGUI() {
        super("Gestión de Habitaciones", 800, 800);
    }

    @Override
    protected void addGuiComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//Solo se cierra esta pestaña cuando le des a la X
        setLayout(new BorderLayout());

        // Crear el modelo de la tabla
        tableModel = new DefaultTableModel(COLUMN_NAMES, 0);
        JTable table = new JTable(tableModel);

        // Configurar la tabla (opcional)
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Agregar la tabla a un scroll pane para que sea desplazable
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Crear y añadir un título
        JLabel titleUser = new JLabel("Gestión de Habitaciones");
        titleUser.setFont(new Font("Arial", Font.BOLD, 24));
        titleUser.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleUser, BorderLayout.NORTH);
    }

    public void refreshDataTabla() {
        tableModel.setRowCount(0); // Limpiar las filas actuales

        List<HabitacionConCliente> habitaciones = DatabaseHelper.getHabitacionesClienteTabla(); // Obtengo las habitaciones con datos del cliente asociados.

        for (HabitacionConCliente habitacion : habitaciones) {
            Object[] row = {
                habitacion.getId(),
                habitacion.getTipo(),
                habitacion.isEstaReservada(), // Muestra si está reservada
                habitacion.getClienteNombre(),
                habitacion.getClienteApellido()
            };
            System.out.println("Adding row: " + Arrays.toString(row)); 
            tableModel.addRow(row);
        }
    }
}
