/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 28/11/2024 1:15:32 p. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 17/09/2024 2:24:05 p. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 14/08/2024 10:46:25 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 2023-12-14 8:11:08 AM ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 2023-11-23 9:57:20 AM ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 2023-11-21 10:35:23 AM ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 2023-10-19 9:26:10 AM ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 2023-10-18 8:53:49 AM ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 2023-10-03 9:06:38 AM ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 2023-07-07 9:27:15 PM ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 2023-07-07 5:48:06 PM ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 2023-07-07 8:47:26 AM ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 2023-07-06 10:07:42 PM ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 2023-06-28 4:04:30 PM ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 09/06/2023 11:14:29 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 01/06/2023 10:00:52 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 26/05/2023 11:13:24 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 25/05/2023 11:13:24 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 23/05/2023 11:13:24 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 10/04/2023 11:34:53 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 15/03/2023 10:58:55 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 09/02/2023 4:30:39 p. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 20/12/2022 4:12:10 p. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 31/10/2022 5:12:11 p. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 31/10/2022 11:37:34 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteAfiliadosACargo]    Script Date: 20/09/2022 3:36:04 p. m. ******/
-- =============================================
-- Author:      Miguel Angel Perilla
-- Update Date: 30 Junio 2022
-- Update Date: 11 Agosto 2022 olga vega 
-- Description: Procedimiento almacenado para obtener el resultado que hace referencia al reporte 3.
-- Reporte 3
---EXEC reporteAfiliadosACargo '2023-11-01','2023-11-30'
-- =============================================
CREATE OR ALTER   PROCEDURE [dbo].[reporteAfiliadosACargo] (
	@FECHA_INICIAL DATE,
	@FECHA_FINAL DATE
)

AS

--declare	@FECHA_INICIAL DATE ='2024-10-01'
--declare 	@FECHA_FINAL DATE='2024-10-31'
BEGIN
-- SET ANSI_WARNINGS OFF
SET NOCOUNT ON
SET ANSI_NULLS ON
SET QUOTED_IDENTIFIER ON

--/---------------------------------------**********---------------------------------------\--
--                          REPORTE DE AFILIADOS A CARGO  -  N° 3.
--\---------------------------------------**********---------------------------------------/--

IF OBJECT_ID('tempdb.dbo.#PoblacionReporteACargo', 'U') IS NOT NULL
    DROP TABLE #PoblacionReporteACargo 
	
	DECLARE @fechaInicioRev BIGINT, @fechaFinRev BIGINT
	SET @fechaInicioRev = DATEDIFF_BIG (ms, '1969-12-31 19:00:00', @FECHA_INICIAL)
	SET @fechaFinRev = DATEDIFF_BIG (ms, '1969-12-31 19:00:00', @FECHA_FINAL)
 
		 drop table if exists #TEMP_AFI_CARGO
		 
		 drop table if exists #TEMP_AFI_CARGO2
		 drop table if exists #TEMP_AFI_CARGO3
		 
		 drop table if exists #TEMP_AFI_CARGO4
		 
		 drop table if exists #TEMP_AFI_CARGO6




		 drop table if exists #rvl
create table #rvl  (rvlid bigint,  dsaid bigint ,rvlvalorcuotaajustada numeric(10,2),rvlesagrario smallint, rvlesdiscapacidad smallint,  rvlesconyugecuidador smallint ,origen varchar(max))
insert into #rvl
exec sp_Execute_remote subsidioreferencedata, N'
				DROP TABLE IF EXISTS #radicados
				CREATE TABLE #radicados (numeroradicado varchar(100), dsaid bigint, dsaresultadovalidacionliquidacion bigint, origen varchar(max))
				 INSERT  #radicados
				 EXEC SP_Execute_remote coreReferenceData, N''
				 SELECT  solNumeroRadicacion, dsaid,dsaresultadovalidacionliquidacion FROM
				 SOLICITUD WITH(NOLOCK)
				 INNER JOIN solicitudLiquidacionSubsidio on slsSolicitudGlobal = solid
				 INNER JOIN cuentaadministradorsubsidio on cassolicitudliquidacionsubsidio = slsid and casOrigenTransaccion = @origen and casTipoTransaccionSubsidio = @tipoCas
				 INNER JOIN detallesubsidioasignado on casid = dsacuentaadministradorsubsidio and  dsaEstado = @estadoDsa
				
				WHERE  CONVERT(DATE,slsFechaDispersion) BETWEEN @FECHA_INICIAL AND @FECHA_FINAL 
				 AND solResultadoProceso =@Estado
				'', N''@FECHA_INICIAL DATE, @FECHA_FINAL DATE, @Estado varchar(10), @origen varchar(100), @tipoCas varchar(10), @estadoDsa varchar(20)'',  @FECHA_FINAL = @FECHA_FINAL, @FECHA_INICIAL=@FECHA_INICIAL, @Estado=''DISPERSADA'',  @origen =''LIQUIDACION_SUBSIDIO_MONETARIO'', @tipoCas =''ABONO'', @estadoDsa=''DERECHO_ASIGNADO''

					CREATE NONCLUSTERED INDEX IX_r on  #radicados(numeroradicado, dsaresultadovalidacionliquidacion)
			 select rvlid,dsaid, rvlvalorcuotaajustada,rvlesagrario, rvlesdiscapacidad, rvlesconyugecuidador   from resultadovalidacionliquidacion R 
			INNER JOIN #radicados on numeroradicado = rvlnumeroradicado  and dsaresultadovalidacionliquidacion = rvlid 
			WHERE rvlestadoderecholiquidacion in (''DERECHO_ASIGNADO'',''ASIGNAR_DERECHO'')
				
				' ,N'@FECHA_INICIAL DATE, @FECHA_FINAL DATE', @FECHA_FINAL = @FECHA_FINAL, @FECHA_INICIAL=@FECHA_INICIAL
				
				create nonclustered index ix_detalle on #rvl(dsaid)

			drop table if exists #minimaestro

		SELECT * 
	INTO #minimaestro
	FROM  MaestroLiquidacion AS dsaSubsidioAfiliado with(nolock)
	WHERE  convert(date,dsaSubsidioAfiliado.slsFechaDispersion) BETWEEN @FECHA_INICIAL AND @FECHA_FINAL---474781
		AND dsaSubsidioAfiliado.casOrigenTransaccion = 'LIQUIDACION_SUBSIDIO_MONETARIO'
		AND dsaSubsidioAfiliado.dsaOrigenRegistroSubsidio = 'LIQUIDACION_SUBSIDIO_MONETARIO'
		AND dsaSubsidioAfiliado.solResultadoProceso = 'DISPERSADA' 
		AND dsaSubsidioAfiliado.dsaEstado = 'DERECHO_ASIGNADO' 
		AND dsaSubsidioAfiliado.casTipoTransaccionSubsidio = 'ABONO'

	CREATE CLUSTERED INDEX IND_LLAVES ON #minimaestro ( dsaId)



	drop table if exists #MiniMaestroRVL

		select m.*, r.rvlvalorcuotaajustada,ISNULL(r.rvlesconyugecuidador,0) AS rvlesconyugecuidador ,r.rvlesdiscapacidad,r.rvlesagrario
		into #MiniMaestroRVL
		 from
		 #minimaestro m
		 inner join #rvl r on r.dsaid=m.dsaId	
	
	CREATE nonCLUSTERED INDEX IND_LLAVES1 ON #MiniMaestroRVL ( dsaBeneficiarioDetalle)
	
	CREATE nonCLUSTERED INDEX IND_LLAVES2 ON #MiniMaestroRVL ( dsaEmpleador)
	
	CREATE nonCLUSTERED INDEX IND_LLAVES3 ON #MiniMaestroRVL ( dsaAfiliadoPrincipal)

	
	CREATE nonCLUSTERED INDEX IND_LLAVES4 ON #MiniMaestroRVL ( rvlesconyugecuidador)


					   SELECT DISTINCT -- roa.*,ap.*, pa.*, Ubicacion.*, Municipio.*, pda.*, E.*, 
					   roaId,
roaCargo,
roaClaseIndependiente,
roaClaseTrabajador,
roaEstadoAfiliado,
roaEstadoEnEntidadPagadora,
roaFechaIngreso,
roaFechaRetiro,
roaHorasLaboradasMes,
roaIdentificadorAnteEntidadPagadora,
roaPorcentajePagoAportes,
roaTipoAfiliado,
roaTipoContrato,
roaTipoSalario,
roaValorSalarioMesadaIngresos,
roaAfiliado,
roaEmpleador,
roaPagadorAportes,
roaPagadorPension,
roaSucursalEmpleador,
roaFechaAfiliacion,
roaMotivoDesafiliacion,
roaSustitucionPatronal,
roaFechaFinPagadorAportes,
roaFechaFinPagadorPension,
roaEstadoEnEntidadPagadoraPension,
roaDiaHabilVencimientoAporte,
roaMarcaExpulsion,
roaEnviadoAFiscalizacion,
roaMotivoFiscalizacion,
roaFechaFiscalizacion,
roaOportunidadPago,
roaCanalReingreso,
roaReferenciaAporteReingreso,
roaReferenciaSolicitudReingreso,
roaFechaFinContrato,
roaMunicipioDesempenioLabores,
afiId,
afiPersona,
afiPignoracionSubsidio,
afiCesionSubsidio,
afiRetencionSubsidio,
afiServicioSinAfiliacion,
afiCausaSinAfiliacion,
afiFechaInicioServiciosSinAfiliacion,
afifechaFinServicioSinAfiliacion, 
pa.perId,
pa.perDigitoVerificacion,
pa.perNumeroIdentificacion,
pa.perRazonSocial,
pa.perTipoIdentificacion,
pa.perUbicacionPrincipal,
pa.perPrimerNombre,
pa.perSegundoNombre,
pa.perPrimerApellido,
pa.perSegundoApellido,
pa.perCreadoPorPila,
ubiId,
ubiAutorizacionEnvioEmail,
ubiCodigoPostal,
ubiDireccionFisica,
ubiEmail,
ubiIndicativoTelFijo,
ubiTelefonoCelular,
ubiTelefonoFijo,
ubiMunicipio,
ubiDescripcionIndicacion,
ubiSectorUbicacion,
ubiEmailSecundario,munId,
munCodigo,
munNombre,
munDepartamento, pedId,
pedPersona,
pedFechaNacimiento,
pedFechaExpedicionDocumento,
pda.pedGenero,
pda.pedOcupacionProfesion,
pda.pedNivelEducativo,
pda.pedGradoAcademico,
pda.pedCabezaHogar,
pda.pedAutorizaUsoDatosPersonales,
pda.pedResideSectorRural,
pda.pedEstadoCivil,
pda.pedHabitaCasaPropia,
pda.pedFallecido,
pda.pedFechaFallecido,
pda.pedBeneficiarioSubsidio,
pda.pedEstudianteTrabajoDesarrolloHumano,
pda.pedFechaDefuncion,
pda.pedPersonaPadre,
pda.pedPersonaMadre,
pda.pedOrientacionSexual,
pda.pedFactorVulnerabilidad,
pda.pedPertenenciaEtnica,
pda.pedPaisResidencia,
pda.pedResguardo,
pda.pedPuebloIndigena, e.empId,
e.empPersona,
e.empNombreComercial,
e.empFechaConstitucion,
e.empNaturalezaJuridica,
e.empCodigoCIIU,
e.empArl,
e.empUltimaCajaCompensacion,
e.empPaginaWeb,
e.empRepresentanteLegal,
e.empRepresentanteLegalSuplente,
e.empEspecialRevision,
e.empUbicacionRepresentanteLegal,
e.empUbicacionRepresentanteLegalSuplente,
e.empCreadoPorPila,
e.empEnviadoAFiscalizacion,
e.empMotivoFiscalizacion,
e.empFechaFiscalizacion, 
							pe.perTipoIdentificacion as TipoidEmpl, 
							pe.perNumeroIdentificacion as idEmpl, 
							pe.perDigitoVerificacion as dv,
							pe.perid as idperempl
		 			     INTO #PoblacionReporteACargo		
						  FROM rolafiliado  roa  
					INNER JOIN afiliado  ap WITH (nolock) 
							ON ap.afiid = roa.roaafiliado 			
					INNER JOIN persona  pa WITH (nolock)  ON pa.perid = ap.afipersona-- and pa.perNumeroIdentificacion='93061987'
					INNER JOIN Ubicacion  WITH (nolock) ON pa.perUbicacionPrincipal=ubiId
					INNER JOIN Municipio  WITH (NOLOCK) ON munid = ubiMunicipio 			 
					INNER JOIN personadetalle  pda WITH (nolock) 
					        ON pa.perid=pda.pedpersona
					LEFT JOIN Empleador em ON em.empid = roa.roaempleador
					LEFT JOIN Empresa e ON em.empempresa = e.empid
					LEFT JOIN Persona pe ON pe.perid = e.emppersona
					INNER JOIN rno.HistoricoAfiliados x ---cambio olga vega 20221031
					        ON x.hraNumeroIdentificacionAfiliado = pa.perNumeroIdentificacion
						   AND x.hraNumeroIdentificacion = ISNULL(pe.pernumeroidentificacion, pa.perNumeroIdentificacion)
						   AND CASE WHEN x.hraTipoIdentificacionEmpresa  = '1' THEN  'CEDULA_CIUDADANIA'
								WHEN x.hraTipoIdentificacionEmpresa  = '2' THEN 'TARJETA_IDENTIDAD'
								WHEN x.hraTipoIdentificacionEmpresa  = '3' THEN 'REGISTRO_CIVIL'
								WHEN x.hraTipoIdentificacionEmpresa  = '4' THEN 'CEDULA_EXTRANJERIA'
								WHEN x.hraTipoIdentificacionEmpresa  = '5' THEN 'NUIP'
								WHEN x.hraTipoIdentificacionEmpresa  = '6' THEN 'PASAPORTE'
								WHEN x.hraTipoIdentificacionEmpresa  = '7' THEN 'NIT'
								WHEN x.hraTipoIdentificacionEmpresa  = '8' THEN 'CARNE_DIPLOMATICO'
								WHEN x.hraTipoIdentificacionEmpresa  = '9' THEN 'PERM_ESP_PERMANENCIA'
								WHEN x.hraTipoIdentificacionEmpresa  = '15' THEN 'PERM_PROT_TEMPORAL' END = ISNULL(pe.perTipoIdentificacion, pa.perTipoIdentificacion)
						   AND x.hraFechaInicialReporte = @FECHA_INICIAL
					       AND x.hraFechaFinalReporte = @FECHA_FINAL

	 --- select '#PoblacionReporteACargo',*   from #PoblacionReporteACargo-- where     perNumeroIdentificacion = '34002034' 

		 CREATE CLUSTERED INDEX id ON #PoblacionReporteACargo (perid,afiid,roaid,pedid)

 ----CAMBIO OLGA VEGA 20221028
 drop table if exists #tempSolicitud
 --**solicitudafiliadon
 select s.*, ROW_NUMBER() over (partition by roaid order by sapid desc) as id
 into #tempSolicitud
 from SolicitudAfiliacionPersona s 
 inner join Solicitud on solid =sapSolicitudGlobal
 inner join #PoblacionReporteACargo pr on pr.roaId = sapRolAfiliado
  where solResultadoProceso= 'APROBADA'---218826
 		
	

	 ---****LIQUIDACIONES
 	drop table if exists #Maestroliq
			SELECT  
				dsaafiliadoprincipal, 
				dsabeneficiariodetalle, 
				COUNT (DISTINCT DSAPERIODOLIQUIDADO) AS canperiodosliquidados,
				dsatipocuotasubsidio,
				dsaEmpleador	
				INTO #Maestroliq
			  FROM MaestroLiquidacion  with(nolock)  
			 WHERE CONVERT(DATE,slsFechaDispersion) BETWEEN @FECHA_INICIAL AND @FECHA_FINAL 
			   AND dsaEstado = 'DERECHO_ASIGNADO' 
			   AND solResultadoProceso = 'DISPERSADA'---agregado para la validación de dispersadas 20230601
			    AND casOrigenTransaccion = 'LIQUIDACION_SUBSIDIO_MONETARIO'
				 AND casTipoTransaccionSubsidio = 'ABONO'
  	 
		  GROUP BY dsaafiliadoprincipal, 
				   dsabeneficiariodetalle,
				   dsatipocuotasubsidio,
				   dsaEmpleador ,dsaPeriodoLiquidado,
				   dsaId 
	 

	 	 CREATE CLUSTERED INDEX INX_Maestroliq ON #Maestroliq (dsaEmpleador,dsaafiliadoprincipal,dsabeneficiariodetalle )

		---******



   
	--/---------------------------------------**********---------------------------------------\--
	--                        Afiliados con derecho a cuota monetaria.
	--\---------------------------------------**********---------------------------------------/--

	----------Tipo Identificacion Aportante----------
	SELECT 
	pr.perid,
	pr.idperempl,

		CASE ISNULL(TipoidEmpl , PR.perTipoIdentificacion)
			WHEN 'CEDULA_CIUDADANIA' THEN '1' 
			WHEN 'TARJETA_IDENTIDAD' THEN '2' 
			WHEN 'REGISTRO_CIVIL' THEN '3'
			WHEN 'CEDULA_EXTRANJERIA' THEN '4'
			WHEN 'NUIP' THEN '5'
			WHEN 'PASAPORTE' THEN '6'
			WHEN 'NIT' THEN '7' 
			WHEN 'CARNE_DIPLOMATICO' THEN '8'
			WHEN 'PERM_ESP_PERMANENCIA' THEN '9' 
			WHEN 'PERM_PROT_TEMPORAL' THEN '15'
			WHEN 'SALVOCONDUCTO' THEN 'Salvoconducto'
		END AS [TIP_IDENTIFICACION],--[Tipo de identificación de la empresa],
		
		----------Numero Identificacion Aportante----------
		ISNULL(pr.idEmpl , PR.perNumeroIdentificacion) AS [NUM_IDENTIFICACION_EMPRESA],--[Número de identificación Empresa],

		----------Tipo Identificacion Afiliado----------
		CASE pr.perTipoIdentificacion  
			WHEN 'CEDULA_CIUDADANIA' THEN '1' 
			WHEN 'TARJETA_IDENTIDAD' THEN '2' 
			WHEN 'REGISTRO_CIVIL' THEN '3'
			WHEN 'CEDULA_EXTRANJERIA' THEN '4'
			WHEN 'NUIP' THEN '5'
			WHEN 'PASAPORTE' THEN '6'
			WHEN 'NIT' THEN '7' 
			WHEN 'CARNE_DIPLOMATICO' THEN '8'
			WHEN 'PERM_ESP_PERMANENCIA' THEN '9' 
			WHEN 'PERM_PROT_TEMPORAL' THEN '15'
			WHEN 'SALVOCONDUCTO' THEN 'Salvoconducto'
		END AS [TIP_IDENTIFICACION_AFILIADO],--[Tipo de Identificación Afiliado],

		----------Numero Identificacion Afiliado----------
		LEFT (pr.perNumeroIdentificacion,15) AS [NUM_IDENTIFICACION_AFILIADO],--[Número de identificación afiliado],
		
		----------Tipo Identificacion Beneficiario----------
		CASE PB.perTipoIdentificacion  
			WHEN 'CEDULA_CIUDADANIA' THEN '1' 
			WHEN 'TARJETA_IDENTIDAD' THEN '2' 
			WHEN 'REGISTRO_CIVIL' THEN '3'
			WHEN 'CEDULA_EXTRANJERIA' THEN '4'
			WHEN 'NUIP' THEN '5'
			WHEN 'PASAPORTE' THEN '6'
			WHEN 'NIT' THEN '7' 
			WHEN 'CARNE_DIPLOMATICO' THEN '8'
			WHEN 'PERM_ESP_PERMANENCIA' THEN '9' 
			WHEN 'PERM_PROT_TEMPORAL' THEN '15'
			WHEN 'SALVOCONDUCTO' THEN 'Salvoconducto'
		END AS [TIP_IDENTIFICACION_PERSONA_A_CARGO],--[Tipo de Identificación de la persona a cargo],

		----------Numero de Identificacion Beneficiario----------
		LEFT (PB.perNumeroIdentificacion,15) AS [NUM_IDENTIFICACION_PERSONA_A_CARGO],--[Número de identificación de la persona a cargo],
		
		----------Primer Nombre----------
		CASE 
			WHEN PB.perPrimerNombre is null THEN '' 
			ELSE  LEFT (PB.perPrimerNombre,30)  
		END AS [PRI_NOMBRE_PERSONA_A_CARGO],--[Primer Nombre de la persona a cargo],

		----------Segundo Nombre----------
		CASE 
			WHEN PB.perSegundoNombre is null THEN '' 
			ELSE  LEFT (PB.perSegundoNombre,30)  
		END  AS [SEG_NOMBRE_PERSONA_A_CARGO],--[Segundo Nombre de la persona a cargo],

		----------Primer Apellido----------
		CASE 
			WHEN PB.perPrimerApellido is null THEN '' 
			ELSE  LEFT (PB.perPrimerApellido,30)  
		END  AS [PRI_APELLIDO_PERSONA_A_CARGO],--[Primer Apellido de la persona a cargo],

		----------Segundo Apellido----------
		CASE 
			WHEN PB.perSegundoApellido is null THEN '' 
			ELSE  LEFT (PB.perSegundoApellido,30)  
		END  AS [SEG_APELLIDO_PERSONA_A_CARGO],--[Segundo Apellido de la persona a cargo],

		----------Fecha Nacimiento----------
		CONVERT (VARCHAR,PDB.pedFechaNacimiento,112) AS [FEC_NACIMIENTO_PERSONA_A_CARGO],--[Fecha de Nacimiento de la persona a cargo],
 
		----------Genero----------
		ISNULL (
			CASE PDB.pedGenero
				WHEN 'FEMENINO' THEN '2'
				WHEN 'MASCULINO' THEN '1'
				WHEN 'INDEFINIDO' THEN'4' 
			END, '3') AS [GEN_PERSONA_A_CARGO],--[Sexo de la persona a cargo],
	
		----------Tipo Beneficiario----------
		CASE BEN.benTipoBeneficiario
			WHEN 'HIJO_BIOLOGICO' THEN '1'
			WHEN 'MADRE' THEN '2'
			WHEN 'PADRE' THEN '2'
			WHEN 'HERMANO_HUERFANO_DE_PADRES' THEN '3'
			WHEN 'HIJASTRO' THEN '4'
			WHEN 'CONYUGE' THEN '5'
			WHEN 'BENEFICIARIO_EN_CUSTODIA' THEN '6'
			WHEN 'HIJO_ADOPTIVO' THEN '6'
			WHEN 'CONYUGE_CUIDADOR' THEN '25' -- AJUSTE GLPI 78393
		END AS [PAR_PERSONA_A_CARGO],--[Parentesco de la persona a cargo],

		----------Codigo Municipio----------
		  pr.munCodigo  AS [COD_MUNICIPIO_RESIDENCIA_DANE],--[Código municipio de residencia de la persona a cargo],
		 
		----------Area Geografica----------
		CASE 
			WHEN pr.pedResideSectorRural = 1 THEN '2' 
			ELSE '1'
		END  AS [ARE_GEOGRAFICA_RESIDENCIA],--[Área Geográfica de Residencia de la persona a cargo],

		 ----grupo étnico y demás del beneficiario

  	 		       CASE pdb.pedPertenenciaEtnica
						WHEN 'AFROCOLOMBIANO' THEN 1
						WHEN 'COMUNIDAD_NEGRA' THEN 2
						WHEN 'INDIGENA' THEN 3
						WHEN 'PALENQUERO' THEN 4 
						WHEN 'RAIZAL_ARCHI_SAN_ANDRES_PROVIDENCIA_SANTA_CATALINA' THEN 5
						WHEN 'ROOM_GITANO' THEN 6
						WHEN 'NINGUNO_DE_LOS_ANTERIORES' THEN 7
						ELSE 8 
					END AS [GRUPO_ETNICO],--[Pertenencia étnica] GLPI 71271

				------resguardo
				ISNULL(pdb.pedResguardo,2) as RESGUARDO,
				----pueblo indigena
				ISNULL(pdb.pedPuebloIndigena,2) as PUEBLO_INDIGENA,
				

		---------- Invalides ? ----------
		CASE ci.coiInvalidez
			WHEN '0' THEN '2'
			WHEN '1' THEN '1' 
			ELSE '2'
		END AS [DISC_PERSONA_A_CARGO],--[Condición de discapacidad de la persona a cargo],

		----------Tipo de Cuota Monetria----------
		CASE 
			WHEN D.dsatipocuotasubsidio = 'REGULAR' 
			 AND D.dsaTipoliquidacionSubsidio <>'ESPECIFICA_FALLECIMIENTO'  THEN '1'---cambio olga vega 20230601
			WHEN D.dsatipocuotasubsidio = 'DISCAPACIDAD'  THEN '2'--cambio olga vega 20230601
			WHEN D.dsatipocuotasubsidio = 'AGRICOLA'   THEN '3'
			WHEN D.dsatipocuotasubsidio = 'DISCAPACIDAD_AGRICOLA'  THEN '4'
			WHEN D.dsaTipoliquidacionSubsidio = 'ESPECIFICA_FALLECIMIENTO'
			 AND D.dsatipocuotasubsidio 
				IN( 
					'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO', 
					'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO', 'REGULAR', 'AGRICOLA'
				) 
			THEN '5' 
			WHEN D.dsaTipoliquidacionSubsidio = 'ESPECIFICA_FALLECIMIENTO'
			 AND D.dsatipocuotasubsidio 
				IN( 
					'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO_AGRICOLA', 
					'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_AGRICOLA'
				) 
			THEN '7' 
			WHEN D.dsaTipoliquidacionSubsidio = 'ESPECIFICA_FALLECIMIENTO'
			 AND D.dsatipocuotasubsidio 
				IN( 
					'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_DISCAPACITADO'
				) 
			THEN '8' 
			WHEN D.dsaTipoliquidacionSubsidio = 'ESPECIFICA_FALLECIMIENTO'
			 AND D.dsatipocuotasubsidio 
				IN( 
					'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_DISCAPACITADO_AGRICOLA'
				) 
			THEN '9' 
			ELSE '6'
		END AS [TIP_CUOTA_MONETARIA_PERSONA_A_CARGO],--[Tipo de cuota monetaria pagado a la persona a cargo],
		
		---------Valor Subsidio----------
		CASE 
			WHEN  sum(D.dsaValorSubsidioMonetario) IS NULL
			THEN 0
			ELSE REPLACE ( sum(D.dsaValorSubsidioMonetario), '.00000','')
		END AS [VAL_CUOTA_MONETARIA_PERSONA_A_CARGO],--[Valor de la cuota monetaria pagada a la persona a cargo],

		---------Cuotas Pagadas----------
		ISNULL (
			CASE
				WHEN D.dsatipocuotasubsidio = 'DISCAPACIDAD' THEN COUNT(DISTINCT dsaid)*2
				WHEN D.dsatipocuotasubsidio = 'DISCAPACIDAD_AGRICOLA' THEN COUNT(DISTINCT dsaid)*2
			 	ELSE ISNULL( count(distinct d.dsaid) , '0')
			END,
		'0') AS [NUM_CUOTAS_PAGADAS],--[Número de cuotas pagadas],

		----------Periodos Liquidados----------
 
		CASE WHEN D.dsaTipoliquidacionSubsidio IN ('ESPECIFICA_CON_AJUSTE_CAMBIO_CONDICION_AGRICOLA',
												   'ESPECIFICA_CON_AJUSTE_VALOR_CUOTA') 
		THEN '0' 
		WHEN (D.dsaTipoliquidacionSubsidio IN ( 'ESPECIFICA_CON_AJUSTE_CAMBIO_CONDICION_DISCAPACIDAD' ) 
		  OR D.dsaTipoliquidacionSubsidio = 'ESPECIFICA_FALLECIMIENTO')
		THEN '1'
		ELSE ISNULL( count(distinct d.dsaPeriodoLiquidado) , '0')
		END AS [NUM_PERIODOS_PAGADAS]--[Numero de periodos pagados]
		---por muerte de beneficiario se coloca una cuota 
 
		INTO #TEMP_AFI_CARGO

		/*declare @FECHA_INICIAL date, @FECHA_FINAL date
set @FECHA_INICIAL = '04-01-2022'
set @FECHA_FINAL = '04-30-2022'

		SELECT *
		
	--	*--, PB.*, D.*/

	         FROM  #PoblacionReporteACargo pr
		
		INNER JOIN #tempSolicitud t WITH (NOLOCK) 
				ON t.sapRolAfiliado = pr.roaid and t.id=1

		INNER JOIN beneficiario ben WITH (nolock) on ben.benafiliado=pr.afiid
			  --AND ben.benFechaAfiliacion <= @FECHA_FINAL 
			    AND ( (ben.benFechaAfiliacion <= @FECHA_FINAL
					 AND ben.benFechaRetiro IS NULL) 
			          OR (ben.benFechaAfiliacion <= @FECHA_FINAL
					 AND ben.benFechaRetiro >= @FECHA_INICIAL ))
		INNER JOIN persona pb WITH (nolock) on pb.perId=ben.benPersona
		INNER JOIN PersonaDetalle pdb WITH (nolock) on pdb.pedPersona=pb.perId
		INNER JOIN BeneficiarioDetalle WITH (nolock) on bedPersonaDetalle=pdb.pedId 
		 LEFT JOIN CondicionInvalidez ci WITH (nolock) ON PB.PERID=coiPersona
		---DetalleSubsidioAsignado D WITH (nolock) ---cambio 20230609
		LEFT JOIN #MiniMaestroRVL D WITH (nolock) ---cambio 20230609
		       ON bedid=dsaBeneficiarioDetalle 
			AND d.dsaEmpleador = pr.roaEmpleador
			AND d.dsaAfiliadoPrincipal = pr.afiid
			and d.rvlesconyugecuidador <>1
			

		LEFT JOIN SolicitudLiquidacionSubsidio SLSubsid on SLSubsid.slsId = d.dsaSolicitudLiquidacionSubsidio

		--LEFT JOIN  #Maestroliq AS CANT ON 
		--	CANT.dsaAfiliadoPrincipal=D.dsaAfiliadoPrincipal AND 
		--	CANT.dsaBeneficiarioDetalle= D.dsaBeneficiarioDetalle AND
		--	CANT.dsaEmpleador = pr.roaEmpleador AND
		--	CANT.dsatipocuotasubsidio  = D.dsatipocuotasubsidio  
 		
		GROUP BY 
		pr.TipoidEmpl,
		pr.idEmpl,
			pr.perTipoIdentificacion,
			pr.perNumeroIdentificacion,
			PB.perTipoIdentificacion,
			PB.perNumeroIdentificacion,
			PB.perPrimerApellido,
			PB.perSegundoApellido,
			PB.perPrimerNombre,
			PB.perSegundoNombre,
			PDB.pedFechaNacimiento, 
			PDB.pedGenero, 
			BEN.benTipoBeneficiario,
			pr.munCodigo,
			pr.pedResideSectorRural,
			ci.coiInvalidez,
			D.dsatipocuotasubsidio,
			D.dsaTipoliquidacionSubsidio,
		 ----grupo étnico y demás del beneficiario
		    pdb.pedPertenenciaEtnica ,--[Pertenencia étnica] GLPI 71271
				------resguardo
			pdb.pedResguardo ,
				----pueblo indigena
			 pdb.pedPuebloIndigena ,
				
			SLSubsid.slsTipoDesembolso ,
	 	pr.perid,
			pr.idperempl
	  


	   CREATE CLUSTERED INDEX id ON #TEMP_AFI_CARGO ([NUM_IDENTIFICACION_EMPRESA],[NUM_IDENTIFICACION_AFILIADO],[NUM_IDENTIFICACION_PERSONA_A_CARGO],[TIP_CUOTA_MONETARIA_PERSONA_A_CARGO])

	 --  SELECT '#TEMP_AFI_CARGO',* FROM #TEMP_AFI_CARGO

		--/---------------------------------------**********---------------------------------------\--
		--   Union de los afiliados con derecho a cuota monetaria y el resto de afiliados del hogar.
		--\---------------------------------------**********---------------------------------------/--
		SELECT DISTINCT
			 	perid,
			idperempl,
			afiliadoCargo.TIP_IDENTIFICACION,
			afiliadoCargo.NUM_IDENTIFICACION_EMPRESA,
			afiliadoCargo.TIP_IDENTIFICACION_AFILIADO,
			afiliadoCargo.NUM_IDENTIFICACION_AFILIADO,
			afiliadoCargo.TIP_IDENTIFICACION_PERSONA_A_CARGO,
			afiliadoCargo.NUM_IDENTIFICACION_PERSONA_A_CARGO,
			afiliadoCargo.PRI_NOMBRE_PERSONA_A_CARGO,
			afiliadoCargo.SEG_NOMBRE_PERSONA_A_CARGO,
			afiliadoCargo.PRI_APELLIDO_PERSONA_A_CARGO,
			afiliadoCargo.SEG_APELLIDO_PERSONA_A_CARGO,
			afiliadoCargo.FEC_NACIMIENTO_PERSONA_A_CARGO,
			afiliadoCargo.GEN_PERSONA_A_CARGO,
			afiliadoCargo.PAR_PERSONA_A_CARGO,
			afiliadoCargo.COD_MUNICIPIO_RESIDENCIA_DANE,
			afiliadoCargo.ARE_GEOGRAFICA_RESIDENCIA,
		   afiliadoCargo.GRUPO_ETNICO ,
            afiliadoCargo.RESGUARDO,
            afiliadoCargo.PUEBLO_INDIGENA,
			afiliadoCargo.DISC_PERSONA_A_CARGO,
			afiliadoCargo.TIP_CUOTA_MONETARIA_PERSONA_A_CARGO,
			SUM(afiliadoCargo.VAL_CUOTA_MONETARIA_PERSONA_A_CARGO ) AS [VAL_CUOTA_MONETARIA_PERSONA_A_CARGO],
			SUM(afiliadoCargo.NUM_CUOTAS_PAGADAS) AS NUM_CUOTAS_PAGADAS,
			SUM(afiliadoCargo.NUM_PERIODOS_PAGADAS) AS NUM_PERIODOS_PAGADAS--,GETDATE() AS FECHA1, GETDATE() AS FECHA2
	--CAMBIO OLGA VEGA 
	INTO #TEMP_AFI_CARGO2
		FROM #TEMP_AFI_CARGO AS afiliadoCargo

		GROUP BY
			 	perid,
			idperempl,
			afiliadoCargo.TIP_IDENTIFICACION,
			afiliadoCargo.NUM_IDENTIFICACION_EMPRESA,
			afiliadoCargo.TIP_IDENTIFICACION_AFILIADO,
			afiliadoCargo.NUM_IDENTIFICACION_AFILIADO,
			afiliadoCargo.TIP_IDENTIFICACION_PERSONA_A_CARGO,
			afiliadoCargo.NUM_IDENTIFICACION_PERSONA_A_CARGO,
			afiliadoCargo.PRI_NOMBRE_PERSONA_A_CARGO,
			afiliadoCargo.SEG_NOMBRE_PERSONA_A_CARGO,
			afiliadoCargo.PRI_APELLIDO_PERSONA_A_CARGO,
			afiliadoCargo.SEG_APELLIDO_PERSONA_A_CARGO,
			afiliadoCargo.FEC_NACIMIENTO_PERSONA_A_CARGO,
			afiliadoCargo.GEN_PERSONA_A_CARGO,
			afiliadoCargo.PAR_PERSONA_A_CARGO,
			afiliadoCargo.COD_MUNICIPIO_RESIDENCIA_DANE,
			afiliadoCargo.ARE_GEOGRAFICA_RESIDENCIA,
			afiliadoCargo.GRUPO_ETNICO ,
            afiliadoCargo.RESGUARDO,
            afiliadoCargo.PUEBLO_INDIGENA,
			afiliadoCargo.DISC_PERSONA_A_CARGO,
			afiliadoCargo.TIP_CUOTA_MONETARIA_PERSONA_A_CARGO
		 


  CREATE CLUSTERED INDEX inx_ids ON #TEMP_AFI_CARGO2 ([NUM_IDENTIFICACION_EMPRESA],[NUM_IDENTIFICACION_AFILIADO],[NUM_IDENTIFICACION_PERSONA_A_CARGO],[TIP_CUOTA_MONETARIA_PERSONA_A_CARGO])


		-----------------------------
--	  	UNION --- PRUEBA
		-----------------------------
 
 -- SELECT '#TEMP_AFI_CARGO2',* FROM #TEMP_AFI_CARGO2
		--/---------------------------------------**********---------------------------------------\--
		--                              Resto de afiliados del hogar.
		--\---------------------------------------**********---------------------------------------/--		
		SELECT DISTINCT
			 pr.perid,
			pr.idperempl,

			CASE ISNULL(PE.perTipoIdentificacion ,pr.perTipoIdentificacion)
				WHEN 'CEDULA_CIUDADANIA' THEN '1' 
				WHEN 'TARJETA_IDENTIDAD' THEN '2' 
				WHEN 'REGISTRO_CIVIL' THEN '3'
				WHEN 'CEDULA_EXTRANJERIA' THEN '4'
				WHEN 'NUIP' THEN '5'
				WHEN 'PASAPORTE' THEN '6'
				WHEN 'NIT' THEN '7' 
				WHEN 'CARNE_DIPLOMATICO' THEN '8'
				WHEN 'PERM_ESP_PERMANENCIA' THEN '9' 
				WHEN 'PERM_PROT_TEMPORAL' THEN '15'
				WHEN 'SALVOCONDUCTO' THEN 'Salvoconducto'
			END AS [TIP_IDENTIFICACION],--[Tipo de identificación de la empresa],
		
			----------Numero Identificacion Aportante----------
			ISNULL(PE.perNumeroIdentificacion ,pr.perNumeroIdentificacion) AS [NUM_IDENTIFICACION_EMPRESA],--[Número de identificación Empresa],

			----------Tipo Identificacion Afiliado----------
			CASE pr.perTipoIdentificacion  
				WHEN 'CEDULA_CIUDADANIA' THEN '1' 
				WHEN 'TARJETA_IDENTIDAD' THEN '2' 
				WHEN 'REGISTRO_CIVIL' THEN '3'
				WHEN 'CEDULA_EXTRANJERIA' THEN '4'
				WHEN 'NUIP' THEN '5'
				WHEN 'PASAPORTE' THEN '6'
				WHEN 'NIT' THEN '7' 
				WHEN 'CARNE_DIPLOMATICO' THEN '8'
				WHEN 'PERM_ESP_PERMANENCIA' THEN '9' 
				WHEN 'PERM_PROT_TEMPORAL' THEN '15'
				WHEN 'SALVOCONDUCTO' THEN 'Salvoconducto'
			END AS [TIP_IDENTIFICACION_AFILIADO],--[Tipo de Identificación Afiliado],

			----------Numero Identificacion Afiliado----------
			LEFT (pr.perNumeroIdentificacion,15) AS [NUM_IDENTIFICACION_AFILIADO],--[Número de identificación afiliado],
		
			----------Tipo Identificacion Beneficiario----------
			CASE PB.perTipoIdentificacion  
				WHEN 'CEDULA_CIUDADANIA' THEN '1' 
				WHEN 'TARJETA_IDENTIDAD' THEN '2' 
				WHEN 'REGISTRO_CIVIL' THEN '3'
				WHEN 'CEDULA_EXTRANJERIA' THEN '4'
				WHEN 'NUIP' THEN '5'
				WHEN 'PASAPORTE' THEN '6'
				WHEN 'NIT' THEN '7' 
				WHEN 'CARNE_DIPLOMATICO' THEN '8'
				WHEN 'PERM_ESP_PERMANENCIA' THEN '9' 
				WHEN 'PERM_PROT_TEMPORAL' THEN '15'
				WHEN 'SALVOCONDUCTO' THEN 'Salvoconducto'
			END AS [TIP_IDENTIFICACION_PERSONA_A_CARGO],--[Tipo de Identificación de la persona a cargo],

			----------Numero de Identificacion Beneficiario----------
			LEFT (PB.perNumeroIdentificacion,15) AS [NUM_IDENTIFICACION_PERSONA_A_CARGO],--[Número de identificación de la persona a cargo],
		
			----------Primer Nombre----------
			CASE 
				WHEN PB.perPrimerNombre is null THEN '' 
				ELSE  LEFT (PB.perPrimerNombre,30)  
			END AS [PRI_NOMBRE_PERSONA_A_CARGO],--[Primer Nombre de la persona a cargo],

			----------Segundo Nombre----------
			CASE 
				WHEN PB.perSegundoNombre is null THEN '' 
				ELSE  LEFT (PB.perSegundoNombre,30)  
			END  AS [SEG_NOMBRE_PERSONA_A_CARGO],--[Segundo Nombre de la persona a cargo],

			----------Primer Apellido----------
			CASE 
				WHEN PB.perPrimerApellido is null THEN '' 
				ELSE  LEFT (PB.perPrimerApellido,30)  
			END  AS [PRI_APELLIDO_PERSONA_A_CARGO],--[Primer Apellido de la persona a cargo],

			----------Segundo Apellido----------
			CASE 
				WHEN PB.perSegundoApellido is null THEN '' 
				ELSE  LEFT (PB.perSegundoApellido,30)  
			END  AS [SEG_APELLIDO_PERSONA_A_CARGO],--[Segundo Apellido de la persona a cargo],

			----------Fecha Nacimiento----------
			CONVERT (VARCHAR,PDB.pedFechaNacimiento,112) AS [FEC_NACIMIENTO_PERSONA_A_CARGO],--[Fecha de Nacimiento de la persona a cargo],

			----------Genero----------
			ISNULL (
				CASE PDB.pedGenero
					WHEN 'FEMENINO' THEN '2'
					WHEN 'MASCULINO' THEN '1'
					WHEN 'INDEFINIDO' THEN'4' 
				END, '3') AS [GEN_PERSONA_A_CARGO],--[Sexo de la persona a cargo],
	
			----------Tipo Beneficiario----------
			CASE BEN.benTipoBeneficiario
				WHEN 'HIJO_BIOLOGICO' THEN '1'
				WHEN 'MADRE' THEN '2'
				WHEN 'PADRE' THEN '2'
				WHEN 'HERMANO_HUERFANO_DE_PADRES' THEN '3'
				WHEN 'HIJASTRO' THEN '4'
				WHEN 'CONYUGE' THEN '5'
				WHEN 'BENEFICIARIO_EN_CUSTODIA' THEN '6'
				WHEN 'HIJO_ADOPTIVO' THEN '6'
			END AS [PAR_PERSONA_A_CARGO],--[Parentesco de la persona a cargo],

			----------Codigo Municipio----------
			 pr.munCodigo  AS [COD_MUNICIPIO_RESIDENCIA_DANE],--[Código municipio de residencia de la persona a cargo],

			----------Area Geografica----------
			CASE 
				WHEN pr.pedResideSectorRural = 1 THEN '2' 
				ELSE '1'
			END  AS [ARE_GEOGRAFICA_RESIDENCIA],--[Área Geográfica de Residencia de la persona a cargo],
			 ----grupo étnico y demás del beneficiario

  	 		       CASE pdb.pedPertenenciaEtnica
						WHEN 'AFROCOLOMBIANO' THEN 1
						WHEN 'COMUNIDAD_NEGRA' THEN 2
						WHEN 'INDIGENA' THEN 3
						WHEN 'PALENQUERO' THEN 4 
						WHEN 'RAIZAL_ARCHI_SAN_ANDRES_PROVIDENCIA_SANTA_CATALINA' THEN 5
						WHEN 'ROOM_GITANO' THEN 6
						WHEN 'NINGUNO_DE_LOS_ANTERIORES' THEN 7
						ELSE 8 
					END AS [GRUPO_ETNICO],--[Pertenencia étnica] GLPI 71271

				------resguardo
				ISNULL(pdb.pedResguardo,2) as RESGUARDO,
				----pueblo indigena
				ISNULL(pdb.pedPuebloIndigena,2) as PUEBLO_INDIGENA,
				
			 -----
			---------- Invalides ? ----------
			CASE ci.coiInvalidez
				WHEN '0' THEN '2'
				WHEN '1' THEN '1' 
				ELSE '2'
			END AS [DISC_PERSONA_A_CARGO],--[Condición de discapacidad de la persona a cargo],

		----------Tipo de Cuota Monetria----------
		CASE 
			WHEN D.dsatipocuotasubsidio = 'REGULAR' 
			 AND D.dsaTipoliquidacionSubsidio <>'ESPECIFICA_FALLECIMIENTO'  THEN '1'---cambio olga vega 20230601
			WHEN D.dsatipocuotasubsidio = 'DISCAPACIDAD' 
			 AND D.dsaTipoliquidacionSubsidio <>'ESPECIFICA_FALLECIMIENTO' THEN '2'--cambio olga vega 20230601
			WHEN D.dsatipocuotasubsidio = 'AGRICOLA' THEN '3'
			WHEN D.dsatipocuotasubsidio = 'DISCAPACIDAD_AGRICOLA' THEN '4'

			WHEN D.dsatipocuotasubsidio 
				IN( 
					'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO', 
					'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO'
				) OR D.dsaTipoliquidacionSubsidio = 'ESPECIFICA_FALLECIMIENTO'
			THEN '5' 

			WHEN D.dsatipocuotasubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO_AGRICOLA' THEN '7'
			WHEN D.dsatipocuotasubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_AGRICOLA' THEN '7'
			WHEN D.dsatipocuotasubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_DISCAPACITADO' THEN '8'
			WHEN D.dsatipocuotasubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_DISCAPACITADO_AGRICOLA' THEN '9'
			WHEN D.dsatipocuotasubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO_AGRICOLA_BENEFICIARIO_DISCAPACITADO' THEN '9' 
			ELSE '6'
		END AS [TIP_CUOTA_MONETARIA_PERSONA_A_CARGO],--[Tipo de cuota monetaria pagado a la persona a cargo],
		
		---------Valor Subsidio----------
		CASE 
			WHEN  sum(D.dsaValorSubsidioMonetario) IS NULL
			THEN 0
			ELSE REPLACE (sum(D.dsaValorSubsidioMonetario), '.00000','')
		END AS [VAL_CUOTA_MONETARIA_PERSONA_A_CARGO],--[Valor de la cuota monetaria pagada a la persona a cargo],

		---------Cuotas Pagadas----------
		ISNULL (
			CASE
				WHEN D.dsatipocuotasubsidio = 'DISCAPACIDAD' THEN COUNT(DISTINCT dsaid)*2
				WHEN D.dsatipocuotasubsidio = 'DISCAPACIDAD_AGRICOLA' THEN COUNT(DISTINCT dsaid)*2
				ELSE  COUNT(DISTINCT d.dsaId) 
			END,
		'0') AS [NUM_CUOTAS_PAGADAS],--[Número de cuotas pagadas],

		----------Periodos Liquidados----------
		CASE WHEN D.dsaTipoliquidacionSubsidio IN ('ESPECIFICA_CON_AJUSTE_CAMBIO_CONDICION_AGRICOLA',
												   'ESPECIFICA_CON_AJUSTE_VALOR_CUOTA') 
		THEN '0' 
		WHEN (D.dsaTipoliquidacionSubsidio IN ( 'ESPECIFICA_CON_AJUSTE_CAMBIO_CONDICION_DISCAPACIDAD' ) 
		  OR D.dsaTipoliquidacionSubsidio = 'ESPECIFICA_FALLECIMIENTO')--CAMBIO 20230601
		THEN '1'
		ELSE  ISNULL(COUNT(DISTINCT D.dsaPeriodoLiquidado), '0')  
		END
		AS [NUM_PERIODOS_PAGADAS]--[Numero de periodos pagados]
		--	,ROA.roaFechaAfiliacion, benFechaAfiliacion
	---	,d.dsaId

 --CAMBIO OLGA VEGA 20230707
 INTO #TEMP_AFI_CARGO3

		FROM  #PoblacionReporteACargo pr
		
		LEFT JOIN empleador empl WITH (nolock) on pr.roaEmpleador=empl.empid  
		LEFT JOIN empresa emp WITH (nolock) on empl.empempresa=emp.empid
		LEFT JOIN persona pe on pe.perid = emp.emppersona 
 
		INNER JOIN beneficiario ben WITH (nolock) on ben.benafiliado=pr.afiid
		INNER JOIN persona pb WITH (nolock) on pb.perId=ben.benPersona
		INNER JOIN PersonaDetalle pdb WITH (nolock) on pdb.pedPersona=pb.perId
		INNER JOIN BeneficiarioDetalle WITH (nolock) on bedPersonaDetalle=pdb.pedId
		 LEFT JOIN CondicionInvalidez ci WITH (nolock) ON PB.PERID=coiPersona
	----SI RECIBIERON SUBSIDIO
		INNER JOIN #MiniMaestroRVL D WITH (nolock) 
		     ON bedid=dsaBeneficiarioDetalle 
			AND d.dsaEmpleador = pr.roaEmpleador
			AND d.dsaAfiliadoPrincipal = pr.afiid	
						and d.rvlesconyugecuidador <>1
  			--CONDICION LIQUIDADOS
			where PB.perNumeroIdentificacion NOT IN (
				SELECT AfiCuo.NUM_IDENTIFICACION_PERSONA_A_CARGO
				  FROM #TEMP_AFI_CARGO AfiCuo
				WHERE AfiCuo.idperempl = pr.idperempl
				and AfiCuo.perId =pr.perId	
			)
			-- AND
			--AfiCuo.NUM_IDENTIFICACION_EMPRESA = pe.perNumeroIdentificacion AND
	 	--	pr.roaEstadoAfiliado IN ('INACTIVO','ACTIVO')---- MODIFICACION OLGA VEGA 20220811--PRUEBA20232525---glpi 73465 20231121

		 --	AND (pr.roaFechaRetiro <= @FECHA_FINAL    prueba por los activos con ben inactivos
		  --   AND CONVERT(DATE,ben.benFechaAfiliacion) <= @FECHA_FINAL --- solo porque tiene subsidio 20230707  
		
	 	    --   AND D.NumeroidentificacionBen = '1056127026'
			
		GROUP BY 
			pr.perid,
			pr.idperempl,
			PE.perTipoIdentificacion, 
			PE.perNumeroIdentificacion,
			PE.perDigitoVerificacion,
			pr.perTipoIdentificacion,
			pr.perNumeroIdentificacion,
			PB.perTipoIdentificacion,
			PB.perNumeroIdentificacion,
			PB.perPrimerApellido,
			PB.perSegundoApellido,
			PB.perPrimerNombre,
			PB.perSegundoNombre,
			PDB.pedFechaNacimiento, 
			PDB.pedGenero, 
			BEN.benTipoBeneficiario,
			pr.munCodigo,
			pr.pedResideSectorRural,
			ci.coiInvalidez, pr.roaFechaAfiliacion,--dsaPeriodoLiquidado,
			ben.benFechaAfiliacion, dsaTipoCuotaSubsidio, 
			dsaTipoliquidacionSubsidio, slsTipoDesembolso ,
			--d.dsaId,	 
			pdb.pedPertenenciaEtnica,pdb.pedResguardo,pdb.pedPuebloIndigena
 

   CREATE CLUSTERED INDEX inx_ids ON #TEMP_AFI_CARGO3 ([NUM_IDENTIFICACION_EMPRESA],[NUM_IDENTIFICACION_AFILIADO],[NUM_IDENTIFICACION_PERSONA_A_CARGO],[TIP_CUOTA_MONETARIA_PERSONA_A_CARGO])
   

 --
 	--/---------------------------------------**********---------------------------------------\--
		--   Union de los afiliados con derecho a cuota monetaria y el resto de afiliados del hogar 2.
		--\---------------------------------------**********---------------------------------------/--
		SELECT DISTINCT
			afiliadoCargo.perid,
			afiliadoCargo.idperempl,
			afiliadoCargo.TIP_IDENTIFICACION,
			afiliadoCargo.NUM_IDENTIFICACION_EMPRESA,
			afiliadoCargo.TIP_IDENTIFICACION_AFILIADO,
			afiliadoCargo.NUM_IDENTIFICACION_AFILIADO,
			afiliadoCargo.TIP_IDENTIFICACION_PERSONA_A_CARGO,
			afiliadoCargo.NUM_IDENTIFICACION_PERSONA_A_CARGO,
			afiliadoCargo.PRI_NOMBRE_PERSONA_A_CARGO,
			afiliadoCargo.SEG_NOMBRE_PERSONA_A_CARGO,
			afiliadoCargo.PRI_APELLIDO_PERSONA_A_CARGO,
			afiliadoCargo.SEG_APELLIDO_PERSONA_A_CARGO,
			afiliadoCargo.FEC_NACIMIENTO_PERSONA_A_CARGO,
			afiliadoCargo.GEN_PERSONA_A_CARGO,
			afiliadoCargo.PAR_PERSONA_A_CARGO,
			afiliadoCargo.COD_MUNICIPIO_RESIDENCIA_DANE,
			afiliadoCargo.ARE_GEOGRAFICA_RESIDENCIA,
			afiliadoCargo.GRUPO_ETNICO ,
            afiliadoCargo.RESGUARDO,
            afiliadoCargo.PUEBLO_INDIGENA,
			afiliadoCargo.DISC_PERSONA_A_CARGO,
			afiliadoCargo.TIP_CUOTA_MONETARIA_PERSONA_A_CARGO,
			SUM( afiliadoCargo.VAL_CUOTA_MONETARIA_PERSONA_A_CARGO ) AS [VAL_CUOTA_MONETARIA_PERSONA_A_CARGO],
			SUM( afiliadoCargo.NUM_CUOTAS_PAGADAS) AS NUM_CUOTAS_PAGADAS,
			SUM( afiliadoCargo.NUM_PERIODOS_PAGADAS) AS NUM_PERIODOS_PAGADAS--,GETDATE() AS FECHA1, GETDATE() AS FECHA2
--CAMBIO OLGA VEGA 
		INTO #TEMP_AFI_CARGO4
		FROM #TEMP_AFI_CARGO3 AS afiliadoCargo
	
		GROUP BY
			afiliadoCargo.perid,
			afiliadoCargo.idperempl,
			afiliadoCargo.TIP_IDENTIFICACION,
			afiliadoCargo.NUM_IDENTIFICACION_EMPRESA,
			afiliadoCargo.TIP_IDENTIFICACION_AFILIADO,
			afiliadoCargo.NUM_IDENTIFICACION_AFILIADO,
			afiliadoCargo.TIP_IDENTIFICACION_PERSONA_A_CARGO,
			afiliadoCargo.NUM_IDENTIFICACION_PERSONA_A_CARGO,
			afiliadoCargo.PRI_NOMBRE_PERSONA_A_CARGO,
			afiliadoCargo.SEG_NOMBRE_PERSONA_A_CARGO,
			afiliadoCargo.PRI_APELLIDO_PERSONA_A_CARGO,
			afiliadoCargo.SEG_APELLIDO_PERSONA_A_CARGO,
			afiliadoCargo.FEC_NACIMIENTO_PERSONA_A_CARGO,
			afiliadoCargo.GEN_PERSONA_A_CARGO,
			afiliadoCargo.PAR_PERSONA_A_CARGO,
			afiliadoCargo.COD_MUNICIPIO_RESIDENCIA_DANE,
			afiliadoCargo.ARE_GEOGRAFICA_RESIDENCIA,
			afiliadoCargo.DISC_PERSONA_A_CARGO,
			afiliadoCargo.TIP_CUOTA_MONETARIA_PERSONA_A_CARGO,
			afiliadoCargo.GRUPO_ETNICO ,
            afiliadoCargo.RESGUARDO,
            afiliadoCargo.PUEBLO_INDIGENA 
 
 --  SELECT '#TEMP_AFI_CARGO',* FROM #TEMP_AFI_CARGO 
 -- SELECT '#TEMP_AFI_CARGO2',* FROM #TEMP_AFI_CARGO2
 -- SELECT '#TEMP_AFI_CARGO3',* FROM #TEMP_AFI_CARGO3
 --SELECT '#TEMP_AFI_CARGO4',* FROM #TEMP_AFI_CARGO4

 --CUOTAS DE CONYUGE CUIDADOR EN BENEFICIARIO
 drop table if exists #TEMP_AFI_CARGO5
 	SELECT DISTINCT
			 pr.perid,
			pr.idperempl,

			CASE ISNULL(PE.perTipoIdentificacion ,pr.perTipoIdentificacion)
				WHEN 'CEDULA_CIUDADANIA' THEN '1' 
				WHEN 'TARJETA_IDENTIDAD' THEN '2' 
				WHEN 'REGISTRO_CIVIL' THEN '3'
				WHEN 'CEDULA_EXTRANJERIA' THEN '4'
				WHEN 'NUIP' THEN '5'
				WHEN 'PASAPORTE' THEN '6'
				WHEN 'NIT' THEN '7' 
				WHEN 'CARNE_DIPLOMATICO' THEN '8'
				WHEN 'PERM_ESP_PERMANENCIA' THEN '9' 
				WHEN 'PERM_PROT_TEMPORAL' THEN '15'
				WHEN 'SALVOCONDUCTO' THEN 'Salvoconducto'
			END AS [TIP_IDENTIFICACION],--[Tipo de identificación de la empresa],
		
			----------Numero Identificacion Aportante----------
			ISNULL(PE.perNumeroIdentificacion ,pr.perNumeroIdentificacion) AS [NUM_IDENTIFICACION_EMPRESA],--[Número de identificación Empresa],

			----------Tipo Identificacion Afiliado----------
			CASE pr.perTipoIdentificacion  
				WHEN 'CEDULA_CIUDADANIA' THEN '1' 
				WHEN 'TARJETA_IDENTIDAD' THEN '2' 
				WHEN 'REGISTRO_CIVIL' THEN '3'
				WHEN 'CEDULA_EXTRANJERIA' THEN '4'
				WHEN 'NUIP' THEN '5'
				WHEN 'PASAPORTE' THEN '6'
				WHEN 'NIT' THEN '7' 
				WHEN 'CARNE_DIPLOMATICO' THEN '8'
				WHEN 'PERM_ESP_PERMANENCIA' THEN '9' 
				WHEN 'PERM_PROT_TEMPORAL' THEN '15'
				WHEN 'SALVOCONDUCTO' THEN 'Salvoconducto'
			END AS [TIP_IDENTIFICACION_AFILIADO],--[Tipo de Identificación Afiliado],

			----------Numero Identificacion Afiliado----------
			LEFT (pr.perNumeroIdentificacion,15) AS [NUM_IDENTIFICACION_AFILIADO],--[Número de identificación afiliado],
		
			----------Tipo Identificacion Beneficiario----------
			CASE PB.perTipoIdentificacion  
				WHEN 'CEDULA_CIUDADANIA' THEN '1' 
				WHEN 'TARJETA_IDENTIDAD' THEN '2' 
				WHEN 'REGISTRO_CIVIL' THEN '3'
				WHEN 'CEDULA_EXTRANJERIA' THEN '4'
				WHEN 'NUIP' THEN '5'
				WHEN 'PASAPORTE' THEN '6'
				WHEN 'NIT' THEN '7' 
				WHEN 'CARNE_DIPLOMATICO' THEN '8'
				WHEN 'PERM_ESP_PERMANENCIA' THEN '9' 
				WHEN 'PERM_PROT_TEMPORAL' THEN '15'
				WHEN 'SALVOCONDUCTO' THEN 'Salvoconducto'
			END AS [TIP_IDENTIFICACION_PERSONA_A_CARGO],--[Tipo de Identificación de la persona a cargo],

			----------Numero de Identificacion Beneficiario----------
			LEFT (PB.perNumeroIdentificacion,15) AS [NUM_IDENTIFICACION_PERSONA_A_CARGO],--[Número de identificación de la persona a cargo],
		
			----------Primer Nombre----------
			CASE 
				WHEN PB.perPrimerNombre is null THEN '' 
				ELSE  LEFT (PB.perPrimerNombre,30)  
			END AS [PRI_NOMBRE_PERSONA_A_CARGO],--[Primer Nombre de la persona a cargo],

			----------Segundo Nombre----------
			CASE 
				WHEN PB.perSegundoNombre is null THEN '' 
				ELSE  LEFT (PB.perSegundoNombre,30)  
			END  AS [SEG_NOMBRE_PERSONA_A_CARGO],--[Segundo Nombre de la persona a cargo],

			----------Primer Apellido----------
			CASE 
				WHEN PB.perPrimerApellido is null THEN '' 
				ELSE  LEFT (PB.perPrimerApellido,30)  
			END  AS [PRI_APELLIDO_PERSONA_A_CARGO],--[Primer Apellido de la persona a cargo],

			----------Segundo Apellido----------
			CASE 
				WHEN PB.perSegundoApellido is null THEN '' 
				ELSE  LEFT (PB.perSegundoApellido,30)  
			END  AS [SEG_APELLIDO_PERSONA_A_CARGO],--[Segundo Apellido de la persona a cargo],

			----------Fecha Nacimiento----------
			CONVERT (VARCHAR,PDB.pedFechaNacimiento,112) AS [FEC_NACIMIENTO_PERSONA_A_CARGO],--[Fecha de Nacimiento de la persona a cargo],

			----------Genero----------
			ISNULL (
				CASE PDB.pedGenero
					WHEN 'FEMENINO' THEN '2'
					WHEN 'MASCULINO' THEN '1'
					WHEN 'INDEFINIDO' THEN'4' 
				END, '3') AS [GEN_PERSONA_A_CARGO],--[Sexo de la persona a cargo],
	
			----------Tipo Beneficiario----------
			CASE BEN.benTipoBeneficiario
				WHEN 'HIJO_BIOLOGICO' THEN '1'
				WHEN 'MADRE' THEN '2'
				WHEN 'PADRE' THEN '2'
				WHEN 'HERMANO_HUERFANO_DE_PADRES' THEN '3'
				WHEN 'HIJASTRO' THEN '4'
				WHEN 'CONYUGE' THEN '5'
				WHEN 'BENEFICIARIO_EN_CUSTODIA' THEN '6'
				WHEN 'HIJO_ADOPTIVO' THEN '6'
			END AS [PAR_PERSONA_A_CARGO],--[Parentesco de la persona a cargo],

			----------Codigo Municipio----------
			 pr.munCodigo  AS [COD_MUNICIPIO_RESIDENCIA_DANE],--[Código municipio de residencia de la persona a cargo],

			----------Area Geografica----------
			CASE 
				WHEN pr.pedResideSectorRural = 1 THEN '2' 
				ELSE '1'
			END  AS [ARE_GEOGRAFICA_RESIDENCIA],--[Área Geográfica de Residencia de la persona a cargo],
			 ----grupo étnico y demás del beneficiario

  	 		       CASE pdb.pedPertenenciaEtnica
						WHEN 'AFROCOLOMBIANO' THEN 1
						WHEN 'COMUNIDAD_NEGRA' THEN 2
						WHEN 'INDIGENA' THEN 3
						WHEN 'PALENQUERO' THEN 4 
						WHEN 'RAIZAL_ARCHI_SAN_ANDRES_PROVIDENCIA_SANTA_CATALINA' THEN 5
						WHEN 'ROOM_GITANO' THEN 6
						WHEN 'NINGUNO_DE_LOS_ANTERIORES' THEN 7
						ELSE 8 
					END AS [GRUPO_ETNICO],--[Pertenencia étnica] GLPI 71271

				------resguardo
				ISNULL(pdb.pedResguardo,2) as RESGUARDO,
				----pueblo indigena
				ISNULL(pdb.pedPuebloIndigena,2) as PUEBLO_INDIGENA,
				
			 -----
			---------- Invalides ? ----------
			CASE ci.coiInvalidez
				WHEN '0' THEN '2'
				WHEN '1' THEN '1' 
				ELSE '2'
			END AS [DISC_PERSONA_A_CARGO],--[Condición de discapacidad de la persona a cargo],

		----------Tipo de Cuota Monetria----------
		CASE 
			WHEN D.dsatipocuotasubsidio = 'REGULAR' 
			 AND D.dsaTipoliquidacionSubsidio <>'ESPECIFICA_FALLECIMIENTO'  THEN '1'---cambio olga vega 20230601
			WHEN D.dsatipocuotasubsidio = 'DISCAPACIDAD' 
			 AND D.dsaTipoliquidacionSubsidio <>'ESPECIFICA_FALLECIMIENTO' THEN '2'--cambio olga vega 20230601
			WHEN D.dsatipocuotasubsidio = 'AGRICOLA' THEN '3'
			WHEN D.dsatipocuotasubsidio = 'DISCAPACIDAD_AGRICOLA' THEN '4'

			WHEN D.dsatipocuotasubsidio 
				IN( 
					'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO', 
					'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO'
				) OR D.dsaTipoliquidacionSubsidio = 'ESPECIFICA_FALLECIMIENTO'
			THEN '5' 

			WHEN D.dsatipocuotasubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO_AGRICOLA' THEN '7'
			WHEN D.dsatipocuotasubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_AGRICOLA' THEN '7'
			WHEN D.dsatipocuotasubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_DISCAPACITADO' THEN '8'
			WHEN D.dsatipocuotasubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_DISCAPACITADO_AGRICOLA' THEN '9'
			WHEN D.dsatipocuotasubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO_AGRICOLA_BENEFICIARIO_DISCAPACITADO' THEN '9' 
			ELSE '6'
		END AS [TIP_CUOTA_MONETARIA_PERSONA_A_CARGO],--[Tipo de cuota monetaria pagado a la persona a cargo],
		
		---------Valor Subsidio----------
		CASE 
			WHEN  sum(D.dsaValorSubsidioMonetario) IS NULL
			THEN 0
			ELSE (sum(D.dsaValorSubsidioMonetario)/3)*2
		END AS [VAL_CUOTA_MONETARIA_PERSONA_A_CARGO],--[Valor de la cuota monetaria pagada a la persona a cargo],

		---------Cuotas Pagadas----------
		ISNULL (
			CASE
				WHEN D.dsatipocuotasubsidio = 'DISCAPACIDAD' THEN COUNT(DISTINCT dsaid)*2
				WHEN D.dsatipocuotasubsidio = 'DISCAPACIDAD_AGRICOLA' THEN COUNT(DISTINCT dsaid)*2
				ELSE  COUNT(DISTINCT d.dsaId) 
			END,
		'0') AS [NUM_CUOTAS_PAGADAS],--[Número de cuotas pagadas],

		----------Periodos Liquidados----------
		CASE WHEN D.dsaTipoliquidacionSubsidio IN ('ESPECIFICA_CON_AJUSTE_CAMBIO_CONDICION_AGRICOLA',
												   'ESPECIFICA_CON_AJUSTE_VALOR_CUOTA') 
		THEN '0' 
		WHEN (D.dsaTipoliquidacionSubsidio IN ( 'ESPECIFICA_CON_AJUSTE_CAMBIO_CONDICION_DISCAPACIDAD' ) 
		  OR D.dsaTipoliquidacionSubsidio = 'ESPECIFICA_FALLECIMIENTO')--CAMBIO 20230601
		THEN '1'
		ELSE  ISNULL(COUNT(DISTINCT D.dsaPeriodoLiquidado), '0')  
		END
		AS [NUM_PERIODOS_PAGADAS]--[Numero de periodos pagados]

 INTO #TEMP_AFI_CARGO5

		FROM  #PoblacionReporteACargo pr
		
		LEFT JOIN empleador empl WITH (nolock) on pr.roaEmpleador=empl.empid  
		LEFT JOIN empresa emp WITH (nolock) on empl.empempresa=emp.empid
		LEFT JOIN persona pe on pe.perid = emp.emppersona 
 
		INNER JOIN beneficiario ben WITH (nolock) on ben.benafiliado=pr.afiid
		INNER JOIN persona pb WITH (nolock) on pb.perId=ben.benPersona
		INNER JOIN PersonaDetalle pdb WITH (nolock) on pdb.pedPersona=pb.perId
		INNER JOIN BeneficiarioDetalle WITH (nolock) on bedPersonaDetalle=pdb.pedId
		 LEFT JOIN CondicionInvalidez ci WITH (nolock) ON PB.PERID=coiPersona 

	----SI RECIBIERON SUBSIDIO
		INNER JOIN #MiniMaestroRVL D WITH (nolock) 
		     ON bedid=dsaBeneficiarioDetalle 
			AND d.dsaEmpleador = pr.roaEmpleador
			AND d.dsaAfiliadoPrincipal = pr.afiid	
			and d.rvlesconyugecuidador =1
		
	
			
		GROUP BY 
			pr.perid,
			pr.idperempl,
			PE.perTipoIdentificacion, 
			PE.perNumeroIdentificacion,
			PE.perDigitoVerificacion,
			pr.perTipoIdentificacion,
			pr.perNumeroIdentificacion,
			PB.perTipoIdentificacion,
			PB.perNumeroIdentificacion,
			PB.perPrimerApellido,
			PB.perSegundoApellido,
			PB.perPrimerNombre,
			PB.perSegundoNombre,
			PDB.pedFechaNacimiento, 
			PDB.pedGenero, 
			BEN.benTipoBeneficiario,
			pr.munCodigo,
			pr.pedResideSectorRural,
			ci.coiInvalidez, pr.roaFechaAfiliacion,--dsaPeriodoLiquidado,
			ben.benFechaAfiliacion, dsaTipoCuotaSubsidio, 
			dsaTipoliquidacionSubsidio, slsTipoDesembolso ,
			--d.dsaId,	 
			pdb.pedPertenenciaEtnica,pdb.pedResguardo,pdb.pedPuebloIndigena

			union

			 
 	SELECT DISTINCT
			 pr.perid,
			pr.idperempl,

			CASE ISNULL(PE.perTipoIdentificacion ,pr.perTipoIdentificacion)
				WHEN 'CEDULA_CIUDADANIA' THEN '1' 
				WHEN 'TARJETA_IDENTIDAD' THEN '2' 
				WHEN 'REGISTRO_CIVIL' THEN '3'
				WHEN 'CEDULA_EXTRANJERIA' THEN '4'
				WHEN 'NUIP' THEN '5'
				WHEN 'PASAPORTE' THEN '6'
				WHEN 'NIT' THEN '7' 
				WHEN 'CARNE_DIPLOMATICO' THEN '8'
				WHEN 'PERM_ESP_PERMANENCIA' THEN '9' 
				WHEN 'PERM_PROT_TEMPORAL' THEN '15'
				WHEN 'SALVOCONDUCTO' THEN 'Salvoconducto'
			END AS [TIP_IDENTIFICACION],--[Tipo de identificación de la empresa],
		
			----------Numero Identificacion Aportante----------
			ISNULL(PE.perNumeroIdentificacion ,pr.perNumeroIdentificacion) AS [NUM_IDENTIFICACION_EMPRESA],--[Número de identificación Empresa],

			----------Tipo Identificacion Afiliado----------
			CASE pr.perTipoIdentificacion  
				WHEN 'CEDULA_CIUDADANIA' THEN '1' 
				WHEN 'TARJETA_IDENTIDAD' THEN '2' 
				WHEN 'REGISTRO_CIVIL' THEN '3'
				WHEN 'CEDULA_EXTRANJERIA' THEN '4'
				WHEN 'NUIP' THEN '5'
				WHEN 'PASAPORTE' THEN '6'
				WHEN 'NIT' THEN '7' 
				WHEN 'CARNE_DIPLOMATICO' THEN '8'
				WHEN 'PERM_ESP_PERMANENCIA' THEN '9' 
				WHEN 'PERM_PROT_TEMPORAL' THEN '15'
				WHEN 'SALVOCONDUCTO' THEN 'Salvoconducto'
			END AS [TIP_IDENTIFICACION_AFILIADO],--[Tipo de Identificación Afiliado],

			----------Numero Identificacion Afiliado----------
			LEFT (pr.perNumeroIdentificacion,15) AS [NUM_IDENTIFICACION_AFILIADO],--[Número de identificación afiliado],
		
			----------Tipo Identificacion Beneficiario----------
			CASE PB.perTipoIdentificacion  
				WHEN 'CEDULA_CIUDADANIA' THEN '1' 
				WHEN 'TARJETA_IDENTIDAD' THEN '2' 
				WHEN 'REGISTRO_CIVIL' THEN '3'
				WHEN 'CEDULA_EXTRANJERIA' THEN '4'
				WHEN 'NUIP' THEN '5'
				WHEN 'PASAPORTE' THEN '6'
				WHEN 'NIT' THEN '7' 
				WHEN 'CARNE_DIPLOMATICO' THEN '8'
				WHEN 'PERM_ESP_PERMANENCIA' THEN '9' 
				WHEN 'PERM_PROT_TEMPORAL' THEN '15'
				WHEN 'SALVOCONDUCTO' THEN 'Salvoconducto'
			END AS [TIP_IDENTIFICACION_PERSONA_A_CARGO],--[Tipo de Identificación de la persona a cargo],

			----------Numero de Identificacion Beneficiario----------
			LEFT (PB.perNumeroIdentificacion,15) AS [NUM_IDENTIFICACION_PERSONA_A_CARGO],--[Número de identificación de la persona a cargo],
		
			----------Primer Nombre----------
			CASE 
				WHEN PB.perPrimerNombre is null THEN '' 
				ELSE  LEFT (PB.perPrimerNombre,30)  
			END AS [PRI_NOMBRE_PERSONA_A_CARGO],--[Primer Nombre de la persona a cargo],

			----------Segundo Nombre----------
			CASE 
				WHEN PB.perSegundoNombre is null THEN '' 
				ELSE  LEFT (PB.perSegundoNombre,30)  
			END  AS [SEG_NOMBRE_PERSONA_A_CARGO],--[Segundo Nombre de la persona a cargo],

			----------Primer Apellido----------
			CASE 
				WHEN PB.perPrimerApellido is null THEN '' 
				ELSE  LEFT (PB.perPrimerApellido,30)  
			END  AS [PRI_APELLIDO_PERSONA_A_CARGO],--[Primer Apellido de la persona a cargo],

			----------Segundo Apellido----------
			CASE 
				WHEN PB.perSegundoApellido is null THEN '' 
				ELSE  LEFT (PB.perSegundoApellido,30)  
			END  AS [SEG_APELLIDO_PERSONA_A_CARGO],--[Segundo Apellido de la persona a cargo],

			----------Fecha Nacimiento----------
			CONVERT (VARCHAR,PDB.pedFechaNacimiento,112) AS [FEC_NACIMIENTO_PERSONA_A_CARGO],--[Fecha de Nacimiento de la persona a cargo],

			----------Genero----------
			ISNULL (
				CASE PDB.pedGenero
					WHEN 'FEMENINO' THEN '2'
					WHEN 'MASCULINO' THEN '1'
					WHEN 'INDEFINIDO' THEN'4' 
				END, '3') AS [GEN_PERSONA_A_CARGO],--[Sexo de la persona a cargo],
	
			----------Tipo Beneficiario----------
			CASE BEN.benTipoBeneficiario
				WHEN 'HIJO_BIOLOGICO' THEN '1'
				WHEN 'MADRE' THEN '2'
				WHEN 'PADRE' THEN '2'
				WHEN 'HERMANO_HUERFANO_DE_PADRES' THEN '3'
				WHEN 'HIJASTRO' THEN '4'
				WHEN 'CONYUGE' THEN '5'
				WHEN 'BENEFICIARIO_EN_CUSTODIA' THEN '6'
				WHEN 'HIJO_ADOPTIVO' THEN '6'
			END AS [PAR_PERSONA_A_CARGO],--[Parentesco de la persona a cargo],

			----------Codigo Municipio----------
			 pr.munCodigo  AS [COD_MUNICIPIO_RESIDENCIA_DANE],--[Código municipio de residencia de la persona a cargo],

			----------Area Geografica----------
			CASE 
				WHEN pr.pedResideSectorRural = 1 THEN '2' 
				ELSE '1'
			END  AS [ARE_GEOGRAFICA_RESIDENCIA],--[Área Geográfica de Residencia de la persona a cargo],
			 ----grupo étnico y demás del beneficiario

  	 		       CASE pdb.pedPertenenciaEtnica
						WHEN 'AFROCOLOMBIANO' THEN 1
						WHEN 'COMUNIDAD_NEGRA' THEN 2
						WHEN 'INDIGENA' THEN 3
						WHEN 'PALENQUERO' THEN 4 
						WHEN 'RAIZAL_ARCHI_SAN_ANDRES_PROVIDENCIA_SANTA_CATALINA' THEN 5
						WHEN 'ROOM_GITANO' THEN 6
						WHEN 'NINGUNO_DE_LOS_ANTERIORES' THEN 7
						ELSE 8 
					END AS [GRUPO_ETNICO],--[Pertenencia étnica] GLPI 71271

				------resguardo
				ISNULL(pdb.pedResguardo,2) as RESGUARDO,
				----pueblo indigena
				ISNULL(pdb.pedPuebloIndigena,2) as PUEBLO_INDIGENA,
				
			 -----
			---------- Invalides ? ----------
			CASE ci1.coiInvalidez
				WHEN '0' THEN '2'
				WHEN '1' THEN '1' 
				ELSE '2'
			END AS [DISC_PERSONA_A_CARGO],--[Condición de discapacidad de la persona a cargo],

		----------Tipo de Cuota Monetria----------
		CASE 
			WHEN D.dsatipocuotasubsidio = 'REGULAR' 
			 AND D.dsaTipoliquidacionSubsidio <>'ESPECIFICA_FALLECIMIENTO'  THEN '1'---cambio olga vega 20230601
			WHEN D.dsatipocuotasubsidio = 'DISCAPACIDAD' 
			 AND D.dsaTipoliquidacionSubsidio <>'ESPECIFICA_FALLECIMIENTO' THEN '2'--cambio olga vega 20230601
			WHEN D.dsatipocuotasubsidio = 'AGRICOLA' THEN '3'
			WHEN D.dsatipocuotasubsidio = 'DISCAPACIDAD_AGRICOLA' THEN '4'

			WHEN D.dsatipocuotasubsidio 
				IN( 
					'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO', 
					'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO'
				) OR D.dsaTipoliquidacionSubsidio = 'ESPECIFICA_FALLECIMIENTO'
			THEN '5' 

			WHEN D.dsatipocuotasubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO_AGRICOLA' THEN '7'
			WHEN D.dsatipocuotasubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_AGRICOLA' THEN '7'
			WHEN D.dsatipocuotasubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_DISCAPACITADO' THEN '8'
			WHEN D.dsatipocuotasubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_BENEFICIARIO_DISCAPACITADO_AGRICOLA' THEN '9'
			WHEN D.dsatipocuotasubsidio = 'AUXILIO_EXTRAORDINARIO_MUERTE_AFILIADO_AGRICOLA_BENEFICIARIO_DISCAPACITADO' THEN '9' 
			ELSE '6'
		END AS [TIP_CUOTA_MONETARIA_PERSONA_A_CARGO],--[Tipo de cuota monetaria pagado a la persona a cargo],
		
		---------Valor Subsidio----------
		CASE 
			WHEN  sum(D.dsaValorSubsidioMonetario) IS NULL
			THEN 0
			ELSE sum(D.dsaValorSubsidioMonetario) /3
		END AS [VAL_CUOTA_MONETARIA_PERSONA_A_CARGO],--[Valor de la cuota monetaria pagada a la persona a cargo],

		---------Cuotas Pagadas----------
	
		isnull(  COUNT(DISTINCT d.dsaId) ,'0') AS [NUM_CUOTAS_PAGADAS],--[Número de cuotas pagadas],

		----------Periodos Liquidados----------
		CASE WHEN D.dsaTipoliquidacionSubsidio IN ('ESPECIFICA_CON_AJUSTE_CAMBIO_CONDICION_AGRICOLA',
												   'ESPECIFICA_CON_AJUSTE_VALOR_CUOTA') 
		THEN '0' 
		WHEN (D.dsaTipoliquidacionSubsidio IN ( 'ESPECIFICA_CON_AJUSTE_CAMBIO_CONDICION_DISCAPACIDAD' ) 
		  OR D.dsaTipoliquidacionSubsidio = 'ESPECIFICA_FALLECIMIENTO')--CAMBIO 20230601
		THEN '1'
		ELSE  ISNULL(COUNT(DISTINCT D.dsaPeriodoLiquidado), '0')  
		END
		AS [NUM_PERIODOS_PAGADAS]--[Numero de periodos pagados]
		FROM  #PoblacionReporteACargo pr
		
		LEFT JOIN empleador empl WITH (nolock) on pr.roaEmpleador=empl.empid  
		LEFT JOIN empresa emp WITH (nolock) on empl.empempresa=emp.empid
		LEFT JOIN persona pe on pe.perid = emp.emppersona 
 
		INNER JOIN beneficiario ben WITH (nolock) on ben.benafiliado=pr.afiid
		INNER JOIN persona pb WITH (nolock) on pb.perId=ben.benPersona
		left join CondicionInvalidez ci1 on ci1.coiPersona = pb.perId
		 LEFT JOIN CondicionInvalidez ci WITH (nolock) ON PB.PERID=ci.coiIdConyugeCuidador 
		INNER JOIN PersonaDetalle pdb WITH (nolock) on pdb.pedPersona=pb.perId
		INNER JOIN BeneficiarioDetalle bd WITH (nolock) on  bd.bedPersonaDetalle=pdb.pedId
		 inner join Persona pbe on pbe.perId = ci.coiPersona
		INNER JOIN beneficiario benc WITH (nolock) on ben.benafiliado=pr.afiid and benc.benPersona = pbe.perId
		inner join BeneficiarioDetalle bdc on benc.benBeneficiarioDetalle = bdc.bedId
	----SI RECIBIERON SUBSIDIO
		inner JOIN #MiniMaestroRVL D WITH (nolock) 
		     ON bdc.bedid=dsaBeneficiarioDetalle 
			AND d.dsaEmpleador = pr.roaEmpleador
			AND d.dsaAfiliadoPrincipal = pr.afiid	
			and d.rvlesconyugecuidador =1
		and D.slsFechaDispersion between ci.coiFechaInicioconyugeCuidador and isnull(ci.coiFechaFinconyugeCuidador,'2999-01-01')
	
			
		GROUP BY 
			pr.perid,
			pr.idperempl,
			PE.perTipoIdentificacion, 
			PE.perNumeroIdentificacion,
			PE.perDigitoVerificacion,
			pr.perTipoIdentificacion,
			pr.perNumeroIdentificacion,
			PB.perTipoIdentificacion,
			PB.perNumeroIdentificacion,
			PB.perPrimerApellido,
			PB.perSegundoApellido,
			PB.perPrimerNombre,
			PB.perSegundoNombre,
			PDB.pedFechaNacimiento, 
			PDB.pedGenero, 
			BEN.benTipoBeneficiario,
			pr.munCodigo,
			pr.pedResideSectorRural,
			ci1.coiInvalidez, pr.roaFechaAfiliacion,--dsaPeriodoLiquidado,
			ben.benFechaAfiliacion, dsaTipoCuotaSubsidio, 
			dsaTipoliquidacionSubsidio, slsTipoDesembolso ,
			--d.dsaId,	 
			pdb.pedPertenenciaEtnica,pdb.pedResguardo,pdb.pedPuebloIndigena


			drop table if exists #TEMP_AFI_CARGO6
				SELECT DISTINCT
			afiliadoCargo.perid,
			afiliadoCargo.idperempl,
			afiliadoCargo.TIP_IDENTIFICACION,
			afiliadoCargo.NUM_IDENTIFICACION_EMPRESA,
			afiliadoCargo.TIP_IDENTIFICACION_AFILIADO,
			afiliadoCargo.NUM_IDENTIFICACION_AFILIADO,
			afiliadoCargo.TIP_IDENTIFICACION_PERSONA_A_CARGO,
			afiliadoCargo.NUM_IDENTIFICACION_PERSONA_A_CARGO,
			afiliadoCargo.PRI_NOMBRE_PERSONA_A_CARGO,
			afiliadoCargo.SEG_NOMBRE_PERSONA_A_CARGO,
			afiliadoCargo.PRI_APELLIDO_PERSONA_A_CARGO,
			afiliadoCargo.SEG_APELLIDO_PERSONA_A_CARGO,
			afiliadoCargo.FEC_NACIMIENTO_PERSONA_A_CARGO,
			afiliadoCargo.GEN_PERSONA_A_CARGO,
			afiliadoCargo.PAR_PERSONA_A_CARGO,
			afiliadoCargo.COD_MUNICIPIO_RESIDENCIA_DANE,
			afiliadoCargo.ARE_GEOGRAFICA_RESIDENCIA,
			afiliadoCargo.GRUPO_ETNICO ,
            afiliadoCargo.RESGUARDO,
            afiliadoCargo.PUEBLO_INDIGENA,
			afiliadoCargo.DISC_PERSONA_A_CARGO,
			afiliadoCargo.TIP_CUOTA_MONETARIA_PERSONA_A_CARGO,
			CAST((SUM(afiliadoCargo.VAL_CUOTA_MONETARIA_PERSONA_A_CARGO)) as bigint) as [VAL_CUOTA_MONETARIA_PERSONA_A_CARGO],
			SUM( afiliadoCargo.NUM_CUOTAS_PAGADAS) AS NUM_CUOTAS_PAGADAS,
			SUM( afiliadoCargo.NUM_PERIODOS_PAGADAS) AS NUM_PERIODOS_PAGADAS--,GETDATE() AS FECHA1, GETDATE() AS FECHA2
--CAMBIO OLGA VEGA 
		INTO #TEMP_AFI_CARGO6
		FROM #TEMP_AFI_CARGO5 AS afiliadoCargo
	
		GROUP BY
			afiliadoCargo.perid,
			afiliadoCargo.idperempl,
			afiliadoCargo.TIP_IDENTIFICACION,
			afiliadoCargo.NUM_IDENTIFICACION_EMPRESA,
			afiliadoCargo.TIP_IDENTIFICACION_AFILIADO,
			afiliadoCargo.NUM_IDENTIFICACION_AFILIADO,
			afiliadoCargo.TIP_IDENTIFICACION_PERSONA_A_CARGO,
			afiliadoCargo.NUM_IDENTIFICACION_PERSONA_A_CARGO,
			afiliadoCargo.PRI_NOMBRE_PERSONA_A_CARGO,
			afiliadoCargo.SEG_NOMBRE_PERSONA_A_CARGO,
			afiliadoCargo.PRI_APELLIDO_PERSONA_A_CARGO,
			afiliadoCargo.SEG_APELLIDO_PERSONA_A_CARGO,
			afiliadoCargo.FEC_NACIMIENTO_PERSONA_A_CARGO,
			afiliadoCargo.GEN_PERSONA_A_CARGO,
			afiliadoCargo.PAR_PERSONA_A_CARGO,
			afiliadoCargo.COD_MUNICIPIO_RESIDENCIA_DANE,
			afiliadoCargo.ARE_GEOGRAFICA_RESIDENCIA,
			afiliadoCargo.DISC_PERSONA_A_CARGO,
			afiliadoCargo.TIP_CUOTA_MONETARIA_PERSONA_A_CARGO,
			afiliadoCargo.GRUPO_ETNICO ,
            afiliadoCargo.RESGUARDO,
            afiliadoCargo.PUEBLO_INDIGENA 

			drop table if exists #TEMP_AFI_CARGO7
 SELECT  *
  INTO #TEMP_AFI_CARGO7
  FROM #TEMP_AFI_CARGO2
  UNION 
 SELECT * 
   FROM #TEMP_AFI_CARGO4
   union 
   select * 
   from #TEMP_AFI_CARGO6

 	
	
	CREATE CLUSTERED INDEX ids ON  #TEMP_AFI_CARGO7 (NUM_IDENTIFICACION_EMPRESA,NUM_IDENTIFICACION_AFILIADO,NUM_IDENTIFICACION_PERSONA_A_CARGO)

                SELECT DISTINCT TIP_IDENTIFICACION,	NUM_IDENTIFICACION_EMPRESA,	
					   TIP_IDENTIFICACION_AFILIADO,	NUM_IDENTIFICACION_AFILIADO,
					   TIP_IDENTIFICACION_PERSONA_A_CARGO, NUM_IDENTIFICACION_PERSONA_A_CARGO,
					   PRI_NOMBRE_PERSONA_A_CARGO, SEG_NOMBRE_PERSONA_A_CARGO,	
					   PRI_APELLIDO_PERSONA_A_CARGO, SEG_APELLIDO_PERSONA_A_CARGO,
					   FEC_NACIMIENTO_PERSONA_A_CARGO,ha.hraCategoria as CATEGORIA, GEN_PERSONA_A_CARGO, 
					   CASE WHEN (select distinct 1 from Persona p   --- Ajuste GLPI # 78393
						inner join Beneficiario b on (b.benPersona = p.perId) and (b.benEstadoBeneficiarioAfiliado = 'ACTIVO') and (b.benTipoBeneficiario = 'CONYUGE')
						inner join CondicionInvalidez ci on ci.coiIdConyugeCuidador = b.benPersona
						where p.perNumeroIdentificacion = NUM_IDENTIFICACION_PERSONA_A_CARGO) = 1 THEN '25' ELSE PAR_PERSONA_A_CARGO END AS PAR_PERSONA_A_CARGO, COD_MUNICIPIO_RESIDENCIA_DANE,
					   ARE_GEOGRAFICA_RESIDENCIA, GRUPO_ETNICO, RESGUARDO, 
					   PUEBLO_INDIGENA,ha.hraTipoAfiliado as TIPO_AFILIADO,
					   ha.hraActividadEconomicaprincipal AS ACTIVIDAD_PRINCIPAL,
					   DISC_PERSONA_A_CARGO,
					   TIP_CUOTA_MONETARIA_PERSONA_A_CARGO,	VAL_CUOTA_MONETARIA_PERSONA_A_CARGO, NUM_CUOTAS_PAGADAS,
					   NUM_PERIODOS_PAGADAS
				  FROM #TEMP_AFI_CARGO7 temp7
			INNER JOIN  rno.HistoricoAfiliados ha 
				    ON TIP_IDENTIFICACION = ha.hraTipoIdentificacionEmpresa
					AND NUM_IDENTIFICACION_EMPRESA = ha.hraNumeroIdentificacion
				   AND NUM_IDENTIFICACION_AFILIADO = ha.hraNumeroIdentificacionAfiliado
				   
				   AND ha.hraFechaInicialReporte = @FECHA_INICIAL
				   AND ha.hraFechaFinalReporte = @FECHA_FINAL


--				   hraCategoria
--hraActividadEconomicaprincipal
 
			---
			DELETE rno.HistoricoAfiliadosACargo 
			--  WHERE hacFechaInicialReporte = @FECHA_INICIAL AND hacFechaFinalReporte = @FECHA_FINAL

			INSERT INTO rno.HistoricoAfiliadosACargo
			SELECT  DISTINCT 	GETDATE(), 
						TIP_IDENTIFICACION,
						NUM_IDENTIFICACION_EMPRESA,
						TIP_IDENTIFICACION_AFILIADO ,
						NUM_IDENTIFICACION_AFILIADO,
						TIP_IDENTIFICACION_PERSONA_A_CARGO ,
						NUM_IDENTIFICACION_PERSONA_A_CARGO,
						PRI_NOMBRE_PERSONA_A_CARGO,
						SEG_NOMBRE_PERSONA_A_CARGO,
						PRI_APELLIDO_PERSONA_A_CARGO,
						SEG_APELLIDO_PERSONA_A_CARGO,
						FEC_NACIMIENTO_PERSONA_A_CARGO,
						GEN_PERSONA_A_CARGO,
						CASE WHEN (select distinct 1 from Persona p   --- Ajuste GLPI # 78393
						inner join Beneficiario b on (b.benPersona = p.perId) and (b.benEstadoBeneficiarioAfiliado = 'ACTIVO') and (b.benTipoBeneficiario = 'CONYUGE')
						inner join CondicionInvalidez ci on ci.coiIdConyugeCuidador = b.benPersona
						where p.perNumeroIdentificacion = NUM_IDENTIFICACION_PERSONA_A_CARGO) = 1 THEN '25' ELSE PAR_PERSONA_A_CARGO END AS PAR_PERSONA_A_CARGO,
						DISC_PERSONA_A_CARGO,
						TIP_CUOTA_MONETARIA_PERSONA_A_CARGO,
						NUM_CUOTAS_PAGADAS,
						NUM_PERIODOS_PAGADAS,
						@FECHA_INICIAL,
						@FECHA_FINAL,
						VAL_CUOTA_MONETARIA_PERSONA_A_CARGO,
						COD_MUNICIPIO_RESIDENCIA_DANE ,
						ARE_GEOGRAFICA_RESIDENCIA,
						GRUPO_ETNICO ,
                        RESGUARDO,
                        PUEBLO_INDIGENA ,
						hraCategoria,
						hraActividadEconomicaprincipal,
						hraTipoAfiliado
				   FROM #TEMP_AFI_CARGO6---CAMBIO #TEMP_AFI_CARGO2
			INNER JOIN rno.HistoricoAfiliados ha 
				    ON TIP_IDENTIFICACION = HA.hraTipoIdentificacionEmpresa
					AND NUM_IDENTIFICACION_EMPRESA = ha.hraNumeroIdentificacion
				   AND NUM_IDENTIFICACION_AFILIADO = ha.hraNumeroIdentificacionAfiliado
				   AND ha.hraFechaInicialReporte = @FECHA_INICIAL
				   AND ha.hraFechaFinalReporte = @FECHA_FINAL
	 
 
END