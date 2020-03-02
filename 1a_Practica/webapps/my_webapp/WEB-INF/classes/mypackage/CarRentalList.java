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

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CarRentalList extends HttpServlet {

  int cont = 0;
  public static String getMd5(String input) {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {

      String user = req.getParameter("userid");
      String password = null;

      PrintWriter out = res.getWriter();

      JSONParser parser = new JSONParser();

      try (Reader reader = new FileReader("users.json")) {
          JSONObject jsonArray = (JSONObject) parser.parse(reader);
          password = (String) jsonArray.get(user);
      } catch (IOException e) {
          out.println("<html><h1>IO Exception</h1></html>");
          e.printStackTrace();
      } catch (ParseException e) {
          out.println("<html><h1>ParseException</h1></html>");
          e.printStackTrace();
      }

        res.setContentType("text/html");
        String md =  getMd5(req.getParameter("password"));
      if (md.equals(password))

    {

        out.println("<html><h1>Cotxes</h1>");

        //JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader("cotxes.json")) {
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            Iterator i = jsonArray.iterator();
            while (i.hasNext()) {
                JSONObject jsonObject = (JSONObject) i.next();
                String model = (String) jsonObject.get("model");
                String sub_model_vehicle = (String) jsonObject.get("sub_model_vehicle");
                String num_vehicles = (String) jsonObject.get("num_vehicles");
                String dies_lloguer = (String) jsonObject.get("dies_lloguer");
                String descompte = (String) jsonObject.get("descompte");
                out.println("<ul>");
                out.println("<li>Model: " + model + "</li>");
                out.println("<li>Sub_Model: " + sub_model_vehicle + "</li>");
                out.println("<li>Num Vehicle: " + num_vehicles + "</li>");
                out.println("<li>Dies Lloguer: " + dies_lloguer + "</li>");
                out.println("<li>Descompte: " + descompte + "</li>");
                out.println("</ul>");
                out.println("</htmls>");
            }
        } catch (IOException e) {
            out.println("<html><h1>IO Exception</h1></html>");
            e.printStackTrace();
        } catch (ParseException e) {

            out.println("<html><h1>ParseException</h1></html>");
            e.printStackTrace();
        }

    }

      else {
        out.println("<html><h1>Login Failed</h1>");
    }
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
    doGet(req, res);
  }
}
