package com.example.expensebackend.Service;

import com.example.expensebackend.Entity.SavingGoal;
import com.example.expensebackend.Entity.User;
import com.example.expensebackend.Repository.SavingGoalRepository;
import com.example.expensebackend.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service // Bao cho Spring biet day la service xu ly muc tieu tiet kiem.
public class SavingGoalService {
    private final SavingGoalRepository savingGoalRepository; // Repository thao tac bang saving_goals.
    private final UserRepository userRepository; // Repository dung de tim user.

    // Constructor injection: Spring truyen repository vao service.
    public SavingGoalService(SavingGoalRepository savingGoalRepository, UserRepository userRepository) {
        this.savingGoalRepository = savingGoalRepository; // Luu SavingGoalRepository vao field.
        this.userRepository = userRepository; // Luu UserRepository vao field.
    }

    // Tao muc tieu tiet kiem moi cho user.
    public SavingGoal createSavingGoal(String email, SavingGoal savingGoal) {
        User user = userRepository.findByEmail(email) // Tim user theo email.
                .orElseThrow(() -> new RuntimeException("User not found")); // Khong thay user thi bao loi.
        savingGoal.setUser(user); // Gan goal thuoc ve user nay.
        return savingGoalRepository.save(savingGoal); // Luu goal vao database.
    }

    // Lay danh sach muc tieu tiet kiem cua user.
    public List<SavingGoal> getSavingGoalsByEmail(String email) {
        User user = userRepository.findByEmail(email) // Tim user theo email.
                .orElseThrow(() -> new RuntimeException("User not found")); // Khong thay user thi bao loi.
        return savingGoalRepository.findByUser(user); // Lay cac goal co user_id cua user nay.
    }

    // Lay mot muc tieu theo id.
    public SavingGoal getSavingGoalById(Long id) {
        return savingGoalRepository.findById(id) // Tim goal theo khoa chinh id.
                .orElseThrow(() -> new RuntimeException("Saving goal not found")); // Khong thay thi bao loi.
    }

    // Cap nhat muc tieu theo id.
    public SavingGoal updateSavingGoal(Long id, SavingGoal newSavingGoal) {
        SavingGoal savingGoal = savingGoalRepository.findById(id) // Lay goal cu trong database.
                .orElseThrow(() -> new RuntimeException("Saving goal not found")); // Khong thay thi bao loi.
        savingGoal.setGoalName(newSavingGoal.getGoalName()); // Cap nhat ten muc tieu.
        savingGoal.setTargetAmount(newSavingGoal.getTargetAmount()); // Cap nhat so tien can dat.
        savingGoal.setCurrentAmount(newSavingGoal.getCurrentAmount()); // Cap nhat so tien hien co.
        savingGoal.setDeadline(newSavingGoal.getDeadline()); // Cap nhat han hoan thanh.
        savingGoal.setStatus(newSavingGoal.getStatus()); // Cap nhat trang thai.
        return savingGoalRepository.save(savingGoal); // Luu thay doi va tra goal moi.
    }

    // Xoa muc tieu theo id.
    public void deleteSavingGoal(Long id) {
        savingGoalRepository.deleteById(id); // Goi JPA xoa row co id tuong ung.
    }

    // Tinh phan tram hoan thanh: currentAmount / targetAmount * 100.
    public double getProgress(Long id) {
        SavingGoal goal = getSavingGoalById(id); // Tai goal can tinh tien do.
        if (goal.getTargetAmount().compareTo(BigDecimal.ZERO) == 0) return 0; // Tranh chia cho 0.
        return goal.getCurrentAmount() // Lay so tien hien tai.
                .divide(goal.getTargetAmount(), 4, RoundingMode.HALF_UP) // Chia cho muc tieu va lam tron 4 chu so.
                .multiply(BigDecimal.valueOf(100)) // Doi tu ti le sang phan tram.
                .doubleValue(); // Chuyen BigDecimal sang double de tra ve JSON don gian.
    }
}
