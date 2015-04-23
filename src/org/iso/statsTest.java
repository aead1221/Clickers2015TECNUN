package org.iso;
import java.sql.*;
import java.io.*; 
import java.util.*;
import javax.servlet.*; 
import javax.servlet.http.*;
 
public class statsTest extends HttpServlet{ 
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
		
		String sql1 = request.getParameter("test_id");
		System.out.println(sql1);
		
		String user = session.getAttribute("name").toString();
		clickers.printTop(response, user);
		
		String htmlStr = "";
		htmlStr += "<div id='clk-title'>";
		htmlStr += "				<h3 align='center'>Test Marks Pie Chart</h3>";
		htmlStr += "			</div>";
		htmlStr += "<div id='clk-box'><div id='clk-stats-test'></div></div>";
		htmlStr += "<div id='clk-pie-data'>";
		htmlStr += "<input type='hidden' name='clk-pie-data' value=" + 1 + ">";
		htmlStr += "<input type='hidden' name='clk-pie-data' value=" + 2 + ">";
		htmlStr += "<input type='hidden' name='clk-pie-data' value=" + 3 + ">";
		htmlStr += "<input type='hidden' name='clk-pie-data' value=" + 2 + ">";
		htmlStr += "<input type='hidden' name='clk-pie-data' value=" + 1 + ">";
		htmlStr += "</div>";
		out.println(htmlStr);
		
		clickers.printBottom(response);
	}
}