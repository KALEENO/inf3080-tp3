CREATE OR REPLACE PROCEDURE SP_afficher_retardataires AS
BEGIN
    FOR loc_retardataire IN (
        SELECT V.LOC_NOM
        FROM VERSEMENTS V
        JOIN LOCATAIRES ON V.LOC_NOM = LOCATAIRES.LOC_NOM
        JOIN LOGEMENTS ON LOCATAIRES.LOG_NO_IMMEUBLE = LOGEMENTS.NO_IMMEUBLE
            AND LOCATAIRES.LOG_NO_LOGEMENT = LOGEMENTS.NO_LOGEMENT
        WHERE TO_CHAR(V.DATE_VERSEMENT, 'MM') = '07'
            AND TO_CHAR(V.DATE_VERSEMENT, 'YYYY') = '2023'
            AND V.MONTANT < LOGEMENTS.LOYER
    ) LOOP
        -- Afficher le nom du locataire retardataire
        DBMS_OUTPUT.PUT_LINE('Nom du locataire retardataire : ' || loc_retardataire.LOC_NOM);
    END LOOP;
END;
/

SET SERVEROUTPUT ON ; 

BEGIN
    sp_afficher_retardataires;
END;