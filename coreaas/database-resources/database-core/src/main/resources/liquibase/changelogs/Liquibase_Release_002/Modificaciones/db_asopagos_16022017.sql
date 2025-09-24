--liquibase formatted sql

--changeset  halzate:01 
--comment: modificación del tamaño de la columna vapBloque de la tabla ValidacionProceso
ALTER TABLE ValidacionProceso ALTER COLUMN vapBloque VARCHAR (60) NULL;


--changeset  leogiral:02 failonerror:false
--comment: Actualizaciones varias
DROP TABLE sysdiagrams;

--changeset  leogiral:03 
--comment: Correción nombre PK tabla intento novedad requisito
DECLARE @table NVARCHAR(512), @sql NVARCHAR(MAX)
SELECT @table = N'dbo.IntentoNoveRequisito'
SELECT @sql = 'ALTER TABLE ' + @table 
    + ' DROP CONSTRAINT ' + name + ';'
    FROM sys.key_constraints
    WHERE [type] = 'PK'
    AND [parent_object_id] = OBJECT_ID(@table)
EXEC sp_executeSQL @sql;
ALTER TABLE dbo.IntentoNoveRequisito ADD CONSTRAINT PK_IntentoNoveRequisito_id PRIMARY KEY CLUSTERED (inrId); 

--changeset  leogiral:05 failonerror:false
--comment: Renombramiento pk intento novedad e intento novedad requisito (borrado FK)
ALTER TABLE IntentoNoveRequisito DROP CONSTRAINT [FK_IntentoNoveRequisito_inrIntentoNovedad];

--changeset  leogiral:06 failonerror:false 
--comment:  Correción nombre PK tabla intento novedad 
DECLARE @table NVARCHAR(512), @sql NVARCHAR(MAX)
SELECT @table = N'dbo.ItentoNovedad'
SELECT @sql = 'ALTER TABLE ' + @table 
    + ' DROP CONSTRAINT ' + name + ';'
    FROM sys.key_constraints
    WHERE [type] = 'PK'
    AND [parent_object_id] = OBJECT_ID(@table)
EXEC sp_executeSQL @sql;
ALTER TABLE dbo.ItentoNovedad ADD CONSTRAINT PK_ItentoNovedad_id PRIMARY KEY CLUSTERED (inoId);

--changeset  leogiral:07 
--comment: Foránea intento novedad e intento novedad requisito
ALTER TABLE IntentoNoveRequisito  WITH CHECK ADD  CONSTRAINT [FK_IntentoNoveRequisito_inrIntentoNovedad] FOREIGN KEY([inrIntentoNovedad]) REFERENCES [ItentoNovedad] ([inoId]);
ALTER TABLE IntentoNoveRequisito CHECK CONSTRAINT [FK_IntentoNoveRequisito_inrIntentoNovedad];
