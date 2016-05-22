package herrberk;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/TrashServlet")
public class TrashServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		request.getRequestDispatcher("header.html").include(request, response);
		request.getRequestDispatcher("link.html").include(request, response);
		
		HttpSession session=request.getSession(false);
		if(session==null){
			response.sendRedirect("index.html");
		}else{
			String email=(String)session.getAttribute("email");
			out.print("<div class=\"username\">Welcome, "+email+
					"<img src=\"./images/welcome.png\"/>"+"</div>");
			out.print("<h1>Trash</h1>");
			
			String msg=(String)request.getAttribute("msg");
			if(msg!=null){
				out.print("<p>"+msg+"</p>");
			}
			
			try{
				Connection con=ConProvider.getConnection();
				PreparedStatement ps=con.prepareStatement("SELECT * FROM message WHERE RECEIVER=? or SENDER=? and TRASH=? ORDER BY ID DESC");
				ps.setString(1,email);
				ps.setString(2,email);
				ps.setString(3,"yes");
				
				ResultSet rs=ps.executeQuery();
				out.print("<table border='1' style='font-size:16px; width:700px;'>");
				out.print("<tr style='background-color:grey;color:white'><td>Sender</td><td>Subject</td><td>Date</td></tr>");
				String received;
				
				while(rs.next()){
					if(rs.getString("sender").equals(email))
						received = "Sent: ";
					else received = "Received: ";
					
					if(rs.getString("TRASH").equals("yes")){
					out.print("<tr><td>"+rs.getString("sender")+
							"</td><td><a href='ViewMailServlet?id="+rs.getString(1)+"'>"+
							"<b>"+received+"</b>"+rs.getString("subject")+"</td><td>"+
							rs.getString("messagedate")+"</td></tr>");
					}
				}
				out.print("</table>");
				
				con.close();
			}catch(Exception e){out.print(e);}
		}
		
		request.getRequestDispatcher("footer.html").include(request, response);
		out.close();

	}

}
