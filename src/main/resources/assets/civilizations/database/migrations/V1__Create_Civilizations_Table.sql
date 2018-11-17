/* Basic Table Creation */
CREATE TABLE LEADER (
  ID INTEGER PRIMARY KEY AUTOINCREMENT,
  NAME TEXT NOT NULL,
  UUID TEXT NOT NULL
);

CREATE TABLE CIVILIZATION (
  ID INTEGER PRIMARY KEY AUTOINCREMENT,
  NAME TEXT NOT NULL,
  ISO_CODE VARCHAR(3) NOT NULL
);