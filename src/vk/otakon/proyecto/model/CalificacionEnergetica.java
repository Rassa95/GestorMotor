package vk.otakon.proyecto.model;

/**
 * Clase bin de clasificacion energetica.
 * 
 * @author David.Plaza
 *
 */
public class CalificacionEnergetica {

	private String calificacion;
	private String descripcion;
	private String icono;

	/**
	 * Contructor de calificacion energetica.
	 * 
	 * @param calificacion Calificacion energetica.
	 * @param descripcion Descripción de la calificacion.
	 * @param icono	Nombre del archivo con la imagen de la calificación.
	 */
	public CalificacionEnergetica(String calificacion, String descripcion, String icono) {

		this.calificacion = calificacion;
		this.descripcion = descripcion;
		this.icono = icono;
	}

	public String getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(String calificacion) {
		this.calificacion = calificacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}
}
