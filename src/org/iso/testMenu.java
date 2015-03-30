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
			connection = DriverManager.getConnection(url);
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

		clickers.printTop(resp, "user");

		out.println("<div id='clk-box'>");
		out.println("<h3> What test do you want to take, " + session.getAttribute("user") + "?</h3><br>");
		out.println("<TABLE BORDER='0'>");
			
		String sql1 = "SELECT IdTest, TestName FROM TestsAA";
		//String sql1 = "SELECT Id_Tests, Name FROM Tests";
		
		if (connection==null) {
			System.out.println("IS NULL");
		}
		
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql1);
			while (rs.next()) {
				String n = rs.getString("TestName");
				out.println("<TR>");
				out.println("<TD align=left'><a href='initTest?nom=" + n + "'>" + n + "</a></TD>");
				out.println("</TR>");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Resulset: " + sql1 + " Exception: " + e);
		}
		
		out.println("</TABLE>");
		out.println("</div>");

		clickers.printBottom(resp);

	}
}