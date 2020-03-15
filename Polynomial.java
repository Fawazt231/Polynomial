package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @FawazT  runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */

	
	@SuppressWarnings("null")
	public static Node add(Node poly1, Node poly2) {
		Node front = poly1;
		Node front2 = poly2;
		Node y = new Node(0,0,null);
		Node w=y;
		if (poly2 == null && poly1 == null) {
			return null;
		}
		if (poly1 == null) {
			while (front2!=null) {
				y.next = new Node(front2.term.coeff, front2.term.degree, null);
				y = y.next;
				front2 = front2.next;
			}
			return w.next;
			
		} 
		if (poly2 == null) {
			while (front!=null) {
				y.next = new Node(front.term.coeff, front.term.degree, null);
				y = y.next;
				front = front.next;
			}
			return w.next;
		}

		Node x = new Node(0,0,null);
		Node poly = x;
		while (front != null && front2 != null) {
			if(front.term.degree<front2.term.degree) {
				x.next = new Node(front.term.coeff, front.term.degree, null);
				front = front.next;
			} else if (front.term.degree == front2.term.degree) {
				x.next = new Node(front.term.coeff+front2.term.coeff, front.term.degree, null);
				front = front.next;
				front2 = front2.next;
			}  else if (front.term.degree>front2.term.degree) {
				x.next= new Node(front2.term.coeff,front2.term.degree, null);
				front2 = front2.next;
			}
			x = x.next;
			
		}
		Node ptr = null;
		if (front != null) {
			ptr = front;
		} else {
			ptr = front2;
		}
		while (ptr!= null) {
			x.next = ptr;
			x = x.next;
			ptr = ptr.next;
		}
		
		Node pnt = poly;
		Node prev = poly;
		pnt = pnt.next;
		while (pnt != null) {
			if (pnt.term.coeff == 0 ) {
				prev.next = pnt.next;
			} else { 
				prev = prev.next;
			}
			pnt = pnt.next;
		}
		
		return poly.next;
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		if(poly1 == null || poly2 == null){
            return null;
        }
		
		
		Node y = new Node (0,0,null);
		Node ptr = poly1;
		Node ptr2 = poly2;
		while (ptr != null) {
			Node x = new Node (0,0,null);
			while (ptr2 != null) {
				x.next = new Node(ptr.term.coeff*ptr2.term.coeff,ptr.term.degree+ptr2.term.degree,null);
				y = Polynomial.add(y, x);
				ptr2 = ptr2.next;
			}
			ptr2 = poly2;
			ptr = ptr.next;
		}
		return y;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		Node front = poly;
		float sum = 0;
		while(front != null) {
			sum = (float) (sum + Math.pow(x,front.term.degree)*front.term.coeff);
			front = front.next;
		}
		return sum;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
