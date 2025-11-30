package com.example.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private JdbcTemplate jdbc;

    @GetMapping("/inventory")
    public List<Map<String, Object>> getInventory() {
        return jdbc.queryForList(
            "SELECT i.inventory_id, i.isbn, i.status, b.name, b.author, b.introduction " +
            "FROM inventory i " +
            "JOIN book b ON i.isbn = b.isbn"
        );
    }
}