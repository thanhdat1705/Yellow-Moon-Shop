/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.controllers;

import datnt.account.AccountDTO;
import datnt.customer.CustomerDAO;
import datnt.customer.CustomerDTO;
import datnt.order.OrderDAO;
import datnt.order.OrderDTO;
import datnt.orderDetails.OrderDetailsDAO;
import datnt.orderDetails.OrderDetailsDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author NTD
 */
public class SearchOrderController extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(SearchOrderController.class);
    private final String ORDER_TRACKING_PAGE = "orderTracking.jsp";
    private final String SHOPPING_PAGE = "shopping.jsp";
    private final String ERROR_PAGE = "error.jsp";

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = ERROR_PAGE;
        String orderIdTmp = request.getParameter("txtOrderId");
        int orderId = orderIdTmp != null ? (orderIdTmp.isEmpty() ? 0 : Integer.parseInt(orderIdTmp)) : 0;
        String cusName = request.getParameter("txtCusName") == null ? "" : request.getParameter("txtCusName");
        List<OrderDTO> orderList = null;
        try {
            HttpSession session = request.getSession();
            AccountDTO userLogin = (AccountDTO) session.getAttribute("USERLOGIN");
            if (userLogin != null) {
                if (userLogin.getRoleId() == 1) {
                    OrderDAO orderDAO = new OrderDAO();
                    orderDAO.searchOrderByAdmin(orderId, cusName);
                    orderList = orderDAO.getListOrders();
                } else {
                    System.out.println(userLogin.getUserId());
                    CustomerDAO cusDAO = new CustomerDAO();
                    CustomerDTO cusDTO = cusDAO.getCustomerByAccId(userLogin.getUserId());
                    OrderDAO orderDAO = new OrderDAO();
                    System.out.println(cusDTO);
                    orderDAO.searchOrderByCustomer(orderId, cusDTO.getId());
                    orderList = orderDAO.getListOrders();
//                    System.out.println(orderList);

//                    for (OrderDTO order : orderList) {
//                        System.out.println(order.getOrderDetails());
//                    }
                }
                if (orderList != null) {
                    for (OrderDTO order : orderList) {
                        OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
                        orderDetailsDAO.searchOrderDetail(order.getId());
                        List<OrderDetailsDTO> list = orderDetailsDAO.getListOrderDetails();
                        if (list != null) {
                            order.setOrderDetails(list);
                        }
                    }
                    request.setAttribute("SEARCH_ORDER_RESULT", orderList);
                    url = ORDER_TRACKING_PAGE;
                }

            } else {
                url = SHOPPING_PAGE;
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.toString());
        } catch (NamingException ex) {
            LOGGER.error(ex.toString());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
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
