--liquibase formatted sql

--changeset jocampo:01
--comment: 
IF NOT EXISTS (select 1 from sys.types where name = 'TablaPersonaIdType')
CREATE TYPE TablaPersonaIdType AS TABLE (perId BIGINT);

--changeset dsuesca:01
--comment: 
IF OBJECT_ID('TRG_AF_INS_Empresa') IS NOT NULL
DROP TRIGGER TRG_AF_INS_Empresa

--changeset dsuesca:02
--comment: Se arreglan datos de los estados
--EstadoAfiliacionEmpleadorCaja
WITH todelete1 AS (
	SELECT eec.*, 
		lag(eec.eecEstadoAfiliacion) over (ORDER BY eec.eecPersona, eec.eecId) AS prevEstadoAfiliacion,
		lag(eec.eecPersona) over (ORDER BY eec.eecPersona, eec.eecId) AS prevPersona
	FROM EstadoAfiliacionEmpleadorCaja eec
)
DELETE FROM todelete1
WHERE eecEstadoAfiliacion = prevEstadoAfiliacion
  AND eecPersona = prevPersona;

--EstadoAfiliacionPersonaCaja eac
WITH todelete2 AS (
	SELECT eac.*, 
		lag(eac.eacEstadoAfiliacion) over (ORDER BY eac.eacPersona,eac.eacId) AS prevEstadoAfiliacion,
		lag(eac.eacPersona) over (ORDER BY eac.eacPersona, eac.eacId) AS prevPersona
	FROM EstadoAfiliacionPersonaCaja eac
)
DELETE FROM todelete2
WHERE eacEstadoAfiliacion = prevEstadoAfiliacion
  AND eacPersona = prevPersona;

--EstadoAfiliacionPersonaEmpresa eac

WITH todelete3 AS (
	SELECT eae.*, 
		lag(eae.eaeEstadoAfiliacion) over (ORDER BY eae.eaePersona,eae.eaeEmpleador,eae.eaeId) AS prevEstadoAfiliacion,
		lag(eae.eaePersona) over (ORDER BY eae.eaePersona,eae.eaeEmpleador,eae.eaeId) AS prevPersona,
		lag(eae.eaeEmpleador) over (ORDER BY eae.eaePersona,eae.eaeEmpleador,eae.eaeId) AS prevEmpleador
	FROM EstadoAfiliacionPersonaEmpresa eae
)
DELETE FROM todelete3
WHERE eaeEstadoAfiliacion = prevEstadoAfiliacion
  AND eaePersona = prevPersona
  AND eaeEmpleador = prevEmpleador;

--EstadoAfiliacionPersonaIndependiente eac
WITH todelete4 AS (
	SELECT eai.*, 
		lag(eai.eaiEstadoAfiliacion) over (ORDER BY eai.eaiPersona,eai.eaiId) AS prevEstadoAfiliacion,
		lag(eai.eaiPersona) over (ORDER BY eai.eaiPersona,eai.eaiId) AS prevPersona		
	FROM EstadoAfiliacionPersonaIndependiente eai
)
DELETE FROM todelete4
WHERE eaiEstadoAfiliacion = prevEstadoAfiliacion
  AND eaiPersona = prevPersona;

--EstadoAfiliacionPersonaPensionado eac
WITH todelete5 AS (
	SELECT eap.*, 
		lag(eap.eapEstadoAfiliacion) over (ORDER BY eap.eapPersona,eap.eapId) AS prevEstadoAfiliacion,
		lag(eap.eapPersona) over (ORDER BY eap.eapPersona,eap.eapId) AS prevPersona		
	FROM EstadoAfiliacionPersonaPensionado eap
)
DELETE FROM todelete5
WHERE eapEstadoAfiliacion = prevEstadoAfiliacion
  AND eapPersona = prevPersona;

--changeset dsuesca:03
--comment: 

CREATE TABLE dbo.HistoricoRolAfiliado (
	hraId bigint IDENTITY(1,1) NOT NULL,
	hraFechaIngreso date NULL,
	hraFechaRetiro datetime NULL,
	hraTipoAfiliado varchar(30) NOT NULL,
	hraAfiliado bigint NOT NULL,
	hraEmpleador bigint NULL,
	hraCanalRecepcion varchar(21) NULL,
	hraSolicitud bigint NULL,
	hraRadicado VARCHAR(12) NULL,
	CONSTRAINT PK_HistoricoRolAfiliado_hraId PRIMARY KEY (hraId)	
);
