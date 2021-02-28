BEGIN TRANSACTION;
DROP TABLE IF EXISTS "korisnici";
CREATE TABLE IF NOT EXISTS "korisnici" (
	"id"	INTEGER NOT NULL UNIQUE,
	"ime"	TEXT NOT NULL,
	"username"	TEXT NOT NULL UNIQUE,
	"password"	TEXT NOT NULL,
	"admin"	INTEGER,
	PRIMARY KEY("id" AUTOINCREMENT)
);
INSERT INTO "korisnici" ("id","ime","username","password","admin") VALUES (1,'Tarik Horozovic','horoz','password',0);
COMMIT;
