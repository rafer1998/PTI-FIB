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
      JSONObject obj = new JSONObject();
      JSONArray list = new JSONArray();
    File f = new File("cotxes.json");
    if (f.exists()) {
        try (Reader reader = new FileReader("cotxes.json")) {
            out.println("<html><h1>Existe</h1></html>");
            JSONParser parser = new JSONParser();
            //obj = (JSONObject) parser.parse(reader);
            list = (JSONArray) parser.parse(reader);
            //JSONObject obj = jsonParser.parse(new FileReader("cotxes.json"));
        }
        catch (ParseException e) {

            out.println("<html><h1>ParseException</h1></html>");
            e.printStackTrace();
        }
    }
    else { 
        obj = new JSONObject();
    }
    JSONObject cotxe = new JSONObject();
    cotxe.put("model", req.getParameter("model_vehicle"));
    cotxe.put("sub_model_vehicle", req.getParameter("sub_model_vehicle"));
    cotxe.put("dies_lloguer", req.getParameter("dies_lloguer"));
    cotxe.put("num_vehicles", req.getParameter("num_vehicles"));
    cotxe.put("descompte", req.getParameter("descompte"));
    list.add(cotxe);
    //obj.put("cotxes")


    try (FileWriter file = new FileWriter("cotxes.json")) {
            file.write(list.toJSONString());
            out.println("<html><h1>Guardat</h1></html>");
        } catch (IOException e) {
            e.printStackTrace();
            out.println("<html><h1>No Guardat</h1></html>");
        }

        System.out.println(obj);
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
    doGet(req, res);
  }
}
