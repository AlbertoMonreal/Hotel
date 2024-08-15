package com.hotel.formDelimitadores;

import javax.swing.text.DocumentFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.AttributeSet;

public class StringDocumentFilter extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string.matches("[a-zA-Z]*")) { // Solo permite letras
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text.matches("[a-zA-Z]*")) { // Solo permite letras
            super.replace(fb, offset, length, text, attrs);
        }
    }
}
