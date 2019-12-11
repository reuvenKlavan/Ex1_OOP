# Complex Function - Ex1 OOP project
The Complex Function

## The Complex function can recive any kind of complex function with the operators deataild below. the comlex function can be represented by four ways:
1. An operator and two Complex Functions in parenthesis with a comma bitween them.
2. An operator and a Complex Function or a Polynom or a Monom in parenthesis of ether side the comma.
3. An operator and two Polynoms in parenthesis with a comma bitween them.
4. An operator and two Monoms in parenthesis with a comma bitween them.
5. An operator and a Polynom and a Monom in parenthesis of ether side the comma.

The Complex Function is a string  that can contain multiple oprators in every complex function. The complex function can as well read a string from a file, and also right the comlex function as a string to a file.
The complex function implements a GUI class that draws the function on a graph with X and Y axis.

## ComplexFunction has the folloing operators:
1. Times
2. Plus
3. Divid
4. Max
5. Min
4. Comp
6. None


## The Monom class can receive any symple monom of the form "+-ax^b" while 'a' belongs to the real numbers and 'b' belongs to the Natural numbers.

The polynom class represents a Polynom with add, multiply functionality witch can receive any kind of addition and subtraction of monom type objects of the form "ax^b + cx^d +e" while 'a','c' and 'e' belongs to the real numbers, and 'b' and 'd' belong to the natural numbers.
it also should support the following:
 * 1. Riemann's Integral.
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative

## Mononm has the main functions:
1. f function - this method return the value of a Monom for the value 'x'.
2. Constructor - receives two numbers: 'a' Double, 'b' Integer and builds a Monom with coefficient 'a' and power 'b'.
3. add - receives Monom and add it to another Monom only if the exponent are equal.
4. multiply - receives Monom and multiplies it with another Monom, by multiplying the coefficient and adding the exponent.
5. string - String constructor, recives string from user and converts it to Monom type.


## Polynom has the main functions:
1.f function - this method return the value of a Monom for the value 'x'.
2. add - adds 2 polynoms by sending every monom of p1 to the method add monom.
3. substract - substracts polynoms by multiplying every monom in p1 by -1 and adding the new monom to current polynom.
4. multiply - multiplies 2 polynoms, first creates array of polynoms in the size of p1, for every copy polynom in that array multiplies by               monom from p1, and in the end summs these polynoms in the array and we get a multiplication of 2 polynoms. 
5. equals - checks if this == p1 by the number of monoms in both of them and by there value.
6. isZero - check if f(x) = 0.
7. root - finds approximately x2 so f(x2) == 0, while x2 is: x0 <= x2 <= x1 (1 , f(x2) < eps (2
8. copy - copies polynom.
9. derivative - goes over every monom in this polynom and derives the monoms.
10. area - calculats approximately the area between x0, x1 and over x axis using reimann integral.
11. iterator - the iterator of monoms array list.
12. toString - Strings this polynom.

## External info:
- Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
- Root: https://www.geeksforgeeks.org/program-for-bisection-method/
- Polynomial: https://en.wikipedia.org/wiki/Polynomial
- Monomial: https://en.wikipedia.org/wiki/Monomial
- Derivative: https://en.wikipedia.org/wiki/Derivative

#### this project was written by @joseph_schtein(206128613) & @Reuven_Klavan(205416365)
