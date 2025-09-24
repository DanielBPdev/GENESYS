--liquibase formatted sql

--changeset clmarin:01
--comment: Insercion de registro en la tabla VariableComunicado
--67 NTF_PAG_APT_DEP_APTE_CTZ
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${contenido}','Contenido','Texto que se adiciona al comunicado por parte del usuario','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_PAG_APT_DEP_APTE_CTZ') ) ;

--68 NTF_PAG_APT_DEP_APTE
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${contenido}','Contenido','Texto que se adiciona al comunicado por parte del usuario','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_PAG_APT_DEP_APTE') ) ;

--69 NTF_GST_INF_PAG_APT
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${contenido}','Contenido','Texto que se adiciona al comunicado por parte del usuario','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_GST_INF_PAG_APT') ) ;

--106 NTF_PAG_APT_TRC
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${contenido}','Contenido','Texto que se adiciona al comunicado por parte del usuario','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_PAG_APT_TRC') ) ;

--107 NTF_PAG_PAG_SIM
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${contenido}','Contenido','Texto que se adiciona al comunicado por parte del usuario','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_PAG_PAG_SIM') ) ;

--108 NTF_GST_INF_FLT_APT
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${contenido}','Contenido','Texto que se adiciona al comunicado por parte del usuario','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_GST_INF_FLT_APT') ) ;

--109 NTF_RCHZ_DVL_APT
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${contenido}','Contenido','Texto que se adiciona al comunicado por parte del usuario','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCHZ_DVL_APT') ) ;

--110 NTF_APR_DVL_APT
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${contenido}','Contenido','Texto que se adiciona al comunicado por parte del usuario','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_APR_DVL_APT') ) ;

--111 NTF_PAG_DVL_APT
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${contenido}','Contenido','Texto que se adiciona al comunicado por parte del usuario','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_PAG_DVL_APT') ) ;

--112 NTF_APR_COR_APT
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${contenido}','Contenido','Texto que se adiciona al comunicado por parte del usuario','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_APR_COR_APT') ) ;

--113 NTF_RCHZ_COR_APT
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${contenido}','Contenido','Texto que se adiciona al comunicado por parte del usuario','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCHZ_COR_APT') ) ;
