create sequence user_id_seq start with 1 increment by 50;

create table users
(
    id bigint default nextval('user_id_seq') not null,
    name        text not null unique,
    password text,
    openid   text,
    login_type   text,
    role   text,
    primary key (id)
);
