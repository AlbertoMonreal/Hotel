package com.hotel.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.hotel.database.DatabaseHelper;

/*
 * La clase GUI servirá como ventana principal y se utilizará para proporcionar una pantalla de inicio de sesión para la administración del hotel.
 */
public class LoginGUI extends BaseFrame {

    public LoginGUI() {
        super("Hotel Administration System",400, 600);     
    }

    @Override
    protected void addGuiComponents() {
        // Crear el JLabel para el título
        JLabel titleUser = new JLabel("Login");
        titleUser.setFont(new Font("Arial", Font.BOLD, 24)); // Fuente, estilo y tamaño del texto
        titleUser.setHorizontalAlignment(SwingConstants.CENTER); // Alinear el texto al centro
        titleUser.setBounds(0, 20, getWidth(), 40); // Dimensiones y posición
        add(titleUser);

        // Crear el JLabel para el nombre de usuario
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 18)); // Fuente y tamaño del texto
        usernameLabel.setBounds(30, 100, getWidth() - 60, 25); // Dimensiones y posición
        add(usernameLabel);

        // Crear el JTextField para la entrada del nombre de usuario
        JTextField usernameField = new JTextField();
        usernameField.setBounds(30, 140, getWidth() - 60, 35); // Dimensiones y posición
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 18)); // Tamaño del texto
        add(usernameField);

        // Crear el JLabel para la contraseña
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 18)); // Fuente y tamaño del texto
        passwordLabel.setBounds(30, 300, getWidth() - 60, 25); // Dimensiones y posición
        add(passwordLabel);

        // Crear el JPasswordField para la entrada de la contraseña
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(30, 340, getWidth() - 60, 35); // Dimensiones y posición
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 18)); // Tamaño del texto
        add(passwordField);

        // Crear el botón de inicio de sesión
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(30, 460, getWidth() - 60, 40); // Dimensiones y posición
        loginButton.setFont(new Font("Dialog", Font.BOLD, 20)); // Fuente y tamaño del texto
        // Añadir acción al botón de inicio de sesión
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el nombre de usuario y la contraseña introducidos
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Obtener el hash de contraseña almacenado en la base de datos para el nombre de usuario introducido
                String storedHashedPassword = DatabaseHelper.getPasswordHash(username);

                if (storedHashedPassword != null) {
                    // Verificar la contraseña proporcionada con el hash almacenado
                    boolean isPasswordCorrect = DatabaseHelper.checkPassword(password, storedHashedPassword);

                    if (isPasswordCorrect) {
                        // Contraseña correcta, proceder con el acceso
                        JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        // Abro la nueva pestaña
                        new MenuAdministradorGUI().setVisible(true);
                        dispose(); // Cerrar la ventana de inicio de sesión si es necesario
                    } else {
                        // Contraseña incorrecta.
                        JOptionPane.showMessageDialog(null, "Nombre de usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // Usuario no encontrado.
                    JOptionPane.showMessageDialog(null, "Nombre de usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(loginButton);

        // Crear un JLabel para el enlace de registro
        JLabel registerLabel = new JLabel("Don't have an account? Register here.");
        registerLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Fuente y tamaño del texto
        registerLabel.setForeground(Color.BLUE); // Color del texto
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER); // Alinear el texto al centro
        registerLabel.setBounds(30, 500, getWidth() - 60, 30); // Dimensiones y posición
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambiar el cursor por el "manoclick"

        // Añadir un MouseListener al JLabel para manejar clics
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Crear e inicializar la ventana de registro
                SignUpGUI signUp = new SignUpGUI();
                signUp.setVisible(true);
                // Opcional: Cerrar la ventana de login si ya no es necesaria
                dispose();
            }
        });
        add(registerLabel);

        
    }
}
