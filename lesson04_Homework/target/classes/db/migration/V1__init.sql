create table categories
(
    id         bigserial primary key,
    title      varchar(255),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table products
(
    id          bigserial primary key,
    title       varchar(255),
    price       numeric(8, 2),
    category_id bigint references categories (id),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

insert into categories(title)
values ('Food'),
       ('Electronic');

insert into products (title, price, category_id)
values ('Bread', 32.00, 1),
       ('Milk', 120.00, 1),
       ('Butter', 320.00, 1),
       ('Cheese', 500.00, 1);

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



create table groups
(
    id    bigserial primary key,
    title varchar(255)
);

insert into groups (title)
values ('ABC-123');

create table students
(
    id       bigserial primary key,
    name     varchar(255),
    age      integer,
    group_id bigint references groups (id)
);

insert into students (name, age, group_id)
values ('Bob', 30, 1),
       ('Max', 32, 1);



