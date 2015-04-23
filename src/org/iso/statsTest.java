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
		
		String ti = request.getParameter("test_id");
		
		String user = session.getAttribute("name").toString();
		clickers.printTop(response, user);
		
		
		String sql2="SELECT COUNT(*) (Select Distinct FirstName, LastName1, LastName2, Mark from Users, TestStudent where TestStudent.Id_Student=Users.IdUser and TestStudent.Id_Test="+ti+" ORDER BY LastName1 ASC)";
		
		int count = 0;
		
		try {
			Statement stmt=connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql2); //ejecutar sql1
			while(rs.next()){
			    count = rs.getInt("count");
			}
		} catch(SQLException e) {	
			e.printStackTrace();
			System.out.println("Resulset: " + sql2 + " Exception: " + e);
		}
		
		System.out.println(count);
		
		String sql1="SELECT distinct FirstName, LastName1, LastName2, Mark from Users, TestStudent where TestStudent.Id_Student=Users.IdUser and TestStudent.Id_Test="+ti+" ORDER BY LastName1 ASC;";
		
		try {
			Statement stmt=connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql1); //ejecutar sql1
			while(rs.next()) {
			}
		} catch(SQLException e) {	
			e.printStackTrace();
			System.out.println("Resulset: " + sql1 + " Exception: " + e);
		}
		String htmlStr = "";
		htmlStr += "<div id='clk-title'>";
		htmlStr += "				<h3 align='center'>Test Marks Pie Chart</h3>";
		htmlStr += "			</div>";
		htmlStr += "<div id='clk-box'><div id='clk-stats-test'></div></div>";
		htmlStr += "<div>";
		htmlStr += "	<input type='hidden' name='clk-pie-data' value=" + 1 + ">";
		htmlStr += "	<input type='hidden' name='clk-pie-data' value=" + 2 + ">";
		htmlStr += "	<input type='hidden' name='clk-pie-data' value=" + 3 + ">";
		htmlStr += "	<input type='hidden' name='clk-pie-data' value=" + 2 + ">";
		htmlStr += "	<input type='hidden' name='clk-pie-data' value=" + 1 + ">";
		htmlStr += "</div>";
		out.println(htmlStr);
		
		clickers.printBottom(response);
	}
}