INSERT INTO ordertb (id, from_location, note, number, status, to_location,
                        deliveryman_id)
    VALUES (1, 'Москва', 'Отправить как можно скорее', 1337,
            'Создан', 'Санкт-Петербург', 1);

INSERT INTO ordertb (id, from_location, note, number, status, to_location,
                     deliveryman_id)
VALUES (2, 'Новосибирск', 'Оплата наличными при получении', 1237,
        'Готов к отправке', 'Екатеринбург', 2);

INSERT INTO ordertb (id, from_location, note, number, status, to_location,
                     deliveryman_id)
VALUES (3, 'Краснодар', 'Жду звонка от доставщика', 1238,
        'Отправлен', 'Сочи', 3);

INSERT INTO ordertb (id, from_location, note, number, status, to_location,
                     deliveryman_id)
VALUES (4, 'Самара', 'Указать точный адрес доставки', 1239,
        'Создан', 'Москва', 4);

INSERT INTO ordertb (id, from_location, note, number, status, to_location,
                     deliveryman_id)
VALUES (5, 'Казань', 'Доставить после 18:00', 1240,
        'Готов к отправке', 'Уфа', 5);

INSERT INTO ordertb (id, from_location, note, number, status, to_location,
                     deliveryman_id)
VALUES (6, 'Ростов-на-Дону', 'Доставить в лабораторию', 1241,
        'Создан', 'Волгоград', 6);

INSERT INTO ordertb (id, from_location, note, number, status, to_location,
                     deliveryman_id)
VALUES (7, 'Ульяновск', 'Без соли и перца, пожалуйста', 1242,
        'Комплектуется', 'Самара', 7);

INSERT INTO ordertb (id, from_location, note, number, status, to_location,
                     deliveryman_id)
VALUES (8, 'Томск', 'Необходимо хранение в холодильнике', 1243,
        'Доставлен', 'Омск', 8);

INSERT INTO ordertb (id, from_location, note, number, status, to_location,
                     deliveryman_id)
VALUES (9, 'Калининград', 'Оставить у входной двери, если не дома', 1244,
        'Комплектуется', 'Москва', 9);

INSERT INTO ordertb (id, from_location, note, number, status, to_location,
                     deliveryman_id)
VALUES (10, 'Екатеринбург', 'Проверить на целостность при получении', 1245,
        'Отправлен', 'Тюмень', 10);

INSERT INTO ordertb (id, from_location, note, number, status, to_location,
                     deliveryman_id)
VALUES (11, 'Москва', 'Доставить до конца месяца', 12345,
        'Отправлен', 'Санкт-Петербург', 1);

INSERT INTO ordertb (id, from_location, note, number, status, to_location,
                     deliveryman_id)
    VALUES (12, 'Санкт-Петербург', 'Срочная доставка', 67890,
            'Отправлен', 'Новосибирск', 2);

INSERT INTO ordertb (id, from_location, note, number, status, to_location,
                     deliveryman_id)
    VALUES (13, 'Екатеринбург', 'Особый груз', 24680,
            'Отправлен', 'Краснодар', 3);

INSERT INTO ordertb (id, from_location, note, number, status, to_location,
                     deliveryman_id)
    VALUES (14, 'Новосибирск', 'Необходима срочная доставка', 13579,
            'Отправлен', 'Москва', 4);

INSERT INTO ordertb (id, from_location, note, number, status, to_location,
                     deliveryman_id)
    VALUES (15, 'Санкт-Петербург', 'Оплата при получении', 24680,
            'Отправлен', 'Владивосток', 5);

INSERT INTO ordertb (id, from_location, note, number, status, to_location,
                     deliveryman_id)
    VALUES (16, 'Краснодар', 'Доставить до конца недели', 35791,
            'Отправлен', 'Санкт-Петербург', 6);

INSERT INTO ordertb (id, from_location, note, number, status, to_location,
                     deliveryman_id)
    VALUES (17, 'Москва', 'Особые условия доставки', 46802,
            'Отправлен', 'Красноярск', 7);

INSERT INTO ordertb (id, from_location, note, number, status, to_location,
                     deliveryman_id)
    VALUES (18, 'Самара', 'Требуется страховка', 57913,
            'Отправлен', 'Ростов-на-Дону', 8);

INSERT INTO ordertb (id, from_location, note, number, status, to_location,
                     deliveryman_id)
    VALUES (19, 'Ростов-на-Дону', 'Необходимо доставить к определенной дате', 68024,
            'Отправлен', 'Москва', 9);

INSERT INTO ordertb (id, from_location, note, number, status, to_location,
                     deliveryman_id)
    VALUES (20, 'Санкт-Петербург', 'Доставить в течение 2 дней', 79135,
            'Отправлен', 'Самара', 10);


INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (1, 1);
INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (1, 6);
INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (1, 8);

INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (2, 3);
INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (2, 4);

INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (3, 5);

INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (4, 7);
INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (4, 8);

INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (5, 9);

INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (6, 10);

INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (7, 2);

INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (8, 1);
INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (8, 8);

INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (9, 3);
INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (9, 5);
INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (9, 9);

INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (10, 2);
INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (10, 10);

INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (11, 3);

INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (12, 2);

INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (13, 4);

INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (41, 1);

INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (15, 4);

INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (16, 2);

INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (17, 3);

INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (18, 1);

INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (19, 1);

INSERT INTO ordertb_cargo (orderTB_id, cargo_id)
    VALUES (20, 3);