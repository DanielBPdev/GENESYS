-- =============================================
-- Author:		Diego Suesca
-- Create date: 2021/03/23
-- Description: Procedimiento que calcula la categoría actual de los beneficiarios
-- NOTA_ SE DEBE CREAR TABLA #CategoriaBeneficiarioCalculada ANTES DE LLAMAR ESTE SP Y INSERTAR LOS perId EN EL CAMPO persona DE LAS PERSONAS A CALCULAR CATEGORIA
-- =============================================

CREATE PROCEDURE USP_GET_CategoriaBeneficiario
AS

	CREATE TABLE #perId (perId BIGINT primary key);

	INSERT #perId (perId)
	SELECT DISTINCT persona FROM #CategoriaBeneficiarioCalculada;

	TRUNCATE TABLE #CategoriaBeneficiarioCalculada;
	

	 -- ************** INICIO CALCULO DE CATEGORIAS AFILIADO *************************************************************
    -- se crea tabla temporal para calcular categorías con sp USP_GET_CategoriaAfiliado
    CREATE TABLE #CategoriaAfiliadoCalculada
    (afiliado bigint,persona bigint,tipoIdentificacion  varchar(20),numeroIdentificacion varchar(16),tipoCotizante varchar(100),clasificacion varchar(48),salario numeric (19,2),estadoAfiliacion varchar(8),fechaFinServicioSinAfiliacion date,aporteEmpleadorNoAfiliado bit,categoria varchar (50)
    );         

    -- personas a calcular categoria
    INSERT #CategoriaAfiliadoCalculada (persona)
    SELECT DISTINCT afi.afiPersona 
    FROM #perId perBen
    JOIN dbo.Beneficiario ben ON ben.benPersona = perBen.perId
    JOIN dbo.afiliado afi ON afi.afiId = ben.benAfiliado

    -- ejecucion sp calculo de categorias, quedaran en la tabla #CategoriaAfiliadoCalculada
    EXEC USP_GET_CategoriaAfiliado;

    -- **************** FIN CALCULO DE CATEGORIAS ***********************************************************************

    
	WITH cteBeneficiariosActivos
	AS
	(
		SELECT DISTINCT ben.benBeneficiarioDetalle AS beneficiarioDetalle, ben.benAfiliado AS afiliado, ben.benTipoBeneficiario AS tipoBeneficiario, 'ACTIVO' AS estadoBeneficiarioAfiliado
		FROM Beneficiario ben
		JOIN #perId p 
					ON p.perId = ben.benPersona	
		WHERE ben.benEstadoBeneficiarioAfiliado = 'ACTIVO'
		AND ben.benBeneficiarioDetalle IS NOT NULL
	),
	cteBeneficiariosInactivos
	AS
	(
		SELECT DISTINCT ben.benBeneficiarioDetalle AS beneficiarioDetalle, ben.benAfiliado AS afiliado, ben.benTipoBeneficiario AS tipoBeneficiario, 'INACTIVO' AS estadoBeneficiarioAfiliado
		FROM Beneficiario ben	
		JOIN #perId p 
					ON p.perId = ben.benPersona	
		LEFT JOIN (
					SELECT cte.beneficiarioDetalle,
						cte.afiliado,
						cte.tipoBeneficiario
					FROM cteBeneficiariosActivos cte					
					) cte ON ben.benBeneficiarioDetalle = cte.beneficiarioDetalle
						AND ben.benAfiliado = cte.afiliado
						AND ben.benTipoBeneficiario = cte.tipoBeneficiario
		WHERE ISNULL(ben.benEstadoBeneficiarioAfiliado, 'INACTIVO') = 'INACTIVO'
		AND ben.benBeneficiarioDetalle IS NOT NULL
		AND cte.beneficiarioDetalle IS NULL
	),
	cteBeneficiariosALL 
	AS
	(
		SELECT beneficiarioDetalle, afiliado, tipoBeneficiario, estadoBeneficiarioAfiliado
		FROM cteBeneficiariosActivos
		UNION ALL
		SELECT beneficiarioDetalle, afiliado, tipoBeneficiario, estadoBeneficiarioAfiliado
		FROM cteBeneficiariosInactivos
	)

	

	INSERT #CategoriaBeneficiarioCalculada
	(beneficiarioDetalle,persona,tipoIdentificacion,numeroIdentificacion,tipoBeneficiario,estadoBeneficiarioAfiliado,afiliado,tipoIdentificacionAfiliado,numeroIdentificacionAfiliado,tipoCotizante,clasificacion,salario,estadoAfiliacionAfiliado,fechaFinServicioSinAfiliacion,categoria)
	SELECT cte.beneficiarioDetalle, 
			per.perId AS persona, 
			per.perTipoIdentificacion AS tipoIdentificacion, 
			per.perNumeroIdentificacion AS numeroIdentificacion, 
			cte.tipoBeneficiario, 
			cte.estadoBeneficiarioAfiliado, 
			cte.afiliado, 
			vw.tipoIdentificacion AS tipoIdentificacionAfiliado, 
			vw.numeroIdentificacion AS numeroIdentificacionAfiliado,
			vw.tipoCotizante, 
			vw.clasificacion, 
			vw.salario, vw.estadoAfiliacion AS estadoAfiliacionAfiliado, 
			vw.fechaFinServicioSinAfiliacion, 
			vw.categoria
	FROM cteBeneficiariosALL cte
	INNER JOIN BeneficiarioDetalle bed ON cte.beneficiarioDetalle = bed.bedId
	INNER JOIN PersonaDetalle ped ON bed.bedPersonaDetalle = ped.pedId
	INNER JOIN Persona per ON ped.pedPersona = per.perId
	INNER JOIN #CategoriaAfiliadoCalculada vw ON cte.afiliado = vw.afiliado