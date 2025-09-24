--liquibase formatted sql

--changeset Heinsohn:1 stripComments:true

-- Creación de TipoSolicitante
Insert Into TipoSolicitante(tsoDescripcion,tsoTipoTipoSolicitante)Values('Persona jurídica','EMPLEADOR');
Insert Into TipoSolicitante(tsoDescripcion,tsoTipoTipoSolicitante)Values('Persona natural','EMPLEADOR');
Insert Into TipoSolicitante(tsoDescripcion,tsoTipoTipoSolicitante)Values('Empleador de servicio doméstico','EMPLEADOR');
Insert Into TipoSolicitante(tsoDescripcion,tsoTipoTipoSolicitante)Values('Propiedad horizontal','EMPLEADOR');
Insert Into TipoSolicitante(tsoDescripcion,tsoTipoTipoSolicitante)Values('Cooperativa o pre-cooperativa de trabajo asociado','EMPLEADOR');
Insert Into TipoSolicitante(tsoDescripcion,tsoTipoTipoSolicitante)Values('Entidad sin ánimo de lucro','EMPLEADOR');
Insert Into TipoSolicitante(tsoDescripcion,tsoTipoTipoSolicitante)Values('Organización religiosa o parroquia','EMPLEADOR');

GO