package com.hotel.gui;

import javax.swing.*;
import java.awt.*;

public class ManageRoomsGUI extends BaseFrame {

    // Define los nombres de las columnas, incluyendo el cliente asociado
    private static final String[] COLUMN_NAMES = {"ID", "Tipo", "Is Reserved", "Nombre-Cliente","Apellido-Cliente"};

    // Define los datos iniciales
    private Object[][] rowData;

    public ManageRoomsGUI() {
        super("Gestion habitaciones", 800, 800);
    }

    @Override
    protected void addGuiComponents() {
        //Vacia
        rowData = new Object[0][COLUMN_NAMES.length];
        
        // Usa un diseño BorderLayout para el panel principal
        setLayout(new BorderLayout());

        // Crear la tabla directamente sin DefaultTableModel
        JTable table = new JTable(rowData, COLUMN_NAMES);

        // Configurar la tabla (opcional)
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // Ajusta el tamaño de las columnas automáticamente

        // Agregar la tabla a un scroll pane para que sea desplazable
        JScrollPane scrollPane = new JScrollPane(table);

        // Agregar el scroll pane al centro del marco principal
        add(scrollPane, BorderLayout.CENTER);

        // Crear y añadir un título
        JLabel titleUser = new JLabel("Gestión de Habitaciones");
        titleUser.setFont(new Font("Arial", Font.BOLD, 24)); // Fuente, estilo y tamaño del texto
        titleUser.setHorizontalAlignment(SwingConstants.CENTER); // Alinear el texto al centro
        add(titleUser, BorderLayout.NORTH); // Agregar el título al norte del layout

        // Asegúrate de que el tamaño de la ventana se ajuste al contenido
        pack();
    }

}
