--liquibase formatted sql

--changeset dsuesca:01
--comment: Se corrigen datos json mal generados en tabla DATOTEMPORALSOLICITUD
UPDATE DATOTEMPORALSOLICITUD
SET dtsJsonPayload = CASE WHEN LEFT(CAST(dtsJsonPayload AS VARCHAR(2)),1) = '"' 
			THEN REPLACE(REPLACE(REPLACE(CAST(dtsJsonPayload AS VARCHAR(MAX)),'\"','"'),'"{','{'),'}"','}')
		ELSE 
			REPLACE(CAST(dtsJsonPayload AS VARCHAR(MAX)),'\"','') END
WHERE dtsJsonPayload like '%"\%';