
CREATE OR ALTER PROCEDURE [sap].[USP_INSERT_Contactos_G2E]    AS

/*  Creador  : Olga Vega
    Fecha   : 2021-09-100
    Descripcion: CONSULTA CON O SIN TIPO Y NRO DE IDENTIFICACIOCION Y FECHA LOS DATOS DE LOS CONTACTOS 
					RL (empRepresentanteLegal)
					RLS (empRepresentanteLegalSuplente)
					JGH (rceTipoRolContactoEmpleador)
					EAP (rceTipoRolContactoEmpleador)
					ECU (rceTipoRolContactoEmpleador)
				desde genesys para integracion con el ERP de comfenalco
				select * from sap.Contactos_G2E where numerodoc = '13248092'
	Ejecucion :
	            EXEC Sap.USP_INSERT_Contactos_G2E
*/
 SET NOCOUNT ON ---agregado para evitar que conjuntos de resultados adicionales interfieran con las sentencias SELECT.CONFIGURAR SIN CUENTA EN;
			    
 BEGIN TRY 



				----PRIMERO UBICO TODOS LOS INGRESOS Y SUS ÚLTIMOS DATOS   
				----INGRESOS 
 		       SELECT CASE WHEN solid = MAX(solid) OVER (PARTITION BY em.empid) THEN solId ELSE NULL END AS SolId,  
						  CASE WHEN solid = MAX(solid) OVER (PARTITION BY em.empid) THEN solFechaRadicacion ELSE NULL END AS FechaRadicacion,
						  em.empId AS IdEmpleador,
						  em.empEmpresa,
						  perId AS PeridEmpresa,
						  solTipoTransaccion AS TipoTransaccion,
						  solNumeroRadicacion AS solNumeroRadicacion,
						  perNumeroIdentificacion AS perNumeroIdentificacion,
						  perTipoIdentificacion AS perTipoIdentificacion
					 INTO #INGRESOS
			         FROM Persona WITH(NOLOCK)
						INNER JOIN empresa e WITH(NOLOCK)
			           ON perid = emppersona  
						INNER JOIN empleador em WITH(NOLOCK)
			           ON  e.empid = em.empEmpresa
						INNER JOIN SolicitudAfiliaciEmpleador WITH(NOLOCK)
			           ON em.empid = saeEmpleador  
						INNER JOIN Solicitud WITH(NOLOCK)
			           ON  saeSolicitudGlobal = solid
			        WHERE solResultadoProceso = 'APROBADA'
					  AND  NOT EXISTS  (SELECT solNumeroRadicacion 
						  FROM sap.ContactosCtrl CTR WITH (NOLOCK) WHERE solicitud.solNumeroRadicacion = CTR.solNumeroRadicacion )
					  --AND solFechaRadicacion = '20220202' --para pruebas John Sotelo 02062022
					  AND solFechaRadicacion > GETDATE() -'05:00:00' - 1
		
---				SELECT * FROM #INGRESOS

---SI HAY UN CAMBIO DE TIPOS Y NUMERO DE IDENTIFICAION DEBERIA SE COMO NUEVO
				INSERT INTO #INGRESOS
				SELECT CASE WHEN solid = MAX(solid) OVER (PARTITION BY em.empid) THEN solId ELSE NULL END AS SolId,  
						  CASE WHEN solid = MAX(solid) OVER (PARTITION BY em.empid) THEN solFechaRadicacion ELSE NULL END AS FechaRadicacion,
						  em.empId AS IdEmpleador,
						  em.empEmpresa,
						  perId AS PeridEmpresa,
						  solTipoTransaccion AS TipoTransaccion,
						  solNumeroRadicacion AS solNumeroRadicacion,
						  perNumeroIdentificacion AS perNumeroIdentificacion,
						  perTipoIdentificacion AS perTipoIdentificacion
			         FROM Persona WITH(NOLOCK)
			   INNER JOIN empresa e WITH(NOLOCK)
			           ON perid = emppersona  
			   INNER JOIN empleador em WITH(NOLOCK)
			           ON e.empid = em.empEmpresa
			   INNER JOIN SolicitudNovedadEmpleador WITH(NOLOCK)
			           ON em.empid = sneIdEmpleador
			   INNER JOIN SolicitudNovedad WITH(NOLOCK)
			           ON sneIdSolicitudNovedad = snoId
			   INNER JOIN Solicitud sol WITH(NOLOCK)
			           ON snoSolicitudGlobal = solid 
			        WHERE solResultadoProceso = 'APROBADA'
					  AND solTipoTransaccion in (
												 'CAMBIO_RAZON_SOCIAL_NOMBRE' ,
												 'CAMBIO_TIPO_NUMERO_DOCUMENTO'  
												)
					  AND NOT EXISTS (SELECT ctr.solNumeroRadicacion 
						  FROM sap.ContactosCtrl ctr WITH (NOLOCK) where ctr.solNumeroRadicacion = sol.solNumeroRadicacion)
					  AND solFechaRadicacion > GETDATE() - '05:00:00' - 1 
			 
	--	----REPRESENTANTE LEGAL
 
	       SELECT CONVERT(varchar, SAP.GetLocalDate(), 23) AS fecing, 
				  CONVERT (varchar, SAP.GetLocalDate(), 108) AS horaing,
				  '' AS codigosap,
				  LEFT(H.tipoDocumentoCaja,2) AS tipodoc,
				  p.perNumeroIdentificacion AS numerodoc,
				  UPPER(CONCAT(p.PerPrimerNombre,' ', p.perSegundoNombre)) AS nombrecompleto,
				  CONCAT(p.perPrimerApellido,' ', p.perSegundoApellido) As nombrepila,
				  'RL' AS TipoContacto,
				  pedGenero AS genero,
				  pedFechaNacimiento AS fecnac,
				  pedEstadoCivil AS estadocivil,
				  ubiTelefonoFijo AS telefono,
				  ubiTelefonoCelular AS celular,
				  ubiEmail AS email,
				  LEFT(ubiDireccionFisica,60) AS direcc,				  
				  munCodigo AS ciudad,
				  depCodigo AS dpto,
				  CASE WHEN ubiAutorizacionEnvioEmail = 1 THEN 'S' ELSE 'N' END AS autenvio,
				  pe.perNumeroIdentificacion AS nit,
				  LEFT(h2.tipoDocumentoCaja,2) AS tipodocEmpresa,
				  pe.perId AS CodigoGenesys,
				  'I' AS operacion,
				  '' AS nrointentos,
				  '' AS fecproceso,
				  '' AS horaproceso,
				  'P' AS estadoreg,
				  '' AS observacion,
				  '' AS usuario
			 INTO #ContactosT_G2E
		     FROM Persona p WITH(NOLOCK)
	   LEFT JOIN PersonaDetalle pd WITH(NOLOCK)
	           ON p.perid = pd.pedPersona
	---====informacion empresa   
	   INNER JOIN Empresa e WITH(NOLOCK)
	           ON p.perId =  e.empRepresentanteLegal
	   INNER JOIN Empleador emp WITH(NOLOCK)
	           ON e.empid = emp.empEmpresa
       INNER JOIN Persona pe WITH(NOLOCK)
	           ON e.emppersona = pe.perId
	---====informacion ubicacion contacto
	   INNER JOIN Ubicacion u WITH(NOLOCK)
	           ON e.empUbicacionRepresentanteLegal= u.ubiid 
	   LEFT JOIN Municipio m WITH(NOLOCK)
	           ON u.ubiMunicipio= m.munId
	   LEFT JOIN Departamento d WITH(NOLOCK)
	           ON d.depid = m.munDepartamento
	   INNER JOIN sap.MaestraIdentificacion h WITH(NOLOCK)
	           ON p.pertipoidentificacion = h.TipoIdGenesys
	   INNER JOIN sap.MaestraIdentificacion h2 WITH(NOLOCK)
	           ON pe.pertipoidentificacion = h2.TipoIdGenesys
	   INNER JOIN #INGRESOS rl WITH(NOLOCK)
	           ON rl.IdEmpleador = emp.empid 
		UNION 
		----REPRESENTANTE LEGAL SUPLENTE
           SELECT 
				  CONVERT(varchar, SAP.GetLocalDate(), 23) AS fecing, 
				  CONVERT (varchar, SAP.GetLocalDate(), 108) AS horaing,
				  '' AS codigosap,
				  LEFT(H.tipoDocumentoCaja,2) AS tipodoc,
				  p.perNumeroIdentificacion,
				  UPPER(CONCAT(p.PerPrimerNombre,' ', p.perSegundoNombre)),
				  UPPER(CONCAT(p.perPrimerApellido,' ', p.perSegundoApellido)),
				  'RLS' AS TipoContacto,
				  pedGenero,
				  pedFechaNacimiento,
				  pedEstadoCivil,
				  ubiTelefonoFijo,
				  ubiTelefonoCelular,
				  ubiEmail,
				  ubiDireccionFisica,  
				  munCodigo,
				  depCodigo,
				  CASE WHEN ubiAutorizacionEnvioEmail = 1 THEN 'S' ELSE 'N' END,
				  pe.perNumeroIdentificacion AS nit,
				  LEFT(h2.tipoDocumentoCaja,2) AS tipodocEmpresa,
				  pe.perId,
				  'I' AS operacion,
				  '' AS nrointentos,
				  '' AS fecproceso,
				  '' AS horaproceso,
				  'P' AS estadoreg,
				  '' AS observacion,
				  ''  AS usuario
	         FROM Persona p WITH(NOLOCK)
	    LEFT JOIN PersonaDetalle pd WITH(NOLOCK)
	           ON p.perid = pd.pedPersona
---====informacion empresa
	   INNER JOIN Empresa e WITH(NOLOCK)
	           ON p.perId =  e.empRepresentanteLegalSuplente
	   INNER JOIN Empleador emp WITH(NOLOCK)
	           ON e.empid = emp.empEmpresa
       INNER JOIN Persona pe WITH(NOLOCK)
	           ON e.emppersona = pe.perId
	   INNER JOIN sap.MaestraIdentificacion h WITH(NOLOCK)
	           ON p.pertipoidentificacion = h.TipoIdGenesys
	   INNER JOIN sap.MaestraIdentificacion h2 WITH(NOLOCK)
	           ON pe.pertipoidentificacion = h2.TipoIdGenesys
	   INNER JOIN #INGRESOS rl WITH(NOLOCK)
	           ON rl.IdEmpleador = emp.empid
---====informacion ubicacion contacto
 	   LEFT JOIN Ubicacion u WITH(NOLOCK)
	           ON e.empUbicacionRepresentanteLegalSuplente= u.ubiid 
	   LEFT JOIN Municipio m WITH(NOLOCK)
	           ON u.ubiMunicipio= m.munId
	   LEFT JOIN Departamento d WITH(NOLOCK)
	           ON d.depid = m.munDepartamento  
		UNION 
		------'JGH' (rceTipoRolContactoEmpleador)
		------'EAP' (rceTipoRolContactoEmpleador)
		------'ECU' (rceTipoRolContactoEmpleador)
           SELECT 
				  CONVERT(varchar, SAP.GetLocalDate(), 23) AS fecing, 
				  CONVERT (varchar, SAP.GetLocalDate(), 108) AS horaing,
				  '' AS codigosap,
				  LEFT(H.tipoDocumentoCaja,2),
				  p.perNumeroIdentificacion,
				  UPPER(CONCAT(p.perPrimerNombre,' ', p.perSegundoNombre)),
				  UPPER(CONCAT(p.perPrimerApellido,' ', p.perSegundoApellido)),
				  CASE rceTipoRolContactoEmpleador 
				  WHEN 'ROL_RESPONSABLE_AFILIACIONES' THEN 'JGH' 
				  WHEN 'ROL_RESPONSABLE_SUBSIDIOS' THEN 'ECU'
				  WHEN 'ROL_RESPONSABLE_APORTES' THEN 'EAP'
				  ELSE '' END AS TipoContacto,
				  pedGenero,
				  pedFechaNacimiento,
				  pedEstadoCivil,
				  ubiTelefonoFijo,
				  ubiTelefonoCelular,
				  ubiEmail,
				  ubiDireccionFisica,----				  
				  munCodigo,
				  depCodigo,
				  CASE WHEN ubiAutorizacionEnvioEmail = 1 THEN 'S' ELSE 'N' END,
				  pe.perNumeroIdentificacion AS nit,
				  LEFT(h2.tipoDocumentoCaja,2) AS tipodocEmpresa,
				  pe.perID,
				  'I' AS operacion,
				  '' AS nrointentos,
				  '' AS fecproceso,
				  '' AS horaproceso,
				  'P'AS estadoreg,
				  '' AS observacion,
				  '' AS usuario
	         FROM Persona p WITH(NOLOCK)
	   INNER JOIN RolContactoEmpleador WITH(NOLOCK)
	           ON rcePersona = perId
	   LEFT JOIN PersonaDetalle pd WITH(NOLOCK)
	           ON p.perid = pd.pedPersona
---====informacion empresa
	   INNER JOIN Empleador emp WITH(NOLOCK)
	           ON rceEmpleador =  emp.empId
	   INNER JOIN Empresa E WITH(NOLOCK)
	           ON emp.empEmpresa =  E.empId
	   INNER JOIN Persona pe WITH(NOLOCK)
	           ON e.empPersona = pe.perid 
---====ubicacion contacto empresa
 	   INNER JOIN Ubicacion u WITH(NOLOCK)
	           ON rceUbicacion = u.ubiid 
	   LEFT JOIN Municipio m WITH(NOLOCK)
	           ON u.ubiMunicipio= m.munId
	   LEFT JOIN Departamento d WITH(NOLOCK)
	           ON d.depid = m.munDepartamento
	   INNER JOIN sap.MaestraIdentificacion h WITH(NOLOCK)
	           ON p.pertipoidentificacion = h.TipoIdGenesys
	   INNER JOIN sap.MaestraIdentificacion h2 WITH(NOLOCK)
	           ON pe.pertipoidentificacion = h2.TipoIdGenesys
------==========
	   INNER JOIN #INGRESOS aso WITH(NOLOCK)
	           ON aso.IdEmpleador = emp.empid  
			
	     GROUP BY h2.CodigoERP, h.CodigoERP, p.pernumeroidentificacion, p.pertipoidentificacion,p.perId,p.perPrimerNombre,p.perSegundoNombre,
				  p.perPrimerApellido, p.perSegundoApellido, p.perrazonsocial, u.ubiDireccionFisica, m.munNombre,  
				  pedGenero, pedFechaNacimiento,pedEstadoCivil,munCodigo,ubiAutorizacionEnvioEmail,
				  d.depNombre , u.ubiTelefonoFijo, u.ubiTelefonoCelular, u.ubiEmail ,h.TipoDocumentoCaja, depCodigo	,
				  pe.perNumeroIdentificacion, pe.perTipoIdentificacion,  pe.perId, rceTipoRolContactoEmpleador,h2.TipoDocumentoCaja

  ------------******------DESPUÉS TRAIGO LOS ÚLTIMOS DATOS EN LA AUDITORIA PARA PODER ARMAR LA ELIMINACION SOLO CON LAS MODIFICACIONES 
   
	--	DECLARE @aux_aud table (rev BIGINT ,ubiid bigint, revTimeStamp bigint, revtype int , s varchar(500))

		----Trae los ubiid que tienen insersion o algún cambio en los último dias por el momento en la auditoria 
		--INSERT @aux_aud(rev,ubiid, revTimeStamp , revtype  , s)
		--EXEC sp_execute_remote CoreAudReferenceData,N'		
		--											  SELECT CASE WHEN u.rev = MAX(u.rev) OVER (PARTITION BY u.ubiid) THEN u.rev ELSE NULL END AS revx,  
		--													 CASE WHEN u.rev = MAX(u.rev) OVER (PARTITION BY u.ubiid) THEN u.ubiid ELSE NULL END AS ubiidx,
		--													 revTimeStamp, revtype
		--												FROM ubicacion_aud u WITH(NOLOCK)
		--										  INNER JOIN Revision r 
		--												  ON r.revid = u.rev
		--											--   WHERE r.revtimestamp<= DATEDIFF_BIG (ms, ''1969-12-31 19:00:00'', DATEADD(HOUR, 5,GETDATE()-1))
		--											  '

	--	SELECT * FROM #ContactosT_G2E
 --SELECT * FROM sap.Contactos_G2E
	------*****************FIN DE LA INSERSIoN  A TEMPORALES*************************--------------------
		 
/*
	-- REEMPLAZO POR CONTROL EN LA TABLA RelacionesCtrl prar registrar la solicitud actualizada
				INSERT INTO sap.Contactos_G2E 
				      SELECT DISTINCT x.fecing, x.horaing, x.codigosap, x.tipodoc, x.numerodoc, x.nombrecompleto, x.nombrepila, x.TipoContacto,
							 case when x.genero = 'masculino' then 'M'
							      when x.genero = 'femenino' then 'F'
								  else '' end
							 , x.fecnac, c.EstadocivilCCF, x.telefono, x.celular, x.email, x.direcc, x.ciudad, x.dpto,
							 x.autenvio, x.nit, x.tipodocEmpresa, x.CodigoGenesys,
							 x.operacion, x.nrointentos, x.fecproceso, x.horaproceso, x.estadoreg, x.observacion, x.usuario 
                        FROM #ContactosT_G2E X
				   LEFT JOIN sap.MaestraEstadoCivil c ON c.EstadoCivilG= X.estadocivil 
				   LEFT JOIN sap.Contactos_G2E Z
				          ON x.TipoDoc = z.tipodoc  
						 AND x.numerodoc = z.numerodoc
						 AND x.TipoContacto = z.TipoContacto
						 AND x.nit = z.nit
						 AND x.tipodocEmpresa = z.tipodocempresa
						 AND x.fecing = z.fecing
					    WHERE z.numerodoc IS NULL  
*/
				INSERT INTO sap.Contactos_G2E 
				      SELECT DISTINCT x.fecing, x.horaing, x.codigosap, x.tipodoc, x.numerodoc, x.nombrecompleto, x.nombrepila, x.TipoContacto,
							 case when x.genero = 'masculino' then 'M'
							      when x.genero = 'femenino' then 'F'
								  else '' end
							 , x.fecnac, c.EstadocivilCCF, x.telefono, x.celular, x.email, x.direcc, x.ciudad, x.dpto,
							 x.autenvio, x.nit, x.tipodocEmpresa, x.CodigoGenesys,
							 x.operacion, x.nrointentos, x.fecproceso, x.horaproceso, x.estadoreg, x.observacion, x.usuario,GETDATE() -'05:00:00' 
                        FROM #ContactosT_G2E X WITH(NOLOCK)
				   LEFT JOIN sap.MaestraEstadoCivil c WITH(NOLOCK) ON c.EstadoCivilG= X.estadocivil  

		-- Marca los registros como ya integrados (se debe cambiar por el consecutivo)
		INSERT INTO sap.ContactosCtrl
		SELECT solNumeroRadicacion, 1, perNumeroIdentificacion, perTipoIdentificacion
		FROM #INGRESOS solNumeroRadicacion



---------Validacion cauntos registros se envian a integrar
/*

	INSERT INTO #registros 
	SELECT  COUNT(*) FROM  #ContactosT_G2E, #INGRESOS
*/
------------------------------------------------------------

       --SELECT * FROM #ContactosT_G2E

	   DROP TABLE #ContactosT_G2E;
	   DROP TABLE #INGRESOS;

END TRY  

BEGIN CATCH
		SELECT  ERROR_NUMBER() AS ErrorNumber  ,ERROR_SEVERITY() AS ErrorSeverity  
    ,ERROR_STATE() AS ErrorState,ERROR_PROCEDURE() AS ErrorProcedure  
    ,ERROR_LINE() AS ErrorLine,ERROR_MESSAGE() AS ErrorMessage;  
 
END CATCH