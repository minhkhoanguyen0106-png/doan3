package com.example.expensebackend.Service;

import com.example.expensebackend.Entity.Category;
import com.example.expensebackend.Entity.Transaction;
import com.example.expensebackend.Entity.User;
import com.example.expensebackend.Repository.CategoryRepository;
import com.example.expensebackend.Repository.TransactionRepository;
import com.example.expensebackend.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Bao cho Spring biet day la class chua logic xu ly giao dich.
public class TransactionService {
    private final TransactionRepository transactionRepository; // Repository thao tac bang transactions.
    private final UserRepository userRepository; // Repository dung de tim user theo email.
    private final CategoryRepository categoryRepository; // Repository dung de tim category theo id.

    // Constructor injection: Spring truyen cac repository can dung vao service.
    public TransactionService(
            TransactionRepository transactionRepository,
            UserRepository userRepository,
            CategoryRepository categoryRepository
    ) {
        this.transactionRepository = transactionRepository; // Luu TransactionRepository vao field.
        this.userRepository = userRepository; // Luu UserRepository vao field.
        this.categoryRepository = categoryRepository; // Luu CategoryRepository vao field.
    }

    // Tao giao dich moi cho user co email va category co categoryId.
    public Transaction createTransaction(String email, Long categoryId, Transaction transaction) {
        User user = userRepository.findByEmail(email) // Tim user theo email duoc truyen tu URL.
                .orElseThrow(() -> new RuntimeException("User not found")); // Khong co user thi bao loi.
        Category category = categoryRepository.findById(categoryId) // Tim danh muc theo id tren URL.
                .orElseThrow(() -> new RuntimeException("Category not found")); // Khong co category thi bao loi.
        transaction.setUser(user); // Gan user cho giao dich de biet giao dich thuoc ai.
        transaction.setCategory(category); // Gan category cho giao dich de biet giao dich thuoc danh muc nao.
        return transactionRepository.save(transaction); // Luu giao dich vao database va tra ve giao dich da luu.
    }

    // Lay danh sach giao dich cua mot user theo email.
    public List<Transaction> getTransactionsByEmail(String email) {
        User user = userRepository.findByEmail(email) // Tim user truoc vi repository dang query theo User object.
                .orElseThrow(() -> new RuntimeException("User not found")); // Khong co user thi bao loi.
        return transactionRepository.findByUser(user); // Lay tat ca giao dich co user_id cua user nay.
    }

    // Lay mot giao dich theo id.
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id) // Tim giao dich theo khoa chinh id.
                .orElseThrow(() -> new RuntimeException("Transaction not found")); // Khong thay thi bao loi.
    }

    // Cap nhat thong tin giao dich theo id.
    public Transaction updateTransaction(Long id, Transaction newTransaction) {
        Transaction transaction = transactionRepository.findById(id) // Lay giao dich cu trong database.
                .orElseThrow(() -> new RuntimeException("Transaction not found")); // Khong thay thi bao loi.
        transaction.setTitle(newTransaction.getTitle()); // Cap nhat tieu de giao dich.
        transaction.setAmount(newTransaction.getAmount()); // Cap nhat so tien.
        transaction.setType(newTransaction.getType()); // Cap nhat loai thu/chi.
        transaction.setNote(newTransaction.getNote()); // Cap nhat ghi chu.
        transaction.setTransactionDate(newTransaction.getTransactionDate()); // Cap nhat ngay giao dich.
        return transactionRepository.save(transaction); // Luu thay doi va tra ve giao dich moi.
    }

    // Xoa giao dich theo id.
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id); // Goi JPA xoa row co id tuong ung.
    }
}
