-- Trigger pour INSERT sur la table LOGEMENTS 
CREATE
OR REPLACE TRIGGER trg_logements_insert AFTER
INSERT
  ON LOGEMENTS FOR EACH ROW
BEGIN
UPDATE IMMEUBLES
SET
  NB_LOGEMENTS = NB_LOGEMENTS + 1
WHERE
  NO_IMMEUBLE = :new.NO_IMMEUBLE;

END;

/
-- Trigger pour UPDATE sur la table LOGEMENTS
CREATE
OR REPLACE TRIGGER trg_logements_update AFTER
UPDATE ON LOGEMENTS FOR EACH ROW
BEGIN IF :new.NO_IMMEUBLE <> :old.NO_IMMEUBLE THEN
UPDATE IMMEUBLES
SET
  NB_LOGEMENTS = NB_LOGEMENTS - 1
WHERE
  NO_IMMEUBLE = :old.NO_IMMEUBLE;

UPDATE IMMEUBLES
SET
  NB_LOGEMENTS = NB_LOGEMENTS + 1
WHERE
  NO_IMMEUBLE = :new.NO_IMMEUBLE;

END IF;

END;

/
-- Trigger pour DELETE sur la table LOGEMENTS
CREATE
OR REPLACE TRIGGER trg_logements_delete AFTER DELETE ON LOGEMENTS FOR EACH ROW
BEGIN
UPDATE IMMEUBLES
SET
  NB_LOGEMENTS = NB_LOGEMENTS - 1
WHERE
  NO_IMMEUBLE = :old.NO_IMMEUBLE;

END;

/
-- Trigger pour INSERT sur la table ENTRETIENS 
CREATE
OR REPLACE TRIGGER trg_entretiens_insert AFTER
INSERT
  ON ENTRETIENS FOR EACH ROW
BEGIN
UPDATE IMMEUBLES
SET
  ENTRETIEN = ENTRETIEN + :new.NB_HEURES
WHERE
  NO_IMMEUBLE = :new.IMM_NO_IMMEUBLE;

END;

/
-- Trigger pour UPDATE sur la table ENTRETIENS 
CREATE
OR REPLACE TRIGGER trg_entretiens_update AFTER
UPDATE ON ENTRETIENS FOR EACH ROW
BEGIN IF :new.NB_HEURES <> :old.NB_HEURES THEN
UPDATE IMMEUBLES
SET
  ENTRETIEN = ENTRETIEN - :old.NB_HEURES + :new.NB_HEURES
WHERE
  NO_IMMEUBLE = :new.IMM_NO_IMMEUBLE;

END IF;

END;

/
-- Trigger pour DELETE sur la table ENTRETIENS 
CREATE
OR REPLACE TRIGGER trg_entretiens_delete AFTER DELETE ON ENTRETIENS FOR EACH ROW
BEGIN
UPDATE IMMEUBLES
SET
  ENTRETIEN = ENTRETIEN - :old.NB_HEURES
WHERE
  NO_IMMEUBLE = :old.IMM_NO_IMMEUBLE;

END;

/