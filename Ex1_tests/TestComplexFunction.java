package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Ex1.ComplexFunction;
import Ex1.Polynom;
import Ex1.function;

public class TestComplexFunction {
	
	@Test
	void fx() {
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
}
