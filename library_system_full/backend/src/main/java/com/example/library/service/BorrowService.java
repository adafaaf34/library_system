package com.example.library.service;

import com.example.library.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BorrowService {
    @Autowired private BorrowRepository borrowRepo;

    @Transactional
    public void borrow(Long userId, Long inventoryId) {
        borrowRepo.callBorrowProcedure(userId, inventoryId);
    }

    @Transactional
    public void returnBook(Long userId, Long inventoryId) {
        borrowRepo.callReturnProcedure(userId, inventoryId);
    }
}
