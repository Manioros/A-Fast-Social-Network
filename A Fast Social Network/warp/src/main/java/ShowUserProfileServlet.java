/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import gr.csd.uoc.cs359.winter2018.warp.db.PostDB;
import gr.csd.uoc.cs359.winter2018.warp.model.Post;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author john
 */
public class ShowUserProfileServlet extends HttpServlet {

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
        try {
            HttpSession session = request.getSession(true);
            StringBuffer buffer = new StringBuffer();

            //String username = (String) session.getAttribute("userName");
            String username = request.getParameter("userName");
            System.out.println("@@@@@@@@@@@@@@  " + username);
            List<Post> posts = PostDB.getTop10RecentPostsOfUser(username);
            //List< Post> posts2 = PostDB.getPosts();
            //System.out.println("ALL POSTS: " + Arrays.toString(posts2.toArray()));
            //katerinav
            System.out.println("hellooooooooooooo");
            //System.out.println(Arrays.toString(posts.toArray()));
            try (PrintWriter out = response.getWriter()) {
                if (session.getAttribute("loggedin") == null || (Boolean) session.getAttribute("loggedin") != true) {
                    buffer.append("{\"error\": true,");
                    buffer.append("\"message\": \"do login first\" }");
                    out.println(buffer);
                    return;
                }
                buffer.append("{\"error\": false,");
                buffer.append("\"message\":\"");
                if (posts.isEmpty()) {
                    buffer.append("NO POSTS");
                    buffer.append("<br/>");
                } else {
                    buffer.append("POSTS OF USER " + username);
                    buffer.append("<br/>");
                    for (int i = 0; i < posts.size(); i++) {

                        Post currentPost = posts.get(i);
                        buffer.append(currentPost.getComment());
                        buffer.append("<br/>");
                    }
                }

                buffer.append("\"}");
                out.println(buffer);

            }

        } catch (Exception e) {
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
