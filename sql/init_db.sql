CREATE SCHEMA `minfin`;

CREATE TABLE `minfin`.`deal`
(
    `id`           INT          NOT NULL AUTO_INCREMENT,
    `url`          VARCHAR(255) NULL,
    `minfin_id`    VARCHAR(45)  NULL,
    `time`         VARCHAR(5)   NULL,
    `date`         DATETIME     NULL,
    `currencyRate` VARCHAR(45)  NULL,
    `sum`          VARCHAR(45)  NULL,
    `currency`     INT          NULL,
    `phone`        VARCHAR(45)  NULL,
    `msg`          VARCHAR(255) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `minfin`.`currency`
(
    `id`   INT        NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(3) NULL,
    `symbol` VARCHAR(1) NULL,
    PRIMARY KEY (`id`)
);