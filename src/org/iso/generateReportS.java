package org.iso;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.io.*; 
import java.util.*;
import java.util.Date;

import javax.servlet.*; 
import javax.servlet.http.*;

import com.itextpdf.text.*;
import com.itextpdf.text.Font.*;
import com.itextpdf.text.pdf.*;

public class generateReportS extends HttpServlet{ 
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
				
		Document doc = new Document();
		doc.setPageSize(PageSize.A4);
		
		String test_id = request.getParameter("test_id");
		String test_name = request.getParameter("test_name");
		int test_nq = 0;
		
		Font [] fonts = {
			new Font(),
			new Font(FontFamily.HELVETICA, 18, Font.NORMAL, new BaseColor(100, 100, 100)),
			new Font(FontFamily.HELVETICA, 32, Font.BOLD, new BaseColor(175, 0, 0)),
			new Font(FontFamily.HELVETICA, 12, Font.NORMAL, new BaseColor(0, 0, 0)),
			new Font(FontFamily.HELVETICA, 12, Font.BOLD, new BaseColor(0, 0, 0))
		};
		
		try {
			PdfWriter.getInstance(doc, new FileOutputStream("C:/Users/Antonio/Documents/eclipse_ws/Clickers/surveyReport.pdf"));
			
			doc.open();
			
			doc.add(new Paragraph("Clickers", fonts[2]));
			doc.add(new Paragraph("Test report", fonts[1]));
			
			doc.add(Chunk.NEWLINE);
			
			doc.add(new Paragraph("Report Generation date: " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + "", fonts[3]));
			
			String sql1 = "Select Surveys.Id_Survey, Surveys.Survey, Surveys.NumberOfQuestions from Surveys where Surveys.Id_Survey=" + test_id + "";
			try {
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(sql1);
				if (rs.next()) {
					test_name = rs.getString("Survey");
					test_nq = Integer.parseInt(rs.getString("NumberOfQuestions").toString());
				}
			} catch(SQLException e) {
				e.printStackTrace();
				System.out.println("Resulset: " + sql1 + " Exception: " + e);
			}
			
			doc.add(new Paragraph("Test name: " + test_name + "", fonts[3]));
			
			doc.add(Chunk.NEWLINE);
			
			PdfPTable t = new PdfPTable(2);
			t.addCell(new Paragraph("Student name", fonts[4]));
			t.addCell(new Paragraph("Mark", fonts[4]));
			
			for (int i=1; i<=test_nq; i++) {
				String sql2 = "SELECT IdSurvey, IdQuestion, AnswerS FROM StudentAnswerS WHERE IdSurvey=" + test_id + " AND IdQuestion=" + i + "";
				
				int sum = 0;
				int num = 0;
				float resul = 0;
				
				try {
					Statement statement = connection.createStatement();
					ResultSet rs = statement.executeQuery(sql2);
					while (rs.next()) {
						sum += Integer.parseInt(rs.getString("AnswerS").toString());
						num++;
					}
					
					resul = ((float)sum)/((float)num);
					
					t.addCell("Question " + i + ":");
					t.addCell("" + resul);
					
				} catch(SQLException e) {
					e.printStackTrace();
					System.out.println("Resulset: " + sql2 + " Exception: " + e);
				}
			}
			
			doc.add(t);
			
			Image im = Image.getInstance("http://upload.wikimedia.org/wikipedia/commons/thumb/3/3b/Tecnun_logo.jpg/640px-Tecnun_logo.jpg");
			im.scaleAbsolute(100, 50);
			im.setAbsolutePosition(450, 40);
			doc.add(im);
			
			doc.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/testReport");
		rd.forward(request, response);
		
	}
}