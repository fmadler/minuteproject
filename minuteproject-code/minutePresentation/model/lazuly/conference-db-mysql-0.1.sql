SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `conference` ;
CREATE SCHEMA IF NOT EXISTS `conference` DEFAULT CHARACTER SET latin1 ;
USE `conference` ;

-- -----------------------------------------------------
-- Table `conference`.`country`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `conference`.`country` ;

CREATE  TABLE IF NOT EXISTS `conference`.`country` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `iso_name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `conference`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `conference`.`address` ;

CREATE  TABLE IF NOT EXISTS `conference`.`address` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `street1` VARCHAR(255) NULL ,
  `street2` VARCHAR(255) NULL ,
  `country_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_address_country1` (`country_id` ASC) ,
  CONSTRAINT `fk_address_country1`
    FOREIGN KEY (`country_id` )
    REFERENCES `conference`.`country` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `conference`.`conference`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `conference`.`conference` ;

CREATE  TABLE IF NOT EXISTS `conference`.`conference` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL ,
  `start_date` DATE NULL DEFAULT NULL ,
  `end_date` DATE NULL DEFAULT NULL ,
  `address_id` BIGINT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_conference_address1` (`address_id` ASC) ,
  CONSTRAINT `fk_conference_address1`
    FOREIGN KEY (`address_id` )
    REFERENCES `conference`.`address` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `conference`.`conference_member`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `conference`.`conference_member` ;

CREATE  TABLE IF NOT EXISTS `conference`.`conference_member` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `conference_id` BIGINT(20) NOT NULL ,
  `first_name` VARCHAR(255) NOT NULL ,
  `last_name` VARCHAR(255) NOT NULL ,
  `email` VARCHAR(255) NOT NULL ,
  `address_id` BIGINT NOT NULL ,
  `status` VARCHAR(45) NOT NULL DEFAULT 'PENDING' ,
  PRIMARY KEY (`id`) ,
  INDEX `FK0005` (`conference_id` ASC) ,
  INDEX `fk_conference_member_address1` (`address_id` ASC) ,
  CONSTRAINT `FK0005`
    FOREIGN KEY (`conference_id` )
    REFERENCES `conference`.`conference` (`id` ),
  CONSTRAINT `fk_conference_member_address1`
    FOREIGN KEY (`address_id` )
    REFERENCES `conference`.`address` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `conference`.`presentation_place`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `conference`.`presentation_place` ;

CREATE  TABLE IF NOT EXISTS `conference`.`presentation_place` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `location` VARCHAR(45) NULL ,
  `number_of_seats` INT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `conference`.`presentation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `conference`.`presentation` ;

CREATE  TABLE IF NOT EXISTS `conference`.`presentation` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `start_time` DATETIME NOT NULL ,
  `end_time` DATETIME NOT NULL ,
  `abstract` VARCHAR(500) NOT NULL ,
  `title` VARCHAR(255) NOT NULL ,
  `status` VARCHAR(15) NOT NULL DEFAULT 'PROPOSAL' ,
  `presentation_place_id` BIGINT(20) NULL ,
  `proposal_time` TIMESTAMP NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_presentation_presentation_place1` (`presentation_place_id` ASC) ,
  CONSTRAINT `fk_presentation_presentation_place1`
    FOREIGN KEY (`presentation_place_id` )
    REFERENCES `conference`.`presentation_place` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `conference`.`evaluation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `conference`.`evaluation` ;

CREATE  TABLE IF NOT EXISTS `conference`.`evaluation` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `conference_member_id` BIGINT(20) NOT NULL ,
  `presentation_id` BIGINT(20) NOT NULL ,
  `star` INT(11) NOT NULL ,
  `comment` VARCHAR(500) NULL DEFAULT NULL ,
  `evaluation_date` DATETIME NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `FK0006` (`conference_member_id` ASC) ,
  INDEX `FK0007` (`presentation_id` ASC) ,
  CONSTRAINT `FK0006`
    FOREIGN KEY (`conference_member_id` )
    REFERENCES `conference`.`conference_member` (`id` ),
  CONSTRAINT `FK0007`
    FOREIGN KEY (`presentation_id` )
    REFERENCES `conference`.`presentation` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `conference`.`sponsor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `conference`.`sponsor` ;

CREATE  TABLE IF NOT EXISTS `conference`.`sponsor` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `privilege_type` VARCHAR(45) NOT NULL ,
  `status` VARCHAR(45) NOT NULL DEFAULT 'PENDING' COMMENT 'test, it' ,
  `conference_id` BIGINT(20) NOT NULL ,
  `address_id` BIGINT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_sponsor_conference1` (`conference_id` ASC) ,
  INDEX `fk_sponsor_address1` (`address_id` ASC) ,
  CONSTRAINT `fk_sponsor_conference1`
    FOREIGN KEY (`conference_id` )
    REFERENCES `conference`.`conference` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sponsor_address1`
    FOREIGN KEY (`address_id` )
    REFERENCES `conference`.`address` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `conference`.`speaker`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `conference`.`speaker` ;

CREATE  TABLE IF NOT EXISTS `conference`.`speaker` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `conference_member_id` BIGINT(20) NOT NULL ,
  `bio` TEXT NOT NULL ,
  `photo` BLOB NULL ,
  `web_site_url` VARCHAR(255) NULL ,
  `sponsor_id` BIGINT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `FK0008` (`conference_member_id` ASC) ,
  INDEX `fk_speaker_sponsor1` (`sponsor_id` ASC) ,
  CONSTRAINT `FK0008`
    FOREIGN KEY (`conference_member_id` )
    REFERENCES `conference`.`conference_member` (`id` ),
  CONSTRAINT `fk_speaker_sponsor1`
    FOREIGN KEY (`sponsor_id` )
    REFERENCES `conference`.`sponsor` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `conference`.`speaker_presentation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `conference`.`speaker_presentation` ;

CREATE  TABLE IF NOT EXISTS `conference`.`speaker_presentation` (
  `speaker_id` BIGINT(20) NOT NULL ,
  `presentation_id` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`speaker_id`, `presentation_id`) ,
  INDEX `FK0004` (`presentation_id` ASC) ,
  CONSTRAINT `FK0003`
    FOREIGN KEY (`speaker_id` )
    REFERENCES `conference`.`speaker` (`id` ),
  CONSTRAINT `FK0004`
    FOREIGN KEY (`presentation_id` )
    REFERENCES `conference`.`presentation` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `conference`.`conference_feedback`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `conference`.`conference_feedback` ;

CREATE  TABLE IF NOT EXISTS `conference`.`conference_feedback` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `comment` TEXT NOT NULL ,
  `feedback_date` DATETIME NOT NULL ,
  `conference_id` BIGINT(20) NOT NULL ,
  `conference_member_id` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_conference_feedback_conference1` (`conference_id` ASC) ,
  INDEX `fk_conference_feedback_conference_member1` (`conference_member_id` ASC) ,
  CONSTRAINT `fk_conference_feedback_conference1`
    FOREIGN KEY (`conference_id` )
    REFERENCES `conference`.`conference` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_conference_feedback_conference_member1`
    FOREIGN KEY (`conference_member_id` )
    REFERENCES `conference`.`conference_member` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `conference`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `conference`.`role` ;

CREATE  TABLE IF NOT EXISTS `conference`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `conference`.`member_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `conference`.`member_role` ;

CREATE  TABLE IF NOT EXISTS `conference`.`member_role` (
  `conference_member_id` BIGINT(20) NOT NULL ,
  `role_id` INT NOT NULL ,
  PRIMARY KEY (`conference_member_id`, `role_id`) ,
  INDEX `fk_member_role_conference_member1` (`conference_member_id` ASC) ,
  INDEX `fk_member_role_role1` (`role_id` ASC) ,
  CONSTRAINT `fk_member_role_conference_member1`
    FOREIGN KEY (`conference_member_id` )
    REFERENCES `conference`.`conference_member` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_member_role_role1`
    FOREIGN KEY (`role_id` )
    REFERENCES `conference`.`role` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Placeholder table for view `conference`.`stat_mb_per_ctry_conf`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `conference`.`stat_mb_per_ctry_conf` (`id` INT, `country` INT, `conference_name` INT, `number` INT);

-- -----------------------------------------------------
-- Placeholder table for view `conference`.`stat_mb_by_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `conference`.`stat_mb_by_role` (`id` INT, `stat_mb_per_ctry_conf_id` INT, `role_name` INT, `number` INT);

-- -----------------------------------------------------
-- View `conference`.`stat_mb_per_ctry_conf`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `conference`.`stat_mb_per_ctry_conf` ;
DROP TABLE IF EXISTS `conference`.`stat_mb_per_ctry_conf`;
USE `conference`;
CREATE  OR REPLACE VIEW `stat_mb_per_ctry_conf` AS 
select concat(iso_name, conf.name) id, iso_name as country, conf.name as conference_name, count(iso_name) as number from country c, conference_member cm, address a, conference conf
where cm.address_id = a.id
and a.country_id = c.id
and cm.conference_id = conf.id
group by iso_name, conf.name

    ;

-- -----------------------------------------------------
-- View `conference`.`stat_mb_by_role`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `conference`.`stat_mb_by_role` ;
DROP TABLE IF EXISTS `conference`.`stat_mb_by_role`;
USE `conference`;
CREATE  OR REPLACE VIEW `stat_mb_by_role` AS
select concat(iso_name, conf.name,r.name) id, concat(iso_name, conf.name) stat_mb_per_ctry_conf_id, r.name role_name, count(r.name) as number 
from country c, conference_member cm, address a, conference conf, member_role mr, role r
where mr.role_id = r.id
and mr.conference_member_id = cm.id 
and cm.address_id = a.id
and a.country_id = c.id
and cm.conference_id = conf.id
group by iso_name, conf.name, r.name
;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
