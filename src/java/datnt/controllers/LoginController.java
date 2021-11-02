/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.controllers;

import datnt.account.AccountDAO;
import datnt.account.AccountDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author NTD
 */
public class LoginController extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(LoginController.class);
    private final String LOGIN_ERROR = "login.jsp";
    private final String SHOPPING_PAGE = "shopping.jsp";
    private final String ADMIN_PAGE = "adminPage.jsp";

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
        String userId = request.getParameter("txtId").trim();
        String password = request.getParameter("txtPassword").trim();
        boolean foundErr = false;
        String error = "error";
        String url = LOGIN_ERROR;
        try {
            AccountDAO dao = new AccountDAO();
            boolean result = dao.checkLogin(userId, password);
            if (result) {

                AccountDTO userLogin = dao.getAccount();
                if (userLogin.getRoleId() == 1) {
                    url = ADMIN_PAGE;
                } else {
                    url = SHOPPING_PAGE;
                }

                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                    Cookie[] cookies = request.getCookies();
                    if (cookies != null) {
                        for (Cookie cookie : cookies) {
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                        }
                    }
                }
                session = request.getSession();
                session.setAttribute("USERLOGIN", userLogin);

                Cookie cookie = new Cookie(userId, password);
                cookie.setMaxAge(60 * 60);
                response.addCookie(cookie);
            } else {
                foundErr = true;
                error = "This account not existed or Invalid email or password!!!";
            }
            if (foundErr) {
                request.setAttribute("LOGINERROR", error);
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
            if (foundErr) {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
                out.close();
            } else {
                response.sendRedirect(url);
                out.close();
            }

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
