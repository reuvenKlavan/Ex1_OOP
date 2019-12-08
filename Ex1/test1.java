package Ex1;

public class test1 {

	public static void main(String[] args) {
		function test = new ComplexFunction("Times(x+1,Plus(x^2+5,x-3))");
		function test2 = test.copy();
		System.out.println(test2.f(-1));
	}

}
