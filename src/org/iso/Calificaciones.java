package org.iso;
import java.sql.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Calificaciones extends HttpServlet {

	Connection conn = null;
	String nombre=null;
	String apellido=null;
	String apellido1=null;
	String sql =null;
	String sql1 =null;
	String a =null;
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

		PrintWriter out = null;
		try {
			out=resp.getWriter();
		} catch (IOException io) {
			System.out.println("alumnosExcepcion");    
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
			out.println(".my-box {background-color: white; font-size:18pt;font-weight: normal;border: 3px solid #990000;border-radius:10px; padding: 20px;margin-top:20%;margin-left: auto;margin-right: auto;width: 40%;height: 100%;}");
			out.println("</style>");
			out.println("<title>Calificaciones</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<header><div class='my-header'><h1>Clickers</h1><h2>TECNUN's online test taking platform</h2></div></header>");
			out.println("<div class='my-box'>");
			out.println("<TABLE border='0'>");
			out.println("<TR>");
			out.println("<BR>");
			out.println("<TH width='3000'>");
			out.println("<B>");
			out.println("STUDENT");
			out.println("</B>");
			out.println("</TH>");
			out.println("<TH width='600'>");
			out.println("<B>");
			out.println("MARK");
			out.println("</B>");
			out.println("</TH>");
			out.println("</TR>");
			out.println("<TR>");
			out.println("<TD colspan=2>&nbsp");
			out.println("</TD>");
			out.println("</TR>");
			
			 sql ="Select Id_Student FROM [Test-Student] Where Id_Test=1";
			
			try {
				Statement statement=conn.createStatement();
				ResultSet result = statement.executeQuery(sql); //ejecutar sql
				
				while(result.next()) {
				a=result.getString("Id_Student");
				//Coger el nombre del alumno al que pertenece la nota (sin hacer)
					 sql1 = "Select [First Name],[Last Name 1], [Last Name 2], Mark FROM Students, [Test-Student] WHERE Id=" + a + "";
					System.out.println(sql1);
					try {
						Statement stmt=conn.createStatement();
						ResultSet rs = stmt.executeQuery(sql1); //ejecutar sql1
						while(rs.next()) {
							  nombre=rs.getString("[First Name]");
							  apellido=rs.getString("[Last Name 1]");
							  apellido1=rs.getString("[Last Name 2]");
							  nota=rs.getString("Mark");
						}
					} catch(SQLException e) {
						e.printStackTrace();
						System.out.println("Resulset: " + sql1 + " Exception: " + e);
					}
				
					//escribir la información de la tarea
					out.println("   <tr>");
					out.println("   <td width='3000'><Font size=\"3\">"+ nombre +" "+ apellido+" "+ apellido1+"</td>");
					out.println("   <td width='600'><Font size=\"3\">"+ nota +"</td>");
					out.println("   </tr>");
				}
				
				} catch(SQLException e) {
				e.printStackTrace();
				System.out.println("Resulset: " + sql + " Exception: " + e);
				
			}
			
			
			
			

			out.println("</TABLE>");
			out.println("<BR>");
			out.println("<BR><a href=\"menu\">Back to menu</a>");
			out.println("</div>");
			out.println("<footer>&copy UN 2015</footer></BODY>");
			out.println("</HTML>");

			out.flush();
			out.close();
		
	} 
}
