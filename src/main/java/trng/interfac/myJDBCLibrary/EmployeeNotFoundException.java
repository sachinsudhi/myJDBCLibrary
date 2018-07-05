package trng.interfac.myJDBCLibrary;

public class EmployeeNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmployeeNotFoundException(String message, EmployeeNotFoundException ene) {
		super(message, ene);
		super.printStackTrace();
	}

	public EmployeeNotFoundException() {
		// TODO Auto-generated constructor stub
		super();
	}
}