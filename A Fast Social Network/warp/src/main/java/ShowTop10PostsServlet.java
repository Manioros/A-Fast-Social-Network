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
@WebServlet(urlPatterns = {"/ShowTop10PostsServlet"})
public class ShowTop10PostsServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException, ClassNotFoundException {

        String htmlHeader = "<html><head><title>Echo Request</title> </head> <body>";
        String htmlFooter = "</body></html>";
        res.setContentType("text/html;charset=UTF-8");
        HttpSession session = req.getSession(true);
        String username = (String) session.getAttribute("username");
        List<Post> posts = PostDB.getTop10RecentPosts();
        try (PrintWriter o = res.getWriter()) {
            o.println(htmlHeader);

            for (int i = 0; i < posts.size(); i++) {
                Post currentPost = posts.get(i);
                Integer postId = currentPost.getPostID();
                String comment = currentPost.getComment();
                String createdAt = currentPost.getCreatedAt();
                String ImageUrl = currentPost.getImageURL();
                String resUrl = currentPost.getResourceURL();
                String lat = currentPost.getLatitude();
                String lon = currentPost.getLongitude();
                String img64 = currentPost.getImageBase64();
                String img = "<img src=\"" + ImageUrl + "\" alt=\"NO IMG AVAILABLE\" height=\"42\" width=\"42\">";
                String link = "<p><a href=\"" + resUrl + "\">trust me and click </a></p>";

                o.print("<div id='");
                o.print(postId);
                o.print("' >");
                o.print("<br><label  id=\'ratefield\' >Rate here:</label><input  type=\"text\"  id=\'RateSection\' required> <button type=\"button\" id=\'Rate\' onclick=\'makeRateReq();\'>Rate</button><br>");
                o.print("<button id = \"mapbtn\" type=\"button\" onclick=create_map_withLatLonParams(\"" + lat + "\",\"" + lon + "\")>See map loc</button>");
                o.println("Posted at:" + createdAt + "\npost id:" + postId + "\ncomment:" + comment + "\nrUrl:" + link + "\nImage:" + img);
                o.println("</div>");

                o.println("<br>***********************************<br>");
            }

            //o.println(htmlFooter);
            o.close();
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
            Logger.getLogger(ShowTop10PostsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ShowTop10PostsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
