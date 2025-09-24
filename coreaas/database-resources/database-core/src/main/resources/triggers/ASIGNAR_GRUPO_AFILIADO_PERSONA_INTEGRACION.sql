--Descripcion: Este trigger se realizo el la base de datos del keycloak de antioquia, con la finalidad de
--que los usuarios personas web de Integracion en el ambiente de Antioquia se les asigne el grupo
--correspondiente

-- DELIMITER $$

-- CREATE TRIGGER ASIGNAR_GRUPO_AFILIADO_PERSONA_INTEGRACION
-- AFTER INSERT ON KeyCloak_antioquia.USER_ROLE_MAPPING
-- FOR EACH ROW
-- BEGIN
--    DECLARE grupoId VARCHAR(255);

--    SELECT id INTO grupoId
--    FROM KeyCloak_antioquia.KEYCLOAK_GROUP
--    WHERE NAME = 'afiliado_persona'
--    LIMIT 1;

--     IF EXISTS (
--       SELECT 1 FROM KeyCloak_antioquia.USER_ROLE_MAPPING urm
--       JOIN KeyCloak_antioquia.KEYCLOAK_ROLE kr on urm.ROLE_ID = kr.ID
--       WHERE NAME = 'Integracion_keycloak'
--    )  THEN
--       INSERT INTO KeyCloak_antioquia.USER_GROUP_MEMBERSHIP (USER_ID, GROUP_ID)
--       VALUES (NEW.USER_ID, grupoId);
--    END IF;
-- END$$