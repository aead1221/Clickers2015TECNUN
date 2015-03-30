package org.iso;
import java.sql.*;
import java.io.*; 
import java.util.*;
import javax.servlet.*; 
import javax.servlet.http.*;
 
public class takeTest extends HttpServlet{ 
	Connection connection;
    
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
		    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		    String url="jdbc:odbc:Clickers";
		    connection=DriverManager.getConnection(url); 
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException { 
		
		HttpSession session = request.getSession(true);
		
		PrintWriter out = response.getWriter();
		
		String user = "Antonio";
		clickers.printTop(response, user);
		
		out.println("<div id='clk-box'>");
		out.println("<br><p align='center'><button class='clk-button' onclick='openReport()'>Download</button></p>");
		out.println("</div>");
		
		clickers.printBottom(response);
	}
}