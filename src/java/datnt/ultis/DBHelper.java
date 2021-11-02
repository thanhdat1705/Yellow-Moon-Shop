/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.ultis;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author NTD
 */
public class DBHelper {

    public static Connection getConnect() throws NamingException, SQLException {
        Context context = new InitialContext();
        Context tomcatContext = (Context) context.lookup("java:comp/env");
        DataSource ds = (DataSource) tomcatContext.lookup("ResourceSharing");
        return ds.getConnection();
    }

    public static String TimeFormat(Timestamp date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }

    public static Timestamp getTime() {
        Date date = new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);
        return ts;
    }

    public static Timestamp convertStringToTimestamp(String date) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date parsedDate = formatter.parse(date);
        Timestamp ts = new Timestamp(parsedDate.getTime());
        return ts;
    }

    public static float roundTo2Decimal(float number) {
        BigDecimal numberBigDecimal = new BigDecimal(Float.toString(number));
        numberBigDecimal = numberBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        return numberBigDecimal.floatValue();
    }

    public static String encryptPass(String base) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(base.getBytes("UTF-8"));
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

}
