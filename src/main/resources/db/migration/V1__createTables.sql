create table currency (
    id serial primary key,
    code varchar(3) not null
);

create table persisted_request(
    id serial primary key,
    amount real,
    base varchar (3),
    desired varchar (50),
    time_created timestamp
);

