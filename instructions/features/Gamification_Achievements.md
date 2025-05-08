# Hệ Thống Thành Tích & Gamification

## Tổng Quan

Hệ thống thành tích và gamification là tính năng quan trọng giúp tăng động lực và sự gắn kết của người dùng với ứng dụng StepCounter. Chức năng này bao gồm hệ thống huy hiệu, pet ảo/cây trồng, xu thưởng và cửa hàng ảo, nhằm tạo trải nghiệm thú vị và thúc đẩy người dùng duy trì thói quen đi bộ.

## Kiến Trúc

Hệ thống gamification sẽ tuân theo mô hình MVVM với các thành phần chính:

1. **AchievementManager**: Quản lý thành tích và huy hiệu
2. **VirtualPetManager**: Quản lý pet ảo/cây trồng
3. **CurrencyManager**: Quản lý xu thưởng
4. **StoreManager**: Quản lý cửa hàng ảo và vật phẩm

## Hệ Thống Huy Hiệu

### Phân Loại Huy Hiệu

1. **Huy hiệu số bước**:
   - Người mới (đạt 1.000 bước)
   - Người đi bộ tích cực (đạt 5.000 bước)
   - Vận động viên (đạt 10.000 bước)
   - Siêu sao (đạt 20.000 bước)
   - Kỷ lục gia (đạt 30.000 bước)

2. **Huy hiệu chuỗi ngày**:
   - Khởi đầu (3 ngày liên tiếp)
   - Đều đặn (7 ngày liên tiếp)
   - Kiên trì (14 ngày liên tiếp)
   - Nghiện đi bộ (30 ngày liên tiếp)
   - Huyền thoại (100 ngày liên tiếp)

3. **Huy hiệu thời gian**:
   - Người dậy sớm (1.000 bước trước 7h sáng)
   - Người đêm (1.000 bước sau 20h tối)
   - Tuần trăng tròn (đi bộ mỗi ngày trong tuần)
   - Mùa vàng (đi bộ mỗi ngày trong tháng)

4. **Huy hiệu tốc độ**:
   - Tốc hành (hoàn thành 1.000 bước trong 10 phút)
   - Cuồng phong (hoàn thành 5.000 bước trong 1 giờ)
   - Tia chớp (đạt tốc độ đi bộ 6km/h)

### Cơ Chế Trao Thưởng Huy Hiệu

- Hệ thống sẽ theo dõi liên tục tiến độ người dùng
- Khi đạt điều kiện, huy hiệu sẽ được tự động mở khóa
- Hiển thị thông báo và animation khi nhận huy hiệu
- Huy hiệu sẽ được lưu vào bộ sưu tập của người dùng
- Một số huy hiệu có cấp độ (đồng, bạc, vàng, bạch kim)

### Hiển Thị Huy Hiệu

- Trang "Bộ sưu tập huy hiệu" hiển thị tất cả huy hiệu
- Huy hiệu chưa đạt được sẽ hiển thị dưới dạng bóng mờ
- Hiển thị điều kiện để đạt được huy hiệu
- Cho phép chia sẻ huy hiệu (lưu ảnh hoặc chia sẻ)

## Pet Ảo/Cây Trồng

### Hệ Thống Pet Ảo

- Người dùng có thể chọn nuôi pet ảo hoặc trồng cây
- Pet ảo phát triển theo số bước chân của người dùng
- Các giai đoạn phát triển của pet:
  - Trứng/hạt giống (0-10.000 bước)
  - Nhỏ (10.000-50.000 bước)
  - Trung bình (50.000-100.000 bước)
  - Lớn (100.000-200.000 bước)
  - Trưởng thành (>200.000 bước)

### Tương Tác Với Pet/Cây

- Cho phép tương tác đơn giản (vuốt ve, cho ăn)
- Pet/cây sẽ thể hiện cảm xúc khác nhau
- Cung cấp animation khi tương tác
- Pet/cây sẽ "buồn" nếu người dùng không đi bộ

### Tùy Chỉnh Pet/Cây

- Cho phép đặt tên cho pet/cây
- Các loại pet/cây khác nhau có thể mở khóa
- Tùy chỉnh màu sắc, phụ kiện cho pet/cây

## Hệ Thống Xu Thưởng

### Cơ Chế Nhận Xu

- Hoàn thành mục tiêu hàng ngày: +10-50 xu
- Đạt huy hiệu mới: +20-100 xu (tùy huy hiệu)
- Duy trì chuỗi ngày: +5 xu/ngày (tăng theo độ dài chuỗi)
- Hoàn thành thử thách: +30-150 xu (tùy độ khó)
- Đạt mốc số bước đặc biệt: +bonus xu

### Quản Lý Xu

- Hiển thị số xu hiện tại trên màn hình chính
- Lịch sử giao dịch xu (nhận và chi tiêu)
- Animation hiệu ứng khi nhận xu

## Cửa Hàng Ảo

### Danh Mục Sản Phẩm

1. **Theme & Giao diện**:
   - Các bộ màu theme khác nhau
   - Các kiểu hiển thị màn hình chính
   - Các hiệu ứng hoạt ảnh tùy chỉnh

2. **Avatar & Hình đại diện**:
   - Các loại avatar nhân vật
   - Khung avatar đặc biệt
   - Badge hiển thị bên cạnh tên

3. **Pet & Phụ kiện**:
   - Các loại pet/cây mới
   - Trang phục cho pet
   - Nội thất/môi trường cho pet/cây

4. **Tính năng đặc biệt**:
   - Công cụ phân tích nâng cao
   - Hiệu ứng đặc biệt khi đạt mục tiêu
   - Widget tùy chỉnh

### Cơ Chế Mua Hàng

- Hiển thị các mặt hàng với giá bằng xu
- Preview trước khi mua
- Xác nhận mua và trừ xu
- Phân loại theo mới/phổ biến/giá

## Tài Nguyên Cần Thiết

1. **Hình ảnh**:
   - Bộ icon huy hiệu (tối thiểu 15-20 loại)
   - Sprite sheets cho pet ảo/cây trồng
   - Icon xu và các vật phẩm trong cửa hàng
   - Animation cho các sự kiện đặc biệt

2. **SFX (âm thanh)**:
   - Âm thanh khi nhận huy hiệu
   - Âm thanh khi pet phát triển
   - Âm thanh khi nhận xu
   - Âm thanh khi mua vật phẩm

3. **UI Components**:
   - Badge collection view
   - Pet interaction screen
   - Store UI
   - Currency display

## Quy Trình Triển Khai

1. **Thiết lập cơ sở dữ liệu**:
   - Tạo các entity cho Achievement, VirtualPet, Currency, StoreItem
   - Thiết lập mối quan hệ giữa các entity
   - Thiết kế các DAO cần thiết

2. **Xây dựng Achievement Manager**:
   - Triển khai AchievementTracker để theo dõi tiến độ
   - Tạo AchievementEvaluator để kiểm tra điều kiện
   - Kết nối với StepRepository để lấy dữ liệu

3. **Xây dựng VirtualPet System**:
   - Triển khai PetEvolutionManager
   - Tạo PetInteractionHandler
   - Xây dựng animation system cho pet

4. **Xây dựng Currency System**:
   - Triển khai CurrencyManager
   - Tạo các reward triggers
   - Thiết lập transaction history

5. **Xây dựng Store**:
   - Thiết kế StoreManager
   - Tạo danh sách sản phẩm
   - Triển khai cơ chế mua hàng
   - Xử lý inventory của người dùng

6. **Tích hợp với UI**:
   - Tạo các màn hình hiển thị huy hiệu
   - Xây dựng UI cho pet
   - Thiết kế giao diện cửa hàng
   - Tạo thông báo và hiệu ứng

## Các Lưu Ý Quan Trọng

1. **Cân bằng hệ thống**:
   - Đảm bảo xu thưởng đủ hấp dẫn nhưng không quá dễ dàng
   - Cân bằng giá cả trong cửa hàng
   - Đảm bảo người dùng mới cũng có thể trải nghiệm tốt

2. **Offline Support**:
   - Hệ thống cần hoạt động hoàn toàn offline
   - Đồng bộ hóa dữ liệu khi có kết nối internet (nếu cần)

3. **Chống gian lận**:
   - Xác thực số bước chân để tránh gian lận
   - Lưu trữ dữ liệu an toàn để tránh sửa đổi

4. **Trải nghiệm người dùng**:
   - Hiệu ứng trực quan và hấp dẫn
   - Không gây phiền nhiễu với quá nhiều thông báo
   - UI dễ hiểu và trực quan

## Tiêu Chí Kiểm Thử

1. **Chức năng**:
   - Tất cả huy hiệu được trao chính xác khi đạt điều kiện
   - Pet/cây phát triển đúng theo số bước
   - Xu được cộng và trừ chính xác
   - Cửa hàng hoạt động đúng

2. **Hiệu suất**:
   - Không gây lag hoặc giật khi hiển thị animation
   - Kiểm tra hiệu suất trên các thiết bị cấu hình thấp

3. **Độ ổn định**:
   - Dữ liệu không bị mất khi ứng dụng crash
   - Xử lý các trường hợp ngoại lệ

## Tích Hợp Với Các Tính Năng Khác

- **Step Counter Core**: Lấy dữ liệu số bước để tính toán thành tích
- **Challenges**: Tích hợp thành tích với hệ thống thử thách
- **Notifications**: Thông báo khi đạt được thành tích mới
- **Widgets**: Hiển thị pet/huy hiệu trên widget

## Phát Triển Trong Tương Lai

- Tích hợp event theo mùa/dịp đặc biệt
- Hệ thống nhiệm vụ hàng ngày
- Tương tác giữa các pet của người dùng khác nhau
- Phát triển minigame liên quan đến pet/thành tích

## Tài Liệu Tham Khảo

- [Gamification Design Framework](https://yukaichou.com/gamification-examples/octalysis-complete-gamification-framework/)
- [Material Design - Celebration & Rewards](https://material.io/design/communication/celebrations.html)
- [Best Practices in Mobile Game Economies](https://www.gameanalytics.com/blog/mobile-game-economy-design.html)
- [Virtual Pet Design Patterns](https://www.gamedeveloper.com/design/design-patterns-in-virtual-pet-games) 