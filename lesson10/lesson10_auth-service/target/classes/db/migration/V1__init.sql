create table users
(
    id         bigserial primary key,
    username   varchar(36) not null,
    password   varchar(80) not null,
    email      varchar(50) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table roles
(
    id         bigserial primary key,
    name       varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table users_roles
(
    user_id    bigint not null references users (id),
    role_id    bigint not null references roles (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users (username, password, email)
values ('bob', '$2a$12$1sA7c3sHHsW3w3zzpT.Q/.3riJE50FAU3gAl0c50UNdGspxvYnxEO', 'bob_johnson@gmail.com'),
       ('john', '$2a$12$1sA7c3sHHsW3w3zzpT.Q/.3riJE50FAU3gAl0c50UNdGspxvYnxEO', 'john_johnson@hamil.com');

insert into users_roles(user_id, role_id)
values (1, 1),
       (2, 2);
