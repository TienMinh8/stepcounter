# Hướng Dẫn Phát Triển StepCounter

Thư mục này chứa các tài liệu hướng dẫn chi tiết cho việc phát triển ứng dụng StepCounter.

## Cấu Trúc Tài Liệu

- [Instruction.md](../Instruction.md) - Tài liệu hướng dẫn chính, tổng quan về các tính năng
- [Project.md](../Project.md) - Tổng quan về dự án, kiến trúc hệ thống và quy trình làm việc
- [Decisions.md](../Decisions.md) - Ghi lại các quyết định thiết kế quan trọng
- [Changelog.md](../Changelog.md) - Lịch sử thay đổi dự án
- [Codebase.md](../Codebase.md) - Tổng quan về cấu trúc code

## Tài Liệu Chi Tiết Cho Từng Tính Năng

### Core Features

1. [Core_StepCounter.md](features/Core_StepCounter.md) - Hướng dẫn chi tiết về tính năng đếm bước chân và quãng đường
2. [Persistence.md](features/Persistence.md) - Hướng dẫn về lưu trữ dữ liệu bước chân và hoạt động

### Gamification

3. [Gamification_Achievements.md](features/Gamification_Achievements.md) - Hệ thống thành tích, huy hiệu và xu thưởng
4. [Gamification_Pets.md](features/Gamification_Pets.md) - Hướng dẫn triển khai pet ảo/cây trồng ảo

### User Experience

5. [Challenges.md](features/Challenges.md) - Hướng dẫn triển khai hệ thống thử thách
6. [Personalization.md](features/Personalization.md) - Cá nhân hóa và phân tích dữ liệu người dùng
7. [Storytelling.md](features/Storytelling.md) - Hệ thống câu chuyện tiến triển

### UI Components

8. [Widgets.md](features/Widgets.md) - Hướng dẫn triển khai widget cho màn hình chính
9. [Notifications.md](features/Notifications.md) - Hệ thống thông báo thông minh

### Activity Tracking

10. [ActivityModes.md](features/ActivityModes.md) - Các chế độ hoạt động khác nhau
11. [HealthFeatures.md](features/HealthFeatures.md) - Tính năng sức khỏe đơn giản

## API & Technical Docs

- [API_Docs.md](API_Docs.md) - Tài liệu về API và tích hợp sensors
- [Database_Schema.md](Database_Schema.md) - Cấu trúc cơ sở dữ liệu
- [Architecture.md](Architecture.md) - Chi tiết về kiến trúc MVVM

## Guidelines

- [Coding_Guidelines.md](guidelines/Coding_Guidelines.md) - Quy ước và chuẩn mực viết code
- [UI_Guidelines.md](guidelines/UI_Guidelines.md) - Tiêu chuẩn thiết kế UI và thành phần
- [Testing_Guidelines.md](guidelines/Testing_Guidelines.md) - Quy trình và phương pháp kiểm thử

## Deployment

- [Deployment.md](Deployment.md) - Hướng dẫn triển khai ứng dụng
- [Release_Checklist.md](Release_Checklist.md) - Danh sách kiểm tra trước khi phát hành

## Workflow

- [Development_Workflow.md](workflow/Development_Workflow.md) - Quy trình phát triển
- [Git_Workflow.md](workflow/Git_Workflow.md) - Quy trình làm việc với Git
- [Issue_Management.md](workflow/Issue_Management.md) - Quản lý issue và bug

## Quy Trình Làm Việc

1. Bắt đầu bằng việc đọc [Instruction.md](../Instruction.md) và [Project.md](../Project.md) để hiểu tổng quan
2. Đọc tài liệu chi tiết về tính năng cần triển khai
3. Tuân thủ các guidelines khi triển khai
4. Cập nhật Decisions.md với các quyết định thiết kế quan trọng
5. Cập nhật Changelog.md sau mỗi tính năng hoàn thành
6. Cập nhật Codebase.md khi thêm mới hoặc thay đổi lớn về cấu trúc 