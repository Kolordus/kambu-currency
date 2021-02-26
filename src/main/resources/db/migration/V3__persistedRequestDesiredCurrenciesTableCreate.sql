create table persisted_request_desired
(
    persisted_request_id   bigint references persisted_request (id),
    desired_currencies_key varchar(255),
    desired_currencies     double precision
);
