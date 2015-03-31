package org.iso;
import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ViewResultsTeacher extends HttpServlet {

	Connection conn = null;
	String TestName=null;
	String TestId=null;
	String sql1 =null;
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

		PrintWriter out = null;
		try {
			out=resp.getWriter();
		} catch (IOException io) {
			System.out.println("alumnosExcepcion");    
		}

		HttpSession session = req.getSession(true);
		
		
		clickers.printTop(resp, session.getAttribute("name").toString());
		
		
		
		
			out.println("			<div id=\"clk-title\">");
			out.println("				<label><h3><p align='center'>View Results</p><h3></label>");
			out.println("			</div>");
			out.println("		<div id=\"clk-body\">");
			out.println("			<div id=\"menu-content\">");
			out.println("				<div class=\"menu-box\">");
			out.println("					<div class=\"menu-header-st\">");
			out.println("						<label>Tests</label>");
			out.println("					</div>");
			out.println("					<table style=\"padding-left:15%; padding-top:10%\">");
			
			String sql1="SELECT  Id_Tests, Description from TestsBF";
			//Poner Order by fecha(por implementar, para que no se acumulen los test antiguos arriba del todo)
			
			
					System.out.println(sql1);
					try {
						Statement stmt=conn.createStatement();
						ResultSet rs = stmt.executeQuery(sql1); //ejecutar sql1
						while(rs.next()) {
							TestName=rs.getString("Description");
							TestId=rs.getString("Id_Tests");
							
							
							out.println("						<tr>");
							out.println("							<td><label><a href='CalificacionesPrueba?TestId="+TestId+"&TestName="+TestName+"'><b>"+TestName+"</b></a></label></td>");
							out.println("							");
							out.println("						</tr>");
						}
						
					
					} catch(SQLException e) {
						e.printStackTrace();
						System.out.println("Resulset: " + sql1 + " Exception: " + e);
						
					}
			out.println("					</table>");
			out.println("				</div>");
			out.println("				<div class=\"menu-box\">");
			out.println("					<div class=\"menu-header\">");
			out.println("						<label>Surveys</label>");
			out.println("					</div>");
			out.println("					<table style=\"padding-left:15%; padding-top:10%\">");
			
			String sql="SELECT  Id_Survey, Description from SurveysBF";
			//Poner Order by fecha(por implementar, para que no se acumulen los test antiguos arriba del todo)
			
			
					System.out.println(sql);
					try {
						Statement stmt=conn.createStatement();
						ResultSet rs = stmt.executeQuery(sql); //ejecutar sql
						while(rs.next()) {
							SurveyName=rs.getString("Description");
							SurveyId=rs.getString("Id_Survey");
							
							
							out.println("						<tr>");
							out.println("							<td><label><a href='CalificacionesSurvey?SurveyId="+SurveyId+"&SurveyName="+SurveyName+"'><b>"+SurveyName+"</b></a></label></td>");
							out.println("							");
							out.println("						</tr>");
						}
						
					
					} catch(SQLException e) {
						e.printStackTrace();
						System.out.println("Resulset: " + sql + " Exception: " + e);
						
					}
			out.println("					</table>");
			out.println("				</div>");
			out.println("			</div>");
			out.println("        </div>");
						
			
			clickers.printBottom(resp);

			out.flush();
			out.close();
		
	} 
}
