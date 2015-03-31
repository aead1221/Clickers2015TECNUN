package org.iso;
import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UserPersonalInf extends HttpServlet {
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
		
        res.setContentType("text/html");
		String userid = req.getParameter("userid");
		

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
		toClient.println("<img id=\"clk-user-icon\" src=\"user-icon.png\">User</span></a>");
			
		toClient.println("<a href=\"Login.html\"><span id=\"clk-user-logout\">Logout</span></a></div></div>");

		toClient.println("<a href=\"Login.html\">");
		toClient.println("<div id=\"clk-menu-bar\">B A C K   &nbspT O&nbsp   M E N U </div></a></header>");
		toClient.println("<div id='clk-body'>		");
		toClient.println("<div class=\"clk-admin\" >");
		toClient.println("<div  class='menu-header-admin' >");
		toClient.println("<table>");
		toClient.println("<tr>");
		toClient.println("<td>&nbsp;&nbsp;&nbsp;INFORMATION</a></td>");
		toClient.println("</tr>");
		toClient.println("</table>");
							
		toClient.println("	</div>");
			
			
		toClient.println("<div class='content-adm'>");
		toClient.println("<div id ='user'>");
		toClient.println("<IMG SRC='images/user.png' width='130' height='120'>");
		toClient.println("</div>");

        toClient.println("<table>");
		
        String sql ="SELECT * FROM Users WHERE IdUser = '"
               + userid + "' ";
        System.out.println(sql);
       
	   try {
		    Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
			while(result.next()) {
			
                toClient.println("<tr>");            
                String name = result.getString("FirstName") + "  " + result.getString("LastName1") + "  " + result.getString("LastName2")  ;
                toClient.println("<td><B> Name: </B>" + name + "<br><br></td>");
                toClient.println("</tr>");
				
				toClient.println("<tr>");  				
                String us = result.getString("IdUser");
                toClient.println("<td><B> Id User:</B> " + us + "<br><br></td>");
                toClient.println("</tr>");
				
				toClient.println("<tr>");  			
                String psw = result.getString("Password");
                toClient.println("<td><B> Password:</B> " + psw + "<br><br></td>");
                toClient.println("</tr>");
				
				toClient.println("<tr>");            
                String adr = result.getString("Address");
                toClient.println("<td><B> Address: </B> " + adr + "<br><br></td>");
                toClient.println("</tr>");	
				
				toClient.println("<tr>");            
                String pn = result.getString("PhoneNumber");
                toClient.println("<td><B> Phone Number : </B> " + pn + "<br><br></td>");
                toClient.println("</tr>");	
				
				toClient.println("<tr>");            
                String em = result.getString("Email");
                toClient.println("<td><B> Email : </B> " + em + "<br><br></td>");
                toClient.println("</tr>");	
				
				toClient.println("<tr>");            
                String br = result.getString("Birth");
                toClient.println("<td><B> Birth : </B> " + br + "<br><br></td>");
                toClient.println("</tr>");	
				
				
				}
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Resulset: " + sql + " Exception: " + e);
        }
		
		toClient.println("</table>");
		toClient.println("</div>");
		toClient.println("<div class=\"Button-position\">");
		toClient.println("<a href='Administrator'><button class='clk-button' >&nbsp;&nbsp; OK &nbsp;&nbsp;</button ></a>");
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
