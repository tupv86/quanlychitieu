# Technical Backlog & Epics cho MVP Quản lý Chi tiêu

Tài liệu này định nghĩa Backlog kỹ thuật từ mức Epic đến Task, dựa trên yêu cầu từ `docs/PRD.md` và `docs/URD.md`. Thứ tự triển khai được liệt kê từ trên xuống dưới, đảm bảo các tính năng phụ thuộc (dependency) được hoàn thiện trước.

---

## Epic 1: Thiết lập & Cấu trúc Nền tảng (Foundation)
Mục tiêu là khởi tạo dự án với đầy đủ cấu hình kết nối database cục bộ, giao diện layouts chung (Template) để gắn các tính năng về sau.

### Feature 1.1: Khởi tạo Project & Cấu hình Database H2
- **Mô tả:** Tạo base source code Spring Boot, cấu hình H2 Database để dữ liệu lưu vào file cục bộ (chống mất dữ liệu khi restart) thay vì in-memory.
- **Dependency:** None
- **Acceptance Criteria:**
  - Dự án build & run thành công cổng mặc định (vd: 8080).
  - Có thể truy cập H2 Console `http://localhost:8080/h2-console`.
  - Dữ liệu được cấu hình lưu vào file (vd: `jdbc:h2:file:./data/chitieu_db`).
  - Khi restart ứng dụng, các bảng/dữ liệu đã tạo trước đó vẫn tồn tại.
- **Tasks:**
  - [ ] Generate project Spring Boot (Web, JPA, H2, Thymeleaf, Lombok).
  - [ ] Cấu hình `application.properties` cho H2 (File-based, console enabled).
  - [ ] Khởi chạy thử và kiểm tra kết nối với H2 Console.

---

## Epic 2: Quản lý Cốt lõi Dữ liệu (Core Transaction Data)
Xây dựng Entity `Transaction` (Giao dịch) và các thao tác CRUD tới database, làm nền móng logic cho toàn bộ ứng dụng.

### Feature 2.1: Entity & Tầng Data Access (Repository)
- **Mô tả:** Định nghĩa thực thể lập trình `Transaction` ánh xạ với bảng CSDL và tạo repository tương tác với CSDL.
- **Dependency:** Feature 1.1
- **Acceptance Criteria:**
  - Database xuất hiện bảng `transaction` (id, amount, date, description, type).
  - Định nghĩa sẵn kiểu enum/chuỗi cho field `type` (INCOME, EXPENSE).
  - Viết được logic Query tùy chỉnh (nếu cần) tìm theo Tháng/Năm trong Interface Repository.
- **Tasks:**
  - [ ] Tạo Enum `TransactionType` (INCOME, EXPENSE).
  - [ ] Tạo Entity `Transaction` (id tự tăng, field dữ liệu cần thiết).
  - [ ] Tạo `TransactionRepository` extends `JpaRepository`.
  - [ ] Thử nghiệm Save/Query nhanh tại `CommandLineRunner` hoặc Data Seeder (Mock data ban đầu).

### Feature 2.2: Tầng Xử lý Logic (Service)
- **Mô tả:** Viết lớp Service xử lý nghiệp vụ: Tính toán Tổng Thu, Tổng Chi, Số Dư và gọi Repository để thêm/xóa/lấy giao dịch.
- **Dependency:** Feature 2.1
- **Acceptance Criteria:**
  - Hàm `getAllTransactions(thang, nam)`: trả về list.
  - Hàm `calculateTotalIncome()`, `calculateTotalExpense()`: trả về tổng tiền.
  - Hàm save, delete hoạt động bình thường qua các unit test cơ bản.
- **Tasks:**
  - [ ] Tạo class `TransactionService`.
  - [ ] Viết logic tính toán (`Total Income`, `Total Expense`, `Balance`).
  - [ ] Viết logic lấy giao dịch theo filter tháng.
  - [ ] Viết chức năng `Save` (cho Thêm/Sửa) và `Delete` cơ sở.

---

## Epic 3: Giao diện Người dùng (User Interface & Frontend)
Ghép nối Frontend sử dụng HTML5 + Thymeleaf cùng Bootstrap 5 nhằm tương tác với các Service đã tạo rên Backend.

### Feature 3.1: Giao diện Layout Tổng & Dashboard
- **Mô tả:** Sử dụng Bootstrap tạo giao diện người dùng đơn giản nhưng dễ nhìn, responsive hỗ trợ điện thoại; gồm một header và vùng thân trang (Dashboard).
- **Dependency:** Epic 1
- **Acceptance Criteria:**
  - Có file `layout.html` bằng Thymeleaf tái sử dụng được (Header, Footer).
  - Tích hợp thành công thư viện CDN Bootstrap 5 css/js.
  - Trang chủ (`/`) hiển thị Dashboard hiển thị "Thống kê" dạng Card tĩnh (Total Income, Total Expense, Balance).
- **Tasks:**
  - [ ] Nhúng Bootstrap 5 CDN vào file template gốc.
  - [ ] Thiết kế Layout (Navbar, Container).
  - [ ] Build UI Dashboard: Khối card 3 cột hiển thị Các Tổng số và Layout List bên dưới.
  - [ ] Tạo Controller trả dòng render View ban đầu (chưa data thật).

### Feature 3.2: Danh sách và Lọc Giao dịch theo Tháng
- **Mô tả:** Kết nối Dashboard MVC controller với Service, render danh sách lịch sử và các con số thật. Có Select box cho phép lọc.
- **Dependency:** Feature 2.2, Feature 3.1
- **Acceptance Criteria:**
  - Các card (Thu/Chi/Số dư) hiển thị số liệu thực trong database.
  - Danh sách table hiển thị đúng các dòng thời gian thực.
  - Chọn tháng từ Dropdown, bấm Submit -> Trang load lại & danh sách + tổng tiền cập nhật đúng tháng đó.
- **Tasks:**
  - [ ] Cập nhật Controller `@GetMapping` nhận param (month, year).
  - [ ] Query Dữ liệu từ Service dựa trên tham số Tháng/Năm.
  - [ ] Đẩy (Model) dữ liệu lên UI và vòng lặp (th:each) tạo các hàng Table.
  - [ ] Setup HTML UI cho form lọc `<select>` submit `GET` request.

### Feature 3.3: Thêm Nhập Giao Dịch Mới (Form Nhập)
- **Mô tả:** Cung cấp Form (hoặc trang con) để người dùng thêm nhanh thu/chi.
- **Dependency:** Feature 2.2, Feature 3.1
- **Acceptance Criteria:**
  - Bấm nút "Thêm Mới" hiển thị form (Modal bootstrap hoặc sub-page).
  - Nhập thông tin: `Loại (Thu/Chi)`, `Số tiền`, `Ngày (Data picker)`, `Mô tả`.
  - Lưu thành công -> Rediect về lại trang dashboard với dữ liệu mới.
- **Tasks:**
  - [ ] Tạo HTML Form sử dụng Bootstrap form.
  - [ ] Tạo `@PostMapping` trong Controller để nhận và parse form data (`@ModelAttribute`).
  - [ ] Gọi Service Save -> Save CSDL -> `redirect:/`.

### Feature 3.4: Xóa Giao Dịch
- **Mô tả:** Có Nút "Xóa" cạnh các dòng trong danh sách để bù đắp yêu cầu (Bởi giả định từ PRD).
- **Dependency:** Feature 3.2
- **Acceptance Criteria:**
  - Bấm nút xóa ở dòng nào -> Giao dịch dòng đó mất.
  - Dashboard load lại, hiển thị tổng Thu/Chi/Số dư cập nhật ngay lập tức.
- **Tasks:**
  - [ ] Bổ sung nút "Xóa" (icon thùng rác/hoặc text Xóa) trên mỗi row list (sử dụng Form POST method `DELETE` hidden field hoặc param thường).
  - [ ] Tạo `@PostMapping` (vì form html thường chỉ Get/Post) xử lý endpoint xóa id.
  - [ ] Redirect về `/` sau khi gọi Service delete.

### Feature 3.5: Cập Nhật Giao Dịch (Sửa Thông Tin)
- **Mô tả:** Có Nút "Sửa" cạnh các dòng trong danh sách để người dùng có thể cập nhật lại thông tin giao dịch nếu nhập sai (số tiền, ngày, loại, mô tả).
- **Dependency:** Feature 3.2, Feature 3.3
- **Acceptance Criteria:**
  - Bấm nút "Sửa" ở một dòng -> Mở form sửa (sub-page hoặc modal) chứa sẵn thông tin của giao dịch đó.
  - Sửa thông tin và bấm Lưu -> Giao dịch được cập nhật thành công vào CSDL.
  - Dashboard load lại với dữ liệu và thống kê mới.
- **Tasks:**
  - [ ] Bổ sung nút "Sửa" trên mỗi dòng lịch sử (cùng text/icon).
  - [ ] Xây dựng controller `@GetMapping` và View HTML để load form chỉnh sửa (tái sử dụng form hoặc sub-page).
  - [ ] Gọi hàm Service lưu/cập nhật và redirect về `/`.

---

## Epic 4: Xác thực & Bảo mật Nội dung (Authentication)
Bảo vệ ứng dụng bằng cách yêu cầu đăng nhập trước khi có thể đọc hay chỉnh sửa bất kỳ dữ liệu nào.

### Feature 4.1: Cơ chế Login và Logout
- **Mô tả:** Triển khai một tính năng Đăng nhập dạng Form-based sử dụng Spring Security cơ bản. Áp dụng Single-user username/password ở mức đơn giản nhất (ví dụ: in-memory user hoặc đọc từ `application.properties`).
- **Dependency:** Epic 1, Epic 3
- **Acceptance Criteria:**
  - Ai cố tình gõ link trực tiếp (như `/`) không qua đăng nhập đều bị trình duyệt chặn và tự redirect về trang `/login`. 
  - Sau khi đăng nhập đúng, redirect vào màn hình Dashboard.
  - Có nút Đăng xuất (Logout) bố trí tại thanh điều hướng Navbar. Bấm đăng xuất sẽ về mặt /login và xóa phiên đăng nhập.
- **Tasks:**
  - [ ] Bổ sung Dependency Spring Security vào file `.gradle`.
  - [ ] Cấu hình class `SecurityConfig` set up đăng nhập cơ bản và phân quyền chặn wildcard `/*`.
  - [ ] Tạo template HTML Frontend cho form `/login` đẹp và trực quan với hệ thống hiện tại.
  - [ ] Tích hợp nút Logout vào UI file `header.html` (Thymeleaf/Spring Security `sec:authorize`).

---

*Ghi chú: Bản Backlog này đáp ứng đủ phạm vi MVP, loại bỏ các phần "Out of Scope" như báo cáo biểu đồ tại PRD. Các feature này đủ nhỏ để code trong một đến hai Session Dev.*
