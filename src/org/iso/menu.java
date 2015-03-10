package org.iso;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class menu extends HttpServlet {
	public void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession(true);
		session.setAttribute("user_id", 5);
		
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
		out.println(".my-box {background-color: white; font-size:18pt;font-weight: normal;border: 3px solid #990000;border-radius:10px; padding: 40px;margin-top:20%;margin-left: auto;margin-right: auto;width: 30%;height: 100%;}");
		out.println("</style>");
		out.println("<title>Clickers</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<header><div class='my-header'><h1>Clickers</h1><h2>TECNUN's online test taking platform</h2></div></header>");



		out.println("<p align='center'>");
		out.println("<div class='my-box'>");
		out.println("<h3> What do you want to do, " + session.getAttribute("user") + "?</h3><br>");
		out.println("<TABLE BORDER='0'>");
		out.println("<TR>");
		out.println("<TD align=left'><a href='testMenu'>Take Test</a></TD>");
		out.println("</TR>");
		out.println("<TR>");
		out.println("<TD align='left'><a href='requestNumbers'>Edit Test</a></TD>");
		out.println("</TR>");
		out.println("<TR>");
		out.println("<TD align='left'><a href='Calificaciones'>See Grades</a></TD>");
		out.println("</TR>");
		out.println("</div>");
		out.println("<p align=center'>");

		out.println("</p>");

		out.println("<footer>&copy UN 2015</footer></body>");

		out.println("</html>");

	}
}