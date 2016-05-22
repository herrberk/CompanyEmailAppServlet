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
@WebServlet("/ViewMailServlet")
public class ViewMailServlet extends HttpServlet {
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
			out.print("<span style='float:right'>Hi, "+email+"</span>");
			
			int id=Integer.parseInt(request.getParameter("id"));
			
			try{
				Connection con=ConProvider.getConnection();
				PreparedStatement ps=con.prepareStatement("SELECT * FROM message WHERE ID=?");
				ps.setInt(1,id);
				ResultSet rs=ps.executeQuery();
				
				if(rs.next()){
					out.print("<h1>"+rs.getString("subject")+"</h1><hr/>");
					out.print("<h2><b>Message:</b><br/> "+rs.getString("message")+" <br/> <br/><br/><b>By: "+rs.getString("sender")+"</b></h2>");
					
					if(rs.getString("TRASH").equals("no") && rs.getString("RECEIVER").equals(email))
						out.print("<h4><a href='DeleteMailServlet?id="+rs.getString(1)+"'>Delete Mail</a></h4>");		
					if(rs.getString("TRASH").equals("yes") && rs.getString("RECEIVER").equals(email))
						out.print("<h4><a href='PermanentDeleteMailServlet?id="+rs.getString(1)+"'>Delete Permanently</a></h4>");
					if(rs.getString("TRASH").equals("yes"))
						out.print("<h4><a href='RecoverMailServlet?id="+rs.getString(1)+"'>Recover Mail</a></h4>");	
					
				}
				con.close();
			}catch(Exception e){
				out.print(e);
				}
		}
		
		
		
		request.getRequestDispatcher("footer.html").include(request, response);
		out.close();
	}

	}

