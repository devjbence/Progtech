
CREATE TABLE users (
	user_id int NOT NULL ,
	name varchar2(20) NOT NULL,
	passw varchar2(20) NOT NULL,
	PRIMARY KEY (user_id)
);


CREATE TABLE matches (
	match_id int NOT NULL ,
	user1 int NOT NULL,
	user2 int NOT NULL,
	match_date DATE NOT NULL,
	file_name varchar2(100) NOT NULL,
	PRIMARY KEY (match_id)
);


ALTER TABLE matches ADD CONSTRAINT matches_fk0 FOREIGN KEY (user1) REFERENCES users(user_id);

ALTER TABLE matches ADD CONSTRAINT matches_fk1 FOREIGN KEY (user2) REFERENCES users(user_id);

