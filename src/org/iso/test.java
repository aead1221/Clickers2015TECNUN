package org.iso;
import java.sql.*;
import java.io.*; 
import java.util.*;
import javax.servlet.*; 
import javax.servlet.http.*;
 
public class test extends HttpServlet{ 
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
		
		String nom = request.getParameter("nom");
		String numero = request.getParameter("num");	
		int num = Integer.parseInt(numero);
		
		String test_id = "";
		int test_nq = 0;
		int test_no = 0;
		int test_s;
		float test_p;
		
		String sql1 = "Select Id_Tests, Name, NumberOfQuestions, NumberOfOptions from Tests where Name ='" + nom + "'";
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql1);
			if (rs.next()) {
				test_id = rs.getString("Id_Tests");
				test_nq = Integer.parseInt(rs.getString("NumberOfQuestions"));
				test_no = Integer.parseInt(rs.getString("NumberOfOptions"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Resulset: " + sql1 + " Exception: " + e);
		}
		
		//System.out.println(test_id + ", " + test_nq + ", " + test_no);
		
		String q = "";
		
		String sql2 = "Select Question from Questions where Id_Test =" + test_id + " and Id_Question= " + (num+1) + "";
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql2);
			if (rs.next()) {
				q = rs.getString("Question");
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Resulset: " + sql2 + " Exception: " + e);
		}
		
		PrintWriter out = response.getWriter(); 
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
		out.println(".my-box {background-color: white; font-size:24pt;font-weight: normal;border: 3px solid #990000;border-radius:10px; padding: 20px;margin-top:20%;margin-left: auto;margin-right: auto;width: 50%;height: 400px;}");
		out.println(".q-box {font-weight: lighter; font-size:19pt; padding:60px}");
		out.println("</style>");
		out.println("<title>Test</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<header><div class='my-header'><h1>Clickers</h1><h2>TECNUN's online test taking platform</h2></div></header>");
		out.println("<div class='my-box'><h3 align='center'>" + nom + "</h3><br>" + (num+1) + ". " + q);
		out.println("<div class='q-box'>");
		out.println("<table>");
		out.println("<form name='pregunta' method='get' action='writeAnswers'>");
		for (int l = 0; l < test_no; l++) {
			out.println("<TR>");
			out.println("<TH><input type='radio' name='resp' value='" + l + "'></TH>");
			out.println("<TH></TH><TH></TH><TH></TH><TH></TH><TH></TH>");
			String o = "";
			
			String sql3 = "Select Option from Options where IdTest =" + test_id + " and IdQuestion= " + (num+1) + " and IdOption=" + (l+1) + "";
			try{
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(sql3);
				if (rs.next()) {
					o = rs.getString("Option");
				}
			} catch(SQLException e) {
				e.printStackTrace();
				System.out.println("Resulset: " + sql3 + " Exception: " + e);
			}
			
			out.println("<TH align='left'>" + o + "</TH></TR>");
		}
		out.println("</table><br>");
		if (num != test_nq-1) {
			out.println("<input type='submit' value='Next'>");
		} else {
			out.println("<input type='submit' value='Finish'>");
		}
		out.println("<input name='nom' type='hidden' value='" + nom + "'>");
		out.println("<input name='num' type='hidden' value='" + num + "'>");
		out.println("</form>");
		out.println("</div>");
		out.println("</div>");
		out.println("<footer>&copy UN 2015</footer></body>");
		out.println("</html>");
	}
}