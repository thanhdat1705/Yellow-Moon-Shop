/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.controllers;

import datnt.cake.CakeDAO;
import datnt.cart.CartItem;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
public class SaveChangeCartController extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(SaveChangeCartController.class);
    private final String CART_PAGE = "cartPage.jsp";
    private final String SHOPPING_PAGE = "shopping.jsp";

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
        boolean status = true;
        List<String> invalid_item_List = null;
        String itemList[] = request.getParameterValues("listCodeAndAmount");
        String url = CART_PAGE;

        try {
            List<CartItem> cartList;
            HttpSession session = request.getSession(false);
            if (session != null) {
                cartList = (List<CartItem>) session.getAttribute("CART_LIST");
                if (cartList == null) {
                    cartList = new ArrayList<>();
                    request.setAttribute("NOTIFICATIONS_ADDTOCART", "Your shopping cart has been cancelled.");
                    url = SHOPPING_PAGE;
                } else {
                    for (String item : itemList) {
                        String items[] = item.split(";;;;;");
                        int cakeId = Integer.parseInt(items[0].trim());
                        String cakeName = items[1].trim();
                        int amount = Integer.parseInt(items[2].trim());
//                        System.out.println(cakeId + cakeName + amount);
                        CakeDAO cakeDAO = new CakeDAO();
                        boolean isActive = cakeDAO.checkStatus(cakeId, status);

                        if (isActive) {
                            //update item in cart
                            for (CartItem cartItem : cartList) {
                                if (cartItem.getCake().getId() == cakeId) {
                                    cartItem.setQuantity(amount);
                                    break;
                                }
                            }
                            session.setAttribute("CART_LIST", cartList);
                        }
//                        else {
//                            //delete item in cart
//                            int index = 0;
//                            for (CartItems cartItem : cartList) {
//                                if (cartItem.getCake().getId() == cakeId) {
//                                    cartList.remove(index);
//                                    break;
//                                }
//                                index++;
//                            }
//                            if (invalid_item_List == null) {
//                                invalid_item_List = new ArrayList<>();
//                            }
//                            invalid_item_List.add(cakeName);
//                        }
                    }
//                    request.setAttribute("REMOVELIST", invalid_item_List);
                    request.setAttribute("NOTIFICATIONS_ADDTOCART", "Your shopping cart has been saved.");
                }
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
