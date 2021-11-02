/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.controllers;

import datnt.account.AccountDTO;
import datnt.cake.CakeDAO;
import datnt.cake.CakeDTO;
import datnt.category.CategoryDAO;
import datnt.category.CategoryDTO;
import datnt.ultis.DBHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
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
public class SearchCakesController extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(SearchCakesController.class);
    private final String SHOPPING_PAGE = "shopping.jsp";
    private final String ADMIN_PAGE = "adminPage.jsp";
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
        int pageSize = request.getParameter("txtPageSize") == null ? 20 : Integer.parseInt(request.getParameter("txtPageSize"));;
        String searchValue = request.getParameter("txtSearchValue") == null ? "" : request.getParameter("txtSearchValue");
        int categoryId = request.getParameter("cbCategory") == null ? 0 : Integer.parseInt(request.getParameter("cbCategory"));
        int pageIndex = request.getParameter("txtPageIndex") == null ? 1 : Integer.parseInt(request.getParameter("txtPageIndex"));
        String moneyMinValue = request.getParameter("txtMin");
        String moneyMaxValue = request.getParameter("txtMax");
        float min = moneyMinValue != null ? (moneyMinValue.isEmpty() ? 0 : Float.parseFloat(moneyMinValue)) : 0;
        float max = moneyMaxValue != null ? (moneyMaxValue.isEmpty() ? Float.MAX_VALUE : Float.parseFloat(moneyMaxValue)) : Float.MAX_VALUE;
        boolean status = (request.getParameter("cbStatus") == null || request.getParameter("cbStatus").isEmpty()) ? true : Boolean.parseBoolean(request.getParameter("cbStatus"));
        int totalPage = 1;
        try {
            System.out.println(DBHelper.encryptPass("123"));
            HttpSession session = request.getSession();
            AccountDTO userLogin = (AccountDTO) session.getAttribute("USERLOGIN");
            CakeDAO dao = new CakeDAO();
            CategoryDAO cateDAO = new CategoryDAO();

            cateDAO.getCategory();
            totalPage = dao.totalPage(pageSize, searchValue, categoryId, 1, min, max, status);
            dao.searchCakes(pageIndex, pageSize, searchValue, categoryId, 1, min, max, status);
            if (userLogin != null) {
                if (userLogin.getRoleId() == 1) {
                    dao = new CakeDAO();
                    totalPage = dao.totalPage(pageSize, searchValue, categoryId, 0, min, max, status);
                    dao.searchCakes(pageIndex, pageSize, searchValue, categoryId, 0, min, max, status);
                }
            }
            List<CakeDTO> result = new ArrayList<>();
            result = dao.getListCakes();
            List<CategoryDTO> cateList = cateDAO.getListCategory();
            cateList.add(0, new CategoryDTO(0, "All"));

            if (userLogin != null) {
                if (userLogin.getRoleId() == 1) {
                    url = ADMIN_PAGE;
                } else {
                    url = SHOPPING_PAGE;
                }
            } else {
                url = SHOPPING_PAGE;
            }

            request.setAttribute("SEARCHRESULT", result);
            request.setAttribute("LISTCATEGORY", cateList);
            request.setAttribute("CURRENTCATEGORY", categoryId);
            request.setAttribute("TOTALPAGE", totalPage);
            request.setAttribute("CURRENTPAGEINDEX", pageIndex);
            request.setAttribute("CURRENTPAGESIZE", pageSize);
            request.setAttribute("CURRENTSTATUS", status);

        } catch (SQLException ex) {
            LOGGER.error(ex.toString());
        } catch (NamingException ex) {
            LOGGER.error(ex.toString());
        } catch (NoSuchAlgorithmException ex){ 
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
