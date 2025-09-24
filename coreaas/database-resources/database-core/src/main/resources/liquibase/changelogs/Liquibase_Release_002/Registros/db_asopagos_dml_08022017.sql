--liquibase formatted sql

--changeset halzate:01 
--comment: Se agrega campo a la entidad surcursalEmpresa

ALTER TABLE sucursalEmpresa ADD sueEstadoSucursal varchar(25)

--changeset jusanchez:02
--comment: modificacion del proceso 122 de cargue múltiple a tipo de dato string
UPDATE LineDefinitionLoad SET numberGroup=NULL,requiredInGroup=null WHERE ID=1221
UPDATE LineLoadCatalog SET tenantId=null, lineOrder=1 WHERE id=1221
UPDATE FieldLoadCatalog SET datatype='STRING' WHERE id>=1221 and id<=1243
UPDATE FIELDDEFINITIONLOAD SET formatDate=null WHERE id>=1221 AND id<=1243

--changeset atoro:03
--comment: Inserccion datos en tabla novedad y validacionProceso
--Novedad 1. Cambio tipo y/o número documento de identificación
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
values('BACK','CAMBIO_TIPO_NUMERO_DOCUMENTO');
--
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_ESTADO_TIPO_DOCUMENTO_NOVEDAD','PERSONA_NATURAL',1,'ACTIVO');

--Novedad 2. Cambio de razón social o nombre
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
values('BACK','CAMBIO_RAZON_SOCIAL_NOMBRE');

--Novedad 3. Cambio de naturaleza jurídica
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','CAMBIO_NATURALEZA_JURIDICA');
	
--Novedad 4. Cambio de actividad económica principal
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','CAMBIO_ACTIVIDAD_ECONOMICA_PRINCIPAL_PRESENCIAL');
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','CAMBIO_ACTIVIDAD_ECONOMICA_PRINCIPAL_WEB');
   
-- Novedad 5. Cambios en otros datos de identificación del empleador
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('FRONT','CAMBIOS_OTROS_DATOS_IDENTIFICACION_EMPLEADOR');   
   
-- Novedad 6. Actualización datos de contacto oficina principal
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('FRONT','ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_PRESENCIAL'); 
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('FRONT','ACTUALIZACION_DATOS_OFICINA_PRINCIPAL_WEB');
	
--Novedad 7. Actualización datos de envío de correspondencia
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('FRONT','ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_PRESENCIAL'); 
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('FRONT','ACTUALIZACION_DATOS_ENVIO_CORRESPONDENCIA_WEB');
	
--Novedad 8 Actualización dirección notificación judicial
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','ACTUALIZACION_DIRECCION_NOTIFICACION_JUDICIAL_PRESENCIAL'); 
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','ACTUALIZACION_DIRECCION_NOTIFICACION_JUDICIAL_WEB');

--Novedad 9	Cambios  en representante legal, su suplente o socios
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','CAMBIOS_REPRESENTANTE_LEGAL_SUPLENTE_O_SOCIOS_PRESENCIAL'); 
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','CAMBIOS_REPRESENTANTE_LEGAL_SUPLENTE_O_SOCIOS_WEB');
	
--Novedad 10 Cambio de medio de pago empleador
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','CAMBIO_MEDIO_PAGO_EMPLEADOR_PRESENCIAL'); 
--Novedad 10 Cambio de medio de pago empleador
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','CAMBIO_MEDIO_PAGO_EMPLEADOR_WEB'); 
	
--Novedad 11 Cambio código o nombre de sucursal
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','CAMBIO_CODIGO_NOMBRE_SUCURSAL'); 

--Novedad 12 Cambio de datos sucursal
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('FRONT','CAMBIO_DATOS_SUCURSAL_PRESENCIA');
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('FRONT','CAMBIO_DATOS_SUCURSAL_WEB');

--Novedad 13 Cambio de actividad económica sucursal
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','CAMBIO_ACTIVIDAD_ECONOMICA_SUCURSAL_PRESENCIAL'); 
--Novedad 13 Cambio de actividad económica sucursal
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','CAMBIO_ACTIVIDAD_ECONOMICA_SUCURSAL_WEB'); 

--Novedad 14 Cambio de medio de pago sucursal
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','CAMBIO_MEDIO_PAGO_SUCURSAL_PRESENCIAL'); 
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','CAMBIO_MEDIO_PAGO_SUCURSAL_WEB'); 

--Novedad 15 Activar o inactivar código sucursal debe conicidir con PILA
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','ACTIVAR_INACTIVAR_CODIGO_SUCURSAL_DEBE_COINCIDIR_CON_PILA'); 

--Novedad 16 Agregar sucursal
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','AGREGAR_SUCURSAL'); 

--Novedad 17 Inactivar sucursal
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','INACTIVAR_SUCURSAL'); 

--Novedad 18 Cambios en roles de contacto
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('FRONT','CAMBIOS_ROLES_CONTACTO_PRESENCIAL');
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('FRONT','CAMBIOS_ROLES_CONTACTO_WEB');

--Novedad 19 Cambio de responsable de contacto CCF
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','CAMBIO_RESPONSABLE_CONTACTOS_CFF');
	
-- Presencial novedades 2-16, 18-19
--------------------------------------------------------------------------------------------------------------
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_ESTADO_AFILIACION_NOVEDAD','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_ESTADO_AFILIACION_NOVEDAD','EMPLEADOR_DE_SERVICIO_DOMESTICO',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_ESTADO_AFILIACION_NOVEDAD','ENTIDAD_SIN_ANIMO_DE_LUCRO',1,'ACTIVO');      
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_ESTADO_AFILIACION_NOVEDAD','ORGANIZACION_RELIGIOSA_O_PARROQUIA',1,'ACTIVO');      
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_ESTADO_AFILIACION_NOVEDAD','PERSONA_JURIDICA',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_ESTADO_AFILIACION_NOVEDAD','PERSONA_NATURAL',1,'ACTIVO');            
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_ESTADO_AFILIACION_NOVEDAD','PROPIEDAD_HORIZONTAL',1,'ACTIVO');   

-- Web novedades 2-16, 18-19
--------------------------------------------------------------------------------------------------------------
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_WEB','NOVEDAD','VALIDACION_ESTADO_AFILIACION_NOVEDAD','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO',1,'ACTIVO');     
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_WEB','NOVEDAD','VALIDACION_ESTADO_AFILIACION_NOVEDAD','EMPLEADOR_DE_SERVICIO_DOMESTICO',1,'ACTIVO'); 
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_WEB','NOVEDAD','VALIDACION_ESTADO_AFILIACION_NOVEDAD','ENTIDAD_SIN_ANIMO_DE_LUCRO',1,'ACTIVO');  
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_WEB','NOVEDAD','VALIDACION_ESTADO_AFILIACION_NOVEDAD','ORGANIZACION_RELIGIOSA_O_PARROQUIA',1,'ACTIVO');      
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_WEB','NOVEDAD','VALIDACION_ESTADO_AFILIACION_NOVEDAD','PERSONA_JURIDICA',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_WEB','NOVEDAD','VALIDACION_ESTADO_AFILIACION_NOVEDAD','PERSONA_NATURAL',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_WEB','NOVEDAD','VALIDACION_ESTADO_AFILIACION_NOVEDAD','PROPIEDAD_HORIZONTAL',1,'ACTIVO'); 

--Novedad 20 Activar beneficios de Ley 1429 de 2010
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','ACTIVAR_BENEFICIOS_LEY_1429_2010_PRESENCIAL');
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','ACTIVAR_BENEFICIOS_LEY_1429_2010_WEB');
	
-- Presencial
--------------------------------------------------------------------------------------------------------------
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_ACTIVAR_BENEFICIARIO_LEY_1429_2010_NOVEDAD','PERSONA_JURIDICA',1,'ACTIVO');
   
-- Web
--------------------------------------------------------------------------------------------------------------
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_WEB','NOVEDAD','VALIDACION_ACTIVAR_BENEFICIARIO_LEY_1429_2010_NOVEDAD','PERSONA_JURIDICA',1,'ACTIVO');

--Novedad 21 Inactivar beneficios de Ley 1429 de 2010
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','INACTIVAR_BENEFICIOS_LEY_1429_2010_PRESENCIAL');
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','INACTIVAR_BENEFICIOS_LEY_1429_2010_WEB');
	
-- Presencial
--------------------------------------------------------------------------------------------------------------
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_INACTIVAR_BENEFICIARIO_LEY_1429_2010_NOVEDAD','PERSONA_JURIDICA',1,'ACTIVO');
   
-- Web
--------------------------------------------------------------------------------------------------------------
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_WEB','NOVEDAD','VALIDACION_INACTIVAR_BENEFICIARIO_LEY_1429_2010_NOVEDAD','PERSONA_JURIDICA',1,'ACTIVO');
   

--Novedad 22 Activar beneficios de Ley 590 de 2000
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','ACTIVAR_BENEFICIOS_LEY_590_2000_PRESENCIAL');
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','ACTIVAR_BENEFICIOS_LEY_590_2000_WEB');
	
-- Presencial
--------------------------------------------------------------------------------------------------------------
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_ACTIVAR_BENEFICIOS_DE_LEY_590_2000_NOVEDAD','PERSONA_JURIDICA',1,'ACTIVO');
   
-- Web
--------------------------------------------------------------------------------------------------------------
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_WEB','NOVEDAD','VALIDACION_ACTIVAR_BENEFICIOS_DE_LEY_590_2000_NOVEDAD','PERSONA_JURIDICA',1,'ACTIVO');

--Novedad 23 Inactivar beneficios de Ley 590 de 2000
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','INACTIVAR_BENEFICIOS_LEY_590_2000_PRESENCIAL');
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','INACTIVAR_BENEFICIOS_LEY_590_2000_WEB');
	
-- Presencial
--------------------------------------------------------------------------------------------------------------
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_INACTIVAR_BENEFICIOS_LEY_2000_NOVEDAD','PERSONA_JURIDICA',1,'ACTIVO');
   
-- Web
--------------------------------------------------------------------------------------------------------------
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_WEB','NOVEDAD','VALIDACION_INACTIVAR_BENEFICIOS_LEY_2000_NOVEDAD','PERSONA_JURIDICA',1,'ACTIVO');

--Novedad 24 Traslado de trabajadores entre sucursales
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','TRASLADO_TRABAJADORES_ENTRE_SUCURSALES');
	
-- Presencial
--------------------------------------------------------------------------------------------------------------
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_TRASLADO_TRABAJADORES_ENTRE_SUCURSALES_NOVEDAD','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_TRASLADO_TRABAJADORES_ENTRE_SUCURSALES_NOVEDAD','EMPLEADOR_DE_SERVICIO_DOMESTICO',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_TRASLADO_TRABAJADORES_ENTRE_SUCURSALES_NOVEDAD','ENTIDAD_SIN_ANIMO_DE_LUCRO',1,'ACTIVO');      
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_TRASLADO_TRABAJADORES_ENTRE_SUCURSALES_NOVEDAD','ORGANIZACION_RELIGIOSA_O_PARROQUIA',1,'ACTIVO');      
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_TRASLADO_TRABAJADORES_ENTRE_SUCURSALES_NOVEDAD','PERSONA_JURIDICA',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_TRASLADO_TRABAJADORES_ENTRE_SUCURSALES_NOVEDAD','PERSONA_NATURAL',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_TRASLADO_TRABAJADORES_ENTRE_SUCURSALES_NOVEDAD','PROPIEDAD_HORIZONTAL',1,'ACTIVO');
	
--Novedad 25 Sustitución patronal
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','SUSTITUCION_PATRONAL');
	
-- Presencial
--------------------------------------------------------------------------------------------------------------
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_SUSTITUCION_PATRONAL_NOVEDAD','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_SUSTITUCION_PATRONAL_NOVEDAD','EMPLEADOR_DE_SERVICIO_DOMESTICO',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_SUSTITUCION_PATRONAL_NOVEDAD','ENTIDAD_SIN_ANIMO_DE_LUCRO',1,'ACTIVO');      
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_SUSTITUCION_PATRONAL_NOVEDAD','ORGANIZACION_RELIGIOSA_O_PARROQUIA',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_SUSTITUCION_PATRONAL_NOVEDAD','PERSONA_JURIDICA',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_SUSTITUCION_PATRONAL_NOVEDAD','PERSONA_NATURAL',1,'ACTIVO');            
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_SUSTITUCION_PATRONAL_NOVEDAD','PROPIEDAD_HORIZONTAL',1,'ACTIVO');

--Novedad 26 Desafiliación
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','DESAFILIACION');
	
-- Presencial
--------------------------------------------------------------------------------------------------------------
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_DESAFILIACION_NOVEDAD','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_DESAFILIACION_NOVEDAD','EMPLEADOR_DE_SERVICIO_DOMESTICO',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_DESAFILIACION_NOVEDAD','ENTIDAD_SIN_ANIMO_DE_LUCRO',1,'ACTIVO');      
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_DESAFILIACION_NOVEDAD','ORGANIZACION_RELIGIOSA_O_PARROQUIA',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_DESAFILIACION_NOVEDAD','PERSONA_JURIDICA',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_DESAFILIACION_NOVEDAD','PERSONA_NATURAL',1,'ACTIVO');            
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_DESAFILIACION_NOVEDAD','PROPIEDAD_HORIZONTAL',1,'ACTIVO');

--Novedad 27 Anulación de afiliación
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('BACK','ANULACION_AFILIACION');
	
-- Presencial
--------------------------------------------------------------------------------------------------------------
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_ANULACION_AFILIACION_NOVEDAD','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_ANULACION_AFILIACION_NOVEDAD','EMPLEADOR_DE_SERVICIO_DOMESTICO',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_ANULACION_AFILIACION_NOVEDAD','ENTIDAD_SIN_ANIMO_DE_LUCRO',1,'ACTIVO');      
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_ANULACION_AFILIACION_NOVEDAD','ORGANIZACION_RELIGIOSA_O_PARROQUIA',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_ANULACION_AFILIACION_NOVEDAD','PERSONA_JURIDICA',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_ANULACION_AFILIACION_NOVEDAD','PERSONA_NATURAL',1,'ACTIVO');            
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('NOVEDADES_EMPRESAS_PRESENCIAL','NOVEDAD','VALIDACION_ANULACION_AFILIACION_NOVEDAD','PROPIEDAD_HORIZONTAL',1,'ACTIVO');

--Novedad 28 Inactivar automáticamente beneficios de Ley 1429 de 2010
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('SISTEMA_AUTOMATICO','INACTIVAR_AUTOMATICAMENTE_BENEFICIO_LEY_1429_2010');

--Novedad 29 Inactivar automáticamente beneficios de Ley 590 de 2000
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('SISTEMA_AUTOMATICO','INACTIVAR_AUTOMATICAMENTE_BENEFICIOS_LEY_590_2000');

--Novedad 30 Inactivar automáticamente cuenta web de empleador
insert into Novedad (novPuntoResolucion,novTipoTransaccion)
	values('SISTEMA_AUTOMATICO','INACTIVAR_AUTOMATICAMENTE_CUENTA_WEB_EMPLEADOR');





