import java.util.Scanner;

public class Number 
{
	private Node low; //left most value
	private Node high;//right most value
	private int digitCount; // total number of digits stored in list
	private int decimalPlaces; // number of digits after deciaml
	private boolean negative; //gives the sign

	public Number()//A constructor.
	{
		//inital values for a Number with an empty string
		this.low = null; 
		this.high = null;
		this.digitCount = 0;
		this.decimalPlaces = 0;
		this.negative = false;
	}
	public Number(String str)//A constructor which takes a String representation of a number (e.g. "-21.507"). Calls accept.
	{
		validate(str);//makes sure its a valid number without unwanted values
		accept(str); //converts string into a double linked list 
	}

	@SuppressWarnings("resource")
	public static void main(String[] args)
	{
		boolean hit= false;
		Number num1 = new Number();
		Number num2 = new Number();
		String ans = "";
		Scanner key = new Scanner(System.in);
		do
		{
			System.out.println("enter a value: e     add: a\n" +
					"subtract: s          multiply: m\n" +
					"reverse sign: r      clear: c\n" +
					"quit: q");
			System.out.print("-> ");
			ans = key.nextLine();
			switch(ans)
			{
			case "e":
				hit = true;
				System.out.println("value: ");
				String val = key.nextLine();
				try 
				{
					num1 = new Number(val);
				} catch ( IllegalArgumentException e)
				{
					System.out.println("Please enter valid input.\n");
					break;
				}
				System.out.println(num1.toString() + "\n"); 
				break;
			case "a":
				if (hit == false)
				{
					System.out.println("please enter a initial value.\n");
					break;
				}
				System.out.println("value: ");
				String val2 = key.nextLine();
				try
				{
					num2 = new Number(val2);
				} catch ( IllegalArgumentException e)
				{
					System.out.println("Please enter valid input.\n");
					break;
				}
				num1 = num1.add(num2);
				System.out.println( "Number 1 after adding: " + num1.toString() + "\n");
				break;
			case "s":
				if (hit == false)
				{
					System.out.println("please enter a initial value.\n");
					break;
				}
				System.out.println("value: ");
				String val3 = key.nextLine();
				try 
				{
					num2 = new Number(val3);
				} catch ( IllegalArgumentException e)
				{
					System.out.println("Please enter valid input.\n");
					break;
				}
				num1 = num1.subtract(num2);
				System.out.println( "Number 1 after subtract: " + num1.toString() + "\n");
				break;
			case "m":
				if (hit == false)
				{
					System.out.println("please enter a initial value.\n");
					break;
				}
				System.out.println("value: ");
				String val4 = key.nextLine();
				try
				{
					num2 = new Number(val4);
				} catch ( IllegalArgumentException e)
				{
					System.out.println("Please enter valid input.\n");
					break;
				}
				num1 = num1.multiply(num2);
				System.out.println("Number 1 after mltiply: " + num1.toString() + "\n");
				break;
			case "r":
				if (hit == false)
				{
					System.out.println("please enter a initial value.\n");
					break;
				}
				num1.reverseSign();
				System.out.println(num1.toString() + "\n");
				break;
			case "c":
				num1.high = null; // clear high
				num1.low = null; // clear low
				num1.high = num1.low; // reduce size of list to 1 but techinically when accept is called its seen as 0
				num1.decimalPlaces = 0;
				num1.digitCount = 0;
				System.out.println("0.0"); // gives user visual of empty list
				break;
			case "q":
				System.out.println("Program terminated.");
				System.exit(0);
				break;
			default: 
				System.out.println("Incorrect option"); // just for debugging, this case will never hit due to validate()
			}
		}while (ans != "q");
	}

	public void validate(String s) // checks string to see if its in proper number format 
	{
		int decCounter = 0; // keep track of decimals for error catching
		for (int i = 0; i < s.length();i++) 
		{
			if ( (int) s.charAt(i) > 57 || (int) s.charAt(i) < 43) // to account for signs and numbers
				throw new IllegalArgumentException();
			if (decCounter > 1) // if more then one decimal point in s
				throw new IllegalArgumentException();
			if (i > 0 && (s.charAt(i) == '+' || s.charAt(i) == '-')) // sign can only be at begining of s
				throw new IllegalArgumentException();
			if (s.charAt(i) == '.') 
				decCounter++; // increment accordingly
		}
	}
	private boolean sign(String s) // return true or false depending on first index of s
	{
		boolean neg = false;
		if (s.charAt(0) == '-' )
			neg = true;
		return neg;
	}
	public void accept(String str)//Builds a list representation of the number represented by the string.
	{
		// decimal point is represented as -1 
		// need to discard the point where its -1, then add back in at toString()
		boolean whole = true; // keep track when decimal places starts
		this.negative = sign(str); // get sign
		if (str.charAt(0) == '-' || str.charAt(0) == '+') // trim off the sign
			str = str.substring(1, str.length());

		for (int i = 0; i < str.length(); i++)
		{	
			if (high == null) // if list(number) is empty
			{
				Node newNode = new Node();
				int data = Character.getNumericValue(str.charAt(i)); // gets numeric value of that index 
				if (data == -1)// if -1 our decimal point has been found
				{
					whole = false; //number is no long whole after this point
					continue;
				}
				newNode.setValue(data); //set value into node
				high = newNode;
				low = newNode;
				digitCount++;
				if (whole == false)//this increments for decimal places 
					decimalPlaces++;
			}
			else // if list is not empty
			{
				Node newNode = new Node(); // make new node
				int data = Character.getNumericValue(str.charAt(i)); // gets numeric value of that index 
				if (data == -1) // if -1 our decimal point has been found
				{
					whole = false; //number is no long whole after this point
					continue;
				}
				newNode.setValue(data); //set value into node
				low.setNext(newNode); // tack node on to end
				newNode.setPrev(low); // update pointers
				newNode.setNext(null);
				low = newNode;
				digitCount++; // update number of nodes
				if (whole == false) // check whole 
					decimalPlaces++;
			}
		}
	}
	private void addRear(Node n, int data) //useful method for adding 0's to back of a number for proper alignment
	{
		n.setValue(data); // get value from paramerter
		if (high == null)//if empty 
		{
			high = n;// that new node is both high and low
			low = n;
		}
		else // if number not empty
		{
			low.setNext(n); //update pointers
			n.setPrev(low);
			low = n;
		}
	}
	private void addFront(Node n, int data) // same shit as addtoRear but adding to front 
	{
		n.setValue(data); // set data from parameter
		if (high == null) // if number is empty
		{
			high = n; // new node is both high and low
			low = n;
		}
		else // if not empty
		{
			high.setPrev(n); //update pointers
			n.setNext(high);
			high = n;
		}
	}
	private Number align(Number n) // properly aligns to numbers so both of the decimalPlaces and digitCount are same  
	{		
		Number tmp1 = this; // temp reference to num 1 
		Number tmp2 = n; // temp reference to num 2
		int x = tmp1.digitCount - tmp1.decimalPlaces; // number of whole numbers for num1
		int y = tmp2.digitCount - tmp2.decimalPlaces; // number of whole numbers for num2
		if ( x < y) // if num1 whole numbers is less then num2 whole numbers 
		{
			while ( x < y ) // if num 2 has more digits then num1
			{
				Node tmp = new Node();
				tmp1.addFront(tmp, 0); // add a tmp dummy node to front of num1
				x++;//update values
				tmp1.digitCount++;
			}
		}
		x = tmp1.decimalPlaces; // number of values after decimal for number 1
		y = tmp2.decimalPlaces;// number of values after decimal for number 2
		if (x < y)
		{
			while (x < y) // num2 has more decimal places the num1
			{
				Node tmp = new Node();
				tmp1.addRear(tmp, 0); // add a tmp dummy node to back of num1
				x++;
				tmp1.digitCount++; //update values
				tmp1.decimalPlaces++;
			}
		}
		x = tmp2.digitCount - tmp2.decimalPlaces; // number of whole numbers before decimal for number 2
		y = tmp1.digitCount - tmp1.decimalPlaces; // number of whole numbers before decimal for number 1
		if (x < y)
		{
			while (x < y) // if num1 has more digits then num2
			{
				Node tmp = new Node();
				tmp2.addFront(tmp, 0); // add a tmp dummy node to front of num2
				x++; // update values
				tmp2.digitCount++;
			}
		}
		x = tmp2.decimalPlaces;
		y = tmp1.decimalPlaces;
		if (x < y)
		{
			while (x < y) // if num1 has more decimal places then num2
			{
				Node tmp = new Node();
				tmp2.addRear(tmp, 0); // add a tmp dummy node to rear of num1
				x++; //update values
				tmp2.digitCount++;
				tmp2.decimalPlaces++;
			}
		}
		return tmp1;
	}
	private int compare(Number n)
	{
		int thisWhole = this.digitCount - this.decimalPlaces; //whole number value of num1
		int nWhole = n.digitCount - n.decimalPlaces; //whole number value of num2
		if (thisWhole > nWhole) // if more whole numbers in num1
			return 1;
		if (thisWhole < nWhole) // if more whole numbers in num2
			return -1;
		if (thisWhole == nWhole) //if whole number values equal
		{
			if (this.decimalPlaces > n.decimalPlaces) // if more decimal values in num1
				return 1;
			if (this.decimalPlaces < n.decimalPlaces) // if more decimal values in num2
				return -1;
			if (this.decimalPlaces == n.decimalPlaces) // if both decimal values are same
			{
				Node thisCursor = this.high; //cursor node starting from num1 high
				Node nCursor = n.high; //cursor node starting from num2 high
				do 
				{
					//compares values of two points until either one is greater then other or end is reached
					//if end is reached and all values are same return 0
					if (thisCursor.getValue() > nCursor.getValue()) 
						return 1;
					if (thisCursor.getValue() < nCursor.getValue())
						return -1;
					if (thisCursor.getValue() == nCursor.getValue() && thisCursor.getNext() == null)
						return 0;
					thisCursor = thisCursor.getNext();
					nCursor = nCursor.getNext();
				} while (thisCursor != null);
			}
		}
		return 0; // this statement should never hit due to everything else within this method
	}
	private Number trim(Number n)
	{
		Number tmp = n; // temp reference to number we want to trim
		while (tmp.high.getValue() == 0 && (tmp.digitCount - tmp.decimalPlaces > 0)) // if there are hanging 0s before decimal
		{
			tmp.high = tmp.high.getNext();//skip over that 0
			tmp.high.setPrev(null);//update values
			tmp.digitCount--;
		}
		while (tmp.low.getValue() == 0 && tmp.decimalPlaces > 0)// if there are hanging 0s after decimal
		{
			tmp.low = tmp.low.getPrev();//skip over that 0
			tmp.low.setNext(null); // update values
			tmp.decimalPlaces--;
			tmp.digitCount--;
		}
		return tmp;
	}
	private Number addAbsolute(Number n)
	{
		Number tmp1 = this.align(n); // temp referenes to num1 and num2
		Number tmp2 = n.align(this); // align both of them

		Number sum = new Number(); // sum of both num1 and num2

		Node curr1 = tmp1.low; //start with smallest value of both numbers
		Node curr2 = tmp2.low;

		int carry = 0; //hanging 1 from addition
		int adder = 0; // valued when both pointer values are added

		while (curr1 != null)
		{
			adder = curr1.getValue() + curr2.getValue() + carry;
			if (adder >= 10) // this means not enough space in one node so we need to chop up adder
			{
				adder = (curr1.getValue() + curr2.getValue() + carry)  % 10; // get remainder
				carry = 1; // update carry
			}
			else 
				carry = 0;
			Node newN = new Node(); 
			sum.addFront(newN, adder); // add newNode with value of adder to front
			curr1 = curr1.getPrev(); // update sum number
			curr2 = curr2.getPrev();
		}
		if (carry == 1) // if there is a hanging carry 
		{
			Node newN = new Node();
			sum.addFront(newN, adder); // create a new node for that carry
		}
		sum.decimalPlaces = tmp1.decimalPlaces; //update values
		sum.digitCount = tmp1.digitCount + carry;

		return sum;
	}
	private Number subtractAbsolute(Number n)
	{
		Number sub = new Number(); // value after subtracting num2 from num2

		Number tmp1 = this.align(n); // temp referenes to num1 and num2
		Number tmp2 = n.align(this); // align both of them
		Node curr1 = tmp1.low; // start from end of number
		Node curr2 = tmp2.low;
		int borrow = 0; // will be explained later
		int subtractor = 0; // value when pointed nodes are subtracted
		if (tmp1.compare(tmp2) == -1)//switch around order if this is small then n
		{
			tmp1 = n.align(this); //second number
			tmp2 = this.align(n); // first number
			curr1 = tmp1.low; // update values
			curr2 = tmp2.low;
			sub.reverseSign();
		}
		if (tmp1.compare(tmp2) == 1) // regular subtraction
		{
			while (curr1 != null)
			{
				subtractor = ( curr1.getValue() - curr2.getValue() )  - borrow; // get subtracted value
				if (subtractor < 0) // since node can only hold 0-9 we need to adjust
				{
					subtractor = ( (curr1.getValue() + 10) - curr2.getValue() )  - borrow; // get remainder
					borrow = 1; // tells code next node is value is decremented by 1 to simulate "borrowed"
				}
				else 
					borrow = 0;
				Node newN = new Node();
				sub.addFront(newN, subtractor); // new node with subtracted value 
				curr1 = curr1.getPrev(); // update values for loop
				curr2 = curr2.getPrev();
			}
			sub.decimalPlaces = tmp1.decimalPlaces; // update values
			sub.digitCount = tmp1.digitCount - borrow;
			sub = trim(sub); // gets rid of hanging 0's (if any)
		}
		else //if both the numbers are equal then answer is 0
		{
			Node newN = new Node();
			sub.addFront(newN, subtractor); // add nothing
			sub.decimalPlaces = 0; // update nothing
			sub.digitCount = 0;
			sub.negative = false;
		}
		return sub;
	}
	public Number add(Number n)//Returns a Number which represents "this + n".
	{
		Number ans = new Number();
		if (this.negative == true && n.negative == true) // for -1 + -1
		{
			ans = this.addAbsolute(n);
			ans.negative = true;
		}	
		if (this.negative == false && n.negative == false) // for 1 + 1
		{
			ans = this.addAbsolute(n);
			ans.negative = false;
		}
		if ( this.negative == true && n.negative == false ) // for -1 + 1
		{
			if (this.compare(n) == 1 || this.compare(n) == 0) // handing if num1 < num2 or same
			{
				ans = this.subtractAbsolute(n);
				ans.negative = true;
			}
			if (this.compare(n) == -1  || this.compare(n) == 0) // handing if num1 > num2 or same
			{
				ans = this.subtractAbsolute(n);
				ans.negative = false;
			}
		}
		if ( this.negative == false && n.negative == true ) // for 1 + -1
		{
			if (this.compare(n) == 1 || this.compare(n) == 0) // handing if num1 < num2 or same
			{
				ans = this.subtractAbsolute(n);
				ans.negative = false;
			}
			if (this.compare(n) == -1  || this.compare(n) == 0) // handing if num1 > num2 or same
			{
				ans = this.subtractAbsolute(n);
				ans.negative = true;
			}
		}
		return ans;
	}
	public Number subtract(Number n)//Returns a Number which represents "this - n".
	{
		Number ans = new Number();
		if (this.negative == true && n.negative == true) // if -1 - -1
		{
			if (this.compare(n) == 1 || this.compare(n) == 0) // if num1 > num2 same
			{
				ans = this.subtractAbsolute(n); 
				ans.negative = true;
			}
			if (this.compare(n) == -1 || this.compare(n) == 0) // if num1 < num2 same
			{
				ans = this.subtractAbsolute(n);
				ans.negative = false;
			}
		}
		if (this.negative == false && n.negative == false) // if 1 - 1
		{
			if (this.compare(n) == 1 || this.compare(n) == 0) // if num1 > num2 or same
			{
				ans = this.subtractAbsolute(n);
				ans.negative = false;
			}
			if (this.compare(n) == -1 || this.compare(n) == 0) // if num1 < num2 or same
			{
				ans = this.subtractAbsolute(n);
				ans.negative = true;
			}
		}
		if (this.negative == true && n.negative == false) // if -1 - 1
		{
			ans = this.addAbsolute(n);
			ans.negative = true;
		}
		if (this.negative == false && n.negative == true) // if 1 - -1
		{
			ans = this.addAbsolute(n);
			ans.negative = false;
		}
		return ans;
	}
	public Number multiply(Number n)//Returns a Number which represents "this * n".
	{
		Number tmp1 = this.align(n); // pointer to alinged numbers
		Number tmp2 = n.align(this);

		//////////////////////////////////////
		//for debugging
		//int dec1 = this.decimalPlaces;
		//int dec2 = n.decimalPlaces;
		//System.out.println("Inside multiply absolute");
		//System.out.println("number 1: " + this.toString());
		//System.out.println("number 2: " + n.toString());

		int newDigit = 0; // multipled value of pointed nodes
		Number product = new Number(); // multipled number of num1 and num2
		Node nPtr = tmp2.high; 
		while (nPtr != null)
		{
			Number partialProduct = new Number(); // number to add unto product
			int carry = 0; // used for partialProduct
			Node thisPtr = tmp1.low; 

			Node newN = new Node();
			product.addRear(newN, 0); // add node with value 0 to end 
			while (thisPtr != null)
			{

				newDigit = (nPtr.getValue() * thisPtr.getValue()) + carry; //get multipied value of pointed nodes
				carry = newDigit / 10; // update carry
				newDigit %= 10; // update multiplied value

				Node newN2 = new Node();
				partialProduct.addFront(newN2, newDigit); // add new node with value to front of partial order
				partialProduct.digitCount++; // update values
				thisPtr = thisPtr.getPrev();
			}
			if (carry != 0) // if there is a carry
			{
				Node newNode = new Node();
				partialProduct.addFront(newNode, carry); // add that carry to front of partial order
				partialProduct.digitCount++; // update values
			}
			product.digitCount++; // update values
			product = product.addAbsolute(partialProduct); // add first run of multiplication
			nPtr = nPtr.getNext(); // update value for loop
		}
		product.decimalPlaces = this.decimalPlaces + n.decimalPlaces; // update decimal places
		
		if (this.negative == true && n.negative == true) // account for sign of num1 and num2
			product.negative = false;
		if (this.negative == false && n.negative == true)
			product.negative = true;
		if (this.negative == true && n.negative == false)
			product.negative = true;
		if (this.negative == false && n.negative == false)
			product.negative = false;
		product = product.trim(product); // get rid of hanging 0s
		return product;
	}
	public void reverseSign()//Reverses the value of negative.
	{
		if (this.negative == true) // if sign -
			this.negative = false; // make +
		else 
			this.negative = true; // turn + to -
	}
	public String toString()//Returns a String representation of the Number (so it can be displayed by System.out.print()).
	{
		StringBuilder number = new StringBuilder(); // efficient version of number String representation
		Node cursor = high; // start from begining of number
		int i = 0; // where to place '.'
		int decPt = this.digitCount -this.decimalPlaces; // get value of whole numbers
		if (this.negative == true)  // number is negative 
			number.append("-"); // tac on a '-' for sign
		while (cursor != null) // tac on a '+' 
		{
			if (i == decPt) // -1 is represented as decimal
				number.append('.');
			number.append(cursor.getValue()); // add numeric value as a char 
			cursor = cursor.getNext(); // update value for loop
			i++;
		}
		return number.toString();
	}
}
