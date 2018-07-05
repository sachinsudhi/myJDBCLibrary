package trng.interfac.myJDBCLibrary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class EmployeeDaoJDBCImplementation implements EmployeeDaoJDBC {
	private List<Employee> employees;
	private File file;
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://127.0.0.1/employees";
	static final String USER = "root";
	static final String PASS = "stormbreaker";
	private static final gender MALE = null;

	public EmployeeDaoJDBCImplementation(int n) throws NullPointerException {
		employees = new ArrayList<Employee>(n);
	}

	@Override
	public void readCSVFile(String filename) {
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
						add(em);
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
				if (employees.get(i) != null) {
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
		} catch (NullPointerException ne) {
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
	public boolean add(Employee e) throws SQLException, ClassNotFoundException  {
		boolean flag = false;
		PreparedStatement pstmt = null;
		Class.forName(JDBC_DRIVER);
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)){
		String sql = "INSERT INTO employees " + "VALUES (?, ?, ?, ?, ?)";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, e.getNumber());
		pstmt.setString(2, e.getName());
		pstmt.setDouble(3, e.getSalary());
		pstmt.setInt(4, e.getAge());
		pstmt.setInt(5, e.getG().equals(gender.MALE) ? 1 : 2);
		int status = pstmt.executeUpdate();
		if (pstmt != null)
			conn.close();
		if (status > 0)
			flag = true;
		return flag;
		}
	}

	@Override
	public boolean delete(int num) throws ClassNotFoundException, SQLException {
		boolean flag=false;
		Connection conn = null;
		   PreparedStatement pstmt = null;
	      Class.forName(JDBC_DRIVER);
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);
	      String sql = "DELETE FROM employees where id=?";
	      pstmt = conn.prepareStatement(sql);
	      pstmt.setInt(1, num);
	      int status = pstmt.executeUpdate();
	         if(pstmt!=null)
	            conn.close();
	         if(conn!=null)
	            conn.close();
		if (status>0)
			flag = true;
		return flag;

	}

	@Override
	public boolean update(Employee e) throws SQLException, ClassNotFoundException {
		boolean flag=false;
		Connection conn = null;
		   PreparedStatement pstmt = null;
	      Class.forName(JDBC_DRIVER);
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);
	      String sql = "UPDATE employees SET name=?, salary=?,age=?,gender=? where id=?";
	      pstmt = conn.prepareStatement(sql);
	      pstmt.setString(1, e.getName());
	      pstmt.setDouble(2, e.getSalary());
	      pstmt.setInt(3, e.getAge());
	      pstmt.setInt(4, e.getG().equals(gender.MALE) ? 1 : 2);
	      pstmt.setInt(5, e.getNumber());
	      int status = pstmt.executeUpdate();
	         if(pstmt!=null)
	            conn.close();
	         if(conn!=null)
	            conn.close();
		if (status>0)
			flag = true;
		return flag;
	}

	@Override
	public boolean retrieve(int num) throws SQLException, ClassNotFoundException {
		boolean flag=true;
		Employee temp = new Employee();
		Connection conn = null;
		   PreparedStatement pstmt = null;
	      Class.forName(JDBC_DRIVER);
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);
	      String sql = "SELECT id, name, salary, age, gender FROM employees where id=?";
	      pstmt = conn.prepareStatement(sql);
	      pstmt.setInt(1, num);
	      ResultSet rs = pstmt.executeQuery();
	      while(rs.next()){
	          temp.setInfo(rs.getInt("id"),rs.getString("name"),rs.getDouble("salary"),rs.getInt("age"),rs.getInt("gender"));
	          if(temp.getNumber()==num)
	          {
                 temp.displayInfo();
                 break;
	          }
	      }
	      rs.close();
	         if(pstmt!=null)
	            conn.close();
	         if(conn!=null)
	            conn.close();
		if (temp == null || temp.getNumber() != num)
			flag = false;
		return flag;
	}

	@Override
	public Employee retrieveAll(int i) throws SQLException, ClassNotFoundException {
		Employee temp = new Employee();
		Connection conn = null;
		   Statement stmt = null;
		   String sql="SELECT id, name, salary, age, gender FROM employees";
	      Class.forName(JDBC_DRIVER);
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);
	      stmt = conn.createStatement();
	      if(i==-1)
	      sql = "SELECT id, name, salary, age, gender FROM employees";
	      else if(i==1)
	      sql = "SELECT id, name, salary, age, gender FROM employees ORDER BY id";
	      else if(i==2)
		      sql = "SELECT id, name, salary, age, gender FROM employees ORDER BY name";
	      else if(i==3)
		      sql = "SELECT id, name, salary, age, gender FROM employees ORDER BY salary";
	      else if(i==4)
		      sql = "SELECT id, name, salary, age, gender FROM employees ORDER BY age";
	      else if(i==5)
		      sql = "SELECT id, name, salary, age, gender FROM employees ORDER BY gender";
	      ResultSet rs = stmt.executeQuery(sql);
	      while(rs.next()){
	          temp.setInfo(rs.getInt("id"),rs.getString("name"),rs.getDouble("salary"),rs.getInt("age"),rs.getInt("gender"));
	          temp.displayInfo();
	      }
	      rs.close();
	         if(stmt!=null)
	            conn.close();
	         if(conn!=null)
	            conn.close();
		return temp;
	}

	@Override
	public Employee search(int num) throws SQLException, ClassNotFoundException {
		Employee temp = new Employee();
		Connection conn = null;
		   Statement stmt = null;
	      Class.forName(JDBC_DRIVER);
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);
	      stmt = conn.createStatement();
	      String sql = "SELECT id, name, salary, age, gender FROM employees";
	      ResultSet rs = stmt.executeQuery(sql);
	      while(rs.next()){
	          temp.setInfo(rs.getInt("id"),rs.getString("name"),rs.getDouble("salary"),rs.getInt("age"),rs.getInt("gender"));
	          if(temp.getNumber()==num)
	        	  return temp;
	      }
	      rs.close();
	         if(stmt!=null)
	            conn.close();
	         if(conn!=null)
	            conn.close();
		temp = null;
		return temp;
	}

}