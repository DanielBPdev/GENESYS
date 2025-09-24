--liquibase formatted sql

--changeset clmarin:01
--comment: Insercion de registros en la tabla PlantillaComunicado
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Notificación de pago de aportes - pagador por sí mismo','Cuerpo','Encabezado','Mensaje','Notificación de pago de aportes - pagador por sí mismo','Pie','NTF_PAG_PAG_SIM');

--changeset clmarin:02
--comment: Insercion de registros en la tabla VariableComunicado con respecto a las Variables
--NTF_PAG_PAG_SIM
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${razonSocial/Nombre}','Razon social/Nombre','Nombre o razón social del aportante','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_PAG_PAG_SIM') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoSolicitante}','Tipo solicitante','Tipo de solicitante objeto de la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_PAG_PAG_SIM') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccion}','Dirección','Dirección de la oficina principal','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_PAG_PAG_SIM') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','Municipio','Municipio del pagador','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_PAG_PAG_SIM') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','Departamento','Departamento del pagador','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_PAG_PAG_SIM') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','Teléfono','Teléfono','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_PAG_PAG_SIM') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDeRecepcionAporte}','fecha de recepcion aporte','fecha de recepcion aporte','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_PAG_PAG_SIM') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${periodoDePago}','periodo de pago','periodo de pago','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_PAG_PAG_SIM') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${montoAporteRecibido}','Monto Aporte Recibido','Monto Aporte Recibido','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_PAG_PAG_SIM') ) ;

--changeset clmarin:03
--comment: Insercion de registros en la tabla VariableComunicado con respecto a las Constantes
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_PAG_PAG_SIM')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de novedades de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_PAG_PAG_SIM')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Nombre de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_PAG_PAG_SIM')) ;
