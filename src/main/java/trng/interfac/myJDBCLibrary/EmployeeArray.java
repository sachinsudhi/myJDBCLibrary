package trng.interfac.myJDBCLibrary;

public interface EmployeeArray {

	boolean add(Employee e);

	boolean delete(int num);

	boolean update(Employee e);

	boolean display(int num);

	Employee displayAll();

	Employee search(int num);

	void readCSVFile(String filename);

	void writeCSVFile(String filename);

}
