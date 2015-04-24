package org.iso;
import java.sql.*;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;



public class SaveTestData extends HttpServlet {
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
		
		
		String strNumero = req.getParameter("num");
		String strOpciones = req.getParameter("op");
		String nom = req.getParameter("nom");
		String subject = req.getParameter("subjectcod");
		//Get id user
		String idteacher = session.getAttribute("user").toString();
		//String idteach = "t1";
		
		
		
		
		int num = Integer.parseInt(strNumero);
		int op = Integer.parseInt(strOpciones);
		int idsubject = Integer.parseInt(subject);
		
		String[] Preguntas = req.getParameterValues("question");
		int[] Answer = new int[num];
		String pickanswer;
		int r;
		
		for (int t=0; t<num; t++) {
			
			r = t + 1;
			pickanswer = req.getParameter("" + r + "");
			Answer[t] = Integer.parseInt(pickanswer);
		}
		
		String sqlk = "Select Id_Tests FROM Tests";
        System.out.println(sqlk);
		int testcount = 0;
        try {
            Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sqlk);
            while(result.next()) {
				testcount =  testcount + 1;
               System.out.println(testcount);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Resulset: " + sqlk + " Exception: " + e);
        }
		int idtest = testcount + 1;
		//int idprovteach = 1;
		
		String sql = "INSERT INTO Tests (Id_Tests, Description, Id_Course, Id_Teacher, NumberOfQuestions, NumberOfOptions) VALUES (";	   
		sql +=  "'" + idtest + "'";
        sql +=  ", '" + nom + "'";
		sql +=  ", '" + idsubject + "'";
		sql +=  ", '" + idteacher + "'"; 
		sql +=  ", '" + num + "'";
        sql +=  ", '" + op + "')";
		
		
        System.out.println("Insert sql: " + sql);
		
		
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Resulset: " + sql + " Exception: " + e);
        }
		
		for (int i=0; i<Preguntas.length; i++) {
			String sqlf = "INSERT INTO Questions (IdTest, IdQuestion, QuestionT, Answer) VALUES (";
			int j = i + 1;
			sqlf +=  "'" + idtest + "'";
			sqlf +=  ", '" + j + "'"; 
			sqlf +=  ", '" + Preguntas[i] + "'";
			sqlf +=  ", '" + Answer[i] + "')";

			System.out.println("Insert sql: " + sqlf);
			try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlf);
		    }catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Resulset: " + sqlf + " Exception: " + e);
             }
			}
		String optiones = "";
		
		
		;
		for (int k=0; k<Preguntas.length; k++) {
			int p = k + 1;
			for (int l=0; l<op; l++) {
			String sqlfu = "INSERT INTO Options (IdTest, IdQuestion, IdOption, OptionT) VALUES (";
			int g = l + 1;
			optiones = "";
			optiones = req.getParameter("option" + k + "" + l + "");
			
			sqlfu +=  "'" + idtest + "'";
			sqlfu +=  ", '" + p + "'";
			sqlfu +=  ", '" + g + "'";
			sqlfu +=  ", '" + optiones + "')";
			
			System.out.println("Insert sql: " + sqlfu);
			try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlfu);
			}catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Resulset: " + sqlfu + " Exception: " + e);
			 }
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
		//out.println("<title>Test Creation</title>");
		//out.println("</head>");
        //out.println("<body>");
        //out.println("<header><div class='my-header'><h1>Clickers</h1><h2>TECNUN's online test taking platform</h2></div></header>");
		clickers.printTop(resp, session.getAttribute("name").toString(), session.getAttribute("user").toString());
		out.println("<div id='clk-box'>");
		out.println("<h3 align='center'>Test Saved</h3><br>");
		out.println("<table>");
		
		out.println("</table>");
		
		out.println("</FORM>");
		out.println("<BR><h6><a href='menuTeacher'>Back to menu</a></h6>");
		out.println("</div>");
		clickers.printBottom(resp);
		//<footer>&copy UN 2015</footer></body></html>");
		
		
		
	}
}
		
		
			
			
			
		
		
		