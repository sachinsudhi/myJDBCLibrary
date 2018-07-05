package trng.interfac.myJDBCLibrary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EmployeeArrayImplementation implements EmployeeArray {

	private Employee[] employees;
	private File file;

	public EmployeeArrayImplementation(int n, String filename) {
		employees = new Employee[n];
		file = new File(filename);
	}
@Override
public void readCSVFile(String filename) {
	FileReader reader = null;
	BufferedReader fileReader = null;
	try {
		int i=0;
		if (!file.exists())
			file.createNewFile();
		String line;
		reader = new FileReader(file);
		fileReader = new BufferedReader(reader);
		fileReader.readLine();// Skip Header line
		while ((line = fileReader.readLine()) != null) {
			String[] fields = line.split(",");
				if(fields.length>0) {
				Employee em=new Employee();
				em.setInfo(Integer.parseInt(fields[0]), fields[1], Double.parseDouble(fields[2]),
					Integer.parseInt(fields[3]), Integer.parseInt(fields[4]));
				employees[i]=em;
				i=i+1;
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
		}
	}
}

@Override
public void writeCSVFile(String filename) {
	FileWriter fileWriter = null;
	try {
		fileWriter = new FileWriter(filename);
		String HEADERline = "EmpID, Name, Salary, Age, Gender";
		String CommaSEPARATOR = ",";
		String lineSEPARATOR = "\n";
		fileWriter.append(HEADERline);
		fileWriter.append(lineSEPARATOR);
		for (int i = 0; i < employees.length; i++) {
			if(employees[i]!=null)
			{
			fileWriter.append(Integer.toString(employees[i].getNumber()));
			fileWriter.append(CommaSEPARATOR);
			fileWriter.append(employees[i].getName());
			fileWriter.append(CommaSEPARATOR);
			fileWriter.append(Double.toString(employees[i].getSalary()));
			fileWriter.append(CommaSEPARATOR);
			fileWriter.append(Integer.toString(employees[i].getAge()));
			fileWriter.append(CommaSEPARATOR);
			if (employees[i].getG().equals(gender.MALE))
				fileWriter.append(Integer.toString(1));
			else
				fileWriter.append(Integer.toString(2));
			fileWriter.append(lineSEPARATOR);
			}
		}
	} catch (IOException ie) {
		System.out.println("There was a problem writing to the file");
	}catch (NullPointerException ne) {
		System.out.println("Saving file...");
	} finally {
		try {
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("There was a problem flushing/closing the fileWriter");
			e.printStackTrace();
		}
	}
}

@Override
public boolean add(Employee e) {
	boolean flag = false;
	int i;
	for (i = 0; i < employees.length; i++) {
		if (employees[i] == null) {
			employees[i] = e;
			flag = true;
			break;
		}
	}
	if (i == employees.length - 1)
		flag = false;
	return flag;
}

@Override
public boolean delete(int num) {
	int i;
	Employee temp = new Employee();
	boolean flag = true;
	for (i = 0; i < employees.length; i++) {
		temp = employees[i];
		if (temp != null && temp.getNumber() == num) {
			employees[i] = null;
			flag = true;
			break;
		}
	}
	if (i == employees.length || temp == null || temp.getNumber() != num)
		flag = false;
	// Shifting logic
	if (temp != null && temp.getNumber() == num) {
		for (int j = i + 1; j < employees.length; j++) {
			employees[j - 1] = employees[j];
		}
		employees[employees.length - 1] = null;
	}
	return flag;

}

@Override
public boolean update(Employee e) {
	int i;
	Employee temp = new Employee();
	boolean flag = true;
	for (i = 0; i < employees.length; i++) {
		temp = employees[i];
		if (temp != null && temp.getNumber() == e.getNumber()) {
			employees[i] = e;
			flag = true;
			break;
		}

	}
	if (i == employees.length || temp == null || temp.getNumber() != e.getNumber())
		flag = false;
	return flag;
}

@Override
public boolean display(int num) {
	int i;
	Employee temp = new Employee();
	boolean flag = true;
	for (i = 0; i < employees.length; i++) {
		temp = employees[i];
		if (temp != null && temp.getNumber() == num) {
			temp.displayInfo();
			break;
		}
	}
	if (temp == null || temp.getNumber() != num)
		flag = false;
	return flag;
}

@Override
public Employee displayAll() {
	int i;
	Employee temp = new Employee();
	for (i = 0; i < employees.length; i++) {
		if (employees[i] != null) {
			temp = employees[i];
			temp.displayInfo();
		}

	}
	return temp;
}

@Override
public Employee search(int num) {
	int i;
	Employee temp = new Employee();
	for (i = 0; i < employees.length; i++) {
		temp = employees[i];
		if (temp != null && temp.getNumber() == num) {
			return temp;
		}
	}
	temp = null;
	return temp;
}

}
