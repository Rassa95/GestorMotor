package vk.otakon.proyecto.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import vk.otakon.proyecto.persistencia.GestorBBDD;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.CardLayout;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;


/**
 * Clase JFFrame principal que carga en el todos los JPanel del proyecto.
 * 
 * @author David.Plaza
 *
 */
public class JFMain extends JFrame {

	protected JPanel contentPane;
	protected JPAlta pAlta;
	protected JPModificar pModificar;
	protected JPConsulta pConsulta;

	protected GestorBBDD gb;

	/**
	 * Create the frame.
	 */

	public JFMain() {

		
		setTitle("Gestor Motor");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 900, 645);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		mnFile.add(mntmExit);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAboutGestorMotor = new JMenuItem("About Gestor Motor");
		mntmAboutGestorMotor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDAbout about = new JDAbout();
				about.setVisible(true);
			}
		});
		mnHelp.add(mntmAboutGestorMotor);
		
		JMenuItem mntmGuide = new JMenuItem("Guide");
		mntmGuide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirAyuda();
			}

		});
		mnHelp.add(mntmGuide);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));

		iniciarConexionBBDD();

		pAlta = new JPAlta(gb, this);
		pModificar = new JPModificar(pConsulta, gb, this);
		pConsulta = new JPConsulta(gb, this, pModificar);

		contentPane.add(pAlta, "pAlta");
		contentPane.add(pModificar, "pModificar");
		contentPane.add(pConsulta, "pConsulta");
	}

	/**
	 * Método que establece la clase gestionadora de la BBDD e inicia la conexión.
	 */
	private void iniciarConexionBBDD() {
		try {
			gb = new GestorBBDD();
		} catch (ClassNotFoundException e1) {

			JOptionPane.showMessageDialog(null, "Error de clase" + e1.getMessage());
		} catch (SQLException e1) {

			JOptionPane.showMessageDialog(null,
				    "Se ha producido un error de conexión con la base de datos.",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	
	/**
	 * Método que cambia el JPanel que se le solicite.
	 * 
	 * @param Nombre del JPanel que se quiere mostrar.
	 */
	protected void cambiarPantalla(String nombrePantalla) {
		((CardLayout) (contentPane.getLayout())).show(contentPane, nombrePantalla);
	}

	/**
	 * Método que muestra el JPanel de consulta aplicando el filtro que tenga seleccionado, consiguiendo asi
	 * motrar la tabla refrescada en todo momento.
	 */
	protected void cambiarPantallaConsulta() {

		if (pConsulta.bClasi.isSelected()) {
			pConsulta.aplicarFiltro();
		} else  if(pConsulta.bConsumo.isSelected()){
			pConsulta.aplicarFiltro();
		}else  if(pConsulta.bEmisiones.isSelected() ){
			pConsulta.aplicarFiltro();
		}else  if(pConsulta.bMarcas.isSelected()){
			pConsulta.aplicarFiltro();
		}else {
			pConsulta.buscarTodos();
		}
		cambiarPantalla("pConsulta");

	}
	
	
	/**
	 * Método que abre la ayuda del programa alojada en Google Drive.
	 */
	private void abrirAyuda() {
		try {
			URL url = new URL("https://drive.google.com/open?id=10dC36mV16tHrLyP1liqn9oza3d-cLfAKt7Txt2ReUqA");
			Desktop.getDesktop().browse(url.toURI());
		} catch (IOException | URISyntaxException e1) {
			JOptionPane.showMessageDialog(null,
				    "Se ha producido un error en el servidor de la ayuda.",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
		}
	}
}
