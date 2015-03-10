package org.iso;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class RegistroUsuario extends HttpServlet {
    public static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
     
        HttpSession session = req.getSession(true);
        
        String name = req.getParameter("usuario");
        session.setAttribute("user", name);
    
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        
		
		toClient.println("<!DOCTYPE html>");
		toClient.println("<html lang='en'>");
		toClient.println("<head>");
		toClient.println("<style type='text/css'>");
		toClient.println("* {padding: 0px; margin: 0px}"); 
		toClient.println("body {font-family:helvetica;font-size: 14px;font-weight:normal;background: url('bg.png') no-repeat top left;background-attachment: fixed}"); 
		toClient.println("header {background-color:#990000;position: fixed;top: 0px;width: 100%}"); 
		toClient.println("header h1 {color:white;font-size: 64pt;font-weight: lighter}"); 
		toClient.println("footer {position: fixed;top: 97%;right: 1%;}");
		toClient.println("header h2 {color:#C0C0C0;font-weight: normal;padding-left:30px}"); 
		toClient.println(".my-header {padding: 30px}");
		toClient.println(".my-box {background-color: white; font-size:24pt;font-weight: normal;border: 3px solid #990000;border-radius:10px; padding: 20px;margin-top:20%;margin-left: auto;margin-right: auto;width: 50%;height: 100%;}");
		toClient.println("</style>");
		toClient.println("<title>User Registered</title>");
		toClient.println("</head>");
		toClient.println("<body>");
		toClient.println("<header><div class='my-header'><h1>Clickers</h1><h2>TECNUN's online test taking platform</h2></div></header>");
		toClient.println("<div class='my-box'>");
        toClient.println("<H2>" + session.getAttribute("user") + " has registered with the following session:</H2><BR>");
		toClient.println("<H4>" + session.getId() + "</H4>");
        toClient.println("<BR><h6><a href='menu'>Go to menu</a></h6>");
		toClient.println("</div>");
        toClient.println("<footer>&copy UN 2015</footer></body>");
        toClient.println("</html>");
        toClient.close();
    }
}