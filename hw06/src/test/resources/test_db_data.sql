insert into authors (id, first_name, last_name, middle_name)
values (1001, 'Александр', 'Пушкин', 'Сергеевич'),
       (1002, 'Гомер', null, null),
       (1003, 'a1003', null, null),
       (1004, 'a1004', null, null),
       (1005, 'a1005', null, null)
       ;

insert into genres (id, name)
values (1001, 'Античная литература'),
       (1002, 'Русская классика'),
       (1003, 'g1003'),
       (1004, 'g1004')
       ;

insert into books (id, title)
values (1001, 'Евгений Онегин'),
       (1002, 'Одиссея'),
       (1003, 'b1003'),
       (1004, 'b1004'),
       (1005, 'b1005')
       ;

insert into book_author (book_id, author_id)
values (1001, 1001),
       (1002, 1002),
       (1003, 1003),
       (1003, 1004),
       (1004, 1004),
       (1004, 1005),
       (1005, 1003)
       ;

insert into book_genre (book_id, genre_id)
values (1001, 1002),
       (1002, 1001),
       (1003, 1003),
       (1004, 1003),
       (1004, 1004),
       (1005, 1003)
       ;