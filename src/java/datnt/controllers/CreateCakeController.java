/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.controllers;

import datnt.account.AccountDTO;
import datnt.cake.CakeDAO;
import datnt.cake.CakeDTO;
import datnt.ultis.DBHelper;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

/**
 *
 * @author NTD
 */
public class CreateCakeController extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(CreateCakeController.class);
    private final String CAKE_DETAILS = "cakeDetails.jsp";
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
        String url = CAKE_DETAILS;
        Timestamp today = DBHelper.getTime();
        try {
            List items = (List) request.getAttribute("CREATE_REQUEST");
//            System.out.println(items);
            Iterator iter = items.iterator();
            Hashtable params = new Hashtable();
            String fileName = null;
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField()) {
                    params.put(item.getFieldName(), item.getString());
                } else {
                    try {
                        String itemName = item.getName();
                        fileName = itemName.substring(itemName.lastIndexOf("\\") + 1);
                        System.out.println("path" + fileName);
                        String realPath = getServletContext().getRealPath("/") + "images\\" + fileName;
                        System.out.println("Rpath" + realPath);
                        File saveFile = new File(realPath);
                        item.write(saveFile);
                    } catch (Exception ex) {
                        LOGGER.error(ex.toString());
                    }
                }
            }
            String name = (String) params.get("txtName");
            String imgeURL = fileName;
            float price = Float.parseFloat((String) params.get("txtPrice"));
            String brand = (String) params.get("txtBrand");
            String description = (String) params.get("txtDescription");
            int categoryId = Integer.parseInt((String) params.get("cbCategory"));
            int quantity = Integer.parseInt((String) params.get("txtQuantity"));
            Timestamp createDate = today;
            Timestamp lastUpdate = today;
            Timestamp expirationDate = DBHelper.convertStringToTimestamp((String) params.get("txtExpirationDate"));
            boolean status = true;
            HttpSession session = request.getSession();
            AccountDTO userLogin = (AccountDTO) session.getAttribute("USERLOGIN");
            
            CakeDAO dao = new CakeDAO();
            int cakeId = dao.insertCake(name, imgeURL, price, brand, description, categoryId, quantity, createDate, lastUpdate, expirationDate, userLogin.getName(), status);
            if (cakeId > 0) {
                CakeDTO dto = new CakeDTO();
                dto = dao.viewCake(cakeId);
                request.setAttribute("VIEWRESULT", dto);
                request.setAttribute("CREATE_SUCC", "Create new cake successFully!!!");
            }else {
                url = ADMIN_PAGE;
            }

        } catch (ParseException ex) {
            LOGGER.error(ex.toString());
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
