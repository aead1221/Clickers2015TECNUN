package org.iso;
import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class testMenu extends HttpServlet {
	Connection connection;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String url="jdbc:odbc:clickers";
			connection=DriverManager.getConnection(url); 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession(true);
		
		resp.setContentType("text/html");

		PrintWriter out = null;
		try {
			out=resp.getWriter();
		} catch (IOException io) {
			System.out.println("alumnosExcepcion");    
		}

		out.println("<!DOCTYPE html>");
		out.println("<html lang='en'>");
		out.println("<head>");
		out.println("<style type='text/css'>");
		out.println("* {padding: 0px; margin: 0px}"); 
		out.println("body {font-family:helvetica;font-size: 14px;font-weight:normal;background: url('bg.png') no-repeat top left;background-attachment: fixed}"); 
		out.println("header {background-color:#990000;position: fixed;top: 0px;width: 100%}"); 
		out.println("header h1 {color:white;font-size: 64pt;font-weight: lighter}"); 
		out.println("header h2 {color:#C0C0C0;font-weight: normal;padding-left:30px}"); 
		out.println("footer {position: fixed;top: 97%;right: 1%;}");
		out.println(".my-header {padding: 30px}");
		out.println(".my-box {background-color: white; font-size:18pt;font-weight: normal;border: 3px solid #990000;border-radius:10px; padding: 40px;margin-top:20%;margin-left: auto;margin-right: auto;width: 30%;height: 100%;}");
		out.println("</style>");
		out.println("<title>Clickers</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<header><div class='my-header'><h1>Clickers</h1><h2>TECNUN's online test taking platform</h2></div></header>");



		out.println("<p align='center'>");
		out.println("<div class='my-box'>");
		out.println("<h3> What test do you want to take, " + session.getAttribute("user") + "?</h3><br>");
		out.println("<TABLE BORDER='0'>");
		
		String sql1 = "Select Id_Tests, Name from Tests";
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql1);
			while (rs.next()) {
				String n = rs.getString("Name");
				out.println("<TR>");
				out.println("<TD align=left'><a href='initTest?nom=" + n + "'>" + n + "</a></TD>");
				out.println("</TR>");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Resulset: " + sql1 + " Exception: " + e);
		}
		
		
		out.println("</TABLE");
		out.println("<BR><BR><h6><a href='menu'>Back to menu</a></h6>");
		out.println("</div>");
		out.println("</p>");

		out.println("<footer>&copy UN 2015</footer></body>");

		out.println("</html>");

	}
}