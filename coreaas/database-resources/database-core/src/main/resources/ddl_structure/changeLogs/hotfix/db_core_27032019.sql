--liquibase formatted sql

--changeset mamonroy:01
--comment: mantis 249540
UPDATE Parametro
SET prmNombre = 'LOGO_SUPERSUBSIDIO'
WHERE prmNombre = 'LOGO_SUPERSERVICIOS';

UPDATE VariableComunicado
SET vcoClave = '${logoSupersubsidio}', vcoDescripcion = 'Logo de la Superintendencia del Subsidio Familiar', vcoNombreConstante = 'LOGO_SUPERSUBSIDIO', vcoNombre = 'Logo Supersubsidio'
WHERE vcoClave = '${logoSuperservicios}';




