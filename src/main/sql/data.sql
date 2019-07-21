INSERT INTO library.users (email, password, name, lastname)
VALUES
  ('ya@ya.ru', '12345', 'Vasya', 'Vasilev'),
  ('go@google.com', '54789', 'Ivan', 'Ivanov'),
  ('vn@yandex.net', 'qwerty', 'Petya', 'Petrovich'),
  ('o@yahoo.net', 'qwerty', 'Pitr', 'Pitrovich'),
  ('code@mail.ru', 'qwerty', 'Leha', 'Lehovich'),
  ('is@ma.com', 'qwerty', 'Nastya', 'Eveeivich'),
  ('del@hoo.com', 'qwerty', 'Natasha', 'Evanovna');

INSERT INTO library.roles (name)
VALUES
('User'),
('Administrator');

INSERT INTO library.users_roles (user_id, role_id)
VALUES
(1, 1),
(2, 2),
(3, 1),
(4, 1),
(5, 1),
(6, 2),
(7, 1);

INSERT INTO library.books (name, description)
VALUES
  ('Anna Karenina', 'about Anna Karenina'),
  ('Love and Death', 'about Love and Death'),
  ('Dogs', 'about dogs'),
  ('Cats', 'about cats'),
  ('Animals', 'about animals'),
  ('Mems', 'about mems'),
  ('Politics', 'about politics'),
  ('Psychology', 'about psychology');

INSERT INTO library.authors (name, lastname)
VALUES
  ('Lev', 'Tolstoi'),
  ('Alexandr', 'Pushkin'),
  ('Alexei', 'Panin'),
  ('Yuri', 'Kuklachev'),
  ('Nikolai', 'Drozdov'),
  ('Patrik', 'Stuart'),
  ('Vladimir', 'Navalni'),
  ('Philip', 'Kirkorov');

INSERT INTO library.authors_books (author_id, book_id)
VALUES
  (1, 1),
  (2, 2),
  (3, 3),
  (4, 4),
  (5, 5),
  (6, 6),
  (7, 7),
  (8, 8);

INSERT INTO library.statuses (name)
VALUES ('available'),
  ('on hands'),
  ('in reading room'),
  ('ordered');

INSERT INTO library.items (book_id, user_id, status_id, date_items)
VALUES
  (1, NULL, 1, now()),
  (1, NULL, 1, now()),
  (1, NULL, 1, now()),
  (2, NULL, 1, now()),
  (2, NULL, 1, now()),
  (2, NULL, 1, now()),
  (3, NULL, 1, now()),
  (3, NULL, 1, now()),
  (3, NULL, 1, now()),
  (4, NULL, 1, now()),
  (4, NULL, 1, now()),
  (4, NULL, 1, now()),
  (5, NULL, 1, now()),
  (5, NULL, 1, now()),
  (5, NULL, 1, now()),
  (7, 1, 3, now()),
  (7, 2, 4, now()),
  (7, 3, 4, now()),
  (6, 4, 2, now()),
  (6, 5, 3, now()),
  (6, 6, 3, now()),
  (8, 4, 2, now()),
  (8, 5, 2, now()),
  (8, 7, 2, now());