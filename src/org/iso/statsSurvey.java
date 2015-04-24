package org.iso;
import java.sql.*;
import java.io.*; 
import java.util.*;

import javax.servlet.*; 
import javax.servlet.http.*;
 
public class statsSurvey extends HttpServlet{ 
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
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException { 
		
		HttpSession session = request.getSession(true);
		
		PrintWriter out = response.getWriter();
		
		String user = session.getAttribute("name").toString();
		clickers.printTop(response, user);
		
		String test_name = request.getParameter("test_name");
		String test_id = request.getParameter("test_id");
		int test_nq = 0;
		
		String sql1 = "Select Tests.Id_Tests, Tests.Description, Tests.NumberOfQuestions from Tests where Tests.Id_Tests=" + test_id + "";
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql1);
			if (rs.next()) {
				test_nq = Integer.parseInt(rs.getString("NumberOfQuestions").toString());
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Resulset: " + sql1 + " Exception: " + e);
		}
		
		String htmlStr = "";
		htmlStr += "<div id='clk-title'>";
		htmlStr += "				<h3 align='center'>Survey Results Bar Chart</h3>";
		htmlStr += "			</div>";
		htmlStr += "<div id='clk-box'><div id='clk-stats-survey'></div>";
		htmlStr += "<a href='CalificacionesPrueba?TestId=" + test_id + "&TestName=" + test_name + "'>";
		htmlStr += "	<button class='clk-button'>Back to marks</button>";
		htmlStr += "</a></div>";
		htmlStr += "<div>";
		htmlStr += "	<input type='hidden' name='clk-bar-data' value='" + test_nq + "'>";
		
		for (int i=1; i<=test_nq; i++) {
			String sql3 = "SELECT IdSurvey, IdQuestion, AnswerS FROM StudentAnswerS WHERE IdSurvey=" + test_id + " AND IdQuestion=" + i + "";
			
			int sum = 0;
			int num = 0;
			float resul = 0;
			
			try {
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(sql3);
				while (rs.next()) {
					sum += Integer.parseInt(rs.getString("AnswerS").toString());
					num++;
				}
				
				resul = ((float)sum)/((float)num);
				
				//t.addCell("Question " + i + ":");
				//t.addCell("" + resul);
				
				htmlStr += "	<input type='hidden' name='clk-bar-data' value=" + resul + ">";
				
			} catch(SQLException e) {
				e.printStackTrace();
				System.out.println("Resulset: " + sql3 + " Exception: " + e);
			}
		}
		
		htmlStr += "</div>";
		
		out.println(htmlStr);
		
		clickers.printBottom(response);
	}
}