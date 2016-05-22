package herrberk;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		
		PrintWriter out=response.getWriter();
		
		request.getRequestDispatcher("header.html").include(request, response);
		
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		
		if(LoginDao.validate(email, password)){
			//out.print("you are successfully logged in!");
			request.getSession().setAttribute("login", "true");
			request.getSession().setAttribute("email", email);
			response.sendRedirect("InboxServlet");
			request.getRequestDispatcher("footer.html").include(request, response);
			
		}else{
			out.print("<script>alert(\"Sorry, username or password error!\")</script>");
			request.getRequestDispatcher("error.html").include(request, response);
		}
		
		
		out.close();
	}

}
