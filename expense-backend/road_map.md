# PROJECT_ROADMAP.md

# ĐỒ ÁN 3 - ỨNG DỤNG QUẢN LÝ CHI TIÊU CÁ NHÂN THÔNG MINH

## 1. Giới thiệu

### 1.1 Mục tiêu
Ứng dụng Quản lý Chi tiêu Cá nhân Thông minh được xây dựng nhằm hỗ trợ người dùng quản lý thu nhập, chi tiêu và kế hoạch tiết kiệm một cách khoa học.

Hệ thống không chỉ lưu trữ các giao dịch tài chính mà còn sử dụng AI để phân tích thói quen chi tiêu, đưa ra lời khuyên và dự đoán tình hình tài chính trong tương lai.

Ứng dụng hướng tới việc trở thành một trợ lý tài chính cá nhân trên điện thoại di động.

---

## 2. Công nghệ sử dụng

| Thành phần | Công nghệ |
|------------|-----------|
| Mobile App | Flutter (Dart) |
| Backend API | Java Spring Boot |
| Database | MySQL |
| ORM | Spring Data JPA (Hibernate) |
| API Testing | Postman |
| IDE Backend | IntelliJ IDEA |
| IDE Mobile | VS Code |
| Build Tool | Maven |
| Version Control | Git + GitHub |
| Kiến trúc | RESTful API |

---

## 3. Kiến trúc hệ thống

```text
Flutter App <-> Spring Boot API <-> MySQL Database
```

---

## 4. Chức năng chính

### 4.1 Quản lý tài khoản
- Đăng ký
- Đăng nhập
- Đăng xuất
- Cập nhật thông tin cá nhân
- Đổi mật khẩu

### 4.2 Quản lý giao dịch
- CRUD giao dịch
- Xem danh sách và chi tiết giao dịch

### 4.3 Quản lý danh mục
- CRUD danh mục
- Danh mục mặc định: Ăn uống, Đi lại, Học tập, Giải trí, Mua sắm, Sức khỏe...

### 4.4 Thống kê tài chính
- Tổng thu nhập
- Tổng chi tiêu
- Số dư hiện tại
- Pie Chart, Bar Chart, Line Chart

### 4.5 Quản lý mục tiêu tiết kiệm
- Tạo mục tiêu
- Theo dõi tiến độ
- Hiển thị phần trăm hoàn thành

---

## 5. Tính năng AI

### AI phân loại giao dịch
### AI tư vấn tài chính
### AI dự đoán tài chính

---

## 6. Database

- users
- categories
- transactions
- saving_goals
- budgets
- notifications
- ai_history

---

## 7. REST API

### User API
- POST /api/users/register
- POST /api/users/login
- GET /api/users/{id}
- PUT /api/users/{id}

### Category API
- GET /api/categories
- POST /api/categories
- PUT /api/categories/{id}
- DELETE /api/categories/{id}

### Transaction API
- GET /api/transactions
- POST /api/transactions
- PUT /api/transactions/{id}
- DELETE /api/transactions/{id}

### Saving Goal API
- GET /api/goals
- POST /api/goals
- PUT /api/goals/{id}
- DELETE /api/goals/{id}

---

## 8. Kiểm thử

### Backend
- Postman
- CRUD Testing
- MySQL Connection
- Validation

### Frontend
- UI Testing
- API Testing
- Chart Testing
- Responsive Testing

---

## 9. Roadmap 30 ngày

### Tuần 1
Thiết kế hệ thống và Backend cơ bản.

### Tuần 2
Xây dựng REST API.

### Tuần 3
Phát triển Flutter App.

### Tuần 4
AI, kiểm thử và hoàn thiện.

---

## 10. Kết quả mong muốn

- Backend Spring Boot
- Database MySQL
- Mobile Flutter
- REST API hoàn chỉnh
- CRUD đầy đủ
- Thống kê bằng biểu đồ
- Quản lý mục tiêu tiết kiệm
- AI phân loại giao dịch
- AI tư vấn tài chính
- AI dự đoán chi tiêu
