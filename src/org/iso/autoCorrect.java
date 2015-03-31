package org.iso;
import java.sql.*;
import java.io.*; 
import java.util.*;

import javax.servlet.*; 
import javax.servlet.http.*;
 
public class autoCorrect extends HttpServlet{ 
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
		
		String user_id = session.getAttribute("user_id").toString();
		
		String nom = request.getParameter("nom");
		String tt = request.getParameter("tt");
		
		int test_nq = 0;
		float test_p = 0;
		int test_done=0;
		String test_id = "";
		
		String sql1 = "Select IdTest, TestName, NoOfQuestions, TestSubtracts, Penalization from TestsAA where TestName ='" + nom + "'";
		try{
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql1);
			if (rs.next()) {
				test_id = rs.getString("IdTest");
				test_nq = Integer.parseInt(rs.getString("NoOfQuestions"));
				test_p = (float)Integer.parseInt(rs.getString("Penalization"))/100;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Resulset: " + sql1 + " Exception: " + e);
		}
		
		float q_value = 10/test_nq;
		float mark = 0;
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		String sql2 = "SELECT DISTINCT QuestionsAA.IdTest, QuestionsAA.IdQuestion, StudentAnswerAA.IdStudent, StudentAnswerAA.AnswerS, QuestionsAA.Answer FROM QuestionsAA INNER JOIN StudentAnswerAA ON (QuestionsAA.IdQuestion = StudentAnswerAA.IdQuestion) AND (QuestionsAA.IdTest = StudentAnswerAA.IdTest) WHERE StudentAnswerAA.IdStudent=" + user_id + " AND QuestionsAA.IdTest=" + test_id + "";
		try{
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql2);
			while (rs.next()) {
				int answer = Integer.parseInt(rs.getString("Answer"));
				int answerS = Integer.parseInt(rs.getString("AnswerS"));
				if (answer == answerS) {
					mark += q_value;
					System.out.println("Mark " + rs.getRow() + ": " + mark);
				} else if (answerS != 0) {
					mark += (-1)*test_p*q_value;
					System.out.println("Mark " + rs.getRow() + ": " + mark);
				} else {
					System.out.println("Mark " + rs.getRow() + ": " + mark);
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Resulset: " + sql2 + " Exception: " + e);
		}
		
		System.out.println("Mark net: " + mark);
		
		if (mark<0) {
			mark = 0;
		}
		
		System.out.println("Mark final: " + mark);
		
		String sql3 = "Select DoneS, IdTest, IdStudent from TestStudentAA where IdTest =" + test_id + " and IdStudent=" + user_id + "";
		try{
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql3);
			if (rs.next()) {
				test_done = Integer.parseInt(rs.getString("DoneS"));
				System.out.println("NOT DONE: " + test_done);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Resulset: " + sql3 + " Exception: " + e);
		}
		
		if (test_done == 0) {
			String sql4 = "UPDATE TestStudentAA SET TestStudentAA.DoneS=1, TestStudentAA.Mark=" + mark + " WHERE (((TestStudentAA.IdTest) =" + test_id + ") and ((TestStudentAA.IdStudent)=" + user_id + "))";
			try{
				Statement statement = connection.createStatement();
				statement.executeUpdate(sql4);
				System.out.println("Your mark is: " + mark + ", and has been successfuly written to the database");
			} catch(SQLException e) {
				e.printStackTrace();
				System.out.println("Resulset: " + sql4 + " Exception: " + e);
			}
		} else {
			System.out.println("Test has already been taken and your mark will not be overwritten");
		}
		
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/finishTest?tt=" + tt + "");
		rd.forward(request, response);
	}
}