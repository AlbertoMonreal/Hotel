package com.hotel;

import com.hotel.gui.LoginGUI;

public class App 
{
    public static void main( String[] args )
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				new LoginGUI().setVisible(true);
			}
		});
    }
}
