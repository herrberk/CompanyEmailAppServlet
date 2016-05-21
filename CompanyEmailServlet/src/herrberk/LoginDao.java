package herrberk;
import java.sql.*;
public class LoginDao {

	public static boolean validate(String email,String password){
		boolean status=false;
		try{
			Connection con=ConProvider.getConnection();
			
			String sql="SELECT * FROM employees WHERE EMAIL='"+email+
					   "' and PASSWORD='" + password +"' and AUTHORIZED='yes'";
			Statement statement = con.createStatement();
	
			ResultSet rs = statement.executeQuery(sql);
			
			if(rs.next()){
				status=true;
			}
			
			
		}catch(Exception e){
			System.out.println(e);
			}
		
		return status;
	}
}
