package vk.otakon.proyecto.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vk.otakon.proyecto.model.CalificacionEnergetica;
import vk.otakon.proyecto.model.Marca;
import vk.otakon.proyecto.model.Modelo;

import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * Clase que se encarga de toda la funcionalidad con la base de datos.
 * 
 * @author David.Plaza
 *
 */
public class GestorBBDD {

	private final static String DRIVER = "com.mysql.jdbc.Driver";
	private final static String BBDD = "jdbc:mysql://localhost/bbdd_gestmotor";
	private final static String USER = "dam2018";
	private final static String PASSWORD = "dam2018";
	private Connection conexion;

	/**
	 * Función que hace una consulta de los 100 primeros modelos de la base de datos
	 * ordenandolos por nombre.
	 * 
	 * @return Devuelve un ResulSet con toda la informacion de la consulta.
	 * @throws SQLException
	 */
	public ResultSet consultarBBDD() throws SQLException {

		String sql = "select m.*, e.icono "
				+ "from modelos m, eficiencias e "
				+ "where m.c_energetica = e.c_energetica "
				+ "order by m.modelo "
				+ "limit 100";

		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(sql);

		return rs;

	}
	
	/**
	 * Función que solo se usa para realizar los tests que devuelve todos los modelos
	 * de la base de datos.
	 * 
	 * @return Devuelve un ResulSet con toda la informacion de la consulta.
	 * @throws SQLException
	 */
	public ResultSet consultarBBDDTest() throws SQLException {

		String sql = "select m.*, e.icono "
				+ "from modelos m, eficiencias e "
				+ "where m.c_energetica = e.c_energetica "
				+ "order by m.modelo ";

		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(sql);

		return rs;

	}

	/**
	 * Funcion que hace una consulta filtrando por el consumo de estos.
	 * 
	 * @param consumo por el que se va a filtrar.
	 * @return Devuelve un ResulSet con toda la informacion de la consulta.
	 * @throws SQLException
	 */
	public ResultSet filtrarBBDDconsumo(float consumo) throws SQLException {

		String sql = "select m.*, e.icono "
				+ "from modelos m, eficiencias e "
				+ "where m.c_energetica = e.c_energetica and consumo <= "+ consumo + " "
				+ "order by consumo desc";

		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(sql);

		return rs;

	}

	/**
	 * Funcion que hace una consulta filtrando por la marca..
	 * 
	 * @param marca por la que se va a filtrar.
	 * @return Devuelve un ResulSet con toda la informacion de la consulta.
	 * @throws SQLException
	 */
	public ResultSet filtrarBBDDmarca(int marca) throws SQLException {

		String sql = "select m.*, e.icono "
				+ "from modelos m, eficiencias e "
				+ "where m.c_energetica = e.c_energetica and id_marca = "+ marca + " "
				+ "order by m.modelo";

		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(sql);

		return rs;

	}

	/**
	 * Funcion que hace una consulta filtrando por las emisiones.
	 * 
	 * @param emisiones por las que se van a filtrar.
	 * @return Devuelve un ResulSet con toda la informacion de la consulta.
	 * @throws SQLException
	 */
	public ResultSet filtrarBBDDemisiones(float emisiones) throws SQLException {

		String sql = "select m.*, e.icono "
				+ "from modelos m, eficiencias e "
				+ "where m.c_energetica = e.c_energetica and emisiones <= "+ emisiones + " "
				+ "order by emisiones desc";

		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(sql);

		return rs;

	}

	/**
	 * Funcion que hace una consulta filtrando por la eficiencia de estos.
	 * 
	 * @param eficiencia por la que se va a filtrar.
	 * @return Devuelve un ResulSet con toda la informacion de la consulta.
	 * @throws SQLException
	 */
	public ResultSet filtrarBBDDeficiencia(String cali) throws SQLException {

		String sql = "select m.*, e.icono "
				+ "from modelos m, eficiencias e"
				+ " where m.c_energetica = e.c_energetica and m.c_energetica = '"+ cali + "' "
				+ "order by m.c_energetica desc";

		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(sql);

		return rs;

	}

	/**
	 * Funcion que añade un modelo a la base de datos.
	 * 
	 * @param Objeto modelo que se va a añadir.
	 * @throws SQLException
	 */
	public void addModelo(Modelo model) throws SQLException {

		if (conexion.isClosed())
			cargarConexion();

		String sql = "INSERT INTO modelos (ID_MARCA,MODELO,CONSUMO,EMISIONES,C_ENERGETICA) VALUES (?,?,?,?,?)";

		PreparedStatement pst = conexion.prepareStatement(sql);

		pst.setInt(1, model.getId_marca());
		pst.setString(2, model.getModelo());
		pst.setFloat(3, model.getConsumo());
		pst.setFloat(4, model.getEmisiones());
		pst.setString(5, model.getCalificacionE());
		pst.executeUpdate();

		pst.close();

	}

	/**
	 * Función que borra un modelo de la base de datos.
	 * 
	 * @param id por el que buscamos el modelo a borrar
	 * @throws SQLException
	 */
	public void borrarFila(int id) throws SQLException {

		String sql = "DELETE FROM modelos WHERE id = " + id + ";";

		Statement st = conexion.createStatement();
		st.executeUpdate(sql);
	}

	/**
	 * Funcion que modifica los parametros de un modelo de la base de datos.
	 * 
	 * @param modelocon la informacion actualizada que vamos a meter en la base de datos.
	 * @throws SQLException
	 */
	public void modificarModelo(Modelo model) throws SQLException {

		if (conexion.isClosed())
			cargarConexion();

		String sql = "UPDATE modelos SET ID_MARCA = ?,MODELO = ?,CONSUMO = ?,EMISIONES = ?,C_ENERGETICA = ? WHERE ID = ?";

		PreparedStatement pst = conexion.prepareStatement(sql);

		pst.setInt(1, model.getId_marca());
		pst.setString(2, model.getModelo());
		pst.setFloat(3, model.getConsumo());
		pst.setFloat(4, model.getEmisiones());
		pst.setString(5, model.getCalificacionE());
		pst.setInt(6, model.getId());
		pst.executeUpdate();

		pst.close();
	}

	/**
	 * Funcion para cargar en memoria todas las marcas de la base de datos.
	 * 
	 * @return Devuelve un ArrayList de marcas.
	 * @throws SQLException
	 */
	public ArrayList<Marca> cargarMarcas() throws SQLException {

		String sql = "select * from marcas";
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(sql);
		ArrayList<Marca> marcas = new ArrayList<Marca>();
		Marca marca;

		while (rs.next()) {
			marca = new Marca(rs.getString("marca"), rs.getInt("id"));
			marcas.add(marca);
		}

		return marcas;
	}

	/**
	 * Funcion para cargar en memoria todas las calificaciones energeticas de la
	 * base de datos.
	 * 
	 * @return Devuelve un ArrayList de calificaciones.
	 * @throws SQLException
	 */
	public ArrayList<CalificacionEnergetica> cargarCalificaciones() throws SQLException {

		String sql = "select * from eficiencias";
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(sql);
		ArrayList<CalificacionEnergetica> calificaciones = new ArrayList<CalificacionEnergetica>();
		CalificacionEnergetica calificacion;

		while (rs.next()) {
			calificacion = new CalificacionEnergetica(rs.getString("C_ENERGETICA"), rs.getString("DESCRIPCION"),
					rs.getString("ICONO"));
			calificaciones.add(calificacion);
		}

		return calificaciones;
	}

	/**
	 * Constructor de la clase.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public GestorBBDD() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
		cargarConexion();
	}

	/**
	 * Funcion para cargar la conexión con la base de datos.
	 * 
	 * @throws SQLException
	 */
	private void cargarConexion() throws SQLException {
		conexion = DriverManager.getConnection(BBDD, USER, PASSWORD);
	}

	/**
	 * Funcion para cerrar la conexión con la base de datos.
	 * 
	 * @throws SQLException
	 */
	public void cerrarConexion() throws SQLException {
		conexion.close();
	}
	
	

}
