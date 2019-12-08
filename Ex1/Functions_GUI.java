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

	@Override
	public void initFromFile(String file) throws IOException {
		
		String line="";
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(file));			
			while((line = reader.readLine())!= null) {
				String func = line.substring(0, line.length()-1);
				function tmp = new ComplexFunction(func);
				collect.add(tmp);
			}			
		} 
		
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	
	
	
	

	@Override
	public void saveToFile(String file) throws IOException {
		Iterator<function> iter = iterator();
		StringBuilder stf = new StringBuilder();
		while(iter.hasNext()) {
			function next = iter.next();
			stf.append(next.toString());
			if(iter.hasNext()) {
				stf.append("\n");
			}	
		}
		
		try{
			
			PrintWriter pw = new PrintWriter(new File(file));
			pw.write(stf.toString());
			pw.close();
		} 
		
		catch (FileNotFoundException e){
			e.printStackTrace();
			return;
		}
	}

	@Override
	public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) {
		if (width<=0) 
			throw new IllegalArgumentException();
		
		if (height<=0)
			throw new IllegalArgumentException();
		
		if(rx.get_min() >= rx.get_max())
			throw new IllegalArgumentException();

		if(ry.get_min() >= ry.get_max())
			throw new IllegalArgumentException();
		
		
		
		StdDraw.setCanvasSize(width, height);
		double[] x = new double[resolution+1];
		double[][] yy = new double[collect.size()][resolution+1];
		double x_step = (rx.get_max()-rx.get_min())/resolution;
		double x0 = rx.get_min();
		for (int i=0; i<=resolution; i++) {
			x[i] = x0;
			for(int a=0;a<collect.size();a++) {
				yy[a][i] = ((ArrayList<function>) collect).get(a).f(x[i]);
			}
			x0+=x_step;
		}
		StdDraw.setXscale(rx.get_min(), rx.get_max());
		StdDraw.setYscale(ry.get_min(), ry.get_max());
		
		
		double min=0, max=0;
		
		
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
		
		
		StdDraw.setPenRadius(0.001);
		StdDraw.setPenColor(Colors2[1]);
		for (double i = min;i < max; i++) {
			Integer drawYAxis = (int)i;
			StdDraw.line(min,drawYAxis , max, drawYAxis);
			StdDraw.text(-0.5, i, drawYAxis.toString());
		}
		
		for (double i = min; i < max; i++) {
			Integer drawXAxis = (int)i;
			StdDraw.line(drawXAxis, min , drawXAxis ,max);
			StdDraw.text(i, -0.5, drawXAxis.toString());
		}
		
		
		
		StdDraw.setPenRadius(0.006);
		StdDraw.setPenColor(Colors2[0]);
		StdDraw.line(rx.get_min(), 0.0, rx.get_max(), 0.0); // X axis
		StdDraw.line(0.0, ry.get_min(), 0.0, ry.get_max()); // y axis
		
		
		
		StdDraw.setPenRadius(0.003);
		
		for(int a=0;a<collect.size();a++) {
			int c = a%Colors.length;
			StdDraw.setPenColor(Colors[c]);
			
			
			System.out.println(a+") "+Colors[a]+"  f(x)= "+((ArrayList<function>) collect).get(a));
			for (int i = 0; i < resolution; i++) {
				StdDraw.line(x[i], yy[a][i], x[i+1], yy[a][i+1]);
			}
		}	
	}
	

	@Override
	public void drawFunctions(String json_file) {
		String line="";
		int i=0;
		int[] variable = new int[7];
		
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(json_file));
			while((line = reader.readLine())!= null && i<=5) {
				if(line.indexOf('{')==-1 && line.indexOf('}')==-1) {	
					int indexOfColon = line.indexOf(':');
					int indexOfCumma = line.indexOf(',');
					int indexOfRange = line.indexOf('[');
					int indexOfRangeEnd = line.indexOf(']');
					if(i<3) {
						String var = line.substring(indexOfColon+1, indexOfCumma);
						variable[i] = Integer.parseInt(var);
						i++;
					}
				
					else if(i==3 || i==5) {
						String varLeft = line.substring(indexOfRange+1, indexOfCumma);
						variable[i] = Integer.parseInt(varLeft);
						i++;
						String varRight = line.substring(indexOfCumma+1, indexOfRangeEnd);
						variable[i] = Integer.parseInt(varRight);
						i++;
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
