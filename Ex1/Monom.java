package Ex1;


import java.util.Comparator;

import javax.management.RuntimeErrorException;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function{
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}
	
	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}
	
	public double get_coefficient() {
		return this._coefficient;
	}
	public int get_power() {
		return this._power;
	}
	/** 
	 * this method returns the derivative monom of this.
	 * @return
	 */
	public Monom derivative() {
		if(this.get_power()==0) {return getNewZeroMonom();}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}
	public double f(double x) {
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	} 
	public boolean isZero() {return this.get_coefficient() == 0;}
	// ***************** add your code below **********************
	public Monom(String s) {
		boolean foundCoe= false;
		
		
		if(s==null) {
			throw new IllegalArgumentException("not valid String");
		}
		int indexOfPower = s.indexOf('^');
		int indexOfX = s.indexOf('x');
		
		if(s=="0" || s=="0.0") {
			this.set_coefficient(0);
			this.set_power(0);
		}
		
		if(s.charAt(0)=='x') {
			this.set_coefficient(1);
		}
		
		else {
			if(indexOfX==-1) {
				double a = Double.parseDouble(s);
				this.set_coefficient(a);
			}
			
			else {
				String tmp="";
				for(int i = 0; i < indexOfX; i++){
					tmp += s.charAt(i);
				}
				
				if(tmp.equals("-")) 
					this.set_coefficient(-1);

				else {
					double a = Double.parseDouble(tmp);
					this.set_coefficient(a);

				}
			}	
		}
			
			if(indexOfPower==-1) {
				
				if(indexOfX==-1)
					this.set_power(0);
				
				else
					this.set_power(1);
			}
			
			else {
				String tmp="";
				for(int i = indexOfPower+1; i <s.length() ; i++){
					tmp += s.charAt(i);
				}
				
				int b = Integer.valueOf(tmp);
				
				if(b<0)
					throw new RuntimeException("The power is negative");
				
				else
					this.set_power(b);
		}
	}	
	
	
	public void add(Monom m) {
		
		if(this.get_power() != m.get_power())
			throw new IllegalArgumentException("the power of the tow Monoms arn't equal");
		
		this.set_coefficient(this.get_coefficient()+m.get_coefficient());
	}
	
	public void multipy(Monom d) {
		
		this.set_coefficient(this.get_coefficient()*d.get_coefficient());
		this.set_power(this.get_power()+d.get_power());
	}
	
	public String toString() {
		String ans = "";
		
		if(this.get_coefficient()==0)
			ans+="0.0";
		
		else {
			ans+=String.valueOf(this.get_coefficient());
			
			if(this.get_power()==1)
				ans+= "x";
			
			else if(this.get_power()>1) {
				ans+= "x^";
				ans+= String.valueOf(this.get_power());
			}	
		}
		
		return ans;
		
	}
	// you may (always) add other methods.
	
	public boolean equals(Monom m){
		
		Monom_Comperator comp = new Monom_Comperator();
		
		if(comp.compareCoefficient(this, m)==0 && this.get_coefficient()==0)
			return true;
		
		else if(comp.compare(this, m)==0 && comp.compareCoefficient(this, m)==0)
			return true;
			
		else 
			return false;
		
	}

	//****************** Private Methods and Data *****************
	

	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		if(p<0) {throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}
	private static Monom getNewZeroMonom() {return new Monom(ZERO);}
	private double _coefficient; 
	private int _power;
	
	
}