drop table user1;
drop table role;
drop table task;
drop table user1_task;



create table role(
	rId INT NOT NULL,
	rName VARCHAR(255) NOT NULL,	
	PRIMARY KEY(rId), 
	constraint role_name_constraint check( rName IN ('EMPLOYEE','MANAGER','ADMIN'))
);

create table user1(
	usrId INT NOT NULL,
	usrName VARCHAR(255),
	usrEmail VARCHAR(255) unique,
	usrPhno VARCHAR(10),
	password VARCHAR(255),
	usrMId INT,	
	roleId INT NOT NULL,
	usrCurrentAdd VARCHAR(255),
	usrPermanentAdd VARCHAR(255),
	usrProfileImage VARCHAR(255),
	PRIMARY KEY (usrId),
	constraint che_man check( usrId <> usrMId),
	FOREIGN KEY(roleId) REFERENCES role(rId),
	FOREIGN KEY(usrMID) REFERENCES user1(usrId)
);



create table task
(
	tId INT NOT NULL,
	tName VARCHAR(255) NOT NULL,
	tOwner INT NOT NULL,
	tStatus VARCHAR(20) NOT NULL,
	tExpEff INT NOT NULL,
	tActEff INT,
	tAllDate TIMESTAMP,
	tCompDate TIMESTAMP,
	tDesc TINYTEXT,
	PRIMARY KEY(tId),
	FOREIGN KEY(tOwner) REFERENCES user1(usrId),
	constraint task_status_check check( tStatus IN('COMPLETED','ON_HOLD','IN_PROCESS','CANCELLED','NEW','PENDING_TO_VERFIFY'))		
);


create table user1_task 
(
	user1_usrId INT NOT NULL,
	task_tId INT NOT NULL,
	PRIMARY KEY (user1_usrId,task_tId)
);


insert into role values (1,'ADMIN');
insert into role values (2,'MANAGER');
insert into role values (3,'EMPLOYEE');

insert into user1 values (1045143,'Zoey Allen','allen.zoey@gmail.com','3456789023','12wertyu45678',null,3,'Banglore','Rajasthan',null);
insert into user1 values (1045145,'Girish meena','Girish.meena@gmail.com','1029384756','12wertyu',null,2,'Banglore','UP',null);
insert into user1 values (1045146,'Raja','S.Raja@gmail.com','1029384756','12wertyu',null,2,'Hyderabad','MP',null);
insert into user1 values (1045147,'Kisuke Urahara','UraharaKisuke@gmail.com','1029384756','12wertyu',null,2,'Kochi','Electronic city, Bangalore',null);
insert into user1 values (1045148,'Naruto Uzumaki','UzumakiN@gmail.com','1029384756','12wertyu',null,2,'Banglore','Noida, Delhi',null);
insert into user1 values (1045149,'Rukia Kuchiki','KRukia@gmail.com','1029384756','12wertyu',null,2,'Chennai','Kerala',null);
insert into user1 values (1045150,'Ichigo Kurusaki','IchigoSoulHunter@gmail.com','1029384756','12wertyu',null,2,'Pune','69th anand vihar road, Rajasthan',null);
insert into user1 values (1045151,'Ken Kaneki','EyePatch@gmail.com','1029384756','12wertyu',null,2,'7 hills, Hyderabad','Rajasthan',null);
insert into user1 values (1045152,'Juuzou Suzuya','SuzuyaJuzu@gmail.com','1029384756','12wertyu',null,2,'Banglore','15th cross hari priya, Kochi',null);
insert into user1 values (1045153,'Light Yagami','KiraYagami@gmail.com','1029384756','12wertyu',null,3,'Manglore','b-block, gargi, Jaipur',null);
insert into user1 values (1045154,'Toka ','Touka.Kirishima@gmail.com','1029384756','12wertyu',null,3,'Banglore','Maharastra',null);
insert into user1 values (1045155,'Ken Karnitez','Kkarnitez@gmail.com','1029384756','12wertyu',null,3,'Banglore','Mysore',null);
insert into user1 values (1045156,'Gitika Kumari','KumariGitika@gmail.com','1029384756','12wertyu',null,3,'Banglore','Gurgaon',null);
insert into user1 values (1045157,'Joey','Crazy.joey.335@gmail.com','1029384756','12wertyu',null,3,'Pune','Rajasthan',null);
insert into user1 values (1045158,'Ankit Bhardwaj','Sunshine.0987@gmail.com','1029384756','12wertyu',null,3,'Chennai','Rajasthan',null);
insert into user1 values (1045159,'Deepak Yadav','Y1234.Deepak@gmail.com','1029384756','12wertyu',null,3,'Nepal','Jammu & Kashmir',null);
insert into user1 values (1045144,'Manisha Meena','meenamanisha337@gmail.com','3456789023','12345678',null,1,'Bangalore','Rajasthan',null);

