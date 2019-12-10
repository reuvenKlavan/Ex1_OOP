package Ex1;

public class ComplexFunction implements complex_function  {
	
	private function left;
	private function right;
	private Operation Op;

	
	
	/**
	 * trivial Constructor
	 * @param Op the Operation for this complex function if right is null it will be none immediately
	 * @param left what would be the left of this complex function 
	 * @param right same as the left
	 */
	public ComplexFunction(Operation Op,function left, function right) {//trivial Constructor
		this.left = left;
		this.right = right;
		if(right == null && this.Op!=Operation.None) {
			this.Op = Operation.None;
		}
		else {
			this.Op = Op;
		}	
	}
	
	/**
	 * create a new complex function from a String 
	 * @param s a String we send to initFromString for create a new complex function
	 */
	public ComplexFunction(String s) {//constructor from  String
		s = s.replaceAll(" ", "");
		s = s.replaceAll("X", "x");
		function tmp = initFromString(s);
		if(tmp instanceof ComplexFunction) {// if the String it is a complex function
			ComplexFunction cons = (ComplexFunction)tmp;
			this.left = cons.left();
			this.right = cons.right();
			this.Op = cons.getOp();
		}
		
		else {// if return from initFromString a polynom
			this.left = tmp;
			this.right = null;
			this.Op = Operation.None;
		}
		
	}
	
	public ComplexFunction(Object obj) {//copy constructor
		if(obj instanceof ComplexFunction) {//if it is complex function
			ComplexFunction tmp =  (ComplexFunction) obj;
			ComplexFunction clone = (ComplexFunction) tmp.copy();
			this.left = clone.left();
			this.right = clone.right();
			this.Op = clone.getOp();
		}
		
		else if(obj instanceof Polynom || obj instanceof Monom) {//else if it is Polynom or Monom
			function tmp = (function)obj;
			function clone = tmp.copy();
			this.left = clone;
			this.right = null;
			this.Op = Operation.None;
		}
		
	}
	
	
	
	
	
	/**
	 * compute this function for x coordinate
	 * @param x where to compute the f function on the x axis  
	 */
	
	@Override
	public double f(double x) {
		double sumRight = 0 , sumLeft = 0;
		double sumOfComp = 0;
		
		if(right == null) {// if right = null don't add nothing and base case
			sumRight= sumRight + 0;
		}
		
		else if(right instanceof Monom || right instanceof Polynom) {//base case for the recursion for the right function
			sumRight = sumRight + right.f(x);	
		}
		
		else
			sumRight = sumRight+right.f(x);//recursion call to compute the right function
		
		
		
		if(this.Op != Operation.Comp) {//if the Operator is not compose of function we need to compute the left in a regular way left.f(x)
		
			if(left instanceof Monom || left instanceof Polynom) {//same for the right 
				sumLeft = sumLeft + left.f(x);
			}
			
			else
				sumLeft = sumLeft+left.f(x);
			
		}	
			
		
		else if(this.Op == Operation.Comp) {//if the Operation is Compose we need to compute left.f(sumOfRight)
		
		
			if(left instanceof Monom || left instanceof Polynom) {
				sumOfComp = sumOfComp + left.f(sumRight);
			}
			
			else
				sumOfComp = sumOfComp+left.f(sumRight);
		}
		
		switch(Op) {//check what is the Operation so we know what kind Operation we need to do
		case Plus: 
			return sumLeft+sumRight;
				
		case Times:
			return sumLeft*sumRight;
			
		case Divid:
			try {
				return sumLeft/sumRight;
			}
			
			catch(Exception e){
				
			}
		
		case Error:	
			throw new IllegalArgumentException("you try to divide by 0 or the Operator is not good");//if the Operator didn't initiate well or we try to divide by 0  
			
		case Max:
			if(sumRight>sumLeft) {
				return sumRight;
			}
			
			else {
				return sumLeft;
			}
		
		case Min:
			if(sumRight>sumLeft) {
				return sumRight;
			}
			
			else {
				return sumLeft;
			}
			
		case Comp:
			return sumOfComp;
			
		case None: 
			return sumLeft;
			
		}
		
		return 0; // the 0 is for the program to compile while we return the wish value in the switch...case
	}

	
	
	
	/**
	 * We'll create a new Complex Function from a String
	 * we use recursion for what is consider the String for left function
	 * and than another call for the rest of the String for the right function 
	 * while our base case is a regular ploynom 
	 * Special case for the right could be null and so the Operation will be none
	 * @param we initiate the Complex Function from the String s   
	 */
	@Override
	public function initFromString(String s) {
		String left = "" , right = ""; 
		function leftResult = null, rightResult= null;
		Operation opResult=null;
		int numOfBrackets=0;
		int indexOfBracket = s.indexOf('(');
		int indexOfComma = s.indexOf(',');
		
		if(indexOfBracket != -1)
			numOfBrackets++;
		
		
		if(indexOfBracket==-1 && indexOfComma==-1){// base case
			return new Polynom<>(s);
			
		}
		
		else if(indexOfBracket!=-1) {
			boolean found = false;
			opResult = ChooseOperation(s.substring(0,indexOfBracket));//choosing the Operator for this ComplexFunction

			if(opResult == Operation.None) {//if the Operator is none we could determine that the right is null 
				int indexOfCloseBracket = s.indexOf(')');
				left = s.substring(indexOfBracket+1, indexOfCloseBracket);
				leftResult = initFromString(left);					
				rightResult = null;					
			}
			
			else {
				for(int i = indexOfBracket+1; i <s.length() && !found;i++) {
				
					if(s.charAt(i)=='(') {//start of new Operation
						numOfBrackets++;
						left = left+'(';
					}
				
					else if(s.charAt(i)==')') {//end of a latest Operation
						numOfBrackets--;
						left = left+')';
					}
				
					else if(numOfBrackets==1 && s.charAt(i)==',') {//when we get to the end of the left function
						right = s.substring(i+1,s.length()-1);// the rest is gone to be the right function										
						leftResult = initFromString(left);					
						rightResult = initFromString(right);
					
						found = !found;
					}
				
					else
						left = left + s.charAt(i);//we add to a String so we could call a recursive call on him
					
				}
			
			}
		
		}
		
		function output = new ComplexFunction( opResult ,leftResult, rightResult);//we create Complex function in the end and return it 
		return output;
		
	}
	
	
	
	
	
	
	
	/**
	 * we deep copy this Complex Function to a new ComplexFunction
	 * @return we return a deep copy of this Complex Function 
	 */
	@Override
	public function copy() {
		function cloneRight, cloneLeft;
		Operation cloneOP;
		
		
		if(right==null) {
			cloneRight = null;
		}
	
		else if(right instanceof Monom) {
			Monom mr = (Monom)right;
			cloneRight = mr.copy();
		}
	
		else if(right instanceof Polynom) {
			Polynom<?> pr = (Polynom<?>)right;
			cloneRight = pr.copy();
		}
		
		else {
			cloneRight = right.copy();// if (base case) else call left recursive 
		}
		
		
		
		if(left instanceof Monom) {
			Monom ml = (Monom)left;
			cloneLeft = ml.copy();
		}
	
		else if(left instanceof Polynom) {
			Polynom<?> pl = (Polynom<?>)left;
			cloneLeft = pl.copy();
		}
		
		else {
			cloneLeft = left.copy();// the same for left as right
		}
		
		cloneOP = this.Op;
		function clone = new ComplexFunction(cloneOP ,cloneLeft, cloneRight); 
		return clone;
	}
	
	
	
	
	
	/**
	 * we create a new Complex Function by this Complex Function
	 * put the new Function to the left and f1 we put on the right
	 * the description is relative for all next 6 methods  
	 * @param the new function on the right	
	 */
	@Override
	public void plus(function f1) {
		function tmp = new ComplexFunction(Op,left,right);
		this.left = tmp;
		this.right = f1;
		this.Op = Operation.Plus;
	}

	@Override
	public void mul(function f1) {
		function tmp = new ComplexFunction(Op,left,right);
		this.left = tmp;
		this.right = f1;
		this.Op = Operation.Times;
	}

	@Override
	public void div(function f1) {
		function tmp = new ComplexFunction(Op,left,right);
		this.left = tmp;
		this.right = f1;
		this.Op = Operation.Divid;		
	}

	@Override
	public void max(function f1) {
		function tmp = new ComplexFunction(Op,left,right);
		this.left = tmp;
		this.right = f1;
		this.Op = Operation.Max;		
	}

	@Override
	public void min(function f1) {
		function tmp = new ComplexFunction(Op,left,right);
		this.left = tmp;
		this.right = f1;
		this.Op = Operation.Min;		
	}

	@Override
	public void comp(function f1) {
		function tmp = new ComplexFunction(Op,left,right);
		this.left = tmp;
		this.right = f1;
		this.Op = Operation.Comp;		
	}

	@Override
	public function left() {
		return left;
	}

	@Override
	public function right() {
		return right;
	}

	@Override
	public Operation getOp() {
		return Op;
	}
	
	
	
	
	
	
	/**
	 * we use with the idle of in order search like in BT to return the String  
	 * @return String of the Object
	 */
	@Override
	public String toString() {
		String output= ChooseString(Op);//the Operator of this Complex function
		
		
		
		if(left instanceof Monom || left instanceof Polynom) {
			output = ChooseString(Op) + left.toString();
		}
		
		else if(left instanceof ComplexFunction) {
			output = output + left.toString();
		}
		
		if(right == null) {
			output = output+')'; 
		}
		
		else if(left instanceof Monom || left instanceof Polynom) {
			output = output+',' +right.toString()+ ')';
		}
		
		else if(left instanceof ComplexFunction) {
			output =  output +","+ right.toString() + ')';
		}
		
		return output;
	}
	
	
	
	
	/**
	* This function checks if the two functions are equal.
	*  The logic comparison is out of our reach therefore
	*  this equals really implements a function of visual equals, by comparing the value of the two functions in
	*  particular range on x axis, in the range [-10,10], and by epsilon (0.001) steps.
	*  If all of the function values are equal, then you will get a True answer.
	*  Even thought the comparison is not logic, the result is, in most cases, the real logic equality.
	*/
	@Override
	public boolean equals(Object obj) {
		boolean match = true;
		if(obj instanceof function) {// if it is not implement Monom/Polynom/ComplexFunction we could not compare it with this 
			function check = (function)obj;
			double eps = 0.001; 
			double x = -10.0;
			while(x<10.0 && match) {
				if(this.f(x) == check.f(x)) 
					x=x+eps;
					
				else 	
					 match = false;			
			}
		}
		
		else {	
			match = false;
		}
		
		return match;
			
	}
	
	
	
	
	
	
	
	private String ChooseString(Operation Op) {// private method
		
		if(this.Op == Operation.Plus) {
			return "Plus(";
		}
		
		else if(this.Op == Operation.Times) {
			return "Times(";
		}
		
		else if(this.Op == Operation.Divid) {
			return "Divid(";
		}
		
		else if(this.Op == Operation.Max) {
			return "Max(";
		}
		
		else if(this.Op == Operation.Min) {
			return "Min(";
		}
		
		else if(this.Op == Operation.Comp) {
			return "Comp(";
		}
		
		else if(this.Op == Operation.None) {
			return "None(";
		}
		
		else {
			throw new IllegalArgumentException("Illegal Operation");
		}	
	}

	private Operation ChooseOperation(String s) { //private method
		
		if(s.equals("Plus")||s.equals("plus")) {
			return Operation.Plus;
		}
		
		else if(s.equals("Times")||s.equals("mul")) {
			return Operation.Times;
		}
		
		else if(s.equals("Divid")||s.equals("div")) {
			return Operation.Divid;
		}
		
		else if(s.equals("Max")||s.equals("max")) {
			return Operation.Max;
		}
		
		else if(s.equals("Min")||s.equals("min")) {
			return Operation.Min;
		}
		
		else if(s.equals("Comp")||s.equals("comp")) {
			return Operation.Comp;
		}
		
		else if(s.equals("None")||s.equals("none")) {
			return Operation.None;
		}
		
		else {
			return Operation.Error;
		}
	}

}
