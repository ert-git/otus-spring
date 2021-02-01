insert into authors ( first_name, last_name, middle_name)
values ('Александр', 'Пушкин', 'Сергеевич'),
       ('Гомер', null, null);

insert into genres (name)
values ('Античная литература'),
       ('Русская классика');

insert into books (title)
values ('Евгений Онегин'),
       ('Одиссея');


insert into book_author (book_id, author_id)
values (1, 1),
       (2, 1);

insert into book_genre (book_id, genre_id)
values (1, 2),
       (2, 1);