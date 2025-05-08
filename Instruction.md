# Hướng dẫn phát triển StepCounter

## Tổng quan

Tài liệu này chứa hướng dẫn chi tiết để phát triển ứng dụng StepCounter. Hãy đọc [Project.md](Project.md) trước để hiểu tổng quan về dự án.

## Cài đặt môi trường phát triển

1. Cài đặt các công cụ cần thiết:

   - Android Studio (bản mới nhất)
   - Java Development Kit (JDK) 11 hoặc cao hơn
   - Git cho quản lý phiên bản

2. Thiết lập cấu hình:

   - Clone repository từ Git
   - Mở dự án trong Android Studio
   - Đồng bộ Gradle dependencies

3. Khởi động ứng dụng:
   - Kết nối thiết bị Android hoặc khởi động Emulator
   - Chạy ứng dụng bằng nút Run trong Android Studio

## Các tính năng cần triển khai

### 1. Tính năng Cơ bản - Đếm bước chân & Quãng đường ❌

**Mô tả**: Theo dõi và ghi lại số bước chân, quãng đường di chuyển của người dùng theo thời gian thực.

**Thành phần**:

- Activity chính hiển thị số bước, quãng đường ❌
- Service chạy nền để đếm bước chân ❌
- Lớp lưu trữ dữ liệu bước chân ❌

**Yêu cầu chức năng**:

- Đếm bước chân chính xác với sai số < 5% ❌
- Tính toán quãng đường dựa trên chiều cao/sải chân người dùng ❌
- Hiển thị dữ liệu theo thời gian thực ❌
- Vẫn tiếp tục đếm khi ứng dụng chạy nền ❌

**Ràng buộc**:

- Tối ưu tiêu thụ pin, không quá 5% pin/ngày ❌
- Hoạt động trên Android 8.0 trở lên ❌

**Tham chiếu**:

- [Android SensorManager API](https://developer.android.com/reference/android/hardware/SensorManager)

**Tiêu chí hoàn thành**:

- Đếm bước chân chính xác trên nhiều thiết bị khác nhau ❌
- Service chạy ổn định ở nền không bị kill bởi hệ thống ❌

### 2. Hệ thống thành tích & Gamification ❌

**Mô tả**: Tạo động lực cho người dùng thông qua hệ thống thành tích, phần thưởng và pet ảo.

**Thành phần**:

- Hệ thống huy hiệu theo mốc số bước, chuỗi ngày, thời gian, tốc độ ❌
- Pet ảo/Avatar phát triển theo thành tích ❌
- Cây trồng ảo phát triển theo số bước ❌
- Hệ thống xu thưởng và cửa hàng ảo ❌

**Yêu cầu chức năng**:

- Tự động trao thưởng huy hiệu khi đạt mốc (5k, 10k, 30k bước; 3, 7, 30, 100 ngày liên tiếp) ❌
- Pet ảo/cây trồng ảo phát triển qua các giai đoạn theo số bước chân ❌
- Hệ thống xu thưởng để mở khóa theme, avatar, vật phẩm trong cửa hàng ảo ❌

**Ràng buộc**:

- Hoạt động offline, đồng bộ online khi có kết nối ❌
- Đảm bảo chống gian lận (không thể tăng bước chân giả) ❌

**Tham chiếu**:

- [Material Design - Gamification Patterns](https://material.io/design)

**Tiêu chí hoàn thành**:

- Đầy đủ 15+ loại huy hiệu khác nhau ❌
- Ít nhất 5 giai đoạn phát triển cho pet ảo/cây trồng ❌
- Cửa hàng với tối thiểu 20 vật phẩm khác nhau ❌

### 3. Hệ thống thử thách ❌

**Mô tả**: Cung cấp các thử thách cho người dùng để tăng sự tương tác và động lực.

**Thành phần**:

- Thử thách hàng ngày ngẫu nhiên ❌
- Thử thách theo chủ đề và cường độ ❌
- Hệ thống quest theo chuỗi ❌

**Yêu cầu chức năng**:

- Tạo thử thách ngẫu nhiên hàng ngày (ví dụ: 5.000 bước trước trưa) ❌
- Cung cấp thử thách theo chủ đề (tuần leo núi, tuần khám phá) ❌
- Tạo các chuỗi nhiệm vụ (hoàn thành nhiệm vụ này mở khóa nhiệm vụ tiếp theo) ❌

**Ràng buộc**:

- Thử thách được cân bằng dựa trên lịch sử hoạt động của người dùng ❌
- Không quá 3 thử thách hoạt động cùng lúc ❌

**Tham chiếu**:

- [Game Design - Quest Systems](https://developer.android.com/games)

**Tiêu chí hoàn thành**:

- Ngân hàng tối thiểu 30 loại thử thách khác nhau ❌
- Thuật toán tạo thử thách cân bằng với khả năng người dùng ❌
- Hệ thống theo dõi và hiển thị tiến độ thử thách trực quan ❌

### 4. Cá nhân hóa và Phân tích ❌

**Mô tả**: Cho phép người dùng tùy chỉnh trải nghiệm và xem phân tích về hoạt động của họ.

**Thành phần**:

- Hệ thống mục tiêu thông minh và tùy chỉnh ❌
- Biểu đồ phân tích xu hướng hoạt động ❌
- Tùy chỉnh giao diện và trải nghiệm ❌

**Yêu cầu chức năng**:

- Đề xuất mục tiêu thông minh dựa trên lịch sử hoạt động ❌
- Tạo biểu đồ trực quan theo ngày/tuần/tháng ❌
- So sánh với hoạt động trước đây (hôm qua, tuần trước, tháng trước) ❌
- Tùy chỉnh theme, màn hình chính, thông báo ❌

**Ràng buộc**:

- Lưu trữ dữ liệu tối thiểu 1 năm cho phân tích ❌
- Tối ưu hiệu suất khi xử lý dữ liệu lớn ❌

**Tham chiếu**:

- [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)
- [Android Room Persistence Library](https://developer.android.com/training/data-storage/room)

**Tiêu chí hoàn thành**:

- Các biểu đồ phân tích dễ hiểu, trực quan ❌
- Ít nhất 5 theme khác nhau để người dùng lựa chọn ❌
- Thuật toán đề xuất mục tiêu chính xác dựa trên dữ liệu người dùng ❌

### 5. Widget và Thông báo ❌

**Mô tả**: Cung cấp widget cho màn hình chính và hệ thống thông báo thông minh.

**Thành phần**:

- Widget hiển thị số bước hiện tại và tiến độ ❌
- Hệ thống thông báo động viên và nhắc nhở ❌
- Thông báo thành tích mới ❌

**Yêu cầu chức năng**:

- Widget với nhiều kích thước hiển thị số bước, thành tích ❌
- Thông báo động viên khi gần đạt mục tiêu ❌
- Thông báo nhắc nhở khi không hoạt động trong thời gian dài ❌
- Thông báo tự động khi đạt thành tích mới ❌

**Ràng buộc**:

- Widget cập nhật ít nhất 15 phút/lần để tiết kiệm pin ❌
- Không gửi quá 5 thông báo/ngày ❌

**Tham chiếu**:

- [Android App Widgets](https://developer.android.com/guide/topics/appwidgets)
- [Notification API](https://developer.android.com/guide/topics/ui/notifiers/notifications)

**Tiêu chí hoàn thành**:

- Widget hiển thị chính xác số bước và tiến độ ❌
- Hệ thống thông báo hoạt động đúng thời điểm ❌
- Người dùng có thể tùy chỉnh loại thông báo nhận được ❌

### 6. Các chế độ hoạt động ❌

**Mô tả**: Cung cấp nhiều chế độ theo dõi cho các hoạt động khác nhau.

**Thành phần**:

- Chế độ đi bộ thông thường ❌
- Chế độ chạy bộ với đo tốc độ ❌
- Chế độ leo cầu thang (đếm tầng) ❌
- Chế độ đạp xe (ước tính từ cảm biến) ❌

**Yêu cầu chức năng**:

- Cho phép người dùng chọn chế độ hoạt động ❌
- Ghi lại thông tin phù hợp với từng loại hoạt động ❌
- Tự động nhận diện loại hoạt động khi có thể ❌

**Ràng buộc**:

- Hoạt động chính xác trên đa dạng thiết bị ❌
- Tối ưu sử dụng pin trong các chế độ ❌

**Tham chiếu**:

- [Activity Recognition API](https://developers.google.com/location-context/activity-recognition)

**Tiêu chí hoàn thành**:

- Nhận diện và theo dõi chính xác ít nhất 4 loại hoạt động ❌
- Chuyển đổi mượt mà giữa các chế độ ❌
- Hiển thị thông tin phù hợp với từng loại hoạt động ❌

### 7. Storytelling ❌

**Mô tả**: Cung cấp câu chuyện tiến triển dựa trên số bước chân để tăng tính giải trí.

**Thành phần**:

- Hệ thống câu chuyện tiến triển dựa trên số bước ❌
- Các cốt truyện đa dạng để lựa chọn ❌
- Minh họa và hiệu ứng tương ứng với cốt truyện ❌

**Yêu cầu chức năng**:

- Mỗi chương trong câu chuyện mở khóa khi đạt mốc bước nhất định ❌
- Nhiều cốt truyện khác nhau để người dùng lựa chọn ❌
- Hình ảnh và nội dung minh họa cho câu chuyện ❌

**Ràng buộc**:

- Câu chuyện phù hợp với nhiều đối tượng người dùng ❌
- Tối ưu dung lượng lưu trữ cho nội dung truyện ❌

**Tham chiếu**:

- [Storytelling in UX](https://material.io/design/communication/storytelling.html)

**Tiêu chí hoàn thành**:

- Ít nhất 3 cốt truyện khác nhau, mỗi truyện có 10+ chương ❌
- Minh họa hấp dẫn cho mỗi chương truyện ❌
- Người dùng có thể theo dõi tiến độ câu chuyện dễ dàng ❌

### 8. Tính năng sức khỏe đơn giản ❌

**Mô tả**: Cung cấp thông tin cơ bản về sức khỏe dựa trên hoạt động và thói quen.

**Thành phần**:

- Theo dõi calo tiêu hao ❌
- Biểu đồ tiêu hao năng lượng ❌
- Hệ thống nhắc nhở thói quen lành mạnh ❌

**Yêu cầu chức năng**:

- Ước tính calo tiêu hao dựa trên hoạt động ❌
- Nhắc nhở uống nước dựa trên mức độ hoạt động ❌
- Nhắc nhở vận động sau thời gian ngồi lâu ❌
- Gợi ý giờ nghỉ ngơi dựa trên hoạt động trong ngày ❌

**Ràng buộc**:

- Không yêu cầu nhập dữ liệu phức tạp từ người dùng ❌
- Thuật toán ước tính calo có độ chính xác hợp lý ❌

**Tham chiếu**:

- [Health Connect API](https://developer.android.com/health-connect)

**Tiêu chí hoàn thành**:

- Ước tính calo chính xác với sai số < 15% ❌
- Hệ thống nhắc nhở thông minh không gây phiền toái ❌
- Hiển thị thông tin sức khỏe dễ hiểu cho người dùng ❌

## Quy trình làm việc

1. Chọn một tính năng chưa được triển khai (đánh dấu ❌)
2. Đánh dấu tính năng đang triển khai (⏳)
3. Triển khai theo các yêu cầu chức năng
4. Kiểm tra theo tiêu chí hoàn thành
5. Đánh dấu tính năng đã hoàn thành (✅)
6. Cập nhật Changelog.md với các thay đổi
7. Cập nhật Codebase.md với mô tả về các thành phần mới

## Legend

- ✅ Completed
- ⏳ In Progress
- ❌ Not Started
