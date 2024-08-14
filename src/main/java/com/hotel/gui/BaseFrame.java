package com.hotel.gui;

import javax.swing.*;

public abstract class BaseFrame extends JFrame {

    // Constructor que recibe el título de la ventana y el tamaño
    public BaseFrame(String title, int width, int height) {
        initialize(title, width, height);
    }

    // Método privado para configurar las propiedades básicas del JFrame.
    private void initialize(String title, int width, int height) {
        // Establece el título de la ventana
        setTitle(title);

        // Termina el programa cuando se cierra la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Establece el layout a null para tener un diseño absoluto
        setLayout(null);

        // Previene que la ventana sea redimensionable
        setResizable(false);

        // Define el tamaño de la ventana y centra la ventana
        setSizeAndCenter(width, height);
        
        // Llama al método addGuiComponents() de la subclase
        // Este método debe ser definido en cada subclase para añadir los componentes específicos
        addGuiComponents();
    }

    // Método para establecer el tamaño y centrar la ventana
    protected void setSizeAndCenter(int width, int height) {
        setSize(width, height);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
    }

    // Método abstracto que las subclases deben implementar
    // Aquí es donde se deben añadir los componentes específicos de la GUI
    protected abstract void addGuiComponents();
}
