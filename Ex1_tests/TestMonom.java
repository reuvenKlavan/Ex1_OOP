package Ex1_tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Ex1.Monom;

class TestMonom {

	@Test
	void add() {
		Monom m1 = new Monom("3.528x^3");
		Monom m2 = new Monom("2.472x^3");
		m1.add(m2);
		assertEquals("6.0x^3",m1.toString());
	}
	
	void multipy() {
		Monom m1 = new Monom("3.1x^3");
		Monom m2 = new Monom("2.2x^2");
		Monom m3 = new Monom("0.18x^5");
		m1.multipy(m2);
		m1.add(m3);
		assertEquals("7.0x^5",m1.toString());
	}
	
	void copy() {
		Monom m1 = new Monom("3.51x^4");
		Monom m2 = (Monom) m1.copy();
		assertEquals(m1.toString(),m2.toString());
	}

}
