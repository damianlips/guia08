package frsf.isi.died.guia08.problema01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import frsf.isi.died.guia08.problema01.modelo.Empleado;
import frsf.isi.died.guia08.problema01.modelo.Empleado.TareaNoExisteException;
import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;
import frsf.isi.died.guia08.problema01.modelo.Tarea;

public class AppRRHH {

	private List<Empleado> empleados;
	
	public void agregarEmpleadoContratado(Integer cuil,String nombre,Double costoHora) {
		// crear un empleado
		// agregarlo a la lista
		if(empleados == null) empleados= new ArrayList<Empleado>();
		Empleado e= new Empleado(cuil, nombre, Tipo.CONTRATADO, costoHora);
		if(!empleados.contains(e)) empleados.add(e);
	}
	
	public void agregarEmpleadoEfectivo(Integer cuil,String nombre,Double costoHora) {
		// crear un empleado
		// agregarlo a la lista		
		if(empleados == null) empleados= new ArrayList<Empleado>();
		Empleado e= new Empleado(cuil, nombre, Tipo.EFECTIVO, costoHora);
		if(!empleados.contains(e)) empleados.add(e);
	}
	
	public void asignarTarea(Integer cuil,Integer idTarea,String descripcion,Integer duracionEstimada) {
		// crear un empleado
		// con el método buscarEmpleado() de esta clase
		// agregarlo a la lista		
		
		Optional<Empleado> e = buscarEmpleado(emp-> emp.sosVos(cuil));
		if(e.isPresent()) {
			Tarea t= new Tarea(idTarea, descripcion, duracionEstimada);
			if(e.get().asignarTarea(t)) System.out.println("Tarea asignada exitosamente");
			else System.out.println(e.get().toString() + " no se puede hacer cargo de la tarea");
		}
	}
	
	public void empezarTarea(Integer cuil,Integer idTarea) {
		// busca el empleado por cuil en la lista de empleados
		// con el método buscarEmpleado() actual de esta clase
		// e invoca al método comenzar tarea
		Optional<Empleado> emp = buscarEmpleado(empleado-> empleado.sosVos(cuil));
		if(emp.isPresent())
			try {
				emp.get().comenzar(idTarea);
			} catch (TareaNoExisteException e) {
				System.out.println(emp.get().toString() + " no tiene asignada esta tarea");
			}
		
	}
	
	public void terminarTarea(Integer cuil,Integer idTarea) {
		// crear un empleado
		// agregarlo a la lista		
		
		Optional<Empleado> emp = buscarEmpleado(empleado-> empleado.sosVos(cuil));
		if(emp.isPresent())
			try {
				emp.get().finalizar(idTarea);
			} catch (TareaNoExisteException e) {
				System.out.println(emp.get().toString() + " no tiene asignada esta tarea");
			}
	}

	public void cargarEmpleadosContratadosCSV(String nombreArchivo) {
		// leer datos del archivo
		// por cada fila invocar a agregarEmpleadoContratado
		
		try(BufferedReader in = new BufferedReader(
				new FileReader (nombreArchivo) 
				)){
			String linea = null;
			while((linea= in.readLine()) != null) {
				String[] fila = linea.split(";");
				Integer cuil= Integer.valueOf(fila[0]);
				String nombre = fila[1];
				Double costoHora = Double.valueOf(fila[2]);
				this.agregarEmpleadoContratado(cuil, nombre, costoHora);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void cargarEmpleadosEfectivosCSV(String nombreArchivo) {
		// leer datos del archivo
		// por cada fila invocar a agregarEmpleadoContratado	
		try(BufferedReader in = new BufferedReader(
				new FileReader (nombreArchivo) 
				)){
			String linea = null;
			while((linea= in.readLine()) != null) {
				String[] fila = linea.split(";");
				Integer cuil= Integer.valueOf(fila[0]);
				String nombre = fila[1];
				Double costoHora = Double.valueOf(fila[2]);
				this.agregarEmpleadoEfectivo(cuil, nombre, costoHora);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cargarTareasCSV(String nombreArchivo) {
		// leer datos del archivo
		// cada fila del archivo tendrá:
		// cuil del empleado asignado, numero de la taera, descripcion y duración estimada en horas.
		
		try(BufferedReader in = new BufferedReader(new FileReader(nombreArchivo))){
			String fila = null;
			while ((fila=in.readLine())!=null) {
				String[] linea = fila.split(";");
				Integer idTarea = Integer.valueOf(linea[0]);
				String descripcion = linea[1];
				Integer duracionEstimada = Integer.valueOf(linea[2]);
				Integer cuil = Integer.valueOf(linea[3]);
				this.asignarTarea(cuil, idTarea, descripcion, duracionEstimada);
			}
		} catch (FileNotFoundException e) {
			System.out.println("No se encontro el archivo csv");
		} catch (IOException e) {
			System.out.println("Error al recuperar los datos, intente nuevamente");
		}
	}
	
	private void guardarTareasTerminadasCSV() {
		// guarda una lista con los datos de la tarea que fueron terminadas
		// y todavía no fueron facturadas
		// y el nombre y cuil del empleado que la finalizó en formato CSV 
		
			try(BufferedWriter out = new BufferedWriter(new FileWriter("src\tareas.csv",true))){
				List<Tarea> tareas = tareasTerminadas();
				for(Tarea t : tareas)
					out.write(t.asCsv() +  System.getProperty("line.separator"));
				
//				No lo implemente de esta otra manera porque pedia otro bloque try catch				
//				this.tareasTerminadas().stream()
//				.map(t -> t.asCsv())
//				.forEach(s-> out.write(s + System.getProperty("line.separator") ) );
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
				
	}
	
	public List<Tarea> tareasTerminadas(){
		if(empleados!=null) {
			return empleados.stream()
					.map(e-> e.tareasSinFacturar())
					.flatMap(List::stream)
					.collect(Collectors.toList());			
		}
		else return new ArrayList<Tarea>();
	}
	
	private Optional<Empleado> buscarEmpleado(Predicate<Empleado> p){
		return this.empleados.stream().filter(p).findFirst();
	}

	public Double facturar() {
		this.guardarTareasTerminadasCSV();
		return this.empleados.stream()				
				.mapToDouble(e -> e.salario())
				.sum();
	}

	public List<Empleado> getEmpleados() {
		return empleados;
	}

	public void setEmpleados(List<Empleado> empleados) {
		this.empleados = empleados;
	}
	
	
	
	
}
