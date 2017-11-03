DROP TABLE IF EXISTS users --answer, question,survey; 
--DROP SEQUENCE IF EXISTS answer_sq;
CREATE SEQUENCE IF NOT EXISTS users_sq;

CREATE TABLE IF NOT EXISTS users (
id INTEGER PRIMARY KEY DEFAULT nextval('users_sq'), 
login varchar(25), 
pass varchar(32),
firstName varchar(32),
middleName varchar(32),
lastName varchar(32),
email varchar(64)
);

ALTER SEQUENCE users_sq OWNED BY users.id;

INSERT INTO USERS (login, pass) values ('admin', 'E8A48653851E28C69D0506508FB27FC5');-- postgres
INSERT INTO USERS (login, pass) values ('ivanov', '4DFE6E220D16E7B633CFDD92BCC8050B');-- ivanov

CREATE SEQUENCE IF NOT EXISTS survey_sq;

CREATE TABLE IF NOT EXISTS survey (
id INTEGER PRIMARY KEY DEFAULT nextval('survey_sq') ,
title VARCHAR(500)
);

ALTER SEQUENCE survey_sq OWNED BY survey.id;

CREATE SEQUENCE IF NOT EXISTS question_sq;

CREATE TABLE IF NOT EXISTS question (
id INTEGER PRIMARY KEY DEFAULT nextval('question_sq'),
id_survey INTEGER REFERENCES survey (id) ON DELETE CASCADE,
title VARCHAR(500)
);

ALTER SEQUENCE question_sq OWNED BY question.id;

CREATE SEQUENCE IF NOT EXISTS answer_sq;

--FOREIGN KEY (article_id) REFERENCES public.articles (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE,

CREATE TABLE IF NOT EXISTS answer (
id INTEGER PRIMARY KEY DEFAULT nextval('answer_sq'),
id_question INTEGER REFERENCES question (id) ON DELETE CASCADE,
title VARCHAR(500),
typeAnswer VARCHAR(16),
required BOOLEAN
);

ALTER SEQUENCE answer_sq OWNED BY answer.id;

CREATE SEQUENCE IF NOT EXISTS interview_sq;

CREATE TABLE IF NOT EXISTS interview (
id INTEGER PRIMARY KEY DEFAULT nextval('interview_sq'),
title VARCHAR(128)
);

ALTER SEQUENCE interview_sq OWNED BY interview.id;

CREATE SEQUENCE IF NOT EXISTS accessKey_sq;

CREATE TABLE IF NOT EXISTS accessKey (
id INTEGER PRIMARY KEY DEFAULT nextval('accessKey_sq'),
key VARCHAR(36),
entry BOOLEAN
);

ALTER SEQUENCE accessKey_sq OWNED BY accessKey.id;

--select key from accessKey where key like 'a8fb7dce-056f-41a3-b974-396487543a25';
--insert into accessKey (key,entry) values ('a8fb7dce-056f-41a3-b974-396487543a25',false);
/*insert into answer (numAnswer,numQuestion, id_interview) values ('Ответ1' ,'Вопрос1', '1');
insert into interview (title) values ('Опрос1');
insert into answer (numAnswer,numQuestion) values ("Ответ2" ,"Вопрос1");
select * from answer left join interview on interview.id = answer.id_interview*/