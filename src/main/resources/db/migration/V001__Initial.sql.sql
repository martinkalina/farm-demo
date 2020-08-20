create table farm
(
    id   serial not null
        constraint farm_pk
            primary key,
    name text   not null,
    note text
);

create table field
(
    id      serial  not null
        constraint field_pk
            primary key,
    name    text    not null,
    borders geometry,
    farm_id integer not null
        constraint field_farm_id_fk
            references farm on delete cascade
);
