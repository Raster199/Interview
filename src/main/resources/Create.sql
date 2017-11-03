DROP TABLE IF EXISTS users; 
DROP SEQUENCE IF EXISTS users_sq;
CREATE SEQUENCE IF NOT EXISTS users_sq;

CREATE TABLE IF NOT EXISTS users (
id INTEGER PRIMARY KEY DEFAULT nextval('users_sq'), login varchar(25), pass varchar(32));
ALTER SEQUENCE users_sq OWNED BY users.id;

INSERT INTO USERS (login, pass) values ('admin', 'E8A48653851E28C69D0506508FB27FC5');-- postgres
INSERT INTO USERS (login, pass) values ('ivanov', '4DFE6E220D16E7B633CFDD92BCC8050B');-- ivanov

CREATE SEQUENCE IF NOT EXISTS networkwater_sq;

CREATE TABLE IF NOT EXISTS networkwater (
id INTEGER PRIMARY KEY DEFAULT nextval('networkwater_sq'),
name varchar(100),
tempT1summer INTEGER ,
tempT2summer INTEGER,
tempT1winter INTEGER ,
tempT2winter INTEGER,
pressure1 DECIMAL,
pressure2 DECIMAL);

ALTER SEQUENCE networkwater_sq OWNED BY networkwater.id;

CREATE TABLE IF NOT EXISTS systems(
id INT,
name varchar(15)); 

INSERT INTO systems (id, name) values (1,'Отопление');
INSERT INTO systems (id, name) values (2,'Вентиляция');
INSERT INTO systems (id, name) values (3,'ГВС');

CREATE SEQUENCE IF NOT EXISTS systemwater_sq;

CREATE TABLE IF NOT EXISTS systemwater (
id INTEGER PRIMARY KEY DEFAULT nextval('systemwater_sq'),
networkId INTEGER,
systemsId INTEGER,
countHeat DECIMAL,
tempT11 INTEGER,
tempT21 INTEGER,
heightSyst INTEGER,
powerPred INTEGER,
dH INTEGER,
maxV INTEGER,
maxH INTEGER,
countGlikol INTEGER);

ALTER SEQUENCE systemwater_sq OWNED BY systemwater.id;

