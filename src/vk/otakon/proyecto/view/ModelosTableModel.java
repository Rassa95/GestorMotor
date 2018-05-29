package vk.otakon.proyecto.view;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 * Clase TableModel que define como será la tabla de la pantalla Consulta.
 * @author David.Plaza
 *
 */
public class ModelosTableModel extends AbstractTableModel {

	private static final int NUMERO_COLUMNAS = 4;
	
	ResultSet rs;
	HashMap<String, ImageIcon> imagenes = new HashMap<String, ImageIcon>();

	public ModelosTableModel(ResultSet rs) {
		this.rs = rs;

	}

	/**
	 * Método que defineel número de columnas de la tabla.
	 */
	@Override
	public int getColumnCount() {

		return NUMERO_COLUMNAS;
	}

	/**
	 * Método que define el número de filas de la tabla.
	 */
	@Override
	public int getRowCount() {

		int contador;

		try {

			rs.last();
			contador = rs.getRow();
			rs.first();

			return contador;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;

	}

	/**
	 * Método que define el contenido de cada celda de la tabla.
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		try {

			rs.absolute(rowIndex + 1);

			switch (columnIndex) {

			case 0:
				return rs.getString("modelo");
			case 1:
				return rs.getFloat("consumo");
			case 2:
				return rs.getFloat("emisiones");
			case 3:

				if (imagenes.containsKey(rs.getString("icono"))) {

					return imagenes.get(rs.getString("icono"));

				} else {
					try {

						imagenes.put(rs.getString("icono"), new ImageIcon(ImageIO.read(
								ModelosTableModel.class.getResourceAsStream("/img/ce/" + rs.getString("icono")))));
						return imagenes.get(rs.getString("icono"));

					} catch (IOException e) {
						e.printStackTrace();
						return null;
					}
				}

			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error SQL" + e.getMessage() + e.getErrorCode());
		}

		return "das";

	}

	/**
	 * Método que define los nombres de las columnas de la tabla.
	 */
	@Override
	public String getColumnName(int column) {

		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			String[] nombres = { rsmd.getColumnName(3), rsmd.getColumnName(4), rsmd.getColumnName(5),
					rsmd.getColumnName(6) };
			return nombres[column];
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
				    "Se ha producido un error de conexión con la base de datos.",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	/**
	 * Método que define la clases de datos que va a tener cada columna.
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {

		Class[] clases = { String.class, Float.class, Float.class, ImageIcon.class };

		return clases[columnIndex];
	}
	
	
}
