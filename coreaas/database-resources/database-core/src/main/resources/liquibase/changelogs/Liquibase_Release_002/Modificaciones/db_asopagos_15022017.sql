--liquibase formatted sql

--changeset ogiral:01 failonerror:false
--comment: Se elimina la PK de SolicitudNovedad 
DECLARE @table NVARCHAR(512), @sql NVARCHAR(MAX)
SELECT @table = N'dbo.SolicitudNovedad'
SELECT @sql = 'ALTER TABLE ' + @table 
    + ' DROP CONSTRAINT ' + name + ';'
    FROM sys.key_constraints
    WHERE [type] = 'PK'
    AND [parent_object_id] = OBJECT_ID(@table)
EXEC sp_executeSQL @sql;

--changeset ogiral:02 
--comment: Se modifica nombre columna snoIdSolicitudNovedad a snoId
EXEC sp_RENAME 'SolicitudNovedad.snoIdSolicitudNovedad','snoId','COLUMN' 

--changeset ogiral:03 failonerror:false
--comment: Se crea la PK de SolicitudNovedad
ALTER TABLE SolicitudNovedad ADD CONSTRAINT PK_SolicitudNovedad_snoId PRIMARY KEY (snoId);

--changeset ogiral:04
--comment: Cambio de tamaño de estado de la solicitud
ALTER TABLE SolicitudNovedad ALTER COLUMN snoEstadoSolicitud VARCHAR (50);
