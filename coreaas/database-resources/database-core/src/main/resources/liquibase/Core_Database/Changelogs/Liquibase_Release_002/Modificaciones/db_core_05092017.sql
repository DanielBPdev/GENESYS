--liquibase formatted sql

--changeset clmarin:01
--comment: Eliminacion de campo en la tabla AporteGeneral y creacion de la tabla AporteDetallado
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AporteGeneral_apgFormaReconocimientoAporte')) ALTER TABLE AporteGeneral DROP CONSTRAINT CK_AporteGeneral_apgFormaReconocimientoAporte;
ALTER TABLE AporteGeneral DROP COLUMN apgFormaReconocimientoAporte;
ALTER TABLE AporteDetallado ADD apdFormaReconocimientoAporte varchar (75) NULL;