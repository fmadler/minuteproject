-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema functional
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `functional` ;

-- -----------------------------------------------------
-- Schema functional
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `functional` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `functional` ;

-- -----------------------------------------------------
-- Table `functional`.`COUNTRY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `functional`.`COUNTRY` ;

CREATE TABLE IF NOT EXISTS `functional`.`COUNTRY` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(45) NULL,
  `CAPITAL` INT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_COUNTRY_CITY1_idx` (`CAPITAL` ASC),
  CONSTRAINT `fk_COUNTRY_CITY1`
    FOREIGN KEY (`CAPITAL`)
    REFERENCES `functional`.`CITY` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `functional`.`CITY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `functional`.`CITY` ;

CREATE TABLE IF NOT EXISTS `functional`.`CITY` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(45) NULL,
  `COUNTRY_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_CITY_COUNTRY1_idx` (`COUNTRY_ID` ASC),
  CONSTRAINT `fk_CITY_COUNTRY1`
    FOREIGN KEY (`COUNTRY_ID`)
    REFERENCES `functional`.`COUNTRY` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `functional`.`ADDRESS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `functional`.`ADDRESS` ;

CREATE TABLE IF NOT EXISTS `functional`.`ADDRESS` (
  `ADDRESS_ID` INT NOT NULL AUTO_INCREMENT,
  `STREET1` VARCHAR(45) NULL,
  `NUMBER` INT NULL,
  `CITY_ID` INT NOT NULL,
  PRIMARY KEY (`ADDRESS_ID`),
  INDEX `fk_ADDRESS_CITY1_idx` (`CITY_ID` ASC),
  CONSTRAINT `fk_ADDRESS_CITY1`
    FOREIGN KEY (`CITY_ID`)
    REFERENCES `functional`.`CITY` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `functional`.`TELEPHONE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `functional`.`TELEPHONE` ;

CREATE TABLE IF NOT EXISTS `functional`.`TELEPHONE` (
  `idTELEPHONE` INT NOT NULL,
  `NUMBER` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idTELEPHONE`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `functional`.`B_CLIENT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `functional`.`B_CLIENT` ;

CREATE TABLE IF NOT EXISTS `functional`.`B_CLIENT` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(45) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL,
  `AGE` INT NOT NULL,
  `ADDRESS_ADDRESS_ID` INT NOT NULL,
  `TITLE` VARCHAR(45) NOT NULL,
  `TELEPHONE_idTELEPHONE` INT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_B_CLIENT_ADDRESS1_idx` (`ADDRESS_ADDRESS_ID` ASC),
  INDEX `fk_B_CLIENT_TELEPHONE1_idx` (`TELEPHONE_idTELEPHONE` ASC),
  CONSTRAINT `fk_B_CLIENT_ADDRESS1`
    FOREIGN KEY (`ADDRESS_ADDRESS_ID`)
    REFERENCES `functional`.`ADDRESS` (`ADDRESS_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_B_CLIENT_TELEPHONE1`
    FOREIGN KEY (`TELEPHONE_idTELEPHONE`)
    REFERENCES `functional`.`TELEPHONE` (`idTELEPHONE`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `functional`.`COMPANY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `functional`.`COMPANY` ;

CREATE TABLE IF NOT EXISTS `functional`.`COMPANY` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `functional`.`DEPARTMENT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `functional`.`DEPARTMENT` ;

CREATE TABLE IF NOT EXISTS `functional`.`DEPARTMENT` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `PARENT_DEPARTMENT_ID` INT NULL,
  `NAME` VARCHAR(45) NULL,
  `COMPANY_ID` INT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_DEPARTMENT_DEPARTMENT_idx` (`PARENT_DEPARTMENT_ID` ASC),
  INDEX `fk_DEPARTMENT_COMPANY1_idx` (`COMPANY_ID` ASC),
  CONSTRAINT `fk_DEPARTMENT_DEPARTMENT`
    FOREIGN KEY (`PARENT_DEPARTMENT_ID`)
    REFERENCES `functional`.`DEPARTMENT` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DEPARTMENT_COMPANY1`
    FOREIGN KEY (`COMPANY_ID`)
    REFERENCES `functional`.`COMPANY` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `functional`.`USER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `functional`.`USER` ;

CREATE TABLE IF NOT EXISTS `functional`.`USER` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(45) NULL,
  `EMAIL` VARCHAR(45) NULL,
  `DEPARTMENT_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_USER_DEPARTMENT1_idx` (`DEPARTMENT_ID` ASC),
  CONSTRAINT `fk_USER_DEPARTMENT1`
    FOREIGN KEY (`DEPARTMENT_ID`)
    REFERENCES `functional`.`DEPARTMENT` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `functional`.`T_GROUP`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `functional`.`T_GROUP` ;

CREATE TABLE IF NOT EXISTS `functional`.`T_GROUP` (
  `idGROUP` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idGROUP`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `functional`.`ROLE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `functional`.`ROLE` ;

CREATE TABLE IF NOT EXISTS `functional`.`ROLE` (
  `idROLE` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idROLE`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `functional`.`USER_X_GROUP`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `functional`.`USER_X_GROUP` ;

CREATE TABLE IF NOT EXISTS `functional`.`USER_X_GROUP` (
  `USER_ID` INT NOT NULL,
  `GROUP_idGROUP` INT NOT NULL,
  INDEX `fk_USER_X_ROLE_USER1_idx` (`USER_ID` ASC),
  INDEX `fk_USER_X_ROLE_GROUP1_idx` (`GROUP_idGROUP` ASC),
  PRIMARY KEY (`USER_ID`, `GROUP_idGROUP`),
  CONSTRAINT `fk_USER_X_ROLE_USER1`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `functional`.`USER` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USER_X_ROLE_GROUP1`
    FOREIGN KEY (`GROUP_idGROUP`)
    REFERENCES `functional`.`T_GROUP` (`idGROUP`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `functional`.`GROUP_X_ROLE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `functional`.`GROUP_X_ROLE` ;

CREATE TABLE IF NOT EXISTS `functional`.`GROUP_X_ROLE` (
  `ROLE_idROLE` INT NOT NULL,
  `GROUP_idGROUP` INT NOT NULL,
  INDEX `fk_GROUP_X_ROLE_ROLE1_idx` (`ROLE_idROLE` ASC),
  INDEX `fk_GROUP_X_ROLE_GROUP1_idx` (`GROUP_idGROUP` ASC),
  PRIMARY KEY (`ROLE_idROLE`, `GROUP_idGROUP`),
  CONSTRAINT `fk_GROUP_X_ROLE_ROLE1`
    FOREIGN KEY (`ROLE_idROLE`)
    REFERENCES `functional`.`ROLE` (`idROLE`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_GROUP_X_ROLE_GROUP1`
    FOREIGN KEY (`GROUP_idGROUP`)
    REFERENCES `functional`.`T_GROUP` (`idGROUP`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `functional`.`keys`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `functional`.`keys` ;

CREATE TABLE IF NOT EXISTS `functional`.`keys` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `TEST` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `functional`.`LANGUAGE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `functional`.`LANGUAGE` ;

CREATE TABLE IF NOT EXISTS `functional`.`LANGUAGE` (
  `idLANGUAGE` INT NOT NULL,
  `CODE` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idLANGUAGE`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `functional`.`COUNTRY_X_LANGUAGE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `functional`.`COUNTRY_X_LANGUAGE` ;

CREATE TABLE IF NOT EXISTS `functional`.`COUNTRY_X_LANGUAGE` (
  `LANGUAGE_idLANGUAGE` INT NOT NULL,
  `COUNTRY_ID` INT NOT NULL,
  `CREATION_DATE` TIME NOT NULL,
  `ORDER` INT NOT NULL,
  INDEX `fk_COUNTRY_X_LANGUAGE_LANGUAGE1_idx` (`LANGUAGE_idLANGUAGE` ASC),
  INDEX `fk_COUNTRY_X_LANGUAGE_COUNTRY1_idx` (`COUNTRY_ID` ASC),
  PRIMARY KEY (`LANGUAGE_idLANGUAGE`, `COUNTRY_ID`),
  CONSTRAINT `fk_COUNTRY_X_LANGUAGE_LANGUAGE1`
    FOREIGN KEY (`LANGUAGE_idLANGUAGE`)
    REFERENCES `functional`.`LANGUAGE` (`idLANGUAGE`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_COUNTRY_X_LANGUAGE_COUNTRY1`
    FOREIGN KEY (`COUNTRY_ID`)
    REFERENCES `functional`.`COUNTRY` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `functional`.`EOTC_SYSTEM_ACTIVITY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `functional`.`EOTC_SYSTEM_ACTIVITY` ;

CREATE TABLE IF NOT EXISTS `functional`.`EOTC_SYSTEM_ACTIVITY` (
  `TORA_SYSTEM_NAME` VARCHAR(64) NOT NULL,
  `YEAR_MONTH` VARCHAR(45) NOT NULL,
  `MINUTES_USED` INT NULL,
  PRIMARY KEY (`TORA_SYSTEM_NAME`, `YEAR_MONTH`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `functional`.`COUNTRY`
-- -----------------------------------------------------
START TRANSACTION;
USE `functional`;
INSERT INTO `functional`.`COUNTRY` (`ID`, `NAME`, `CAPITAL`) VALUES (1, 'France', NULL);
INSERT INTO `functional`.`COUNTRY` (`ID`, `NAME`, `CAPITAL`) VALUES (2, 'England', NULL);
INSERT INTO `functional`.`COUNTRY` (`ID`, `NAME`, `CAPITAL`) VALUES (3, 'Belgium', NULL);
INSERT INTO `functional`.`COUNTRY` (`ID`, `NAME`, `CAPITAL`) VALUES (4, 'Germany', NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `functional`.`CITY`
-- -----------------------------------------------------
START TRANSACTION;
USE `functional`;
INSERT INTO `functional`.`CITY` (`ID`, `NAME`, `COUNTRY_ID`) VALUES (1, 'Paris', 1);
INSERT INTO `functional`.`CITY` (`ID`, `NAME`, `COUNTRY_ID`) VALUES (2, 'London', 2);
INSERT INTO `functional`.`CITY` (`ID`, `NAME`, `COUNTRY_ID`) VALUES (3, 'Brussels', 3);
INSERT INTO `functional`.`CITY` (`ID`, `NAME`, `COUNTRY_ID`) VALUES (4, 'Berlin', 4);

COMMIT;


-- -----------------------------------------------------
-- Data for table `functional`.`ADDRESS`
-- -----------------------------------------------------
START TRANSACTION;
USE `functional`;
INSERT INTO `functional`.`ADDRESS` (`ADDRESS_ID`, `STREET1`, `NUMBER`, `CITY_ID`) VALUES (1, 'street', 3, 1);
INSERT INTO `functional`.`ADDRESS` (`ADDRESS_ID`, `STREET1`, `NUMBER`, `CITY_ID`) VALUES (2, 'street2', 4, 1);
INSERT INTO `functional`.`ADDRESS` (`ADDRESS_ID`, `STREET1`, `NUMBER`, `CITY_ID`) VALUES (3, 'rue', 3, 2);
INSERT INTO `functional`.`ADDRESS` (`ADDRESS_ID`, `STREET1`, `NUMBER`, `CITY_ID`) VALUES (4, 'rue alpha', 45, 2);
INSERT INTO `functional`.`ADDRESS` (`ADDRESS_ID`, `STREET1`, `NUMBER`, `CITY_ID`) VALUES (5, 'betalaan', 56, 3);

COMMIT;


-- -----------------------------------------------------
-- Data for table `functional`.`B_CLIENT`
-- -----------------------------------------------------
START TRANSACTION;
USE `functional`;
INSERT INTO `functional`.`B_CLIENT` (`ID`, `NAME`, `DESCRIPTION`, `AGE`, `ADDRESS_ADDRESS_ID`, `TITLE`, `TELEPHONE_idTELEPHONE`) VALUES (1, 'sean', 'o\'connor', 34, 1, 'MR', NULL);
INSERT INTO `functional`.`B_CLIENT` (`ID`, `NAME`, `DESCRIPTION`, `AGE`, `ADDRESS_ADDRESS_ID`, `TITLE`, `TELEPHONE_idTELEPHONE`) VALUES (2, 'damian', 'christiansen', 33, 2, 'MR', NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `functional`.`COMPANY`
-- -----------------------------------------------------
START TRANSACTION;
USE `functional`;
INSERT INTO `functional`.`COMPANY` (`ID`, `NAME`) VALUES (1, 'Big company');
INSERT INTO `functional`.`COMPANY` (`ID`, `NAME`) VALUES (2, 'Other company');

COMMIT;


-- -----------------------------------------------------
-- Data for table `functional`.`DEPARTMENT`
-- -----------------------------------------------------
START TRANSACTION;
USE `functional`;
INSERT INTO `functional`.`DEPARTMENT` (`ID`, `PARENT_DEPARTMENT_ID`, `NAME`, `COMPANY_ID`) VALUES (1, NULL, 'A', 1);
INSERT INTO `functional`.`DEPARTMENT` (`ID`, `PARENT_DEPARTMENT_ID`, `NAME`, `COMPANY_ID`) VALUES (2, 1, 'A1', NULL);
INSERT INTO `functional`.`DEPARTMENT` (`ID`, `PARENT_DEPARTMENT_ID`, `NAME`, `COMPANY_ID`) VALUES (3, 1, 'A2', NULL);
INSERT INTO `functional`.`DEPARTMENT` (`ID`, `PARENT_DEPARTMENT_ID`, `NAME`, `COMPANY_ID`) VALUES (4, 2, 'A11', NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `functional`.`USER`
-- -----------------------------------------------------
START TRANSACTION;
USE `functional`;
INSERT INTO `functional`.`USER` (`ID`, `NAME`, `EMAIL`, `DEPARTMENT_ID`) VALUES (1, 'test', 'test@test.com', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `functional`.`T_GROUP`
-- -----------------------------------------------------
START TRANSACTION;
USE `functional`;
INSERT INTO `functional`.`T_GROUP` (`idGROUP`, `NAME`) VALUES (1, 'ADMINISTRATOR');

COMMIT;


-- -----------------------------------------------------
-- Data for table `functional`.`ROLE`
-- -----------------------------------------------------
START TRANSACTION;
USE `functional`;
INSERT INTO `functional`.`ROLE` (`idROLE`, `NAME`) VALUES (1, 'MANAGE_ENTITY');
INSERT INTO `functional`.`ROLE` (`idROLE`, `NAME`) VALUES (2, 'REPORT');

COMMIT;


-- -----------------------------------------------------
-- Data for table `functional`.`USER_X_GROUP`
-- -----------------------------------------------------
START TRANSACTION;
USE `functional`;
INSERT INTO `functional`.`USER_X_GROUP` (`USER_ID`, `GROUP_idGROUP`) VALUES (1, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `functional`.`GROUP_X_ROLE`
-- -----------------------------------------------------
START TRANSACTION;
USE `functional`;
INSERT INTO `functional`.`GROUP_X_ROLE` (`ROLE_idROLE`, `GROUP_idGROUP`) VALUES (1, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `functional`.`LANGUAGE`
-- -----------------------------------------------------
START TRANSACTION;
USE `functional`;
INSERT INTO `functional`.`LANGUAGE` (`idLANGUAGE`, `CODE`) VALUES (-1, 'FR');
INSERT INTO `functional`.`LANGUAGE` (`idLANGUAGE`, `CODE`) VALUES (-2, 'DE');
INSERT INTO `functional`.`LANGUAGE` (`idLANGUAGE`, `CODE`) VALUES (-3, 'EN');
INSERT INTO `functional`.`LANGUAGE` (`idLANGUAGE`, `CODE`) VALUES (-4, 'NL');

COMMIT;

