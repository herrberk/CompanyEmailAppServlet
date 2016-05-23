package herrberk;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/RecoverMailServlet")

/**
 * Recovers a mail from the trash and moves into the previous folder
 * @author Berk
 *
 */
public class RecoverMailServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

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
			int id=Integer.parseInt(request.getParameter("id"));
			
			try{
				Connection con=ConProvider.getConnection();
				PreparedStatement ps=con.prepareStatement("UPDATE message SET TRASH=? WHERE id=?");
				ps.setString(1,"no");
				ps.setInt(2,id);
				int i=ps.executeUpdate();
				if(i>0){
					request.setAttribute("msg","Mail successfully recovered!");
					request.getRequestDispatcher("InboxServlet").forward(request, response);
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
