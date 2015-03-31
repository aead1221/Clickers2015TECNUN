package org.iso;
import java.sql.*;
import java.io.*; 
import java.util.*;
import javax.servlet.*; 
import javax.servlet.http.*;
 
public class statsSurvey extends HttpServlet{ 
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
		
		String htmlStr = "";
		htmlStr += "<div id='clk-title'>";
		htmlStr += "				<h3 align='center'>Survey Results Bar Chart</h3>";
		htmlStr += "			</div>";
		htmlStr += "<div id='clk-box'><div id='clk-stats-survey'></div></div>";
		out.println(htmlStr);
		
		clickers.printBottom(response);
	}
}