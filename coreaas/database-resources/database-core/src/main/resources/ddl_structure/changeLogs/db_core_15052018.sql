--liquibase formatted sql

--changeset rarboleda:01
--comment: Creacion campo roaOportunidadPago
ALTER TABLE RolAfiliado ADD roaOportunidadPago varchar(11);

--changeset alquintero:02
--comment: Creacion campo roaOportunidadPago
EXEC sp_RENAME 'LegalizacionDesembolso.lgdFechaTrasnferencia ' , 'lgdFechaTransferencia', 'COLUMN';
