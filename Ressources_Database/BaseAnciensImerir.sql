-- MySQL Script generated by MySQL Workbench
-- Thu Jan 12 17:21:31 2017
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `AncienImerir` DEFAULT CHARACTER SET utf8 ;
USE `AncienImerir` ;

-- -----------------------------------------------------
-- Table `mydb`.`Promotion`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AncienImerir`.`Promotion` ;

CREATE TABLE IF NOT EXISTS `AncienImerir`.`Promotion` (
  `id` INT NOT NULL,
  `nom` VARCHAR(255) NULL,
  `annee` VARCHAR(255) NULL,
  `promo_actuelle` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Eleve`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AncienImerir`.`Eleve` ;

CREATE TABLE IF NOT EXISTS `AncienImerir`.`Eleve` (
  `id` INT NULL AUTO_INCREMENT,
  `nom` VARCHAR(255) NULL,
  `prenom` VARCHAR(255) BINARY NULL,
  `date_inscritpion` VARCHAR(255) NULL,
  `promotion` VARCHAR(255) NULL,
  `telephone_mobile` VARCHAR(255) NULL,
  `telephone_perso` VARCHAR(255) NULL,
  `site_web` VARCHAR(255) NULL,
  `adresse` VARCHAR(255) NULL,
  `ville` VARCHAR(255) NULL,
  `entreprise` VARCHAR(255) NULL,
  `photo` BLOB NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `promotion`
    FOREIGN KEY (`id`)
    REFERENCES `AncienImerir`.`Promotion` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Admin`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AncienImerir`.`Admin` ;

CREATE TABLE IF NOT EXISTS `AncienImerir`.`Admin` (
  `id` INT NOT NULL,
  `nom` VARCHAR(255) NOT NULL,
  `prenom` VARCHAR(255) NOT NULL,
  `mail` VARCHAR(255) NOT NULL,
  `mot_de_passe_admin` VARCHAR(255) NOT NULL,
  `sel` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Developpeur`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AncienImerir`.`Developpeur` ;

CREATE TABLE IF NOT EXISTS `AncienImerir`.`Developpeur` (
  `id` INT NOT NULL,
  `nom` VARCHAR(255) NULL,
  `prenom` VARCHAR(255) NULL,
  `mail` VARCHAR(255) NOT NULL,
  `mot_de_passe_developpeur` VARCHAR(255) NULL,
  `sel` VARCHAR(255) NULL,
  PRIMARY KEY (`id`, `mail`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Invite`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AncienImerir`.`Invite` ;

CREATE TABLE IF NOT EXISTS `AncienImerir`.`Invite` (
  `id` INT NOT NULL,
  `nom` VARCHAR(255) NOT NULL,
  `prenom` VARCHAR(255) NULL,
  `mail` VARCHAR(255) NULL,
  `date_inscription` VARCHAR(255) NULL,
  `garant` VARCHAR(255) NULL,
  PRIMARY KEY (`id`, `nom`),
  CONSTRAINT `eleve_garant`
    FOREIGN KEY (`id`)
    REFERENCES `AncienImerir`.`Eleve` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Entreprise`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AncienImerir`.`Entreprise` ;

CREATE TABLE IF NOT EXISTS `AncienImerir`.`Entreprise` (
  `id` INT NOT NULL,
  `nom` VARCHAR(255) NULL,
  `adresse` VARCHAR(255) NULL,
  `ville`VARCHAR(255) NULL,
  `ancien_eleve_associe` VARCHAR(255) NULL,
  `telephone` VARCHAR(255) NULL,
  `mail` VARCHAR(255) NULL,
  `domaine_activite` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
