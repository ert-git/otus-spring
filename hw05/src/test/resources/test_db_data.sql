insert into authors (id, first_name, last_name, middle_name)
values (1001, 'Александр', 'Пушкин', 'Сергеевич'),
       (1002, 'Гомер', null, null);

insert into genres (id, name)
values (1001, 'Античная литература'),
       (1002, 'Русская классика');

insert into books (id, title)
values (1001, 'Евгений Онегин'),
       (1002, 'Одиссея');


insert into book_author (book_id, author_id)
values (1001, 1001),
       (1002, 1002);

insert into book_genre (book_id, genre_id)
values (1001, 1002),
       (1002, 1001);