package Ex1_tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Ex1.Monom;
import Ex1.Polynom;
import Ex1.Polynom_able;

class TestPolynom {

	@Test
	void fxTest() {
		Polynom<Monom> poly = new Polynom<>("3x+2-4x^2");
		double output = poly.f(3);
		assertEquals(output,-25);
		
	}
	@Test
	void addTest() {
		Polynom<Monom> p1 = new Polynom<>("x^5-3x^2+4x-7");
		Polynom<Monom> p2 = new Polynom<>("x^4+3x^2-x^5+x^2");
		p1.add(p2);
		assertEquals("-7.0 + 4.0x + 1.0x^2 + 1.0x^4", p1.toString());
	}
	@Test
	void sustructTest() {
		Polynom<Monom> p1 = new Polynom<>("3x^5-3x^3+2x^4-7");
		Polynom<Monom> p2 = new Polynom<>("x^4+3x^2-x^5+x^2");
		p1.substract(p2);
		double output = p1.f(2);
		assertEquals(97.0, output);
	}
	
	@Test 
	void multiply() {
		Polynom<Monom> p1 = new Polynom<>("3x^2+5x");
		Polynom<Monom> p2 = new Polynom<>("x^2-4x+7");
		p1.multiply(p2);
		assertEquals("35.0x + 1.0x^2 + -7.0x^3 + 3.0x^4", p1.toString());
		
	}
	@Test
	void root() {
		Polynom<Monom> p1 = new Polynom<>("x^2-9");
		double root = p1.root(-5, 0, 0.001);
		root = Math.floor(root);
		assertEquals(-3,root);
		
	}
	@Test
	void area() {
		Polynom<Monom> p1 = new Polynom<>("x^2+3x-9");
		double area = p1.area(3, 6, 0.001);
		area = (double) (Math.floor(area * 100)) / (100);
		assertEquals(76.48,area);
	}
	
	@Test
	void derivative() {
		Polynom<Monom> p1 = new Polynom<>("3.25x^4+3.5x^2-2.2x^5+x^2");
		Polynom_able der = p1.derivative();
		assertEquals("9.0x + 13.0x^3 + -11.0x^4" , der.toString());
	}
	@Test
	void copy() {
		Polynom<Monom> p1 = new Polynom<>("3.25x^4+3.5x^2-2.2x^5+x^2");
		Polynom_able clone = p1.copy();
		assertEquals(p1.toString(),clone.toString());
	}

}
