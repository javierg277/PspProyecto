package org.example.model.DAO;

import org.example.connection.ConnectionMySQL;
import org.example.model.domain.User;
import org.example.util.AppData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class UserDAO implements AutoCloseable {

    private final static String FINDALL = "SELECT * FROM user";
    private final static String FINDBYUSERNAME = "SELECT * FROM user WHERE name=?";
    private final static String FINDBYID = "SELECT * FROM user WHERE id=?";
    private final static String INSERT = "INSERT INTO user (name,password,money) VALUES (?,?,?)";
    private final static String UPDATE = "UPDATE user SET money=? WHERE id=?";

    private Connection conn;

    public UserDAO(Connection conn){
        this.conn = conn;
    }
    public UserDAO() {
        this.conn = ConnectionMySQL.getConnect();
    }

    /**
     * Method that finds all users stored at the database.
     * @return a Set of all Users stored at the database.
     */
    public synchronized Set<User> findAll() throws SQLException {
        Set<User> result = new HashSet<User>();
        try(PreparedStatement pst = this.conn.prepareStatement(FINDALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    User u = new User();
                    u.setId(res.getInt("id"));
                    u.setName(res.getString("name"));
                    u.setPassword(res.getString("password"));
                    u.setMoney(res.getDouble("money"));
                    result.add(u);
                }
            }
        }
        return result;
    }

    /**
     * Method that finds a user stored at the database.
     * @param param , the email/username to find.
     * @return the User found/null if not found.
     */
    public synchronized User find(String param) throws SQLException {
        User result = new User();
            try (PreparedStatement pst = this.conn.prepareStatement(FINDBYUSERNAME)) {
                pst.setString(1, param);
                try (ResultSet res = pst.executeQuery()) {
                    if (res.next()) {
                        result.setId(res.getInt("id"));
                        result.setName(res.getString("name"));
                        result.setPassword(res.getString("password"));
                        result.setMoney(res.getDouble("money"));
                        return result;
                    }
                }
            }
        return null;
    }

    /**
     * Method that finds a user stored at the database.
     * @param id , the id to find.
     * @return the User found/null if not found.
     */
    public synchronized User find(int id) throws SQLException {
        User result = new User();
        try (PreparedStatement pst = this.conn.prepareStatement(FINDBYID)) {
            pst.setInt(1, id);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    result.setId(res.getInt("id"));
                    result.setName(res.getString("name"));
                    result.setPassword(res.getString("password"));
                    result.setMoney(res.getDouble("money"));
                    return result;
                }
            }
        }
        return null;
    }



    /**
     * Method that stores a User at the database.
     * @param entity , the User to save.
     * @return the stored User.
     */
    public synchronized User save(Object entity) throws SQLException {
        User u = find(((User)entity).getName());

        if(u == null) {
            try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                pst.setString(1, ((User) entity).getName());
                pst.setString(2, AppData.getPa().hash(((User) entity).getPassword()));
                pst.setDouble(3, ((User) entity).getMoney());
                pst.executeUpdate();
            }
        }

        return (User)entity;
    }

    public synchronized User updateMoney(int id, double money) throws SQLException {
        User u = find(id);

        if(u != null) {
            try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                pst.setDouble(1, (money));
                pst.setInt(2, id);
                pst.executeUpdate();
            }
        }
        u = find(id);

        return u;
    }



    @Override
    public void close() throws Exception {

    }
}
