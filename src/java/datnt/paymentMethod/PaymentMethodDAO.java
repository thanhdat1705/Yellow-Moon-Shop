/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.paymentMethod;

import datnt.ultis.DBHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author NTD
 */
public class PaymentMethodDAO {
    
    private List<PaymentMethodDTO> listPaymentMethod = new ArrayList<>();;

    public List<PaymentMethodDTO> getListPaymentMethod() {
        return listPaymentMethod;
    }
    
    public void getPaymentMethod() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try {
            con = DBHelper.getConnect();
            String sql = "Select Id, Name "
                    + "From PaymentMethod ";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                PaymentMethodDTO dto = new PaymentMethodDTO(id, name);
                if (this.listPaymentMethod == null) {
                    this.listPaymentMethod = new ArrayList<>();

                }
                this.listPaymentMethod.add(dto);

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
