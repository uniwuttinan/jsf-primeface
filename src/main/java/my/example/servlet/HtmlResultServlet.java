package my.example.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import my.example.model.Person;
import my.example.service.NameService;

/**
 * Servlet implementation class TextResultServlet
 */
@WebServlet("/html-result-servlet")
public class HtmlResultServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		NameService n = new NameService();
		Person p = new Person();
		p.setFirstName(request.getParameter("fname"));
		p.setLastName(request.getParameter("lname"));
		
		response.setContentType("text/html");
		response.getWriter().append("<!DOCTYPE html>\r\n");
		response.getWriter().append("<html>\r\n");
		response.getWriter().append("<head>\r\n");
		response.getWriter().append("<meta charset=\"UTF-8\">\r\n");
		response.getWriter().append("<title>HTML Form</title>\r\n");
		response.getWriter().append("</head>\r\n");
		response.getWriter().append("<body>\r\n");
		response.getWriter().append("	Your name is "+n.display(p)+"\r\n<br/>");
		response.getWriter().append("	<a href=\"form-02.html\"> Back </a>\r\n");
		response.getWriter().append("</body>\r\n");
		response.getWriter().append("</html>");
		response.getWriter().close();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// do nothing
	}
	private static final long serialVersionUID = 1L;

}
