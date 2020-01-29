CREATE TABLE resource
(
  id           INTEGER PRIMARY KEY AUTOINCREMENT,
  name         TEXT NOT NULL UNIQUE,
  localization TEXT NOT NULL UNIQUE
);

CREATE TABLE resource_history
(
  id     INTEGER PRIMARY KEY AUTOINCREMENT,
  res_id INTEGER NOT NULL,
  civ_id INTEGER NOT NULL,
  amount INTEGER NOT NULL,
  reason TEXT,
  FOREIGN KEY (res_id) REFERENCES player (id),
  FOREIGN KEY (civ_id) REFERENCES civilization (id)
);

CREATE INDEX resource_history_res_id_index on resource_history (res_id);
CREATE INDEX resource_history_civ_id_index on resource_history (civ_id);

CREATE TABLE civilization_resource
(
  id     INTEGER PRIMARY KEY AUTOINCREMENT,
  civ_id INTEGER NOT NULL,
  res_id INTEGER NOT NULL,
  amount INTEGER NOT NULL DEFAULT 0,
  UNIQUE (civ_id, res_id) ON CONFLICT REPLACE,
  FOREIGN KEY (res_id) REFERENCES player (id),
  FOREIGN KEY (civ_id) REFERENCES civilization (id)
);

CREATE TRIGGER civ_create_civ_resources
  AFTER INSERT
  ON civilization
  BEGIN
    INSERT INTO civilization_resource (civ_id, res_id)
    SELECT new.id, res.id
    FROM resource res;
  END;

CREATE TRIGGER resource_create_civ_resources
  AFTER INSERT
  ON resource
  BEGIN
    INSERT INTO civilization_resource (civ_id, res_id)
    SELECT civ.id, new.id
    FROM civilization civ;
  END;

CREATE TRIGGER resource_history_insert_update_civ_resource
  AFTER INSERT
  on resource_history
  BEGIN
    UPDATE civilization_resource
    SET amount = amount + new.amount
    WHERE civ_id = new.civ_id
      AND res_id = new.res_id;
  END;

CREATE TRIGGER resource_history_update_update_civ_resources
  AFTER UPDATE
  on resource_history
  BEGIN
    UPDATE civilization_resource
    SET amount = (amount - old.amount + new.amount)
    WHERE civ_id = new.civ_id
      AND res_id = new.res_id;
  END;

CREATE TRIGGER resource_history_delete_update_civ_resources
  AFTER DELETE
  on resource_history
  BEGIN
    UPDATE civilization_resource
    SET amount = amount - old.amount
    WHERE civ_id = old.civ_id
      AND res_id = old.res_id;
  END;