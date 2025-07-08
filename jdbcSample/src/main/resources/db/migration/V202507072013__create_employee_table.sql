CREATE TABLE employees(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(150) not null,
    salary decimal(10,2) not null,
    birthday TIMESTAMP not null,
    PRIMARY KEY (id)
)engine=InnoDB default charset=utf8mb4;