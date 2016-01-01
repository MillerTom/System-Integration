-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.5.13-55


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema ebay
--

CREATE DATABASE IF NOT EXISTS ebay;
USE ebay;
CREATE TABLE  `ebay`.`ebay_list_au` (
  `SellerSku` varchar(16) NOT NULL,
  `SuppId` int(10) DEFAULT NULL,
  `ISBN13` bigint(20) NOT NULL,
  `ISBN10` varchar(10) DEFAULT NULL,
  `QTY` int(10) NOT NULL,
  `Price` double NOT NULL DEFAULT '0',
  KEY `Index_2` (`ISBN13`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;
CREATE TABLE  `ebay`.`files` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(45) NOT NULL,
  `file_type` int(11) DEFAULT NULL,
  `file_date` date NOT NULL,
  `file_time` varchar(12) NOT NULL,
  `download_file_path` varchar(255) DEFAULT NULL,
  `file_path` varchar(255) DEFAULT NULL,
  `request_status` int(11) DEFAULT NULL,
  `response_status` int(11) DEFAULT NULL,
  PRIMARY KEY (`f_id`),
  KEY `FT_FK` (`file_type`),
  KEY `REQ_S_FK` (`request_status`),
  KEY `RESP_S_FK` (`response_status`),
  CONSTRAINT `FT_FK` FOREIGN KEY (`file_type`) REFERENCES `valid_entry` (`ve_id`),
  CONSTRAINT `REQ_S_FK` FOREIGN KEY (`request_status`) REFERENCES `valid_entry` (`ve_id`),
  CONSTRAINT `RESP_S_FK` FOREIGN KEY (`response_status`) REFERENCES `valid_entry` (`ve_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE  `ebay`.`logs` (
  `l_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) NOT NULL,
  `message` text CHARACTER SET utf8 NOT NULL,
  `message_type` varchar(45) DEFAULT NULL,
  `action_date` date DEFAULT NULL,
  `action_time` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`l_id`),
  KEY `UN_FK` (`user_name`),
  KEY `MT_FK` (`message_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE  `ebay`.`reports` (
  `r_id` int(11) NOT NULL AUTO_INCREMENT,
  `report_type` int(11) DEFAULT NULL,
  `report_date` date NOT NULL,
  `report_time` varchar(12) DEFAULT NULL,
  `file_name` varchar(255) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `user` varchar(45) DEFAULT NULL,
  `load_status` int(11) DEFAULT NULL,
  PRIMARY KEY (`r_id`),
  KEY `RT_FK` (`report_type`),
  KEY `ST_FK` (`status`),
  KEY `U_FK` (`user`),
  KEY `LS_FK` (`load_status`),
  CONSTRAINT `LS_FK` FOREIGN KEY (`load_status`) REFERENCES `valid_entry` (`ve_id`),
  CONSTRAINT `RT_FK` FOREIGN KEY (`report_type`) REFERENCES `valid_entry` (`ve_id`),
  CONSTRAINT `ST_FK` FOREIGN KEY (`status`) REFERENCES `valid_entry` (`ve_id`),
  CONSTRAINT `U_FK` FOREIGN KEY (`user`) REFERENCES `users` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE  `ebay`.`suppliers` (
  `SuppID` int(10) NOT NULL AUTO_INCREMENT,
  `SuppName` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `CurID` int(10) DEFAULT NULL,
  `BDLMFileName` varchar(100) DEFAULT NULL,
  `Sku` char(3) NOT NULL,
  `freight` varchar(10) NOT NULL,
  `TableName` varchar(45) NOT NULL DEFAULT 'supplier_list',
  `BDWFileName` varchar(100) DEFAULT NULL,
  `supplierAvailability` char(1) DEFAULT NULL,
  `upd_zero_wieght` int(10) NOT NULL DEFAULT '0',
  `weight_restric_status` enum('Yes','No') NOT NULL DEFAULT 'No',
  `frwt_unit` varchar(50) DEFAULT NULL,
  `handling_fee` double DEFAULT NULL,
  PRIMARY KEY (`SuppID`),
  KEY `SuppCurFK` (`CurID`)
) ENGINE=MyISAM AUTO_INCREMENT=119 DEFAULT CHARSET=latin1;
INSERT INTO `ebay`.`suppliers` VALUES  (1,'AirLift',2,'E:/AgentScape91/lib/cdbmqueries/AirLiftFull10-1-09506PM.txt','AAY','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/AirLiftFull7-11-09416PM.txt','A',0,'No',NULL,NULL),
 (2,'BookPoint',2,'E:/AgentScape91/lib/cdbmqueries/BookPointFull10-1-09500PM.txt','AA6','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/BookPointFull7-11-09411PM.txt','B',0,'No',NULL,NULL),
 (3,'Ingram',1,'E:/AgentScape91/lib/cdbmqueries/IngramFull10-1-091224PM.txt','AAC','1.15','bisupplier_list','E:/AgentScape91/lib/cdbmqueries/IngramFull7-12-091208PM.txt','B',399,'No',NULL,NULL),
 (4,'CentralBooks',2,'E:/AgentScape91/lib/cdbmqueries/CentralBooksFull9-27-09435PM.txt','AAO','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/CentralBooksFull7-12-09730AM.txt','A',0,'No',NULL,NULL),
 (5,'ComputerBookshop',2,'E:/AgentScape91/lib/cdbmqueries/ComputerBookshopFull10-1-09453PM.txt','AAP','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/ComputerBookshopFull7-11-09405PM.txt','A',0,'No',NULL,NULL),
 (6,'CUP',2,'E:/AgentScape91/lib/cdbmqueries/CUPFull10-1-09502PM.txt','AAA','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/CUPFull7-12-09733AM.txt','A',0,'No',NULL,NULL),
 (7,'Elsevier',2,'E:/AgentScape91/lib/cdbmqueries/ElsevierFull9-30-09451PM.txt','AA5','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/ElsevierFull7-11-09422PM.txt','B',0,'No',NULL,NULL),
 (8,'Foyles',2,'E:/AgentScape91/lib/cdbmqueries/FoylesFull10-1-09514PM.txt','AAW','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/FoylesFull7-12-09734AM.txt','D',0,'No',NULL,NULL),
 (9,'Gazelle',2,'E:/AgentScape91/lib/cdbmqueries/GazelleFull10-1-09502PM.txt','AAG','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/GazelleFull7-12-09746AM.txt','A',0,'No',NULL,NULL),
 (10,'GBS',2,'E:/AgentScape91/lib/cdbmqueries/GBSFull10-1-09508PM.txt','AA9','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/GBSFull7-12-09432PM.txt','B',0,'No',NULL,NULL),
 (11,'Capstone',2,'E:/AgentScape91/lib/cdbmqueries/CapstoneFull10-1-09509PM.txt','AA0','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/HarcourtFull7-11-09418PM.txt','A',0,'No',NULL,NULL),
 (12,'HarperCollins',2,'E:/AgentScape91/lib/cdbmqueries/HarperCollinsFull10-1-09509PM.txt','AA8','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/HarperCollinsFull7-12-09747AM.txt','B',0,'No',NULL,NULL),
 (13,'LBS',2,'E:/AgentScape91/lib/cdbmqueries/LBSFull10-1-09459PM.txt','AA2','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/LBSFull7-11-09410PM.txt','A',0,'No',NULL,NULL),
 (14,'LightningSource',2,'E:/AgentScape91/lib/cdbmqueries/LightningSourceFull10-1-09509PM.txt','AAV','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/LightningSourceFull7-12-09439PM.txt','B',0,'No',NULL,NULL),
 (15,'MacMillan',2,'E:/AgentScape91/lib/cdbmqueries/MacMillanFull10-1-09512PM.txt','AA7','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/MacMillanFull7-11-09420PM.txt','B',0,'No',NULL,NULL),
 (16,'Marston',2,'E:/AgentScape91/lib/cdbmqueries/MarstonFull10-1-09512PM.txt','AAJ','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/MarstonFull7-11-09420PM.txt','B',0,'No',NULL,NULL),
 (17,'MacGrawhill',2,'E:/AgentScape91/lib/cdbmqueries/MacGrawhillFull10-1-09512PM.txt','AA3','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/MacGrawhillFull7-11-09420PM.txt','B',0,'No',NULL,NULL),
 (18,'NBN',2,'E:/AgentScape91/lib/cdbmqueries/NBNFull10-1-09506PM.txt','AAR','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/NBNFull7-10-09449PM.txt','A',0,'No',NULL,NULL),
 (20,'Pearson',2,'E:/AgentScape91/lib/cdbmqueries/PearsonFull10-1-09513PM.txt','AAK','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/PearsonFull7-12-09910PM.txt','B',0,'No',NULL,NULL),
 (21,'Cengage',2,'E:/AgentScape91/lib/cdbmqueries/CengageFull10-1-09506PM.txt','AAQ','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/CengageFull7-11-09416PM.txt','A',0,'No',NULL,NULL),
 (22,'TBS',2,'E:/AgentScape91/lib/cdbmqueries/TBSFull10-1-09513PM.txt','AB9','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/TBSFull7-12-09445PM.txt','B',0,'No',NULL,NULL),
 (23,'TurpinEurospan',2,'E:/AgentScape91/lib/cdbmqueries/TurpinEurospanFull10-1-09504PM.txt','AAN','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/TurpinEurospanFull7-11-09414PM.txt','A',0,'No',NULL,NULL),
 (24,'Wiley',2,'E:/AgentScape91/lib/cdbmqueries/WileyFull10-1-09513PM.txt','AAH','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/WileyFull7-11-09421PM.txt','B',0,'No',NULL,NULL),
 (25,'Sage',2,'E:/AgentScape91/lib/cdbmqueries/SageFull10-1-09514PM.txt','AAX','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/SageFull7-12-09445PM.txt','B',0,'No',NULL,NULL),
 (26,'Turnaround',2,'E:/AgentScape91/lib/cdbmqueries/TurnaroundFull10-1-09458PM.txt','AAT','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/TurnaroundFull7-11-09409PM.txt','A',0,'No',NULL,NULL),
 (69,'WelshBooks',2,NULL,'WBC','0','supplier_list',NULL,'A',0,'No',NULL,NULL),
 (70,'IngramPOD',1,NULL,'APC','1.15','bisupplier_list',NULL,'C',399,'No',NULL,NULL),
 (28,'BnT1',1,'E:/AgentScape91/lib/cdbmqueries/BnT1Full10-1-091040AM.txt','AAS','1.13','bisupplier_list','E:/AgentScape91/lib/cdbmqueries/BnT1Full7-12-091040AM.txt','B',399,'No',NULL,NULL),
 (29,'Gardners',2,'E:/AgentScape91/lib/cdbmqueries/GardnersFull10-1-09805PM.txt','AAZ','0','bgsupplier_list','E:/AgentScape91/lib/cdbmqueries/GardnersFull7-12-09742PM.txt','A',0,'No',NULL,NULL),
 (30,'Bertrams',2,'E:/AgentScape91/lib/cdbmqueries/BertramsFull10-1-09811PM.txt','AAU','0','bgsupplier_list','E:/AgentScape91/lib/cdbmqueries/BertramsFull7-12-09744PM.txt','A',0,'No',NULL,NULL),
 (31,'BookSource',2,'E:/AgentScape91/lib/cdbmqueries/BookSourceFull10-1-09507PM.txt','ABS','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/BookSourceFull7-12-09755AM.txt','A',0,'No',NULL,NULL),
 (32,'OUP',2,'E:/AgentScape91/lib/cdbmqueries/OUPFull10-1-09513PM.txt','AOP','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/OUPFull7-10-09501PM.txt','B',0,'No',NULL,NULL),
 (33,'STL',2,'E:/AgentScape91/lib/cdbmqueries/STLFull10-1-09457PM.txt','AST','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/STLFull7-12-09755AM.txt','A',0,'No',NULL,NULL),
 (34,'LBBC',2,'E:/AgentScape91/lib/cdbmqueries/LBBCFull10-1-09507PM.txt','ALB','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/LBBCFull7-10-09453PM.txt','A',0,'No',NULL,NULL),
 (35,'DeepBooks',2,'E:/AgentScape91/lib/cdbmqueries/DeepBooksFull10-1-09507PM.txt','ADB','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/DeepBooksFull7-11-09416PM.txt','A',0,'No',NULL,NULL),
 (36,'BookSpeed',2,'E:/AgentScape91/lib/cdbmqueries/BookSpeedFull10-1-09454PM.txt','BKS','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/BookSpeedFull7-9-09446PM.txt','A',0,'No',NULL,NULL),
 (37,'Diamond',2,'E:/AgentScape91/lib/cdbmqueries/DiamondFull9-29-09451PM.txt','ADI','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/DiamondFull7-10-09453PM.txt','A',0,'No',NULL,NULL),
 (38,'NelsonThornes',2,'E:/AgentScape91/lib/cdbmqueries/NelsonThornesFull9-30-09445PM.txt','ANT','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/NelsonThornesFull7-11-09416PM.txt','B',0,'No',NULL,NULL),
 (39,'MSL',2,'E:/AgentScape91/lib/cdbmqueries/MSLFull10-1-09507PM.txt','AMS','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/MSLFull7-12-09428PM.txt','A',0,'No',NULL,NULL),
 (40,'Penguin',2,'E:/AgentScape91/lib/cdbmqueries/PenguinFull9-30-09451PM.txt','APG','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/PenguinFull7-12-09755AM.txt','B',0,'No',NULL,NULL),
 (41,'SCM',2,'E:/AgentScape91/lib/cdbmqueries/SCMFull10-1-09507PM.txt','ASC','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/SCMFull7-11-09416PM.txt','A',0,'No',NULL,NULL),
 (42,'CBS',2,'E:/AgentScape91/lib/cdbmqueries/CBSFull9-29-09453PM.txt','ACB','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/CBSFull7-10-09454PM.txt','A',0,'No',NULL,NULL),
 (43,'IngramCDF',1,'','AAD','0','supplier_list','','B',0,'No',NULL,NULL),
 (44,'BEBC',2,'E:/AgentScape91/lib/cdbmqueries/BEBCFull10-1-09509PM.txt','ABE','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/BEBCFull7-12-09438PM.txt','A',0,'No',NULL,NULL),
 (45,'Boydell',2,'E:/AgentScape91/lib/cdbmqueries/BoydellFull10-1-09509PM.txt','ABY','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/BoydellFull7-10-09456PM.txt','A',0,'No',NULL,NULL),
 (46,'BnT2',1,'E:/AgentScape91/lib/cdbmqueries/BnT2Full10-1-091051AM.txt','AAE','1.13','bisupplier_list','E:/AgentScape91/lib/cdbmqueries/BnT2Full7-12-091046AM.txt','B',399,'No',NULL,NULL),
 (47,'Bookazine',1,'E:/AgentScape91/lib/cdbmqueries/BookazineFull10-1-091051AM.txt','ABZ','1.50','supplier_list','','B',399,'No',NULL,NULL),
 (48,'Scholastic',2,'E:/AgentScape91/lib/cdbmqueries/ScholasticFull10-1-09514PM.txt','ASH','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/ScholasticFull7-11-09422PM.txt','A',0,'No',NULL,NULL),
 (49,'TSO',2,'E:/AgentScape91/lib/cdbmqueries/TSOFull10-1-09507PM.txt','TS1','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/TSOFull7-11-09416PM.txt','A',0,'No',NULL,NULL),
 (50,'Libri',3,'E:/AgentScape91/lib/cdbmqueries/LibriFull10-1-091052AM.txt','LIB','0.0012','bisupplier_list','E:/AgentScape91/lib/cdbmqueries/LibriFull7-12-091047AM.txt','C',0,'No',NULL,NULL),
 (51,'Stanfords',2,'E:/AgentScape91/lib/cdbmqueries/StanfordsFull10-1-09508PM.txt','ASF','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/StanfordsFull7-11-09417PM.txt','B',0,'No',NULL,NULL),
 (52,'ESB',2,'E:/AgentScape91/lib/cdbmqueries/ESBFull10-1-09508PM.txt','ESB','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/ESBFull7-12-09437PM.txt','A',0,'No',NULL,NULL),
 (53,'Phaidon',2,'E:/AgentScape91/lib/cdbmqueries/PhaidonFull10-1-09508PM.txt','PHA','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/PhaidonFull7-11-09417PM.txt','A',0,'No',NULL,NULL),
 (54,'CornerHouse',2,'E:/AgentScape91/lib/cdbmqueries/CornerHouseFull10-1-09508PM.txt','ACH','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/CornerHouseFull7-10-09455PM.txt','A',0,'No',NULL,NULL),
 (55,'GillMacmillan',2,'E:/AgentScape91/lib/cdbmqueries/GillMacmillanFull10-1-09504PM.txt','AGM','0','supplier_list','E:/AgentScape91/lib/cdbmqueries/GillMacmillanFull7-12-09755AM.txt','B',0,'No',NULL,NULL),
 (56,'Haynes',2,'E:/AgentScape91/lib/cdbmqueries/HaynesFull10-1-09508PM.txt','HAY','0','supplier_list','','A',0,'No',NULL,NULL),
 (57,'SearchPress',2,'E:/AgentScape91/lib/cdbmqueries/SearchPressFull10-1-09508PM.txt','ASP','0','supplier_list','','A',0,'No',NULL,NULL),
 (58,'Artdata',2,'E:/AgentScape91/lib/cdbmqueries/ArtdataFull10-1-09508PM.txt','ART','0','supplier_list','','A',0,'No',NULL,NULL),
 (59,'Motilal',2,'E:/AgentScape91/lib/cdbmqueries/MotilalFull10-1-09812PM.txt','AMO','0','supplier_list','','A',0,'No',NULL,NULL),
 (60,'WestCountry',2,' ','AWC','0','supplier_list',' ','A',0,'No',NULL,NULL),
 (61,'MediaMBT',NULL,NULL,'MBT','','supplier_list',NULL,NULL,0,'No',NULL,NULL),
 (62,'MediaMSD',NULL,NULL,'MSD','','supplier_list',NULL,NULL,0,'No',NULL,NULL),
 (63,'BDStock',2,NULL,'AAB','0','bisupplier_list',NULL,'A',0,'No',NULL,NULL),
 (64,'MediaMGD',NULL,NULL,'MGD','0','supplier_list',NULL,NULL,0,'No',NULL,NULL),
 (65,'Cordee',2,NULL,'ACD','0','supplier_list',NULL,'A',0,'No',NULL,NULL),
 (66,'NHBS',2,NULL,'ANH','0','supplier_list',NULL,'A',0,'No',NULL,NULL),
 (67,'KNV',3,NULL,'KNV','0.0011','bisupplier_list',NULL,'B',0,'No',NULL,NULL),
 (68,'NBNUS',1,NULL,'ANB','1.70','supplier_list',NULL,'B',399,'No',NULL,NULL),
 (71,'Centersoft',2,NULL,'MCT','0','supplier_list',NULL,NULL,0,'No',NULL,NULL),
 (72,'Ozmo',2,NULL,'MOZ','0','supplier_list',NULL,NULL,0,'No',NULL,NULL),
 (73,'Argosy',3,NULL,'ARG','0','supplier_list',NULL,'B',0,'No',NULL,NULL),
 (74,'WDL',2,NULL,'MWD','0','supplier_list',NULL,'A',0,'No',NULL,NULL),
 (75,'Midac',3,NULL,'AMI','0.0036','supplier_list',NULL,'B',0,'No',NULL,NULL),
 (76,'IVP',3,NULL,'IVP','0','supplier_list',NULL,'A',0,'No',NULL,NULL),
 (77,'Bay',2,NULL,'BAY','0','supplier_list',NULL,'A',0,'No',NULL,NULL),
 (78,'Koch',2,NULL,'MKH','0','supplier_list',NULL,'A',0,'No',NULL,NULL),
 (79,'CurveBall',2,'','MCR','0','supplier_list','','A',0,'No',NULL,NULL),
 (80,'Mastertronic',2,NULL,'MMT','0','supplier_list',NULL,'A',0,'No',NULL,NULL),
 (81,'Easons',3,'','EAS','0','supplier_list',NULL,'B',0,'No',NULL,NULL),
 (82,'ESD',2,'','MES','0','supplier_list',NULL,'A',0,'No',NULL,NULL),
 (83,'GemLogistics',2,NULL,'MGM','0','supplier_list',NULL,'A',0,'No',NULL,NULL),
 (84,'ThomasNelsonUS',1,NULL,'TNU','0.00152','supplier_list',NULL,'B',399,'No','GBP/Gram',NULL),
 (85,'Hut',2,NULL,'MHT','0','supplier_list',NULL,'A',0,'No',NULL,NULL),
 (86,'NewLeaf',1,NULL,'NLF','0.00152','supplier_list',NULL,'B',399,'No','GBP/Gram',NULL),
 (88,'STLUS',1,NULL,'AS2','0.00152','supplier_list',NULL,'B',399,'No','GBP/Gram',NULL),
 (87,'GemDistribution',2,NULL,'MG2','0','supplier_list',NULL,'A',0,'No',NULL,NULL),
 (89,'IrishBooks',3,NULL,'EIR','0','supplier_list',NULL,'B',0,'No',NULL,NULL),
 (90,'Lasgo',2,NULL,'MLG','0','supplier_list',NULL,'A',0,'No',NULL,NULL),
 (91,'Nacscorp',1,NULL,'NAC','0.00152','supplier_list',NULL,'B',399,'No','GBP/Gram',NULL),
 (93,'Follett',1,NULL,'FLT','0.00152','supplier_list',NULL,'B',399,'No','GBP/Gram',NULL),
 (92,'ThomasNelsonUK',1,NULL,'TUK','0','supplier_list',NULL,'B',0,'No',NULL,NULL),
 (94,'WHS',2,NULL,'WHS','0','supplier_list',NULL,'A',0,'No',NULL,NULL),
 (95,'Esdevium',2,NULL,'WOW','0','supplier_list',NULL,'A',0,'No',NULL,NULL),
 (96,'Quayside',1,NULL,'QUY','0.00152','supplier_list',NULL,'B',399,'No','GBP/Gram',NULL),
 (97,'HumanKinetics',2,NULL,'HKN','0','supplier_list',NULL,'A',0,'No',NULL,NULL),
 (99,'UniversalPictures',2,NULL,'MUP','0','supplier_list',NULL,'A',0,'No',NULL,NULL),
 (98,'MediaMoguls',2,NULL,'MOG','0','supplier_list',NULL,'A',0,'No',NULL,NULL),
 (100,'Informa',2,NULL,'INF','0','supplier_list',NULL,'A',0,'No',NULL,NULL),
 (101,'Brodart',1,NULL,'BRD','0.00152','supplier_list',NULL,'B',0,'No','GBP/Gram',NULL),
 (103,'BakerAndTaylorUK',2,NULL,'BTY','0','supplier_list',NULL,'A',0,'No',NULL,NULL),
 (102,'FaberMusic',2,NULL,'FBM','0','supplier_list',NULL,'A',0,'No',NULL,NULL),
 (104,'Agapea',3,NULL,'PEA','0.00071','supplier_list',NULL,'C',0,'No','GBP/Gram',NULL),
 (105,'IPG',1,NULL,'UGH','0.00152','supplier_list',NULL,'B',399,'No','GBP/Gram',NULL),
 (106,'Kingsway',2,NULL,'KIN','0','supplier_list',NULL,'A',0,'No',NULL,NULL),
 (107,'Oxbow',2,NULL,'OXB','0','supplier_list',NULL,'A',0,'No',NULL,NULL),
 (108,'LSIHardbacks',2,NULL,'LHB','0','supplier_list',NULL,'K',0,'No',NULL,NULL),
 (109,'Elkar',3,NULL,'ELK','0.00085','supplier_list',NULL,'C',0,'No','GBP/Gram',NULL),
 (110,'ElectricWord',2,NULL,'EWO','0','supplier_list',NULL,'A',0,'No',NULL,NULL),
 (111,'MachadoLibros',3,NULL,'MAC','0.00085','supplier_list',NULL,'C',0,'No','GBP/Gram',NULL),
 (112,'FastBooks',3,NULL,'FAS','0.00085','supplier_list',NULL,'C',0,'No','GBP/Gram',NULL),
 (113,'LetMePrint',2,NULL,'LMP','0','supplier_list',NULL,'B',0,'No',NULL,NULL),
 (114,'UDLLibros',3,NULL,'UDL','0.00085','supplier_list',NULL,'C',0,'No','GBP/Gram',NULL),
 (115,'Papasotiriou',3,NULL,'PAP','0.00085','supplier_list',NULL,'C',0,'No','GBP/Gram',NULL),
 (116,'LevantLebanon',2,NULL,'LVN','0.0012','bisupplier_list',NULL,'H',0,'No',NULL,NULL),
 (117,'Libroco',3,NULL,'LIO','0.00111','bisupplier_list',NULL,'C',0,'No',NULL,NULL),
 (118,'MSM',2,NULL,'MS2','0','supplier_list',NULL,'A',0,'No',NULL,NULL);
CREATE TABLE  `ebay`.`users` (
  `u_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) DEFAULT NULL,
  `last_name` varchar(25) DEFAULT NULL,
  `user_name` varchar(45) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(45) NOT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`u_id`),
  UNIQUE KEY `UN_UNQ` (`user_name`),
  UNIQUE KEY `EM_UNQ` (`email`),
  KEY `UT_FK` (`type`),
  CONSTRAINT `UT_FK` FOREIGN KEY (`type`) REFERENCES `valid_entry` (`ve_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
INSERT INTO `ebay`.`users` VALUES  (1,'tmiller','tmiller','tmiller','9efcfa8a6be1ac99e660e6b861156014','tomamiller@gmail.com','(+2)01069603439',1),
 (2,'Afaf','Hassan','afaf','827ccbeea8a706c4c34a16891f84e7b','Afaf.Hassan@bookdepository.co.uk','0120000000',2);
CREATE TABLE  `ebay`.`valid_entry` (
  `ve_id` int(11) NOT NULL,
  `ve_name` varchar(20) NOT NULL,
  `ve_category` int(11) NOT NULL,
  `ve_describtion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ve_id`),
  KEY `VET_FK` (`ve_category`),
  CONSTRAINT `VET_FK` FOREIGN KEY (`ve_category`) REFERENCES `valid_entry_types` (`vet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
INSERT INTO `ebay`.`valid_entry` VALUES  (1,'admin',1,'adminstrator'),
 (2,'user',1,'regular user'),
 (3,'Failure Login',2,'failure login try'),
 (4,'Successfull Login',2,'successfull login try'),
 (5,'Add',3,'Listing Add File'),
 (6,'Revise',3,'Listing Revise File'),
 (7,'End',3,'Listing End File'),
 (8,'Success',4,'uploading File Succeeded'),
 (9,'Failure',4,'Uploading File Failed'),
 (10,'InProcess',4,'Uploading the File Now'),
 (11,'Success',5,'File Downloaded Successfully'),
 (12,'Failure',5,'Failed to download the File'),
 (13,'InProcess',5,'Downloading File Now'),
 (14,'Inventory',6,'Inventory Report'),
 (15,'Solid',6,'Solid Report'),
 (16,'Loaded',7,'Report Loaded Successfully'),
 (17,'Not_Yet',7,'Failed to Load Report'),
 (18,'Loading',7,'Still Loading Report');
CREATE TABLE  `ebay`.`valid_entry_types` (
  `vet_id` int(11) NOT NULL,
  `vet_description` varchar(255) NOT NULL,
  PRIMARY KEY (`vet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
INSERT INTO `ebay`.`valid_entry_types` VALUES  (1,'user type'),
 (2,' user login status'),
 (3,'File Types'),
 (4,'Upload Result'),
 (5,'Download Result'),
 (6,'Report Types'),
 (7,'Load Report Result');



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
