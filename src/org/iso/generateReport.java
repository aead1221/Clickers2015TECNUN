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
 
public class generateReport extends HttpServlet{ 
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
		
		Font [] fonts = {
			new Font(),
			new Font(FontFamily.HELVETICA, 18, Font.NORMAL, new BaseColor(100, 100, 100)),
			new Font(FontFamily.HELVETICA, 32, Font.BOLD, new BaseColor(175, 0, 0)),
			new Font(FontFamily.HELVETICA, 12, Font.NORMAL, new BaseColor(0, 0, 0))
		};
		
		try {
			PdfWriter.getInstance(doc, new FileOutputStream("testPDF.pdf"));
			
			doc.open();
			
			doc.add(new Paragraph("Clickers", fonts[2]));
			doc.add(new Paragraph("Test report", fonts[1]));
			
			doc.add(Chunk.NEWLINE);
			
			doc.add(new Paragraph("Report Generation date: " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + "", fonts[3]));
			doc.add(new Paragraph("Test name: ", fonts[3]));
			doc.add(new Paragraph("Teacher: ", fonts[3]));
			
			doc.add(Chunk.NEWLINE);
			
			PdfPTable t = new PdfPTable(2);
			t.addCell("Antonio Amador Duran");
			t.addCell("10");
			doc.add(t);
			
			Image im = Image.getInstance("content/logo.jpg");
			im.scaleAbsolute(100, 50);
			im.setAbsolutePosition(450, 40);
			doc.add(im);
			
			doc.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			

	}
}