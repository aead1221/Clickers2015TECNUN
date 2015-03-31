package org.iso;
import java.sql.*;
import java.io.*; 
import java.util.*;
import javax.servlet.*; 
import javax.servlet.http.*;
 
public class initTest extends HttpServlet{ 
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
		String student_id = "" + session.getAttribute("user") + "";
		
		String nom = req.getParameter("nom");
		String tt = req.getParameter("tt");
		
		String test_id = "";
		int test_nq = 0;
		
		String sql1 = "Select IdTest, TestName from TestsAA where TestName ='" + nom + "'";
		try{
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql1);
			if (rs.next()) {
				test_id = rs.getString("IdTest");
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Resulset: " + sql1 + " Exception: " + e);
		}
		
		String sql2 = "INSERT INTO TestStudentAA (IdTest, IdStudent, DoneS, Mark) VALUES ("+ test_id +", " + student_id + ", 0, 0)";
		try{
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql2);             //This Update is meant to write the Test-Student register, it is presumably not working because of an Access restriction.
		} catch(SQLException e) {
			System.out.println("Test/Survey has already been taken and cannot be overwritten");
			System.out.println("Resulset: " + sql2 + " Exception: " + e);
		}
		
		ServletContext sc = getServletContext();
		if (tt.equals("test")) {
			RequestDispatcher rd = sc.getRequestDispatcher("/takeTest?nom="+ nom + "&num=0&tt=" + tt +"");
			rd.forward(req , resp);
		} else {
			System.out.println(tt);
			RequestDispatcher rd = sc.getRequestDispatcher("/takeSurvey?nom="+ nom + "&num=0&tt=" + tt +"");
			rd.forward(req , resp);
		}
		
		
	}
}