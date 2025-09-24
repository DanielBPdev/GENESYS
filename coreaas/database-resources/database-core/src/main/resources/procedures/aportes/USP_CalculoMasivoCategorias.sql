-- =============================================
-- Author:		Alfonso Baquero Echeverry
-- Create date: 2018/08/14
-- Description:	Procedimiento almacenado encargado actualizar 
-- el estado del aporte en Core con la nueva evaluación realizada
-- =============================================
CREATE PROCEDURE [dbo].[USP_CalculoMasivoCategorias]
AS

DECLARE @AporteDetallado TABLE (apdId bigint, apdPersona bigint);
--DECLARE #CategoriaAfiliado TABLE (ctaAfiliado bigint, ctaTipoAfiliado varchar(30), ctaId bigint);


BEGIN
    SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED

    print 'Inicia USP_CalculoMasivoCategorias'

    INSERT INTO @AporteDetallado
    SELECT TOP 500 apd.apdId , apd.apdPersona FROM AporteDetallado apd 
    WHERE apd.apdModalidadRecaudoAporte = 'PILA'
    AND apd.apdMarcaCalculoCategoria = 1
    ORDER BY apd.apdId 

   

    IF EXISTS (SELECT 1 FROM @AporteDetallado) 
    BEGIN
            UPDATE ad
            SET ad.apdMarcaCalculoCategoria = 0
            FROM AporteDetallado ad
            INNER JOIN @AporteDetallado tad on ad.apdId = tad.apdId

            --INSERT INTO #CategoriaAfiliado
            SELECT ctaAfiliado, ctaTipoAfiliado, MAX(ctaId) ctaId
            INTO #CategoriaAfiliado
            FROM CategoriaAfiliado
            INNER JOIN Afiliado afi ON afi.afiId = ctaAfiliado
            INNER JOIN @AporteDetallado apd ON apd.apdPersona = afi.afiPersona
            GROUP BY ctaAfiliado, ctaTipoAfiliado
            ORDER BY ctaId

            CREATE CLUSTERED INDEX IX_CategoriaAfiliado ON #CategoriaAfiliado (ctaId,ctaAfiliado)           

           
            BEGIN TRY
                -- ************** INICIO CALCULO DE CATEGORIAS ****************************************************************************
                -- se crea tabla temporal para calcular categorías con sp USP_GET_CategoriaAfiliado
                CREATE TABLE #CategoriaAfiliadoCalculada
                (afiliado bigint,persona bigint,tipoIdentificacion  varchar(20),numeroIdentificacion varchar(16),tipoCotizante varchar(100),clasificacion varchar(48),salario numeric (19,2),estadoAfiliacion varchar(8),fechaFinServicioSinAfiliacion date,aporteEmpleadorNoAfiliado bit,categoria varchar (50)
                );         

                -- personas a calcular categoria
                INSERT #CategoriaAfiliadoCalculada (persona)
                SELECT DISTINCT apdPersona FROM @AporteDetallado

                -- ejecucion sp calculo de categorias, quedaran en la tabla #CategoriaAfiliadoCalculada
                EXEC USP_GET_CategoriaAfiliado

                -- **************** FIN CALCULO DE CATEGORIAS ***********************************************************************

                CREATE CLUSTERED INDEX IX_CategoriaAfiliadoCalculada ON #CategoriaAfiliadoCalculada (afiliado,tipoCotizante)

                SELECT vw.afiliado, vw.tipoCotizante, vw.clasificacion, vw.salario, vw.categoria, vw.estadoAfiliacion,
                    CASE WHEN vw.estadoAfiliacion <> ctaActual.ctaEstadoAfiliacion THEN
                            CASE WHEN vw.estadoAfiliacion = 'INACTIVO' THEN 'RETIRO' ELSE 'REINTEGRO' END
                        WHEN ISNULL(vw.salario,0) <> ISNULL(ctaActual.ctaTotalIngresoMesada,0) THEN
                            --CASE @bNuevoAporte WHEN 1 THEN
                                (CASE vw.tipoCotizante 
                                    WHEN 'TRABAJADOR_DEPENDIENTE' THEN 
                                        (CASE WHEN vw.aporteEmpleadorNoAfiliado = 1 THEN 'APORTE_RECIBIDO_NO_AFILIADO_CAJA' ELSE 'APORTE_RECIBIDO_AFILIADO_CAJA' END)
                                    ELSE 'APORTE_RECIBIDO' END
                                )
                        -- ELSE
                            --    'NUEVA_AFILIACION'
                    --  END
                    END motivoCambioCategoria, 
                    vw.fechaFinServicioSinAfiliacion, 
                    dbo.GetLocalDate() fechaCambioCategoria
                INTO #NuevosRegistros
                FROM #CategoriaAfiliado T
                JOIN CategoriaAfiliado ctaActual ON T.ctaId = ctaActual.ctaId AND T.ctaAfiliado = ctaActual.ctaAfiliado
                JOIN #CategoriaAfiliadoCalculada vw     
                                                ON vw.afiliado = ctaActual.ctaAfiliado 
                                                AND vw.tipoCotizante = ctaActual.ctaTipoAfiliado                
                WHERE ISNULL(ctaActual.ctaCategoria,'') <> ISNULL(vw.categoria,'')
                OPTION (RECOMPILE)

                DECLARE @TempCategoriasOut TABLE(ctaId bigint,ctaAfiliado bigint,ctaTipoAfiliado varchar(30),ctaClasificacion varchar(48),ctaTotalIngresoMesada numeric (19,5),ctaEstadoAfiliacion varchar(8),ctaFechaFinServicioSinAfiliacion datetime,ctaCategoria varchar(50),ctaMotivoCambioCategoria varchar(50),ctaFechaCambioCategoria datetime);
 
                INSERT INTO CategoriaAfiliado (ctaAfiliado,ctaTipoAfiliado,ctaClasificacion,ctaTotalIngresoMesada,ctaCategoria,ctaEstadoAfiliacion,ctaFechaFinServicioSinAfiliacion,ctaMotivoCambioCategoria,ctaFechaCambioCategoria)
                OUTPUT INSERTED.ctaId ,INSERTED.ctaAfiliado ,INSERTED.ctaTipoAfiliado ,INSERTED.ctaClasificacion ,INSERTED.ctaTotalIngresoMesada ,INSERTED.ctaEstadoAfiliacion ,INSERTED.ctaFechaFinServicioSinAfiliacion ,INSERTED.ctaCategoria ,INSERTED.ctaMotivoCambioCategoria ,INSERTED.ctaFechaCambioCategoria
                INTO @TempCategoriasOut
                SELECT afiliado, tipoCotizante, clasificacion, salario, categoria, estadoAfiliacion, fechaFinServicioSinAfiliacion, motivoCambioCategoria, fechaCambioCategoria
                FROM #NuevosRegistros
                WHERE motivoCambioCategoria IS NOT NULL

                INSERT INTO CategoriaBeneficiario (ctbBeneficiarioDetalle,ctbTipoBeneficiario,ctbCategoriaAfiliado)
                SELECT DISTINCT ben.benBeneficiarioDetalle, ben.benTipoBeneficiario, t.ctaId
                FROM @TempCategoriasOut t
                JOIN Beneficiario ben ON ben.benAfiliado = t.ctaAfiliado


                
                --INSERT INTO CategoriaBeneficiario (ctbBeneficiarioDetalle,ctbTipoBeneficiario,ctbCategoriaAfiliado)
                --SELECT vw.beneficiarioDetalle, vw.tipoBeneficiario, cta.ctaId
                --FROM #NuevosRegistros new
                --JOIN CategoriaAfiliado cta ON new.afiliado  = cta.ctaAfiliado 
                --                          AND new.tipoCotizante  = cta.ctaTipoAfiliado
                --JOIN VW_CategoriaBeneficiario vw ON vw.afiliado = cta.ctaAfiliado 
                --                                AND vw.tipoCotizante = cta.ctaTipoAfiliado
                --WHERE motivoCambioCategoria IS NOT NULL
                --OPTION (RECOMPILE)
                

                
                --INSERT INTO CategoriaBeneficiario (ctbBeneficiarioDetalle,ctbTipoBeneficiario,ctbCategoriaAfiliado)
                --SELECT vw.beneficiarioDetalle, vw.tipoBeneficiario, cta.ctaId
                --FROM VW_CategoriaBeneficiario vw
                --INNER JOIN CategoriaAfiliado cta ON vw.afiliado = cta.ctaAfiliado AND vw.tipoCotizante = cta.ctaTipoAfiliado
                --INNER JOIN #NuevosRegistros new ON cta.ctaAfiliado = new.afiliado AND cta.ctaTipoAfiliado = new.tipoCotizante         
                --WHERE motivoCambioCategoria IS NOT NULL
                              


                -- **************************  NUEVAS AFILIACIONES ********************************

                DELETE FROM @TempCategoriasOut;

                SELECT vw.afiliado, vw.tipoCotizante, vw.clasificacion, vw.salario,vw.categoria, vw.estadoAfiliacion, vw.fechaFinServicioSinAfiliacion, 
                    'NUEVA_AFILIACION' motivoCambioCategoria,
                    dbo.getLocalDate() as fechaCambioCategoria
                INTO #NuevosRegistros1
                FROM (SELECT DISTINCT apdPersona FROM @AporteDetallado) apd
                JOIN #CategoriaAfiliadoCalculada vw ON apd.apdPersona = vw.persona                
                WHERE vw.estadoAfiliacion = 'ACTIVO'

                DELETE n
                FROM #NuevosRegistros1 n
                JOIN CategoriaAfiliado cta ON cta.ctaAfiliado = n.afiliado 
                                          AND cta.ctaTipoAfiliado = n.tipoCotizante               
               

                INSERT CategoriaAfiliado (ctaAfiliado,ctaTipoAfiliado,ctaClasificacion,ctaTotalIngresoMesada,ctaCategoria,ctaEstadoAfiliacion,ctaFechaFinServicioSinAfiliacion,ctaMotivoCambioCategoria,ctaFechaCambioCategoria)
                OUTPUT INSERTED.ctaId ,INSERTED.ctaAfiliado ,INSERTED.ctaTipoAfiliado ,INSERTED.ctaClasificacion ,INSERTED.ctaTotalIngresoMesada ,INSERTED.ctaEstadoAfiliacion ,INSERTED.ctaFechaFinServicioSinAfiliacion ,INSERTED.ctaCategoria ,INSERTED.ctaMotivoCambioCategoria ,INSERTED.ctaFechaCambioCategoria
                INTO @TempCategoriasOut             
                SELECT afiliado, tipoCotizante, clasificacion, salario,categoria, estadoAfiliacion, fechaFinServicioSinAfiliacion, 
                        motivoCambioCategoria,fechaCambioCategoria
                FROM #NuevosRegistros1                

                INSERT INTO CategoriaBeneficiario (ctbBeneficiarioDetalle,ctbTipoBeneficiario,ctbCategoriaAfiliado)
                SELECT DISTINCT ben.benBeneficiarioDetalle, ben.benTipoBeneficiario, t.ctaId
                FROM @TempCategoriasOut t
                JOIN Beneficiario ben ON ben.benAfiliado = t.ctaAfiliado

                /*
                INSERT INTO CategoriaBeneficiario (ctbBeneficiarioDetalle,ctbTipoBeneficiario,ctbCategoriaAfiliado)
                SELECT vw.beneficiarioDetalle, vw.tipoBeneficiario, cta.ctaId
                FROM #NuevosRegistros1 new
                JOIN CategoriaAfiliado cta ON new.afiliado  = cta.ctaAfiliado 
                                          AND new.tipoCotizante  = cta.ctaTipoAfiliado
                JOIN VW_CategoriaBeneficiario vw ON vw.afiliado = cta.ctaAfiliado 
                                                AND vw.tipoCotizante = cta.ctaTipoAfiliado
                WHERE motivoCambioCategoria IS NOT NULL
                OPTION (RECOMPILE)
                */

                /*
                INSERT INTO CategoriaBeneficiario (ctbBeneficiarioDetalle,ctbTipoBeneficiario,ctbCategoriaAfiliado)
                SELECT vw.beneficiarioDetalle, vw.tipoBeneficiario, cta.ctaId
                FROM VW_CategoriaBeneficiario vw
                INNER JOIN CategoriaAfiliado cta ON vw.afiliado = cta.ctaAfiliado AND vw.tipoCotizante = cta.ctaTipoAfiliado
                INNER JOIN #NuevosRegistros1 new ON cta.ctaAfiliado = new.afiliado AND cta.ctaTipoAfiliado = new.tipoCotizante 
                WHERE motivoCambioCategoria IS NOT NULL
                */


            END TRY
            BEGIN CATCH
                UPDATE ad
                SET ad.apdMarcaCalculoCategoria = 1
                FROM AporteDetallado ad
                INNER JOIN @AporteDetallado tad on ad.apdId = tad.apdId
            END CATCH
    END
    print 'Finaliza USP_CalculoMasivoCategorias'
END;
