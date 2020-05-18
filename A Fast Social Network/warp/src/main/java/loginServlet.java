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
import javax.servlet.http.HttpSession;

/**
 *
 * @author johnm
 */
@WebServlet(urlPatterns = {"/loginServlet"})
public class loginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.ClassNotFoundException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, NoSuchAlgorithmException {
        String username = request.getParameter("user");
        String password = request.getParameter("pass");
        if (username == null || password == null
                || !username.matches("[A-Za-z]{8,}")
                || !password.matches("(?=.*\\d)(?=.*[a-zA-Z])(?=.*[^A-Za-z0-9]).{8,10}")) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            response.setHeader("WWW-Authenticate", "BASIC realm=\"Your realm\"");

            response.setContentType("Bad username or password");
            return;
        }

        HttpSession session = request.getSession(true);


        //boolean flag = false;/*save if exists user with username and password*/
        response.setContentType("application/json;charset=UTF-8");
        StringBuffer buffer = new StringBuffer();
        try (PrintWriter out = response.getWriter()) {
            User user = UserDB.getUser(username);
            //******************************************************************
            //get password hash from login input
            MessageDigest messageDigest;
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            byte[] messageDigestMD5 = messageDigest.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte bytes : messageDigestMD5) {
                stringBuffer.append(String.format("%02x", bytes & 0xff));
            }
            String digestedPassword = stringBuffer.toString();
            System.out.println("password:" + password);
            System.out.println("digestedMD5(hex):" + stringBuffer.toString());
            //******************************************************************
            if (user != null) {
                if (digestedPassword.equals(user.getPassword())) {
                    buffer.append("{\"error\": false,");
                    buffer.append("\"message\": \"ok\" }");
                    session.setAttribute("loggedin", true);
                    session.setAttribute("username", username);
                } else {
                    buffer.append("{\"error\": true,");
                    buffer.append("\"message\": \"no such username or password wrong\" }");
                }

            } else {
                buffer.append("{\"error\": true,");
                buffer.append("\"message\": \"no such username or password wrong\" }");
            }
            out.println(buffer);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
