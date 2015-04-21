package org.iso;
import java.sql.*;
import java.io.*; 
import java.util.*;

import javax.servlet.*; 
import javax.servlet.http.*;
 
public class writeAnswers extends HttpServlet{ 
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
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { 
	
		HttpSession session = req.getSession(true);
		String student_id = "" + session.getAttribute("user").toString() + "";
			
		int num = Integer.parseInt(req.getParameter("num"));
		String tt = req.getParameter("tt");
		String nom = req.getParameter("nom");
		String sresp = "" + req.getParameter("resp") + "";
		
		try {
			int i = Integer.parseInt(sresp);
		} catch (Exception e) {
			sresp = "0";
		}
		
		String test_id = "";
		int test_nq = 0;
		
		String sql1 = "Select Id_Tests, Description, NumberOfQuestions from Tests where Description ='" + nom + "'";
		try{
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql1);
			if (rs.next()) {
				test_id = rs.getString("Id_Tests");
				test_nq = Integer.parseInt(rs.getString("NumberOfQuestions"));
			} else {
				String sql11 = "Select Id_Survey, Survey, NumberOfQuestions from Surveys where TestName ='" + nom + "'";
				try{
					Statement statement1 = connection.createStatement();
					ResultSet rs1 = statement1.executeQuery(sql11);
					if (rs1.next()) {
						test_id = rs1.getString("Id_Survey");
						test_nq = Integer.parseInt(rs1.getString("NumberOfQuestions"));
					}
				} catch(SQLException e) {
					e.printStackTrace();
					System.out.println("Resulset: " + sql11 + " Exception: " + e);
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Resulset: " + sql1 + " Exception: " + e);
		}
		
		String sql2 = "Insert into StudentAnswer (IdStudent, IdTest, IdQuestion, AnswerS) Values ('" + student_id + "', '" + test_id + "', '" + (num+1) +"', '" + sresp + "')";
		try{
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql2);
		} catch(SQLException e) {
			System.out.println("Question has already been answered and cannot be overwritten");
			System.out.println("Resulset: " + sql2 + " Exception: " + e);
		}
		
		ServletContext sc = getServletContext();
		if (tt.equals("test")) {
			if (num != test_nq - 1) {
				RequestDispatcher rd = sc.getRequestDispatcher("/takeTest?nom="+ nom + "&num=" + (num+1) + "&tt=" + tt + "");
				rd.forward(req , resp);
			} else {
				RequestDispatcher rd = sc.getRequestDispatcher("/autoCorrect?nom=" + nom + "&tt=" + tt + "");
				rd.forward(req , resp);
			}
		} else {
			if (num != test_nq - 1) {
				RequestDispatcher rd = sc.getRequestDispatcher("/takeSurvey?nom="+ nom + "&num=" + (num+1) + "&tt=" + tt + "");
				rd.forward(req , resp);
			} else {
				RequestDispatcher rd = sc.getRequestDispatcher("/finishTest?tt=" + tt + "");
				rd.forward(req , resp);
			}
		}
		
	}
}