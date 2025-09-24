-- =======================================================================================================================================
-- Author:		Jhon Freddy Rico Bermúdez
-- Create date: 2020/09/02
-- Description:	Define el cursor para obtener la información de la plantilla CAR_PER_EXP (Carta a persona expulsada)
-- =======================================================================================================================================
CREATE PROCEDURE [dbo].[USP_UTIL_CURSOR_CAR_PER_EXP] 
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
		CONVERT(VARCHAR(40), '${nombreYApellidosAfiliadoPrincipal}') as llave2,
		CONVERT(VARCHAR(100), (per.perPrimerNombre +' ' + CASE WHEN per.perSegundoNombre IS NOT NULL
			THEN per.perSegundoNombre ELSE '' END + ' ' + per.perPrimerApellido + ' ' + CASE WHEN 
			per.perSegundoApellido IS NOT NULL THEN per.perSegundoApellido ELSE '' END)) AS valor2, 
		CONVERT(VARCHAR, '${direccion}') as llave3, CONVERT(VARCHAR(100), ubi.ubiDireccionFisica) valor3,
		CONVERT(VARCHAR, '${telefono}') as llave4, CONVERT(VARCHAR, (CASE WHEN ubi.ubiTelefonoFijo+'' IS NOT NULL
				THEN ubi.ubiTelefonoFijo ELSE ubi.ubiTelefonoCelular END))  AS valor4,
		CONVERT(VARCHAR, '${ciudad}') as llave5, CONVERT(VARCHAR(50), mun.munNombre) as valor5
		FROM Ubicacion ubi,
		Municipio mun,
		Persona per
		WHERE ubi.ubiId 				= per.perUbicacionPrincipal 
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
	     (llave5, valor5)
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