CREATE SCHEMA IF NOT EXISTS `minfin`;

CREATE TABLE IF NOT EXISTS `minfin`.`sell_price`
(
    `id`           INT          NOT NULL AUTO_INCREMENT,
    `date`         DATETIME     NULL,
    `price` DOUBLE,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `minfin`.`buy_price`
(
    `id`           INT          NOT NULL AUTO_INCREMENT,
    `date`         DATETIME     NULL,
    `price` DOUBLE,
    PRIMARY KEY (`id`)
);

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
    `watchCount`   INT          NULL,
    `active`       BOOLEAN      NULL,
    PRIMARY KEY (`id`)
);

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

CREATE TABLE IF NOT EXISTS `minfin`.`trade_status`
(
    `id`         INT      NOT NULL AUTO_INCREMENT,
    `start_date` DATETIME NULL,
    `end_date`   DATETIME NULL,
    PRIMARY KEY (`id`)
);

# dictionaries
DROP TABLE `minfin`.`currency`;
CREATE TABLE `minfin`.`currency`
(
    `id`     INT        NOT NULL AUTO_INCREMENT,
    `name`   VARCHAR(3) NULL,
    `symbol` VARCHAR(3) NULL,
    PRIMARY KEY (`id`)
);
INSERT INTO `minfin`.`currency` (`id`, `name`, `symbol`)
VALUES ('1', 'usd', '$');
INSERT INTO `minfin`.`currency` (`id`, `name`, `symbol`)
VALUES ('2', 'uah', '₴');

DROP TABLE `minfin`.`transaction_type`;
CREATE TABLE `minfin`.`transaction_type`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `from` INT         NULL,
    `to`   INT         NULL,
    `name` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
);

INSERT INTO `minfin`.`transaction_type` (`id`, `from`, `to`, `name`)
VALUES ('1', '1', '2', 'usd-uah');
INSERT INTO `minfin`.`transaction_type` (`id`, `from`, `to`, `name`)
VALUES ('2', '2', '1', 'uah-usd');
INSERT INTO `minfin`.`transaction_type` (`id`, `from`, `to`, `name`)
VALUES ('3', '1', '1', 'invest_usd');
INSERT INTO `minfin`.`transaction_type` (`id`, `from`, `to`, `name`)
VALUES ('4', '2', '2', 'invest_uah');

DROP TABLE `minfin`.`user_role`;
CREATE TABLE `minfin`.`user_role` (`id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
`name` VARCHAR(45) NULL,
PRIMARY KEY (`id`),
UNIQUE INDEX `id_UNIQUE` (`id` ASC));

INSERT INTO `minfin`.`user_role` (`name`) VALUES ('admin');
INSERT INTO `minfin`.`user_role` (`name`) VALUES ('cashier');
INSERT INTO `minfin`.`user_role` (`name`) VALUES ('uber_admin');

DROP TABLE `minfin`.`user`;
CREATE TABLE `minfin`.`user` (
                                 `id` INT NOT NULL AUTO_INCREMENT,
                                 `login` VARCHAR(45) NOT NULL,
                                 `pass` VARCHAR(45) NOT NULL,
                                 `role` INT NOT NULL,
                                 PRIMARY KEY (`id`),
                                 UNIQUE INDEX `id_UNIQUE` (`id` ASC),
                                 UNIQUE INDEX `login_UNIQUE` (`login` ASC));

INSERT INTO `minfin`.`user` (`login`, `pass`, `role`) VALUES ('misha', '1111', '1');
INSERT INTO `minfin`.`user` (`login`, `pass`, `role`) VALUES ('vovik', '1111', '2');
INSERT INTO `minfin`.`user` (`login`, `pass`, `role`) VALUES ('luchyk', '1111', '3');