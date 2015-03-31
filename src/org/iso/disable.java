package org.iso;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class disable extends HttpServlet {

  private String idUser = null;
 
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

    System.out.println("Iniciando TareasEnBaseDatos (version BD)...");
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

  public void doGet (HttpServletRequest req, HttpServletResponse resp) 
                                         throws ServletException, IOException {

    boolean hayError = false;

    System.out.println("TareasEnBaseDatos, v1.5");
	//if(req.getParameter("ID")!=null) ID = req.getParameter("ID");
    //else hayError=true;
	
	if(req.getParameter("userid")!=null) idUser = req.getParameter("userid");
    else hayError=true;
	
   
    if(!hayError) {
      if (actualizarBaseDeDatos() == 0) devolverPaginaHTML(resp);
      else resp.sendError(500, "Se ha producido un error al actualizar la base de datos");
    } else resp.sendError(500, "Se ha producido un error en la adquisicion de parametros");
  } 

  public int actualizarBaseDeDatos() {
    
    Statement stmt=null;
    int numeroFilasActualizadas=0;
    int error = 0;
    String strSql = " UPDATE Users SET Active = 'no' WHERE IdUser =  '" + idUser + "' ";
    System.out.println(strSql);
	
    try {
      stmt = conn.createStatement();
      numeroFilasActualizadas = stmt.executeUpdate(strSql);
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
      return error;
    } 
  } 

  public void devolverPaginaHTML(HttpServletResponse response) throws ServletException, IOException {	
		String pagina="MenuAdmin";
		response.sendRedirect(pagina);
	}
}
   