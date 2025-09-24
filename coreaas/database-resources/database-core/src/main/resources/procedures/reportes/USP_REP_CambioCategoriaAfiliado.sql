-- =============================================
-- Author:      Diego Suesca
-- Create date: 2019/02/22
-- Description: SP que comprueba si el afiliado a cambiado de categoría y lo inserta en CategoriaAfiliado
-- =============================================
CREATE PROCEDURE USP_REP_CambioCategoriaAfiliado
    @iAfiId BIGINT,
    @bNuevoAporte BIT = 0
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
        SELECT afiPersona FROM Afiliado WHERE afiId = @iAfiId;        

        -- ejecucion sp calculo de categorias, quedaran en la tabla #CategoriaAfiliadoCalculada
        EXEC USP_GET_CategoriaAfiliado

        -- **************** FIN CALCULO DE CATEGORIAS ***********************************************************************
    

        SELECT ctaEstadoAfiliacion,
        ctaTotalIngresoMesada,        
        cta.ctaTipoAfiliado,       
        ctaCategoria
        INTO #CategoriaAfiliadoUltima
        FROM CategoriaAfiliado cta
        INNER JOIN (
            SELECT ctaTipoAfiliado, MAX(ctaId) ctaId
            FROM CategoriaAfiliado
            WHERE ctaAfiliado = @iAfiId
            GROUP BY ctaAfiliado, ctaTipoAfiliado
        ) T ON T.ctaId = cta.ctaId


        SELECT vw.afiliado, vw.tipoCotizante, vw.clasificacion, vw.salario, vw.categoria, vw.estadoAfiliacion,
            CASE WHEN vw.estadoAfiliacion <> ctaUltima.ctaEstadoAfiliacion THEN
                    CASE WHEN vw.estadoAfiliacion = 'INACTIVO' THEN 'RETIRO' ELSE 'REINTEGRO' END
                WHEN ISNULL(vw.salario,0) <> ISNULL(ctaUltima.ctaTotalIngresoMesada,0) THEN
                    CASE @bNuevoAporte WHEN 1 THEN
                        (CASE vw.tipoCotizante 
                            WHEN 'TRABAJADOR_DEPENDIENTE' THEN 
                                (CASE WHEN vw.aporteEmpleadorNoAfiliado = 1 THEN 'APORTE_RECIBIDO_NO_AFILIADO_CAJA' ELSE 'APORTE_RECIBIDO_AFILIADO_CAJA' END)
                            ELSE 'APORTE_RECIBIDO' END
                        )
                    ELSE
                        'NUEVA_AFILIACION'
                END
            END motivoCambioCategoria, 
            vw.fechaFinServicioSinAfiliacion, 
            dbo.GetLocalDate() fechaCambioCategoria
        INTO #NuevosRegistros
        FROM #CategoriaAfiliadoCalculada vw
        INNER JOIN #CategoriaAfiliadoUltima ctaUltima 
                                                    ON vw.tipoCotizante = ctaUltima.ctaTipoAfiliado
        WHERE ISNULL(ctaUltima.ctaCategoria,'') <> ISNULL(vw.categoria,'')     
  

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
        --                      AND new.tipoCotizante  = cta.ctaTipoAfiliado
        --JOIN VW_CategoriaBeneficiario vw ON vw.afiliado = cta.ctaAfiliado 
        --                            AND vw.tipoCotizante = cta.ctaTipoAfiliado
        --WHERE motivoCambioCategoria IS NOT NULL
        --OPTION (RECOMPILE)
                
        --INSERT INTO CategoriaBeneficiario (ctbBeneficiarioDetalle,ctbTipoBeneficiario,ctbCategoriaAfiliado)
        --SELECT vw.beneficiarioDetalle, vw.tipoBeneficiario, cta.ctaId
        --FROM VW_CategoriaBeneficiario vw
        --INNER JOIN CategoriaAfiliado cta ON vw.afiliado = cta.ctaAfiliado AND vw.tipoCotizante = cta.ctaTipoAfiliado
        --INNER JOIN #NuevosRegistros new ON cta.ctaAfiliado = new.afiliado AND cta.ctaTipoAfiliado = new.tipoCotizante         
        --WHERE motivoCambioCategoria IS NOT NULL
        
END;