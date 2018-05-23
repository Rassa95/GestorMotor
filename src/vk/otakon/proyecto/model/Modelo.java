package vk.otakon.proyecto.model;

public class Modelo {

	private int id;
	private int id_marca;
	private String modelo;
	private float consumo;
	private float emisiones;
	private String calificacionE;
	
	
	public Modelo(int id, int id_marca, String modelo, float consumo, float emisiones, String calificacionE) {
		
		this.id = id;
		this.id_marca = id_marca;
		this.modelo = modelo;
		this.consumo = consumo;
		this.emisiones = emisiones;
		this.calificacionE = calificacionE;
		
	}
	
	public Modelo(int id_marca, String modelo, float consumo, float emisiones, String calificacionE) {
		
		this.id_marca = id_marca;
		this.modelo = modelo;
		this.consumo = consumo;
		this.emisiones = emisiones;
		this.calificacionE = calificacionE;
	}
	
	public Modelo() {
		// TODO Auto-generated constructor stub
	}

	public void mostrar() {
		System.out.println(id_marca);
		System.out.println(modelo);
		System.out.println(consumo);
		System.out.println(emisiones);
		System.out.println(calificacionE);
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getId_marca() {
		return id_marca;
	}


	public void setId_marca(int id_marca) {
		this.id_marca = id_marca;
	}


	public String getModelo() {
		return modelo;
	}


	public void setModelo(String modelo) {
		this.modelo = modelo;
	}


	public float getConsumo() {
		return consumo;
	}


	public void setConsumo(float consumo) {
		this.consumo = consumo;
	}


	public float getEmisiones() {
		return emisiones;
	}


	public void setEmisiones(float emisiones) {
		this.emisiones = emisiones;
	}


	public String getCalificacionE() {
		return calificacionE;
	}


	public void setCalificacionE(String calificacionE) {
		this.calificacionE = calificacionE;
	}
	
	
	
}
