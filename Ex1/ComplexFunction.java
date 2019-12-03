package Ex1;

public class ComplexFunction<T> implements complex_function  {
	
	private function left;
	private function right;
	private Operation Op;

	
	public ComplexFunction(function left, function right, Operation Op) {
		this.left = left;
		this.right = right;
		if(right == null && this.Op!=Op.None) {
			this.Op = Op.None;
		}
		else {
			this.Op = Op;
		}	
	}
	
	public ComplexFunction(String s) {
		function tmp = initFromString(s);
		ComplexFunction constru = (ComplexFunction)tmp;
		this.left = constru.left();
		this.right = constru.right();
		this.Op = constru.getOp();
		
	}
	
	
	
	
	
	
	@Override
	public double f(double x) {
		double sumRight = 0 , sumLeft = 0;
		double sumOfComp = 0;
		
		if(right instanceof Monom) {
			Monom mr = (Monom)right;
			sumRight = mr.f(x);
		}
		
		else if(right instanceof Polynom) {
			Polynom<?> pr = (Polynom<?>)right;
			sumRight = pr.f(x);
		}
		
		else
			sumRight = sumRight+right.f(x);
		
		if(this.Op != Op.Comp) {
		
			if(left instanceof Monom) {
				Monom ml = (Monom)left;
				sumLeft =  ml.f(x);
			}
		
			else if(left instanceof Polynom) {
				Polynom<?> pl = (Polynom<?>)left;
				sumLeft = pl.f(x);
			}
			else
				sumLeft = sumLeft+left.f(x) ;
		}
		
		else if(this.Op == Op.Comp) {
		
		
			if(left instanceof Monom) {
				Monom ml = (Monom)left;
				sumLeft = ml.f(x);
			}
		
			else if(left instanceof Polynom) {
				Polynom pl = (Polynom)left;
				sumLeft = pl.f(x);
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
			throw new IllegalArgumentException("You try to by 0");
			
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
			if(right == null) {
				return sumLeft;
			}
			
			else {
				return sumRight;
			}
		}
		
		return 0; // the 0 is for the progrem to compile while we return the wish value in the switch...case
	}

	
	
	
	
	
	
	@Override
	public function initFromString(String s) {
		String left = "" , right = ""; 
		function leftResult = null, rightResult= null;
		int numOfBrackets=0;
		int indexOfBracket = s.indexOf('(');
		int indexOfComma = s.indexOf(',');
		
		if(indexOfBracket != -1)
			numOfBrackets++;
		
		
		if(indexOfBracket==-1 && indexOfComma==-1){
			return new Polynom(s);
			
		}
		
		else if(indexOfBracket!=-1) {
			boolean found = false;
			
			
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
					ChooseOperation(s.substring(0,indexOfBracket));
					found = !found;
				}
				
				else
					left = left + s.charAt(i);
					
			}
			
			
		}
		
		function output = new ComplexFunction(leftResult, rightResult, this.Op);
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
		function clone = new ComplexFunction<>(cloneLeft, cloneRight, cloneOP); 
		return clone;
	}
	
	
	
	
	
	

	@Override
	public void plus(function f1) {
		function tmp = new ComplexFunction<>(left,right,Op);
		this.left = tmp;
		this.right = f1;
		this.Op = Op.Plus;
	}

	@Override
	public void mul(function f1) {
		function tmp = new ComplexFunction<>(left,right,Op);
		this.left = tmp;
		this.right = f1;
		this.Op = Op.Times;
	}

	@Override
	public void div(function f1) {
		function tmp = new ComplexFunction<>(left,right,Op);
		this.left = tmp;
		this.right = f1;
		this.Op = Op.Divid;		
	}

	@Override
	public void max(function f1) {
		function tmp = new ComplexFunction<>(left,right,Op);
		this.left = tmp;
		this.right = f1;
		this.Op = Op.Max;		
	}

	@Override
	public void min(function f1) {
		function tmp = new ComplexFunction<>(left,right,Op);
		this.left = tmp;
		this.right = f1;
		this.Op = Op.Min;		
	}

	@Override
	public void comp(function f1) {
		function tmp = new ComplexFunction<>(left,right,Op);
		this.left = tmp;
		this.right = f1;
		this.Op = Op.Comp;		
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
		String output= "";
		
		if(left instanceof Monom || left instanceof Polynom) {
			output += ChooseString(Op)+ left.toString();
		}
		
		else if(left instanceof ComplexFunction) {
			output = output + left.toString();
		}
		
		if(right == null) {
			output +=')'; 
		}
		
		else if(left instanceof Monom || left instanceof Polynom) {
			output += ',' +right.toString()+ ')';
		}
		
		else if(left instanceof ComplexFunction) {
			output = ','+ output + right.toString() + ')';
		}
		
		return output;
	}
	
	
	
	
	
	
	@Override
	public boolean equals(Object obj) {
		
		return false;
	}
	
	
	
	
	
	
	
	private String ChooseString(Operation Op) {
		
		if(this.Op == Op.Plus) {
			return "Plus(";
		}
		
		else if(this.Op == Op.Times) {
			return "Times(";
		}
		
		else if(this.Op == Op.Divid) {
			return "Divid(";
		}
		
		else if(this.Op == Op.Max) {
			return "Max(";
		}
		
		else if(this.Op == Op.Min) {
			return "Min(";
		}
		
		else if(this.Op == Op.Comp) {
			return "Comp(";
		}
		
		else {
			throw new IllegalArgumentException("Illegal Operation");
		}	
	}

	private void ChooseOperation(String s) { 
		
		if(s.equals("Plus")) {
			this.Op = Op.Plus;
		}
		
		else if(s.equals("Times")) {
			this.Op = Op.Times;
		}
		
		else if(s.equals("Divid")) {
			this.Op = Op.Divid;
		}
		
		else if(s.equals("Max")) {
			this.Op = Op.Max;
		}
		
		else if(s.equals("Min")) {
			this.Op = Op.Min;
		}
		
		else if(s.equals("Comp")) {
			this.Op = Op.Comp;
		}
		
		else {
			this.Op = Op.Error;
		}
	}

}
