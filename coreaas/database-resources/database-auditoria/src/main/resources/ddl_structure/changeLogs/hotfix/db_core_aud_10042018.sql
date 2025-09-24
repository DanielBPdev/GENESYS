--liquibase formatted sql

--changeset atoro:01
--comment: Se agregan campos a tabla RolAfiliado
ALTER TABLE RolAfiliado_aud ADD roaEnviadoAFiscalizacion BIT;
ALTER TABLE RolAfiliado_aud ADD roaMotivoFiscalizacion VARCHAR(30);
ALTER TABLE RolAfiliado_aud ADD roaFechaFiscalizacion DATE;

