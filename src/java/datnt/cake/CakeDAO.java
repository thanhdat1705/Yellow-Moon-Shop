/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.cake;

import datnt.ultis.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author NTD
 */
public class CakeDAO implements Serializable {

    private List<CakeDTO> listCakes = new ArrayList<>();

    public List<CakeDTO> getListCakes() {
        return listCakes;
    }

    public void searchCakes(int pageIndex, int pageSize, String searchValue, int categoryId, int quantity, float minPrice, float maxPrice, boolean status)
            throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int start = (pageIndex - 1) * pageSize + 1;
        int end = pageIndex * pageSize;

        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "DECLARE "
                        + "@num int = ?; "
                        + "IF @num = 0 "
                        + "BEGIN "
                        + "SELECT Id, Name, Image, Price, Brand, Description, Quantity, Category, CategoryId, CreateDate, ExpirationDate, LastUpdateDate, UserUpdate, Status "
                        + "FROM (Select C.Id, C.Name, C.Image, C.Price, C.Brand, C.Description, C.Quantity, CC.Name as Category, CC.Id as CategoryId, C.CreateDate, C.ExpirationDate, C.LastUpdateDate, C.UserUpdate, C.Status, "
                        + "ROW_NUMBER() OVER (ORDER BY C.CreateDate DESC) AS RowNum "
                        + "FROM dbo.Cake as C INNER JOIN dbo.CakeCategory as CC ON C.CategoryId = CC.Id "
                        + "Where C.Name like ? "
                        + "AND C.Quantity >= ? "
                        + "AND C.Status = ? "
                        + "AND C.Price BETWEEN ? AND ?) AS List "
                        + "Where RowNum >= ? and RowNum <= ? "
                        + "END "
                        + "ELSE "
                        + "BEGIN "
                        + "SELECT Id, Name, Image, Price, Brand, Description, Quantity, Category, CategoryId, CreateDate, ExpirationDate, LastUpdateDate, UserUpdate, Status "
                        + "FROM (Select C.Id, C.Name, C.Image, C.Price, C.Brand, C.Description, C.Quantity, CC.Name as Category, CC.Id as CategoryId, C.CreateDate, C.ExpirationDate, C.LastUpdateDate, C.UserUpdate, C.Status, "
                        + "ROW_NUMBER() OVER (ORDER BY C.CreateDate DESC) AS RowNum "
                        + "FROM dbo.Cake as C INNER JOIN dbo.CakeCategory as CC ON C.CategoryId = CC.Id "
                        + "Where C.Name like ? "
                        + "AND C.CategoryId = @num "
                        + "AND C.Quantity >= ? "
                        + "AND C.Status = ? "
                        + "AND C.Price BETWEEN ? AND ?) AS List "
                        + "Where RowNum >= ? and RowNum <= ? "
                        + "END; ";

                stm = con.prepareStatement(sql);
                stm.setInt(1, categoryId);
                //selecting products in all category.
                stm.setString(2, "%" + searchValue + "%");
                stm.setInt(3, quantity);
                stm.setBoolean(4, status);
                stm.setFloat(5, minPrice);
                stm.setFloat(6, maxPrice);
                stm.setInt(7, start);
                stm.setInt(8, end);
                //end
                //selecting products in specify category.
                stm.setString(9, "%" + searchValue + "%");
                stm.setInt(10, quantity);
                stm.setBoolean(11, status);
                stm.setFloat(12, minPrice);
                stm.setFloat(13, maxPrice);
                stm.setInt(14, start);
                stm.setInt(15, end);
                //end
                rs = stm.executeQuery();
                while (rs.next()) {
                    int id = Integer.parseInt(rs.getString("Id"));
                    String name = rs.getString("Name");
                    String image = rs.getString("Image");
                    float price = rs.getFloat("Price");
                    String brand = rs.getString("Brand");
                    String description = rs.getString("Description");
                    int cQuantity = rs.getInt("Quantity");
                    int cCategoryId = rs.getInt("CategoryId");
                    String category = rs.getString("Category");
                    Timestamp expirationDate = rs.getTimestamp("ExpirationDate");
                    Timestamp createDate = rs.getTimestamp("CreateDate");
                    Timestamp lastUpdateDate = rs.getTimestamp("LastUpdateDate");
                    String userUpdate = rs.getString("UserUpdate");
                    boolean cStatus = rs.getBoolean("Status");
                    CakeDTO dto = new CakeDTO(id, name, image, price, brand, description, category, cQuantity, DBHelper.TimeFormat(createDate), DBHelper.TimeFormat(lastUpdateDate), DBHelper.TimeFormat(expirationDate), userUpdate, cStatus, cCategoryId);
                    if (this.listCakes == null) {
                        this.listCakes = new ArrayList<>();
                    }
                    this.listCakes.add(dto);

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

    public int totalPage(int totalCakesPerPage, String searchValue, int categoryId, int quantity, float minPrice, float maxPrice, boolean status)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        float totalCakes = 0;

        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql;
                if (categoryId == 0) {
                    sql = "Select count (RowNum) "
                            + "From "
                            + "(Select Cake.Name, ROW_NUMBER() OVER (ORDER BY Cake.CreateDate DESC) AS RowNum "
                            + "From Cake "
                            + "Where Cake.Name like ? "
                            + "AND Cake.Quantity >= ? "
                            + "And Cake.Status = ? "
                            + "AND Cake.Price BETWEEN ? AND ?) AS list ";
                } else {
                    sql = "Select count (RowNum) "
                            + "From "
                            + "(Select Cake.Name, ROW_NUMBER() OVER (ORDER BY Cake.CreateDate DESC) AS RowNum "
                            + "From Cake "
                            + "Where Cake.Name like ? "
                            + "AND Cake.Quantity >= ? "
                            + "And Cake.Status = ? "
                            + "AND Cake.Price BETWEEN ? AND ? "
                            + "And Cake.CategoryId = ?) AS list ";
                }
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                stm.setInt(2, quantity);
                stm.setBoolean(3, status);
                stm.setFloat(4, minPrice);
                stm.setFloat(5, maxPrice);
                if (categoryId != 0) {
                    stm.setInt(6, categoryId);
                }
                rs = stm.executeQuery();
                if (rs.next()) {
                    totalCakes = (float) rs.getInt(1);

                    if (totalCakes > 0) {
                        return (int) Math.ceil(totalCakes / totalCakesPerPage);
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

    public int insertCake(String name, String image, float price, String brand, String description, int categoryId,
            int quantity, Timestamp createDate, Timestamp lastUpdateDate, Timestamp expirationDate, String userUpdate, boolean status)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "INSERT INTO Cake(Name, Image, Price, Brand, Description, CategoryId, Quantity, CreateDate, LastUpdateDate, ExpirationDate, UserUpdate, Status) "
                        + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?) "
                        + "SELECT SCOPE_IDENTITY() AS CakeId ";

                stm = con.prepareStatement(sql);
                stm.setString(1, name);
                stm.setString(2, image);
                stm.setFloat(3, price);
                stm.setString(4, brand);
                stm.setString(5, description);
                stm.setInt(6, categoryId);
                stm.setInt(7, quantity);
                stm.setTimestamp(8, createDate);
                stm.setTimestamp(9, lastUpdateDate);
                stm.setTimestamp(10, expirationDate);
                stm.setString(11, userUpdate);
                stm.setBoolean(12, status);
                rs = stm.executeQuery();
                if (rs.next()) {
                    int cakeId = (int) rs.getInt(1);

                    return cakeId;
                }
//                int row = stm.executeUpdate();
//                if (row > 0) {
//                    return row;
//                }
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

    public CakeDTO viewCake(int cakeId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "Select C.Name, C.Image, C.Price, C.Brand, C.Description, CC.Name as Category, C.Quantity, C.CreateDate, C.LastUpdateDate, "
                        + "C.ExpirationDate, C.UserUpdate, C.Status, C.Id, CC.Id as CategoryId "
                        + "FROM dbo.Cake as C INNER JOIN dbo.CakeCategory as CC ON C.CategoryId = CC.Id "
                        + "Where C.Id = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, cakeId);

                rs = stm.executeQuery();
                if (rs.next()) {
                    String name = rs.getString(1);
                    String image = rs.getString(2);
                    float price = rs.getFloat(3);
                    String brand = rs.getString(4);
                    String description = rs.getString(5);
                    String category = rs.getString(6);
                    int quantity = rs.getInt(7);
                    Timestamp createDate = rs.getTimestamp(8);
                    Timestamp lastUpdateDate = rs.getTimestamp(9);
                    Timestamp expirationDate = rs.getTimestamp(10);
                    String userUpdate = rs.getString(11);
                    boolean status = rs.getBoolean(12);
                    int id = rs.getInt(13);
                    int categoryId = rs.getInt(14);
                    CakeDTO dto = new CakeDTO(id, name, image, price, brand, description, category, quantity, DBHelper.TimeFormat(createDate), DBHelper.TimeFormat(lastUpdateDate), DBHelper.TimeFormat(expirationDate), userUpdate, status, categoryId);
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

    public int updateCake(int pk, String name, String image, float price, String brand, String description, int category, int quantity,
            Timestamp createDate, Timestamp lastUpdateDate, Timestamp expirationDate, String userUpdate, boolean status)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "Update Cake "
                        + "SET Name = ?, Image = ?, Price = ?, Brand = ?, Description = ?, CategoryId = ?, Quantity = ?, CreateDate = ?, LastUpdateDate = ?, ExpirationDate = ?, UserUpdate = ?, Status = ? "
                        + "Where Id = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, name);
                stm.setString(2, image);
                stm.setFloat(3, price);
                stm.setString(4, brand);
                stm.setString(5, description);
                stm.setInt(6, category);
                stm.setInt(7, quantity);
                stm.setTimestamp(8, createDate);
                stm.setTimestamp(9, lastUpdateDate);
                stm.setTimestamp(10, expirationDate);
                stm.setString(11, userUpdate);
                stm.setBoolean(12, status);
                stm.setInt(13, pk);

                int row = stm.executeUpdate();
                if (row > 0) {
                    return row;
                }
            }
        } finally {
            if (con != null) {
                con.close();
            }
            if (stm != null) {
                stm.close();
            }
        }
        return 0;
    }

    public boolean checkStatus(int cakeId, boolean status) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT C.Id "
                        + "FROM dbo.Cake as C "
                        + "Where C.Id = ? AND C.Status = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, cakeId);
                stm.setBoolean(2, status);

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

    public void updateCakeAfterOrder(int cakeId, int remain) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "UPDATE Cake SET Quantity = ? "
                        + "WHERE CAKE.Id = ? ";

                stm = con.prepareStatement(sql);
                stm.setInt(1, remain);
                stm.setInt(2, cakeId);

                int row = stm.executeUpdate();
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

}
