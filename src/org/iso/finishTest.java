package org.iso;
import java.sql.*;
import java.io.*; 
import java.util.*;
import javax.servlet.*; 
import javax.servlet.http.*;
 
public class finishTest extends HttpServlet{ 
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
		
		String tt = request.getParameter("tt");
		
		PrintWriter out = response.getWriter();
		
		String user = "Antonio";
		clickers.printTop(response, user);
		
		String htmlStr="";
		if (tt.equals("test")) {
			htmlStr += "			<div id=\"clk-box\">";
			htmlStr += "				<h3>You have finished your test</h3><br>";
			htmlStr += "				<h6>Your mark will be available as soon as the teacher uploads it</h6>";
			htmlStr += "			</div>";
		} else {
			htmlStr += "			<div id=\"clk-box\">";
			htmlStr += "				<h3>You have finished your survey</h3><br>";
			htmlStr += "				<h6>Thank you for collaborating</h6>";
			htmlStr += "			</div>";
		}
		out.println(htmlStr);
		
		clickers.printBottom(response);
	}
}