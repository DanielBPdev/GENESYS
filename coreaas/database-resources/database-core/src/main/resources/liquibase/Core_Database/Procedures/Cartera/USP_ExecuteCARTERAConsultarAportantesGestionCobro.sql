-- =============================================
-- Author:		Claudia Milena Marín Hincapié
-- Create date: 2019/03/15
-- Description:	Procedimiento almacenado encargado de consultar los aportantes para los criterios de gestión de cobro
-- HU-22-229
-- =============================================
CREATE PROCEDURE [dbo].[USP_ExecuteCARTERAConsultarAportantesGestionCobro]
	@estadoAfiliacion VARCHAR(41),	--Estado afiliación de la entidad 'ACTIVO' 	
	@estadoCartera VARCHAR(13),		--Estados de cartera posibles valores (AL_DIA, MOROSO)
	@lineaCobro VARCHAR(3),			--Linea de cobro para el empleador  (LC1,LC2,LC3)				
	@estadoOperacion VARCHAR(10),	--Estado de operación de la cartera 'VIGENTE' 	
	@valorPromedioAportes NUMERIC(19,5), --Valor promedio de aportes acorde al SMMLV
	@periodoAporteInicial VARCHAR(7),--Periodo del aporte inicial, se tiene en cuenta la cantidad de periodos
	@periodoAporteFinal VARCHAR(7),	--Periodo del aporte 
	@trabajadoresActivos  INT,		--Cantidad de trabajadores activos relacionados al empleador
	@aplicaCriterio BIT,			--EL 0 ES UNA VARIABLE, aplicar filtro = 0 es no aplica
	@periodoEvaluarInicial DATE,	--Periodo a evaluar inicial, se tiene en cuenta los periodos morosos 
	@periodoEvaluarFinal DATE,		--Periodo a evaluar final
	@corte BIGINT,					--Corte indica la cantidad de datos maximos que traera la consulta de los aportantes que aplican con los criterios 
	@criterio1Promedio NUMERIC(19,5),	--Porcentaje Valor promedio aportes mensuales
	@criterio2Activos NUMERIC(19,5),	--Porcentaje Cantidad de trabajadores activos
	@criterio3VecesMoroso NUMERIC(19,5)	--Porcentaje veces moroso en cartera 
AS
SET NOCOUNT ON

DECLARE @estadosCartera TABLE (estadoCartera VARCHAR(6))

INSERT INTO @estadosCartera (estadoCartera)
	SELECT Data FROM dbo.Split(@estadoCartera,',') 

DECLARE @Filtros1Y2EstadosAportante TABLE(
	personaId BIGINT,
	tipoIdentificacion VARCHAR(20),
	numeroIdentificacion VARCHAR(16),
	razonSocial VARCHAR(250),
	tipoSolicitante VARCHAR(13),
	empresaId BIGINT,
	empleadorId BIGINT,
	estadoEmpleador VARCHAR(8),
	estadoCartera VARCHAR(6)
)

DECLARE @Filtros3PromedioAportes TABLE(
	apgEmpresa BIGINT,
	apgTipoSolicitante VARCHAR(13),
	promedio NUMERIC(19,5)
)

DECLARE @Filtros4TrabajadoresActivos TABLE(
	empId BIGINT,
	totalTrabajadoresActivos INT
)

DECLARE @Filtros5MorosoAlgunaVez TABLE(
	carPersona BIGINT,
	totalVecesMoroso INT
)

DECLARE @AportantesConvenioExclusion TABLE(
	perId BIGINT
)

DECLARE @smlv NUMERIC(19,5)

SELECT @smlv = prm.prmValor*@valorPromedioAportes FROM Parametro prm WHERE prm.prmNombre='SMMLV'  

INSERT INTO @AportantesConvenioExclusion (perId)
SELECT DISTINCT per.perId FROM Persona per 
INNER  JOIN CicloAportante cap ON per.perId = cap.capPersona
INNER JOIN CicloCartera ccr ON cap.capCicloCartera = ccr.ccrId AND ccr.ccrEstadoCiclo IN ('ACTIVO')
INNER JOIN SolicitudGestionCobroManual scm ON cap.capId = scm.scmCicloAportante AND scm.scmLineaCobro in (@lineaCobro)

INSERT INTO @AportantesConvenioExclusion (perId)
SELECT DISTINCT per.perId FROM Persona per
INNER JOIN ExclusionCartera exc ON per.perId = exc.excPersona AND exc.excTipoSolicitante = 'EMPLEADOR'
	AND exc.excTipoExclusionCartera IN ('EXCLUSION_NEGOCIO','IMPOSICION_RECURSO','ACLARACION_MORA')
	AND exc.excEstadoExclusionCartera IN ('ACTIVA')

INSERT INTO @AportantesConvenioExclusion (perId)
SELECT DISTINCT per.perId FROM Persona per	
INNER JOIN ConvenioPago cop ON per.perId = cop.copPersona AND cop.copTipoSolicitante = 'EMPLEADOR'
	AND cop.copEstadoConvenioPago in ('ACTIVO')


-- Se hace acotación de la cartera más antigua para el caso de varios periodos en mora (CarteraAgrupadora)
INSERT INTO @Filtros1Y2EstadosAportante (
	personaId, tipoIdentificacion,	numeroIdentificacion, razonSocial, tipoSolicitante, empresaId, empleadorId, estadoEmpleador, estadoCartera
)
SELECT DISTINCT pers.perId, pers.perTipoIdentificacion, pers.perNumeroIdentificacion, pers.perRazonSocial, cart.carTipoSolicitante, empr.empId, empl.empId, empl.empEstadoEmpleador, cart.carEstadoCartera
FROM Persona pers
INNER JOIN Empresa empr ON empr.empPersona = pers.perId
INNER JOIN Empleador empl ON empl.empEmpresa = empr.empId
INNER JOIN Cartera cart ON cart.carpersona = pers.perId 
INNER JOIN (
	SELECT MIN(cagCartera) AS cagCartera
	FROM CarteraAgrupadora carg
	GROUP BY carg.cagNumeroOperacion
) AS cagt ON cagt.cagCartera = cart.carId 
WHERE empl.empEstadoEmpleador = @estadoAfiliacion
AND cart.carEstadoCartera in (SELECT estadoCartera FROM @estadosCartera) 
AND cart.carTipoLineaCobro = @lineaCobro 
AND cart.carEstadoOperacion = @estadoOperacion
AND pers.perId not in (SELECT perId FROM @AportantesConvenioExclusion)

--Falta agregar la validación que el promedio de aportes sea menor a la parametrización de acuerdo al salario minimo
INSERT INTO @Filtros3PromedioAportes (
	apgEmpresa, apgTipoSolicitante,	promedio
)
SELECT 
	ap.apgEmpresa, ap.apgTipoSolicitante, AVG(ap.apgValTotalApoObligatorio) AS promedio 
FROM AporteGeneral ap 
INNER JOIN @Filtros1Y2EstadosAportante f1y2 ON f1y2.empresaId = ap.apgEmpresa
WHERE ap.apgTipoSolicitante = 'EMPLEADOR'
AND ap.apgperiodoAporte BETWEEN @periodoAporteInicial
							AND @periodoAporteFinal
GROUP BY ap.apgEmpresa, ap.apgTipoSolicitante  

INSERT INTO @Filtros4TrabajadoresActivos (
	empId, totalTrabajadoresActivos 
)
SELECT roa.roaEmpleador, COUNT(roa.roaId) AS total
FROM RolAfiliado roa 
INNER JOIN @Filtros1Y2EstadosAportante f1y2 on f1y2.empleadorId = roa.roaEmpleador
WHERE roa.roaEstadoAfiliado = @estadoAfiliacion
GROUP BY roa.roaEmpleador
HAVING COUNT(roa.roaId) >= @trabajadoresActivos  

INSERT INTO @Filtros5MorosoAlgunaVez (
	carPersona, totalVecesMoroso
)
SELECT DISTINCT 
	cart.carPersona, COUNT(*)
FROM Cartera cart 
INNER JOIN @Filtros1Y2EstadosAportante f1y2 ON f1y2.personaId = cart.carPersona
WHERE cart.carTipoLineaCobro = @lineaCobro
AND (0 = @aplicaCriterio
	OR cart.carPeriodoDeuda BETWEEN @periodoEvaluarInicial
								AND @periodoEvaluarFinal
)
GROUP BY cart.carPersona

SELECT TOP(@corte) f1y2.tipoIdentificacion, f1y2.numeroIdentificacion, f1y2.razonSocial, f1y2.tipoSolicitante, f1y2.estadoCartera,
 f3.promedio, f4.totalTrabajadoresActivos, f5.totalVecesMoroso
FROM @Filtros1Y2EstadosAportante f1y2 
INNER JOIN (
	SELECT TOP (
				SELECT CAST(ROUND(COUNT(*) * @criterio1Promedio, 0) AS INTEGER)
				FROM @Filtros3PromedioAportes fc1 
			) 
		fc1.*
	FROM @Filtros3PromedioAportes fc1
) AS f3 ON f1y2.empresaId = f3.apgEmpresa
INNER JOIN (
	SELECT TOP (
				SELECT CAST(ROUND(COUNT(*) * @criterio2Activos, 0) AS INTEGER) 
				FROM @Filtros4TrabajadoresActivos fc2 
			) 
		fc2.*
	FROM @Filtros4TrabajadoresActivos fc2
) AS f4 ON f1y2.empleadorId = f4.empId
INNER JOIN (
	SELECT TOP (
				SELECT CAST(ROUND(COUNT(*) * @criterio3VecesMoroso, 0) AS INTEGER) 
				FROM @Filtros5MorosoAlgunaVez fc3
			) 
		fc3.*
	FROM @Filtros5MorosoAlgunaVez fc3
) AS f5 ON f1y2.personaId = f5.carPersona
