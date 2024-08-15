package com.hotel.gui;

import javax.swing.*;
import com.hotel.database.DatabaseHelper;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LiberarReservaGUI extends BaseFrame {

    private JTextField habitacionIdField;
    private JButton liberarButton;

    public LiberarReservaGUI() {
        super("Liberar Reserva", 400, 200);
    }

    @Override
    protected void addGuiComponents() {
        // Configurar el layout del panel
        setLayout(new GridLayout(3, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Campo para ingresar los IDs de las habitaciones
        add(new JLabel("IDs de Habitaciones (separados por comas):"));
        habitacionIdField = new JTextField();
        add(habitacionIdField);
        
        // Botón para liberar las reservas
        liberarButton = new JButton("Liberar Reservas");
        liberarButton.setFont(new Font("Dialog", Font.PLAIN, 16));
        liberarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                liberarReservas();
            }
        });
        add(liberarButton);
    }

    private void liberarReservas() {
        // Obtener los IDs de habitaciones del campo de texto
        String habitacionIDsStr = habitacionIdField.getText();
        if (habitacionIDsStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese al menos un ID de habitación.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Separar los IDs de las habitaciones por comas
        String[] idsArray = habitacionIDsStr.split(",");
        boolean exito = true;

        for (String idStr : idsArray) {
            idStr = idStr.trim();  // Eliminar espacios en blanco
            if (idStr.isEmpty()) {
                continue;  // Ignorar IDs vacíos
            }

            try {
                int habitacionID = Integer.parseInt(idStr);
                boolean resultado = DatabaseHelper.liberarReserva(habitacionID);

                if (!resultado) {
                    exito = false;  // Si alguno falla, marcar el proceso como fallido
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID de habitación inválido: " + idStr + ". Debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
                exito = false;  // Marcar el proceso como fallido en caso de error de formato
            }
        }

        if (exito) {
            JOptionPane.showMessageDialog(this, "Reservas liberadas exitosamente.");
        } else {
            JOptionPane.showMessageDialog(this, "Algunas reservas no se pudieron liberar. Verifique los IDs e intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
