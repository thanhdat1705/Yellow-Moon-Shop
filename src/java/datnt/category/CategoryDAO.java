/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.category;

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
public class CategoryDAO implements Serializable{
    
    private List<CategoryDTO> listCategory = new ArrayList<>();;

    public List<CategoryDTO> getListCategory() {
        return listCategory;
    }
    
    public void getCategory() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try {
            con = DBHelper.getConnect();
            String sql = "Select Id, Name "
                    + "From CakeCategory ";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                CategoryDTO dto = new CategoryDTO(id, name);
                if (this.listCategory == null) {
                    this.listCategory = new ArrayList<>();

                }
                this.listCategory.add(dto);

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
