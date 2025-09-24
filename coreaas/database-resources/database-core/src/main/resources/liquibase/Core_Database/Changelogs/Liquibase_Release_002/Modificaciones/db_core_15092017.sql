--liquibase formatted sql

--changeset clmarin:01
--comment: Actualizacion de registros en la tabla PlantillaComunicado
--67 NTF_PAG_APT_DEP_APTE_CTZ
UPDATE PlantillaComunicado SET pcoMensaje = CONCAT(pcoMensaje, '<br /> ${contenido}') WHERE pcoEtiqueta = 'NTF_PAG_APT_DEP_APTE_CTZ';

--68 NTF_PAG_APT_DEP_APTE
UPDATE PlantillaComunicado SET pcoMensaje = CONCAT(pcoMensaje, '<br /> ${contenido}') WHERE pcoEtiqueta = 'NTF_PAG_APT_DEP_APTE';

--69 NTF_GST_INF_PAG_APT
UPDATE PlantillaComunicado SET pcoMensaje = CONCAT(pcoMensaje, '<br /> ${contenido}') WHERE pcoEtiqueta = 'NTF_GST_INF_PAG_APT';

--106 NTF_PAG_APT_TRC
UPDATE PlantillaComunicado SET pcoMensaje = CONCAT(pcoMensaje, '<br /> ${contenido}') WHERE pcoEtiqueta = 'NTF_PAG_APT_TRC';

--107 NTF_PAG_PAG_SIM
UPDATE PlantillaComunicado SET pcoMensaje = CONCAT(pcoMensaje, '<br /> ${contenido}') WHERE pcoEtiqueta = 'NTF_PAG_PAG_SIM';

--108 NTF_GST_INF_FLT_APT
UPDATE PlantillaComunicado SET pcoMensaje = CONCAT(pcoMensaje, '<br /> ${contenido}') WHERE pcoEtiqueta = 'NTF_GST_INF_FLT_APT';

--109 NTF_RCHZ_DVL_APT
UPDATE PlantillaComunicado SET pcoMensaje = CONCAT(pcoMensaje, '<br /> ${contenido}') WHERE pcoEtiqueta = 'NTF_RCHZ_DVL_APT';

--110 NTF_APR_DVL_APT
UPDATE PlantillaComunicado SET pcoMensaje = CONCAT(pcoMensaje, '<br /> ${contenido}') WHERE pcoEtiqueta = 'NTF_APR_DVL_APT';

--111 NTF_PAG_DVL_APT
UPDATE PlantillaComunicado SET pcoMensaje = CONCAT(pcoMensaje, '<br /> ${contenido}') WHERE pcoEtiqueta = 'NTF_PAG_DVL_APT';

--112 NTF_APR_COR_APT
UPDATE PlantillaComunicado SET pcoMensaje = CONCAT(pcoMensaje, '<br /> ${contenido}') WHERE pcoEtiqueta = 'NTF_APR_COR_APT';

--113 NTF_RCHZ_COR_APT
UPDATE PlantillaComunicado SET pcoMensaje = CONCAT(pcoMensaje, '<br /> ${contenido}') WHERE pcoEtiqueta = 'NTF_RCHZ_COR_APT';