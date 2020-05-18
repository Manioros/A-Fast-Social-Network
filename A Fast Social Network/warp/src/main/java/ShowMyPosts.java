/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import gr.csd.uoc.cs359.winter2018.warp.db.PostDB;
import gr.csd.uoc.cs359.winter2018.warp.db.UserDB;
import gr.csd.uoc.cs359.winter2018.warp.model.Post;
import gr.csd.uoc.cs359.winter2018.warp.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
@WebServlet(urlPatterns = {"/ShowMyPosts"})
public class ShowMyPosts extends HttpServlet {

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
            buffer.append("\"message\":\"");
            //String uname = user.getUserName();
            List<Post> posts = PostDB.getPostsOfUser(username);
            for (int i = 0; i < posts.size(); i++) {

                Post currentPost = posts.get(i);
                buffer.append("Username: ");
                buffer.append(currentPost.getUserName());
                buffer.append(",PostID: ");
                buffer.append(currentPost.getPostID());
                buffer.append(" Comment: ");
                buffer.append(currentPost.getComment());
                buffer.append(" ImageURL: ");
                buffer.append(currentPost.getImageURL());
                buffer.append(" ImageBase64: ");
                buffer.append(currentPost.getImageBase64());
                buffer.append("Latitude: ");
                buffer.append(currentPost.getLatitude());
                buffer.append(" Longitude: ");
                buffer.append(currentPost.getLongitude());
                buffer.append(" CreatedAt: ");
                buffer.append(currentPost.getCreatedAt());
                buffer.append("<br/>");
            }
            buffer.append("\"}");
            out.println(buffer);

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
