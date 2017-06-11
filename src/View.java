import java.util.Scanner;

public class View {
	private Scanner input;
	private Operations op;
	private int randNumPicker;
	
	public View(){
		/*
		 * Scanner is used to retrieve user input
		 * op refers to the operations class, which handles all input
		 * randNumPicker is used for when 'r' is entered
		 * 	This is an array of 100 random numbers, which were retrieve from the provided LCPU SRPN
		 */
		input = new Scanner(System.in);
		op = new Operations();
		randNumPicker = 0;
	}
	
	public void getInput(){
		/*
		 * This while loop will continue to run, since it will always be true
		 */
		int j = 0;
		while(j<1){
			
			String inputValue = input.nextLine();
			
			/* 
			 * The following boolean is checked before processing input
			 * If it is ever true, then a '-' will be added to
			 * the beginning of the number string before processing
			 * it
			 */
			boolean isNegative = false;
			
			// Numbers are concatenated into this string
			String tempNumString = "";
			
			// This for loop runs for the length of the line of input provided by the user
			for (int i=0;i<inputValue.length(); i++){
				// Breaks the loop if a comment is detected
				if (inputValue.charAt(i) == '#'){
					break;
				}
				
				// Checking if current character is a number
				boolean isNum = Character.isDigit(inputValue.charAt(i));
				
				// Checking if next character is also a number
				boolean isNextNum = false;
				if (i<inputValue.length()-1){
					isNextNum = Character.isDigit(inputValue.charAt(i+1));
				}
				
				/*
				 * The IF/ELSE IF statements are used to process input
				 */
				
				if (isNum == true){
					tempNumString += inputValue.charAt(i);
				} 
				
				else if (isNum == false){
					/*
					 * If it is a '-' followed by a number, the isNegative flag is set to true
					 * Before this is done, if there is anything in the tempNumString, it is
					 * processed first
					 */
					if (inputValue.charAt(i) == '-' && isNextNum == true){
						// Processing tempNumString first
						if (tempNumString != ""){
							if (isNegative == true){
								String tempNegNum = "-"+tempNumString;
								processInput(tempNegNum);
								tempNumString = "";
								tempNegNum = "";
								} else {
									processInput(tempNumString);
									tempNumString = "";
							}
						}
						
						// After processing tempNumString (if we needed to), the flag is then set
						isNegative = true;
					}
					
					/*
					 * Otherwise, the program now knows that the tempNumString is complete
					 * This means that whatever is in tempNumString must be processed
					 * The program checks if the negative flag is up at this point, in order to process the tempNumString accordingly
					 * If the negative flag IS up, then it will add a '-' to the beginning of the string, by creating a new temporary negative string
					 * After the number is processed, then the current character must also be processed
					 * After all this is done, flags and temporary strings are reset
					 */
						
					else {
						// Processing the number string if it is negative
						if (isNegative == true){
							String tempNegNum = "-"+tempNumString;
							processInput(tempNegNum);
							String tempNonNumString = Character.toString(inputValue.charAt(i));
							processInput(tempNonNumString);
							tempNumString = "";
							tempNegNum = "";
							// Resetting the isNegative flag
							isNegative = false;
						} 
						
						// Processing the number string if it is not negative (i.e. positive)
						// Also processing the current character
						else {
							processInput(tempNumString);
							tempNumString = "";
							String tempNonNumString = Character.toString(inputValue.charAt(i));
							processInput(tempNonNumString);
							
						}
					}
				}
			}
			
			/*
			 * When the loop ends with a number, then the tempNumString will still not have been processed
			 * Therefore, the code below ensures that the tempNumString always gets processed
			 * It is not a problem if empty quotes "" get processed, because the processor method can handle this
			 */

				if (isNegative == true){
					String tempNegNum = "-"+tempNumString;
					processInput(tempNegNum);
					tempNumString = "";
					tempNegNum = "";
					isNegative = false;
				} else {
					processInput(tempNumString);
					tempNumString = "";
			}
		}
	}
	
	
	/*
	 * The following method receives a string input, and processes it
	 */
	
	private void processInput(String input){
		
		/*
		 * The following flag is used to check if input is a number
		 * If the string is longer than 1 character, then the first character 
		 * may be a negative, which means it is still a number
		 * Therefore, both the first and second characters are checked in this case
		 * Otherwise, only the first character needs to be checked
		 */
		boolean isNum = false;
		
		// If the string is longer than 1 character, then it may be a negative number
		if (input.length()>1){
			boolean checkCharOne = Character.isDigit(input.charAt(0));
			boolean checkCharTwo = Character.isDigit(input.charAt(1));
			if (checkCharOne == true || checkCharTwo == true){
				isNum = true;
			}
		} 
		
		// If the string is just one character, then its either a number or not, so only this character is checked
		else if (input.length()==1){
			boolean checkCharOne = Character.isDigit(input.charAt(0));
			if (checkCharOne == true){
				isNum = true;
			}
		}
		
		/*
		 * The following statements send the input to the Operations class accordingly
		 */
		
		if (isNum == true){
			op.addToStack(input);
		}
		
		else if(input.equals("+")){
			op.add();
		} 
		
		else if (input.equals("-")){
			op.subtract();
		} 
		
		else if (input.equals("*")){
			op.multiply();
		} 
		
		else if (input.equals("/")){
			op.divide();
		} 
		
		else if (input.equals("^")){
			op.exponent();
		} 
		
		else if (input.equals("%")){
			op.modulo();
		} 
		
		else if (input.equals("d")){
			op.printStack();
		} 
		
		else if (input.equals("r")){
			// In case the last number of the random number array is reached, wrap around back to the first one
			if (randNumPicker == 99){
				randNumPicker = 0;
			}
			op.randomNumber(randNumPicker);
			// Must increment the randNumPicker counter by 1
			randNumPicker++;
		} 
		
		else if (input.equals("=")){
			op.equals();
		}
		
		/*
		 * The method which reads input sometimes feeds this method an empty string "", or a string with a space " "
		 * The following statements ensure that the program can handle this
		 * Since the program should ignore these inputs, the braces are empty
		 */
		
		else if (input.equals(" ")){
		}
		
		else if (input.equals("")){
		}
		
		/*
		 * If none of the previous conditions were satisfied
		 * then this is an unrecognized character
		 */
		else {
			op.unrecognizedInput(input);
		}
	}
}