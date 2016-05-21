package herrberk;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
public class RegisterDao {

	public static boolean save(String name,String email,String password,String gender,String dob,String addressLine,String city,String state,String country,String contact){
		boolean status=false;
		
		try{
			Connection con=ConProvider.getConnection();
			PreparedStatement ps=con.prepareStatement("INSERT INTO employees(NAME,EMAIL,PASSWORD,GENDER,DOB,ADDRESS,CITY,STATE,COUNTRY,PHONE,REGISTRATIONDATE,AUTHORIZED) values(?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1,name);
			ps.setString(2,email);
			ps.setString(3,password);
			ps.setString(4,gender);
			ps.setString(5,dob);
			ps.setString(6,addressLine);
			ps.setString(7,city);
			ps.setString(8,state);
			ps.setString(9,country);
			ps.setString(10,contact);
			ps.setString(11,Formatter.getcurrentDate());
			ps.setString(12,"yes");
			
			ps.executeUpdate();
			status=true;			
		}catch(Exception e){
			System.out.println(e);
			status=false;
			}	

		return status;
	}
}
