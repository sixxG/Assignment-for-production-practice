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