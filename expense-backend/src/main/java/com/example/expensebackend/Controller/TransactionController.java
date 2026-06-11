package com.example.expensebackend.Controller;

import com.example.expensebackend.Entity.Transaction;
import com.example.expensebackend.Service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Bao cho Spring biet class nay la REST controller va tra ve JSON.
@RequestMapping("/api/transactions") // Tat ca API giao dich bat dau bang /api/transactions.
public class TransactionController {
    private final TransactionService transactionService; // Service xu ly logic lien quan giao dich.

    // Constructor injection: Spring tu truyen TransactionService vao controller.
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService; // Luu service vao field de cac API ben duoi su dung.
    }

    @PostMapping("/user/{email}/category/{categoryId}") // Tao giao dich cho user va danh muc cu the.
    public Transaction createTransaction(
            @PathVariable String email, // Lay email user tu URL.
            @PathVariable Long categoryId, // Lay id danh muc tu URL.
            @RequestBody Transaction transaction // Lay JSON body va map thanh Transaction.
    ) {
        return transactionService.createTransaction(email, categoryId, transaction); // Goi service de gan user/category va luu.
    }

    @GetMapping("/user/{email}") // Lay danh sach giao dich cua user theo email.
    public List<Transaction> getTransactionsByEmail(@PathVariable String email) {
        return transactionService.getTransactionsByEmail(email); // Goi service tim user roi lay giao dich cua user do.
    }

    @GetMapping("/{id}") // Lay chi tiet giao dich theo id.
    public Transaction getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id); // Goi service tim giao dich theo khoa chinh id.
    }

    @PutMapping("/{id}") // Cap nhat giao dich theo id.
    public Transaction updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        return transactionService.updateTransaction(id, transaction); // Goi service cap nhat cac field duoc phep sua.
    }

    @DeleteMapping("/{id}") // Xoa giao dich theo id.
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id); // Goi service xoa giao dich trong database.
    }
}
