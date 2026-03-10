# User Requirements Document (URD)

## 1. Vai trò Người dùng (User Roles)
- **Người dùng cá nhân (Single User):** Đối tượng chính sử dụng ứng dụng để ghi chép và theo dõi tài chính hằng ngày của bản thân. Phiên bản MVP chạy đơn lẻ nhưng nay yêu cầu phải có tính năng Đăng nhập (Authentication) để bảo mật thông tin tài chính trước những người không được phép.

## 2. Câu chuyện Người dùng (User Stories)

**Nhóm tính năng Xác thực Cơ bản (Authentication):**
- **US00:** Là một người dùng, tôi muốn khi vừa truy cập vào ứng dụng sẽ xuất hiện một form Đăng nhập (Login) yêu cầu điền tên tài khoản và mật khẩu, để đảm bảo không ai khác dòm ngó được các khoản chi tiêu của tôi.
- **US00b:** Là một người dùng, tôi muốn có nút "Đăng xuất" (Logout) trên màn hình chính để thoát khỏi hệ thống một cách an toàn.

**Nhóm tính năng Quản lý giao dịch (Transaction Management):**
- **US01:** Là một người dùng, tôi muốn có thể thêm nhanh một khoản **THU** (ví dụ: tiền lương, tiền thưởng) với các thông tin: *Số tiền, Ngày tháng, và Mô tả* để ghi nhận dòng tiền vào.
- **US02:** Là một người dùng, tôi muốn có thể thêm nhanh một khoản **CHI** (ví dụ: tiền ăn uống, mua sắm) với các thông tin: *Số tiền, Ngày tháng, và Mô tả* để lưu vết tiêu xài của mình.
- **US03:** Là một người dùng, tôi muốn xem lại danh sách tất cả các khoản thu/chi đã thêm trong một giao diện liệt kê rõ ràng, dễ nhìn.
- **US04:** Là một người dùng, tôi muốn có khả năng **Cập nhật/Chỉnh sửa (Update/Edit)** thông tin của một giao dịch nếu lỡ nhập sai số tiền, ngày tháng, ghi chú hoặc loại giao dịch.
- **US05:** Là một người dùng, tôi muốn có khả năng **Xóa (Delete)** một giao dịch nếu bị nhập trùng hoặc không muốn lưu giao dịch đó nữa.

**Nhóm tính năng Thống kê & Kiểm soát:**
- **US06:** Là một người dùng, tôi muốn nhìn thấy ngay tức thì các chỉ số như: **Tổng con số Thu**, **Tổng con số Chi**, và **Số dư còn lại** ngay khi truy cập trang chính để nắm bắt nhanh tình hình tài chính của mình.
- **US07:** Là một người dùng, tôi muốn có thể thay đổi khoảng thời gian quan sát bằng cách **Lọc theo một Tháng/Năm cụ thể** (vd: Tháng 03/2026), sao cho danh sách giao dịch và các con số Tổng thu/chi/số dư được tính toán lại chỉ dành riêng cho tháng đó.

## 3. Luồng trải nghiệm người dùng dự kiến (Expected User Workflow)
1. **Truy cập:** Mở ứng dụng qua trình duyệt web trên điện thoại hoặc máy tính. Nếu chưa đăng nhập, ứng dụng hiện màn hình Login.
2. **Đăng nhập:** Nhập tài khoản và mật khẩu hợp lệ (được cấu hình sẵn) rồi nhấn Đăng nhập để đi tiếp.
3. **Dashboard (Màn hình chính):** 
   - Ngay trên đầu hiển thị khu vực "Thống kê": Tiền đang có, Tháng này đã tiêu bao nhiêu, Đã thu bao nhiêu. Mặc định là tháng hiện tại.
   - Bên dưới là Danh sách các giao dịch (lịch sử) của thời gian tương ứng.
3. **Thêm thao tác mới:**
   - Người dùng click vào nút "Thêm giao dịch mới".
   - Một popup/form xuất hiện hỏi `Thu` hay `Chi`, yêu cầu nhập `Số tiền` và `Ghi chú`.
   - Bấm lưu lại, form đóng xuống, danh sách lịch sử có thêm dòng mới, con số thống kê Tự động nhảy số mới nhất.
4. **Tra cứu lịch sử:** 
   - Người dùng sử dụng một bộ Select box/Dropdown để chọn xem "Tháng trước".
   - Danh sách và các con số chuyển đổi sang kết quả của tháng trước đó.

## 4. Các yêu cầu phi chức năng (Non-Functional Requirements)
***(Từ góc độ của Người dùng cá nhân)***
- **Khả năng sử dụng đa thiết bị (Responsive):** Ứng dụng phải dễ nhìn, chữ đủ to và bố cục không bị vỡ khi dùng điện thoại để ghi chép các khoản chi tiêu hằng ngày (Bootstrap sẽ đảm nhiệm thiết kế này).
- **Sự tối giản & Nhanh chóng (Simplicity & Speed):** Thao tác nhập liệu diễn ra càng ít bước càng tốt. Không có các trường thông tin dư thừa. Tốc độ chuyển trang / cập nhật con số phải ngay lập tức.
- **Toàn vẹn dữ liệu cá nhân (Data Persistence):** Mặc dù là ứng dụng nhỏ (H2 database), nhưng người dùng hy vọng sau khi tắt máy hôm sau mở lên thì những khoản mình nhập ngày hôm qua vẫn còn nguyên (Cần cấu hình lưu file dữ liệu xuống ổ cứng cục bộ - Local File).
