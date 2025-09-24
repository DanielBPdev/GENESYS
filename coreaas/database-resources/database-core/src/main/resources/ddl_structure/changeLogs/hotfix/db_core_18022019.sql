--liquibase formatted sql

--changeset squintero:01
--comment: 
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroDeRadicacion}','0','Número de radicación','Número de radicado de la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_LPESM_TIM_PS') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoDeTransaccion}','0','Tipo de transacción','Tipo de transacción','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_LPESM_TIM_PS') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','0','Tipo identificación','Tipo identificación','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_LPESM_TIM_PS') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','0','Número Identificación','Número Identificación','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_LPESM_TIM_PS') ) ;

UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroDeRadicacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroDeRadicacion}') WHERE pcoEtiqueta = 'COM_AVI_LPESM_TIM_PS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoDeTransaccion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoDeTransaccion}') WHERE pcoEtiqueta = 'COM_AVI_LPESM_TIM_PS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacion}') WHERE pcoEtiqueta = 'COM_AVI_LPESM_TIM_PS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacion}') WHERE pcoEtiqueta = 'COM_AVI_LPESM_TIM_PS';


