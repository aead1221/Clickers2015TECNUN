package org.iso;
import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class EditTestContent extends HttpServlet {
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
		
		String idtest;
		idtest = req.getParameter("id");
		int id = Integer.parseInt(idtest);
		
		String number = "";
		String options = "hola";
		String testName = "";
		
		String sql = "Select Name, NumberOfQuestions, NumberOfOptions FROM TestsAI WHERE Id_Tests=" + id +"";
		
		System.out.println(sql);
		
		try {
		Statement statement=connection.createStatement();
		ResultSet result = statement.executeQuery(sql);
		
		while(result.next()) {
		testName = result.getString("Name");
		number = result.getString("NumberOfQuestions");
		options = result.getString("NumberOfOptions");
		}
		}
		
		catch(SQLException e) {
		e.printStackTrace();
		System.out.println("Resulset: " + sql + " Exception: " + e);
		}
		
		
		int num = Integer.parseInt(number);
		int op = Integer.parseInt(options);
		
		
						
		
	
		
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
		out.println("<title>Test Edition</title>");
		out.println("</head>");
        out.println("<body>");
        out.println("<header><div class='my-header'><h1>Clickers</h1><h2>TECNUN's online test taking platform</h2></div></header>");
        out.println("<div class='my-box'>");
		out.println("<h3 align='center'>Test Content</h3><br>");
		out.println("<FORM Action='UpdateTestChanges'>");
		out.println("<FORM Action='UpdateTestChanges'>");
		out.println("<table>");
		
		String sqlz = "Select OptionB FROM OptionsAI WHERE IdTest=" + id + "";
		int dim = num * op;
		String[] Opciones = new String[dim];
		int contid = 0;
		
		
		System.out.println(sqlz);
		
		try {
		Statement statement=connection.createStatement();
		ResultSet result = statement.executeQuery(sqlz);
		
		while(result.next()) {
		 
		Opciones[contid] = result.getString("OptionB");
		contid = contid + 1;
		
		
		
		
		}
		}
		catch(SQLException e) {
		e.printStackTrace();
		System.out.println("Resulset: " + sqlz + " Exception: " + e);
		}
		
		String sqlo = "Select Id_Question, Question, Answer FROM QuestionsAI WHERE Id_Test=" + id +"";
		
		System.out.println(sqlo);
		
		String position = "";
		String quest = "";
		String answer = "";
		int pos = 0;	
		int conti = 0;
		
		try {
		Statement statement=connection.createStatement();
		ResultSet result = statement.executeQuery(sqlo);
		
		while(result.next()) {
		position = result.getString("Id_Question");
		pos = Integer.parseInt(position);
		quest = result.getString("Question");
		answer = result.getString("Answer");
		int answ = Integer.parseInt(answer);
		
		
		
		
		
		
		out.println("<tr><td colspan='2'><h4> Question " + pos +"<h4></td></tr>");
		out.println("<tr><td colspan='2'><INPUT type='text' name='question' value='" + quest + "' style='width:200px;'></td><td colspan='2'><h4> Answer <h4></td><td colspan='2'><INPUT type='text' name='answer' value='" + answ + "'  style='width:30px;'></td></tr>");
		
		for(int j=0; j<op; j++) {
				
				int w = j + 1;
				out.println("<tr><td><h6>Option " + w + ":</h6></td><td><INPUT type='text' name='option" + pos + w + "' value='" + Opciones[conti] + "'></td>");
				conti = conti + 1;
				
			}
			
               
        }
			out.println("<tr><td colspan='2'>&nbsp</td></tr>"); 
		}
		
		
		catch(SQLException e) {
		e.printStackTrace();
		System.out.println("Resulset: " + sqlo + " Exception: " + e);
		}
	
		out.println("</table>");
		out.println("<INPUT type='submit' value='Save changes'>");
		out.println("<INPUT name='num' type='hidden' value= "+ num +">");
		out.println("<INPUT name='op' type='hidden' value= "+ op +">");
		out.println("<INPUT name='nom' type='hidden' value= "+ testName +">");
		out.println("<INPUT name='codtest' type='hidden' value= "+ id +">");
		
		out.println("</FORM>");
		out.println("<BR><h6><a href='TeacherMenu.html'>Back to menu</a></h6>");
		out.println("</div><footer>&copy UN 2015</footer></body></html>");
		
		
		
		
	}
}
		
		