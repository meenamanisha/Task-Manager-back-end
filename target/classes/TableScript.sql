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
	PRIMARY KEY (usrId),
	constraint che_man check( usrId <> usrMId),
	FOREIGN KEY(roleId) REFERENCES role(rId)
);



create table task
(
	tId INT NOT NULL,
	tName VARCHAR(255) NOT NULL,
	tOwner VARCHAR(255) NOT NULL,
	tStatus VARCHAR(20) NOT NULL,
	tExpEff INT NOT NULL,
	tActEff INT,
	tAllDate TIMESTAMP,
	tCompDate TIMESTAMP,
	PRIMARY KEY(tId),
	constraint task_status_check check( tStatus IN('Completed','New','In Progress','On Hold','Cancelled'))		
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

insert into user1 values (1,'manisha meena','manisha@gmail.com','1234567890','123',1054152,1,'Banglore','Rajasthsn');