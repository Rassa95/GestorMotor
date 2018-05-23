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

public class ModelosTableModel extends AbstractTableModel {

	ResultSet rs;
	HashMap<String, ImageIcon> imagenes = new HashMap<String, ImageIcon>();

	public ModelosTableModel(ResultSet rs) {
		this.rs = rs;

	}

	@Override
	public int getColumnCount() {

		return 4;
	}

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

						imagenes.put(rs.getString("icono"), new ImageIcon(ImageIO
								.read(ModelosTableModel.class.getResourceAsStream("/img/ce/" + rs.getString("icono")))));
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

	@Override
	public String getColumnName(int column) {

		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			String[] nombres = { rsmd.getColumnName(3), rsmd.getColumnName(4), rsmd.getColumnName(5),
					rsmd.getColumnName(6) };
			return nombres[column];
		} catch (SQLException e) {// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {

		Class[] clases = { String.class, Float.class, Float.class, ImageIcon.class };

		return clases[columnIndex];
	}
}
