﻿create database TestOnline
go
use TestOnline
go
create table Users (
	id int identity(1,1) primary key not null,
	username varchar(255) not null unique,
	password varchar(255) not null,
	role varchar(255) default 'user'  -- user/admin
)

create table Questions (
	id int identity(1,1) primary key not null,
	details nvarchar(255) not null,
)

go 

create table Answers (
	id int identity(1,1) primary key not null,
	details nvarchar(255) not null,
	idQuestion int not null,
	truth int default 0,  -- true: 1 and false: 0
	foreign key (idQuestion) references Questions(id)
)

go

insert into Users values 
('admin', '123', 1),
('test01', '123', 0),
('test02', '123', 0)

go

insert into Questions values 
(N'Thiết bị nào sau đây dùng để kết nối mạng?'),
(N'Hệ thống nhớ của máy tính bao gồm:'),
(N'Trong mạng máy tính, thuật ngữ Share có ý nghĩa gì?'),
(N'Bộ nhớ RAM và ROM là bộ nhớ gì?'),
(N'Các thiết bị nào thông dụng nhất hiện nay dùng để cung cấp dữ liệu cho máy xử lý?'),
(N'Khái niệm hệ điều hành là gì?'),
(N'Cho biết cách xóa một tập tin hay thư mục mà không di chuyển vào Recycle Bin?'),
(N'Danh sách các mục chọn trong thực đơn gọi là:'),
(N'Công dụng của phím Print Screen là gì?'),
(N'Nếu bạn muốn làm cho cửa sổ nhỏ hơn (không kín màn hình), bạn nên sử dụng nút nào?'),
(N'Trong soạn thảo Word, công dụng của tổ hợp phím Ctrl – S là:'),
(N'Trong soạn thảo Word, để chèn các kí tự đặc biệt vào văn bản, ta thực hiện:'),
(N'Trong soạn thảo Word, để kết thúc 1 đoạn (Paragraph) và muốn sang 1 đoạn mới:'),
(N'Trong soạn thảo Word, tổ hợp phím nào cho phép đưa con trỏ về cuối văn bản:'),
(N'Trong soạn thảo Word, sử dụng phím nóng nào để chọn tất cả văn bản:'),
(N'Trong soạn thảo Word, để chọn một đoạn văn bản ta thực hiện:'),
(N'Trong soạn thảo Word, muốn đánh dấu lựa chọn một từ, ta thực hiện:'),
(N'Trong soạn thảo Word, muốn tách một ô trong Table thành nhiều ô, ta thực hiện:'),
(N'Trong bảng tính Excel, giá trị trả về của công thức =LEN(“TRUNG TAM TIN HOC”) là:'),
(N'Trong bảng tính Excel, cho các giá trị như sau: ô A4 = 4, ô A2 = 5, ô A3 = 6, ô A7 = 7 tại vị trí ô B2 lập công thức B2 = Sum(A4,A2,Count(A3,A4)) cho biết kết quả ô B2 sau khi Enter:'),
(N'Trong bảng tính Excel, ô A1 chứa nội dung “TTTH ĐHKHTN”. Khi thực hiện công thức = LEN(A1) thì giá trị trả về kết quả:'),
(N'Trong bảng tính Excel, hàm nào dùng để tìm kiếm:'),
(N'Trong bảng tính Excel, để lưu tập tin đang mở dưới một tên khác, ta chọn:'),
(N'Trong bảng tính Excel, hàm Today() trả về:'),
(N'Trong bảng tính Excel, các dạng địa chỉ sau đây, địa chỉ nào là địa chỉ tuyệt đối:'),
(N'Trong bảng tính Excel, tại ô A2 có giá trị số 25; Tại ô B2 gõ vào công thức =SQRT(A2) thì nhận được kết quả:')

go 

insert into Answers values 
(N'Ram', 1, 0),
(N'Rom', 1, 0),
(N'Router', 1, 1),
(N'CPU', 1, 0),
(N'Bộ nhớ trong, Bộ nhớ ngoài', 2, 1),
(N'Cache, Bộ nhớ ngoài', 2, 0),
(N'Bộ nhớ ngoài, ROM', 2, 0),
(N'Đĩa quang, Bộ nhớ trong', 2, 0),
(N'Chia sẻ tài nguyên', 3, 1),
(N'Nhãn hiệu của một thiết bị kết nối mạng', 3, 0),
(N'Thực hiện lệnh in trong mạng cục bộ', 3, 0),
(N'Một phần mềm hỗ trợ sử dụng mạng cục bộ', 3, 0),
(N'Primary memory', 4, 1),
(N'Receive memory', 4, 0),
(N'Secondary memory', 4, 0),
(N'Random access memory', 4, 0),
(N'Bàn phím (Keyboard), Chuột (Mouse), Máy in (Printer)', 5, 0),
(N'Máy quét ảnh (Scaner)', 5, 0),
(N'Bàn phím (Keyboard), Chuột (Mouse) và Máy quét ảnh (Scaner)', 5, 1),
(N'Máy quét ảnh (Scaner), Chuột (Mouse)', 5, 0),
(N'Cung cấp và xử lý các phần cứng và phần mềm', 6, 0),
(N'Nghiên cứu phương pháp, kỹ thuật xử lý thông tin bằng máy tính điện tử', 6, 0),
(N'Nghiên cứu về công nghệ phần cứng và phần mềm', 6, 0),
(N'Là một phần mềm chạy trên máy tính, dùng để điều hành, quản lý các thiết bị phần cứng và các tài nguyên phần mềm trên máy tính', 6, 1),
(N'Chọn thư mục hay tâp tin cần xóa -> Delete', 7, 0),
(N'Chọn thư mục hay tâp tin cần xóa -> Ctrl + Delete', 7, 0),
(N'Chọn thư mục hay tâp tin cần xóa -> Alt + Delete', 7, 0),
(N'Chọn thư mục hay tâp tin cần xóa -> Shift + Delete', 7, 1),
(N'Menu pad', 8, 0),
(N'Menu options', 8, 0),
(N'Menu bar', 8, 1),
(N'Tất cả đều sai', 8, 0),
(N'In màn hình hiện hành ra máy in', 9, 0),
(N'Không có công dụng gì khi sử dụng 1 mình nó', 9, 0),
(N'In văn bản hiện hành ra máy in', 9, 0),
(N'Chụp màn hình hiện hành', 9, 1),
(N'Maximum', 10, 0),
(N'Minimum', 10, 0),
(N'Restore down', 10, 1),
(N'Close', 10, 0),
(N'Tạo một văn bản mới', 11, 0),
(N'Chức năng thay thế nội dung trong soạn thảo', 11, 0),
(N'Định dạng chữ hoa', 11, 0),
(N'Lưu nội dung tập tin văn bản vào đĩa', 11, 1),
(N'View – Symbol', 12, 0),
(N'Format – Symbol', 12, 0),
(N'Tools – Symbol', 12, 0),
(N'Insert – Symbol', 12, 1),
(N'Bấm tổ hợp phím Ctrl – Enter', 13, 0),
(N'Bấm phím Enter', 13, 1),
(N'Bấm tổ hợp phím Shift – Enter', 13, 0),
(N'Word tự động, không cần bấm phím', 13, 0),
(N'Shift + End', 14, 0),
(N'Alt + End', 14, 0),
(N'Ctrl + End', 14, 1),
(N'Ctrl + Alt + End', 14, 0),
(N'Alt + A', 15, 0),
(N'Ctrl + A', 15, 1),
(N'Ctrl + Shift + A', 15, 0),
(N'Câu 1 và 2', 15, 0),
(N'Click 1 lần trên đoạn', 16, 0),
(N'Click 2 lần trên đoạn', 16, 0),
(N'Click 3 lần trên đoạn', 16, 1),
(N'Click 4 lần trên đoạn', 16, 0),
(N'Nháy đúp chuột vào từ cần chọn', 17, 0),
(N'Bấm tổ hợp phím Ctrl – C', 17, 0),
(N'Nháy chuột vào từ cần chọn', 17, 0),
(N'Bấm phím Enter', 17, 0),
(N'Table – Merge Cells', 18, 0),
(N'Table – Split Cells', 18, 0),
(N'Tools – Split Cells', 18, 0),
(N'Table – Cells', 18, 0),
(N'15', 19, 0),
(N'16', 19, 0),
(N'17', 19, 1),
(N'18', 19, 0),
(N'10', 20, 0),
(N'9', 20, 0),
(N'11', 20, 1),
(N'Lỗi', 20, 0),
(N'6', 21, 0),
(N'11', 21, 1),
(N'5', 21, 0),
(N'0', 21, 0),
(N'Vlookup', 22, 1),
(N'IF', 22, 0),
(N'Left', 22, 0),
(N'Sum', 22, 0),
(N'File / Save As', 23, 1),
(N'File / Save', 23, 0),
(N'File / New', 23, 0),
(N'Edit / Replace', 23, 0),
(N'Số ngày trong tháng', 24, 0),
(N'Số tháng trong năm', 24, 0),
(N'Ngày hiện hành của hệ thống', 24, 1),
(N'Số giờ trong ngày', 24, 0),
(N'B$1$$10$D', 25, 0),
(N'B$1', 25, 0),
(N'$B1:$D10', 25, 0),
(N'$B$1:$D$10', 25, 1),
(N'0', 26, 0),
(N'5', 26, 1),
(N'#VALUE!', 26, 0),
(N'#NAME!', 26, 0)