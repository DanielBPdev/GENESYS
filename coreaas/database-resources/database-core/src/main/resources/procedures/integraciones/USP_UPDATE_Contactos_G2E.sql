
CREATE OR ALTER PROCEDURE [sap].[USP_UPDATE_Contactos_G2E] AS


/*  Creador  : Olga Vega
    Fecha   : 2021-09-100
    Descripcion: CONSULTA CON O SIN TIPO Y NRO DE IDENTIFICACIOCION Y FECHA LOS DATOS DE LOS CONTACTOS 
					RL (empRepresentanteLegal)
					RLS (empRepresentanteLegalSuplente)
					JGH (rceTipoRolContactoEmpleador)
					EAP (rceTipoRolContactoEmpleador)
					ECU (rceTipoRolContactoEmpleador)
				desde genesys para integracion con el ERP de comfenalco
		 
	Ejecucion :
	                EXEC Sap.USP_UPDATE_Contactos_G2E
*/
 SET NOCOUNT ON ---agregado para evitar que conjuntos de resultados adicionales interfieran con las sentencias SELECT.CONFIGURAR SIN CUENTA EN;
			    
 BEGIN TRY 
   
	              SELECT CASE WHEN solid = MAX(solid) OVER (PARTITION BY em.empid) THEN solId ELSE NULL END AS SolId,  
						  CASE WHEN solid = MAX(solid) OVER (PARTITION BY em.empid) THEN solFechaRadicacion ELSE NULL END AS FechaRadicacion,
						  em.empId AS IdEmpleador,
						  em.empEmpresa,
						  perId AS PeridEmpresa,
						  solTipoTransaccion AS TipoTransaccion,
						  solNumeroRadicacion AS solNumeroRadicacion,
						  perNumeroIdentificacion AS perNumeroIdentificacion,
						  perTipoIdentificacion AS perTipoIdentificacion
					 INTO #MODCONTACTO_RL_RLS
			         FROM Persona  WITH(NOLOCK)
			   INNER JOIN empresa e WITH(NOLOCK)
			           ON perid = emppersona  
			   INNER JOIN empleador em WITH(NOLOCK)
			           ON e.empid = em.empEmpresa
			   INNER JOIN SolicitudNovedadEmpleador WITH(NOLOCK)
			           ON em.empid = sneIdEmpleador
			   INNER JOIN SolicitudNovedad WITH(NOLOCK)
			           ON sneIdSolicitudNovedad = snoId
			   INNER JOIN Solicitud WITH(NOLOCK)
			           ON  snoSolicitudGlobal = solid
			        WHERE solResultadoProceso = 'APROBADA'
					  AND solTipoTransaccion in ('CAMBIOS_REPRESENTANTE_LEGAL_SUPLENTE_O_SOCIOS_WEB' ,
												 'CAMBIOS_REPRESENTANTE_LEGAL_SUPLENTE_O_SOCIOS_PRESENCIAL'
												)
					 AND NOT EXISTS(SELECT solNumeroRadicacion FROM sap.ContactosCtrl ctr WITH (NOLOCK) 
						WHERE Solicitud.solNumeroRadicacion = ctr.solNumeroRadicacion) -- CAMBIO YESIKA BERNAL (NOT IN X NOT EXISTS)

					 AND solUsuarioRadicacion <> 'integracion'-----CAMBIO OLGA VEGA 20220215
					  AND solFechaRadicacion > GETDATE() - '05:00:00' - 1

 --SELECT '#MODCONTACTO_RL_RLS',* from #MODCONTACTO_RL_RLS

					  ----AFILIACIONES SUBSIDIO OFICINA PRINCIPAL 
				  SELECT CASE WHEN solid = MAX(solid) OVER (PARTITION BY em.empid) THEN solId ELSE NULL END AS SolId,  
						  CASE WHEN solid = MAX(solid) OVER (PARTITION BY em.empid) THEN solFechaRadicacion ELSE NULL END AS FechaRadicacion,
						  em.empId AS IdEmpleador,
						  em.empEmpresa,
						  perId AS PeridEmpresa,
						  solTipoTransaccion AS TipoTransaccion,
						  solNumeroRadicacion AS solNumeroRadicacion,
						  perNumeroIdentificacion AS perNumeroIdentificacion,
						  perTipoIdentificacion AS perTipoIdentificacion
					 INTO #MODCONTACTO_A_S_OP
			         FROM Persona WITH(NOLOCK)
			   INNER JOIN empresa e WITH(NOLOCK)
			           ON perid = emppersona  
			   INNER JOIN empleador em WITH(NOLOCK)
			           ON e.empid = em.empEmpresa
			   INNER JOIN SolicitudNovedadEmpleador WITH(NOLOCK)
			           ON em.empid = sneIdEmpleador
			   INNER JOIN SolicitudNovedad WITH(NOLOCK)
			           ON sneIdSolicitudNovedad = snoId
			   INNER JOIN Solicitud WITH(NOLOCK)
			           ON snoSolicitudGlobal = solid
			        WHERE solResultadoProceso = 'APROBADA'
					  AND solTipoTransaccion in ('ACTUALIZACION_DIRECCION_NOTIFICACION_JUDICIAL_WEB',
												 'ACTUALIZACION_DIRECCION_NOTIFICACION_JUDICIAL_PRESENCIAL',
												 'ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_WEB',
												 'ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_PRESENCIAL',
												 'ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_WEB',
												 'ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_PRESENCIAL',
												 'CAMBIO_RESPONSABLE_CONTACTOS_CFF',
												 'CAMBIOS_ROLES_CONTACTO_WEB',
												 'CAMBIOS_ROLES_CONTACTO_PRESENCIAL' 
												 
												)
				   AND NOT EXISTS(SELECT solNumeroRadicacion FROM sap.ContactosCtrl ctr WITH (NOLOCK) 
						WHERE Solicitud.solNumeroRadicacion = ctr.solNumeroRadicacion) -- CAMBIO YESIKA BERNAL (NOT IN X NOT EXISTS)

					  AND solFechaRadicacion > GETDATE() - '05:00:00' - 1
					  AND solUsuarioRadicacion <> 'integracion'-----CAMBIO OLGA VEGA 20220215

 
--------------------------------------------------------*****CON PARAMETROS****-----------------------------------

 	------REPRESENTANTE LEGAL
 
	       SELECT --ROW_NUMBER() OVER(ORDER BY dapid ASC) AS Consecutivo,
				  CONVERT(varchar, SAP.GetLocalDate(), 23) AS fecing, 
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
				  LEFT(ubiDireccionFisica,60) AS direcc,----				  
				  munCodigo AS ciudad,
				  depCodigo AS dpto,
				  CASE WHEN ubiAutorizacionEnvioEmail = 1 THEN 'S' ELSE 'N' END AS autenvio,
				  pe.perNumeroIdentificacion AS nit,
				  LEFT(h2.tipoDocumentoCaja,2) AS tipodocEmpresa,
				  pe.perId AS CodigoGenesys,
				  'M' AS operacion,
				  '' AS nrointentos,
				  '' AS fecproceso,
				  '' AS horaproceso,  
				  'P' AS estadoreg,
				  '' AS observacion,
				  ''  AS 	usuario
			 INTO #Contactos_G2E
		     FROM Persona p WITH(NOLOCK)
	   LEFT JOIN PersonaDetalle pd  WITH(NOLOCK)
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
	           ON m.munDepartamento = d.depid
	   INNER JOIN sap.MaestraIdentificacion h WITH(NOLOCK)
	           ON p.pertipoidentificacion = h.TipoIdGenesys
	   INNER JOIN sap.MaestraIdentificacion h2 WITH(NOLOCK)
	           ON pe.pertipoidentificacion = h2.TipoIdGenesys
	   INNER JOIN #MODCONTACTO_RL_RLS rl WITH(NOLOCK)
	           ON emp.empid  = rl.IdEmpleador

	     GROUP BY h2.codigoERP, h.codigoERP, p.pernumeroidentificacion, p.pertipoidentificacion,p.perId,p.perPrimerNombre,p.perSegundoNombre,
				  p.perPrimerApellido, p.perSegundoApellido, p.perrazonsocial, u.ubiDireccionFisica, m.munNombre,  
				  pedGenero, pedFechaNacimiento,pedEstadoCivil,munCodigo,ubiAutorizacionEnvioEmail,
				  d.depNombre , u.ubiTelefonoFijo, u.ubiTelefonoCelular, u.ubiEmail ,h.TipoDocumentoCaja, depCodigo,
				  pe.perNumeroIdentificacion, pe.perTipoIdentificacion,  pe.perId,h2.TipoDocumentoCaja
		
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
				  'RLS' ,
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
				  pe.perNumeroIdentificacion,
				  LEFT(H2.tipoDocumentoCaja,2) AS tipodocEmpresa,
				  pe.perId,
				  'M' AS operacion,
				  '' AS nrointentos,
				  '' AS fecproceso,
				  '' AS horaproceso, 
				  'P' AS estadoreg,
				  'ACTUALIZACIONES' AS observacion,
				  ''  AS usuario
	         FROM Persona p  WITH(NOLOCK)
	    LEFT JOIN PersonaDetalle pd WITH(NOLOCK)
	           ON p.perid = pd.pedPersona 
---====informacion empresa
	   INNER JOIN Empresa e WITH(NOLOCK)
	           ON p.perId =  e.empRepresentanteLegalSuplente
	   INNER JOIN Empleador emp WITH(NOLOCK)
	           ON e.empid = emp.empEmpresa
       INNER JOIN Persona pe WITH(NOLOCK)
	           ON e.emppersona = pe.perId
---====informacion ubicacion contacto
 	   INNER JOIN Ubicacion u WITH(NOLOCK)
	           ON e.empUbicacionRepresentanteLegalSuplente= u.ubiid 
	   LEFT JOIN Municipio m WITH(NOLOCK)
	           ON u.ubiMunicipio= m.munId
	   LEFT JOIN Departamento d WITH(NOLOCK)
	           ON m.munDepartamento = d.depid
	   INNER JOIN sap.MaestraIdentificacion h WITH(NOLOCK)
	           ON p.pertipoidentificacion = h.TipoIdGenesys
	   INNER JOIN sap.MaestraIdentificacion h2 WITH(NOLOCK)
	           ON pe.pertipoidentificacion = h2.TipoIdGenesys
	   INNER JOIN #MODCONTACTO_RL_RLS rl WITH(NOLOCK)
	           ON emp.empid = rl.IdEmpleador 
	  GROUP BY    h.codigoERP,h2.codigoERP, p.pernumeroidentificacion, p.pertipoidentificacion,p.perId,p.perPrimerNombre,p.perSegundoNombre,
				  p.perPrimerApellido, p.perSegundoApellido, p.perrazonsocial, u.ubiDireccionFisica, m.munNombre,  
				  pedGenero, pedFechaNacimiento,pedEstadoCivil,munCodigo,ubiAutorizacionEnvioEmail,
				  d.depNombre , u.ubiTelefonoFijo, u.ubiTelefonoCelular, u.ubiEmail ,h.TipoDocumentoCaja, depCodigo,
				  pe.perNumeroIdentificacion, pe.perTipoIdentificacion,  pe.perId,h2.TipoDocumentoCaja
 
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
				  ELSE '' END,
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
				  pe.perNumeroIdentificacion,
				  LEFT(H2.tipoDocumentoCaja,2),
				  pe.perID,
				  'M' AS operacion,
				  '' AS nrointentos,
				  '' AS fecproceso,
				  '' AS horaproceso,
				  'P'AS estadoreg,
				  'ACTUALIZACIONES' AS observacion,
				  '' AS usuario
	         FROM Persona p WITH(NOLOCK)
	   INNER JOIN RolContactoEmpleador WITH(NOLOCK)
	           ON perId = rcePersona
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
	   LEFT JOIN Municipio m  WITH(NOLOCK)
	           ON u.ubiMunicipio= m.munId
	   LEFT JOIN Departamento d WITH(NOLOCK)
	           ON m.munDepartamento = d.depid
	   INNER JOIN sap.MaestraIdentificacion h WITH(NOLOCK)
	           ON p.pertipoidentificacion = h.TipoIdGenesys
	   INNER JOIN sap.MaestraIdentificacion h2 WITH(NOLOCK)
	           ON pe.pertipoidentificacion = h2.TipoIdGenesys
------==========
	   INNER JOIN #MODCONTACTO_A_S_OP aso WITH(NOLOCK)
	           ON emp.empid = aso.IdEmpleador
	 GROUP BY h2.CodigoERP, h.CodigoERP, p.pernumeroidentificacion, p.pertipoidentificacion,p.perId,p.perPrimerNombre,p.perSegundoNombre,
				  p.perPrimerApellido, p.perSegundoApellido, p.perrazonsocial, u.ubiDireccionFisica, m.munNombre,  
				  pedGenero, pedFechaNacimiento,pedEstadoCivil,munCodigo,ubiAutorizacionEnvioEmail,
				  d.depNombre , u.ubiTelefonoFijo, u.ubiTelefonoCelular, u.ubiEmail ,h.TipoDocumentoCaja, depCodigo	,
				  pe.perNumeroIdentificacion, pe.perTipoIdentificacion,  pe.perId, rceTipoRolContactoEmpleador,h2.TipoDocumentoCaja

/*
 		INSERT INTO sap.Contactos_G2E 
				      SELECT DISTINCT x.fecing,x.horaing,x.codigosap,x.tipodoc,x.numerodoc,x.nombrecompleto,x.nombrepila,x.TipoContacto,
							 case when x.genero = 'masculino' then 'M'
							      when x.genero = 'femenino' then 'F'
								  else '' end
							      ,x.fecnac,c.EstadocivilCCF,x.telefono,x.celular,x.email,x.direcc,x.ciudad,x.dpto,
							 x.autenvio,x.nit,x.tipodocEmpresa,x.CodigoGenesys,
							 x.operacion,x.nrointentos,x.fecproceso,x.horaproceso,x.estadoreg,x.observacion,x.usuario 
                        FROM #Contactos_G2E	X
				   LEFT JOIN sap.MaestraEstadoCivil c ON c.EstadoCivilG= X.estadocivil 
				   LEFT JOIN sap.Contactos_G2E Z
				          ON x.TipoDoc =  z.tipodoc  
						 AND x.numerodoc =  z.numerodoc
						 AND x.TipoContacto =  z.TipoContacto
						 AND x.fecing = z.fecing
						 --AND DATEPART(Hour, x.horaing) = DATEPART(Hour, z.horaing)
					    WHERE z.numerodoc IS NULL
*/


			 
---------Validacion cauntos registros se envian a integrar
/*
			INSERT INTO #registros 
			SELECT COUNT(*) FROM #MODCONTACTO_RL_RLS
*/
------------------------------------------------------------



 		INSERT INTO sap.Contactos_G2E 
				      SELECT DISTINCT x.fecing,x.horaing,x.codigosap,x.tipodoc,x.numerodoc,x.nombrecompleto,x.nombrepila,x.TipoContacto,
							 case when x.genero = 'masculino' then 'M'
							      when x.genero = 'femenino' then 'F'
								  else '' end
							      ,x.fecnac,c.EstadocivilCCF,x.telefono,x.celular,x.email,x.direcc,x.ciudad,x.dpto,
							 x.autenvio,x.nit,x.tipodocEmpresa,x.CodigoGenesys,
							 x.operacion,x.nrointentos,x.fecproceso,x.horaproceso,x.estadoreg,x.observacion,x.usuario,GETDATE() -'05:00:00' 
                        FROM #Contactos_G2E	X WITH(NOLOCK)
				   LEFT JOIN sap.MaestraEstadoCivil c WITH(NOLOCK) ON  X.estadocivil = c.EstadoCivilG

		-- Marca los registros como ya integrados (se debe cambiar por el consecutivo)
		INSERT INTO sap.ContactosCtrl
		SELECT solNumeroRadicacion, 1, perNumeroIdentificacion, perTipoIdentificacion
		FROM #MODCONTACTO_A_S_OP WITH(NOLOCK)
	
		 INSERT INTO sap.ContactosCtrl
		SELECT solNumeroRadicacion, 1, perNumeroIdentificacion, perTipoIdentificacion
		FROM #MODCONTACTO_RL_RLS WITH(NOLOCK)

          --SELECT * FROM #Contactos_G2E
END TRY  

BEGIN CATCH
		SELECT  ERROR_NUMBER() AS ErrorNumber  ,ERROR_SEVERITY() AS ErrorSeverity  
    ,ERROR_STATE() AS ErrorState,ERROR_PROCEDURE() AS ErrorProcedure  
    ,ERROR_LINE() AS ErrorLine,ERROR_MESSAGE() AS ErrorMessage;  
 
END CATCH