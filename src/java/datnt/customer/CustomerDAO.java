/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.customer;

import datnt.ultis.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.NamingException;

/**
 *
 * @author NTD
 */
public class CustomerDAO implements Serializable {

    public int insertCustomer(String accountId, String name, String phone, String address)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnect();

            if (con != null) {
                String sql = "INSERT INTO Customer(AccountId, Name, Phone, Address) "
                        + "VALUES (?,?,?,?) ";
                stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stm.setString(1, accountId);
                stm.setString(2, name);
                stm.setString(3, phone);
                stm.setString(4, address);

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

    public CustomerDTO getCustomerByCusId(int customerId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT C.Id, C.Name, C.AccountId, C.Phone, C.Address "
                        + "FROM dbo.Customer as C "
                        + "Where C.Id = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, customerId);

                rs = stm.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    String accountId = rs.getString(3);
                    String phone = rs.getString(4);
                    String address = rs.getString(5);
                    CustomerDTO dto = new CustomerDTO(id, accountId, name, phone, address);
                    return dto;
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
        return null;
    }

    public CustomerDTO getCustomerByAccId(String accId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT C.Id, C.Name, C.AccountId, C.Phone, C.Address "
                        + "FROM dbo.Customer as C "
                        + "Where C.AccountId = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, accId);

                rs = stm.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    String accountId = rs.getString(3);
                    String phone = rs.getString(4);
                    String address = rs.getString(5);
                    CustomerDTO dto = new CustomerDTO(id, accountId, name, phone, address);
                    return dto;
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
        return null;
    }

}
