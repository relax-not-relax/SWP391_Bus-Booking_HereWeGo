/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Notification;
import utils.DBUtils;

/**
 *
 * @author BAOTRAM
 */
public class NotificationManager {

    private static final String ADD = "INSERT INTO Notification([userId],[message],[date]) VALUES(?,?,CONVERT(datetime,CURRENT_TIMESTAMP))";
    private static final String GET_LIST = "SELECT * FROM [Notification]";
    private static final String SORT = "SELECT * FROM Notification ORDER BY date";
    private static final String SORT_DESC = "SELECT * FROM Notification ORDER BY date desc";

    public static boolean add(long userId, String message) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        boolean add = false;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                pst = cn.prepareStatement(ADD);
                pst.setLong(1, userId);
                pst.setString(2, message);
                pst.executeUpdate();
                add = true;
            }
        } catch (Exception e) {
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return add;
    }

    public static ArrayList<Notification> getList() throws SQLException {
        ArrayList<Notification> list = new ArrayList<>();
        Notification no = null;
        Connection conn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                psm = conn.prepareStatement(GET_LIST);
                rs = psm.executeQuery();
                while (rs != null && rs.next()) {
                    no = new Notification(rs.getLong(1), rs.getLong(2), rs.getString(3), rs.getString(4));
                    list.add(no);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (psm != null) {
                psm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public static ArrayList<Notification> sort() throws SQLException {
        ArrayList<Notification> list = new ArrayList<>();
        Notification no = null;
        Connection conn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                psm = conn.prepareStatement(SORT);
                rs = psm.executeQuery();

                while (rs != null && rs.next()) {
                    no = new Notification(rs.getLong(1), rs.getLong(2), rs.getString(3), rs.getString(4));
                    list.add(no);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (psm != null) {
                psm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }
    
    public static ArrayList<Notification> sortDesc() throws SQLException {
        ArrayList<Notification> list = new ArrayList<>();
        Notification no = null;
        Connection conn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                psm = conn.prepareStatement(SORT_DESC);
                rs = psm.executeQuery();

                while (rs != null && rs.next()) {
                    no = new Notification(rs.getLong(1), rs.getLong(2), rs.getString(3), rs.getString(4));
                    list.add(no);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (psm != null) {
                psm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }
}
