CREATE
OR ALTER PROCEDURE
[dbo].[USP_REP_ConsultarCarteraPrescrita]
AS
BEGIN
    DECLARE @iRevision BIGINT
    DECLARE @carPersona BIGINT
    DECLARE @IdCartera BIGINT
    DECLARE @operacion VARCHAR(20)
    DECLARE @tipoSolicitante VARCHAR(20)
    DECLARE @periodo DATE
    DECLARE @Cartera TABLE
                     (
                         Id              BIGINT IDENTITY,
                         idCartera       BIGINT,
                         fechaBitacora   DATE,
                         numeroOperacion VARCHAR(20),
                         carPersona      BIGINT,
                         tipoSolicitante VARCHAR(20),
                         periodo         DATE
                     );

INSERT INTO @Cartera
SELECT car.carid,
       b.bcaFecha,
       b.bcaNumeroOperacion,
       car.carPersona,
       b.bcaTipoSolicitante,
       car.carPeriodoDeuda
FROM cartera car WITH (NOLOCK)
             INNER JOIN CarteraAgrupadora c WITH (NOLOCK) ON car.carId = c.cagCartera
    INNER JOIN BitacoraCartera b WITH (NOLOCK) ON b.bcaNumeroOperacion = c.cagNumeroOperacion
WHERE car.carTipoLineaCobro IN ('LC1', 'LC4', 'LC5', 'C6')
  AND car.carEstadoOperacion = 'VIGENTE'
  AND car.carDeudaPresunta > 0
  AND b.bcaActividad IN ('GENERAR_LIQUIDACION', 'LC5A', 'LC4A')
  AND b.bcaActividad NOT IN ('CARTERA_PRESCRITA')
  AND DATEDIFF(DAY, bcafecha, GETDATE()) >= (SELECT cnsValor
    FROM Constante
    WHERE cnsNombre = 'CARTERA_PRESCRIBIR_DIAS_CONTEO_GENERAR_LIQUIDACION')
GROUP BY b.bcaNumeroOperacion, car.carid, b.bcaactividad, b.bcaFecha, carPersona, b.bcaTipoSolicitante,
    car.carPeriodoDeuda

INSERT INTO @Cartera
SELECT car.carid,
       car.carFechaCreacion,
       b.bcaNumeroOperacion,
       car.carPersona,
       b.bcaTipoSolicitante,
       car.carPeriodoDeuda
FROM cartera car WITH (NOLOCK)
             INNER JOIN CarteraAgrupadora c WITH (NOLOCK) ON car.carId = c.cagCartera
    INNER JOIN BitacoraCartera b WITH (NOLOCK) ON b.bcaNumeroOperacion = c.cagNumeroOperacion
WHERE car.carTipoLineaCobro IN ('LC1')
  AND car.carEstadoOperacion = 'VIGENTE'
  AND car.carDeudaPresunta > 0
  AND EXISTS(SELECT *
    FROM BitacoraCartera
    WHERE BitacoraCartera.bcaActividad = 'CARTERA_PRESCRITA'
  AND bcapersona = car.carpersona)
  AND b.bcaActividad = 'GENERAR_LIQUIDACION'
  AND DATEDIFF(DAY, carFechaCreacion, GETDATE()) >= (SELECT cnsValor
    FROM Constante
    WHERE cnsNombre = 'CARTERA_PRESCRIBIR_DIAS_CONTEO_GENERAR_LIQUIDACION')
GROUP BY b.bcaNumeroOperacion, car.carid, b.bcaactividad, b.bcaFecha, car.carFechaCreacion, carPersona,
    b.bcaTipoSolicitante, car.carPeriodoDeuda

DECLARE @loop_counterCartera INT,@counter INT = 1

    SET @loop_counterCartera = (SELECT COUNT(*) FROM @Cartera);

    WHILE @counter <= @loop_counterCartera
BEGIN
SELECT @IdCartera = idCartera,
       @operacion = numeroOperacion,
       @carPersona = carPersona,
       @tipoSolicitante = tipoSolicitante,
       @periodo = periodo
FROM @Cartera
WHERE Id = @counter
BEGIN
UPDATE Cartera
SET carDeudaPresunta=0,
    carEstadoCartera='AL_DIA',
    carEstadoOperacion='NO_VIGENTE',
    carPrescribir='PRESCRIBIR'
WHERE carId = @IdCartera

UPDATE CarteraDependiente
SET cadDeudaPresunta=0,
    cadDeudaReal=0,
    cadEstadoOperacion='NO_VIGENTE'
WHERE cadCartera = @IdCartera

    INSERT INTO BitacoraCartera
VALUES (GETDATE(), 'CARTERA_PRESCRITA', 'PERSONAL', 'OTRO', 'service-account-clientes_web',
    @carPersona, @tipoSolicitante, @operacion, @periodo);


EXEC USP_UTIL_GET_CrearRevision 'com.asopagos.entidades.ccf.cartera.Cartera', 1,
                     '',
                     'USUARIO_SISTEMA', @iRevision OUTPUT
                INSERT INTO aud.Cartera_aud (carDeudaPresunta, carEstadoCartera,
                                             carEstadoOperacion,
                                             carFechaCreacion, carPersona, carMetodo,
                                             carPeriodoDeuda,
                                             carDeudaPresuntaUnitaria, carTipoAccionCobro,
                                             carTipoDeuda,
                                             carTipoLineaCobro, carTipoSolicitante, carId, carDeudaParcial,
                                             carPrescribir, REV,
                                             REVTYPE)
SELECT carDeudaPresunta,
       carEstadoCartera,
       carEstadoOperacion,
       carFechaCreacion,
       carPersona,
       carMetodo,
       carPeriodoDeuda,
       carDeudaPresuntaUnitaria,
       carTipoAccionCobro,
       carTipoDeuda,
       carTipoLineaCobro,
       carTipoSolicitante,
       carId,
       carDeudaParcial,
       carPrescribir,
       @iRevision,
       1
FROM Cartera
WHERE carId = @IdCartera
    EXEC USP_UTIL_GET_CrearRevision
                     'com.asopagos.entidades.ccf.cartera.CarteraDependiente',
                     1, '', 'USUARIO_SISTEMA',
                     @iRevision OUTPUT

INSERT INTO aud.CarteraDependiente_aud (cadDeudaPresunta,
                                        cadEstadoOperacion,
                                        cadCartera, cadPersona,
                                        cadDeudaReal,
                                        cadAgregadoManual, cadId, REV,
                                        REVTYPE)
SELECT cad.cadDeudaPresunta,
       cad.cadEstadoOperacion,
       cad.cadCartera,
       cad.cadPersona,
       ISNULL(cad.cadDeudaReal, 0),
       cad.cadAgregadoManual,
       cad.cadId,
       @iRevision,
       1
FROM CarteraDependiente cad
WHERE cadCartera = @IdCartera

    SET @counter = @counter + 1;
END
END
END
