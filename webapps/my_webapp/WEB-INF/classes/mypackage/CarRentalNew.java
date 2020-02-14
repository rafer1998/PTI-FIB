package mypackage;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.Reader;
import java.io.File;
import java.util.Iterator;
public class CarRentalNew extends HttpServlet {

  int cont = 0;

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();
    //String nombre = req.getParameter("name");

    File f = new File("cotxes.json");
    if (f.exists()) {
    JSONObject obj = jsonParser.parse(new FileReader("cotxes.json"));
    }
    else { 
    JSONObject obj = new JSONObject();
    }
    obj.put("model", req.getParameter("model_vehicle"));
    obj.put("sub_model_vehicle", req.getParameter("sub_model_vehicle"));
    obj.put("dies_lloguer", req.getParameter("dies_lloguer"));
    obj.put("num_vehicles", req.getParameter("num_vehicles"));
    obj.put("descompte", req.getParameter("descompte"));

    try (FileWriter file = new FileWriter("cotxes.json")) {
            file.write(obj.toJSONString());
            out.println("<html><h1>Guardat</h1></html>");
        } catch (IOException e) {
            e.printStackTrace();
            out.println("<html><h1>No Guardat</h1></html>");
        }

        System.out.println(obj);


    //out.println("<html><h1>" + car_name + "</h1></html>");

    //cont ++;
    //out.println("<html><big>Hola Amigo "+ nombre + "</big><br>"+
               // cont + " Accesos desde su carga.</html>");
    //out.println(req);
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
    doGet(req, res);
  }
}
