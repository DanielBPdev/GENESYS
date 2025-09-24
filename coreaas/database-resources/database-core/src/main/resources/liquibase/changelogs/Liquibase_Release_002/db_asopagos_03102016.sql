--liquibase formatted sql

--changeset lzarate:01 stripComments:false  
CREATE TABLE ValidacionProceso(
	vapId bigint IDENTITY(1,1) NOT NULL,
	vapBloque varchar(20) NULL,
	vapValidacion varchar(50) NULL,
	vapTipoAfiliado varchar(30) NULL,
	vapTipoBeneficiario varchar(30) NULL,
    vapProceso varchar(100) NULL,
	vapEstadoProceso varchar (20) NULL,
	vapOrden int

	
    CONSTRAINT PK_ValidacionProceso_vapId PRIMARY KEY (vapId) 
) ;


EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Entidad que representa las diferentes parametrizaciones para validaciones de negocio' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ValidacionProceso';

--changeset lzarate:02 stripComments:false  
/*29/09/2016-sbriñez-HU-TRASNVERSAL*/
CREATE TABLE DatosRegistroValidacion(
	drvId bigint IDENTITY(1,1) NOT NULL,
	CONSTRAINT PK_DatosRegistroValidaciono_drvId PRIMARY KEY (drvId) 
) ;




EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Entidad que representa los datos asociados a los resultados de las validaciones' , @level0type=N'SCHEMA', @level0name=N'dbo',@level1type=N'TABLE',@level1name=N'DatosRegistroValidacion';

--changeset lzarate:03 stripComments:false  
CREATE TABLE DetalleDatosRegistroValidacion(
   ddrIdDato bigint NOT NULL,
   ddrValorDetalle varchar(20),
   ddrIdDetalle varchar(20) NOT NULL,
   CONSTRAINT PK_DetalleDatosRegistroValidacion_ddrvIdDato_ddrIdDetalle PRIMARY KEY (ddrIdDato,ddrIdDetalle)
)	;


ALTER TABLE DetalleDatosRegistroValidacion ADD CONSTRAINT FK_DetalleDatosRegistroValidacion_ddrvIdDato FOREIGN KEY (ddrIdDato) REFERENCES DatosRegistroValidacion(drvId)
GO 

--changeset lzarate:04 stripComments:false  

CREATE TABLE HistoriaResultadoValidacion
(
   hrvId bigint PRIMARY KEY NOT NULL,
   hrvDetalle varchar(20),
   hrvResultado varchar(20),
   hrvValidacion varchar(30),
   hrvIdDatosRegistro bigint
)
GO
ALTER TABLE HistoriaResultadoValidacion ADD CONSTRAINT FK_HistoriaResultadoValidacion_hrvIdDatosRegistro FOREIGN KEY (hrvIdDatosRegistro) REFERENCES DatosRegistroValidacion(drvId)
GO

CREATE UNIQUE INDEX UK_HistoriaResultadoValidacion_hrvId ON HistoriaResultadoValidacion (hrvId);
GO 


--changeset mgiraldo:05 stripComments:false 
/*03/10/2016-mgiraldo-HU-nivelacion*/ 
ALTER TABLE Comunicado ALTER COLUMN comEstadoEnvio VARCHAR(20);
ALTER TABLE Comunicado ALTER COLUMN comMedioComunicado VARCHAR(10);
ALTER TABLE Comunicado ALTER COLUMN comSedeCajaCompensacion VARCHAR(2);
ALTER TABLE EscalaSoliciAfiliEmpleador ALTER COLUMN eaeResultadoAnalista VARCHAR(30);
ALTER TABLE ItemChequeoAfiliEmpleador ALTER COLUMN ichEstadoRequisito VARCHAR(20);
ALTER TABLE NotificacionEnviada ALTER COLUMN noeEstadoEnvioNot VARCHAR(20);
ALTER TABLE OcupacionProfesion ALTER COLUMN ocuNombre VARCHAR(100);

--changeset mgiraldo:06 stripComments:false  
drop index IDX_Solicitud_solNumeroRadicacion on Solicitud;

--changeset mgiraldo:07 stripComments:false
SET ANSI_PADDING ON  
ALTER TABLE Solicitud ALTER COLUMN solNumeroRadicacion VARCHAR(12);

CREATE UNIQUE NONCLUSTERED INDEX IDX_Solicitud_solNumeroRadicacion
ON Solicitud (solNumeroRadicacion)
WHERE solNumeroRadicacion IS NOT NULL;
SET ANSI_PADDING OFF
go

--changeset jcamargo:08 stripComments:false 
/*03/10/2016-jcamargo-HU-110*/
set IDENTITY_INSERT ValidacionProceso ON
insert into ValidacionProceso (vapId,vapProceso,vapBloque,vapValidacion,vapOrden)values(1,'AFILIACION_EMPRESAS_WEB','112-110-1','VALIDACION_SOLICITUD_EMPLEADOR',1);
insert into ValidacionProceso (vapId,vapProceso,vapBloque,vapValidacion,vapOrden)values(2,'AFILIACION_EMPRESAS_WEB','112-110-1','VALIDACION_EMPLEADOR_ACTIVO',2);
insert into ValidacionProceso (vapId,vapProceso,vapBloque,vapValidacion,vapOrden)values(3,'AFILIACION_EMPRESAS_WEB','112-110-1','VALIDACION_EMPLEADOR_BD_CORE',3);
set IDENTITY_INSERT ValidacionProceso OFF
go


