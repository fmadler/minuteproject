-- MySQL dump 10.13  Distrib 5.1.51, for apple-darwin10.3.0 (i386)
--
-- Host: localhost    Database: buslogic_intro
-- ------------------------------------------------------
-- Server version	5.1.51

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customer`
--
DROP SCHEMA IF EXISTS `buslogic_intro` ;
CREATE SCHEMA IF NOT EXISTS `buslogic_intro` DEFAULT CHARACTER SET latin1;
USE `buslogic_intro` ;

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `Name` varchar(30) NOT NULL,
  `Balance` decimal(19,4) DEFAULT NULL,
  `CreditLimit` decimal(19,4) NOT NULL,
  `OrderSequence` int(11) DEFAULT 0,
  `Notes` varchar(50) DEFAULT NULL,
  `CustomerLevel` varchar(1) DEFAULT NULL,
  `IsCreditPreApproved` bit(1) DEFAULT NULL,
  `BigOrderCount` Int DEFAULT NULL,
  `MaxOrderAmount` decimal(19,4) NOT NULL,
  PRIMARY KEY (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department` (
  `idDepartment` bigint(20) NOT NULL AUTO_INCREMENT,
  `DepartmentName` varchar(45) DEFAULT NULL,
  `ManagedBy` varchar(20) DEFAULT NULL,
  `HeadDeptId` bigint(20) DEFAULT NULL,
  `SumSubDepartmentBudget` decimal(19,4) DEFAULT NULL,
  `Budget` decimal(19,4) DEFAULT NULL,
  `BudgetWithSubDepartmentBudget` decimal(19,4) DEFAULT NULL,
  PRIMARY KEY (`idDepartment`),
  KEY `HeadDepartment` (`HeadDeptId`),
  KEY `Manager` (`ManagedBy`),
  CONSTRAINT `HeadDepartment` FOREIGN KEY (`HeadDeptId`) REFERENCES `department` (`idDepartment`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Manager` FOREIGN KEY (`ManagedBy`) REFERENCES `employee` (`Name`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `Name` varchar(20) NOT NULL,
  `BaseSalary` decimal(10,4) DEFAULT NULL,
  `ReportsToDepartmentId` bigint(20) DEFAULT NULL,
  `DepartmentName` varchar(45) DEFAULT NULL,
  `OnLoanToDepartmentId` bigint(20) DEFAULT NULL,
  `EmployeeType` char(12) NOT NULL,
  PRIMARY KEY (`Name`),
  KEY `ReportsToDept` (`ReportsToDepartmentId`),
  KEY `OnLoanToDept` (`OnLoanToDepartmentId`),
  CONSTRAINT `ReportsToDept` FOREIGN KEY (`ReportsToDepartmentId`) REFERENCES `department` (`idDepartment`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `OnLoanToDept` FOREIGN KEY (`OnLoanToDepartmentId`) REFERENCES `department` (`idDepartment`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `empsales`
--

DROP TABLE IF EXISTS `empsales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empsales` (
  `EmpSalesId` bigint(20) NOT NULL AUTO_INCREMENT,
  `EmpName` varchar(20) DEFAULT NULL,
  `Year` int(11) DEFAULT NULL,
  `Month` int(11) DEFAULT NULL,
  `TotalSales` decimal(19,4) DEFAULT NULL,
   PRIMARY KEY (`EmpSalesId`),
  KEY `employee` (`EmpName`),
  CONSTRAINT `employee` FOREIGN KEY (`EmpName`) REFERENCES `employee` (`Name`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `employee_audit`
--

DROP TABLE IF EXISTS `employee_audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee_audit` (
  `Name` varchar(20) DEFAULT NULL,
  `Employee_audit_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `BaseSalary` decimal(19,4) DEFAULT NULL,
  `BaseSalary_old` decimal(19,4) DEFAULT NULL,
  PRIMARY KEY (`employee_audit_id`),
  KEY `employee` (`name`),
  CONSTRAINT `auditedEmployee` FOREIGN KEY (`name`) REFERENCES `employee` (`Name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- -----------------------------------------------------
-- Table `buslogic_intro`.`employee_raise_service`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `buslogic_intro`.`employee_raise_service` ;

CREATE  TABLE IF NOT EXISTS `buslogic_intro`.`employee_raise_service` (
  `Name` VARCHAR(20) NULL ,
  `PercentRaiseToGive` DECIMAL(5,2) NOT NULL ,
  `CurrentSalary` DECIMAL(19,4) NULL DEFAULT NULL ,
  `NewSalary` DECIMAL(19,4) NULL DEFAULT NULL ,
  `EmpRaiseId` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  INDEX `employeeForRaise` (`Name` ASC) ,
  PRIMARY KEY (`EmpRaiseId`) ,
  CONSTRAINT `employeeForRaise`
    FOREIGN KEY (`Name` )
    REFERENCES `buslogic_intro`.`employee` (`Name` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


--
-- Table structure for table `lineitem`
--

DROP TABLE IF EXISTS `lineitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lineitem` (
  `ProductNumber` bigint(20) NOT NULL,
  `PurchaseOrderNumber` bigint(20) NOT NULL,
  `QtyOrdered` int(11) DEFAULT NULL,
  `PartPrice` decimal(19,4) DEFAULT NULL,
  `Amount` decimal(19,4) DEFAULT NULL,
  `KitQuantityOrdered` int(11) DEFAULT NULL,
  `KitNumberRequired` int(11) DEFAULT NULL,
  `KitComponentCount` int(11) DEFAULT NULL,
  `KitPartNum` bigint(20) DEFAULT NULL,
  `KitOrderNumber` bigint(20) DEFAULT NULL,
  `Notes` varchar(50) DEFAULT NULL,
  `IsReady` bit(1) DEFAULT b'0',
  `LineitemId` bigint(20) NOT NULL AUTO_INCREMENT,
  `KitLineitemId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`LineitemId`),
  KEY `Custorder` (`PurchaseOrderNumber`),
  KEY `KitItem` (`KitLineitemId`),
  KEY `Part` (`ProductNumber`),
  CONSTRAINT `Part` FOREIGN KEY (`ProductNumber`) REFERENCES `product` (`PartNum`) ON UPDATE CASCADE,
  CONSTRAINT `Custorder` FOREIGN KEY (`PurchaseOrderNumber`) REFERENCES `purchaseorder` (`OrderNumber`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `KitItem` FOREIGN KEY (`KitLineitemId`) REFERENCES `lineitem` (`LineitemId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

-- -----------------------------------------------------
-- Table `buslogic_intro`.`lineitem_note`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `buslogic_intro`.`lineitem_note` ;

CREATE  TABLE IF NOT EXISTS `buslogic_intro`.`lineitem_note` (
  `LineItemId` BIGINT(20) NOT NULL ,
  `NoteNumber` BIGINT(20) NOT NULL ,
  `SpecialHandling` VARCHAR(50) NULL DEFAULT NULL ,
  PRIMARY KEY (`NoteNumber`, `LineItemId`) ,
  INDEX `LineitemForNote` (`LineItemId` ASC) ,
  CONSTRAINT `LineitemForNote`
    FOREIGN KEY (`LineItemId` )
    REFERENCES `buslogic_intro`.`lineitem` (`LineitemId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = latin1;


--
-- Table structure for table `lineitem_usage`
--

DROP TABLE IF EXISTS `lineitem_usage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lineitem_usage` (
  `Explanation` varchar(50) DEFAULT NULL,
  `OrderNumber` bigint(20) NOT NULL,
  `LineitemId` bigint(20) NOT NULL,
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Id`),
  CONSTRAINT `OrderForUsage`
    FOREIGN KEY (`OrderNumber` )
    REFERENCES `buslogic_intro`.`purchaseorder` (`OrderNumber` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `LineItemForUsage`
    FOREIGN KEY (`LineitemId` )
    REFERENCES `buslogic_intro`.`Lineitem` (`LineitemId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
 ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `order_issue`
--

DROP TABLE IF EXISTS `order_issue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_issue` (
  `Issue` varchar(50) DEFAULT NULL,
  `DateStamp` datetime DEFAULT NULL,
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `PurchaseOrderNumber` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment` (
  `PaymentID` bigint(20) NOT NULL AUTO_INCREMENT,
  `Amount` decimal(19,4) DEFAULT NULL,
  `AmountUnDisbursed` decimal(19,4) DEFAULT NULL,
  `PlacedDate` date DEFAULT NULL,
  `CustomerName` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`PaymentID`),
  KEY `PaymentCustomer` (`CustomerName`),
  CONSTRAINT `PaymentCustomer` FOREIGN KEY (`CustomerName`) REFERENCES `customer` (`Name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `payment_purchaseorder_allocation`
--

DROP TABLE IF EXISTS `payment_purchaseorder_allocation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_purchaseorder_allocation` (
  `PurchaseOrderNumber` bigint(20) NOT NULL,
  `PaymentID` bigint(20) NOT NULL,
  `Amount` decimal(19,4) DEFAULT NULL,
  PRIMARY KEY (`PurchaseOrderNumber`,`PaymentID`),
  KEY `Payment` (`PaymentID`),
  KEY `Purchaseorder` (`PurchaseOrderNumber`),
  CONSTRAINT `Payment` FOREIGN KEY (`PaymentID`) REFERENCES `payment` (`PaymentID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Purchaseorder` FOREIGN KEY (`PurchaseOrderNumber`) REFERENCES `purchaseorder` (`OrderNumber`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `Name` varchar(50) DEFAULT NULL,
  `Price` decimal(19,4) DEFAULT NULL,
  `QtyOnHand` int(11) DEFAULT NULL,
  `TotalQtyOrdered` int(11) DEFAULT NULL,
  `QtyReorder` int(11) DEFAULT NULL,
  `IsReorderRequired` bit(1) DEFAULT NULL,
  `CountComponents` int(11) DEFAULT NULL,
  `SumComponentsValue` decimal(19,4) DEFAULT NULL,
  `NeedsUsageTerms` bit(1) NOT NULL,
  `PartNum` bigint(20) NOT NULL AUTO_INCREMENT,
  `IsActive` bit(1) DEFAULT NULL,
  PRIMARY KEY (`PartNum`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `product_billofmaterials`
--

DROP TABLE IF EXISTS `product_billofmaterials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_billofmaterials` (
  `PartNumKit` bigint(20) NOT NULL,
  `PartNumber` bigint(20) NOT NULL,
  `ProductNameKit` varchar(50) DEFAULT NULL,
  `ProductName` varchar(50) DEFAULT NULL,
  `ComponentPrice` decimal(19,4) DEFAULT NULL,
  `KitNumberRequired` int(11) DEFAULT NULL,
  `Value` decimal(19,4) DEFAULT NULL,
  PRIMARY KEY (`PartNumKit`,`PartNumber`),
  KEY `Kit` (`PartNumKit`),
  KEY `Product` (`PartNumber`),
  CONSTRAINT `Kit` FOREIGN KEY (`PartNumKit`) REFERENCES `product` (`PartNum`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Product` FOREIGN KEY (`PartNumber`) REFERENCES `product` (`PartNum`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `purchaseorder`
--

DROP TABLE IF EXISTS `purchaseorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchaseorder` (
  `OrderNumber` bigint(20) NOT NULL AUTO_INCREMENT,
  `AmountTotal` decimal(19,4) DEFAULT NULL,
  `AmountDiscounted` decimal(19,4) DEFAULT NULL,
  `AmountPaid` decimal(19,4) DEFAULT NULL,
  `AmountUnPaid` decimal(19,4) DEFAULT NULL COMMENT '/orders.findall({it.isPaid==false}).sum(it.amount)',
  `IsReady` bit(1) DEFAULT NULL,
  `ApprovingOfficer` varchar(10) DEFAULT NULL,
  `OfficerItemUsageApproval` varchar(50) DEFAULT NULL,
  `UnresolvedUsageCount` int(11) DEFAULT NULL,
  `PlacedDate` date DEFAULT NULL,
  `DueDate` date DEFAULT NULL,
  `SalesRepName` varchar(20) DEFAULT NULL,
  `CustomerName` varchar(30) DEFAULT NULL,
  `ClonedFromOrderNumber` bigint(20) DEFAULT NULL,
  `OrderSequence` int(11) DEFAULT 0,
  `ItemCount` int(11) DEFAULT NULL,
  `EmpSalesId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`OrderNumber`),
  KEY `SalesRep` (`SalesRepName`),
  KEY `Customer` (`CustomerName`),
  CONSTRAINT `Customer` FOREIGN KEY (`CustomerName`) REFERENCES `customer` (`Name`) ON UPDATE CASCADE,
  CONSTRAINT `SalesRep` FOREIGN KEY (`SalesRepName`) REFERENCES `employee` (`Name`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `EmpSales` FOREIGN KEY (`EmpSalesId`) REFERENCES `empsales` (`EmpSalesId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `valid_customerlevel`
--

DROP TABLE IF EXISTS `valid_customerlevel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `valid_customerlevel` (
  `LevelSTR` varchar(1) NOT NULL,
  `Description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`LevelSTR`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

