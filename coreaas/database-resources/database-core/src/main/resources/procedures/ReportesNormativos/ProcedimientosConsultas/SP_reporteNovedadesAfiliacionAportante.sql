/****** Object:  StoredProcedure [dbo].[reporteNovedadesAfiliacionAportante]    Script Date: 28/02/2023 4:45:04 p. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteNovedadesAfiliacionAportante]    Script Date: 18/01/2023 4:10:51 p. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteNovedadesAfiliacionAportante]    Script Date: 10/10/2022 11:22:13 a. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteNovedadesAfiliacionAportante]    Script Date: 06/10/2022 12:12:48 p. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteNovedadesAfiliacionAportante]    Script Date: 02/09/2022 4:17:01 p. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteNovedadesAfiliacionAportante]    Script Date: 10/08/2022 6:54:55 p. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteNovedadesAfiliacionAportante]    Script Date: 16/03/2021 8:57:09 a. m. ******/
---EXEC [reporteNovedadesAfiliacionAportante] '2022-12-03','2022-12-09'
---EXEC [reporteNovedadesAfiliacionAportante] '2023-02-01','2023-02-28'
----ARREGLO DE FECHA INICIO Y FIN PARA CONSULTAR SIN HORAS
---reporte #15 Reporte de novedades de estado de afiliacion y al dia  aportante


CREATE OR ALTER PROCEDURE [dbo].[reporteNovedadesAfiliacionAportante](
	@fechaInicio DATETIME,
	@fechaFin DATETIME
)

AS
BEGIN
-- SET ANSI_WARNINGS OFF
SET NOCOUNT ON

SET ANSI_NULLS ON
 
SET QUOTED_IDENTIFIER ON
 

IF OBJECT_ID('tempdb.dbo.#fechaslc') IS NOT NULL DROP TABLE #fechaslc
-- Creación tabla temporal. De aqui se saca el dato de la fecha de salida de la linea de cobro
create table #fechaslc
(
	REV bigint not null,
	carEstadoCartera varchar(6) null,
	carEstadoOperacion varchar(10) null,
	carPersona bigint not null,
	carTipoAccionCobro varchar(4) null,
	carTipoLineaCobro varchar(3) null,
	carId bigint not null,
	RevFecha date  null,
	Origen varchar (max) not null
)

insert into #fechaslc
execute sp_execute_remote N'CoreAudReferenceData',N'SELECT   DISTINCT     c.REV, c.carEstadoCartera, c.carEstadoOperacion, c.carPersona, c.carTipoAccionCobro, c.carTipoLineaCobro, c.carId, 
                         (DATEADD(SECOND, r.revTimeStamp / 1000, ''19700101'') AT TIME ZONE ''UTC'' AT TIME ZONE ''SA Pacific Standard Time'') AS RevFecha 
						    FROM  Cartera_aud c INNER JOIN
                         Revision r ON c.REV = r.revId
		WHERE (c.carEstadoCartera = ''AL_DIA'') 
			AND (c.carEstadoOperacion = ''NO_VIGENTE'')
			AND ((c.carTipoLineaCobro = ''LC1'' )
			OR (c.carTipoLineaCobro = ''C6'' ))'



 ---select * from #fechaslc



 IF OBJECT_ID('tempdb.dbo.#fechaentc') IS NOT NULL DROP TABLE #fechaentc
-- Creación tabla temporal. De aqui se saca el dato de la fecha de en de la linea de cobro
create table #fechaentc
(
	REV bigint not null,
	carEstadoCartera varchar(6) null,
	carEstadoOperacion varchar(10) null,
	carPersona bigint not null,
	carTipoAccionCobro varchar(4) null,
	carTipoLineaCobro varchar(3) null,
	carId bigint not null,
	RevFecha date  null,
	Origen varchar (max) not null
)

insert into #fechaentc
execute sp_execute_remote N'CoreAudReferenceData',N'SELECT   DISTINCT     c.REV, c.carEstadoCartera, c.carEstadoOperacion, c.carPersona, c.carTipoAccionCobro, c.carTipoLineaCobro, c.carId, 
                         (DATEADD(SECOND, r.revTimeStamp / 1000, ''19700101'') AT TIME ZONE ''UTC'' AT TIME ZONE ''SA Pacific Standard Time'') AS RevFecha 
						    FROM  Cartera_aud c INNER JOIN
                         Revision r ON c.REV = r.revId
		WHERE (c.carEstadoCartera = ''MOROSO'') 
		     AND carTipoAccionCobro IS NULL
			AND (c.carEstadoOperacion = ''VIGENTE'')
			AND ((c.carTipoLineaCobro = ''LC1'' )
			OR (c.carTipoLineaCobro = ''C6'' ))'


 
/*

	SELECT distinct 
		----------  Tipo Registro  ----------
		2 AS [Tipo de registro],

		----------  Codigo Caja  ----------
		(
			SELECT cnsValor 
			FROM dbo.Constante 
			WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO'
		) AS [Código caja de compensación familiar],

		----------  Tipo Identificación  ----------
		CASE pa.perTipoIdentificacion
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'PASAPORTE' THEN 'PA'
			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN 'REGISTRO_CIVIL' THEN 'RC'
			WHEN 'NIT' THEN 'NI'
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
			WHEN 'SALVOCONDUCTO' THEN 'SC'
			else pa.perTipoIdentificacion
		END [Tipo de identificación del afiliado],

	--	select perTipoIdentificacion from Persona group by perTipoIdentificacion

		----------  Número de Identificación  ----------
		pa.perNumeroIdentificacion AS [Número de identificación del afiliado],

		----------  Primer Apellido  ----------
		pa.perPrimerApellido AS [Primer Apellido],

		----------  Segundo Apellido  ----------
		pa.perSegundoApellido AS [Segundo Apellido],

		----------  Primer Nombre  ----------
		pa.perPrimerNombre AS [Primer Nombre],

		----------  Segundo Nombre  ----------
		pa.perSegundoNombre AS [Segundo Nombre],

		----------  Codigo Novedad  ----------
		'C05' AS [Código de la novedad],

		----------  Estado Afiliación  ----------
		CASE 
			WHEN roa.roaEstadoAfiliado IN ('ACTIVO')
			THEN 1
				--CASE 
					--WHEN Mora.carId IS NOT NULL 
					--THEN 3
					--ELSE 
					--1
				--END
			ELSE 2
		END [Estado de la afiliación],

		----------  Tipo Identificación Aportante  ----------
		CASE per.perTipoIdentificacion
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'PASAPORTE' THEN 'PA'
			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN 'NIT' THEN 'NI'
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
			WHEN 'SALVOCONDUCTO' THEN 'SC'
		END AS [Tipo de identificación del aportante],

		----------  Numero de Identificacion Aportante  ----------
		per.perNumeroIdentificacion AS [Número de identificación del aportante],

		----------  Digito Verificacion Aportante  ----------
		NULL AS [Digito de verificación del aportante],

		----------  Razón Social Aportante  ----------
		LEFT(
			CASE 
				WHEN ISNULL(per.perPrimerNombre, '') = '' THEN per.perRazonSocial
				ELSE (
					per.perPrimerNombre +

					CASE 
						WHEN per.perSegundoNombre IS NULL THEN ' ' 
						ELSE ' '+per.perSegundoNombre+' ' 
					END +
				
					per.perPrimerApellido +

					CASE 
						WHEN per.perSegundoApellido IS NULL THEN '' 
						ELSE ' '+per.perSegundoApellido 
					END
				) 
			END
		,150) AS [Razón social del aportante],

		----------  Estado Cartera - Al dia  ----------
		CASE 	
			WHEN ISNULL(Mora.carEstadoCartera,'AL_DIA') = 'AL_DIA' THEN '1'
			ELSE '2'
		END AS [Al día]
		---prueba,
	---	, carFechaCreacion as fechasalidaoentrada

		   FROM Persona pa 
     INNER JOIN dbo.Afiliado afi ON afi.afiPersona = pa.perId
     INNER JOIN dbo.RolAfiliado roa 
		     ON roa.roaAfiliado = afi.afiId 
	        AND roa.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
		-- AND roa.roaEstadoAfiliado IN ('ACTIVO')
INNER JOIN CarteraDependiente ON cadPersona= PA.perId
INNER JOIN Empleador em ON em.empid = roa.roaEmpleador
INNER JOIN Empresa e ON e.empid = em.empEmpresa
INNER JOIN persona per ON per.perId = e.empPersona
INNER JOIN (
			    SELECT sapRolAfiliado    
			      FROM SolicitudAfiliacionPersona  WITH(NOLOCK)
			INNER JOIN Solicitud  WITH(NOLOCK) ON Solid = sapSolicitudGlobal
			     WHERE --sapRolAfiliado = 403012
				 solFechaRadicacion BETWEEN @fechaInicio AND @fechaFin
				   --AND solTipoTransaccion in ( 'NOVEDAD_REINTEGRO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO','AFILIACION_PERSONAS_WEB_DEPENDIENTE_REINTEGRO')
           ) AS reintegrosafil
		ON reintegrosafil.sapRolAfiliado = roa.roaId 

INNER JOIN (    SELECT roaAfiliado  
			      FROM SolicitudAfiliacionPersona WITH(NOLOCK)
			INNER JOIN Solicitud WITH(NOLOCK) ON Solid = sapSolicitudGlobal
			INNER JOIN RolAfiliado r WITH(NOLOCK) ON sapRolAfiliado = r.roaId
			     WHERE solFechaRadicacion < @fechaInicio 
				 AND solTipoTransaccion IN ('AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION','AFILIACION_PERSONAS_WEB_DEPENDIENTE_NUEVA_AFILIACION')
			) AS afilant
			ON	afilant.roaAfiliado = roa.roaAfiliado 

 	 LEFT JOIN Cartera AS Mora with(nolock)
			ON Mora.carPersona = per.perId
			AND Mora.carEstadoOperacion = 'VIGENTE'
			AND Mora.carDeudaPresunta > 0
			AND mora.carTipoLineaCobro IN ('LC1')
	 WHERE  Mora.carPersona NOT IN ( SELECT carPersona 
								   FROM cartera  
								  WHERE carEstadoCartera = 'MOROSO' 
								    AND carDeudaPresunta>0
									AND carTipoLineaCobro IN ('LC1')
								GROUP BY carPersona
								HAVING COUNT(*)>1)
		AND Mora.carFechaCreacion>=@fechaInicio AND Mora.carFechaCreacion<=@fechaFin

  UNION  -- all prueba
  */

/*	SELECT DISTINCT
		----------  Tipo Registro  ----------
		2 AS [Tipo de registro],

		----------  Codigo Caja  ----------
		(
			SELECT cnsValor 
			FROM dbo.Constante 
			WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO'
		) AS [Código caja de compensación familiar],

		----------  Tipo Identificacion Afiliado  ----------
		CASE pa.perTipoIdentificacion
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'PASAPORTE' THEN 'PA'
			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN 'REGISTRO_CIVIL' THEN 'RC'
			WHEN 'NIT' THEN 'NI'
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
			WHEN 'SALVOCONDUCTO' THEN 'SC'
		ELSE pa.perTipoIdentificacion
		END AS [Tipo de identificación del afiliado],

		----------  Numero de Identificacion Afiliado  ----------
		pa.perNumeroIdentificacion AS [Número de identificación del afiliado ],

		----------  Primer Apellido Afiliado  ----------
		pa.perPrimerApellido AS [Primer Apellido],
	
		----------  Segundo Apellido Afiliado  ----------
		pa.perSegundoApellido AS [Segundo Apellido],
	
		----------  Primer Nombre Afiliado  ----------
		pa.perPrimerNombre AS [Primer Nombre],
	
		----------  Segundo Nombre  ----------
		pa.perSegundoNombre AS [Segundo Nombre],
	
		---------- Codigo Novedad  ----------
		'C06' AS [Código de la novedad],

		----------  Estado Afiliación  ----------
		CASE 
			WHEN em.empEstadoEmpleador IN ('ACTIVO')
			THEN 1
				--CASE 
				--	WHEN Mora.carId IS NOT NULL THEN 3
				--	ELSE 1
				--END
			ELSE 2
		END [Estado de la afiliación],

		/*CASE 
			WHEN Mora.carId IS NOT NULL THEN 3
			ELSE 1
		END [Estado de la afiliación],*/

		----------  Tipo Identificación Aportante  ----------
		CASE pem.perTipoIdentificacion
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'PASAPORTE' THEN 'PA'
			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN 'NIT' THEN 'NI'
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
			WHEN 'SALVOCONDUCTO' THEN 'SC'
		END [Tipo de identificación del aportante],

		----------  Numero de identificacion del aportante  ----------
		pem.perNumeroIdentificacion [Número de identificación del aportante],

		----------  Digito de verificacion del aportante  ----------
		CASE pem.perTipoIdentificacion 
			WHEN 'NIT' then pem.perDigitoVerificacion 
			ELSE NULL
		END AS [Digito de verificación del aportante],

		----------  Razon Social Aportante  ----------
		LEFT(
			CASE 
				WHEN ISNULL(pem.perPrimerNombre, '') = '' THEN pem.perRazonSocial
				ELSE (
					pem.perPrimerNombre +

					CASE 
						WHEN pem.perSegundoNombre IS NULL THEN ' ' 
						ELSE ' '+pem.perSegundoNombre+' ' 
					END +

					pem.perPrimerApellido +

					CASE
						WHEN pem.perSegundoApellido IS NULL THEN '' 
						ELSE ' '+pem.perSegundoApellido 
					END
				) 
			END
		,150) AS [Razón social del aportante],

		----------  Estado Cartera - Al dia  ----------
		CASE WHEN	
			Mora.carId IS NULL  THEN '1'
			ELSE '2'
		END AS [Al día]

	--	, carFechaCreacion as fechasalidaoentrada
		   FROM Persona pa 
     INNER JOIN dbo.Afiliado afi ON afi.afiPersona = pa.perId
     INNER JOIN dbo.RolAfiliado roa 
		     ON roa.roaAfiliado = afi.afiId 
	     AND roa.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
	--	 AND roa.roaEstadoAfiliado IN ('ACTIVO')
INNER JOIN CarteraDependiente ON cadPersona= PA.perId 
INNER JOIN Empleador em ON em.empid = roa.roaEmpleador
INNER JOIN Empresa e ON e.empid = em.empEmpresa
INNER JOIN persona pem ON pem.perId = e.empPersona
INNER JOIN Cartera AS Mora with(nolock)
			ON Mora.carPersona = pem.perId
			AND Mora.carEstadoOperacion = 'VIGENTE'
			AND Mora.carDeudaPresunta > 0
			AND CONVERT(DATE,Mora.carFechaCreacion) BETWEEN @fechaInicio AND @fechaFin
		    AND mora.carTipoLineaCobro IN ('LC1' )
			AND Mora.Carid = cadCartera
				 WHERE  Mora.carPersona NOT IN ( SELECT carPersona 
								   FROM cartera  
								  WHERE carEstadoCartera = 'MOROSO' 
								    AND carDeudaPresunta>0
									AND carTipoLineaCobro IN ('LC1')
								GROUP BY carPersona
								HAVING COUNT(*)>1)
AND CONVERT(DATE,Mora.carFechaCreacion) BETWEEN @fechaInicio AND @fechaFin
---AND pem.perNumeroIdentificacion IN ( '75082788','1053822473')


 UNION 
 */



	SELECT DISTINCT
		----------  Tipo Registro  ----------
		2 AS [Tipo de registro],

		----------  Codigo Caja  ----------
		(
			SELECT cnsValor 
			FROM dbo.Constante 
			WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO'
		) AS [Código caja de compensación familiar],

		----------  Tipo Identificacion Afiliado  ----------
		CASE pa.perTipoIdentificacion
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'PASAPORTE' THEN 'PA'
			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN 'REGISTRO_CIVIL' THEN 'RC'
			WHEN 'NIT' THEN 'NI'
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
			WHEN 'SALVOCONDUCTO' THEN 'SC'
		ELSE pa.perTipoIdentificacion
		END AS [Tipo de identificación del afiliado],

		----------  Numero de Identificacion Afiliado  ----------
		pa.perNumeroIdentificacion AS [Número de identificación del afiliado ],

		----------  Primer Apellido Afiliado  ----------
		pa.perPrimerApellido AS [Primer Apellido],
	
		----------  Segundo Apellido Afiliado  ----------
		pa.perSegundoApellido AS [Segundo Apellido],
	
		----------  Primer Nombre Afiliado  ----------
		pa.perPrimerNombre AS [Primer Nombre],
	
		----------  Segundo Nombre  ----------
		pa.perSegundoNombre AS [Segundo Nombre],
	
		---------- Codigo Novedad  ----------
		'C06' AS [Código de la novedad],

		----------  Estado Afiliación  ----------
		CASE 
			WHEN em.empEstadoEmpleador IN ('ACTIVO')
			THEN 1
				--CASE 
				--	WHEN Mora.carId IS NOT NULL THEN 3
				--	ELSE 1
				--END
			ELSE 2
		END [Estado de la afiliación],

		/*CASE 
			WHEN Mora.carId IS NOT NULL THEN 3
			ELSE 1
		END [Estado de la afiliación],*/

		----------  Tipo Identificación Aportante  ----------
		CASE pem.perTipoIdentificacion
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'PASAPORTE' THEN 'PA'
			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN 'NIT' THEN 'NI'
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
			WHEN 'SALVOCONDUCTO' THEN 'SC'
		END [Tipo de identificación del aportante],

		----------  Numero de identificacion del aportante  ----------
		pem.perNumeroIdentificacion [Número de identificación del aportante],

		----------  Digito de verificacion del aportante  ----------
		CASE pem.perTipoIdentificacion 
			WHEN 'NIT' then pem.perDigitoVerificacion 
			ELSE NULL
		END AS [Digito de verificación del aportante],

		----------  Razon Social Aportante  ----------
		LEFT(
			CASE 
				WHEN ISNULL(pem.perPrimerNombre, '') = '' THEN pem.perRazonSocial
				ELSE (
					pem.perPrimerNombre +

					CASE 
						WHEN pem.perSegundoNombre IS NULL THEN ' ' 
						ELSE ' '+pem.perSegundoNombre+' ' 
					END +

					pem.perPrimerApellido +

					CASE
						WHEN pem.perSegundoApellido IS NULL THEN '' 
						ELSE ' '+pem.perSegundoApellido 
					END
				) 
			END
		,150) AS [Razón social del aportante],

		----------  Estado Cartera - Al dia  ----------
		CASE 	
			WHEN  ISNULL(salida.carEstadoCartera,'AL_DIA')= 'AL_DIA' THEN '1'
			ELSE '2'
		END AS [Al día]
	--	, salida.RevFecha as fechasalidaoentrada
	INTO #FINAL

 	   FROM Persona pa 
     INNER JOIN dbo.Afiliado afi ON afi.afiPersona = pa.perId
     INNER JOIN dbo.RolAfiliado roa 
		 ON roa.roaAfiliado = afi.afiId 
	     AND roa.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
	--	 AND roa.roaEstadoAfiliado IN ('ACTIVO')
INNER JOIN CarteraDependiente ON cadPersona= pa.perId
INNER JOIN Empleador em ON em.empid = roa.roaEmpleador
INNER JOIN Empresa e ON e.empid = em.empEmpresa
INNER JOIN persona pem ON pem.perId = e.empPersona
INNER JOIN cartera c ON c.carPersona = pem.perid 	AND c.Carid = cadCartera

INNER JOIN #fechaslc salida   
ON  c.carId=salida.CARID AND c.carPersona=salida.carPersona
     WHERE CONVERT(DATE,salida.RevFecha) BETWEEN @fechaInicio AND @fechaFin
	     AND c.carTipoLineaCobro IN ('LC1','C6' )
	 	 AND  c.carPersona NOT IN ( SELECT carPersona 
								   FROM cartera  
								  WHERE carEstadoCartera = 'MOROSO' 
								    AND carDeudaPresunta>0
									AND carTipoLineaCobro IN ('LC1','C6')
								GROUP BY carPersona
								HAVING COUNT(*)>1)
        AND CONVERT(DATE,salida.RevFecha)  BETWEEN @fechaInicio AND  @fechaFin
	 --39241
	--  AND   pem.perNumeroIdentificacion IN ( '10163780')
	--ORDER BY 12

	UNION
	

	SELECT DISTINCT
		----------  Tipo Registro  ----------
		2 AS [Tipo de registro],

		----------  Codigo Caja  ----------
		(
			SELECT cnsValor 
			FROM dbo.Constante 
			WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO'
		) AS [Código caja de compensación familiar],

		----------  Tipo Identificacion Afiliado  ----------
		CASE pa.perTipoIdentificacion
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'PASAPORTE' THEN 'PA'
			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN 'REGISTRO_CIVIL' THEN 'RC'
			WHEN 'NIT' THEN 'NI'
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
			WHEN 'SALVOCONDUCTO' THEN 'SC'
		ELSE pa.perTipoIdentificacion
		END AS [Tipo de identificación del afiliado],

		----------  Numero de Identificacion Afiliado  ----------
		pa.perNumeroIdentificacion AS [Número de identificación del afiliado ],

		----------  Primer Apellido Afiliado  ----------
		pa.perPrimerApellido AS [Primer Apellido],
	
		----------  Segundo Apellido Afiliado  ----------
		pa.perSegundoApellido AS [Segundo Apellido],
	
		----------  Primer Nombre Afiliado  ----------
		pa.perPrimerNombre AS [Primer Nombre],
	
		----------  Segundo Nombre  ----------
		pa.perSegundoNombre AS [Segundo Nombre],
	
		---------- Codigo Novedad  ----------
		'C06' AS [Código de la novedad],

		----------  Estado Afiliación  ----------
		CASE 
			WHEN em.empEstadoEmpleador IN ('ACTIVO')
			THEN 1
				--CASE 
				--	WHEN Mora.carId IS NOT NULL THEN 3
				--	ELSE 1
				--END
			ELSE 2
		END [Estado de la afiliación],

		/*CASE 
			WHEN Mora.carId IS NOT NULL THEN 3
			ELSE 1
		END [Estado de la afiliación],*/

		----------  Tipo Identificación Aportante  ----------
		CASE pem.perTipoIdentificacion
			WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN 'PASAPORTE' THEN 'PA'
			WHEN 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN 'NIT' THEN 'NI'
			WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE'
			WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
			WHEN 'SALVOCONDUCTO' THEN 'SC'
		END [Tipo de identificación del aportante],

		----------  Numero de identificacion del aportante  ----------
		pem.perNumeroIdentificacion [Número de identificación del aportante],

		----------  Digito de verificacion del aportante  ----------
		CASE pem.perTipoIdentificacion 
			WHEN 'NIT' then pem.perDigitoVerificacion 
			ELSE NULL
		END AS [Digito de verificación del aportante],

		----------  Razon Social Aportante  ----------
		LEFT(
			CASE 
				WHEN ISNULL(pem.perPrimerNombre, '') = '' THEN pem.perRazonSocial
				ELSE (
					pem.perPrimerNombre +

					CASE 
						WHEN pem.perSegundoNombre IS NULL THEN ' ' 
						ELSE ' '+pem.perSegundoNombre+' ' 
					END +

					pem.perPrimerApellido +

					CASE
						WHEN pem.perSegundoApellido IS NULL THEN '' 
						ELSE ' '+pem.perSegundoApellido 
					END
				) 
			END
		,150) AS [Razón social del aportante],

		----------  Estado Cartera - Al dia  ----------
		CASE 	
			WHEN  ISNULL(entrada.carEstadoCartera,'MOROSO')= 'MOROSO' THEN '2'
			ELSE '1'
		END AS [Al día]
	--	, salida.RevFecha as fechasalidaoentrada
 	   FROM Persona pa 
     INNER JOIN dbo.Afiliado afi ON afi.afiPersona = pa.perId
     INNER JOIN dbo.RolAfiliado roa 
		 ON roa.roaAfiliado = afi.afiId 
	     AND roa.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
	--	 AND roa.roaEstadoAfiliado IN ('ACTIVO')
INNER JOIN CarteraDependiente ON cadPersona= pa.perId
INNER JOIN Empleador em ON em.empid = roa.roaEmpleador
INNER JOIN Empresa e ON e.empid = em.empEmpresa
INNER JOIN persona pem ON pem.perId = e.empPersona
INNER JOIN cartera c ON c.carPersona = pem.perid 	AND c.Carid = cadCartera

INNER JOIN #fechaentc entrada   
ON  c.carId=entrada.CARID AND c.carPersona=entrada.carPersona
     WHERE CONVERT(DATE,entrada.RevFecha) BETWEEN @fechaInicio AND @fechaFin
	     AND c.carTipoLineaCobro IN ('LC1','C6' )
	 	 AND  c.carPersona NOT IN ( SELECT carPersona 
								   FROM cartera  
								  WHERE carEstadoCartera = 'MOROSO' 
								    AND carDeudaPresunta>0
									AND carTipoLineaCobro IN ('LC1','C6')
								GROUP BY carPersona
								HAVING COUNT(*)>1)
        AND CONVERT(DATE,entrada.RevFecha)  BETWEEN @fechaInicio AND  @fechaFin
	 --39241
	--   AND   pem.perNumeroIdentificacion IN ( '10163780')
	ORDER BY 12 asc,15 desc

		 -- SELECT RevFecha, Carpersona , 2 as aldia  FROM #fechaentc WHERE CARPERSONA = 59120
		   
		 --SELECT RevFecha, carpersona, 1 as salida   FROM #fechaslc WHERE CARPERSONA = 59120

	 

		 SELECT 
		 CASE WHEN rev = MAX(rev)  
						    OVER (PARTITION BY carpersona) THEN rev ELSE NULL END AS rev,
							  revfecha, carpersona , aldia 
		 INTO #maxestado
		 FROM (
		                SELECT CASE WHEN rev = MAX(rev)  
						    OVER (PARTITION BY carpersona) THEN rev ELSE NULL END AS rev,
							RevFecha, Carpersona , 2 as aldia  
							FROM #fechaentc
		  UNION
		 SELECT CASE WHEN rev = MAX(rev)  
						    OVER (PARTITION BY carpersona) THEN rev ELSE NULL END AS rev,
							RevFecha, carpersona, 1 as salida   FROM #fechaslc
		 ) maxestado
	 
 

  	 	 SELECT XX.carpersona , XX.aldia 
		  into #estadofinal
		  FROM #maxestado XX
		  WHERE rev is not null 

 

	SELECT[Tipo de registro],	[Código caja de compensación familiar],
	      [Tipo de identificación del afiliado],
		  [Número de identificación del afiliado], 
		  [Primer Apellido],
		  [Segundo Apellido],
		  [Primer Nombre],
		  [Segundo Nombre],	[Código de la novedad],
		  [Estado de la afiliación],	
		  [Tipo de identificación del aportante],
		  [Número de identificación del aportante],
		  [Digito de verificación del aportante],
		  [Razón social del aportante],	  F.[Al día]
 FROM #FINAL F
 INNER JOIN (	SELECT perNumeroIdentificacion,aldia
				FROM #estadofinal
				inner join Persona ON perid = carPersona
				group by perNumeroIdentificacion,aldia
				 )X 
         ON X.aldia= F.[Al día] 
		 AND X.perNumeroIdentificacion = F.[Número de identificación del aportante]


END