-- =============================================
-- Author: Robinson Castillo
-- Create date: 2024-02-12
-- Description:	Proceso encargado de limpiar una planilla
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_ReprocesarPlanillaM1]
	@pipId varchar(max)
AS

set nocount on;

BEGIN


    SET XACT_ABORT ON;
	SET NOCOUNT ON;
	SET QUOTED_IDENTIFIER ON;

    BEGIN TRY

	drop table if exists #procesarId

	create table #procesarId (pipId bigint)

	select @pipId = left(@pipId, len(@pipId) - 1)
	insert #procesarId select value from string_split(@pipId, ',');


	IF (select count(*) from #procesarId) > 0
        BEGIN

		;with pla as (
		select pipIdPlanilla, pipCodigoOperadorInformacion, pipId
		from dbo.pilaIndicePlanilla with (nolock)
		where pipId in (select a.pipId from #procesarId as a  where a.pipId is not null)
		and isnull(pipEstadoArchivo,'') not in ('ANULADO','RECAUDO_NOTIFICADO'))
		select p.pipId
		into #PlanillasProc
		from pla 
		inner join dbo.pilaIndicePlanilla as p on p.pipIdPlanilla = pla.pipIdPlanilla and p.pipCodigoOperadorInformacion = pla.pipCodigoOperadorInformacion
		and isnull(pipEstadoArchivo,'') not in ('ANULADO','RECAUDO_NOTIFICADO')
        
            SELECT p.pipId, p.pipIdPlanilla, p.pipFechaRecibo, p.pipNombreArchivo
            INTO #IndicePlanillaArchivoI
            FROM PilaIndicePlanilla p with (nolock)
            INNER JOIN PilaEstadoBloque e with (nolock) ON p.pipId = e.pebIndicePlanilla
            WHERE 1= 1
			and p.pipId in (select pipId from #PlanillasProc)
            AND p.pipTipoArchivo IN ('ARCHIVO_OI_I', 'ARCHIVO_OI_IP','ARCHIVO_OI_IR', 'ARCHIVO_OI_IPR')
            AND isnull(p.pipEstadoArchivo,'') NOT IN ('ANULADO','RECAUDO_NOTIFICADO')
            AND (
                pipTipoCargaArchivo != 'AUTOMATICA_WEB' OR
                (pipTipoCargaArchivo = 'AUTOMATICA_WEB' AND isnull(p.pipEstadoArchivo,'') != 'DESCARGADO')
                )
			and e.pebEstadoBloque6 is null
            AND (e.pebAccionBloque9 IS NULL AND e.pebEstadoBloque10 IS NULL AND e.pebAccionBloque10 IS NULL)
            ORDER BY p.pipDateTimeUpdate ASC

            -- Validación pesimista de ejecución de bloques siguientes
            DELETE i 
            FROM #IndicePlanillaArchivoI i
            INNER JOIN HistorialEstadoBloque h with (nolock) ON i.pipId = h.hebIdIndicePlanilla
            --WHERE hebBloque IN ('BLOQUE_7_OI', 'BLOQUE_8_OI', 'BLOQUE_9_OI', 'BLOQUE_10_OI')
            WHERE hebBloque IN ('BLOQUE_9_OI', 'BLOQUE_10_OI')

            SELECT  p.pipId, p.pipIdPlanilla, p.pipFechaRecibo, p.pipNombreArchivo, i.pipId AS pipIdArchivoI
            INTO #IndicePlanillaArchivoAtmp
            FROM PilaIndicePlanilla p with (nolock)
            INNER JOIN PilaEstadoBloque e with (nolock) ON p.pipId = e.pebIndicePlanilla
            INNER JOIN #IndicePlanillaArchivoI i ON (i.pipIdPlanilla = p.pipIdPlanilla AND REPLACE(i.pipNombreArchivo,'_I','_A') = p.pipNombreArchivo)
            WHERE 1= 1
            AND p.pipTipoArchivo IN ('ARCHIVO_OI_A', 'ARCHIVO_OI_AP', 'ARCHIVO_OI_AR', 'ARCHIVO_OI_APR')
            AND isnull(p.pipEstadoArchivo,'') NOT IN ('ANULADO','RECAUDO_NOTIFICADO')
            AND (
                p.pipTipoCargaArchivo != 'AUTOMATICA_WEB' OR
                (p.pipTipoCargaArchivo = 'AUTOMATICA_WEB' AND isnull(p.pipEstadoArchivo,'') != 'DESCARGADO')
                )
            AND (e.pebEstadoBloque10 IS NULL AND e.pebAccionBloque10 IS NULL)
            
            SELECT * 
            INTO #IndicePlanillaArchivoA
            FROM #IndicePlanillaArchivoAtmp

            SELECT d.pipIdArchivoI 
            INTO #Duplicadas
            FROM (
                SELECT pipIdArchivoI, count(pipIdArchivoI) AS conteo
                FROM #IndicePlanillaArchivoA
                GROUP BY pipIdArchivoI
            ) AS d
            WHERE d.conteo > 1

            -- Si se encuentra mas de un archivo A para un I determinado se determina que no se puede reprocesar
            DELETE a 
            FROM #IndicePlanillaArchivoA a
            INNER JOIN #Duplicadas d ON d.pipIdArchivoI = a.pipIdArchivoI
            
            -- REINICIAR F
            SELECT d.numPlanilla, d.periodo, d.codOperador
            INTO #CriteriosDatosF
            FROM (
                SELECT pi1.pi1NumPlanilla AS numPlanilla, replace(pi1.pi1PeriodoAporte, '-', '') AS periodo, pi1.pi1CodOperador AS codOperador
                FROM PilaArchivoIRegistro1 pi1 with (nolock)
                INNER JOIN #IndicePlanillaArchivoA a ON pi1.pi1IndicePlanilla = a.pipIdArchivoI

                UNION
                
                SELECT ip1.ip1NumPlanilla AS numPlanilla, replace(ip1.ip1PeriodoAporte, '-', '') AS periodo, ip1.ip1CodOperador AS codOperador
                FROM PilaArchivoIPRegistro1 ip1 with (nolock)
                INNER JOIN #IndicePlanillaArchivoA a ON ip1.ip1IndicePlanilla = a.pipIdArchivoI
            ) d

            SELECT r6.pf6Id, r6.pf6IndicePlanillaOF
            INTO #DatosF
            FROM dbo.PilaArchivoFRegistro6 r6 with (nolock)
            INNER JOIN #CriteriosDatosF c ON (
                CAST(r6.pf6NumeroPlanilla AS BIGINT) = CAST(c.numPlanilla AS BIGINT)
                AND r6.pf6PeriodoPago = c.periodo
                AND r6.pf6CodOperadorInformacion = c.codOperador
                AND r6.pf6EstadoConciliacion != 'REGISTRO_6_ANULADO')

            BEGIN TRAN

                -- REINICIO DEL REGISTRO F
                UPDATE r6
                SET pf6EstadoConciliacion = NULL
                FROM dbo.PilaArchivoFRegistro6 r6 
                INNER JOIN #DatosF d ON r6.pf6Id = d.pf6Id

                UPDATE pio
                SET pio.pioEstadoArchivo = 'ARCHIVO_FINANCIERO_PENDIENTE_CONCILIACION'
                FROM dbo.PilaIndicePlanillaOF pio 
                INNER JOIN #DatosF d ON pio.pioId = d.pf6IndicePlanillaOF

                -- BORRAR Datos control
                DELETE ppv
                FROM PilaPasoValores ppv 
                INNER JOIN #IndicePlanillaArchivoA a ON ppv.ppvIdPlanilla = a.pipIdPlanilla

                -- BORRAR Archivo I
                DELETE tpr
                FROM TemAporteProcesado tpr 
                INNER JOIN staging.RegistroGeneral reg ON tpr.tprAporteGeneral = reg.regId
                INNER JOIN #IndicePlanillaArchivoA a ON reg.regRegistroControl = a.pipIdArchivoI
                
                DELETE tem
                FROM TemAporte tem
                INNER JOIN staging.RegistroDetallado red ON tem.temIdTransaccion = red.redId
                INNER JOIN staging.RegistroGeneral reg ON red.redRegistroGeneral = reg.regId
                INNER JOIN #IndicePlanillaArchivoA a ON reg.regRegistroControl = a.pipIdArchivoI
                
                DELETE tap
                FROM TemAportante tap
                INNER JOIN staging.RegistroDetallado red ON tap.tapIdTransaccion = red.redId
                INNER JOIN staging.RegistroGeneral reg ON red.redRegistroGeneral = reg.regId
                INNER JOIN #IndicePlanillaArchivoA a ON reg.regRegistroControl = a.pipIdArchivoI
                
                DELETE tct
                FROM TemCotizante tct
                INNER JOIN staging.RegistroDetallado red ON tct.tctIdTransaccion = red.redId
                INNER JOIN staging.RegistroGeneral reg ON red.redRegistroGeneral = reg.regId
                INNER JOIN #IndicePlanillaArchivoA a ON reg.regRegistroControl = a.pipIdArchivoI
                
                DELETE ten
                FROM TemNovedad ten
                INNER JOIN staging.RegistroDetallado red ON ten.tenRegistroDetallado = red.redId
                INNER JOIN staging.RegistroGeneral reg ON red.redRegistroGeneral = reg.regId
                INNER JOIN #IndicePlanillaArchivoA a ON reg.regRegistroControl = a.pipIdArchivoI
                
                DELETE rai
                FROM staging.RegistroAfectacionAnalisisIntegral rai
                INNER JOIN staging.RegistroDetallado red ON rai.raiRegistroDetalladoAfectado = red.redId
                INNER JOIN staging.RegistroGeneral reg ON red.redRegistroGeneral = reg.regId
                INNER JOIN #IndicePlanillaArchivoA a ON reg.regRegistroControl = a.pipIdArchivoI
                
                DELETE rdn
                FROM staging.RegistroDetalladoNovedad rdn
                INNER JOIN staging.RegistroDetallado red ON rdn.rdnRegistroDetallado = red.redId
                INNER JOIN staging.RegistroGeneral reg ON red.redRegistroGeneral = reg.regId
                INNER JOIN #IndicePlanillaArchivoA a ON reg.regRegistroControl = a.pipIdArchivoI

                DELETE red
                FROM staging.RegistroDetallado red
                INNER JOIN staging.RegistroGeneral reg ON red.redRegistroGeneral = reg.regId
                INNER JOIN #IndicePlanillaArchivoA a ON reg.regRegistroControl = a.pipIdArchivoI

                DELETE reg
                FROM staging.RegistroGeneral reg 
                INNER JOIN #IndicePlanillaArchivoA a ON reg.regRegistroControl = a.pipIdArchivoI

                DELETE pev 
                FROM PilaErrorValidacionLog pev
                INNER JOIN #IndicePlanillaArchivoA a ON pev.pevIndicePlanilla = a.pipIdArchivoI

                DELETE psc
                FROM PilaSolicitudCambioNumIdentAportante psc 
                INNER JOIN #IndicePlanillaArchivoA a ON psc.pscPilaIndicePlanilla = a.pipIdArchivoI;

                DELETE heb
                FROM HistorialEstadoBloque heb 
                INNER JOIN #IndicePlanillaArchivoA a ON heb.hebIdIndicePlanilla = a.pipIdArchivoI;
                
                DELETE pi1
                FROM dbo.PilaArchivoIRegistro1 pi1 
                INNER JOIN #IndicePlanillaArchivoA a ON pi1IndicePlanilla = a.pipIdArchivoI;

                DELETE pi2
                FROM dbo.PilaArchivoIRegistro2 pi2
                INNER JOIN #IndicePlanillaArchivoA a ON pi2IndicePlanilla = a.pipIdArchivoI;

                DELETE pi3
                FROM dbo.PilaArchivoIRegistro3 pi3
                INNER JOIN #IndicePlanillaArchivoA a ON pi3IndicePlanilla = a.pipIdArchivoI;

                DELETE ip1
                FROM dbo.PilaArchivoIPRegistro1 ip1
                INNER JOIN #IndicePlanillaArchivoA a ON ip1IndicePlanilla = a.pipIdArchivoI;
                
                DELETE ip2
                FROM dbo.PilaArchivoIPRegistro2 ip2
                INNER JOIN #IndicePlanillaArchivoA a ON ip2IndicePlanilla = a.pipIdArchivoI;
                
                DELETE ip3
                FROM dbo.PilaArchivoIPRegistro3 ip3
                INNER JOIN #IndicePlanillaArchivoA a ON ip3IndicePlanilla = a.pipIdArchivoI;
                
                DELETE pap
                FROM staging.PreliminarArchivoPila pap
                INNER JOIN #IndicePlanillaArchivoA a ON pap.papIndicePlanilla = a.pipIdArchivoI;

                UPDATE peb
                SET peb.pebAccionBloque0 = 'EJECUTAR_BLOQUE_1', 
                    peb.pebEstadoBloque1 = NULL, peb.pebAccionBloque1 = NULL, peb.pebFechaBloque1 = NULL, 
                    peb.pebEstadoBloque2 = NULL, peb.pebAccionBloque2 = NULL, peb.pebFechaBloque2 = NULL, 
                    peb.pebEstadoBloque3 = NULL, peb.pebAccionBloque3 = NULL, peb.pebFechaBloque3 = NULL, 
                    peb.pebEstadoBloque4 = NULL, peb.pebAccionBloque4 = NULL, peb.pebFechaBloque4 = NULL, 
                    peb.pebEstadoBloque5 = NULL, peb.pebAccionBloque5 = NULL, peb.pebFechaBloque5 = NULL, 
                    peb.pebEstadoBloque6 = NULL, peb.pebAccionBloque6 = NULL, peb.pebFechaBloque6 = NULL  -- hasta este punto se habian excluido las de bloque 7 en adelante
                FROM PilaEstadoBloque peb
                INNER JOIN #IndicePlanillaArchivoA a ON peb.pebIndicePlanilla = a.pipIdArchivoI;
                
                UPDATE pip
                SET pip.pipEstadoArchivo = 'CARGADO',
                	pip.pipDateTimeUpdate = dbo.getLocalDate()
                FROM pilaIndicePlanilla pip 
                INNER JOIN #IndicePlanillaArchivoA a ON pip.pipid = a.pipIdArchivoI;

                -- BORRAR Archivo A
                DELETE pev 
                FROM PilaErrorValidacionLog pev
                INNER JOIN #IndicePlanillaArchivoA a ON pev.pevIndicePlanilla = a.pipId

                DELETE psc
                FROM PilaSolicitudCambioNumIdentAportante psc 
                INNER JOIN #IndicePlanillaArchivoA a ON psc.pscPilaIndicePlanilla = a.pipId;
                
                DELETE heb
                FROM HistorialEstadoBloque heb 
                INNER JOIN #IndicePlanillaArchivoA a ON heb.hebIdIndicePlanilla = a.pipId;

                DELETE pa1
                FROM dbo.PilaArchivoARegistro1 pa1
                INNER JOIN #IndicePlanillaArchivoA a ON pa1IndicePlanilla = a.pipId;

                DELETE ap1
                FROM dbo.PilaArchivoAPRegistro1 ap1
                INNER JOIN #IndicePlanillaArchivoA a ON ap1IndicePlanilla = a.pipId;
                
                DELETE pap
                FROM staging.PreliminarArchivoPila pap
                INNER JOIN #IndicePlanillaArchivoA a ON pap.papIndicePlanilla = a.pipId;
                
                UPDATE peb
                SET peb.pebAccionBloque0 = 'EJECUTAR_BLOQUE_1', 
                    peb.pebEstadoBloque1 = NULL, peb.pebAccionBloque1 = NULL, peb.pebFechaBloque1 = NULL, 
                    peb.pebEstadoBloque2 = NULL, peb.pebAccionBloque2 = NULL, peb.pebFechaBloque2 = NULL, 
                    peb.pebEstadoBloque3 = NULL, peb.pebAccionBloque3 = NULL, peb.pebFechaBloque3 = NULL, 
                    peb.pebEstadoBloque4 = NULL, peb.pebAccionBloque4 = NULL, peb.pebFechaBloque4 = NULL, 
                    peb.pebEstadoBloque5 = NULL, peb.pebAccionBloque5 = NULL, peb.pebFechaBloque5 = NULL, 
                    peb.pebEstadoBloque6 = NULL, peb.pebAccionBloque6 = NULL, peb.pebFechaBloque6 = NULL  -- hasta este punto se habian excluido las de bloque 7 en adelante
                FROM PilaEstadoBloque peb
                INNER JOIN #IndicePlanillaArchivoA a ON peb.pebIndicePlanilla = a.pipId;

                UPDATE pip
                SET pip.pipEstadoArchivo = 'CARGADO',
                	pip.pipDateTimeUpdate = dbo.getLocalDate()
                FROM pilaIndicePlanilla pip 
                INNER JOIN #IndicePlanillaArchivoA a ON pip.pipid = a.pipId;


			delete 
			from dbo.LogErrorPilaM1
			where lp1IndicePlanillaOI in ( SELECT pipIdArchivoI AS id FROM #IndicePlanillaArchivoA)


            COMMIT;

            SELECT pipId AS id
            FROM #IndicePlanillaArchivoA
            UNION
            SELECT pipIdArchivoI AS id
            FROM #IndicePlanillaArchivoA

            DROP TABLE #IndicePlanillaArchivoI
            DROP TABLE #IndicePlanillaArchivoA
            DROP TABLE #IndicePlanillaArchivoAtmp
            DROP TABLE #Duplicadas
            DROP TABLE #CriteriosDatosF
            DROP TABLE #DatosF

        END
        ELSE
        BEGIN
            BEGIN TRAN
                INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
                VALUES (dbo.getLocalDate()
                    ,'USP_ReiniciarPlanillas | @fechaCorte=' + CAST(getdate() AS VARCHAR(30)) 
                    ,'PilaProceso Inactivo');
            COMMIT;
        END;

        BEGIN TRAN
            INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
            VALUES (dbo.getLocalDate()
                ,'USP_ReiniciarPlanillas | @fechaCorte=' + CAST(getdate() AS VARCHAR(30)) 
                ,'Fin');
        COMMIT;
    END TRY
    BEGIN CATCH
        IF XACT_STATE() <> 0 AND @@TRANCOUNT > 0
        BEGIN
            ROLLBACK
        END

        BEGIN TRAN
            INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
            VALUES (dbo.getLocalDate()
                ,'USP_ReiniciarPlanillas | @fechaCorte=' + CAST(getdate() AS VARCHAR(30)) 
                ,ERROR_MESSAGE());
        COMMIT;

        THROW
    END CATCH;
END;