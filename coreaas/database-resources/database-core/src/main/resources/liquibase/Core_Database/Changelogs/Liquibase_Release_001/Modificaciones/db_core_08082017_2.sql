--liquibase formatted sql

--changeset clmarin:01
--comment: Se actualizan registros de la tabla PlantillaComunicado con respecto a las variables
--Comunicado NTF_REG_BNF_WEB_TRB
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelAfiliadoPrincipal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelAfiliadoPrincipal}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionResidencia}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionResidencia}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${contenido}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${contenido}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${razonSocial/Nombre}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${razonSocial/Nombre}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreDelBeneficiario}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreDelBeneficiario}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${parentesco}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${parentesco}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB';

--Comunicado NTF_REG_BNF_WEB_EMP
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreYApellidosRepresentanteLegal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreYApellidosRepresentanteLegal}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${razonSocial/Nombre}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${razonSocial/Nombre}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccion}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelAfiliadoPrincipal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelAfiliadoPrincipal}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${contenido}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${contenido}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreDelBeneficiario}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreDelBeneficiario}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${parentesco}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${parentesco}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';

--Comunicado NTF_ENRL_AFL_IDPE_WEB
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelAfiliadoPrincipal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelAfiliadoPrincipal}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_IDPE_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p><a href="${enlaceDeEnrolamiento}">Enlace de enrolamiento </a></p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> <a href="${enlaceDeEnrolamiento}">Enlace de enrolamiento </a>') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_IDPE_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${contenido}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${contenido}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_IDPE_WEB';

--Comunicado NTF_ENRL_AFL_PNS_WEB
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelAfiliadoPrincipal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelAfiliadoPrincipal}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_PNS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p><a href="${enlaceDeEnrolamiento}">Enlace de enrolamiento </a></p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> <a href="${enlaceDeEnrolamiento}">Enlace de enrolamiento </a>') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_PNS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${contenido}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${contenido}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_PNS_WEB';

--Comunicado NTF_ENRL_AFL_EMP_WEB
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_EMP_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_EMP_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${razonSocial/Nombre}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${razonSocial/Nombre}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_EMP_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p><a href="${enlaceDeEnrolamiento}">Enlace de enrolamiento </a></p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> <a href="${enlaceDeEnrolamiento}">Enlace de enrolamiento </a>') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_EMP_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${contenido}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${contenido}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_EMP_WEB';

--Comunicado CNFR_RET_APRT
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'CNFR_RET_APRT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'CNFR_RET_APRT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreYApellidosRepresentanteLegal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreYApellidosRepresentanteLegal}') WHERE pcoEtiqueta = 'CNFR_RET_APRT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${razonSocial/Nombre}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${razonSocial/Nombre}') WHERE pcoEtiqueta = 'CNFR_RET_APRT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionEmpleador}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionEmpleador}') WHERE pcoEtiqueta = 'CNFR_RET_APRT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoEmpleador}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoEmpleador}') WHERE pcoEtiqueta = 'CNFR_RET_APRT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${novedadEmpleador}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${novedadEmpleador}') WHERE pcoEtiqueta = 'CNFR_RET_APRT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${contenido}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${contenido}') WHERE pcoEtiqueta = 'CNFR_RET_APRT';

--Comunicado NTF_PARA_SBC_NVD_PERS
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'NTF_PARA_SBC_NVD_PERS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'NTF_PARA_SBC_NVD_PERS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelAfiliadoPrincipal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelAfiliadoPrincipal}') WHERE pcoEtiqueta = 'NTF_PARA_SBC_NVD_PERS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccion}') WHERE pcoEtiqueta = 'NTF_PARA_SBC_NVD_PERS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'NTF_PARA_SBC_NVD_PERS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${resultadoNovedad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${resultadoNovedad}') WHERE pcoEtiqueta = 'NTF_PARA_SBC_NVD_PERS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${contenido}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${contenido}') WHERE pcoEtiqueta = 'NTF_PARA_SBC_NVD_PERS';

--Comunicado NTF_INT_AFL_DEP
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'NTF_INT_AFL_DEP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'NTF_INT_AFL_DEP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelAfiliadoPrincipal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelAfiliadoPrincipal}') WHERE pcoEtiqueta = 'NTF_INT_AFL_DEP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${razonSocial/Nombre}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${razonSocial/Nombre}') WHERE pcoEtiqueta = 'NTF_INT_AFL_DEP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccion}') WHERE pcoEtiqueta = 'NTF_INT_AFL_DEP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'NTF_INT_AFL_DEP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'NTF_INT_AFL_DEP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'NTF_INT_AFL_DEP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoSolicitante}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoSolicitante}') WHERE pcoEtiqueta = 'NTF_INT_AFL_DEP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${clasificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${clasificacion}') WHERE pcoEtiqueta = 'NTF_INT_AFL_DEP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${contenido}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${contenido}') WHERE pcoEtiqueta = 'NTF_INT_AFL_DEP';

--Comunicado NTF_INT_AFL_IDPE
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'NTF_INT_AFL_IDPE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'NTF_INT_AFL_IDPE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelAfiliadoPrincipal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelAfiliadoPrincipal}') WHERE pcoEtiqueta = 'NTF_INT_AFL_IDPE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${razonSocial/Nombre}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${razonSocial/Nombre}') WHERE pcoEtiqueta = 'NTF_INT_AFL_IDPE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'NTF_INT_AFL_IDPE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'NTF_INT_AFL_IDPE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoSolicitante}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoSolicitante}') WHERE pcoEtiqueta = 'NTF_INT_AFL_IDPE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${clasificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${clasificacion}') WHERE pcoEtiqueta = 'NTF_INT_AFL_IDPE';

--Comunicado NTF_INT_AFL_PNS
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'NTF_INT_AFL_PNS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'NTF_INT_AFL_PNS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresYApellidosDelAfiliadoPrincipal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresYApellidosDelAfiliadoPrincipal}') WHERE pcoEtiqueta = 'NTF_INT_AFL_PNS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${razonSocial/Nombre}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${razonSocial/Nombre}') WHERE pcoEtiqueta = 'NTF_INT_AFL_PNS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'NTF_INT_AFL_PNS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'NTF_INT_AFL_PNS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoSolicitante}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoSolicitante}') WHERE pcoEtiqueta = 'NTF_INT_AFL_PNS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${clasificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${clasificacion}') WHERE pcoEtiqueta = 'NTF_INT_AFL_PNS';

--Comunicado RCHZ_AFL_EMP_PRE
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreYApellidosRepresentanteLegal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreYApellidosRepresentanteLegal}') WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreEmpresa}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreEmpresa}') WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccion}') WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_PRE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'RCHZ_AFL_EMP_PRE';

--changeset clmarin:02
--comment: Se actualizan registros de la tabla PlantillaComunicado con respecto a las constantes
--Comunicado NTF_REG_BNF_WEB_TRB
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdCcf}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdCcf}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p><a href="${webCcf}">Web CCF</a></p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> <a href="${webCcf}">Web CCF</a>') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${firmaResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${firmaResponsableCcf}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_TRB';

--Comunicado NTF_REG_BNF_WEB_EMP
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdCcf}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdCcf}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p><a href="${webCcf}">Web CCF</a></p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> <a href="${webCcf}">Web CCF</a>') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${firmaResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${firmaResponsableCcf}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_REG_BNF_WEB_EMP';

--Comunicado NTF_ENRL_AFL_IDPE_WEB
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_IDPE_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_IDPE_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_IDPE_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_IDPE_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_IDPE_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_IDPE_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_IDPE_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_IDPE_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p><a href="${webCcf}">Web CCF</a></p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> <a href="${webCcf}">Web CCF</a>') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_IDPE_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tiempoExpiracionEnlace}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tiempoExpiracionEnlace}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_IDPE_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_IDPE_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${firmaResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${firmaResponsableCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_IDPE_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_IDPE_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_IDPE_WEB';

--Comunicado NTF_ENRL_AFL_PNS_WEB
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_PNS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_PNS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_PNS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_PNS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_PNS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_PNS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_PNS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_PNS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p><a href="${webCcf}">Web CCF</a></p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> <a href="${webCcf}">Web CCF</a>') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_PNS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tiempoExpiracionEnlace}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tiempoExpiracionEnlace}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_PNS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_PNS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${firmaResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${firmaResponsableCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_PNS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_PNS_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_PNS_WEB';

--Comunicado NTF_ENRL_AFL_EMP_WEB
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_EMP_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_EMP_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_EMP_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_EMP_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_EMP_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_EMP_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_EMP_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_EMP_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p><a href="${webCcf}">Web CCF</a></p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> <a href="${webCcf}">Web CCF</a>') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_EMP_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tiempoExpiracionEnlace}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tiempoExpiracionEnlace}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_EMP_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_EMP_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${firmaResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${firmaResponsableCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_EMP_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_EMP_WEB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_ENRL_AFL_EMP_WEB';

--Comunicado CNFR_RET_APRT
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'CNFR_RET_APRT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'CNFR_RET_APRT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'CNFR_RET_APRT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'CNFR_RET_APRT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'CNFR_RET_APRT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'CNFR_RET_APRT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p><a href="${webCcf}">Web CCF</a></p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> <a href="${webCcf}">Web CCF</a>') WHERE pcoEtiqueta = 'CNFR_RET_APRT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'CNFR_RET_APRT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'CNFR_RET_APRT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableNovedadesCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableNovedadesCcf}') WHERE pcoEtiqueta = 'CNFR_RET_APRT';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableNovedadesCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableNovedadesCcf}') WHERE pcoEtiqueta = 'CNFR_RET_APRT';

--Comunicado NTF_PARA_SBC_NVD_PERS
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'NTF_PARA_SBC_NVD_PERS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'NTF_PARA_SBC_NVD_PERS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'NTF_PARA_SBC_NVD_PERS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'NTF_PARA_SBC_NVD_PERS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_PARA_SBC_NVD_PERS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_PARA_SBC_NVD_PERS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p><a href="${webCcf}">Web CCF</a></p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> <a href="${webCcf}">Web CCF</a>') WHERE pcoEtiqueta = 'NTF_PARA_SBC_NVD_PERS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'NTF_PARA_SBC_NVD_PERS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableNovedadesCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableNovedadesCcf}') WHERE pcoEtiqueta = 'NTF_PARA_SBC_NVD_PERS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableNovedadesCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableNovedadesCcf}') WHERE pcoEtiqueta = 'NTF_PARA_SBC_NVD_PERS';

--Comunicado NTF_INT_AFL_DEP
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_DEP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_DEP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_DEP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_DEP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_DEP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_DEP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p><a href="${webCcf}">Web CCF</a></p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> <a href="${webCcf}">Web CCF</a>') WHERE pcoEtiqueta = 'NTF_INT_AFL_DEP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'NTF_INT_AFL_DEP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${firmaResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${firmaResponsableCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_DEP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_DEP';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_DEP';

--Comunicado NTF_INT_AFL_IDPE
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_IDPE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_IDPE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_IDPE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_IDPE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_IDPE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_IDPE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${webCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_IDPE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'NTF_INT_AFL_IDPE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${firmaResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${firmaResponsableCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_IDPE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_IDPE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_IDPE';

--Comunicado NTF_INT_AFL_PNS
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_PNS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_PNS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_PNS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_PNS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_PNS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_PNS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${webCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_PNS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'NTF_INT_AFL_PNS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${firmaResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${firmaResponsableCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_PNS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_PNS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_INT_AFL_PNS';

--Comunicado RCHZ_AFL_EMP_PRE
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

--changeset criparra:03
--comment: Se modifican tamaño de campos de las tablas SolicitudAporte y Trazabilidad
ALTER TABLE SolicitudAporte ALTER COLUMN soaEstadoSolicitud varchar(30);
ALTER TABLE Trazabilidad ALTER COLUMN traEstadoSolicitud varchar(30);
ALTER TABLE Trazabilidad ALTER COLUMN traEstadoInicialSolicitud varchar(30);

--changeset flopez:04
--comment: Se crea la tabla SolicitudNovedadPila y adiciona campo en la tabla RegistroNovedadFutura
CREATE TABLE SolicitudNovedadPila(
	spiId BIGINT NOT NULL IDENTITY(1,1),
	spiSolicitudNovedad BIGINT NOT NULL,
	spiRegistroDetallado BIGINT NOT NULL,
	CONSTRAINT PK_SolicitudNovedadPila_spiId PRIMARY KEY (spiId)
);

ALTER TABLE SolicitudNovedadPila ADD CONSTRAINT FK_SolicitudNovedadPila_spiSolicitudNovedad FOREIGN KEY (spiSolicitudNovedad) REFERENCES SolicitudNovedad(snoId);
ALTER TABLE RegistroNovedadFutura ADD rnfRegistroDetallado BIGINT NULL;

--changeset rarboleda:05
--comment: Actualizacion de registros de la tabla ValidatorParamValue para resolver mantis 0226682
UPDATE ValidatorParamValue SET value = 7 WHERE validatorDefinition_id = 2110031 AND validatorParameter_id = 211238;
UPDATE ValidatorParamValue SET value = 7 WHERE validatorDefinition_id = 2110153 AND validatorParameter_id = 211238;
UPDATE ValidatorParamValue SET value = 7 WHERE validatorDefinition_id = 2110151 AND validatorParameter_id = 211238;
UPDATE ValidatorParamValue SET value = 7 WHERE validatorDefinition_id = 2110029 AND validatorParameter_id = 211238;