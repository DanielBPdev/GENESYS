CREATE OR ALTER PROCEDURE [dbo].[USP_REP_CalcularCategoriaBeneficiario]
    @idBeneficiario BIGINT,
    @idafiliado BIGINT,
    @idBeneficiarioDetalle BIGINT,
    @tipoIdentificacionBeneficiario VARCHAR(50),
    @identificacionBeneficiario VARCHAR(30),
    @tipoIdentificacionAfiliado VARCHAR(50),
    @identificacionAfiliado VARCHAR(30)
WITH EXECUTE AS OWNER
AS
BEGIN
/*
--PARA PRUEBAS INICIA
--IDENTIFICACION BENFICIARIO
DECLARE @tipoIdentificacionBeneficiario varchar(50)='';
DECLARE @identificacionBeneficiario varchar(30) ='';

--IDENTIFICACION AFILIADO PRINCIPAL
DECLARE @tipoIdentificacionAfiliado varchar(50)='';
DECLARE @identificacionAfiliado varchar(30) =''; 

DECLARE @idBeneficiario BIGINT=NULL;
DECLARE @idafiliado BIGINT =NULL;
DECLARE @idBeneficiarioDetalle BIGINT =NULL;
*/
    SET NOCOUNT ON;

    -- Buscar afiliado por identificación si no se recibe ID
    IF (@idafiliado IS NULL OR @idafiliado = 0)
       AND (@identificacionAfiliado IS NOT NULL OR @identificacionAfiliado != '')
    BEGIN
        SELECT @idafiliado = a.afiId
        FROM Afiliado a
        INNER JOIN Persona p ON p.perId = a.afiPersona
        WHERE p.perTipoIdentificacion = @tipoIdentificacionAfiliado
          AND p.perNumeroIdentificacion = @identificacionAfiliado;
    END

    -- Buscar beneficiario por detalle si no se recibe ID
    IF (@idBeneficiario IS NULL OR @idBeneficiario = 0)
       AND (@idBeneficiarioDetalle IS NOT NULL OR @idBeneficiarioDetalle != 0)
    BEGIN
        SELECT @idBeneficiario = b.benId
        FROM Beneficiario b
        WHERE b.benAfiliado = @idafiliado
          AND b.benBeneficiarioDetalle = @idBeneficiarioDetalle;
    END

    -- Buscar beneficiario por identificación si no se recibe ID
    IF (@idBeneficiario IS NULL OR @idBeneficiario = 0)
       AND (@identificacionBeneficiario IS NOT NULL OR @identificacionBeneficiario != '')
    BEGIN
        SELECT @idBeneficiario = b.benId
        FROM Beneficiario b
        INNER JOIN Persona p ON p.perId = b.benPersona
        WHERE b.benAfiliado = @idafiliado
          AND p.perTipoIdentificacion = @tipoIdentificacionBeneficiario
          AND p.perNumeroIdentificacion = @identificacionBeneficiario;
    END

    -- Variables de estado del beneficiario
    DECLARE @estadoBeneficiario VARCHAR(30);
    DECLARE @fechaRetiroBeneficiario DATE;
    DECLARE @fechaAfiliacionBeneficiario DATE = NULL;
    DECLARE @fechaRetiroAfiliadoUltima DATE = NULL;

    SELECT 
        @estadoBeneficiario = b.benEstadoBeneficiarioAfiliado,
        @fechaRetiroBeneficiario = b.benFechaRetiro,
        @fechaAfiliacionBeneficiario = b.benFechaAfiliacion
    FROM Beneficiario b
    WHERE b.benId = @idBeneficiario
      AND b.benAfiliado = @idafiliado;

    -- Limpieza de tablas temporales
    DROP TABLE IF EXISTS #categoriasAfiliado;
    DROP TABLE IF EXISTS #categoriasBeneficiario;
    DROP TABLE IF EXISTS #categoriasBeneficiarioFinal;

    -- Carga de categorías del beneficiario
    CREATE TABLE #categoriasBeneficiario (
        catId BIGINT,
        catIdAfiliado BIGINT,
        catCategoriaPersona NVARCHAR(20),
        catClasificacion NVARCHAR(50),
        catFechaCambioCategoria DATETIME,
        catAfiliadoPrincipal NVARCHAR(20),
        catMotivoCambioCategoria NVARCHAR(100),
        catTipoAfiliado NVARCHAR(50),
        propiedad NVARCHAR(20),
        prioridad INT,
        tarifaUVT VARCHAR(50)
    );

    INSERT INTO #categoriasBeneficiario (
        catId, catIdAfiliado, catCategoriaPersona, catClasificacion, 
        catFechaCambioCategoria, catAfiliadoPrincipal, 
        catMotivoCambioCategoria, catTipoAfiliado, propiedad, 
        prioridad, tarifaUVT
    )
    SELECT 
        c.catId, c.catIdAfiliado, c.catCategoriaPersona, c.catClasificacion,
        c.catFechaCambioCategoria, c.catAfiliadoPrincipal,
        c.catMotivoCambioCategoria, c.catTipoAfiliado,
        'BENEFICIARIO', 0, catTarifaUVTPersona
    FROM Categoria c WITH (NOLOCK)
    INNER JOIN Beneficiario b ON b.benId = c.catIdBeneficiario
    WHERE c.catIdAfiliado = @idafiliado
      AND b.benId = @idBeneficiario;

    -- Evaluar condiciones de inactividad del beneficiario
    IF @estadoBeneficiario = 'INACTIVO'
    BEGIN
        SELECT TOP 1 @fechaRetiroAfiliadoUltima = CONVERT(DATE, r.roaFechaRetiro)
        FROM RolAfiliado r
        WHERE r.roaAfiliado = @idafiliado
          AND r.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
        ORDER BY r.roaFechaRetiro DESC;

        IF NOT EXISTS (
            SELECT 1
            FROM RolAfiliado r WITH (NOLOCK)
            INNER JOIN Afiliado a WITH (NOLOCK) ON a.afiId = r.roaAfiliado
            WHERE a.afiId = @idafiliado
              AND r.roaTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
              AND roaEstadoAfiliado = 'ACTIVO'
        )
        AND @fechaRetiroBeneficiario = @fechaRetiroAfiliadoUltima
        BEGIN
            IF (
                SELECT CONVERT(DATE, a.afifechaFinServicioSinAfiliacion)
                FROM Afiliado a
                WHERE a.afiId = @idafiliado
            ) > CONVERT(DATE, GETDATE())
            BEGIN
                DELETE FROM #categoriasBeneficiario
                WHERE catId = (
                    SELECT TOP 1 c.catId
                    FROM #categoriasBeneficiario c
                    WHERE c.catCategoriaPersona = 'SIN_CATEGORIA'
                      AND c.catTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
                    ORDER BY c.catFechaCambioCategoria DESC
                );
            END
        END
    END

    -- Tabla final de categorías
    CREATE TABLE #categoriasBeneficiarioFinal (
        ctaId BIGINT,
        fechaCambioCategoria DATETIME,
        tipoAfiliado VARCHAR(50),
        categoria VARCHAR(20),
        motivoCambioCategoria VARCHAR(100),
        propiedad NVARCHAR(20),
        prioridad INT,
        tarifaUVT VARCHAR(50),
        fechaFinServicioSinAfiliacion DATETIME
    );

    INSERT INTO #categoriasBeneficiarioFinal (
        ctaId, fechaCambioCategoria, tipoAfiliado, 
        categoria, motivoCambioCategoria, prioridad, 
        tarifaUVT, fechaFinServicioSinAfiliacion
    )
    SELECT 
        cb.catId, cb.catFechaCambioCategoria, cb.catTipoAfiliado,
        cb.catCategoriaPersona, cb.catMotivoCambioCategoria,
        cb.prioridad, cb.tarifaUVT, NULL
    FROM #categoriasBeneficiario cb;

    -- Resultado final ordenado
    SELECT 
        CONVERT(VARCHAR, c.fechaCambioCategoria, 23) AS fechaCambioCategoria,
        c.tipoAfiliado,
        c.categoria,
        c.motivoCambioCategoria,
        c.tarifaUVT,
        CASE 
            WHEN c.fechaFinServicioSinAfiliacion IS NOT NULL THEN c.fechaFinServicioSinAfiliacion
            WHEN c.categoria = 'SIN_CATEGORIA' THEN c.fechaCambioCategoria
            ELSE NULL
        END AS fechaFinServicioSinAfiliacion
    FROM #categoriasBeneficiarioFinal c
    ORDER BY c.prioridad DESC, c.fechaCambioCategoria DESC, c.ctaId DESC, c.categoria DESC;

    RETURN;
END;
