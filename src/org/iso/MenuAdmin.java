package org.iso;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class MenuAdmin extends HttpServlet {
	public void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession(true);
		
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
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/clickers.css\"> ");
		out.println("</head>");
		out.println("<body>");
		out.println("<header>");
		out.println("<div id=\"clk-header\">");
		out.println(" <h1>Clickers</h1>");
		out.println("<h2>TECNUN's online test taking platform</h2>");
	    out.println(" </div>");
		out.println("<div id=\"clk-user-bar\">");
		out.println("<div id=\"clk-user-buttons\">");
	    out.println(" <a href=\"UserPersonalInfo.html\">");
		out.println("<span id=\"clk-user-view\">");
	    out.println(" <img id=\"clk-user-icon\" src=\"images/user-icon.png\">");
		out.println(session.getAttribute("name").toString());
		out.println("</span>");
		out.println("</a>");
		out.println("<a href=\"Login.html\">");
		out.println("<span id=\"clk-user-logout\">");
		out.println("Logout");
		out.println("</span>");
		out.println("</a>");
		out.println("</div>");
		out.println("</div>");
		out.println("<a href=\"Login.html\">");
		out.println("<div id=\"clk-menu-bar\">");
		out.println("B A C K   &nbspT O&nbsp   M E N U");
		out.println("</div>");
		out.println("</a>");
		out.println("</header>");
		out.println("<div id=\"clk-body\">");

		out.println("<div>");
		out.println("<p align='center'>");
		out.println("<IMG id=\"menu-pic\" SRC=\"images/borde.bmp\">");
		out.println("</p>");
		
		out.println("</div>");
		
		out.println("<table id=\"menu-content\">");
		out.println("<tr>");
		out.println("<td>");
		out.println("<div class=\"menu-box\">");
		out.println("<div class=\"menu-header-st\">");
		out.println("<label> What do you want to do?</label>");
		out.println("</div>");
	
		out.println("<table style=\"padding-left:15%; padding-top:10%\">");
			
		out.println("<tr>");
		out.println("<td><label><h3><a href='Administrator'> Manage Users</a></h3></label></td>");
		
		out.println("</tr>");
		out.println("</table>");
	
		out.println("</div>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</div>");
	
	
		out.println("<footer>");
		out.println("<div class=\"clk-footer\">");
		out.println("<h3>&copy Tecnun. Escuela de Ingenieros. Universidad de Navarra. Paseo de Manuel Lardizabal, 13. 20018 Donostia-San Sebastian. Gipuzkoa (Spain). ");
		out.println("<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tel. +34 943 21 98 77. Fax: +34 943 31 14 42. GPS: Latitud: 43.304654 Longitud: -2.009873.");
		out.println("</h3>");
		out.println("<div>");
		out.println("<img id=\"logouni\" src=\"logo.png\"> ");
		out.println("</div>");
		out.println("</div>");
		out.println("</footer>");
		out.println("</body>");
		out.println("</html>");

	}
}