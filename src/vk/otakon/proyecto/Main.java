package vk.otakon.proyecto;

import java.awt.EventQueue;
import vk.otakon.proyecto.view.JFMain;


/**
 * Main principal del programa.
 * @author David.Plaza
 *
 */
public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFMain frame = new JFMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
