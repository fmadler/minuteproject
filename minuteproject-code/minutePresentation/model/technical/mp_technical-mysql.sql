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
  `ID` INT NOT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mp_technical`.`TB_A`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mp_technical`.`TB_A` ;

CREATE  TABLE IF NOT EXISTS `mp_technical`.`TB_A` (
  `ID` INT NOT NULL ,
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
  `ID` INT NOT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mp_technical`.`TB_D`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mp_technical`.`TB_D` ;

CREATE  TABLE IF NOT EXISTS `mp_technical`.`TB_D` (
  `ID` INT NOT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mp_technical`.`TB_C_X_D`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mp_technical`.`TB_C_X_D` ;

CREATE  TABLE IF NOT EXISTS `mp_technical`.`TB_C_X_D` (
  `TB_C_ID` INT NOT NULL ,
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
  `TB_C_ID` INT NOT NULL ,
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

USE `mp_technical` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
