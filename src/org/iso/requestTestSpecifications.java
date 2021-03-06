package org.iso;
import java.sql.*;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class requestTestSpecifications extends HttpServlet {
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
	public void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession(true);
		
		
		resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
		
//		out.println("<!DOCTYPE html>");
//		out.println("<html lang='en'>");
//        out.println("<head>");
//		out.println("<style type='text/css'>");
//		out.println("* {padding: 0px; margin: 0px}"); 
//		out.println("body {font-family:helvetica;font-size: 14px;font-weight:normal;background: url('bg.png') no-repeat top left;background-attachment: fixed}"); 
//		out.println("header {background-color:#990000;position: fixed;top: 0px;width: 100%}"); 
//		out.println("header h1 {color:white;font-size: 64pt;font-weight: lighter}"); 
//		out.println("header h2 {color:#C0C0C0;font-weight: normal;padding-left:30px}"); 
//		out.println("footer {position: fixed;top: 97%;right: 1%;}");
//		out.println(".my-header {padding: 30px}");
//		out.println(".my-box {background-color: white; font-size:18pt;font-weight: normal;border: 3px solid #990000;border-radius:10px; padding: 40px;margin-top:20%;margin-left: auto;margin-right: auto;width: 30%;height: 100%;}");
//		out.println("</style>");
//		out.println("<title>Test Specifications</title>");
//		out.println("</head>");
//		out.println("<body>");
//		out.println("<header><div class='my-header'><h1>Clickers</h1><h2>TECNUN's online test taking platform</h2></div></header>");
        clickers.printTop(resp, session.getAttribute("name").toString(), session.getAttribute("user").toString());
        out.println("<div id='clk-title'><h3 align='center'>Test Specifications</h3></div>");
        out.println("<div id='clk-box'>");
		out.println("<FORM Action='TestTemplate'>");
		out.println("<table><tr>");
		out.println("<h6 align='left'>Name for the test:</h6>");
		out.println("</tr><tr>");
		out.println("<input type='text' name='nom'><br><br>");
		out.println("</tr><tr>");
		out.println("<h6 align='left'>Number of questions:<h6>");
		out.println("</tr><tr>");
		out.println("<input type='number' class='clk-num' name='num'><br><br>");
		out.println("</tr><tr>");
		out.println("<h6 align='left'>Number of options for each question:</h6>");
		out.println("</tr><tr>");
		out.println("<input type='number' class='clk-num' name='op'><br><br>");
		out.println("</tr><tr>");
		out.println("<h6 align='left'>Subject Id:</h6>");
		out.println("</tr><tr>");
		out.println("<input type='number' class='clk-num' name='subjectid'><br><br>");
		out.println("</tr><tr>");
		out.println("<INPUT type='submit' value='Continue'>");
		out.println("</tr></table></form>");
		out.println("</FORM>");
		out.println("<BR><h6><a href='menuTeacher'>Back to menu</a></h6>");
		out.println("</div>");
		clickers.printBottom(resp);
		//<footer>&copy UN 2015</footer></body></html>
	} 
}