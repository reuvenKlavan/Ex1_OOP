package Ex1;

import java.io.IOException;

public class test_GUI {

	public static void main(String[] args) throws IOException {
		function test1 = new ComplexFunction("Times(x+1,Plus(x^2+5,x-3))");
		function test2 = new ComplexFunction("Times(Divid(x^2+6,x+3),Plus(x^2+5,x-3))");
		function test3 = new ComplexFunction("Plus(x+5,Min(x+5,x-3))");
		Functions_GUI tmp = new Functions_GUI();
		tmp.add(test1);
		tmp.add(test2);
		tmp.add(test3);
		//tmp.saveToFile("function.json");
		//tmp.initFromFile("function.json");
		Range rx = new Range(-10.0, 10.0);
		Range ry = new Range(-10.0, 10.0);
		int width = 2000;
		int height = 800;
		int resolution = 100; 
		tmp.drawFunctions(width, height, rx, ry, resolution);
	}

}
