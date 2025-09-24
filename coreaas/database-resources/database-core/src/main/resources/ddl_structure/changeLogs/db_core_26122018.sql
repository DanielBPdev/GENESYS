--liquibase formatted sql

--changeset squintero:01
--comment: Adición de configuración de comunicados
insert into GrupoPrioridad (gprNombre) values ('SUPERVISOR_AFILIACION_PERSONA');
insert into GrupoPrioridad (gprNombre) values ('SUPERVISOR_NOVEDADES_EMPLEADOR');
insert into GrupoPrioridad (gprNombre) values ('SUPERVISOR_NOVEDADES_PERSONA');
insert into GrupoPrioridad (gprNombre) values ('SUPERVISOR_SUBSIDIO_MONETARIO');
insert into GrupoPrioridad (gprNombre) values ('SUPERVISOR_APORTES');
insert into GrupoPrioridad (gprNombre) values ('COORDINADOR_SUBSIDIO_FOVIS');

--changeset squintero:02
--comment: Adición de configuración de comunicados
insert into DestinatarioGrupo (dgrGrupoPrioridad, dgrRolContacto) values ((SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_AFILIACION_PERSONA'), 'SUPERVISOR_AFILIACION_PERSONA');
insert into DestinatarioGrupo (dgrGrupoPrioridad, dgrRolContacto) values ((SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_NOVEDADES_EMPLEADOR'), 'SUPERVISOR_NOVEDADES_EMPLEADOR');
insert into DestinatarioGrupo (dgrGrupoPrioridad, dgrRolContacto) values ((SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_NOVEDADES_PERSONA'), 'SUPERVISOR_NOVEDADES_PERSONA');
insert into DestinatarioGrupo (dgrGrupoPrioridad, dgrRolContacto) values ((SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_SUBSIDIO_MONETARIO'), 'SUPERVISOR_SUBSIDIO_MONETARIO');
insert into DestinatarioGrupo (dgrGrupoPrioridad, dgrRolContacto) values ((SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_APORTES'), 'SUPERVISOR_APORTES');
insert into DestinatarioGrupo (dgrGrupoPrioridad, dgrRolContacto) values ((SELECT gprId from GrupoPrioridad where gprNombre = 'COORDINADOR_SUBSIDIO_FOVIS'), 'COORDINADOR_SUBSIDIO_FOVIS');

--changeset squintero:03
--comment: Adición de configuración de comunicados
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('AFILIACION_EMPRESAS_WEB', 'COM_AVI_AEW_TIM_PS');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('AFILIACION_EMPRESAS_WEB', 'COM_AVI_AEW_TIM_GSB');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('AFILIACION_PERSONAS_PRESENCIAL', 'COM_AVI_APP_TIM_PS');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('AFILIACION_PERSONAS_PRESENCIAL', 'COM_AVI_APP_TIM_ASE');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('AFILIACION_PERSONAS_PRESENCIAL', 'COM_AVI_APP_TIM_GSB');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('AFILIACION_DEPENDIENTE_WEB', 'COM_AVI_ADW_TIM_PS');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('AFILIACION_DEPENDIENTE_WEB', 'COM_AVI_ADW_TIM_GSB');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('AFILIACION_INDEPENDIENTE_WEB', 'COM_AVI_AIPW_TIM_PS');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('AFILIACION_INDEPENDIENTE_WEB', 'COM_AVI_AIPW_TIM_GSB');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('NOVEDADES_EMPRESAS_PRESENCIAL', 'COM_AVI_NEP_TIM_PS');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('NOVEDADES_EMPRESAS_PRESENCIAL', 'COM_AVI_NEP_TIM_ASE');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('NOVEDADES_EMPRESAS_PRESENCIAL', 'COM_AVI_NEP_TIM_GSB');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('NOVEDADES_EMPRESAS_WEB', 'COM_AVI_NEW_TIM_PS');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('NOVEDADES_EMPRESAS_WEB', 'COM_AVI_NEW_TIM_GSB');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('NOVEDADES_PERSONAS_PRESENCIAL', 'COM_AVI_NPP_TIM_PS');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('NOVEDADES_PERSONAS_PRESENCIAL', 'COM_AVI_NPP_TIM_ASE');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('NOVEDADES_PERSONAS_PRESENCIAL', 'COM_AVI_NPP_TIM_GSB');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('NOVEDADES_DEPENDIENTE_WEB', 'COM_AVI_NDW_TIM_PS');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('NOVEDADES_DEPENDIENTE_WEB', 'COM_AVI_NDW_TIM_GSB');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('NOVEDADES_PERSONAS_WEB', 'COM_AVI_NPW_TIM_PS');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('NOVEDADES_PERSONAS_WEB', 'COM_AVI_NPW_TIM_GSB');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('PAGO_APORTES_MANUAL', 'COM_AVI_AM_TIM_PS');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('PAGO_APORTES_MANUAL', 'COM_AVI_AM_TIM_PI');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('DEVOLUCION_APORTES', 'COM_AVI_DA_TIM_PS');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('DEVOLUCION_APORTES', 'COM_AVI_DA_TIM_PI');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('CORRECCION_APORTES', 'COM_AVI_CA_TIM_PS');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('CIERRE_RECAUDO', 'COM_AVI_CRAYCO_TIM_PS');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('POSTULACION_FOVIS_PRESENCIAL', 'COM_AVI_PFP_TIM_PS');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('POSTULACION_FOVIS_PRESENCIAL', 'COM_AVI_PFP_TIM_PGC');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('POSTULACION_FOVIS_WEB', 'COM_AVI_PFW_TIM_PS');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('POSTULACION_FOVIS_WEB', 'COM_AVI_PFW_TIM_PGC');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('SUBSIDIO_MONETARIO_MASIVO', 'COM_AVI_LMSM_TIM_PS');
insert into DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) values ('SUBSIDIO_MONETARIO_ESPECIFICO', 'COM_AVI_LPESM_TIM_PS');

--changeset squintero:04
--comment: Adición de configuración de comunicados
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'AFILIACION_EMPRESAS_WEB' and dcoEtiquetaPlantilla = 'COM_AVI_AEW_TIM_PS'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_AFILIACION_EMPLEADOR'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'AFILIACION_EMPRESAS_WEB' and dcoEtiquetaPlantilla = 'COM_AVI_AEW_TIM_GSB'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_AFILIACION_EMPLEADOR'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'AFILIACION_PERSONAS_PRESENCIAL' and dcoEtiquetaPlantilla = 'COM_AVI_APP_TIM_PS'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_AFILIACION_PERSONA'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'AFILIACION_PERSONAS_PRESENCIAL' and dcoEtiquetaPlantilla = 'COM_AVI_APP_TIM_ASE'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_AFILIACION_PERSONA'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'AFILIACION_PERSONAS_PRESENCIAL' and dcoEtiquetaPlantilla = 'COM_AVI_APP_TIM_GSB'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_AFILIACION_PERSONA'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'AFILIACION_DEPENDIENTE_WEB' and dcoEtiquetaPlantilla = 'COM_AVI_ADW_TIM_PS'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_AFILIACION_PERSONA'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'AFILIACION_DEPENDIENTE_WEB' and dcoEtiquetaPlantilla = 'COM_AVI_ADW_TIM_GSB'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_AFILIACION_PERSONA'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'AFILIACION_INDEPENDIENTE_WEB' and dcoEtiquetaPlantilla = 'COM_AVI_AIPW_TIM_PS'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_AFILIACION_PERSONA'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'AFILIACION_INDEPENDIENTE_WEB' and dcoEtiquetaPlantilla = 'COM_AVI_AIPW_TIM_GSB'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_AFILIACION_PERSONA'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'NOVEDADES_EMPRESAS_PRESENCIAL' and dcoEtiquetaPlantilla = 'COM_AVI_NEP_TIM_PS'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_NOVEDADES_EMPLEADOR'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'NOVEDADES_EMPRESAS_PRESENCIAL' and dcoEtiquetaPlantilla = 'COM_AVI_NEP_TIM_ASE'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_NOVEDADES_EMPLEADOR'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'NOVEDADES_EMPRESAS_PRESENCIAL' and dcoEtiquetaPlantilla = 'COM_AVI_NEP_TIM_GSB'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_NOVEDADES_EMPLEADOR'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'NOVEDADES_EMPRESAS_WEB' and dcoEtiquetaPlantilla = 'COM_AVI_NEW_TIM_PS'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_NOVEDADES_EMPLEADOR'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'NOVEDADES_EMPRESAS_WEB' and dcoEtiquetaPlantilla = 'COM_AVI_NEW_TIM_GSB'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_NOVEDADES_EMPLEADOR'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'NOVEDADES_PERSONAS_PRESENCIAL' and dcoEtiquetaPlantilla = 'COM_AVI_NPP_TIM_PS'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_NOVEDADES_PERSONA'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'NOVEDADES_PERSONAS_PRESENCIAL' and dcoEtiquetaPlantilla = 'COM_AVI_NPP_TIM_ASE'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_NOVEDADES_PERSONA'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'NOVEDADES_PERSONAS_PRESENCIAL' and dcoEtiquetaPlantilla = 'COM_AVI_NPP_TIM_GSB'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_NOVEDADES_PERSONA'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'NOVEDADES_DEPENDIENTE_WEB' and dcoEtiquetaPlantilla = 'COM_AVI_NDW_TIM_PS'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_NOVEDADES_PERSONA'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'NOVEDADES_DEPENDIENTE_WEB' and dcoEtiquetaPlantilla = 'COM_AVI_NDW_TIM_GSB'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_NOVEDADES_PERSONA'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'NOVEDADES_PERSONAS_WEB' and dcoEtiquetaPlantilla = 'COM_AVI_NPW_TIM_PS'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_NOVEDADES_PERSONA'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'NOVEDADES_PERSONAS_WEB' and dcoEtiquetaPlantilla = 'COM_AVI_NPW_TIM_GSB'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_NOVEDADES_PERSONA'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'PAGO_APORTES_MANUAL' and dcoEtiquetaPlantilla = 'COM_AVI_AM_TIM_PS'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_APORTES'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'PAGO_APORTES_MANUAL' and dcoEtiquetaPlantilla = 'COM_AVI_AM_TIM_PI'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_APORTES'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'DEVOLUCION_APORTES' and dcoEtiquetaPlantilla = 'COM_AVI_DA_TIM_PS'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_APORTES'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'DEVOLUCION_APORTES' and dcoEtiquetaPlantilla = 'COM_AVI_DA_TIM_PI'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_APORTES'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'CORRECCION_APORTES' and dcoEtiquetaPlantilla = 'COM_AVI_CA_TIM_PS'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_APORTES'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'CIERRE_RECAUDO' and dcoEtiquetaPlantilla = 'COM_AVI_CRAYCO_TIM_PS'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_APORTES'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'POSTULACION_FOVIS_PRESENCIAL' and dcoEtiquetaPlantilla = 'COM_AVI_PFP_TIM_PS'), (SELECT gprId from GrupoPrioridad where gprNombre = 'COORDINADOR_SUBSIDIO_FOVIS'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'POSTULACION_FOVIS_PRESENCIAL' and dcoEtiquetaPlantilla = 'COM_AVI_PFP_TIM_PGC'), (SELECT gprId from GrupoPrioridad where gprNombre = 'COORDINADOR_SUBSIDIO_FOVIS'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'POSTULACION_FOVIS_WEB' and dcoEtiquetaPlantilla = 'COM_AVI_PFW_TIM_PS'), (SELECT gprId from GrupoPrioridad where gprNombre = 'COORDINADOR_SUBSIDIO_FOVIS'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'POSTULACION_FOVIS_WEB' and dcoEtiquetaPlantilla = 'COM_AVI_PFW_TIM_PGC'), (SELECT gprId from GrupoPrioridad where gprNombre = 'COORDINADOR_SUBSIDIO_FOVIS'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'SUBSIDIO_MONETARIO_MASIVO' and dcoEtiquetaPlantilla = 'COM_AVI_LMSM_TIM_PS'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_SUBSIDIO_MONETARIO'), 1);
insert into prioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
 values ((SELECT dcoId from DestinatarioComunicado where dcoProceso = 'SUBSIDIO_MONETARIO_ESPECIFICO' and dcoEtiquetaPlantilla = 'COM_AVI_LPESM_TIM_PS'), (SELECT gprId from GrupoPrioridad where gprNombre = 'SUPERVISOR_SUBSIDIO_MONETARIO'), 1);