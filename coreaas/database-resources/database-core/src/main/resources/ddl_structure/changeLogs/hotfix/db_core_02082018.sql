--liquibase formatted sql

--changeset abaquero:01
--comment: Correcion Plantillas
UPDATE dbo.PlantillaComunicado
SET pcoCuerpo='<p style="text-align: justify;">${contenido}</p><p style="text-align: justify;"><p><strong>Declaraci&oacute;n:</strong></p><p style="text-align: justify;">En calidad de jefe de hogar postulante declaro que la informaci&oacute;n registrada en este formulario es cierta y tiene por objeto solicitar la postulaci&oacute;n al subsidio de vivienda FOVIS de <strong>${nombreCcf}</strong>. En caso de ser aceptados como postulantes nos comprometemos a cumplir y a respetar la legislaci&oacute;n vigente,al igual que los estatus y reglamentos de <strong>${nombreCcf}</strong>.Cualquier falsedad u omisi&oacute;n voluntaria conlleva a la anulaci&oacute;n de esta solicitud</p><p style="text-align: justify;">Si requiere m&aacute;s informaci&oacute;n sobre los resultados de validaci&oacute;n, por favor comun&iacute;quese con la caja de compensaci&oacute;n.</p><p style="text-align: justify;">Nota: La solicitud ser&aacute; radicada, lo cual no implica aceptaci&oacute;n debido a que quedar&aacute; pendiente la revisi&oacute;n de los requisitos documentales, por parte de la caja de compensaci&oacute;n familiar. En caso de hallar inconsistencias la postulaci&oacute;n puede ser rechazada.</p><p style="text-align: justify;">Acepto los t&eacute;rminos y condiciones de postulaci&oacute;n descritos en la anterior declaraci&oacute;n<span class="glyphicon glyphicon-info-sign" aria-hidden="true" role="button" data-toggle="tooltip" title="" data-original-title="Haga click aquí, si usted está de acuerdo con la información registrada"></span><input id="aceptacionDeclaracion" name="aceptacionDeclaracion" type="checkbox" /></p>'
WHERE pcoId=139;

--changeset dsuesca:02
--comment: Ajustes mantis 0241616 
UPDATE Solicitud 
SET solInstanciaProceso = NULL
WHERE solid IN(
				SELECT MAX(solid) 
				  FROM Solicitud
				WHERE solInstanciaProceso IN (
											SELECT sol.solInstanciaProceso
											  FROM Solicitud sol
											WHERE sol.solInstanciaProceso IS NOT NULL
											GROUP BY SOLINSTANCIAPROCESO
											having COUNT(*) > 1)
				GROUP BY solInstanciaProceso
);

--changeset dsuesca:03 context:pruebas
--comment: Ajustes mantis 0241616
--DECLARE @id bigint
--SELECT @id = MAX(CAST(solInstanciaProceso AS bigint)) + 1 FROM Solicitud
--DBCC CHECKIDENT ('bpm_db_dev.dbo.ProcessInstanceInfo', RESEED, ISNULL(@id,1));

--changeset dsuesca:04 context:integracion-asopagos
--comment: Ajustes mantis 0241616
DECLARE @id bigint
SELECT @id = MAX(CAST(solInstanciaProceso AS bigint)) + 1 FROM Solicitud
DBCC CHECKIDENT ('bpm_db.dbo.ProcessInstanceInfo', RESEED, @id);

--changeset dsuesca:05 context:asopagos_funcional
--comment: Ajustes mantis 0241616
DECLARE @id bigint
SELECT @id = MAX(CAST(solInstanciaProceso AS bigint)) + 1 FROM Solicitud
DBCC CHECKIDENT ('bpm_db.dbo.ProcessInstanceInfo', RESEED, @id);

--changeset abaquero:06
--comment: Actualizacion tabla ValidatorDefinition
UPDATE ValidatorDefinition SET state=0 WHERE id=2110054;

--changeset dsuesca:07
--comment: Actualizacion tabla ValidatorDefinition
CREATE UNIQUE NONCLUSTERED INDEX UK_Solicitud_solInstanciaProceso ON Solicitud(solInstanciaProceso) WHERE solInstanciaProceso IS NOT NULL;