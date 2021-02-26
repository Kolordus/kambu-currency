create table persisted_request_invoked_external_api_urls
(
    persisted_request_id      bigint references persisted_request (id),
    invoked_external_api_urls varchar(256)
);
