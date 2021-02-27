create table currency (
    id serial primary key,
    code varchar(3) not null
);

create table persisted_request(
    id serial primary key,
    request_url varchar (255),
    time_created timestamp,
    amount real,
    base_currency varchar (3),
    exception_name varchar (255)
);
