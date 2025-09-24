--liquibase formatted sql

--changeset jusanchez:01
--comment: Se agrega campo en la tabla AporteDetallado
ALTER TABLE AporteDetallado ADD apdFechaMovimiento date;

--changeset clmarin:02
--comment: Se eliminan registros de la tabla VariableComunicado y se actualizan registros en la tabla PlantillaComunicado
DELETE vc FROM Plantillacomunicado pc INNER JOIN VariableComunicado vc ON pc.pcoId=vc.vcoPlantillaComunicado WHERE pcoEtiqueta in ('NTF_PAG_APT_DEP_APTE_CTZ','NTF_PAG_APT_DEP_APTE','NTF_GST_INF_PAG_APT','NTF_PAG_APT_TRC');
UPDATE PlantillaComunicado SET pcoCuerpo='Cuerpo', pcoMensaje='Mensaje' WHERE pcoEtiqueta= 'NTF_PAG_APT_DEP_APTE_CTZ';
UPDATE PlantillaComunicado SET pcoCuerpo='Cuerpo', pcoMensaje='Mensaje' WHERE pcoEtiqueta= 'NTF_PAG_APT_DEP_APTE';
UPDATE PlantillaComunicado SET pcoCuerpo='Cuerpo', pcoMensaje='Mensaje' WHERE pcoEtiqueta= 'NTF_GST_INF_PAG_APT';
UPDATE PlantillaComunicado SET pcoCuerpo='Cuerpo', pcoMensaje='Mensaje' WHERE pcoEtiqueta= 'NTF_PAG_APT_TRC';