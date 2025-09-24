
-- ====================================================================================================
-- Author: VILLAMARIN JULIAN
-- Create date: MAYO 10 DE 2021
-- Description: Extrae la informacion de los movimientos contables para la integracion de FOVIS 
-- =====================================================================================================
CREATE OR ALTER PROCEDURE [sap].[USP_GetAcreedoresFOVIS_DocumentoId] 	@documentoId nvarchar(20), @tipoDocumento nvarchar(20), @usuario varchar(50)
AS			
			
 BEGIN TRY 
	BEGIN TRANSACTION

	-- Acreedores de FOVIS por transferencia
  		INSERT INTO  sap.Acreedores
				SELECT DISTINCT TOP 1 
				CONVERT(varchar, SAP.GetLocalDate(), 111) AS fecing, -- TOP 1: para que tome aleatoria en una cuenta bancaria, asi no la tenga como principal
				CONVERT(varchar, SAP.GetLocalDate(), 108) AS horaing,
				'I' AS operacion, 
				'' AS codigosap,
				p.perid AS codigoGenesys,
				CASE WHEN perTipoIdentificacion = 'NIT' THEN 'E'
				ELSE 'P' END AS tipo,
				'FOVIS' AS procesoOrigen, 
				'COMF' AS sociedad, 
				mi.grupoCuenta AS grupoCuenta, 
				CASE  WHEN perDigitoVerificacion IS NOT NULL THEN 'Empresa'
				WHEN ped.pedGenero = 'MASCULINO' THEN 'Señor'
				WHEN ped.pedGenero = 'FEMENINO' THEN 'Señora'
				ELSE 'Señor(a)' END tratamiento,
				perNumeroIdentificacion AS nroDocumento, 
				mi.TipoDocumentoCaja AS tipoDocumento,
				CASE WHEN perTipoIdentificacion = 'NIT' THEN SUBSTRING (perrazonsocial,1,35) 
					ELSE LEFT(perPrimerNombre, 35) END nombre1,
				CASE WHEN perTipoIdentificacion = 'NIT' THEN SUBSTRING (perrazonsocial,36,70)
					ELSE LEFT(perSegundoNombre, 35) END nombre2,
				CASE WHEN perTipoIdentificacion = 'NIT' THEN ''
					ELSE LEFT(perPrimerApellido, 35) END nombre3,
				CASE WHEN perTipoIdentificacion = 'NIT' THEN ''
					ELSE LEFT(perSegundoApellido, 35) END nombre4,
				REPLACE(REPLACE(UPPER(REPLACE(UPPER(ISNULL(u.ubiDireccionFisica, '')),'#', '')),'   ', ' '),'  ',' ') AS direccion, 
				ISNULL(M.muncodigo, '') AS municipio, 
				'CO' AS pais, 
				ISNULL(D.depCodigo, '') AS departamento, 
				ISNULL(U.ubiTelefonoFijo, '') AS telefono, 
				ISNULL(U.ubiTelefonoCelular,'') AS celular, 
				'' AS fax, -- No se registra fax en Genesys
				LOWER(U.ubiEmail) AS email, 
				LOWER(U.ubiEmail) AS email2,
				CASE WHEN perTipoIdentificacion = 'NIT' THEN ''
				ELSE 'NR' END AS claseImpuesto,
				CASE WHEN perTipoIdentificacion = 'NIT' THEN ''
				ELSE '1' END AS PersonaFisica,
				'CO' AS clavePaisBanco, 
				CASE WHEN LEN(b.banCodigoPILA - 1000) = 1 THEN CONCAT('0', CAST(b.banCodigoPILA - 1000 AS VARCHAR(4))) 
					WHEN LEN(b.banCodigoPILA - 1000) = 2 THEN CAST(b.banCodigoPILA - 1000 AS VARCHAR(4))
					WHEN LEN(b.banCodigoPILA - 1000) = 3 THEN CAST(b.banCodigoPILA AS VARCHAR(4))
					ELSE CAST(b.banCodigoPILA AS VARCHAR(4)) END AS claveBanco,
				CASE WHEN REPLACE(met.metNumeroCuenta, '-', '')  IS NULL THEN REPLACE(prov.provNumeroCuenta, '-', '') else met.metNumeroCuenta  END AS nroCuentaBancaria,
				CASE WHEN met.metNombreTitularCuenta  IS NULL  THEN  prov.provNombreTitularCuenta else met.metNombreTitularCuenta END  AS titularCuenta,
     			CASE WHEN met.metTipoCuenta  = 'AHORROS' OR prov.provTipoCuenta  = 'AHORROS' THEN '02' 
				WHEN met.metTipoCuenta = 'CORRIENTE' OR prov.provTipoCuenta = 'CORRIENTE' THEN '01'
				WHEN met.metTipoCuenta = 'DAVIPLATA' OR prov.provTipoCuenta = 'DAVIPLATA' THEN '03'
				ELSE '00'  END AS claveControlBanco,
				'F1' AS tipoBancoInter, 
				perNumeroIdentificacion AS referenciaBancoCuenta, -- VALIDAR SI ES EL DOCUMENTO DE IDENTIFICACIoN
				mi.cuentaAsociada AS cuentaAsociada,
				'009' AS claveClasificacion, 
				'AG' AS grupoTesoreria, 
				'' AS nroPersonal, 
				'K001' AS condicionPago, 
				'KRED' AS grupoTolerancia, 
				1 AS VerificacionFacturaDoble, 
				'TE' AS viasPago, 
				'2' AS ExtractoCuenta, 
				'' AS actividadEconomica, 
				'CO' AS paisRetencion, 
				0 AS nroreintentos, 
				'' AS fecproceso, 
				'' AS horaproceso, 
				'P' AS estadoreg, 
				'' AS observacion,
				@usuario AS usuario	
			FROM Persona P 
			LEFT JOIN MedioTransferencia met WITH (NOLOCK) ON met.metTipoIdentificacionTitular = P.perTipoIdentificacion AND met.metNumeroIdentificacionTitular = P.perNumeroIdentificacion
			LEFT JOIN proveedor pro WITH (NOLOCK) ON pro.provTipoIdentificacionTitular = P.perTipoIdentificacion AND  pro.provNumeroIdentificacionTitular = P.perNumeroIdentificacion
			LEFT JOIN proveedor prov WITH (NOLOCK) ON prov.provPersona = P.perid
			LEFT JOIN MedioDePago mdp WITH (NOLOCK) ON met.mdpId = mdp.mdpId 
			LEFT JOIN PersonaDetalle ped WITH (NOLOCK) ON p.perId = ped.pedPersona  
			LEFT JOIN Ubicacion U WITH (NOLOCK) ON p.perUbicacionPrincipal = u.ubiId 
			LEFT JOIN Municipio M WITH (NOLOCK) ON U.ubiMunicipio = M.munId
			LEFT JOIN Departamento D WITH (NOLOCK) ON D.depId = M.munDepartamento
			LEFT JOIN Banco AS B WITH (NOLOCK) ON  met.metBanco = B.banId OR prov.provBanco = B.banId
			LEFT JOIN sap.MaestraIdentificacion mi WITH (NOLOCK) ON p.pertipoidentificacion = mi.TipoIdGenesys
			WHERE P.perNumeroIdentificacion = @documentoId
			AND P.perTipoIdentificacion = @tipoDocumento;



				
		---Actualizacion de campos si no tiene cuenta bancaria-----------------------------------------------------

		UPDATE sap.Acreedores SET clavePaisBanco = '' , claveControlBanco = '', tipoBancoInter = '', referenciaBancoCuenta = ''
		WHERE nroCuentaBancaria IS NULL AND nroDocumento = @documentoId 

       -------------------------------------------------------------------------------------------------------------


		-- Acreedores de FOVIS por cheque
  		INSERT INTO  sap.Acreedores			
			SELECT DISTINCT TOP 1 
				CONVERT(varchar, SAP.GetLocalDate(), 111) AS fecing, -- TOP 1: para que tome aleatoria en una cuenta bancaria, asi no la tenga como principal
				CONVERT(varchar, SAP.GetLocalDate(), 108) AS horaing,
				'I' AS operacion, 
				'' AS codigosap,
				p.perid AS codigoGenesys,
				CASE WHEN perTipoIdentificacion = 'NIT' THEN 'E'
				ELSE 'P' END AS tipo,
				'FOVIS' AS procesoOrigen, 
				'COMF' AS sociedad, 
				mi.grupoCuenta AS grupoCuenta, 
				CASE  WHEN perDigitoVerificacion IS NOT NULL THEN 'Empresa'
				WHEN ped.pedGenero = 'MASCULINO' THEN 'Señor'
				WHEN ped.pedGenero = 'FEMENINO' THEN 'Señora'
				ELSE 'Señor(a)' END tratamiento,
				perNumeroIdentificacion AS nroDocumento, 
				mi.TipoDocumentoCaja AS tipoDocumento,
				CASE WHEN perTipoIdentificacion = 'NIT' THEN SUBSTRING (perrazonsocial,1,40) 
					ELSE LEFT(CONCAT_WS(' ', perPrimerNombre, perSegundoNombre), 40) END nombre1,
				CASE WHEN perTipoIdentificacion = 'NIT' THEN SUBSTRING (perrazonsocial,41,80)
					ELSE LEFT(CONCAT_WS(' ', perPrimerApellido, perSegundoApellido), 40) END nombre2,
				CASE WHEN perTipoIdentificacion = 'NIT' THEN ''
					ELSE CASE WHEN CHARINDEX(',',RTRIM(LEFT(CONCAT_WS(', ', perPrimerNombre, perSegundoNombre), 40))) = LEN(RTRIM(LEFT(CONCAT_WS(', ', perPrimerNombre, perSegundoNombre), 40))) THEN REPLACE(RTRIM(LEFT(CONCAT_WS(', ', perPrimerNombre, perSegundoNombre), 40)),',','') ELSE RTRIM(LEFT(CONCAT_WS(', ', perPrimerNombre, perSegundoNombre), 40))END END AS nombre3,
				CASE WHEN perTipoIdentificacion = 'NIT' THEN ''
					ELSE CASE WHEN CHARINDEX(',',RTRIM(LEFT(CONCAT_WS(', ', perPrimerApellido, perSegundoApellido), 40))) = LEN(RTRIM(LEFT(CONCAT_WS(', ', perPrimerApellido, perSegundoApellido), 40))) THEN REPLACE(RTRIM(LEFT(CONCAT_WS(', ', perPrimerApellido, perSegundoApellido), 40)),',','') ELSE RTRIM(LEFT(CONCAT_WS(', ', perPrimerApellido, perSegundoApellido), 40))END END AS nombre4,
				REPLACE(REPLACE(UPPER(REPLACE(UPPER(ISNULL(u.ubiDireccionFisica, '')),'#', '')),'   ', ' '),'  ',' ') AS direccion, 
				ISNULL(M.muncodigo, '') AS municipio, 
				'CO' AS pais, 
				ISNULL(D.depCodigo, '') AS departamento, 
				ISNULL(U.ubiTelefonoFijo, '') AS telefono, 
				ISNULL(U.ubiTelefonoCelular,'') AS celular, 
				'' AS fax, -- No se registra fax en Genesys
				LOWER(U.ubiEmail) AS email, 
				LOWER(U.ubiEmail) AS email2,
				CASE WHEN perTipoIdentificacion = 'NIT' THEN ''
				ELSE 'NR' END AS claseImpuesto,
				CASE WHEN perTipoIdentificacion = 'NIT' THEN ''
				ELSE '1' END AS PersonaFisica,
				'' AS clavePaisBanco, 
				'' AS claveBanco,
				'' AS nroCuentaBancaria, 
				'' AS titularCuenta,						--mec.mecNombreTitularCuenta AS titularCuenta, 
				'' AS claveControlBanco,
				'' AS tipoBancoInter, 
				'' AS referenciaBancoCuenta, --perNumeroIdentificacion AS referenciaBancoCuenta, -- VALIDAR SI ES EL DOCUMENTO DE IDENTIFICACIoN
				mi.cuentaAsociada AS cuentaAsociada, 
				'009' AS claveClasificacion, 
				'AG' AS grupoTesoreria, 
				'' AS nroPersonal, 
				'K001' AS condicionPago,
				'KRED' AS grupoTolerancia, 
				1 AS VerificacionFacturaDoble, 
				'TE' AS viasPago, 
				'2' AS ExtractoCuenta, 
				'' AS actividadEconomica, 
				'CO' AS paisRetencion, 
				0 AS nroreintentos, 
				'' AS fecproceso, 
				'' AS horaproceso, 
				'P' AS estadoreg, 
				'' AS observacion, 
				'' AS usuario		--ES LLAMADO DESDE EL MOVIMIENTO CONTABLE F06, HEREDA DEL USUARIO QUE REALIZA LA TRANSACCION
			FROM Persona P 
			INNER JOIN MedioCheque mec WITH (NOLOCK) ON mec.mecTipoIdentificacionTitular = P.perTipoIdentificacion AND mec.mecNumeroIdentificacionTitular = P.perNumeroIdentificacion
			INNER JOIN MedioDePago mdp WITH (NOLOCK) ON mec.mdpId = mdp.mdpId 
			LEFT JOIN PersonaDetalle ped WITH (NOLOCK) ON p.perId = ped.pedPersona
			LEFT JOIN Ubicacion U WITH (NOLOCK) ON p.perUbicacionPrincipal = u.ubiId 
			LEFT JOIN Municipio M WITH (NOLOCK) ON U.ubiMunicipio = M.munId
			LEFT JOIN Departamento D WITH (NOLOCK) ON D.depId = M.munDepartamento
			INNER JOIN sap.MaestraIdentificacion mi WITH (NOLOCK) ON p.pertipoidentificacion = mi.TipoIdGenesys
			WHERE P.perNumeroIdentificacion = @documentoId
			AND P.perTipoIdentificacion = @tipoDocumento;

	COMMIT TRANSACTION
END TRY  

BEGIN CATCH

	ROLLBACK TRANSACTION
	
		SELECT  ERROR_NUMBER() AS ErrorNumber  ,ERROR_SEVERITY() AS ErrorSeverity  
    ,ERROR_STATE() AS ErrorState      ,ERROR_PROCEDURE() AS ErrorProcedure  
    ,ERROR_LINE() AS ErrorLine      ,ERROR_MESSAGE() AS ErrorMessage;  
 
END CATCH