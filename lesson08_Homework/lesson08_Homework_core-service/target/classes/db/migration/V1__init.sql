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

create table orders
(
    id          bigserial primary key,
    username    varchar(255),
    address    varchar(255),
    telephone_number    varchar(255),
    total_price numeric(8, 2),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp

);

create table orders_items
(
    id                bigserial primary key,
    order_id          bigint references orders (id),
    product_id        bigint references products (id),
    price_per_product numeric(8, 2),
    quantity          int,
    price             numeric(8, 2),
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp

)




