/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.paypal.base.rest.PayPalRESTException;
import config.Config;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import manager.NotificationManager;
import manager.OrderManager;
import manager.PaymentServices;
import manager.TicketManager;
import model.OrderDetail;
import model.RouteDetail;
import model.User;

/**
 *
 * @author Admin
 */
@WebServlet(name = "OrderController", urlPatterns = {"/order"})
public class OrderController extends HttpServlet {

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
        String action = (String) request.getAttribute("action");
        String controller = (String) request.getAttribute("controller");
        switch (action) {
            case "authorizePayment":
                doPost(request, response);
                break;
        }

        request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String typeChange = request.getParameter("typeChange");
        if (typeChange == null) {

            String productName = request.getParameter("productName");
            String subTotal = request.getParameter("subTotal");
            String numberOfTickets = request.getParameter("numberOfTickets");
            String totalPrice = request.getParameter("totalPrice");

            String[] listPasName = request.getParameterValues("pasName");
            String[] listPasPhone = request.getParameterValues("pasPhone");
            HttpSession session = request.getSession();
            session.setAttribute("listPasNameForTicket", listPasName);
            session.setAttribute("listPasPhoneForTicket", listPasPhone);

            OrderDetail orderDetail = new OrderDetail(productName, numberOfTickets, subTotal, totalPrice);

            try {
                PaymentServices paymentServices = new PaymentServices();
                String approvalLink = paymentServices.authorizePayment(orderDetail);

                response.sendRedirect(approvalLink);
            } catch (PayPalRESTException ex) {
                ex.printStackTrace();
                request.setAttribute("errorMessage", "Invalid Payment Details");
                request.getRequestDispatcher(Config.ERROR_PAGE).forward(request, response);
            }
        } else if (typeChange.equals("extra")) {

            String productName = request.getParameter("productName");
            String subTotal = request.getParameter("subTotal");
            String numberOfTickets = request.getParameter("numberOfTickets");
            String totalPrice = request.getParameter("totalPrice");

            String[] listPasName = request.getParameterValues("pasName");
            String[] listPasPhone = request.getParameterValues("pasPhone");
            HttpSession session = request.getSession();
            User us = (User) session.getAttribute("LOGIN_CUSTOMER");
            session.setAttribute("listPasNameForTicket", listPasName);
            session.setAttribute("listPasPhoneForTicket", listPasPhone);

            OrderDetail orderDetail = new OrderDetail(productName, numberOfTickets, subTotal, totalPrice);
            try {
                NotificationManager.add(us.getUserId(), us.getName() + " Change ticket");
            } catch (SQLException ex) {
                Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                PaymentServices paymentServices = new PaymentServices();
                String approvalLink = paymentServices.authorizePayment(orderDetail);

                response.sendRedirect(approvalLink);
            } catch (PayPalRESTException ex) {
                ex.printStackTrace();
                request.setAttribute("errorMessage", "Invalid Payment Details");
                request.getRequestDispatcher(Config.ERROR_PAGE).forward(request, response);
            }
        } else {
            HttpSession session = request.getSession();
            User us = (User) session.getAttribute("LOGIN_CUSTOMER");
            int pos = (int) session.getAttribute("posForTicket");
            String[] listName = request.getParameterValues("pasName");
            String[] listPhone = request.getParameterValues("pasPhone");
            RouteDetail rd = (RouteDetail) session.getAttribute("RouteDetailForTicket");
            try {
                OrderManager.addOrder(us.getUserId(), rd.getPrice());

                long id = OrderManager.getOrderIdLatest(us.getUserId());
                long idChange = (long) request.getSession().getAttribute("ticketIdChange");
                TicketManager.changeTicket(idChange, id, rd.getRouteDetailId(), pos, listName[0], listPhone[0]);
                NotificationManager.add(us.getUserId(), us.getName() + " Change ticket");
                session.removeAttribute("ticketIdChange");
            } catch (Exception e) {
                System.out.println(e);
            }
            request.setAttribute("controller", "user");
            request.setAttribute("action", "myBooking");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
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
