
create  table brands (
    id bigint not null auto_increment,
    brand_id bigint,
    name varchar(255) not null,
    inventory_sum bigint,
    primary key (id)
);

create index brands_brand_id on brands(brand_id);