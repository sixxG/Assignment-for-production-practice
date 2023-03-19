create table cargo (
    id bigint not null auto_increment,
    count integer not null,
    name varchar(255) not null,
    price float(53) not null,
    primary key (id)
);

create table deliveryman (
    id bigint not null auto_increment,
    fio varchar(255) not null,
    phone varchar(25) not null,
    vehicle varchar(255) not null,
    primary key (id)
);

create table orderTB (
    id bigint not null auto_increment,
    from_location varchar(255) not null,
    note varchar(255) not null,
    number bigint not null,
    status varchar(255) not null,
    to_location varchar(255) not null,
    deliveryman_id bigint not null,
    primary key (id)
);

create table orderTB_cargo (
    orderTB_id bigint not null,
    cargo_id bigint not null,
    primary key (orderTB_id, cargo_id)
);


alter table orderTB_cargo
    add constraint FK23g9y55s151x74mo3nnidfuqg
    foreign key (cargo_id)
    references cargo (id)
    on delete cascade;

alter table orderTB
    add constraint FK48p0yr61a8tjvn8ol3sq54uin
    foreign key (deliveryman_id)
    references deliveryman (id);