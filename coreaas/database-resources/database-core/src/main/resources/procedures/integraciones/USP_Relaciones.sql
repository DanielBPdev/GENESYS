CREATE OR ALTER PROCEDURE  [sap].[USP_Relaciones]  AS 

BEGIN 
SET NOCOUNT ON;

CREATE TABLE #Relaciones(
	[fecing] [date] NOT NULL,
	[horaing] [time](7) NOT NULL,
	[tipocliente1] [varchar](1) NOT NULL,
	[cliente1] [bigint] NOT NULL,
	[identificacionCliente1] [varchar](16) NULL,
	[codrelacion] [varchar](2) NOT NULL,
	[tipocliente2] [varchar](1) NOT NULL,
	[cliente2] [bigint] NOT NULL,
	[identificacionCliente2] [varchar](16) NULL,
	[operacion] [varchar](1) NOT NULL,
	[nrointentos] [int] NOT NULL,
	[fecproceso] [date] NULL,
	[horaproceso] [time](7) NULL,
	[estadoreg] [varchar](1) NULL,
	[observacion] [varchar](255) NULL,
	[usuario] [varchar](50) NULL,
	[FechaEjecucion]  DATETIME )

---SELECT * FROM Sap.Relaciones_G2E
 ---=********************************************************====
 ---Autor: Olga Vega
 ---Fecha Creacion :2021-09-27
 ---Descripcion: procedimiento para reportar las relaciones entre genesys y el ERP de la caja con el fin de detectar las activaciones y retiros que afectan una relacion entre Empresas o personas
 ---Ejecucion: EXEC Sap.USP_Relaciones 

 ---TRUNCATE TABLE Sap.Relaciones_G2E 
  ---=********************************************************====
 ----PARA LAS RELACIONES TRABAJADORES EMPRESAS

		  INSERT INTO #relaciones
 		  SELECT  CONVERT (varchar,SAP.GetLocalDate(),23) AS fecing,
			      CONVERT (varchar,SAP.GetLocalDate(),108)  AS Horaing ,
				  'P' AS tipocliente1, 
				  p.perId AS cliente1, 
				  p.perNumeroIdentificacion AS identificacionCliente1,
				  m.Codigotransaccion AS codrelacion, 
				  'E' AS tipocliente2, 
				  pe.perid AS cliente2, 
				  pe.perNumeroIdentificacion AS identificacionCliente2,
				  CASE WHEN m.Tipotransaccion = 'ING' THEN 'I'ELSE 'R' END AS operacion, 
				  0 AS nrointentos,
				  '' AS fecproceso,
			      '' AS horaproceso,
				  'P' AS estadoreg, 
				  '' AS observacion,
				  '' AS usuario,
				  GETDATE() -'05:00:00' as FechaEjecucion
			FROM Persona p WITH(NOLOCK)
				INNER JOIN Afiliado a WITH(NOLOCK) ON  p.perid = a.afiPersona 
				INNER JOIN RolAfiliado r WITH(NOLOCK) ON a.afiid = r.roaAfiliado
				INNER JOIN SolicitudAfiliacionPersona sap WITH(NOLOCK) ON r.roaid = sap.sapRolAfiliado
				INNER JOIN Solicitud s WITH(NOLOCK) ON sapSolicitudGlobal = s.solid
				INNER JOIN Sap.MaestroRelaciones m WITH(NOLOCK) ON s.solTipoTransaccion = m.Novedad
				INNER JOIN Empleador em WITH(NOLOCK) ON r.roaEmpleador = em.empid
				INNER JOIN Empresa e WITH(NOLOCK) ON em.empEmpresa = e.empid 
				INNER JOIN Persona pe WITH(NOLOCK) ON e.empPersona = pe.perId
	       WHERE m.Origen = 'Trabajador' 
		     AND m.Destino = 'Empresa'---Origen	Destino Trabajador	Beneficiario
			 AND s.solResultadoProceso = 'APROBADA'
			 AND s.solFechaCreacion > GETDATE()-'05:00:00'-1

--UNION

		  INSERT INTO #relaciones
		  SELECT  CONVERT (varchar,SAP.GetLocalDate(),23) AS fecing,
			      CONVERT (varchar,SAP.GetLocalDate(),108)  AS Horaing ,
				  'P' AS tipocliente1, 
				  p.perId AS  cliente1, 
				  p.perNumeroIdentificacion AS identificacionCliente1,
				  m.Codigotransaccion AS codrelacion, 
				  'E' AS tipocliente2, 
				  pe.perid AS cliente2, 
				  pe.perNumeroIdentificacion AS identificacionCliente2,
				  CASE WHEN m.Tipotransaccion = 'ING' THEN 'I'ELSE 'R' END AS operacion, 
				  0 AS nrointentos,
				  '' AS fecproceso,
			      '' AS horaproceso,
				  'P' AS estadoreg, 
				  '' AS observacion,
				  '' AS usuario,
				   GETDATE() -'05:00:00' as FechaEjecucion
			FROM Persona p WITH(NOLOCK)
				INNER JOIN Afiliado a WITH(NOLOCK) ON  p.perid = a.afiPersona 
				INNER JOIN RolAfiliado r WITH(NOLOCK) ON a.afiid = r.roaAfiliado
				INNER JOIN SolicitudNovedadPersona snp WITH(NOLOCK) ON r.roaid = snp.snpRolAfiliado
				INNER JOIN SolicitudNovedad WITH(NOLOCK) ON snoId = snp.snpSolicitudNovedad
				INNER JOIN Solicitud s WITH(NOLOCK) ON snoSolicitudGlobal = s.solid
				INNER JOIN Sap.MaestroRelaciones m WITH(NOLOCK) ON s.solTipoTransaccion = m.Novedad
				INNER JOIN Empleador em WITH(NOLOCK) ON r.roaEmpleador = em.empid
				INNER JOIN Empresa e WITH(NOLOCK) ON em.empEmpresa = e.empid 
				INNER JOIN Persona pe WITH(NOLOCK) ON e.empPersona = pe.perId
	       WHERE m.Origen = 'Trabajador' 
		     AND m.Destino = 'Empresa'---Origen	Destino Trabajador	Beneficiario
			 AND s.solResultadoProceso = 'APROBADA'
			 AND s.solFechaCreacion > GETDATE()-'05:00:00'-1

--UNION -----PARA LAS RELACIONES BENEFICIARIOS 

      INSERT INTO #relaciones
	  SELECT   CONVERT (varchar,SAP.GetLocalDate(),23) AS fecing,
			      CONVERT (varchar,SAP.GetLocalDate(),108)  AS Horaing,
				  'P' AS tipocliente1, 
				  pa.perId AS  cliente1, 
				  pa.perNumeroIdentificacion AS identificacionCliente1,
				  CASE b.benTipoBeneficiario 
				  WHEN 'BENEFICIARIO_EN_CUSTODIA' THEN '10' 
				  WHEN 'CONYUGE' THEN '21'
				  WHEN 'HERMANO_HUERFANO_DE_PADRES' THEN '24'
				  WHEN 'HIJASTRO' THEN '23'
				  WHEN 'HIJO_BIOLOGICO' THEN '22'
				  WHEN 'HIJO_ADOPTIVO' THEN '22'
				  WHEN 'MADRE' THEN '26'
				  WHEN 'PADRE' THEN '25'
				  ELSE '00' END	AS codrelacion, 
				  'P' AS tipocliente2, 
				  p.perid AS cliente2, 
				  p.perNumeroIdentificacion AS identificacionCliente2,
				  'I' AS Operacion,
				  0 AS nrointentos,
				  '' AS fecproceso,
			      '' AS horaproceso, 
				  'P' AS estadoreg, 
				  '' AS observacion,
				  '' AS usuario,
				   GETDATE() -'05:00:00' as FechaEjecucion
			FROM Persona p WITH(NOLOCK)
				INNER JOIN Beneficiario b WITH(NOLOCK) ON p.perid = b.benPersona
				INNER JOIN Afiliado a WITH(NOLOCK) ON  b.benAfiliado = a.afiId 
				INNER JOIN Persona pa WITH(NOLOCK) ON a.afiPersona = pa.perId
	       WHERE b.benEstadoBeneficiarioAfiliado = 'ACTIVO'
		     AND benFechaAfiliacion >= GETDATE()-'05:00:00'-1
			 
--UNION
		  INSERT INTO #relaciones
		  SELECT  CONVERT (varchar,SAP.GetLocalDate(),23) AS fecing,
			      CONVERT (varchar,SAP.GetLocalDate(),108)  AS Horaing,
				  'P' AS tipocliente1, 
				  pa.perId AS  cliente1, 
				  pa.perNumeroIdentificacion AS identificacionCliente1,
				  m.Codigotransaccion AS codrelacion, 
				  'P' AS tipocliente2, 
				  p.perid AS cliente2, 
				  p.perNumeroIdentificacion AS identificacionCliente2,
				  CASE WHEN m.Tipotransaccion = 'ING' THEN 'I'ELSE 'R' END AS operacion, 
				  0 AS nrointentos,
				  '' AS fecproceso,
			      '' AS horaproceso, 
				  'P' AS estadoreg, 
				  '' AS observacion,
				  '' AS usuario,
				   GETDATE() -'05:00:00' as FechaEjecucion
			FROM Persona p WITH(NOLOCK)
				INNER JOIN Beneficiario b WITH(NOLOCK) ON p.perid = b.benPersona
				INNER JOIN Afiliado a WITH(NOLOCK) ON  b.benAfiliado = a.afiId
				INNER JOIN SolicitudNovedadPersona snp WITH(NOLOCK) ON b.benId = snp.snpBeneficiario
				INNER JOIN SolicitudNovedad  WITH(NOLOCK) ON snoId = snp.snpSolicitudNovedad
				INNER JOIN Solicitud s WITH(NOLOCK) ON snoSolicitudGlobal = s.solid
				INNER JOIN Sap.MaestroRelaciones m WITH(NOLOCK) ON s.solTipoTransaccion = m.Novedad
				INNER JOIN Persona pa WITH(NOLOCK) ON a.afiPersona  = pa.perId
	       WHERE m.Origen = 'Trabajador' 
		     AND m.Destino = 'Beneficiario'
			 AND s.solResultadoProceso = 'APROBADA'
			 AND s.solFechaCreacion > GETDATE()-'05:00:00'-1
			 ---nrointentos


			 
---------Validacion cauntos registros se envian a integrar
  /*
			INSERT INTO #registros 
			SELECT COUNT(*) FROM #Relaciones
*/
------------------------------------------------------------
			 ---insertamos lo que no este en estado pendiente con las mismas caracteristicas

			INSERT INTO Sap.Relaciones_G2E (fecing,horaing,tipocliente1,cliente1,identificacionCliente1,codrelacion,
											tipocliente2,cliente2,identificacionCliente2,operacion,nrointentos,
											fecproceso,horaproceso,estadoreg,observacion,usuario, FechaEjecucion)

			        SELECT DISTINCT x.* 
			        FROM #Relaciones x WITH(NOLOCK)
					LEFT JOIN Sap.Relaciones_G2E r WITH(NOLOCK)
			          ON 
						 x.fecing = r.fecing AND
						 x.tipocliente1 = r.tipocliente1 AND
						 x.cliente1 = r.cliente1 AND
						 x.identificacionCliente1 = r.identificacionCliente1 AND
						 x.codrelacion = r.codrelacion AND
						 x.tipocliente2 = r.tipocliente2 AND
						 x.cliente2 = r.cliente2 AND
						 x.identificacionCliente2 = r.identificacionCliente2 
					WHERE r.fecing IS NULL  
					

END