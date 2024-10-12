drop table if exists checklist cascade;
drop table if exists habits cascade;
drop table if exists users cascade;
drop sequence if exists user_id_seq;
--create sequence user_id_seq start with 1 increment by 1;
create table users (
                       id bigint not null,
                       email varchar(255) unique,
                       password varchar(255) not null,
                       role varchar(255) not null check (role in ('ROLE_USER','ROLE_ADMIN')),
                       name varchar(255) not null unique,
                       primary key (id)
);
create table habits (
                        id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                        title varchar(255) not null unique,
                        description varchar(255) not null,
                        period varchar(255) not null check (period in ('DAILY','WEEKLY')),
                        start TIMESTAMP WITHOUT TIME ZONE,
                        user_id BIGINT,
                        CONSTRAINT fk_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE


);
create table checklist (
                        id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                        date DATE,
                        habit_id BIGINT,
                        CONSTRAINT unique_habit_and_date UNIQUE (habit_id, date),
                        CONSTRAINT fk_habit FOREIGN KEY (habit_id) REFERENCES habits (id) ON DELETE CASCADE


)



