CREATE SCHEMA IF NOT EXISTS `minfin`;

CREATE TABLE IF NOT EXISTS `minfin`.`sell_price`
(
    `id`    INT      NOT NULL AUTO_INCREMENT,
    `date`  DATETIME NULL,
    `price` DOUBLE,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `minfin`.`buy_price`
(
    `id`    INT      NOT NULL AUTO_INCREMENT,
    `date`  DATETIME NULL,
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
CREATE TABLE `minfin`.`user_role`
(
    `id`   INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45)  NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC)
);

INSERT INTO `minfin`.`user_role` (`name`)
VALUES ('admin');
INSERT INTO `minfin`.`user_role` (`name`)
VALUES ('cashier');
INSERT INTO `minfin`.`user_role` (`name`)
VALUES ('uber_admin');

DROP TABLE `minfin`.`user`;
CREATE TABLE `minfin`.`user`
(
    `id`    INT         NOT NULL AUTO_INCREMENT,
    `login` VARCHAR(45) NOT NULL,
    `pass`  VARCHAR(45) NOT NULL,
    `role`  INT         NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC),
    UNIQUE INDEX `login_UNIQUE` (`login` ASC)
);

INSERT INTO `minfin`.`user` (`login`, `pass`, `role`)
VALUES ('misha', '1111', '1');
INSERT INTO `minfin`.`user` (`login`, `pass`, `role`)
VALUES ('vovik', '1111', '2');
INSERT INTO `minfin`.`user` (`login`, `pass`, `role`)
VALUES ('luchyk', '1111', '3');

DROP TABLE if exists `minfin`.`uber_driver`;
CREATE TABLE `minfin`.`uber_driver`
(
    `id`         INT          NOT NULL AUTO_INCREMENT,
    `driverUUID` VARCHAR(36)  NOT NULL,
    `driverType` VARCHAR(127) NOT NULL,
    `name`       VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);
INSERT INTO `minfin`.`uber_driver` (`driverUUID`, `driverType`, `name`)
VALUES ('252ccf66-ff11-468d-8488-3d4e046be9c0', 'usual_35_500_1000', 'Юрій_Горбатий');
INSERT INTO `minfin`.`uber_driver` (`driverUUID`, `driverType`, `name`)
VALUES ('198ed624-f207-4229-96ad-7ab9d1e320db', 'usual40', 'Андрій_Павлеса');
INSERT INTO `minfin`.`uber_driver` (`driverUUID`, `driverType`, `name`)
VALUES ('6f5cd143-0077-437d-bad4-eeb75577852a', 'owner_0', 'Володимир_Лучків');
INSERT INTO `minfin`.`uber_driver` (`driverUUID`, `driverType`, `name`)
VALUES ('6dfce16e-9334-4091-96a8-77d3508eaab5', 'usual40', 'Ростислав_Петрик');
INSERT INTO `minfin`.`uber_driver` (`driverUUID`, `driverType`, `name`)
VALUES ('8097b7d7-3b55-4493-89b7-1d59ed68844a', 'usual40', 'Ігор_Висоцький');
INSERT INTO `minfin`.`uber_driver` (`driverUUID`, `driverType`, `name`)
VALUES ('5c67ca64-6673-451b-b4f4-ce0d34365b9b', 'usual40', 'Владислав_Форкун');
INSERT INTO `minfin`.`uber_driver` (`driverUUID`, `driverType`, `name`)
VALUES ('62d19332-7f86-4104-b547-ac5eb542a32d', 'usual40', 'Костянтин_Кобзяк');
INSERT INTO `minfin`.`uber_driver` (`driverUUID`, `driverType`, `name`)
VALUES ('64dbdba4-b0a5-4e22-95ec-f3b37d7b61f3', 'owner_0', 'Mykhailo_Mikus');
INSERT INTO `minfin`.`uber_driver` (`driverUUID`, `driverType`, `name`)
VALUES ('661d24c5-4cad-4c63-a30f-ab7957599415', 'owner_5', 'Мар\'ян_Торган');
INSERT INTO `minfin`.`uber_driver` (`driverUUID`, `driverType`, `name`)
VALUES ('2a180965-5af6-41ea-bfd6-c59cb99e4268', 'partner', 'Михайло_Мікусь');
INSERT INTO `minfin`.`uber_driver` (`driverUUID`, `driverType`, `name`)
VALUES ('24de51c0-4e0e-4d3a-9e8a-40b2ae946936', 'owner_5', 'Богдан_Мікусь');



DROP TABLE if exists `minfin`.`uber_payment_record_row`;
CREATE TABLE `minfin`.`uber_payment_record_row`
(
    `tripUUID`     VARCHAR(36) NOT NULL,
    `amount`       DOUBLE      NOT NULL,
    `timestamp`    DATETIME    NOT NULL,

    `driverId`     INT         NOT NULL,
    `fileRowIndex` INT         NOT NULL,
    `creation`     DATETIME    NOT NULL,
    `itemType`     INT         NOT NULL,
    `description`  INT         NOT NULL,
    `disclaimer`   VARCHAR(255),
    `week_id`      INTEGER     NOT NULL,
    PRIMARY KEY (`tripUUID`, `amount`, `timestamp`, `disclaimer`)
);

DROP TABLE if exists `minfin`.`uber_item_type`;
CREATE TABLE `minfin`.`uber_item_type`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(36) NOT NULL,
    PRIMARY KEY (`id`)
);
INSERT INTO `minfin`.`uber_item_type` (`name`)
VALUES ('trip');
INSERT INTO `minfin`.`uber_item_type` (`name`)
VALUES ('cash_collected');
INSERT INTO `minfin`.`uber_item_type` (`name`)
VALUES ('tip');
INSERT INTO `minfin`.`uber_item_type` (`name`)
VALUES ('payouts');
INSERT INTO `minfin`.`uber_item_type` (`name`)
VALUES ('promotion');

DROP TABLE if exists `minfin`.`uber_description`;
CREATE TABLE `minfin`.`uber_description`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(36) NOT NULL,
    PRIMARY KEY (`id`)
);
INSERT INTO `minfin`.`uber_description` (`name`)
VALUES ('Готівка отримана');


DROP TABLE if exists `minfin`.`uber_sms_code`;
CREATE TABLE `minfin`.`uber_sms_code`
(
    `id`      INT        NOT NULL AUTO_INCREMENT,
    `code`    VARCHAR(4) NOT NULL,
    `created` DATETIME   NOT NULL,
    `used`    BOOLEAN    NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE if exists `minfin`.`uber_update_request`;
CREATE TABLE `minfin`.`uber_update_request`
(
    `id`      INT      NOT NULL AUTO_INCREMENT,
    `created` DATETIME NOT NULL,
    `started` BOOLEAN  NOT NULL,
    `updated` DATETIME NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE if exists `minfin`.`uber_captcha`;
CREATE TABLE `minfin`.`uber_captcha`
(
    `id`       INT          NOT NULL AUTO_INCREMENT,
    `created`  DATETIME     NOT NULL,
    `fileId`   VARCHAR(100) NULL,
    `answer`   VARCHAR(32)  NULL,
    `realPath` VARCHAR(200) NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE if exists `minfin`.`uber_filling`;
CREATE TABLE `minfin`.`uber_filling`
(
    `date`              DATETIME     NOT NULL,
    `card`              VARCHAR(16)  NULL,
    `station`           VARCHAR(16)  NULL,
    `amount`            DOUBLE       NULL,
    `discount`          DOUBLE       NULL,
    `amountAndDiscount` DOUBLE       NULL,
    `sapCode`           VARCHAR(16)  NULL,
    `shop`              VARCHAR(100) NULL,
    `car`               VARCHAR(100) NULL,
    `address`           VARCHAR(200) NULL,
    `itemAmount`        DOUBLE       NULL,
    `price`             DOUBLE       NULL,
    `week_id`           INTEGER      NOT NULL,
    `id`                INTEGER      NOT NULL,
    `km`                INTEGER      NULL,
    PRIMARY KEY (`date`)
);

DROP TABLE if exists `minfin`.`bolt_payment_record_day`;
CREATE TABLE `minfin`.`bolt_payment_record_day`
(
    `creation`               DATETIME     NOT NULL,
    `driverName`             VARCHAR(100) NOT NULL,
    `timestamp`              DATETIME     NOT NULL,
    `amount`                 DOUBLE       NOT NULL,
    `reject_amount`          DOUBLE       NOT NULL,
    `booking_payment_amount` DOUBLE       NOT NULL,
    `booking_payment_minus`  DOUBLE       NOT NULL,
    `additional_payment`     DOUBLE       NOT NULL,
    `bolt_commission`        DOUBLE       NOT NULL,
    `cash`                   DOUBLE       NOT NULL,
    `cash_turn`              DOUBLE       NOT NULL,
    `bonus`                  DOUBLE       NOT NULL,
    `compensation`           DOUBLE       NOT NULL,
    `week_balance`           DOUBLE       NOT NULL,
    `week_id`                INTEGER      NOT NULL,
    PRIMARY KEY (`driverName`, `timestamp`)
);

DROP TABLE if exists `minfin`.`week_range`;
CREATE TABLE `minfin`.`week_range`
(
    `id`    INT      NOT NULL AUTO_INCREMENT,
    `start` DATETIME NOT NULL,
    `end`   DATETIME NOT NULL,
    PRIMARY KEY (`id`)
);
ALTER TABLE `minfin`.`week_range`
    ADD UNIQUE INDEX `start_UNIQUE` (`start` ASC),
    ADD UNIQUE INDEX `end_UNIQUE` (`end` ASC);

DROP TABLE if exists `minfin`.`uber_vehicle`;
CREATE TABLE `minfin`.`uber_vehicle`
(
    `id`    INT         NOT NULL AUTO_INCREMENT,
    `name`  VARCHAR(45) NULL,
    `plate` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE if exists `minfin`.`uber_filling_card`;
CREATE TABLE `minfin`.`uber_filling_card`
(
    `id`         INT         NOT NULL,
    `station`    VARCHAR(45) NOT NULL,
    `vehicle_id` INT         NULL,
    PRIMARY KEY (`id`, `station`)
);

DROP TABLE if exists `minfin`.`fuel_account_leftover`;
CREATE TABLE `minfin`.`fuel_account_leftover`
(
    `date`    DATETIME    NOT NULL,
    `station` VARCHAR(45) NOT NULL,
    `value`   DOUBLE      NOT NULL,
    PRIMARY KEY (`date`)
);
