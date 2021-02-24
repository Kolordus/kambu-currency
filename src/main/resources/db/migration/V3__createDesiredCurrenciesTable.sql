create table persisted_request_desired(
    persisted_request_id bigint references persisted_request (id),
    desired varchar (256)
);

