package com.hotel.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
        // Genero un salt y encripto la contraseña
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    // Método para verificar la contraseña
    public static boolean checkPassword(String password, String hashed) {
        // Verifico que la contraseña proporcionada coincide con la contraseña encriptada
        return BCrypt.verifyer().verify(password.toCharArray(), hashed).verified;
    }
}
