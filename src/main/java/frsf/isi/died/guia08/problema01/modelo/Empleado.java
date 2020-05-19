package frsf.isi.died.guia08.problema01.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import frsf.isi.died.guia08.problema01.modelo.Tarea.EmpleadoAsignadoException;
import frsf.isi.died.guia08.problema01.modelo.Tarea.TareaFinalizadaException;

public class Empleado {

	public enum Tipo { CONTRATADO,EFECTIVO}; 
	
	private Integer cuil;
	private String nombre;
	private Tipo tipo;
	private Double costoHora;
	private List<Tarea> tareasAsignadas;
	
	private Function<Tarea, Double> calculoPagoPorTarea;		
	private Predicate<Tarea> puedeAsignarTarea;

	public class TareaNoExisteException extends Exception {
		public TareaNoExisteException() {
			super("No existe la tarea");
		}
	}
	public boolean sosVos(Integer cuil) {
		return (cuil==this.cuil);
	}
	
	public Empleado(Integer cuil, String nombre, Tipo tipo, Double costoHora) {
		super();
		this.cuil = cuil;
		this.nombre = nombre;
		this.tipo = tipo;
		this.costoHora = costoHora;
		this.setTipo(tipo);
	}
	
	public void setTipo(Tipo t) {
		this.tipo=t;
		switch(tipo) {
		case CONTRATADO:
			puedeAsignarTarea= (tarea)->(this.contarPendientes()<5 && (tareasAsignadas==null || !tareasAsignadas.contains(tarea)))	;		
			calculoPagoPorTarea = (tarea) -> {
				if((tarea.getDias()*4)<tarea.getDuracionEstimada()) 
					return (tarea.getDuracionEstimada()*this.costoHora * 1.3);
				else if((((tarea.getDias()*4)-8)>(tarea.getDuracionEstimada()))) 
					return (tarea.getDuracionEstimada()*this.costoHora * 0.75);
				else return (tarea.getDuracionEstimada()*this.costoHora);
			};
			break;
			
		case EFECTIVO:
			puedeAsignarTarea= (tarea)->((this.horasTEstimadas()+tarea.getDuracionEstimada())<=15 &&
								(tareasAsignadas==null || !tareasAsignadas.contains(tarea)))	;
			
			calculoPagoPorTarea = (tarea) -> {
					if((tarea.getDias()*4)<tarea.getDuracionEstimada()) 
							return (tarea.getDuracionEstimada()*this.costoHora * 1.2);
					else return (tarea.getDuracionEstimada()*this.costoHora);
			};
		}
		
	}
	
	public Long contarPendientes () {
		if(tareasAsignadas==null) return 0l;
		return tareasAsignadas.stream()
		.filter((t) -> !t.terminada() )
		.count();
	}
	
	public List<Tarea> tareasSinFacturar() {
		if(tareasAsignadas==null) return new ArrayList<Tarea>();
		else {
			return tareasAsignadas.stream()
			.filter((t)->(!t.getFacturada() && t.terminada()))
			.collect(Collectors.toList());
		}
		
	}
	
	public Double salario() {
		// cargar todas las tareas no facturadas
		// calcular el costo
		// marcarlas como facturadas.
		
		if(tareasAsignadas==null) return 0d;
		return tareasAsignadas.stream()
		.filter((t)->(!t.getFacturada() && t.terminada()))
		.mapToDouble((t)-> {t.setFacturada(true); return this.costoTarea(t);} )
		.sum();

	}
	
	/**
	 * Si la tarea ya fue terminada nos indica cuaal es el monto según el algoritmo de calculoPagoPorTarea
	 * Si la tarea no fue terminada simplemente calcula el costo en base a lo estimado.
	 * @param t
	 * @return
	 */
	public Double costoTarea(Tarea t) {
		if(tareasAsignadas!= null && tareasAsignadas.contains(t) && t.terminada()) return calculoPagoPorTarea.apply(t);
		return (t.getDuracionEstimada()*this.costoHora);
	}
		
	public Boolean asignarTarea(Tarea t) {
		
		if(puedeAsignarTarea.test(t)==false)
			return false;
		else {
			try {
				t.asignarEmpleado(this);
				if(tareasAsignadas==null) tareasAsignadas = new ArrayList<Tarea>();
				tareasAsignadas.add(t);
				return true;
			}
			catch(EmpleadoAsignadoException e) {
				e.getMessage();
				return false;
			}
			catch(TareaFinalizadaException f) {
				f.getMessage();
				return false;
			}
		}
	}
	public Integer horasTEstimadas() {
		if(tareasAsignadas==null) return 0;	
		return tareasAsignadas.stream()
		.filter((t) -> !t.terminada() )
		.mapToInt((t)-> t.getDuracionEstimada() )
		.sum();
		
	}
	
	public void comenzar(Integer idTarea) throws TareaNoExisteException {
		// busca la tarea en la lista de tareas asignadas 
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de inicio la fecha y hora actual
		
		Tarea tarea= buscar (idTarea);
		tarea.setFechaInicio(LocalDateTime.now());
	}
	
	public void finalizar(Integer idTarea) throws TareaNoExisteException{
		// busca la tarea en la lista de tareas asignadas 
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de finalizacion la fecha y hora actual
		
		Tarea tarea= buscar (idTarea);
		tarea.setFechaFin(LocalDateTime.now());
		
	}

	public void comenzar(Integer idTarea,String fecha) throws TareaNoExisteException {

		// busca la tarea en la lista de tareas asignadas 
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de finalizacion la fecha y hora actual
		
		Tarea tarea= buscar (idTarea);
		DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		LocalDateTime d= LocalDateTime.parse(fecha, f);
		tarea.setFechaInicio(d);
		
	}
	
	public void finalizar(Integer idTarea,String fecha)throws TareaNoExisteException {
		// busca la tarea en la lista de tareas asignadas 
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de finalizacion la fecha y hora actual
		
		Tarea tarea= buscar (idTarea);
		DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		LocalDateTime d= LocalDateTime.parse(fecha, f);
		tarea.setFechaFin(d);
	}
	
	
	public Tarea buscar (Integer idTarea)throws TareaNoExisteException {
		if(tareasAsignadas==null)	throw new TareaNoExisteException();

		else {
			return tareasAsignadas.stream()
					.filter(t-> t.sosVos(idTarea))
					.findAny().orElseThrow(TareaNoExisteException::new);		
		}
		
	} 

	public List<Tarea> tar () {
		return tareasAsignadas;
	}
	
	@Override
	public String toString() {
		return nombre;
	} 
	
	public Integer getCuil() {
		return this.cuil;
	}
	
	public String getNombre() {
		return nombre;
	} 
}
