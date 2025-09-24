--liquibase formatted sql

--changeset clmarin:01
--comment: Se actualizan registros en la tabla plantillacomunicado
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${nombreCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA1';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA1';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA1';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA1';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${direccionCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA1';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA1';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${webCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA1';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${webCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA1';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta='NTF_REC_APT_PLA1';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${responsableCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA1';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA1';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${nombreCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA2';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA2';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA2';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA2';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${direccionCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA2';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA2';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${webCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA2';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta='NTF_REC_APT_PLA2';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${responsableCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA2';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA2';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA32';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA32';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA32';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${direccionCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA32';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA32';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${webCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA32';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta='NTF_REC_APT_PLA32';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${responsableCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA32';
UPDATE PlantillaComunicado SET pcoCuerpo = CONCAT(pcoCuerpo, '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje=CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta='NTF_REC_APT_PLA32';

--changeset jusanchez:02
--comment: Se elimina campo de la tabla Parametro
ALTER TABLE Parametro DROP COLUMN prmEstado;