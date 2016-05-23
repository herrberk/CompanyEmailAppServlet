package herrberk;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Main duty is to create a new mail, check user validity and to send
 * if it is valid.
 * @author Berk
 *
 */
@WebServlet("/ComposeServletProcess")
public class ComposeServletProcess extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		request.getRequestDispatcher("header.html").include(request, response);
		
		String receiver=request.getParameter("to");
		String subject=request.getParameter("subject");
		String message=request.getParameter("message");
		message=message.replaceAll("\n","<br/>");
		String email=(String)request.getSession(false).getAttribute("email");
		
		
		if(EmailChecker.check(receiver) && !subject.equals("")){
			ComposeDao.save(email, receiver, subject, message);
			request.setAttribute("msg","Message Successfully Sent!" +
		"<img src=\"./images/success.png\"/>");
			request.getRequestDispatcher("ComposeServlet").forward(request, response);
		}
		else{
			request.setAttribute("msg","Error sending the message!"+
					"<img src=\"./images/fail.png\"/>");
			request.getRequestDispatcher("ComposeServlet").forward(request, response);
		}
			request.getRequestDispatcher("footer.html").include(request, response);
		out.close();

	}

}
