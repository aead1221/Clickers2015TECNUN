package org.iso;
import java.sql.*;
import java.io.*; 
import java.util.*;

import javax.servlet.*; 
import javax.servlet.http.*;

public class clickers {
	public static void printTop(HttpServletResponse response, String user) throws IOException {
		
		PrintWriter out = response.getWriter();
		
		String htmlStr="";
		htmlStr += "<!DOCTYPE html>";
		htmlStr += "<html lang=\"en\">";
		htmlStr += "	<head>";
		htmlStr += "		<title>Clickers - Test</title>";
		htmlStr += "        <link rel=\"stylesheet\" type=\"text/css\" href=\"css/clickers.css\">";
		htmlStr += "	</head>";
		htmlStr += "	<body>";
		htmlStr += "        <header>";
		htmlStr += "            <div id=\"clk-header\">";
		htmlStr += "                <h1>Clickers</h1>";
		htmlStr += "                <h2>TECNUN's online test taking platform</h2>";
		htmlStr += "            </div>";
		htmlStr += "            <div id=\"clk-user-bar\">";
		htmlStr += "                <div id=\"clk-user-buttons\">";
		htmlStr += "                    <a href=\"UserPersonalInfo.html\">";
		htmlStr += "                        <span id=\"clk-user-view\">";
		htmlStr += "                            <img id=\"clk-user-icon\" src=\"images/user-icon.png\">";
		htmlStr += "                            " + user + "";
		htmlStr += "                        </span>";
		htmlStr += "                    </a>";
		htmlStr += "                    <a href=\"Login.html\">";
		htmlStr += "                        <span id=\"clk-user-logout\">";
		htmlStr += "                            Logout";
		htmlStr += "                        </span>";
		htmlStr += "                    </a>";
		htmlStr += "                </div>";
		htmlStr += "            </div>";
		htmlStr += "            <a href=\"Login.html\">";
		htmlStr += "                <div id=\"clk-menu-bar\">";
		htmlStr += "                    B A C K   &nbspT O&nbsp   M E N U";
		htmlStr += "                </div>";
		htmlStr += "            </a>";
		htmlStr += "        </header>";
		htmlStr += "        <div id=\"clk-body\">";
		
		out.println(htmlStr);

	}
	
	public static void printBottom(HttpServletResponse response) throws IOException {
		
		PrintWriter out = response.getWriter();
		
		String htmlStr="";
		htmlStr += "        </div>";
		htmlStr += "        <footer>";
		htmlStr += "            <div class=\"clk-footer\">";
		htmlStr += "                <h3>&copy Tecnun. Escuela de Ingenieros. Universidad de Navarra. Paseo de Manuel Lardizabal, 13. 20018 Donostia-San Sebastian. Gipuzkoa (Spain). ";
		htmlStr += "                <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tel. +34 943 21 98 77. Fax: +34 943 31 14 42. GPS: Latitud: 43.304654 Longitud: -2.009873.";
		htmlStr += "                </h3>";
		htmlStr += "                <div>";
		htmlStr += "                    <img id=\"logouni\" src=\"images/logo.png\"> ";
		htmlStr += "                </div>";
		htmlStr += "            </div>";
		htmlStr += "        </footer>";
		htmlStr += "	</body>";
		htmlStr += "</html>";
		
		out.println(htmlStr);

	}
}