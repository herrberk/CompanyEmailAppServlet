package herrberk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Returns true if the receiver email address exists in the database.
 * @author Berk
 */
public class EmailChecker {
	
	public static boolean check(String receiver){
		
		boolean status=false;
		try{
			Connection con=ConProvider.getConnection();
			PreparedStatement ps=con.prepareStatement("SELECT * FROM employees WHERE EMAIL=?");
			ps.setString(1,receiver);
			ResultSet rs=ps.executeQuery();
			
			if(rs.next())
				status = true;
		}catch(Exception e){
			status = false;
		}
		return status;
			
	}
}
