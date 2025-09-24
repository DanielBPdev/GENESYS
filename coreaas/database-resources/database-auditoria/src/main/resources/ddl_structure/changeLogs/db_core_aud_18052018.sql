--liquibase formatted sql

--changeset jocorrea:01
--comment: Creacion campos empMotivoInactivaRetencionSubsidio,sueMotivoInactivaRetencionSubsidio
ALTER TABLE Empleador_aud ADD empMotivoInactivaRetencionSubsidio VARCHAR(26);
ALTER TABLE SucursalEmpresa_aud ADD sueMotivoInactivaRetencionSubsidio VARCHAR(26);