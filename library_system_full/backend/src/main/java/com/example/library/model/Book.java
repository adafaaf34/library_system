package com.example.library.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String author;

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
    private Inventory inventory;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<BorrowingRecord> borrowingRecords;

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public Inventory getInventory() { return inventory; }
    public void setInventory(Inventory inventory) { this.inventory = inventory; }
    public Set<BorrowingRecord> getBorrowingRecords() { return borrowingRecords; }
    public void setBorrowingRecords(Set<BorrowingRecord> borrowingRecords) { this.borrowingRecords = borrowingRecords; }
}
