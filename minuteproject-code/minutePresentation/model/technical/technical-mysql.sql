SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `mp_technical` ;
CREATE SCHEMA IF NOT EXISTS `mp_technical` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `mp_technical` ;

-- -----------------------------------------------------
-- Table `mp_technical`.`TB_B`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mp_technical`.`TB_B` ;

CREATE  TABLE IF NOT EXISTS `mp_technical`.`TB_B` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mp_technical`.`TB_A`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mp_technical`.`TB_A` ;

CREATE  TABLE IF NOT EXISTS `mp_technical`.`TB_A` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `TB_B_ID` INT NOT NULL ,
  `TB_B_ID1` INT NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_TB_A_TB_B_idx` (`TB_B_ID` ASC) ,
  INDEX `fk_TB_A_TB_B1_idx` (`TB_B_ID1` ASC) ,
  CONSTRAINT `fk_TB_A_TB_B`
    FOREIGN KEY (`TB_B_ID` )
    REFERENCES `mp_technical`.`TB_B` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TB_A_TB_B1`
    FOREIGN KEY (`TB_B_ID1` )
    REFERENCES `mp_technical`.`TB_B` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mp_technical`.`TB_C`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mp_technical`.`TB_C` ;

CREATE  TABLE IF NOT EXISTS `mp_technical`.`TB_C` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mp_technical`.`TB_D`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mp_technical`.`TB_D` ;

CREATE  TABLE IF NOT EXISTS `mp_technical`.`TB_D` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mp_technical`.`TB_C_X_D`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mp_technical`.`TB_C_X_D` ;

CREATE  TABLE IF NOT EXISTS `mp_technical`.`TB_C_X_D` (
  `TB_C_ID` INT NOT NULL AUTO_INCREMENT ,
  `TB_D_ID` INT NOT NULL ,
  INDEX `fk_TB_C_X_D_TB_C1_idx` (`TB_C_ID` ASC) ,
  INDEX `fk_TB_C_X_D_TB_D1_idx` (`TB_D_ID` ASC) ,
  PRIMARY KEY (`TB_C_ID`, `TB_D_ID`) ,
  CONSTRAINT `fk_TB_C_X_D_TB_C1`
    FOREIGN KEY (`TB_C_ID` )
    REFERENCES `mp_technical`.`TB_C` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TB_C_X_D_TB_D1`
    FOREIGN KEY (`TB_D_ID` )
    REFERENCES `mp_technical`.`TB_D` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mp_technical`.`TB_C_X_D2`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mp_technical`.`TB_C_X_D2` ;

CREATE  TABLE IF NOT EXISTS `mp_technical`.`TB_C_X_D2` (
  `TB_C_ID` INT NOT NULL AUTO_INCREMENT ,
  `TB_D_ID` INT NOT NULL ,
  INDEX `fk_TB_C_X_D_TB_C1_idx` (`TB_C_ID` ASC) ,
  INDEX `fk_TB_C_X_D_TB_D1_idx` (`TB_D_ID` ASC) ,
  PRIMARY KEY (`TB_C_ID`, `TB_D_ID`) ,
  CONSTRAINT `fk_TB_C_X_D_TB_C10`
    FOREIGN KEY (`TB_C_ID` )
    REFERENCES `mp_technical`.`TB_C` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TB_C_X_D_TB_D10`
    FOREIGN KEY (`TB_D_ID` )
    REFERENCES `mp_technical`.`TB_D` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mp_technical`.`TB_ALL_TYPES`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mp_technical`.`TB_ALL_TYPES` ;

CREATE  TABLE IF NOT EXISTS `mp_technical`.`TB_ALL_TYPES` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `VARCHAR` VARCHAR(45) NULL ,
  `DECIMAL` DECIMAL(10,2) NULL ,
  `DATE_TIME` DATETIME NULL ,
  `BLOB_FIELD` BLOB NULL ,
  `BIGINT_FIELD` BIGINT NULL ,
  `BIT_FIELD` BIT NULL )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mp_technical`.`TB_E1`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mp_technical`.`TB_E1` ;

CREATE  TABLE IF NOT EXISTS `mp_technical`.`TB_E1` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `TB_E_ID` INT NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_TB_E_TB_E1_idx` (`TB_E_ID` ASC) ,
  CONSTRAINT `fk_TB_E_TB_E1`
    FOREIGN KEY (`TB_E_ID` )
    REFERENCES `mp_technical`.`TB_E1` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mp_technical`.`TB_E2`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mp_technical`.`TB_E2` ;

CREATE  TABLE IF NOT EXISTS `mp_technical`.`TB_E2` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `TB_E_ID` INT NOT NULL ,
  `TB_E2_ID` INT NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_TB_E_TB_E1_idx` (`TB_E_ID` ASC) ,
  INDEX `fk_TB_E2_TB_E21_idx` (`TB_E2_ID` ASC) ,
  CONSTRAINT `fk_TB_E_TB_E10`
    FOREIGN KEY (`TB_E_ID` )
    REFERENCES `mp_technical`.`TB_E2` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TB_E2_TB_E21`
    FOREIGN KEY (`TB_E2_ID` )
    REFERENCES `mp_technical`.`TB_E2` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mp_technical`.`TB_E3`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mp_technical`.`TB_E3` ;

CREATE  TABLE IF NOT EXISTS `mp_technical`.`TB_E3` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `TB_E_ID` INT NOT NULL ,
  `TB_E2_ID` INT NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_TB_E_TB_E1_idx` (`TB_E_ID` ASC) ,
  INDEX `fk_TB_E2_TB_E21_idx` (`TB_E2_ID` ASC) ,
  CONSTRAINT `fk_TB_E_TB_E100`
    FOREIGN KEY (`TB_E_ID` )
    REFERENCES `mp_technical`.`TB_E3` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TB_E2_TB_E210`
    FOREIGN KEY (`TB_E2_ID` )
    REFERENCES `mp_technical`.`TB_E3` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mp_technical`.`TB_E4`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mp_technical`.`TB_E4` ;

CREATE  TABLE IF NOT EXISTS `mp_technical`.`TB_E4` (
  `TB_E3_ID` INT NOT NULL ,
  `TB_E3_ID1` INT NOT NULL ,
  INDEX `fk_TB_E4_TB_E31_idx` (`TB_E3_ID` ASC) ,
  INDEX `fk_TB_E4_TB_E32_idx` (`TB_E3_ID1` ASC) ,
  CONSTRAINT `fk_TB_E4_TB_E31`
    FOREIGN KEY (`TB_E3_ID` )
    REFERENCES `mp_technical`.`TB_E3` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TB_E4_TB_E32`
    FOREIGN KEY (`TB_E3_ID1` )
    REFERENCES `mp_technical`.`TB_E3` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mp_technical`.`TB_E5`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mp_technical`.`TB_E5` ;

CREATE  TABLE IF NOT EXISTS `mp_technical`.`TB_E5` (
  `TB_E3_ID` INT NOT NULL ,
  `TB_E3_ID1` INT NOT NULL ,
  INDEX `fk_TB_E5_TB_E31_idx` (`TB_E3_ID` ASC) ,
  INDEX `fk_TB_E5_TB_E32_idx` (`TB_E3_ID1` ASC) ,
  CONSTRAINT `fk_TB_E5_TB_E31`
    FOREIGN KEY (`TB_E3_ID` )
    REFERENCES `mp_technical`.`TB_E3` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TB_E5_TB_E32`
    FOREIGN KEY (`TB_E3_ID1` )
    REFERENCES `mp_technical`.`TB_E3` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mp_technical`.`keys`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mp_technical`.`keys` ;

CREATE  TABLE IF NOT EXISTS `mp_technical`.`keys` (
  `idkeys` INT NOT NULL AUTO_INCREMENT ,
  `TEST` VARCHAR(45) NULL ,
  PRIMARY KEY (`idkeys`) )
ENGINE = InnoDB;

USE `mp_technical` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
