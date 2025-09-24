--liquibase formatted sql

--changeset arocha:01
--comment: Se agregan campos en la tabla staging.Aportante y se crea la tabla staging.BeneficioEmpresaPeriodo
--Adicion de campos a la tabla staging.Aportante
ALTER TABLE staging.Aportante DROP COLUMN apoTarifaEmpleador;
ALTER TABLE staging.Aportante DROP COLUMN apoTipoBeneficio;
ALTER TABLE staging.Aportante DROP COLUMN apoBeneficioActivo;

--Creacion de la tabla staging.BeneficioEmpresaPeriodo
CREATE TABLE staging.BeneficioEmpresaPeriodo(
       bepId bigint IDENTITY(1,1) NOT NULL,
       bepTransaccion bigint NOT NULL,
       bepTipoIdentificacion varchar(20) NOT NULL,
       bepNumeroIdentificacion varchar(16) NOT NULL,
       bepTipoBeneficio varchar(16) NOT NULL,
       bepBeneficioActivo bit NOT NULL,
       bepPeriodoAporte varchar(7) NOT NULL,
       bepTarifaEmpleador numeric(5,5) NOT NULL,
CONSTRAINT PK_BeneficioEmpresaPeriodo PRIMARY KEY (bepId)
);
ALTER TABLE staging.BeneficioEmpresaPeriodo ADD CONSTRAINT FK_BeneficioEmpresaPeriodo_Transaccion FOREIGN KEY(bepTransaccion) REFERENCES staging.Transaccion (traId);

--changeset arocha:02
--comment: Se adiciona campos y se actualizan registros en la tabla TemAporte, se alteran campos en la tabla staging.RegistroDetallado.  
ALTER TABLE TemAporte ADD temMarcaValRegistroAporte VARCHAR(50);
UPDATE tem SET temMarcaValRegistroAporte = redOUTMarcaValRegistroAporte FROM TemAporte tem INNER JOIN staging.RegistroDetallado red ON tem.temRegistroDetallado = red.redId;
DELETE FROM TemAporte WHERE temRegistroGeneral NOT IN (SELECT regId FROM staging.RegistroGeneral);
ALTER TABLE TemAporte ALTER COLUMN temMarcaValRegistroAporte VARCHAR(50) NOT NULL;
ALTER TABLE TemAporte ADD temEstadoValRegistroAporte VARCHAR(60);
UPDATE TemAporte SET temEstadoValRegistroAporte = 'OK';
ALTER TABLE TemAporte ALTER COLUMN temEstadoValRegistroAporte VARCHAR(60) NOT NULL;
ALTER TABLE TemAporte ADD temUsuarioAprobadorAporte VARCHAR(50);
UPDATE TemAporte SET temUsuarioAprobadorAporte = 'SISTEMA';
ALTER TABLE TemAporte ALTER COLUMN temUsuarioAprobadorAporte VARCHAR(50) NOT NULL;
UPDATE staging.RegistroDetallado SET redUsuarioAprobadorAporte = 'SISTEMA';
ALTER TABLE staging.RegistroDetallado ALTER COLUMN redUsuarioAprobadorAporte VARCHAR(50) NOT NULL;

