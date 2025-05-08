# Cấu Trúc Cơ Sở Dữ Liệu StepCounter

## Tổng Quan

Tài liệu này mô tả chi tiết cấu trúc cơ sở dữ liệu SQLite sử dụng Room Persistence Library cho ứng dụng StepCounter. Cơ sở dữ liệu được thiết kế để lưu trữ và quản lý hiệu quả dữ liệu về bước chân, hoạt động, thành tích, và các tính năng gamification khác.

## Mô Hình Thực Thể Quan Hệ (ERD)

```
+------------------+     +------------------+     +--------------------+
| User             |     | DailySteps       |     | Activity           |
+------------------+     +------------------+     +--------------------+
| PK: userId       |<----|FK: userId        |     | PK: activityId     |
| username         |     | PK: date         |     | FK: userId         |
| height           |     | steps            |     | type               |
| weight           |     | distance         |     | startTime          |
| strideLength     |     | calories         |     | endTime            |
| dailyGoal        |     | activeMinutes    |     | steps              |
| createdAt        |     | createdAt        |     | distance           |
| updatedAt        |     +------------------+     | avgSpeed           |
+------------------+            |                 | calories           |
        |                       |                 +--------------------+
        |                       |                         |
+------------------+     +------------------+     +--------------------+
| Achievement      |     | DailyGoal        |     | StepSession        |
+------------------+     +------------------+     +--------------------+
| PK: achievementId|     | PK: goalId       |     | PK: sessionId      |
| name             |     | FK: userId       |     | FK: userId         |
| description      |     | date             |     | startTime          |
| iconResource     |     | targetSteps      |     | endTime            |
| category         |     | achieved         |     | initialSteps       |
| condition        |     | achievedAt       |     | finalSteps         |
| reward           |     +------------------+     | isActive           |
+------------------+                              +--------------------+
        |
        |
+------------------+     +------------------+     +--------------------+
| UserAchievement  |     | VirtualPet       |     | Currency           |
+------------------+     +------------------+     +--------------------+
| PK: userAchId    |     | PK: petId        |     | PK: currencyId     |
| FK: userId       |     | FK: userId       |     | FK: userId         |
| FK: achievementId|     | name             |     | FK: userId         |
| progress         |     | type             |     | balance            |
| unlocked         |     | level            |     | lifetimeEarned     |
| unlockedAt       |     | experience       |     +--------------------+
+------------------+     | mood             |             |
                         | lastInteraction  |             |
                         +------------------+             |
                                |                         |
                                |                         |
                         +------------------+     +--------------------+
                         | Challenge        |     | Transaction        |
                         +------------------+     +--------------------+
                         | PK: challengeId  |     | PK: transactionId  |
                         | name             |     | FK: userId         |
                         | description      |     | FK: userId         |
                         | type             |     | amount             |
                         | target           |     | type               |
                         | reward           |     | source             |
                         | startDate        |     | timestamp          |
                         | endDate          |     | description        |
                         +------------------+     +--------------------+
                                |
                                |
                         +------------------+
                         | UserChallenge    |
                         +------------------+
                         | PK: userChalId   |
                         | FK: userId       |
                         | FK: challengeId  |
                         | progress         |
                         | status           |
                         | joinedAt         |
                         | completedAt      |
                         +------------------+
```

## Chi Tiết Các Thực Thể

### 1. User

Lưu trữ thông tin cơ bản về người dùng.

| Trường          | Kiểu dữ liệu | Mô tả                          | Ràng buộc             |
|-----------------|--------------|--------------------------------|-----------------------|
| userId          | Long         | Khóa chính                     | Primary Key, AutoInc  |
| username        | String       | Tên người dùng                 | Not null, Unique      |
| height          | Float        | Chiều cao (cm)                 | Nullable              |
| weight          | Float        | Cân nặng (kg)                  | Nullable              |
| strideLength    | Float        | Chiều dài sải chân (cm)        | Default: 75.0         |
| dailyGoal       | Int          | Mục tiêu bước chân hàng ngày   | Default: 10000        |
| createdAt       | Long         | Thời điểm tạo tài khoản        | Not null              |
| updatedAt       | Long         | Thời điểm cập nhật gần nhất    | Not null              |

**Chỉ mục**:
- PRIMARY KEY (userId)

### 2. DailySteps

Lưu trữ số bước chân hàng ngày của người dùng.

| Trường        | Kiểu dữ liệu | Mô tả                         | Ràng buộc            |
|---------------|--------------|-------------------------------|----------------------|
| userId        | Long         | ID người dùng                 | Foreign Key          |
| date          | Long         | Ngày (timestamp)              | Part of Primary Key  |
| steps         | Int          | Tổng số bước trong ngày       | Not null, Default: 0 |
| distance      | Float        | Quãng đường (m)               | Not null, Default: 0 |
| calories      | Int          | Calo tiêu hao                 | Default: 0           |
| activeMinutes | Int          | Số phút hoạt động             | Default: 0           |
| createdAt     | Long         | Thời điểm tạo record          | Not null             |

**Chỉ mục**:
- PRIMARY KEY (userId, date)
- FOREIGN KEY (userId) REFERENCES User(userId)

### 3. Activity

Lưu trữ thông tin về các hoạt động cụ thể.

| Trường        | Kiểu dữ liệu | Mô tả                         | Ràng buộc            |
|---------------|--------------|-------------------------------|----------------------|
| activityId    | Long         | ID hoạt động                  | Primary Key, AutoInc |
| userId        | Long         | ID người dùng                 | Foreign Key          |
| type          | Int          | Loại hoạt động (đi bộ, chạy)  | Not null             |
| startTime     | Long         | Thời gian bắt đầu             | Not null             |
| endTime       | Long         | Thời gian kết thúc            | Nullable             |
| steps         | Int          | Số bước trong hoạt động       | Default: 0           |
| distance      | Float        | Quãng đường (m)               | Default: 0           |
| avgSpeed      | Float        | Tốc độ trung bình (m/s)       | Nullable             |
| calories      | Int          | Calo tiêu hao                 | Default: 0           |

**Chỉ mục**:
- PRIMARY KEY (activityId)
- FOREIGN KEY (userId) REFERENCES User(userId)
- INDEX idx_activity_user_date (userId, startTime)

### 4. StepSession

Lưu trữ thông tin về các phiên đếm bước chân (giúp xử lý reset counter).

| Trường        | Kiểu dữ liệu | Mô tả                         | Ràng buộc            |
|---------------|--------------|-------------------------------|----------------------|
| sessionId     | Long         | ID phiên                      | Primary Key, AutoInc |
| userId        | Long         | ID người dùng                 | Foreign Key          |
| startTime     | Long         | Thời gian bắt đầu             | Not null             |
| endTime       | Long         | Thời gian kết thúc            | Nullable             |
| initialSteps  | Long         | Số bước ban đầu từ sensor     | Not null             |
| finalSteps    | Long         | Số bước cuối từ sensor        | Nullable             |
| isActive      | Boolean      | Phiên có đang hoạt động       | Default: true        |

**Chỉ mục**:
- PRIMARY KEY (sessionId)
- FOREIGN KEY (userId) REFERENCES User(userId)
- INDEX idx_session_status (userId, isActive)

### 5. Achievement

Lưu trữ định nghĩa các thành tích có thể đạt được.

| Trường         | Kiểu dữ liệu | Mô tả                         | Ràng buộc            |
|----------------|--------------|-------------------------------|----------------------|
| achievementId  | Long         | ID thành tích                 | Primary Key, AutoInc |
| name           | String       | Tên thành tích                | Not null             |
| description    | String       | Mô tả thành tích              | Not null             |
| iconResource   | String       | Resource ID của icon          | Not null             |
| category       | Int          | Loại thành tích               | Not null             |
| condition      | String       | Điều kiện để đạt được (JSON)  | Not null             |
| reward         | Int          | Phần thưởng (xu)              | Default: 0           |

**Chỉ mục**:
- PRIMARY KEY (achievementId)
- INDEX idx_achievement_category (category)

### 6. UserAchievement

Lưu trữ tiến độ và trạng thái thành tích của người dùng.

| Trường         | Kiểu dữ liệu | Mô tả                       | Ràng buộc            |
|----------------|--------------|-----------------------------|-----------------------|
| userAchId      | Long         | ID bản ghi                  | Primary Key, AutoInc |
| userId         | Long         | ID người dùng               | Foreign Key          |
| achievementId  | Long         | ID thành tích               | Foreign Key          |
| progress       | Float        | Tiến độ (0-100%)            | Default: 0           |
| unlocked       | Boolean      | Trạng thái mở khóa          | Default: false       |
| unlockedAt     | Long         | Thời điểm mở khóa           | Nullable             |

**Chỉ mục**:
- PRIMARY KEY (userAchId)
- UNIQUE (userId, achievementId)
- FOREIGN KEY (userId) REFERENCES User(userId)
- FOREIGN KEY (achievementId) REFERENCES Achievement(achievementId)

### 7. DailyGoal

Lưu trữ mục tiêu hàng ngày và trạng thái đạt được.

| Trường         | Kiểu dữ liệu | Mô tả                       | Ràng buộc            |
|----------------|--------------|-----------------------------|-----------------------|
| goalId         | Long         | ID mục tiêu                 | Primary Key, AutoInc |
| userId         | Long         | ID người dùng               | Foreign Key          |
| date           | Long         | Ngày (timestamp)            | Not null             |
| targetSteps    | Int          | Mục tiêu số bước            | Not null             |
| achieved       | Boolean      | Đã đạt mục tiêu chưa        | Default: false       |
| achievedAt     | Long         | Thời điểm đạt được          | Nullable             |

**Chỉ mục**:
- PRIMARY KEY (goalId)
- UNIQUE (userId, date)
- FOREIGN KEY (userId) REFERENCES User(userId)
- INDEX idx_goal_date (userId, date)

### 8. VirtualPet

Lưu trữ thông tin về pet ảo của người dùng.

| Trường           | Kiểu dữ liệu | Mô tả                       | Ràng buộc            |
|------------------|--------------|-----------------------------|-----------------------|
| petId            | Long         | ID pet                      | Primary Key, AutoInc |
| userId           | Long         | ID người dùng               | Foreign Key          |
| name             | String       | Tên pet                     | Not null             |
| type             | Int          | Loại pet                    | Not null             |
| level            | Int          | Cấp độ                      | Default: 1           |
| experience       | Int          | Kinh nghiệm                 | Default: 0           |
| mood             | Int          | Tâm trạng                   | Default: 50          |
| lastInteraction  | Long         | Lần tương tác cuối          | Not null             |

**Chỉ mục**:
- PRIMARY KEY (petId)
- FOREIGN KEY (userId) REFERENCES User(userId)
- UNIQUE (userId, type) /* Một người dùng chỉ có 1 pet mỗi loại */

### 9. Currency

Lưu trữ thông tin về xu thưởng của người dùng.

| Trường          | Kiểu dữ liệu | Mô tả                       | Ràng buộc            |
|-----------------|--------------|-----------------------------|-----------------------|
| currencyId      | Long         | ID bản ghi                  | Primary Key, AutoInc |
| userId          | Long         | ID người dùng               | Foreign Key, Unique  |
| balance         | Int          | Số dư hiện tại              | Default: 0           |
| lifetimeEarned  | Int          | Tổng xu kiếm được           | Default: 0           |
| lastUpdated     | Long         | Lần cập nhật cuối           | Not null             |

**Chỉ mục**:
- PRIMARY KEY (currencyId)
- UNIQUE (userId)
- FOREIGN KEY (userId) REFERENCES User(userId)

### 10. Transaction

Lưu trữ lịch sử giao dịch xu.

| Trường          | Kiểu dữ liệu | Mô tả                         | Ràng buộc            |
|-----------------|--------------|-------------------------------|-----------------------|
| transactionId   | Long         | ID giao dịch                  | Primary Key, AutoInc |
| userId          | Long         | ID người dùng                 | Foreign Key          |
| amount          | Int          | Số lượng xu (+/-)             | Not null             |
| type            | Int          | Loại giao dịch (earn/spend)   | Not null             |
| source          | String       | Nguồn gốc (achievement/store) | Not null             |
| timestamp       | Long         | Thời điểm giao dịch           | Not null             |
| description     | String       | Mô tả giao dịch               | Not null             |

**Chỉ mục**:
- PRIMARY KEY (transactionId)
- FOREIGN KEY (userId) REFERENCES User(userId)
- INDEX idx_transaction_date (userId, timestamp)

### 11. Challenge

Lưu trữ thông tin về các thử thách trong ứng dụng.

| Trường          | Kiểu dữ liệu | Mô tả                        | Ràng buộc            |
|-----------------|--------------|------------------------------|-----------------------|
| challengeId     | Long         | ID thử thách                 | Primary Key, AutoInc |
| name            | String       | Tên thử thách                | Not null             |
| description     | String       | Mô tả chi tiết               | Not null             |
| type            | Int          | Loại thử thách               | Not null             |
| target          | Int          | Mục tiêu cần đạt             | Not null             |
| reward          | Int          | Phần thưởng (xu)             | Not null             |
| startDate       | Long         | Ngày bắt đầu                 | Not null             |
| endDate         | Long         | Ngày kết thúc                | Not null             |

**Chỉ mục**:
- PRIMARY KEY (challengeId)
- INDEX idx_challenge_dates (startDate, endDate)
- INDEX idx_challenge_type (type)

### 12. UserChallenge

Lưu trữ trạng thái tham gia thử thách của người dùng.

| Trường          | Kiểu dữ liệu | Mô tả                       | Ràng buộc            |
|-----------------|--------------|-----------------------------|-----------------------|
| userChalId      | Long         | ID bản ghi                  | Primary Key, AutoInc |
| userId          | Long         | ID người dùng               | Foreign Key          |
| challengeId     | Long         | ID thử thách                | Foreign Key          |
| progress        | Float        | Tiến độ hoàn thành (0-100%) | Default: 0           |
| status          | Int          | Trạng thái (đang tham gia/hoàn thành/thất bại) | Not null |
| joinedAt        | Long         | Thời điểm tham gia          | Not null             |
| completedAt     | Long         | Thời điểm hoàn thành        | Nullable             |

**Chỉ mục**:
- PRIMARY KEY (userChalId)
- UNIQUE (userId, challengeId)
- FOREIGN KEY (userId) REFERENCES User(userId)
- FOREIGN KEY (challengeId) REFERENCES Challenge(challengeId)
- INDEX idx_user_challenge (userId, status)

## Các Giá Trị Enum

### ActivityType
- 0: WALKING
- 1: RUNNING
- 2: CYCLING

### AchievementCategory
- 0: STEPS
- 1: DISTANCE
- 2: STREAK
- 3: TIME_BASED
- 4: SPEED

### TransactionType
- 0: EARN
- 1: SPEND

### ChallengeType
- 0: DAILY
- 1: WEEKLY
- 2: MONTHLY
- 3: SPECIAL

### ChallengeStatus
- 0: ACTIVE
- 1: COMPLETED
- 2: FAILED

### PetType
- 0: DOG
- 1: CAT
- 2: BIRD
- 3: PLANT

## Quan Hệ Giữa Các Bảng

1. **User - DailySteps**: Một-nhiều. Một người dùng có nhiều bản ghi DailySteps (mỗi ngày một bản ghi).
2. **User - Activity**: Một-nhiều. Một người dùng có nhiều hoạt động.
3. **User - StepSession**: Một-nhiều. Một người dùng có nhiều phiên đếm bước chân.
4. **User - UserAchievement**: Một-nhiều. Một người dùng có nhiều thành tích.
5. **Achievement - UserAchievement**: Một-nhiều. Một thành tích có thể thuộc về nhiều người dùng.
6. **User - DailyGoal**: Một-nhiều. Một người dùng có nhiều mục tiêu hàng ngày.
7. **User - VirtualPet**: Một-nhiều. Một người dùng có thể có nhiều pet (nhưng mỗi loại chỉ một).
8. **User - Currency**: Một-một. Mỗi người dùng có một bản ghi Currency.
9. **User - Transaction**: Một-nhiều. Một người dùng có nhiều giao dịch.
10. **User - UserChallenge**: Một-nhiều. Một người dùng tham gia nhiều thử thách.
11. **Challenge - UserChallenge**: Một-nhiều. Một thử thách có nhiều người tham gia.

## Migrations

Để xử lý việc thay đổi schema cơ sở dữ liệu khi có phiên bản mới, cần triển khai Migration strategy. Mỗi Migration sẽ được đánh version từ 1 đến n.

Ví dụ:
- **Migration 1-2**: Thêm bảng VirtualPet
- **Migration 2-3**: Thêm trường 'mood' vào bảng VirtualPet
- **Migration 3-4**: Thêm bảng Challenge và UserChallenge

## Đảm Bảo Dữ Liệu

### Sao Lưu & Phục Hồi
- Implement cơ chế sao lưu tự động định kỳ
- Lưu trữ bản sao lưu trong bộ nhớ ngoài
- Cung cấp tùy chọn phục hồi dữ liệu từ bản sao lưu

### Xử Lý Xung Đột & Đồng Bộ
- Implement timestamp-based conflict resolution
- Ưu tiên dữ liệu mới nhất khi có xung đột
- Lưu log thay đổi để debug khi cần

## Tối Ưu Hiệu Suất

### Chỉ Mục
Ngoài các chỉ mục đã đề cập ở trên, có thể thêm chỉ mục cho các truy vấn phổ biến:
- Tạo chỉ mục cho các column thường xuyên được query
- Tránh tạo quá nhiều chỉ mục làm chậm thao tác INSERT/UPDATE

### Tối Ưu Truy Vấn
- Sử dụng LIMIT để giới hạn kết quả trả về
- Tránh sử dụng các truy vấn JOIN phức tạp
- Sử dụng paging cho danh sách dài
- Cache kết quả truy vấn thường xuyên

## Lưu Ý Triển Khai

### Xử Lý Lỗi
- Implement cơ chế retry khi gặp lỗi database
- Log chi tiết lỗi để debug
- Cung cấp fallback strategy khi database không khả dụng

### Bảo Mật
- Sử dụng Room với SQLCipher nếu cần mã hóa dữ liệu
- Không lưu thông tin nhạy cảm trong database
- Implement cơ chế xác thực cho các thao tác nhạy cảm

## Tài Liệu Liên Quan

- [API_Docs.md](API_Docs.md) - Tài liệu về API và tích hợp với database
- [Architecture.md](Architecture.md) - Kiến trúc tổng thể của ứng dụng
- [features/Core_StepCounter.md](features/Core_StepCounter.md) - Chi tiết về tính năng đếm bước chân
- [features/Gamification_Achievements.md](features/Gamification_Achievements.md) - Chi tiết về hệ thống thành tích 