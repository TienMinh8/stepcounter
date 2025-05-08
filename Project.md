# StepCounter - Ứng dụng đếm bước chân với Gamification

## Tổng quan

StepCounter là ứng dụng Android giúp người dùng theo dõi số bước chân và quãng đường di chuyển hàng ngày. Ứng dụng lưu lại lịch sử quá trình chạy/đi bộ, cung cấp bảng xếp hạng hàng tuần, và sử dụng gamification (xu thưởng, thành tích) để tăng tính gắn kết của người dùng. Nguồn doanh thu chính của ứng dụng đến từ quảng cáo.

## Kiến trúc hệ thống

Hệ thống được xây dựng theo kiến trúc MVVM (Model-View-ViewModel) với các thành phần chính sau:

- Frontend: Kotlin, Android Jetpack Components, XML layouts
- Backend: SQLite/Room Persistence Library cho lưu trữ local
- Gamification: Hệ thống thành tích, pet ảo, thử thách
- Sensors: SensorManager API để đếm bước chân và nhận diện hoạt động
- Analytics: MPAndroidChart cho biểu đồ và phân tích
- Notification: AlarmManager và NotificationManager

### Sơ đồ kiến trúc

Các thành phần kết nối với nhau theo sơ đồ được mô tả trong [Diagram.md](Diagram.md).

## Thành phần chính

- **UI Layer**: Giao diện người dùng và tương tác - Activities và Fragments
- **ViewModel Layer**: Xử lý logic nghiệp vụ và quản lý trạng thái
- **Repository Layer**: Cấp trung gian giữa ViewModel và Data sources
- **Data Layer**: Xử lý dữ liệu (Room Database, SharedPreferences)
- **Service Layer**: StepCounterService chạy nền để đếm bước
- **Gamification System**: Quản lý thành tích, xu thưởng và pet ảo
- **Notification System**: Gửi thông báo động lực và nhắc nhở

## Quy trình làm việc

1. Service Layer nhận dữ liệu từ SensorManager để đếm bước chân
2. Data Layer lưu trữ dữ liệu bước chân, quãng đường và hoạt động
3. Repository Layer cung cấp dữ liệu cho ViewModel
4. ViewModel Layer xử lý logic và cập nhật UI
5. Gamification System theo dõi tiến độ và trao thưởng
6. Notification System gửi thông báo dựa trên hoạt động người dùng
7. UI Layer hiển thị dữ liệu, thành tích và tiến độ người dùng

## Hướng dẫn phát triển

- [Hướng dẫn cài đặt](Instruction.md)
- [API Sensors](instructions/API_Docs.md)
- [Hướng dẫn triển khai](instructions/Deployment.md)

## Tài liệu tham khảo

- [Decisions.md](Decisions.md): Các quyết định thiết kế quan trọng
- [Changelog.md](Changelog.md): Lịch sử thay đổi
- [Codebase.md](Codebase.md): Tổng quan về cấu trúc code
