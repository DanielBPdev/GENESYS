--liquibase formatted sql

--changeset mamonroy:01
--comment:
IF EXISTS (SELECT cnsId FROM Constante WHERE cnsNombre = 'CAJA_COMPENSACION_ID' AND cnsValor = 14) AND EXISTS (SELECT cnsId FROM Constante WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO' AND cnsValor = 'CCF16')
BEGIN

UPDATE ped
SET ped.pedGenero = 'MASCULINO'
FROM Persona per
JOIN PersonaDetalle ped ON per.perId = ped.pedPersona
WHERE per.perNumeroIdentificacion = '1062445400'
AND per.perTipoIdentificacion = 'REGISTRO_CIVIL'
AND per.perId = 16800
AND (ped.pedGenero = 'INDEFINIDO' OR ped.pedGenero IS NULL)

END