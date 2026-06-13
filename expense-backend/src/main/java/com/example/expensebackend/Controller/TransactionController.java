package com.example.expensebackend.Controller;

import com.example.expensebackend.Entity.Transaction;
import com.example.expensebackend.Service.TransactionService;
import com.example.expensebackend.dto.Request.TransactionRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/user/{email}")
    public Transaction createTransaction(
            @PathVariable String email,
            @RequestBody TransactionRequest request
    ) {
        return transactionService.createTransaction(email, request);
    }

    @GetMapping("/user/{email}")
    public List<Transaction> getTransactionsByEmail(@PathVariable String email) {
        return transactionService.getTransactionsByEmail(email);
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @PutMapping("/{id}")
    public Transaction updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        return transactionService.updateTransaction(id, transaction);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }
}
