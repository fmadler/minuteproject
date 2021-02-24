SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `mp_devops` ;
CREATE SCHEMA IF NOT EXISTS `mp_devops` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `mp_devops` ;

-- -----------------------------------------------------
-- Table `mp_devops`.`MP_I18N_LANGUAGE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mp_devops`.`MP_I18N_LANGUAGE` ;

CREATE  TABLE IF NOT EXISTS `mp_devops`.`MP_I18N_LANGUAGE` (
  `id` INT NOT NULL ,
  `code` VARCHAR(45) NOT NULL ,
  `ORIGINAL_NAME_ID` INT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_MP_I18N_LANGUAGE_MP_I18N_MAP1_idx` (`ORIGINAL_NAME_ID` ASC) ,
  UNIQUE INDEX `code_UNIQUE` (`code` ASC) ,
  CONSTRAINT `fk_MP_I18N_LANGUAGE_MP_I18N_MAP1`
    FOREIGN KEY (`ORIGINAL_NAME_ID` )
    REFERENCES `mp_devops`.`MP_I18N_TRANSLATION_MAP` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mp_devops`.`MP_I18N_TRANSLATION_MAP`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mp_devops`.`MP_I18N_TRANSLATION_MAP` ;

CREATE  TABLE IF NOT EXISTS `mp_devops`.`MP_I18N_TRANSLATION_MAP` (
  `id` INT NOT NULL ,
  `key` VARCHAR(255) NOT NULL ,
  `TRANSLATION` VARCHAR(2000) NOT NULL ,
  `LANGUAGE_ID` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_MP_I18N_MAP_MP_I18N_LANGUAGE_idx` (`LANGUAGE_ID` ASC) ,
  UNIQUE INDEX `key_UNIQUE` (`key` ASC) ,
  CONSTRAINT `fk_MP_I18N_MAP_MP_I18N_LANGUAGE`
    FOREIGN KEY (`LANGUAGE_ID` )
    REFERENCES `mp_devops`.`MP_I18N_LANGUAGE` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mp_devops`.`MP_I18N_IDIOM_X_TRANSLATION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mp_devops`.`MP_I18N_IDIOM_X_TRANSLATION` ;

CREATE  TABLE IF NOT EXISTS `mp_devops`.`MP_I18N_IDIOM_X_TRANSLATION` (
  `MP_I18N_MAP_id` INT NOT NULL ,
  `MP_I18N_LANGUAGE_id` INT NOT NULL ,
  INDEX `fk_MP_I18N_IDIOM_X_TRANSLATION_MP_I18N_MAP1_idx` (`MP_I18N_MAP_id` ASC) ,
  INDEX `fk_MP_I18N_IDIOM_X_TRANSLATION_MP_I18N_LANGUAGE1_idx` (`MP_I18N_LANGUAGE_id` ASC) ,
  PRIMARY KEY (`MP_I18N_MAP_id`, `MP_I18N_LANGUAGE_id`) ,
  CONSTRAINT `fk_MP_I18N_IDIOM_X_TRANSLATION_MP_I18N_MAP1`
    FOREIGN KEY (`MP_I18N_MAP_id` )
    REFERENCES `mp_devops`.`MP_I18N_TRANSLATION_MAP` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_MP_I18N_IDIOM_X_TRANSLATION_MP_I18N_LANGUAGE1`
    FOREIGN KEY (`MP_I18N_LANGUAGE_id` )
    REFERENCES `mp_devops`.`MP_I18N_LANGUAGE` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mp_devops`.`MP_RIGHT_USER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mp_devops`.`MP_RIGHT_USER` ;

CREATE  TABLE IF NOT EXISTS `mp_devops`.`MP_RIGHT_USER` (
  `ID` INT NOT NULL ,
  `IDENTIFIER` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mp_devops`.`MP_RIGHT_GROUP`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mp_devops`.`MP_RIGHT_GROUP` ;

CREATE  TABLE IF NOT EXISTS `mp_devops`.`MP_RIGHT_GROUP` (
  `ID` INT NOT NULL ,
  `NAME` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mp_devops`.`MP_RIGHT_ROLE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mp_devops`.`MP_RIGHT_ROLE` ;

CREATE  TABLE IF NOT EXISTS `mp_devops`.`MP_RIGHT_ROLE` (
  `ID` INT NOT NULL ,
  `NAME` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mp_devops`.`MP_RIGHT_USER_X_GROUP`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mp_devops`.`MP_RIGHT_USER_X_GROUP` ;

CREATE  TABLE IF NOT EXISTS `mp_devops`.`MP_RIGHT_USER_X_GROUP` (
  `MP_RIGHT_USER_ID` INT NOT NULL ,
  `MP_RIGHT_GROUP_ID` INT NOT NULL ,
  INDEX `fk_MP_RIGHT_USER_X_GROUP_MP_RIGHT_USER1_idx` (`MP_RIGHT_USER_ID` ASC) ,
  INDEX `fk_MP_RIGHT_USER_X_GROUP_MP_RIGHT_GROUP1_idx` (`MP_RIGHT_GROUP_ID` ASC) ,
  PRIMARY KEY (`MP_RIGHT_USER_ID`, `MP_RIGHT_GROUP_ID`) ,
  CONSTRAINT `fk_MP_RIGHT_USER_X_GROUP_MP_RIGHT_USER1`
    FOREIGN KEY (`MP_RIGHT_USER_ID` )
    REFERENCES `mp_devops`.`MP_RIGHT_USER` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_MP_RIGHT_USER_X_GROUP_MP_RIGHT_GROUP1`
    FOREIGN KEY (`MP_RIGHT_GROUP_ID` )
    REFERENCES `mp_devops`.`MP_RIGHT_GROUP` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mp_devops`.`MP_RIGHT_GROUP_X_ROLE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mp_devops`.`MP_RIGHT_GROUP_X_ROLE` ;

CREATE  TABLE IF NOT EXISTS `mp_devops`.`MP_RIGHT_GROUP_X_ROLE` (
  `MP_RIGHT_GROUP_ID` INT NOT NULL ,
  `MP_RIGHT_ROLE_ID` INT NOT NULL ,
  INDEX `fk_MP_RIGHT_GROUP_X_ROLE_MP_RIGHT_GROUP1_idx` (`MP_RIGHT_GROUP_ID` ASC) ,
  INDEX `fk_MP_RIGHT_GROUP_X_ROLE_MP_RIGHT_ROLE1_idx` (`MP_RIGHT_ROLE_ID` ASC) ,
  PRIMARY KEY (`MP_RIGHT_GROUP_ID`, `MP_RIGHT_ROLE_ID`) ,
  CONSTRAINT `fk_MP_RIGHT_GROUP_X_ROLE_MP_RIGHT_GROUP1`
    FOREIGN KEY (`MP_RIGHT_GROUP_ID` )
    REFERENCES `mp_devops`.`MP_RIGHT_GROUP` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_MP_RIGHT_GROUP_X_ROLE_MP_RIGHT_ROLE1`
    FOREIGN KEY (`MP_RIGHT_ROLE_ID` )
    REFERENCES `mp_devops`.`MP_RIGHT_ROLE` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `mp_devops` ;

-- -----------------------------------------------------
-- Placeholder table for view `mp_devops`.`MP_I18N_TRANSLATION_VIEW`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mp_devops`.`MP_I18N_TRANSLATION_VIEW` (`ID` INT, `KEY` INT, `TRANSLATION` INT, `CODE` INT);

-- -----------------------------------------------------
-- View `mp_devops`.`MP_I18N_TRANSLATION_VIEW`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `mp_devops`.`MP_I18N_TRANSLATION_VIEW` ;
DROP TABLE IF EXISTS `mp_devops`.`MP_I18N_TRANSLATION_VIEW`;
USE `mp_devops`;
CREATE  OR REPLACE VIEW `MP_I18N_TRANSLATION_VIEW` AS
select m.ID, m.KEY, m.TRANSLATION, l.CODE 

from MP_I18N_TRANSLATION_MAP m,
MP_I18N_LANGUAGE l
where  m.LANGUAGE_ID = l.ID
;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
