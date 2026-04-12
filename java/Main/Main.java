package Main;
import java.awt.EventQueue;

import com.formdev.flatlaf.FlatLightLaf;

import gui.MainWindowGUI;

public class Main {

	public static void main(String[] args) {
	    EventQueue.invokeLater(() -> {
	        try {
	            FlatLightLaf.setup(); // Apply modern look
	            MainWindowGUI frame = new MainWindowGUI();
	            frame.setVisible(true);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    });
	}
}