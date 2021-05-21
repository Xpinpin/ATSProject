SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

DROP DATABASE IF EXISTS `ats2021mike`;
CREATE DATABASE IF NOT EXISTS `ats2021mike` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `ats2021mike`;

CREATE TABLE `Clients` (
  `ClientId` int(11) NOT NULL,
  `Name` nvarchar(50) NOT NULL,
  `Address` nvarchar(150) NOT NULL,
  `EmailAddress` nvarchar(150) NOT NULL,
  `PhoneNumber` nvarchar(20) NOT NULL,
  `CreatedAt` datetime NOT NULL,
  `UpdatedAt` datetime DEFAULT NULL,
  `DeletedAt` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Employees` (
  `EmployeeId` int(11) NOT NULL,
  `FirstName` nvarchar(30) NOT NULL,
  `LastName` nvarchar(30) NOT NULL,
  `SIN` nvarchar(9) NOT NULL,
  `HourlyRate` decimal(13,2) NOT NULL,
  `IsDeleted` bit(1) NOT NULL,
  `CreatedAt` datetime NOT NULL,
  `UpdatedAt` datetime DEFAULT NULL,
  `DeletedAt` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `EmployeesTasks` (
  `EmployeeId` int(11) NOT NULL,
  `TaskId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Jobs` (
  `JobId` int(11) NOT NULL,
  `TeamId` int(11) NOT NULL,
  `ClientId` int(11) NOT NULL,
  `Description` nvarchar(100) NOT NULL,
  `Start` datetime NOT NULL,
  `End` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `JobsTasks` (
  `TaskId` int(11) NOT NULL,
  `JobId` int(11) NOT NULL,
  `OperatingCost` decimal(13,2) NOT NULL,
  `OperatingRevenue` decimal(13,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `JobComments` (
  `CommentId` int(11) NOT NULL,
  `JobId` int(11) NOT NULL,
  `Comment` nvarchar(250) NOT NULL,
  `CreatedAt` datetime NOT NULL,
  `UpdatedAt` datetime DEFAULT NULL,
  `DeletedAt` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Tasks` (
  `TaskId` int(11) NOT NULL,
  `Name` nvarchar(50) NOT NULL,
  `Description` nvarchar(50) NOT NULL,
  `Duration` int(11) NOT NULL,
  `CreatedAt` datetime NOT NULL,
  `UpdatedAt` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Teams` (
  `TeamId` int(11) NOT NULL,
  `Name` nvarchar(50) NOT NULL,
  `IsOnCall` bit(1) NOT NULL,
  `IsDeleted` bit(1) NOT NULL,
  `CreatedAt` datetime NOT NULL,
  `UpdatedAt` datetime DEFAULT NULL,
  `DeletedAt` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `TeamMembers` (
  `EmployeeId` int(11) NOT NULL,
  `TeamId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE `Clients`
  ADD PRIMARY KEY (`ClientId`);

ALTER TABLE `Employees`
  ADD PRIMARY KEY (`EmployeeId`);

ALTER TABLE `EmployeesTasks`
  ADD PRIMARY KEY (`EmployeeId`,`TaskId`),
  ADD KEY `task_employees_tasks` (`TaskId`);

ALTER TABLE `Jobs`
  ADD PRIMARY KEY (`JobId`),
  ADD KEY `teams_jobs` (`TeamId`),
  ADD KEY `clients_jobs` (`ClientId`);

ALTER TABLE `JobsTasks`
  ADD PRIMARY KEY (`TaskId`,`JobId`),
  ADD KEY `jobs_jobs_tasks` (`JobId`);

ALTER TABLE `JobComments`
  ADD PRIMARY KEY (`CommentId`),
  ADD KEY `jobs_job_comments` (`JobId`);

ALTER TABLE `Tasks`
  ADD PRIMARY KEY (`TaskId`);

ALTER TABLE `Teams`
  ADD PRIMARY KEY (`TeamId`);

ALTER TABLE `TeamMembers`
  ADD PRIMARY KEY (`EmployeeId`,`TeamId`),
  ADD KEY `teams_team_members` (`TeamId`);


ALTER TABLE `Clients`
  MODIFY `ClientId` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `Employees`
  MODIFY `EmployeeId` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `Jobs`
  MODIFY `JobId` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `JobComments`
  MODIFY `CommentId` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `Teams`
  MODIFY `TeamId` int(11) NOT NULL AUTO_INCREMENT;
  
ALTER TABLE `Tasks`
  MODIFY `TaskId` int(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `EmployeesTasks`
  ADD CONSTRAINT `employee_employees_tasks` FOREIGN KEY (`EmployeeId`) REFERENCES `Employees` (`EmployeeId`),
  ADD CONSTRAINT `task_employees_tasks` FOREIGN KEY (`TaskId`) REFERENCES `Tasks` (`TaskId`);

ALTER TABLE `Jobs`
  ADD CONSTRAINT `ClientsJobs` FOREIGN KEY (`ClientId`) REFERENCES `Clients` (`ClientId`),
  ADD CONSTRAINT `TeamsJobs` FOREIGN KEY (`TeamId`) REFERENCES `Teams` (`TeamId`);

ALTER TABLE `JobsTasks`
  ADD CONSTRAINT `jobs_jobs_tasks` FOREIGN KEY (`JobId`) REFERENCES `Jobs` (`JobId`),
  ADD CONSTRAINT `tasks_jobs_tasks` FOREIGN KEY (`TaskId`) REFERENCES `Tasks` (`TaskId`);

ALTER TABLE `JobComments`
  ADD CONSTRAINT `jobs_job_comments` FOREIGN KEY (`JobId`) REFERENCES `Jobs` (`JobId`);

ALTER TABLE `TeamMembers`
  ADD CONSTRAINT `employees_team_members` FOREIGN KEY (`EmployeeId`) REFERENCES `Employees` (`EmployeeId`),
  ADD CONSTRAINT `teams_team_members` FOREIGN KEY (`TeamId`) REFERENCES `Teams` (`TeamId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

--------------
/* Employee Store Proc */
DELIMITER //
DROP PROCEDURE IF EXISTS InsertEmployee
// DELIMITER;

DELIMITER //
CREATE DEFINER=`dev`@`localhost` PROCEDURE InsertEmployee(

	IN FirstName_param VARCHAR(30),
    IN LastName_param  VARCHAR(30),
    IN SIN_param VARCHAR(9),
    IN HourlyRate_param DECIMAL(13,2),
    
    OUT Id_out INT
)	
BEGIN
	INSERT INTO employees(FirstName, LastName, SIN, HourlyRate, IsDeleted, CreatedAt)
    VALUES(FirstName_param,LastName_param, SIN_param, HourlyRate_param, 1, (SELECT NOW()));

	SET Id_out = LAST_INSERT_ID();
END // 
DELIMITER ;


DELIMITER //
DROP PROCEDURE IF EXISTS ShowEmployees
// DELIMITER;
DELIMITER //
CREATE DEFINER=`dev`@`localhost` PROCEDURE ShowEmployees()
BEGIN
	SELECT * FROM employees
    ORDER BY FirstName;

END //
DELIMITER ;	

DELIMITER //
DROP PROCEDURE IF EXISTS ShowEmployee
// DELIMITER;
DELIMITER //
CREATE DEFINER=`dev`@`localhost` PROCEDURE ShowEmployee(
	IN Id_param INT
)
BEGIN
	SELECT * FROM employees
    WHERE (Id_param IS NULL OR EmployeeId = Id_param)
    ORDER BY FirstName;

END //
DELIMITER ;	

DELIMITER //
DROP PROCEDURE IF EXISTS ShowEmployeesNotAssignTeam
// DELIMITER;
DELIMITER //
CREATE DEFINER=`dev`@`localhost` PROCEDURE ShowEmployeesNotAssignTeam()
BEGIN
	SELECT * FROM employees
    WHERE EmployeeId NOT IN
(
	SELECT employees.EmployeeId FROM employees
     INNER JOIN teammembers ON employees.EmployeeId = teammembers.EmployeeId
     )
    ORDER BY FirstName;

END //
DELIMITER ;	


DELIMITER //
DROP PROCEDURE IF EXISTS ShowEmployeeTasks
// DELIMITER;
DELIMITER //
CREATE DEFINER=`dev`@`localhost` PROCEDURE ShowEmployeeTasks(
	IN Id_param INT
)
BEGIN
	SELECT * FROM employees
    INNER JOIN employeestasks ON employees.EmployeeId = employeestasks.EmployeeId
    INNER JOIN tasks ON employeestasks.TaskId = tasks.TaskId
    WHERE (Id_param IS NULL OR employees.EmployeeId = Id_param)
    ORDER BY FirstName;

END //
DELIMITER ;	

DELIMITER //
DROP PROCEDURE IF EXISTS ShowEmployeeTeam
// DELIMITER;
DELIMITER //
CREATE DEFINER=`dev`@`localhost` PROCEDURE ShowEmployeeTeam(
	IN Id_param INT
)
BEGIN
	SELECT * FROM employees
    INNER JOIN teammembers ON employees.EmployeeId = teammembers.EmployeeId
    INNER JOIN teams ON teammembers.TeamId = teams.TeamId
    WHERE (Id_param IS NULL OR employees.EmployeeId = Id_param)
    ORDER BY FirstName;

END //
DELIMITER ;	

DELIMITER //
	DROP PROCEDURE IF EXISTS DeleteEmployee
	// DELIMITER;

	DELIMITER //
	CREATE DEFINER=`dev`@`localhost` PROCEDURE DeleteEmployee (
	IN Id_param INT
	)
	BEGIN
		
		IF EXISTS (SELECT * FROM teammembers WHERE EmployeeId = Id_param)  THEN
			/*When the employee is a team */

			UPDATE employees
			SET IsDeleted = 1,
			DeletedAt = (SELECT NOW())
			WHERE EmployeeId = Id_param;
		
		ELSEIF EXISTS(SELECT * FROM employeestasks WHERE EmployeeId = Id_param) THEN
		
			Delete FROM employeestasks WHERE EmployeeId = Id_param;
			DELETE FROM employees WHERE EmployeeId = Id_param;
		
		ELSE
		/*When the employee is not in any teams */
		
			DELETE FROM employees WHERE EmployeeId = Id_param;

		END IF;
		
	END//
	DELIMITER ;


DELIMITER //
DROP PROCEDURE IF EXISTS ShowEmployeesBySearch
// DELIMITER;

DELIMITER //
CREATE DEFINER=`dev`@`localhost` PROCEDURE ShowEmployeesBySearch(
	IN SearchString_param VARCHAR(50)
)
BEGIN
	SELECT * FROM employees
	WHERE (SearchString_param IS NULL OR 
    LastName LIKE concat("%",SearchString_param, "%") OR 
    SIN LIKE concat("%",SearchString_param, "%"))
    ORDER BY LastName;
END //
DELIMITER ;	


DELIMITER //
DROP PROCEDURE IF EXISTS EmployeeAvailableSkills
// DELIMITER;

DELIMITER //
CREATE DEFINER=`dev`@`localhost` PROCEDURE `EmployeeAvailableSkills`(IN Id_param INT)
BEGIN
	SELECT * FROM Tasks WHERE
	TaskId NOT IN (
    SELECT tasks.TaskId FROM tasks 
INNER JOIN employeestasks ON tasks.TaskId = employeestasks.TaskId
WHERE employeestasks.EmployeeId = Id_param );
END // 
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS DeleteTask
// DELIMITER;

DELIMITER //
CREATE DEFINER=`dev`@`localhost` PROCEDURE `DeleteTask`(
IN Id_param INT
)
BEGIN
	DELETE FROM tasks
	WHERE TaskId = Id_param;
END // 
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS EmployeeWithTask
// DELIMITER;

DELIMITER //
CREATE DEFINER=`dev`@`localhost` PROCEDURE `EmployeeWithTask`(
IN Id_param INT
)
BEGIN
	SELECT COUNT(*) FROM employeestasks
    WHERE TaskId = Id_param;
END // 
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS JobWithTask
// DELIMITER;

DELIMITER //
CREATE DEFINER=`dev`@`localhost` PROCEDURE `JobWithTask`(
IN Id_param INT
)
BEGIN
	SELECT COUNT(*) FROM jobstasks
    WHERE TaskId = Id_param;
END // 
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS DeleteJob
// DELIMITER;

DELIMITER //
CREATE DEFINER=`dev`@`localhost` PROCEDURE `DeleteJob`(
IN Id_param INT
)
BEGIN
	DELETE FROM jobstasks
    WHERE JobId = Id_param;
    
	DELETE FROM jobs
    WHERE JobId = Id_param;
END // 
DELIMITER ;


--------------
/* Client Store Proc */
DELIMITER //
DROP PROCEDURE IF EXISTS InsertClient
// DELIMITER;
DELIMITER //
CREATE DEFINER=`dev`@localhost PROCEDURE InsertClient(
	IN Name_param VARCHAR(50),
    IN Address_param  VARCHAR(150),
	IN EmailAddress_param  VARCHAR(100),
	IN PhoneNumber_param  VARCHAR(20),
    OUT Id_out INT
)	
BEGIN
	INSERT INTO ats2021mike.clients
		(
		Name,
		Address,
        EmailAddress,
        PhoneNumber,
		CreatedAt)
	VALUES
		(
		Name_param,
		Address_param,
        EmailAddress_param,
        PhoneNumber_param,
		(SELECT NOW()));
	SET Id_out = LAST_INSERT_ID();
END //
DELIMITER ;
DELIMITER //
DROP PROCEDURE IF EXISTS UpdateClient
// DELIMITER;
DELIMITER //
CREATE DEFINER=`dev`@localhost PROCEDURE UpdateClient(
	IN ClientId_param INT,
	IN Name_param VARCHAR(50),
    IN Address_param  VARCHAR(150),
    IN CreateAt_param DATETIME,
	IN UpdatedAt_param DATETIME,
	IN DeletedAt_param DATETIME
)	
BEGIN
	UPDATE ats2021mike.clients
	SET
	ClientId = ClientId_param,
	Name = Name_param,
	Address = Address_param,
	CreatedAt = CreateAt_param ,
	UpdatedAt = UpdatedAt_param,
	DeletedAt = DeletedAt_param
	WHERE ClientId = ClientId_param;
END //
DELIMITER ;
DELIMITER //
DROP PROCEDURE IF EXISTS ShowClient
// DELIMITER;
DELIMITER //
CREATE DEFINER=`dev`@localhost PROCEDURE ShowClient(
	IN Id_param INT
)
BEGIN
	SELECT * FROM clients
    WHERE (Id_param IS NULL OR ClientId = Id_param)
    ORDER BY Name;
END //
DELIMITER ;	
DELIMITER //
DROP PROCEDURE IF EXISTS ShowClients
// DELIMITER;
DELIMITER //
CREATE DEFINER=`dev`@localhost PROCEDURE ShowClients(
	IN SearchString_param VARCHAR(50)
)
BEGIN
	SELECT * FROM clients
	WHERE (SearchString_param IS NULL OR Name LIKE concat("%",SearchString_param, "%"))
    ORDER BY Name;
END //
DELIMITER ;	

DELIMITER //
DROP PROCEDURE IF EXISTS ShowAllClients
// DELIMITER;
DELIMITER //
CREATE DEFINER=`dev`@localhost PROCEDURE ShowAllClients(
)
BEGIN
	SELECT * FROM clients
    ORDER BY Name;
END //
DELIMITER ;	



----------------------
/* Task Store Proc */

DELIMITER //
DROP PROCEDURE IF EXISTS InsertTask
// DELIMITER;

DELIMITER //
CREATE DEFINER=`dev`@`localhost` PROCEDURE `InsertTask`(
	IN Name_param nvarchar(50),
	IN Description_param nvarchar(50),
	IN Duration_param int(11),
    
    OUT Id_out INT
)
BEGIN
	Insert Into `tasks`(Name, Description, Duration, CreatedAt)
    Values(Name_param, Description_param, Duration_param, (SELECT NOW()));
    SET Id_out = LAST_INSERT_ID();
END //
DELIMITER ;	

DELIMITER //
DROP PROCEDURE IF EXISTS ListTasks
// DELIMITER;
DELIMITER //
CREATE DEFINER=`dev`@`localhost` PROCEDURE `ListTasks`(
IN Id_Param INT
)
BEGIN
	SELECT * FROM Tasks
		WHERE (Id_Param IS NULL
        OR TaskId = Id_Param)
	ORDER BY
		Name;
END //
DELIMITER ;	


DELIMITER //
DROP PROCEDURE IF EXISTS ShowTask
// DELIMITER;
DELIMITER //
CREATE DEFINER=`dev`@`localhost` PROCEDURE `ShowTask`(
IN id_param INT
)
BEGIN
	SELECT *  FROM Tasks
	WHERE
		TaskId = id_param;
END //
DELIMITER ;	

--------------
/* Team Store Proc UpdatedAtCreatedAt */

DELIMITER //
DROP PROCEDURE IF EXISTS CreateTeam
// DELIMITER;

DELIMITER //
CREATE DEFINER=`dev`@`localhost` PROCEDURE CreateTeam(

	IN Name_param VARCHAR(50),
    IN IsOnCall_param  BIT(1),
 
	IN EmployeeId_param INT,
    IN TeamId_param  INT,
    
    OUT Id_out INT
)	
BEGIN

IF NOT EXISTS(SELECT TeamId from teams WHERE TeamId = TeamId_param) 
THEN
	INSERT INTO teams(Name, IsOnCall, IsDeleted, CreatedAt)
    VALUES(Name_param,IsOnCall_param, 1, (SELECT NOW()));

	SET TeamId_param = LAST_INSERT_ID();

END IF;

	INSERT INTO teammembers(EmployeeId, TeamId)
    VALUES(EmployeeId_param, TeamId_param);

	SET Id_out = TeamId_param;
END // 
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS ShowAllTeams
// DELIMITER;

DELIMITER //
CREATE DEFINER=`dev`@`localhost` PROCEDURE ShowAllTeams(
)	
BEGIN
	SELECT * FROM teams;
END // 
DELIMITER ;

USE ATS2021mike
DELIMITER //
DROP PROCEDURE IF EXISTS ShowTeam
// DELIMITER;

DELIMITER //
CREATE DEFINER=`dev`@`localhost` PROCEDURE ShowTeam(
    IN TeamId_param  INT
)	
BEGIN
	SELECT * FROM teams WHERE Teamid = TeamId_param;
END // 
DELIMITER ;


/* Jobs Store Proc */
Use ats2021mike
DELIMITER //
DROP PROCEDURE IF EXISTS InsertJob
// DELIMITER;

DELIMITER //
CREATE DEFINER=`dev`@`localhost` PROCEDURE `InsertJob`(
	IN TeamId_param int(11),
	IN ClientId_param int(11),
	IN Description_param nvarchar(100),
	IN Start_param nvarchar(30),
	IN End_param nvarchar(30),
    IN IsOnSite_param tinyint(1),
    
    OUT Id_out INT
)
BEGIN
		INSERT INTO `jobs`
		(
		`TeamId`,
		`ClientId`,
		`Description`,
		`Start`,
		`End`,
       `OnSite`)
		VALUES
		(
		TeamId_param,
		ClientId_param ,
		Description_param,
		Start_param,
		End_param,
        IsOnSite_param) ;

    SET Id_out = LAST_INSERT_ID();
END //
DELIMITER ;	

DELIMITER //
DROP PROCEDURE IF EXISTS InsertJobTask
// DELIMITER;

DELIMITER //
CREATE DEFINER=`dev`@`localhost` PROCEDURE `InsertJobTask`(
	IN TaskId_param int(11),
	IN JobId_param int(11),
	IN OpCost_param decimal(13,2),
	IN OpRevenue_param decimal(13,2),
    
    OUT Id_out INT
)
BEGIN
		INSERT INTO `ats2021mike`.`jobstasks`
			(`TaskId`,
			`JobId`,
			`OperatingCost`,
			`OperatingRevenue`)
			VALUES
			(TaskId_param,
			 JobId_param,
			 OpCost_param,
			 OpRevenue_param);

    SET Id_out = LAST_INSERT_ID();
END //
DELIMITER ;	

Use ATS2021Mike
DELIMITER //
DROP PROCEDURE IF EXISTS SearchJobByDate
// DELIMITER;
CREATE DEFINER=`dev`@`localhost` PROCEDURE `SearchJobByDate`(
	IN SearchDate_param varchar(30)
)
BEGIN
	 SELECT *
     FROM jobs
     Inner Join teams ON jobs.TeamId = teams.TeamId
     WHERE start BETWEEN concat(SearchDate_param, ' 00:00:00' ) AND  concat(SearchDate_param, ' 23:59:59' );
     
END//

Use ATS2021Mike
DELIMITER //
DROP PROCEDURE IF EXISTS ShowTasksJob
// DELIMITER;

DELIMITER //
CREATE DEFINER=`dev`@`localhost` PROCEDURE `ShowTasksJob`(
	IN JobId_param int(11)
)
BEGIN
	 SELECT jobstasks.TaskId, jobId, OperatingCost, OperatingRevenue
     FROM jobstasks
     INNER JOIN tasks ON tasks.TaskId = jobstasks.TaskId
     WHERE jobid = JobId_param; 
     
END//

Use ATS2021Mike
DELIMITER //
DROP PROCEDURE IF EXISTS ShowJob
// DELIMITER;

DELIMITER //
CREATE DEFINER=`dev`@`localhost` PROCEDURE `ShowJob`(
	IN JobId_param int(11)

)
BEGIN
	 SELECT *
     FROM jobs
     Inner Join jobstasks On jobstasks.jobId = jobs.jobId
     WHERE jobId  = JobId_param;
     
     
END //

USE ats2020mike
DELIMITER //
DROP PROCEDURE IF EXISTS ShowTeamsWithTasks
// DELIMITER;

DELIMITER //
CREATE DEFINER=`dev`@`localhost` PROCEDURE `ShowTeamsWithTasks`(
	IN TasksId_param int(11)

)
BEGIN
SELECT Teams.TeamID, Teams.Name FROM TEAMS
	WHERE 
        TeamId  IN
        (
			SELECT Teams.TeamId
            FROM Teams
            INNER JOIN TeamMembers ON TeamMembers.TeamId = Teams.TeamId
            INNER JOIN Employeestasks On TeamMembers.EmployeeId = Employeestasks.EmployeeId
            WHERE Employeestasks.TaskId = TasksId_param
        );
     
END //

DELIMITER //
DROP PROCEDURE IF EXISTS CheckTeamAvailability
// DELIMITER;

DELIMITER //
CREATE DEFINER=`dev`@`localhost` PROCEDURE `CheckTeamAvailability`(
	IN TasksId_param int(11),
	IN Start_param datetime,
	IN End_param datetime 

)
BEGIN
SELECT Count(TeamId) FROM TEAMS
	WHERE TeamId NOT IN
		(
			SELECT	TeamId
			FROM	Jobs
			WHERE	(Start <= Start_param AND End >= Start_param)
					OR (Start < End_param AND End >= End_param)
					OR (Start_param <= Start AND End_param >= Start)
		)
     
     
END //

DELIMITER //
DROP PROCEDURE IF EXISTS ShowTeamMembers
// DELIMITER;

DELIMITER //
CREATE DEFINER=`dev`@`localhost` PROCEDURE `ShowTeamMembers`(
	IN TeamId_param int(11)

)
BEGIN

Select employeeid from teammembers where teamid = TeamId_param;
     
END //


/* Seed Data */

 INSERT INTO `tasks` (Name, Description, Duration, CreatedAt)
							VALUES('Router Configuration', 'Set up a network connection on host computers.', 45, (SELECT NOW())),
							('Server OS Support', 'Physical Hardware & Software Management 24x7.', 60, (SELECT NOW())),
                            ('Switch Configuration', 'Preparing a switch for remote management access.', 75, (SELECT NOW())),
                            ('Server OS Installations', 'Installing UNIX Operating System on all machines.', 90, (SELECT NOW()));

 
 INSERT INTO `employees`(FirstName, LastName, SIN, HourlyRate, IsDeleted, CreatedAt)
							VALUES('Jon', 'Snow', '123456789', 44.00, 0, (SELECT NOW())),
							('Tormund', 'Giantsbane', '123456788', 34.00, 0, (SELECT NOW())),
                            ('Tyrion', 'Lannister', '123456787', 78.00, 0, (SELECT NOW())),
                            ('Arya', 'Stark', '123456787', 55.00, 0, (SELECT NOW()));
                           
 
 INSERT INTO `clients`(`Name`,`Address`,`EmailAddress`, `PhoneNumber`,`CreatedAt`)
							VALUES('Youth Impact Jeunesse Inc.', '536 Mountain Rd, Moncton, NB E1C 2N5','youthimpact@email.com', '506-789-3328', (SELECT NOW())),
							('GreenLight Property Management', '80 Mapleton Rd Suite 205, Moncton, NB E1C 7W8', 'greenlight@email.com', '506-123-4583', (SELECT NOW())),
                            ('Penniac Construction Ltd', '347 Mountain Rd Suite 200, Moncton, NB E1C 2M7', 'penniacconst@email.com', '506-123-7894', (SELECT NOW())),
                            ('River City Moving ltd', '180 Beaverbrook St, Moncton, NB E1C 9S5', 'rivercity@email.com', '506-178-2365', (SELECT NOW()));

INSERT INTO `employeestasks`(`EmployeeId`, `TaskId` )
							VALUES(1,1),
								  (1,3),
                                  (2,3),
                                  (4,2);
 