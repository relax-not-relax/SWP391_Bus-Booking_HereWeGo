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
import model.User;
import utils.DBUtils;

/**
 *
 * @author baolo
 */
public class UserManager {

    private static final String LOGIN = "SELECT * FROM [User] WHERE phone = ? AND password = ?";
    private static final String LOGIN_GOOGLE = "SELECT * FROM [User] WHERE googleId = ?";
    private static final String REGISTER = "INSERT INTO [User] VALUES(?,?,?,?,?,?, CURRENT_TIMESTAMP)";
    private static final String CHECK_DUPLICATE = "SELECT * FROM [User] WHERE phone = ?";
    private static final String CHANGE_NAME = "UPDATE [User] SET [name] = ? WHERE userId = ?";
    private static final String CHANGE_PASSWORD = "UPDATE [User] SET [password] = ? WHERE userId = ?";
    
    public static User getUserById(long id) throws SQLException {
        Connection cn = null;
        User us = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                String sql = "select * from [User] where userId = ?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setLong(1, id);
                ResultSet rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    us = new User(id, sql, LOGIN, sql, LOGIN, 0, sql, REGISTER);
                }
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
            }
        } catch (Exception e) {

        } finally {
            if (cn != null) {
                cn.close();
            }
        }
        return us;
    }

    public static User checkLogin(String phone, String password) throws SQLException {
        User user = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                pst = cn.prepareStatement(LOGIN);
                pst.setString(1, phone);
                pst.setString(2, password);
                rs = pst.executeQuery();
                if (rs.next()) {
                    user = new User(rs.getInt(1), rs.getNString(2), rs.getNString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return user;
    }

    public static User checkLoginGoogle(String googleId) throws SQLException {
        User user = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                pst = cn.prepareStatement(LOGIN_GOOGLE);
                pst.setString(1, googleId);
                rs = pst.executeQuery();
                if (rs.next()) {
                    user = new User(rs.getInt(1), rs.getNString(2), rs.getNString(3), rs.getString(4), rs.getString(5), rs.getInt(6), "***", rs.getString(8));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return user;
    }

    public static boolean register(User user) throws SQLException {
        Connection con = DBUtils.getConnection();
        if (con != null) {
            PreparedStatement ps = con.prepareStatement(REGISTER);
            ps.setString(1, user.getName());
            ps.setString(2, user.getGoogleId());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getAvtLink());
            ps.setInt(5, 2);
            ps.setString(6, user.getPassword());
            if (ps.executeUpdate() > 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkDuplicate(String phone) throws SQLException {
        Connection con = DBUtils.getConnection();
        if (con != null) {
            PreparedStatement ps = con.prepareStatement(CHECK_DUPLICATE);
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
        }
        return false;
    }

    public static int countCustomer() throws SQLException {
        int count = 0;
        Connection cn = DBUtils.getConnection();
        if (cn != null) {
            PreparedStatement pst = cn.prepareStatement("SELECT COUNT(u.userId) FROM [User] u WHERE u.roleId = 2");
            ResultSet rs = pst.executeQuery();
            if (rs != null && rs.next()) {
                count = rs.getInt(1);
            }
            cn.close();
        }
        return count;
    }

    public static ArrayList<User> getListCustomer() throws SQLException {
        Connection cn = DBUtils.getConnection();
        ArrayList<User> list = new ArrayList<>();
        User us = null;
        if (cn != null) {
            PreparedStatement pst = cn.prepareStatement("SELECT * FROM [User] WHERE roleId = 2");
            ResultSet rs = pst.executeQuery();
            while (rs != null && rs.next()) {
                us = new User(rs.getInt(1), rs.getNString(2), rs.getNString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8));
                list.add(us);
            }
            cn.close();

        }
        return list;
    }

    public static void deleteCustomer(long id) throws SQLException{
        Connection cn = DBUtils.getConnection();
        if (cn!= null){
            PreparedStatement pst = cn.prepareStatement("DELETE FROM [User] WHERE [User].userId = ?");
            pst.setLong(1, id);
            pst.executeUpdate();
            cn.close();
        }
    }
    
    public static boolean changeName(long userId, String name) throws Exception{
        Connection cn = null;
        PreparedStatement pst = null;
        boolean change = false;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                pst = cn.prepareStatement(CHANGE_NAME);
                pst.setString(1, name);
                pst.setLong(2, userId);
                pst.executeUpdate();
                change = true;              
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return change;
    }
    
    public static boolean changePasword(long userId, String password) throws Exception{
        Connection cn = null;
        PreparedStatement pst = null;
        boolean change = false;
        try {
            cn = DBUtils.getConnection();
            if (cn != null) {
                pst = cn.prepareStatement(CHANGE_PASSWORD);
                pst.setString(1, password);
                pst.setLong(2, userId);
                pst.executeUpdate();
                change = true;              
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return change;
    }
}
