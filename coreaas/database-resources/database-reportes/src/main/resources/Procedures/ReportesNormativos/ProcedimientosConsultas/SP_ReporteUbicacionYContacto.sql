/****** Object:  StoredProcedure [dbo].[reporteUbicacionYContacto]    Script Date: 2023-10-31 8:59:01 AM ******/
/****** Object:  StoredProcedure [dbo].[reporteUbicacionYContacto]    Script Date: 28/11/2022 12:42:57 p. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteUbicacionYContacto]    Script Date: 09/11/2022 11:35:41 a. m. ******/ 
/****** Object:  StoredProcedure [dbo].[reporteUbicacionYContacto]    Script Date: 02/09/2022 9:06:11 a. m. ******/
-- =============================================
-- Author:      Miguel Angel Perilla
-- Update Date: 06 Julio 2022
-- Description: Procedimiento almacenado para obtener el resultado que hace referencia al reporte 7.
-- Reporte 7
---execute reporteUbicacionYContacto '2023-01-01','2023-10-31'
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[reporteUbicacionYContacto]( @FECHA_INICIAL DATE, @FECHA_FINAL DATE )

AS
BEGIN
SET NOCOUNT ON

--/---------------------------------------**********---------------------------------------\--
--                          REPORTE DE UBICACION Y CONTACTO  -  N° 7.
--\---------------------------------------**********---------------------------------------/--
-----------------------------------------
-----------------------------------------
---------DATA EMPLEADORES NOVEDADES
-----------------------------------------
	DECLARE @sql NVARCHAR(4000)
	SET @sql = 
	'
		SELECT 
			ubi.ubiId AS [id_ubi], 
			ubiE.ubeTipoUbicacion AS [tipo_ubicacion], 
			MAX(DATEADD(SECOND, revTimeStamp / 1000, ''19700101'') AT TIME ZONE ''UTC'' AT TIME ZONE ''SA Pacific Standard Time'') fecRev
			
		FROM 
			Ubicacion_aud as ubi
			LEFT JOIN UbicacionEmpresa_aud AS ubiE ON ubiE.ubeUbicacion = ubi.ubiId
			INNER JOIN dbo.Revision rev ON rev.revId = ubi.REV

		WHERE 
			ubiE.ubeTipoUbicacion IN ( ''UBICACION_PRINCIPAL'', ''ENVIO_CORRESPONDENCIA'' )
			--AND (DATEADD(SECOND, revTimeStamp / 1000, ''19700101'') AT TIME ZONE ''UTC'' AT TIME ZONE ''SA Pacific Standard Time'') BETWEEN @FECHA_INICIAL AND @FECHA_FINAL
		GROUP BY 
			ubi.ubiId, 
			ubiE.ubeTipoUbicacion 
		'

	IF OBJECT_ID('tempdb.dbo.#TempUbicacionAud', 'U') IS NOT NULL
		DROP TABLE #TempUbicacionAud; 

	CREATE TABLE #TempUbicacionAud(
		id_ubi INT, 
		tipo_ubicacion VARCHAR(500), 
		fecRev VARCHAR(500), 
		shard VARCHAR(500)
	)
 	
	INSERT INTO #TempUbicacionAud (
		id_ubi, 
		tipo_ubicacion, 
		fecRev, 
		shard
	)

	EXEC sp_execute_remote N'CoreAudReferenceData',
		@sql
		--,
		--N'@FECHA_INICIAL DATE, @FECHA_FINAL DATE',
		--@FECHA_INICIAL = @FECHA_INICIAL, 
		--@FECHA_FINAL = @FECHA_FINAL

	--SELECT MAX(FORMAT(CAST(fecRev AS DATE), 'yyyy-MM-dd')) FROM #TempUbicacionAud

	----  SELECT * FROM #TempUbicacionAud where id_ubi  in (817093,817094)

SELECT 
	----------  Periodo del reporte  ----------
	FORMAT(GETDATE(), 'yyyy-MM') [Periodo del reporte],

	----------  Codigo Administradora  ----------
	consolidado.[Cód Administradora],
	
	----------  Nombre Administradora  ----------
	--consolidado.[Nombre administradora] AS [Nombre o razón social aportante],
	
	----------  Razon Social  ----------
	left(consolidado.[Nombre o razón social aportante],200) as [Nombre o razón social aportante],

	----------  Tipo Identificación  ----------
	consolidado.[Tipo de documento del aportante],

	----------  Numero Documento  ----------
	consolidado.[Número de documento del aportante],

	----------  Digto de Verificacion  ----------
	--consolidado.[Número de dígito de verificación],

	----------  Direccion Uno  ----------
	consolidado.[Dirección 1],

	----------  Codigo Departamento Uno  ----------
	consolidado.[Código departamento 1],

	----------  Codigo Municipio Uno  ----------
	consolidado.[Código municipio 1],

	----------  Nombre Departamento Uno  ----------
	--consolidado.[Nombre departamento 1],

	----------  Nombre Municipio Uno  ----------
	--consolidado.[Nombre municipio 1],

	----------  Direccion Dos  ----------
	consolidado.[Dirección 2],

	----------  Codigo Departamento Dos  ----------
	consolidado.[Código departamento 2],

	----------  Codigo Municipio Dos  ----------
	consolidado.[Código municipio 2],

	----------  Nombre Departamento Dos  ----------
	--consolidado.[Nombre departamento 2],

	----------  Nombre Municipio Dos  ----------
	--consolidado.[Nombre municipio 2],

	----------  Telefono Uno  ----------
	ISNULL(consolidado.[Teléfono fijo 1],'')  AS [Teléfono fijo 1] ,

	----------  Indicativo Telefono Uno  ----------
	CASE 
		WHEN ISNULL(consolidado.[Teléfono fijo 1],'')= ''
		THEN '' ELSE consolidado.[Indicativo teléfono 1] END
	  AS [Indicativo teléfono 1],

	----------  Telefono Dos  ----------
	consolidado.[Teléfono fijo 2],

	----------  Indicativo Telefono Dos  ----------
	CASE 
		WHEN consolidado.[Teléfono fijo 2] IS NOT NULL OR consolidado.[Teléfono fijo 2] = ''
		THEN consolidado.[Indicativo teléfono 2]
	END AS [Indicativo teléfono 2],

	----------  Celular Uno  ----------
	ISNULL(consolidado.[Celular 1],'') AS [Celular 1],

	----------  Celular Dos  ----------
	consolidado.[Celular 2],

	----------  Email Uno  ----------
	 ISNULL(consolidado.[Correo electrónico 1],'')  AS [Correo electrónico 1],

	----------  Email Dos  ----------
	consolidado.[Correo electrónico 2],

	----------  Fecha Actualización  ----------
	
	MAX(consolidado.[Última fecha de actualización de información]) AS [Última fecha de actualización de información]
	 
	--consolidado.PARA
FROM(

	/*********************************************************/

		SELECT 	

		----------  Codigo Administradora  ----------
		REPLACE(cns.cnsValor, 'CCF0', 'CCF') AS [Cód Administradora],
	
		----------  Nombre Administradora  ----------
		prm.prmValor AS [Nombre administradora],
	
		----------  Razon Social  ----------
		left(CASE 
			WHEN per.perRazonSocial IS NULL 
			THEN RTrim(
				Coalesce(per.perPrimerNombre + ' ','') 
				+ Coalesce(per.perSegundoNombre + ' ', '')
				+ Coalesce(per.perPrimerApellido + ' ', '')
				+ Coalesce(per.perSegundoApellido, ''))
			ELSE per.perRazonSocial 
		END,200) AS [Nombre o razón social aportante],

		----------  Tipo Identificación  ----------
		CASE per.perTipoIdentificacion
			WHEN 'REGISTRO_CIVIL'		THEN 'RC'
			WHEN 'TARJETA_IDENTIDAD'	THEN 'TI'
			WHEN 'CEDULA_CIUDADANIA'	THEN 'CC'
			WHEN 'CEDULA_EXTRANJERIA'	THEN 'CE'
			WHEN 'PASAPORTE'			THEN 'PA'
			WHEN 'CARNE_DIPLOMATICO'	THEN 'CD'
			WHEN 'NIT'					THEN 'NI'
			WHEN 'SALVOCONDUCTO'		THEN 'SC'
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE' 
			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'  
		END AS [Tipo de documento del aportante],

		----------  Numero Documento  ----------
		per.perNumeroIdentificacion AS [Número de documento del aportante],

		----------  Digto de Verificacion  ----------
		CASE perTipoIdentificacion 
			WHEN 'NIT' THEN per.perDigitoVerificacion
			ELSE NULL
		END AS [Número de dígito de verificación],

		----------  Direccion Uno  ----------
		CASE
			WHEN RTRIM(ubi.ubiDireccionFisica) = 'SIN DIRECCION'
			THEN ''
			ELSE RTRIM(ubi.ubiDireccionFisica)
		END AS [Dirección 1],

		----------  Codigo Departamento Uno  ----------
		dep.depCodigo AS [Código departamento 1],

		----------  Codigo Municipio Uno  ----------
		mun.munCodigo AS [Código municipio 1],

		----------  Nombre Departamento Uno  ----------
		dep.depNombre AS [Nombre departamento 1],

		----------  Nombre Municipio Uno  ----------
		mun.munNombre AS [Nombre municipio 1],

		----------  Direccion Dos  ----------
		CASE
			WHEN RTRIM(ubi2.ubiDireccionFisica) = 'SIN DIRECCION'
			THEN ''
			ELSE 
				CASE 
					WHEN RTRIM(ubi2.ubiDireccionFisica) = RTRIM(ubi.ubiDireccionFisica)
					THEN ''
					ELSE RTRIM(ubi2.ubiDireccionFisica) 
				END 
		END AS [Dirección 2],

		----------  Codigo Departamento Dos  ----------
		CASE
			WHEN RTRIM(ubi2.ubiDireccionFisica) = 'SIN DIRECCION'
			THEN ''
			ELSE 
				CASE 
					WHEN RTRIM(ubi2.ubiDireccionFisica) = RTRIM(ubi.ubiDireccionFisica)
					THEN ''
					ELSE dep2.depCodigo 
				END 
		END AS [Código departamento 2],

		----------  Codigo Municipio Dos  ----------
		CASE
			WHEN RTRIM(ubi2.ubiDireccionFisica) = 'SIN DIRECCION'
			THEN ''
			ELSE 
				CASE 
					WHEN RTRIM(ubi2.ubiDireccionFisica) = RTRIM(ubi.ubiDireccionFisica)
					THEN ''
					ELSE mun2.munCodigo 
				END 
		END AS [Código municipio 2],

		----------  Nombre Departamento Dos  ----------
		CASE
			WHEN RTRIM(ubi2.ubiDireccionFisica) = 'SIN DIRECCION'
			THEN ''
			ELSE 
				CASE 
					WHEN RTRIM(ubi2.ubiDireccionFisica) = RTRIM(ubi.ubiDireccionFisica)
					THEN ''
					ELSE dep2.depNombre
				END 
		END AS [Nombre departamento 2],

		----------  Nombre Municipio Dos  ----------
		CASE
			WHEN RTRIM(ubi2.ubiDireccionFisica) = 'SIN DIRECCION'
			THEN ''
			ELSE 
				CASE 
					WHEN RTRIM(ubi2.ubiDireccionFisica) = RTRIM(ubi.ubiDireccionFisica)
					THEN ''
					ELSE mun2.munNombre
				END 
		END AS [Nombre municipio 2],

		----------  Telefono Uno  ----------
		ISNULL(ubi.ubiTelefonoFijo,'') AS [Teléfono fijo 1],

		----------  Indicativo Telefono Uno  ----------
		CASE WHEN ISNULL(ubi.ubiTelefonoFijo,'') = ''
		THEN '' 
		ELSE ISNULL(ubi.ubiIndicativoTelFijo,'') END 
		AS [Indicativo teléfono 1],

		----------  Telefono Dos  ----------
		CASE 
			WHEN ubi.ubiTelefonoFijo = ubi2.ubiTelefonoFijo 
				OR ubi2.ubiTelefonoFijo IS NULL
			THEN ''
			ELSE ubi2.ubiTelefonoFijo
		END AS [Teléfono fijo 2],

		----------  Indicativo Telefono Dos  ----------
		CASE 
			WHEN ubi.ubiTelefonoFijo = ubi2.ubiTelefonoFijo
				OR ubi2.ubiTelefonoFijo IS NULL
			THEN ''
			ELSE ubi2.ubiIndicativoTelFijo 
		END AS [Indicativo teléfono 2],

		----------  Celular Uno  ----------
		ISNULL(ubi.ubiTelefonoCelular,'') AS [Celular 1],

		----------  Celular Dos  ----------
		CASE 
			WHEN ubi.ubiTelefonoCelular = ubi2.ubiTelefonoCelular
				OR UBI2.ubiTelefonoCelular IS NULL
			THEN ''
			ELSE ubi2.ubiTelefonoCelular 
		END AS [Celular 2],

		----------  Email Uno  ----------
		CASE WHEN LEN(ubi.ubiEmail)>50 THEN '' ELSE  ISNULL(ubi.ubiEmail,'')  END   AS [Correo electrónico 1],

		----------  Email Dos  ----------
		CASE 
			WHEN  ubi.ubiEmail  =  ubi2.ubiEmail 
				OR  ubi2.ubiEmail  IS NULL
				OR  LEN(ubi2.ubiEmail)>50  
				OR  LEN(ubi.ubiEmail)>50  
			THEN ''
			ELSE 	  
			-- LEFT(
			ISNULL(ubi2.ubiEmail,'')
			-- ,50) 
				---	+ REPLICATE(' ',  LEN(ISNULL(ubi2.ubiEmail,'')) -50)
		END AS [Correo electrónico 2],

		----------  Fecha Actualización  ----------
	
		/*CASE 
			WHEN ( SELECT DATEDIFF(YEAR, MAX(sol.solFechaRadicacion), @FECHA_FINAL) ) < 1 
			THEN MAX(FORMAT(sol.solFechaRadicacion, 'yyyy-MM-dd')) 
			ELSE ''*/
			--MAX(FORMAT(CAST(tUbiAud.fecRev AS DATE), 'yyyy-MM-dd'))
			
			--MAX(FORMAT(tUbiAud.fecRev, 'yyyy-MM-dd'))
			--MAX(FORMAT(sol.solFechaRadicacion, 'yyyy-MM-dd'))
		--END 
		MAX(FORMAT(CAST(tUbiAud.fecRev AS DATE), 'yyyy')) AS [Última fecha de actualización de información]
	
	FROM ExclusionCartera e

 INNER JOIN PERSONA per ON per.perId = excPersona
 INNER JOIN Empresa AS empresa ON empresa.empPersona = per.perid 
		
		----------Ubicacion Persona----------
		INNER JOIN Empleador AS empleador 
			 
			 ON empresa.empId = empleador.empEmpresa
			AND   (

				empleador.empMotivoDesafiliacion = 'EXPULSION_POR_MOROSIDAD' AND 
				empleador.empFechaRetiro >= '2014-05-23'
			     )

		

		INNER JOIN Cartera AS CAR 
			ON CAR.carPersona = empresa.empPersona
			AND CAR.carTipoLineaCobro = 'C6'
			AND ( 
				CAR.carestadoOperacion ='VIGENTE' OR 
				empleador.empEstadoEmpleador = 'ACTIVO' 
				)
		
		----------Datos ubicacion UNO----------
		LEFT JOIN UbicacionEmpresa as ubiE 
			on ubiE.ubeEmpresa = empresa.empId AND
			ubeTipoUbicacion = 'UBICACION_PRINCIPAL'
		LEFT JOIN Ubicacion ubi ON ubiE.ubeUbicacion = ubi.ubiId
		LEFT JOIN Municipio mun ON (ubi.ubiMunicipio = mun.munId)
		LEFT JOIN Departamento dep ON (mun.munDepartamento = dep.depId)
		LEFT join #TempUbicacionAud tUbiAud on tUbiAud.id_ubi =  ubi.ubiId

		----------Datos ubicacion DOS----------
		LEFT JOIN UbicacionEmpresa as ubiE2 
				ON ubiE2.ubeEmpresa = empresa.empId AND
					ubiE2.ubeTipoUbicacion = 'ENVIO_CORRESPONDENCIA'
		LEFT JOIN Ubicacion ubi2 ON ubiE2.ubeUbicacion = ubi2.ubiId
		LEFT JOIN Municipio mun2 ON (ubi2.ubiMunicipio = mun2.munId)
		LEFT JOIN Departamento dep2 ON (mun2.munDepartamento = dep2.depId)
		LEFT JOIN #TempUbicacionAud tUbiAud2 on tUbiAud2.id_ubi = ubi2.ubiId

		LEFT JOIN Constante cns ON (cnsNombre = 'CAJA_COMPENSACION_CODIGO')
		LEFT JOIN Parametro prm ON (prmNombre = 'NOMBRE_CCF')

		LEFT JOIN ( 

				SELECT apg.apgPersona, max(apg.apgFechaProcesamiento) AS fechaUltimo
				FROM AporteGeneral2 apg 
				WHERE 
					apg.apgPersona IS NOT NULL AND
					apg.apgEstadoAportante = 'ACTIVO' 
				GROUP BY
					apg.apgPersona

			UNION 
	
				SELECT emp.empPersona, max(apg.apgFechaProcesamiento) AS fechaUltimo
				FROM 
					AporteGeneral2 apg 
					JOIN Empresa emp ON (apg.apgEmpresa = emp.empId)
				WHERE
					apg.apgEstadoAportante = 'ACTIVO'
				GROUP BY 
					emp.empPersona

		) AS personaAporte ON personaAporte.apgPersona = per.perId

 
	GROUP BY
			cns.cnsValor,
			prm.prmValor,
			PER.perPrimerNombre,
			per.perPrimerApellido,
			perSegundoNombre,
			perSegundoApellido,
			perDigitoVerificacion,
			per.perRazonSocial,
			per.perTipoIdentificacion,
			per.perNumeroIdentificacion,
			perTipoIdentificacion,
			ubi.ubiDireccionFisica,
			dep.depCodigo,
			mun.munCodigo,
			dep.depNombre,
			mun.munNombre,
			ubi.ubiTelefonoFijo,
			ubi.ubiIndicativoTelFijo,
			ubi.ubiTelefonoCelular,
			ubi.ubiEmail,
			ubi2.ubiDireccionFisica,
			dep2.depCodigo,
			mun2.munCodigo,
			dep2.depNombre,
			mun2.munNombre,
			ubi2.ubiTelefonoFijo,
			ubi2.ubiIndicativoTelFijo,
			ubi2.ubiTelefonoCelular,
			ubi2.ubiEmail



		UNION






	/******************************************************/

	---lo anterior solo para prueba por exclusion


	/*---*****************************************************---*/
	---------------------------------------
	-- EXPULSADOS POR MORA
	---------------------------------------
	SELECT 	

		----------  Codigo Administradora  ----------
		REPLACE(cns.cnsValor, 'CCF0', 'CCF') AS [Cód Administradora],
	
		----------  Nombre Administradora  ----------
		prm.prmValor AS [Nombre administradora],
	
		----------  Razon Social  ----------
		left(CASE 
			WHEN per.perRazonSocial IS NULL 
			THEN RTrim(
				Coalesce(per.perPrimerNombre + ' ','') 
				+ Coalesce(per.perSegundoNombre + ' ', '')
				+ Coalesce(per.perPrimerApellido + ' ', '')
				+ Coalesce(per.perSegundoApellido, ''))
			ELSE per.perRazonSocial 
		END,200) AS [Nombre o razón social aportante],

		----------  Tipo Identificación  ----------
		CASE per.perTipoIdentificacion
			WHEN 'REGISTRO_CIVIL'		THEN 'RC'
			WHEN 'TARJETA_IDENTIDAD'	THEN 'TI'
			WHEN 'CEDULA_CIUDADANIA'	THEN 'CC'
			WHEN 'CEDULA_EXTRANJERIA'	THEN 'CE'
			WHEN 'PASAPORTE'			THEN 'PA'
			WHEN 'CARNE_DIPLOMATICO'	THEN 'CD'
			WHEN 'NIT'					THEN 'NI'
			WHEN 'SALVOCONDUCTO'		THEN 'SC'
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE' 
			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'  
		END AS [Tipo de documento del aportante],

		----------  Numero Documento  ----------
		per.perNumeroIdentificacion AS [Número de documento del aportante],

		----------  Digto de Verificacion  ----------
		CASE perTipoIdentificacion 
			WHEN 'NIT' THEN per.perDigitoVerificacion
			ELSE NULL
		END AS [Número de dígito de verificación],

		----------  Direccion Uno  ----------
		CASE
			WHEN RTRIM(ubi.ubiDireccionFisica) = 'SIN DIRECCION'
			THEN ''
			ELSE RTRIM(ubi.ubiDireccionFisica)
		END AS [Dirección 1],

		----------  Codigo Departamento Uno  ----------
		dep.depCodigo AS [Código departamento 1],

		----------  Codigo Municipio Uno  ----------
		mun.munCodigo AS [Código municipio 1],

		----------  Nombre Departamento Uno  ----------
		dep.depNombre AS [Nombre departamento 1],

		----------  Nombre Municipio Uno  ----------
		mun.munNombre AS [Nombre municipio 1],

		----------  Direccion Dos  ----------
		CASE
			WHEN RTRIM(ubi2.ubiDireccionFisica) = 'SIN DIRECCION'
			THEN ''
			ELSE 
				CASE 
					WHEN RTRIM(ubi2.ubiDireccionFisica) = RTRIM(ubi.ubiDireccionFisica)
					THEN ''
					ELSE RTRIM(ubi2.ubiDireccionFisica) 
				END 
		END AS [Dirección 2],

		----------  Codigo Departamento Dos  ----------
		CASE
			WHEN RTRIM(ubi2.ubiDireccionFisica) = 'SIN DIRECCION'
			THEN ''
			ELSE 
				CASE 
					WHEN RTRIM(ubi2.ubiDireccionFisica) = RTRIM(ubi.ubiDireccionFisica)
					THEN ''
					ELSE dep2.depCodigo 
				END 
		END AS [Código departamento 2],

		----------  Codigo Municipio Dos  ----------
		CASE
			WHEN RTRIM(ubi2.ubiDireccionFisica) = 'SIN DIRECCION'
			THEN ''
			ELSE 
				CASE 
					WHEN RTRIM(ubi2.ubiDireccionFisica) = RTRIM(ubi.ubiDireccionFisica)
					THEN ''
					ELSE mun2.munCodigo 
				END 
		END AS [Código municipio 2],

		----------  Nombre Departamento Dos  ----------
		CASE
			WHEN RTRIM(ubi2.ubiDireccionFisica) = 'SIN DIRECCION'
			THEN ''
			ELSE 
				CASE 
					WHEN RTRIM(ubi2.ubiDireccionFisica) = RTRIM(ubi.ubiDireccionFisica)
					THEN ''
					ELSE dep2.depNombre
				END 
		END AS [Nombre departamento 2],

		----------  Nombre Municipio Dos  ----------
		CASE
			WHEN RTRIM(ubi2.ubiDireccionFisica) = 'SIN DIRECCION'
			THEN ''
			ELSE 
				CASE 
					WHEN RTRIM(ubi2.ubiDireccionFisica) = RTRIM(ubi.ubiDireccionFisica)
					THEN ''
					ELSE mun2.munNombre
				END 
		END AS [Nombre municipio 2],

		----------  Telefono Uno  ----------
		ISNULL(ubi.ubiTelefonoFijo,'') AS [Teléfono fijo 1],

		----------  Indicativo Telefono Uno  ----------
		CASE WHEN ISNULL(ubi.ubiTelefonoFijo,'') = ''
		THEN '' 
		ELSE ISNULL(ubi.ubiIndicativoTelFijo,'') END 
		AS [Indicativo teléfono 1],

		----------  Telefono Dos  ----------
		CASE 
			WHEN ubi.ubiTelefonoFijo = ubi2.ubiTelefonoFijo 
				OR ubi2.ubiTelefonoFijo IS NULL
			THEN ''
			ELSE ubi2.ubiTelefonoFijo
		END AS [Teléfono fijo 2],

		----------  Indicativo Telefono Dos  ----------
		CASE 
			WHEN ubi.ubiTelefonoFijo = ubi2.ubiTelefonoFijo
				OR ubi2.ubiTelefonoFijo IS NULL
			THEN ''
			ELSE ubi2.ubiIndicativoTelFijo 
		END AS [Indicativo teléfono 2],

		----------  Celular Uno  ----------
		ISNULL(ubi.ubiTelefonoCelular,'') AS [Celular 1],

		----------  Celular Dos  ----------
		CASE 
			WHEN ubi.ubiTelefonoCelular = ubi2.ubiTelefonoCelular
				OR UBI2.ubiTelefonoCelular IS NULL
			THEN ''
			ELSE ubi2.ubiTelefonoCelular 
		END AS [Celular 2],

		----------  Email Uno  ----------
		CASE WHEN LEN(ubi.ubiEmail)>50 THEN '' ELSE  ISNULL(ubi.ubiEmail,'')  END   AS [Correo electrónico 1],

		----------  Email Dos  ----------
		CASE 
			WHEN  ubi.ubiEmail  =  ubi2.ubiEmail 
				OR  ubi2.ubiEmail  IS NULL
				OR  LEN(ubi2.ubiEmail)>50  
				OR  LEN(ubi.ubiEmail)>50  
			THEN ''
			ELSE 	  
			-- LEFT(
			ISNULL(ubi2.ubiEmail,'')
			-- ,50) 
				---	+ REPLICATE(' ',  LEN(ISNULL(ubi2.ubiEmail,'')) -50)
		END AS [Correo electrónico 2],

		----------  Fecha Actualización  ----------
	
		/*CASE 
			WHEN ( SELECT DATEDIFF(YEAR, MAX(sol.solFechaRadicacion), @FECHA_FINAL) ) < 1 
			THEN MAX(FORMAT(sol.solFechaRadicacion, 'yyyy-MM-dd')) 
			ELSE ''*/
			--MAX(FORMAT(CAST(tUbiAud.fecRev AS DATE), 'yyyy-MM-dd'))
			
			--MAX(FORMAT(tUbiAud.fecRev, 'yyyy-MM-dd'))
			--MAX(FORMAT(sol.solFechaRadicacion, 'yyyy-MM-dd'))
		--END 
		MAX(FORMAT(CAST(tUbiAud.fecRev AS DATE), 'yyyy')) AS [Última fecha de actualización de información]
	
	FROM
		SolicitudNovedadEmpleador solNovEmpleador
		INNER JOIN SolicitudNovedad solNov 
				ON solNov.snoId = solNovEmpleador.sneIdSolicitudNovedad
				AND snoEstadoSolicitud IN ('CERRADA', 'APROBADA')
		
		INNER JOIN Solicitud sol 
			ON sol.solId = solNov.snoSolicitudGlobal --AND 
			--MAX( sol.solFechaRadicacion ) --BETWEEN @FECHA_INICIAL AND @FECHA_FINAL

		--INNER  JOIN parametrizacionNovedad AS parametrizacionNovedad 
		--	ON solNov.snoNovedad = parametrizacionNovedad.novId
		--	AND parametrizacionNovedad.novTipoTransaccion IN (
		--		'ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_PRESENCIAL',
		--		'ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_WEB',
		--		'ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_PRESENCIAL',
		--		'ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_WEB')
			

		----------Ubicacion Persona----------
		INNER JOIN Empleador AS empleador 
			ON empleador.empId = sneIdEmpleador

			AND   (

				empleador.empMotivoDesafiliacion = 'EXPULSION_POR_MOROSIDAD' AND 
				empleador.empFechaRetiro >= '2014-05-23'
			     )

		INNER JOIN Empresa AS empresa ON empresa.empId = empleador.empEmpresa
		INNER JOIN PERSONA per ON per.perId = empresa.empPersona

		INNER JOIN Cartera AS CAR 
			ON CAR.carPersona = empresa.empPersona
			AND CAR.carTipoLineaCobro = 'C6'
			AND ( 
				CAR.carestadoOperacion ='VIGENTE' OR 
				empleador.empEstadoEmpleador = 'ACTIVO' 
				)
		
		----------Datos ubicacion UNO----------
		LEFT JOIN UbicacionEmpresa as ubiE 
			on ubiE.ubeEmpresa = empresa.empId AND
			ubeTipoUbicacion = 'UBICACION_PRINCIPAL'
		LEFT JOIN Ubicacion ubi ON ubiE.ubeUbicacion = ubi.ubiId
		LEFT JOIN Municipio mun ON (ubi.ubiMunicipio = mun.munId)
		LEFT JOIN Departamento dep ON (mun.munDepartamento = dep.depId)
		LEFT join #TempUbicacionAud tUbiAud on tUbiAud.id_ubi =  ubi.ubiId

		----------Datos ubicacion DOS----------
		LEFT JOIN UbicacionEmpresa as ubiE2 
				ON ubiE2.ubeEmpresa = empresa.empId AND
					ubiE2.ubeTipoUbicacion = 'ENVIO_CORRESPONDENCIA'
		LEFT JOIN Ubicacion ubi2 ON ubiE2.ubeUbicacion = ubi2.ubiId
		LEFT JOIN Municipio mun2 ON (ubi2.ubiMunicipio = mun2.munId)
		LEFT JOIN Departamento dep2 ON (mun2.munDepartamento = dep2.depId)
		LEFT JOIN #TempUbicacionAud tUbiAud2 on tUbiAud2.id_ubi = ubi2.ubiId

		LEFT JOIN Constante cns ON (cnsNombre = 'CAJA_COMPENSACION_CODIGO')
		LEFT JOIN Parametro prm ON (prmNombre = 'NOMBRE_CCF')

		LEFT JOIN ( 

				SELECT apg.apgPersona, max(apg.apgFechaProcesamiento) AS fechaUltimo
				FROM AporteGeneral2 apg 
				WHERE 
					apg.apgPersona IS NOT NULL AND
					apg.apgEstadoAportante = 'ACTIVO' 
				GROUP BY
					apg.apgPersona

			UNION 
	
				SELECT emp.empPersona, max(apg.apgFechaProcesamiento) AS fechaUltimo
				FROM 
					AporteGeneral2 apg 
					JOIN Empresa emp ON (apg.apgEmpresa = emp.empId)
				WHERE
					apg.apgEstadoAportante = 'ACTIVO'
				GROUP BY 
					emp.empPersona

		) AS personaAporte ON personaAporte.apgPersona = per.perId

		----RESTRICCION PARA HABILITAR CUANDO ENTREMOS EN PRODUCCION 
	/*	WHERE   sol.soltipotransaccion  
			   IN (
				  'ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_PRESENCIAL',
				  'ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_WEB',
				  'ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_PRESENCIAL',
				  'ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_WEB')*/
	-- where 
	---	per.pernumeroIdentificacion = '860007738' ---prueba
	GROUP BY
			cns.cnsValor,
			prm.prmValor,
			PER.perPrimerNombre,
			per.perPrimerApellido,
			perSegundoNombre,
			perSegundoApellido,
			perDigitoVerificacion,
			per.perRazonSocial,
			per.perTipoIdentificacion,
			per.perNumeroIdentificacion,
			perTipoIdentificacion,
			ubi.ubiDireccionFisica,
			dep.depCodigo,
			mun.munCodigo,
			dep.depNombre,
			mun.munNombre,
			ubi.ubiTelefonoFijo,
			ubi.ubiIndicativoTelFijo,
			ubi.ubiTelefonoCelular,
			ubi.ubiEmail,
			ubi2.ubiDireccionFisica,
			dep2.depCodigo,
			mun2.munCodigo,
			dep2.depNombre,
			mun2.munNombre,
			ubi2.ubiTelefonoFijo,
			ubi2.ubiIndicativoTelFijo,
			ubi2.ubiTelefonoCelular,
			ubi2.ubiEmail
			
------------------
	 UNION 
------------------
	---------------------------------------
	------- NUEVAS AFILIACIONES
	---------------------------------------
	SELECT 	

		----------  Codigo Administradora  ----------
		REPLACE(cns.cnsValor, 'CCF0', 'CCF') AS [Cód Administradora],
	
		----------  Nombre Administradora  ----------
		prm.prmValor AS [Nombre administradora],
	
		----------  Razon Social  ----------
		CASE 
			WHEN per.perRazonSocial IS NULL 
			THEN RTrim(
				Coalesce(per.perPrimerNombre + ' ','') 
				+ Coalesce(per.perSegundoNombre + ' ', '')
				+ Coalesce(per.perPrimerApellido + ' ', '')
				+ Coalesce(per.perSegundoApellido, ''))
			ELSE per.perRazonSocial 
		END AS [Nombre o razón social aportante],

		----------  Tipo Identificación  ----------
		CASE per.perTipoIdentificacion
			WHEN 'REGISTRO_CIVIL'		THEN 'RC'
			WHEN 'TARJETA_IDENTIDAD'	THEN 'TI'
			WHEN 'CEDULA_CIUDADANIA'	THEN 'CC'
			WHEN 'CEDULA_EXTRANJERIA'	THEN 'CE'
			WHEN 'PASAPORTE'			THEN 'PA'
			WHEN 'CARNE_DIPLOMATICO'	THEN 'CD'
			WHEN 'NIT'					THEN 'NI'
			WHEN 'SALVOCONDUCTO'		THEN 'SC'
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE' 
			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT' 
		END AS [Tipo de documento del aportante],

		----------  Numero Documento  ----------
		per.perNumeroIdentificacion AS [Número de documento del aportante],

		----------  Digto de Verificacion  ----------
		CASE perTipoIdentificacion 
			WHEN 'NIT' THEN per.perDigitoVerificacion
			ELSE NULL
		END AS [Número de dígito de verificación],

		----------  Direccion Uno  ----------
		CASE
			WHEN RTRIM(ubi.ubiDireccionFisica) = 'SIN DIRECCION'
			THEN ''
			ELSE RTRIM(ubi.ubiDireccionFisica)
		END AS [Dirección 1],

		----------  Codigo Departamento Uno  ----------
		dep.depCodigo AS [Código departamento 1],

		----------  Codigo Municipio Uno  ----------
		mun.munCodigo AS [Código municipio 1],

		----------  Nombre Departamento Uno  ----------
		dep.depNombre AS [Nombre departamento 1],

		----------  Nombre Municipio Uno  ----------
		mun.munNombre AS [Nombre municipio 1],

		----------  Direccion Dos  ----------
		CASE
			WHEN RTRIM(ubi2.ubiDireccionFisica) = 'SIN DIRECCION'
			THEN ''
			ELSE 
				CASE 
					WHEN RTRIM(ubi2.ubiDireccionFisica) = RTRIM(ubi.ubiDireccionFisica)
					THEN ''
					ELSE RTRIM(ubi2.ubiDireccionFisica) 
				END 
		END AS [Dirección 2],

		----------  Codigo Departamento Dos  ----------
		CASE
			WHEN RTRIM(ubi2.ubiDireccionFisica) = 'SIN DIRECCION'
			THEN ''
			ELSE 
				CASE 
					WHEN RTRIM(ubi2.ubiDireccionFisica) = RTRIM(ubi.ubiDireccionFisica)
					THEN ''
					ELSE dep2.depCodigo  
				END 
		END AS [Código departamento 2],

		----------  Codigo Municipio Dos  ----------
		CASE
			WHEN RTRIM(ubi2.ubiDireccionFisica) = 'SIN DIRECCION'
			THEN ''
			ELSE 
				CASE 
					WHEN RTRIM(ubi2.ubiDireccionFisica) = RTRIM(ubi.ubiDireccionFisica)
					THEN ''
					ELSE mun2.munCodigo 
				END 
		END AS [Código municipio 2],

		----------  Nombre Departamento Dos  ----------
		CASE
			WHEN RTRIM(ubi2.ubiDireccionFisica) = 'SIN DIRECCION'
			THEN ''
			ELSE 
				CASE 
					WHEN RTRIM(ubi2.ubiDireccionFisica) = RTRIM(ubi.ubiDireccionFisica)
					THEN ''
					ELSE dep2.depNombre 
				END 
		END AS [Nombre departamento 2],

		----------  Nombre Municipio Dos  ----------
		CASE
			WHEN RTRIM(ubi2.ubiDireccionFisica) = 'SIN DIRECCION'
			THEN ''
			ELSE 
				CASE 
					WHEN RTRIM(ubi2.ubiDireccionFisica) = RTRIM(ubi.ubiDireccionFisica)
					THEN ''
					ELSE mun2.munNombre 
				END 
		END AS [Nombre municipio 2],

		----------  Telefono Uno  ----------
		ISNULL(ubi.ubiTelefonoFijo,'') AS [Teléfono fijo 1],

		----------  Indicativo Telefono Uno  ----------
		ISNULL(ubi.ubiIndicativoTelFijo,'') AS [Indicativo teléfono 1],

		----------  Telefono Dos  ----------
		CASE 
			WHEN ubi2.ubiTelefonoFijo = ubi.ubiTelefonoFijo
				OR ubi2.ubiTelefonoFijo IS NULL
			THEN ''
			ELSE ubi2.ubiTelefonoFijo
		END AS [Teléfono fijo 2],

		----------  Indicativo Telefono Dos  ----------
		CASE 
			WHEN ubi2.ubiTelefonoFijo = ubi.ubiTelefonoFijo
				OR ubi2.ubiTelefonoFijo IS NULL
			THEN ''
			ELSE ubi2.ubiIndicativoTelFijo 
		END AS [Indicativo teléfono 2],

		----------  Celular Uno  ----------
		ISNULL(ubi.ubiTelefonoCelular,'') AS [Celular 1],

		----------  Celular Dos  ----------
		CASE 
			WHEN ubi.ubiTelefonoCelular = ubi2.ubiTelefonoCelular
				OR ubi2.ubiTelefonoCelular IS NULL
			THEN ''
			ELSE ubi2.ubiTelefonoCelular
		END AS [Celular 2],

		----------  Email Uno  ----------
		 CASE WHEN  LEN(ubi.ubiEmail)>50 THEN '' ELSE  ISNULL(ubi.ubiEmail,'') END
		
		AS [Correo electrónico 1],

		----------  Email Dos  ----------
		CASE 
			WHEN   ubi.ubiEmail  =  ubi2.ubiEmail 
				OR ubi2.ubiEmail IS NULL
					OR  LEN(ubi2.ubiEmail)>50 
					OR  LEN(ubi.ubiEmail)>50  
			THEN ''
			ELSE 	  ISNULL(ubi2.ubiEmail,'') 
					 
		END AS [Correo electrónico 2],

		----------  Fecha Actualización  ----------
		/*CASE 
			WHEN ( SELECT DATEDIFF(YEAR, MAX(sol.solFechaRadicacion), @FECHA_FINAL) ) < 1 
			THEN MAX(FORMAT(sol.solFechaRadicacion, 'yyyy-MM-dd')) 
			ELSE ''
		END*/ --MAX(FORMAT(sol.solFechaRadicacion, 'yyyy-MM-dd')) AS [Última fecha de actualización de información]

	--	MAX(FORMAT(sol.solFechaRadicacion, 'yyyy')) AS [Última fecha de actualización de información]
	MAX(FORMAT(CAST(tUbiAud.fecRev AS DATE), 'yyyy')) AS [Última fecha de actualización de información]

	FROM
		SolicitudAfiliaciEmpleador solAfiEmpleador
		INNER JOIN Solicitud sol 
			ON sol.solId = solAfiEmpleador.saeSolicitudGlobal AND 
			sol.solResultadoProceso IN ( 'APROBADA', 'CERRADA')

		----------Ubicacion Persona----------
		INNER JOIN Empleador AS empleador 
			ON empleador.empId = solAfiEmpleador.saeEmpleador
			AND empleador.empEstadoEmpleador = 'ACTIVO'

		INNER JOIN Empresa AS empresa ON empresa.empId = empleador.empEmpresa
		INNER JOIN PERSONA per ON per.perId = empresa.empPersona

		----------Datos ubicacion UNO----------
		INNER JOIN UbicacionEmpresa AS ubiE 
			ON ubiE.ubeEmpresa = empresa.empId AND
			ubiE.ubeTipoUbicacion IN ( 'UBICACION_PRINCIPAL' )
		INNER JOIN Ubicacion ubi ON ubiE.ubeUbicacion = ubi.ubiId
		INNER JOIN Municipio mun ON (ubi.ubiMunicipio = mun.munId)
		INNER JOIN Departamento dep ON (mun.munDepartamento = dep.depId)

		----------Datos ubicacion DOS----------
		INNER JOIN UbicacionEmpresa AS ubiE2 
			ON ubiE2.ubeEmpresa = empresa.empId AND
			ubiE2.ubeTipoUbicacion = 'ENVIO_CORRESPONDENCIA'
		INNER JOIN Ubicacion ubi2 ON ubiE2.ubeUbicacion = ubi2.ubiId
		INNER JOIN Municipio mun2 ON (ubi2.ubiMunicipio = mun2.munId)
		INNER JOIN Departamento dep2 ON (mun2.munDepartamento = dep2.depId)
		
		LEFT JOIN Constante cns ON (cnsNombre = 'CAJA_COMPENSACION_CODIGO')
		LEFT JOIN Parametro prm ON (prmNombre = 'NOMBRE_CCF')
		
		LEFT join #TempUbicacionAud tUbiAud on tUbiAud.id_ubi =  ubi2.ubiId---prueba 20220728

		LEFT JOIN ( 

				SELECT apg.apgPersona, max(apg.apgFechaProcesamiento) AS fechaUltimo
				FROM AporteGeneral2 apg 
				WHERE 
					apg.apgPersona IS NOT NULL AND
					apg.apgEstadoAportante = 'ACTIVO' 
				GROUP BY
					apg.apgPersona
			
			-----------------
			UNION 
			-----------------

				SELECT emp.empPersona, max(apg.apgFechaProcesamiento) AS fechaUltimo
				FROM 
					AporteGeneral2 apg 
					JOIN Empresa emp ON (apg.apgEmpresa = emp.empId)
				WHERE
					apg.apgEstadoAportante = 'ACTIVO'
				GROUP BY 
					emp.empPersona

		) AS personaAporte ON personaAporte.apgPersona = per.perId

	WHERE 
		solAfiEmpleador.saeEstadoSolicitud IN ( 'CERRADA', 'APROBADA' ) 
		--AND sol.solFechaRadicacion <= @FECHA_FINAL PRUEBA
		--AND ( DATEDIFF(YEAR, sol.solFechaRadicacion, @FECHA_FINAL) ) < 1 
		--	 and 
	--	pernumeroIdentificacion = '860007738' ---prueba

			----RESTRICCION PARA HABILITAR CUANDO ENTREMOS EN PRODUCCION 
	/*	SolicitudNovedadEmpleador solNovEmpleador
		INNER JOIN SolicitudNovedad solNov 
				ON solNov.snoId = solNovEmpleador.sneIdSolicitudNovedad
				AND snoEstadoSolicitud IN ('CERRADA', 'APROBADA')
		INNER JOIN Solicitud sol 
			ON sol.solId = solNov.snoSolicitudGlobal  
			AND  sol.soltipotransaccion  
			   IN (
				  'ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_PRESENCIAL',
				  'ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_WEB',
				  'ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_PRESENCIAL',
				  'ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_WEB')*/

	GROUP BY
			cns.cnsValor,
			prm.prmValor,
			PER.perPrimerNombre,
			per.perPrimerApellido,
			perSegundoNombre,
			perSegundoApellido,
			perDigitoVerificacion,
			per.perRazonSocial,
			per.perTipoIdentificacion,
			per.perNumeroIdentificacion,
			perTipoIdentificacion,
			ubi.ubiDireccionFisica,
			dep.depCodigo,
			mun.munCodigo,
			dep.depNombre,
			mun.munNombre,
			ubi.ubiTelefonoFijo,
			ubi.ubiIndicativoTelFijo,
			ubi.ubiTelefonoCelular,
			ubi.ubiEmail,
			ubi2.ubiDireccionFisica,
			dep2.depCodigo,
			mun2.munCodigo,
			dep2.depNombre,
			mun2.munNombre,
			ubi2.ubiTelefonoFijo,
			ubi2.ubiIndicativoTelFijo,
			ubi2.ubiTelefonoCelular,
			ubi2.ubiEmail
	/*---*****************************************************---*/
	)AS consolidado

	GROUP BY 
		consolidado.[Número de documento del aportante],
		consolidado.[Cód Administradora],
		consolidado.[Nombre administradora],
		consolidado.[Nombre o razón social aportante],
		consolidado.[Tipo de documento del aportante],
		consolidado.[Número de documento del aportante],
		consolidado.[Número de dígito de verificación],
		consolidado.[Dirección 1],
		consolidado.[Código departamento 1],
		consolidado.[Código municipio 1],
		consolidado.[Nombre departamento 1],
		consolidado.[Nombre municipio 1],
		consolidado.[Dirección 2],
		consolidado.[Código departamento 2],
		consolidado.[Código municipio 2],
		consolidado.[Nombre departamento 2],
		consolidado.[Nombre municipio 2],
		consolidado.[Teléfono fijo 1],
		consolidado.[Indicativo teléfono 1],
		consolidado.[Teléfono fijo 2],
		consolidado.[Indicativo teléfono 2],
		consolidado.[Celular 1],
		consolidado.[Celular 2],
		consolidado.[Correo electrónico 1],
		consolidado.[Correo electrónico 2]
		
END