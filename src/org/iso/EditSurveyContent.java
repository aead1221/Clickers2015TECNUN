package org.iso;
import java.sql.*;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class EditSurveyContent extends HttpServlet {
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
		
		String idsurvey;
		idsurvey = req.getParameter("id");
		int id = Integer.parseInt(idsurvey);
		
		String number = "";
		String options = "hola";
		String surveyName = "";
		
		String sql = "Select Survey, NumberOfQuestions, NumberOfOptions FROM Surveys WHERE Id_Survey=" + id +"";
		System.out.println(sql);
		
		try {
		Statement statement=connection.createStatement();
		ResultSet result = statement.executeQuery(sql);
		
		while(result.next()) {
		surveyName = result.getString("Survey");
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
		
//		out.println("<!DOCTYPE html>");
//
//		
//		out.println("<html lang='en'>");
//		out.println("<head>");
//		out.println("<style type='text/css'>");
//		out.println("* {padding: 0px; margin: 0px}"); 
//		out.println("body {font-family:helvetica;font-size: 14px;font-weight:normal;background: url('bg.png') no-repeat top left;background-attachment: fixed}"); 
//		out.println("header {background-color:#990000;position: fixed;top: 0px;width: 100%}"); 
//		out.println("header h1 {color:white;font-size: 64pt;font-weight: lighter}"); 
//		out.println("header h2 {color:#C0C0C0;font-weight: normal;padding-left:30px}"); 
//		out.println("footer {position: fixed;top: 97%;right: 1%;}");
//		out.println(".my-header {padding: 30px}");
//		out.println(".my-box {background-color: white; font-size:18pt;font-weight: normal;border: 3px solid #990000;border-radius:10px; padding: 40px;margin-top:20%;margin-left: auto;margin-right: auto;width: 30%;height: 100%;}");
//		out.println("</style>");
//		out.println("<title>Survey Edition</title>");
//		out.println("</head>");
//        out.println("<body>");
        //out.println("<header><div class='my-header'><h1>Clickers</h1><h2>TECNUN's online test taking platform</h2></div></header>");
		clickers.printTop(resp, session.getAttribute("name").toString(), session.getAttribute("user").toString());
		out.println("<div id='clk-box'>");
		out.println("<h3 align='center'>Survey Content</h3><br>");
		out.println("<FORM Action='UpdateSurveyChanges'>");
		out.println("<FORM Action='UpdateSurveyChanges'>");
		out.println("<table>");
		
		String sqlz = "Select OptionT FROM ScaleOptions WHERE IdScale=" + op + "";
		
		int dim = op;
		String[] Opciones = new String[dim];
		int contid = 0;
		
		System.out.println(sqlz);
		
		try {
		Statement statement=connection.createStatement();
		ResultSet result = statement.executeQuery(sqlz);
		
		while(result.next()) {
		 
		Opciones[contid] = result.getString("OptionT");
		contid = contid + 1;
		}
		}
		catch(SQLException e) {
		e.printStackTrace();
		System.out.println("Resulset: " + sqlz + " Exception: " + e);
		}
		
		String sqlo = "Select Id_Question, Question FROM Squestions WHERE Id_Survey=" + id +"";
		
		System.out.println(sqlo);
		
		String position = "";
		String quest = "";
		int pos = 0;	
	
		
		try {
		Statement statement=connection.createStatement();
		ResultSet result = statement.executeQuery(sqlo);
		
		while(result.next()) {
		position = result.getString("Id_Question");
		pos = Integer.parseInt(position);
		quest = result.getString("Question");
		
		out.println("<tr><td colspan='2'><h4> Question " + pos +"<h4></td></tr>");
		out.println("<tr><td colspan='2'><INPUT type='text' name='question' value='" + quest + "' style='width:200px;'></td></tr>");
		
		for(int j=0; j<op; j++) {
				
				int w = j + 1;
				out.println("<tr><td><h6>Option " + w + ":</h6></td><td><INPUT type='text' name='option" + pos + w + "' value='" + Opciones[j] + "'></td>");
				
				
			}
			
               
        }
			out.println("<tr><td colspan='2'>&nbsp</td></tr>"); 
		}
		catch(SQLException e) {
		e.printStackTrace();
		System.out.println("Resulset: " + sqlo + " Exception: " + e);
		}
		out.println("<tr>");
		out.println("<h6 align='left'>Confirm Scale Options of the Survey:</h6>");
		out.println("</tr><tr>");
		out.println("<select name='scaleconfirm'>");
		out.println("<option value='3'>3: Disagree,Neutral, Agree</option>");
		out.println("<option value='5'>5: Strongly disagree,Disagree, Neutral, Agree, Strongly agree</option>");
		out.println("</select");
		out.println("<br>");
		out.println("</tr>");
		out.println("</table>");
		out.println("<INPUT type='submit' value='Save changes'>");
		out.println("<INPUT name='num' type='hidden' value= "+ num +">");
		out.println("<INPUT name='op' type='hidden' value= "+ op +">");
		out.println("<INPUT name='nom' type='hidden' value= "+ surveyName +">");
		out.println("<INPUT name='cod' type='hidden' value= "+ id +">");
		
		out.println("</FORM>");
		out.println("<BR><h6><a href='menuTeacher'>Back to menu</a></h6>");
		out.println("</div>");
		clickers.printBottom(resp);
		//<footer>&copy UN 2015</footer></body></html>");
		}
		}