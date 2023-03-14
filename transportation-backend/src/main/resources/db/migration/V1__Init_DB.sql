create table cargo (
   id bigint not null auto_increment,
   count integer not null,
   name varchar(255) not null,
   price float(53) not null,
   primary key (id)
);