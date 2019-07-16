DROP SCHEMA library CASCADE;

CREATE SCHEMA library;

CREATE TABLE library.users (
	id SERIAL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(50),
    lastname VARCHAR(50),
	CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT email_key UNIQUE (email)
)
WITH (oids = false);

ALTER TABLE library.users
	OWNER TO postgres;
    
CREATE TABLE library.roles (
	id SERIAL,
    name VARCHAR(50) NOT NULL,
	CONSTRAINT roles_pkey PRIMARY KEY (id),
    CONSTRAINT name_roles_key UNIQUE (name)
)
WITH (oids = false);

ALTER TABLE library.roles
	OWNER TO postgres;

CREATE TABLE library.users_roles(
	user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    CONSTRAINT users_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT users_fkey FOREIGN KEY (user_id)
    	REFERENCES library.users(id)
        ON DELETE RESTRICT
    	ON UPDATE RESTRICT
    	NOT DEFERRABLE,
    CONSTRAINT roles_fkey FOREIGN KEY (role_id)
    	REFERENCES library.roles(id)
        ON DELETE RESTRICT
    	ON UPDATE RESTRICT
    	NOT DEFERRABLE
)
WITH (oids = false);

ALTER TABLE library.users_roles
	OWNER TO postgres;

CREATE TABLE library.books (
	id SERIAL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR,
    CONSTRAINT books_pkey PRIMARY KEY (id)
)
WITH (oids = false);

ALTER TABLE library.books
	OWNER TO postgres;
    
CREATE TABLE library.authors(
	id SERIAL,
    name VARCHAR(50) NOT NULL,
    lastname VARCHAR(50),
    CONSTRAINT authors_pkey PRIMARY KEY (id) 
)
WITH (oids = false);

ALTER TABLE library.authors
	OWNER TO postgres;
    
CREATE TABLE library.authors_books (
	author_id INTEGER NOT NULL,
    book_id INTEGER NOT NULL,
    CONSTRAINT authors_books_pkey PRIMARY KEY(author_id, book_id),
    CONSTRAINT authors_fkey FOREIGN KEY(author_id)
    	REFERENCES library.authors(id)
        ON DELETE RESTRICT
    	ON UPDATE RESTRICT
    	NOT DEFERRABLE,
    CONSTRAINT books_fkey FOREIGN KEY(book_id)
    	REFERENCES library.books(id)
        ON DELETE RESTRICT
    	ON UPDATE RESTRICT
    	NOT DEFERRABLE
)
WITH (oids = false);

ALTER TABLE library.authors_books
	OWNER TO postgres;
    
CREATE TABLE library.statuses(
	id SERIAL,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT statuses_pkey PRIMARY KEY(id),
    CONSTRAINT name_statuses_key UNIQUE(name)
)
WITH (oids = false);

ALTER TABLE library.statuses
	OWNER TO postgres;
	
    
CREATE TABLE library.items(
	id SERIAL,
    book_id INTEGER NOT NULL,
    user_id INTEGER,
    status_id INTEGER NOT NULL,
    date_items DATE NOT NULL DEFAULT NOW(),
    CONSTRAINT items_pkey PRIMARY KEY(id),
    CONSTRAINT books_fkey FOREIGN KEY(book_id)
    	REFERENCES library.books(id)
        ON DELETE CASCADE
    	ON UPDATE NO ACTION
    	NOT DEFERRABLE,
    CONSTRAINT users_fkey FOREIGN KEY(user_id)
    	REFERENCES library.users(id)
        ON DELETE SET NULL
    	ON UPDATE NO ACTION
    	NOT DEFERRABLE,
    CONSTRAINT statuses_fkey FOREIGN KEY(status_id)
    	REFERENCES library.statuses(id)
        ON DELETE NO ACTION
    	ON UPDATE NO ACTION
    	NOT DEFERRABLE
)
WITH (oids = false);

ALTER TABLE library.items
	OWNER TO postgres;


