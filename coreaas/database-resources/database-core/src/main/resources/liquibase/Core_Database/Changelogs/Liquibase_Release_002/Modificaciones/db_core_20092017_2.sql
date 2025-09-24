--liquibase formatted sql

--changeset clmarin:01
--comment: Actualizacion de registros en la tabla VariableComunicado con respecto a las Variables
--RCHZ_AFL_EMP_PRE
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreYApellidosRepresentanteLegal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreYApellidosRepresentanteLegal}') WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreEmpresa}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreEmpresa}') WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccion}') WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_PRE';

--changeset clmarin:02
--comment: Actualizacion de registros en la tabla VariableComunicado con respecto a las Constantes
--RCHZ_AFL_EMP_PRE
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${webCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${firmaResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${firmaResponsableCcf}') WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_PRE';

--changeset clmarin:03
--comment: Actualizacion de registros en la tabla PlantillaComunicado
--RCHZ_AFL_EMP_PRE
UPDATE PlantillaComunicado SET pcoAsunto = 'Rechazo de solicitud de afiliación de empleador empresas presencial', pcoNombre = 'Rechazo de solicitud de afiliación de empleador empresas presencial' WHERE pcoEtiqueta= 'RCHZ_AFL_EMP_PRE';

--changeset jongarcia:04
--comment: Actualizacion de registro en la tabla Parametro
UPDATE Parametro SET prmValor ='cd5f3ee7-39ad-4275-9e8c-cfeeae478280' WHERE prmNombre = 'IDM_CLIENT_WEB_CLIENT_SECRET';