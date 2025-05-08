# Quy Ước Viết Code - StepCounter

## Tổng Quan

Tài liệu này định nghĩa các quy tắc và tiêu chuẩn cho mã nguồn dự án StepCounter. Mục đích là đảm bảo tính nhất quán, khả năng đọc hiểu và dễ bảo trì của code. Tất cả các thành viên trong team phải tuân thủ những quy tắc này.

## Ngôn Ngữ & Công Nghệ

### Kotlin

- Sử dụng Kotlin làm ngôn ngữ chính cho phát triển
- Target Kotlin version: 1.8.x trở lên
- Sử dụng các tính năng mới của Kotlin khi hợp lý
- Ưu tiên sử dụng Kotlin Coroutines cho xử lý bất đồng bộ

### Android

- Minimum SDK: API 24 (Android 7.0)
- Target SDK: API 34 (Android 14)
- Tuân thủ các nguyên tắc Material Design 3
- Sử dụng Jetpack Components khi thích hợp

## Kiến Trúc & Cấu Trúc Dự Án

### Kiến Trúc MVVM

Dự án tuân theo mô hình MVVM (Model-View-ViewModel) với các thành phần:

- **Model**: Dữ liệu và logic nghiệp vụ
- **View**: UI (Activities, Fragments, Composables)
- **ViewModel**: Trung gian giữa Model và View
- **Repository**: Nguồn dữ liệu cho ViewModel

### Cấu Trúc Thư Mục

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/stepcounter/
│   │   │   ├── data/                   # Data layer
│   │   │   │   ├── local/              # Local database
│   │   │   │   ├── model/              # Data models
│   │   │   │   └── repository/         # Repositories
│   │   │   ├── di/                     # Dependency injection
│   │   │   ├── domain/                 # Domain layer (use cases)
│   │   │   ├── service/                # Background services
│   │   │   ├── ui/                     # UI layer
│   │   │   │   ├── achievements/       # Achievements feature
│   │   │   │   ├── challenges/         # Challenges feature
│   │   │   │   ├── dashboard/          # Main dashboard
│   │   │   │   ├── pets/               # Virtual pet feature
│   │   │   │   ├── settings/           # App settings
│   │   │   │   ├── statistics/         # Statistics & history
│   │   │   │   └── store/              # In-app store
│   │   │   ├── util/                   # Utility classes
│   │   │   └── worker/                 # Background workers
│   │   └── res/                        # Android resources
│   └── test/                           # Unit tests
└── build.gradle                        # App build config
```

## Quy Ước Đặt Tên

### Quy Tắc Chung

- Tên phải mô tả đúng mục đích và chức năng
- Tránh viết tắt trừ khi phổ biến (e.g., HTTP, JSON)
- Đặt tên bằng tiếng Anh

### Classes & Interfaces

- Đặt tên theo PascalCase (e.g., `StepCounter`, `DailyGoalRepository`)
- Tên phải là danh từ hoặc cụm danh từ
- Tên giao diện không bắt đầu với 'I' (e.g., `Repository` không phải `IRepository`)

### Biến & Thuộc Tính

- Đặt tên theo camelCase (e.g., `stepCount`, `totalDistance`)
- Tên biến phải là danh từ hoặc cụm danh từ
- Biến boolean nên bắt đầu với 'is', 'has', 'should' (e.g., `isActive`, `hasAchievement`)
- Hằng số viết HOA_VÀ_CÁCH_NHAU_BẰNG_GẠCH_DƯỚI (e.g., `MAX_STEP_COUNT`)

### Hàm & Phương Thức

- Đặt tên theo camelCase (e.g., `calculateDistance()`, `saveStepData()`)
- Tên hàm phải là động từ hoặc cụm động từ
- Các hàm truy vấn boolean nên bắt đầu với 'is', 'has', 'should' (e.g., `isGoalAchieved()`)

### Tài Nguyên (Resources)

- IDs: `<component_type>_<screen>_<description>` (e.g., `button_dashboard_start`, `text_achievements_title`)
- Layouts: `<type>_<description>.xml` (e.g., `activity_main.xml`, `fragment_statistics.xml`, `item_achievement.xml`)
- Drawables: `<type>_<description>.xml` (e.g., `ic_achievement_unlocked.xml`, `bg_dashboard.xml`)
- Colors: `<base_color>_<variant>` (e.g., `blue_500`, `green_light`)
- Dimensions: `<component>_<property>` (e.g., `text_size_heading`, `margin_standard`)
- Strings: `<screen>_<description>` (e.g., `dashboard_steps_label`, `achievements_unlock_message`)

## Phong Cách & Định Dạng

### Phong Cách Code

- Viết code súc tích nhưng dễ đọc
- Ưu tiên biểu thức lambda/higher-order functions khi phù hợp
- Sử dụng extension functions để mở rộng chức năng
- Ưu tiên immutability (val thay vì var khi có thể)
- Sử dụng null safety features của Kotlin (e.g., `?.`, `!!`, `?:`)

### Định Dạng

- Indentation: 4 spaces (không dùng tabs)
- Line width: tối đa 100 ký tự
- Method chaining nên đặt mỗi method trên một dòng
- Spaces around operators (e.g., `a + b` không phải `a+b`)
- Mỗi class trong một file riêng biệt
- Thứ tự properties trong một class:
  1. Constants
  2. Properties
  3. Constructors
  4. Lifecycle methods
  5. Public methods
  6. Private methods
  7. Inner classes/interfaces

### Comments

- Dùng KDoc cho classes và public methods
- Comments nên giải thích WHY, không phải WHAT
- TODO comments phải có tên người chịu trách nhiệm và timeline
- Sử dụng dấu `//` cho single-line comments
- Tránh để lại commented-out code

## Công Cụ Linting

### Ktlint

- Cài đặt ktlint như một phần của quy trình build
- Chạy ktlint trước mỗi commit
- Cấu hình trong file `.editorconfig`

### Detekt

- Sử dụng detekt để phát hiện các vấn đề phức tạp hơn
- Chạy detekt trong quá trình CI
- Thiết lập ngưỡng complexity thích hợp

## Các Quy Tắc Cụ Thể

### Activities & Fragments

- Không đặt logic nghiệp vụ trong Activities/Fragments
- Sử dụng ViewModel để lưu trữ và xử lý UI state
- Implement lifecycle methods theo thứ tự chronological
- Sử dụng ViewBinding thay vì findViewById

### ViewModels

- Không tham chiếu trực tiếp đến Views từ ViewModel
- Expose state thông qua LiveData, StateFlow hoặc SharedFlow
- ViewModel chỉ tương tác với domain layer (use cases) hoặc repositories
- Định nghĩa các sự kiện UI rõ ràng

### Coroutines

- Sử dụng structured concurrency
- Tránh sử dụng GlobalScope
- Sử dụng CoroutineScope phù hợp với lifecycle
- Xử lý lỗi trong coroutines bằng try-catch hoặc CoroutineExceptionHandler

### Dependency Injection

- Sử dụng Dagger Hilt cho DI
- Module organization theo các logical components
- Tránh singleton khi không cần thiết
- Inject dependencies thông qua constructor khi có thể

## Xử Lý Exceptions

### Quy Tắc Chung

- Wrap các exceptions cụ thể với các domain-specific exceptions
- Log exceptions với đầy đủ thông tin
- Không bỏ qua exceptions mà không xử lý

### Error Handling

- Sử dụng sealed classes để đại diện cho các trạng thái lỗi
- Return Result<T> để xử lý lỗi trong các hàm có thể thất bại
- Sử dụng try-catch một cách thích hợp

## Testing

### Unit Tests

- Viết unit tests cho tất cả business logic
- Sử dụng JUnit và Mockito/MockK
- Đặt tên test theo mẫu `methodName_condition_expectedResult`
- Cấu trúc mỗi test theo mô hình AAA (Arrange-Act-Assert)

### UI Tests

- Sử dụng Espresso cho UI tests
- Tạo Page Objects để đại diện cho các màn hình
- Sử dụng IdlingResources cho các async operations

## Hiệu Suất

### Quy Tắc Chung

- Tránh tạo objects không cần thiết, đặc biệt trong vòng lặp
- Sử dụng các collection operations phù hợp
- Sử dụng lazy initialization khi cần

### UI Performance

- Tránh nested layouts phức tạp
- Sử dụng ConstraintLayout cho các layouts phẳng
- Tránh overdraw
- Sử dụng RecyclerView thay vì ListView
- Tối ưu việc tải và hiển thị hình ảnh

### Memory Management

- Tránh memory leaks
- Xử lý resources trong onPause/onDestroy
- Sử dụng WeakReferences khi cần thiết

## Bảo Mật

### Quy Tắc Chung

- Không hard-code sensitive information
- Sử dụng EncryptedSharedPreferences cho dữ liệu nhạy cảm
- Kiểm tra input validation kỹ lưỡng
- Xử lý permissions theo Android best practices

### Dữ Liệu Người Dùng

- Tuân thủ quy định GDPR
- Cung cấp cơ chế export/delete data
- Rõ ràng về việc thu thập và sử dụng dữ liệu

## Danh Sách Kiểm Tra Code Review

Dưới đây là danh sách kiểm tra khi thực hiện code review:

1. **Chất lượng code**
   - Code có tuân thủ coding standards không?
   - Có duplicated code không?
   - Có magic numbers hoặc hardcoded strings không?

2. **Thiết kế**
   - Code có tuân thủ SOLID principles không?
   - Có tôn trọng kiến trúc MVVM không?
   - Dependencies có được quản lý hợp lý không?

3. **Chức năng**
   - Code có thực hiện đúng yêu cầu không?
   - Có xử lý đầy đủ các edge cases không?
   - Error handling có đầy đủ không?

4. **Hiệu suất**
   - Có operations tốn kém không cần thiết không?
   - Có potential memory leaks không?
   - Code có optimize cho mobile không?

5. **Testability**
   - Có unit tests không?
   - Tests có cover đủ functionality không?
   - Các tests có pass không?

6. **Documentation**
   - Code có được comment/doc đầy đủ không?
   - Có cập nhật README nếu cần không?
   - Public APIs có documents không?

## Tài Liệu Tham Khảo

- [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- [Android Developer Guides](https://developer.android.com/guide)
- [Material Design Guidelines](https://m3.material.io/)
- [Clean Architecture in Android](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Jetpack MVVM Guide](https://developer.android.com/jetpack/guide) 