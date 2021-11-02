/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.order;

import datnt.cake.CakeDTO;
import datnt.ultis.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author NTD
 */
public class OrderDAO implements Serializable {

    private List<OrderDTO> listOrders = new ArrayList<>();

    public List<OrderDTO> getListOrders() {
        return listOrders;
    }

    public int insertOrder(int customerId, float totalPrice, Timestamp createDate, int paymentMethodId, int paymentStatusId)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.getConnect();

            if (con != null) {
                String sql = "INSERT INTO Orders(CustomerId, TotalPrice, CreateDate, PaymentMethodId, PaymentStatusId) "
                        + "VALUES (?,?,?,?,?) ";
                stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stm.setInt(1, customerId);
                stm.setFloat(2, totalPrice);
                stm.setTimestamp(3, createDate);
                stm.setInt(4, paymentMethodId);
                stm.setInt(5, paymentStatusId);

                int row = stm.executeUpdate();
                if (row > 0) {
                    rs = stm.getGeneratedKeys();
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return 0;
    }

    public void searchOrderByCustomer(int orderId, int cusId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "DECLARE "
                        + "@orderId int = ?; "
                        + "IF @orderId = 0 "
                        + "BEGIN "
                        + "SELECT O.Id, O.CustomerId, O.TotalPrice, O.CreateDate, PM.Name as 'PaymentMethod', PS.Name as 'PaymentStatus', C.Name, C.Address "
                        + "FROM dbo.Orders as O INNER JOIN dbo.Customer as C ON O.CustomerId = C.Id  "
                        + "INNER JOIN dbo.PaymentMethod as PM ON O.PaymentMethodId = PM.Id "
                        + "INNER JOIN dbo.PaymentStatus as PS ON O.PaymentStatusId = PS.Id "
                        + "WHERE O.CustomerId = ? "
                        + "ORDER BY O.CreateDate DESC "
                        + "END "
                        + "ELSE "
                        + "BEGIN "
                        + "SELECT O.Id, O.CustomerId, O.TotalPrice, O.CreateDate, PM.Name as 'PaymentMethod', PS.Name as 'PaymentStatus', C.Name, C.Address "
                        + "FROM dbo.Orders as O INNER JOIN dbo.Customer as C ON O.CustomerId = C.Id "
                        + "INNER JOIN dbo.PaymentMethod as PM ON O.PaymentMethodId = PM.Id "
                        + "INNER JOIN dbo.PaymentStatus as PS ON O.PaymentStatusId = PS.Id "
                        + "WHERE O.CustomerId = ? AND O.Id LIKE ? "
                        + "ORDER BY O.CreateDate DESC "
                        + "END; ";

                stm = con.prepareStatement(sql);
                stm.setInt(1, orderId);
                //selecting orders in all order.
                stm.setInt(2, cusId);
                //end
                //selecting products in specify order.
                stm.setInt(3, cusId);
                stm.setString(4, "%" + orderId + "%");
                //end
                rs = stm.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    int customerId = rs.getInt(2);
                    float totalPrice = rs.getFloat(3);
                    Timestamp createDate = rs.getTimestamp(4);
                    String paymentMethod = rs.getString(5);
                    String paymentStatus = rs.getString(6);
                    String customerName = rs.getString(7);
                    String address = rs.getString(8);

                    OrderDTO dto = new OrderDTO(id, customerId, customerName, DBHelper.roundTo2Decimal(totalPrice), DBHelper.TimeFormat(createDate), address, paymentMethod, paymentStatus, null);

                    if (this.listOrders == null) {
                        this.listOrders = new ArrayList<>();
                    }
                    this.listOrders.add(dto);

                }
            }
        } finally {
            if (con != null) {
                con.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

    public void searchOrderByAdmin(int orderId, String cusName) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "DECLARE "
                        + "@orderId int = ?; "
                        + "IF @orderId = 0 "
                        + "BEGIN "
                        + "SELECT O.Id, O.CustomerId, O.TotalPrice, O.CreateDate, PM.Name as 'PaymentMethod', PS.Name as 'PaymentStatus', C.Name, C.Address "
                        + "FROM dbo.Orders as O INNER JOIN dbo.Customer as C ON O.CustomerId = C.Id "
                        + "INNER JOIN dbo.PaymentMethod as PM ON O.PaymentMethodId = PM.Id "
                        + "INNER JOIN dbo.PaymentStatus as PS ON O.PaymentStatusId = PS.Id "
                        + "Where C.Name LIKE ? "
                        + "ORDER BY O.CreateDate DESC "
                        + "END "
                        + "ELSE "
                        + "BEGIN "
                        + "SELECT O.Id, O.CustomerId, O.TotalPrice, O.CreateDate, PM.Name as 'PaymentMethod', PS.Name as 'PaymentStatus', C.Name, C.Address "
                        + "FROM dbo.Orders as O INNER JOIN dbo.Customer as C ON O.CustomerId = C.Id "
                        + "INNER JOIN dbo.PaymentMethod as PM ON O.PaymentMethodId = PM.Id "
                        + "INNER JOIN dbo.PaymentStatus as PS ON O.PaymentStatusId = PS.Id "
                        + "Where C.Name LIKE ? AND O.Id = @orderId "
                        + "ORDER BY O.CreateDate DESC "
                        + "END; ";

                stm = con.prepareStatement(sql);
                stm.setInt(1, orderId);
                //selecting orders in all order.
                stm.setString(2, "%" + cusName + "%");
                //end
                //selecting products in specify order.
                stm.setString(3, "%" + cusName + "%");
                //end
                rs = stm.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    int customerId = rs.getInt(2);
                    float totalPrice = rs.getFloat(3);
                    Timestamp createDate = rs.getTimestamp(4);
                    String paymentMethod = rs.getString(5);
                    String paymentStatus = rs.getString(6);
                    String customerName = rs.getString(7);
                    String address = rs.getString(8);

                    OrderDTO dto = new OrderDTO(id, customerId, customerName, DBHelper.roundTo2Decimal(totalPrice), DBHelper.TimeFormat(createDate), address, paymentMethod, paymentStatus, null);

                    if (this.listOrders == null) {
                        this.listOrders = new ArrayList<>();
                    }
                    this.listOrders.add(dto);

                }
            }
        } finally {
            if (con != null) {
                con.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

}
