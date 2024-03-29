package Ex1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.awt.Color;
import java.io.BufferedReader;
import com.google.gson.Gson;

public class Functions_GUI implements functions{
	
	private Collection<function> collect;
	public static Color[] Colors = {Color.blue, Color.cyan, Color.MAGENTA, Color.ORANGE, 
			Color.red, Color.GREEN, Color.PINK};
	
	public static Color[] Colors2 = {Color.BLACK, Color.GRAY};
	
	public Functions_GUI() {
		collect = new ArrayList<function>();
	}
	
	@Override
	public boolean add(function arg0) {
		return collect.add(arg0);
	}

	@Override
	public boolean addAll(Collection<? extends function> arg0) {
		return collect.addAll(arg0);
	}


	@Override
	public void clear() {
		collect.clear();
	}

	@Override
	public boolean contains(Object arg0) {
		return collect.contains(arg0);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		return collect.containsAll(arg0);
	}

	@Override
	public boolean isEmpty() {
		return collect.isEmpty();
	}

	@Override
	public Iterator<function> iterator() {
		return collect.iterator();
	}

	@Override
	public boolean remove(Object arg0) {
		return collect.remove(arg0);
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		return collect.removeAll(arg0);
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		return collect.retainAll(arg0);
	}

	@Override
	public int size() {
		return collect.size();
	}

	@Override
	public Object[] toArray() {
		return collect.toArray();
	}

	@Override
	public <T> T[] toArray(T[] arg0) { 
		return collect.toArray(arg0);
	}
	
	
	
	/**
	*for the collection of function we have in this file,
	*we read a function (a line) from the file and and sending the String to initFromString
	*and add to the collect (the private field in this class) 
	*@param the name of the file we gone to read
	*/
	@Override
	public void initFromFile(String file) throws IOException {
		
		String line="";
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(file));			
			while((line = reader.readLine())!= null) {
				String func = line.substring(0, line.length());
				function tmp = new ComplexFunction(func);
				collect.add(tmp);
			}			
		} 
		
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	
	
	
	/**
	*for the collection of function we have in this private field in the class,
	*we save them into a file while for every function we moving for the next line in the file
	*@param the name of the file we gone to create 
	*/
	@Override
	public void saveToFile(String file) throws IOException {
		Iterator<function> iter = iterator();
		StringBuilder stf = new StringBuilder();
		while(iter.hasNext()) {//we pass on every function in collect and add it to the StringBuilder
			function next = iter.next();
			stf.append(next.toString());
			if(iter.hasNext()) {
				stf.append("\n");
			}	
		}
		
		try{
			
			PrintWriter pw = new PrintWriter(new File(file));//create the file it self
			pw.write(stf.toString());
			pw.close();
		} 
		
		catch (FileNotFoundException e){
			e.printStackTrace();
			return;
		}
	}
	/**
	* create a gui window and draw the function for the current moment in private method collect
	* @param width of the width of the gui window
	* @param height of the height of the gui window
	* @param rx the range of the x axis
	* @param ry the range of the y axis
	* @param resolution to determine the x step
	* @return create gui a window that print the function in collect our private method 
	*/
	@Override
	public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) {
		if (width<=0) 
			throw new IllegalArgumentException("width of gui must to be positive");
		
		if (height<=0)
			throw new IllegalArgumentException("hight of gui must to be positive");
		
		if(rx.get_min() >= rx.get_max())
			throw new IllegalArgumentException("the min of range could not bigger or equal to the max of the range");

		if(ry.get_min() >= ry.get_max())
			throw new IllegalArgumentException("the min of range could not bigger or equal to the max of the range");
		
		if(resolution<=0)
			throw new IllegalArgumentException("resolution must to be positive");
		
		
		StdDraw.setCanvasSize(width, height);//create a new gui window using by width and height 
		double[] x = new double[resolution+1];//the x coordinates we are gone to compute the f(x)
		double[][] yy = new double[collect.size()][resolution+1];//saving for each function there f(x)
		double x_step = (rx.get_max()-rx.get_min())/resolution;//the "step" on the x axis
		double x0 = rx.get_min();
		for (int i=0; i<=resolution; i++) {//step on the x axis
			x[i] = x0;
			for(int a=0;a<collect.size();a++) {//compute the f(x) for the a index function
				if(((ArrayList<function>) collect).get(a) instanceof ComplexFunction) {//if you are complex function
					if(((ComplexFunction) ((ArrayList<function>) collect).get(a)).getOp() != Operation.Divid) {//if your operation isn't divide
						yy[a][i] = ((ArrayList<function>) collect).get(a).f(x[i]);
					}
					
					else {
						if(((ComplexFunction) ((ArrayList<function>) collect).get(a)).right().f(x0)!=0) {//if we divide by a value that different from zero
							yy[a][i] = ((ArrayList<function>) collect).get(a).f(x[i]);
						}
						
						else {//right.f(x0)=0 and Operator = divide that mean asymptotic
								yy[a][i] = Double.MAX_VALUE;
							
						
						}
					}
				}
				
				else {//if you polynom or monom 
					yy[a][i] = ((ArrayList<function>) collect).get(a).f(x[i]);
				}
			}
			x0+=x_step;
		}
		StdDraw.setXscale(rx.get_min(), rx.get_max());
		StdDraw.setYscale(ry.get_min(), ry.get_max());
		
		
		double min=0, max=0;// we done it so the gui window will look better
		
		
		if(rx.get_min() < ry.get_min()) {
			 min = rx.get_min();
		}
		
		else {
			 min = ry.get_min();
		}
		
		if(rx.get_max() < ry.get_max()) {
			 max = ry.get_max();
		}
		
		else {
			 max = rx.get_max();
		}
		
		
		StdDraw.setPenRadius(0.001);//adding coordinate to the gui window
		StdDraw.setPenColor(Colors2[1]);//grey color
		for (double i = min;i < max; i++) {
			Integer drawYAxis = (int)i;
			StdDraw.line(min,drawYAxis , max, drawYAxis);//the line it self
			StdDraw.text(-0.5, i, drawYAxis.toString());//number of coordinate 
		}
		
		for (double i = min; i < max; i++) {//same as the last loop but parallel to the x axis
			Integer drawXAxis = (int)i;
			StdDraw.line(drawXAxis, min , drawXAxis ,max);
			StdDraw.text(i, -0.5, drawXAxis.toString());
		}
		
		
		
		StdDraw.setPenRadius(0.006);//adding the main x and y axis
		StdDraw.setPenColor(Colors2[0]);
		StdDraw.line(rx.get_min(), 0.0, rx.get_max(), 0.0); // X axis
		StdDraw.line(0.0, ry.get_min(), 0.0, ry.get_max()); // y axis
		
		
		
		StdDraw.setPenRadius(0.003);
		
		for(int a=0;a<collect.size();a++) {
			int c = a%Colors.length;
			StdDraw.setPenColor(Colors[c]);
			
			
			System.out.println(a+") "+Colors[a]+"  f(x)= "+((ArrayList<function>) collect).get(a));
			for (int i = 0; i < resolution; i++) {//the x step
				StdDraw.line(x[i], yy[a][i], x[i+1], yy[a][i+1]);//draw the function
			}
		}	
	}
	
	/**
	 * we convert a line from json file to a String and we use to get the variable
	 * for creating a gui window and print the function in this window by sending those
	 * variable to the other method of drawFunction  
	 * @param json_file the file we generate from the variable for gui window
	 */
	@Override
	public void drawFunctions(String json_file) {
		String line="";
		int i=0;
		int[] variable = {-1,-1,-1,-1,-1,-1,-1};
		
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(json_file));
			while((line = reader.readLine())!= null && i<=5) {
				if(line.indexOf('{')==-1 && line.indexOf('}')==-1) {//in the start and end of json file there is { or } what mean we can skip this line 	
					int indexOfColon = line.indexOf(':');
					int indexOfCumma = line.indexOf(',');
					int indexOfRange = line.indexOf('[');
					int indexOfRangeEnd = line.indexOf(']');
					
					if(line.contains("Width") && variable[0]==-1) {
						String var = line.substring(indexOfColon+1, indexOfCumma);
						variable[0] = Integer.parseInt(var);
						i++;
					}
					
					else if(line.contains("Height") && variable[1]==-1) {
						String var = line.substring(indexOfColon+1, indexOfCumma);
						variable[1] = Integer.parseInt(var);
						i++;
					}
					
					else if(line.contains("Resolution") && variable[2]==-1) {
						String var = line.substring(indexOfColon+1, indexOfCumma);
						variable[2] = Integer.parseInt(var);
						i++;
					}
				
					else if(line.contains("Range_X")) {
						String varLeft = line.substring(indexOfRange+1, indexOfCumma);
						variable[3] = Integer.parseInt(varLeft);
						i++;
						String varRight = line.substring(indexOfCumma+1, indexOfRangeEnd);
						variable[4] = Integer.parseInt(varRight);
						i++;
					}
					
					else if( line.contains("Range_Y")) {
						String varLeft = line.substring(indexOfRange+1, indexOfCumma);
						variable[5] = Integer.parseInt(varLeft);
						i++;
						String varRight = line.substring(indexOfCumma+1, indexOfRangeEnd);
						variable[6] = Integer.parseInt(varRight);
						i++;
					}
					
					else {
						throw new IllegalArgumentException("one of the lines in the json is not like in the given format");
					}
				}
			}
		}	
				
		 catch (IOException e) {
				e.printStackTrace();
				System.out.println(e);
		}
		
		Range rx = new Range(variable[3], variable[4]);
		Range ry = new Range(variable[5], variable[6]);
		
		drawFunctions(variable[0], variable[1], rx, ry, variable[2]);
		
	}

}
