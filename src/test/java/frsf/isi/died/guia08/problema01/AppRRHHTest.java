package frsf.isi.died.guia08.problema01;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import frsf.isi.died.guia08.problema01.modelo.Empleado;
import frsf.isi.died.guia08.problema01.modelo.Tarea;
import frsf.isi.died.guia08.problema01.modelo.Empleado.TareaNoExisteException;
import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;



public class AppRRHHTest {

	AppRRHH a;
	Empleado e, e1, e2, e3, e4;
	Tarea t;
	
	@Before
	public void init() {
		a = new AppRRHH();
	}
	
	@Test
	public void testCargarEmpleados() {
		
		a.agregarEmpleadoContratado(111, "Damian Lipschitz", 50d);
		a.agregarEmpleadoContratado(222, "Federico Yust", 10d);
		a.agregarEmpleadoEfectivo(123, "Federico Quijada", 20d);
		a.agregarEmpleadoEfectivo(321, "Roger Manticof", 50d);
		e=a.getEmpleados().get(0);
		assertTrue(e.getCuil()==111 && e.getNombre().equals("Damian Lipschitz") && e.getTipo()==Tipo.CONTRATADO && e.getCostoHora()==50);
		e=a.getEmpleados().get(1);
		assertTrue(e.getCuil()==222 && e.getNombre().equals("Federico Yust") && e.getTipo()==Tipo.CONTRATADO && e.getCostoHora()==10);
		e=a.getEmpleados().get(2);
		assertTrue(e.getCuil()==123 && e.getNombre().equals("Federico Quijada") && e.getTipo()==Tipo.EFECTIVO && e.getCostoHora()==20);
		e=a.getEmpleados().get(3);
		assertTrue(e.getCuil()==321 && e.getNombre().equals("Roger Manticof") && e.getTipo()==Tipo.EFECTIVO && e.getCostoHora()==50);
		
	}
	
	
	@Test
	public void testCargarEmpleadosCSV() {
		a.cargarEmpleadosContratadosCSV("src\\testContratados.csv");
//		a.cargarEmpleadosContratadosCSV("D:\\DAMIAN\\EclipseWorkspace\\eclipse-workspace\\guia08\\src\\test\\java\\frsf\\isi\\died\\guia08\\problema01\\testContratados.csv");
		e=a.getEmpleados().get(0);
		assertTrue(e.getCuil()==111 && e.getNombre().equals("Damian Lipschitz") && e.getTipo()==Tipo.CONTRATADO && e.getCostoHora()==50);
		e=a.getEmpleados().get(1);
		assertTrue(e.getCuil()==222 && e.getNombre().equals("Federico Yust") && e.getTipo()==Tipo.CONTRATADO && e.getCostoHora()==10);
//		a.cargarEmpleadosEfectivosCSV("D:\\DAMIAN\\EclipseWorkspace\\eclipse-workspace\\guia08\\src\\test\\java\\frsf\\isi\\died\\guia08\\problema01\\testEfectivos.csv");
		a.cargarEmpleadosEfectivosCSV("src\\testEfectivos.csv");
		e=a.getEmpleados().get(2);
		assertTrue(e.getCuil()==123 && e.getNombre().equals("Federico Quijada") && e.getTipo()==Tipo.EFECTIVO && e.getCostoHora()==20);
		e=a.getEmpleados().get(3);
		assertTrue(e.getCuil()==321 && e.getNombre().equals("Roger Manticof") && e.getTipo()==Tipo.EFECTIVO && e.getCostoHora()==50);
		
	}
	
	@Test
	public void testAsignarTarea() {
		a.agregarEmpleadoContratado(111, "Damian Lipschitz", 50d);
		a.agregarEmpleadoEfectivo(123, "Federico Quijada", 20d);
		a.asignarTarea(111, 555, "Sacar la basura", 1);
		a.asignarTarea(123, 666, "Limpiar el baño", 5);
		e=a.getEmpleados().get(0);
		assertTrue(e.getCuil()==111 && e.getNombre().equals("Damian Lipschitz") && e.getTipo()==Tipo.CONTRATADO && e.getCostoHora()==50);
		t=e.getTareasAsignadas().get(0);
		assertTrue((t.getId()==555) && t.getDescripcion().equals("Sacar la basura") && t.getDuracionEstimada()==1);
		e=a.getEmpleados().get(1);
		assertTrue(e.getCuil()==123 && e.getNombre().equals("Federico Quijada") && e.getTipo()==Tipo.EFECTIVO && e.getCostoHora()==20);
		t=e.getTareasAsignadas().get(0);
		assertTrue((t.getId()==666) && t.getDescripcion().equals("Limpiar el baño") && t.getDuracionEstimada()==5);
		
	}
	
	@Test
	public void testEmpezarTarea() {
		a.agregarEmpleadoContratado(111, "Damian Lipschitz", 50d);
		a.agregarEmpleadoEfectivo(123, "Federico Quijada", 20d);
		a.asignarTarea(111, 555, "Sacar la basura", 1);
		a.asignarTarea(123, 666, "Limpiar el baño", 5);
		a.empezarTarea(111, 555);
		a.empezarTarea(123, 666);
		e=a.getEmpleados().get(0);
		assertTrue(e.getCuil()==111 && e.getNombre().equals("Damian Lipschitz") && e.getTipo()==Tipo.CONTRATADO && e.getCostoHora()==50 );
		t=e.getTareasAsignadas().get(0);
		assertTrue((t.getId()==555) && t.getDescripcion().equals("Sacar la basura") && t.getDuracionEstimada()==1 && t.getFechaInicio().equals(LocalDateTime.now()));
		e=a.getEmpleados().get(1);
		assertTrue(e.getCuil()==123 && e.getNombre().equals("Federico Quijada") && e.getTipo()==Tipo.EFECTIVO && e.getCostoHora()==20);
		t=e.getTareasAsignadas().get(0);
		assertTrue((t.getId()==666) && t.getDescripcion().equals("Limpiar el baño") && t.getDuracionEstimada()==5 && t.getFechaInicio().equals(LocalDateTime.now()));
		
	}
	
	@Test
	public void testNoEmpezarTareaTareaNoExiste() {
		a.agregarEmpleadoContratado(111, "Damian Lipschitz", 50d);
		a.agregarEmpleadoEfectivo(123, "Federico Quijada", 20d);
		a.asignarTarea(111, 555, "Sacar la basura", 1);
		a.empezarTarea(111, 555);
		a.empezarTarea(123, 666);
		e=a.getEmpleados().get(0);
		assertTrue(e.getCuil()==111 && e.getNombre().equals("Damian Lipschitz") && e.getTipo()==Tipo.CONTRATADO && e.getCostoHora()==50 );
		t=e.getTareasAsignadas().get(0);
		assertTrue((t.getId()==555) && t.getDescripcion().equals("Sacar la basura") && t.getDuracionEstimada()==1 && t.getFechaInicio().equals(LocalDateTime.now()));
		e=a.getEmpleados().get(1);
		assertTrue(e.getCuil()==123 && e.getNombre().equals("Federico Quijada") && e.getTipo()==Tipo.EFECTIVO && e.getCostoHora()==20);
		assertTrue(e.getTareasAsignadas()==null);
		
	}
	
	@Test
	public void testTerminarTarea() {
		a.agregarEmpleadoContratado(111, "Damian Lipschitz", 50d);
		a.agregarEmpleadoEfectivo(123, "Federico Quijada", 20d);
		a.asignarTarea(111, 555, "Sacar la basura", 1);
		a.asignarTarea(123, 666, "Limpiar el baño", 5);
		a.empezarTarea(111, 555);
		a.empezarTarea(123, 666);
		e=a.getEmpleados().get(0);
		assertTrue(e.getCuil()==111 && e.getNombre().equals("Damian Lipschitz") && e.getTipo()==Tipo.CONTRATADO && e.getCostoHora()==50 );
		t=e.getTareasAsignadas().get(0);
		assertTrue((t.getId()==555) && t.getDescripcion().equals("Sacar la basura") && t.getDuracionEstimada()==1 && t.getFechaInicio().equals(LocalDateTime.now()));
		e=a.getEmpleados().get(1);
		assertTrue(e.getCuil()==123 && e.getNombre().equals("Federico Quijada") && e.getTipo()==Tipo.EFECTIVO && e.getCostoHora()==20);
		t=e.getTareasAsignadas().get(0);
		assertTrue((t.getId()==666) && t.getDescripcion().equals("Limpiar el baño") && t.getDuracionEstimada()==5 && t.getFechaInicio().equals(LocalDateTime.now()));
		
		a.terminarTarea(111, 555);
		a.terminarTarea(123, 666);
		assertTrue(t.getFechaFin().equals(LocalDateTime.now()));
		t = a.getEmpleados().get(0).getTareasAsignadas().get(0);
		assertTrue(t.getFechaFin().equals(LocalDateTime.now()));
		
	}
	
	@Test
	public void testNoTerminarTareaTareaNoExiste() {
		a.agregarEmpleadoContratado(111, "Damian Lipschitz", 50d);
		a.agregarEmpleadoEfectivo(123, "Federico Quijada", 20d);
		a.asignarTarea(111, 555, "Sacar la basura", 1);
		a.empezarTarea(111, 555);
		a.terminarTarea(123, 666);
		e=a.getEmpleados().get(0);
		assertTrue(e.getCuil()==111 && e.getNombre().equals("Damian Lipschitz") && e.getTipo()==Tipo.CONTRATADO && e.getCostoHora()==50 );
		t=e.getTareasAsignadas().get(0);
		assertTrue((t.getId()==555) && t.getDescripcion().equals("Sacar la basura") && t.getDuracionEstimada()==1 && t.getFechaInicio().equals(LocalDateTime.now()));
		e=a.getEmpleados().get(1);
		assertTrue(e.getCuil()==123 && e.getNombre().equals("Federico Quijada") && e.getTipo()==Tipo.EFECTIVO && e.getCostoHora()==20);
		assertTrue(e.getTareasAsignadas()==null);
		
	}
	
	@Test
	public void testCargarTareasCSV() {
		a.cargarEmpleadosContratadosCSV("src\\testContratados.csv");
		a.cargarEmpleadosEfectivosCSV("src\\testEfectivos.csv");
		a.cargarTareasCSV("src\\testTareasCSV.csv");

		e=a.getEmpleados().get(0);
		assertTrue(e.sosVos(111) && e.getNombre().equals("Damian Lipschitz") && e.getTipo()==Tipo.CONTRATADO && e.getCostoHora()==50);
		t=e.getTareasAsignadas().get(0);
		assertTrue((t.getId()==555) && t.getDescripcion().equals("Sacar la basura") && t.getDuracionEstimada()==1);
		t=e.getTareasAsignadas().get(1);
		assertTrue((t.getId()==777) && t.getDescripcion().equals("Lavar los platos") && t.getDuracionEstimada()==7);
		
		
		e=a.getEmpleados().get(1);
		assertTrue(e.sosVos(222) && e.getNombre().equals("Federico Yust") && e.getTipo()==Tipo.CONTRATADO && e.getCostoHora()==10);
		t=e.getTareasAsignadas().get(0);

		assertTrue((t.getId()==888) && t.getDescripcion().equals("Barrer el piso") && t.getDuracionEstimada()==9);
		
		
		e=a.getEmpleados().get(2);
		assertTrue(e.getCuil()==123 && e.getNombre().equals("Federico Quijada") && e.getTipo()==Tipo.EFECTIVO && e.getCostoHora()==20);
		t=e.getTareasAsignadas().get(0);
		assertTrue((t.getId()==666) && t.getDescripcion().equals("Limpiar el baño") && t.getDuracionEstimada()==5);
				
		
		e=a.getEmpleados().get(3);
		assertTrue(e.sosVos(321) && e.getNombre().equals("Roger Manticof") && e.getTipo()==Tipo.EFECTIVO && e.getCostoHora()==50);
		assertTrue(e.getTareasAsignadas()==null);
		
	}
	
	@Test
	public void testGuardarTareasTerminadasCSV() {
		a.cargarEmpleadosContratadosCSV("src\\testContratados.csv");
		a.cargarEmpleadosEfectivosCSV("src\\testEfectivos.csv");
		a.cargarTareasCSV("src\\testTareasCSV.csv");
		DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		Boolean falla=false, falla2=false;

		e=a.getEmpleados().get(0);
		try {
			e.comenzar(777, LocalDateTime.now().minusDays(2).format(f));
			e.comenzar(555, LocalDateTime.now().minusDays(2).format(f));
			e.finalizar(777);
			e.finalizar(555);
			e.buscar(555).setFacturada(true);
			e=a.getEmpleados().get(2);
			e.comenzar(666, LocalDateTime.now().minusDays(2).format(f));
			e.finalizar(666);
			e=a.getEmpleados().get(1);
			e.comenzar(888, LocalDateTime.now().minusDays(2).format(f));
		} catch (TareaNoExisteException e1) {
			falla=true;
		}
		
		a.guardarTareasTerminadasCSV();
		
		
		try(BufferedReader in = new BufferedReader(new FileReader("src\\tareas.csv"))){
			String fila = null;
				fila=in.readLine();
				String[] linea = fila.split(";");
				Integer idTarea = Integer.valueOf(linea[0]);
				String descripcion = linea[1];
				Integer duracionEstimada = Integer.valueOf(linea[2]);
				Integer cuil = Integer.valueOf(linea[3]);
				String nombre = linea[4];
				if(! ((idTarea==777 && descripcion.equals("Lavar los platos") && duracionEstimada==7 && cuil==111 && nombre.equals("Damian Lipschitz"))
						||
						(idTarea==666 && descripcion.equals("Limpiar el baño") && duracionEstimada==5 && cuil==123 && nombre.equals("Federico Quijada"))	)
				)
					falla2=true;
				fila=in.readLine();
				linea = fila.split(";");
				if(Integer.valueOf(linea[0])==idTarea) falla2=true;
				else {
					idTarea = Integer.valueOf(linea[0]);
					descripcion = linea[1];
					duracionEstimada = Integer.valueOf(linea[2]);
					cuil = Integer.valueOf(linea[3]);
					nombre = linea[4];
					if(! ((idTarea==777 && descripcion.equals("Lavar los platos") && duracionEstimada==7 && cuil==111 && nombre.equals("Damian Lipschitz"))
							||
							(idTarea==666 && descripcion.equals("Limpiar el baño") && duracionEstimada==5 && cuil==123 && nombre.equals("Federico Quijada"))	)
					)
						falla2=true;
				}
				
				
				
						assertFalse(falla);
						assertFalse(falla2);
		} catch (FileNotFoundException e) {
			falla=true;
		} catch (IOException e) {
			falla=true;
		}
		assertFalse(falla);

	}
	
}
