package frsf.isi.died.guia08.problema01.modelo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

public class Tarea {

	private Integer id;
	private String descripcion;
	private Integer duracionEstimada;
	private Empleado empleadoAsignado;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
	private Boolean facturada;
	
	public Tarea() {
        Random ran = new Random(); 
        this.id = ran.nextInt(1000); 
        this.descripcion="";
        facturada=false;
        duracionEstimada=0;
        
	} 
	
	public Tarea(Integer id, String descripcion, Integer duracionEstimada) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.duracionEstimada = duracionEstimada;
		facturada=false;
	}

	public class EmpleadoAsignadoException extends Exception {
		public EmpleadoAsignadoException() {
			super("Esta tarea ya tiene un empleado asignado");
		}
	}
	
	public class TareaFinalizadaException extends Exception {
		public TareaFinalizadaException() {
			super("Esta tarea ya ha sido finalizada");
		}
	}
	@Override
	public boolean equals(Object o) {
		return (o instanceof Tarea && ((Tarea)o).id==this.id);
	}
	
	public boolean sosVos(Integer idd) {
		return (idd.intValue()==(this.id));
	}
	
	
	public void asignarEmpleado(Empleado e) throws EmpleadoAsignadoException, TareaFinalizadaException{
		if(fechaFin!=null) throw new TareaFinalizadaException();
		if(this.empleadoAsignado!=null) throw new EmpleadoAsignadoException();
		this.empleadoAsignado=e;
		// si la tarea ya tiene un empleado asignado
		// y tiene fecha de finalizado debe lanzar una excepcion
	}

	public Integer getId() {
		return id;
	}
	public Boolean terminada() {
		return(fechaFin!=null);
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getDuracionEstimada() {
		return duracionEstimada;
	}

	public void setDuracionEstimada(Integer duracionEstimada) {
		this.duracionEstimada = duracionEstimada;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Boolean getFacturada() {
		return facturada;
	}

	public void setFacturada(Boolean facturada) {
		this.facturada = facturada;
	}

	public Empleado getEmpleadoAsignado() {
		return empleadoAsignado;
	}
	
	public Long getDias() {
		if(fechaFin==null) return 0l;
		else {
			Duration d = Duration.between(fechaInicio, fechaFin);
			if(d.toHours()%24 == 0) return d.toDays();
			else return d.toDays()+1;
			//considero que si uso parte de un dia ese dia se cuenta
		}	
	}
	
	public String asCsv() {
		return this.id+ ";\""+ this.descripcion+"\";"+this.duracionEstimada+"\";" + this.empleadoAsignado.getCuil() +"\";" + this.empleadoAsignado.getNombre();
	}
	
}
