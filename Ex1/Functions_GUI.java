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
			Color.red, Color.GREEN, Color.PINK, Color.BLACK, Color.GRAY};
	
	
	
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
			Gson gson = new Gson();
			
			while((line = reader.readLine())!= null) {
				String func = line.substring(1, line.length()-1);
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
		Gson gson = new Gson();
		StringBuilder json = new StringBuilder();
		while(iter.hasNext()) {
			function next = iter.next();
			json.append(gson.toJson(next.toString()));
			if(iter.hasNext()) {	
				json.append("\n");
			}	
		}
		
		try{
			
			PrintWriter pw = new PrintWriter(new File(file));
			pw.write(json.toString());
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
		
		StdDraw.setPenRadius(0.006);
		StdDraw.setPenColor(Colors[7]);
		StdDraw.line(rx.get_min(), 0.0, rx.get_max(), 0.0); // X axis
		StdDraw.line(0.0, ry.get_min(), 0.0, ry.get_max()); // y axis
		
		StdDraw.setPenRadius(0.001);
		StdDraw.setPenColor(Colors[8]);
		for (double i = ry.get_min();i < ry.get_max(); i ++) {
			StdDraw.line(i, ry.get_min(), i, ry.get_max());
		}
		
		for (double i = rx.get_min(); i < rx.get_max(); i ++) {
			StdDraw.line(rx.get_min(), i, rx.get_max(), i);
		}
		
		StdDraw.setPenRadius(0.003);
		// plot the approximation to the function
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
		
		
	}

}
