-- =======================================================================================================================================
-- Author:		Jhon Freddy Rico Berm�dez
-- Create date: 2020/09/02
-- Description:	Define el cursor para obtener la informaci�n de la plantilla CAR_EMP_EXP (Carta a empresa expulsada)
-- =======================================================================================================================================
CREATE PROCEDURE [dbo].[USP_UTIL_CURSOR_CAR_EMP_EXP] 
	@idPlantilla bigint, 
 	@tipoAccionCobro varchar(4), 
 	@tipoIdentificacion varchar(20),
	@numeroIdentificacion varchar(16),
 	@paramCursor  CURSOR VARYING OUTPUT
AS
 SET NOCOUNT ON

 SET @paramCursor = CURSOR FAST_FORWARD FOR
	SELECT llave, valor, variable.tipoVariable
	FROM 
	(
		SELECT vCom.vcoClave clave, vCom.vcoTipoVariableComunicado tipoVariable
		FROM VariableComunicado vCom 
		WHERE vCom.vcoPlantillaComunicado = @idPlantilla
	) variable,
	(
		SELECT CONVERT(VARCHAR, '${fechaDelSistema}') as llave1, 
		(Select Top 1 CONVERT(VARCHAR, dosFechaHoraCargue, 120) from DocumentoSoporte 
					join DocumentoCartera on dosId=dcaDocumentoSoporte 
					join Cartera on dcaCartera = carId 
					where carPersona = per.perId 
					and dcaAccionCobro = @tipoAccionCobro
					order by dosId desc ) AS valor1, 
		CONVERT(VARCHAR(50), '${nombreYApellidosRepresentanteLegal}') as llave2,
		CONVERT(VARCHAR(100), (prl.perPrimerNombre +' ' + CASE WHEN prl.perSegundoNombre IS NOT NULL
			THEN prl.perSegundoNombre ELSE '' END + ' ' +
			prl.perPrimerApellido + ' ' + CASE WHEN prl.perSegundoApellido IS NOT NULL 
			THEN prl.perSegundoApellido ELSE '' END)) AS valor2, 
		CONVERT(VARCHAR, '${razonSocial/Nombre}') as llave3, CONVERT(VARCHAR(100), per.perRazonSocial)  AS valor3,
		CONVERT(VARCHAR, '${direccion}') as llave4, CONVERT(VARCHAR(100), ubi.ubiDireccionFisica) AS valor4,
		CONVERT(VARCHAR, '${telefono}') as llave5, CONVERT(VARCHAR, (CASE WHEN ubi.ubiTelefonoFijo+'' IS NOT NULL
				THEN ubi.ubiTelefonoFijo ELSE ubi.ubiTelefonoCelular END))  AS valor5,
		CONVERT(VARCHAR, '${ciudad}') as llave6, CONVERT(VARCHAR(50), mun.munNombre) AS valor6
		FROM Empresa empr,
		UbicacionEmpresa ubiE,
		Ubicacion ubi,
		Municipio mun,
		Persona per, 
		Persona prl
		WHERE empr.empPersona			= per.perId	
		AND empr.empRepresentanteLegal	= prl.perId
		AND ubiE.ubeEmpresa 			= empr.empId
		AND ubiE.ubeUbicacion 			= ubi.ubiId
		AND ubiE.ubeTipoUbicacion		= 'ENVIO_CORRESPONDENCIA'
		AND mun.munId					= ubi.ubiMunicipio
		AND per.perTipoIdentificacion	= @tipoIdentificacion
		AND per.perNumeroIdentificacion	= @numeroIdentificacion
	) p
	cross apply
	(
	     values
	     (llave1, valor1),
	     (llave2, valor2),
	     (llave3, valor3),
	     (llave4, valor4),
	     (llave5, valor5),
	     (llave6, valor6)
	) c (llave, valor)
	WHERE 
	variable.clave like ('%'+llave+'%')
	UNION	
	SELECT variable.clave llave, valorConstante.valor valor, variable.tipoVariable
	FROM 
	(
		select c.cnsValor valor, cnsNombre nombre from Constante c
		union
		select p.prmValor valor, p.prmNombre from Parametro p 
	) valorConstante,
	(
		SELECT vCom.vcoClave clave, vCom.vcoNombreConstante nombreConstante, vCom.vcoTipoVariableComunicado tipoVariable 
		FROM VariableComunicado vCom 
			WHERE vCom.vcoPlantillaComunicado  = @idPlantilla
			AND vCom.vcoTipoVariableComunicado = 'CONSTANTE'
	) variable
	WHERE 
	valorConstante.nombre = variable.nombreConstante
	
	OPEN @paramCursor