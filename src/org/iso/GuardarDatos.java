package org.iso;
import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;



public class GuardarDatos extends HttpServlet {
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
		
		resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
		File test = new File(System.getProperty("catalina.base") + "/webapps/Clickers/WEB-INF/classes/test.txt");
		
		String strNumero = req.getParameter("num");
		String strOpciones = req.getParameter("op");
		String nom = req.getParameter("nom");
		
		
		
		int num = Integer.parseInt(strNumero);
		int op = Integer.parseInt(strOpciones);
		
		String[] Preguntas = req.getParameterValues("question");
		FileWriter fw = new FileWriter(test);
		new PrintWriter(test).close();								//clears previous test...
		PrintWriter pw = new PrintWriter(fw);
		String h = (num + "\t" + op + "\t" + nom);
		pw.println(h);
		String options = "";
		for (int i=0; i<num; i++) {
			pw.println(Preguntas[i]);
			for (int j=0; j<op; j++){
				options = req.getParameter("option"+ i + "" + j + "");
				pw.println(options);
			}			
		}
		fw.close();
		pw.close();
		  
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/menu");
		rd.forward(req , resp);
		String sqlk = "Select Id_Tests FROM Tests";
        System.out.println(sqlk);
		int azul = 0;
        try {
            Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sqlk);
            while(result.next()) {
				azul =  azul + 1;
               
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Resulset: " + sqlk + " Exception: " + e);
        }
		int idtest = azul + 1;
		
		String sql = "INSERT INTO Tests (Id_Tests, Name, NumberOfQuestions, NumberOfOptions) VALUES (";	   
		sql +=  "'" + idtest + "'";
        sql +=  ", '" + nom + "'";
        sql +=  ", '" + num + "'";
        sql +=  ", '" + op + "')";
        System.out.println("Insert sql: " + sql);
		
		
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Resulset: " + sql + " Exception: " + e);
        }
		
		for (int i=0; i<Preguntas.length; i++) {
			String sqlf = "INSERT INTO Questions (Id_Question, Question, Id_Test) VALUES (";
			int j = i + 1;
			sqlf +=  "'" + j + "'";
			sqlf +=  ", '" + Preguntas[i] + "'";
			sqlf +=  ", '" + idtest + "')";

			System.out.println("Insert sql: " + sqlf);
			try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlf);
		    }catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Resulset: " + sqlf + " Exception: " + e);
             }
			}
		String optiones = "";
		
		
		;
		for (int k=0; k<Preguntas.length; k++) {
			int p = k + 1;
			for (int l=0; l<op; l++) {
			String sqlfu = "INSERT INTO Options (IdOption, Option, IdTest,IdQuestion) VALUES (";
			int g = l + 1;
			optiones = "";
			optiones = req.getParameter("option" + p + "" + g + "");
			
			sqlfu +=  "'" + g + "'";
			sqlfu +=  ", '" + optiones + "'";
			sqlfu +=  ", '" + idtest + "'";
			sqlfu +=  ", '" + p + "')";
			
			System.out.println("Insert sql: " + sqlfu);
			try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlfu);
			}catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Resulset: " + sqlfu + " Exception: " + e);
			 }
			}
		}
		
		
		
	}
}
		
		
			
			
			
		
		
		