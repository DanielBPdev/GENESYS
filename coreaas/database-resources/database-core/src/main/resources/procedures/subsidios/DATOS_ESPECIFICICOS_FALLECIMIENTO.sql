CREATE PROCEDURE [dbo].[DATOS_ESPECIFICICOS_FALLECIMIENTO]
(
@TipoId NVARCHAR (30),
@Id NVARCHAR (30)
)

AS
BEGIN TRY

TRUNCATE TABLE fall.MedioDePago;
TRUNCATE TABLE fall.GrupoFamiliar;
TRUNCATE TABLE fall.Empresa;
TRUNCATE TABLE fall.SucursalEmpresa;
TRUNCATE TABLE fall.Empleador;
TRUNCATE TABLE fall.RolAfiliado;
TRUNCATE TABLE fall.AdministradorSubsidio;
TRUNCATE TABLE fall.AdminSubsidioGrupo;
TRUNCATE TABLE fall.Solicitud;
TRUNCATE TABLE fall.ParametrizacionNovedad;
TRUNCATE TABLE fall.SolicitudNovedad;
TRUNCATE TABLE fall.SolicitudNovedadPersona;
TRUNCATE TABLE fall.NovedadDetalle;
TRUNCATE TABLE fall.AporteGeneral;
TRUNCATE TABLE fall.AporteDetallado;
TRUNCATE TABLE fall.SocioEmpleador;
TRUNCATE TABLE fall.PersonaDetalle;
TRUNCATE TABLE fall.BeneficiarioDetalle;
TRUNCATE TABLE fall.Beneficiario;
TRUNCATE TABLE fall.BeneficioEmpleador;
TRUNCATE TABLE fall.CondicionInvalidez;
TRUNCATE TABLE fall.SolicitudNovedadEmpleador;
TRUNCATE TABLE fall.CertificadoEscolarBeneficiario;
TRUNCATE TABLE fall.CargueBloqueoCuotaMonetaria;
TRUNCATE TABLE fall.BloqueoBeneficiarioCuotaMonetaria;
TRUNCATE TABLE fall.ItemChequeo;
TRUNCATE TABLE fall.SolicitudAfiliacionPersona;
TRUNCATE TABLE fall.Persona;
TRUNCATE TABLE fall.Afiliado;

DROP TABLE IF EXISTS #Table_afiId;
DROP TABLE IF EXISTS #Table_perId;
DROP TABLE IF EXISTS #Table_empId;
DROP TABLE IF EXISTS #Table_grfId;
DROP TABLE IF EXISTS #Table_solId;
DROP TABLE IF EXISTS #Table_novId;
DROP TABLE IF EXISTS #Table_snoId;
DROP TABLE IF EXISTS #Table_snpId;
DROP TABLE IF EXISTS #Table_apgId;
DROP TABLE IF EXISTS #Table_apdId;
DROP TABLE IF EXISTS #Table_bedId;
DROP TABLE IF EXISTS #Table_cabId;
DROP TABLE IF EXISTS #Table_sapId;
DROP TABLE IF EXISTS #Table_sneId;
DROP TABLE IF EXISTS #Table_asuId;
DROP TABLE IF EXISTS #Table_mdpId;

CREATE TABLE #Table_afiId (afiId INT, entidad VARCHAR (50)); 
CREATE TABLE #Table_perId (perId INT, entidad VARCHAR (50));
CREATE TABLE #Table_empId (empId INT, entidad VARCHAR (50));
CREATE TABLE #Table_grfId (grfId INT, entidad VARCHAR (50));
CREATE TABLE #Table_solId (solId BIGINT, entidad VARCHAR (50));
CREATE TABLE #Table_novId (novId BIGINT, entidad VARCHAR (50));
CREATE TABLE #Table_snoId (snoId BIGINT, entidad VARCHAR (50));
CREATE TABLE #Table_snpId (snpId BIGINT, entidad VARCHAR (50));
CREATE TABLE #Table_apgId (apgId BIGINT, entidad VARCHAR (50));
CREATE TABLE #Table_apdId (apdId BIGINT, entidad VARCHAR (50));
CREATE TABLE #Table_bedId (bedId INT, entidad VARCHAR (50));
CREATE TABLE #Table_cabId (cabId INT, entidad VARCHAR (50));
CREATE TABLE #Table_sapId (sapId BIGINT, entidad VARCHAR (50));
CREATE TABLE #Table_sneId (sneId INT, entidad VARCHAR (50));
CREATE TABLE #Table_asuId (asuId INT, entidad VARCHAR (50));
CREATE TABLE #Table_mdpId (mdpId BIGINT, entidad VARCHAR (50));


--1. Se consulta el dato de la persona del afiliado consultado

INSERT INTO #Table_afiId (afiId, entidad)
SELECT afiId, 'afiliado'
FROM Persona 
INNER JOIN Afiliado 
ON afiPersona = perId 
WHERE perNumeroIdentificacion = CONVERT (VARCHAR (30), @Id) 
AND perTipoIdentificacion = CONVERT (VARCHAR (30), @TipoId);


INSERT INTO #Table_perId (perId, entidad)
SELECT perId, 'afiliado'
FROM Persona 
INNER JOIN Afiliado 
ON afiPersona = perId 
WHERE perNumeroIdentificacion = @Id 
AND perTipoIdentificacion = @TipoId;

--2. Se consultan los datos beneficiarios del afiliado seleccionado
INSERT INTO #Table_bedId (bedId, entidad)
SELECT benBeneficiarioDetalle, 'afiliado'
FROM #Table_afiId 
INNER JOIN Beneficiario 
ON afiId = benAfiliado
GROUP BY benBeneficiarioDetalle;

--3. Se consultan los datos del afiliado principal secundario
INSERT INTO #Table_perId (perId, entidad)
SELECT afiPersona, 'SegundoAfiliadoPrincipal'
FROM #Table_bedId a 
INNER JOIN Beneficiario b
ON a.bedId = b.benBeneficiarioDetalle
INNER JOIN Afiliado afi
ON afi.afiId = b.benAfiliado
LEFT JOIN #Table_perId perId
ON perId.perId = afi.afiPersona
WHERE b.benAfiliado NOT IN (SELECT afiId FROM #Table_afiId)
AND perId.perId IS NULL
GROUP BY afi.afiPersona;

INSERT INTO #Table_afiId (afiId, entidad)
SELECT b.benAfiliado, 'SegundoAfiliadoPrincipal'
FROM #Table_bedId a 
INNER JOIN Beneficiario b
ON a.bedId = b.benBeneficiarioDetalle
WHERE b.benAfiliado NOT IN (SELECT afiId FROM #Table_afiId)
GROUP BY b.benAfiliado;

--4. Se consultas los datos de los beneficiarios del afiliado principal secundario
INSERT INTO #Table_bedId (bedId, entidad)
SELECT b.benBeneficiarioDetalle, 'SegundoAfiliadoPrincipal'
FROM #Table_afiId a 
INNER JOIN Beneficiario b
ON a.afiId = b.benAfiliado 
WHERE a.entidad = 'SegundoAfiliadoPrincipal'
AND b.benBeneficiarioDetalle NOT IN (SELECT bedId FROM #Table_bedId)
GROUP BY b.benBeneficiarioDetalle;

--5. Se consultas los datos de persona de todos los beneficiarios asociados al proceso
INSERT INTO #Table_perId (perId, entidad)
SELECT pd.pedPersona, 'beneficiario_'+a.entidad
FROM #Table_bedId a 
INNER JOIN beneficiarioDetalle b
ON a.bedId = b.bedId
INNER JOIN personaDetalle pd
ON pd.pedId = b.bedPersonaDetalle
LEFT JOIN #Table_perId perId
ON perId.perId = pd.pedPersona
WHERE perId.perId IS NULL
GROUP BY pd.pedPersona, a.entidad;

--6. Se consultas los grupos familiares de todos los beneficiarios asociados al proceso
INSERT INTO #Table_grfId (grfId, entidad)
SELECT grf.grfId, afi.entidad 
FROM #Table_afiId afi
INNER JOIN grupoFamiliar grf
ON grf.grfAfiliado = afi.afiId
GROUP  BY grf.grfId, afi.entidad;

--7. Se consultas los datos de afiliado de los beneficiarios del proceso
INSERT INTO #Table_afiId (afiId, entidad)
SELECT afi.afiId, perBen.entidad 
FROM #Table_perId perBen
INNER JOIN afiliado afi ON afi.afiPersona = perBen.perId
WHERE entidad LIKE 'beneficiario_%';

--8. Se consultas los datos de empresas relacionados a las personas (Afiliado-Beneficiario) del proceso

	--8.1- Datos de personas del proceso como empleadores 
	INSERT INTO #Table_empId (empId, entidad)
	SELECT emp.empId, (per.entidad+'_como_Empresa') 
	FROM #Table_perId per
	INNER JOIN empresa emp
	ON emp.empPersona = per.perId
	GROUP  BY emp.empId, per.entidad;


	--8.2- Datos de empleadores de afiliados del proceso 
	INSERT INTO #Table_empId (empId, entidad)
	SELECT empl.empEmpresa, (afi.entidad+'_Empleador') 
	FROM #Table_afiId afi
	INNER JOIN rolAfiliado roa
	ON afi.afiId = roa.roaAfiliado
	INNER JOIN empleador empl
	ON empl.empId = roa.roaEmpleador
	GROUP  BY empl.empEmpresa, afi.entidad;

--9. Se consultan los datos de solicitud de afiliación de los datos relacionados al proceso
INSERT INTO #Table_sapId (sapId, entidad)
SELECT sap.sapId, afi.entidad 
FROM #Table_afiId afi
INNER JOIN rolAfiliado roa
ON roa.roaAfiliado = afi.afiId
INNER JOIN solicitudAfiliacionPersona sap
ON sap.sapRolAfiliado = roa.roaId
GROUP BY sap.sapId, afi.entidad

--10. Se consultan los datos de solicitud de novedad persona de los datos relacionados al proceso
INSERT INTO #Table_snpId (snpId, entidad)
		SELECT snp.snpId, per.entidad 
		FROM #Table_perId per
		INNER JOIN solicitudNovedadPersona snp
		ON snp.snpPersona = per.perId
		GROUP BY snp.snpId, per.entidad
		EXCEPT
		SELECT snp.snpId, per.entidad 
		FROM #Table_perId per
		INNER JOIN solicitudNovedadPersona snp
		ON snp.snpPersona = per.perId
		WHERE per.entidad LIKE 'beneficiario_%'
		AND snp.snpBeneficiario IS NOT NULL
		GROUP BY snp.snpId, per.entidad;

--11. Se consultan los datos de solicitud de novedad empleador de los datos relacionados al proceso
INSERT INTO #Table_sneId (sneId, entidad)
		SELECT sne.sneId, emp.entidad 
		FROM #Table_empId emp
		INNER JOIN empleador empl
		ON empl.empEmpresa = emp.empId
		INNER JOIN solicitudNovedadEmpleador sne
		ON sne.sneIdEmpleador = empl.empId
		WHERE emp.entidad LIKE '%_Empleador'
		GROUP BY sne.sneId, emp.entidad;
		
--12. Se consultan los datos de solicitud de novedad de los datos relacionados al proceso
		--12.1. SolicitudNovedadPersona
		INSERT INTO #Table_snoId (snoId, entidad)
		SELECT b.snpSolicitudNovedad, a.entidad 
		FROM #Table_snpId a
		INNER JOIN SolicitudNovedadPersona b
		ON a.snpId = b.snpId
		GROUP BY b.snpSolicitudNovedad, a.entidad;

		--12.2. SolicitudNovedadEmpresa
		INSERT INTO #Table_snoId (snoId, entidad)
		SELECT b.sneIdSolicitudNovedad, a.entidad 
		FROM #Table_sneId a
		INNER JOIN solicitudNovedadEmpleador b
		ON a.sneId = b.sneId
		GROUP BY b.sneIdSolicitudNovedad, a.entidad;


--13. Se consultas los datos relacionados a parametrizacion novedad
INSERT INTO #Table_novId (novId, entidad)
SELECT b.snoNovedad, a.entidad 
FROM #Table_snoId a
INNER JOIN SolicitudNovedad b
ON a.snoId = b.snoId
GROUP BY b.snoNovedad, a.entidad;

--14. Se consultan los datos de solicitudes de los datos relacionados al proceso
		--14.1. SolicitudAfiliacionPersona
		INSERT INTO #Table_solId (solId, entidad)
		SELECT b.sapSolicitudGlobal, (a.entidad+'_sap') 
		FROM #Table_sapId a
		INNER JOIN solicitudAfiliacionPersona b
		ON a.sapId = b.sapId
		GROUP BY b.sapSolicitudGlobal, a.entidad;

		--14.2. solicitudNovedad
		INSERT INTO #Table_solId (solId, entidad)
		SELECT b.snoSolicitudGlobal, (a.entidad+ '_sno')
		FROM #Table_snoId a
		INNER JOIN SolicitudNovedad b
		ON a.snoId = b.snoId
		GROUP BY b.snoSolicitudGlobal, a.entidad;

--15. Se consultan los datos de persona de empresas relacionados al proceso

INSERT INTO #Table_perId (perId, entidad)
SELECT b.empPersona, 'Empresa'
FROM #Table_empId a
INNER JOIN empresa b
ON a.empId = b.empId
LEFT JOIN #Table_perId c
ON c.perId = b.empPersona
WHERE c.perId IS NULL
GROUP BY b.empPersona;

--16. Se consultan los datos de los padresBiologicos relacionados al proceso

		--16.1 Campo PadreBiologico
		INSERT INTO #Table_perId (perId, entidad)
		SELECT ped.pedPersonaPadre, 'Padre_'+a.entidad
		FROM #Table_bedId a
		INNER JOIN beneficiarioDetalle b
		ON a.bedId = b.bedId
		INNER JOIN personaDetalle ped
		ON ped.pedId = b.bedPersonaDetalle
		WHERE ped.pedPersonaPadre NOT IN (SELECT perId FROM #Table_perId)
		GROUP BY ped.pedPersonaPadre, a.entidad;

		--16.2 Campo MadreBiologico
		INSERT INTO #Table_perId (perId, entidad)
		SELECT ped.pedPersonaMadre, 'Padre_'+a.entidad
		FROM #Table_bedId a
		INNER JOIN beneficiarioDetalle b
		ON a.bedId = b.bedId
		INNER JOIN personaDetalle ped
		ON ped.pedId = b.bedPersonaDetalle
		WHERE ped.pedPersonaMadre NOT IN (SELECT perId FROM #Table_perId)
		GROUP BY ped.pedPersonaMadre, a.entidad;

--17. Se consultan los datos de los aportesDetallados relacionados al proceso

INSERT INTO #Table_apdId (apdId, entidad)
SELECT apd.apdId, per.entidad
FROM #Table_perId per
INNER JOIN aporteDetallado apd
ON per.perId = apd.apdPersona
WHERE per.entidad <> 'Empresa'
GROUP BY apd.apdId, per.entidad;


--18. Se consultan los datos de los aporteGeneral relacionados al proceso

INSERT INTO #Table_apgId (apgId, entidad)
SELECT b.apdAporteGeneral, a.entidad
FROM #Table_apdId a
INNER JOIN aporteDetallado b
ON a.apdId= b.apdId
GROUP BY b.apdAporteGeneral, a.entidad;

--19. Se consultan los datos de los CargueBloqueoCuotaMonetaria relacionados al proceso

INSERT INTO #Table_cabId (cabId, entidad)
SELECT a.bbcCargueBloqueoCuotaMonetaria, b.entidad
FROM BloqueoBeneficiarioCuotaMonetaria a
INNER JOIN #Table_perId b
ON a.bbcPersona = b.perId
WHERE b.entidad IN ('afiliado', 'beneficiario_afiliado');


--20. Se consultan los datos de los Administradores de subsidio relacionados al proceso

INSERT INTO #Table_asuId (asuId, entidad)
SELECT asg.asgAdministradorSubsidio, 'Administrador'
FROM #Table_grfId grf
INNER JOIN adminSubsidioGrupo asg
ON grf.grfId= asg.asgGrupoFamiliar
GROUP BY asg.asgAdministradorSubsidio;

--21. Se consultan los datos de los Administradores de subsidio relacionados al proceso (Persona)

INSERT INTO #Table_perId (perId, entidad)
SELECT b.asuPersona, 'Administrador'
FROM #Table_asuId a
INNER JOIN administradorSubsidio b
ON a.asuId = b.asuId
WHERE b.asuPersona NOT IN (SELECT perId FROM #Table_perId)
GROUP BY b.asuPersona;


--22. Se consultan los datos de los medios de pago de grupos familiares relacionados al proceso

INSERT INTO #Table_mdpId (mdpId, entidad)
SELECT asg.asgMedioDePago, 'grupoFamiliar'
FROM #Table_grfId grf
INNER JOIN adminSubsidioGrupo asg
ON grf.grfId= asg.asgGrupoFamiliar
GROUP BY asg.asgMedioDePago;

--23. Poblar el esquema fall. como buffer de incremental staging para datos de fallecimiento

INSERT INTO fall.MedioDePago (mdpId, entidad)
SELECT DISTINCT mdpId, entidad FROM #Table_mdpId;

INSERT INTO fall.GrupoFamiliar (grfId, entidad)
SELECT DISTINCT grfId, entidad FROM #Table_grfId;

INSERT INTO fall.Empresa (empId, entidad)
SELECT DISTINCT empId, entidad FROM #Table_empId;

INSERT INTO fall.SucursalEmpresa (sueId, entidad)
SELECT DISTINCT sue.sueId, emp.entidad 
FROM #Table_empId emp
INNER JOIN sucursalEmpresa sue ON sue.sueEmpresa = emp.empId;

INSERT INTO fall.Empleador (empId, entidad)
SELECT DISTINCT empl.empId, emp.entidad FROM #Table_empId emp
INNER JOIN empleador empl ON empl.empEmpresa = emp.empId;

INSERT INTO fall.RolAfiliado (roaId, entidad)
SELECT DISTINCT roa.roaId, afi.entidad FROM #Table_afiId afi
INNER JOIN rolAfiliado roa ON roa.roaAfiliado = afi.afiId;

INSERT INTO fall.AdministradorSubsidio (asuId, entidad)
SELECT asuId, entidad FROM #Table_asuId;

INSERT INTO fall.AdminSubsidioGrupo (asgId, entidad)
SELECT DISTINCT asg.asgId, grf.entidad FROM #Table_grfId grf
INNER JOIN adminSubsidioGrupo asg ON asg.asgGrupoFamiliar = grf.grfId;

INSERT INTO fall.Solicitud (solId, entidad)
SELECT solId, entidad FROM #Table_solId;

INSERT INTO fall.ParametrizacionNovedad (novId, entidad)
SELECT novId, entidad FROM #Table_novId;

INSERT INTO fall.SolicitudNovedad (snoId, entidad)
SELECT snoId, entidad FROM #Table_snoId;

INSERT INTO fall.SolicitudNovedadPersona (snpId, entidad)
SELECT snpId, entidad FROM #Table_snpId;

INSERT INTO fall.NovedadDetalle (nopId, entidad)
SELECT DISTINCT b.nopId, a.entidad FROM #Table_snoId a
INNER JOIN novedadDetalle b ON a.snoId = b.nopSolicitudNovedad;

INSERT INTO fall.AporteGeneral (apgId, entidad)
SELECT apgId, entidad FROM #Table_apgId;

INSERT INTO fall.AporteDetallado (apdId, entidad)
SELECT apdId, entidad FROM #Table_apdId;

INSERT INTO fall.SocioEmpleador (semId, entidad)
SELECT DISTINCT sem.semId, per.entidad FROM #Table_perId per
INNER JOIN socioEmpleador sem ON sem.semPersona = per.perId;

INSERT INTO fall.PersonaDetalle (pedId, entidad)
SELECT DISTINCT ped.pedId, per.entidad FROM #Table_perId per
INNER JOIN personaDetalle ped ON ped.pedPersona = per.perId;

INSERT INTO fall.BeneficiarioDetalle (bedId, entidad)
SELECT bedId, entidad FROM #Table_bedId;

INSERT INTO fall.Beneficiario (benId, entidad)
SELECT DISTINCT ben.benId, bed.entidad FROM #Table_bedId bed
INNER JOIN beneficiario ben ON ben.benBeneficiarioDetalle = bed.bedId;

INSERT INTO fall.BeneficioEmpleador (bemId, entidad)
SELECT DISTINCT bem.bemId, emp.entidad FROM #Table_empId emp
INNER JOIN empleador empl ON empl.empEmpresa = emp.empId
INNER JOIN BeneficioEmpleador bem ON bem.bemEmpleador = empl.empId;

INSERT INTO fall.CondicionInvalidez (coiId, entidad)
SELECT DISTINCT coi.coiId, per.entidad FROM #Table_perId per
INNER JOIN condicionInvalidez coi ON coi.coiPersona = per.perId;

INSERT INTO fall.SolicitudNovedadEmpleador (sneId, entidad)
SELECT sneId, entidad FROM #Table_sneId;

INSERT INTO fall.CertificadoEscolarBeneficiario (cebId, entidad)
SELECT DISTINCT ceb.cebId, bed.entidad FROM #Table_bedId bed
INNER JOIN certificadoEscolarBeneficiario ceb ON ceb.cebBeneficiarioDetalle = bed.bedId;

INSERT INTO fall.CargueBloqueoCuotaMonetaria (cabId, entidad)
SELECT cabId, entidad FROM #Table_cabId;

INSERT INTO fall.BloqueoBeneficiarioCuotaMonetaria (bbcId, entidad)
SELECT DISTINCT bbcId, cab.entidad FROM #Table_cabId cab
INNER JOIN BloqueoBeneficiarioCuotaMonetaria bcc ON bcc.bbcCargueBloqueoCuotaMonetaria = cab.cabId;

INSERT INTO fall.ItemChequeo (ichId, entidad)
SELECT DISTINCT ich.ichId, sol.entidad FROM #Table_solId sol
INNER JOIN ItemChequeo ich ON ich.ichSolicitud = sol.solId;

INSERT INTO fall.SolicitudAfiliacionPersona (sapId, entidad)
SELECT sapId, entidad FROM #Table_sapId;

INSERT INTO fall.Persona (perId, entidad)
SELECT perId, entidad FROM #Table_perId;

INSERT INTO fall.Afiliado (afiId, entidad)
SELECT afiId, entidad FROM #Table_afiId;

return;
END TRY

BEGIN CATCH
	SELECT   
	ERROR_NUMBER() AS ErrorNumber,ERROR_MESSAGE() AS ErrorMessage;
END CATCH