package vk.otakon.proyecto.view;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JToolBar;

import vk.otakon.proyecto.model.CalificacionEnergetica;
import vk.otakon.proyecto.model.Marca;
import vk.otakon.proyecto.model.Modelo;
import vk.otakon.proyecto.persistencia.GestorBBDD;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 * Clase JPanel da la funcionalidad de modificar un modelo de la base de datos.
 * 
 * @author David.Plaza
 *
 */
public class JPModificar extends JPanel {

	protected JTextField tfModelo;
	protected JComboBox cbMarcas;
	protected JComboBox cbCalificacion;
	protected JSlider slEmisiones;
	protected JSlider slConsumo;
	protected JLabel lbConsumo;
	protected JLabel lbEmisiones;

	protected ArrayList<Marca> marcas;
	protected ArrayList<CalificacionEnergetica> calificaciones;
	protected Modelo model = new Modelo();
	private GestorBBDD gb;
	protected JPConsulta search;

	/**
	 * Create the panel.
	 */
	public JPModificar(JPConsulta busqueda, GestorBBDD gb, JFMain principal) {
		this.gb = gb;
		this.search = busqueda;

		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		add(toolBar, BorderLayout.NORTH);

		JButton btnBuscar = new JButton("");
		btnBuscar.setToolTipText("Realizar nueva busqueda");
		btnBuscar.setIcon(new ImageIcon(JPAlta.class.getResource("/img/64/zoom.png")));
		toolBar.add(btnBuscar);

		JButton btnGuardar = new JButton("");
		btnGuardar.setToolTipText("Guardar cambios");
		btnGuardar.setIcon(new ImageIcon(JPAlta.class.getResource("/img/64/la-computacion-en-nube.png")));
		toolBar.add(btnGuardar);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel label = new JLabel("Marca");
		label.setBounds(147, 109, 79, 14);
		panel.add(label);

		JLabel label_1 = new JLabel("Modelo");
		label_1.setBounds(147, 172, 78, 14);
		panel.add(label_1);

		JLabel label_2 = new JLabel("Consumo");
		label_2.setBounds(147, 241, 78, 14);
		panel.add(label_2);

		JLabel label_3 = new JLabel("Emisiones");
		label_3.setBounds(147, 313, 78, 14);
		panel.add(label_3);

		JLabel label_4 = new JLabel("Calificacion Energetica ");
		label_4.setBounds(147, 386, 150, 14);
		panel.add(label_4);

		cbCalificacion = new JComboBox();
		cbCalificacion.setBounds(329, 383, 268, 20);
		panel.add(cbCalificacion);

		slEmisiones = new JSlider();
		slEmisiones.setValue(0);
		slEmisiones.setPaintTicks(true);
		slEmisiones.setMinorTickSpacing(500);
		slEmisiones.setMaximum(5000);
		slEmisiones.setMajorTickSpacing(1000);
		slEmisiones.setBorder(null);
		slEmisiones.setBounds(329, 313, 262, 23);
		panel.add(slEmisiones);

		slConsumo = new JSlider();
		slConsumo.setValue(0);
		slConsumo.setPaintTicks(true);
		slConsumo.setMinorTickSpacing(25);
		slConsumo.setMaximum(250);
		slConsumo.setMajorTickSpacing(50);
		slConsumo.setBounds(329, 230, 268, 23);
		panel.add(slConsumo);

		tfModelo = new JTextField();
		tfModelo.setColumns(10);
		tfModelo.setBounds(329, 169, 268, 20);
		panel.add(tfModelo);

		cbMarcas = new JComboBox();
		cbMarcas.setBounds(329, 106, 268, 20);
		panel.add(cbMarcas);

		lbConsumo = new JLabel("0.0");
		lbConsumo.setBounds(606, 241, 46, 14);
		panel.add(lbConsumo);

		lbEmisiones = new JLabel("0.0");
		lbEmisiones.setBounds(606, 313, 46, 14);
		panel.add(lbEmisiones);

		slEmisiones.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				DecimalFormat f1 = new DecimalFormat("#.00");
				lbEmisiones.setText(f1.format(slEmisiones.getValue() * 0.1));
			}
		});

		slConsumo.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				DecimalFormat f1 = new DecimalFormat("#.00");
				lbConsumo.setText(f1.format(slConsumo.getValue() * 0.1));
			}
		});

		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				editarModelo();
			}
		});

		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				principal.cambiarPantallaConsulta();
			}
		});

		cargarDatos();
	}

	/**
	 * Método que carga todos los datos recogidos, los pone en un objeto modelo y luego hace un UPDATE en la base 
	 * de datos buscando por el ID del modelo.
	 */
	public void editarModelo() {

		if (tfModelo.getText().trim().equalsIgnoreCase("")) {
			JOptionPane.showMessageDialog(null, "El campo \"Modelo\" debe estar relleno. Inténtelo de nuevo.");
		} else {
			try {
				int posicion = cbMarcas.getSelectedIndex();

				model.setId_marca(marcas.get(posicion).getId());

				model.setModelo(tfModelo.getText());

				model.setConsumo(Float.parseFloat(lbConsumo.getText()));

				model.setEmisiones(Float.parseFloat(lbEmisiones.getText()));

				String eficiencia = cbCalificacion.getSelectedItem().toString();

				if (cbCalificacion.getSelectedIndex() < 7) {
					model.setCalificacionE(eficiencia.substring(eficiencia.length() - 1));
				} else {

					model.setCalificacionE(eficiencia.substring(eficiencia.length() - 2, eficiencia.length()));
				}

				gb.modificarModelo(model);
				JOptionPane.showMessageDialog(null,
						"El modelo " + model.getModelo() + " ha sido modificado con éxito.");
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "Error SQL" + e1.getMessage() + e1.getErrorCode());
			}
		}

	}

	/**
	 * Método para cargar los datos de los Combo Box en memoria.
	 */
	public void cargarDatos() {
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
}
