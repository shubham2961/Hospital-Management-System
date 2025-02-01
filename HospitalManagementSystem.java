package hospitalManagement;

import java.nio.channels.SelectableChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class HospitalManagementSystem {
private static final String url="jdbc:mysql://localhost:3306/hospital";
private static final String username="root";
private static final String password="shubham@123";

public static void main(String[] args) {
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		
	} catch (Exception e) {
e.printStackTrace();	}
	Scanner scanner=new Scanner(System.in);
	try {
		Connection con=DriverManager.getConnection(url,username,password);
		Patient patient=new Patient(con,scanner);
		Doctor doctor=new Doctor(con);
		while(true)
		{
			System.out.println("HOSPITAL MANAGEMENT SYSTEM");
			System.out.println("1. Add Patients");
			System.out.println("2. View Patients");
			System.out.println("3. View Doctors");
			System.out.println("4. Book Appointment");
			System.out.println("5. Exit");
			System.out.println("Enter your choise");
			int choise=scanner.nextInt();
			switch (choise) {
			case 1:
				patient.addPatient();
				break;
				case 2:
					patient.viewPatients();
					break;
					case 3:
						doctor.viewDoctors();
						break;
						case 4:
							bookAppointment(patient, doctor, con, scanner);
							break;
							case 5:
								System.out.println("Thank you for using hospital management system");
								return;
								
				

			default:
				System.out.println("Enter valid choise!!!");
				break;
			}
		}
		
		
	} catch (Exception e) {
e.printStackTrace();	}
}
public static void bookAppointment(Patient patient,Doctor doctor, Connection connection,Scanner scanner) {
	 System.out.println("Enter Patient Id: ");
	 int patientid=scanner.nextInt();
	 System.out.println("Enter Doctor Id: ");
	 int doctorid=scanner.nextInt();
	 System.out.println("Enter appoinment data(YYYY-MM-DD) : ");
	 String appointmentDate=scanner.next();
	 if(patient.getPatientById(patientid)&& doctor.getdoctorById(doctorid))
	 {
		 if(checkDoctorAvailability(doctorid,appointmentDate,connection)) {
			 String appointmentQuery="insert into appointments(patient_id,doctor_id,appointment_data)values(?,?,?)";
		 try {
			PreparedStatement ptmt=connection.prepareStatement(appointmentQuery);
			ptmt.setInt(1,patientid);
			ptmt.setInt(2, doctorid);
			ptmt.setString(3, appointmentDate);
			int rowAffected=ptmt.executeUpdate();
			if(rowAffected>0) {
				System.out.println("Appointment Booked!");
			}
			else {
				System.out.println("Failed to Book appointment");
			}
			
		} catch (Exception e) {
e.printStackTrace();		}
		 }
		 else {
			System.out.println("Doctor not available on this data!!");
		}
	 }
	 else {
		System.out.println("Either doctor or patient doesn't exist!!");
	}

}
public static boolean checkDoctorAvailability(int doctorid, String appointmentDate,Connection connection)
{
	String query="Select count(*) from appointments where doctor_id=? and appointment_data=?" ;
	try {
		PreparedStatement ptmt=connection.prepareStatement(query);
		ptmt.setInt(1, doctorid);
		ptmt.setString(2,appointmentDate);
		ResultSet rs=ptmt.executeQuery();
		if(rs.next())
		{
			int count=rs.getInt(1);
			if(count==0) {
				return true;
			}
			else {
				return false;
			}
		}
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	return false;
	}


}

