-- =======================================================================================================================================
-- Author:		Jhon Freddy Rico Bermúdez
-- Create date: 2020/09/02
-- Description:	Define el cursor para obtener la información de la plantilla LIQ_APO_MOR (Liquidación de aportes en mora)
-- =======================================================================================================================================
CREATE PROCEDURE [dbo].[USP_UTIL_CURSOR_LIQ_APO_MOR] 
 	@idPlantilla bigint, 
 	@cartera     bigint,
 	@paramCursor CURSOR VARYING OUTPUT
AS
 --SET NOCOUNT ON;
 
 SET @paramCursor = CURSOR FAST_FORWARD FOR
	SELECT llave, valor, variable.tipoVariable
	FROM 
	(
		SELECT vCom.vcoClave clave, vCom.vcoTipoVariableComunicado tipoVariable
		FROM VariableComunicado vCom 
		WHERE vCom.vcoPlantillaComunicado = @idPlantilla
	) variable,
	(
		SELECT TOP 1
			CONVERT(VARCHAR, '${razonSocial/Nombre}') as llave1, CONVERT(VARCHAR(100),per.perRazonSocial)	AS valor1, 
			CONVERT(VARCHAR, '${tipoDeIdentificacion}') as llave2, CONVERT(VARCHAR,per.perTipoIdentificacion) AS valor2, 
			CONVERT(VARCHAR, '${numeroIdentificacion}') as llave3, CONVERT(VARCHAR,per.perNumeroIdentificacion)  AS valor3,
			CONVERT(VARCHAR, '${fechaSuspencionAutomatica}') as llave4, CONVERT(VARCHAR,hacA.hacFechaAsignacionAccion, 120)  AS valor4,
			CONVERT(VARCHAR, '${nombreComunicado}') as llave5, CONVERT(VARCHAR(40),'Notificación de No Recaudo de Aportes') AS valor5,
			CONVERT(VARCHAR, '${fechaComunicado}') as llave6, CONVERT(VARCHAR,hacA.hacFechaAsignacionAccion, 120) AS valor6,
			CONVERT(VARCHAR, '${fechaNotificacionPersonal}') as llave7, CONVERT(VARCHAR,hacC.hacFechaAsignacionAccion, 120) as valor7,	
			CONVERT(VARCHAR, '${consecutivoLiquidacion}') as llave8, CONVERT(VARCHAR,dca.dcaConsecutivoLiquidacion) as valor8,
			CONVERT(VARCHAR, '${fechaLiquidacion}') as llave9, CONVERT(VARCHAR,dos.dosFechaHoraCargue, 120) as valor9
		FROM Cartera car
		JOIN HistoricoAsignacionCartera hacA ON car.carId = hacA.hacCartera
		JOIN HistoricoAsignacionCartera hacC ON car.carId = hacC.hacCartera
		JOIN DocumentoCartera dca ON car.carId = dca.dcaCartera
		JOIN DocumentoSoporte dos ON dos.dosId = dcaDocumentoSoporte
		JOIN Persona per ON per.perId = car.carPersona
		WHERE car.carId = @cartera
		ORDER BY valor8 DESC
	) p
	cross apply
	(
		 values
		 (llave1, valor1),
		 (llave2, valor2),
		 (llave3, valor3),
		 (llave4, valor4),
		 (llave5, valor5),
		 (llave6, valor6),
		 (llave7, valor7),
		 (llave8, valor8),
		 (llave9, valor9)
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
		AND vCom.vcoClave not like '%logo%'
	) variable
	WHERE 
	valorConstante.nombre = variable.nombreConstante

	OPEN @paramCursor
