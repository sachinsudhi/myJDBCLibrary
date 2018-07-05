package trng.interfac.myJDBCLibrary;

import java.sql.SQLException;

public interface EmployeeService {
	boolean createEmployee(Employee e) throws InvalidSalaryException, ClassNotFoundException, SQLException;

	boolean viewEmployee(int num) throws ClassNotFoundException, SQLException;

	Employee viewAllEmployees(int i) throws ClassNotFoundException, SQLException;

	boolean updateEmployee(Employee e) throws EmployeeNotFoundException, InvalidSalaryException, ClassNotFoundException, SQLException;

	boolean deleteEmployee(int num) throws EmployeeNotFoundException, ClassNotFoundException, SQLException;

	Employee lookUpEmployee(int num) throws ClassNotFoundException, SQLException;

	double employeeHra(Employee e1);

	double employeeGrossSalary(Employee e1);

	void readfromCSVFile(String filename);

	void writeToCSVFile(String filename);
}
