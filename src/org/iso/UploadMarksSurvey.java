package org.iso;
import java.sql.*;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class UploadMarksSurvey extends HttpServlet {

	Connection conn = null;
	String SurveyId=null;
	String SurveyName=null;
	String nombre = null;
	String apellido=null;
	String apellido1=null;
	private String MarkStr=null;
	private String MarkStrB=null;
	private int cMark = 0;
	int Id=0;
	String sql2=null;
	int var=0;
	
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
		
		System.out.println("dentro UPLOAD MARKS id:" + SurveyId);
		

		

		PrintWriter out = null;
		try {
			out=resp.getWriter();
		} catch (IOException io) {
			System.out.println("alumnosExcepcion");    
		}

		HttpSession session = req.getSession(true);
		
		
		clickers.printTop(resp, session.getAttribute("name").toString(), session.getAttribute("user").toString());
		
		
			boolean hayError = false;
			
			System.out.println("dentro funcion id:" + SurveyId);
			String sql2="SELECT  FirstName, LastName1, LastName2, Mark from Users,SurveyStudent where SurveyStudent.Id_Student= Users.IdUser and SurveyStudent.Id_Survey="+SurveyId+"";
			
			
					System.out.println(sql2);
					try {
						Statement stmt=conn.createStatement();
						ResultSet rs = stmt.executeQuery(sql2); //ejecutar sql2
						
						while(rs.next()) {
							nombre=rs.getString("FirstName");
							apellido=rs.getString("LastName1");
							apellido1=rs.getString("LastName2");
							MarkStr=rs.getString("Mark");
							
							
							
							System.out.println("nombre: " + nombre);
							System.out.println("id: " + Id);
							
							out.println("");
							out.println("<tr class='clk-content-st'>");
							out.println("<td><label>" + nombre +" "+ apellido+" "+ apellido1+"</label></td>");
							out.println("<td> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>");
							out.println("<td><label><p align='center'><input class='clk-button' type='text' name="+nombre+" value=''></p></label></td>");
							out.println("<td> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>");
							out.println("</tr>");
							
							
								System.out.println("nombre no es null");
								MarkStrB = req.getParameter(nombre);
							
							
							System.out.println("hayError: " + hayError);
							
							System.out.println(" string mark " + MarkStrB);

							cMark=Integer.parseInt(MarkStrB);
							System.out.println("num mark: "+cMark);
				
						if(!hayError) {
							var=actualizarBaseDeDatos();
						}
								
						}
												
								
							
						if(var==-1 || var==0) 
							
						devolverPaginaHTML(resp);
						else resp.sendError(500, "Se ha producido un error al actualizar la base de datos");
							
							
						
					
					} catch(SQLException e) {
						e.printStackTrace();
						System.out.println("Resulset: " + sql2 + " Exception: " + e);
						
					}
			
			
  } 
  
  
  public int actualizarBaseDeDatos() {
    
    Statement stmt=null;
    int numeroFilasActualizadas=0;
    int error = 0;
	
	System.out.println("dentro de actualizarBaseDatos: " +Id);
	
	
	
	String sql3="SELECT distinct  Id_Student,LastName2 from SurveyStudent,Users where SurveyStudent.Id_Student=Users.IdUser and LastName2='"+ apellido1+"'";
		
	//actualiza base de datos
   
    try {
      stmt = conn.createStatement();
	  ResultSet rs = stmt.executeQuery(sql3);
	  while(rs.next()) {
							
		  String temp;
		  temp=rs.getString("Id_Student");
								
	  System.out.println("dentro bucle: " +Id);
	
	  String strSql = "UPDATE SurveyStudent SET " //Tabla de la base de datos a la que apunta el DSN en la que queramos hacer una actualizacion 
	        + "Mark=" + cMark + " WHERE Id_Student='"+temp+"'"; 
	      System.out.println(strSql);
	      stmt=conn.createStatement();
	      numeroFilasActualizadas = stmt.executeUpdate(strSql);
		  
		  System.out.println("numeroFilasActualizadas"+ numeroFilasActualizadas);
		
	  }
	
	  
	  
      if(numeroFilasActualizadas!=1) error = -1;
    } catch (SQLException sql) {
      System.out.println("Se produjo un error creando Statement");
      System.out.println(sql.getMessage());
      sql.printStackTrace();
      error = -2;
    } catch (Exception ex) {
      System.out.println("Se produjo un error");
      System.out.println(ex.getMessage());
      ex.printStackTrace();
      error = -4;
    } finally {
      if(stmt!=null) {
        try { 
          stmt.close();
        } catch(SQLException e){
          System.out.println("Error cerrando Statement");
          System.out.println(e.getMessage());
          e.printStackTrace();
          error = -3;
        }
      }
	   System.out.println("error:" + error);
	  
      return error;
    } 
  } 

  //redirige a la Survey Actualizada
  public void devolverPaginaHTML(HttpServletResponse resp) throws ServletException, IOException {
    
    String pagina="ViewResultsTeacher?SurveyId="+SurveyId+"&Mark="+nombre+"";
	System.out.println("devolver html");
	resp.sendRedirect(pagina);
  } 
}
		