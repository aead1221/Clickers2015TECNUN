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
		
		String htmlStr="";
		htmlStr += "<div id=\"clk-title\">";
		htmlStr += "				<label><h3>Test/Survey Selection</h3></label>";
		htmlStr += "			</div>";
		htmlStr += "			<div id=\"menu-content\">";
		htmlStr += "				<div class=\"menu-box\">";
		htmlStr += "					<div class=\"menu-header\">";
		htmlStr += "						<label>Tests</label>";
		htmlStr += "					</div>";
		htmlStr += "					<table style=\"padding-left:15%; padding-top:10%\">";
		
		String sql1 = "SELECT IdTest, TestName, TestType FROM TestsAA WHERE TestType='test'";
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql1);
			while (rs.next()) {
				String n = rs.getString("TestName");
				htmlStr += "						<tr>";
				htmlStr += "							<td><label><a href='initTest?nom=" + n + "&tt=test'><b>" + n + "</b></a></label></td>";
				htmlStr += "						</tr>";
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Resulset: " + sql1 + " Exception: " + e);
		}
		
		htmlStr += "					</table>";
		htmlStr += "				</div>";
		htmlStr += "				<div class=\"menu-box\">";
		htmlStr += "					<div class=\"menu-header\">";
		htmlStr += "						<label>Surveys</label>";
		htmlStr += "					</div>";
		htmlStr += "					<table style=\"padding-left:15%; padding-top:10%\">";
		
		String sql2 = "SELECT IdTest, TestName, TestType FROM TestsAA WHERE TestType='survey'";
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql2);
			while (rs.next()) {
				String n = rs.getString("TestName");
				htmlStr += "						<tr>";
				htmlStr += "							<td><label><a href='initTest?nom=" + n + "&tt=survey'><b>" + n + "</b></a></label></td>";
				htmlStr += "						</tr>";
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Resulset: " + sql2 + " Exception: " + e);
		}
		
		htmlStr += "					</table>";
		htmlStr += "				</div>";
		htmlStr += "			</div>";
		
		out.println(htmlStr);

		clickers.printBottom(resp);

	}
}