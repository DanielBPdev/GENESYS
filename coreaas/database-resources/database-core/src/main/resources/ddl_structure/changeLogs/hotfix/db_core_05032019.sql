--liquibase formatted sql

--changeset dsuesca:01
--comment: 
CREATE TYPE TablaIdType AS TABLE   
( perIdAfiliado BIGINT, idEmpleador BIGINT);  

--changeset dsuesca:02
--comment: Se arregla dato de EstadoAfiliacionPersonaEmpresa
UPDATE eae
SET eae.eaeEmpleador = vw_eae.roaEmpleador
FROM EstadoAfiliacionPersonaEmpresa eae
INNER JOIN VW_EstadoAfiliacionPersonaEmpresa vw_eae ON eae.eaePersona =  vw_eae.perId
													AND eae.eaeEmpleador = vw_eae.perIdEmpleador