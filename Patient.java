package hospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Patient {
	private Connection connection;
	private Scanner scanner;
public Patient( Connection connection,Scanner scanner) {
this.connection=connection;
this.scanner=scanner;


}

public void addPatient() {
	System.out.println("Enter Patient Name :");
	String name=scanner.next();
	System.out.println("Enter Patient Age");
	int age=scanner.nextInt();
	System.out.println("Enter Patient Gender");
	String gender=scanner.next();
	
	
	try {
		String query="Insert into patients(name,age,gender)values(?,?,?)";
		PreparedStatement ptmt=connection.prepareStatement(query);
		ptmt.setString(1,name);
		ptmt.setInt(2, age);
		ptmt.setString(3,gender);
		int affectedRows=ptmt.executeUpdate();
		if(affectedRows>0)
		{
			System.out.println("Patient Added Successfully");
		}
		else {
			System.out.println("Faild to add patient !!");
		}
		
		
	} catch (Exception e) {
e.printStackTrace();
	}
}
public void viewPatients() {
	String query="select * from patients";
	try {
		PreparedStatement ptmt=connection.prepareStatement(query);
		ResultSet rs=ptmt.executeQuery();
		System.out.println("Patients: ");
		System.out.println("+-------------+----------------------+------------+--------------+");
		System.out.println("| Patient id  | Name                 | Age        | Gender       |");
		System.out.println("+-------------+----------------------+------------+--------------+");
while(rs.next())
{
	int id=rs.getInt("id");
	String name=rs.getString("name");
	int age=rs.getInt("age");
	String gender=rs.getString("gender");
	System.out.printf("| %-11s | %-20s | %-10s | %-12s |\n",id,name,age,gender);
	System.out.println("+-------------+----------------------+------------+--------------+");

}
	} catch (Exception e) {
e.printStackTrace();
}
}
public boolean getPatientById(int id) {
	String query="select * from patients where id=?";
	try {
		PreparedStatement ptmt=connection.prepareStatement(query);
		ptmt.setInt(1, id);
		ResultSet rs=ptmt.executeQuery();
		if(rs.next())
		{
			return true;
		}
		else {
			return false;
		}
	} catch (Exception e) {
e.printStackTrace();
}
	return false;

	
}
}
