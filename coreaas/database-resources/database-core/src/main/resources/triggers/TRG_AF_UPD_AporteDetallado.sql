-- =============================================
-- Author:      Diego Suesca
-- Create date: 2019/01/18 
-- Description: 
-- =============================================
CREATE TRIGGER TRG_AF_UPD_AporteDetallado
ON AporteDetallado
AFTER UPDATE AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @apdId BIGINT
    DECLARE @apdPersona BIGINT
    DECLARE @apdAporteGeneral BIGINT
    DECLARE @emplId BIGINT
    DECLARE @apdTipoCotizante VARCHAR(100)
	DECLARE @apdMarcaPeriodo VARCHAR(19)
	DECLARE @periodoAporte DATE
	DECLARE @idRegistroDetallado BIGINT
	DECLARE @apdEstadoAporteAjuste VARCHAR(100)
    DECLARE @fechaRegular DATE = DATEADD(month,-1, DATEADD(mm,DATEDIFF(mm,0,dbo.GetLocalDate()),0))
    
    SELECT @apdId = apdId, 
            @apdPersona = apdPersona,
            @apdAporteGeneral = apdAporteGeneral,
            @apdTipoCotizante = apdTipoCotizante,
			@apdMarcaPeriodo = apdMarcaPeriodo,
			@idRegistroDetallado = apdRegistroDetallado,
			@apdEstadoAporteAjuste=apdEstadoAporteAjuste
    FROM INSERTED
    	--AJUSTE CONDICIONAL (IF) PARA CORRECCIONES POR APORTE MANUAL YA QUE EL CREA UN NUEVO REGISTRO Y EL ORIGINAL LO ANULA
	IF UPDATE (apdEstadoAporteAjuste) AND @apdEstadoAporteAjuste = 'ANULADO' 
	 BEGIN
	 	execute [dbo].[USP_REP_CalcularCategoriaAportesPila] @idRegistroDetallado
	 END
    IF UPDATE (apdPersona)  OR UPDATE (apdAporteGeneral)
    BEGIN
        IF @apdPersona IS NOT NULL AND @apdAporteGeneral IS NOT NULL
        BEGIN
             DECLARE @tablaVacia AS TablaIdType;  
            EXEC USP_REP_INS_EstadoAfiliacionPersonaCaja @apdPersona, @tablaVacia   
          
            SELECT @emplId = empl.empId
            FROM Empresa emp
                INNER JOIN Empleador empl on empl.empEmpresa = emp.empId
                INNER JOIN AporteGeneral apg    ON emp.empId = apg.apgEmpresa
                INNER JOIN AporteDetallado apd      ON apd.apdAporteGeneral = apg.apgId
            WHERE apd.apdId = @apdId 

            IF @emplId IS NOT NULL
            BEGIN                
                EXEC USP_REP_INS_EstadoAfiliacionPersonaEmpresa @apdPersona, @emplId, @tablaVacia                 
            END

            IF @apdTipoCotizante = 'TRABAJADOR_INDEPENDIENTE'
            BEGIN
                EXEC USP_REP_INS_EstadoAfiliacionPersonaIndependiente @apdPersona, @tablaVacia  
            END

            IF @apdTipoCotizante = 'PENSIONADO'
            BEGIN
                EXEC USP_REP_INS_EstadoAfiliacionPersonaPensionado @apdPersona, @tablaVacia 
            END             
        END
    END
	

    -- CATEGORIA AFILIADO **********************************************************************************    
    IF UPDATE (apdPersona)  OR UPDATE (apdAporteGeneral) OR UPDATE (apdTipoCotizante) OR UPDATE(apdSalarioBasico)
    BEGIN
        DECLARE @afiId BIGINT        

        IF @apdPersona IS NOT NULL AND @apdAporteGeneral IS NOT NULL AND @apdMarcaPeriodo = 'PERIODO_REGULAR'
        BEGIN
            SELECT @afiId = afi.afiId
            FROM Afiliado afi
            WHERE afi.afiPersona = @apdPersona            
            execute [dbo].[USP_REP_CalcularCategoriaAportesPila] @idRegistroDetallado
            --EXEC USP_REP_CambioCategoriaAfiliado @afiId,1
        END
        IF @apdMarcaPeriodo = 'PERIODO_RETROACTIVO' AND @apdTipoCotizante = 'TRABAJADOR_DEPENDIENTE'
		BEGIN
			SELECT @periodoAporte=CAST((SELECT MAX(apgPeriodoAporte) FROM AporteGeneral, AporteDetallado 
				WHERE apgId=apdAporteGeneral AND apdTipoCotizante = 'TRABAJADOR_DEPENDIENTE' AND apdPersona=@apdPersona) + '-01' AS DATE)
			
			SELECT @afiId = afi.afiId
			FROM Afiliado afi
			WHERE afi.afiPersona = @apdPersona

			IF @fechaRegular>@periodoAporte
			BEGIN
					execute [dbo].[USP_REP_CalcularCategoriaAportesPila] @idRegistroDetallado
				--EXEC USP_REP_CambioCategoriaAfiliado @afiId,1
			END
		END 
    END  
      
END