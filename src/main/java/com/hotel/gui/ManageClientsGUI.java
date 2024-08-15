package com.hotel.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.hotel.database.DatabaseHelper;
import com.hotel.formDelimitadores.NumericDocumentFilter;
import com.hotel.formDelimitadores.StringDocumentFilter;
import com.hotel.samples.HabitacionConCliente;
import javax.swing.text.AbstractDocument;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;

public class ManageClientsGUI extends JFrame {
    
    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField dniField;
    private JTextField emailField;
    private JTextField telefonoField;
    private JDateChooser fechaSalidaChooser;
    
    
    private JList<String> habitacionesList;  // Cambiado a JList para selección múltiple
    private DefaultListModel<String> habitacionesListModel;
    
    public ManageClientsGUI() {
        setTitle("Gestión de Clientes");
        setSize(600, 600);  // Aumenta el tamaño para acomodar la lista
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Solo cierro esta pestaña.
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // Usar BoxLayout para organizar verticalmente
        
        // Crear campos del formulario
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // Panel para el formulario con GridLayout
        add(formPanel);
        
        formPanel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        ((AbstractDocument) nombreField.getDocument()).setDocumentFilter(new StringDocumentFilter()); // Aplicar filtro para letras
        formPanel.add(nombreField);
    
        formPanel.add(new JLabel("Apellido:"));
        apellidoField = new JTextField();
        ((AbstractDocument) apellidoField.getDocument()).setDocumentFilter(new StringDocumentFilter()); // Aplicar filtro para letras
        formPanel.add(apellidoField);

        formPanel.add(new JLabel("DNI:"));
        dniField = new JTextField();
        formPanel.add(dniField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Teléfono:"));
        telefonoField = new JTextField();
        ((AbstractDocument) telefonoField.getDocument()).setDocumentFilter(new NumericDocumentFilter()); // Aplicar filtro para números
        formPanel.add(telefonoField);

        formPanel.add(new JLabel("Fecha Salida:"));
        fechaSalidaChooser = new JDateChooser();  // Usa JDateChooser en lugar de JTextField
        fechaSalidaChooser.setDateFormatString("yyyy-MM-dd"); // Configura el formato de fecha
        formPanel.add(fechaSalidaChooser);
        
        // Crear lista para seleccionar habitaciones
        JPanel listPanel = new JPanel(); // Panel para el JList
        add(listPanel);
        
        listPanel.add(new JLabel("Seleccionar Habitaciones:"));
        habitacionesListModel = new DefaultListModel<>();
        habitacionesList = new JList<>(habitacionesListModel);
        habitacionesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(habitacionesList);
        scrollPane.setPreferredSize(new Dimension(400, 300)); // Ajustar tamaño del JScrollPane
        listPanel.add(scrollPane);
        
        loadHabitaciones();

        // Botón para guardar cliente y reservar habitación
        JPanel buttonPanel = new JPanel(); // Crear un panel solo para el boton, asi lo puedo centrar.
        JButton guardarYReservarButton = new JButton("Guardar y Reservar");
        guardarYReservarButton.setPreferredSize(new Dimension(200, 50));
        guardarYReservarButton.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Uso del flowlayout.
        buttonPanel.add(guardarYReservarButton); // Añadir el botón al panel

        guardarYReservarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAndReserve();
            }
        });
        add(buttonPanel); // Añadir el panel al JFrame
    }

    private void loadHabitaciones() {
        // Aquí cargarías las habitaciones disponibles desde la base de datos
        for (HabitacionConCliente habitacion : DatabaseHelper.getHabitacionesDisponibles()) {
            if (!habitacion.isEstaReservada()) {
                habitacionesListModel.addElement(habitacion.getId() + " - " + habitacion.getTipo() + " - " + DatabaseHelper.getPrecioHabitacion(habitacion.getId()));
            }
        }
    }
    
    private void saveAndReserve() {
        // Implementa la lógica para guardar el cliente y reservar las habitaciones
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        String dni = dniField.getText();
        String email = emailField.getText();
        String telefono = telefonoField.getText();
        // Convierto JDateChooser en String.
        Date fechaSalidaDate = fechaSalidaChooser.getDate();
        String fechaSalida = "";
        if (fechaSalidaDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            fechaSalida = sdf.format(fechaSalidaDate);
        }
        
        // Primero, guardo al cliente y obtengo su ID(lo necesito para relacionarlo con la posterior reserva de habitacion)
        int clienteID = DatabaseHelper.saveCliente(nombre, apellido, dni, email, telefono,fechaSalida);
        
        if (clienteID == -1) {
            JOptionPane.showMessageDialog(this, "Error al guardar el cliente");
            return;
        }

        List<Integer> habitacionesSeleccionadas = habitacionesList.getSelectedValuesList()
                .stream()
                .map(s -> Integer.parseInt(s.split(" - ")[0]))
                .collect(Collectors.toList());

        if (habitacionesSeleccionadas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona al menos una habitación para reservar");
            return;
        }

        boolean reservaExitosa = true;
        for (int habitacionID : habitacionesSeleccionadas) {
            if (!DatabaseHelper.reservarHabitacion(habitacionID,clienteID)) {
                reservaExitosa = false;
                break;
            }
        }

        if (reservaExitosa) {
            JOptionPane.showMessageDialog(this, "Cliente guardado y habitaciones reservadas con éxito");
        } else {
            JOptionPane.showMessageDialog(this, "Error al reservar una o más habitaciones");
        }
    }
}
