package org.iso;
import java.sql.*;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;




public class SaveSurveyData extends HttpServlet {
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
	//Get user id
	String idteacher = session.getAttribute("user").toString();
	
	
	//String idteach = "t1";
		
		
	resp.setContentType("text/html");
    PrintWriter out = resp.getWriter();
	
	String strNumero = req.getParameter("num");
	String strOpciones = req.getParameter("op");
	String nom = req.getParameter("nom");
	String subject = req.getParameter("subjectcod");
	
	int num = Integer.parseInt(strNumero);
	int op = Integer.parseInt(strOpciones);
	int idsubject = Integer.parseInt(subject);
	
	
	String[] Preguntas = req.getParameterValues("question");
	
	String sql = "Select Id_Survey FROM Surveys";
    System.out.println(sql);
	int surveycount = 0;
    
	try {
		Statement statement=connection.createStatement();
		ResultSet result = statement.executeQuery(sql);
    while(result.next()) {
		surveycount =  surveycount + 1;
        }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Resulset: " + sql + " Exception: " + e);
        }
	int idsurvey = surveycount + 1;
	//int idprovteach = 1;
	
	//insert id_teacher y id_subject
	
	String sqlf = "INSERT INTO Surveys (Id_Survey, Survey, Id_Course, Id_Teacher, NumberOfQuestions, NumberOfOptions) VALUES (";	   
	sqlf +=  "'" + idsurvey + "'";
    sqlf +=  ", '" + nom + "'";
	sqlf +=  ", '" + idsubject + "'";
	sqlf +=  ", '" + idteacher + "'"; //idteach
	sqlf +=  ", '" + num + "'";
    sqlf +=  ", '" + op + "')";
		
		
    System.out.println("Insert sqlf: " + sqlf);
		
		
    try{
    Statement statement = connection.createStatement();
    statement.executeUpdate(sqlf);
    } catch(SQLException e) {
    e.printStackTrace();
    System.out.println("Resulset: " + sqlf + " Exception: " + e);
        }
	for (int i=0; i<Preguntas.length; i++) {
			String sqli = "INSERT INTO Squestions (Id_Question, Question, Id_Survey) VALUES (";
			int j = i + 1;
			sqli +=  "" + j + "";
			sqli +=  ", '" + Preguntas[i] + "'";
			sqli +=  ", " + idsurvey + ")";
			

			System.out.println("Insert sql: " + sqli);
			try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqli);
		    }catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Resulset: " + sqli + " Exception: " + e);
             }
			}
	
	//out.println("<!DOCTYPE html>");
	//out.println("<html lang='en'>");
	//out.println("<head>");
	//out.println("<style type='text/css'>");
	//out.println("* {padding: 0px; margin: 0px}"); 
	//out.println("body {font-family:helvetica;font-size: 14px;font-weight:normal;background: url('bg.png') no-repeat top left;background-attachment: fixed}"); 
	//out.println("header {background-color:#990000;position: fixed;top: 0px;width: 100%}"); 
	//out.println("header h1 {color:white;font-size: 64pt;font-weight: lighter}"); 
	//out.println("header h2 {color:#C0C0C0;font-weight: normal;padding-left:30px}"); 
	//out.println("footer {position: fixed;top: 97%;right: 1%;}");
	//out.println(".my-header {padding: 30px}");
	//out.println(".my-box {background-color: white; font-size:18pt;font-weight: normal;border: 3px solid #990000;border-radius:10px; padding: 40px;margin-top:20%;margin-left: auto;margin-right: auto;width: 30%;height: 100%;}");
	//out.println("</style>");
	//out.println("<title>Survey Creation</title>");
	//out.println("</head>");
    //out.println("<body>");
    //out.println("<header><div class='my-header'><h1>Clickers</h1><h2>TECNUN's online test taking platform</h2></div></header>");
	clickers.printTop(resp, session.getAttribute("name").toString(), session.getAttribute("user").toString());
	out.println("<div id='clk-box'>");
	out.println("<h3 align='center'>Survey Saved</h3><br>");
	out.println("<table>");
		
	out.println("</table>");
		
	out.println("</FORM>");
	out.println("<BR><h6><a href='menuTeacher'>Back to menu</a></h6>");
	out.println("</div>");
	clickers.printBottom(resp);
	//<footer>&copy UN 2015</footer></body></html>");
}
}