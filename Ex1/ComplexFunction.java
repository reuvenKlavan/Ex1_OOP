package Ex1;

public class ComplexFunction implements complex_function  {
	
	private function left;
	private function right;
	private Operation Op;
	
	public ComplexFunction(function left, function right, Operation Op) {
		this.left = left;
		this.right = right;
		if((left==null || right == null)&& this.Op!=Op.None) {
			this.Op = Op.None;
		}
		else {
			this.Op = Op;
		}	
	}
	
	
	@Override
	public double f(double x) {
		double sumRight = 0 , sumLeft = 0;
		double sumOfComp = 0;
		
		if(right==null) {
				sumRight=sumRight+0;
		}
		
		else if(right instanceof Monom) {
			Monom mr = (Monom)right;
			return mr.f(x);
		}
		
		else if(right instanceof Polynom) {
			Polynom pr = (Polynom)right;
			return sumRight+pr.f(x);
		}
		
		sumRight = sumRight+right.f(x);
		
		if(this.Op != Op.Comp) {
			if(left==null) {
				sumLeft = sumLeft+0;
			}
		
			else if(left instanceof Monom) {
				Monom ml = (Monom)left;
				return ml.f(x);
			}
		
			else if(left instanceof Polynom) {
				Polynom pl = (Polynom)left;
				return pl.f(x);
			}
		
			sumLeft = sumLeft+left.f(x) ;
		}
		
		else if(this.Op == Op.Comp) {
			
			
			if(left==null) {
				sumLeft = sumLeft+0;
			}
		
			else if(left instanceof Monom) {
				Monom ml = (Monom)left;
				return ml.f(x);
			}
		
			else if(left instanceof Polynom) {
				Polynom pl = (Polynom)left;
				return pl.f(x);
			}
			
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public function copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void plus(function f1) {
		function tmp = new ComplexFunction(left,right,Op);
		this.left = tmp;
		this.right = f1;
		this.Op = Op.Plus;
	}

	@Override
	public void mul(function f1) {
		function tmp = new ComplexFunction(left,right,Op);
		this.left = tmp;
		this.right = f1;
		this.Op = Op.Times;
	}

	@Override
	public void div(function f1) {
		function tmp = new ComplexFunction(left,right,Op);
		this.left = tmp;
		this.right = f1;
		this.Op = Op.Divid;		
	}

	@Override
	public void max(function f1) {
		function tmp = new ComplexFunction(left,right,Op);
		this.left = tmp;
		this.right = f1;
		this.Op = Op.Max;		
	}

	@Override
	public void min(function f1) {
		function tmp = new ComplexFunction(left,right,Op);
		this.left = tmp;
		this.right = f1;
		this.Op = Op.Min;		
	}

	@Override
	public void comp(function f1) {
		function tmp = new ComplexFunction(left,right,Op);
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

}
