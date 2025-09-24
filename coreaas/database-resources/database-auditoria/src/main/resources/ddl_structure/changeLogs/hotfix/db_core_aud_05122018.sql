--liquibase formatted sql

--changeset atoro:01
--comment: Se agrega campo bcaNumeroOperacion
ALTER TABLE RolAfiliado_aud ADD roaFechaFinContrato DATE;