--liquibase formatted sql

--changeset jocorrea:01
--comment: Creacion campos empMotivoInactivaRetencionSubsidio,sueMotivoInactivaRetencionSubsidio
ALTER TABLE Empleador ADD empMotivoInactivaRetencionSubsidio VARCHAR(26);
ALTER TABLE SucursalEmpresa ADD sueMotivoInactivaRetencionSubsidio VARCHAR(26);