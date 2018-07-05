package trng.interfac.myJDBCLibrary;

import lombok.Data;

@Data
public class Employee {
	private int number;
	private String name;
	private double salary;
	private int age;

	{
		salary = 10000;
	}
	gender g;
	private static String COMPANY_NAME;
	static {
		COMPANY_NAME = "XYZ_COMPANY";
	}

	public static String getCompanyName() {
		return COMPANY_NAME;
	}

	public void setInfo(int number, String name, double salary, int age, int gen) {
		this.number = number;
		this.name = name;
		this.salary = salary;
		this.age = age;
		if (gen == 1)
			g = gender.MALE;
		else if (gen == 2)
			g = gender.FEMALE;
	}

	public void displayInfo() {
		System.out.println("The Employee number is " + this.number + "\n" + "The Employee name is " + this.name + "\n"
				+ "The Employee salary is " + this.salary + "\nThe Employee age is " + this.age + "\n");
		System.out.println("Employee gender:" + g.name() + "\n");
	}

}
