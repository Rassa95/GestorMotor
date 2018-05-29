package vk.otakon.proyecto.view;

import javax.swing.JPanel;

import vk.otakon.proyecto.model.CalificacionEnergetica;
import vk.otakon.proyecto.model.Marca;
import vk.otakon.proyecto.persistencia.GestorBBDD;
import java.awt.BorderLayout;
import javax.swing.JToolBar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;

/**
 * Clase JPanel donde se realizan y muestran las consultas de los modelos.
 * 
 * @author David.Plaza
 *
 */
public class JPConsulta extends JPanel {

	protected GestorBBDD gb;
	protected ResultSet rs;
	protected JTable table;
	private JFMain jfmain;

	protected JComboBox<String> cbMarcas;
	protected JComboBox<String> cbCalificacion;
	protected JRadioButton bMarcas;
	protected JRadioButton bConsumo;
	protected JRadioButton bEmisiones;
	protected JRadioButton bClasi;
	protected JLabel lbEmisiones;
	protected JLabel lbConsumo;

	protected ArrayList<Marca> marcas;
	protected ArrayList<CalificacionEnergetica> calificaciones;
	protected JPModificar jpModi;
	protected JFMain principal;

	/**
	 * Create the panel.
	 */
	public JPConsulta(GestorBBDD gb, JFMain principal, JPModificar jpModi) {

		this.jpModi = jpModi;
		this.principal = principal;
		this.gb = gb;

		setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		add(toolBar, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 105, 878, 402);
		panel.add(scrollPane);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);

		bMarcas = new JRadioButton("Marcas");
		bMarcas.setBounds(468, 60, 109, 23);
		panel.add(bMarcas);

		bConsumo = new JRadioButton("Consumo m\u00E1ximo");
		bConsumo.setBounds(16, 60, 131, 23);
		panel.add(bConsumo);

		bEmisiones = new JRadioButton("Emisiones m\u00E1ximas");
		bEmisiones.setBounds(16, 19, 151, 23);
		panel.add(bEmisiones);

		bClasi = new JRadioButton("Clasificacion Energetica");
		bClasi.setBounds(468, 19, 173, 23);
		panel.add(bClasi);

		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(bMarcas);
		btnGroup.add(bConsumo);
		btnGroup.add(bEmisiones);
		btnGroup.add(bClasi);

		cbMarcas = new JComboBox();
		cbMarcas.setBounds(647, 61, 231, 20);
		panel.add(cbMarcas);

		cbCalificacion = new JComboBox();
		cbCalificacion.setBounds(647, 20, 231, 20);
		panel.add(cbCalificacion);

		lbEmisiones = new JLabel("New label");
		lbEmisiones.setBounds(402, 23, 46, 14);
		panel.add(lbEmisiones);
		lbEmisiones.setText("0.00");

		lbConsumo = new JLabel("New label");
		lbConsumo.setBounds(402, 64, 46, 14);
		panel.add(lbConsumo);
		lbConsumo.setText("0.00");

		JSlider slEmisiones = new JSlider();
		slEmisiones.setPaintTicks(true);
		slEmisiones.setMinorTickSpacing(500);
		slEmisiones.setMajorTickSpacing(1000);
		slEmisiones.setValue(0);
		slEmisiones.setMaximum(5000);

		slEmisiones.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				DecimalFormat f1 = new DecimalFormat("#.00");
				lbEmisiones.setText(f1.format(slEmisiones.getValue() * 0.1));
			}
		});
		slEmisiones.setBounds(173, 16, 219, 26);
		panel.add(slEmisiones);

		JSlider slConsumo = new JSlider();
		slConsumo.setPaintTicks(true);
		slConsumo.setMinorTickSpacing(25);
		slConsumo.setValue(0);
		slConsumo.setMaximum(250);
		slConsumo.setMajorTickSpacing(50);
		slConsumo.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				DecimalFormat f1 = new DecimalFormat("#.00");
				lbConsumo.setText(f1.format(slConsumo.getValue() * 0.1));
			}
		});
		slConsumo.setBounds(173, 60, 219, 26);
		panel.add(slConsumo);

		JButton btnBorrar = new JButton("");
		btnBorrar.setToolTipText("Borrar seleccionado");
		btnBorrar.setIcon(new ImageIcon(JPConsulta.class.getResource("/img/64/basura.png")));

		JButton btnEditar = new JButton("");
		btnEditar.setToolTipText("Editar seleccionado");
		btnEditar.setIcon(new ImageIcon(JPConsulta.class.getResource("/img/64/editar.png")));

		JButton btnFiltrar = new JButton("");
		btnFiltrar.setToolTipText("Aplicar Filtro");
		btnFiltrar.setIcon(new ImageIcon(JPConsulta.class.getResource("/img/64/filtrar.png")));
		toolBar.add(btnFiltrar);
		toolBar.add(btnEditar);
		toolBar.add(btnBorrar);

		JButton btnSubir = new JButton("");
		btnSubir.setToolTipText("A\u00F1adir modelo");
		btnSubir.setIcon(new ImageIcon(JPAlta.class.getResource("/img/64/la-computacion-en-nube.png")));
		toolBar.add(btnSubir);

		btnSubir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				principal.cambiarPantalla("pAlta");
			}
		});

		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				aplicarFiltro();
			}
		});

		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				editarModelo();
			}
		});

		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				borrarModelo();
				aplicarFiltro();
			}
		});

		cargarDatosFiltros();

		// Peta y no deja editar si lo dejo puesto
		// buscarTodos();
	}

	/**
	 * Método para hacer la confirmación para borrar un modelo de la base de datos.
	 * 
	 * @param modelo a borrar.
	 * @return boolean con la confirmación
	 */
	public boolean getConfirmacion(String modelo) {

		int respuesta = JOptionPane.showConfirmDialog(null, "¿Deseas borrar el modelo " + modelo + "?", "Gestormotor",
				JOptionPane.YES_NO_OPTION);

		if (respuesta == JOptionPane.OK_OPTION) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Método para cargar los datos de los Combo Box en memoria.
	 */
	public void cargarDatosFiltros() {

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
	 * Método para cargar y mostrar todos los modelos de la base de datos.
	 */
	public void buscarTodos() {

		try {
			ModelosTableModel mtm = new ModelosTableModel(gb.consultarBBDD());
			table.setModel(mtm);
		} catch (SQLException e1) {

			JOptionPane.showMessageDialog(null,
				    "Se ha producido un error de conexión con la base de datos.",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);

		}

	}

	/**
	 * Método para hacer una consulta y ver los resultados a la base de datos pero aplicando el filtro seleccionado.
	 */
	public void aplicarFiltro() {
		if (bConsumo.isSelected()) {
			try {
				rs = gb.filtrarBBDDconsumo(Float.parseFloat(lbConsumo.getText()));
				table.setModel(new ModelosTableModel(rs));
			} catch (SQLException e1) {

				JOptionPane.showMessageDialog(null,
					    "Se ha producido un error de conexión con la base de datos.",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);

			}
		} else if (bMarcas.isSelected()) {
			try {

				int idMarca = marcas.get(cbMarcas.getSelectedIndex()).getId();
				rs = gb.filtrarBBDDmarca(idMarca);
				table.setModel(new ModelosTableModel(rs));

			} catch (SQLException e1) {

				JOptionPane.showMessageDialog(null,
					    "Se ha producido un error de conexión con la base de datos.",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);

			}

		} else if (bEmisiones.isSelected()) {
			try {
				rs = gb.filtrarBBDDemisiones(Float.parseFloat(lbEmisiones.getText()));
				table.setModel(new ModelosTableModel(rs));
			} catch (SQLException e1) {

				JOptionPane.showMessageDialog(null,
					    "Se ha producido un error de conexión con la base de datos.",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);

			}
		} else if (bClasi.isSelected()) {
			try {

				String eficiencia = cbCalificacion.getSelectedItem().toString();

				if (cbCalificacion.getSelectedIndex() < 7) {
					eficiencia = eficiencia.substring(eficiencia.length() - 1);
				} else {

					eficiencia = eficiencia.substring(eficiencia.length() - 2, eficiencia.length());
				}

				rs = gb.filtrarBBDDeficiencia(eficiencia);
				table.setModel(new ModelosTableModel(rs));
			} catch (SQLException e1) {

				JOptionPane.showMessageDialog(null,
					    "Se ha producido un error de conexión con la base de datos.",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);

			}
		} else {
			JOptionPane.showMessageDialog(null,
					"No tienes ningun filtro selecionado. Por favor, selecciona uno para aplicar un filtro.");
		}

	}

	/**
	 * Método que muestra la pantalla de modificacion cargando en esta todos los datos del modelo que se quiera modificar.
	 */
	public void editarModelo() {

		if (table.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, "Por favor, selecciona la fila que quieras editar.");
		} else {
			int id = table.getSelectedRow() + 1;
			try {
				rs.absolute(id);

				for (int i = 0; i < marcas.size(); i++) {
					if (rs.getInt("id_marca") == jpModi.marcas.get(i).getId()) {
						jpModi.cbMarcas.setSelectedIndex(i);
						break;
					}
				}

				for (int i = 0; i < calificaciones.size(); i++) {
					if (rs.getString("C_ENERGETICA").equalsIgnoreCase(jpModi.calificaciones.get(i).getCalificacion())) {
						jpModi.cbCalificacion.setSelectedIndex(i);
						break;
					}
				}

				jpModi.tfModelo.setText(rs.getString("modelo"));
				jpModi.slConsumo.setValue(Math.round(rs.getFloat("consumo") * 10));
				jpModi.slEmisiones.setValue(Math.round(rs.getFloat("emisiones") * 10));
				jpModi.model.setId(rs.getInt("id"));

				principal.cambiarPantalla("pModificar");

			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null,
					    "Se ha producido un error de conexión con la base de datos.",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
			}

		}

	}

	/**
	 * Método para borrar el modelo seleccionado de la tabla de la base de datos.
	 */
	public void borrarModelo() {
		if (table.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, "Por favor, selecciona una fila de la tabla para borrar.");
		} else {
			if (getConfirmacion((String) table.getValueAt(table.getSelectedRow(), 0))) {
				int id = table.getSelectedRow() + 1;

				try {
					rs.absolute(id);
					gb.borrarFila(rs.getInt("ID"));
					// rs = gb.consultarBBDD();
					table.setModel(new ModelosTableModel(rs));
					JOptionPane.showMessageDialog(null, "Modelo eliminado con éxito.");
				} catch (SQLException e1) {
					
					JOptionPane.showMessageDialog(null,
						    "Se ha producido un error de conexión con la base de datos.",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
		}

	}
}
