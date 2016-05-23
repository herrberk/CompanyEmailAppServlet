package herrberk;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/RegisterServlet")

/**
 * Gets the registration information from the html and passes into
 * registerdao to save to the database
 * @author Berk
 */
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		request.getRequestDispatcher("header.html").include(request, response);
		
		String name=request.getParameter("name");
		String password=request.getParameter("password");
		String email=request.getParameter("email");
		String gender=request.getParameter("gender");
		String dob=request.getParameter("dob");
		String addressLine=request.getParameter("addressLine");
		String city=request.getParameter("city");
		String state=request.getParameter("state");
		String country=request.getParameter("country");
		String contact=request.getParameter("contact");
		
		boolean status= RegisterDao.save(name, email, password, gender, dob, addressLine, city, state, country, contact);
		if(status){
			out.print("<p>You are successfully registered!</p>"+
		"<img src=\"./images/registered.png\"/>");
			request.getRequestDispatcher("login.html").include(request, response);
			
		}
		
		request.getRequestDispatcher("footer.html").include(request, response);
		out.close();
	}

}
