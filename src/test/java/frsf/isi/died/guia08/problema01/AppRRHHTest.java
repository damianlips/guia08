package frsf.isi.died.guia08.problema01;

import static org.junit.Assert.*;

import java.io.File;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import frsf.isi.died.guia08.problema01.modelo.Empleado;
import frsf.isi.died.guia08.problema01.modelo.Tarea;
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
	public void cargarTareasCSV() {
		a.cargarEmpleadosContratadosCSV("src\\testContratados.csv");
		a.cargarEmpleadosEfectivosCSV("src\\testEfectivos.csv");
		a.cargarTareasCSV("src\\testTareasCSV.csv");
		
		for(Empleado e : a.getEmpleados()) {
			System.out.println(e);
			if(e.getTareasAsignadas()==null) System.out.println(e + " no tiene tareas asignadas");
			else {
				for(Tarea t: e.getTareasAsignadas()) {
					System.out.println(t.getDescripcion());
				}
			}
		}
		
		e=a.getEmpleados().get(0);
		assertTrue(e.sosVos(111) && e.getNombre().equals("Damian Lipschitz") && e.getTipo()==Tipo.CONTRATADO && e.getCostoHora()==50);
		t=e.getTareasAsignadas().get(0);
		assertTrue((t.getId()==555) && t.getDescripcion().equals("Sacar la basura") && t.getDuracionEstimada()==1);
		t=e.getTareasAsignadas().get(1);
		assertTrue((t.getId()==777) && t.getDescripcion().equals("Lavar los platos") && t.getDuracionEstimada()==7);
		
		
		e=a.getEmpleados().get(1);
//		assertTrue(e.getCuil()==222 && e.getNombre().equals("Federico Yust") && e.getTipo()==Tipo.CONTRATADO && e.getCostoHora()==10);
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
	/*
	@Test
	public void javaNoSirve() {
		a.cargarEmpleadosContratadosCSV("src\\testContratados.csv");
		e=a.getEmpleados().get(1);
		assertTrue(e.getCuil()==222);
		assertFalse(e.sosVos(222));
		
	}
	*/
}
