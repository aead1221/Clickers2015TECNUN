package org.iso;
import java.sql.*;
import java.io.*; 
import java.util.*;

import javax.servlet.*; 
import javax.servlet.http.*;
 
public class takeSurvey extends HttpServlet{ 
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
		
		String tt = request.getParameter("tt");
		String nom = request.getParameter("nom");
		String numero = request.getParameter("num");	
		int num = Integer.parseInt(numero);
		
		String test_id = "";
		int test_nq = 0;
		int test_sn = 0;
		
		String sql1 = "Select Id_Survey, Survey, NumberOfQuestions, NumberOfOptions from Surveys where Survey ='" + nom + "'";
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql1);
			if (rs.next()) {
				test_id = rs.getString("Id_Survey");
				test_nq = Integer.parseInt(rs.getString("NumberOfQuestions"));
				test_sn = Integer.parseInt(rs.getString("NumberOfOptions"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Resulset: " + sql1 + " Exception: " + e);
		}
		
		//System.out.println(test_id + ", " + test_nq + ", " + test_no);
		
		String q = "";
		
		String sql2 = "Select Id_Survey, Id_Question, Question from Squestions where Id_Survey =" + test_id + " and Id_Question= " + (num+1) + "";
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql2);
			if (rs.next()) {
				q = rs.getString("QuestionT");
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Resulset: " + sql2 + " Exception: " + e);
		}
		
		PrintWriter out = response.getWriter(); 
		clickers.printTop(response, session.getAttribute("name").toString());
		String htmlStr="";
		htmlStr += "<div id='clk-title'>";
		htmlStr += "				<h3 align='center'>" + nom + "</h3>";
		htmlStr += "			</div>";
		htmlStr += "			<div id='clk-box'>";
		htmlStr += "				Question " + (num+1) + ". " + q + "";
		htmlStr += "				<br>";
		htmlStr += "				<br>";
		htmlStr += "				<div>";
		htmlStr += "	                    <form name='pregunta' method='get' action='writeAnswers'>";
		htmlStr += "						<table>";
		for (int l = 1; l <= test_sn; l++) {
			htmlStr += "							<TR>";
			htmlStr += "								<TH>";
			htmlStr += "									<input type='radio' name='resp' value='" + l + "'>";
			htmlStr += "								</TH>";
			htmlStr += "								<TH></TH><TH></TH><TH></TH><TH></TH><TH></TH>";
			String o = "";
			
			String sql3 = "Select OptionT, IdScale, IdOption from ScaleOptions where IdScale =" + test_sn + " and IdOption=" + (l) + "";
			try{
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(sql3);
				if (rs.next()) {
					o = rs.getString("OptionT");
				}
			} catch(SQLException e) {
				e.printStackTrace();
				System.out.println("Resulset: " + sql3 + " Exception: " + e);
			}
			htmlStr += "								<TH align='left'>" + o + "</TH></TR>";
		}
		htmlStr += "						</table><br>";
		if (num != test_nq-1) {
			htmlStr += "<input class='clk-button' type='submit' value='Next'>";
		} else {
			htmlStr += "<input class='clk-button' type='submit' value='Finish'>";
		}
		htmlStr += "<input name='nom' type='hidden' value='" + nom + "'>";
		htmlStr += "<input name='num' type='hidden' value='" + num + "'>";
		htmlStr += "<input name='tt' type='hidden' value='" + tt + "'>";
		htmlStr += "</form>";
		htmlStr += "</div>";
		htmlStr += "</div>";
		out.println(htmlStr);
		clickers.printBottom(response);
	}
}