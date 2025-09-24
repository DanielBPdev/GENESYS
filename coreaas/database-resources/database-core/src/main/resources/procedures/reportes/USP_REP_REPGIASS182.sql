/****** Object:  StoredProcedure [dbo].[USP_REP_REPGIASS182]    Script Date: 2023-09-20 10:39:08 AM ******/
/****** Object:  StoredProcedure [dbo].[USP_REP_REPGIASS182]    Script Date: 2023-09-13 12:08:11 PM ******/
-- =============================================
-- Author:		Pedro Luis Parra Gamboa
-- Create date: 2021/01/29
-- Description:	Genera reporte de Giass 182
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_REP_REPGIASS182]
AS
BEGIN
SET ANSI_WARNINGS OFF
SET NOCOUNT ON


--REPORTE 182 GIASS
--Calculo de las fechas de reporte
DECLARE @fecha DATE = dbo.getLocalDate(),
		@fechaHora DATETIME ;
SET @fechaHora = DATEADD (HOUR,16,CONVERT (DATETIME,@fecha)); 

SELECT DISTINCT
CASE perTipoIdentificacion 
     WHEN 'CEDULA_CIUDADANIA' THEN 'CC'
	WHEN 'CEDULA_EXTRANJERIA' THEN 'CE'
	WHEN 'NIT' THEN 'NI'
	WHEN 'TARJETA_IDENTIDAD' THEN 'TI'
	WHEN 'PASAPORTE' THEN 'PA'
	WHEN 'PERM_PROT_TEMPORAL' THEN 'PT'
	WHEN 'PERM_ESP_PERMANENCIA' THEN 'PE' 
	WHEN 'CARNE_DIPLOMATICO' THEN 'CD' 
	WHEN 'REGISTRO_CIVIL' THEN 'RC'
	WHEN 'SALVOCONDUCTO' THEN 'SC'---SE ADICIONA PERMISO ESPECIAL EL DIA 20210518 Y PORQUE ES OBLIGATORIO
	ELSE ''
	END 
AS 'C47',
perNumeroIdentificacion AS 'C48',
SUBSTRING (perPrimerApellido, 1,20) AS 'C49',
ISNULL (SUBSTRING (perSegundoApellido, 1,30), '') AS 'C50',
SUBSTRING (perPrimerNombre, 1,20) AS 'C51',
ISNULL (SUBSTRING (perSegundoNombre, 1,30), '') AS 'C52',
CAST (pedFechaNacimiento AS VARCHAR (10)) AS 'C54',
CASE pedGenero WHEN 'FEMENINO' THEN 'F' 
	WHEN 'MASCULINO' THEN 'M' 
	ELSE ''
	END AS 'C53',
depCodigo AS 'C209',
RIGHT (munCodigo, 3) AS 'C210',
'170' AS 'C55', --Pendiente carga tabla ISO 3166
'' AS 'C196', -- No se tiene ampo de departamento de expedición de documento
'' AS 'C142', -- NO se tiene campo de municipio de expedición de documento
concat (ISNULL (cast (pedFechaExpedicionDocumento AS VARCHAR (10)), ''),';') AS 'C143'--, CONVERT (DATETIME, roaFechaAfiliacion)
FROM persona
INNER JOIN PersonaDetalle WITH(NOLOCK) ON pedPersona = perId
INNER JOIN Afiliado WITH(NOLOCK) ON afiPersona = perId
INNER JOIN RolAfiliado WITH(NOLOCK) ON roaAfiliado = afiId
INNER JOIN SolicitudAfiliacionPersona WITH(NOLOCK) ON sapRolAfiliado = roaId
INNER JOIN Solicitud WITH(NOLOCK) ON solId = sapSolicitudGlobal
--DATOS DE UBICACIÓN
LEFT JOIN Ubicacion WITH(NOLOCK) ON ubiId = perUbicacionPrincipal
LEFT JOIN Municipio WITH(NOLOCK) ON munId = ubiMunicipio
LEFT JOIN Departamento WITH(NOLOCK) ON depId = munDepartamento
-- Nacionalidad
LEFT JOIN Pais pai WITH(NOLOCK) ON pai.paiId = pedPaisResidencia
WHERE sapEstadoSolicitud = 'CERRADA' AND solResultadoProceso = 'APROBADA'
 AND roaEstadoAfiliado in ( 'INACTIVO' ,'ACTIVO')
--AND roaFechaAfiliacion <=  @fechaHora  AND (roaEstadoAfiliado = 'ACTIVO' OR (roaEstadoAfiliado = 'INACTIVO' AND roaFechaRetiro >  @fechaHora))

--OBSERVACIONES
/*No se tiene información para los campos de nacionalidad, municipio y departamento de nacimiento del afiliado, se requiere verificar que hacer con estos campos*/

--SELECT * FROM PersonaDetalle
--SELECT * FROM Pais WHERE paiDescripcion LIKE '%colo%'

--SELECT * FROM PersonaDetalle
--INNER JOIN Persona ON perId = pedPersona
--INNER JOIN Pais ON paiId = pedPaisResidencia
--WHERE perNumeroIdentificacion = '105379658'

--SELECT * FROM Persona 
--INNER JOIN PersonaDetalle ON pedPersona = perId
--WHERE perNumeroIdentificacion = '105379658'
END