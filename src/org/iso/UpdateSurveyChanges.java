package org.iso;
import java.sql.*;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;



public class UpdateSurveyChanges extends HttpServlet {
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
	
        String strOpciones = req.getParameter("scaleconfirm");
		String idsurvey = req.getParameter("cod");
		
		
		int op = Integer.parseInt(strOpciones);
		int idsurv = Integer.parseInt(idsurvey);
		
		String sqlUpdate1 = "UPDATE Surveys SET NumberOfOptions=" + op + " WHERE Id_Survey=" + idsurv + "";
		System.out.println(sqlUpdate1);
		
		try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlUpdate1);
			}
			catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Resulset: " + sqlUpdate1 + " Exception: " + e);
			}
		
		
		String[] Preguntas = req.getParameterValues("question");
		
		for (int i=0; i<Preguntas.length; i++) {
		int j = i + 1; 
		
		String sqlUpdate = "UPDATE Squestions SET Question='" + Preguntas[i] + "' WHERE Id_Survey=" + idsurv + " AND Id_Question=" + j + "";
		System.out.println(sqlUpdate);
		
		try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlUpdate);
			}
			catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Resulset: " + sqlUpdate + " Exception: " + e);
			}
		}
	
//	out.println("<!DOCTYPE html>");
//	out.println("<html lang='en'>");
//	out.println("<head>");
//	out.println("<style type='text/css'>");
//	out.println("* {padding: 0px; margin: 0px}"); 
//	out.println("body {font-family:helvetica;font-size: 14px;font-weight:normal;background: url('bg.png') no-repeat top left;background-attachment: fixed}"); 
//	out.println("header {background-color:#990000;position: fixed;top: 0px;width: 100%}"); 
//	out.println("header h1 {color:white;font-size: 64pt;font-weight: lighter}"); 
//	out.println("header h2 {color:#C0C0C0;font-weight: normal;padding-left:30px}"); 
//	out.println("footer {position: fixed;top: 97%;right: 1%;}");
//	out.println(".my-header {padding: 30px}");
//	out.println(".my-box {background-color: white; font-size:18pt;font-weight: normal;border: 3px solid #990000;border-radius:10px; padding: 40px;margin-top:20%;margin-left: auto;margin-right: auto;width: 30%;height: 100%;}");
//	out.println("</style>");
//	out.println("<title>Survey Edition</title>");
//	out.println("</head>");
//    out.println("<body>");
   // out.println("<header><div class='my-header'><h1>Clickers</h1><h2>TECNUN's online test taking platform</h2></div></header>");
	clickers.printTop(resp, session.getAttribute("name").toString(), session.getAttribute("user").toString());
	out.println("<div id='clk-box'>");
	out.println("<h3 align='center'>Survey Edited</h3><br>");
	out.println("<table>");
		
	out.println("</table>");
		
	out.println("</FORM>");
	out.println("<BR><h6><a href='menuTeacher'>Back to menu</a></h6>");
	out.println("</div>");
	clickers.printBottom(resp);
	//<footer>&copy UN 2015</footer></body></html>");
	}
	}
		
		
		
	