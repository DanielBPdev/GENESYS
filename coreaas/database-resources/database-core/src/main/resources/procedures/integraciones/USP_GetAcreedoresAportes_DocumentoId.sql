CREATE OR ALTER PROCEDURE [sap].[USP_GetAcreedoresAportes_DocumentoId] 
	 @documentoId nvarchar(20), @tipoDocumento nvarchar(20), @usuario varchar(50), @planilla varchar(12)
AS			
	
-- Variables para los datos de contacto:
DECLARE @regCodDepartamento VARCHAR(2)

-- Se crea tabla temporal para completar datos de contacto del acreedor primero desde Genesys y al no encontrar con la información los busca en PILA
DROP TABLE IF EXISTS  #Acreedores
CREATE TABLE #Acreedores(
	[consecutivo] [bigint] NOT NULL,
	[fecIng] [date] NOT NULL,
	[horaIng] [time](7) NOT NULL,
	[operacion] [varchar](1) NOT NULL,
	[codigoSap] [varchar](10) NULL,
	[codigoGenesys] [bigint] NOT NULL,
	[tipo] [varchar](1) NOT NULL,
	[procesoOrigen] [varchar](10) NOT NULL,
	[sociedad] [varchar](4) NOT NULL,
	[grupoCuenta] [varchar](4) NOT NULL,
	[tratamiento] [varchar](30) NOT NULL,
	[nroDocumento] [varchar](16) NOT NULL,
	[tipoDocumento] [varchar](3) NOT NULL,
	[nombre1] [varchar](35) NOT NULL,
	[nombre2] [varchar](35) NULL,
	[nombre3] [varchar](35) NULL,
	[nombre4] [varchar](35) NULL,
	[direccion] [varchar](60) NULL,
	[municipio] [varchar](12) NULL,
	[pais] [varchar](3) NULL,
	[departamento] [varchar](3) NULL,
	[telefono] [varchar](30) NULL,
	[celular] [varchar](30) NULL,
	[fax] [varchar](30) NULL,
	[email] [varchar](241) NULL,
	[email2] [varchar](241) NULL,
	[claseImpuesto] [varchar](2) NOT NULL,
	[personaFisica] [bit] NOT NULL,
	[clavePaisBanco] [varchar](3) NULL,
	[claveBanco] [varchar](15) NULL,
	[nroCuentaBancaria] [varchar](18) NULL,
	[titularCuenta] [varchar](60) NULL,
	[claveControlBanco] [varchar](2) NULL,
	[tipoBancoInter] [varchar](4) NULL,
	[referenciaBancoCuenta] [varchar](20) NULL,
	[cuentaAsociada] [varchar](10) NOT NULL,
	[claveClasificacion] [varchar](3) NOT NULL,
	[grupoTesoreria] [varchar](10) NOT NULL,
	[nroPersonal] [varchar](8) NULL,
	[condicionPago] [varchar](4) NOT NULL,
	[grupoTolerancia] [varchar](4) NOT NULL,
	[verificacionFacturaDoble] [bit] NOT NULL,
	[viasPago] [varchar](10) NOT NULL,
	[extractoCuenta] [varchar](1) NOT NULL,
	[actividadEconomica] [varchar](4) NULL,
	[paisRetencion] [varchar](3) NOT NULL,
	[nroIntentos] [smallint] NOT NULL,
	[fecProceso] [date] NULL,
	[horaProceso] [time](7) NULL,
	[estadoReg] [varchar](1) NOT NULL,
	[observacion] [varchar](2000) NULL,
	[usuario] [varchar](50) NOT NULL,
)

--set @documentoId ='1000396807'
--set @tipoDocumento = 'CC'
--set @planilla = '67839814'

BEGIN TRY
	BEGIN TRANSACTION

		-- Acreedores de Aportes
	INSERT INTO #Acreedores	  
		SELECT DISTINCT TOP 1
			ROW_NUMBER() OVER(ORDER BY da.dapid ASC) AS consecutivo,
			CONVERT(varchar, SAP.GetLocalDate(), 111) AS fecing, 
			CONVERT(varchar, SAP.GetLocalDate(), 108) AS horaing,
			'I' AS Operacion,
			'' AS codigosap,
			p.perId AS codigoGenesys,
			CASE WHEN p.perTipoIdentificacion = 'NIT' THEN 'E'
				ELSE 'P' END AS tipo,
			'APORTES' AS procesoOrigen,
			'COMF' AS sociedad,
			mi.grupoCuenta AS grupoCuenta,
			CASE WHEN mi.tipoIdGenesys = 'NIT' THEN 'Empresa' 
				WHEN ped.pedGenero = 'MASCULINO' THEN 'Señor'
				WHEN ped.pedGenero = 'FEMENINO' THEN 'Señora'
			ELSE 'Señor' END AS tratamiento,
			p.pernumeroidentificacion AS nroDocumento,
			mi.TipoDocumentoCaja AS tipoDocumento,
							CASE WHEN perTipoIdentificacion = 'NIT' THEN SUBSTRING (perrazonsocial,1,35) 
					ELSE LEFT(perPrimerNombre, 35) END nombre1,
				CASE WHEN perTipoIdentificacion = 'NIT' THEN SUBSTRING (perrazonsocial,36,35)
					ELSE LEFT(perSegundoNombre, 35) END nombre2,
				CASE WHEN perTipoIdentificacion = 'NIT' THEN ''
					ELSE LEFT(perPrimerApellido, 35) END nombre3,
				CASE WHEN perTipoIdentificacion = 'NIT' THEN ''
					ELSE LEFT(perSegundoApellido, 35) END nombre4,
				/*
				-- Como estaba antes de correo de Luz María: Marzo 03 de 2022 "Modificación integración de acreedores"
				CASE WHEN perTipoIdentificacion = 'NIT' THEN SUBSTRING (perrazonsocial,1,35) 
					ELSE LEFT(CONCAT_WS(' ', perPrimerNombre, perSegundoNombre), 35) END nombre1,
				CASE WHEN perTipoIdentificacion = 'NIT' THEN SUBSTRING (perrazonsocial,36,70)
					ELSE LEFT(CONCAT_WS(' ', perPrimerApellido, perSegundoApellido), 35) END nombre2,
				CASE WHEN perTipoIdentificacion = 'NIT' THEN ''
					ELSE CASE WHEN CHARINDEX(',',RTRIM(LEFT(CONCAT_WS(', ', perPrimerNombre, perSegundoNombre), 35))) = LEN(RTRIM(LEFT(CONCAT_WS(', ', perPrimerNombre, perSegundoNombre), 35))) THEN REPLACE(RTRIM(LEFT(CONCAT_WS(', ', perPrimerNombre, perSegundoNombre), 35)),',','') ELSE RTRIM(LEFT(CONCAT_WS(', ', perPrimerNombre, perSegundoNombre), 35))END END AS nombre3,
				CASE WHEN perTipoIdentificacion = 'NIT' THEN ''
					ELSE CASE WHEN CHARINDEX(',',RTRIM(LEFT(CONCAT_WS(', ', perPrimerApellido, perSegundoApellido), 35))) = LEN(RTRIM(LEFT(CONCAT_WS(', ', perPrimerApellido, perSegundoApellido), 35))) THEN REPLACE(RTRIM(LEFT(CONCAT_WS(', ', perPrimerApellido, perSegundoApellido), 35)),',','') ELSE RTRIM(LEFT(CONCAT_WS(', ', perPrimerApellido, perSegundoApellido), 35))END END AS nombre4,
				*/
			REPLACE(REPLACE(UPPER(REPLACE(UPPER(ISNULL(u.ubiDireccionFisica, '')),'#', '')),'   ', ' '),'  ',' ') AS direccion,
			ISNULL(M.muncodigo, '') AS municipio, 
			'CO' AS pais, 
			ISNULL(D.depCodigo, '') AS departamento, 
			CASE  WHEN LEN(ISNULL(U.ubiTelefonoFijo, ''))>7 THEN U.ubiTelefonoFijo ELSE '' END AS telefono, 
			ISNULL(U.ubiTelefonoCelular,'') AS celular, 
			'' AS fax, -- No se registra fax en Genesys
			LOWER(U.ubiEmail) AS email, 
			LOWER(U.ubiEmail) AS email2,
			CASE WHEN perTipoIdentificacion = 'NIT' THEN ''
				ELSE 'NR' END AS claseImpuesto,
			CASE WHEN perTipoIdentificacion = 'NIT' THEN ''
				ELSE '1' END AS PersonaFisica,
			'CO' AS clavePaisBanco,
			CASE WHEN LEN(ban.banCodigoPILA - 1000) = 1 THEN CONCAT('0', CAST(ban.banCodigoPILA - 1000 AS VARCHAR(4))) 
				WHEN LEN(ban.banCodigoPILA - 1000) = 2 THEN CAST(ban.banCodigoPILA - 1000 AS VARCHAR(4))
				WHEN LEN(ban.banCodigoPILA - 1000) = 3 THEN CAST(ban.banCodigoPILA AS VARCHAR(4))
				ELSE CAST(ban.banCodigoPILA AS VARCHAR(4)) END AS claveBanco,
			REPLACE(met.metNumeroCuenta, '-', '') AS nroCuentaBancaria, 
			met.metNombreTitularCuenta AS titularCuenta, 
			CASE WHEN met.metTipoCuenta = 'AHORROS' THEN '02' 
				WHEN met.metTipoCuenta = 'CORRIENTE'  THEN '01' 
				ELSE '03' END AS claveControlBanco,
			'A1' AS tipoBancoInter, 
			'' AS referenciaBancoCuenta,	-- FALTA
			mi.grupoCuenta AS cuentaAsociada,
			'009' AS claveClasificacion,
			'AG' AS grupoTesoreria,
			'' AS nroPersonal,
			'K001' AS condicionPago,
			'KRED' AS grupoTolerancia,
			1 AS VerificacionFacturaDoble,
			'TE' AS viasPago,
			'2' AS ExtractoCuenta,
			'' AS actividadEconomica,		--GAP	
			'CO' AS paisRetencion,
			0 AS nroreintentos,	
			'' AS fecproceso, 
			'' AS horaproceso,
			'P' AS estadoreg,
			'' AS observacion,
			'' AS usuario		--ES LLAMADO DESDE EL MOVIMIENTO CONTABLE F06, HEREDA DEL USUARIO QUE REALIZA LA TRANSACCION
	         FROM devolucionaporte da
	   INNER JOIN SolicitudDevolucionAporte sd 
	           ON da.dapId = sd.sdaDevolucionAporte
	   INNER JOIN solicitud s 
	           ON sd.sdaSolicitudGlobal = s.solid 
	   INNER JOIN Persona p 
	           ON sd.sdapersona = p.perid
	   LEFT JOIN Ubicacion u
	           ON p.perUbicacionprincipal = u.ubiid 
	   LEFT JOIN Municipio m 
	           ON u.ubiMunicipio = m.munId
	   LEFT JOIN departamento d 
	           ON d.depId = m.munDepartamento
	   INNER JOIN sap.MaestraIdentificacion mi
	           ON p.pertipoidentificacion = mi.TipoIdGenesys
	    LEFT JOIN PersonaDetalle ped
	           ON ped.pedPersona = p.perid 
	    LEFT JOIN MedioDePago mdp 
			   ON da.dapmediopago = mdp.mdpId
	    LEFT JOIN MedioTransferencia met
			   ON met.mdpId = mdp.mdpId
	    LEFT JOIN Banco AS ban 
			   ON met.metBanco = ban.banId
	        WHERE P.perNumeroIdentificacion = @documentoId
			AND mi.tipoDocumentoCaja = @tipoDocumento
			  --AND dapMotivoPeticion = 'APORTANTE_NO_AFILIADO_CAJA'
			  --AND dapDestinatarioDevolucion = 'EMPLEADOR'
 	   GROUP BY dapid, solFechaRadicacion, solResultadoProceso, solObservacion, solUsuarioRadicacion, dapFechaRecepcion,
	             CodigoERP, 
				 mi.grupoCuenta,
				 mi.tipoIdGenesys,
				 ped.pedGenero,
				 p.pernumeroidentificacion, 
				 p.pertipoidentificacion,
				 ban.banCodigoPILA,
				 met.metNumeroCuenta,
				 met.metNombreTitularCuenta,
				 met.metTipoCuenta,
				 p.perId,
				 p.perrazonsocial,
				 p.perPrimerNombre,
				 p.perSegundoNombre,
				 p.perPrimerApellido,
				 p.perSegundoApellido,
				 u.ubiDireccionFisica,
			     m.munCodigo,
				 d.depCodigo,
				 u.ubiTelefonoFijo,
				 u.ubiTelefonoCelular,
				 u.ubiEmail,
				 mi.TipoDocumentoCaja

			
		select direccion, municipio, departamento,telefono,fax,email,email2, * from #Acreedores
		 


		-- Cuando los datos de contacto del Acreedor son insuficientes desde la fuente de Core, se traen de la información de la planilla PILA
			SELECT DISTINCT @regCodDepartamento = CASE WHEN LEN(reg.regCodDepartamento) = 1 THEN CONCAT('0',reg.regCodDepartamento) ELSE reg.regCodDepartamento END 
			FROM pila.RegistroGeneral reg INNER JOIN sap.MaestraIdentificacion mi ON reg.regTipoIdentificacionAportante = mi.TipoIdGenesys
			WHERE reg.regNumeroIdentificacionAportante = @documentoId
			AND mi.tipoDocumentoCaja = @tipoDocumento
			AND reg.regnumplanilla = @planilla

		-- Actualiza los campos de contacto.
		UPDATE #Acreedores SET [direccion] = CASE	WHEN [direccion] IS NULL OR [direccion] = '' THEN reg.regDireccion
													WHEN [direccion] = 'SIN NOMENCLATURA' AND reg.regDireccion <> '' THEN reg.regDireccion
													ELSE [direccion]  END,
								[municipio] = CASE WHEN [municipio] IS NULL OR [municipio] = '' THEN CONCAT(@regCodDepartamento, regCodCiudad) ELSE [municipio] END,
								[departamento] = CASE WHEN [departamento] IS NULL OR [departamento] = '' THEN @regCodDepartamento ELSE [departamento] END,
								[telefono] = CASE WHEN [telefono] IS NULL OR [telefono] = '' THEN reg.regTelefono ELSE [telefono] END,
								[fax] = '',
								[email] = CASE WHEN [email] IS NULL OR [email] = '' THEN reg.regEmail ELSE [email] END,
								[email2] = CASE WHEN [email2] IS NULL OR [email2] = '' THEN reg.regEmail ELSE [email2] END
		FROM  #Acreedores acr
		INNER JOIN pila.RegistroGeneral reg  ON acr.nroDocumento = reg.regNumeroIdentificacionAportante
		INNER JOIN sap.MaestraIdentificacion mi ON reg.regTipoIdentificacionAportante = mi.TipoIdGenesys
		WHERE reg.regNumeroIdentificacionAportante = @documentoId
			AND mi.tipoDocumentoCaja = @tipoDocumento
			AND reg.regnumplanilla = @planilla
	

		--SELECT * FROM #Acreedores
		-- INICIO: Control de envío de registro completo para el acreedor, debe tener los campos completos, en caso negativo no se integra.
		IF EXISTS (SELECT fecIng FROM #Acreedores 
					WHERE ([nroDocumento] IS NOT NULL OR [nroDocumento] <> '')
					AND ([tipoDocumento] IS NOT NULL OR [tipoDocumento] <> '')
					AND ([nombre1] IS NOT NULL OR [nombre1] <> '')
					AND ([direccion] IS NOT NULL OR [direccion] <> '')
					AND ([municipio] IS NOT NULL OR [municipio] <> '' OR ISNUMERIC( [municipio]) = 1)
					AND ([pais] IS NOT NULL OR [pais] <> '')
					AND ([departamento] IS NOT NULL OR [departamento] <> '')
					--AND ([email] IS NOT NULL OR [email] <> '')  ---- se quita el 13/10/2022 en sesion con caja debido a que este campo no debe frenar integracion --- Camilo Gomez 
					) 
			BEGIN
			Print 'Ingresa insercion Normal'
					

			INSERT INTO SAP.Acreedores
			SELECT [fecIng],[horaIng],[operacion],[codigoSap],[codigoGenesys],[tipo],[procesoOrigen],[sociedad],[grupoCuenta],[tratamiento],[nroDocumento],
					[tipoDocumento],[nombre1],[nombre2],[nombre3],[nombre4],[direccion],[municipio],[pais],[departamento],[telefono],[celular],[fax],[email],[email2],
					[claseImpuesto],[personaFisica],[clavePaisBanco],[claveBanco],[nroCuentaBancaria],[titularCuenta],[claveControlBanco],[tipoBancoInter],
					[referenciaBancoCuenta],[cuentaAsociada],[claveClasificacion],[grupoTesoreria],[nroPersonal],[condicionPago],[grupoTolerancia],[verificacionFacturaDoble],
					[viasPago],[extractoCuenta],[actividadEconomica],[paisRetencion],[nroIntentos],[fecProceso],[horaProceso],[estadoReg],[observacion],[usuario]
			FROM #Acreedores
				
		
			END
		------- Colocar datos y en observacion se deja "ERROR DE DATOS FALTANTES", dejar estadoreg en I   
			ELSE
				BEGIN
				
				Print 'Ingresa insercion tipo I-Datos Faltantes'
				INSERT INTO SAP.Acreedores
				SELECT isnull([fecIng],''),isnull([horaIng],''),isnull([operacion],''),isnull([codigoSap],''),isnull([codigoGenesys],''),isnull([tipo],''),isnull([procesoOrigen],''),isnull([sociedad],''),isnull([grupoCuenta],''),isnull([tratamiento],''),isnull([nroDocumento],''),
					isnull([tipoDocumento],''),isnull([nombre1],''),isnull([nombre2],''),isnull([nombre3],''),isnull([nombre4],''),isnull([direccion],''),isnull([municipio],''),isnull([pais],''),isnull([departamento],''),isnull([telefono],''),isnull([celular],''),isnull([fax],''),isnull([email],''),isnull([email2],''),
					isnull([claseImpuesto],''),isnull([personaFisica],''),isnull([clavePaisBanco],''),isnull([claveBanco],''),isnull([nroCuentaBancaria],''),isnull([titularCuenta],''),isnull([claveControlBanco],''),isnull([tipoBancoInter],''),
					isnull([referenciaBancoCuenta],''),isnull([cuentaAsociada],''),isnull([claveClasificacion],''),isnull([grupoTesoreria],''),isnull([nroPersonal],''),isnull([condicionPago],''),isnull([grupoTolerancia],''),isnull([verificacionFacturaDoble],''),
					isnull([viasPago],''),isnull([extractoCuenta],''),isnull([actividadEconomica],''),isnull([paisRetencion],''),isnull([nroIntentos],''),isnull([fecProceso],''),isnull([horaProceso],''),'I' as [estadoReg] ,'DATOS FALTANTES'as[observacion],isnull([usuario],'')
				FROM #Acreedores
				
					
				END
	
		select estadoReg, direccion, municipio, departamento,telefono,fax,email,email2, * from #Acreedores


		
		-- FIN: Control de envío de registro completo para el acreedor, debe tener los campos completos, en caso negativo no se integra.

	COMMIT TRANSACTION
END TRY  

BEGIN CATCH

	ROLLBACK TRANSACTION

	SELECT  ERROR_NUMBER() AS ErrorNumber  ,ERROR_SEVERITY() AS ErrorSeverity  
	,ERROR_STATE() AS ErrorState,ERROR_PROCEDURE() AS ErrorProcedure  
    	,ERROR_LINE() AS ErrorLine,ERROR_MESSAGE() AS ErrorMessage;  
 
END CATCH