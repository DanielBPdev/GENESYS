--liquibase formatted sql


--changeset  atoro:01
--comment:Actualización de novedades

--Novedad 1. Cambio tipo y/o número documento de identificación  

UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarPersonaNovedad' WHERE novTipoTransaccion='CAMBIO_TIPO_NUMERO_DOCUMENTO'; 

--Novedad 2. Cambio de razón social o nombre 

UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarPersonaNovedad' WHERE novTipoTransaccion='CAMBIO_RAZON_SOCIAL_NOMBRE' ; 

--Novedad 3. Cambio de naturaleza jurídica 
UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarEmpresaNovedad' WHERE novTipoTransaccion='CAMBIO_NATURALEZA_JURIDICA' ; 

--Novedad 4. Cambio de actividad económica principal 
UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarEmpresaNovedad' WHERE novTipoTransaccion='CAMBIO_ACTIVIDAD_ECONOMICA_PRINCIPAL_PRESENCIAL' ; 

UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarEmpresaNovedad' WHERE novTipoTransaccion='CAMBIO_ACTIVIDAD_ECONOMICA_PRINCIPAL_WEB' ; 

-- Novedad 5. Cambios en otros datos de identificación del empleador 
UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarEmpresaNovedad' WHERE novTipoTransaccion='CAMBIOS_OTROS_DATOS_IDENTIFICACION_EMPLEADOR' ; 

-- Novedad 6. Actualización datos de contacto oficina principal 
UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarUbicacionNovedad' WHERE novTipoTransaccion='ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_PRESENCIAL' ; 

UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarUbicacionNovedad' WHERE novTipoTransaccion='ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_WEB' ; 
               

--Novedad 7. Actualización datos de envío de correspondencia 
UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarUbicacionNovedad' WHERE novTipoTransaccion='ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_PRESENCIAL' ; 

UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarUbicacionNovedad' WHERE novTipoTransaccion='ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_WEB' ; 
                

--Novedad 8 Actualización dirección notificación judicial 
UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarUbicacionNovedad' WHERE novTipoTransaccion='ACTUALIZACION_DIRECCION_NOTIFICACION_JUDICIAL_PRESENCIAL' ; 

UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarUbicacionNovedad' WHERE novTipoTransaccion='ACTUALIZACION_DIRECCION_NOTIFICACION_JUDICIAL_WEB'; 
  
--Novedad 10 Cambio de medio de pago empleador 
UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarEmpleadorNovedad' WHERE novTipoTransaccion='CAMBIO_MEDIO_PAGO_EMPLEADOR_PRESENCIAL'; 

UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarEmpleadorNovedad' WHERE novTipoTransaccion='CAMBIO_MEDIO_PAGO_EMPLEADOR_WEB'; 
              

--Novedad 11 Cambio código o nombre de sucursal 
UPDATE Novedad set novTipoNovedad='SUCURSAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarUbicacionNovedad' WHERE novTipoTransaccion='CAMBIO_CODIGO_NOMBRE_SUCURSAL'; 

--Novedad 12 Cambio de datos sucursal 
UPDATE Novedad set novTipoNovedad='SUCURSAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarUbicacionNovedad' WHERE novTipoTransaccion='CAMBIO_DATOS_SUCURSAL_PRESENCIA'; 

UPDATE Novedad set novTipoNovedad='SUCURSAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarUbicacionNovedad' WHERE novTipoTransaccion='CAMBIO_DATOS_SUCURSAL_WEB'; 

--Novedad 13 Cambio de actividad económica sucursal 
UPDATE Novedad set novTipoNovedad='SUCURSAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarSucursalNovedad' WHERE novTipoTransaccion='CAMBIO_ACTIVIDAD_ECONOMICA_SUCURSAL_PRESENCIAL'; 

UPDATE Novedad set novTipoNovedad='SUCURSAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarSucursalNovedad' WHERE novTipoTransaccion='CAMBIO_ACTIVIDAD_ECONOMICA_SUCURSAL_WEB'; 

--Novedad 14 Cambio de medio de pago sucursal 
UPDATE Novedad set novTipoNovedad='SUCURSAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarSucursalNovedad' WHERE novTipoTransaccion='CAMBIO_MEDIO_PAGO_SUCURSAL_PRESENCIAL'; 

UPDATE Novedad set novTipoNovedad='SUCURSAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarSucursalNovedad' WHERE novTipoTransaccion='CAMBIO_MEDIO_PAGO_SUCURSAL_WEB'; 

--Novedad 15 Activar o inactivar código sucursal debe conicidir con PILA 
UPDATE Novedad set novTipoNovedad='SUCURSAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarSucursalNovedad' WHERE novTipoTransaccion='ACTIVAR_INACTIVAR_CODIGO_SUCURSAL_DEBE_COINCIDIR_CON_PILA'; 
 

--Novedad 16 Agregar sucursal 
UPDATE Novedad set novTipoNovedad='SUCURSAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarSucursalNovedad' WHERE novTipoTransaccion='AGREGAR_SUCURSAL'; 

--Novedad 17 Inactivar sucursal 
UPDATE Novedad set novTipoNovedad='SUCURSAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarSucursalNovedad' WHERE novTipoTransaccion='INACTIVAR_SUCURSAL'; 

--Novedad 20 Activar beneficios de Ley 1429 de 2010 
UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarEmpleadorNovedad' WHERE novTipoTransaccion='ACTIVAR_BENEFICIOS_LEY_1429_2010_PRESENCIAL'; 

UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarEmpleadorNovedad' WHERE novTipoTransaccion='ACTIVAR_BENEFICIOS_LEY_1429_2010_WEB'; 
 
--Novedad 21 Inactivar beneficios de Ley 1429 de 2010 

UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarEmpleadorNovedad' WHERE novTipoTransaccion='INACTIVAR_BENEFICIOS_LEY_1429_2010_PRESENCIAL'; 

UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarEmpleadorNovedad' WHERE novTipoTransaccion='INACTIVAR_BENEFICIOS_LEY_1429_2010_WEB'; 


--Novedad 22 Activar beneficios de Ley 590 de 2000 
UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarEmpleadorNovedad' WHERE novTipoTransaccion='ACTIVAR_BENEFICIOS_LEY_590_2000_PRESENCIAL'; 

UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarEmpleadorNovedad' WHERE novTipoTransaccion='ACTIVAR_BENEFICIOS_LEY_590_2000_WEB'; 

--Novedad 23 Inactivar beneficios de Ley 590 de 2000 
UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarEmpleadorNovedad' WHERE novTipoTransaccion='INACTIVAR_BENEFICIOS_LEY_590_2000_PRESENCIAL'; 

UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarEmpleadorNovedad' WHERE novTipoTransaccion='INACTIVAR_BENEFICIOS_LEY_590_2000_WEB'; 

--Novedad 26 Desafiliación 
UPDATE Novedad set novTipoNovedad='GENERAL', novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarEmpleadorNovedad' WHERE novTipoTransaccion='DESAFILIACION'; 


-- changeset halzate:02
--comment Corrección del comunicado 27 e insterts para el comunicado 28
DELETE FROM VariableComunicado
WHERE vcoPlantillaComunicado = (SELECT pcoId FROM PlantillaComunicado
WHERE pcoNombre = 'Notificacion de aceptación de afiliacion de independiente despues de subsanacion')

-- Variables comunicado 27 Notificacion de aceptación de afiliacion de independiente despues de subsanacion
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de aceptación de afiliacion de independiente despues de subsanacion') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de aceptación de afiliacion de independiente despues de subsanacion') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${nombresYApellidosDelAfiliadoPrincipal}','Nombres y Apellidos del afiliado principal','Nombre completo del afiliado principal objeto de la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de aceptación de afiliacion de independiente despues de subsanacion') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${direccionResidencia}','Dirección residencia','Dirección capturada en "Información de ubicación y correspondencia" del afiliado principal objeto de la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de aceptación de afiliacion de independiente despues de subsanacion') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${telefono}','Teléfono','Teléfono fijo o Teléfono celular capturado en "Información de ubicación y correspondencia" del afiliado principal', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de aceptación de afiliacion de independiente despues de subsanacion') ) ;

-- Constantes comunicado 27 Notificacion de aceptación de afiliacion de independiente despues de subsanacion
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de aceptación de afiliacion de independiente despues de subsanacion')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de aceptación de afiliacion de independiente despues de subsanacion')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${departamentoCcf}','Departamento CCF','Departamento de la caja de Compensación','DEPARTAMENTO_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de aceptación de afiliacion de independiente despues de subsanacion')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de la caja de Compensación','CIUDAD_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de aceptación de afiliacion de independiente despues de subsanacion')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de aceptación de afiliacion de independiente despues de subsanacion')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Teléfono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de aceptación de afiliacion de independiente despues de subsanacion')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${tipoIdCcf}','Tipo ID CCF','Tipo de la caja de Compensación','TIPO_ID_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de aceptación de afiliacion de independiente despues de subsanacion')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${numeroIdCcf}','Número ID CCF','Número de la caja de Compensación','NUMERO_ID_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de aceptación de afiliacion de independiente despues de subsanacion')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${webCcf}','Web CCF','Web de la caja de Compensación','WEB_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de aceptación de afiliacion de independiente despues de subsanacion')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${logoSuperservicios}','Logo SuperServicios','Logo de la caja de Compensación','LOGO_SUPERSERVICIOS',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de aceptación de afiliacion de independiente despues de subsanacion')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${firmaResponsableCcf}','Firma Responsable CCF','Firma de la caja de Compensación','FIRMA_RESPONSABLE_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de aceptación de afiliacion de independiente despues de subsanacion')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de la caja de Compensación','RESPONSABLE_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de aceptación de afiliacion de independiente despues de subsanacion')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Cargo de la caja de Compensación','CARGO_RESPONSABLE_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de aceptación de afiliacion de independiente despues de subsanacion')) ;


-- Constantes comunicado 28 Notificacion de rechazo de afiliacion de independiente despues de subsanacion
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de rechazo de afiliacion de independiente despues de subsanacion')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de rechazo de afiliacion de independiente despues de subsanacion')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${departamentoCcf}','Departamento CCF','Departamento de la caja de Compensación','DEPARTAMENTO_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de rechazo de afiliacion de independiente despues de subsanacion')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de la caja de Compensación','CIUDAD_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de rechazo de afiliacion de independiente despues de subsanacion')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de rechazo de afiliacion de independiente despues de subsanacion')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Teléfono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de rechazo de afiliacion de independiente despues de subsanacion')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${tipoIdCcf}','Tipo ID CCF','Tipo de la caja de Compensación','TIPO_ID_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de rechazo de afiliacion de independiente despues de subsanacion')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${numeroIdCcf}','Número ID CCF','Número de la caja de Compensación','NUMERO_ID_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de rechazo de afiliacion de independiente despues de subsanacion')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${webCcf}','Web CCF','Web de la caja de Compensación','WEB_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de rechazo de afiliacion de independiente despues de subsanacion')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${logoSuperservicios}','Logo SuperServicios','Logo de la caja de Compensación','LOGO_SUPERSERVICIOS',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de rechazo de afiliacion de independiente despues de subsanacion')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${firmaResponsableCcf}','Firma Responsable CCF','Firma de la caja de Compensación','FIRMA_RESPONSABLE_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de rechazo de afiliacion de independiente despues de subsanacion')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de la caja de Compensación','RESPONSABLE_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de rechazo de afiliacion de independiente despues de subsanacion')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Cargo de la caja de Compensación','CARGO_RESPONSABLE_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificacion de rechazo de afiliacion de independiente despues de subsanacion')) ;
