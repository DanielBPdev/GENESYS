-- =============================================
-- Author:      Diego Suesca
-- Create date: 2019/01/18 
-- Description: 
-- =============================================
CREATE OR ALTER TRIGGER TRG_AF_INS_AporteDetallado
ON AporteDetallado
AFTER INSERT AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @apdEstadoCotizante varchar(50);
    DECLARE @contadorGlpi61052 int;
    declare @fechaInicio varchar(50);
    declare @fechaFin varchar(50);
    DECLARE @dateinicio DATETIME;
    DECLARE @datefin DATETIME;
    DECLARE @dateAporte DATETIME;
    declare @varApdTipoCotizante varchar(200);
    declare @varEmpid varchar(200);
    declare @varApgPeriodoAporte varchar(200);
    declare @varApgPeriodoAporteOrdenado varchar(200);
    declare @varApgPersona varchar(200);
    DECLARE @ESTADOFINALAPORTE varchar(200) = 'REGISTRADO';
    DECLARE @apdId BIGINT
    DECLARE @apdPersona BIGINT
    DECLARE @apdAporteGeneral BIGINT
    DECLARE @apdMarcaPeriodo VARCHAR(19)
    DECLARE @apdModalidadRecaudoAporte VARCHAR(19)
    DECLARE @emplId BIGINT  
    DECLARE @afiId BIGINT
    DECLARE @apdTipoCotizante VARCHAR(100)
    DECLARE @periodoAporte DATE
    DECLARE @idRegistroDetallado BIGINT
    DECLARE @apdSalarioBasico BIGINT
    DECLARE @fechaRegular DATE = DATEADD(month,-1, DATEADD(mm,DATEDIFF(mm,0,dbo.GetLocalDate()),0))
    DECLARE @data VARCHAR(100)
    DECLARE @apgEstadoAportante varchar(50);
    DECLARE @varApgFechaRecaudo DATE;
    DECLARE @varApgFechaRecaudoYYYYMM varchar(50);
    DECLARE @varFechaRetiroYYYYMM varchar(50);
    DECLARE @varFechaAfiliacionYYYYMM varchar(50);
	DECLARE @estadoPersona varchar(100);
	


    
    
    SELECT @apdId = apdId, 
            @apdPersona = apdPersona,
            @apdAporteGeneral = apdAporteGeneral,
            @apdTipoCotizante = apdTipoCotizante,
            @apdMarcaPeriodo = apdMarcaPeriodo,
            @apdModalidadRecaudoAporte = apdModalidadRecaudoAporte,
            @idRegistroDetallado = apdRegistroDetallado,
            @apdSalarioBasico = apdSalarioBasico
    FROM INSERTED

            --CONDICION APORTES MANUELES
            IF @apdModalidadRecaudoAporte = 'MANUAL' and @apdSalarioBasico > 0 and @apdMarcaPeriodo != 'PERIODO_FUTURO'
                BEGIN 
                    execute [dbo].[USP_REP_CalcularCategoriaAportesPila] @idRegistroDetallado
                END
         --FIN CONDICION APORTES MANUELES

   
    IF @apdPersona IS NOT NULL AND @apdAporteGeneral IS NOT NULL
    BEGIN
        DECLARE @tablaVacia AS TablaIdType;  
        EXEC USP_REP_INS_EstadoAfiliacionPersonaCaja  @apdPersona,@tablaVacia
       
        SELECT @emplId = empl.empId
        FROM Empresa emp
            INNER JOIN Empleador empl ON empl.empEmpresa = emp.empId
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

        ----CATEGORIA AFILIADO **********************************************************************************
        IF @apdModalidadRecaudoAporte <> 'PILA' AND @apdModalidadRecaudoAporte <> 'MANUAL'
       BEGIN
            IF @apdMarcaPeriodo = 'PERIODO_REGULAR'
            BEGIN
                SELECT @afiId = afi.afiId
                FROM Afiliado afi
                WHERE afi.afiPersona = @apdPersona

                EXEC USP_REP_CambioCategoriaAfiliado @afiId,1
                EXEC USP_REP_NuevaCategoriaAfiliado @afiId
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
                    EXEC USP_REP_CambioCategoriaAfiliado @afiId,1
                    EXEC USP_REP_NuevaCategoriaAfiliado @afiId
                END
            END
        END
    END
	IF EXISTS (SELECT 1 FROM ParametrizacionGaps WHERE prgNombre='REGISTRO_APORTES_CAMBIO_CAJA_MULTIAFILIACION' and prgEstado='ACTIVO')
	begin 
	--IF PARA GLPI 61052 PARAMETROS APORTE_DETALLADO
	 	--select 'APORTE_DETALLADO',NULL,NULL,NULL,NULL,@NULL,@apdId,@apdMarcaPeriodo,
		execute USP_EJECUTAR_REGISTRO_APORTES_CAMBIO_CAJA_MULTIAFILIACION 'APORTE_DETALLADO',NULL,NULL,NULL,NULL,NULL,@apdId,@apdMarcaPeriodo
	end
    
      
END