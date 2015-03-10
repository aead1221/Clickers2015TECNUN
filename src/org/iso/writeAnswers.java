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
		String student_id = "" + session.getAttribute("user_id") + "";
			
		int num = Integer.parseInt(req.getParameter("num"));
		String nom = req.getParameter("nom");
		String sresp = "" + req.getParameter("resp") + "";
		
		String test_id = "";
		int test_nq = 0;
		
		String sql1 = "Select Id_Tests, Name, NumberOfQuestions from Tests where Name ='" + nom + "'";
		try{
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql1);
			if (rs.next()) {
				test_id = rs.getString("Id_Tests");
				test_nq = Integer.parseInt(rs.getString("NumberOfQuestions"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Resulset: " + sql1 + " Exception: " + e);
		}
		
		/*String sql2 = "Insert into [Student-Answer] (Id_Student, Id_Test, Id_Question, StudentAnswer) Values ('" + student_id + "', '" + test_id + "', '" + (num+1) +"', '" + sresp + "')";
		try{
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql2);						//This Update is meant to write answers on de DB
		} catch(SQLException e) {							//but has no value without the Update on the initTest servlet
			e.printStackTrace();							//because of an enforced relation
			System.out.println("Resulset: " + sql2 + " Exception: " + e);
		}*/
		
		ServletContext sc = getServletContext();
		if (num != test_nq - 1) {
			RequestDispatcher rd = sc.getRequestDispatcher("/test?nom="+ nom + "&num=" + (num+1) + "");
			rd.forward(req , resp);
		} else {
			RequestDispatcher rd = sc.getRequestDispatcher("/testFinished.html");
			rd.forward(req , resp);
		}
		
	}
}