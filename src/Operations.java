public class Operations {
	private Stack rpnStack;
	/*
	 * The datatype long is used so that we can test if the result of the 
	 * operators is larger/smaller than 2147483647/-2147483647 accordingly
	 * This is sufficient, since it will also be impossible to input 
	 * numbers larger/smaller than 2147483647/-2147483647
	 */
	private long result;
	
	/*
	 * Constructor
	 */
	public Operations(){
		rpnStack = new Stack();
	}
	
	/*
	 * The following method is called whenever a number 
	 * needs to be added to the stack
	 */
	public void addToStack(String input){
		// If the stack already reached its maximum limit, the following error is produced
		if (rpnStack.getHead() == 23){
			System.err.println("Stack overflow.");
		} else if (input.length() > 13){
			/*
			 * This first method of saturation checks if there are too many characters in the number
			 * If there are too many characters, then the max/min value will be pushed to the stack
			 */
			if (input.charAt(0) == '-'){
				rpnStack.push(-2147483648);
			} else {
				rpnStack.push(2147483647);
			}
		} else {
			
			/*
			 * If the previous saturation was not applied to the input, then the following one will be
			 * This one checks the actual value of the number as a long datatype, then compares it to the max/min
			 * and processes it accordingly
			 */
			
			long number = Long.parseLong(input);
			
			if (number > 2147483647){
				rpnStack.push(2147483647);
			} else if (number < -2147483648){
				rpnStack.push(-2147483648);
			} 
			
			// The following condition handles positive octals
			else if (input.charAt(0) == '0'){
				/*
				 * The following FOR loop runs through the entire length of the number string
				 * If it comes across an 8 or a 9, it will break out of the for loop
				 * This is because octal doesn't have base 8 or 9
				 */
				int finalValue = 0;
				for (int i=1; i<input.length(); i++){
					int currNumber = Character.getNumericValue(input.charAt(i));
					if (currNumber>=8){
						break;
					} else {
					finalValue = finalValue * 8 + currNumber;
					}
				}
				// Once the entire octal is processed, it is added to the stack
				rpnStack.push(finalValue);
				
			// THe following condition handles negative octals
			} else if (input.charAt(0) == '-' && input.charAt(1) == '0'){
				/*
				 * The same as the positive octal implementation
				 * However, this starts at index 2, not 1, because it needs
				 * to account for the negative character
				 */
			
				int finalValue = 0;
				for (int i=2; i<input.length(); i++){
					int currNumber = Character.getNumericValue(input.charAt(i));
					if (currNumber>=8){
						break;
					} else {
					finalValue = finalValue * 8 + currNumber;
					}
				}
				// Before pushing the number to the stack, we make it negative, since this is a negative octal
				finalValue = finalValue * -1;
				rpnStack.push(finalValue);
			} 
			
			/*
			 * If the number doesn't need to be saturated nor is an octal
			 * Then it will be handled normally, and just pushed to the stack
			 * It must first be wrapped into an integer though, since the 
			 * stack only accepts integers
			 */
			else {
				int intNumber = (int) number;
				rpnStack.push(intNumber);
			}
		}
	}
	
	/*
	 * The following method is called by the operators in order to handle saturation
	 * It is private, since it will only be called by methods inside this class
	 */
	
	private void saturatedHandling(){
		if (result > 2147483647){
			rpnStack.pop();
			rpnStack.pop();
			rpnStack.push(2147483647);
		} else if (result < -2147483648){
			rpnStack.pop();
			rpnStack.pop();
			rpnStack.push(-2147483648);
		} else {
			int intResult = (int) result;
			rpnStack.pop();
			rpnStack.pop();
			rpnStack.push(intResult);
		}
	}
	
	public void add(){
		/*
		 * If there aren't enough operators (the stack has 1 or 
		 * less elements in it), then stack underflow is printed
		 */
		if (rpnStack.getHead() <= 1){
			System.err.println("Stack underflow.");
		} else {
			/* 
			 * Otherwise, the result is calculated, which may be larger than 2147483647
			 * or smaller than -2147483647
			 * This is okay, since saturatedHandling can process it accordingly
			 */
			result = (long) rpnStack.getLastArr() + (long) rpnStack.get2ndLastArr();
			saturatedHandling();
		}
	}
	
	public void subtract(){
		if (rpnStack.getHead() <= 1){
			System.err.println("Stack underflow.");
		} else{
			result = (long) rpnStack.get2ndLastArr() - (long) rpnStack.getLastArr();
			saturatedHandling();
		}
	}
	
	public void multiply(){
		if (rpnStack.getHead() <= 1){
			System.err.println("Stack underflow.");
		} else{
			result = (long) rpnStack.get2ndLastArr() * (long) rpnStack.getLastArr();
			saturatedHandling();
		}
	}
	
	public void divide(){
		if (rpnStack.getHead() <= 1){
			System.err.println("Stack underflow.");
		} else{
			// Divide by zero error
			if (rpnStack.getArrayVal(rpnStack.getHead()-1) == 0){
				System.err.println("Divide by 0.");
			} else {
			result = (long) rpnStack.get2ndLastArr() / (long) rpnStack.getLastArr();
			saturatedHandling();
			}
		}
	}
	
	public void exponent(){
		if (rpnStack.getHead() <= 1){
			System.err.println("Stack underflow.");
		} else{
			// Negative power error
			if (rpnStack.getArrayVal(rpnStack.getHead()-1) < 0){
				System.err.println("Negative power.");
			} else {
			double one = rpnStack.get2ndLastArr();
			double two = rpnStack.getLastArr();
			result = (long) Math.pow(one, two);
			saturatedHandling();
			}
		}
	}
	
	public void modulo(){
			if (rpnStack.getHead() <= 1){
				System.err.println("Stack underflow.");
			} else{
				// Divide by zero error
				if (rpnStack.getArrayVal(rpnStack.getHead()-1) == 0){
					System.err.println("Divide by 0.");
				} else {
				result = rpnStack.get2ndLastArr() % rpnStack.getLastArr();
				saturatedHandling();
				}
			}
	}
	
	// The following method is used when 'd' is input
	public void printStack(){
		int start = 0;
		int end = rpnStack.getHead();
		for (start = 0; start<end; start++){
			System.out.println(""+rpnStack.getArrayVal(start));
		}
	}
	
	// The following method is used when '=' is input
	public void equals(){
		// Stack empty error
		if (rpnStack.getHead() == 0){
			System.err.println("Stack empty.");
		} else {
		System.out.println(""+rpnStack.getLastArr());
		}
	}
	
	public void unrecognizedInput(String in){
		System.err.println("Unrecognized operator or operand \""+in+"\".");
	}
	
	public void randomNumber(int i){
		// The following 100 numbers were retrieved from the LCPU SRPN
		int[] randNumberArray = {1804289383,846930886,1681692777,1714636915,1957747793,424238335,719885386,1649760492,596516649,1189641421,1025202362,1350490027,783368690,1102520059,2044897763,1967513926,1365180540,1540383426,304089172,1303455736,35005211,521595368,294702567,1726956429,336465782,861021530,278722862,233665123,2145174067,468703135,1101513929,1801979802,1315634022,635723058,1369133069,112589167,1059961393,2089018456,628175011,1656478042,1131176229,1653377373,859484421,1914544919,608413784,756898537,1734575198,1973594324,149798315,2038664370,1129566413,184803526,412776091,1424268980,1911759956,749241873,137806862,42999170,982906996,135497281,511702305,2084420925,1937477084,1827336327,572660336,1159126505,805750846,1632621729,1100661313,1433925857,1141616124,84353895,939819582,2001100545,1998898814,1548233367,610515434,1585990364,1374344043,760313750,1477171087,356426808,945117267,1889947178,1780695788,709393584,491705403,1918502651,752392754,1474612399,2053999932,1264095060,1411549676,1843993368,943947739,1984210012,855636226,1749698586,1469348094,1956297539};
		if (rpnStack.getHead() == 23){
			System.err.println("Stack overflow.");
		} else {
		rpnStack.push(randNumberArray[i]);
		}
	}
}