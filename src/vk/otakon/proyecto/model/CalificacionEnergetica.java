package vk.otakon.proyecto.model;

public class CalificacionEnergetica {

	private String calificacion;
	private String descripcion;
	private String icono;
	

	
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
