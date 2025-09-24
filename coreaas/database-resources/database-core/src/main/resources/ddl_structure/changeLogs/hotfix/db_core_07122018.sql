--liquibase formatted sql

--changeset dsuesca:01
--comment: 
UPDATE bitacoracartera SET bcaresultado='NO_ENVIADO' WHERE bcaresultado IN('NO_ENVIADA');
UPDATE Certificado SET cerTipoAfiliado = 'TRABAJADOR_DEPENDIENTE' WHERE cerTipoAfiliado = 'PERSONA_DEPENDIENTE';