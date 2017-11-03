DROP TABLE IF EXISTS users, project, systemwater; 
DROP SEQUENCE IF EXISTS users_sq;
CREATE SEQUENCE IF NOT EXISTS users_sq;

CREATE TABLE IF NOT EXISTS users (
  id INTEGER PRIMARY KEY DEFAULT nextval('users_sq'), 
  login varchar(25), 
  pass varchar(32)
);
ALTER SEQUENCE users_sq OWNED BY users.id;

INSERT INTO USERS (login, pass) values ('admin', 'E8A48653851E28C69D0506508FB27FC5');-- postgres
INSERT INTO USERS (login, pass) values ('ivanov', '4DFE6E220D16E7B633CFDD92BCC8050B');-- ivanov


CREATE SEQUENCE IF NOT EXISTS project_sq;

CREATE TABLE IF NOT EXISTS project(
  id INTEGER PRIMARY KEY DEFAULT nextval('project_sq'),
  name VARCHAR(100),
  tempT1summer INTEGER ,
  tempT2summer INTEGER,
  tempT1winter INTEGER ,
  tempT2winter INTEGER,
  pressure1 DECIMAL,
  pressure2 DECIMAL,
  checkPipe DECIMAL,
  checkEquipment DECIMAL
);
ALTER SEQUENCE project_sq OWNED BY project.id;

INSERT INTO project (name, tempT1summer, tempT2summer, tempT1winter, tempT2winter, pressure1, pressure2, checkPipe, checkEquipment) values ('Стадион','70','40','130','70','9.4','5.2','1','1.15');
INSERT INTO project (name, tempT1summer, tempT2summer, tempT1winter, tempT2winter, pressure1, pressure2, checkPipe, checkEquipment) values ('Вокзал','70','40','130','70','8.4','4.0','1.15','1.15');
INSERT INTO project (name, tempT1summer, tempT2summer, tempT1winter, tempT2winter, pressure1, pressure2, checkPipe, checkEquipment) values ('Магазин','70','40','130','70','11.5','3.2','1','1.15');
INSERT INTO project (name, tempT1summer, tempT2summer, tempT1winter, tempT2winter, pressure1, pressure2, checkPipe, checkEquipment) values ('Жилой дом','70','40','130','70','5.2','2.2','1','1.15');
INSERT INTO project (name, tempT1summer, tempT2summer, tempT1winter, tempT2winter, pressure1, pressure2, checkPipe, checkEquipment) values ('Гараж','70','40','130','70','5.0','2.6','1','1.15');


CREATE TABLE IF NOT EXISTS systems(
  id INT,
  name varchar(15)
 ); 

INSERT INTO systems (id, name) values (1,'Отопление');
INSERT INTO systems (id, name) values (2,'Вентиляция');
INSERT INTO systems (id, name) values (3,'ГВС');

CREATE SEQUENCE IF NOT EXISTS systemwater_sq;

CREATE TABLE IF NOT EXISTS systemwater (
  id INTEGER PRIMARY KEY DEFAULT nextval('systemwater_sq'),
  project_id INTEGER,
  systems_id INTEGER,
  countHeat DECIMAL,
  tempT11 INTEGER,
  tempT21 INTEGER,
  heightSyst DECIMAL,
  dH INTEGER,
  maxV DECIMAL,
  maxH DECIMAL,
  countGlikol INTEGER
 );
ALTER SEQUENCE systemwater_sq OWNED BY systemwater.id;

INSERT INTO systemwater (/* project_id, systems_id,*/ countHeat, tempT11, tempT21, heightSyst, dH, maxV, maxH, countGlikol) values (/*'1','2',*/'1.5','95','70','20','10','1','10','0');
INSERT INTO systemwater (/* project_id, systems_id,*/ countHeat, tempT11, tempT21, heightSyst, dH, maxV, maxH, countGlikol) values (/*'1','2',*/'1.5','85','60','10','8','1','10','0');
INSERT INTO systemwater (/* project_id, systems_id,*/ countHeat, tempT11, tempT21, heightSyst, dH, maxV, maxH, countGlikol) values (/*'1','2',*/'1.5','90','70','20','15','1','10','0');
INSERT INTO systemwater (/* project_id, systems_id,*/ countHeat, tempT11, tempT21, heightSyst, dH, maxV, maxH, countGlikol) values (/*'1','2',*/'1.5','95','60','20','12','1','10','0');

SELECT * FROM project;
/*SELECT * FROM systemwater;*/