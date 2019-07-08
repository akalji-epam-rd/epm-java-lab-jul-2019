create table authors
(
  id       integer not null
    constraint authors_pkey
    primary key,
  name     text    not null,
  lastname text
);

create table books
(
  id          integer not null
    constraint books_pkey
    primary key,
  name        text    not null,
  description text
);

create table users
(
  id       integer not null
    constraint users_pkey
    primary key,
  email    text    not null
    constraint users_email_key
    unique,
  password text    not null,
  name     text,
  lastname text
);

create table roles
(
  id   integer not null
    constraint roles_pkey
    primary key,
  name text    not null
    constraint roles_name_key
    unique
);

create table statuses
(
  id   integer not null
    constraint statuses_pkey
    primary key,
  name text    not null
    constraint statuses_name_key
    unique
);

create table authors_books
(
  author_id integer not null
    constraint authors_books_author_id_fkey
    references authors,
  book_id   integer not null
    constraint authors_books_book_id_fkey
    references books,
  constraint authors_books_pkey
  primary key (author_id, book_id)
);

create table users_roles
(
  user_id integer not null
    constraint users_roles_user_id_fkey
    references users,
  role_id integer not null
    constraint users_roles_role_id_fkey
    references roles,
  constraint users_roles_pkey
  primary key (user_id, role_id)
);

create table items
(
  id        integer                   not null
    constraint items_pkey
    primary key,
  book_id   integer                   not null
    constraint items_book_id_fkey
    references books,
  user_id   integer
    constraint items_user_id_fkey
    references users,
  status_id integer                   not null
    constraint items_status_id_fkey
    references statuses,
  date      date default CURRENT_DATE not null
);

