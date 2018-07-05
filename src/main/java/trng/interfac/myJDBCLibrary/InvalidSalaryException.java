package trng.interfac.myJDBCLibrary;

public class InvalidSalaryException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidSalaryException(String message, InvalidSalaryException ise) {
		super(message, ise);
		super.printStackTrace();
	}

	public InvalidSalaryException() {
		// TODO Auto-generated constructor stub
		super();
	}
}