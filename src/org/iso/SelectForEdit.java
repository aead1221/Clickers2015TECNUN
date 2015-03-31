package org.iso;
import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SelectForEdit extends HttpServlet {
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
	public void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession(true);
		
		resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
	//Prove that it takes well the teacher id
	String idteacher = session.getAttribute("user").toString();
	int idteach = Integer.parseInt(idteacher);
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
		out.println(".my-box {background-color: white; font-size:18pt;font-weight: normal;border: 3px solid #990000;border-radius:10px; padding: 40px;margin-top:20%;margin-left: auto;margin-right: auto;width: 30%;height: 100%;}");
		out.println("</style>");
		out.println("<title>Select For Edit</title>");
		out.println("</head>");
        out.println("<body>");
        out.println("<header><div class='my-header'><h1>Clickers</h1><h2>TECNUN's online test taking platform</h2></div></header>");
        out.println("<div class='my-box'>");
		out.println("<h3 align='center'>Edit Test or Surveys</h3><br>");
			
			
		String sql = "Select Id_Tests, Name FROM TestsAI WHERE Id_Teacher=" + idteach + "";//idteach
		String idtest;
		System.out.println(sql);
		int contador = 0;
		out.println("<table>");
		
		try {
			Statement statement=connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
		while(result.next()) {
			contador = contador + 1;
			if (contador==1) {
			out.println("<tr><h3 align='center'>TESTS</h3></tr>");
			}
			else {
			}
			idtest = result.getString("Id_Tests");
			String testName = result.getString("Name");
			out.println("<tr><td colspan='2'><h4><a href='EditTestContent?id=" + idtest + "'><b>" + contador + ". " + testName +"</b></a></h4></td></tr>");
				
		}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Resulset: " + sql + " Exception: " + e);
				}
		out.println("</table>");
		out.println("<BR>");
		out.println("<table>");
						
				
		int container = 0;
		String sqlf = "Select Id_Survey, Survey FROM SurveyAI WHERE Id_Teacher=" + idteach + "";//idteach
		System.out.println(sqlf);
		try {
		    Statement statement=connection.createStatement();
		    ResultSet result = statement.executeQuery(sqlf);
		while(result.next()) {
			container = container + 1;
			if (container==1) {
			out.println("<tr><h3 align='center'>SURVEYS</h3></tr>");
			}
			else {
			}
			
			String idsurvey = result.getString("Id_Survey");
			String surveyName = result.getString("Survey");
			
			out.println("<tr><td colspan='2'><h4><a href='EditSurveyContent?id=" + idsurvey + "'><b>" + container + ". " + surveyName +"</b></a></h4></td></tr>");
			
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Resulset: " + sqlf + " Exception: " + e);
		   }
				
		out.println("</table>");
		out.println("<BR>");
		out.println("<BR>");
			
		out.println("<BR><h6><a href='TeacherMenu.html'>Back to menu</a></h6>");
		out.println("</div><footer>&copy UN 2015</footer></body></html>");
		
				
			
			
				


			
		}
	}
					
				