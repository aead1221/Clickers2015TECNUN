package org.iso;
import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class menuTeacher extends HttpServlet {

	Connection conn = null;
	
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

		HttpSession session = req.getSession(true);
		
		
		clickers.printTop(resp, session.getAttribute("name").toString());


		out.println("					<div>");
		out.println("						<p align='center'>");
		out.println("						<IMG id='menu-pic' SRC='images/teacher.jpg'>");
		out.println("						</p>");
		out.println("					</div>");
		out.println("					");
		out.println("					<table id='menu-content'>");
		out.println("						<tr>");
		out.println("							<td>");
		out.println("								<div class='menu-box'>");
		out.println("									");
		out.println("									<div class='menu-header-st'>");
		out.println("										<label>Menu</label>");
		out.println("									</div>");
		out.println("								");
		out.println("									<table style='padding-left:15%; padding-top:10%'>");
		out.println("										<tr>");
		out.println("											<td><label><a href='requestTestSpecifications'><b>Create test</b></a></label></td> ");
		out.println("											");
		out.println("										</tr>");
		out.println("										<tr>");
		out.println("											<td><label><a href='requestSurveySpecifications'><b>Create survey</b></a></label></td> ");
		out.println("											");
		out.println("										</tr>");
		out.println("										<tr>");
		out.println("											<td><label><a href='SelectForEdit'><b>Edit test or survey</b></a></label></td>");
		out.println("											");
		out.println("										</tr>");
		out.println("										<tr>");
		out.println("											<td><label><a href='ViewResultsTeacher'><b>View results</b></a></label></td>");
		out.println("											");
		out.println("										</tr>");
		out.println("									");
		out.println("									</table>");
		out.println("								");
		out.println("								</div>");
		out.println("							</td>");
		out.println("							<td>");
		out.println("								<div class='menu-box'>");
		out.println("									<div class='menu-header-st'>");
		out.println("										<label>Links</label>");
		out.println("									</div>");
		out.println("									");
		out.println("									<table style='padding-left:15%; padding-top:10%'>");
		out.println("										<tr>");
		out.println("											<td><label><a href=http://www.tecnun.es/profesores-pas/gestion-academica><b>Faculty/Pas</b></a></label></td>");
		out.println("											");
		out.println("										</tr>");
		out.println("										<tr>");
		out.println("											<td><label><a href=http://www.tecnun.es/servicios/biblioteca><b>Library</b></a></label></td>");
		out.println("											");
		out.println("										</tr>");
		out.println("										<tr>");
		out.println("											<td><label><a href=http://www.tecnun.es/servicios/informatica/presentacion><b>Computer Science</b></a></label></td>");
		out.println("											");
		out.println("										</tr>");
		out.println("									");
		out.println("									</table>");
		out.println("								</div>");
		out.println("							</td>");
		out.println("						</tr>");
		out.println("					</table>");
		out.println("				");
		out.println("				</div>");
		clickers.printBottom(resp);

			out.flush();
			out.close();
		
	} 
}