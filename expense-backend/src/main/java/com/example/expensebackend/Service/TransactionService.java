package com.example.expensebackend.Service;

import com.example.expensebackend.Entity.Category;
import com.example.expensebackend.Entity.Transaction;
import com.example.expensebackend.Entity.User;
import com.example.expensebackend.Repository.CategoryRepository;
import com.example.expensebackend.Repository.TransactionRepository;
import com.example.expensebackend.Repository.UserRepository;
import com.example.expensebackend.dto.Request.TransactionRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public TransactionService(
            TransactionRepository transactionRepository,
            UserRepository userRepository,
            CategoryRepository categoryRepository
    ) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public Transaction createTransaction(String email, TransactionRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setCategory(category);
        transaction.setTitle(request.getTitle());
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setNote(request.getNote());
        transaction.setTransactionDate(request.getTransactionDate());

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return transactionRepository.findByUser(user);
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public Transaction updateTransaction(Long id, Transaction newTransaction) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        transaction.setTitle(newTransaction.getTitle());
        transaction.setAmount(newTransaction.getAmount());
        transaction.setType(newTransaction.getType());
        transaction.setNote(newTransaction.getNote());
        transaction.setTransactionDate(newTransaction.getTransactionDate());
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
