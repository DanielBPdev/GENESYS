CREATE OR ALTER PROCEDURE [sap].[USP_GetlCCARTERA_A26] 		@FechaInicial date, @FechaFinal date
As

SET ANSI_NULLS ON
SET  NOCOUNT ON 
SET QUOTED_IDENTIFIER ON

Declare @SaldoAnterior bigint , @consecutivo bigint , @referenciaNum bigint
CREATE TABLE #IC_Aportes_Enc
	(
		[fecIng] [date] NOT NULL,
		[horaIng] [time](7) NOT NULL,
		[fechaDocumento] [date] NOT NULL,
		[fechaContabilizacion] [date] NOT NULL,
		[periodo] [varchar](2) NOT NULL,
		[referencia] [varchar](16) NOT NULL,
		[tipoMovimiento] [varchar](3) NOT NULL,
		[consecutivo] [bigint] NOT NULL,
		[observacion] [varchar](2000) NULL,
		[moneda] [varchar](5) NOT NULL,
		[documentoContable] [varchar](10) NULL,
		[sociedad] [varchar](4) NOT NULL,
		[ejercicio] [varchar](4) NULL,
		[nroIntentos] [smallint] NOT NULL,
		[fecProceso] [date] NULL,
		[horaProceso] [time](7) NULL,
		[estadoReg] [varchar](1) NOT NULL,
		[usuario] [varchar](50) NOT NULL,
	)

	CREATE TABLE #IC_Aportes_Det
	(
		[consecutivo] [bigint] NOT NULL,
		[codigoSap] [varchar](10) NULL,
		[importeDocumento] NUMERIC(21,5) NOT NULL,
		[operador] [varchar](1) NULL,
		[claveCont] [varchar](2) NOT NULL,
		[asignacion] [varchar](18) NOT NULL,
		[ref1] [varchar](12) NOT NULL,
		[tipoDocumento] [varchar](3) NOT NULL,
		[ref3] [varchar](20) NOT NULL,
		[noIdentificado] [varchar](2) NULL,
		[adelantado] [varchar](2) NULL,
		[identificadorDelBanco] [varchar](18) NULL,
		[codigoBanco] [varchar](15) NULL,
		[transitoria] [varchar](1) NULL,
		[claseDeAporte] [varchar](3) NULL,
		[claseDeAportePrescripcion] [varchar](1) NULL,
		[tieneIntereses] [bit] NOT NULL,
		[tipoInteres] [varchar](3) NULL,
		[correccion] [bit] NOT NULL,
		[tipoMora] [varchar](2) NULL,
		[id] [bigint] NOT NULL,
		[codigoGenesys] [bigint] NULL,
		[tipo] [varchar](1) NULL,
		[claseCuenta] [varchar](1) NOT NULL,
		[usuario] [varchar](50) NOT NULL,
		[deudareal] NUMERIC(21,5) NOT NULL,
	)
	Create table #IC_SaldoCarteraReal(
		[idSaldoReal] [int] not null primary key identity(1,1),
		[saldoActualReal] [bigint] Not Null,
		[salfechaContabilizacion] [date] NOT NULL,	
		[consecutivo] [bigint] NOT NULL 
		
	)

------------------ trae los campos de la tabla poblada  en la BD de reportes del reporte cierre de cartera------------------------------------------
	CREATE TABLE #cartera
(FechaProceso	datetime,
[Periodo reporte]	nvarchar(7),
[Cód. Administradora]	varchar(6),
[Tipo de documento del aportante]	varchar(2),
[Número de documento del aportante]	varchar(16),
[Nombre o razón social del aportante]	varchar(250),
[Digito de verificación]	smallint,
[Periodo mora]	nvarchar(7),
[Valor de la cartera]	numeric(19, 5),
[Última acción de cobro]	varchar(50),
[Fecha última acción de cobro]	date,
[Clasificacion del estado del aportante]	varchar(1),
[Aportantes expulsados]	int,
[Valor Deuda Presunta]	numeric(38, 5),
[Valor Deuda Real]	numeric(38, 5),
[Marcación "X" si tiene Deuda presunta]	varchar(1),
[Marcación "X" si tiene Deuda Real]	varchar(1),
[Fecha generación de reporte]	date,
[Valor Convenio Pago]	numeric(19, 5),
[Fecha de Convenio de pago]	datetime,
[Fecha fin Convenio de pago]	datetime,
[Fecha de ingreso a cartera]	datetime,
[Deuda parcial o total]	nvarchar(50),
FechaInicial	date,
FechaFinal	date,
ruta Varchar (max)
)


   DECLARE @SQL NVARCHAR(MAX)
 	SELECT @SQL  = N'SELECT * FROM rno.HistoricoCierreCartera 	
					 WHERE [FechaInicial] = @FechaInicial
					   AND [FechaFinal]= @FechaFinal'
	INSERT INTO #cartera
	EXEC sp_execute_remote N'ReportesReferenceData',
	@SQL,
	 N'@FechaInicial DATE, @FechaFinal DATE',
	 @FechaInicial = @FechaInicial, 
	 @FechaFinal = @FechaFinal		
	



	------Se calcula la cartera del periodo anterior
	Select @SaldoAnterior = saldo.[saldoActualReal] from ( Select top 1 [idSaldoReal], [saldoActualReal] from sap.IC_SaldoCarteraReal order by [idSaldoReal] desc) as saldo
	--Select @SaldoAnterior


	--------se alimenta temporal con la clave 40
				INSERT INTO #IC_Aportes_Det
				([consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[ref1],[tipoDocumento],[ref3],[noIdentificado],[adelantado],
				[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],
				[codigoGenesys],[tipo],[claseCuenta],[usuario],[deudareal])

				SELECT distinct 
				1 consecutivo,
				'' codigosap, --pendiente dice que aplica pero no se puede traer porque no va ref1 ni ref 2 
				-- sum(convert(numeric(19,0),presunta.carDeudaPresunta)) - @SaldoAnterior importedocumento,
				isnull(ABS(sum(convert(numeric(19,0),[Valor Deuda Real]))- @SaldoAnterior),0) importedocumento,
				--case when sum(convert(numeric(19,0),6)) - @SaldoAnterior < 0 then '-' else '+' end as operador,
				case when sum(convert(numeric(19,0),[Valor Deuda Real])) - @SaldoAnterior < 0 then '-' else '' end as operador,
				'40' clavecont, 
				'' asignacion, --No se envia en este movimiento
				'' ref1, --No se envia en este movimiento
				'' tipodocumento, --No se envia en este movimiento
				'' ref3, --No se envia en este movimiento
				'' noidentificado, --No se envia en este movimiento
				'' adelantado, --No se envia en este movimiento
				'' identificadorDelBanco, --No se envia en este movimiento
				'' codigoBanco, --No se envia en este movimiento
				'' transitoria, --No se envia en este movimiento
				'' clasedeAporte, --No se envia en este movimiento
				'' clasedeAportePrescripcion, --No se envia en este movimiento
				'' tieneInteres, --No se envia en este movimiento
				'' tipoInteres,
				'0' correccion,
				'RM' tipoMora, --validar que valor se envia
				'0' id,
				' ' codigoGenesys, --validar por lo mismo de codigo sap
				'' tipo, --validar por lo mismo de codigo sap
				'S' clasecuenta,
				'Integracíones' usuario,
				isnull(sum(convert(numeric(19,0),[Valor Deuda Real])),0) deudareal
						
				FROM #cartera
				WHERE [FechaInicial] = @FechaInicial
				and [FechaFinal]  = @FechaFinal
				

	--------se alimenta temporal con la clave 50		
				INSERT INTO #IC_Aportes_Det
				([consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[ref1],[tipoDocumento],[ref3],[noIdentificado],[adelantado],
				[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],
				[codigoGenesys],[tipo],[claseCuenta],[usuario],[deudareal])
				select [consecutivo],[codigoSap],[importeDocumento],[operador],'50' [claveCont],[asignacion],[ref1],[tipoDocumento],[ref3],[noIdentificado],[adelantado],
				[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[id],
				' ' as [codigoGenesys],[tipo],[claseCuenta],[usuario],[deudareal] from #IC_Aportes_Det

				INSERT INTO  #IC_Aportes_Enc
				([fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo],[referencia],[tipoMovimiento],[consecutivo],[observacion],[moneda],[documentoContable],
				[sociedad],[ejercicio],[nroIntentos],[fecProceso],[horaProceso],[estadoReg],[usuario])
				SELECT  CONVERT(varchar, SAP.GetLocalDate(), 111) AS fecing, 
				CONVERT (varchar, SAP.GetLocalDate(), 108) AS horaing,
				CONVERT(varchar, SAP.GetLocalDate(), 111) AS fechaDocumento, 
				CONVERT(varchar, SAP.GetLocalDate(), 111) AS fechaContabilizacion, 
				MONTH(CONVERT(varchar, SAP.GetLocalDate(), 111)) AS periodo, 
				'0' AS referencia, --validar que referencia se va usar
				'A26' AS tipoMovimiento, 
				'1' AS consecutivo,
				'' AS observacion, 
				'COP' AS moneda, 
				'' AS documentoContable, 
				'COMF' AS sociedad, 
				'' AS ejercicio, 
				'' AS nrointentos, 
				'' AS fecproceso, 
				'' AS horaproceso, 
				'P' AS estadoreg,
				'Integracíones' usuario

				INSERT INTO #IC_SaldoCarteraReal 
				([saldoActualReal], [salfechaContabilizacion],[consecutivo])
				
				Select deudareal ,CONVERT(varchar, SAP.GetLocalDate(), 111) AS salfechaContabilizacion,
				'1' as consecutivo from #IC_Aportes_Det where claveCont = '40'


			
		SELECT @referenciaNum = valorActual 
		FROM sap.ic_referencia
		WHERE comentario = 'M'
		AND estado = 'A';

		UPDATE #IC_Aportes_Enc
		SET referencia = @referenciaNum

	--- Comentarear

			UPDATE sap.ic_referencia
			SET valorActual = @referenciaNum + 1
			WHERE comentario = 'M'
			AND estado = 'A';
	
	-----------------------------


	--	SELECT @consecutivo = ISNULL(MAX(consecutivo), 0) FROM sap.IC_Aportes_Enc

	--	UPDATE #IC_Aportes_Det
	--	SET consecutivo = consecutivo + @consecutivo

	--	UPDATE #IC_Aportes_Enc
	--	SET consecutivo = consecutivo + @consecutivo

	--	UPDATE #IC_SaldoCarteraReal
	--	SET consecutivo = consecutivo + @consecutivo

	--	----------  llenado final consecutivo para evitar saltos de linea 13/09/2023 -----------
	--UPDATE  #IC_Aportes_Det
	--SET consecutivo =(SELECT consecutivo FROM #IC_Aportes_Enc)

	--UPDATE #IC_SaldoCarteraReal
	--	SET consecutivo = (SELECT consecutivo FROM #IC_Aportes_Enc)



------ comentarear

		IF @referenciaNum is not null
			BEGIN

				INSERT INTO IC_Aportes_Enc(
					[fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo],[referencia],[tipoMovimiento],[observacion],
					[moneda],[documentoContable],[sociedad],[ejercicio],[nroIntentos],[fecProceso],[horaProceso],[estadoReg],[usuario])
				SELECT distinct [fecIng],[horaIng],[fechaDocumento],[fechaContabilizacion],[periodo],[referencia],[tipoMovimiento],[observacion],
					[moneda],[documentoContable],[sociedad],[ejercicio],[nroIntentos],[fecProceso],[horaProceso],[estadoReg],[usuario]
				FROM #IC_Aportes_Enc; 

				-- Actualizar el consecutivo definitivo en la tabla de integración de encabezado con el detalle
					SELECT @consecutivo= MAX(consecutivo) FROM sap.IC_Aportes_Enc

					UPDATE #IC_Aportes_Det
					SET consecutivo = @consecutivo

					UPDATE #IC_SaldoCarteraReal
					SET consecutivo = @consecutivo

				INSERT INTO sap.IC_Aportes_Det([consecutivo],[codigoSap],[importeDocumento],[operador],[claveCont],[asignacion],[ref1],[tipoDocumento],[ref3],
						[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
						[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[codigoGenesys],[tipo],
						[claseCuenta],[usuario],[fechaEjecucion])
				SELECT [consecutivo],[codigoSap],CAST(CAST([importeDocumento] AS BIGINT) AS VARCHAR(18)),[operador],[claveCont],[asignacion],[ref1],[tipoDocumento],[ref3],
						[noIdentificado],[adelantado],[identificadorDelBanco],[codigoBanco],[transitoria],[claseDeAporte],
						[claseDeAportePrescripcion],[tieneIntereses],[tipoInteres],[correccion],[tipoMora],[codigoGenesys],[tipo],
						[claseCuenta],[usuario],GETDATE()-'05:00:00'
				FROM #IC_Aportes_Det 

				UPDATE  #IC_Aportes_Det
					SET consecutivo =(SELECT consecutivo FROM #IC_Aportes_Enc)
				

				INSERT INTO sap.IC_SaldoCarteraReal
				Select [saldoActualReal],[salfechaContabilizacion],[consecutivo]
				From #IC_SaldoCarteraReal
				

		END
		

				SELECT * FROM #IC_Aportes_Det
				SELECT * FROM #IC_Aportes_Enc
				SELECT * FROM #IC_SaldoCarteraReal

				DROP TABLE  #IC_Aportes_Det
				DROP TABLE #IC_Aportes_Enc
				DROP TABLE #IC_SaldoCarteraReal


				--select * from sap.IC_SaldoCarteraReal




				
