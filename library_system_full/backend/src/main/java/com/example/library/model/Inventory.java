package com.example.library.model;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int totalCopies;  // 總書數
    private int availableCopies;  // 可借書數量

    @OneToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }
    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
}
