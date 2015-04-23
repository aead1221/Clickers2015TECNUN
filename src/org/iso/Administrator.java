package org.iso;
import java.sql.*;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class Administrator extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url="jdbc:odbc:Clickers";
            connection=DriverManager.getConnection(url); 
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        
    	HttpSession session = req.getSession(true);
    	
    	res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
		
        toClient.println("<!DOCTYPE HTML>");

	    toClient.println("<html lang=\"en\">");

		toClient.println("<head><title>Clickers - Administrator</title>");
		toClient.println("<LINK href=\"css/clickers.css\" type=\"text/css\" rel=\"stylesheet\"></head>");
	
		toClient.println("<body><header><div id=\"clk-header\">");
		
		toClient.println("<h1>Clickers</h1>");
		toClient.println("<h2>TECNUN's online test taking platform</h2></div>");
		toClient.println("<div id=\"clk-user-bar\">");
		toClient.println("<div id=\"clk-user-buttons\">");
		toClient.println("<a href=\"UserPersonalInfo.html\">");
		toClient.println("<span id=\"clk-user-view\">");
		toClient.println("<img id=\"clk-user-icon\" src=\"images/user-icon.png\"> "+session.getAttribute("name").toString() +"</span></a>");
		
	   
		toClient.println("<a href=\"Login.html\"><span id=\"clk-user-logout\">Logout</span></a></div></div>");

		toClient.println("<a href=\"logout\">");
		toClient.println("<div id=\"clk-menu-bar\">B A C K   &nbspT O&nbsp   M E N U </div></a></header>");

		toClient.println("<div id='clk-body'>");
		
		toClient.println("<div id='clk-title'><label><h3>Manage Users</h3></label></div>");
	
		toClient.println("<div class=\"clk-admin\" ><div  class='menu-header-admin' >");
			
		toClient.println("<table><tr>");
					
	    toClient.println("<td>USER</a></td>");
	    toClient.println("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>");
	    toClient.println("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;USER TYPE&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>");
	    toClient.println("<td>&nbsp;&nbsp; ADD USER &nbsp;&nbsp;</td>");
	    toClient.println("<td><a href=\"AddUser.html\"><button class='clk-button' >&nbsp;&nbsp; + &nbsp;&nbsp;</button ></a></td>");
    	toClient.println("</tr></table></div><div class=\"content-adm\" >	");
				
		
        toClient.println("<table>");
        
        String sql = "SELECT Users.IdUser, Users.FirstName, Users.LastName1, Users.LastName2, UserType.Description FROM UserType INNER JOIN Users ON UserType.[Id_Usertype] = Users.[UserType] ";
        System.out.println(sql);
        try {
            Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                toClient.println("<tr>");
                String name = result.getString("FirstName");
				String lname1 = result.getString("LastName1");
				String lname2 = result.getString("LastName2");	
				String ustype = result.getString("Description");	
				String iduser = result.getString("IdUser");	
                toClient.println("<td><a href=\"UserPersonalInf?userid=" + iduser + "\" >"+ name + " " + lname1 + " " + lname2 + "</a> </td>");
                toClient.println("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + ustype + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>");
                toClient.println("<td><a href=\"EditUser?userid=" + iduser + "\" ><button class='clk-button' >&nbsp;&nbsp; Modify &nbsp;&nbsp;</button ></a></td>");
				toClient.println("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>");
                toClient.println("<td><a href=\"disable?userid=" + iduser + "\"> <button class='clk-button' >&nbsp;&nbsp; Disable &nbsp;&nbsp;</button ></a></td>");
                toClient.println("</tr>");
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Resulset: " + sql + " Exception: " + e);
        }
        toClient.println("</table>");
		
		toClient.println("</div></div></div><footer>");
	
		toClient.println("<div class=\"clk-footer\">");
		toClient.println("<h3>&copy Tecnun. Escuela de Ingenieros. Universidad de Navarra. Paseo de Manuel Lardizabal, 13. 20018 Donostia-San Sebastian. Gipuzkoa (Spain). ");
		toClient.println("<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tel. +34 943 21 98 77. Fax: +34 943 31 14 42. GPS: Latitud: 43.304654 Longitud: -2.009873.");		
				
		toClient.println("</h3><div><img id=\"logouni\" src=\"logo.png\"></div></div></footer>");
        toClient.println("</body>");
        toClient.println("</html>");
        toClient.close();
    }
}