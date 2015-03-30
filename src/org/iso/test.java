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
		
		String sql1 = "Select IdTest, TestName, NoOfQuestions, NoOfOptions from TestsAA where TestName ='" + nom + "'";
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql1);
			if (rs.next()) {
				test_id = rs.getString("IdTest");
				test_nq = Integer.parseInt(rs.getString("NoOfQuestions"));
				test_no = Integer.parseInt(rs.getString("NoOfOptions"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Resulset: " + sql1 + " Exception: " + e);
		}
		
		//System.out.println(test_id + ", " + test_nq + ", " + test_no);
		
		String q = "";
		
		String sql2 = "Select IdTest, IdQuestion, QuestionT from QuestionsAA where IdTest =" + test_id + " and IdQuestion= " + (num+1) + "";
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
		clickers.printTop(response, session.getAttribute("user_id").toString());
		out.println("<div id='clk-box'><h3 align='center'>" + nom + "</h3><br>" + (num+1) + ". " + q);
		out.println("<div class='q-box'>");
		out.println("<table>");
		out.println("<form name='pregunta' method='get' action='writeAnswers'>");
		for (int l = 1; l <= test_no; l++) {
			out.println("<TR>");
			out.println("<TH><input type='radio' name='resp' value='" + l + "'></TH>");
			out.println("<TH></TH><TH></TH><TH></TH><TH></TH><TH></TH>");
			String o = "";
			
			String sql3 = "Select OptionT, IdTest, IdQuestion, IdOption from OptionsAA where IdTest =" + test_id + " and IdQuestion= " + (num+1) + " and IdOption=" + (l) + "";
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
		clickers.printBottom(response);
	}
}