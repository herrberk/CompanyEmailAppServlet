package herrberk;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		request.getRequestDispatcher("header.html").include(request, response);
		
		request.getSession().invalidate();
		out.print("<b><p class=\"titles\">You are successfully logged out!</p></b>"+ "<a href=\"index.html\">"+
	"<img src=\"./images/back.png\" width=\"70\" height=\"70\"/></a>");
		
		request.getRequestDispatcher("login.html").include(request, response);
		
		request.getRequestDispatcher("footer.html").include(request, response);
		out.close();

	}

}
