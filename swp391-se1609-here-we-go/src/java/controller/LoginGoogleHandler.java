/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import config.Config;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import manager.UserManager;
import model.GoogleUserDTO;
import model.User;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import utils.Constants;

/**
 *
 * @author Admin
 */
public class LoginGoogleHandler extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        String code = request.getParameter("code");
        String accessToken = getToken(code);
        GoogleUserDTO googleInf = getUserInfor(accessToken);
        User user = null;
        user = UserManager.checkLoginGoogle(googleInf.getId());
        if (user == null){ 
            user = new User(0, googleInf.getName(), googleInf.getId(), null, googleInf.getPicture(), 2, "*****", "","","");
            UserManager.register(user);           
        }
         HttpSession session = request.getSession();
         session.setAttribute("LOGIN_CUSTOMER", user);
         session.setAttribute("LOGIN_CUSTOMER_NAME", user.getName());
         session.setAttribute("LOGIN_CUSTOMER_PHONE", user.getPhone());
         session.setAttribute("LOGIN_ROLE", user.getRoleId());
         request.setAttribute("controller", "home");
         request.setAttribute("action", "index");
         request.getRequestDispatcher(Config.LAYOUT).forward(request, response);                
    }

    //Function
    public static String getToken(String code) throws IOException {
        String response = Request.Post(Constants.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form().add("client_id", Constants.GOOGLE_CLIENT_ID)
                        .add("client_secret", Constants.GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", Constants.GOOGLE_REDIRECT_URI).add("code", code)
                        .add("grant_type", Constants.GOOGLE_GRANT_TYPE).build())
                .execute().returnContent().asString();
        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
        return accessToken;
    }

    public static GoogleUserDTO getUserInfor(final String accessToken) throws IOException {
        String link = Constants.GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        GoogleUserDTO googlePojo = new Gson().fromJson(response, GoogleUserDTO.class);
        return googlePojo;
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
        } catch (SQLException ex) {
            Logger.getLogger(LoginGoogleHandler.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(LoginGoogleHandler.class.getName()).log(Level.SEVERE, null, ex);
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
