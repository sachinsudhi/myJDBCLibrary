package trng.interfac.myJDBCLibrary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EmployeeCollectionImplementation implements EmployeeCollection {
	private List<Employee> employees;
	private File file;

	public EmployeeCollectionImplementation(int n, String filename) throws NullPointerException {
		employees = new ArrayList<Employee>(n);
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
					employees.add(i, em);
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
			for (int i = 0; i < employees.size(); i++) {
				if(employees.get(i)!=null)
				{
				fileWriter.append(Integer.toString(employees.get(i).getNumber()));
				fileWriter.append(CommaSEPARATOR);
				fileWriter.append(employees.get(i).getName());
				fileWriter.append(CommaSEPARATOR);
				fileWriter.append(Double.toString(employees.get(i).getSalary()));
				fileWriter.append(CommaSEPARATOR);
				fileWriter.append(Integer.toString(employees.get(i).getAge()));
				fileWriter.append(CommaSEPARATOR);
				if (employees.get(i).getG().equals(gender.MALE))
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
		boolean flag = employees.add(e);
		return flag;
	}

	@Override
	public boolean delete(int num) {
		
		Employee temp = new Employee();
		boolean flag = true;
		Iterator<Employee> iter=employees.iterator();
		while(iter.hasNext()) {
			temp = iter.next();
			int i=employees.indexOf(temp);
			if (temp != null && temp.getNumber() == num) {
				employees.remove(i);
				flag = true;
				break;
			}
		}
		if ( temp == null || temp.getNumber() != num)
			flag = false;
		return flag;

	}

	@Override
	public boolean update(Employee e) {
		Employee temp = new Employee();
		boolean flag = true;
		Iterator<Employee> iter=employees.iterator();
		while(iter.hasNext()) {
			temp = iter.next();
			int i=employees.indexOf(temp);
			if (temp != null && temp.getNumber() == e.getNumber()) {
				employees.set(i, e);
				//employees.add(i, e);
				flag=true;
				break;
			}
		}
		if (temp == null || temp.getNumber() != e.getNumber())
			flag = false;
		return flag;
	}

	@Override
	public boolean display(int num) {
		int i;
		Employee temp = new Employee();
		boolean flag = true;
		for (i = 0; i < employees.size(); i++) {
			temp = employees.get(i);
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
		for (i = 0; i < employees.size(); i++) {
			if (employees.get(i) != null) {
				temp = employees.get(i);
				temp.displayInfo();
			}

		}
		return temp;
	}

	@Override
	public Employee search(int num) {
		int i;
		Employee temp = new Employee();
		for (i = 0; i < employees.size(); i++) {
			temp = employees.get(i);
			if (temp != null && temp.getNumber() == num) {
				return temp;
			}
		}
		temp = null;
		return temp;
	}

}