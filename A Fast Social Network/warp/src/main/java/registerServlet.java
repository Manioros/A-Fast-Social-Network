/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import gr.csd.uoc.cs359.winter2018.warp.db.UserDB;
import gr.csd.uoc.cs359.winter2018.warp.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author johnm
 */
@WebServlet(urlPatterns = {"/registerServlet"})
public class registerServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NoSuchAlgorithmException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            User user = new User();
            String username = request.getParameter("user");
            String email = request.getParameter("email");
            String password = request.getParameter("pass");
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            String birthday = request.getParameter("birthday");
            String gender = request.getParameter("gender");
            String counrty = request.getParameter("counrty");
            String city = request.getParameter("city");
            String job = request.getParameter("job");
            String interests = request.getParameter("interests");
            String general_information = request.getParameter("general_information");
            String address = request.getParameter("address");

            //digest password **************************************************
            MessageDigest messageDigest;
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            byte[] messageDigestMD5 = messageDigest.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte bytes : messageDigestMD5) {
                stringBuffer.append(String.format("%02x", bytes & 0xff));
            }
            System.out.println("password:" + password);
            System.out.println("digestedMD5(hex):" + stringBuffer.toString());
            String digestedPassword = stringBuffer.toString();
            //*****************************************************************
            user.setUserName(username);
            user.setEmail(email);
            user.setPassword(digestedPassword);
            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setBirthDate(birthday);
            user.setCountry(counrty);
            user.setTown(city);
            user.setAddress(address);
            user.setOccupation(job);
            user.setGender(gender);
            user.setInterests(interests);
            user.setInfo(general_information);

            if (UserDB.checkValidUserName(username)) {
                System.out.println("==>Adding users");
                UserDB.addUser(user);
                System.out.println(user.toString());
                System.out.println("==>Added user");

                StringBuffer buffer = new StringBuffer();
                buffer.append("{\"error\": false,");
                buffer.append("\"message\": \"Η εγγραφή σας πραγματοποιήθηκε επιτυχώς\" }");

                out.println(buffer);

            } else {
                StringBuffer buffer1 = new StringBuffer();
                buffer1.append("{\"error\": true,");
                buffer1.append("\"message\": \"User\" + username + \"already exists\"}");
                out.println(buffer1);
            }
        } catch (ClassNotFoundException e) {
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(registerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(registerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
