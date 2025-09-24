/****** Object:  StoredProcedure [dbo].[reporteAcciondeCobro]    Script Date: 13/09/2022 5:13:33 p. m. ******/
/****** Object:  StoredProcedure [dbo].[reporteAcciondeCobro]    Script Date: 02/09/2022 11:43:51 a. m. ******/ 
/****** Object:  StoredProcedure [dbo].[reporteAcciondeCobro]    Script Date: 24/08/2022 10:40:58 a. m. ******/
-- =============================================
-- Author:      Miguel Angel Perilla
-- Create Date: 10 Junio 2022
-- Description: Procedimiento almacenado para obtener el resultado que hace referencia al reporte 36.
-- Reporte 36
----execute reporteAcciondeCobro '2022-09-01','2022-09-19'
-- =============================================
create or ALTER   PROCEDURE [dbo].[reporteAcciondeCobro]( @FECHA_INICIAL DATE, @FECHA_FINAL DATE )

AS
BEGIN

SET NOCOUNT ON
	--/---------------------------------------**********---------------------------------------\--
	--                          REPORTE DE ACCION DE COBRO  -  N� 36.
	--\---------------------------------------**********---------------------------------------/--
	----------------------------
	------- METODO 1
	----------------------------
		SELECT
		---------- Nombre o raz�n social del aportante
		----------------------------------------------------------------------
		PER.perRazonSocial AS [Nombre o razon social del aportante],

		---------- Tipo de documento del aportante
		----------------------------------------------------------------------
		CASE
			WHEN PER.perTipoIdentificacion = 'NIT' THEN 'NI'
			WHEN PER.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN PER.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN PER.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN PER.perTipoIdentificacion = 'REGISTRO_CIVIL' THEN 'RC'
			WHEN PER.perTipoIdentificacion = 'PASAPORTE' THEN 'PA'
			WHEN PER.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN PER.perTipoIdentificacion = 'SALVOCONDUCTO' THEN 'SC'
			WHEN PER.perTipoIdentificacion = 'PERM_ESP_PERMANENCIA' THEN 'PE'
		END	AS [Tipo de documento del aportante],

		---------- N�mero de documento del aportante
		----------------------------------------------------------------------
		PER.perNumeroIdentificacion AS [Numero de documento del aportante],

		---------- N�mero de d�gito de verificaci�n
		----------------------------------------------------------------------
		PER.perDigitoVerificacion AS [Numero de digito de verificacion],

		---------- Mecanismo de env�o de la comunicaci�n persuasiva
		----------------------------------------------------------------------
		CASE
			WHEN BCA.bcaMedio IN ('ELECTRONICO')
			THEN '2'
			ELSE '3'
		END AS [Mecanismo de envio de la comunicacion persuasiva],

		---------- Descripci�n del otro mecanismo de env�o de la comunicaci�n
		----------------------------------------------------------------------
		null AS [Descripcion del otro mecanismo de envio de la comunicacion],

		---------- Fecha de env�o de la comunicac�on persuasiva
		----------------------------------------------------------------------
		(SELECT MAX( FORMAT( BCA2.bcaFecha,  'yyyy-MM-dd') )
		FROM BitacoraCartera BCA2
			WHERE 
				BCA2.bcaPersona = PER.perId
				AND BCA2.bcaActividad IN ( 'E1', 'D1' )
				AND BCA2.bcaResultado IN ( 'EXITOSO', 'NO_EXITOSO' )
				AND BCA2.bcaFecha between @FECHA_INICIAL and  @FECHA_FINAL
			)AS [Fecha de envio de la comunicacion persuasiva],

		---------- Estado de la comunicaci�n
		----------------------------------------------------------------------
		ISNULL((

			SELECT  
				CASE 
					WHEN BCA2.bcaResultado IN ('EXITOSO') THEN '1'
					WHEN BCA2.bcaResultado IN ('NO_EXITOSO') THEN '2'
					ELSE '2'
				END
			FROM BitacoraCartera BCA2
			WHERE 
				BCA2.bcaPersona = PER.perId
				AND BCA2.bcaActividad IN ( 'E1', 'D1' )
				AND BCA2.bcaResultado IN ( 'EXITOSO', 'NO_EXITOSO' )
				AND BCA2.bcaFecha between @FECHA_INICIAL and  @FECHA_FINAL

		), '2') AS [Estado de la comunicacion],

		---------- identificaci�n del documento que presta m�rito ejecutivo para iniciar el cobro
		----------------------------------------------------------------------
		MAX(DCA.dcaConsecutivoLiquidacion) AS [identificacion del documento que presta merito ejecutivo para iniciar el cobro],

		---------- Fecha de constituci�n del documento que presta m�rito ejecutivo
		----------------------------------------------------------------------
		(
			SELECT MAX(FORMAT( BCA3.bcaFecha,  'yyyy-MM-dd'))
			FROM BitacoraCartera BCA3 
			WHERE 
				BCA3.bcaActividad IN ( 'GENERAR_LIQUIDACION' )
				AND BCA3.bcaPersona = PER.perId
				AND BCA3.bcaFecha between @FECHA_INICIAL AND  @FECHA_FINAL
		)AS [Fecha de constitucion del documento que presta merito ejecutivo]
	
	INTO #ACCIONES 
	FROM
		Cartera CAR
		INNER JOIN Persona PER ON PER.perId = CAR.carPersona
		INNER JOIN BitacoraCartera BCA 
			ON BCA.bcaPersona = PER.perId
			--AND BCA.bcaActividad IN ( 'E1', 'D1' )
			--AND BCA.bcaResultado IN ( 'ENVIADO' )
		--WHERE PER.perNumeroIdentificacion = '10132500'
	LEFT JOIN ( SELECT dcaDocumentoSoporte, dcaCartera, dcaConsecutivoLiquidacion, carpersona
		              FROM DocumentoCartera  
					  inner join cartera on carid = dcaCartera
		             WHERE dcaConsecutivoLiquidacion IS NOT NULL) AS  DCA
		     ON DCA.carpersona = car.carpersona 
			--  AND LEFT(dcaConsecutivoLiquidacion,6)= LEFT (@FECHA_INICIAL,6)---PRUEBA
		INNER JOIN Empresa EMPRE ON EMPRE.empPersona = PER.perId
		INNER JOIN Empleador EMPLE 
			ON EMPLE.empEmpresa = EMPRE.empId
			AND EMPLE.empEstadoEmpleador IN ('ACTIVO','INACTIVO','NO_FORMALIZADO_RETIRADO_CON_APORTES')
	WHERE
		CAR.carTipoLineaCobro IN ('LC1', 'C6')
		AND CAR.carMetodo = 'METODO_1'
		AND CAR.carTipoAccionCobro NOT IN ('A1','B1', 'A01', 'AB1', 'BC1') 
		AND CAR.carEstadoCartera = 'MOROSO'
		AND CAR.carEstadoOperacion = 'VIGENTE'
		AND  (DATEDIFF(DAY,CAR.carFechaCreacion,CAST(@FECHA_FINAL AS DATE))) >= 30 --BETWEEN @FECHA_INICIAL AND @FECHA_FINAL
		--AND PER.perNumeroIdentificacion = '10132500'
	
	GROUP BY
		PER.perId,
		PER.perRazonSocial,
		PER.perTipoIdentificacion,
		PER.perNumeroIdentificacion,
		PER.perDigitoVerificacion,
		BCA.bcaMedio
		--BCA.bcaFecha
		--DCA.dcaConsecutivoLiquidacion

	UNION ALL

	----------------------------
	------- METODO 2
	----------------------------

	SELECT 
		---------- Nombre o raz�n social del aportante
		----------------------------------------------------------------------
		PER.perRazonSocial AS [Nombre o razon social del aportante],

		---------- Tipo de documento del aportante
		----------------------------------------------------------------------
		CASE
			WHEN PER.perTipoIdentificacion = 'NIT' THEN 'NI'
			WHEN PER.perTipoIdentificacion = 'CEDULA_CIUDADANIA' THEN 'CC'
			WHEN PER.perTipoIdentificacion = 'CEDULA_EXTRANJERIA' THEN 'CE'
			WHEN PER.perTipoIdentificacion = 'TARJETA_IDENTIDAD' THEN 'TI'
			WHEN PER.perTipoIdentificacion = 'REGISTRO_CIVIL' THEN 'RC'
			WHEN PER.perTipoIdentificacion = 'PASAPORTE' THEN 'PA'
			WHEN PER.perTipoIdentificacion = 'CARNE_DIPLOMATICO' THEN 'CD'
			WHEN PER.perTipoIdentificacion = 'SALVOCONDUCTO' THEN 'SC'
			WHEN PER.perTipoIdentificacion = 'PERM_ESP_PERMANENCIA' THEN 'PE'
		END AS [Tipo de documento del aportante],

		---------- N�mero de documento del aportante
		----------------------------------------------------------------------
		PER.perNumeroIdentificacion AS [Numero de documento del aportante],

		---------- N�mero de d�gito de verificaci�n
		----------------------------------------------------------------------
		PER.perDigitoVerificacion AS [Numero de digito de verificacion],

		---------- Mecanismo de env�o de la comunicaci�n persuasiva
		----------------------------------------------------------------------
		CASE
			WHEN BCA.bcaMedio IN ('ELECTRONICO')
			THEN '2'
			ELSE '3'
		END AS [Mecanismo de envio de la comunicacion persuasiva],

		---------- Descripci�n del otro mecanismo de env�o de la comunicaci�n
		----------------------------------------------------------------------
		null AS [Descripcion del otro mecanismo de envio de la comunicacion],

		---------- Fecha de env�o de la comunicac�on persuasiva
		----------------------------------------------------------------------
		(SELECT (FORMAT( BCA.bcaFecha,  'yyyy-MM-dd'))
		 
			)
		
		AS [Fecha de envio de la comunicacion persuasiva],

		---------- Estado de la comunicaci�n
		----------------------------------------------------------------------
		ISNULL((
			SELECT 
				CASE 
					WHEN BCA2.bcaResultado IN ('EXITOSO') THEN '1'
					WHEN BCA2.bcaResultado IN ('NO_EXITOSO') THEN '2'
					ELSE '2'
				END
			FROM BitacoraCartera BCA2
			WHERE 
				BCA2.bcaPersona = PER.perId
				AND BCA2.bcaActividad IN ( 'F2', 'G2' )
				AND BCA2.bcaResultado IN ( 'EXITOSO', 'NO_EXITOSO' )
				AND BCA2.bcaFecha between @FECHA_INICIAL and  @FECHA_FINAL

		), '2') AS [Estado de la comunicacion],

		---------- identificaci�n del documento que presta m�rito ejecutivo para iniciar el cobro
		----------------------------------------------------------------------
		DCA.dcaConsecutivoLiquidacion AS [identificacion del documento que presta merito ejecutivo para iniciar el cobro],

		---------- Fecha de constituci�n del documento que presta m�rito ejecutivo
		----------------------------------------------------------------------
		(
			SELECT MAX(FORMAT( BCA3.bcaFecha,  'yyyy-MM-dd'))
			FROM BitacoraCartera BCA3 
			WHERE 
				BCA3.bcaActividad IN ( 'INGRESO_PREVENTIVA' ,'GENERAR_LIQUIDACION')
				AND BCA3.bcaPersona = PER.perId
				AND BCA3.bcaFecha between @FECHA_INICIAL and  @FECHA_FINAL
		)AS [Fecha de constitucion del documento que presta merito ejecutivo]

	FROM
		Cartera CAR
		INNER JOIN Persona PER ON PER.perId = CAR.carPersona
		INNER JOIN BitacoraCartera BCA 
			ON BCA.bcaPersona = PER.perId
			AND BCA.bcaActividad IN ('F2','G2','GENERAR_LIQUIDACION' )
			AND BCA.bcaResultado IN ('ENVIADO' ,'GENERADO_PERSONALMENTE')
			AND BCA.bcaFecha BETWEEN @FECHA_INICIAL AND @FECHA_FINAL
	LEFT JOIN ( SELECT dcaDocumentoSoporte, dcaCartera, dcaConsecutivoLiquidacion, carpersona
		              FROM DocumentoCartera  
		            inner join cartera on carid = dcaCartera
		             WHERE dcaConsecutivoLiquidacion IS NOT NULL) AS  DCA
		     ON DCA.carpersona = car.carpersona 
		INNER JOIN Empresa EMPRE ON EMPRE.empPersona = PER.perId
		INNER JOIN Empleador EMPLE 
			ON EMPLE.empEmpresa = EMPRE.empId
			AND EMPLE.empEstadoEmpleador IN ('ACTIVO','INACTIVO','NO_FORMALIZADO_RETIRADO_CON_APORTES')
	WHERE 
		CAR.carTipoLineaCobro IN  ('LC1')
		AND CAR.carMetodo = 'METODO_2'
		AND CAR.carTipoAccionCobro NOT IN ('A2','B2', 'A02', 'AB2', 'BC2') 
		AND CAR.carEstadoCartera = 'MOROSO'
		AND CAR.carEstadoOperacion = 'VIGENTE'
		AND  (DATEDIFF(DAY,CAR.carFechaCreacion,CAST(@FECHA_FINAL AS DATE))) >= 30
		--AND CAR.carFechaCreacion BETWEEN @FECHA_INICIAL AND @FECHA_FINAL*/

	SELECT 
		[Nombre o razon social del aportante],
		[Tipo de documento del aportante],
		[Numero de documento del aportante],
		[Numero de digito de verificacion],
		MIN([Mecanismo de envio de la comunicacion persuasiva]) AS [Mecanismo de envio de la comunicacion persuasiva],
		[Descripcion del otro mecanismo de envio de la comunicacion],
		[Fecha de envio de la comunicacion persuasiva],
		[Estado de la comunicacion],
		[identificacion del documento que presta merito ejecutivo para iniciar el cobro],
		[Fecha de constitucion del documento que presta merito ejecutivo]
	FROM #ACCIONES
	GROUP BY 
		[Nombre o razon social del aportante],
		[Tipo de documento del aportante],
		[Numero de documento del aportante],
		[Numero de digito de verificacion],
		[Descripcion del otro mecanismo de envio de la comunicacion],
		[Fecha de envio de la comunicacion persuasiva],
		[Estado de la comunicacion],
		[identificacion del documento que presta merito ejecutivo para iniciar el cobro],
		[Fecha de constitucion del documento que presta merito ejecutivo]
END