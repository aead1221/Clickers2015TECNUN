package org.iso;
import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class login extends HttpServlet {
    Connection connection;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url="jdbc:odbc:Clickers";
            connection=DriverManager.getConnection(url); 
        } catch(Exception e) {
            System.out.println("Problem creating connection");
            e.printStackTrace();
        }
    }

    public void destroy () {
        super.destroy();
        System.out.print("Closing connection ...");
        try {
            connection.close();
            System.out.println("Connection closed");
        } catch(SQLException ex){
            System.out.println("Problem closing connection");
            System.out.println(ex.getMessage());
        }
    }

    public void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("usuario");
        String password = req.getParameter("password");

        if(username==null) {
            System.out.println("Problem reading username from request");
            return;
        }
        if(password==null) {
          System.out.println("Problem reading password from request");
          return;
        }
        Statement stmt = null;
        try {
            stmt=connection.createStatement();
            ResultSet rs = null;
            rs = stmt.executeQuery("SELECT IdUser, UserType, Password, FirstName , LastName1  FROM Users WHERE IdUser = '"
               + username + "' AND Password = '" + password + "'");

            if(rs.next()==false){
                Error_login(req, resp);
                return;
            } else {
			    HttpSession session = req.getSession(true);
                String name = rs.getString("FirstName") + " " + rs.getString("LastName1");
                session.setAttribute("name", name);
                session.setAttribute("user", username);
				String typeuser = rs.getString("UserType");
				int typeus = Integer.parseInt(typeuser);
				if (typeus == 1){
					resp.sendRedirect("MenuAdmin");
					}
					else if (typeus == 2 ) {
					resp.sendRedirect("menuTeacher");
					}
					else {
					resp.sendRedirect("menuStudent");
					}
                return;
            }
        } catch (SQLException sql) {
            System.out.println("Error creating Statement");
            System.out.println(sql.getMessage());
            return;
        } finally {      
            if(stmt!=null) {
                try {
                    stmt.close();
                } catch(SQLException e) {
                    System.out.println("Error closing Statement");
                    System.out.println(e.getMessage());
                    return;
                }
            }
        } 
    } 

    public void Error_login(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");
        PrintWriter out=null;
        try {
            out=resp.getWriter();
        } catch (IOException io) {
            System.out.println("Error opening PrintWriter");
        }
		out.println("<!DOCTYPE html>");
		out.println("<html lang='en'>");
		out.println("<head>");
		out.println("<style type='text/css'>");
		out.println("* {padding: 0px; margin: 0px}"); 
		out.println("body {font-family:helvetica;font-size: 14px;font-weight:normal;background: url('bg.png') no-repeat top left;background-attachment: fixed}"); 
		out.println("header {background-color:#990000;position: fixed;top: 0px;width: 100%}"); 
		out.println("header h1 {color:white;font-size: 64pt;font-weight: lighter}"); 
		out.println("footer {position: fixed;top: 97%;right: 1%;}");
		out.println("header h2 {color:#C0C0C0;font-weight: normal;padding-left:30px}"); 
		out.println(".my-header {padding: 30px}");
		out.println(".my-box {background-color: white; font-size:24pt;font-weight: normal;border: 3px solid #990000;border-radius:10px; padding: 20px;margin-top:20%;margin-left: auto;margin-right: auto;width: 50%;height: 100%;}");
		out.println("</style>");
        out.println("<title>Error in login</title>");
        out.println("</head>");
        out.println("<body>");
		out.println("<header><div class='my-header'><h1>Clickers</h1><h2>TECNUN's online test taking platform</h2></div></header>");
		out.println("<div class='my-box'>");
        out.println("<BR>");
        out.println("<H2 align=\"center\">Username or password incorrect</H2>");
		out.println("<BR>");
		out.println("<a href='Login.html'>Try Again ?</a>");
		out.println("</div>");
        out.println("<footer>&copy UN 2015</footer></body>");
        out.println("</html>");
        out.flush();
        out.close();
		
    }

}
