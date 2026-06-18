# PROJECT_ROADMAP.md

# ĐỒ ÁN 3

# ỨNG DỤNG QUẢN LÝ CHI TIÊU CÁ NHÂN THÔNG MINH

---

# 1. GIỚI THIỆU

## 1.1 Mục tiêu

Ứng dụng Quản lý Chi tiêu Cá nhân Thông minh được xây dựng nhằm hỗ trợ người dùng quản lý thu nhập, chi tiêu, ngân sách và kế hoạch tiết kiệm một cách hiệu quả.

Hệ thống không chỉ lưu trữ dữ liệu tài chính cá nhân mà còn ứng dụng trí tuệ nhân tạo (AI) để phân tích thói quen chi tiêu, đưa ra các khuyến nghị tài chính và dự đoán xu hướng chi tiêu trong tương lai.

Ứng dụng được phát triển trên nền tảng di động bằng Flutter, sử dụng Spring Boot để xây dựng hệ thống Backend RESTful API và MySQL để lưu trữ dữ liệu.

---

# 2. CÔNG NGHỆ SỬ DỤNG

| Thành phần      | Công nghệ                   |
| --------------- | --------------------------- |
| Mobile App      | Flutter (Dart)              |
| Backend API     | Spring Boot                 |
| Security        | Spring Security + JWT       |
| Database        | MySQL                       |
| ORM             | Spring Data JPA (Hibernate) |
| API Testing     | Postman                     |
| IDE Backend     | IntelliJ IDEA               |
| IDE Mobile      | VS Code                     |
| Build Tool      | Maven                       |
| Version Control | Git + GitHub                |
| AI Service      | OpenAI API / Gemini API     |
| Kiến trúc       | RESTful API                 |

---

# 3. KIẾN TRÚC HỆ THỐNG

```text
Flutter Mobile App
        |
        v
Spring Boot REST API
        |
        v
Spring Security + JWT
        |
        v
MySQL Database
        |
        v
AI Service (OpenAI/Gemini)
```

---

# 4. CHỨC NĂNG HỆ THỐNG

## 4.1 Quản lý tài khoản

* Đăng ký tài khoản
* Đăng nhập
* Đăng xuất
* Cập nhật thông tin cá nhân
* Đổi mật khẩu
* Quên mật khẩu
* Xác thực JWT
* Refresh Token
* Phân quyền USER / ADMIN

---

## 4.2 Quản lý giao dịch

* Thêm giao dịch thu nhập
* Thêm giao dịch chi tiêu
* Chỉnh sửa giao dịch
* Xóa giao dịch
* Xem danh sách giao dịch
* Xem chi tiết giao dịch
* Tìm kiếm giao dịch
* Lọc theo ngày, tháng, năm

---

## 4.3 Quản lý danh mục

* Thêm danh mục
* Sửa danh mục
* Xóa danh mục
* Xem danh mục
* Danh mục mặc định:

    * Ăn uống
    * Đi lại
    * Học tập
    * Giải trí
    * Mua sắm
    * Sức khỏe
    * Tiền điện
    * Tiền nước
    * Khác

---

## 4.4 Thống kê tài chính

* Tổng thu nhập
* Tổng chi tiêu
* Số dư hiện tại
* Thống kê theo ngày
* Thống kê theo tháng
* Thống kê theo năm
* Biểu đồ cột (Bar Chart)
* Biểu đồ tròn (Pie Chart)
* Biểu đồ đường (Line Chart)

---

## 4.5 Quản lý mục tiêu tiết kiệm

* Tạo mục tiêu tiết kiệm
* Chỉnh sửa mục tiêu
* Xóa mục tiêu
* Theo dõi tiến độ
* Hiển thị phần trăm hoàn thành
* Cảnh báo khi đạt mục tiêu

---

## 4.6 Quản lý ngân sách

* Thiết lập ngân sách theo tháng
* Thiết lập ngân sách theo danh mục
* Theo dõi mức chi tiêu
* Cảnh báo khi vượt ngân sách
* Thống kê ngân sách

---

## 4.7 Thông báo

* Nhắc mục tiêu tiết kiệm
* Thông báo vượt ngân sách
* Thông báo giao dịch bất thường
* Thông báo AI khuyến nghị

---

## 4.8 Quản trị hệ thống (Admin)

* Xem danh sách người dùng
* Tìm kiếm người dùng
* Khóa tài khoản
* Mở khóa tài khoản
* Xóa tài khoản
* Quản lý danh mục hệ thống
* Xem thống kê toàn hệ thống
* Quản lý lịch sử AI

---

# 5. TÍNH NĂNG AI

## 5.1 AI Phân Loại Giao Dịch

Tự động phân loại giao dịch vào danh mục phù hợp dựa trên nội dung mô tả.

Ví dụ:

"Mua trà sữa" → Ăn uống

"Đổ xăng xe" → Đi lại

---

## 5.2 AI Tư Vấn Tài Chính

* Phân tích thói quen chi tiêu
* Đưa ra lời khuyên tiết kiệm
* Đề xuất cắt giảm chi tiêu không cần thiết

---

## 5.3 AI Dự Đoán Tài Chính

* Dự đoán chi tiêu tháng tiếp theo
* Dự đoán số tiền tiết kiệm có thể đạt được
* Đưa ra cảnh báo tài chính sớm

---

# 6. DATABASE

## Bảng Users

* id
* full_name
* email
* password
* phone
* avatar
* role_id
* status
* created_at

## Bảng Roles

* id
* role_name

Ví dụ:

* USER
* ADMIN

## Bảng Refresh Tokens

* id
* user_id
* token
* expiry_date

## Bảng Categories

* id
* user_id
* name
* type

## Bảng Transactions

* id
* user_id
* category_id
* amount
* description
* transaction_date

## Bảng Saving Goals

* id
* user_id
* title
* target_amount
* current_amount
* deadline

## Bảng Budgets

* id
* user_id
* category_id
* budget_amount
* month
* year

## Bảng Notifications

* id
* user_id
* title
* content
* is_read

## Bảng AI History

* id
* user_id
* prompt
* response
* created_at

---

# 7. BẢO MẬT HỆ THỐNG

## Spring Security

* Authentication
* Authorization

## JWT

* Access Token
* Refresh Token

## Password Security

* BCrypt Password Encoder

## Role Based Access Control

* USER
* ADMIN

---

# 8. REST API

## Authentication API

POST /api/auth/register

POST /api/auth/login

POST /api/auth/refresh-token

POST /api/auth/logout

GET /api/auth/profile

PUT /api/auth/profile

PUT /api/auth/change-password

POST /api/auth/forgot-password

POST /api/auth/reset-password

---

## Transaction API

GET /api/transactions

POST /api/transactions

PUT /api/transactions/{id}

DELETE /api/transactions/{id}

---

## Category API

GET /api/categories

POST /api/categories

PUT /api/categories/{id}

DELETE /api/categories/{id}

---

## Saving Goal API

GET /api/goals

POST /api/goals

PUT /api/goals/{id}

DELETE /api/goals/{id}

---

## Budget API

GET /api/budgets

POST /api/budgets

PUT /api/budgets/{id}

DELETE /api/budgets/{id}

---

## Statistics API

GET /api/statistics/monthly

GET /api/statistics/yearly

GET /api/statistics/dashboard

---

## Admin API

GET /api/admin/users

GET /api/admin/users/{id}

PUT /api/admin/users/{id}/lock

PUT /api/admin/users/{id}/unlock

DELETE /api/admin/users/{id}

GET /api/admin/statistics

---

# 9. KIỂM THỬ

## Backend

* Unit Test
* API Test
* JWT Test
* Security Test
* CRUD Test
* Database Test

## Frontend

* UI Test
* Responsive Test
* API Integration Test

---

# 10. ROADMAP 30 NGÀY

## Tuần 1

* Thiết kế Database
* Thiết kế ERD
* Cấu hình Spring Boot
* Kết nối MySQL
* Xây dựng Authentication
* JWT Authentication
* Spring Security

## Tuần 2

* User API
* Category API
* Transaction API
* Role USER / ADMIN
* Refresh Token

## Tuần 3

* Saving Goal API
* Budget API
* Statistics API
* Dashboard API
* Flutter UI

## Tuần 4

* AI Financial Advisor
* AI Categorization
* AI Prediction
* Testing
* Fix Bug
* Deploy

---

# 11. KẾT QUẢ MONG MUỐN

* Flutter Mobile App hoàn chỉnh
* Spring Boot REST API
* MySQL Database
* JWT Authentication
* Spring Security
* Role USER / ADMIN
* Refresh Token
* CRUD đầy đủ
* Dashboard thống kê
* Quản lý giao dịch
* Quản lý ngân sách
* Quản lý mục tiêu tiết kiệm
* AI phân loại giao dịch
* AI tư vấn tài chính
* AI dự đoán chi tiêu
* Hệ thống quản trị Admin
* Triển khai thực tế trên Cloud
