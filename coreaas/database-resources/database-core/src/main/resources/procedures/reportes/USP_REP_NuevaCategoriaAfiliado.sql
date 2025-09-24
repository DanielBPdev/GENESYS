-- =============================================
-- Author:		Diego Suesca
-- Create date: 2019/02/22
-- Description:	SP que comprueba si es un nuevo afiliado para agregarle categoría
-- =============================================
CREATE PROCEDURE USP_REP_NuevaCategoriaAfiliado
	@iAfiId BIGINT
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


    SELECT 
    afiliado,    
    tipoCotizante, 
    clasificacion, 
    salario,
    categoria, 
    estadoAfiliacion, 
    fechaFinServicioSinAfiliacion, 
    'NUEVA_AFILIACION' motivoCambioCategoria,
    dbo.getLocalDate() as fechaCambioCategoria
    INTO #NuevosRegistros
    FROM #CategoriaAfiliadoCalculada
    WHERE estadoAfiliacion = 'ACTIVO'

    DELETE n
    FROM #NuevosRegistros n
    JOIN (SELECT ctaTipoAfiliado
            FROM CategoriaAfiliado  
            WHERE ctaAfiliado = @iAfiId              
          ) cta ON cta.ctaTipoAfiliado = n.tipoCotizante


    DECLARE @TempCategoriasOut TABLE(ctaId bigint,ctaAfiliado bigint,ctaTipoAfiliado varchar(30),ctaClasificacion varchar(48),ctaTotalIngresoMesada numeric (19,5),ctaEstadoAfiliacion varchar(8),ctaFechaFinServicioSinAfiliacion datetime,ctaCategoria varchar(50),ctaMotivoCambioCategoria varchar(50),ctaFechaCambioCategoria datetime);
    
    INSERT CategoriaAfiliado (ctaAfiliado,ctaTipoAfiliado,ctaClasificacion,ctaTotalIngresoMesada,ctaCategoria,ctaEstadoAfiliacion,ctaFechaFinServicioSinAfiliacion,ctaMotivoCambioCategoria,ctaFechaCambioCategoria)
    OUTPUT INSERTED.ctaId ,INSERTED.ctaAfiliado ,INSERTED.ctaTipoAfiliado ,INSERTED.ctaClasificacion ,INSERTED.ctaTotalIngresoMesada ,INSERTED.ctaEstadoAfiliacion ,INSERTED.ctaFechaFinServicioSinAfiliacion ,INSERTED.ctaCategoria ,INSERTED.ctaMotivoCambioCategoria ,INSERTED.ctaFechaCambioCategoria
    INTO @TempCategoriasOut    
	SELECT afiliado, tipoCotizante, clasificacion, salario,
	(select case when tipoCotizante='TRABAJADOR_INDEPENDIENTE' then 
	  (select case when estadoAfiliacion = 'INACTIVO' then 'SIN_CATEGORIA'
			else  (case when salario <= (select prmValor * 4 from Parametro where prmNombre = 'SMMLV') then 'B' else 'C' end )
			 end)
			when  tipoCotizante='PENSIONADO' then 
			(select case when estadoAfiliacion = 'INACTIVO' then 'SIN_CATEGORIA' ELSE 
				( case when clasificacion in ('MAS_1_5_SM_0_6_POR_CIENTO','MAS_1_5_SM_2_POR_CIENTO') and salario > (select prmValor*2 from Parametro where prmNombre = 'SMMLV')   and salario <= (select prmValor*4 from Parametro where prmNombre = 'SMMLV')
				then 'B' 
				when clasificacion in ('MAS_1_5_SM_0_6_POR_CIENTO','MAS_1_5_SM_2_POR_CIENTO') and salario > (select prmValor*4 from Parametro where prmNombre = 'SMMLV')  
				then 'C' ELSE 'A' END)
			END)
	else  categoria end) as categoria, estadoAfiliacion, fechaFinServicioSinAfiliacion, 
            motivoCambioCategoria,fechaCambioCategoria
    FROM #NuevosRegistros    

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
    

END;