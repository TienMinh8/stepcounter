# Hệ Thống Thử Thách & Mục Tiêu

## Tổng Quan

Hệ thống thử thách và mục tiêu là tính năng quan trọng giúp người dùng duy trì động lực và theo đuổi các mục tiêu vận động cụ thể trong ứng dụng StepCounter. Tính năng này cung cấp các thử thách đa dạng, từ mục tiêu hàng ngày đến thử thách dài hạn, tạo nên trải nghiệm gamification hấp dẫn và mang tính cá nhân hóa.

## Kiến Trúc

Hệ thống thử thách tuân theo mô hình MVVM với các thành phần chính:

1. **ChallengeManager**: Quản lý các thử thách, cập nhật trạng thái
2. **GoalTracker**: Theo dõi tiến độ mục tiêu của người dùng
3. **ChallengeRepository**: Lưu trữ và truy xuất dữ liệu thử thách
4. **RewardService**: Xử lý phần thưởng khi hoàn thành thử thách

## Phân Loại Thử Thách

### 1. Thử Thách Hàng Ngày

- **Mục tiêu số bước**: Đạt X bước trong ngày
  - Cơ bản (5.000 bước)
  - Trung bình (10.000 bước)
  - Cao cấp (15.000 bước)
- **Thời điểm cụ thể**: Đi bộ X bước vào thời điểm cụ thể
  - Người dậy sớm (1.000 bước trước 7h)
  - Người về đêm (1.000 bước sau 20h)
- **Tốc độ**: Đạt X bước trong khoảng thời gian nhất định
  - Sprint (1.000 bước trong 10 phút)
  - Power walk (5.000 bước trong 1 giờ)

### 2. Thử Thách Tuần/Tháng

- **Tuần năng động**: Đạt mục tiêu số bước Y ngày trong tuần
- **Tăng tốc**: Mỗi ngày trong tuần tăng số bước 500 so với ngày trước
- **Mục tiêu tháng**: Đạt tổng X bước trong tháng
- **Kiên trì**: Không bỏ lỡ mục tiêu Y ngày liên tiếp

### 3. Thử Thách Đặc Biệt

- **Thử thách theo mùa**: Các thử thách đặc biệt theo dịp lễ, mùa
- **Thử thách marathon**: Đạt 42.195 bước trong 1 tuần (tương đương marathon)
- **Thử thách địa điểm**: Đạt X bước trong khoảng thời gian Y giờ
- **Thử thách đột phá**: Vượt qua kỷ lục cá nhân về số bước trong ngày

### 4. Thử Thách Cá Nhân Hóa

- **Mục tiêu tùy chỉnh**: Người dùng tự thiết lập mục tiêu số bước
- **Lịch trình tùy chỉnh**: Tạo lịch trình đi bộ cá nhân cho tuần/tháng
- **Thử thách dựa trên lịch sử**: Tự động đề xuất thử thách dựa trên dữ liệu trước đây

## Hệ Thống Phần Thưởng

### Cấu Trúc Phần Thưởng

- **Xu thưởng**: Nhận xu để sử dụng trong cửa hàng
- **Huy hiệu đặc biệt**: Huy hiệu độc quyền khi hoàn thành thử thách
- **Multiplier**: Tăng hệ số nhận xu trong khoảng thời gian giới hạn
- **Vật phẩm đặc biệt**: Mở khóa vật phẩm độc quyền cho pet/avatar

### Cơ Chế Trao Thưởng

- Hiển thị animation và thông báo khi hoàn thành thử thách
- Tự động cộng phần thưởng vào tài khoản người dùng
- Hiển thị lịch sử thành tích và phần thưởng

## Giao Diện Người Dùng

### Màn Hình Thử Thách

- **Tab Thử Thách Hiện Tại**: Hiển thị các thử thách đang tham gia
  - Tiến độ hiện tại (thanh tiến độ trực quan)
  - Thời gian còn lại
  - Phần thưởng
- **Tab Thử Thách Có Sẵn**: Danh sách thử thách có thể tham gia
  - Mô tả thử thách
  - Độ khó (dễ, trung bình, khó)
  - Phần thưởng dự kiến
- **Tab Thành Tích**: Hiển thị thử thách đã hoàn thành
  - Ngày hoàn thành
  - Phần thưởng đã nhận
  - Thống kê (số thử thách hoàn thành, tỷ lệ thành công)

### Thiết Kế Trực Quan

- **Card view** cho mỗi thử thách
- **Icon đặc trưng** cho từng loại thử thách
- **Màu sắc** phân biệt độ khó và trạng thái
- **Animation** khi tham gia và hoàn thành thử thách

## Tính Năng Đề Xuất

### Hệ Thống Đề Xuất Thử Thách

- Phân tích dữ liệu người dùng để đề xuất thử thách phù hợp
- Xem xét lịch sử hoạt động, thời gian rảnh, sở thích
- Cung cấp thử thách với độ khó tăng dần để duy trì động lực

### Tùy Chỉnh Thử Thách

- Cho phép người dùng tùy chỉnh các thông số của thử thách
- Cung cấp template để người dùng dễ dàng tạo thử thách riêng
- Lưu và chia sẻ thử thách tùy chỉnh

## Tài Nguyên Cần Thiết

1. **UI Components**:
   - Challenge card layout
   - Progress indicators
   - Reward display components
   - Challenge creation UI

2. **Hình ảnh**:
   - Icon cho các loại thử thách
   - Badge designs cho thành tích
   - Animation hoàn thành thử thách

3. **Database**:
   - Challenge entity
   - UserChallenge entity (liên kết người dùng với thử thách)
   - ChallengeProgress entity
   - ChallengeReward entity

## Quy Trình Triển Khai

1. **Thiết kế cơ sở dữ liệu**:
   - Tạo các entity, DAO cho Challenge system
   - Thiết lập quan hệ giữa User và Challenge
   - Xây dựng repository pattern

2. **Xây dựng ChallengeManager**:
   - Tạo logic quản lý vòng đời thử thách
   - Thiết lập hệ thống đánh giá tiến độ
   - Tích hợp với StepRepository để lấy dữ liệu

3. **Xây dựng RewardService**:
   - Triển khai cơ chế trao thưởng
   - Tích hợp với CurrencyManager
   - Tạo notification cho phần thưởng

4. **Triển khai UI**:
   - Tạo các màn hình hiển thị thử thách
   - Xây dựng luồng tham gia thử thách
   - Thiết kế hiển thị tiến độ và phần thưởng

5. **Triển khai Recommendation Engine**:
   - Xây dựng thuật toán đề xuất thử thách
   - Tạo cơ chế đánh giá sở thích người dùng
   - Thiết lập hệ thống tạo thử thách tự động

## Các Lưu Ý Quan Trọng

1. **Cân Bằng Hệ Thống**:
   - Độ khó thử thách phù hợp với khả năng người dùng
   - Phần thưởng tương xứng với nỗ lực
   - Đảm bảo người mới vẫn có thể hoàn thành thử thách

2. **Trải Nghiệm Người Dùng**:
   - UI rõ ràng, dễ hiểu
   - Feedback kịp thời về tiến độ
   - Không quá nhiều thông báo gây phiền nhiễu

3. **Tối Ưu Hiệu Suất**:
   - Cập nhật tiến độ không làm chậm ứng dụng
   - Tối ưu truy vấn database
   - Xử lý offline, đồng bộ khi có kết nối

## Tiêu Chí Kiểm Thử

1. **Chức năng**:
   - Thử thách được tạo và gán chính xác
   - Tiến độ cập nhật kịp thời
   - Phần thưởng được trao đúng khi hoàn thành

2. **Hiệu suất**:
   - Tốc độ tải danh sách thử thách
   - Hiệu suất khi có nhiều thử thách cùng lúc
   - Tải CPU và bộ nhớ trong giới hạn

3. **Trải nghiệm**:
   - Dễ dàng tìm và tham gia thử thách
   - Thông tin tiến độ rõ ràng, trực quan
   - Cảm giác thành tựu khi hoàn thành

## Tích Hợp Với Các Tính Năng Khác

- **Step Counter Core**: Lấy dữ liệu số bước đi để đánh giá tiến độ
- **Gamification**: Tích hợp hệ thống xu thưởng và huy hiệu
- **Notifications**: Gửi thông báo nhắc nhở và chúc mừng
- **Analytics**: Thu thập dữ liệu về tỷ lệ hoàn thành để cải thiện hệ thống

## Phát Triển Trong Tương Lai

- Tích hợp thử thách nhóm
- Tạo hệ thống giải đấu/sự kiện theo thời gian thực
- Thử thách dựa trên vị trí địa lý
- Tích hợp với các ứng dụng sức khỏe khác

## Tài Liệu Tham Khảo

- [Behavioral Design for Habit Formation](https://www.nngroup.com/articles/habit-forming-design/)
- [Gamification Design in Health Apps](https://www.mdpi.com/2227-9032/7/2/65)
- [Challenge-Based Learning Framework](https://challengebasedlearning.org/framework/)
- [Effective Goal Setting in Fitness Apps](https://www.ncbi.nlm.nih.gov/pmc/articles/PMC6520758/) 