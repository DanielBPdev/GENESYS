--liquibase formatted sql

--changeset jocorrea:01
--comment: Creacion campo empRetencionSubsidioActiva,empMotivoRetencionSubsidio tabla Empleador
ALTER TABLE Empleador_aud ADD empRetencionSubsidioActiva BIT,empMotivoRetencionSubsidio VARCHAR(24);

---changeset jocorrea:02
--comment: Creacion campo sueRetencionSubsidioActiva,sueMotivoRetencionSubsidio tabla SucursalEmpresa
ALTER TABLE SucursalEmpresa_aud ADD sueRetencionSubsidioActiva BIT,sueMotivoRetencionSubsidio VARCHAR(24);

--changeset jocorrea:03
--comment: Creacion campo pedFechaDefuncion tabla PersonaDetalle
ALTER TABLE PersonaDetalle_aud ADD pedFechaDefuncion DATE;
