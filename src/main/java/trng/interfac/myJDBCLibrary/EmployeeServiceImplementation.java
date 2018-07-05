package trng.interfac.myJDBCLibrary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class EmployeeServiceImplementation implements EmployeeService {
	EmployeeDaoJDBC narr;

	public EmployeeServiceImplementation(int armax) {
		narr = new EmployeeDaoJDBCImplementation(armax);
	}

	@Override
	public void readfromCSVFile(String filename) {
		File file;
		file = new File(filename);
		FileReader reader = null;
		BufferedReader fileReader = null;
		try {
			int i = 0;
			if (!file.exists())
				file.createNewFile();
			String line;
			reader = new FileReader(file);
			fileReader = new BufferedReader(reader);
			fileReader.readLine();// Skip Header line
			while ((line = fileReader.readLine()) != null) {
				String[] fields = line.split(",");
				if (fields.length > 0) {
					Employee em = new Employee();
					em.setInfo(Integer.parseInt(fields[0]), fields[1], Double.parseDouble(fields[2]),
							Integer.parseInt(fields[3]), Integer.parseInt(fields[4]));
					try {
						narr.add(em);
					} catch (ClassNotFoundException | SQLException e) {
     					e.printStackTrace();
					}
					i = i + 1;
				}
			}
		} catch (IOException ie) {
			System.out.println("There was a problem reading the file");
		} finally {
			try {
				reader.close();
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch(NullPointerException ne) {
				ne.printStackTrace();
			}
		}
	}

	@Override
	public void writeToCSVFile(String filename) {
		narr.writeCSVFile(filename);
	}

	@Override
	public boolean createEmployee(Employee e) throws InvalidSalaryException, ClassNotFoundException, SQLException {
		if (e.getSalary() <= 5000) {
			InvalidSalaryException ise = new InvalidSalaryException();
			throw new InvalidSalaryException("Salary range exception", ise);
		}
		boolean outcome;
		outcome = narr.add(e);
		return outcome;

	}

	@Override
	public boolean viewEmployee(int num) throws ClassNotFoundException, SQLException {
		boolean outcome;
		outcome = narr.retrieve(num);
		return outcome;
	}

	@Override
	public Employee viewAllEmployees(int i) throws ClassNotFoundException, SQLException {
		Employee e = new Employee();
		e = narr.retrieveAll(i);
		return e;
	}

	@Override
	public boolean updateEmployee(Employee e) throws EmployeeNotFoundException, InvalidSalaryException, ClassNotFoundException, SQLException {
		if (e.getSalary() <= 5000) {
			InvalidSalaryException ise = new InvalidSalaryException();
			throw new InvalidSalaryException("Salary range exception", ise);
		}
		if (narr.search(e.getNumber()) == null) {
			EmployeeNotFoundException ene = new EmployeeNotFoundException();
			throw new EmployeeNotFoundException("Unknown Employee ID\n", ene);
		}
		boolean outcome;
		outcome = narr.update(e);
		return outcome;
	}

	@Override
	public boolean deleteEmployee(int num) throws EmployeeNotFoundException, ClassNotFoundException, SQLException {
		if (narr.search(num) == null) {
			EmployeeNotFoundException ene = new EmployeeNotFoundException();
			throw new EmployeeNotFoundException("Unknown Employee ID\n", ene);
		}
		boolean outcome;
		outcome = narr.delete(num);
		return outcome;
	}

	@Override
	public Employee lookUpEmployee(int num) throws ClassNotFoundException, SQLException {
		Employee e = new Employee();
		e = narr.search(num);
		return e;
	}

	@Override
	public double employeeHra(Employee e1) {
		double s1 = e1.getSalary();
		int a1 = e1.getAge();
		if (s1 < 10000)
			return 0.15 * s1;
		else if (s1 < 20000)
			return 0.2 * s1;
		else if (s1 < 30000 && a1 >= 40)
			return 0.27 * s1;
		else if (s1 < 30000 && a1 < 40)
			return 0.25 * s1;
		else
			return 0.30 * s1;

	}

	@Override
	public double employeeGrossSalary(Employee e1) {
		double s1 = e1.getSalary();
		int a1 = e1.getAge();
		if (s1 < 10000)
			return (s1 + 0.08 * s1 + 0.15 * s1);
		else if (s1 < 20000)
			return (s1 + 0.1 * s1 + 0.2 * s1);
		else if (s1 < 30000 && a1 >= 40)
			return (s1 + 0.15 * s1 + 0.27 * s1);
		else if (s1 < 30000 && a1 < 40)
			return (s1 + 0.13 * s1 + 0.25 * s1);
		else
			return (s1 + 0.17 * s1 + 0.30 * s1);
	}

}