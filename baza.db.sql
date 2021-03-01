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
DROP TABLE IF EXISTS "kategorije";
CREATE TABLE IF NOT EXISTS "kategorije" (
	"id"	INTEGER NOT NULL,
	"naziv"	TEXT,
	PRIMARY KEY("id" AUTOINCREMENT)
);
INSERT INTO "korisnici" ("id","ime","username","password","admin") VALUES (1,'Administrator','admin','21232f297a57a5a743894a0e4a801fc3',1);
INSERT INTO "kategorije" ("id","naziv") VALUES (1,'Predjelo');
INSERT INTO "kategorije" ("id","naziv") VALUES (2,'Glavno jelo');
INSERT INTO "kategorije" ("id","naziv") VALUES (3,'Dezert');
INSERT INTO "kategorije" ("id","naziv") VALUES (4,'Piće');
COMMIT;
