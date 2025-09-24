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

--NTF_APR_COR_APT
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'NTF_APR_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'NTF_APR_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${razonSocial/Nombre}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${razonSocial/Nombre}') WHERE pcoEtiqueta = 'NTF_APR_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoSolicitante}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoSolicitante}') WHERE pcoEtiqueta = 'NTF_APR_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccion}') WHERE pcoEtiqueta = 'NTF_APR_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${municipio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${municipio}') WHERE pcoEtiqueta = 'NTF_APR_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamento}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamento}') WHERE pcoEtiqueta = 'NTF_APR_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'NTF_APR_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaRegistroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaRegistroSolicitud}') WHERE pcoEtiqueta = 'NTF_APR_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroSolicitud}') WHERE pcoEtiqueta = 'NTF_APR_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${resultadoEvaluacionSupervisor}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${resultadoEvaluacionSupervisor}') WHERE pcoEtiqueta = 'NTF_APR_COR_APT';

--NTF_RCHZ_COR_APT
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'NTF_RCHZ_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'NTF_RCHZ_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${razonSocial/Nombre}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${razonSocial/Nombre}') WHERE pcoEtiqueta = 'NTF_RCHZ_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoSolicitante}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoSolicitante}') WHERE pcoEtiqueta = 'NTF_RCHZ_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccion}') WHERE pcoEtiqueta = 'NTF_RCHZ_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${municipio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${municipio}') WHERE pcoEtiqueta = 'NTF_RCHZ_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamento}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamento}') WHERE pcoEtiqueta = 'NTF_RCHZ_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'NTF_RCHZ_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaRegistroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaRegistroSolicitud}') WHERE pcoEtiqueta = 'NTF_RCHZ_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroSolicitud}') WHERE pcoEtiqueta = 'NTF_RCHZ_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${resultadoFinal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${resultadoFinal}') WHERE pcoEtiqueta = 'NTF_RCHZ_COR_APT';

--changeset clmarin:02
--comment: Actualizacion de registros en la tabla VariableComunicado con respecto a las Constantes
--NTF_PAG_PAG_SIM
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'NTF_PAG_PAG_SIM';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'NTF_PAG_PAG_SIM';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'NTF_PAG_PAG_SIM';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'NTF_PAG_PAG_SIM';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_PAG_PAG_SIM';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_PAG_PAG_SIM';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${webCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta = 'NTF_PAG_PAG_SIM';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'NTF_PAG_PAG_SIM';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_PAG_PAG_SIM';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_PAG_PAG_SIM';

--NTF_APR_COR_APT
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'NTF_APR_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'NTF_APR_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'NTF_APR_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'NTF_APR_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_APR_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_APR_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${webCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta = 'NTF_APR_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'NTF_APR_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_APR_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_APR_COR_APT';

--NTF_RCHZ_COR_APT 
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'NTF_RCHZ_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'NTF_RCHZ_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'NTF_RCHZ_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'NTF_RCHZ_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_RCHZ_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_RCHZ_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${webCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta = 'NTF_RCHZ_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'NTF_RCHZ_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_RCHZ_COR_APT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_RCHZ_COR_APT';