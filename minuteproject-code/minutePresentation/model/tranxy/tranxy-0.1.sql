SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `tranxy` ;
CREATE SCHEMA IF NOT EXISTS `tranxy` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `tranxy` ;

-- -----------------------------------------------------
-- Table `tranxy`.`translation_key`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tranxy`.`translation_key` ;

CREATE TABLE IF NOT EXISTS `tranxy`.`translation_key` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `key_name` VARCHAR(200) NOT NULL,
  `description` VARCHAR(400) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tranxy`.`language`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tranxy`.`language` ;

CREATE TABLE IF NOT EXISTS `tranxy`.`language` (
  `idlanguage` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(45) NOT NULL,
  `locale` VARCHAR(10) NOT NULL,
  `description` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idlanguage`),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tranxy`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tranxy`.`user` ;

CREATE TABLE IF NOT EXISTS `tranxy`.`user` (
  `iduser` BIGINT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(200) NULL,
  PRIMARY KEY (`iduser`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tranxy`.`translation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tranxy`.`translation` ;

CREATE TABLE IF NOT EXISTS `tranxy`.`translation` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `translation` VARCHAR(800) NULL,
  `language_id` INT NOT NULL,
  `Key_id` BIGINT NOT NULL,
  `is_final` TINYINT NOT NULL,
  `date_finalization` DATE NULL,
  `translator_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_traduction_language` (`language_id` ASC),
  INDEX `fk_traduction_Key1` (`Key_id` ASC),
  INDEX `fk_traduction_user1` (`translator_id` ASC),
  CONSTRAINT `fk_traduction_language`
    FOREIGN KEY (`language_id`)
    REFERENCES `tranxy`.`language` (`idlanguage`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_traduction_Key1`
    FOREIGN KEY (`Key_id`)
    REFERENCES `tranxy`.`translation_key` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_traduction_user1`
    FOREIGN KEY (`translator_id`)
    REFERENCES `tranxy`.`user` (`iduser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tranxy`.`application`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tranxy`.`application` ;

CREATE TABLE IF NOT EXISTS `tranxy`.`application` (
  `idapplication` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL,
  `description` VARCHAR(200) NULL,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idapplication`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tranxy`.`application_x_key`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tranxy`.`application_x_key` ;

CREATE TABLE IF NOT EXISTS `tranxy`.`application_x_key` (
  `Key_id` BIGINT NOT NULL,
  `application_id` BIGINT NOT NULL,
  INDEX `fk_application_x_key_Key1` (`Key_id` ASC),
  INDEX `fk_application_x_key_application1` (`application_id` ASC),
  PRIMARY KEY (`Key_id`, `application_id`),
  CONSTRAINT `fk_application_x_key_Key1`
    FOREIGN KEY (`Key_id`)
    REFERENCES `tranxy`.`translation_key` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_application_x_key_application1`
    FOREIGN KEY (`application_id`)
    REFERENCES `tranxy`.`application` (`idapplication`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tranxy`.`translation_request`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tranxy`.`translation_request` ;

CREATE TABLE IF NOT EXISTS `tranxy`.`translation_request` (
  `idtranslation_request` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `request_date` DATE NOT NULL,
  `user_id` BIGINT NOT NULL,
  `Key_id` BIGINT NOT NULL,
  PRIMARY KEY (`idtranslation_request`),
  INDEX `fk_translation_request_user1` (`user_id` ASC),
  INDEX `fk_translation_request_Key1` (`Key_id` ASC),
  CONSTRAINT `fk_translation_request_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `tranxy`.`user` (`iduser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_translation_request_Key1`
    FOREIGN KEY (`Key_id`)
    REFERENCES `tranxy`.`translation_key` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tranxy`.`request_key`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tranxy`.`request_key` ;

CREATE TABLE IF NOT EXISTS `tranxy`.`request_key` (
  `translation_request_id` BIGINT NOT NULL,
  `language_id` INT NOT NULL,
  INDEX `fk_request_key_translation_request1` (`translation_request_id` ASC),
  PRIMARY KEY (`translation_request_id`, `language_id`),
  INDEX `fk_request_key_language1` (`language_id` ASC),
  CONSTRAINT `fk_request_key_translation_request1`
    FOREIGN KEY (`translation_request_id`)
    REFERENCES `tranxy`.`translation_request` (`idtranslation_request`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_request_key_language1`
    FOREIGN KEY (`language_id`)
    REFERENCES `tranxy`.`language` (`idlanguage`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tranxy`.`language_x_translator`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tranxy`.`language_x_translator` ;

CREATE TABLE IF NOT EXISTS `tranxy`.`language_x_translator` (
  `language_id` INT NOT NULL,
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`language_id`, `user_id`),
  INDEX `fk_request_key_language1` (`language_id` ASC),
  INDEX `fk_language_x_translator_user1` (`user_id` ASC),
  CONSTRAINT `fk_request_key_language10`
    FOREIGN KEY (`language_id`)
    REFERENCES `tranxy`.`language` (`idlanguage`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_language_x_translator_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `tranxy`.`user` (`iduser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tranxy`.`language_x_speaker`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tranxy`.`language_x_speaker` ;

CREATE TABLE IF NOT EXISTS `tranxy`.`language_x_speaker` (
  `language_id` INT NOT NULL,
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`language_id`, `user_id`),
  INDEX `fk_request_key_language1` (`language_id` ASC),
  INDEX `fk_language_x_translator_user1` (`user_id` ASC),
  CONSTRAINT `fk_request_key_language100`
    FOREIGN KEY (`language_id`)
    REFERENCES `tranxy`.`language` (`idlanguage`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_language_x_translator_user10`
    FOREIGN KEY (`user_id`)
    REFERENCES `tranxy`.`user` (`iduser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
