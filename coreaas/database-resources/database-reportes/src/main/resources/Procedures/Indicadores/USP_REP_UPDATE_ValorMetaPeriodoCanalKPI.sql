-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2018/06/28
-- Description:	Actualiza las Metas para las dimensiones existentes
-- =============================================
CREATE PROCEDURE USP_REP_UPDATE_ValorMetaPeriodoCanalKPI
AS

	--CREATE INDEX IX_ValorMetaPeriodoCanalKPI ON ValorMetaPeriodoCanalKPI (vmpMetaPeriodoKPI,vmpCanal,vmpPeriodo)
	--CREATE INDEX IX_MetaPeriodoKPI ON MetaPeriodoKPI (mpkMeta,mpkAnio,mpkFrecuencia)

	WITH cte AS
	(
		SELECT 
			dic.dicId AS dmcDimCanal, 
			dip.dipId AS dmcDimPeriodo, 
			ISNULL(vmpM.vmpValorInt,0) AS dmcMetaAfiEmpMes,
			ISNULL(vmpB.vmpValorInt,0) AS dmcMetaAfiEmpBimensual,
			ISNULL(vmpT.vmpValorInt,0) AS dmcMetaAfiEmpTrimestral,
			ISNULL(vmpS.vmpValorInt,0) AS dmcMetaAfiEmpSemestral,
			ISNULL(vmpA.vmpValorInt,0) AS dmcMetaAfiEmpAnual,

			ISNULL(vmpMR.vmpValorInt,0) AS dmcMetaRechEmpMes,
			ISNULL(vmpBR.vmpValorInt,0) AS dmcMetaRechEmpBimensual,
			ISNULL(vmpTR.vmpValorInt,0) AS dmcMetaRechEmpTrimestral,
			ISNULL(vmpSR.vmpValorInt,0) AS dmcMetaRechEmpSemestral,
			ISNULL(vmpAR.vmpValorInt,0) AS dmcMetaRechEmpAnual,
			
			 
			ISNULL(vmpM1.vmpValorInt,0) AS dmcMetaAfiPerMes,
			ISNULL(vmpB1.vmpValorInt,0) AS dmcMetaAfiPerBimensual,
			ISNULL(vmpT1.vmpValorInt,0) AS dmcMetaAfiPerTrimestral,
			ISNULL(vmpS1.vmpValorInt,0) AS dmcMetaAfiPerSemestral,
			ISNULL(vmpA1.vmpValorInt,0) AS dmcMetaAfiPerAnual,

			ISNULL(vmpMR1.vmpValorInt,0) AS dmcMetaRechPerMes,
			ISNULL(vmpBR1.vmpValorInt,0) AS dmcMetaRechPerBimensual,
			ISNULL(vmpTR1.vmpValorInt,0) AS dmcMetaRechPerTrimestral,
			ISNULL(vmpSR1.vmpValorInt,0) AS dmcMetaRechPerSemestral,
			ISNULL(vmpAR1.vmpValorInt,0) AS dmcMetaRechPerAnual

		FROM DimCanal dic
		CROSS JOIN DimPeriodo dip
		LEFT JOIN (	ValorMetaPeriodoCanalKPI vmpM
					INNER JOIN MetaPeriodoKPI mpkM ON vmpM.vmpMetaPeriodoKPI = mpkM.mpkId AND mpkM.mpkFrecuencia = 'Mensual' AND mpkM.mpkMeta = 'PORCENTAJE_AFILIACIONES_EMPLEADORES') ON dic.dicDescripcion = vmpM.vmpCanal and dip.dipAnio = mpkM.mpkAnio and dip.dipMes = vmpM.vmpPeriodo
		LEFT JOIN (	ValorMetaPeriodoCanalKPI vmpB
					INNER JOIN MetaPeriodoKPI mpkB ON vmpB.vmpMetaPeriodoKPI = mpkB.mpkId AND mpkB.mpkFrecuencia = 'Bimensual' AND mpkB.mpkMeta = 'PORCENTAJE_AFILIACIONES_EMPLEADORES') ON dic.dicDescripcion = vmpB.vmpCanal and dip.dipAnio = mpkB.mpkAnio and dip.dipMes = vmpB.vmpPeriodo
		LEFT JOIN (	ValorMetaPeriodoCanalKPI vmpT
					INNER JOIN MetaPeriodoKPI mpkT ON vmpT.vmpMetaPeriodoKPI = mpkT.mpkId AND mpkT.mpkFrecuencia = 'Trimestral' AND mpkT.mpkMeta = 'PORCENTAJE_AFILIACIONES_EMPLEADORES') ON dic.dicDescripcion = vmpT.vmpCanal and dip.dipAnio = mpkT.mpkAnio and dip.dipMes = vmpT.vmpPeriodo
		LEFT JOIN (	ValorMetaPeriodoCanalKPI vmpS
					INNER JOIN MetaPeriodoKPI mpkS ON vmpS.vmpMetaPeriodoKPI = mpkS.mpkId AND mpkS.mpkFrecuencia = 'Semestral' AND mpkS.mpkMeta = 'PORCENTAJE_AFILIACIONES_EMPLEADORES') ON dic.dicDescripcion = vmpS.vmpCanal and dip.dipAnio = mpkM.mpkAnio and dip.dipMes = vmpS.vmpPeriodo
		LEFT JOIN (	ValorMetaPeriodoCanalKPI vmpA
					INNER JOIN MetaPeriodoKPI mpkA ON vmpA.vmpMetaPeriodoKPI = mpkA.mpkId AND mpkA.mpkFrecuencia = 'Anual' AND mpkA.mpkMeta = 'PORCENTAJE_AFILIACIONES_EMPLEADORES') ON dic.dicDescripcion = vmpA.vmpCanal and dip.dipAnio = mpkA.mpkAnio and dip.dipMes = vmpA.vmpPeriodo

		LEFT JOIN (	ValorMetaPeriodoCanalKPI vmpMR
					INNER JOIN MetaPeriodoKPI mpkMR ON vmpMR.vmpMetaPeriodoKPI = mpkMR.mpkId AND mpkMR.mpkFrecuencia = 'Mensual' AND mpkMR.mpkMeta = 'PORCENTAJE_RECHAZOS_AFILIACIONES_EMPLEADORES') ON dic.dicDescripcion = vmpMR.vmpCanal and dip.dipAnio = mpkMR.mpkAnio and dip.dipMes = vmpMR.vmpPeriodo
		LEFT JOIN (	ValorMetaPeriodoCanalKPI vmpBR
					INNER JOIN MetaPeriodoKPI mpkBR ON vmpBR.vmpMetaPeriodoKPI = mpkBR.mpkId AND mpkBR.mpkFrecuencia = 'Bimensual' AND mpkBR.mpkMeta = 'PORCENTAJE_RECHAZOS_AFILIACIONES_EMPLEADORES') ON dic.dicDescripcion = vmpBR.vmpCanal and dip.dipAnio = mpkBR.mpkAnio and dip.dipMes = vmpBR.vmpPeriodo
		LEFT JOIN (	ValorMetaPeriodoCanalKPI vmpTR
					INNER JOIN MetaPeriodoKPI mpkTR ON vmpTR.vmpMetaPeriodoKPI = mpkTR.mpkId AND mpkTR.mpkFrecuencia = 'Trimestral' AND mpkTR.mpkMeta = 'PORCENTAJE_RECHAZOS_AFILIACIONES_EMPLEADORES') ON dic.dicDescripcion = vmpTR.vmpCanal and dip.dipAnio = mpkTR.mpkAnio and dip.dipMes = vmpTR.vmpPeriodo
		LEFT JOIN (	ValorMetaPeriodoCanalKPI vmpSR
					INNER JOIN MetaPeriodoKPI mpkSR ON vmpSR.vmpMetaPeriodoKPI = mpkSR.mpkId AND mpkSR.mpkFrecuencia = 'Semestral' AND mpkSR.mpkMeta = 'PORCENTAJE_RECHAZOS_AFILIACIONES_EMPLEADORES') ON dic.dicDescripcion = vmpSR.vmpCanal and dip.dipAnio = mpkM.mpkAnio and dip.dipMes = vmpSR.vmpPeriodo
		LEFT JOIN (	ValorMetaPeriodoCanalKPI vmpAR
					INNER JOIN MetaPeriodoKPI mpkAR ON vmpAR.vmpMetaPeriodoKPI = mpkAR.mpkId AND mpkAR.mpkFrecuencia = 'Anual' AND mpkAR.mpkMeta = 'PORCENTAJE_RECHAZOS_AFILIACIONES_EMPLEADORES') ON dic.dicDescripcion = vmpAR.vmpCanal and dip.dipAnio = mpkAR.mpkAnio and dip.dipMes = vmpAR.vmpPeriodo

		LEFT JOIN (	ValorMetaPeriodoCanalKPI vmpM1
					INNER JOIN MetaPeriodoKPI mpkM1 ON vmpM1.vmpMetaPeriodoKPI = mpkM1.mpkId AND mpkM1.mpkFrecuencia = 'Mensual' AND mpkM1.mpkMeta = 'PORCENTAJE_AFILIACIONES_PERSONAS') ON dic.dicDescripcion = vmpM1.vmpCanal and dip.dipAnio = mpkM1.mpkAnio and dip.dipMes = vmpM1.vmpPeriodo
		LEFT JOIN (	ValorMetaPeriodoCanalKPI vmpB1
					INNER JOIN MetaPeriodoKPI mpkB1 ON vmpB1.vmpMetaPeriodoKPI = mpkB1.mpkId AND mpkB1.mpkFrecuencia = 'Bimensual' AND mpkB1.mpkMeta = 'PORCENTAJE_AFILIACIONES_PERSONAS') ON dic.dicDescripcion = vmpB1.vmpCanal and dip.dipAnio = mpkB1.mpkAnio and dip.dipMes = vmpB1.vmpPeriodo
		LEFT JOIN (	ValorMetaPeriodoCanalKPI vmpT1
					INNER JOIN MetaPeriodoKPI mpkT1 ON vmpT1.vmpMetaPeriodoKPI = mpkT1.mpkId AND mpkT1.mpkFrecuencia = 'Trimestral' AND mpkT1.mpkMeta = 'PORCENTAJE_AFILIACIONES_PERSONAS') ON dic.dicDescripcion = vmpT1.vmpCanal and dip.dipAnio = mpkT1.mpkAnio and dip.dipMes = vmpT1.vmpPeriodo
		LEFT JOIN (	ValorMetaPeriodoCanalKPI vmpS1
					INNER JOIN MetaPeriodoKPI mpkS1 ON vmpS1.vmpMetaPeriodoKPI = mpkS1.mpkId AND mpkS1.mpkFrecuencia = 'Semestral' AND mpkS1.mpkMeta = 'PORCENTAJE_AFILIACIONES_PERSONAS') ON dic.dicDescripcion = vmpS1.vmpCanal and dip.dipAnio = mpkM1.mpkAnio and dip.dipMes = vmpS1.vmpPeriodo
		LEFT JOIN (	ValorMetaPeriodoCanalKPI vmpA1
					INNER JOIN MetaPeriodoKPI mpkA1 ON vmpA1.vmpMetaPeriodoKPI = mpkA1.mpkId AND mpkA1.mpkFrecuencia = 'Anual' AND mpkA1.mpkMeta = 'PORCENTAJE_AFILIACIONES_PERSONAS') ON dic.dicDescripcion = vmpA1.vmpCanal and dip.dipAnio = mpkA1.mpkAnio and dip.dipMes = vmpA1.vmpPeriodo

		LEFT JOIN (	ValorMetaPeriodoCanalKPI vmpMR1
					INNER JOIN MetaPeriodoKPI mpkMR1 ON vmpMR1.vmpMetaPeriodoKPI = mpkMR1.mpkId AND mpkMR1.mpkFrecuencia = 'Mensual' AND mpkMR1.mpkMeta = 'PORCENTAJE_RECHAZOS_AFILIACIONES_PERSONAS') ON dic.dicDescripcion = vmpMR1.vmpCanal and dip.dipAnio = mpkMR1.mpkAnio and dip.dipMes = vmpMR1.vmpPeriodo
		LEFT JOIN (	ValorMetaPeriodoCanalKPI vmpBR1
					INNER JOIN MetaPeriodoKPI mpkBR1 ON vmpBR1.vmpMetaPeriodoKPI = mpkBR1.mpkId AND mpkBR1.mpkFrecuencia = 'Bimensual' AND mpkBR1.mpkMeta = 'PORCENTAJE_RECHAZOS_AFILIACIONES_PERSONAS') ON dic.dicDescripcion = vmpBR1.vmpCanal and dip.dipAnio = mpkBR1.mpkAnio and dip.dipMes = vmpBR1.vmpPeriodo
		LEFT JOIN (	ValorMetaPeriodoCanalKPI vmpTR1
					INNER JOIN MetaPeriodoKPI mpkTR1 ON vmpTR1.vmpMetaPeriodoKPI = mpkTR1.mpkId AND mpkTR1.mpkFrecuencia = 'Trimestral' AND mpkTR1.mpkMeta = 'PORCENTAJE_RECHAZOS_AFILIACIONES_PERSONAS') ON dic.dicDescripcion = vmpTR1.vmpCanal and dip.dipAnio = mpkTR1.mpkAnio and dip.dipMes = vmpTR1.vmpPeriodo
		LEFT JOIN (	ValorMetaPeriodoCanalKPI vmpSR1
					INNER JOIN MetaPeriodoKPI mpkSR1 ON vmpSR1.vmpMetaPeriodoKPI = mpkSR1.mpkId AND mpkSR1.mpkFrecuencia = 'Semestral' AND mpkSR1.mpkMeta = 'PORCENTAJE_RECHAZOS_AFILIACIONES_PERSONAS') ON dic.dicDescripcion = vmpSR1.vmpCanal and dip.dipAnio = mpkM1.mpkAnio and dip.dipMes = vmpSR1.vmpPeriodo
		LEFT JOIN (	ValorMetaPeriodoCanalKPI vmpAR1
					INNER JOIN MetaPeriodoKPI mpkAR1 ON vmpAR1.vmpMetaPeriodoKPI = mpkAR1.mpkId AND mpkAR1.mpkFrecuencia = 'Anual' AND mpkAR1.mpkMeta = 'PORCENTAJE_RECHAZOS_AFILIACIONES_PERSONAS') ON dic.dicDescripcion = vmpAR1.vmpCanal and dip.dipAnio = mpkAR1.mpkAnio and dip.dipMes = vmpAR1.vmpPeriodo
	)

	MERGE DimMetasCanalPeriodo AS T
	USING cte AS S
	ON (T.dmcDimCanal = S.dmcDimCanal AND T.dmcDimPeriodo = S.dmcDimPeriodo)
	WHEN NOT MATCHED BY TARGET
		THEN INSERT(dmcDimCanal,dmcDimPeriodo,dmcMetaAfiEmpMes,dmcMetaAfiEmpBimensual,dmcMetaAfiEmpTrimestral,dmcMetaAfiEmpSemestral,dmcMetaAfiEmpAnual,dmcMetaRechEmpMes,dmcMetaRechEmpBimensual,dmcMetaRechEmpTrimestral,dmcMetaRechEmpSemestral,dmcMetaRechEmpAnual,dmcMetaAfiPerMes,dmcMetaAfiPerBimensual,dmcMetaAfiPerTrimestral,dmcMetaAfiPerSemestral,dmcMetaAfiPerAnual,dmcMetaRechPerMes,dmcMetaRechPerBimensual,dmcMetaRechPerTrimestral,dmcMetaRechPerSemestral,dmcMetaRechPerAnual) 
		VALUES(S.dmcDimCanal,S.dmcDimPeriodo,S.dmcMetaAfiEmpMes,S.dmcMetaAfiEmpBimensual,S.dmcMetaAfiEmpTrimestral,S.dmcMetaAfiEmpSemestral,S.dmcMetaAfiEmpAnual,S.dmcMetaRechEmpMes,S.dmcMetaRechEmpBimensual,S.dmcMetaRechEmpTrimestral,S.dmcMetaRechEmpSemestral,S.dmcMetaRechEmpAnual,S.dmcMetaAfiPerMes,S.dmcMetaAfiPerBimensual,S.dmcMetaAfiPerTrimestral,S.dmcMetaAfiPerSemestral,S.dmcMetaAfiPerAnual,S.dmcMetaRechPerMes,S.dmcMetaRechPerBimensual,S.dmcMetaRechPerTrimestral,S.dmcMetaRechPerSemestral,S.dmcMetaRechPerAnual)
	WHEN MATCHED
		THEN UPDATE SET
			dmcMetaAfiEmpMes = S.dmcMetaAfiEmpMes,
			dmcMetaAfiEmpBimensual = S.dmcMetaAfiEmpBimensual,
			dmcMetaAfiEmpTrimestral = S.dmcMetaAfiEmpTrimestral,
			dmcMetaAfiEmpSemestral = S.dmcMetaAfiEmpSemestral,
			dmcMetaAfiEmpAnual = S.dmcMetaAfiEmpAnual,
			dmcMetaRechEmpMes = S.dmcMetaRechEmpMes,
			dmcMetaRechEmpBimensual = S.dmcMetaRechEmpBimensual,
			dmcMetaRechEmpTrimestral = S.dmcMetaRechEmpTrimestral,
			dmcMetaRechEmpSemestral = S.dmcMetaRechEmpSemestral,
			dmcMetaRechEmpAnual = S.dmcMetaRechEmpAnual,

			dmcMetaAfiPerMes = S.dmcMetaAfiPerMes,
			dmcMetaAfiPerBimensual = S.dmcMetaAfiPerBimensual,
			dmcMetaAfiPerTrimestral = S.dmcMetaAfiPerTrimestral,
			dmcMetaAfiPerSemestral = S.dmcMetaAfiPerSemestral,
			dmcMetaAfiPerAnual = S.dmcMetaAfiPerAnual,
			dmcMetaRechPerMes = S.dmcMetaRechPerMes,
			dmcMetaRechPerBimensual = S.dmcMetaRechPerBimensual,
			dmcMetaRechPerTrimestral = S.dmcMetaRechPerTrimestral,
			dmcMetaRechPerSemestral = S.dmcMetaRechPerSemestral,
			dmcMetaRechPerAnual = S.dmcMetaRechPerAnual;

;