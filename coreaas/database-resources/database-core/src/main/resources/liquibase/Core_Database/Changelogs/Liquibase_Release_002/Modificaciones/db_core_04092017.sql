--liquibase formatted sql

--changeset clmarin:01
--comment: Actualizacion de registros en la tabla VariableComunicado con respecto a las Variables
--NTF_PAG_PAG_SIM
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${razonSocial/Nombre}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${razonSocial/Nombre}') WHERE pcoEtiqueta = 'NTF_PAG_PAG_SIM';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoSolicitante}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoSolicitante}') WHERE pcoEtiqueta = 'NTF_PAG_PAG_SIM';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccion}') WHERE pcoEtiqueta = 'NTF_PAG_PAG_SIM';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${municipio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${municipio}') WHERE pcoEtiqueta = 'NTF_PAG_PAG_SIM';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamento}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamento}') WHERE pcoEtiqueta = 'NTF_PAG_PAG_SIM';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'NTF_PAG_PAG_SIM';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDeRecepcionAporte}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDeRecepcionAporte}') WHERE pcoEtiqueta = 'NTF_PAG_PAG_SIM';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${periodoDePago}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${periodoDePago}') WHERE pcoEtiqueta = 'NTF_PAG_PAG_SIM';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${montoAporteRecibido}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${montoAporteRecibido}') WHERE pcoEtiqueta = 'NTF_PAG_PAG_SIM';

--changeset clmarin:02
--comment: Actualizacion de registros en la tabla VariableComunicado con respecto a las Constantes
--Comunicado NTF_PAG_PAG_SIM
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'NTF_PAG_PAG_SIM';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_PAG_PAG_SIM';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_PAG_PAG_SIM';

--changeset clmarin:03
--comment: Se adiciona campo en la tabla AporteDetallado
ALTER TABLE AporteDetallado ADD apdFechaCreacion date NULL;