package org.iso;
import java.sql.*;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class ViewMarksStudents extends HttpServlet {
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
	public void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession(true);
		//Prove that it takes well the student id
		//String Idstudent = session.getAttribute("user").toString();
		int idstudent = 1;
		
		resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
		
		//int idstudent = 1;
		
//		out.println("<!DOCTYPE html>");
//		out.println("<html lang=\"en\">");
//		out.println("<head>");
//		out.println("<link rel='stylesheet' type='text/css' href='clickers-css.css'>");
//		out.println("<title>Clickers - Menu</title>");
//		out.println("</head>");
//		out.println("	");
//		out.println("<body>");
//		out.println("<header>");
//		out.println("<div id=\"clk-header\">");
//		out.println("<h1>Clickers</h1>");
//		out.println("<h2>TECNUN's online test taking platform</h2>");
//		out.println("</div>");
//		out.println("<div id=\"clk-user-bar\">");
//		out.println("<div id=\"clk-user-buttons\">");
	
		clickers.printTop(resp, session.getAttribute("user").toString());
		out.println("<div id = 'clk-box'>");
		out.println("<div class=\"marksStudent-header \">");
		out.println("<label><p align='center'> Marks</p></label>");
		out.println("</div>");
		out.println("<form method=\"get\" action=\"statistic.html\" >");
		out.println("<table style=\"padding-left:16%; padding-top:5%\">");
		out.println("<tr class=\"clk-subtitle-st\">");
		out.println("<td><label><p align='left'><h3><U> TEST </U></h3></p></label></td>");
		out.println("<td> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>");
		out.println("<td><label><p align='center'><h3><U> MARKS </U></h3></p></label></td>");
		out.println("");
		out.println("");
		out.println("</tr>");
		out.println("");
		
		int testcount = 0;
		String sql = "Select Id_Test FROM TestStudentAI WHERE Id_Student=" + idstudent + "";
		
		System.out.println(sql);
		
		try {
		Statement statement=connection.createStatement();
		ResultSet result = statement.executeQuery(sql);
		
		while(result.next()) {
		testcount = testcount + 1;
		
		}
		}
		
		catch(SQLException e) {
		e.printStackTrace();
		System.out.println("Resulset: " + sql + " Exception: " + e);
		}
		int[] codtest = new int[testcount];
		int[] markers = new int[testcount];
		
		
		
		String sql1 = "Select Id_Test, Mark FROM TestStudentAI WHERE Id_Student=" + idstudent + "";
		int sum = 0;
		System.out.println(sql1);
		
		try {
		Statement statement=connection.createStatement();
		ResultSet result = statement.executeQuery(sql1);
		
		while(result.next()) {
		String testID = result.getString("Id_Test");
		int testCOD = Integer.parseInt(testID);
		codtest[sum] = testCOD;
		
		String mark = result.getString("Mark");
		int note = Integer.parseInt(mark);
		markers[sum] = note;
		
		sum = sum + 1;
		
		}
		}
		
		catch(SQLException e) {
		e.printStackTrace();
		System.out.println("Resulset: " + sql1 + " Exception: " + e);
		}
		String[] testName = new String[testcount];
		
		for (int i=0; i<testcount; i++) {
		
			String sql2 = "Select Name FROM TestsAI WHERE Id_Tests=" + codtest[i] + "";
			System.out.println(sql2);
			
			try {
			Statement statement=connection.createStatement();
			ResultSet result = statement.executeQuery(sql2);
			
			while(result.next()) {
			testName[i] = result.getString("Name");
			}
			}
			
			catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Resulset: " + sql2 + " Exception: " + e);
			}
			}
		for (int j=0; j<testcount; j++) {
			
			out.println("<tr class=\"clk-content-st\">");
			out.println("<td><label>" + testName[j] + "</a></label></td>");
			out.println("<td> &nbsp;&nbsp;&nbsp; </td>");
			out.println("<td><label><p align='center'>" + markers[j] + "</p></a></label></td>");
			out.println("<td> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>");
			out.println("<td><a href='statsTest'><buttom> Statistics</buttom></a></td>");
			//out.println("<td><input style type=\"submit\" value=\"Statistics\"></td>");
			out.println("");
			out.println("</tr>");
			out.println("");
		}

		out.println("</table>");
		out.println("</form>");
		out.println("</div>");
		
		clickers.printBottom(resp);
//		out.println("<footer>");
//		out.println("<BR><h6><a href='menuTeacher'>Back to menu</a></h6>");
//		out.println("&copy UN 2015");
//		out.println("</footer>");
//		out.println("</body>");
//		out.println("</html>");
		}
		}
