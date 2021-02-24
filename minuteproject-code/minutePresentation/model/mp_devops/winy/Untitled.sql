SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';


-- -----------------------------------------------------
-- Table `winy`.`REGION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `winy`.`REGION` ;

CREATE  TABLE IF NOT EXISTS `winy`.`REGION` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `NAME` VARCHAR(455) NOT NULL ,
  `GEO` VARCHAR(45) NULL ,
  `DESCRIPTION` VARCHAR(6000) NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `winy`.`CONTACT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `winy`.`CONTACT` ;

CREATE  TABLE IF NOT EXISTS `winy`.`CONTACT` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `NAME` VARCHAR(255) NULL ,
  `STREET1` VARCHAR(255) NULL ,
  `STREET2` VARCHAR(255) NULL ,
  `POST_CODE` INT NULL ,
  `CITY` VARCHAR(45) NULL ,
  `MAIL` VARCHAR(45) NULL ,
  `OPENING_HOURS_INFO` VARCHAR(600) NULL ,
  `TELEPHONE` VARCHAR(45) NULL ,
  `WEBSITE_URL` VARCHAR(400) NULL ,
  `REGISTER_DATE` DATETIME NOT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `winy`.`WINERY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `winy`.`WINERY` ;

CREATE  TABLE IF NOT EXISTS `winy`.`WINERY` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `NAME` VARCHAR(455) NULL ,
  `GEO` VARCHAR(45) NULL ,
  `REGION_ID` INT NOT NULL ,
  `CONTACT_ID` INT NULL ,
  `DESCRIPTION` VARCHAR(6000) NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_WINERY_DOMAIN_idx` (`REGION_ID` ASC) ,
  INDEX `fk_WINERY_CONTACT1_idx` (`CONTACT_ID` ASC) ,
  CONSTRAINT `fk_WINERY_DOMAIN`
    FOREIGN KEY (`REGION_ID` )
    REFERENCES `winy`.`REGION` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_WINERY_CONTACT1`
    FOREIGN KEY (`CONTACT_ID` )
    REFERENCES `winy`.`CONTACT` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `winy`.`PRODUCT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `winy`.`PRODUCT` ;

CREATE  TABLE IF NOT EXISTS `winy`.`PRODUCT` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `NAME` VARCHAR(45) NOT NULL ,
  `IMAGE_URL` VARCHAR(45) NULL ,
  `DETAIL` VARCHAR(45) NULL ,
  `PRICE_INFO` VARCHAR(45) NULL ,
  `WINERY_ID` INT NOT NULL ,
  `ACTIVE` TINYINT(1) NULL ,
  `YEAR` INT NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_PRODUCT_WINERY1_idx` (`WINERY_ID` ASC) ,
  CONSTRAINT `fk_PRODUCT_WINERY1`
    FOREIGN KEY (`WINERY_ID` )
    REFERENCES `winy`.`WINERY` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `winy`.`AWARD`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `winy`.`AWARD` ;

CREATE  TABLE IF NOT EXISTS `winy`.`AWARD` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `EVENT_REFERENCE` VARCHAR(45) NULL ,
  `YEAR` INT NULL ,
  `WINERY_ID` INT NOT NULL ,
  `AWARD` VARCHAR(500) NULL ,
  `PRODUCT_ID` INT NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_AWARD_WINERY1_idx` (`WINERY_ID` ASC) ,
  INDEX `fk_AWARD_PRODUCT1_idx` (`PRODUCT_ID` ASC) ,
  CONSTRAINT `fk_AWARD_WINERY1`
    FOREIGN KEY (`WINERY_ID` )
    REFERENCES `winy`.`WINERY` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_AWARD_PRODUCT1`
    FOREIGN KEY (`PRODUCT_ID` )
    REFERENCES `winy`.`PRODUCT` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `winy`.`WINERY_PICTURES`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `winy`.`WINERY_PICTURES` ;

CREATE  TABLE IF NOT EXISTS `winy`.`WINERY_PICTURES` (
  `ID` INT NOT NULL ,
  `WINERY_ID` INT NOT NULL ,
  `IMAGE_NAME` VARCHAR(45) NOT NULL ,
  `DESCRIPTION` VARCHAR(500) NULL ,
  `DISPLAY_ORDER` INT NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_WINERY_PICTURES_WINERY1_idx` (`WINERY_ID` ASC) ,
  UNIQUE INDEX `WINERY_ID_UNIQUE` (`WINERY_ID` ASC) ,
  UNIQUE INDEX `DISPLAY_ORDER_UNIQUE` (`DISPLAY_ORDER` ASC) ,
  CONSTRAINT `fk_WINERY_PICTURES_WINERY1`
    FOREIGN KEY (`WINERY_ID` )
    REFERENCES `winy`.`WINERY` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `winy`.`CEPAGE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `winy`.`CEPAGE` ;

CREATE  TABLE IF NOT EXISTS `winy`.`CEPAGE` (
  `ID` INT NOT NULL ,
  `NAME` VARCHAR(45) NOT NULL ,
  `DESCRIPTION` VARCHAR(45) NOT NULL ,
  `NB_YEAR_OF_CONSERVATION` INT NOT NULL ,
  `BEST_NB_OF_YEAR_TO_CONSUME_MIN` INT NOT NULL ,
  `BEST_NB_OF_YEAR_TO_CONSUME_MAX` INT NOT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `winy`.`REGION`
-- -----------------------------------------------------
START TRANSACTION;
USE `winy`;
INSERT INTO `winy`.`REGION` (`ID`, `NAME`, `GEO`, `DESCRIPTION`) VALUES (1, 'Alsace', 'à définir', 'Super région à visiter');
INSERT INTO `winy`.`REGION` (`ID`, `NAME`, `GEO`, `DESCRIPTION`) VALUES (2, 'Bordeaux', 'près de l\'Atlantique', 'Bon vin');

COMMIT;

-- -----------------------------------------------------
-- Data for table `winy`.`WINERY`
-- -----------------------------------------------------
START TRANSACTION;
USE `winy`;
INSERT INTO `winy`.`WINERY` (`ID`, `NAME`, `GEO`, `REGION_ID`, `CONTACT_ID`, `DESCRIPTION`) VALUES (1, 'Vignoble Kaysersberg', 'Près colmar', 1, NULL, 'Domaine fleuri');
INSERT INTO `winy`.`WINERY` (`ID`, `NAME`, `GEO`, `REGION_ID`, `CONTACT_ID`, `DESCRIPTION`) VALUES (2, 'Saint Emilion', NULL, 2, NULL, 'Très connu');

COMMIT;
