--- creación de tablas para el proceso de novedades de dependientes. 
if exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA = 'core' and TABLE_NAME = 'cotReintegro')
	begin
		drop external table  core.cotReintegro		
	end

if exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA = 'core' and TABLE_NAME = 'cotFechaIngreso')
	begin
		drop external table  core.cotFechaIngreso
	end

if exists (select * from INFORMATION_SCHEMA.ROUTINES where ROUTINE_NAME = 'ASP_COTFECHAINGRESO')
	begin
		drop procedure dbo.ASP_COTFECHAINGRESO 
	end


if exists (select * from INFORMATION_SCHEMA.ROUTINES where ROUTINE_NAME = 'ASP_COTREINTEGRO')
	begin
		drop procedure dbo.ASP_COTREINTEGRO 
	end

create external table core.cotReintegro (id bigInt, perTipoIdentificacion varchar (30), perNumeroIdentificacion varchar (30), roaEstadoAfiliado varchar (20), roaOportunidadPago varchar(30), roaFechaRetiro date, tiempoReinAfi smallInt) 
with (DATA_SOURCE = coreReferenceData, SCHEMA_NAME = 'dbo', OBJECT_NAME  = 'cotReintegro');

create external table core.cotFechaIngreso (id bigInt, perTipoIdentificacionCot varchar (30), perNumeroIdentificacionCot varchar(30), roaEstadoAfiliado varchar (20), perNumeroIdentificacionAport varchar(30), roaFechaIngreso date, roaFechaRetiro date) 
with (DATA_SOURCE = coreReferenceData, SCHEMA_NAME = 'dbo', OBJECT_NAME  = 'cotFechaIngreso');


--==== Se agrega para controlar las solicitudes de novedad. 

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[core].[SolicitudNovedadPila]') AND type in (N'U'))
DROP EXTERNAL TABLE [core].[SolicitudNovedadPila]

CREATE EXTERNAL TABLE core.SolicitudNovedadPila ([spiId] [bigint] NOT NULL, 	[spiSolicitudNovedad] [bigint] NOT NULL, [spiRegistroDetallado] [bigint] NOT NULL,
	[spiOriginadoEnAporteManual] [bit] NULL, 	[spiIdRegistroDetalladoNovedad] [bigint] NULL )
WITH (DATA_SOURCE = [CoreReferenceData],SCHEMA_NAME = N'dbo',OBJECT_NAME = N'SolicitudNovedadPila')
