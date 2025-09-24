package com.asopagos.notificaciones.constants;

/**
 * <b>Descripción:</b> Clase que contiene las constantes con los nombres de los
 * NamedQueries del servicio <b>Historia de Usuario:</b> Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class NamedQueriesConstants {

    /** consulta de plantilla de comunicado por etiqueta */
    public static final String CONSULTAR_PLANTILLA_COMUNICADO_POR_ETIQUETA = "Notificaciones.plantillaComunicado.porEtiqueta";

    /** consulta de modulo por ID de la plantilla de comunicado */
    public static final String CONSULTAR_PLANTILLA_COMUNICADO_MODULO = "Notificaciones.plantillaComunicado.conModulo";

    /** consulta de variables de plantilla de comunicado por etiqueta */
    public static final String CONSULTAR_VARIABLE_COMUNICADO_POR_ETIQUETA = "Notificaciones.variableComunicado.porPlantillaComunicado";

    /** consulta de prioridades de un destinatario comunicado por etiqueta y proceso */
    public static final String CONSULTAR_PRIORIDAD_ETIQUETA_PROCESO_DESTINATARIO_COMUNICADO = "Notificaciones.prioridades.proceso.etiqueta.destinatario.comunicado";

    /** Consulta el Destinatario Grupo dado un id de GrupoPrioridad */
    public static final String CONSULTAR_DESTINATARIO_GRUPO_ID = "Notificaciones.destinatario.grupo.id";

    /** Consulta un destinatario por un id de destinatario */
    public static final String CONSULTAR_DESTINATARIO_ID = "Notificaciones.destinatario.id";

    /** Consulta un Destinatario por su grupo */
    public static final String CONSULTAR_DESTINATARIO_POR_GRUPO = "Notificaciones.destinatario.por.grupo";

    /**
     * Consulta la ubicacion de una empresa por el id de una empresa
     * OficinaPrincipal
     */
    public static final String CONSULTAR_UBICACION_EMPRESA_ID_UBICACION_PRINCIPAL_AFILIACION = "Notificaciones.ubicacion.empresa.id.ubicacion.principal.afiliacion";
    
    /**
     * Consulta la ubicacion de una empresa por el id de una empresa
     * OficinaPrincipal
     */
    public static final String CONSULTAR_UBICACION_EMPRESA_ID_UBICACION_PRINCIPAL_NOVEDAD = "Notificaciones.ubicacion.empresa.id.ubicacion.principal.novedad";

    /**
     * Consulta la ubicacion de una empresa  - oficina principal
     */
    public static final String CONSULTAR_UBICACION_EMPRESA_ID_UBICACION_PRINCIPAL_NOVEDAD_PERSONA_DEPWEB = "Notificaciones.ubicacion.empresa.id.ubicacion.principal.novedad.persona";

    /**
     * Consulta la ubicacion de una empresa por el id de una empresa
     * OficinaPrincipal
     */
    public static final String CONSULTAR_AFILIACION_UBICACION_EMPRESA_ID_ENVIO_CORRESPONDENCIA = "Notificaciones.afiliacion.ubicacion.empresa.id.envio.correspondencia";

    /**
     * Consulta la ubicacion de una empresa por el id de una empresa
     * OficinaPrincipal
     */
    public static final String CONSULTAR_NOVEDAD_UBICACION_EMPRESA_ID_ENVIO_CORRESPONDENCIA = "Notificaciones.novedad.ubicacion.empresa.id.envio.correspondencia";
    
    /**
     * Consulta la ubicacion de una empresa por el id de una empresa
     * Notificacion Judicial
     */
    public static final String CONSULTAR_UBICACION_EMPRESA_ID_NOTIFICACION_JUDICIAL_AFILIACION = "Notificaciones.ubicacion.empresa.id.notificacion.judicial.afiliacion";
    
    /**
     * Consulta la ubicacion de una empresa por el id de una empresa
     * NotificacionJudicial
     */
    public static final String CONSULTAR_UBICACION_EMPRESA_ID_NOTIFICACION_JUDICIAL_NOVEDAD = "Notificaciones.ubicacion.empresa.id.notificacion.judicial.novedad";

    /**Consultar el responsable de afiliaciones de la sucursal rol contacto empleador por id de empresa
     * Responsable afiliaciones
     */
    public static final String CONSULTAR_RESPONSABLE_AFILIACIONES_AFILIACION = "Notificaciones.responsable.afiliaciones.afiliacion";
    
    /**Consultar el responsable de afiliaciones de la sucursal rol contacto empleador por id de empresa
     * Responsable afiliaciones
     */
    public static final String CONSULTAR_RESPONSABLE_AFILIACIONES_NOVEDAD = "Notificaciones.responsable.afiliaciones.novedad";

    /**
     * Consultar el responsable de afiliaciones de la sucursal rol contanto empleador - Responsable Afiliaciones
     */
    public static final String CONSULTAR_RESPONSABLE_AFILIACIONES_NOVEDAD_PERSONA_DEP_WEB = "Notificaciones.responsable.afiliaciones.novedad.persona.empleador";

    /**Consultar el empleador por medio de la solicitud de una novedad y el tipo de afiliado 
     * Trabajador dependiente
     */
    public static final String CONSULTAR_EMPLEADOR_POR_SOLICITUD_NOVEDAD_ROL_AFILIADO = "Notificaciones.empleador.solicitud.novedad.rol.afiliado";
    
    /**Consulta el empleador por una solicitud de afiliacion y el tipo de un afiliado 
     * Trabajador dependiente
     */
    public static final String CONSULTAR_EMPLEADOR_POR_SOLICITUD_AFILIACION_ROL_AFILIADO = "Notificaciones.empleador.solicitud.afiliacion.rol.afiliado";

    /**Consultar una persona por una solicitud de novedad y el tipo de afiliado
     * Trabajador independiente
     */
    public static final String CONSULTAR_PERSONA_POR_SOLICITUD_NOVEDAD_ROL_AFILIADO = "Notificaciones.persona.solicitud.novedad.rol.afiliado";

    /**Consultar una persona por una solicitud de afiliacion y el tipo de afiliado
     * Trabajador dependiente
     */
    public static final String CONSULTAR_PERSONA_POR_SOLICITUD_AFILIACION_ROL_AFILIADO_DEPENDIENTE = "Notificaciones.persona.solicitud.afiliacion.rol.afiliado.dependiente";
    
    /**Consultar una persona por una solicitud de novedad y el tipo de afiliado
     * Trabajador dependiente
     */
    public static final String CONSULTAR_PERSONA_POR_SOLICITUD_NOVEDAD_ROL_AFILIADO_DEPENDIENTE = "Notificaciones.persona.solicitud.novedad.rol.afiliado.dependiente";

    /**Consultar una persona por una solicitud de afiliacion y el tipo de afiliado
     * Trabajador independiente
     */
    public static final String CONSULTAR_PERSONA_POR_SOLICITUD_AFILIACION_ROL_AFILIADO = "Notificaciones.persona.solicitud.afiliacion.rol.afiliado";
    /**Consultar una persona por una solicitud de novedad y el tipo de afiliado
     * Trabajador pensionado
     */
    public static final String CONSULTAR_PERSONA_POR_SOLICITUD_NOVEDAD_ROL_AFILIADO_PENSIONADO = "Notificaciones.persona.solicitud.novedad.rol.afiliado.pensionado";

    /**Consultar una persona por una solicitud de afiliacion y el tipo de afiliado
     * Trabajador pensionado
     */
    public static final String CONSULTAR_PERSONA_POR_SOLICITUD_AFILIACION_ROL_AFILIADO_PENSIONADO = "Notificaciones.persona.solicitud.afiliacion.rol.afiliado.pensionado";
    
    /**Consultar el responsable de aportes del rol contacto empleador por id de persona
     * Responsable Aportes
     */
    public static final String CONSULTAR_RESPONSABLE_APORTES_PERSONA = "Notificaciones.responsable.aportes.persona";

    /**Consultar el responsable de aportes del rol contacto empleador por id de empleador
     * Responsable aportes
     */
    public static final String CONSULTAR_RESPONSABLE_APORTES_EMPLEADOR = "Notificaciones.responsable.aportes.empleador";
    
    /**Consultar el responsable de subsidios del rol contacto empleador por id de persona
     * Responsable subsidios
     */
    public static final String CONSULTAR_RESPONSABLE_SUBSIDIOS_PERSONA = "Notificaciones.responsable.subsidios.persona";

    /**Consultar el responsable de subsidios del rol contacto empleador por id de empleador
     * Responsable subsidios
     */
    public static final String CONSULTAR_RESPONSABLE_SUBSIDIOS_EMPLEADOR = "Notificaciones.responsable.subsidios.empleador";

    /**Consultar una persona por una solicitud de novedad empleador y el tipo de afiliado
     * Trabajador pensionado
     */
    public static final String CONSULTAR_PERSONA_POR_SOLICITUD_NOVEDAD_EMPLEADOR_ROL_AFILIADO_PENSIONADO = "Notificaciones.persona.solicitud.novedad.empleador.rol.afiliado.pensionado";

    /**
     * Consultar una persona por una solicitud de novedad empleador y el tipo de afiliado
     * Trabajador independiente
     */
    public static final String CONSULTAR_PERSONA_POR_SOLICITUD_NOVEDAD_EMPLEADOR_ROL_AFILIADO_TRABAJADOR_INDEPENDIENTE = "Notificaciones.persona.solicitud.novedad.empleador.rol.afiliado.trabajador.independiente";

    /**
     * Consultar un afiliado principal por el id de una solicitud de novedad de empleador
     */
    public static final String CONSULTAR_EMPLEADOR_POR_SOLICITUD_NOVEDAD_ROL_AFILIADO_PRINCIPAL = "Notificaciones.empleador.solicitud.novedad.rol.afiliado.principal";
 
    /**
     * Se consulta un afiliado principal por el id de una solicitud de afliacion de empleador 
     */
    public static final String CONSULTAR_EMPLEADOR_POR_SOLICITUD_AFILIACION_ROL_AFILIADO_PRINCIPAL = "Notificaciones.empleador.solicitud.afiliacion.rol.afiliado.principal";

    /**
     * Se consulta un afiliado principal por el id de una solicitud de novedad de persona
     */
    public static final String CONSULTAR_PERSONA_POR_SOLICITUD_NOVEDAD_ROL_AFILIADO_PRINCIPAL = "Notificaciones.persona.solicitud.novedad.rol.afiliado.principal";

    /**
     * Se consulta un correo principal por el id del certificado
     */
    public static final String CONSULTAR_CORREO_PRIORIDAD_PERSONA = "Notificaciones.certificado.correo.prioridad.persona";

    /**
     * Se consulta un afiliado principal por el id de una solicitud de afiliacion de persona
     */
    public static final String CONSULTAR_PERSONA_POR_SOLICITUD_AFILIACION_ROL_AFILIADO_PRINCIPAL = "Notificaciones.persona.solicitud.afiliacion.rol.afiliado.principal";

    /**
     * Se consulta un asesor responsable del empleador por id de empleador si es el responsable de caja numero uno
     */
    public static final String CONSULTAR_ASESOR_RESPONSABLE_EMPLEADOR_NOVEDAD = "Notificaciones.empleador.responsable.caja.uno.novedad";

    /**
     * Se consulta  un asesor responsable del empleador por id de empleador  si es el resopnsable de caja numero dos
     */
    public static final String CONSULTAR_ASESOR_RESPONSABLE_EMPLEADOR_DOS_NOVEDAD = "Notificaciones.empleador.responsable.caja.dos.novedad";
    
    /**
     * Se consulta un asesor responsable del empleador por id de empleador si es el responsable de caja numero uno
     */
    public static final String CONSULTAR_ASESOR_RESPONSABLE_EMPLEADOR_AFILIACION = "Notificaciones.empleador.responsable.caja.uno.afiliacion";

    /**
     * Se consulta  un asesor responsable del empleador por id de empleador  si es el resopnsable de caja numero dos
     */
    public static final String CONSULTAR_ASESOR_RESPONSABLE_EMPLEADOR_DOS_AFILIACION = "Notificaciones.empleador.responsable.caja.dos.afiliacion";

    /**
     * Se consulta la entidad pagadora por el identificador de un empleador
     */
    public static final String CONSULTAR_ENTIDAD_PAGADORA = "Notificaciones.consultar.entidada.pagadora";

    /**
     * Se consulta todos los grupos de las prioridades de envio de correos
     */
    public static final String CONSULTAR_GRUPO_PRIORIDAD = "Notificaciones.consultar.grupo.prioridad";

    /**
     * Se actualiza la prioridad de un grupo por proceso y etiqueta    
     */
    public static final String ACTUALIZAR_PRIORIDAD_ETIQUETA_PROCESO_DESTINATARIO_COMUNICADO = "Notificaciones.actualizar.prioridad.destinatario";

    /**
     * Se actualizan las prioridades de los grupos parametrizadas por pantallas los grupos no priorizados su valor de prioridad sera 0
     */
    public static final String ACTUALIZAR_PRIORIDAD_CERO_ETIQUETA_PROCESO = "Notificaciones.actualizar.prioridad.cero";
    
    /**
     * Se consulta la prioridad destinatario relacionada solo por el proceso
     */
    public static final String CONSULTAR_PRIORIDAD_PROCESO_DESTINATARIO_COMUNICADO = "Notificaciones.prioridades.proceso.destinatario.comunicado";

    /**
     * Se actualiza los grupopos conprioridad cero cuando no se recibe ningún grupo parametrizado
     */
    public static final String ACTUALIZAR_GRUPOS_PRIORIDAD_CERO_ETIQUETA_PROCESO = "Notificaciones.actualizar.grupos.prioridad.cero";

    /**
     * Se consultan las etiquetas de un comunicado por proceso
     */
    public static final String CONSULTAR_ETIQUETAS_POR_PROCESO = "Notificaciones.consultar.etiqueta.por.proceso";

    /**
     * Se consulta el tipo de transacción
     */
    public static final String CONSULTAR_TIPO_TRANSACCION = "Notificaciones.tipo.transaccion";

    /**
     * Se consulta el correo del aportante si su solicitud es de aporte
     */
    public static final String CONSULTAR_APORTANTE_EMPLEADOR = "Notificaciones.consultar.aportante.empleador";

    /**
     * Se consulta el correo del aportante si su solicitud es de aporte
     */
    public static final String CONSULTAR_APORTANTE_INDP_PENS = "Notificaciones.consultar.aportante.persona";
    
    /**
     * Se consulta el correo del aportante si su solicitud es de una devolución de aporte
     */
    public static final String CONSULTAR_APORTANTE_DEVOLUCION_EMPLEADOR = "Notificaciones.consultar.aportante.devolucion.empleador";
    
    /**
     * Se consulta el correo del aportante si su solicitud es de una devolución de aporte
     */
    public static final String CONSULTAR_APORTANTE_DEVOLUCION_INDP_PENS = "Notificaciones.consultar.aportante.devolucion.persona";

    /**
     * Se consulta el correo del aportante si su solicitud es de una correción de aporte
     */
    public static final String CONSULTAR_APORTANTE_CORRECCION_EMPLEADOR = "Notificaciones.consultar.aportante.correccion.empleador";
    
    /**
     * Se consulta el correo del aportante si su solicitud es de una correción de aporte
     */
    public static final String CONSULTAR_APORTANTE_CORRECCION_INDP_PENS = "Notificaciones.consultar.aportante.correccion.persona";

    /**
     * Se consulta el correo del representante legal para una solicitud de novedad
     */
    public static final String CONSULTAR_NOVEDAD_REPRESENTANTE_LEGAL_UBICACION = "Notificaciones.consultar.novedad.representante.legal.ubicacion";

    /**
     * Consulta el correo del representante legal para una solicitud de novedad persona dep web
     */
    public static final String CONSULTAR_UBICACION_REPRESENTANTE_LEGAL_NOVEDAD_PERSONA_DEPWEB = "Notificaciones.consultar.representante.legal.ubicacion.novedad.depweb";

    /**
     * Se consulta el correo del representante legal para una solicitud de afiliacion
     */
    public static final String CONSULTAR_AFILIACION_REPRESENTANTE_LEGAL_UBICACION = "Notificaciones.consultar.afiliacion.representante.legal.ubicacion";
    
    /**
     * Se consulta el correo del representante legal para una solicitud de novedad
     */
    public static final String CONSULTAR_NOVEDAD_REPRESENTANTE_LEGAL_SUPLENTE_UBICACION = "Notificaciones.consultar.novedad.representante.legal.suplente.ubicacion";

    /**
     * Se consulta el correo del representante legal para una solicitud de afiliacion
     */
    public static final String CONSULTAR_AFILIACION_REPRESENTANTE_LEGAL_SUPLENTE_UBICACION = "Notificaciones.consultar.afiliacion.representante.legal.suplente.ubicacion";

    /**
     * Se consulta el correo del aportante si su solicitud es de aporte para un empleador
     */
    public static final String CONSULTAR_APORTANTE_REPRESENTANTE_LEGAL_EMPLEADOR = "Notificaciones.consultar.aportante.representante.legal.empleador";
    
    /**
     * Se consulta el correo del aportante si su solicitud es de aporte para una persona
     */
    public static final String CONSULTAR_APORTANTE_REPRESENTANTE_LEGAL_INDP_PENS = "Notificaciones.consultar.aportante.representante.legal.persona";

    /**
     * Se consulta el correo del aportante si su solicitud es de una devolución de aporte
     */
    public static final String CONSULTAR_APORTANTE_DEVOLUCION_REPRESENTANTE_LEGAL = "Notificaciones.consultar.aportante.devolucion.representante.legal";

    /**
     * Se consulta el correo del aportante si su solicitud es de una correción de aporte
     */
    public static final String CONSULTAR_APORTANTE_CORRECCION_REPRESENTANTE_LEGAL = "Notificaciones.consultar.aportante.correccion.representante.legal";
    
    /**
     * Se consulta el correo del aportante si su solicitud es de aporte
     */
    public static final String CONSULTAR_APORTANTE_RESPONSABLE_AFILIACIONES_EMPLEADOR = "Notificaciones.consultar.aportante.responsable.afiliaciones.empleador";
    
    /**
     * Se consulta el correo del aportante si su solicitud es de aporte
     */
    public static final String CONSULTAR_APORTANTE_RESPONSABLE_AFILIACIONES_INDP_PENS = "Notificaciones.consultar.aportante.responsable.afiliaciones.persona";

    /**
     * Se consulta el correo del aportante si su solicitud es de una devolución de aporte
     */
    public static final String CONSULTAR_APORTANTE_DEVOLUCION_RESPONSABLE_AFILIACIONES_EMPLEADOR = "Notificaciones.consultar.aportante.devolucion.responsable.afiliaciones.empleador";
    
    /**
     * Se consulta el correo del aportante si su solicitud es de una devolución de aporte
     */
    public static final String CONSULTAR_APORTANTE_DEVOLUCION_RESPONSABLE_AFILIACIONES_INDP_PENS = "Notificaciones.consultar.aportante.devolucion.responsable.afiliaciones.persona";

    /**
     * Se consulta el correo del aportante si su solicitud es de una correción de aporte
     */
    public static final String CONSULTAR_APORTANTE_CORRECCION_RESPONSABLE_AFILIACIONES_EMPLEADOR = "Notificaciones.consultar.aportante.correccion.responsable.afiliaciones.empleador";
    
    /**
     * Se consulta el correo del aportante si su solicitud es de una correción de aporte
     */
    public static final String CONSULTAR_APORTANTE_CORRECCION_RESPONSABLE_AFILIACIONES_INDP_PENS = "Notificaciones.consultar.aportante.correccion.responsable.afiliaciones.persona";
    
    /**
     * Se consulta el correo del aportante si su solicitud es de aporte
     */
    public static final String CONSULTAR_APORTANTE_RESPONSABLE_APORTES_EMPLEADOR = "Notificaciones.consultar.aportante.responsable.aportes.empleador";
    
    /**
     * Se consulta el correo del aportante si su solicitud es de aporte
     */
    public static final String CONSULTAR_APORTANTE_RESPONSABLE_APORTES_INDP_PENS = "Notificaciones.consultar.aportante.responsable.aportes.persona";

    /**
     * Se consulta el correo del aportante si su solicitud es de una correción de aporte
     */
    public static final String CONSULTAR_APORTANTE_CORRECCION_RESPONSABLE_APORTES_EMPLEADOR = "Notificaciones.consultar.aportante.correccion.responsable.aportes.empleador";

    /**
     * Se consulta el correo del aportante si su solicitud es de una correción de aporte
     */
    public static final String CONSULTAR_APORTANTE_CORRECCION_RESPONSABLE_APORTES_INDP_PENS = "Notificaciones.consultar.aportante.correccion.responsable.aportes.persona";

    /**
     * Se consulta el correo del aportante si su solicitud es de aporte
     */
    public static final String CONSULTAR_APORTANTE_ENTIDAD_PAGADORA_EMPLEADOR = "Notificaciones.consultar.aportante.entidad.pagadora.empleador";
    
    /**
     * Se consulta el correo del aportante si su solicitud es de aporte
     */
    public static final String CONSULTAR_APORTANTE_ENTIDAD_PAGADORA_INDP_PENS = "Notificaciones.consultar.aportante.entidad.pagadora.persona";

    /**
     * Se consulta el correo del aportante si su solicitud es de una correción de aporte
     */
    public static final String CONSULTAR_APORTANTE_CORRECCION_ENTIDAD_PAGADORA = "Notificaciones.consultar.aportante.correccion.entidad.pagadora";

    /**
     * Se consulta el correo del aportante si su solicitud es de una devolución de aporte
     */
    public static final String CONSULTAR_APORTANTE_DEVOLUCION_ENTIDAD_PAGADORA = "Notificaciones.consultar.aportante.devolucion.entidad.pagadora";

    /**
     * se consulta el correo de los socios relacionados a un empleador
     */
    public static final String CONSULTAR_SOCIOS_POR_SOLICITUD_NOVEDAD = "Notificaciones.socios.novedad.empleador";

    /**
     * Se consulta el correo de envio de correspondencia para una persona relacionada a un empleador
     */
    public static final String CONSULTAR_AFILIACION_PERSONA_UBICACION_EMPRESA_ID_ENVIO_CORRESPONDENCIA = "Notificaciones.afiliacion.persona.ubicacion.empresa.id.envio.correspondencia";
    
    /**
     * Se consulta el correo de envio de correspondencia para una persona relacionada a un empleador
     */
    public static final String CONSULTAR_AFILIACION_PERSONA_AUTORIZACION_EMPRESA = "Notificaciones.afiliacion.persona.autorizacion.empresa.id.envio.correspondencia";

    /**
     * Se consulta el correo de un representante legal para una persona relacionada a un empleador
     */
    public static final String CONSULTAR_AFILIACION_PERSONA_REPRESENTANTE_LEGAL_UBICACION = "Notificaciones.consultar.afiliacion.persona.representante.legal.ubicacion";

    /**
     * Se consulta el correo del responsable de afiliaciones para una persona relacionada a un empleador
     */
    public static final String CONSULTAR_RESPONSABLE_AFILIACIONES_AFILIACION_PERSONA = "Notificaciones.responsable.afiliaciones.afiliacion.persona";

    /**
     * Se consulta la autorizacion de envio para una persona
     */
    public static final String CONSULTAR_AUTORIZACION_AFILIACION_PERSONA = "Notificaciones.consultar.autorizacion.afiliacion.persona";

    /**
     * Se consulta la autorización de envio para la correccion de aportes
     */
    public static final String CONSULTAR_AUTORIZACION_APORTE = "Notificaciones.consultar.autorizacion.aportes";

    /**
     * Se consulta la autorización de envio para la correccion de aportes
     */
    public static final String CONSULTAR_AUTORIZACION_APORTE_CORRECCION = "Notificaciones.consultar.autorizacion.correccion.aportes";

    /**
     * Se consulta en la solicitud de devolucion de aportes el correo del responsable de aportes para un empleador
     */
    public static final String CONSULTAR_APORTANTE_DEVOLUCION_RESPONSABLE_APORTES_EMPLEADOR = "Notificaciones.consultar.aportante.devolucion.responsable.aportes.empleador";
    
    /**
     * Se consulta en la solicitud de devolucion de aportes el correo del responsable de aportes para una persona
     */
    public static final String CONSULTAR_APORTANTE_DEVOLUCION_RESPONSABLE_APORTES_INDP_PENS = "Notificaciones.consultar.aportante.devolucion.responsable.aportes.persona";

    /**
     * Se consulta la autorización de envio para la correccion de devolución
     */
    public static final String CONSULTAR_AUTORIZACION_APORTE_DEVOLUCION = "Notificaciones.consultar.autorizacion.devolucion.aportes";
    
    /**
     * Se consulta el correo del aportante si su solicitud es de aporte para un empleador
     */
    public static final String CONSULTAR_APORTANTE_ENVIO_CORRESPONDENCIA_EMPLEADOR = "Notificaciones.consultar.aportante.envio.correspondencia.empleador";
    
    /**
     * Se consulta el correo del aportante si su solicitud es de aporte para una persona
     */
    public static final String CONSULTAR_APORTANTE_ENVIO_CORRESPONDENCIA_INDP_PENS = "Notificaciones.consultar.aportante.envio.correspondencia.persona";

    /**
     * Se consulta el correo del aportante si su solicitud es de aporte para un empleador
     */
    public static final String CONSULTAR_APORTANTE_REPRESENTANTE_LEGAL_SUPLENTE_EMPLEADOR = "Notificaciones.consultar.aportante.representante.legal.suplente.empleador";
    
    /**
     * Se consulta el correo del aportante si su solicitud es de aporte para una persona
     */
    public static final String CONSULTAR_APORTANTE_REPRESENTANTE_LEGAL_SUPLENTE_INDP_PENS = "Notificaciones.consultar.aportante.representante.legal.suplente.persona";

    /**
     * Consultar el responsable de subsidios del rol contacto empleador para un empleador
     * Responsable subsidios
     */
    public static final String CONSULTAR_APORTANTE_RESPONSABLE_SUBSIDIOS_EMPLEADOR = "Notificaciones.aportante.responsable.subsidios.empleador";

    /**
     * Consultar el responsable de subsidios del rol contacto empleador para una persona
     * Responsable subsidios
     */
    public static final String CONSULTAR_APORTANTE_RESPONSABLE_SUBSIDIOS_INDP_PENS = "Notificaciones.aportante.responsable.subsidios.persona";

    /**
     * Se consulta el correo del aportante si su solicitud es de aporte para un empleador
     */
    public static final String CONSULTAR_APORTANTE_OFICINA_PRINCIPAL_EMPLEADOR = "Notificaciones.consultar.aportante.oficina.principal.empleador";

    /**
     * Se consulta el correo del aportante si su solicitud es de aporte para un persona
     */
    public static final String CONSULTAR_APORTANTE_OFICINA_PRINCIPAL_INDP_PENS = "Notificaciones.consultar.aportante.oficina.principal.persona";
    
    /**
     * Se consulta el correo del aportante si su solicitud es de aporte para un empleador
     */
    public static final String CONSULTAR_APORTANTE_NOTIFICACION_JUDICIAL_EMPLEADOR = "Notificaciones.consultar.aportante.notificacion.judicial.empleador";

    /**
     * Se consulta el correo del aportante si su solicitud es de aporte para un persona
     */
    public static final String CONSULTAR_APORTANTE_NOTIFICACION_JUDICIAL_INDP_PENS = "Notificaciones.consultar.aportante.notificacion.judicial.persona";

    /**
     * Se consulta el correo del rol contacto empleador responsable de afiliaciones relacionado a las solicitud preventiva como tipo de solicitante igual a empleador
     */
    public static final String CONSULTAR_GESTION_PREVENTIVA_RESPONSABLE_AFILIACIONES_EMPLEADOR = "Notificaciones.consultar.gestion.preventiva.responsable.afiliaciones.empleador";

    /**
     * Se consulta el correo del rol contacto empleador responsable de afiliaciones relacionado a las solicitud preventiva como tipo de solicitante igual a independiente o pensionado
     */
    public static final String CONSULTAR_GESTION_PREVENTIVA_RESPONSABLE_AFILIACIONES_INDP_PENS = "Notificaciones.consultar.gestion.preventiva.responsable.afiliaciones.persona";
 
    /**
     * Se consulta el correo del rol contacto empleador responsable de aportes relacionado a las solicitud preventiva como tipo de solicitante igual a empleador
     */
    public static final String CONSULTAR_GESTION_PREVENTIVA_RESPONSABLE_APORTES_EMPLEADOR = "Notificaciones.consultar.gestion.preventiva.responsable.aportes.empleador";

    /**
     * Se consulta el correo del rol contacto empleador responsable de aportes relacionado a las solicitud preventiva como tipo de solicitante igual a independiente o pensionado
     */
    public static final String CONSULTAR_GESTION_PREVENTIVA_RESPONSABLE_APORTES_INDP_PENS = "Notificaciones.consultar.gestion.preventiva.responsable.aportes.persona";

    /**
     * Se consulta el correo del rol contacto empleador responsable de subsidios relacionado a las solicitud preventiva como tipo de solicitante igual a empleador
     */
    public static final String CONSULTAR_GESTION_PREVENTIVA_RESPONSABLE_SUBSIDIOS_EMPLEADOR = "Notificaciones.gestion.preventiva.responsable.subsidios.empleador";

    /**
     * Se consulta el correo del rol contacto empleador responsable de subsidios relacionado a las solicitud preventiva como tipo de solicitante igual a independiente o pensionado
     */
    public static final String CONSULTAR_GESTION_PREVENTIVA_RESPONSABLE_SUBSIDIOS_INDP_PENS = "Notificaciones.gestion.preventiva.responsable.subsidios.persona";

    /**
     * Se consulta el correo del representante legal relacionado a la solicitud preventiva 
     */
    public static final String CONSULTAR_GESTION_PREVENTIVA_REPRESENTANTE_LEGAL = "Notificaciones.consultar.gestion.preventiva.representante.legal";

    /**
     * Se consulta el correo del representante legal suplente relacionado a una solicitud preventiva
     */
    public static final String CONSULTAR_GESTION_PREVENTIVA_REPRESENTANTE_LEGAL_SUPLENTE = "Notificaciones.consultar.gestion.preventiva.representante.legal.suplente";

    /**
     * Se consulta el ocrreo de la ubicacion principal relacionada a una solicitud preventiva
     */
    public static final String CONSULTAR_GESTION_PREVENTIVA_UBICACION_PRINCIPAL = "Notificaciones.consultar.gestion.preventiva.ubicacion.principal";
    
    /**
     * Se consulta el ocrreo de la envio correspondencia relacionada a una solicitud preventiva
     */
    public static final String CONSULTAR_GESTION_PREVENTIVA_ENVIO_CORRESPONDENCIA = "Notificaciones.consultar.gestion.preventiva.envio.correspondencia";
    
    /**
     * Se consulta el ocrreo de la notificacion judicial relacionada a una solicitud preventiva
     */
    public static final String CONSULTAR_GESTION_PREVENTIVA_NOTIFICACION_JUDICIAL = "Notificaciones.consultar.gestion.preventiva.notificacion.judicial";
    
    /**
     * Consultar el correo para la notificación de un Jefe de Hogar para las postulaciones FOVIS.
     */
    public static final String CONSULTAR_POSTULACION_FOVIS_ENVIO_CORRESPONDENCIA = "Notificaciones.consultar.notificacion.postulacion.fovis";

    /**
     * Consultar el correo para la notificación de un Jefe de Hogar para las postulaciones FOVIS en proceso de verificacion.
     */
    public static final String CONSULTAR_ENVIO_CORRESPONDENCIA_POSTULACION_FOVIS_VERIFICACION = "Notificaciones.consultar.notificacion.postulacion.fovis.verificacion";
    
    /**
     * Consulta el correo si este cuenta con la autorización para el representante legal
     */
    public static final String CONSULTAR_AUTORIZACION_CARTERA = "Notificaciones.consultar.autorizacion.cartera";

    /**
     * Consulta el correo si cuenta con la autorización para el afiliado principal 
     */
    public static final String CONSULTAR_AUTORIZACION_CARTERA_AFILIADO_PRINCIPAL = "Notificaciones.consultar.autorizacion.cartera.afiliado";

    /**
     * Consultar el correo para la notificación de un Jefe de Hogar para las novedades FOVIS.
     */
    public static final String CONSULTAR_NOVEDAD_FOVIS_ENVIO_CORRESPONDENCIA = "Notificaciones.consultar.notificacion.novedad.fovis";
    
    /**
     * Consultar el correo para la notificación de un Jefe de Hogar para la legalizacion desembolso FOVIS.
     */
    public static final String CONSULTAR_LEGALIZACION_DESEMBOLSO_FOVIS_ENVIO_CORRESPONDENCIA = "Notificaciones.consultar.notificacion.legalizacion.fovis";
    
    /**
     * Consulta el correo si este cuenta con la autorización para el responsable de aportes
     */
    public static final String CONSULTAR_AUTORIZACION_CARTERA_APORTES = "Notificaciones.consultar.autorizacion.cartera.aportes";

    /**
     * Consulta el correo si este cuenta con la autorización para el responsable de subsidios
     */
    public static final String CONSULTAR_AUTORIZACION_CARTERA_SUBSIDIO = "Notificaciones.consultar.autorizacion.cartera.subsidios";

    /**
     * Consulta el correo si este cuenta con la autorización para el representante legal suplente
     */
    public static final String CONSULTAR_AUTORIZACION_CARTERA_LEGAL_SUPLENTE = "Notificaciones.consultar.autorizacion.cartera.suplente";

    /**
     * Consulta el correo si este cuenta con la autorización para el responsable de afiliaciones
     */
    public static final String CONSULTAR_AUTORIZACION_CARTERA_AFILIACIONES = "Notificaciones.consultar.autorizacion.cartera.afiliaciones";

    /**
     * Consulta el correo si este cuenta con la autorización para la ubicacion principal
     */
    public static final String CONSULTAR_AUTORIZACION_CARTERA_PRINCIPAL = "Notificaciones.consultar.autorizacion.cartera.principal";
    
    /**
     * Consulta el correo si este cuenta con la autorización para la ubicacion envio de correspondencia
     */
    public static final String CONSULTAR_AUTORIZACION_CARTERA_CORRESPONDENCIA = "Notificaciones.consultar.autorizacion.cartera.correspondencia";
    
    /**
     * Consulta el correo si este cuenta con la autorización para la ubicacion notificación judicial
     */
    public static final String CONSULTAR_AUTORIZACION_CARTERA_JUDICIAL = "Notificaciones.consultar.autorizacion.cartera.judicial";
    
    /**
     * constante que representa la consulta del tipoTransaccion asociado a una solicitud para el proceso de alertas en BPM
     */
    public static final String CONSULTAR_TIPO_TX_POR_SOLICITUD = "Notificaciones.consultar.tipoTransaccionPorSolicitud";
    
    /**
     * Consulta si el empleador autoriza el envío de comunicados
     */
    public static final String CONSULTAR_AUTORIZACION_CORRESPONDENCIA_EMPLEADOR = "Notificaciones.consultar.autorizacion.correspondencia.empleador";
    
    /**
     * Consultar autorizacion novedad empleador
     */
    public static final String CONSULTAR_AUTORIZACION_NOVEDAD_EMPLEADOR = "Notificaciones.consultar.autorizacion.novedad.empleador";
    
    /**
     * Consulta la autorizacion de envio de correos del empleador a partir de una solicitud como aportante
     */
    public static final String CONSULTAR_APORTANTE_AUTORIZACION_CORRESPONDENCIA_EMPLEADOR = "Notificaciones.consultar.aportante.autorizacion.correspondencia.empleador";
    
    /**
     * Consulta la autorizacion de envio de correos de un independiente o pensionado a partir de una solicitud como aportante
     */
    public static final String CONSULTAR_APORTANTE_AUTORIZACION_CORRESPONDENCIA_INDP_PENS = "Notificaciones.consultar.aportante.autorizacion.correspondencia.persona";
    
    /**
     * Se consulta la autorizacion de envio para el caso de un empleador en gestion preventiva
     */
    public static final String CONSULTAR_AUTORIZACION_GESTION_PREVENTIVA = "Notificaciones.consultar.autorizacion.gestion.preventiva";
    
    /**
     * Se consulta la autorizacion de envio para el caso de un empleador en procesos de cartera
     */
    public static final String CONSULTAR_AUTORIZACION_ENVIO_CARTERA = "Notificaciones.consultar.autorizacion.envio.cartera.correspondencia";
    
    /**
     * Se consulta la autorizacion de envio para las afiliaciones como empleador
     */
    public static final String CONSULTAR_AUTORIZACION_ENVIO_AFILIACION_EMPLEADOR = "Notificaciones.autorizacion.empresa.afiliacion";
    
    /**
     * Se consultan los datos relevantes para el envío del correo a la 
     */
    public static final String CONSULTAR_AFILIACION_PERSONA_UBICACION_EMPRESA_NOTIFICACION_JUDICIAL = "Notificaciones.afiliacion.persona.ubicacion.empresa.notificacion.judicial";
    
    /**
     * Se consultan los datos relacionador al empleador a apartir del rol del empleado.
     */
    public static final String CONSULTAR_AFILIACION_PERSONA_UBICACION_EMPRESA_UBICACION_PRINCIPAL = "Notificaciones.afiliacion.persona.ubicacion.empresa.ubicacion.principal";
    
    /**
     * Se consulta el id del empleador asociado a una soliciud de afiliación.
     */
    public static final String CONSULTAR_EMPLEADOR_AFILIACION = "Notificaciones.consultar.empleador.afiliacion";
    
    /**
     * Se consultan los datos (de acuerdo al tipo de reponsable) de un empleador para el proceso PILA
     */
    public static final String CONSULTAR_PILA_RESPONSABLE_EMPLEADOR = "Notificaciones.consultar.responsable.PILA";
    
    /**
     * Se consultan los datos (de acuerdo al tipo de ubicacion de la empresa) de un empleador para el proceo PILA
     */
    public static final String CONSULTAR_PILA_UBICACION_EMPLEADOR = "Notificaciones.consultar.ubicacion.PILA";
    
    /**
     * Se consultan los datos del representante legal asociado a una empresa/empleador para el proceso PILA
     */
    public static final String CONSULTAR_PILA_REPRESENTANTE_LEGAL_SUPLENTE_EMPLEADOR = "Notificaciones.consultar.representanteLegalSuplente.PILA";
    
    /**
     * Se consultan los datos del rol (responsable aportes, subsidios, afiliaciones) asociado a un empleador para el proceso de gestion de cobro manual
     */
    public static final String CONSULTAR_GESTION_COBRO_MANUAL_ROL_EMPLEADOR = "Notificaciones.consultar.rolResponsable.gestionCobroManual";
    
    /**
     * Se consulta el estado de afiliacion de una persona
     */
    public static final String CONSULTAR_ESTADO_AFILIACION_PERSONA = "Notificaciones.consultar.estado.afiliacion.persona";
    
    /**
     * Se consulta el estado de afiliacion de un empleador
     */
    public static final String CONSULTAR_ESTADO_AFILIACION_EMPLEADOR = "Notificaciones.consultar.estado.afiliacion.empleador";

    /**texto auditoria seven alex */
    public static final String   CONSULTAR_TEXTO_AUDITORIA_SEVEN ="Notificaciones.consultar.texto.auditoria.respuesta.seven";

    /**Constante archivo adjunto comunicados */
    public static final String CONSULTAR_ARCHIVO_ADJUNTO = "Notificaciones.consultar.archivo.adjunto";

    /**Consulta numero identificacion empleador (cartera) */
    public static final String OBTENER_NUMERO_IDENTIFICACION_EMPRESA_CARTERA = "Notificaciones.obtener.numero.identificacion.empresa.cartera";
    
}
