--liquibase formatted sql

--changeset arocha:01 runOnChange:true
--Creación del Function [dbo].[Split]


/****** Object:  UserDefinedFunction [dbo].[Split]    Script Date: 23/06/2017 11:58:48 a.m. ******/
IF (OBJECT_ID('FN_CategoriaAfiliado') IS NOT NULL)
	DROP FUNCTION [dbo].[FN_CategoriaAfiliado]
GO

/****** Object:  UserDefinedFunction [dbo].[Split]    Script Date: 23/06/2017 11:58:48 a.m. ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:      Diego Suesca
-- Create date: 2020/11/20
-- Description: Calcula la categoría actual de los afiliados
-- =============================================


CREATE FUNCTION FN_CategoriaAfiliado (
    @perId BIGINT
)
RETURNS TABLE
AS
RETURN

    WITH cteSalarioTipoCotizanteActivos
    AS
    (
        SELECT roa.roaAfiliado AS afiliado, afi.afiPersona AS persona, roa.roaTipoAfiliado AS tipoCotizante, SUM(roa.roaValorSalarioMesadaIngresos) AS salario, 'ACTIVO' AS estadoAfiliacion, MAX(afi.afifechaFinServicioSinAfiliacion) AS fechaFinServicioSinAfiliacion,MAX(roa.roaFechaAfiliacion) maxFechaAfiliacion
        FROM dbo.RolAfiliado roa
        INNER JOIN dbo.Afiliado afi ON roa.roaAfiliado = afi.afiId
        WHERE roa.roaEstadoAfiliado = 'ACTIVO'
        GROUP BY roa.roaAfiliado, afi.afiPersona, roa.roaTipoAfiliado 
    ),
    
    cteSalarioTipoCotizanteActivosDiscriminados
    AS
    (
        SELECT roa.roaAfiliado AS afiliado, afi.afiPersona AS persona, emp.empEmpresa as empresa, roa.roaTipoAfiliado AS tipoCotizante, roa.roaValorSalarioMesadaIngresos AS salario, 'ACTIVO' AS estadoAfiliacion, afi.afifechaFinServicioSinAfiliacion AS fechaFinServicioSinAfiliacion, roa.roaFechaAfiliacion AS maxFechaAfiliacion
        FROM dbo.RolAfiliado roa
        INNER JOIN dbo.Afiliado afi ON roa.roaAfiliado = afi.afiId
        INNER JOIN dbo.Empleador emp ON roa.roaEmpleador = emp.empId
        WHERE roa.roaEstadoAfiliado = 'ACTIVO'
    ),
    
    cteSalarioTipoCotizanteInactivos
    AS
    (
        SELECT roa.roaAfiliado AS afiliado, afi.afiPersona AS persona, roa.roaTipoAfiliado AS tipoCotizante, SUM(roa.roaValorSalarioMesadaIngresos) AS salario, 'INACTIVO' AS estadoAfiliacion, MAX(afi.afifechaFinServicioSinAfiliacion) AS fechaFinServicioSinAfiliacion,MAX(roa.roaFechaAfiliacion) maxFechaAfiliacion
        FROM dbo.RolAfiliado roa
        INNER JOIN dbo.Afiliado afi ON roa.roaAfiliado = afi.afiId
        WHERE ISNULL(roa.roaEstadoAfiliado,'INACTIVO') = 'INACTIVO' AND
            NOT EXISTS 
            (
                SELECT 1 
                FROM dbo.RolAfiliado roa1 
                WHERE roa.roaAfiliado = roa1.roaAfiliado 
                AND roa1.roaTipoAfiliado = roa.roaTipoAfiliado 
                AND roa1.roaEstadoAfiliado = 'ACTIVO'
            )
        GROUP BY roa.roaAfiliado, afi.afiPersona, roa.roaTipoAfiliado 
    ),
    cteSalarioTipoCotizanteAll
    AS
    (
        SELECT afiliado, persona, tipoCotizante, salario, estadoAfiliacion, fechaFinServicioSinAfiliacion, maxFechaAfiliacion
        FROM cteSalarioTipoCotizanteActivos
        UNION ALL
        SELECT afiliado, persona, tipoCotizante, salario, estadoAfiliacion, fechaFinServicioSinAfiliacion, maxFechaAfiliacion
        FROM cteSalarioTipoCotizanteInactivos
    ),
    /*
    cteSalarioTipoCotizanteSinAportes
    AS
    (
        SELECT afiliado, persona, tipoCotizante, salario, estadoAfiliacion, fechaFinServicioSinAfiliacion
        FROM cteSalarioTipoCotizanteAll cte
        WHERE NOT EXISTS 
            (
                SELECT 1
                FROM AporteDetallado apd
                INNER JOIN AporteGeneral apg ON apd.apdAporteGeneral = apg.apgId
                WHERE apd.apdPersona = cte.persona
                AND apg.apgEmpresa IS NULL
                AND apd.apdEstadoRegistroAporteArchivo = 'OK'
                AND apd.apdEstadoAporteRecaudo = 'VIGENTE'
                AND apd.apdTipoCotizante = cte.tipoCotizante
            ) AND
            NOT EXISTS
            (
                SELECT 1
                FROM AporteDetallado apd
                INNER JOIN AporteGeneral apg ON apd.apdAporteGeneral = apg.apgId
                WHERE apd.apdPersona = cte.persona
                AND apg.apgEmpresa IS NOT NULL
                AND apd.apdEstadoRegistroAporteArchivo = 'OK'
                AND apd.apdEstadoAporteRecaudo = 'VIGENTE'
                AND apd.apdTipoCotizante = cte.tipoCotizante
            )
    ),
    */
    cteSalarioTipoCotizanteAportes
    AS
    (
        SELECT empresa, tipoCotizante, persona, periodoAporte, SUM(salario) AS salarioMaxEmpleador,
                CAST(MAX(AporteEmpleadorNoAfiliado) AS BIT) AS aporteEmpleadorNoAfiliado
                --,MAX(fechaRecaudo) AS fechaRecaudo
        FROM (
            SELECT apg.apgEmpresa empresa,
                    apd.apdTipoCotizante AS tipoCotizante,
                    apg.apgRegistroGeneral,
                    apd.apdPersona AS persona,
                    apg.apgPeriodoAporte AS periodoAporte,
                    MAX(apd.apdSalarioBasico) salario,
                    --MAX(apg.apgFechaRecaudo) fechaRecaudo,
                CASE WHEN ISNULL(empl.empEstadoEmpleador,'INACTIVO') = 'INACTIVO' THEN 1 ELSE 0 END AS AporteEmpleadorNoAfiliado
            FROM dbo.AporteDetallado apd
            INNER JOIN dbo.AporteGeneral apg ON (apg.apgId = apd.apdAporteGeneral)
            LEFT JOIN dbo.Empleador empl ON apg.apgEmpresa = empl.empEmpresa
            WHERE apd.apdPersona = @perId
              AND apd.apdEstadoRegistroAporteArchivo = 'OK'
                AND apd.apdEstadoAporteRecaudo = 'VIGENTE'
                AND apd.apdMarcaPeriodo IN ('PERIODO_REGULAR','PERIODO_FUTURO')
                AND CAST(apg.apgPeriodoAporte+'-01' AS DATE) <= 
                --dbo.GetPeriodoRegular(apd.apdTipoCotizante,CAST(dbo.GetLocalDate() AS DATE))
                -- se cambia el código de la función a CASE por rendimiento
                CASE WHEN apd.apdTipoCotizante = 'TRABAJADOR_INDEPENDIENTE' OR apd.apdTipoCotizante = 'PENSIONADO' 
                    THEN DATEFROMPARTS(YEAR(dbo.GetLocalDate()),MONTH(dbo.GetLocalDate()),1)
                ELSE
                    DATEFROMPARTS(YEAR(DATEADD(MONTH,-1,dbo.GetLocalDate())),MONTH(DATEADD(MONTH,-1,dbo.GetLocalDate())),1)
                END
            GROUP BY apg.apgEmpresa, apd.apdTipoCotizante, apg.apgRegistroGeneral, apd.apdPersona, apg.apgPeriodoAporte,
                     CASE WHEN ISNULL(empl.empEstadoEmpleador,'INACTIVO') = 'INACTIVO' THEN 1 ELSE 0 END
        ) as p
        GROUP BY empresa, persona, tipoCotizante, periodoAporte
    ),
    cteMAXPeriodoAportePersona AS
    (
        SELECT  persona, tipoCotizante, MAX(periodoAporte) periodoAporte
        FROM cteSalarioTipoCotizanteAportes
        GROUP BY persona, tipoCotizante, aporteEmpleadorNoAfiliado
    ),

    cteSalarioTipoCotizanteAux AS
    (
        SELECT afi.afiId AS afiliado,
                afi.afiPersona AS persona,
                sal.tipoCotizante,
                SUM(sal.salarioMaxEmpleador) + (CASE WHEN b.salario IS NOT NULL THEN b.salario ELSE 0 END) AS salario,
                cte.estadoAfiliacion,
                cte.fechaFinServicioSinAfiliacion,
                sal.aporteEmpleadorNoAfiliado,
                DATEADD(MONTH,1, CAST(max(maxP.periodoAporte)+'-01' AS DATE)) maxFechaAporte,
                cte.maxFechaAfiliacion,
                cte.salario salarioAfiliacion
        FROM cteMAXPeriodoAportePersona maxP
        INNER JOIN cteSalarioTipoCotizanteAportes sal ON maxP.persona = sal.persona
                                                    AND maxP.tipoCotizante = sal.tipoCotizante
                                                    AND maxP.periodoAporte = sal.periodoAporte
        INNER JOIN dbo.Afiliado afi ON sal.persona = afi.afiPersona
        INNER JOIN cteSalarioTipoCotizanteAll cte ON maxP.persona = cte.persona
                                                AND maxP.tipoCotizante = cte.tipocotizante
        LEFT JOIN (
            SELECT 
                stc.persona as persona,
                stc.empresa as empresa,
                SUM(stc.salario) AS salario
            FROM cteSalarioTipoCotizanteActivosDiscriminados stc
            LEFT JOIN cteSalarioTipoCotizanteAportes sta ON (stc.persona = sta.persona AND stc.empresa = sta.empresa)
            where sta.persona is null and sta.empresa is null
            group by stc.persona, stc.empresa
            ) as b on (b.persona = afi.afiPersona)
        GROUP BY afi.afiId, afi.afiPersona, sal.tipoCotizante, estadoAfiliacion, fechaFinServicioSinAfiliacion,
        sal.aporteEmpleadorNoAfiliado,cte.maxFechaAfiliacion,cte.salario, b.salario

    ),
    
    
    cteSalarioTipoCotizante AS
    (
        SELECT afiliado,persona,tipoCotizante,
        CASE WHEN maxFechaAporte = DATEFROMPARTS(YEAR(maxFechaAfiliacion),MONTH(maxFechaAfiliacion),1) THEN salario
            WHEN maxFechaAporte < maxFechaAfiliacion THEN salarioAfiliacion
            WHEN maxFechaAporte > maxFechaAfiliacion THEN salario
        END salario,
        estadoAfiliacion, fechaFinServicioSinAfiliacion, aporteEmpleadorNoAfiliado
        FROM cteSalarioTipoCotizanteAux cte

        UNION ALL 
        SELECT afiliado, persona, tipoCotizante, salario, estadoAfiliacion, fechaFinServicioSinAfiliacion,
        CAST(0 AS BIT) AS aporteEmpleadorNoAfiliado
        FROM cteSalarioTipoCotizanteAll cte
        WHERE NOT EXISTS 
        (SELECT 1
         FROM cteMAXPeriodoAportePersona
         WHERE persona = cte.persona
           AND tipoCotizante = cte.tipoCotizante)
    )
    
    
    SELECT t.afiliado,
    t.persona,
    per.perTipoIdentificacion AS tipoIdentificacion,
    per.perNumeroIdentificacion AS numeroIdentificacion,
    t.tipoCotizante,
    sol.solClasificacion AS clasificacion,
    t.salario,
    t.estadoAfiliacion,
    t.fechaFinServicioSinAfiliacion,
    t.aporteEmpleadorNoAfiliado,
    dbo.GetCategoria(t.tipoCotizante, sol.solClasificacion, t.salario, t.estadoAfiliacion,
                        t.fechaFinServicioSinAfiliacion,salarioMinimo.prmValor) AS categoria
    FROM (SELECT prmValor FROM Parametro WHERE prmNombre = 'SMMLV') salarioMinimo,
    cteSalarioTipoCotizante t
    INNER JOIN (    SELECT MAX(sapSolicitudGlobal) sapSolicitudGlobal, roaAfiliado, roaTipoAfiliado
                    FROM SolicitudAfiliacionPersona sap
                    INNER JOIN RolAfiliado roa ON sap.sapRolAfiliado = roa.roaId
                    WHERE sapEstadoSolicitud <> 'PRE_RADICADA'
                    GROUP BY roaAfiliado, roaTipoAfiliado) sap ON t.afiliado = sap.roaAfiliado
                                                                AND t.tipoCotizante = sap.roaTipoAfiliado
    INNER JOIN Solicitud sol ON sap.sapSolicitudGlobal = sol.solId
    INNER JOIN Persona per ON t.persona = per.perId
    WHERE persona = @perId
GO