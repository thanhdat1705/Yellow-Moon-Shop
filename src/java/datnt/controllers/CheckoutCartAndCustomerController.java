/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.controllers;

import datnt.account.AccountDTO;
import datnt.cake.CakeDAO;
import datnt.cake.CakeDTO;
import datnt.cart.CartItem;
import datnt.customer.CustomerDAO;
import datnt.customer.CustomerDTO;
import datnt.order.OrderDAO;
import datnt.orderDetails.OrderDetailsDAO;
import datnt.ultis.DBHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
public class CheckoutCartAndCustomerController extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(CheckoutCartAndCustomerController.class);
    private final String CART_PAGE = "cartPage.jsp";
    private final String SHOPPING_PAGE = "shopping.jsp";
    private final String CUSTOMER_INFO_PAGE = "customerInfoPage.jsp";
    private final String ORDER_TRACKING_PAGE = "orderTracking.jsp";

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
        List<String> amount_exceed_item_List = null;
        List<CartItem> cartList = null;
        String url = SHOPPING_PAGE;
        String name = request.getParameter("txtName");
        String phone = request.getParameter("txtPhone");
        String address = request.getParameter("txtAddress");
        int paymentMethodId = Integer.parseInt(request.getParameter("cbPaymentMethod"));
        float totalOrder = 0;
        Timestamp today = DBHelper.getTime();
        boolean foundErr = false;

        try {
            HttpSession session = request.getSession();
            if (session != null) {
                cartList = (List<CartItem>) session.getAttribute("CART_LIST");
                if (cartList == null) {
                    foundErr = true;
                    request.setAttribute("NOTIFICATIONS_ADDTOCART", "Your shopping cart has been cancelled.");
                    url = SHOPPING_PAGE;
                } else {
//                    System.out.println(name);
//                    System.out.println(phone);
//                    System.out.println(address);
//                    System.out.println(paymentMethodId);
                    for (CartItem item : cartList) {
                        int cakeId = item.getCake().getId();
                        float price = item.getCake().getPrice();
                        int amount = item.getQuantity();
                        CakeDAO cakeDAO = new CakeDAO();
                        boolean isActive = cakeDAO.checkStatus(cakeId, true);
                        totalOrder += price * amount;
                        if (isActive) {
                            CakeDTO cakeDTO = cakeDAO.viewCake(cakeId);
                            if (cakeDTO.getQuantity() < amount) {
                                if (amount_exceed_item_List == null) {
                                    amount_exceed_item_List = new ArrayList<>();
                                }
                                amount_exceed_item_List.add(cakeDTO.getName() + " is only has " + cakeDTO.getQuantity() + " in store. Please choose again!!!");
                                foundErr = true;
                            }
                        }
                    }
                    if (foundErr) {
                        request.setAttribute("AMOUNTEXCEEDLIST", amount_exceed_item_List);
                        url = CART_PAGE;
                    } else {
                        AccountDTO userLogin = (AccountDTO) session.getAttribute("USERLOGIN");
                        if (userLogin != null) {
                            CustomerDAO cusDAO = new CustomerDAO();
                            CustomerDTO cusDTO = cusDAO.getCustomerByAccId(userLogin.getUserId());
                            OrderDAO orderDAO = new OrderDAO();
                            int orderId = orderDAO.insertOrder(cusDTO.getId(), DBHelper.roundTo2Decimal(totalOrder), today, paymentMethodId, 1);
                            if (orderId > 0) {
                                for (CartItem item : cartList) {
                                    int cakeId = item.getCake().getId();
                                    int amount = item.getQuantity();
                                    OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
                                    CakeDAO cakeDAO = new CakeDAO();
                                    CakeDTO cakeDTO = cakeDAO.viewCake(cakeId);
                                    orderDetailsDAO.insertOrderDetail(orderId, cakeId, amount);
                                    int remain = cakeDTO.getQuantity() - amount;
                                    cakeDAO.updateCakeAfterOrder(cakeId, remain);
                                }
                                
                                request.setAttribute("NOTIFICATION", "Thank you for purchase our product.");
                            }
                            url = ORDER_TRACKING_PAGE;
                        } else {
                            //17/17 duong so 52 - P. Hiep Binh Chanh - Thu Duc
                            CustomerDAO cusDAO = new CustomerDAO();
                            int customerId = cusDAO.insertCustomer(null, name, phone, address);
                            if (customerId > 0) {
                                OrderDAO orderDAO = new OrderDAO();
                                int orderId = orderDAO.insertOrder(customerId, DBHelper.roundTo2Decimal(totalOrder), today, paymentMethodId, 1);
                                if (orderId > 0) {
                                    for (CartItem item : cartList) {
                                        int cakeId = item.getCake().getId();
                                        int amount = item.getQuantity();
                                        OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
                                        CakeDAO cakeDAO = new CakeDAO();
                                        CakeDTO cakeDTO = cakeDAO.viewCake(cakeId);
                                        orderDetailsDAO.insertOrderDetail(orderId, cakeId, amount);
                                        int remain = cakeDTO.getQuantity() - amount;
                                        cakeDAO.updateCakeAfterOrder(cakeId, remain);
                                    }

                                    request.setAttribute("NOTIFICATIONS_ADDTOCART", "Thank you for purchase our product.");
                                }
                            }
                            url = SHOPPING_PAGE;

                        }
                        session.removeAttribute("CART_LIST");
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.toString());
        } catch (NamingException ex) {
            LOGGER.error(ex.toString());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
//            response.sendRedirect(url);
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
