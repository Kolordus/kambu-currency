create table persisted_request_desired_currencies_response
(
    persisted_request_id   bigint references persisted_request (id),
    desired_currencies_response_key varchar(255),
    desired_currencies_response     double precision
);
