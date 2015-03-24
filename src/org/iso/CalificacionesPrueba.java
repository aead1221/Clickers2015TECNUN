import java.sql.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CalificacionesPrueba extends HttpServlet {

	Connection conn = null;
	String nombre=null;
	String apellido=null;
	String apellido1=null;
	
	String sql1 =null;
	
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
			out.println("<title>Clickers - Menu</title>");
			out.println("<link rel='stylesheet' type='text/css' href='css/clickers.css'>"); 
			out.println("</head>"); 
			out.println("<body>"); 
			out.println("<header>"); 
			out.println("<div id='clk-header'>"); 
			out.println("<h1>Clickers</h1>");
			out.println("<h2>TECNUN's online test taking platform</h2>");
			out.println("</div>");
			out.println("<div id='clk-user-bar'>");
			out.println("<div id='clk-user-buttons'>");
			out.println("<a href='UserPersonalInfo.html'>");
			out.println("<span id='clk-user-view'>");
			out.println("<img id='clk-user-icon' src='user-icon.png'>");
			out.println("User");
			out.println("</span>");
			out.println("</a>");
			out.println("<a href='Login.html'>");
			out.println("<span id='clk-user-logout'>");
			out.println("Logout");
			out.println("</span>");
			out.println("</a>");
			out.println("</div>");
			out.println("</div>");
			out.println("<a href='InicioSesion.html'>");
			out.println("<div id='clk-menu-bar'>");
			out.println("B A C K   &nbspT O&nbsp   M E N U");
			out.println("</div>");
			out.println("</a>");
			out.println("</header>");
			out.println("<div id='clk-body'>");
			out.println("<div id='clk-title'>");
			out.println("<label><h3> NOMBRE DEL TEST</h3></label>");
			out.println("</div>");
			out.println("<div id='clk-box'>");
			out.println("<table style='padding-left:11%; padding-top:5%'>");
			out.println("<tr class='clk-subtitle-st'>");
			out.println("<td><label><p align='left'><h3><U> USER </U></h3></p></label></td>");
			out.println("<td> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>");
			out.println("<td><label><p align='center'><h3><U> MARKS </U></h3></p></label></td>");
			out.println("<td> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>");
			out.println("<td>");
			out.println("<select  class='clk-cb' name='Order By'>");
			out.println("<option value='apellido'> Last Name </option>");
			out.println("<option value='mark'> Marks </option>");
			out.println("</select>");
			out.println("<br>");
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("<form method='get' action='statistic.html'>");
			out.println("<p align='right'><input class='clk-button' type='submit' value='Statistics'></p>");
			
			out.println("</td>");
			
			out.println("</tr>");
			out.println("</div>");
			out.println("<tr class='clk-content-st'>");
			

			
			String sql1="SELECT  FirstName, LastName1, LastName2, Mark from Students, TestStudent where Id_Student=Id and Id_Test=1";
			
			
					System.out.println(sql1);
					try {
						Statement stmt=conn.createStatement();
						ResultSet rs = stmt.executeQuery(sql1); //ejecutar sql1
						while(rs.next()) {
							nombre=rs.getString("FirstName");
							apellido=rs.getString("LastName1");
							apellido1=rs.getString("LastName2");
							nota=rs.getString("Mark");
							
							out.println("<td><label>" + nombre +"  "+ apellido+"  "+ apellido1+"</label></td>");
							out.println("<td> &nbsp;&nbsp;&nbsp; </td>");
							out.println("<td><label><p align='center'>" + nota + "</p></label></td>");
							out.println("<td> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>");
							
							out.println("</tr>");
						}
						
						
						

					} catch(SQLException e) {
						e.printStackTrace();
						System.out.println("Resulset: " + sql1 + " Exception: " + e);
						out.println("<tr>");
						out.println("<td><input class='clk-button' type='submit' value='Statistics'></td>");
						out.println("</tr>");
					}
			out.println("/form");
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
