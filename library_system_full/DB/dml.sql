USE library_db;

INSERT INTO book (isbn, name, author, introduction) VALUES
('9789864760001','示範書A','作者甲','簡介 A'),
('9789864760002','示範書B','作者乙','簡介 B');

INSERT INTO inventory (isbn, status) VALUES
('9789864760001','AVAILABLE'),
('9789864760001','AVAILABLE'),
('9789864760002','AVAILABLE');
