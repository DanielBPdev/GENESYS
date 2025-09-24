--liquibase formatted sql

--changeset anbuitrago:01
--comment: Se modifican el de tipo de dato a dos campos en la tabla PilaSolicitudCambioNumIdentAportante
ALTER TABLE PilaSolicitudCambioNumIdentAportante ALTER COLUMN pscFechaRespuesta DATETIME;                                                                                
ALTER TABLE PilaSolicitudCambioNumIdentAportante ALTER COLUMN pscFechaSolicitud DATETIME;