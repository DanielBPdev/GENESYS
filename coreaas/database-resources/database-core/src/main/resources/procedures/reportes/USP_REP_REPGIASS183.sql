/****** Object:  StoredProcedure [dbo].[USP_REP_REPGIASS183]    Script Date: 2023-10-13 8:43:35 AM ******/
/****** Object:  StoredProcedure [dbo].[USP_REP_REPGIASS183]    Script Date: 2023-09-27 9:09:42 AM ******/
/****** Object:  StoredProcedure [dbo].[USP_REP_REPGIASS183]    Script Date: 2023-09-20 10:18:32 AM ******/
 
/****** Object:  StoredProcedure [dbo].[USP_REP_REPGIASS183]    Script Date: 2023-09-13 12:07:28 PM ******/
-- =============================================
-- Author:		Pedro Luis Parra Gamboa
-- Create date: 2021/01/29
-- Description:	Genera reporte de Giass 183 GLPI 70408 
-- =============================================
CREATE OR ALTER   PROCEDURE [dbo].[USP_REP_REPGIASS183]
AS
BEGIN
SET ANSI_WARNINGS OFF
SET NOCOUNT ON

--REPORTE 183 GIASS
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
	WHEN 'SALVOCONDUCTO' THEN 'SC'
	ELSE ''
	END AS 'C94',
perNumeroIdentificacion AS 'C95',
SUBSTRING (perPrimerApellido, 1,20) AS 'C96',
ISNULL (SUBSTRING (perSegundoApellido, 1,30), '') AS 'C97',
SUBSTRING (perPrimerNombre, 1,20) AS 'C98',
ISNULL (SUBSTRING (perSegundoNombre, 1,30), '') AS 'C99',
CASE WHEN ISNULL(coiInvalidez, 0) = 1  THEN '1'
	--- WHEN ISNULL(coiInvalidez, 0) = 0  THEN 2
	-- WHEN ISNULL(coiInvalidez, 0) = 1 AND T.afiId IS NOT NULL THEN 3 como se sabe que es rural 
	ELSE ''
	END AS 'C746',
cast (pedFechaNacimiento AS VARCHAR (10)) AS 'C101',
CASE WHEN pedGenero = 'FEMENINO' THEN 'F' ELSE 'M' END AS 'C100',
'170' AS 'C102',-- Pendiente agregar tabla de ISO 3166
concat (CASE WHEN pedNivelEducativo IN ('NINGUNO', 'PRIMERA_INFANCIA', 'PREESCOLAR') OR isnull(graNivelEducativo,'0')='0' THEN 0
	WHEN pedNivelEducativo IN ('BASICA_PRIMARIA_COMPLETA_ADULTOS', 'BASICA_PRIMARIA_INCOMPLETA_ADULTOS', 'BASICA_PRIMARIA_COMPLETA', 'BASICA_PRIMARIA_INCOMPLETA') THEN 1
	WHEN pedNivelEducativo IN ('MEDIA_COMPLETA_ADULTOS', 'MEDIA_INCOMPLETA_ADULTOS', 'BASICA_SECUNDARIA_COMPLETA_ADULTOS', 'BASICA_SECUNDARIA_INCOMPLETA_ADULTOS', 
	'MEDIA_COMPLETA', 'MEDIA_INCOMPLETA', 'BASICA_SECUNDARIA_COMPLETA', 'BASICA_SECUNDARIA_INCOMPLETA') THEN 2
	WHEN pedNivelEducativo = 'EDUCACION_PARA_TRABAJO_Y_DESARROLLO_HUMANO'  THEN 3
	WHEN pedNivelEducativo IN ('SUPERIOR', 'SUPERIOR_PREGRADO') THEN 4
	WHEN pedNivelEducativo = 'SUPERIOR_POSGRADO' THEN 5
	ELSE 0
	END ,';') AS 'C160'
FROM Persona
INNER JOIN PersonaDetalle WITH(NOLOCK) ON pedPersona = perId
INNER JOIN Beneficiario  WITH(NOLOCK) ON benPersona = perId
--INNER JOIN GrupoFamiliar  WITH(NOLOCK) ON grfId = benGrupoFamiliar
--INNER JOIN Ubicacion  WITH(NOLOCK) ON ubiId = grfUbicacion
LEFT JOIN CondicionInvalidez  WITH(NOLOCK) ON coiPersona = perId
LEFT JOIN (SELECT DISTINCT afiId FROM Afiliado  WITH(NOLOCK) 
			INNER JOIN RolAfiliado  WITH(NOLOCK) ON roaAfiliado = afiId
			INNER JOIN SucursalEmpresa  WITH(NOLOCK)  ON sueId = roaSucursalEmpleador
			INNER JOIN CodigoCIIU  WITH(NOLOCK)  ON ciiId = sueCodigoCIIU
			WHERE LEFT (ciiCodigo, 2) IN ('01', '02')
			) AS T
ON benAfiliado = T.afiId
LEFT JOIN GradoAcademico ON graId = pedGradoAcademico
WHERE 
-- benFechaAfiliacion <= @fechaHora 
--AND (benEstadoBeneficiarioAfiliado = 'ACTIVO' OR (benEstadoBeneficiarioAfiliado = 'INACTIVO' AND benFechaRetiro > @fechaHora))
 --AND 
 benEstadoBeneficiarioAfiliado IS NOT NULL
 AND benFechaAfiliacion IS NOT NULL
--OBSERVACIONES
/*No se tiene información para los campos de nacionalidad se requiere verificar que hacer con este campo*/
END