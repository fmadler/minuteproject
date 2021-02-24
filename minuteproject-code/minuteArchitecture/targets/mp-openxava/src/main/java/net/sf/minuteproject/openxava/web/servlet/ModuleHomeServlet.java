package net.sf.minuteproject.openxava.web.servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.openxava.util.*;

public class ModuleHomeServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		String [] uri = request.getRequestURI().split("/");
		if (uri.length < 4) {
			dispatcher = request.getRequestDispatcher("/xava/homeMenu.jsp");
		} else {
			dispatcher = request.getRequestDispatcher(
			"/xava/home.jsp?application=" + uri[1] + "&module=" + uri[3]);
		}
		dispatcher.forward(request, response);
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	

}
