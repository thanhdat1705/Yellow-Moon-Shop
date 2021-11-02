/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.controllers;

import datnt.account.AccountDAO;
import static datnt.controllers.LoginController.LOGGER;
import datnt.customer.CustomerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author NTD
 */
public class SignUpController extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(SignUpController.class);
    private final String LOGIN_PAGE = "login.jsp";
    private final String SIGNUP_ERROR = "signUp.jsp";

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
        String url = SIGNUP_ERROR;
        String userId = request.getParameter("txtId");
        String password = request.getParameter("txtPassword");
        int roleId = 2;
        String name = request.getParameter("txtFullName");
        String address = request.getParameter("txtAddress");
        String phone = request.getParameter("txtPhone");

        try {
//            System.out.println(name);
//            System.out.println(address);
            AccountDAO accDAO = new AccountDAO();
            boolean isDup = accDAO.isDuplicateUserId(userId);
            if (!isDup) {
                String accId = accDAO.insertAccount(userId, password, name, roleId);
                if (accId != null) {
                    CustomerDAO cusDAO = new CustomerDAO();
                    int result = cusDAO.insertCustomer(accId, name, phone, address);
                    if (result > 0) {
                        url = LOGIN_PAGE;
                        request.setAttribute("SIGNUP_SUCC", "You have successfully registered an account!!!");
                    }
                }
            } else {
                request.setAttribute("IS_DUP_USERID", "This user id already exists in the system!!!");
            }

        } catch (NamingException ex) {
            LOGGER.error(ex.toString());
        } catch (SQLException ex) {
            LOGGER.error(ex.toString());
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error(ex.toString());
        } catch (UnsupportedEncodingException ex) {
            LOGGER.error(ex.toString());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
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
