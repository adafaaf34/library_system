package com.example.library.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    // 主鍵
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;  // 保留原本名稱

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String passwordHash;

    private String salt;

    private String userName;

    // 一對多關聯 BorrowingRecord
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BorrowingRecord> borrowingRecords;

    // ===== Getters & Setters =====
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getSalt() { return salt; }
    public void setSalt(String salt) { this.salt = salt; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Set<BorrowingRecord> getBorrowingRecords() { return borrowingRecords; }
    public void setBorrowingRecords(Set<BorrowingRecord> borrowingRecords) { this.borrowingRecords = borrowingRecords; }
}
