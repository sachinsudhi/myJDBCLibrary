package trng.interfac.myJDBCLibrary;

import java.sql.SQLException;

public interface EmployeeDaoJDBC {

	boolean add(Employee e) throws ClassNotFoundException, SQLException;

	boolean delete(int num) throws ClassNotFoundException, SQLException;

	boolean update(Employee e) throws SQLException, ClassNotFoundException;

	boolean retrieve(int num) throws SQLException, ClassNotFoundException;

	Employee retrieveAll(int i) throws SQLException, ClassNotFoundException;

	Employee search(int num) throws SQLException, ClassNotFoundException;

	void readCSVFile(String filename);

	void writeCSVFile(String filename);

}
