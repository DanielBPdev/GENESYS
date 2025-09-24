--liquibase formatted sql

--changeset atoro:01
--comment: 
ALTER TABLE RolAfiliado ADD roaFechaFinContrato DATE;
ALTER TABLE aud.RolAfiliado_aud ADD roaFechaFinContrato DATE;