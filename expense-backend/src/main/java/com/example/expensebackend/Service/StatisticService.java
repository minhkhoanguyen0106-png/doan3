package com.example.expensebackend.Service;

import com.example.expensebackend.Entity.Transaction;
import com.example.expensebackend.Entity.TransactionType;
import com.example.expensebackend.Entity.User;
import com.example.expensebackend.Repository.TransactionRepository;
import com.example.expensebackend.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service // Bao cho Spring biet day la service xu ly thong ke.
public class StatisticService {
    private final TransactionRepository transactionRepository; // Repository lay danh sach giao dich.
    private final UserRepository userRepository; // Repository tim user theo email.

    // Constructor injection: Spring truyen repository vao service.
    public StatisticService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository; // Luu TransactionRepository vao field.
        this.userRepository = userRepository; // Luu UserRepository vao field.
    }

    // Tinh tong thu, tong chi va so du cua user.
    public Map<String, Object> getSummary(String email) {
        User user = userRepository.findByEmail(email) // Tim user theo email.
                .orElseThrow(() -> new RuntimeException("User not found")); // Khong thay user thi bao loi.
        List<Transaction> transactions = transactionRepository.findByUser(user); // Lay tat ca giao dich cua user.

        BigDecimal income = transactions.stream() // Tao stream de xu ly danh sach giao dich.
                .filter(t -> t.getType() == TransactionType.INCOME) // Chi giu giao dich thu nhap.
                .map(Transaction::getAmount) // Lay so tien cua tung giao dich.
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Cong tat ca so tien, mac dinh 0 neu khong co.
        BigDecimal expense = transactions.stream() // Tao stream khac de tinh chi tieu.
                .filter(t -> t.getType() == TransactionType.EXPENSE) // Chi giu giao dich chi tieu.
                .map(Transaction::getAmount) // Lay so tien cua tung giao dich.
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Cong tat ca so tien chi.

        Map<String, Object> result = new HashMap<>(); // Tao map de tra JSON linh hoat.
        result.put("totalIncome", income); // Dua tong thu vao response.
        result.put("totalExpense", expense); // Dua tong chi vao response.
        result.put("balance", income.subtract(expense)); // So du = tong thu - tong chi.
        return result; // Tra ket qua thong ke ve controller.
    }

    // Tinh tong thu/chi theo tung thang cua nam duoc chon.
    public Map<String, Object> getMonthlySummary(String email, int year) {
        User user = userRepository.findByEmail(email) // Tim user theo email.
                .orElseThrow(() -> new RuntimeException("User not found")); // Khong thay user thi bao loi.
        List<Transaction> transactions = transactionRepository.findByUser(user); // Lay giao dich cua user.

        Map<Integer, BigDecimal> incomeByMonth = new TreeMap<>(); // TreeMap giup sap xep thang tang dan.
        Map<Integer, BigDecimal> expenseByMonth = new TreeMap<>(); // Luu tong chi theo tung thang.

        for (int i = 1; i <= 12; i++) { // Tao san du 12 thang.
            incomeByMonth.put(i, BigDecimal.ZERO); // Mac dinh thu nhap moi thang la 0.
            expenseByMonth.put(i, BigDecimal.ZERO); // Mac dinh chi tieu moi thang la 0.
        }

        transactions.stream() // Duyet tat ca giao dich cua user.
                .filter(t -> t.getTransactionDate() != null && t.getTransactionDate().getYear() == year) // Chi lay giao dich dung nam.
                .forEach(t -> { // Xu ly tung giao dich hop le.
                    int month = t.getTransactionDate().getMonthValue(); // Lay thang cua giao dich.
                    if (t.getType() == TransactionType.INCOME) // Neu la thu nhap.
                        incomeByMonth.merge(month, t.getAmount(), BigDecimal::add); // Cong vao tong thu cua thang.
                    else // Neu khong phai INCOME thi xem la chi tieu.
                        expenseByMonth.merge(month, t.getAmount(), BigDecimal::add); // Cong vao tong chi cua thang.
                });

        Map<String, Object> result = new HashMap<>(); // Tao response map.
        result.put("income", incomeByMonth); // Dua map thu nhap theo thang vao response.
        result.put("expense", expenseByMonth); // Dua map chi tieu theo thang vao response.
        return result; // Tra ket qua ve controller.
    }

    // Gom tong chi tieu theo ten danh muc.
    public Map<String, Object> getByCategory(String email) {
        User user = userRepository.findByEmail(email) // Tim user theo email.
                .orElseThrow(() -> new RuntimeException("User not found")); // Khong thay user thi bao loi.
        List<Transaction> transactions = transactionRepository.findByUser(user); // Lay giao dich cua user.

        Map<String, BigDecimal> byCategory = transactions.stream() // Duyet danh sach giao dich.
                .filter(t -> t.getCategory() != null && t.getType() == TransactionType.EXPENSE) // Chi lay chi tieu co category.
                .collect(Collectors.groupingBy( // Gom nhom theo ten category.
                        t -> t.getCategory().getCategoryName(), // Key cua map la ten danh muc.
                        Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add) // Value la tong amount.
                ));

        Map<String, Object> result = new HashMap<>(); // Tao response map.
        result.put("expenseByCategory", byCategory); // Dua ket qua gom nhom vao response.
        return result; // Tra ve controller.
    }
}
