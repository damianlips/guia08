package frsf.isi.died.guia08.problema01.modelo;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import frsf.isi.died.guia08.problema01.modelo.Empleado.TareaNoExisteException;
import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;

//@Ignore
public class EmpleadoTest {

	// IMPORTANTE
	// ESTA CLASE ESTA ANOTADA COMO @IGNORE por lo que no ejecutará ningun test
	// hasta que no borre esa anotación.
	
	Tarea t, t1, t2,t3,t4,t5;
	Empleado e1, e2;
	
	@Before
	public void init() {
		t= new Tarea (123, "", 3);
		t1= new Tarea (234, "", 5);
		t2= new Tarea (345, "", 8);
		t3= new Tarea (456, "", 4);
		t4= new Tarea (567, "", 12);
		t5= new Tarea (678, "", 7);
		
		e1= new Empleado(111, "Damian Lipschitz", Tipo.CONTRATADO, 50d);
		e2= new Empleado(222, "Federico Yust", Tipo.EFECTIVO, 10d);
	}
	
	
	
	@Test
	public void testSalario() {
		Boolean falla= false;
		e1.asignarTarea(t);
		e1.asignarTarea(t1);
		e1.asignarTarea(t2);
		e1.asignarTarea(t3);
		try {
			e1.comenzar(123);
			e1.comenzar(234);
			e1.comenzar(345);
			e1.comenzar(456);
			e1.finalizar(123);
			e1.finalizar(234);
			e1.finalizar(345);
			e1.asignarTarea(t4);
		} catch (TareaNoExisteException e) {
			falla=true;
		}
		t1.setFacturada(true);
		
		assertTrue(  
				(e1.salario()== (e1.costoTarea(t2)+ e1.costoTarea(t) ) ) &&
				t2.getFacturada() && t.getFacturada() && !falla
				);
		
	}

	@Test
	public void testCostoTareaTerminadaContratado() {
		e1.asignarTarea(t2);
		t2.setFechaInicio(LocalDateTime.now());
		t2.setFechaFin(LocalDateTime.now().plusDays(2));
		Double costo = e1.costoTarea(t2);
		assertTrue( costo == 400d);
		
	}
	@Test
	public void testCostoTareaTerminadaContratadoAntesDeTiempo() {
		e1.asignarTarea(t2);
		t2.setFechaInicio(LocalDateTime.now());
		t2.setFechaFin(LocalDateTime.now().plusHours(1));
		Double costo = e1.costoTarea(t2);
		assertTrue( costo == 520d);
	}
	@Test
	public void testCostoTareaTerminadaContratadoTarde() {
		e1.asignarTarea(t2);
		t2.setFechaInicio(LocalDateTime.now());
		t2.setFechaFin(LocalDateTime.now().plusHours(97));
		Double costo = e1.costoTarea(t2);
		assertTrue( costo == 300d);
	}
	
	@Test
	public void testCostoTareaTerminadaEfectivoAntesDeTiempo() {
		e2.asignarTarea(t2);
		t2.setFechaInicio(LocalDateTime.now());
		t2.setFechaFin(LocalDateTime.now().plusHours(10));
		Double costo = e2.costoTarea(t2);
		assertTrue( costo == 96d);
	}
	
	@Test
	public void testCostoTareaTerminadaEfectivo() {
		e2.asignarTarea(t2);
		t2.setFechaInicio(LocalDateTime.now());
		t2.setFechaFin(LocalDateTime.now().plusHours(97));
		Double costo = e2.costoTarea(t2);
		assertTrue( costo == 80d);
	}
	
	@Test
	public void testCostoTareaNoTerminada() {
		e1.asignarTarea(t2);
		t2.setFechaInicio(LocalDateTime.now());
		Double costo = e1.costoTarea(t2);
		assertTrue( costo == 400d);
		
		
		e2.asignarTarea(t);
		t.setFechaInicio(LocalDateTime.now());
		costo = e2.costoTarea(t);
		assertTrue( costo == 30d);
	}
	@Test
	public void testCostoTareaNoAsignada() {
		e1.asignarTarea(t);
		t2.setFechaInicio(LocalDateTime.now());
		Double costo = e1.costoTarea(t2);
		assertTrue( costo == 400d);
		
		
		e2.asignarTarea(t2);
		t.setFechaInicio(LocalDateTime.now());
		costo = e2.costoTarea(t);
		assertTrue( costo == 30d);
	}
	
	

	@Test
	public void testNoAsignarTareaConEmpleadoOTerminada() {
		Boolean b1, b2, b3, b4,b5;
		b1=e1.asignarTarea(t);
		b2=e2.asignarTarea(t);
		try {
			b3=true;
			e1.comenzar(123);
			e1.finalizar(123);
		} catch (TareaNoExisteException e) {
			b3=false;
		}
		b4=e1.asignarTarea(t);
		b5=e2.asignarTarea(t);
		assertTrue(b1&&b3&&!(b2||b4||b5));
	}
	
	@Test
	public void testNoAsignarTareaRepetida() {
		e1.asignarTarea(t);
		e1.asignarTarea(t);
		e2.asignarTarea(t2);
		e2.asignarTarea(t2);
		
		assertTrue(e1.tar().size()==1 && e2.tar().size()==1);
	}
	
	@Test
	public void testAsignarTareaContratado() {
		Boolean b, b1, b2, b3, b4,b5;
		b=e1.asignarTarea(t);
		b1=e1.asignarTarea(t1);
		b2=e1.asignarTarea(t2);
		b3=e1.asignarTarea(t3);
		b4=e1.asignarTarea(t4);
		b5=e1.asignarTarea(t5);		
		assertTrue(b&&b1&&b2&&b3&&b4&&!b5);
		try {
			b1=true;
			e1.comenzar(123);
			e1.finalizar(123);
		} catch (TareaNoExisteException e) {
			b1=false;
		}
		b5=e1.asignarTarea(t5);
		assertTrue(b5 && b1);
	}
	
	
	@Test
	public void testAsignarTareaEfectivo() {
		Boolean b, b1, b2, b3;
		b=e2.asignarTarea(t);
		b1=e2.asignarTarea(t1);
		b3=e2.asignarTarea(t3);
		b2=e2.asignarTarea(t2);
		assertTrue(b&&b1&&(!b2)&&b3);
		try {
			b1=true;
			e2.comenzar(234);
			e2.finalizar(234);
		} catch (TareaNoExisteException e) {
			b1=false;
		}
		b2=e1.asignarTarea(t2);
		assertTrue(b2);
		assertTrue(b1);
	}

	@Test
	public void testComenzarInteger() {
		e1.asignarTarea(t);
		try {
			e1.comenzar(123);
		} catch (TareaNoExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(t.getFechaInicio() , LocalDateTime.now());
	}

	@Test
	public void testFinalizarInteger() {
		e1.asignarTarea(t);
		try {
			e1.comenzar(123);
			e1.finalizar(123);
		} catch (TareaNoExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(t.getFechaFin() , LocalDateTime.now());
	}
	
	@Test
	public void testFinalizarIntegerNoExiste() {
		Boolean falla=false;
		try {
			e1.finalizar(1);
		} catch (TareaNoExisteException e) {
			falla=true;
		}
		assertTrue(falla);
	}
	
	@Test
	public void testComenzarIntegerNoExiste() {
		Boolean falla=false;
		try {
			e1.comenzar(1);
		} catch (TareaNoExisteException e) {
			falla=true;
		}
		assertTrue(falla);
	}

	@Test
	public void testComenzarIntegerString() {
		e1.asignarTarea(t);
		try {
			e1.comenzar(123, "05-08-2020 03:05");
		} catch (TareaNoExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(t.getFechaInicio() , LocalDateTime.of(2020, 8, 5, 3, 5));
	}
	
	@Test
	public void testComenzarIntegerStringNoExiste() {
		Boolean falla=false;
		try {
			e1.comenzar(1,"05-08-2020 03:05");
		} catch (TareaNoExisteException e) {
			falla=true;
		}
		assertTrue(falla);
	}

	@Test
	public void testFinalizarIntegerStringNoExiste() {
		Boolean falla=false;
		try {
			e1.finalizar(1,"05-08-2020 03:05");
		} catch (TareaNoExisteException e) {
			falla=true;
		}
		assertTrue(falla);
	}
	
	@Test
	public void testFinalizarIntegerString() {
		e1.asignarTarea(t);
		try {
			e1.comenzar(123);
			e1.finalizar(123,"05-08-2020 03:05");
		} catch (TareaNoExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(t.getFechaFin() , LocalDateTime.of(2020, 8, 5, 3, 5));
	}

}
