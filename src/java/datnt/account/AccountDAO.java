/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.account;

import datnt.ultis.DBHelper;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author NTD
 */
public class AccountDAO implements Serializable {

    private AccountDTO dto;

    public AccountDTO getAccount() {
        return dto;
    }

    public boolean checkLogin(String userId, String password)
            throws SQLException, NamingException, NoSuchAlgorithmException, UnsupportedEncodingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnect();

            if (con != null) {
                String sql = "Select Id, Password, Name, RoleId "
                        + "From Account "
                        + "Where Id = ? And Password = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, userId);
                stm.setString(2, DBHelper.encryptPass(password));
                rs = stm.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("Name");
                    int role = rs.getInt("RoleId");
                    this.dto = new AccountDTO(userId, name, password, role);

                    return true;
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
        return false;
    }

    public boolean isDuplicateUserId(String userId)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnect();

            if (con != null) {
                String sql = "Select Id "
                        + "From Account "
                        + "Where Id = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, userId);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return true;
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
        return false;
    }

    public String insertAccount(String userId, String password, String name, int roleId)
            throws SQLException, NamingException, NoSuchAlgorithmException, UnsupportedEncodingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnect();

            if (con != null) {
                String sql = "INSERT INTO Account(Id, Password, Name, RoleId) "
                        + "OUTPUT INSERTED.Id "
                        + "VALUES (?,?,?,?) ";
                stm = con.prepareStatement(sql);
                stm.setString(1, userId);
                stm.setString(2, DBHelper.encryptPass(password));
                stm.setString(3, name);
                stm.setInt(4, roleId);
                rs = stm.executeQuery();

                if (rs.next()) {
                    String id = rs.getString(1);
                    return id;
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
        return null;
    }

}
