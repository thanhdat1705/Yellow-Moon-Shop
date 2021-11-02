/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.controllers;

import static datnt.controllers.CreateCakeController.LOGGER;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author NTD
 */
public class MainController extends HttpServlet {

    private final String LOGIN_PAGE = "login.jsp";
    private final String LOGIN_CONTROLLER = "LoginController";
    private final String LOGOUT_CONTROLLER = "LogoutController";
    private final String SEARCH_CAKES_CONTROLLER = "SearchCakesController";
    private final String CREATE_CAKE_CONTROLLER = "CreateCakeController";
    private final String GET_CATEGORY_CONTROLLER = "GetCategoryController";
    private final String VIEW_CAKE_CONTROLLER = "ViewCakeController";
    private final String SHOPPING_PAGE = "shopping.jsp";
    private final String UPDATE_CAKE_CONTROLLER = "UpdateCakeController";
    private final String STARTUP_CONTROLLER = "StartupController";
    private final String SIGNUP_CONTROLLER = "SignUpController";
    private final String ADD_TO_CART_CONTROLLER = "AddToCartController";
    private final String SAVE_CHANGE_CART_CONTROLLER = "SaveChangeCartController";
    private final String DELETE_FROM_CART_CONTROLLER = "DeleteFromCartController";
    private final String CONFIRM_CART_CONTROLLER = "ConfirmCartController";
    private final String CHECKOUT_CART_AND_CUSTOMER_CONTROLLER = "CheckoutCartAndCustomerController";
    private final String GET_PAYMENT_METHOD_CONTROLLER = "GetPaymentMethodController";
    private final String GET_CUSTOMER_CONTROLLER = "GetCustomerController";
    private final String SEARCH_ORDER_CONTROLLER = "SearchOrderController";

//    private final String CUSTOMER_INFO_PAGE = "customerInfoPage.jsp";
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
        String url = STARTUP_CONTROLLER;
        boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
        String action = null;
        String tmp = null;
        if (!isMultiPart) {
            action = request.getParameter("btnAction");
        } else {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List items = null;
            try {
                items = upload.parseRequest(request);
                Iterator iter = items.iterator();
                Hashtable params = new Hashtable();
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    if (item.isFormField()) {
                        params.put(item.getFieldName(), item.getString());
                    }
                }
                action = (String) params.get("btnAction");
                if (action.equals("Create new")) {
                    request.setAttribute("CREATE_REQUEST", items);
                } else if (action.equals("Confirm update")) {
                    request.setAttribute("UPDATE_REQUEST", items);
                }
            } catch (FileUploadException ex) {
                LOGGER.error(ex.toString());
            }
        }
        System.out.println(action + " action");
        try {
            if (action == null) {
//                url = SEARCH_CAKES_CONTROLLER;
                url = STARTUP_CONTROLLER;
            } else if (action.equals("Login")) {
                url = LOGIN_CONTROLLER;
            } else if (action.equals("Logout")) {
                url = LOGOUT_CONTROLLER;
            } else if (action.equals("Search")) {
                url = SEARCH_CAKES_CONTROLLER;
            } else if (action.equals("ChangePage")) {
                url = SEARCH_CAKES_CONTROLLER;
            } else if (action.equals("Create new")) {
                url = CREATE_CAKE_CONTROLLER;
            } else if (action.equals("GetCategory")) {
                url = GET_CATEGORY_CONTROLLER;
            } else if (action.equals("ViewDetail")) {
                url = VIEW_CAKE_CONTROLLER;
            } else if (action.equals("Update")) {
                url = VIEW_CAKE_CONTROLLER;
            } else if (action.equals("Confirm update")) {
                url = UPDATE_CAKE_CONTROLLER;
            } else if (action.equals("Sign Up")) {
                url = SIGNUP_CONTROLLER;
            } else if (action.equals("Add to card")) {
                url = ADD_TO_CART_CONTROLLER;
            } else if (action.equals("Save")) {
                url = SAVE_CHANGE_CART_CONTROLLER;
            } else if (action.equals("Confirm delete")) {
                url = DELETE_FROM_CART_CONTROLLER;
            } else if (action.equals("Confirm purchase")) {
                url = CONFIRM_CART_CONTROLLER;
//                url = CHECKOUT_CART_AND_CUSTOMER_CONTROLLER;
            } else if (action.equals("GetPaymentMethod")) {
                url = GET_PAYMENT_METHOD_CONTROLLER;
            } else if (action.equals("GetCustomer")) {
                url = GET_CUSTOMER_CONTROLLER;
            } else if (action.equals("Check out")) {
                url = CHECKOUT_CART_AND_CUSTOMER_CONTROLLER;
            } else if (action.equals("Search order")) {
                url = SEARCH_ORDER_CONTROLLER;
            }

        } finally {
//            System.out.println(url);
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
