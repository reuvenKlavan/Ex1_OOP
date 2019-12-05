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
		if(arg0 instanceof Monom || arg0 instanceof Polynom || arg0 instanceof ComplexFunction)
			return collect.contains(arg0);
		
		else
			return false;
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
		if(arg0 instanceof Monom || arg0 instanceof Polynom || arg0 instanceof ComplexFunction)
			return collect.remove(arg0);
		
		else
			return false;
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
		Gson gson = new Gson();
		String json = gson.toJson(file);
		System.out.println(json);
		saveToFile(json);
	}

	@Override
	public void saveToFile(String file) throws IOException {
		try 
		{
			PrintWriter pw = new PrintWriter(new File("function.json"));
			pw.write(file);
			pw.close();
		} 
		catch (FileNotFoundException e) 
		{
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
