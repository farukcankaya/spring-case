create sequence hibernate_sequence start with 1 increment by 1
create table campaign (type varchar(31) not null, id bigint not null, discount_type varchar(255) not null, discount_value decimal(19,2) not null, maximum_discount_price decimal(19,2), name varchar(255), primary key (id))
create table category_campaign (category_id bigint not null, category_name varchar(255) not null, id bigint not null, primary key (id))
create table product_campaign (product_id bigint not null, product_name varchar(255) not null, id bigint not null, primary key (id))
alter table category_campaign add constraint FKqelsh8dqwh5m0d5ildx01oq5t foreign key (id) references campaign
alter table product_campaign add constraint FKa0l7yl8dd1iy7hexd7evg8fwn foreign key (id) references campaign
