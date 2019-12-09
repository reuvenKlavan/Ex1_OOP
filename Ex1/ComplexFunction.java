package Ex1;

public class ComplexFunction implements complex_function  {
	
	private function left;
	private function right;
	private Operation Op;

	
	public ComplexFunction(Operation Op,function left, function right) {
		this.left = left;
		this.right = right;
		if(right == null && this.Op!=Operation.None) {
			this.Op = Operation.None;
		}
		else {
			this.Op = Op;
		}	
	}
	
	public ComplexFunction(String s) {
		s = s.replaceAll(" ", "");
		s = s.replaceAll("X", "x");
		function tmp = initFromString(s);
		if(tmp instanceof ComplexFunction) {
			ComplexFunction cons = (ComplexFunction)tmp;
			this.left = cons.left();
			this.right = cons.right();
			this.Op = cons.getOp();
		}
		
		else {
			this.left = tmp;
			this.right = null;
			this.Op = Operation.None;
		}
		
	}
	
	public ComplexFunction(function fun) {
		if(fun instanceof ComplexFunction) {
			ComplexFunction clone = (ComplexFunction) fun.copy();
			this.left = clone.left();
			this.right = clone.right();
			this.Op = clone.getOp();
		}
		
		else if(fun instanceof Polynom || fun instanceof Monom) {
			this.left = fun;
			this.right = null;
			this.Op = Operation.None;
		}
		
	}
	
	
	
	
	
	

	@Override
	public double f(double x) {
		double sumRight = 0 , sumLeft = 0;
		double sumOfComp = 0;
		
		if(right == null) {
			sumRight= sumRight + 0;
		}
		
		else if(right instanceof Monom || right instanceof Polynom) {
			sumRight = sumRight + right.f(x);	
		}
		
		else
			sumRight = sumRight+right.f(x);
		
		
		
		if(this.Op != Operation.Comp) {
		
			if(left instanceof Monom || left instanceof Polynom) {
				sumLeft = sumLeft + left.f(x);
			}
			
			else
				sumLeft = sumLeft+left.f(x);
			
		}	
			
		
		else if(this.Op == Operation.Comp) {
		
		
			if(left instanceof Monom || left instanceof Polynom) {
				sumOfComp = sumOfComp + left.f(sumRight);
			}
			
			else
				sumOfComp = sumOfComp+left.f(sumRight);
		}
		
		switch(Op) {
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
			throw new IllegalArgumentException("You try to divide by 0");
			
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
		
		return 0; // the 0 is for the progrem to compile while we return the wish value in the switch...case
	}

	
	
	
	
	
	
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
		
		
		if(indexOfBracket==-1 && indexOfComma==-1){
			return new Polynom<>(s);
			
		}
		
		else if(indexOfBracket!=-1) {
			boolean found = false;
			opResult = ChooseOperation(s.substring(0,indexOfBracket));

			if(opResult == Operation.None) {
				int indexOfCloseBracket = s.indexOf(')');
				left = s.substring(indexOfBracket+1, indexOfCloseBracket);
				leftResult = initFromString(left);					
				rightResult = null;					
			}
			
			else {
				for(int i = indexOfBracket+1; i <s.length() && !found;i++) {
				
					if(s.charAt(i)=='(') {
						numOfBrackets++;
						left = left+'(';
					}
				
					else if(s.charAt(i)==')') {
						numOfBrackets--;
						left = left+')';
					}
				
					else if(numOfBrackets==1 && s.charAt(i)==',') {
						right = s.substring(i+1,s.length()-1);										
						leftResult = initFromString(left);					
						rightResult = initFromString(right);
					
						found = !found;
					}
				
					else
						left = left + s.charAt(i);
					
				}
			
			}
		
		}
		
		function output = new ComplexFunction( opResult ,leftResult, rightResult);
		return output;
		
	}
	
	
	
	
	
	
	
	

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
			cloneRight = right.copy();
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
			cloneLeft = left.copy();
		}
		
		cloneOP = this.Op;
		function clone = new ComplexFunction(cloneOP ,cloneLeft, cloneRight); 
		return clone;
	}
	
	
	
	
	
	

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
	
	
	
	
	
	
	
	@Override
	public String toString() {
		String output= ChooseString(Op);
		
		
		
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
	
	
	
	
	
	
	@Override
	public boolean equals(Object obj) {
		boolean match = true;
		if(obj instanceof function) {
			function check = (function)obj;
			double eps = 0.01; 
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
	
	
	
	
	
	
	
	private String ChooseString(Operation Op) {
		
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

	private Operation ChooseOperation(String s) { 
		
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
