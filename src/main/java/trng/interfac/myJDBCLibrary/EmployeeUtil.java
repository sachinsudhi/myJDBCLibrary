package trng.interfac.myJDBCLibrary;

import java.sql.SQLException;
import java.util.Scanner;

public class EmployeeUtil {
	public Employee highestSalary(Employee e1, Employee e2) {
		double s1 = e1.getSalary();
		double s2 = e2.getSalary();
		return s1 > s2 ? e1 : e2;
	}

	public static Employee highestAge(Employee e1, Employee e2) {
		int a1 = e1.getAge();
		int a2 = e2.getAge();
		return a1 > a2 ? e1 : e2;
	}

	public static void incrementSalary(Employee e1) {
		double s1 = e1.getSalary();
		e1.setSalary((s1 + (0.1 * s1)));
	}

	public static void updateSalary(Employee e1) {
		double s1 = e1.getSalary();
		int a1 = e1.getAge();
		if (s1 < 10000 && a1 > 35)
			e1.setSalary((s1 + (0.15 * s1)));

		if (s1 < 15000 && a1 > 45)
			e1.setSalary((s1 + (0.20 * s1)));

		if (s1 < 20000 && a1 > 55)
			e1.setSalary((s1 + (0.25 * s1)));
	}

	public static void displayMenu(EmployeeService ser) {
		try (Scanner ip = new Scanner(System.in)) {
			while (true) {
				System.out.println("\n |||MENU|||\n");
				System.out.println(
						"1.Create Employee\n2.Read/View an Employee\n3.View all employees\n4.Update Employee\n5.Delete Employee\n6.View Employee HRA\n7.View Employee Gross Salary\n8.Exit\n");
				int choice = ip.nextInt();
				switch (choice) {
				case 1:
					Employee e = new Employee();
					e = EmployeeUtil.collectData();
					boolean outcome;
					try {
						outcome = ser.createEmployee(e);
						if (outcome == true)
							System.out.println("Employee record added successfully \n");
						else
							System.out.println("Warning:Cannot add any more employee records\n");
					} catch (InvalidSalaryException ise) {
						ise.getMessage();
						System.out.println("Salary should be greater than 5000\n");
					} catch (ClassNotFoundException e3) {
						e3.printStackTrace();
					} catch (SQLException e3) {
						e3.printStackTrace();
					}

					break;

				case 2:
					System.out.println("Enter the Employee ID to view\n");
					int num1 = ip.nextInt();
					boolean outcome3;
					try {
						outcome3 = ser.viewEmployee(num1);
						if (!outcome3)
							System.out.println("Employee does not exist");
					} catch (ClassNotFoundException e4) {
						e4.printStackTrace();
					} catch (SQLException e4) {
						e4.printStackTrace();
					}
					break;

				case 3:
					Employee e1 = new Employee();
					try {
						System.out.println("Do you wish the data to be sorted?");
						System.out.println("1:Yes 2:No\n");
						int localchoice = ip.nextInt();
						if (localchoice == 1) {
							System.out
									.println("Select the attribute according to which you want to sort the results\n");
							System.out.println("1:ID 2:Name 3:Salary 4:age 5:gender");
							int attribute = ip.nextInt();
							e1 = ser.viewAllEmployees(attribute);
						} else if (localchoice == 2) {
							e1 = ser.viewAllEmployees(-1);
							System.out.println("Default order set");
						} else {
							System.out.println("Please select a valid option\n");
						}

					} catch (ClassNotFoundException | SQLException e3) {
						e3.printStackTrace();
					}
					if (e1 == null)
						System.out.println("Employee data does not exist/not yet available");
					break;

				case 4:
					Employee e2 = new Employee();
					e2 = EmployeeUtil.collectData();
					boolean outcome2;
					try {
						outcome2 = ser.updateEmployee(e2);
						if (outcome2)
							System.out.println("Employee successfully updated");
					} catch (EmployeeNotFoundException ene) {
						ene.getCause();
						System.out.println("Given ID is invalid. Please provide a valid employee ID\n");
					} catch (InvalidSalaryException ise) {
						ise.getMessage();
						System.out.println("Salary should be greater than 5000\n");
					} catch (ClassNotFoundException e3) {
						e3.printStackTrace();
					} catch (SQLException e3) {
						e3.printStackTrace();
					}
					break;

				case 5:
					System.out.println("Enter the Employee ID to delete\n");
					int num3 = ip.nextInt();
					boolean outcome1;
					try {
						outcome1 = ser.deleteEmployee(num3);
						if (outcome1)
							System.out.println("Employee successfully deleted");
					} catch (EmployeeNotFoundException ene) {
						ene.getCause();
						System.out.println("Given ID is invalid. Please provide a valid employee ID\n");
					} catch (ClassNotFoundException e3) {
						e3.printStackTrace();
					} catch (SQLException e3) {
						e3.printStackTrace();
					}
					break;

				case 6:
					System.out.println("Enter the employee ID who's HRA you wish to see\n");
					int num5 = ip.nextInt();
					Employee temp = new Employee();
					try {
						temp = ser.lookUpEmployee(num5);
					} catch (ClassNotFoundException e3) {
						e3.printStackTrace();
					} catch (SQLException e3) {
						e3.printStackTrace();
					}
					if (temp == null)
						System.out.println("Employee with the given ID does not exist");
					else {
						double ghra = ser.employeeHra(temp);
						System.out.println("HRA for the Employee is " + ghra + "\n");
					}
					break;

				case 7:
					System.out.println("Enter the employee ID who's gross salary you wish to see\n");
					int num6 = ip.nextInt();
					Employee temp1 = new Employee();
					try {
						temp1 = ser.lookUpEmployee(num6);
					} catch (ClassNotFoundException e3) {
						e3.printStackTrace();
					} catch (SQLException e3) {
						e3.printStackTrace();
					}
					if (temp1 == null)
						System.out.println("Employee with the given ID does not exist");
					else {
						double gross = ser.employeeGrossSalary(temp1);
						System.out.println("Gross salary for the Employee is " + gross + "\n");
					}
					break;

				default:
					// ser.writeToCSVFile(filename);
					// System.out.println(filename+" saved in current workspace folder.");
					System.out.println("\nThanks for using our System. Goodbye\n");
					System.exit(0);
					break;

				}
			}
		}
	}

	public static Employee createEmployeeObject(int num, String nam, double sal, int ag, int nu) {
		Employee e1 = new Employee();
		e1.setInfo(num, nam, sal, ag, nu);
		return e1;
	}

	public static Employee collectData() {
		Employee e = new Employee();
		Scanner ip = new Scanner(System.in);
		System.out.println("Enter the Employee ID\n");
		int num = ip.nextInt();
		System.out.println("Enter the Employee name \n");
		String nam = ip.next();
		System.out.println("Enter the Employee Salary\n");
		double sal = ip.nextDouble();
		System.out.println("Enter the Employee age\n");
		int ag = ip.nextInt();
		System.out.println("Enter the Employee gender\n1.Male\n2.Female\n");
		int nu = ip.nextInt();
		e.setInfo(num, nam, sal, ag, nu);
		return e;
	}
}