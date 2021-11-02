/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.controllers;

import datnt.cake.CakeDAO;
import datnt.cake.CakeDTO;
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
public class AddToCartController extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(SearchCakesController.class);
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
        String url = SHOPPING_PAGE;
//        CakeDTO cake = (CakeDTO) request.getParameter("cakeInfo");
        int cakeId = Integer.parseInt(request.getParameter("cakeId"));
        int amount = Integer.parseInt(request.getParameter("txtQuantity"));
        boolean isActive = false;
        boolean found = false;
        try {
            CakeDAO dao = new CakeDAO();
            isActive = dao.checkStatus(cakeId, true);
            if (isActive) {
                CakeDTO cake = dao.viewCake(cakeId);
                List<CartItem> cartList;
                CartItem item = new CartItem(cake, amount);

                HttpSession session = request.getSession();
                cartList = (List<CartItem>) session.getAttribute("CART_LIST");
//                System.out.println(cartList);

                if (cartList == null) {
                    cartList = new ArrayList<>();
                    cartList.add(item);
                } else {
                    for (CartItem cartItem : cartList) {
                        if (cartItem.getCake().getId() == cakeId) {
                            found = true;
                            cartItem.setQuantity(cartItem.getQuantity() + amount);
                            break;
                        } else {
//                            System.out.println("not found");
                            found = false;
                        }
                    }
                    if (!found) {
                        cartList.add(item);
                    }
                }
                session.setAttribute("CART_LIST", cartList);
//                cartList.forEach(element -> {
//                    System.out.println(element.getCake().getName() + " - " + element.getQuantity());
//                });

                request.setAttribute("NOTIFICATIONS_ADDTOCART", "Item was successfully added to your cart.");
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
