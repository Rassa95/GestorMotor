package vk.otakon.proyecto.test;

import java.sql.ResultSet;
import java.sql.SQLException;

import vk.otakon.proyecto.model.Modelo;
import vk.otakon.proyecto.persistencia.GestorBBDD;

public class JunitTesting {

	public boolean addModelo() {
		
		try {
			boolean flag = false;
			
			GestorBBDD gb = new GestorBBDD();
			
			Modelo model = new Modelo(5, "Modelo de prueba", 30, 500, "B");
			
			gb.addModelo(model);
			
			ResultSet rs= gb.consultarBBDD();
			
			while(rs.next()) {
				if (rs.getString("modelo").equalsIgnoreCase(model.getModelo())) {
					flag = true;
				}
			}
			
			if(flag) {
				return true;
			}else {
				return false;
			}
		} catch (ClassNotFoundException | SQLException e) {
			return false;
		}
	}
	
	
	
	public boolean delModelo() {
		boolean encontrado = false;
		boolean borrado = false;
		
		try {

			GestorBBDD gb = new GestorBBDD();
		
			Modelo model = new Modelo(5, "Modelo de prueba", 30, 500, "B");
			
			ResultSet rs= gb.consultarBBDD();
			
			while(rs.next()) {
				if (rs.getString("modelo").equalsIgnoreCase(model.getModelo())) {
					encontrado = true;
					gb.borrarFila(rs.getInt("id"));
					break;
				}
			}
			
			if (encontrado) {
				
				rs= gb.consultarBBDD();
				
				while(rs.next()) {
					if (rs.getString("modelo").equalsIgnoreCase(model.getModelo())) {
						borrado = true;
						break;
					}
				}
			}
			
			if (encontrado && borrado) {
				return true;
			}else {
				return false;
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			return false;
		}
		
	}
}
