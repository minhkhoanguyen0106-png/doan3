# HƯỚNG DẪN VIẾT CODE SPRING BOOT - ĐỒ ÁN QUẢN LÝ CHI TIÊU

## MỤC LỤC

1. [Cấu trúc Project](#1-cấu-trúc-project)
2. [Entity - Thực thể](#2-entity---thực-thể)
3. [Repository - Kho dữ liệu](#3-repository---kho-dữ-liệu)
4. [Service - Xử lý nghiệp vụ](#4-service---xử-lý-nghiệp-vụ)
5. [DTO - Đối tượng truyền dữ liệu](#5-dto---đối-tượng-truyền-dữ-liệu)
6. [Controller - Điều hướng API](#6-controller---điều-hướng-api)
7. [Enum - Kiểu liệt kê](#7-enum---kiểu-liệt-kê)
8. [Validation - Kiểm tra dữ liệu](#8-validation---kiểm-tra-dữ-liệu)
9. [Quy tắc đặt tên](#9-quy-tắc-đặt-tên)
10. [Quy trình xử lý Request](#10-quy-trình-xử-lý-request)
11. [Ví dụ hoàn chỉnh](#11-ví-dụ-hoàn-chỉnh)

---

## 1. CẤU TRÚC PROJECT

```
expense-backend/
├── src/main/java/com/example/expensebackend/
│   ├── Config/           # Cấu hình (Security, CORS...)
│   ├── Controller/      # Điều hướng API (REST endpoints)
│   ├── Entity/          # Ánh xạ bảng database
│   ├── Enum/             # Kiểu liệt kê (nếu có)
│   ├── Repository/       # Truy cập database
│   ├── Service/          # Logic nghiệp vụ
│   └── dto/              # Đối tượng truyền dữ liệu
│       ├── Request/      # DTO nhận từ client
│       └── Response/     # DTO trả về client
├── src/main/resources/
│   └── application.properties  # Cấu hình DB, server...
└── pom.xml               # Thư viện Maven
```

**Quy tắc**: Mỗi Entity đều có 1 Controller, 1 Service, 1 Repository tương ứng.

---

## 2. ENTITY - THỰC THỂ

### 2.1 Mục đích
Entity là class Java ánh xạ với 1 bảng trong MySQL. Mỗi field = 1 cột trong bảng.

### 2.2 Cấu trúc cơ bản

```java
package com.example.expensebackend.Entity;

import jakarta.persistence.*;  // Import JPA annotations

@Entity                         // Đánh dấu class này là Entity
@Table(name = "ten_bang")      // Tên bảng trong database
public class TenEntity {
    @Id                         // Khóa chính
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Tự động tăng
    private Long id;

    @Column(nullable = false)   // Cột không được NULL
    private String tenCot;

    private String moTa;         // Cột có thể NULL (mặc định)

    // ==================== GETTERS ====================
    public Long getId() { return id; }
    public String getTenCot() { return tenCot; }

    // ==================== SETTERS ====================
    public void setId(Long id) { this.id = id; }
    public void setTenCot(String tenCot) { this.tenCot = tenCot; }
}
```

### 2.3 Các Annotation quan trọng

| Annotation | Ý nghĩa | Ví dụ |
|-----------|---------|-------|
| `@Entity` | Class này là Entity | |
| `@Table(name = "abc")` | Tên bảng trong DB | `@Table(name = "users")` |
| `@Id` | Khóa chính | `private Long id` |
| `@GeneratedValue` | Tự động tạo ID | `strategy = GenerationType.IDENTITY` |
| `@Column` | Cấu hình cột | `@Column(nullable = false, unique = true)` |
| `@ManyToOne` | Quan hệ N-1 | Nhiều Transaction thuộc 1 User |
| `@JoinColumn` | Cột join | `@JoinColumn(name = "user_id")` |
| `@Enumerated` | Lưu enum thành String | `INCOME` thay vì `0` |
| `@Transient` | Không lưu vào DB | |

### 2.4 Quan hệ giữa các Entity

**1. @ManyToOne (N-1)**: Nhiều bản ghi thuộc 1 bảng khác

```java
// Transaction thuộc về 1 User và 1 Category
@ManyToOne
@JoinColumn(name = "user_id")    // Tên cột trong bảng hiện tại
private User user;

@ManyToOne
@JoinColumn(name = "category_id")
private Category category;
```

**2. Cấu trúc bảng trong DB**:

```
users (1) ─────┬───── (N) transactions
               ├───── (N) categories
               ├───── (N) budgets
               ├───── (N) saving_goals
               ├───── (N) notifications
               └───── (N) aiHistory
```

### 2.5 Ví dụ Entity đầy đủ

```java
package com.example.expensebackend.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Quan hệ N-1 với User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Quan hệ N-1 với Category
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // Cột bắt buộc
    @Column(nullable = false)
    private String title;

    // Số tiền, dùng BigDecimal thay vì double để tránh sai số
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    // Enum lưu thành String trong DB
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    // Cột tùy chọn
    private String note;

    // Ngày giao dịch, mặc định là thời điểm tạo
    @Column(name = "transaction_date")
    private LocalDateTime transactionDate = LocalDateTime.now();

    // ==================== GETTERS ====================
    public Long getId() { return id; }
    public User getUser() { return user; }
    public Category getCategory() { return category; }
    public String getTitle() { return title; }
    public BigDecimal getAmount() { return amount; }
    public TransactionType getType() { return type; }
    public String getNote() { return note; }
    public LocalDateTime getTransactionDate() { return transactionDate; }

    // ==================== SETTERS ====================
    public void setId(Long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setCategory(Category category) { this.category = category; }
    public void setTitle(String title) { this.title = title; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setType(TransactionType type) { this.type = type; }
    public void setNote(String note) { this.note = note; }
    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
```

### 2.6 Lưu ý quan trọng

1. **Không lưu password thô (plain text)** → Phải mã hóa bằng BCrypt
2. **Dùng BigDecimal cho số tiền** → Tránh sai số float/double
3. **Tất cả Entity đều cần getter/setter** → Để Jackson convert JSON
4. **Entity không có logic nghiệp vụ** → Chỉ chứa data và ánh xạ

---

## 3. REPOSITORY - KHO DỮ LIỆU

### 3.1 Mục đích
Repository là interface kết nối Java với MySQL. Spring Boot tự động tạo câu SQL dựa trên method name.

### 3.2 Cấu trúc cơ bản

```java
package com.example.expensebackend.Repository;

import com.example.expensebackend.Entity.EntityName;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface EntityNameRepository extends JpaRepository<EntityName, Long> {

    // Spring tự động tạo câu SQL dựa trên tên method
    Optional<EntityName> findByEmail(String email);  // SELECT * WHERE email = ?

    List<EntityName> findByUser(User user);         // SELECT * WHERE user_id = ?

    boolean existsByEmail(String email);             // SELECT COUNT(*) > 0 WHERE email = ?
}
```

### 3.3 Các method tự động

| Method | SQL được tạo |
|--------|---------------|
| `findById(id)` | `SELECT * FROM table WHERE id = ?` |
| `findAll()` | `SELECT * FROM table` |
| `save(entity)` | `INSERT INTO...` hoặc `UPDATE...` |
| `deleteById(id)` | `DELETE FROM table WHERE id = ?` |
| `existsById(id)` | `SELECT COUNT(*) > 0 WHERE id = ?` |

### 3.4 Tự tạo method theo tên field

**Quy tắc**: `findBy` + TênField

```java
// Tìm theo email
Optional<User> findByEmail(String email);
// SELECT * FROM users WHERE email = ?

// Tìm theo tên (contains)
List<User> findByNameContaining(String keyword);
// SELECT * FROM users WHERE name LIKE '%keyword%'

// Tìm theo nhiều điều kiện
Optional<User> findByEmailAndPhoneNumber(String email, String phone);
// SELECT * FROM users WHERE email = ? AND phoneNumber = ?
```

### 3.5 Ví dụ Repository đầy đủ

```java
package com.example.expensebackend.Repository;

import com.example.expensebackend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository  // Đánh dấu đây là Repository (optional vì JpaRepository đã có)
public interface UserRepository extends JpaRepository<User, Long> {

    // Tìm user theo email
    Optional<User> findByEmail(String email);

    // Kiểm tra email đã tồn tại chưa
    boolean existsByEmail(String email);

    // Tìm user theo phone number
    Optional<User> findByPhoneNumber(String phoneNumber);
}
```

### 3.6 Lưu ý

1. **Interface extends JpaRepository** → Spring tự động implement
2. **Không cần viết SQL** → Chỉ cần đặt tên method đúng quy tắc
3. **Trả về Optional** → Để xử lý null an toàn
4. **Luôn có @Repository** → Để Spring quản lý (optional nhưng nên có)

---

## 4. SERVICE - XỬ LÝ NGHIỆP VỤ

### 4.1 Mục đích
Service chứa logic xử lý nghiệp vụ, kết nối Controller với Repository.

```
Controller ←→ Service ←→ Repository ←→ Database
```

### 4.2 Cấu trúc cơ bản

```java
package com.example.expensebackend.Service;

import com.example.expensebackend.Entity.EntityName;
import com.example.expensebackend.Repository.EntityNameRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service  // Đánh dấu đây là Service
public class EntityNameService {

    private final EntityNameRepository repository;  // Tiêm Repository

    // Constructor injection (Spring tự động truyền)
    public EntityNameService(EntityNameRepository repository) {
        this.repository = repository;
    }

    // ==================== CRUD METHODS ====================

    // Tạo mới
    public EntityName create(EntityName entity) {
        return repository.save(entity);
    }

    // Lấy tất cả
    public List<EntityName> getAll() {
        return repository.findAll();
    }

    // Lấy theo ID
    public Optional<EntityName> getById(Long id) {
        return repository.findById(id);
    }

    // Cập nhật
    public EntityName update(Long id, EntityName newEntity) {
        EntityName entity = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Not found"));
        // Cập nhật các field
        entity.setTenCot(newEntity.getTenCot());
        return repository.save(entity);
    }

    // Xóa
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
```

### 4.3 Các nghiệp vụ phức tạp

```java
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public TransactionService(
            TransactionRepository transactionRepository,
            UserRepository userRepository,
            CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    // Tạo giao dịch với user và category
    public Transaction createTransaction(String email, Long categoryId, Transaction transaction) {
        // Bước 1: Tìm user theo email
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Bước 2: Tìm category
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new RuntimeException("Category not found"));

        // Bước 3: Gán user và category vào transaction
        transaction.setUser(user);
        transaction.setCategory(category);

        // Bước 4: Lưu
        return transactionRepository.save(transaction);
    }

    // Xử lý đăng ký với mã hóa password
    public RegisterResponse register(RegisterRequest request) {
        // Kiểm tra email đã tồn tại chưa
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Mã hóa password
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        // Tạo entity
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(hashedPassword);

        // Lưu
        User savedUser = userRepository.save(user);

        // Trả về response
        RegisterResponse response = new RegisterResponse();
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        return response;
    }
}
```

### 4.4 Xử lý lỗi (Exception Handling)

```java
// Tìm hoặc ném lỗi
public User getUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
}

// Kiểm tra trước khi thao tác
public void deleteUser(Long id) {
    if (!userRepository.existsById(id)) {
        throw new RuntimeException("User not found with id: " + id);
    }
    userRepository.deleteById(id);
}
```

### 4.5 Lưu ý

1. **Constructor Injection** → Spring tự động truyền dependency
2. **Logic nghiệp vụ đặt ở đây** → Không đặt trong Controller
3. **Kiểm tra dữ liệu trước khi lưu** → Validate ở Service
4. **Mã hóa password trước khi lưu** → Dùng PasswordEncoder

---

## 5. DTO - ĐỐI TƯỢNG TRUYỀN DỮ LIỆU

### 5.1 Mục đích
DTO chuyển data giữa Client (Flutter) và Server (Spring Boot).

```
Flutter → JSON → Controller → DTO → Service → Repository → Database
Flutter ← JSON ← Controller ← DTO ← Service ← Repository ← Database
```

### 5.2 Tại sao cần DTO thay vì Entity?

| Entity | DTO |
|--------|-----|
| Ánh xạ trực tiếp với DB | Chỉ chứa data cần gửi/nhận |
| Có thể chứa password | Không gửi password về client |
| Có quan hệ phức tạp (@ManyToOne) | Trả về đơn giản, tránh lazy loading |
| Thay đổi theo DB | Độc lập với DB |
| Có logic | Chỉ có data |

### 5.3 Cấu trúc thư mục DTO

```
dto/
├── Request/                    # Nhận từ client
│   ├── LoginRequest.java
│   ├── RegisterRequest.java
│   ├── TransactionRequest.java
│   └── ...
│
└── Response/                   # Trả về client
    ├── LoginResponse.java
    ├── RegisterResponse.java
    ├── TransactionResponse.java
    └── ...
```

### 5.4 Request DTO - Nhận dữ liệu từ Client

```java
package com.example.expensebackend.dto.Request;

import jakarta.validation.constraints.*;  // Import validation

/**
 * DTO nhận dữ liệu đăng ký từ client.
 *
 * Mục đích: Client gửi thông tin để tạo tài khoản mới.
 * Validation:
 *   - name: không được trống, 2-100 ký tự
 *   - email: đúng format email
 *   - password: ít nhất 6 ký tự
 *
 * Ví dụ JSON gửi lên:
 * {
 *   "name": "Nguyen Van A",
 *   "email": "a@example.com",
 *   "password": "123456",
 *   "phoneNumber": "0912345678",
 *   "address": "123 ABC Street"
 * }
 */
public class RegisterRequest {

    // ===== VALIDATION =====
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be 2-100 characters")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    // Các trường tùy chọn
    private String phoneNumber;

    @Size(max = 255, message = "Address too long")
    private String address;

    // ===== GETTERS =====
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }

    // ===== SETTERS =====
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setAddress(String address) { this.address = address; }
}
```

### 5.5 Response DTO - Trả dữ liệu cho Client

```java
package com.example.expensebackend.dto.Response;

/**
 * DTO trả về thông tin đăng nhập thành công.
 *
 * Không bao gồm password vì lý do bảo mật.
 *
 * Ví dụ JSON trả về:
 * {
 *   "id": 1,
 *   "name": "Nguyen Van A",
 *   "email": "a@example.com",
 *   "phoneNumber": "0912345678",
 *   "address": "123 ABC Street"
 * }
 */
public class LoginResponse {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;

    // Constructor
    public LoginResponse(Long id, String name, String email,
                         String phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Constructor không tham số (cần cho Jackson)
    public LoginResponse() {}

    // ===== GETTERS =====
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }

    // ===== SETTERS =====
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setAddress(String address) { this.address = address; }
}
```

### 5.6 Response DTO cho thống kê (Phức tạp hơn)

```java
package com.example.expensebackend.dto.Response;

import java.math.BigDecimal;
import java.util.Map;

/**
 * DTO trả về tổng quan tài chính của user.
 *
 * Bao gồm: tổng thu, tổng chi, số dư.
 */
public class SummaryResponse {

    private BigDecimal totalIncome;     // Tổng thu
    private BigDecimal totalExpense;    // Tổng chi
    private BigDecimal balance;         // Số dư = thu - chi
    private Integer transactionCount;  // Số giao dịch

    // ===== CONSTRUCTOR =====
    public SummaryResponse(BigDecimal totalIncome, BigDecimal totalExpense,
                          BigDecimal balance, Integer transactionCount) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.balance = balance;
        this.transactionCount = transactionCount;
    }

    // ===== GETTERS =====
    public BigDecimal getTotalIncome() { return totalIncome; }
    public BigDecimal getTotalExpense() { return totalExpense; }
    public BigDecimal getBalance() { return balance; }
    public Integer getTransactionCount() { return transactionCount; }

    // ===== SETTERS =====
    public void setTotalIncome(BigDecimal totalIncome) { this.totalIncome = totalIncome; }
    public void setTotalExpense(BigDecimal totalExpense) { this.totalExpense = totalExpense; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public void setTransactionCount(Integer transactionCount) { this.transactionCount = transactionCount; }
}
```

### 5.7 Ví dụ DTO cho Transaction

```java
// ===== REQUEST =====
public class TransactionRequest {
    private String title;
    private BigDecimal amount;
    private String type;  // INCOME hoặc EXPENSE
    private String note;
    private LocalDateTime transactionDate;
    // KHÔNG gửi user_id vì lấy từ email trên URL
}

// ===== RESPONSE =====
public class TransactionResponse {
    private Long id;
    private String title;
    private BigDecimal amount;
    private String type;
    private String note;
    private LocalDateTime transactionDate;
    private String categoryName;   // Trả tên thay vì object
    private Long userId;           // Trả ID thay vì object
}
```

### 5.8 Lưu ý

1. **Validation annotation** → Đặt ở Request DTO
2. **Không gửi password về client** → Bỏ trong Response
3. **Trả ID thay vì Object** → Tránh lazy loading
4. **Trả tên thay vì full object** → Giảm kích thước JSON
5. **Constructor không tham số** → Cần cho Jackson deserialize

---

## 6. CONTROLLER - ĐIỀU HƯỚNG API

### 6.1 Mục đích
Controller nhận HTTP request từ client, gọi Service xử lý, trả JSON response.

### 6.2 Cấu trúc cơ bản

```java
package com.example.expensebackend.Controller;

import com.example.expensebackend.dto.Request.RequestDTO;
import com.example.expensebackend.dto.Response.ResponseDTO;
import com.example.expensebackend.Service.EntityService;
import jakarta.validation.Valid;  // Để validate request
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController                           // Trả JSON, không phải HTML
@RequestMapping("/api/ten")              // URL bắt đầu bằng /api/ten
public class EntityController {

    private final EntityService service;  // Tiêm Service

    // Constructor injection
    public EntityController(EntityService service) {
        this.service = service;
    }

    // ===== CRUD ENDPOINTS =====

    // POST /api/ten - Tạo mới
    @PostMapping
    public ResponseDTO create(@Valid @RequestBody RequestDTO request) {
        return service.create(request);
    }

    // GET /api/ten - Lấy tất cả
    @GetMapping
    public List<ResponseDTO> getAll() {
        return service.getAll();
    }

    // GET /api/ten/{id} - Lấy theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getById(@PathVariable Long id) {
        return service.getById(id)
            .map(ResponseEntity::ok)                    // Tìm thấy → 200
            .orElseGet(() -> ResponseEntity.notFound().build());  // Không thấy → 404
    }

    // PUT /api/ten/{id} - Cập nhật
    @PutMapping("/{id}")
    public ResponseDTO update(@PathVariable Long id,
                              @Valid @RequestBody RequestDTO request) {
        return service.update(id, request);
    }

    // DELETE /api/ten/{id} - Xóa
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
```

### 6.3 Các Annotation quan trọng

| Annotation | Ý nghĩa | Ví dụ |
|-----------|---------|-------|
| `@RestController` | Class trả JSON | Thay vì `@Controller` trả HTML |
| `@RequestMapping` | URL base cho tất cả method | `/api/users` |
| `@GetMapping` | GET request | Lấy data |
| `@PostMapping` | POST request | Tạo mới |
| `@PutMapping` | PUT request | Cập nhật |
| `@DeleteMapping` | DELETE request | Xóa |
| `@RequestBody` | Đọc JSON từ body | `{"name": "A"}` |
| `@PathVariable` | Lấy từ URL | `/users/5` → id=5 |
| `@RequestParam` | Lấy từ query string | `/search?name=A` |
| `@Valid` | Validate request | Kiểm tra @NotBlank, @Email... |

### 6.4 Các kiểu trả về

```java
// Trả về object (200 OK)
@PostMapping
public Entity create(@RequestBody Entity entity) {
    return service.create(entity);
}

// Trả về ResponseEntity với status code
@GetMapping("/{id}")
public ResponseEntity<Entity> getById(@PathVariable Long id) {
    return service.getById(id)
        .map(ResponseEntity::ok)               // 200 OK
        .orElseGet(() -> ResponseEntity.notFound().build());  // 404 Not Found
}

// Trả về 201 Created
@PostMapping
public ResponseEntity<Entity> create(@RequestBody Entity entity) {
    Entity created = service.create(entity);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
}

// Trả về lỗi 400 Bad Request
@PostMapping
public ResponseEntity<String> create(@RequestBody Entity entity) {
    try {
        return ResponseEntity.ok(service.create(entity));
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

// Trả về List
@GetMapping
public List<Entity> getAll() {
    return service.getAll();
}
```

### 6.5 Các loại API theo chức năng

```java
// ===== USER APIs =====
// POST /api/users/register
// POST /api/users/login
// GET  /api/users              → Lấy tất cả user
// GET  /api/users/{id}         → Lấy user theo ID
// PUT  /api/users/{id}         → Cập nhật user
// DELETE /api/users/{id}       → Xóa user
// PUT  /api/users/{email}/change-password  → Đổi mật khẩu

// ===== TRANSACTION APIs =====
// POST /api/transactions/user/{email}/category/{categoryId}  → Tạo giao dịch
// GET  /api/transactions/user/{email}           → Lấy giao dịch của user
// GET  /api/transactions/{id}                  → Lấy chi tiết giao dịch
// PUT  /api/transactions/{id}                  → Cập nhật giao dịch
// DELETE /api/transactions/{id}                → Xóa giao dịch

// ===== STATISTIC APIs =====
// GET /api/statistics/summary/user/{email}     → Tổng thu/chi/số dư
// GET /api/statistics/monthly/user/{email}?year=2026  → Theo tháng
// GET /api/statistics/by-category/user/{email} → Theo danh mục
```

### 6.6 Ví dụ Controller đầy đủ

```java
package com.example.expensebackend.Controller;

import com.example.expensebackend.Entity.Transaction;
import com.example.expensebackend.Service.TransactionService;
import com.example.expensebackend.dto.Request.TransactionRequest;
import com.example.expensebackend.dto.Response.TransactionResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Tạo giao dịch cho user cụ thể
    // POST /api/transactions/user/{email}/category/{categoryId}
    @PostMapping("/user/{email}/category/{categoryId}")
    public ResponseEntity<TransactionResponse> createTransaction(
            @PathVariable String email,
            @PathVariable Long categoryId,
            @Valid @RequestBody TransactionRequest request) {

        try {
            TransactionResponse response = transactionService
                .createTransaction(email, categoryId, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Lấy giao dịch của user
    // GET /api/transactions/user/{email}
    @GetMapping("/user/{email}")
    public List<TransactionResponse> getByEmail(@PathVariable String email) {
        return transactionService.getByEmail(email);
    }

    // Lấy 1 giao dịch
    // GET /api/transactions/{id}
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getById(@PathVariable Long id) {
        return transactionService.getById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Cập nhật giao dịch
    // PUT /api/transactions/{id}
    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody TransactionRequest request) {

        try {
            TransactionResponse response = transactionService.update(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Xóa giao dịch
    // DELETE /api/transactions/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

### 6.7 Lưu ý

1. **Dùng @Valid** → Kích hoạt validation ở Request DTO
2. **Trả ResponseEntity** → Kiểm soát HTTP status code
3. **@PathVariable vs @RequestParam** → URL vs Query string
4. **Service trả về Optional** → Controller xử lý 404

---

## 7. ENUM - KIỂU LIỆT KÊ

### 7.1 Mục đích
Enum định nghĩa các giá trị cố định, ví dụ: INCOME, EXPENSE.

### 7.2 Cấu trúc

```java
package com.example.expensebackend.Entity;

public enum TransactionType {
    INCOME,    // Thu nhập
    EXPENSE    // Chi tiêu
}
```

```java
package com.example.expensebackend.Entity;

public enum CategoryType {
    INCOME,
    EXPENSE
}
```

### 7.3 Sử dụng trong Entity

```java
// Trong Entity
@Enumerated(EnumType.STRING)  // Lưu thành String "INCOME" thay vì số 0
@Column(nullable = false)
private TransactionType type;

// Gán giá trị
transaction.setType(TransactionType.INCOME);

// So sánh
if (transaction.getType() == TransactionType.INCOME) {
    // Xử lý thu nhập
}
```

### 7.4 Lưu ý

1. **@Enumerated(EnumType.STRING)** → Lưu "INCOME" thay vì 0,1
2. **Đặt trong Entity folder** → Thuộc về project, không phải thư viện
3. **Viết hoa** → Quy ước cho Enum

---

## 8. VALIDATION - KIỂM TRA DỮ LIỆU

### 8.1 Các Annotation phổ biến

| Annotation | Ý nghĩa | Ví dụ |
|-----------|---------|-------|
| `@NotNull` | Không được null | `@NotNull private String name` |
| `@NotBlank` | Không được rỗng hoặc chỉ space | `@NotBlank private String email` |
| `@NotEmpty` | Không được rỗng hoặc null | `@NotEmpty private List<String>` |
| `@Email` | Đúng format email | `a@b.com` |
| `@Size(min, max)` | Độ dài chuỗi | `@Size(min = 2, max = 100)` |
| `@Min` | Giá trị tối thiểu | `@Min(0)` |
| `@Max` | Giá trị tối đa | `@Max(100)` |
| `@Pattern` | Regex pattern | `@Pattern(regexp = "\\d{10}")` |
| `@Positive` | Số dương | `@Positive private BigDecimal amount` |

### 8.2 Ví dụ Request với Validation

```java
public class RegisterRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be 2-100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Pattern(regexp = "^\\d{10,15}$", message = "Phone must be 10-15 digits")
    private String phoneNumber;

    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String address;
}
```

### 8.3 Bật Validation trong Controller

```java
@PostMapping
public ResponseEntity<?> create(@Valid @RequestBody RequestDTO request) {
    // Nếu validation fail → Spring trả 400 Bad Request tự động
    return ResponseEntity.ok(service.create(request));
}
```

### 8.4 Xử lý lỗi Validation (tùy chọn - nâng cao)

```java
// Tạo class xử lý lỗi toàn cục
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return ResponseEntity.badRequest().body(errors);
    }
}
```

---

## 9. QUY TẮC ĐẶT TÊN

### 9.1 Package

```
com.example.expensebackend
├── Config/
├── Controller/
├── Entity/
├── Enum/
├── Repository/
├── Service/
└── dto/
    ├── Request/
    └── Response/
```

### 9.2 File

| Loại | Quy tắc | Ví dụ |
|------|---------|-------|
| Entity | PascalCase, danh từ số ít | `User.java`, `Transaction.java` |
| Enum | PascalCase | `TransactionType.java` |
| Repository | Entity + Repository | `UserRepository.java` |
| Service | Entity + Service | `UserService.java` |
| Controller | Entity + Controller | `UserController.java` |
| Request DTO | Entity + Request | `RegisterRequest.java` |
| Response DTO | Entity + Response | `LoginResponse.java` |

### 9.3 Method

| Loại | Quy tắc | Ví dụ |
|------|---------|-------|
| Repository | `findBy` + Field | `findByEmail(email)` |
| Service CRUD | create, get, update, delete | `createUser()`, `getUserById()` |
| Service nghiệp vụ | Động từ mô tả | `calculateBalance()`, `getMonthlySummary()` |

### 9.4 Biến

| Loại | Quy tắc | Ví dụ |
|------|---------|-------|
| Field | camelCase | `phoneNumber`, `createdAt` |
| Local variable | camelCase | `savedUser`, `hashedPassword` |
| Parameter | camelCase | `registerRequest`, `email` |

### 9.5 URL Endpoint

```
POST   /api/users          → Tạo user
GET    /api/users          → Lấy tất cả
GET    /api/users/{id}      → Lấy 1 user
PUT    /api/users/{id}      → Cập nhật
DELETE /api/users/{id}      → Xóa

GET    /api/transactions/user/{email}  → Giao dịch của user
GET    /api/statistics/summary/user/{email}  → Tổng quan
```

---

## 10. QUY TRÌNH XỬ LÝ REQUEST

### 10.1 Flow hoàn chỉnh

```
┌─────────────────────────────────────────────────────────────────┐
│                        CLIENT (Flutter)                         │
└─────────────────────────────────┬───────────────────────────────┘
                                  │
                                  ▼ HTTP POST /api/users/register
                                  │ Body: {"name": "A", "email": "a@b.com", ...}
┌─────────────────────────────────┴───────────────────────────────┐
│                     UserController.java                        │
│  @PostMapping("/register")                                      │
│  public RegisterResponse register(@Valid @RequestBody RegisterRequest request) │
│      ↓                                                         │
│      return userService.register(request);                     │
└─────────────────────────────────┬───────────────────────────────┘
                                  │
                                  ▼
┌─────────────────────────────────┴───────────────────────────────┐
│                      UserService.java                          │
│  public RegisterResponse register(RegisterRequest request) {   │
│      // 1. Kiểm tra email đã tồn tại?                           │
│      // 2. Mã hóa password (BCrypt)                             │
│      // 3. Tạo User entity                                      │
│      // 4. Lưu vào DB (UserRepository.save)                    │
│      // 5. Tạo categories mặc định                              │
│      // 6. Trả về RegisterResponse                              │
│  }                                                             │
└─────────────────────────────────┬───────────────────────────────┘
                                  │
                                  ▼
┌─────────────────────────────────┴───────────────────────────────┐
│                     UserRepository.java                        │
│  public interface extends JpaRepository<User, Long> {           │
│      Optional<User> findByEmail(String email);                  │
│  }                                                             │
└─────────────────────────────────┬───────────────────────────────┘
                                  │
                                  ▼ SQL: INSERT INTO users (...) VALUES (...)
┌─────────────────────────────────┴───────────────────────────────┐
│                    MySQL Database                               │
│  users table: id, name, email, password (hashed), ...           │
└─────────────────────────────────┬───────────────────────────────┘
                                  │
                                  ▼ HTTP 200 OK
                                  │ Body: {"name": "A", "email": "a@b.com", ...}
┌─────────────────────────────────┴───────────────────────────────┐
│                        CLIENT (Flutter)                         │
│  Nhận response → Lưu vào local storage → Chuyển màn hình       │
└─────────────────────────────────────────────────────────────────┘
```

### 10.2 Các bước xử lý chi tiết

**Bước 1: Client gửi request**
```
POST /api/users/register
Content-Type: application/json

{
  "name": "Nguyen Van A",
  "email": "a@example.com",
  "password": "123456",
  "phoneNumber": "0912345678"
}
```

**Bước 2: Controller nhận và validate**
```java
@PostMapping("/register")
public RegisterResponse register(@Valid @RequestBody RegisterRequest request) {
    // Spring tự động parse JSON → RegisterRequest
    // Kiểm tra @NotBlank, @Email, @Size...
    return userService.register(request);
}
```

**Bước 3: Service xử lý nghiệp vụ**
```java
public RegisterResponse register(RegisterRequest request) {
    // Validate email
    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
        throw new RuntimeException("Email already exists");
    }

    // Hash password
    String hashedPassword = passwordEncoder.encode(request.getPassword());

    // Tạo entity
    User user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPassword(hashedPassword);

    // Lưu
    User savedUser = userRepository.save(user);

    // Trả response
    return new RegisterResponse(savedUser.getName(), savedUser.getEmail(), ...);
}
```

**Bước 4: Repository thực thi SQL**
```java
// Tự động tạo: INSERT INTO users (name, email, password, ...) VALUES (?, ?, ?, ...)
// savedUser có id được gán tự động
```

**Bước 5: Trả JSON về Client**
```json
{
  "name": "Nguyen Van A",
  "email": "a@example.com",
  "phoneNumber": "0912345678",
  "address": null
}
```

---

## 11. VÍ DỤ HOÀN CHỈNH

### 11.1 Entity: SavingGoal.java

```java
package com.example.expensebackend.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "saving_goals")
public class SavingGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String goalName;

    @Column(nullable = false)
    private BigDecimal targetAmount;  // Số tiền mục tiêu

    @Column(nullable = false)
    private BigDecimal currentAmount; // Số tiền đã tiết kiệm

    @Column(nullable = false)
    private LocalDate deadline;       // Ngày hết hạn

    private String status;            // Trạng thái: ACTIVE, COMPLETED, EXPIRED

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // ===== GETTERS =====
    public Long getId() { return id; }
    public String getGoalName() { return goalName; }
    public BigDecimal getTargetAmount() { return targetAmount; }
    public BigDecimal getCurrentAmount() { return currentAmount; }
    public LocalDate getDeadline() { return deadline; }
    public String getStatus() { return status; }
    public User getUser() { return user; }

    // ===== SETTERS =====
    public void setId(Long id) { this.id = id; }
    public void setGoalName(String goalName) { this.goalName = goalName; }
    public void setTargetAmount(BigDecimal targetAmount) { this.targetAmount = targetAmount; }
    public void setCurrentAmount(BigDecimal currentAmount) { this.currentAmount = currentAmount; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    public void setStatus(String status) { this.status = status; }
    public void setUser(User user) { this.user = user; }

    // ===== COMPUTED FIELD (Không lưu vào DB) =====
    @Transient
    public double getProgressPercentage() {
        if (targetAmount == null || targetAmount.compareTo(BigDecimal.ZERO) == 0) {
            return 0.0;
        }
        return currentAmount.multiply(BigDecimal.valueOf(100))
                .divide(targetAmount, 2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }
}
```

### 11.2 DTO: SavingGoalRequest.java

```java
package com.example.expensebackend.dto.Request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class SavingGoalRequest {

    @NotBlank(message = "Goal name is required")
    @Size(max = 100, message = "Goal name cannot exceed 100 characters")
    private String goalName;

    @NotNull(message = "Target amount is required")
    @Positive(message = "Target amount must be positive")
    private BigDecimal targetAmount;

    @PositiveOrZero(message = "Current amount cannot be negative")
    private BigDecimal currentAmount;

    @NotNull(message = "Deadline is required")
    @Future(message = "Deadline must be in the future")
    private LocalDate deadline;

    private String status;

    // ===== GETTERS =====
    public String getGoalName() { return goalName; }
    public BigDecimal getTargetAmount() { return targetAmount; }
    public BigDecimal getCurrentAmount() { return currentAmount; }
    public LocalDate getDeadline() { return deadline; }
    public String getStatus() { return status; }

    // ===== SETTERS =====
    public void setGoalName(String goalName) { this.goalName = goalName; }
    public void setTargetAmount(BigDecimal targetAmount) { this.targetAmount = targetAmount; }
    public void setCurrentAmount(BigDecimal currentAmount) { this.currentAmount = currentAmount; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    public void setStatus(String status) { this.status = status; }
}
```

### 11.3 DTO: SavingGoalResponse.java

```java
package com.example.expensebackend.dto.Response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SavingGoalResponse {

    private Long id;
    private String goalName;
    private BigDecimal targetAmount;
    private BigDecimal currentAmount;
    private LocalDate deadline;
    private String status;
    private Double progressPercentage;
    private Long userId;

    // ===== CONSTRUCTOR =====
    public SavingGoalResponse() {}

    public SavingGoalResponse(Long id, String goalName, BigDecimal targetAmount,
                              BigDecimal currentAmount, LocalDate deadline,
                              String status, Double progressPercentage, Long userId) {
        this.id = id;
        this.goalName = goalName;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.deadline = deadline;
        this.status = status;
        this.progressPercentage = progressPercentage;
        this.userId = userId;
    }

    // ===== GETTERS =====
    public Long getId() { return id; }
    public String getGoalName() { return goalName; }
    public BigDecimal getTargetAmount() { return targetAmount; }
    public BigDecimal getCurrentAmount() { return currentAmount; }
    public LocalDate getDeadline() { return deadline; }
    public String getStatus() { return status; }
    public Double getProgressPercentage() { return progressPercentage; }
    public Long getUserId() { return userId; }

    // ===== SETTERS =====
    public void setId(Long id) { this.id = id; }
    public void setGoalName(String goalName) { this.goalName = goalName; }
    public void setTargetAmount(BigDecimal targetAmount) { this.targetAmount = targetAmount; }
    public void setCurrentAmount(BigDecimal currentAmount) { this.currentAmount = currentAmount; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    public void setStatus(String status) { this.status = status; }
    public void setProgressPercentage(Double progressPercentage) { this.progressPercentage = progressPercentage; }
    public void setUserId(Long userId) { this.userId = userId; }
}
```

### 11.4 Repository: SavingGoalRepository.java

```java
package com.example.expensebackend.Repository;

import com.example.expensebackend.Entity.SavingGoal;
import com.example.expensebackend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SavingGoalRepository extends JpaRepository<SavingGoal, Long> {

    // Tìm goals theo user
    List<SavingGoal> findByUser(User user);

    // Tìm goals theo user và status
    List<SavingGoal> findByUserAndStatus(User user, String status);
}
```

### 11.5 Service: SavingGoalService.java

```java
package com.example.expensebackend.Service;

import com.example.expensebackend.Entity.SavingGoal;
import com.example.expensebackend.Entity.User;
import com.example.expensebackend.Repository.SavingGoalRepository;
import com.example.expensebackend.Repository.UserRepository;
import com.example.expensebackend.dto.Response.SavingGoalResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavingGoalService {

    private final SavingGoalRepository savingGoalRepository;
    private final UserRepository userRepository;

    public SavingGoalService(SavingGoalRepository savingGoalRepository,
                             UserRepository userRepository) {
        this.savingGoalRepository = savingGoalRepository;
        this.userRepository = userRepository;
    }

    // Tạo mục tiêu tiết kiệm
    public SavingGoalResponse createSavingGoal(String email, SavingGoalRequest request) {
        // Tìm user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Tạo entity
        SavingGoal goal = new SavingGoal();
        goal.setGoalName(request.getGoalName());
        goal.setTargetAmount(request.getTargetAmount());
        goal.setCurrentAmount(request.getCurrentAmount() != null ?
                request.getCurrentAmount() : BigDecimal.ZERO);
        goal.setDeadline(request.getDeadline());
        goal.setStatus(request.getStatus() != null ? request.getStatus() : "ACTIVE");
        goal.setUser(user);

        // Lưu
        SavingGoal savedGoal = savingGoalRepository.save(goal);

        // Trả về response
        return convertToResponse(savedGoal);
    }

    // Lấy tất cả goal của user
    public List<SavingGoalResponse> getSavingGoalsByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<SavingGoal> goals = savingGoalRepository.findByUser(user);
        return goals.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Lấy goal theo ID
    public SavingGoalResponse getSavingGoalById(Long id) {
        SavingGoal goal = savingGoalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found"));
        return convertToResponse(goal);
    }

    // Tính phần trăm hoàn thành
    public Double getProgress(Long id) {
        SavingGoal goal = savingGoalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found"));
        return goal.getProgressPercentage();
    }

    // Cập nhật goal
    public SavingGoalResponse updateSavingGoal(Long id, SavingGoalRequest request) {
        SavingGoal goal = savingGoalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found"));

        goal.setGoalName(request.getGoalName());
        goal.setTargetAmount(request.getTargetAmount());
        goal.setCurrentAmount(request.getCurrentAmount());
        goal.setDeadline(request.getDeadline());
        goal.setStatus(request.getStatus());

        SavingGoal updatedGoal = savingGoalRepository.save(goal);
        return convertToResponse(updatedGoal);
    }

    // Xóa goal
    public void deleteSavingGoal(Long id) {
        if (!savingGoalRepository.existsById(id)) {
            throw new RuntimeException("Goal not found");
        }
        savingGoalRepository.deleteById(id);
    }

    // Chuyển Entity → Response DTO
    private SavingGoalResponse convertToResponse(SavingGoal goal) {
        return new SavingGoalResponse(
                goal.getId(),
                goal.getGoalName(),
                goal.getTargetAmount(),
                goal.getCurrentAmount(),
                goal.getDeadline(),
                goal.getStatus(),
                goal.getProgressPercentage(),
                goal.getUser().getId()
        );
    }
}
```

### 11.6 Controller: SavingGoalController.java

```java
package com.example.expensebackend.Controller;

import com.example.expensebackend.Service.SavingGoalService;
import com.example.expensebackend.dto.Response.SavingGoalResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class SavingGoalController {

    private final SavingGoalService savingGoalService;

    public SavingGoalController(SavingGoalService savingGoalService) {
        this.savingGoalService = savingGoalService;
    }

    // Tạo mục tiêu tiết kiệm
    // POST /api/goals/user/{email}
    @PostMapping("/user/{email}")
    public ResponseEntity<SavingGoalResponse> create(
            @PathVariable String email,
            @Valid @RequestBody SavingGoalRequest request) {
        try {
            SavingGoalResponse response = savingGoalService.createSavingGoal(email, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Lấy tất cả mục tiêu của user
    // GET /api/goals/user/{email}
    @GetMapping("/user/{email}")
    public List<SavingGoalResponse> getByEmail(@PathVariable String email) {
        return savingGoalService.getSavingGoalsByEmail(email);
    }

    // Lấy 1 mục tiêu
    // GET /api/goals/{id}
    @GetMapping("/{id}")
    public ResponseEntity<SavingGoalResponse> getById(@PathVariable Long id) {
        return savingGoalService.getSavingGoalById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lấy phần trăm hoàn thành
    // GET /api/goals/{id}/progress
    @GetMapping("/{id}/progress")
    public ResponseEntity<Double> getProgress(@PathVariable Long id) {
        return ResponseEntity.ok(savingGoalService.getProgress(id));
    }

    // Cập nhật mục tiêu
    // PUT /api/goals/{id}
    @PutMapping("/{id}")
    public ResponseEntity<SavingGoalResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody SavingGoalRequest request) {
        try {
            SavingGoalResponse response = savingGoalService.updateSavingGoal(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Xóa mục tiêu
    // DELETE /api/goals/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        savingGoalService.deleteSavingGoal(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

## CHECKLIST TRƯỚC KHI COMMIT

- [ ] Entity có đầy đủ getter/setter
- [ ] Entity có @Entity, @Table, @Id, @GeneratedValue
- [ ] Repository extends JpaRepository
- [ ] Service có @Service và Constructor Injection
- [ ] Controller có @RestController và @RequestMapping
- [ ] Request DTO có validation annotations
- [ ] Response DTO không chứa password
- [ ] Trả về ResponseEntity thay vì Entity trực tiếp
- [ ] Xử lý Exception với try-catch hoặc orElseThrow
- [ ] Dùng @Valid trước @RequestBody
- [ ] Quy tắc đặt tên nhất quán

---

**Chúc bạn hoàn thành Đồ Án 3 thật tốt!**