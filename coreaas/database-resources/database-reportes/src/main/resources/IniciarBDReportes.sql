DELETE FROM MarcaProcesamiento

DECLARE @sqlDel NVARCHAR(MAX)
							  
	DECLARE @TablasCursor AS CURSOR
	DECLARE
	@TipoTabla Varchar(255)

	SET @TablasCursor = CURSOR FAST_FORWARD FOR
	SELECT name
	FROM sysobjects
	where name like '%_aud'
	
	OPEN @TablasCursor
	FETCH NEXT FROM @TablasCursor INTO
	@TipoTabla
	
	WHILE @@FETCH_STATUS = 0
	BEGIN

		SET @sqlDel = 'DELETE FROM aud.' + @TipoTabla
		EXEC sp_executesql @sqlDel
	
		FETCH NEXT FROM @TablasCursor INTO
		@TipoTabla
	END
CLOSE @TablasCursor;
DEALLOCATE @TablasCursor;


truncate table PlanillaPILA
truncate table EstadoArchivoPILA

truncate table EstadoAfiliacionEmpleadorCaja
truncate table EstadoAfiliacionPersonaCaja
truncate table EstadoAfiliacionPersonaIndependiente
truncate table EstadoAfiliacionPersonaPensionado
truncate table EstadoAfiliacionPersonaEmpresa
truncate table EstadoAfiliacionBeneficiario

truncate table registrolog
truncate table WaterMarkedRows
delete from RevisionAuditoriaReportes

truncate table CategoriaBeneficiario
delete from CategoriaAfiliado

truncate table FactNovedadPersona
truncate table FactNovedadEmpleador
truncate table FactArchivoPILA
truncate table FactCondicionCotizante
truncate table FactCondicionAportante
truncate table FactCondicionBeneficiario
truncate table FactSolicitudAfiliacionPersona
truncate table FactCondicionPersona
truncate table FactSolicitudAfiliacionEmpleador
truncate table FactCondicionEmpleador

truncate table FactGestionCarteraV2
truncate table FactCondicionSolicitanteCarteraV2
truncate table FactPostulacionFOVISV2
truncate table FactAsignacionFOVISV2
truncate table FactLegalizacionDesembolsoFOVISV2
truncate table FactNovedadFOVISV2

truncate table FactAfiliadoV2
truncate table FactAfiliacionPersonaV2
truncate table FactSolicitudAfiliacionPersonaV2
truncate table FactSolicitudAfiliacionEmpleadorV2
truncate table FactAfiliacionEmpleadorV2
truncate table FactSolicitudNovedadPersonaV2
truncate table FactSolicitudNovedadEmpleadorV2

--delete from DimMetasCanalPeriodo
--delete from DimPeriodo
delete from DimPersona
delete from DimEmpleador
delete from DimAportante
delete from DimCotizante


print 'delete tablas core'
	DECLARE @TablasCore AS TABLE (tabla VARCHAR(100), orden smallint IDENTITY)
	DECLARE @tabla AS VARCHAR(100)
	DECLARE @columns VARCHAR(1000)
	DECLARE @columnsWithoutShardName VARCHAR(1000)
	DECLARE @sql NVARCHAR(4000)
	DECLARE @sqlDelete NVARCHAR(4000)
	DECLARE @sqlInsert NVARCHAR(4000)
	DECLARE @DBNAME VARCHAR(255)

	INSERT INTO @TablasCore (tabla) VALUES
	--('DiasFestivos'),
	('MedioDePago'),
	('MedioEfectivo'),
	('MedioTarjeta'),
	('MedioTransferencia'),
	('Persona'),
	('Afiliado'),
	('GrupoFamiliar'),
	--('CodigoCIIU'),
	('Empresa'),
	('SucursalEmpresa'),
	('Empleador'),
	('RolAfiliado'),
	('AdministradorSubsidio'),
	('AdminSubsidioGrupo'),
	('DatoTemporalSolicitud'), --No va en aud
	('IntentoNovedad'),
	('IntentoAfiliacion'),
	('SedeCajaCompensacion'),
	('Solicitud'),
	('SolicitudAfiliaciEmpleador'),
	('SolicitudAfiliacionPersona'),
	('Comunicado'),
	--('Parametro'),
	--('ParametrizacionNovedad'),
	('SolicitudNovedad'),
	('SolicitudNovedadEmpleador'),
	('SolicitudNovedadPersona'),
	('NovedadDetalle'),
	('AporteGeneral'),
	('AporteDetallado'),
	('SocioEmpleador'),
	('PersonaDetalle'),
	('BeneficiarioDetalle'),
	('Beneficiario'),
	('BeneficioEmpleador'),
	('CondicionInvalidez'),

	('RolContactoEmpleador'),
	('ActividadCartera'),
	('AgendaCartera'),
	('CarteraAgrupadora'),
	('Cartera'),
	('CertificadoEscolarBeneficiario'),
	('RolContactoEmpleador'),
	('Constante'),
	('CarteraDependiente'),

	('MovimientoAporte'),
	('SolicitudPreventiva'),
	('SolicitudPreventivaAgrupadora'),
	('SolicitudGestionCobroManual'),
	('SolicitudGestionCobroFisico'),
	('SolicitudGestionCobroElectronico'),
	('SolicitudFiscalizacion'),
			
	('CicloAsignacion'),
	('SolicitudAsignacion'),
	('JefeHogar'),
	('IntegranteHogar'),
	('PostulacionFOVIS'),
	('SolicitudPostulacion'),
	('LegalizacionDesembolso'),
	('SolicitudLegalizacionDesembolso'),
	('SolicitudNovedadFovis'),
	('SolicitudNovedadPersonaFovis')
	
	
	SET @TablasCursor = CURSOR FAST_FORWARD FOR
	
	SELECT tabla
	FROM @TablasCore
	ORDER BY orden desc
	
	OPEN @TablasCursor
	FETCH NEXT FROM @TablasCursor INTO
	@TipoTabla
	
	WHILE @@FETCH_STATUS = 0
	BEGIN

		SET @sqlDel = 'DELETE FROM dbo.' + @TipoTabla
		EXEC sp_executesql @sqlDel
	
		FETCH NEXT FROM @TablasCursor INTO
		@TipoTabla
	END
	CLOSE @TablasCursor;
	DEALLOCATE @TablasCursor;