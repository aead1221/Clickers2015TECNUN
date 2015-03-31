package org.iso;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class newUser extends HttpServlet {

  private String idUser = null;
  private String typeuser = null;
  private String fname = null;
  private String lname1 = null;
  private String lname2 = null;
  private String active = null;
  private String birth = null;
  private String addr = null;
  private String phonumb = null;
  private String email = null;
  private String psw = null;
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
	
	if(req.getParameter("IdUser")!=null) idUser = req.getParameter("IdUser");
    else hayError=true;
	
    if(req.getParameter("UserType")!=null) typeuser = req.getParameter("UserType");
    else hayError=true;
            
    if(req.getParameter("FirstName")!=null) fname = req.getParameter("FirstName");
    else hayError = true;
	
	if(req.getParameter("LastName1")!=null) lname1 = req.getParameter("LastName1");
    else hayError=true;

    if(req.getParameter("LastName2")!=null) lname2 = req.getParameter("LastName2");
    else hayError = true;
	
	if(req.getParameter("active")!=null) active = req.getParameter("active");
    else hayError = true;

	if(req.getParameter("Birth")!=null) birth = req.getParameter("Birth");
    else hayError=true;
            
    if(req.getParameter("Address")!=null) addr = req.getParameter("Address");
    else hayError = true;
	
	if(req.getParameter("PhoneNumber")!=null) phonumb = req.getParameter("PhoneNumber");
    else hayError=true;

    if(req.getParameter("Email")!=null) email = req.getParameter("Email");
    else hayError = true;
	
	if(req.getParameter("Password")!=null) psw = req.getParameter("Password");
    else hayError = true;
    if(!hayError) {
      if (actualizarBaseDeDatos() == 0) devolverPaginaHTML(resp);
      else resp.sendError(500, "Se ha producido un error al actualizar la base de datos");
    } else resp.sendError(500, "Se ha producido un error en la adquisicion de parametros");
  } 

  public int actualizarBaseDeDatos() {
    
    Statement stmt=null;
    int numeroFilasActualizadas=0;
    int error = 0;
    String strSql = "INSERT INTO Users ( IdUser, UserType, FirstName, LastName1, LastName2, Active, Birth, Address, PhoneNumber, Email, Password ) VALUES ( '" 
      + idUser + "', " + typeuser + ",'" + fname + "','" + lname1 + "','" + lname2+"', '" + active + "', '" + birth + "','" + addr + "'," + phonumb + ",'" + email + "','" + psw + "' )";
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
   