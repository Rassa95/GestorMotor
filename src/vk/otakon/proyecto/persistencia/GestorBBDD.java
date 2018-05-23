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

public class GestorBBDD {
	
	private final static String DRIVER = "com.mysql.jdbc.Driver";
	private final static String BBDD="jdbc:mysql://localhost/bbdd_gestmotor";
	private final static String USER="dam2018";
	private final static String PASSWORD="dam2018";
	private Connection conexion;
	
	public ResultSet consultarBBDD() throws SQLException {
		
		String sql = "select m.*, e.icono from modelos m, eficiencias e where m.c_energetica = e.c_energetica order by m.modelo";
		
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		return rs;

	}
	
	public ResultSet filtrarBBDDconsumo(float consumo) throws SQLException {
		
		String sql = "select m.*, e.icono from modelos m, eficiencias e where m.c_energetica = e.c_energetica and consumo <= "+consumo+" order by consumo desc";
		
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		return rs;

	}
	
	public ResultSet filtrarBBDDmarca(int marca) throws SQLException {
		
		String sql = "select m.*, e.icono from modelos m, eficiencias e where m.c_energetica = e.c_energetica and id_marca = "+marca+" order by m.modelo";
		
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		return rs;

	}
	
	public ResultSet filtrarBBDDemisiones(float emisiones) throws SQLException {
		
		String sql = "select m.*, e.icono from modelos m, eficiencias e where m.c_energetica = e.c_energetica and emisiones <= "+emisiones+" order by emisiones desc";
		
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		return rs;

	}
	
public ResultSet filtrarBBDDeficiencia(String cali) throws SQLException {
		
		String sql = "select m.*, e.icono from modelos m, eficiencias e where m.c_energetica = e.c_energetica and m.c_energetica = '"+cali+"' order by m.c_energetica desc";
		
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		return rs;

	}
	
	
	
	
	public void addModelo(Modelo model) throws SQLException {
		
		if (conexion.isClosed()) cargarConexion(); 
		
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
	
	public void borrarFila(int id) throws SQLException {
		
		String sql = "DELETE FROM modelos WHERE id = "+id+";";
		
		Statement st = conexion.createStatement();
		st.executeUpdate(sql);
	}
	
	public void modificarModelo(Modelo model) throws SQLException {
		
		if (conexion.isClosed()) cargarConexion(); 
		
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
	
	public ArrayList<Marca> cargarMarcas() throws SQLException {
		
		String sql = "select * from marcas";
		Statement st = conexion.createStatement(); 
		ResultSet rs = st.executeQuery(sql);
		ArrayList<Marca> marcas = new ArrayList<Marca>();
		Marca marca;
		
		while(rs.next()) {
			marca = new Marca(rs.getString("marca"),rs.getInt("id"));
			marcas.add(marca);
		}
		
		return marcas;
	}

	public ArrayList<CalificacionEnergetica> cargarCalificaciones() throws SQLException {
		
		String sql = "select * from eficiencias";
		Statement st = conexion.createStatement(); 
		ResultSet rs = st.executeQuery(sql);
		ArrayList<CalificacionEnergetica> calificaciones = new ArrayList<CalificacionEnergetica>();
		CalificacionEnergetica calificacion;
		
		while(rs.next()) {
			calificacion = new CalificacionEnergetica(rs.getString("C_ENERGETICA"),rs.getString("DESCRIPCION"), rs.getString("ICONO"));
			calificaciones.add(calificacion);
		}
		
		return calificaciones;
	}

	
	
	public GestorBBDD() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
		cargarConexion();
	}


	private void cargarConexion() throws SQLException {
		conexion = DriverManager.getConnection(BBDD, USER, PASSWORD);
	}
	
	
	public void cerrarConexion() throws SQLException {
		conexion.close();
	}



}
