create table persisted_request_desired(
    persisted_request_id bigint references persisted_request (id),
    desired_key varchar (256),
    desired double precision
);

