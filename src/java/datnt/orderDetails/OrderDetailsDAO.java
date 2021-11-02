/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.orderDetails;

import datnt.order.OrderDTO;
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
public class OrderDetailsDAO implements Serializable {

    private List<OrderDetailsDTO> listOrderDetails = null;

    public List<OrderDetailsDTO> getListOrderDetails() {
        return listOrderDetails;
    }

    public void insertOrderDetail(int orderId, int cakeId, int quantity)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnect();

            if (con != null) {
                String sql = "INSERT INTO OrderDetails(OrderId, CakeId, Quantity) "
                        + "VALUES (?,?,?) ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, orderId);
                stm.setInt(2, cakeId);
                stm.setInt(3, quantity);

                int row = stm.executeUpdate();
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
    }

    public void searchOrderDetail(int orderId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT O.OrderId, C.Id AS 'CakeId', C.Name AS 'CakeName', C.Image, C.Brand, C.ExpirationDate, O.Quantity, C.Price "
                        + "FROM dbo.OrderDetails AS O INNER JOIN dbo.Cake AS C ON O.CakeId = C.Id "
                        + "Where O.OrderId = ? ";

                stm = con.prepareStatement(sql);
                stm.setInt(1, orderId);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    int cakeId = rs.getInt(2);
                    String cakeName = rs.getString(3);
                    String image = rs.getString(4);
                    String brand = rs.getString(5);
                    Timestamp expirationDate = rs.getTimestamp(6);
                    int quantity = rs.getInt(7);
                    float price = rs.getFloat(8);
                    
                    OrderDetailsDTO dto = new OrderDetailsDTO(orderId, cakeId, cakeName, image, brand, DBHelper.TimeFormat(expirationDate), quantity, price);

                    if (this.listOrderDetails == null) {
                        this.listOrderDetails = new ArrayList<>();
                    }
                    this.listOrderDetails.add(dto);

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
