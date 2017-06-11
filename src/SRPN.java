public class SRPN {

	public static void main(String[] args) {
		
		/* The view class gets input, and processes it.
		 * It contains a loop, which allows this program to keep getting input
		 * The TRY CATCH is used to handle EOF
		 */
		
		try {
		View newView = new View();
		newView.getInput();
		} catch (Exception e){
			return;
		}
		
	}
}