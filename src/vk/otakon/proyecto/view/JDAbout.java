package vk.otakon.proyecto.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

/**
 * Clase JDialog que genera la ventana about con los datos del programa.
 * 
 * @author David.Plaza
 *
 */

public class JDAbout extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			JDAbout dialog = new JDAbout();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructor de la ventana JDAbout.
	 */
	public JDAbout() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDAbout.class.getResource("/img/64/25-512.png")));
		setTitle("About Gestor Motor");
		setModal(true);
		setBounds(100, 100, 285, 255);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblDesarrolladoPor = new JLabel("Version: 1.0");
		lblDesarrolladoPor.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDesarrolladoPor.setBounds(29, 26, 91, 29);
		contentPanel.add(lblDesarrolladoPor);
		
		JLabel lblRelease = new JLabel("Release date:  31-05-2018");
		lblRelease.setFont(new Font("Arial", Font.PLAIN, 12));
		lblRelease.setBounds(29, 55, 181, 45);
		contentPanel.add(lblRelease);
		
		JLabel lblNewLabel = new JLabel("Developed by David Plaza Urda.");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 13));
		lblNewLabel.setBounds(29, 125, 187, 45);
		contentPanel.add(lblNewLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
	}
}
