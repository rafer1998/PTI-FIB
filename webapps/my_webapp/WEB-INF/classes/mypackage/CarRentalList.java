package mypackage;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

public class CarRentalList extends HttpServlet {

  int cont = 0;

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();
    //String nombre = req.getParameter("userid");
    /*cont ++;
    out.println("<html><big>Hola Amigo "+ nombre + "</big><br>"+
                cont + " Accesos desde su carga.</html>"); */
    JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader("cotxes.json")) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            String model = (String) jsonObject.get("model");
            String sub_model_vehicle = (String) jsonObject.get("sub_model_vehicle");
            String num_vehicles = (String) jsonObject.get("num_vehicles");
            String dies_lloguer = (String) jsonObject.get("dies_lloguer");
            String descompte = (String) jsonObject.get("descompte");
            out.println("<html><h1>Cotxes</h1>");
            out.println("<ul>");
            out.println("<li>Model: "+ model + "</li>");
            out.println("<li>Sub_Model: "+ sub_model_vehicle + "</li>");
            out.println("<li>Num Vehicle: "+ num_vehicles + "</li>");
            out.println("<li>Dies Lloguer: "+ dies_lloguer + "</li>");
            out.println("<li>Descompte: "+ descompte + "</li>");
            out.println("</ul>");
            out.println("</htmls>");

            } catch (IOException e) {

            out.println("<html><h1>IO Exception</h1></html>");
            e.printStackTrace();
        } catch (ParseException e) {

            out.println("<html><h1>ParseException</h1></html>");
            e.printStackTrace();
        }
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
    doGet(req, res);
  }
}
