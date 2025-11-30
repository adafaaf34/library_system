package com.example.library.repository;

import com.example.library.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public boolean existsByPhone(String phone) {
        Integer cnt = jdbc.queryForObject(
            "SELECT COUNT(*) FROM users WHERE phone_number = ?",
            Integer.class,
            phone
        );
        return cnt != null && cnt > 0;
    }

    public void insertUser(String phone, String passwordHash, String salt, String userName) {
        jdbc.update(
            "INSERT INTO users (phone_number, password_hash, salt, user_name, registration_time) VALUES (?, ?, ?, ?, NOW())",
            phone, passwordHash, salt, userName
        );
    }

    public User findByPhone(String phone) {
        List<User> list = jdbc.query(
            "SELECT user_id, phone_number, password_hash, salt, user_name FROM users WHERE phone_number = ?",
            (rs, rowNum) -> mapUser(rs),
            phone
        );
        return list.isEmpty() ? null : list.get(0);
    }

    public User findById(Long userId) {
        List<User> list = jdbc.query(
            "SELECT user_id, phone_number, password_hash, salt, user_name FROM users WHERE user_id = ?",
            (rs, rowNum) -> mapUser(rs),
            userId
        );
        return list.isEmpty() ? null : list.get(0);
    }

    public void updateLastLogin(Long userId) {
        jdbc.update("UPDATE users SET last_login_time = NOW() WHERE user_id = ?", userId);
    }

    private User mapUser(ResultSet rs) throws SQLException {
        User u = new User();
        u.setUserId(rs.getLong("user_id"));
        u.setPhoneNumber(rs.getString("phone_number"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setSalt(rs.getString("salt"));
        u.setUserName(rs.getString("user_name"));
        return u;
    }
}
