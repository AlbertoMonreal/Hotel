package com.hotel.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.hotel.database.*;

import javax.swing.JOptionPane;

public class SignUpGUI extends BaseFrame {

    public SignUpGUI() {
        super("Hotel Registration System",500, 920);
    }

    @Override
    protected void addGuiComponents() {
        JLabel titleUser = new JLabel("Sign Up");
        titleUser.setFont(new Font("Arial", Font.BOLD, 24));
        titleUser.setHorizontalAlignment(SwingConstants.CENTER);
        titleUser.setBounds(0, 20, getWidth(), 40);
        add(titleUser);

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        firstNameLabel.setBounds(30, 80, getWidth() - 60, 25);
        add(firstNameLabel);

        JTextField firstNameField = new JTextField();
        firstNameField.setBounds(30, 120, getWidth() - 60, 35);
        firstNameField.setFont(new Font("Dialog", Font.PLAIN, 18));
        add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        lastNameLabel.setBounds(30, 180, getWidth() - 60, 25);
        add(lastNameLabel);

        JTextField lastNameField = new JTextField();
        lastNameField.setBounds(30, 220, getWidth() - 60, 35);
        lastNameField.setFont(new Font("Dialog", Font.PLAIN, 18));
        add(lastNameField);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        usernameLabel.setBounds(30, 280, getWidth() - 60, 25);
        add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(30, 320, getWidth() - 60, 35);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 18));
        add(usernameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        emailLabel.setBounds(30, 380, getWidth() - 60, 25);
        add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(30, 420, getWidth() - 60, 35);
        emailField.setFont(new Font("Dialog", Font.PLAIN, 18));
        add(emailField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        phoneLabel.setBounds(30, 460, getWidth() - 60, 25);
        add(phoneLabel);

        JTextField phoneField = new JTextField();
        phoneField.setBounds(30, 500, getWidth() - 60, 35);
        phoneField.setFont(new Font("Dialog", Font.PLAIN, 18));
        add(phoneField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        passwordLabel.setBounds(30, 560, getWidth() - 60, 25);
        add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(30, 600, getWidth() - 60, 35);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 18));
        add(passwordField);

        JLabel retypePasswordLabel = new JLabel("Retype Password:");
        retypePasswordLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        retypePasswordLabel.setBounds(30, 660, getWidth() - 60, 25);
        add(retypePasswordLabel);

        JPasswordField retypePasswordField = new JPasswordField();
        retypePasswordField.setBounds(30, 700, getWidth() - 60, 35);
        retypePasswordField.setFont(new Font("Dialog", Font.PLAIN, 18));
        add(retypePasswordField);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(30, 760, getWidth() - 60, 40);
        registerButton.setFont(new Font("Dialog", Font.BOLD, 20));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String username = usernameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();
                String password = new String(passwordField.getPassword());
                String retypePassword = new String(retypePasswordField.getPassword());

                if (username.isEmpty() || password.isEmpty() || retypePassword.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields must be filled out", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!password.equals(retypePassword)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (password.length() < 5) {
                    JOptionPane.showMessageDialog(null, "Password should have 5 characters minimum!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (isValidEmail(email)!=true) { //Compruebo la sintaxis del email antes de intentar enviarlo a la BD.
                    JOptionPane.showMessageDialog(null, "error: Email sintax!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Llamada a la lógica de guardar en base de datos
                    boolean success = DatabaseHelper.registerUser(firstName, lastName, username, email, phone, password);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "User registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        new LoginGUI().setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to register user.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        add(registerButton);

        JLabel loginLabel = new JLabel("Already have an account? Login here.");
        loginLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        loginLabel.setForeground(Color.BLUE);
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setBounds(30, 810, getWidth() - 60, 30);
        loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new LoginGUI().setVisible(true);
                dispose();
            }
        });
        add(loginLabel);
    }


    // Método para validar el formato del email
    private static boolean isValidEmail(String email) {
        // Expresión regular donde solo importa el @
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
