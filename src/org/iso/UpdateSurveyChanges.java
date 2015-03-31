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
		
		resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
		
		String strNumero = req.getParameter("num");
		String strOpciones = req.getParameter("op");
		String testName = req.getParameter("nom");
		String idsurvey = req.getParameter("cod");
		
		int num = Integer.parseInt(strNumero);
		int op = Integer.parseInt(strOpciones);
		int idsurv = Integer.parseInt(idsurvey);
		
		int dimension = num * op;
		int sum = 0;
		
		String[] Preguntas = req.getParameterValues("question");
		String[] Options = new String[dimension];
		
		
		for (int b=0; b<num; b++) {
			for (int c=0; c<op; c++) {
			int h = b + 1;
			int k = c + 1;
			String optiones = "";
			optiones = req.getParameter("option" + h + k + "");
			Options[sum] = optiones;
			sum = sum + 1;
			}
		}
		for (int i=0; i<Preguntas.length; i++) {
		int j = i + 1; 
		
		String sqlUpdate = "UPDATE SquestionsAI SET Question='" + Preguntas[i] + "' WHERE Id_Survey=" + idsurv + " AND Id_Question=" + j + "";
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
		int suma = 0;
		for (int d=0; d<num; d++) {
			for (int p=0; p<op; p++) {
				int f = d + 1;
				int g = p + 1;
				String sqlUpdate = "UPDATE SoptionsAI SET OptionB='" + Options[suma] + "' WHERE IdSurvey=" + idsurv + " AND IdQuestion=" + f + "AND IdOption=" + g + "";
				suma = suma + 1;
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
			}
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
	out.println("<title>Survey Edition</title>");
	out.println("</head>");
    out.println("<body>");
    out.println("<header><div class='my-header'><h1>Clickers</h1><h2>TECNUN's online test taking platform</h2></div></header>");
    out.println("<div class='my-box'>");
	out.println("<h3 align='center'>Survey Edited</h3><br>");
	out.println("<table>");
		
	out.println("</table>");
		
	out.println("</FORM>");
	out.println("<BR><h6><a href='TeacherMenu.html'>Back to menu</a></h6>");
	out.println("</div><footer>&copy UN 2015</footer></body></html>");
	}
	}
		
		
		
	