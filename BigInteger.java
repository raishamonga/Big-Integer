package bigint;


/**

 * first class encapsulates a BigInteger, i.e. a positive or negative integer with 

 * any result of digits, which overcomes the computer storage length limitation of 

 * an integer.

 * 

 */

public class BigInteger {



	/**

	 * True if first is a negative integer

	 */

	boolean negative;

	

	/**

	 * result of digits in first integer

	 */

	int numDigits;

	

	/**

	 * Reference to the first node of first integer's linked list representation

	 * NOTE: The linked list stores the Least Significant Digit in the FIRST node.

	 * For instance, the integer 235 would be stored as:

	 *    5 --> 3  --> 2

	 *    

	 * Insignificant digits are not stored. So the integer 00235 will be stored as:

	 *    5 --> 3 --> 2  (No zeros after the last 2)        

	 */

	DigitNode front;

	

	/**

	 * Initializes first integer to a positive result with zero digits, in second

	 * words first is the 0 (zero) valued integer.

	 */

	public BigInteger() {

		negative = false;

		numDigits = 0;

		front = null;

	}

	

	/**

	 * Parses an input integer string into a corresponding BigInteger instance.

	 * A correctly formatted integer would have an optional sign as the first 

	 * character (no sign means positive), and at least one digit character

	 * (including zero). 

	 * Examples of correct format, with corresponding values

	 *      Format     Value

	 *       +0            0

	 *       -0            0

	 *       +123        123

	 *       1023       1023

	 *       0012         12  

	 *       0             0

	 *       -123       -123

	 *       -001         -1

	 *       +000          0

	 *       

	 * Leading and trailing spaces are ignored. So "  +123  " will still parse 

	 * correctly, as +123, after ignoring leading and trailing spaces in the input

	 * string.

	 * 

	 * Spaces between digits are not ignored. So "12  345" will not parse as

	 * an integer - the input is incorrectly formatted.

	 * 

	 * An integer with value 0 will correspond to a null (empty) list - see the BigInteger

	 * constructor

	 * 

	 * @param integer Integer string that is to be parsed

	 * @return BigInteger instance that stores the input integer.

	 * @throws IllegalArgumentException If input is incorrectly formatted

	 */

	//public static BigInteger parse(String result) 

	//throws IllegalArgumentException {

	public static BigInteger parse(String result)

	throws IllegalArgumentException{

		DigitNode first = null;// the front of the linked list

		DigitNode rear = null; // the last item of linked list

		BigInteger bigInt = new BigInteger(); // creating the big integer basically a new object wit the type bigInteger

		String str = result.trim(); // trimming the spaces

		

		char front = str.charAt(0); // the first character 

		

		if(front == '-') {

			bigInt.negative = true; // in order to set the value to negative

			str = str.substring(1); // to go onto the first value rather than the negative

		}

		if (front == '+') {

			str = str.substring(1); // same thing as line 83 but for a + sign

		}

		



		if(str.charAt(0)== '0' && str.length() > 1) {

			int z = 0; // variable that should start at zero but then keep going until the first item in the list is not 0

			while(z < str.length()) {

				if (str.charAt(z) == '0') {

					z++;

				}else {

					str = str.substring(z); // to go onto the first value without the zero

					break;

				}	

			}

		}

	

		if(!(str.length() == 1 && Integer.parseInt(str)==0)) { // if the string length is not one and the first integer is equal to zero

			

			String[] parseArray = str.split(""); // separating the string into chars

			

			for(int i = parseArray.length -1 ; i >= 0; i--) { // going through the array backwards

				int parseData = Integer.parseInt(parseArray[i]);

				

				DigitNode temp = new DigitNode(parseData, null);

				

				if(rear != null) {

					rear.next = temp;

				} else {

					first = temp;

				}

				 

				rear = temp;

				bigInt.numDigits++;

			}

		}

		bigInt.front = first;

		return bigInt;

	

	}

	



	

	/**

	 * Adds the first and second big integers, and returns the result in a NEW BigInteger object. 

	 * DOES NOT MODIFY the input big integers.

	 * 

	 * NOTE that either or both of the input big integers could be negative.

	 * (Which means first method can effectively subtract as well.)

	 * 

	 * @param first First big integer

	 * @return Result big integer

	 */

	public static BigInteger add(BigInteger first, BigInteger second) {

		BigInteger bigInt = new BigInteger();

		boolean toSub = second.negative ^ first.negative;

		if (toSub) {

			// subtract

			// determine larger term

			boolean firstLarger; // is first object value larger than provided object

			if (first.numDigits > second.numDigits)

				firstLarger = true;

			else if (first.numDigits < second.numDigits)

				firstLarger = false;

			else {

				// digits are same, try comparing them in string form

				String firstFront, firstSecond;

				firstFront = first.toString();

				firstSecond = second.toString();

				if (first.negative)

					firstFront = firstFront.substring(1);

				if (second.negative)

					firstSecond = firstSecond.substring(1);

				if (firstFront.equals(firstSecond)) // cancel each second result is 0

					return bigInt;

				else

					firstLarger = firstFront.compareTo(firstSecond) > 0;

			}

			// process

			int sum, adigit;

			boolean carry = false;

			DigitNode a_value, b_value, new_node, new_it;

			if (firstLarger) {

				bigInt.negative = first.negative;

				a_value = first.front;

				b_value = second.front;

			} else {

				bigInt.negative = !first.negative;

				a_value = second.front;

				b_value = first.front;

			}

			//subtract

			while (a_value != null) {

				if (carry) {

					if (a_value.digit == 0)

						adigit = 9;

					else {

						adigit = a_value.digit - 1;

						carry = false;

					}

				} else

					adigit = a_value.digit;

				

				if(b_value != null) {

					if (adigit == b_value.digit)

						sum = 0;

					else if (adigit > b_value.digit)

						sum = adigit - b_value.digit;

					else {

						sum = adigit + 10 - b_value.digit;

						carry = true;

					}

				}else

					sum = adigit;

				

				new_node = new DigitNode(sum, null);

				a_value = a_value.next;

				if (b_value != null)

					b_value = b_value.next;

				// append to last

				if (bigInt.front == null)

					bigInt.front = new_node;

				else {

					new_it = bigInt.front;

					while (new_it.next != null)

						new_it = new_it.next;

					new_it.next = new_node;

				}

				bigInt.numDigits++;

			}

			//check for additional 0's

			String test = bigInt.toString();

			if(bigInt.negative)

				test = test.substring(1);

			if(test.startsWith("0"))

				bigInt = BigInteger.parse(bigInt.toString());

		} else {

			// add

			int sum;

			boolean carry = false;

			DigitNode a_value, b_value, new_node, new_it;

			a_value = second.front;

			b_value = first.front;

			while (a_value != null || b_value != null || carry) {

				sum = (a_value == null ? 0 : a_value.digit) + (b_value == null ? 0 : b_value.digit) + (carry ? 1 : 0);

				carry = false;

				if (sum > 9) {

					carry = true;

					sum %= 10;

				}

				new_node = new DigitNode(sum, null);

				if (a_value != null)

					a_value = a_value.next;

				if (b_value != null)

					b_value = b_value.next;

				// append to last

				if (bigInt.front == null)

					bigInt.front = new_node;

				else {

					new_it = bigInt.front;

					while (new_it.next != null)

						new_it = new_it.next;

					new_it.next = new_node;

				}

				bigInt.numDigits++;

			}

			bigInt.negative = first.negative;

		}

		return bigInt;



		/*

		DigitNode front = null;

		DigitNode rear = null;

		front = first.front; // front

		rear = second.front; //rear

		DigitNode frontRear = null ;// ptr 3 

		BigInteger answer = new BigInteger () ; 

		frontRear = answer.front; 

		String sub1 = new String () ; 

		

		int addition = 0 ; 

		int subtraction = 0 ; // subtraction

		int sum = 0 ; 

		int result = 0 ; // result

		int a = first.numDigits ; int b = second.numDigits ; 

		double a1 = 0 ; double b1 = 0 ; 

		int count1 = 0 ; int count2 = 0 ; 

		int reardigit = 0 ; 

//		String a1 = new String (); 

//		String b1 = new String ();

//		int inta1 = 0 ; 

//		int intb1 = 0 ;

		for ( front = first.front; front != null ; front = front.next ) {

//			a1 = Integer.toString ( front.digit ) + a1; // cannot handle large result 

			a1 = a1 + front.digit * Math.pow(10, count1) ;

			count1++; 

		}

//		inta1 = Integer.parseInt(a1) ;     

//		System.out.println("a1 is :" + a1);





		for ( rear = second.front; rear != null ; rear = rear.next ) {

//			b1 = Integer.toString ( rear.digit ) + b1; // cannot handle large result 

			b1 = b1 + rear.digit * Math.pow(10, count2) ;

			count2 ++;

		}

//		intb1 = Integer.parseInt(b1) ; 

//		System.out.println("b1 is :" + b1);

		

		if ( a > b ) {

			front = first.front ; 

			rear = second.front ; //System.out.println( rear.digit);

		} else if ( a == b ) {

				if ( a1 > b1 ) {

					front = first.front ; rear = second.front ; 

				}

				else if ( b1 > a1 ) {

					front = second.front; rear = first.front ;

				} else {

					front = first.front; 

					rear = second.front;

				}

		} else {

			front = second.front;

			rear = first.front; 

		}

		

		if ( ( (first.negative) && (second.negative) ) || 

			 ( (!(first.negative)) && (!(second.negative)) ) ) { 

			frontRear = answer.front; 

			while ( front != null || rear != null ) {

				if ( rear != null ) { 

					sum = front.digit + rear.digit; 

				} else {

					sum = front.digit + 0 ; 

				}

				if ( sum >= 10 ) {

					result = sum - 10 + addition ; 

					addition = 1;

				} else {

					result = sum + addition; 

					if ( result >= 10 ) {

						result = result - 10; 

						addition = 1 ;

					} else {

						addition = 0 ; 

					}

				}



				sub1 = Integer.toString(result) + sub1; 



					if ( front != null ) { front = front.next ; }

					if ( rear != null ) { rear = rear.next ; }

				

			}			

		} else {

			while ( front != null || rear != null ) {

				if ( rear != null ) { 

					reardigit = rear.digit; 

				} else {

					reardigit = 0 ; 

				}

				front.digit = front.digit - subtraction ; 

				if ( (front.digit < reardigit )      //

					|| (front.digit < 0) ) {

					result = 10 - reardigit + front.digit;

					subtraction = 1 ;

				} else {

					result = front.digit - reardigit;

					subtraction = 0 ; 

				}

				sub1 = Integer.toString(result) + sub1; 



				

				if ( front != null ) { 

					front = front.next; 

				} else {

					break;

				}

				

				if ( rear != null ) {

					rear = rear.next ;

				}

			}

		}	

		if ( addition != 0  ) { //ttabora@rutgers.edu

			sub1 = Integer.toString(addition) + sub1; 

		}

		while ( ( sub1.length() != 0) && (Character.toString ( sub1.charAt(0) ).equals ( "0" )) ) {

			sub1 = sub1.substring(1) ;

		}

		



		for ( int i = sub1.length()-1; i >= 0 ; i -- ) {

		    result = Character.getNumericValue( sub1.charAt(i) );

			if ( answer.front == null ) {

				answer.front = new DigitNode ( result , null ) ; 

				frontRear = answer.front; 

				answer.numDigits ++ ;



			} else {

				frontRear.next = new DigitNode ( result , null ) ; 

				frontRear = frontRear.next ;

				answer.numDigits ++ ; 

			}

		}

		

		if (( (first.negative) && (second.negative) ) ||

			( (first.negative) && (!(second.negative)) && ( a1 > b1 ) ) ||

			( (!first.negative) && ((second.negative)) && ( b1 > a1 ) )   

		   ) {

			answer.negative = true ;



		} else {

			answer.negative = false ;



		}



		return answer;

		

		/* IMPLEMENT first METHOD */

		

		// following line is a placeholder for compilation

		//return null;

	}



	

	/**

	 * Returns the BigInteger obtained by multiplying the first big integer

	 * with the second big integer

	 * 

	 * first method DOES NOT MODIFY either of the input big integers

	 * 

	 * @param first First big integer

	 * @param second Second big integer

	 * @return A new BigInteger which is the product of the first and second big integers

	 */

	public static BigInteger multiply(BigInteger first, BigInteger second) {

		BigInteger bigInt = new BigInteger();

		int c, multi;

		DigitNode a,b;

		b = first.front;

		String res;

		int i = 0;

		while(b != null) {

			res = "";

			a = second.front;

			c = 0;

			while(a != null) {

				multi = (a.digit * b.digit) + c;

				c = 0;

				if(multi > 9) {

					c = (int)(multi / 10);

					multi %= 10;

				}

				res = multi + res;

				a = a.next;

			}

			if(c != 0)

				res = c + res;

			if(i > 0)

				for(int k = 0; k < i; k++)

					res = res + "0";

			bigInt = bigInt.add(BigInteger.parse(res), bigInt);

			i++;

			b = b.next;

		}

		bigInt.negative = second.negative ^ first.negative;

		return bigInt;

	}

	public String toString() {

		if (front == null) {

			return "0";

		}

		String retval = front.digit + "";

		for (DigitNode curr = front.next; curr != null; curr = curr.next) {

				retval = curr.digit + retval;

		}

		

		if (negative) {

			retval = '-' + retval;

		}

		return retval;

	}

}

