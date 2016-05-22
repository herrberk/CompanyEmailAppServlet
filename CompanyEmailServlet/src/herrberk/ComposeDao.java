package herrberk;

import java.sql.Connection;
import java.sql.Statement;

public class ComposeDao {

	public static synchronized boolean save(String sender,String receiver,String subject,String message){
		boolean status=false;
		try{
			Connection con=ConProvider.getConnection();
			String sql  = "INSERT INTO message(SENDER,RECEIVER,SUBJECT,MESSAGE,TRASH,MESSAGEDATE)" +
	                   "VALUES ('"+sender+"', '"+receiver+"','"+subject  
	                   +"','"+message +"','no','"+ Formatter.getcurrentDate() +"')";
			Statement statement = con.createStatement();
			statement.executeUpdate(sql);
			status=true;			
		}catch(Exception e){
			System.out.println(e);
			status=false;
			}
				
		return status;
	}
}
