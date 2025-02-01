package hospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Doctor {
	private Connection connection;
public Doctor( Connection connection) {
this.connection=connection;


}


public void viewDoctors() {
	String query="select * from doctors";
	try {
		PreparedStatement ptmt=connection.prepareStatement(query);
		ResultSet rs=ptmt.executeQuery();
		System.out.println("Doctors: ");
		System.out.println("+------------+---------------------+---------------------+");
		System.out.println("| Doctor id  |Name                 | Specialization      |");
		System.out.println("+------------+---------------------+---------------------+");
while(rs.next())
{
	int id=rs.getInt("id");
	String name=rs.getString("name");
	String specialization=rs.getString("Specialization");
	System.out.printf("| %-10s | %-19s |%-21s|\n",id,name,specialization);
	System.out.println("+------------+---------------------+---------------------+");

}
	} catch (Exception e) {
e.printStackTrace();
}
}
public boolean getdoctorById(int id) {
	String query="select * from doctors where id=?";
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
