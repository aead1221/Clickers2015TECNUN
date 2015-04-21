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
		
		String user_id = session.getAttribute("user").toString();
		
		String nom = request.getParameter("nom");
		String tt = request.getParameter("tt");
		
		int test_nq = 0;
		float test_p = 0;
		int test_done=0;
		String test_id = "";
		
		String sql1 = "Select Id_Tests, Description, NumberOfQuestions, Substracts, Penalization from Tests where Description ='" + nom + "'";
		try{
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql1);
			if (rs.next()) {
				test_id = rs.getString("Id_Tests");
				test_nq = Integer.parseInt(rs.getString("NumberOfQuestions"));
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
		
		String sql2 = "SELECT DISTINCT Questions.IdTest, Questions.IdQuestion, StudentAnswer.IdStudent, StudentAnswer.AnswerS, Questions.Answer FROM Questions INNER JOIN StudentAnswer ON (Questions.IdQuestion = StudentAnswer.IdQuestion) AND (Questions.IdTest = StudentAnswer.IdTest) WHERE StudentAnswer.IdStudent='" + user_id + "' AND Questions.IdTest=" + test_id + "";
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
		
		String sql3 = "Select Done, Id_Test, Id_Student from TestStudent where Id_Test =" + test_id + " and Id_Student='" + user_id + "'";
		try{
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql3);
			if (rs.next()) {
				test_done = Integer.parseInt(rs.getString("Done"));
				System.out.println("NOT DONE: " + test_done);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Resulset: " + sql3 + " Exception: " + e);
		}
		
		if (test_done == 0) {
			String sql4 = "UPDATE TestStudent SET TestStudent.Done=1, TestStudent.Mark=" + mark + " WHERE (((TestStudent.Id_Test) =" + test_id + ") and ((TestStudent.Id_Student)='" + user_id + "'))";
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