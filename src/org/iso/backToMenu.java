package org.iso;
import java.sql.*;
import java.io.*; 
import java.util.*;

import javax.servlet.*; 
import javax.servlet.http.*;
 
public class backToMenu extends HttpServlet{ 
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
		
		String user_id = session.getAttribute("user").toString();

		String user_type = "";
		
		String sql1 = "SELECT IdUser, UserType FROM Users WHERE IdUser='" + user_id + "'";
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql1);
			if (rs.next()) {
				user_type = rs.getString("UserType");
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Resulset: " + sql1 + " Exception: " + e);
		}
		
		ServletContext sc = getServletContext();
		if (user_type.equals("1")) {
			RequestDispatcher rd = sc.getRequestDispatcher("/MenuAdmin");
			rd.forward(request , response);
		} else if (user_type.equals("2")) {
			RequestDispatcher rd = sc.getRequestDispatcher("/menuTeacher");
			rd.forward(request , response);
		} else if (user_type.equals("3")) {
			RequestDispatcher rd = sc.getRequestDispatcher("/menuStudent");
			rd.forward(request , response);
		}
	}
}