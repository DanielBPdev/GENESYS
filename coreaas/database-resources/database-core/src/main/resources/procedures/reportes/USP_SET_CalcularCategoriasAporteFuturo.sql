-- =============================================
-- Author:      Diego Suesca
-- Create date: 2019/02/22
-- Description: SP que calcula categoria para todos los afiliados al inicio de cada mes para aportes futuros
-- =============================================
CREATE PROCEDURE USP_SET_CalcularCategoriasAporteFuturo    
with execute as owner
AS
BEGIN    
SET NOCOUNT ON;	
    -- ************** INICIO CALCULO DE CATEGORIAS ****************************************************************************
    -- se crea tabla temporal para calcular categorías con sp USP_GET_CategoriaAfiliado
    CREATE TABLE #CategoriaAfiliadoCalculada
    (afiliado bigint,persona bigint,tipoIdentificacion  varchar(20),numeroIdentificacion varchar(16),tipoCotizante varchar(100),clasificacion varchar(48),salario numeric (19,2),estadoAfiliacion varchar(8),fechaFinServicioSinAfiliacion date,aporteEmpleadorNoAfiliado bit,categoria varchar (50)
    );         

    -- personas a calcular categoria
    INSERT #CategoriaAfiliadoCalculada (persona)
    SELECT afiPersona FROM Afiliado

    -- ejecucion sp calculo de categorias, quedaran en la tabla #CategoriaAfiliadoCalculada
    EXEC USP_GET_CategoriaAfiliado

    -- **************** FIN CALCULO DE CATEGORIAS ***********************************************************************


    SELECT vw.afiliado, vw.tipoCotizante, vw.clasificacion, vw.salario, vw.categoria, vw.estadoAfiliacion,
        CASE WHEN vw.estadoAfiliacion <> ctaActual.ctaEstadoAfiliacion THEN
                CASE WHEN vw.estadoAfiliacion = 'INACTIVO' THEN 'RETIRO' ELSE 'REINTEGRO' END
            WHEN ISNULL(vw.salario,0) <> ISNULL(ctaActual.ctaTotalIngresoMesada,0) THEN                
                    (CASE vw.tipoCotizante 
                        WHEN 'TRABAJADOR_DEPENDIENTE' THEN 
                            (CASE WHEN vw.aporteEmpleadorNoAfiliado = 1 THEN 'APORTE_RECIBIDO_NO_AFILIADO_CAJA' ELSE 'APORTE_RECIBIDO_AFILIADO_CAJA' END)
                        ELSE 'APORTE_RECIBIDO' END
                    )           
        END motivoCambioCategoria, 
        vw.fechaFinServicioSinAfiliacion, 
        dbo.GetLocalDate() fechaCambioCategoria
    INTO #NuevosRegistros
    FROM #CategoriaAfiliadoCalculada vw
    INNER JOIN CategoriaAfiliado ctaActual ON vw.afiliado = ctaActual.ctaAfiliado 
                                          AND vw.tipoCotizante = ctaActual.ctaTipoAfiliado
    INNER JOIN (
        SELECT ctaAfiliado, ctaTipoAfiliado, MAX(ctaId) ctaId
        FROM CategoriaAfiliado       
        GROUP BY ctaAfiliado, ctaTipoAfiliado
    ) T ON T.ctaId = ctaActual.ctaId AND T.ctaAfiliado = ctaActual.ctaAfiliado
    WHERE   ISNULL(ctaActual.ctaCategoria,'') <> ISNULL(vw.categoria,'')     


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
    

    --  ********************* NUEVAS AFILIACIONES     ******************

    DELETE FROM @TempCategoriasOut;
    
    SELECT vw.afiliado, vw.tipoCotizante, vw.clasificacion, vw.salario,vw.categoria, vw.estadoAfiliacion, vw.fechaFinServicioSinAfiliacion, 
            'NUEVA_AFILIACION' motivoCambioCategoria,
            dbo.getLocalDate() as fechaCambioCategoria
    INTO #NuevosRegistros2
    FROM #CategoriaAfiliadoCalculada vw
    WHERE vw.estadoAfiliacion = 'ACTIVO'

    DELETE n
    FROM #NuevosRegistros2 n
    JOIN CategoriaAfiliado cta ON cta.ctaAfiliado = n.afiliado 
                              AND cta.ctaTipoAfiliado = n.tipoCotizante
 

    INSERT CategoriaAfiliado (ctaAfiliado,ctaTipoAfiliado,ctaClasificacion,ctaTotalIngresoMesada,ctaCategoria,ctaEstadoAfiliacion,ctaFechaFinServicioSinAfiliacion,ctaMotivoCambioCategoria,ctaFechaCambioCategoria)
    OUTPUT INSERTED.ctaId ,INSERTED.ctaAfiliado ,INSERTED.ctaTipoAfiliado ,INSERTED.ctaClasificacion ,INSERTED.ctaTotalIngresoMesada ,INSERTED.ctaEstadoAfiliacion ,INSERTED.ctaFechaFinServicioSinAfiliacion ,INSERTED.ctaCategoria ,INSERTED.ctaMotivoCambioCategoria ,INSERTED.ctaFechaCambioCategoria
    INTO @TempCategoriasOut          
    SELECT afiliado, tipoCotizante, clasificacion, salario,categoria, estadoAfiliacion, fechaFinServicioSinAfiliacion, 
            motivoCambioCategoria,fechaCambioCategoria
    FROM #NuevosRegistros2

    INSERT INTO CategoriaBeneficiario (ctbBeneficiarioDetalle,ctbTipoBeneficiario,ctbCategoriaAfiliado)
    SELECT DISTINCT ben.benBeneficiarioDetalle, ben.benTipoBeneficiario, t.ctaId
    FROM @TempCategoriasOut t
    JOIN Beneficiario ben ON ben.benAfiliado = t.ctaAfiliado

    
    --INSERT INTO CategoriaBeneficiario (ctbBeneficiarioDetalle,ctbTipoBeneficiario,ctbCategoriaAfiliado)
    --SELECT vw.beneficiarioDetalle, vw.tipoBeneficiario, cta.ctaId
    --FROM #NuevosRegistros2 new
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
    --INNER JOIN #NuevosRegistros2 new ON cta.ctaAfiliado = new.afiliado AND cta.ctaTipoAfiliado = new.tipoCotizante 
    --WHERE motivoCambioCategoria IS NOT NULL
    
END;