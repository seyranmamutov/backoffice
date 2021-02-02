
create  table inventories (
    id bigint not null auto_increment,
    brand_id bigint not null,
    received datetime,
    quantity bigint,
    primary key (id),
    foreign key (brand_id) references brands(id)

);
