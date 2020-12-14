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
--
insert into license values ( 1, '2388ii789', 'aziwa@gmail.com');
insert into license values ( 2, '2388ii889', 'maria@gmail.com');
insert into license values ( 3, '2388gi889', 'charles@gmail.com');
insert into license values ( 4, '2388yi889', 'habtu@gmail.com');
insert into license values ( 5, '2388yuyi889', 'zhang@gmail.com');

insert into address values (1000, 'Fairfield', 'IA', '1000 N', '52557');
--
insert into user VALUES(111, 'aziwa@gmail.com', 'Lee sensei', true, 'sensei', '2388ii789', 'aaa', true, 1000);
insert into user VALUES(222, 'maria@gmail.com', 'Maria Me', true, 'Maria', '2388ii889', 'aaa', true, 1000);
insert into user VALUES(333, 'charles@gmail.com', 'Maria Me', true, 'Charle', '2388gi889', 'aaa', false, 1000);
insert into user VALUES(444, 'habtu@gmail.com', 'Maria Me', true, 'habtu', '2388yi889', 'aaa', true, 1000);
insert into user VALUES(555, 'zhang@gmail.com', 'Maria Me', true, 'Hail', '2388yuyi889', 'aaa', false, 1000);

insert into local_paypal_account values ( 111, 100000, 100000, 0, 111);
insert into local_paypal_account values ( 2222, 40000, 40000, 0, 222);
insert into local_paypal_account values ( 333, 4000, 4000, 0, 333);
insert into local_paypal_account values ( 444, 4000, 4000, 0, 444);
insert into local_paypal_account values ( 555, 4000, 4000, 0, 555);
--
insert into product values (1,    to_date('15-12-20','dd-mm-yy')    ,to_date('20-12-20','dd-mm-yy'),    3    ,'A',    'N',    3,  'N',    'N',    'N',    'N',    'N', 'N', 'Charles',  30,    111);
insert into product values (2,    to_date('16-12-20','dd-mm-yy')    ,to_date('21-12-20','dd-mm-yy'),    3    ,'B',    'N',    3,  'N',    'N',    'N',    'N',    'N', 'N', 'Ali',   30,    222);
insert into product values (3,    to_date('17-12-20','dd-mm-yy')    ,to_date('22-12-20','dd-mm-yy'),    3    ,'C',    'N',    3,  'N',    'N',    'N',    'N',    'N', 'N', 'Mary',    30,    333);
insert into product values (4,    to_date('18-12-20','dd-mm-yy')    ,to_date('23-12-20','dd-mm-yy'),    3    ,'D',    'N',    3,  'N',    'N',    'N',    'N',    'N', 'N', 'Halian',   30,    444);
insert into product values (5,    to_date('19-12-20','dd-mm-yy')    ,to_date('24-12-20','dd-mm-yy'),    3    ,'E',    'N',    3,  'N',    'N',    'N',    'N',    'N', 'N', 'Habtu',   30,    555);

insert into category_product values ( 1, 1 );
insert into category_product values ( 2, 2 );
insert into category_product values ( 3, 3 );
insert into category_product values ( 4, 4);

