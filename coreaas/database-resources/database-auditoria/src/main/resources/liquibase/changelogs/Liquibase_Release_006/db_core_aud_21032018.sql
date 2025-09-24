--liquibase formatted sql

--changeset atoro:01
--comment:Se adiciona campo en la tabla SolicitudPreventiva_aud
ALTER TABLE SolicitudPreventiva_aud ADD sprFechaFiscalizacion DATE; 

--changeset rarboleda:02
--comment:Se modifica el tamaño de campo en la tabla SolicitudLiquidacionSubsidio_aud
ALTER TABLE SolicitudLiquidacionSubsidio_aud ALTER COLUMN slsTipoLiquidacionEspecifica VARCHAR(32); 

--changeset atoro:03
--comment:Se adiciona campos en las tablas ParametrizacionFiscalizacion_aud y Cartera_aud
ALTER TABLE ParametrizacionFiscalizacion_aud ADD pfiGestionPreventiva bit NULL; 
ALTER TABLE Cartera_aud ADD carUsuarioTraspaso VARCHAR(255) NULL; 
