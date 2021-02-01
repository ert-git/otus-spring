create table authors (
    id  bigint primary key auto_increment,
    first_name varchar2,
    last_name varchar2,
    middle_name varchar2
);

create table genres (
    id   bigint primary key auto_increment,
    name varchar2
);

create table books (
    id        bigint primary key auto_increment,
    title     varchar2
);

create table book_author (
    id bigint primary key auto_increment,
    book_id bigint,
    author_id     bigint,
    foreign key (author_id) references authors (id),
    foreign key (book_id) references books (id)
);
create table book_genre (
    id bigint primary key auto_increment,
    book_id bigint,
    genre_id     bigint,
    foreign key (genre_id) references genres (id),
    foreign key (book_id) references books (id)
);


create table comments (
    id   bigint primary key auto_increment,
    text varchar2,
    book_id bigint,
    foreign key (book_id) references books (id)
);