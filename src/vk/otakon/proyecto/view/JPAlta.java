package vk.otakon.proyecto.view;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Frame;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JToolBar;

import sun.nio.cs.ext.GB18030;
import vk.otakon.proyecto.model.CalificacionEnergetica;
import vk.otakon.proyecto.model.Marca;
import vk.otakon.proyecto.model.Modelo;
import vk.otakon.proyecto.persistencia.GestorBBDD;
import vk.otakon.proyecto.view.JFMain;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;

/**
 * Clase JPanel que permite crear un nuevo modelo y añadirlo a la base de datos.
 * 
 * @author David.Plaza
 *
 */
public class JPAlta extends JPanel {

	private JTextField tfModelo;

	private JComboBox cbMarcas;
	private JComboBox cbCalificacion;
	private JLabel lbEmisiones;
	private JLabel lbConsumo;
	private GestorBBDD gb;
	private JFMain jfmain;

	private ArrayList<Marca> marcas;
	private ArrayList<CalificacionEnergetica> calificaciones;

	/**
	 * Create the panel.
	 */

	public JPAlta(GestorBBDD gb, JFMain principal) {

		this.gb = gb;

		setBackground(Color.RED);
		setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		add(toolBar, BorderLayout.NORTH);

		JButton btnGuardar = new JButton("");
		btnGuardar.setToolTipText("A\u00F1adir modelo");
		btnGuardar.setIcon(new ImageIcon(JPAlta.class.getResource("/img/64/la-computacion-en-nube.png")));

		JButton btnBuscar = new JButton((String) null);
		btnBuscar.setToolTipText("Buscar modelos");
		btnBuscar.setIcon(new ImageIcon(JPAlta.class.getResource("/img/64/zoom.png")));
		toolBar.add(btnBuscar);
		toolBar.add(btnGuardar);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		cbMarcas = new JComboBox();
		cbMarcas.setBounds(371, 65, 268, 20);
		panel.add(cbMarcas);

		tfModelo = new JTextField();
		tfModelo.setColumns(10);
		tfModelo.setBounds(371, 128, 268, 20);
		panel.add(tfModelo);

		JLabel label = new JLabel("Modelo");
		label.setBounds(189, 131, 78, 14);
		panel.add(label);

		JLabel label_1 = new JLabel("Consumo");
		label_1.setBounds(189, 200, 78, 14);
		panel.add(label_1);

		JLabel label_2 = new JLabel("Emisiones");
		label_2.setBounds(189, 272, 78, 14);
		panel.add(label_2);

		JLabel label_3 = new JLabel("Marca");
		label_3.setBounds(189, 68, 79, 14);
		panel.add(label_3);

		cbCalificacion = new JComboBox();
		cbCalificacion.setBounds(371, 342, 268, 20);
		panel.add(cbCalificacion);

		JLabel lblCalificacion = new JLabel("Calificacion Energetica ");
		lblCalificacion.setBounds(189, 345, 150, 14);
		panel.add(lblCalificacion);

		JSlider slConsumo = new JSlider();
		slConsumo.setValue(0);
		slConsumo.setMinorTickSpacing(25);
		slConsumo.setMajorTickSpacing(50);
		slConsumo.setPaintTicks(true);
		slConsumo.setMaximum(250);
		slConsumo.setBounds(371, 189, 268, 23);
		panel.add(slConsumo);

		JSlider slEmision = new JSlider();
		slEmision.setValue(0);
		slEmision.setMinorTickSpacing(500);
		slEmision.setPaintTicks(true);
		slEmision.setBorder(null);
		slEmision.setMajorTickSpacing(1000);
		slEmision.setMaximum(5000);
		slEmision.setBounds(371, 272, 262, 23);
		panel.add(slEmision);

		lbConsumo = new JLabel("");
		lbConsumo.setBounds(648, 200, 46, 14);
		panel.add(lbConsumo);
		lbConsumo.setText("0.0");

		lbEmisiones = new JLabel("");
		lbEmisiones.setBounds(648, 272, 46, 14);
		panel.add(lbEmisiones);
		lbEmisiones.setText("0.0");

		slConsumo.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				DecimalFormat f1 = new DecimalFormat("#.00");
				lbConsumo.setText(f1.format(slConsumo.getValue() * 0.1));
			}
		});

		slEmision.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				DecimalFormat f1 = new DecimalFormat("#.00");
				lbEmisiones.setText(f1.format(slEmision.getValue() * 0.1));
			}
		});

		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				principal.cambiarPantalla("pConsulta");
			}
		});

		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				guardarModelo();
			}
		});

		cargarDatos();

	}
	
	/**
	 * Método para cargar los datos de los Combo Box en memoria.
	 */
	private void cargarDatos() {

		try {
			marcas = gb.cargarMarcas();
			calificaciones = gb.cargarCalificaciones();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Marca marca : marcas) {
			cbMarcas.addItem(marca.getNombre());
		}

		for (CalificacionEnergetica calificacion : calificaciones) {
			cbCalificacion.addItem(calificacion.getDescripcion());
		}
	}

	/**
	 * Método que crea un objeto modelo con los datos recogidos y lo añade a la base de datos.
	 */
	private void guardarModelo() {

		int posicion = cbMarcas.getSelectedIndex();
		int idMarca = marcas.get(posicion).getId();
		String modelo = tfModelo.getText();

		float consumo = Float.parseFloat(lbConsumo.getText());
		float emisiones = Float.parseFloat(lbEmisiones.getText());

		String eficiencia = cbCalificacion.getSelectedItem().toString();

		if (cbCalificacion.getSelectedIndex() < 7) {
			eficiencia = eficiencia.substring(eficiencia.length() - 1);
		} else {

			eficiencia = eficiencia.substring(eficiencia.length() - 2, eficiencia.length());
		}

		Modelo model = new Modelo(idMarca, modelo, consumo, emisiones, eficiencia);

		if (modelo.trim().equalsIgnoreCase("")) {
			JOptionPane.showMessageDialog(null, "El campo \"Modelo\" debe estar relleno. Inténtelo de nuevo.");
		} else {
			try {
				gb.addModelo(model);
				JOptionPane.showMessageDialog(null, "El modelo " + model.getModelo() + " ha sido añadido con éxito.");
			} catch (SQLException e) {
				
				JOptionPane.showMessageDialog(null,
					    "Se ha producido un error de conexión con la base de datos.",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
