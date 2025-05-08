# Hướng Dẫn Thiết Kế Giao Diện - StepCounter

## Tổng Quan

Tài liệu này định nghĩa các tiêu chuẩn thiết kế giao diện người dùng cho ứng dụng StepCounter. Mục đích là tạo ra một giao diện hiện đại, trực quan và thân thiện với người dùng, đồng thời thể hiện tinh thần gamification của ứng dụng dựa trên concept fitness tracking với tông màu tím tham khảo [ui.png].

## Nguyên Tắc Thiết Kế

### 1. Đơn Giản & Hiện Đại
- Ưu tiên thiết kế tối giản, không gian trắng hợp lý
- Hiển thị số liệu dễ đọc, nổi bật với font size lớn
- Sử dụng thẻ có góc bo tròn mềm mại

### 2. Phản Hồi Trực Quan
- Cung cấp phản hồi tức thì khi người dùng tương tác
- Sử dụng biểu đồ tròn (circular progress) cho mục tiêu
- Hiển thị số liệu tiến độ bằng % và biểu đồ

### 3. Mang Tính Khích Lệ
- Thiết kế tạo cảm giác thành tựu và tiến bộ
- Hiển thị thông điệp khích lệ ("Today you took 10,000 steps!")
- Tạo niềm vui khi người dùng đạt được mục tiêu

### 4. Nhất Quán
- Duy trì nhất quán trong toàn bộ ứng dụng
- Sử dụng cùng một kiểu thiết kế thẻ cho các thành phần 
- Đảm bảo layout thống nhất giữa các màn hình

### 5. Dễ Tiếp Cận
- Tuân thủ các nguyên tắc accessibility
- Sử dụng kích thước text đủ lớn
- Đảm bảo độ tương phản màu sắc phù hợp
- Hỗ trợ TalkBack và các công cụ trợ năng khác

## Material Design 3

StepCounter sử dụng Material Design 3 với phong cách hiện đại và tối giản:

- Sử dụng Material Components cho Android
- Áp dụng Dynamic Color nếu có sẵn (Android 12+)
- Hỗ trợ cả light mode và dark mode
- Tùy chỉnh theme với palette màu tím làm chủ đạo

## Palette Màu

### Màu Chính (Primary)
- **Primary**: `#7C4DFF` - Tím (Purple)
- **Primary Variant**: `#5E35B1` - Tím đậm
- **On Primary**: `#FFFFFF` - Trắng

### Màu Phụ (Secondary)
- **Secondary**: `#B39DDB` - Tím nhạt
- **Secondary Variant**: `#9575CD` - Tím nhạt đậm
- **On Secondary**: `#000000` - Đen

### Màu Nhấn (Accent)
- **Tertiary**: `#64B5F6` - Xanh da trời
- **On Tertiary**: `#FFFFFF` - Trắng

### Màu Nền (Background)
- **Background**: `#F5F5F5` - Trắng xám nhẹ (Light mode)
- **Background Dark**: `#121212` - Đen nhẹ (Dark mode)
- **Surface**: `#FFFFFF` - Trắng (Light mode)
- **Surface Dark**: `#1E1E1E` - Xám đen (Dark mode)

### Màu Trạng Thái (Status)
- **Success**: `#4CAF50` - Xanh lá
- **Warning**: `#FFC107` - Vàng
- **Error**: `#F44336` - Đỏ
- **Info**: `#03A9F4` - Xanh da trời

### Màu Biểu Đồ (Charts)
- **Chart Primary**: `#7C4DFF` - Tím
- **Chart Secondary**: `#B39DDB` - Tím nhạt
- **Chart Water**: `#64B5F6` - Xanh da trời
- **Chart Steps**: `#7C4DFF` - Tím
- **Chart Distance**: `#5E35B1` - Tím đậm
- **Chart Calories**: `#FF7043` - Cam đỏ

## Typography

### Font Family
- **Primary Font**: Google Sans Text / Product Sans (hoặc Roboto làm fallback)
- **Alternative**: Roboto (system default)

### Font Sizes
- **Display Large**: 57sp (Hiển thị số bước chân chính)
- **Display Medium**: 45sp
- **Display Small**: 36sp
- **Headline Large**: 32sp (Số bước chân trên dashboard)
- **Headline Medium**: 28sp
- **Headline Small**: 24sp
- **Title Large**: 22sp (Tiêu đề thẻ)
- **Title Medium**: 16sp
- **Title Small**: 14sp
- **Body Large**: 16sp
- **Body Medium**: 14sp
- **Body Small**: 12sp
- **Label Large**: 14sp
- **Label Medium**: 12sp
- **Label Small**: 11sp

## Thành Phần UI

### Thẻ Dashboard (Dashboard Cards)
- Thẻ vuông có góc bo tròn 16dp
- Nền màu trắng với shadow nhẹ (elevation 2dp)
- Icon mô tả chức năng góc trên trái
- Giá trị lớn ở giữa, đơn vị nhỏ bên dưới
- Padding nội dung 16dp
- Kích thước icon: 24dp x 24dp
- Hiệu ứng khi tap vào (ripple effect)

### Thẻ Metrics
- Sử dụng grid layout 2x2 cho các thẻ metric chính
- Mỗi thẻ có màu nền riêng biệt (xanh dương, tím, xanh lá, tím đậm)
- Text và icon màu trắng đảm bảo độ tương phản
- Hiển thị giá trị lớn (steps, calories, cups, km)
- Hiển thị icon tương ứng với metric

### Biểu Đồ Tròn (Circular Progress)
- Đường viền dày 12dp
- Màu nền nhạt, màu tiến độ đậm
- Hiển thị phần trăm hoàn thành ở giữa
- Animation khi loading dữ liệu (1-2 giây)
- Sử dụng cho mục tiêu bước chân, nước uống

### Biểu Đồ Đường (Line Charts)
- Đường cong mượt mà thay vì đường thẳng
- Gradient fill từ đường cong xuống trục x
- Không hiển thị lưới chi tiết, chỉ các đường chính
- Chú thích nhỏ gọn
- Animation khi hiển thị dữ liệu

### Nút (Buttons)
- **Primary Button**: Filled với màu primary tím
- **Secondary Button**: Outlined với viền tím
- **Text Button**: Chỉ text và ripple effect
- **Icon Button**: Nút tròn với icon
- Tất cả các nút đều có ripple effect khi nhấn
- Corner radius: 24dp (bo tròn hoàn toàn)
- Sử dụng nút "Continue" ở cuối màn hình

### Navigation
- Bottom Navigation với icons rõ ràng
- Hiệu ứng highlight khi tab được chọn
- 3-5 tab chính: Home, Stats, Achievements, Profile
- Icon có animation nhỏ khi được chọn
- Màu active: tím primary

### Widgets nhỏ
- Icons tròn cho các tính năng phụ (nước, nhắc nhở)
- Màu nền nhạt, icon đậm
- Kích thước đồng nhất

## Layout

### Khoảng Cách (Spacing)
- **Extra Small**: 4dp
- **Small**: 8dp
- **Medium**: 16dp
- **Large**: 24dp
- **Extra Large**: 32dp
- **Content Padding**: 20dp (padding bao quanh nội dung)

### Cấu Trúc Màn Hình
- Sử dụng ConstraintLayout cho layout phức tạp
- Header rõ ràng với avatar người dùng và lời chào
- Phần nội dung chính hiển thị bước chân và tiến độ
- Grid hoặc list cho các thẻ metric
- Phần dưới cùng dành cho nút hành động chính

## Màn Hình Chính

### 1. Dashboard
- Avatar và lời chào ở phần header ("Hi, Grace")
- Hiển thị mục tiêu và tiến độ hiện tại ("Today you took 10,000 steps!")
- Biểu đồ tròn lớn hiển thị % hoàn thành
- Grid metrics (steps, water, kcal, km)
- Quick actions ở phía dưới (nước, cài đặt)

### 2. Chi Tiết Chỉ Số
- Hiển thị chi tiết về một chỉ số cụ thể (ví dụ: nước uống)
- Biểu đồ tròn lớn ở giữa hiển thị tiến độ (40%)
- Tiến độ theo các thời điểm khác nhau (morning, afternoon, evening, night)
- Nút Continue ở cuối màn hình

### 3. Thống Kê (Statistics)
- Biểu đồ đường hiển thị dữ liệu theo 7 ngày gần nhất
- Giá trị trung bình và cao nhất
- Bộ lọc thời gian dễ sử dụng
- Thống kê tổng hợp (steps, km, kcal)

### 4. Theo Dõi Hoạt Động (Activity Tracking)
- Hiển thị thành tích ("You have reached 40% of your goal")
- Số bước chân đạt được (7,255) với icon ngôi sao
- Các chỉ số khác (steps, km, kcal)
- Biểu đồ đường ở phần dưới

### 5. Chi Tiết Hoạt Động
- Hiển thị số bước chân lớn (9,789)
- Chi tiết về km và calories
- Biểu đồ đường
- Giày chạy bộ và thông tin về hoạt động
- Gallery ảnh từ hoạt động

## Icons & Hình Ảnh

### Icons
- Sử dụng Material Icons với phong cách Rounded
- Kích thước tối thiểu 24dp x 24dp
- Icon trắng trên nền màu, icon màu trên nền trắng

### Illustrations
- Sử dụng hình ảnh giày chạy bộ tím/hồng
- Illustrations đơn giản, phẳng với màu tím chủ đạo
- Giữ phong cách nhất quán

## Animation & Motion

### Transitions
- Sử dụng shared element transitions (biểu đồ tròn, thẻ)
- Animation mượt mà khi chuyển trang
- Thời lượng transition: 300ms (standard)

### Feedback Animations
- Celebration animation khi đạt mục tiêu
- Pulse animation cho số liệu khi cập nhật
- Loading animation cho biểu đồ tròn (xoay theo chiều kim đồng hồ)

## Responsive Design

### Phone Support
- Tối ưu cho kích thước màn hình 5"-7"
- Density scaling phù hợp (mdpi đến xxhdpi)
- Adaptive layout cho portrait và landscape

## Best Practices

### Loading States
- Sử dụng skeleton screens thay vì spinner
- Animation loading cho biểu đồ tròn
- Transition mượt mà từ loading sang content

### Empty States
- Thiết kế empty states với hình ảnh giày chạy
- Thông điệp khích lệ 
- Nút hành động rõ ràng

## Accessibility

### Color Contrast
- Đảm bảo text trắng trên nền tím có độ tương phản cao
- Sử dụng font size lớn cho các chỉ số quan trọng
- Test với các filter màu sắc

## Yếu Tố Đặc Trưng 

### Circular Progress
- Biểu đồ tròn là yếu tố nhận diện chính của ứng dụng
- Sử dụng nhất quán cho hiển thị tiến độ mục tiêu
- Animation mượt mà khi tăng/giảm

### Card Layout
- Layout dạng thẻ với góc bo tròn nhẹ
- Các thẻ metric nhỏ gọn, màu sắc phân biệt
- Shadow nhẹ tạo cảm giác độ sâu

### Typography
- Sử dụng font sans-serif hiện đại (Google Sans)
- Kích thước lớn cho các giá trị chính
- Tương phản giữa font đậm (cho giá trị) và font thường (cho đơn vị)

## Tài Liệu Tham Khảo

- [Material Design 3 Guidelines](https://m3.material.io/)
- [Android Design Guidelines](https://developer.android.com/design)
- [Accessibility Guidelines](https://developer.android.com/guide/topics/ui/accessibility) 