package com.hotel.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import com.hotel.samples.HabitacionConCliente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class DatabaseHelper {

    // Parámetros de conexión a la base de datos
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/login_schema";
    private static final String USER = "root";
    private static final String PASSWORD = "alberto123";

    // Método para obtener una conexión a la base de datos
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Método para registrar un nuevo usuario
    public static boolean registerUser(String firstName, String lastName, String username, String email, String phone, String password) {
        // Encripto la contraseña antes de insertarla.
        String hashedPassword = hashPassword(password);
        
        String sql = "INSERT INTO recepcionista (nombre_usuario, nombre, apellido, email, telefono, passwd) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, email);
            pstmt.setString(5, phone);
            pstmt.setString(6, hashedPassword); // Insertar la contraseña encriptada.
            
            int rowsAffected = pstmt.executeUpdate(); //Linea para la consulta INSERT INTO.
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener el hash de la contraseña almacenado en la base de datos para un usuario específico
    public static String getPasswordHash(String username) {
        String sql = "SELECT passwd FROM recepcionista WHERE nombre_usuario = ?";
        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery(); //Linea para las consultas SELECT.

            if (rs.next()) { //Si hay una siguiente fila.
                return rs.getString("passwd");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    //METODOS AUXILIARES

    // Método para encriptar la contraseña
    public static String hashPassword(String password) {
        // Genero un salt de 12 y encripto la contraseña.
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    // Método para verificar la contraseña
    public static boolean checkPassword(String password, String hashed) {
        // Verifico que la contraseña proporcionada coincide con la contraseña encriptada
        return BCrypt.verifyer().verify(password.toCharArray(), hashed).verified;
    }


    public static List<HabitacionConCliente> getHabitacionesClienteTabla() {

        List<HabitacionConCliente> habitacionesCliente = new ArrayList<>();
        /*String sql = "SELECT h.id, h.tipo, h.EstaReservada, c.nombre AS cliente_nombre, c.apellido AS cliente_apellido " +
                        "FROM habitaciones h " + "JOIN clientes c ON h.ClienteID = c.id";*/

        //De esta manera permites valores nulos de cliente.
        String sql = "SELECT h.id, h.tipo, h.EstaReservada, c.nombre AS cliente_nombre, c.apellido AS cliente_apellido " +
                        "FROM habitaciones h " +
                        "LEFT JOIN clientes c ON h.ClienteID = c.id";
        
        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {
    
            while (rs.next()) {
                int id = rs.getInt("id");
                String tipo = rs.getString("tipo");
                boolean estaReservada = rs.getBoolean("EstaReservada");
                String clienteNombre = rs.getString("cliente_nombre");
                String clienteApellido = rs.getString("cliente_apellido");
    
                HabitacionConCliente habitacion = new HabitacionConCliente(
                    id,
                    tipo,
                    estaReservada,
                    clienteNombre != null ? clienteNombre : "No disponible", //Si no hay nombre, que se imprima el valor como No Disponible
                    clienteApellido != null ? clienteApellido : "No disponible" //Si no hay apellido, que se imprima el valor como No Disponible
                );
                habitacionesCliente.add(habitacion);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return habitacionesCliente;
    }

    public static List<HabitacionConCliente> getHabitacionesDisponibles() {
        List<HabitacionConCliente> habitacionesCliente = new ArrayList<>();
    
        // Consulta para obtener solo las habitaciones disponibles (no reservadas)
        String sql = "SELECT h.id, h.tipo, h.EstaReservada, c.nombre AS cliente_nombre, c.apellido AS cliente_apellido " +
                    "FROM habitaciones h " +
                    "LEFT JOIN clientes c ON h.ClienteID = c.id " +
                    "WHERE h.EstaReservada = FALSE";  // Filtra las habitaciones no reservadas
    
        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {
    
            while (rs.next()) {
                int id = rs.getInt("id");
                String tipo = rs.getString("tipo");
                boolean estaReservada = rs.getBoolean("EstaReservada");
                String clienteNombre = rs.getString("cliente_nombre");
                String clienteApellido = rs.getString("cliente_apellido");
    
                HabitacionConCliente habitacion = new HabitacionConCliente(
                    id,
                    tipo,
                    estaReservada,
                    clienteNombre != null ? clienteNombre : "No disponible",
                    clienteApellido != null ? clienteApellido : "No disponible"
                );
                habitacionesCliente.add(habitacion);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return habitacionesCliente;
    }
    //Obtener precio de la habitacion.
    public static double getPrecioHabitacion(int habitacionID) {
        double precio = 0.0;
        String sql = "SELECT Precio FROM habitaciones WHERE id = ?";
    
        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setInt(1, habitacionID);
            ResultSet rs = pstmt.executeQuery();
    
            if (rs.next()) {
                precio = rs.getDouble("Precio");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return precio;
    }
    
    

public static int saveCliente(String nombre, String apellido, String dni, String email, String telefono,String fechaSalida) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Conectar a la base de datos
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // SQL para insertar un nuevo cliente
            String sql = "INSERT INTO clientes (nombre, apellido, dni, email, telefono, fecha_salida) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellido);
            preparedStatement.setString(3, dni);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, telefono);
            preparedStatement.setString(6, fechaSalida);

            // Ejecutar la inserción
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating client failed, no rows affected.");
            }

            // Obtener el ID del cliente recién insertado
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);  // Retorna el ID del cliente
            } else {
                throw new SQLException("Creating client failed, no ID obtained.");
            }

        } catch (SQLException e) {
            e.printStackTrace();  
            return -1;  // Error al guardar el cliente
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static boolean reservarHabitacion(int habitacionID, int clienteID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Me conecto a la BD
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // SQL para actualizar la habitación como reservada
            String sql = "UPDATE habitaciones SET clienteID = ?, EstaReservada = TRUE WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, clienteID);
            preparedStatement.setInt(2, habitacionID);

            // Ejecutar la actualización
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;  // Retorna true si la actualización fue exitosa

        } catch (SQLException e) {
            e.printStackTrace();  
            return false;  // Indico que hubo un error al reservar la habitación
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Método para liberar una "reserva".(Puede ser mas óptimo si creamos una tabla reserva.)
    public static boolean liberarReserva(int clienteID) {
        try (Connection conn = getConnection();
            PreparedStatement updateHabitacionStmt = conn.prepareStatement(
                "UPDATE habitaciones SET clienteID = NULL, EstaReservada = FALSE WHERE clienteID = ?");
            PreparedStatement deleteClienteStmt = conn.prepareStatement(
                "DELETE FROM clientes WHERE id = ?")) {

            // Primero, desasociar el cliente de todas las habitaciones en las que esté reservado
            updateHabitacionStmt.setInt(1, clienteID);
            int habitacionesActualizadas = updateHabitacionStmt.executeUpdate();

            // Luego, se elimina el cliente
            deleteClienteStmt.setInt(1, clienteID);
            int clienteEliminado = deleteClienteStmt.executeUpdate();

            // Verificar si ambos procesos se realizaron con éxito
            return habitacionesActualizadas > 0 || clienteEliminado > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Error al liberar la reserva
        }
    }
    

    
}
