-- =============================================
-- Author:		Claudia Milena Marín Hincapié
-- Create date: 2019/03/15
-- Description:	Procedimiento almacenado encargado de consultar los aportantes para los criterios de gestión de cobro
-- HU-22-229
-- =============================================
CREATE PROCEDURE [dbo].[USP_ExecuteCARTERAConsultarAportantesGestionCobroPersonas]
	@estadoAfiliacion VARCHAR(41),	--Estado afiliación de la entidad 'ACTIVO' 	
	@estadoCartera VARCHAR(13),		--Estados de cartera posibles valores (AL_DIA, MOROSO)
	@lineaCobro VARCHAR(3),			--Linea de cobro para el empleador  (LC1,LC2,LC3)				
	@estadoOperacion VARCHAR(10),	--Estado de operación de la cartera 'VIGENTE' 	
	@valorPromedioAportes NUMERIC(19,5), --Valor promedio de aportes acorde al SMMLV
	@periodoAporteInicial VARCHAR(7),--Periodo del aporte inicial, se tiene en cuenta la cantidad de periodos
	@periodoAporteFinal VARCHAR(7),	--Periodo del aporte 
	@aplicaCriterio BIT,			--EL 0 ES UNA VARIABLE, aplicar filtro = 0 es no aplica
	@periodoEvaluarInicial DATE,	--Periodo a evaluar inicial, se tiene en cuenta los periodos morosos 
	@periodoEvaluarFinal DATE,		--Periodo a evaluar final
	@corte BIGINT,					--Corte indica la cantidad de datos maximos que traera la consulta de los aportantes que aplican con los criterios 
	@criterio1Promedio NUMERIC(19,5),	--Porcentaje Valor promedio aportes mensuales
	@criterio3VecesMoroso NUMERIC(19,5),	--Porcentaje veces moroso en cartera 
	@tipoSolicitante VARCHAR(13),	--Tipo de solicitante independiente o pensionado
	@tipoAfiliado VARCHAR(24)		--Tipo de afiliado Trabajador independiente o pensionado
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
	estadoCartera VARCHAR(6)
)

DECLARE @Filtros3PromedioAportes TABLE(
	apgPersona BIGINT,
	apgTipoSolicitante VARCHAR(13),
	promedio NUMERIC(19,5)
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
INNER JOIN ExclusionCartera exc ON per.perId = exc.excPersona AND exc.excTipoSolicitante = @tipoSolicitante
	AND exc.excTipoExclusionCartera IN ('EXCLUSION_NEGOCIO','IMPOSICION_RECURSO','ACLARACION_MORA')
	AND exc.excEstadoExclusionCartera IN ('ACTIVA')

INSERT INTO @AportantesConvenioExclusion (perId)
SELECT DISTINCT per.perId FROM Persona per	
INNER JOIN ConvenioPago cop ON per.perId = cop.copPersona AND cop.copTipoSolicitante = @tipoSolicitante
	AND cop.copEstadoConvenioPago in ('ACTIVO')


-- Se hace acotación de la cartera más antigua para el caso de varios periodos en mora (CarteraAgrupadora)
INSERT INTO @Filtros1Y2EstadosAportante (
	personaId, tipoIdentificacion,	numeroIdentificacion, razonSocial, tipoSolicitante, empresaId, empleadorId, estadoEmpleador, estadoCartera
)
SELECT DISTINCT pers.perId, pers.perTipoIdentificacion, pers.perNumeroIdentificacion, 
CASE WHEN pers.perRazonSocial !=NULL THEN pers.perRazonSocial ELSE pers.perPrimerNombre+' '+pers.perSegundoNombre+' '+pers.perPrimerApellido+' '+pers.perSegundoApellido END,
cart.carTipoSolicitante,cart.carEstadoCartera
FROM Persona pers
INNER JOIN Afiliado afi ON afi.afiPersona = pers.perId
INNER JOIN RolAfiliado roa ON roa.roaAfiliado= afi.afiId
INNER JOIN Cartera cart ON cart.carpersona = pers.perId 
INNER JOIN (
	SELECT MIN(cagCartera) AS cagCartera
	FROM CarteraAgrupadora carg
	GROUP BY carg.cagNumeroOperacion
) AS cagt ON cagt.cagCartera = cart.carId 
WHERE roa.roaEstadoAfiliado = @estadoAfiliacion
AND roa.roaTipoAfiliado = @tipoAfiliado
AND cart.carEstadoCartera in (SELECT estadoCartera FROM @estadosCartera) 
AND cart.carTipoLineaCobro = @lineaCobro 
AND cart.carEstadoOperacion = @estadoOperacion
AND pers.perId not in (SELECT perId FROM @AportantesConvenioExclusion)

--Falta agregar la validación que el promedio de aportes sea menor a la parametrización de acuerdo al salario minimo
INSERT INTO @Filtros3PromedioAportes (
	apgPersona, apgTipoSolicitante,	promedio
)
SELECT 
	ap.apgPersona, ap.apgTipoSolicitante, AVG(ap.apgValTotalApoObligatorio) AS promedio 
FROM AporteGeneral ap 
INNER JOIN @Filtros1Y2EstadosAportante f1y2 ON f1y2.empresaId = ap.apgEmpresa
WHERE ap.apgTipoSolicitante = @tipoSolicitante
AND ap.apgperiodoAporte BETWEEN @periodoAporteInicial
							AND @periodoAporteFinal
GROUP BY ap.apgEmpresa, ap.apgTipoSolicitante  

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
 f3.promedio, f5.totalVecesMoroso
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
				SELECT CAST(ROUND(COUNT(*) * @criterio3VecesMoroso, 0) AS INTEGER) 
				FROM @Filtros5MorosoAlgunaVez fc3
			) 
		fc3.*
	FROM @Filtros5MorosoAlgunaVez fc3
) AS f5 ON f1y2.personaId = f5.carPersona
