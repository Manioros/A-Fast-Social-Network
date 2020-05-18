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
@WebServlet(urlPatterns = {"/updateServlet"})
public class updateServlet extends HttpServlet {

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
        HttpSession session = request.getSession(true);
        StringBuffer buffer = new StringBuffer();
        try (PrintWriter out = response.getWriter()) {
            if (session.getAttribute("loggedin") == null || (Boolean) session.getAttribute("loggedin") != true) {
                buffer.append("{\"error\": true,");
                buffer.append("\"message\": \"do login first\" }");
                out.println(buffer);
                return;
            }
            String username = (String) session.getAttribute("username");
            User user = UserDB.getUser(username);
            if (user != null) {
                //String username = request.getParameter("user");
                //String email = request.getParameter("email");
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

                //user.setUserName(username);
                //user.setEmail(email);
                user.setPassword(password);
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

                System.out.println("Updating" + user.getUserName());

                UserDB.updateUser(user);
                buffer.append("{\"error\": false,");
                buffer.append("\"message\": \"Η ενημερωση σας πραγματοποιήθηκε επιτυχώς\" }");

                out.println(buffer);
            }

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
