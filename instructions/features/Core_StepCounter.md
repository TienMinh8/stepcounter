# Tính Năng Đếm Bước Chân & Quãng Đường

## Tổng Quan

Đây là tính năng cốt lõi của ứng dụng StepCounter, cho phép theo dõi và ghi lại số bước chân và quãng đường di chuyển của người dùng trong thời gian thực. Tính năng này sử dụng cảm biến bước chân có sẵn trên thiết bị Android thông qua SensorManager API.

## Kiến Trúc

Tính năng này tuân theo mô hình MVVM với các thành phần chính:

1. **UI Layer**: Activity và Fragment hiển thị số bước chân và quãng đường
2. **ViewModel**: Quản lý trạng thái và xử lý logic nghiệp vụ
3. **Repository**: Cầu nối giữa ViewModel và Data sources
4. **Service**: StepCounterService chạy nền để đếm bước chân
5. **Data Layer**: Room Database lưu trữ dữ liệu bước chân

## Thành Phần Chính

### Activity chính hiển thị số bước, quãng đường

Màn hình chính của ứng dụng hiển thị:
- Số bước chân trong ngày hiện tại
- Quãng đường đã đi được (km hoặc dặm)
- Tiến độ so với mục tiêu (thanh tiến độ tròn)
- Các thông tin bổ sung (calo, thời gian hoạt động)

**Yêu cầu UI:**
- Thiết kế hiện đại, dễ đọc
- Hiển thị số liệu lớn, rõ ràng
- Sử dụng animating counter khi số liệu thay đổi
- Thanh tiến độ trực quan (có thể tùy chỉnh màu sắc)
- Tab/navigation để chuyển giữa các chế độ xem (ngày/tuần/tháng)

### Service chạy nền để đếm bước chân

StepCounterService sẽ chạy liên tục ở nền để đếm bước chân, ngay cả khi ứng dụng đã đóng.

**Yêu cầu chức năng:**
- Đăng ký lắng nghe sự kiện từ cảm biến bước chân
- Xử lý sự kiện bước chân từ TYPE_STEP_COUNTER sensor
- Khởi động tự động khi khởi động thiết bị
- Xử lý restart service khi bị kill bởi hệ thống
- Tối ưu tiêu thụ pin (sử dụng Foreground Service với thông báo liên tục)
- Xử lý các trường hợp ngoại lệ (thiết bị không có sensor)

**Cơ chế đếm bước chân:**
- Sử dụng TYPE_STEP_COUNTER sensor từ SensorManager
- Xử lý reset counter khi khởi động lại thiết bị
- Tính toán số bước trong ngày hiện tại
- Cập nhật repository với số bước mới nhất
- Cung cấp tùy chọn reset số bước vào thời điểm cụ thể (ví dụ: 0h)

### Lớp lưu trữ dữ liệu bước chân

Dữ liệu bước chân cần được lưu trữ để tham chiếu lịch sử và phân tích.

**Model dữ liệu:**
- Steps entity: ngày, tổng số bước, quãng đường, calo tiêu hao
- Activity entity: khoảng thời gian, loại hoạt động, số bước, quãng đường
- StepGoal entity: mục tiêu hàng ngày, tuần, tháng

**Room Database:**
- Cung cấp DAO (Data Access Object) cho phép CRUD operations
- Sử dụng LiveData hoặc Flow để quan sát thay đổi dữ liệu
- Tích hợp với ViewModel thông qua Repository pattern

## Tài Nguyên Cần Thiết

1. **Tài nguyên hình ảnh:**
   - Icon cho bước chân, quãng đường
   - Hình động hoặc animation cho thanh tiến độ
   - Background và theme elements

2. **Layout:**
   - Màn hình chính (main_activity.xml)
   - Fragment hiển thị bước chân (step_counter_fragment.xml)
   - Notification layout cho service nền

3. **Strings và localization:**
   - Các chuỗi văn bản cho UI
   - Thông báo và gợi ý
   - Hỗ trợ đa ngôn ngữ cơ bản (EN, VI)

## Quy Trình Triển Khai

1. **Thiết lập project và permissions:**
   - Thêm các permission cần thiết vào AndroidManifest
   - Cài đặt thư viện Room và Lifecycle components

2. **Triển khai data layer:**
   - Tạo entities và DAO
   - Thiết lập Room Database
   - Tạo Repository pattern

3. **Triển khai service:**
   - Tạo StepCounterService extends Foreground Service
   - Kết nối với SensorManager API
   - Xử lý lifecycle của service (start, stop, restart)

4. **Triển khai ViewModel và UI:**
   - Tạo ViewModel để quản lý dữ liệu và logic
   - Xây dựng UI cho activity chính
   - Kết nối ViewModel với UI thông qua data binding

5. **Tính năng quãng đường:**
   - Triển khai thuật toán tính quãng đường dựa trên số bước và chiều dài sải chân
   - Cho phép người dùng tùy chỉnh chiều dài sải chân

6. **Tối ưu hóa:**
   - Test trên nhiều thiết bị để đảm bảo chính xác
   - Tối ưu hóa tiêu thụ pin
   - Xử lý các trường hợp ngoại lệ

## Các Lưu Ý Quan Trọng

1. **Độ chính xác:**
   - Cần đảm bảo sai số < 5% so với số bước thực tế
   - Đối chiếu với các thiết bị khác (Fitbit, Garmin) để kiểm tra

2. **Tiêu thụ pin:**
   - Giám sát và tối ưu tiêu thụ pin (sử dụng Battery Historian)
   - Không vượt quá 5% pin/ngày cho service nền

3. **Khả năng tương thích:**
   - Kiểm tra trên các thiết bị không có cảm biến bước chân tích hợp
   - Cung cấp giải pháp thay thế (ví dụ: accelerometer) nếu cần

4. **Trải nghiệm người dùng:**
   - Hiển thị thông tin rõ ràng, dễ hiểu
   - Cung cấp feedback trực quan khi đạt mục tiêu
   - Đảm bảo UI cập nhật kịp thời khi có bước chân mới

## Tiêu Chí Kiểm Thử

1. **Chính xác:**
   - Kiểm tra độ chính xác bằng cách đếm thủ công và so sánh
   - Kiểm tra trên nhiều thiết bị khác nhau
   - Kiểm tra sau các lần khởi động lại thiết bị

2. **Hiệu suất:**
   - Kiểm tra tiêu thụ pin trong 24h
   - Kiểm tra tải CPU và bộ nhớ
   - Kiểm tra hiệu suất khi ứng dụng chạy nền trong thời gian dài

3. **Độ ổn định:**
   - Kiểm tra service có tự động khởi động lại sau khi bị kill
   - Kiểm tra khả năng phục hồi sau khi thiết bị hết pin
   - Kiểm tra các trường hợp lỗi cảm biến

## Tích Hợp Với Các Tính Năng Khác

- **Gamification:** Cung cấp dữ liệu bước chân cho hệ thống thành tích
- **Challenges:** Theo dõi tiến độ thử thách dựa trên số bước
- **Analytics:** Cung cấp dữ liệu cho phân tích và biểu đồ
- **Widgets:** Hiển thị số bước hiện tại trên widget
- **Health Features:** Sử dụng số bước để tính calo tiêu hao

## Tài Liệu Tham Khảo

- [Android SensorManager Documentation](https://developer.android.com/reference/android/hardware/SensorManager)
- [Android Foreground Service Guide](https://developer.android.com/guide/components/foreground-services)
- [Room Persistence Library](https://developer.android.com/training/data-storage/room)
- [Battery Optimization Best Practices](https://developer.android.com/topic/performance/power) 