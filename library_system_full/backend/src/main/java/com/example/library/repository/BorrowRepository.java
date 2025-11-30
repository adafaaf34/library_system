package com.example.library.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;

@Repository
public class BorrowRepository {
    @Autowired
    private JdbcTemplate jdbc;

    public void callBorrowProcedure(Long userId, Long inventoryId) {
        jdbc.execute((Connection con) -> {
            CallableStatement cs = con.prepareCall("{CALL sp_borrow_book(?, ?)}");
            cs.setLong(1, userId);
            cs.setLong(2, inventoryId);
            cs.execute();
            cs.close();
            return null;
        });
    }

    public void callReturnProcedure(Long userId, Long inventoryId) {
        jdbc.execute((Connection con) -> {
            CallableStatement cs = con.prepareCall("{CALL sp_return_book(?, ?)}");
            cs.setLong(1, userId);
            cs.setLong(2, inventoryId);
            cs.execute();
            cs.close();
            return null;
        });
    }
}
