/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import gr.csd.uoc.cs359.winter2018.warp.db.PostDB;
import gr.csd.uoc.cs359.winter2018.warp.model.Post;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author john
 */
public class addPostServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        System.out.println("@@@@@@@@&&&&&&&&&&&&&@@@@@@@@@@");
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
            String text = request.getParameter("text");
            String url = request.getParameter("url");
            String imgUrl = request.getParameter("imgUrl");

            Post post = new Post();
            post.setComment(text);
            post.setResourceURL(url);
            post.setImageURL(imgUrl);
            post.setUserName(username);
            PostDB.addPost(post);
            System.out.println(PostDB.getTop10RecentPostsOfUser("testuname"));
            buffer.append("{\"error\": false,");
            buffer.append("\"message\":\"ok\" ");
            buffer.append("}");
            out.println(buffer);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(addPostServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
