package frsf.isi.died.guia08.problema01.modelo;

import static org.junit.Assert.*;
import java.time.Duration;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;
import frsf.isi.died.guia08.problema01.modelo.Tarea.EmpleadoAsignadoException;
import frsf.isi.died.guia08.problema01.modelo.Tarea.TareaFinalizadaException;


public class TareaTest {
	//Integer cuil, String nombre, Tipo tipo, Double costoHora
	Tarea t, t1;
	Empleado e1, e2;
	@Before
	public void init() {
		t= new Tarea (123, "", 3);
		t1= new Tarea (234, "", 5);
		e1= new Empleado(111, "Damian Lipschitz", Tipo.CONTRATADO, 50d);
		e2= new Empleado(222, "Federico Yust", Tipo.EFECTIVO, 10d);
	}
	
	@Test
	public void sosVosTest() {
		assertTrue(t.sosVos(123));
		assertTrue(t1.sosVos(234));
	}
	

	@Test
	public void asignarEmpleadoTest() {
		Boolean falla=false;
		try {
			t.asignarEmpleado(e1);
		} catch (EmpleadoAsignadoException e) {
			falla=true;
		} catch (TareaFinalizadaException e) {
			falla=true;
		}
		assertEquals(t.getEmpleadoAsignado(), e1);
		assertFalse(falla);
	}
	
	@Test
	public void noAsignarEmpleadoConAsignadoTest() {
		Boolean falla=false;
		try {
			t.asignarEmpleado(e1);
			t.asignarEmpleado(e2);
		} catch (EmpleadoAsignadoException e) {
			falla=true;
		} catch (TareaFinalizadaException e) {
		}
		assertEquals(t.getEmpleadoAsignado(), e1);
		assertTrue(falla);
	}
	
	
	@Test
	public void noAsignarEmpleadoConFinalizadaTest() {
		Boolean falla=false;
		try {
			t.asignarEmpleado(e2);
			t.setFechaFin(LocalDateTime.now());
			t.asignarEmpleado(e1);
		} catch (EmpleadoAsignadoException e) {
		} catch (TareaFinalizadaException e) {
			falla=true;
		}
		assertEquals(t.getEmpleadoAsignado(), e2);
		assertTrue(t.terminada());
		assertTrue(falla);
	}
	
	

}
