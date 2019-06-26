CREATE SCHEMA IF NOT EXISTS `minfin`;

CREATE TABLE IF NOT EXISTS `minfin`.`deal`
(
    `id`           INT          NOT NULL AUTO_INCREMENT,
    `url`          VARCHAR(255) NULL,
    `minfin_id`    VARCHAR(45)  NULL,
    `time`         VARCHAR(5)   NULL,
    `date`         DATETIME     NULL,
    `currencyRate` VARCHAR(45)  NULL,
    `sum`          VARCHAR(45)  NULL,
    `currency`     INT          NOT NULL,
    `phone`        VARCHAR(45)  NULL,
    `msg`          VARCHAR(255) NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `minfin`.`currency`
(
    `id`     INT        NOT NULL AUTO_INCREMENT,
    `name`   VARCHAR(3) NULL,
    `symbol` VARCHAR(1) NULL,
    PRIMARY KEY (`id`)
);
INSERT INTO `minfin`.`currency` (`id`, `name`, `symbol`)
VALUES ('1', 'usd', '$');
INSERT INTO `minfin`.`currency` (`id`, `name`, `symbol`)
VALUES ('2', 'uah', '₴');

CREATE TABLE IF NOT EXISTS `minfin`.`transaction_type`
(
    `id`   INT NOT NULL AUTO_INCREMENT,
    `from` INT NULL,
    `to`   INT NULL,
    PRIMARY KEY (`id`)
);
INSERT INTO `minfin`.`transaction_type` (`id`, `from`, `to`)
VALUES ('1', '1', '2');
INSERT INTO `minfin`.`transaction_type` (`id`, `from`, `to`)
VALUES ('2', '2', '1');
INSERT INTO `minfin`.`transaction_type` (`id`, `from`, `to`)
VALUES ('3', '1', '1');
INSERT INTO `minfin`.`transaction_type` (`id`, `from`, `to`)
VALUES ('4', '2', '2');

CREATE TABLE IF NOT EXISTS `minfin`.`transaction`
(
    `id`            INT      NOT NULL AUTO_INCREMENT,
    `currency_rate` DOUBLE   NULL,
    `date`          DATETIME NULL,
    `type`          INT      NULL,
    `change_usd`    INT      NULL,
    `change_uah`    INT      NULL,
    `usd_before`    INT      NULL,
    `usd_after`     INT      NULL,
    `uah_before`    INT      NULL,
    `uah_after`     INT      NULL,
    PRIMARY KEY (`id`)
);