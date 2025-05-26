truncate table users;

insert into users(name, password, openid, login_type, role) values
('user','{noop}user',null,'direct', 'USER')
;