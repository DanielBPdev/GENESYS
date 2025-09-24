--liquibase formatted sql

--changeset halzate:01 
--comment: Se modifica nombre columna empBeneficioLey2429Activo a empBeneficioLey1429Activo
EXEC sp_RENAME 'Empleador.empBeneficioLey2429Activo','empBeneficioLey1429Activo','COLUMN' 