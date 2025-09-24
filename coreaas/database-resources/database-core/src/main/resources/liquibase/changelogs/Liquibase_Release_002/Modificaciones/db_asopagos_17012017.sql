--liquibase formatted sql

--changeset  mgiraldo:01
--comment:Cambio de tipo de dato AFP smallint
ALTER TABLE RolAfiliado DROP CONSTRAINT FK_RolAfiliado_roaPagadorPension;
ALTER TABLE AFP DROP CONSTRAINT PK_AFP_afpId;

ALTER TABLE RolAfiliado ALTER COLUMN roaPagadorPension smallint;
ALTER TABLE AFP ALTER COLUMN afpId smallint;

ALTER TABLE AFP ADD CONSTRAINT PK_AFP_afpId PRIMARY KEY  (afpId);  
ALTER TABLE RolAfiliado ADD CONSTRAINT FK_RolAfiliado_roaPagadorPension FOREIGN KEY (roaPagadorPension) REFERENCES AFP;

--changeset  mgiraldo:02
--comment:Cambio de tipo de dato AreaCajaCompensacion smallint
ALTER TABLE AreaCajaCompensacion DROP CONSTRAINT PK_AreaCajaCompensacion_arcId;
ALTER TABLE AreaCajaCompensacion ALTER COLUMN arcId smallint;
ALTER TABLE AreaCajaCompensacion ADD CONSTRAINT PK_AreaCajaCompensacion_arcId PRIMARY KEY  (arcId);

--changeset  mgiraldo:03
--comment:Cambio de tipo de dato Constante int
ALTER TABLE Constante DROP CONSTRAINT PK_Constante_cnsId;
ALTER TABLE Constante ALTER COLUMN cnsId int;
ALTER TABLE Constante ADD CONSTRAINT PK_Constante_cnsId PRIMARY KEY  (cnsId);  

--changeset  mgiraldo:04
--comment:Cambio de tipo de dato Parametro int
ALTER TABLE Parametro DROP CONSTRAINT PK_Parametro_prmId;
ALTER TABLE Parametro ALTER COLUMN prmId int;
ALTER TABLE Parametro ADD CONSTRAINT PK_Parametro_prmId PRIMARY KEY  (prmId); 