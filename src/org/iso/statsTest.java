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
		String tn = request.getParameter("test_name");
		
		String user = session.getAttribute("name").toString();
		clickers.printTop(response, user);
		
		
		String sql2="SELECT distinct FirstName, LastName1, LastName2, Mark from Users, TestStudent where TestStudent.Id_Student=Users.IdUser and TestStudent.Id_Test=" + ti + " ORDER BY LastName1 ASC;";
		
		int count[] = {0,0,0,0,0};
		try {
			Statement stmt=connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql2);
			while(rs.next()){
			    int mark = Integer.parseInt(rs.getString("Mark"));
			    if (mark < 5 || mark == 0) {
			    	count[0]++;
			    } else if (mark < 7) {
			    	count[1]++;
			    } else if (mark < 9) {
			    	count[2]++;
			    } else if (mark < 10) {
			    	count[3]++;
			    } else {
			    	count[4]++;
			    }
			}
		} catch(SQLException e) {	
			e.printStackTrace();
			System.out.println("Resulset: " + sql2 + " Exception: " + e);
		}
		
		String htmlStr = "";
		htmlStr += "<div id='clk-title'>";
		htmlStr += "				<h3 align='center'>Test Marks Pie Chart</h3>";
		htmlStr += "			</div>";
		htmlStr += "<div id='clk-box'><div id='clk-stats-test'></div>";
		htmlStr += "<a href='CalificacionesPrueba?TestId=" + ti + "&TestName=" + tn + "'>";
		htmlStr += "	<button class='clk-button'>Back to marks</button>";
		htmlStr += "</a></div>";
		htmlStr += "<div>";
		htmlStr += "	<input type='hidden' name='clk-pie-data' value=" + count[0] + ">";
		htmlStr += "	<input type='hidden' name='clk-pie-data' value=" + count[1] + ">";
		htmlStr += "	<input type='hidden' name='clk-pie-data' value=" + count[2] + ">";
		htmlStr += "	<input type='hidden' name='clk-pie-data' value=" + count[3] + ">";
		htmlStr += "	<input type='hidden' name='clk-pie-data' value=" + count[4] + ">";
		htmlStr += "</div>";
		out.println(htmlStr);
		
		clickers.printBottom(response);
	}
}