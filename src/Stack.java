
public class Stack {
	
	private int head = 0;
	private int max_size = 24;
	private int[] array = new int[23];
	
	// Constructor is empty, since it doesn't need to do anything
	Stack(){
	}
	
	// Checks if stack is empty
	public boolean isEmpty(){
		return (head == 0);
	}
	
	// Adds number to stack
	public void push(int i){
		if(head<max_size){
			array[head] = i;
			head++;
		}
	}
	
	// Removes last number from the stack (remember LIFO)
	public int pop(){
		if (isEmpty() == false){
			int poppedNum = array[head-1];
			head--;
			return poppedNum;
		} else {
			return -1;
		}
	}
	
	// Retrieves last number from stack, but doesn't remove it
	public int peek(){
		if (isEmpty() == false){
			int peekedNum = array[head-1];
			return peekedNum;
		} else {
			return -1;
		}
	}
	
	// Checks how many numbers are in the stack
	public int getHead(){
		return head;
	}
	
	// Gets a specific input from the array by using the index number
	public int getArrayVal(int i){
		return array[i];
	}
	
	// Gets the last item added to the stack
	public int getLastArr(){
		return array[head-1];
	}
	
	// Gets the 2nd last item added to the stack
	public int get2ndLastArr(){
		return array[head-2];
	}
}
