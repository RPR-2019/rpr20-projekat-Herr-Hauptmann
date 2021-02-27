BEGIN TRANSACTION;
DROP TABLE IF EXISTS "korisnici";
CREATE TABLE IF NOT EXISTS "korisnici" (
	"ime"	TEXT NOT NULL,
	"username"	TEXT NOT NULL UNIQUE,
	"password"	TEXT NOT NULL,
	"id"	INTEGER NOT NULL UNIQUE,
	PRIMARY KEY("id" AUTOINCREMENT)
);
COMMIT;