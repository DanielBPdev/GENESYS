--liquibase formatted sql

--changeset jocorrea:01
--comment: Se agregan campos a la tabla Empleador
ALTER TABLE Empleador ADD empFechaSubsanacionExpulsion date NULL;
ALTER TABLE Empleador ADD empMotivoSubsanacionExpulsion varchar(200) NULL;


--changeset clmarin:02
--comment: Se borran los registros de la tabla DestinatarioGrupo y un registro de PrioridadDestinatario
TRUNCATE TABLE DestinatarioGrupo;
DELETE FROM PrioridadDestinatario WHERE prdDestinatarioComunicado = 75 AND prdGrupoPrioridad = 3 AND prdPrioridad = 1;


