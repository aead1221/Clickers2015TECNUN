package org.iso;
import java.sql.*;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class SurveyTemplate extends HttpServlet {

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
		
	
		
		String strNumber = req.getParameter("num");
		String testName = req.getParameter("nom");
		String subject = req.getParameter("subjectid");
		String scaleops = req.getParameter("scale");
		
		
		int num = Integer.parseInt(strNumber);
		int idsubject = Integer.parseInt(subject);
		int op = Integer.parseInt(scaleops);
		
		//out.println("<!DOCTYPE html>");

		
		//out.println("<html lang='en'>");
		//out.println("<head>");
		//out.println("<style type='text/css'>");
		//out.println("* {padding: 0px; margin: 0px}"); 
		//out.println("body {font-family:helvetica;font-size: 14px;font-weight:normal;background: url('bg.png') no-repeat top left;background-attachment: fixed}"); 
		//out.println("header {background-color:#990000;position: fixed;top: 0px;width: 100%}"); 
		//out.println("header h1 {color:white;font-size: 64pt;font-weight: lighter}"); 
		//out.println("header h2 {color:#C0C0C0;font-weight: normal;padding-left:30px}"); 
		//out.println("footer {position: fixed;top: 97%;right: 1%;}");
		//out.println(".my-header {padding: 30px}");
		//out.println(".my-box {background-color: white; font-size:18pt;font-weight: normal;border: 3px solid #990000;border-radius:10px; padding: 40px;margin-top:20%;margin-left: auto;margin-right: auto;width: 30%;height: 100%;}");
		//out.println("</style>");
		//out.println("<title>Test Edition</title>");
		//out.println("</head>");
        //out.println("<body>");
        //out.println("<header><div class='my-header'><h1>Clickers</h1><h2>TECNUN's online test taking platform</h2></div></header>");
		clickers.printTop(resp, session.getAttribute("name").toString(), session.getAttribute("user").toString());
		out.println("<div id='clk-box'>");
		out.println("<h3 align='center'>Survey Content</h3><br>");
		out.println("<FORM Action='SaveSurveyData'>");
		out.println("<FORM Action='SaveSurveyData'>");
		out.println("<table>");
		for(int i=0; i<num; i++){
			int v = i + 1;
			out.println("<tr><td colspan='2'><h4> Question " + v +"<h4></td></tr>");
			out.println("<tr><td colspan='2'><INPUT type='text' name='question' style='width:200px;'></td></tr>");
			if(op==3) {
				
				out.println("<tr><td><h6>Option " + 1 + ":</h6></td><td><INPUT type='text' name='option" + i + 1 + "' value='Disagree'></td>");
				out.println("<tr><td><h6>Option " + 2 + ":</h6></td><td><INPUT type='text' name='option" + i + 2 + "' value='Neutral'></td>");
				out.println("<tr><td><h6>Option " + 3 + ":</h6></td><td><INPUT type='text' name='option" + i + 3 + "' value='Agree'></td>");
			}
			else {
				
				out.println("<tr><td><h6>Option " + 1 + ":</h6></td><td><INPUT type='text' name='option" + i + 1 + "' value='Strongly disagree'></td>");
				out.println("<tr><td><h6>Option " + 2 + ":</h6></td><td><INPUT type='text' name='option" + i + 2 + "' value='Disagree'></td>");
				out.println("<tr><td><h6>Option " + 3 + ":</h6></td><td><INPUT type='text' name='option" + i + 3 + "' value='Neutral'></td>");
				out.println("<tr><td><h6>Option " + 4 + ":</h6></td><td><INPUT type='text' name='option" + i + 4 + "' value='Agree'></td>");
				out.println("<tr><td><h6>Option " + 5 + ":</h6></td><td><INPUT type='text' name='option" + i + 5 + "' value='Strongly agree'></td>");
				
			}
		}
			out.println("<tr><td colspan='2'>&nbsp</td></tr>"); 
		
		out.println("</table>");
		out.println("<INPUT type='submit' value='Save changes'>");
		out.println("<INPUT name='num' type='hidden' value= "+ num +">");
		out.println("<INPUT name='op' type='hidden' value= "+ op +">");
		out.println("<INPUT name='nom' type='hidden' value= "+ testName +">");
		out.println("<INPUT name='subjectcod' type='hidden' value= "+ idsubject +">");
		out.println("</FORM>");
		out.println("<BR><h6><a href='menuTeacher'>Back to menu</a></h6>");
		out.println("</div>");
		clickers.printBottom(resp);
		//<footer>&copy UN 2015</footer></body></html>");
		
		
	}
}