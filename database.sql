--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `idRole` int(11) NOT NULL COMMENT 'Идентификатор роли пользователя.',
  `login` varchar(45) NOT NULL COMMENT 'Логин пользователя системы.',
  `password` varchar(45) NOT NULL COMMENT 'Пароль пользователя системы.',
  `name` varchar(45) NOT NULL COMMENT 'Имя пользователя системы.',
  `surname` varchar(45) NOT NULL COMMENT 'Фамилия пользователя системы.',
  `address` varchar(200) DEFAULT 'null' COMMENT 'Адрес клиента. Для фармацевта и врача значение по умолчанию(null).',
  `email` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`idUser`,`idRole`),
  UNIQUE KEY `login_UNIQUE` (`login`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_account_Role1_idx` (`idRole`),
  CONSTRAINT `fk_account_Role1` FOREIGN KEY (`idRole`) REFERENCES `role` (`idRole`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='Информация о всех пользователях системы: клиентах, фармацевтах, врачах.';


--
-- Table structure for table `dosage`
--

DROP TABLE IF EXISTS `dosage`;

CREATE TABLE `dosage` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idMedicament` int(11) NOT NULL,
  `dosage` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_dosage_medicament1_idx` (`idMedicament`),
  CONSTRAINT `fk_dosage_medicament1` FOREIGN KEY (`idMedicament`) REFERENCES `medicament` (`idMedicament`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;


--
-- Table structure for table `medicament`
--

DROP TABLE IF EXISTS `medicament`;

CREATE TABLE `medicament` (
  `idMedicament` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор препарата.',
  `name` varchar(45) NOT NULL COMMENT 'Название препарата.',
  `producer` varchar(45) NOT NULL COMMENT 'Страна-производитель препарата.',
  `price` decimal(10,2) NOT NULL COMMENT 'Цена лекарственного препарата.',
  `prescription` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'Необходимость наличия электронного рецепта для покупки препарата. 0 - рецепт не нужен, 1 - нужен.',
  `image` varchar(64) DEFAULT NULL,
  `availability` tinyint(4) DEFAULT '1' COMMENT '0 - нет в наличии\n1 - есть в наличии',
  `modeOfApplication` varchar(300) DEFAULT NULL,
  `contraindications` varchar(300) DEFAULT NULL,
  `sideEffects` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`idMedicament`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='Информация о лекарственном препарате.';

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `idOrder` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный номер заказа.',
  `idUser` int(11) NOT NULL,
  `idMedicament` int(11) NOT NULL COMMENT 'Идентификатор заказываемого препарата.',
  `idOrderStatus` int(11) NOT NULL,
  `number` int(11) NOT NULL COMMENT 'Количество упаковок заказываемого препарата.',
  `idDosage` int(10) unsigned NOT NULL,
  `idPrescription` int(11) DEFAULT NULL,
  PRIMARY KEY (`idOrder`,`idUser`,`idMedicament`,`idOrderStatus`),
  KEY `fk_order_medicament1_idx` (`idMedicament`),
  KEY `fk_order_account1_idx` (`idUser`),
  KEY `fk_order_orderStatus1_idx` (`idOrderStatus`),
  KEY `fk_order_dosage1_idx` (`idDosage`),
  KEY `fk_order_prescription1_idx` (`idPrescription`),
  CONSTRAINT `fk_order_account1` FOREIGN KEY (`idUser`) REFERENCES `account` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_dosage1` FOREIGN KEY (`idDosage`) REFERENCES `dosage` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_medicament1` FOREIGN KEY (`idMedicament`) REFERENCES `medicament` (`idMedicament`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_orderStatus1` FOREIGN KEY (`idOrderStatus`) REFERENCES `orderstatus` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_prescription1` FOREIGN KEY (`idPrescription`) REFERENCES `prescription` (`idPrescription`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8 COMMENT='Заказ на покупку лекарственного препарата, формируемый клиентом.';


--
-- Table structure for table `orderstatus`
--

DROP TABLE IF EXISTS `orderstatus`;

CREATE TABLE `orderstatus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


--
-- Table structure for table `prescription`
--

DROP TABLE IF EXISTS `prescription`;

CREATE TABLE `prescription` (
  `idPrescription` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор электронного рецепта.',
  `idDoctor` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `idMedicament` int(11) NOT NULL,
  `dateOfIssue` datetime NOT NULL COMMENT 'Дата выдачи рецепта.',
  `dateOfCompletion` datetime NOT NULL COMMENT 'Дата завершения действия рецепта.',
  `number` int(11) NOT NULL,
  `idDosage` int(10) unsigned NOT NULL,
  `valid` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`idPrescription`,`idDoctor`,`idUser`,`idMedicament`),
  KEY `fk_prescription_account1_idx` (`idDoctor`),
  KEY `fk_prescription_account2_idx` (`idUser`),
  KEY `fk_prescription_medicament1_idx` (`idMedicament`),
  KEY `fk_prescription_dosage1_idx` (`idDosage`),
  CONSTRAINT `fk_prescription_account1` FOREIGN KEY (`idDoctor`) REFERENCES `account` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_prescription_account2` FOREIGN KEY (`idUser`) REFERENCES `account` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_prescription_dosage1` FOREIGN KEY (`idDosage`) REFERENCES `dosage` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_prescription_medicament1` FOREIGN KEY (`idMedicament`) REFERENCES `medicament` (`idMedicament`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='Данные об электронном рецепте, который выдает врач определенному клиенту на покупку препарата.';


--
-- Table structure for table `request_for_renewal`
--

DROP TABLE IF EXISTS `request_for_renewal`;

CREATE TABLE `request_for_renewal` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idPrescription` int(11) NOT NULL,
  `idRequestStatus` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`,`idPrescription`,`idRequestStatus`),
  KEY `fk_request_for_renewal_prescription1_idx` (`idPrescription`),
  KEY `fk_request_for_renewal_requestStatus1_idx` (`idRequestStatus`),
  CONSTRAINT `fk_request_for_renewal_prescription1` FOREIGN KEY (`idPrescription`) REFERENCES `prescription` (`idPrescription`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_request_for_renewal_requestStatus1` FOREIGN KEY (`idRequestStatus`) REFERENCES `requeststatus` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;


--
-- Table structure for table `requeststatus`
--

DROP TABLE IF EXISTS `requeststatus`;

CREATE TABLE `requeststatus` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Status` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `idRole` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор роли.',
  `role` varchar(45) NOT NULL COMMENT 'Наименование роли(клиент, фармацевт или врач).',
  PRIMARY KEY (`idRole`),
  UNIQUE KEY `role_UNIQUE` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='Вспомогательная таблица. Хранит информацию о ролях, существующих в системе.';