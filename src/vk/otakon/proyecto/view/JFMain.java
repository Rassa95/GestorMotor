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
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class JFMain extends JFrame {

	protected JPanel contentPane;
	protected JPAlta pAlta;
	protected JPModificar pModificar;
	protected JPConsulta2 pConsulta2;
	
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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		

		try {
			gb = new GestorBBDD();
		} catch (ClassNotFoundException e1) {
			
			JOptionPane.showMessageDialog(null, "Error de clase" + e1.getMessage());
		} catch (SQLException e1) {
			
			JOptionPane.showMessageDialog(null, "Se ha producido un error SQL: \n" + e1.getMessage());
		}
		
		
		pAlta = new JPAlta(gb, this);
		pModificar = new JPModificar(pConsulta2, gb, this);
		pConsulta2 = new JPConsulta2(gb, this, pModificar);
		
		
		contentPane.add(pAlta, "pAlta");
		contentPane.add(pModificar, "pModificar");
		contentPane.add(pConsulta2, "pConsulta2");
	}
	
	protected void cambiarPantalla(String nombrePantalla) {
		((CardLayout)(contentPane.getLayout())).show(contentPane, nombrePantalla);
	}
	
	protected void cambiarPantallaConsulta() {
		
		pConsulta2.aplicarFiltro();
		cambiarPantalla("pConsulta2");
		
	}
}
