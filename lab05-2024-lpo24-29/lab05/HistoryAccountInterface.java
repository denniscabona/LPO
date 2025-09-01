package lab05;

public interface HistoryAccountInterface extends CreditAccountInterface {
	/*
	 * undo the previous withdraw/deposit operation
	 * 
	 * throws an exception of class IllegalStateException if
	 * 
	 * there was no previous operation or
	 * 
	 * the previous operation has been already undone (that is, undo() cannot be repeated)
	 * 
	 * returns the updated balance
	 * 
	 */
	int undo();

	/*
	 * redo the previous withdraw/deposit operation
	 * 
	 * throws an exception of class IllegalStateException if
	 * 
	 * there was no previous operation 
	 * 
	 * redo() can be repeated an arbitrary number of times
	 * 
	 * returns the updated balance
	 * 
	 */

	
	int redo();

}
