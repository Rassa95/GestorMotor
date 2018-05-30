package vk.otakon.proyecto.model;


/**
 * Clase bin Marca.
 * @author David.Plaza
 *
 */
public class Marca {

	private String nombre;
	private int id;

	/**
	 * Constructor de Marca.
	 * 
	 * @param nombre Nombre de la marca.
	 * @param id Número identificativo de la marca.
	 */
	public Marca(String nombre, int id) {
		this.nombre = nombre;
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
