package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Ex1.ComplexFunction;
import Ex1.Monom;
import Ex1.Operation;
import Ex1.Polynom;
import Ex1.function;

public class TestComplexFunction {
	
	@Test
	void fx1() {
		function t1 = new ComplexFunction("Comp(3x-2x^4,1-x^3)");
		double x = 2;
		double fx = t1.f(x);
		assertEquals(-4823,fx);
	}
	@Test
	void copy() {
		function t2 = new ComplexFunction("Plus(Divid(3x+5,Min(2x,3x)),Times(4x+1x^3,-3x-4x^2))");
		function t3 = t2.copy();
		assertNotSame(t2,t3);
	}
	
	@Test 
	void deepCopy(){
		function t2 = new ComplexFunction("Plus(Divid(3x+5,Min(2x,3x)),Times(4x+1x^3,-3x-4x^2))");
		function t3 = t2.copy();
		assertEquals(t2,t3);
	}
	
	@Test
	void initFromString1() {
		function t4 = new ComplexFunction("None(3x+5)");
		boolean equal = t4.toString().equals("None(5.0 + 3.0x)");
		assertEquals(true, equal);
	}
	
	@Test
	void initFromString2() {
		function t5 = new ComplexFunction("Divid(Min(3x+5,1-7x),Max(x^2+5,x-5x^1))");
		boolean equal = t5.toString().equals("Divid(Min(5.0 + 3.0x,1.0 + -7.0x),Max(5.0 + 1.0x^2,-4.0x))");
		assertEquals(true,equal);
	}
	
	@Test 
	void fx2() {
		Polynom<Monom> poly1 = new Polynom<>("10-x^2+1.5x+2.25x^3");
		Polynom<Monom> poly2 = new Polynom<>("-10+x-1.2X^2+x^3");
		ComplexFunction cf1 = new ComplexFunction(Operation.Comp,poly1, poly2);//left.f(x^3-1.2x^2+x-10)
		Monom m1 = new Monom(-2.32, 2);
		Monom m2 = new Monom(1.5, 4);
		ComplexFunction cf2 = new ComplexFunction(Operation.Plus,m1,m2);//cf2 == -2.32x^2+1.5x^4
		ComplexFunction cf3 = new ComplexFunction("Times(x^3+x-X^2+1.56,x^2-x)");//(x^3-x^+x+1.56)(x^2-x+) 
		cf2.div(cf3);
		cf1.mul(cf2);
		double output = cf1.f(0.1);
		output = (double)((int)(output*10000))/10000;
		assertEquals(-355.7869,output);
	}
	
	@Test
	void DeepCopy() {
		Polynom<Monom> poly1 = new Polynom<>("10-x^2+1.5x+2.25x^3");
		Polynom<Monom> poly2 = new Polynom<>("-10+x-1.2X^2+x^3");
		ComplexFunction cf1 = new ComplexFunction(Operation.Comp,poly1, poly2);
		Monom m1 = new Monom(-2.32, 2);
		Monom m2 = new Monom(1.5, 4);
		ComplexFunction cf2 = new ComplexFunction(Operation.Plus,m1,m2);
		ComplexFunction cf3 = new ComplexFunction("Times(x^3+x-X^2+1.56,x^2-x)");
		cf2.div(cf3);
		cf1.mul(cf2);
		function cf4 = cf1.copy();
		Monom tmp1 = new Monom("0");
		Monom tmp2 = new Monom("1");
		cf1.plus(tmp1);
		cf1.mul(tmp2);
		assertEquals(cf1,cf4);// maybe we add monoms but I add a netural monom to multiply and add
	}
	
	
	@Test
	void divideWithZeroMone() {
		function f1 = new ComplexFunction("div(x-3,X^2-5)");
		double output = f1.f(3);
		assertEquals(0,output);
	}
	
	@Test
	void divideByZero() {
		function f1 = new ComplexFunction("div(X^2-5,x-3)");
		double output = f1.f(3);
		fail("you try to divide by 0");
	}
	
	@Test 
	void Equals_Between_None_To_Regular_Polynom() {
		function poly = new Polynom<>("3x+12x^3-10.5x^4+1.5x^2-13");
		function complex = new ComplexFunction("none(-10.5x^4+12x^3+1.5x^2+3x-13)");
		boolean check = complex.equals(poly);
		assertEquals(true,check);
	}
}







