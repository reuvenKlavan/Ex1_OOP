package Ex1;

import java.util.Comparator;

public class Monom_Comperator implements Comparator<Monom> {

	public Monom_Comperator() {;}
	
	public int compare(Monom o1, Monom o2) {
		int dp = o1.get_power() - o2.get_power();
		return dp;
	}
	// ******** add your code below *********
	public int compareCoefficient(Monom m1, Monom m2) {// I add this method  before boaz say we don't need the write it so it stay
			
		double diffrence = m1.get_coefficient()-m2.get_coefficient(); 
		
		if(diffrence == 0 || diffrence < m1.EPSILON) // if the difference is small so we will consider it as equal because
			return 0;								 // when we Modify from binary to decimal using operator on two double value we "add" an non rational number
		
		else if(m1.get_coefficient()>m2.get_coefficient())
			return 1;
		
		else 
			return -1;
		
		
	}
	
}
