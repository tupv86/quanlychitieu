# Product Requirements Document (PRD)

## 1. Tổng quan Sản phẩm (Product Overview)
Ứng dụng Quản lý Chi tiêu Cá nhân là một ứng dụng web dạng MVP (Minimum Viable Product). Ứng dụng giúp người dùng ghi chép, theo dõi biến động tài chính hằng ngày một cách đơn giản, tập trung vào việc tính toán nhanh tổng thu, tổng chi và số dư hiện tại.

## 2. Ngăn xếp Công nghệ (Tech Stack)
- **Backend:** Spring Boot (Java)
- **Frontend:** Thymeleaf (Template engine), Bootstrap 5 (UI, Responsive)
- **Database:** H2 Database (Phù hợp cho MVP, dễ cài đặt)

## 3. Mục tiêu & Tính năng Cốt lõi
*Dựa trên các yêu cầu trực tiếp:*
1. **Quản lý giao dịch:** Cho phép người dùng nhập nhanh các khoản thu và chi hằng ngày (bao gồm: số tiền, ngày tháng, mô tả tóm tắt), đồng thời có thể cập nhật, chỉnh sửa hoặc xóa giao dịch bị sai.
2. **Liệt kê (Listing):** Xem danh sách tất cả các giao dịch đã nhập một cách rõ ràng, dễ nhìn.
3. **Lọc dữ liệu:** Cung cấp bộ lọc theo tháng để xem biến động tài chính trong các khoảng thời gian cụ thể.
4. **Thống kê tổng quan:** Hiển thị tức thời trên màn hình: 
   - Tổng thu (Total Income)
   - Tổng chi (Total Expense)
   - Số dư (Balance = Tổng thu - Tổng chi)

## 4. Phạm vi MVP (MVP Scope)
**Bao gồm (In Scope):**
- Giao diện người dùng Web phản hồi tốt trên cả Desktop và Mobile cơ bản (nhờ Bootstrap 5).
- Các chức năng CRUD (Tạo, Xem, Cập nhật, Xóa) căn bản của Giao dịch dựa trên yêu cầu.
- Lọc danh sách giao dịch đơn giản theo tiêu chí "Tháng/Năm".
- Tính toán tổng thu/chi/số dư bằng logic server-side đơn giản.
- Tính năng Đăng nhập (Authentication) cơ bản để bảo vệ dữ liệu cá nhân.

**Không bao gồm (Out of Scope cho MVP 1.0):**
- Biểu đồ và báo cáo chỉ số phức tạp, dự báo tài chính.
- Hệ thống quản lý nhiều người dùng (Multi-tenant) và Phân quyền phức tạp (Authorization/Roles).
- Tính năng xuất/nhập tệp (Export/Import) từ Excel/CSV.
- Đa tiền tệ, tỷ giá hối đoái.
- Nhắc nhở, thông báo qua email hoặc SMS.

## 5. Các Giả định & Vấn đề còn thiếu (Assumptions & Missing Requirements)
Trong quá trình lên tài liệu, có một số điểm chưa được làm rõ và đã được tạm giả định như sau (Cần xác nhận lại với Product Owner/User):
1. **Tài khoản người dùng (Authentication):** Mặc dù MVP này thiết kế cho một **Single-user** (mọi người dùng dùng chung tài khoản/dữ liệu), nhưng nay đã bổ sung yêu cầu **bắt buộc phải có form Đăng nhập (Login)** để thao tác. Việc này nhằm ngăn chặn người lạ có thể tùy tiện truy cập nếu ứng dụng được public ra internet.
2. **Chỉnh sửa / Xóa giao dịch (Edit/Delete):** Đã bổ sung chính thức tính năng Cập nhật (Sửa) và Xóa để người dùng có thể điều chỉnh khoản nhập sai, đảm bảo sự chính xác của số liệu thống kê.
3. **Phân loại giao dịch (Categories):** Người dùng có cần chia các khoản chi theo danh mục (Ăn uống, Giải trí, Lương...) hay không? Giả định MVP hiện tại **không cần tạo bảng Danh mục riêng**, mà chỉ dùng một trường `type` (THU / CHI) và `mô tả` ghi chú là đủ.
4. **Lưu trữ dữ liệu (Database Persistence):** H2 thường mặc định chạy *in-memory* (dữ liệu biến mất khi sập/tắt server). Giả định ứng dụng sẽ phải **cấu hình H2 lưu xuống một file cục bộ** (Local file) để tránh mất dữ liệu của người dùng sau mỗi lần restart Spring Boot.
5. **Đơn vị tiền tệ (Currency):** Giả định chỉ định dạng hỗ trợ cứng một loại tiền tệ chính (vd: VNĐ) cho hệ thống, chưa có định dạng thập phân lẻ ($10.99) nếu dùng ngoại tệ.
