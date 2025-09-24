--liquibase formatted sql

--changeset dsuesca:01
--comment: Ajuste para evitar mas de dos medios de pago activo para un grupo familiar, genera doble cuota en subsidio
SELECT max(asg.asgId) asgId,asg.asgGrupoFamiliar
into #ids
FROM AdminSubsidioGrupo asg
JOIN
(
	SELECT count(*) A,asgGrupoFamiliar 
	from AdminSubsidioGrupo
	WHERE asgMedioPagoActivo =1
	group by asgGrupoFamiliar
	having count(*)>1
) grupos on grupos.asgGrupoFamiliar = asg.asgGrupoFamiliar 
GROUP BY asg.asgGrupoFamiliar
 
UPDATE AdminSubsidioGrupo
SET asgMedioPagoActivo = 0
WHERE asgGrupoFamiliar IN (
	SELECT asgGrupoFamiliar
	FROM #ids	
);  
 
UPDATE AdminSubsidioGrupo
SET asgMedioPagoActivo = 1
WHERE asgId IN (
	SELECT asgId
	FROM #ids
	);

--changeset dsuesca:02
--comment: Ajuste para evitar mas de dos medios de pago activo para un grupo familiar, genera doble cuota en subsidio
CREATE UNIQUE NONCLUSTERED INDEX IX_AdminSubsidioGrupo_asgGrupoFamiliar_asgMedioPagoActivo_1
ON AdminSubsidioGrupo (asgGrupoFamiliar)
WHERE asgMedioPagoActivo =1;