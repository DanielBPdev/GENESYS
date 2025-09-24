--liquibase formatted sql

--changeset mamonroy:01
--comment:
UPDATE vco
SET vco.vcoTipoVariableComunicado = 'CONSTANTE',vcoNombreConstante = 'CIUDAD_CCF'
FROM PlantillaComunicado pco
JOIN VariableComunicado vco ON pco.pcoId = vco.vcoPlantillaComunicado
WHERE pco.pcoEtiqueta = 'NTF_RAD_POS_FOVIS_PRE'
AND vco.vcoClave = '${ciudadSolicitud}';

--changeset jroa:01
--comment: Creacion de campo lerCajaCompensacionCodigo
ALTER TABLE [dbo].[ListaEspecialRevision] ADD lerCajaCompensacionCodigo VARCHAR (20)
ALTER TABLE [aud].[ListaEspecialRevision_aud] ADD lerCajaCompensacionCodigo VARCHAR (20)