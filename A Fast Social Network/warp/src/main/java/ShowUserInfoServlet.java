/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import gr.csd.uoc.cs359.winter2018.warp.db.UserDB;
import gr.csd.uoc.cs359.winter2018.warp.model.User;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(urlPatterns = {"/ShowUserInfoServlet"})
public class ShowUserInfoServlet extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        StringBuffer buffer = new StringBuffer();
        HttpSession session = request.getSession(true);
        try (PrintWriter out = response.getWriter()) {
            if (session.getAttribute("loggedin") == null || (Boolean) session.getAttribute("loggedin") != true) {
                buffer.append("{\"error\": true,");
                buffer.append("\"message\": \"do login first\" }");
                out.println(buffer);
                return;
            }
            String username = (String) session.getAttribute("username");
            User user = UserDB.getUser(username);
            if (user == null) {

                buffer.append("{\"error\": true,");
                buffer.append("\"message\": \"user not found\" }");
                out.println(buffer);
                return;
            }
            buffer.append("{\"error\": false,");
            buffer.append("\"message\":");

            buffer.append(convertUserToJson(user));

            buffer.append("}");
            out.println(buffer);

        } catch (ClassNotFoundException e) {
        }
    }

    String convertUserToJson(User user) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"UserName\": \"").append(user.getUserName()).append("\",")
                .append("\"email\": \"").append(user.getEmail()).append("\",")
                .append("\"password\": \"").append(user.getPassword()).append("\",")
                .append("\"FirstName\": \"").append(user.getFirstName()).append("\",")
                .append("\"LastName\": \"").append(user.getLastName()).append("\",")
                .append("\"BirthDate\": \"").append(user.getBirthDate()).append("\",")
                .append("\"Gender\": \"").append(user.getGender()).append("\",")
                .append("\"Country\": \"").append(user.getCountry()).append("\",")
                .append("\"Town\": \"").append(user.getTown()).append("\",")
                .append("\"Address\": \"").append(user.getAddress()).append("\",")
                .append("\"Occupation\": \"").append(user.getOccupation()).append("\",")
                .append("\"Interests\": \"").append(user.getInterests()).append("\",")
                .append("\"Info\": \"").append(user.getInfo()).append("\"}");

        return sb.toString();
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
        processRequest(request, response);
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
        processRequest(request, response);
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
