
-- -----------------------------------------------------
-- Schema employeeasset
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema employeeasset
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `employeeasset` DEFAULT CHARACTER SET utf8 ;
USE `employeeasset` ;

-- -----------------------------------------------------
-- Table `employeeasset`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `employeeasset`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(100) NOT NULL,
  `last_name` VARCHAR(100) NOT NULL,
  `phoneno` VARCHAR(45) NOT NULL,
  `address` VARCHAR(255) NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `status` ENUM("Active", "Inactive") NOT NULL DEFAULT 'Active',
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `employeeasset`.`asset`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `employeeasset`.`asset` (
  `asset_id` INT NOT NULL AUTO_INCREMENT,
  `asset_name` VARCHAR(100) NOT NULL,
  `asset_category` ENUM("Laptop", "Furniture", "Gadgets", "Car") NULL,
  `asset_model` VARCHAR(100) NOT NULL,
  `asset_description` VARCHAR(255) NULL,
  `asset_value` DOUBLE NOT NULL,
  `manufacturing_date` DATE NOT NULL,
  `expiry_date` DATE NOT NULL,
  `image_url` VARCHAR(255) NULL,
  `status` ENUM("Available", "Issued") NOT NULL DEFAULT 'Available',
  PRIMARY KEY (`asset_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `employeeasset`.`admin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `employeeasset`.`admin` (
  `admin_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(100) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `phoneno` VARCHAR(45) NOT NULL,
  `address` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NULL,
  `status` ENUM("Active", "Inactive") NOT NULL DEFAULT 'Active',
  PRIMARY KEY (`admin_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `employeeasset`.`asset_request`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `employeeasset`.`asset_request` (
  `request_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `asset_id` INT NOT NULL,
  `request_type` ENUM("AssetRequest", "AssetReturn") NOT NULL DEFAULT 'AssetRequest',
  `status` ENUM("Pending", "Approved", "Rejected") NOT NULL DEFAULT 'Pending',
  PRIMARY KEY (`request_id`),
  INDEX `fk_asset_request_employee1_idx` (`user_id` ASC) ,
  INDEX `fk_asset_request_asset1_idx` (`asset_id` ASC) ,
  CONSTRAINT `fk_asset_request_employee1`
    FOREIGN KEY (`user_id`)
    REFERENCES `employeeasset`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_asset_request_asset1`
    FOREIGN KEY (`asset_id`)
    REFERENCES `employeeasset`.`asset` (`asset_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `employeeasset`.`asset_allocation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `employeeasset`.`asset_allocation` (
  `asset_allocation_id` INT NOT NULL AUTO_INCREMENT,
  `request_id` INT NOT NULL,
  `admin_id` INT NOT NULL,
  `issued_date` DATE NULL,
  `returned_date` DATE NULL,
  PRIMARY KEY (`asset_allocation_id`),
  INDEX `fk_asset_allocation_admin1_idx` (`admin_id` ASC) ,
  INDEX `fk_asset_allocation_asset_request1_idx` (`request_id` ASC) ,
  CONSTRAINT `fk_asset_allocation_admin1`
    FOREIGN KEY (`admin_id`)
    REFERENCES `employeeasset`.`admin` (`admin_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_asset_allocation_asset_request1`
    FOREIGN KEY (`request_id`)
    REFERENCES `employeeasset`.`asset_request` (`request_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `employeeasset`.`asset_service`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `employeeasset`.`asset_service` (
  `service_id` INT NOT NULL AUTO_INCREMENT,
  `request_id` INT NOT NULL,
  `admin_id` INT NULL,
  `service_type` ENUM("Malfunction", "Repair", "Lost") NULL,
  `description` VARCHAR(255) NULL,
  `status` ENUM("Pending", "Serviced") NULL,
  PRIMARY KEY (`service_id`),
  INDEX `fk_asset_service_asset_request1_idx` (`request_id` ASC) ,
  INDEX `fk_asset_service_admin1_idx` (`admin_id` ASC) ,
  CONSTRAINT `fk_asset_service_asset_request1`
    FOREIGN KEY (`request_id`)
    REFERENCES `employeeasset`.`asset_request` (`request_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_asset_service_admin1`
    FOREIGN KEY (`admin_id`)
    REFERENCES `employeeasset`.`admin` (`admin_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `employeeasset`.`asset_audit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `employeeasset`.`asset_audit` (
  `audit_id` INT NOT NULL AUTO_INCREMENT,
  `request_id` INT NOT NULL,
  `audit_status` ENUM("Pending", "Verified", "Rejected") NULL DEFAULT 'Pending',
  PRIMARY KEY (`audit_id`),
  INDEX `fk_asset_audit_asset_request1_idx` (`request_id` ASC) ,
  CONSTRAINT `fk_asset_audit_asset_request1`
    FOREIGN KEY (`request_id`)
    REFERENCES `employeeasset`.`asset_request` (`request_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `employeeasset`.`login`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `employeeasset`.`login` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `role` ENUM("Admin", "Employee") NOT NULL,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) ,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

