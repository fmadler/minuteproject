use buslogic_intro;

INSERT INTO `customer` VALUES 
	('Gloria\'s Garden','0.0000','910.0000','Posies','G','',0),
	('Hibernating Bears','100.0000','100000.0000','Bears','S','\0',2),
	('Max Air','100.0000','100000.0000','Max','N','\0',2),
	('Shari\'s Shangri La','0.0000','0.0000','Shari','G','',0);


INSERT INTO `product` VALUES 
	('Hammer','10.0000',0,4,100,'\0',0,'0.0000','\0',1,''),
	('Shovel','20.0000',0,8,200,'\0',0,'0.0000','\0',2,''),
	('Dynamite','500.0000',0,0,500,'\0',0,'0.0000','',5,''),
	('Boing 747X','0.0000',0,0,300,'\0',0,'0.0000','\0',10,''),
	('WingX','0.0000',2,0,10,'\0',0,'00.0000','\0',11,NULL),
	('Boing 747','10300.0000',0,0,300,'\0',3,'10300.0000','\0',12,''),
	('Fuselage','1300.0000',4,0,5,'\0',0,'0.0000','\0',13,NULL),
	('Wing','4000.0000',2,0,10,'\0',2,'4000.000','\0',14,NULL),
	('Engine','1500.0000',3,0,20,'\0',0,'0.0000','\0',15,NULL),
	('Bolt','10.0000',4000,0,10000,'\0',0,'0.0000','\0',16,NULL);

INSERT INTO `product_billofmaterials` VALUES 
	(12, 13, 'Boing 747','Fuselage',1300,1,1300),	
	(12, 14, 'Boing 747','Wing',4000,2,8000),	
	(12, 16, 'Boing 747','Bolt',10,100,1000),	
	(14, 15, 'Wing','Engine',1500,2,3000),	
	(14, 16, 'Wing','Bolt',10,100,1000);
	
INSERT INTO `employee` VALUES 
	('Lt Kiji','150000.0000', NULL,NULL,NULL,'exempt'),
	('A. Lincoln','250000.0000', NULL,NULL,NULL,'exempt'),
	('M. Ghandi','250000.0000', NULL,NULL,NULL,'exempt'),
	('M. King','250000.0000', NULL,NULL,NULL,'exempt'),
	('Willie Loman','40000.0000',NULL,NULL,NULL,'salesrep');
	
	
INSERT INTO `purchaseorder` VALUES 
	(1,'50.0000','50.0000','0.0000','50.0000','\0','G PO.1','',0,'2010-11-27','2010-10-28',NULL,'Gloria\'s Garden',0,2,NULL),
	(2,'50.0000','50.0000','0.0000','50.0000',1,'M PO.1 =2','',0,'2010-11-27','2010-11-28',NULL,'Max Air',0,2,NULL),
	(3,'50.0000','50.0000','0.0000','50.0000',1,'M PO.2 =3','',0,'2010-11-27','2010-11-28',NULL,'Max Air',0,2,NULL),
	(4,'50.0000','50.0000','0.0000','50.0000',1,'G PO.1','',0,'2010-11-27','2010-12-28',NULL,'Hibernating Bears',0,2,NULL),
	(5,'50.0000','50.0000','0.0000','50.0000',1,'G PO.1','',0,'2010-11-27','2010-12-28',NULL,'Hibernating Bears',0,2,NULL);
	
	
	
INSERT INTO `lineitem` VALUES 
	(1,1,1,'10.0000','10.0000',0,0,0,NULL,NULL,'Part 1, Order 1: 1',0,1,NULL),
	(2,1,2,'20.0000','40.0000',0,0,0,NULL,NULL,'Part 2, Order 2: 2',0,2,NULL),

	(1,2,1,'10.0000','10.0000',0,0,0,NULL,NULL,'Part 1, Order 2: 1',1,3,NULL),
	(2,2,2,'20.0000','40.0000',0,0,0,NULL,NULL,'Part 2, Order 2: 2',1,4,NULL),

	(1,3,1,'10.0000','10.0000',0,0,0,NULL,NULL,'Part 1, Order 3: 1',1,5,NULL),
	(2,3,2,'20.0000','40.0000',0,0,0,NULL,NULL,'Part 2, Order 3: 2',1,6,NULL),
	
	(1,4,1,'10.0000','10.0000',0,0,0,NULL,NULL,'Part 1, Order 1: 1',1,7,NULL),
	(2,4,2,'20.0000','40.0000',0,0,0,NULL,NULL,'Part 2, Order 2: 2',1,8,NULL),
	
	(1,5,1,'10.0000','10.0000',0,0,0,NULL,NULL,'Part 1, Order 1: 1',1,9,NULL),
	(2,5,2,'20.0000','40.0000',0,0,0,NULL,NULL,'Part 2, Order 2: 2',1,10,NULL);


INSERT INTO `lineitem_note` VALUES 
	(1,1,'Pre-loaded Note');

	
INSERT INTO `lineitem_usage` VALUES 
	('Not to be taken internally',1,1,1);
	
	
INSERT INTO `valid_customerlevel` VALUES 
	('G','Gold'),
	('N','Normal'),
	('S','Silver');
	
	
	