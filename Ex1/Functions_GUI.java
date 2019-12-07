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
import java.io.BufferedReader;
import com.google.gson.Gson;

public class Functions_GUI implements functions{
	
	private Collection<function> collect;
	
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
				String func = line.substring(1, line.length()-2);
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
				json.append(",");
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
		
	}

	@Override
	public void drawFunctions(String json_file) {
		
		
	}

}
