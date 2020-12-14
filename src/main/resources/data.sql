create table if not exists persistent_logins (
    username varchar_ignorecase(100) not null,
    series varchar(64) primary key,
    token varchar(64) not null,
    last_used timestamp not null
    );
INSERT INTO category(category_id, name) VALUES (1, 'Phones');
INSERT INTO category(category_id, name) VALUES (2, 'Laptops');
INSERT INTO category(category_id, name) VALUES (3, 'Home');
INSERT INTO category(category_id, name) VALUES (4, 'Cars');

insert into role values ( 1, 'SELLER');
insert into role values ( 2, 'BUYER');
insert into role values ( 3, 'ADMIN');

insert into license values ( 1, '2388ii789', 'aziwa@gmail.com');
insert into license values ( 2, '2388ii889', 'maria@gmail.com');
insert into license values ( 3, '2388gi889', 'charles@gmail.com');
insert into license values ( 4, '2388yi889', 'habtu@gmail.com');
insert into license values ( 5, '2388yuyi889', 'zhang@gmail.com');
insert into license values ( 6, '2388ti889', 'user1@gmail.com');
insert into license values ( 7, '2388ggi889', 'sensei1@gmail.com');
insert into license values ( 8, '2388dfsi889', 'loggerq@gmail.com');
insert into license values ( 9, '2388as889', 'cable1@gmail.com');
insert into license values ( 10, '2388dfasi889', 'laba1@gmail.com');

insert into address values (1, 'Fairfield', 'IA', '1000 N', '52557');
insert into address values (2, 'las vegas', 'CA', '1000 N', '92104');

insert into user VALUES(1, 'aziwa@@gmail.com', 'Lee sensei', true, true, 'abc', 1, 1, 1);
insert into user VALUES(2, 'maria@@gmail.com', 'Maria Me', true, true, 'aaa', 1, 2, 1);
insert into user VALUES(3, 'charles@@gmail.com', 'Maria Me', true, true, 'aaa', 2, 3, 2);
insert into user VALUES(4, 'habtu@@gmail.com', 'Maria Me', true, true, 'aaa', 1, 4, 2);
insert into user VALUES(5, 'zhang@@gmail.com', 'Maria Me', true, true, 'aaa', 2, 5, 1);
insert into user VALUES(6, 'user1@@gmail.com', 'Maria Me', true, true, 'aaa', 1, 6, 1);
insert into user VALUES(7, 'sensei1@@gmail.com', 'Maria Me', true, true, 'aaa', 1, 7, 2);
insert into user VALUES(8, 'loggerq@@gmail.com', 'Maria Me', true, true, 'aaa', 2, 8, 2);
insert into user VALUES(9, 'cable1@@gmail.com', 'Maria Me', true, false, 'aaa', 1, 9, 2);
insert into user VALUES(10, 'laba1@@gmail.com', 'Maria Me', true, false, 'aaa', 1, 10, 2);

insert into paypal_account values ( 1, 100000, 100000, 0, 1);
insert into paypal_account values ( 2, 40000, 40000, 0, 2);
insert into paypal_account values ( 3, 4000, 4000, 0, 3);
insert into paypal_account values ( 4, 4000, 4000, 0, 4);
insert into paypal_account values ( 5, 4000, 4000, 0, 5);
insert into paypal_account values ( 6, 400, 400, 0, 6);
insert into paypal_account values ( 7, 400, 400, 0, 7);
insert into paypal_account values ( 8, 400, 400, 0, 8);
insert into paypal_account values ( 9, 400, 400, 0, 9);
insert into paypal_account values ( 10, 400, 400, 0, 10);

insert into product values (1,    to_date('07-12-20','dd-mm-yy')    ,to_date('17-12-20','dd-mm-yy'),    3    ,'A',    'N',    3,  'N',    'N',    'N',    'N',    'N', 'N', 'Charles',  30,    1);
insert into product values (2,    to_date('08-12-20','dd-mm-yy')    ,to_date('18-12-20','dd-mm-yy'),    3    ,'B',    'N',    3,  'N',    'N',    'N',    'N',    'N', 'N', 'Ali',   30,    2);
insert into product values (3,    to_date('07-12-20','dd-mm-yy')    ,to_date('19-12-20','dd-mm-yy'),    3    ,'C',    'N',    3,  'N',    'N',    'N',    'N',    'N', 'N', 'Mary',    30,    5);
insert into product values (4,    to_date('10-12-20','dd-mm-yy')    ,to_date('20-12-20','dd-mm-yy'),    3    ,'D',    'Y',    3,  'N',    'N',    'N',    'N',    'N', 'N', 'Halian',   30,    6);
insert into product values (5,    to_date('11-12-20','dd-mm-yy')    ,to_date('21-12-20','dd-mm-yy'),    3    ,'E',    'N',    3,  'N',    'N',    'N',    'N',    'N', 'N', 'Habtu',   30,    3);
insert into product values (6,    to_date('12-12-20','dd-mm-yy')    ,to_date('22-12-20','dd-mm-yy'),    3    ,'F',    'N',    3,  'N',    'N',    'N',    'N',    'N', 'N', 'Charles',   30,    1);
insert into product values (7,    to_date('13-12-20','dd-mm-yy')    ,to_date('23-12-20','dd-mm-yy'),    3    ,'G',    'N',    3,  'N',    'N',    'N',    'N',    'N', 'N',  'Ali',  30,    2);
insert into product values (8,    to_date('14-12-20','dd-mm-yy')    ,to_date('24-12-20','dd-mm-yy'),    3    ,'H',    'N',    3,  'N',    'N',    'N',    'N',    'N', 'N',  'Mary', 30,    5);
insert into product values (9,    to_date('15-12-20','dd-mm-yy')    ,to_date('25-12-20','dd-mm-yy'),    3    ,'I',    'N',    3,  'N',    'N',    'N',    'N',    'N', 'N',  'Halian', 30,    6);
insert into product values (10,    to_date('16-12-20','dd-mm-yy')    ,to_date('26-12-20','dd-mm-yy'),   3    ,'J',    'N',    3,  'N',    'N',    'N',    'N',    'N', 'N',  'Habtu',  30,    3);

insert into category_product values ( 1, 1 );
insert into category_product values ( 1, 2 );
insert into category_product values ( 1, 3 );
insert into category_product values ( 1, 10 );
insert into category_product values ( 1, 1 );
insert into category_product values ( 1, 2 );
insert into category_product values ( 1, 3 );
insert into category_product values ( 1, 10 );
insert into category_product values ( 1, 1 );
insert into category_product values ( 1, 2 );
insert into category_product values ( 1, 3 );
insert into category_product values ( 1, 10 );
insert into category_product values ( 2, 8 );
insert into category_product values ( 2, 4 );
insert into category_product values ( 2, 5 );
insert into category_product values ( 3, 6 );
insert into category_product values ( 3, 7 );
insert into category_product values ( 4, 8 );
insert into category_product values ( 4, 9 );
insert into category_product values ( 4, 10 );

