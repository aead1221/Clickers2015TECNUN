package org.iso;
import java.sql.*;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class MarksFormSurvey extends HttpServlet {

	Connection conn = null;
	String nombre=null;
	String apellido=null;
	String apellido1=null;
	String SurveyName=null;
	String SurveyId=null;
	
	
	String sql =null;
	
	String nota=null;
	public void init (ServletConfig config) throws ServletException {   
		super.init(config);

		String dsn = new String("jdbc:odbc:Clickers"); 

		try {
		  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		} catch(ClassNotFoundException ex) {
		  System.out.println("Error al cargar el driver");
		  System.out.println(ex.getMessage());
		  ex.printStackTrace();
		}
		try { 
		  conn = DriverManager.getConnection(dsn, "", "");
		} catch (SQLException sqlEx) {
		  System.out.println("Se ha producido un error al establecer la conexion con: " + dsn);
		  System.out.println("Message: " + sqlEx.getMessage());
		  System.out.println("StackTrace: ");
		  sqlEx.printStackTrace();
		}

		System.out.println("Iniciando Calificaciones (version BD)...");
	}
	
	public void destroy () {
		super.destroy();
		System.out.println("Cerrando conexion...");
		try {
		  conn.close();
		} catch(SQLException ex){
		  System.out.println("No se pudo cerrar la conexion");
		  System.out.println(ex.getMessage());
		  ex.printStackTrace();
		}
	}
	
	public void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("text/html");
		SurveyId = req.getParameter("SurveyId");
		SurveyName=req.getParameter("SurveyName");
    
		PrintWriter out = null;
		try {
			out=resp.getWriter();
		} catch (IOException io) {
			System.out.println("alumnosExcepcion");    
		}

		HttpSession session = req.getSession(true);
		
		
		clickers.printTop(resp, session.getAttribute("name").toString());

		
			out.println("<div id='clk-title'>");
			out.println("<label><h3><p align='center'> "+SurveyName+"</p></h3></label>");
			out.println("</div>");
			out.println("<div id='clk-box'>");
			
			
			
			out.println("<table style='padding-left:11%; padding-top:5%'>");
		
			out.println("<form method='get' action='UploadMarksSurvey'> ");
			
			out.println("<tr class='clk-subtitle-st'>");
			out.println("<td><label><p align='left'><h3><U> USER </U></h3></p></label></td>");
			out.println("<td> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>");
			out.println("<td><label><p align='center'><h3><U> MARKS </U></h3></p></label></td>");
			out.println("<td> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>");
		
			out.println("</tr>");
			out.println("</div>");
			
			
			
			String sql="SELECT  FirstName, LastName1, LastName2, Id_Student from StudentsBF,SurveyStudentBF where Id_Student=Id and Id_Survey="+SurveyId+"";
			
			
					System.out.println(sql);
					try {
						Statement stmt=conn.createStatement();
						ResultSet rs = stmt.executeQuery(sql); //ejecutar sql
						while(rs.next()) {
							nombre=rs.getString("FirstName");
							apellido=rs.getString("LastName1");
							apellido1=rs.getString("LastName2");
							
							
							out.println("");
							out.println("<tr class='clk-content-st'>");
							out.println("<td><label>" + nombre +" "+ apellido+" "+ apellido1+"</label></td>");
							out.println("<td> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>");
							out.println("<td><label><p align='center'><input class='clk-button' type='text' name="+nombre+" value=''></p></label></td>");
							out.println("<td> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>");
							out.println("</tr>");
						}
						
						
						

					} catch(SQLException e) {
						e.printStackTrace();
						System.out.println("Resulset: " + sql + " Exception: " + e);
						
					}
					
			out.println("<td><label><p align='center'><input class='clk-button' type='submit' value='UploadMarks'  onclick='inicio()'></p></label></td>");
			out.println("<input type='hidden' name='SurveyId' value="+SurveyId+">");
			out.println("<input type='hidden' name='SurveyName' value="+SurveyName+">");
					
			out.println("</form>");
			out.println("</TABLE>");
			out.println("<BR>");
			out.println("<BR><a href=\"menuTeacher\"><input class='clk-button' type='submit' value='Back To Menu'></a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size='2'>* Acuerdese de rellenar todos los campos</font></div>");
			
			

			clickers.printBottom(resp);

			out.flush();
			out.close();
		
	} 
}
