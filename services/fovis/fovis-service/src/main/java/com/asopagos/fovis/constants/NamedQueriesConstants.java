package com.asopagos.fovis.constants;

/**
 * <b>Descripción:</b> Clase que contiene las constantes con los nombres de los
 * NamedQueries del servicio de FOVIS <b>Historia de Usuario:</b>Proceso: 3.2
 * FOVIS
 * 
 * @author Fabian López<flopez@heinsohn.com.co>
 */
public class NamedQueriesConstants {

    /**
     * Constante con el nombre de la consulta que obtiene la lista de proyectos
     * de vivienda asociados a un oferente
     */
    public static final String CONSULTAR_PROYECTO_POR_OFERENTE = "Fovis.Consultar.ProyectoSolucionVivienda.Oferente";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de proyectos
     * de vivienda asociados a un oferente filtrados por si están registrados o no
     */
    public static final String CONSULTAR_PROYECTO_POR_OFERENTE_REG_NOREG = "Fovis.Consultar.ProyectoSolucionVivienda.Oferente.regNoReg";

    /**
     * Consulta los datos generales de la parametrización de FOVIS
     */
    public static final String CONSULTAR_PARAMETRIZACION_GENERAL_FOVIS = "Parametrizacion.consultarDatosGeneralesFovis";

    /**
     * Consulta la parametricación de las modalidades para FOVIS
     */
    public static final String CONSULTAR_PARAMETRIZACION_MODALIDADES = "Parametrizacion.Modalidad.consultarParametrizacionModalidades";

    /**
     * Consulta la parametricación Consultar el tope del decreto 1467 de 2019
     */
    public static final String CONSULTAR_TOPE_POR_MUNICIPIO = "Parametrizacion.Dec14672019.consultarTopePorMunicipio";

    /**
     * Consulta los randos SVF por la modalidad.
     */
    public static final String CONSULTA_RANGOS_SVF_POR_MODALIDAD = "Parametrizacion.RangoSVFModalidad.consultarRangosSVFPorModalidad";

    /**
     * Consulta las formas de pago por la modalidad.
     */
    public static final String CONSULTA_FORMAS_DE_PAGO_POR_MODALIDAD = "Parametrizacion.FormaDePago.consultarFormasDePagoPorModalidad";

    /**
     * Consulta la parametricación de los medios de pago para FOVIS
     */
    public static final String CONSULTAR_MEDIOS_DE_PAGO_FOVIS = "Parametrizacion.MediosPagoCCF.consultarMediosDePagoAplicanFovis";

    /**
     * Consulta los ciclos de asignación registrados para el año especificado
     */
    public static final String CONSULTA_CICLOS_ASIGNACION_POR_ANIO = "Parametrizacion.CicloAsignacion.consultarCiclosAsignacionPorAnio";
    
    /**
     * Consulta los ciclos de asignación registrados para el año especificado
     */
    public static final String CONSULTA_CICLOS_ASIGNACION_MODALIDAD = "Parametrizacion.CicloAsignacion.consultarCiclosAsignacionPorModalidad";

    /**
     * Consulta las modalidades por ciclo de asignación
     */
    public static final String CONSULTA_MODALIDADES_POR_CICLO = "Parametrizacion.CicloModalidad.consultarModalidadesPorCiclo";

    /**
     * Consulta las modalidades por ciclo de asignación
     */
    public static final String CONSULTA_MODALIDADES_POR_CICLO_HABILITADAS = "Parametrizacion.CicloModalidad.consultarModalidadesPorCicloHabilitadas";

    /**
     * Consulta la parametricación de ejecución programada de novedades para
     * fovis
     */
    public static final String CONSULTA_PROGRAMACION_NOVEDADES = "Parametrizacion.CicloModalidad.consultarProgramacionNovedades";

    /**
     * Remueve los rangos SVF asociados a la modalidad enviada
     */
    public static final String REMOVER_RANGOS_SVF_POR_MODALIDAD = "Parametrizacion.RangoTopeValorSFV.removerRangosTopeValorSFVPorModalidad";

    /**
     * Remueve las formas de pago asociadas a la modalidad
     */
    public static final String REMOVER_FORMAS_DE_PAGO_POR_MODALIDAD = "Parametrizacion.FormaPagoModalidad.removerFormasPagoPorModalidad";

    /**
     * Remueve los registros de modalidades asociadas al ciclo de asignación
     * enviado
     */
    public static final String REMOVER_MODALIDADES_POR_CICLO_ASIGNACION = "Parametrizacion.CicloModalidad.removerModalidadesPorCiclo";

    /**
     * Consulta los medios de pago por ID
     */
    public static final String CONSULTA_MEDIO_PAGO_CCF_POR_ID = "MediosDePagoCCF.consultarMedioDePagoPorId";

    /**
     * Consulta la cantidad de postulaciones por ciclo de asignación
     */
    public static final String CANTIDAD_POSTULACIONES_POR_CICLO = "PostulacionFOVIS.cantidadPostulacionesPorCiclo";

    /**
     * Consulta que se encarga de buscar una solicitud de postulacion por el id de la solicitud global
     */
    public static final String CONSULTAR_SOLICITUD_POR_ID_SOLICITUD_GLOBAL = "FOVIS.consultar.solicitudPostulacion.idSolicitudGlobal";

    /**
     * Obtiene la cantidad de escalamientos sin resultado de análisis, por solicitud global
     */
    public static final String CANTIDAD_ESCALAMIENTOS_SIN_RESULTADO_POR_SOLICITUD = "FOVIS.EscalamientoSolicitud.cantidadEscalamientosSinResultado";

    /**
     * Consulta los ciclos de asignación que no son predecesores de ningún otro
     */
    public static final String CONSULTA_CICLOS_ASIGNACION_NO_PREDECESORES = "FOVIS.CicloAsignacion.consultarCiclosNoPredecesores";

    /**
     * Consulta que se encarga de buscar una postulacion FOVIS por el id.
     */
    public static final String CONSULTAR_POSTULACION_FOVIS_POR_ID = "FOVIS.consultar.postulacionFOVIS.idPostulacion";

    /**
     * Consulta que se encarga de buscar las personaDetalle asociadas a una postulacion FOVIS.
     */
    public static final String CONSULTAR_PERSONA_DETALLE_POR_POSTULACION = "FOVIS.consultar.PersonaDetalle.idPostulacion";

    /**
     * Constante con el nombre de la consulta que obtiene una solicitud global, por número de radicado
     */
    public static final String CONSULTAR_SOLICITUD = "FOVIS.Consultar.Solicitud.NumeroRadicado";

    /**
     * Constante con el nombre del querie que elimina las condiciones especiales que no tenga asociadas una persona.
     */
    public static final String CONSULTAR_CONDICIONES_PERSONA = "FOVIS.condicionEspecial.consultarCondiciones";

    /**
     * Constante con el nombre de la consulta que obtiene el departamento con la marca de excepción que aplica al Subsidio Familiar de
     * Vivienda urbano
     */
    public static final String CONSULTAR_DEPARTAMENTO_EXCEPCION_FOVIS = "Fovis.Consultar.Departamento.ExcepcionFOVIS";

    /**
     * Consulta si el recurso complementario ya existe para la postulación y con el nombre asociado
     */
    public static final String CONSULTAR_RECURSO_COMP_NOMBRE_IDPOSTULACION = "FOVIS.recursoComplementario.consultarPorNombrePostulacion";

    /**
     * Consulta si el recurso complementario ya existe para la postulación y con el nombre asociado
     */
    public static final String CONSULTAR_AHORRO_PREVIO_NOMBRE_IDPOSTULACION = "FOVIS.ahorroPrevio.consultarPorNombrePostulacion";

    /**
     * Consulta los ciclos de asignación con el estado especificado
     */
    public static final String CONSULTA_CICLOS_ASIGNACION_POR_ESTADO = "Parametrizacion.CicloAsignacion.consultarCiclosAsignacionPorEstado";

    /**
     * Consulta las postualciones que aplican para envío a control interno.
     */
    public static final String CONSULTA_POSTULACIONES_PARA_CONTROL_INTERNO = "Parametrizacion.PostualcionFOVIS.postulacionesParaControlInterno";

    /**
     * Consulta que se encarga de buscar los integrantes del hogar asociadas a una postulacion FOVIS.
     */
    public static final String CONSULTAR_INTEGRANTES_HOGAR_POR_POSTULACION = "FOVIS.consultar.IntegranteHogar.idPostulacion";

    /**
     * Consultar los postulantes a FOVIS. Se genera un listado con los postulantes para ser mostrado en pantallas.
     */
    public static final String CONSULTAR_POSTULANTES = "FOVIS.Consultar.Postulantes";

    /**
     * Consultar los postulantes a FOVIS <b>con estado igual a Postulado</b>. Se genera un listado con los postulantes para ser mostrado
     * en pantallas.
     */
    public static final String CONSULTAR_POSTULANTES_ESTADO_SOLICITUD_POSTULACION = "FOVIS.Consultar.Postulantes.EstadoSolicitudPostulacion";

    /**
     * Consulta el ciclo sucesor de un ciclo determinado.
     */
    public static final String CONSULTAR_CICLO_SUCESOR = "Parametrizacion.CicloAsignacion.consultarCicloSucesor";

    /**
     * Consulta el una Parametrización Modalidad por Nombre
     */
    public static final String CONSULTAR_PARAMETRIZACION_POR_MODALIDAD = "Parametrizacion.Modalidad.consultarParametrizacionPorModalidad";

    /**
     * Consulta la prarametrizazion por el nombre.
     */
    public static final String CONSULTAR_PARAMETRIZACION_FOVIS_POR_NOMBRE = "Parametrizacion.ParametrizacionFOVIS.consultarPorNombre";

    /**
     * Consulta los ciclos de asignación por el ciclo predecesor.
     */
    public static final String CONSULTAR_CICLO_ASIGNACION_POR_CICLO_PREDECESOR = "Parametrizacion.CicloAsignacion.consultarPorCicloPredecesor";

    /**
     * Constante con el nombre de la consulta que obtiene el histórico de postulaciones FOVIS para el jefe de hogar
     */
    public static final String CONSULTAR_HISTORICO_POSTULACION_JEFE = "Fovis.Consultar.Historico.Postulacion.Jefe";

    /**
     * Constante con el nombre de la consulta que obtiene el histórico de postulaciones FOVIS para los integrantes del hogar. Se consulta
     * por jefe del hogar
     */
    public static final String CONSULTAR_HISTORICO_POSTULACION_INTEGRANTES = "Fovis.Consultar.Historico.Postulacion.Integrantes";

    /**
     * Constante con el nombre de la consulta que obtiene el histórico de postulaciones FOVIS, por persona
     */
    public static final String CONSULTAR_SOLICITUD_USUARIOWEB = "Fovis.ConsultarSolicitudUsuario";

    /**
     * Constante para consultar la ubicación por el Id
     */
    public static final String CONSULTAR_UBICACION_ID = "Fovis.Ubicacion.consultarUbicacionId";

    /**
     * Constante con el nombre de la consulta que obtiene las solicitudes de postulacion por estados y los datos del jefe del hogar.
     */
    public static final String CONSULTAR_SOLICITUD_POSTULACION_POR_ESTADOS_Y_JEFE_HOGAR = "Fovis.SolicitudPostulacion.consultarPorEstadosYDatosPersonaJefeHogar";

    /**
     * Constante para consultar las postulaciones para subsidio dentro de un ciclo de asignacion
     */
    public static final String CONSULTAR_POSTULACIONES_CICLO_ASIGNACION = "Fovis.ConsultarPostulacionesCicloAsignacion";

    /**
     * Constante para consultar las postulaciones para subsidio dentro de un ciclo de asignacion para cruces internos
     */
    public static final String CONSULTAR_POSTULACIONES_CRUCES_INTERNOS = "Fovis.ConsultarPostulacionesCrucesInternos";

    /**
     * Consulta el ciclo de asignación por el id.
     */
    public static final String CONSULTAR_CICLO_ASIGNACION_POR_ID = "Parametrizacion.CicloAsignacion.consultarPorId";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de hogares que aplican para cómputo de calificación de postulación FOVIS
     */
    public static final String CONSULTAR_HOGARES_CALIFICACION_POSTULACION = "Fovis.Consultar.Hogares.Calificacion.Postulacion";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de condiciones especiales de una persona, por identificación
     */
    public static final String CONSULTAR_CONDICION_ESPECIAL_PERSONA = "Fovis.Consultar.CondicionEspecial.Persona";

    /**
     * Consulta los ciclos de asignación que no son predecesores de ningún otro exceptuando los id de ciclos que lleguen
     */
    public static final String CONSULTA_CICLOS_ASIGNACION_NO_PREDECESORES_CON_EXCEPCION = "FOVIS.CicloAsignacion.consultarCiclosNoPredecesoresConExcepcion";

    /**
     * Consulta los ciclos de asignación que no son predecesores de ningún otro exceptuando los id de ciclos que lleguen e incluyendo el
     * predecesor que llegue
     */
    public static final String CONSULTA_CICLOS_ASIGNACION_NO_PREDECESORES_CON_PREDECESOR = "FOVIS.CicloAsignacion.consultarCiclosNoPredecesoresConPredecesor";

    /**
     * Consulta el ciclo sucesor de un ciclo determinado excluyendo los identificadores que lleguen.
     */
    public static final String CONSULTAR_CICLO_SUCESOR_CON_EXCEPCION = "Parametrizacion.CicloAsignacion.consultarCicloSucesorConExcepcion";

    /**
     * Busca el nombre de los campos de un archivo
     */
    public static final String CONSULTAR_NOMBRE_CAMPO_ARCHIVO = "search.nameField.file";

    /**
     * Consulta el registro del cargue de archivo por el identificador
     */
    public static final String CONSULTAR_ARCHIVO_CRUCE_POR_ID = "find.archivo.cruce.id";

    /**
     * Consulta el cruce con su detalle por el identificador cruce
     */
    public static final String CONSULTAR_DETALLE_CRUCE_POR_TIPO_INFORMACION_IDENTIFICACION = "find.cruce.detalle.tipoInformacion.identificacion";

    /**
     * Consulta todos los cruces con su detalle por el identificador de la solicitud
     */
    public static final String CONSULTAR_CRUCE_POR_SOLICITUD_ID = "find.cruce.detalle.id.solicitud";

    /**
     * Consulta todos los cruces con su detalle por el identificador del archivo cargado
     */
    public static final String CONSULTAR_CRUCE_POR_NRO_POSTULACION_ID_CARGUE = "find.cruce.detalle.numeroPostulacion.cargue";

    /**
     * Consulta el numero de postulacion asociado a la persona jefe de hogar
     */
    public static final String CONSULTAR_NRO_POSTULACION_PERSONA_JEFE_HOGAR = "FOVIS.find.numeroPostulacion.persona.jefeHogar";
    /**
     * Consulta el numero de postulacion asociado a la persona integrante hogar
     */
    public static final String CONSULTAR_NRO_POSTULACION_PERSONA_INTEGRANTE_HOGAR = "FOVIS.find.numeroPostulacion.persona.integranteHogar";

    /**
     * Consulta la solicitud de postulacion asociado a el numero de postulacion enviado
     */
    public static final String CONSULTAR_SOLICITUD_POSTULACION_BY_NRO_POSTULACION = "find.solicitud.postulacion.numeroPostulacion";

    /**
     * Consulta la solicitud gestion cruce por el identificador de la misma
     */
    public static final String CONSULTAR_SOLICITUD_GESTION_CRUCE_BY_ID = "find.solicitud.gestion.cruce.id";

    /**
     * Consulta para obtener el total de ahorro previo de un hogar por medio del identificador de postulación
     */
    public static final String CALCULAR_TOTAL_AHORRO_PREVIO_HOGAR = "fovis.calcular.total.ahorro.previo.by.idPostulacion";

    /**
     * Consulta para obtener el total de recurso complementario de un hogar por medio del identificador de postulación
     */
    public static final String CALCULAR_TOTAL_RECURSO_COMPLMENTARIO_HOGAR = "fovis.calcular.total.recurso.complementario.by.idPostulacion";

    /**
     * 
     */
    public static final String CONSULTA_SOLICITUD_POSTULACION_BY_NUMERO_IDENTIFICACION = "FOVIS.find.solicitud.postulacion.by.numeroCedula";

    /**
     * Constante con el nombre de la consulta que obtiene las fechas que se evaluarán durante el puntaje FOVIS relacionado a ahorro
     * programado - HU-048, Parte 5
     */
    public static final String CONSULTAR_FECHAS_AHORRO_PROGRAMADO = "Fovis.Consultar.Fechas.Ahorro.Programado";

    /**
     * Consulta la solicitud gestion cruce por el identificador de la Solicitud de Postulación
     */
    public static final String CONSULTAR_SOL_GESTION_CRUCE_BY_ID_SOL_POSTULACION = "FOVIS.find.solicitudGestionCruce.by.idSolicitudPostulacion";
    
    /**
     * Consulta la solicitud gestion cruce por el identificador de la Solicitud de Postulación
     */
    public static final String CONSULTAR_SOL_GESTION_CRUCE_BY_ID_SOL_POSTULACION_ESTADO_CRUCEHOGAR = "FOVIS.find.solicitudGestionCruce.by.idSolicitudPostulacion.and.estadoCruceHogar";
    
    /**
     * Consulta la solicitud gestion cruce por el identificador de la Solicitud de Postulación
     */
    public static final String CONSULTAR_SOL_GESTION_CRUCE_BY_ID_SOL_POSTULACION_TIPO_CRUCE = "FOVIS.find.solicitudGestionCruce.by.idSolicitudPostulacion.tipoCruce";

    /**
     * Consulta la solicitud gestion cruce por el identificador de la Solicitud de Postulación
     */
    public static final String CONSULTAR_SOL_GESTION_CRUCE_BY_TIPO_CRUCE_ESTADO = "FOVIS.find.solicitudGestionCruce.by.tipoCruce.estadoCruce";

    /**
     * Consulta el cruce por el identificador de la Solicitud de Gestión Cruce
     */
    public static final String CONSULTAR_CRUCE_BY_SOLICITUD_GESTION_CRUCE = "FOVIS.find.Cruce.by.idSolicitudGestionCruce";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de los integrantes de un hogar
     */
    public static final String CONSULTAR_MIEMBROS_HOGAR = "Fovis.Consultar.Miembros.Hogar";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de postulaciones en estadom hábil
     */
    public static final String CONSULTAR_POSTULACIONES_ASIGNACION = "fovis.consultar.postulaciones.asignacion.porEstadoHogarSolicitudGlobal";

    /**
     * Constante con el nombre de la consulta que obtiene una solicitud de asignacion, por el identificador de la solicitud
     */
    public static final String CONSULTAR_SOLICITUD_ASIGNACION = "fovis.consultar.solicitudAsignacion";

    /**
     * Constante con el nombre de la consulta que obtiene una solicitud de asignacion, por el identificador de la solicitud
     */
    public static final String CONSULTAR_IDPERSONA_TIPO_NUMERO = "FOVIS.ConsultarPersonaTipoNumeroIdentificacion";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de asignaciones previas de un hogar, por jefe de hogar y resultado de la
     * asignación
     */
    public static final String CONSULTAR_ASIGNACIONES_PREVIAS_HOGAR = "Fovis.Consultar.Asignaciones.Previas.Hogar";

    /**
     * Constante con el nombre de la consulta que obtiene la parametrización por identificador de una modalidad FOVIS
     */
    public static final String CONSULTAR_PARAMETRIZACION_MODALIDAD = "Fovis.Consultar.ParametrizacionModalidad.Nombre";

    /**
     * Constante con el nombre de la consulta que obtiene un registro de ahorro previo por id de postulación y tipo de ahorro
     */
    public static final String CONSULTAR_AHORRO_PREVIO = "Fovis.Consultar.AhorroPrevio";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de postulaciones por solicitud de asignacion y resultado de asignacion
     */
    public static final String CONSULTAR_ASIGNACIONES_POR_SOLICITUD_y_RESULTADO_ASIGNACION = "fovis.consultar.postulaciones.asignacion.porSolicitudYResultadoAsignacion";

    /**
     * Constante con el nombre de la consulta que obtiene las cartas de asignacion por ano y ciclo de asignacion seleccionado. Se genera un
     * listado con la informacion para ser mostrado en pantallas
     */
    public static final String CONSULTAR_CARTAS_ASIGNACION_FOVIS_POR_CICLO_ANO_ASIGNACION = "fovis.Consultar.CartasAsignacionPorAnoCiclo";

    /**
     * Constante con el nombre de la consulta que obtiene la información de una solicitud global de postulación, por identificador de la
     * postulación
     */
    public static final String CONSULTAR_SOLICITUD_GLOBAL_POSTULACION = "Fovis.Consultar.SolicitudGlobal.Postulacion";

    /**
     * Constante con el nombre de la consulta que obtiene una lista de años asociada al ciclo de asignacion que esta en estado cerrado
     */
    public static final String CONSULTAR_ANOS_CICLO_ASIGNACION_ESTADO_CERRADO = "fovis.ConsultarAnosCicloAsignacionEstadoCerrado";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de los cruces generados por Ejecucion de proceso Asincrono
     */
    public static final String CONSULTAR_CRUCES_POR_EJECUCION = "Fovis.Consultar.Cruces.Ejecucion";

    /**
     * Consulta los ciclos de asignación en estado cerrado para el año especificado
     */
    public static final String CONSULTA_CICLOS_ASIGNACION_CERRADOS_POR_ANIO = "fovis.consultarCiclosAsignacionCerradosPorAnio";

    /**
     * Consulta la informacion de la hoja afiliado por id archivo cruce
     */
    public static final String CONSULTA_INFO_HOJA_AFILIADO = "find.hoja.afiliado.archivo.cruce.id";
    /**
     * Consulta la informacion de la hoja beneficiario por id archivo cruce
     */
    public static final String CONSULTA_INFO_HOJA_BENEFICIARIO = "find.hoja.beneficiario.archivo.cruce.id";
    /**
     * Consulta la informacion de la hoja beneficiario arriendo por id archivo cruce
     */
    public static final String CONSULTA_INFO_HOJA_BENEFICIARIO_ARRIENDO = "find.hoja.beneficiarioarriendo.archivo.cruce.id";
    /**
     * Consulta la informacion de la hoja catastros por id archivo cruce
     */
    public static final String CONSULTA_INFO_HOJA_CATASTROS = "find.hoja.catastros.archivo.cruce.id";
    /**
     * Consulta la informacion de la hoja cat ant por id archivo cruce
     */
    public static final String CONSULTA_INFO_HOJA_CAT_ANT = "find.hoja.catant.archivo.cruce.id";
    /**
     * Consulta la informacion de la hoja cat bog por id archivo cruce
     */
    public static final String CONSULTA_INFO_HOJA_CAT_BOG = "find.hoja.catbog.archivo.cruce.id";
    /**
     * Consulta la informacion de la hoja cat cali por id archivo cruce
     */
    public static final String CONSULTA_INFO_HOJA_CAT_CALI = "find.hoja.catcali.archivo.cruce.id";
    /**
     * Consulta la informacion de la hoja cat med por id archivo cruce
     */
    public static final String CONSULTA_INFO_HOJA_CAT_MED = "find.hoja.catmed.archivo.cruce.id";
    /**
     * Consulta la informacion de la hoja cedula por id archivo cruce
     */
    public static final String CONSULTA_INFO_HOJA_CEDULA = "find.hoja.cedula.archivo.cruce.id";
    /**
     * Consulta la informacion de la hoja fechas corte por id archivo cruce
     */
    public static final String CONSULTA_INFO_HOJA_FECHAS_CORTE = "find.hoja.fechascorte.archivo.cruce.id";
    /**
     * Consulta la informacion de la hoja igac por id archivo cruce
     */
    public static final String CONSULTA_INFO_HOJA_IGAC = "find.hoja.igac.archivo.cruce.id";
    /**
     * Consulta la informacion de la hoja nuevo hogar por id archivo cruce
     */
    public static final String CONSULTA_INFO_HOJA_NUEVO_HOGAR = "find.hoja.nuevohogar.archivo.cruce.id";
    /**
     * Consulta la informacion de la hoja reunidos por id archivo cruce
     */
    public static final String CONSULTA_INFO_HOJA_REUNIDOS = "find.hoja.reunidos.archivo.cruce.id";
    /**
     * Consulta la informacion de la hoja sisben por id archivo cruce
     */
    public static final String CONSULTA_INFO_HOJA_SISBEN = "find.hoja.sisben.archivo.cruce.id";
    /**
     * Consulta la informacion de la hoja unidos por id archivo cruce
     */
    public static final String CONSULTA_INFO_HOJA_UNIDOS = "find.hoja.unidos.archivo.cruce.id";

    /**
     * Consulta la solicitud de postulacion por el id de la misma
     */
    public static final String CONSULTA_SOLICITUD_POSTULACION_BY_ID = "FOVIS.consultar.solicitudPostulacion.idSolicitudPostulacion";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de asignaciones previas de un hogar, por jefe de hogar o integrante y la
     * modalidad
     */
    public static final String CONSULTAR_ASIGNACIONES_PREVIAS_PERSONA_MODALIDAD = "Fovis.Consultar.Asignaciones.Previas.Hogar.Persona.Modalidad";

    /**
     * Consulta los datos para la vista generar documento soporte acta de asignación
     */
    public static final String CONSULTAR_DATOS_GENERAR_SOPORTE_ACTA_ASGINACION = "fovis.ConsultarDatosGenerarSoporteActaAsignacion";

    /**
     * Consulta los responsables del ultimo acta de asignación
     */
    public static final String CONSULTAR_RESPONSABLES_ULTIMA_ACTA_ASGINACION = "fovis.ConsultarResponsablesUltimaActaAsignacion";

    /**
     * Consulta el acta de asignación por la solicitud global
     */
    public static final String CONSULTAR_ACTA_ASIGNACION_SOLICITUD_GLOBAL = "fovis.consultar.actaAsignacion.por.solicitudGlobal";

    /**
     * Consulta los responsables del ultimo acta de asignación
     */
    public static final String CONSULTAR_SOLICITUD_ASIGNACION_POR_ID_SOLICITUD_GLOBAL = "fovis.consultar.solicitudAsignacion.PorSolicitudGlobal";

    /**
     * Consulta que se encarga de buscar los integrantes del hogar por estado excluyendo los id relacionados.
     */
    public static final String CONSULTAR_INTEGRANTES_HOGAR_NO_RELACIONADOS_ACTIVOS = "FOVIS.consultar.IntegranteHogar.noRelacionadosActivos";

    /**
     * Consulta que se encarga de buscar los integrantes del hogar por el jefe del hogar y el estado.
     */
    public static final String CONSULTAR_INTEGRANTES_HOGAR_JEFE_HOGAR_ESTADO = "FOVIS.consultar.IntegranteHogar..por.JefeHogar.estado";

    /**
     * Consulta que se encarga de buscar un documento de estado de solicitud relacionado con una solictud global
     */
    public static final String CONSULTAR_DOCUMENTO_ESTADO_SOLICITUD = "FOVIS.consultar.DocumentoEstadoSolicitud";

    /**
     * Constante para consultar la Licencia por el identificador de los proyectos de vivienda
     */
    public static final String CONSULTAR_LICENCIA_IDS_PROYECTO_VIVIENDA = "Fovis.licencia.consultarLicenciaIdProyectoVivienda";

    /**
     * Constante para consultar el detalle de la licencia por el identificador de las licencias
     */
    public static final String CONSULTAR_LICENCIA_DETALLE_POR_ID_LICENCIAS = "Fovis.licenciaDetalle.consultarLicenciaDetallePorIdsLicencia";

    /**
     * Constante que consulta los medios de pago asociados a un proyecto.
     */
    public static final String CONSULTAR_MEDIODEPAGO_PROYECTO = "Fovis.MedioDePago.consultarMedioPagoProyecto";

    /**
     * Constante que constula el medio de pago activo de un proyecto de vivienda.
     */
    public static final String CONSULTAR_MEDIODEPAGO_ACTIVO_PROYECTO = "Fovis.MedioDePago.consultarMedioPagoActivoProyecto";

    /**
     * Constante con el nombre de la consulta que obtiene el acta de asignacion por el identificador de la solicitud de asignacion
     */
    public static final String CONSULTAR_ACTA_ASGINACION_POR_ID_SOLICITUD_ASIGNACION = "fovis.consultar.ActaAsignacionPorIdSolicitudAsignacion";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de documentos soporte de un determinado proyesto de vivienda
     */
    public static final String CONSULTAR_DOCUMENTOS_SOPORTE_POR_PROYECTO = "Fovis.Consultar.DocumentoSoporte.Por.ProyectoSolucionVivienda";

    /**
ProyectoSolucionVivienda     * Busca el salario acumulativo de un afiliado en sus roles y por estado
     */
    public static final String BUSCAR_SALARIO_AFILIADO_ESTADO = "Personas.buscar.salario.afiliado.estado";

    /**
     * busca el beneficiarioDetalle asociado a la persona integrante del hogar
     */
    public static final String BUSCAR_BENEFICIARIO_DETALLE_POR_DATOS_INTEGRANTE_HOGAR_ESTADO = "Beneficiario.buscar.detalle.por.tipo.nroIdentificacion.estado.y.jefehogar";

    /**
     * Consulta el integrante de hogar por tipo y numero de identificacion y la postulación
     */
    public static final String CONSULTAR_INTEGRANTE_HOGAR_POR_TIPO_NUMERO_ID_POSTULACION = "FOVIS.consultar.integranteHogar.por.tipo.numeroId.postulacionId";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de hogares que aplican para cómputo de calificación de postulación FOVIS
     * y que tengan reclamaciones procedentes
     */
    public static final String CONSULTAR_HOGARES_CALIFICACION_POSTULACION_CON_RECLAMACION_PROCEDENTE = "Fovis.Consultar.Hogares.Calificacion.Postulacion.NovedadReclamanteProcedente";
    
    /**
	 * Consulta si una persona esta asociada a una postulación y si perdio el subsidio por imposibilidad de pago 
	 */
	public static final String VALIDAR_POSTULACION_PERDIO_VIVIENDA_IMPOSIBILIDAD_PAGO = "Fovis.jefeHogar.HogarPerdioSubsidioNoPago";

    /**
     * Constante que representa la consulta de solicitudes de gestion de cruce por la lista de sol postulacion y el tipo cruce
     */
    public static final String CONSULTAR_SOLICITUD_GESTION_CRUCE_BY_LIST_ID_SOL_POST_TIPO_CRUCE = "FOVIS.find.solicitudGestionCruce.by.listIdSolicitudPostulacion.tipoCruce";

    /**
     * Constante que representa la consulta de solicitudes de postulacion por la lista de numeros de postulacion
     */
    public static final String CONSULTAR_SOLICITUD_POSTULACION_BY_LIST_NUMERO_POSTULACION = "find.solicitud.postulacion.by.listNumeroPostulacion";

    /**
     * Consulta que se encarga de buscar una solicitud de postulacion por el id de la solicitud global
     */
    public static final String CONSULTAR_SOLICITUD_VERIFICACION_POR_ID_SOLICITUD_GLOBAL = "FOVIS.consultar.solicitudVerificacionFovis.idSolicitudGlobal";
    
    /**
     * Consulta todos los cruces con su detalle por el identificador de la solicitud
     */
    public static final String CONSULTAR_ESTADO_CRUCE_HOGAR = "Fovis.Consultar.Estado.Cruces.Hogar";    
    
    /**
     * Consulta todos los cruces con su detalle por el identificador de la solicitud de gestion
     */
    public static final String CONSULTAR_CRUCE_POR_SOLICITUD_GESTION_ID = "find.cruce.detalle.id.solicitudGestion";
    
	/**
	 * Constante con el nombre de la consulta historico de las solicitudes de
	 * postulacion
	 */
	public static final String CONSULTAR_HISTORICO_SOLICITUD_POSTULACION = "fovis.Consultar.HistoricoSolicitudPostulacion";

	/**
	 * Constante con el nombre de la consulta el historico de las solicitudes de
	 * postulacion por rango de fechas
	 */
	public static final String CONSULTAR_HISTORICO_SOLICITUD_POSTULACION_RANGO_FECHAS = "fovis.Consultar.HistoricoSolicitudPostulacionRangoFechas";
	
	/**
	 * Constante con el nombre de la consulta el historico de la asignacion
	 * FOVIS
	 */
	public static final String CONSULTAR_HISTORICO_ASIGNACION_FOVIS = "fovis.consultar.HistoricoAsignacionFOVIS";
	
	/**
     * Constante con el nombre de la consulta que obtiene la lista de hogares que tienen cruces internos en proceso
     */
    public static final String CONSULTAR_HOGARES_CRUCE_INTERNO_EN_PROCESO = "Fovis.Consultar.Hogares.CruceInternoEnProceso";
    
    /**
     * Consulta el cruce con su detalle por la cedula
     */
    public static final String CONSULTAR_DETALLE_CRUCE_POR_TODOS_TIPOS_INFORMACION_IDENTIFICACION = "find.cruce.detalle.todosTiposInformacion.tipoIdentificacion";

    /**
     * Constante con el nombre de la consulta que obtiene el avance del proceso de calificacion
     */
    public static final String CONSULTAR_CANTIDAD_AVANCE_CALIFICACION = "fovis.consultar.cantidadAvanceCalificacion";

    /**
     * Consulta la informacion de la solicitud de postulacion por el numero de radicado
     * Entrega la informacion de Postulacion, JefeHogar y Solicitud
     */
    public static final String CONSULTAR_INFO_POSTULACION_BY_NUMERO_RADICACION = "find.info.postulacion.by.numeroRadicado";

    /**
     * Consulta la informacion de la solicitud de postulacion por el numero de radicado
     * Entrega la informacion de Postulacion, JefeHogar, Solicitud y cruces
     */
    public static final String CONSULTAR_INFO_POSTULACION_BY_NUMERO_RADICACION_CRUCE = "find.info.postulacion.by.numeroRadicado.cruce";


    /**
     * Constante para la consulta de la solicitud de gestion cruce  por el identificador de solicitud global
     */
    public static final String CONSULTAR_SOLICITUD_GESTION_CRUCE_POR_ID_SOLICITUD_GLOBAL = "FOVIS.consultar.solicitudGestionCruceFovis.idSolicitudGlobal";

    /**
     * Constante para la consulta de la solicitud de postulación por medio del identificador de la solicitud global de la solicitud de
     * gestion de cruce
     */
    public static final String CONSULTAR_SOLICITUD_POSTULACION_BY_ID_SOL_GLOBAL_SOL_GESTION_CRUCE_O_VERIFICA = "FOVIS.consultar.solicitudPostulacion.by.idSolicitudGlobal.solicutGestionCruceFovis.solicutVerificacionFovis";

    /**
     * Consulta la información básica de la postulación fovis por Número radicado, Tipo identificación y/o Número identificación
     * Filtros: numeroRadicado, tipoIdentificacion, numeroIdentificacion
     */
    public static final String CONSULTAR_INFO_BASICA_POSTULACION_FOVIS = "fovis.consultar.postulacion.info.basica";

    /**
     * Consulta la información básica del proyecto solución de vivienda asociado a la postulación por el identificador del proyecto
     * Filtro: idProyecto
     */
    public static final String CONSULTAR_INFO_BASICA_PROYECTO_POSTULACION_FOVIS = "fovis.consultar.postulacion.info.proyecto";

    /**
     * Consulta la información de los ahorros asociados a la postulación
     * Filtro: idPostulacion
     */
    public static final String CONSULTAR_INFO_BASICA_AHORROS_POSTULACION_FOVIS = "fovis.consultar.postulacion.info.ahorro";

    /**
     * Consulta la información de los recusros complementarios asociados a la postulación
     * Filtro: idPostulacion
     */
    public static final String CONSULTAR_INFO_BASICA_RECURSOS_COMPLEMENTARIOS_POSTULACION_FOVIS = "fovis.consultar.postulacion.info.recursoComplementario";

    /**
     * Constante que representa la consulta de ciclos de asignacion con predecesor que no han sido cerrados
     */
    public static final String CONSULTA_CICLOS_ASIGNACION_CON_PREDECESOR = "consultar.ciclos.asignacion.con.predecesor";

    /**
     * Constante que representa la consulta de postulaciones asociadas a un ciclo de asignación cerrado y el resultado de la asignaciones
     * calificado no asignado
     */
    public static final String CONSULTA_POSTULACIONES_BY_CICLO_Y_RESULTADO_ASIGNACION = "consultar.postulaciones.by.ciclo.and.resultado.asignacion";
    
    /**
     * Constante que representa la consulta de postulaciones asociadas a un ciclo de asignación cerrado y las novedades
     * "Habilitación hogar suspendido por cambio de año" o "Habilitación postulación rechazada"
     */
    public static final String CONSULTA_POSTULACIONES_BY_CICLO_Y_TIPO_TRANSACCION = "consultar.postulaciones.by.ciclo.and.novedad.tipo.transaccion";

    /**
     * Constante qeu representa la consulta de informacion de solicitud postulacion por el identificador de la postulacion
     */
    public static final String CONSULTA_INFORMACION_POSTULACION_BY_ID = "fovis.consultar.info.by.idPostulacion";

    /**
     * Constante que representa la consulta de oferente asociado a la postulación por el identificador de la postulacion
     */
    public static final String CONSULTA_INFORMACION_OFERENTE_BY_ID_POSTULACION = "fovis.consultar.info.oferente.by.idPostulacion";

    /**
     * Constante que representa la consulta de proyecto asociada a la postulacion por el identificador de la postulacion
     */
    public static final String CONSULTA_INFORMACION_PROYECTO_BY_ID_POSTULACION = "fovis.consultar.info.proyecto.by.idPostulacion";

    /**
     * Constante que representa la consulta de ahorros previos asociados a la postulacion por el identificador de la postulacion
     */
    public static final String CONSULTA_INFORMACION_AHORRO_PREVIO_BY_ID_POSTULACION = "fovis.consultar.ahorros.previos.by.idPostulacion";

    /**
     * Constante que representa la consulta de recursos complementarios asociados a la postulacion por el identificador de la postulacion
     */
    public static final String CONSULTA_INFORMACION_RECURSOS_COMPLEMENT_BY_ID_POSTULACION = "fovis.consultar.recursos.complementarios.by.idPostulacion";

    /**
     * Constante que representa la consulta de postulaciones relacionadas a la asignacion por el identificador de la solicitud de asignacion
     */
    public static final String CONSULTA_POSTULACIONES_RELACIONADAS_ASIGNACION_BY_ID_SOL_ASIGNACION = "fovis.consultar.postulaciones.asignacion.by.id.solicitudAsignacion";

    /**
     * Constante que representa la consulta de cantidad de procesos de asignacion de una postulacion por el identificador de la postulacion
     */
    public static final String CONSULTA_CANTIDAD_PROCESOS_ASIGNACION_BY_ID_POST = "fovis.consultar.cantidad.procesos.asignacion.by.id.postulacion";

    /**
     * Consatante que representa la consulta de los miembros de hogar asociados por el identificador de la postulación La postulación incluye el jefe de hogar
     */
    public static final String CONSULTA_MIEMBROS_HOGAR_BY_ID_POSTULACION = "fovis.consultar.info.miembros.hogar.by.idPostulacion";

    /**
     * Constante que representa la consulta del jefe de hogar por su identificador
     */
    public static final String CONSULTA_JEFE_HOGAR_BY_ID = "consultar.jefeHogar.by.id";

    /**
     * Constante que representa la consulta de integrantes hogar por la lista de identificadores enviada
     */
    public static final String CONSULTA_LISTA_INTEGRANTE_HOGAR_BY_LIST_ID ="consultar.lista.integrantesHogar.by.id";
    
    /**
     * Constante que representa la consulta de inhabilidades de las personas de una postulación
     */
    public static final String CONSULTA_INHABILIDADES_PERSONA_POSTULACION_BY_ID = "fovis.consultar.inhabilidad.persona.postulacion";

    /**
     * Constante que representa la consulta de lista de chequeo por solicitud persona y clasificación
     */
    public static final String CONSULTA_LISTA_CHEQUEO_BY_SOLICITUD_PERSONA_CLASIFICACION = "fovis.consultar.listaChequeo.by.solicitud.persona.clasificacion";
    
    /**
     * Constante que representa la consulta de proveedor asociado a la postulación por el identificador de la postulacion
     */
    /// 49270BORRADOKT comentado ya que el glpi 49270 contempla la tabla LegalizacionDesembolosoProveedor pero esta nunca se inserta y no se utiliza
   // public static final String CONSULTA_INFORMACION_PROVEEDOR_BY_ID_POSTULACION = "fovis.consultar.info.proveedor.by.idPostulacion";
    
    /**
     * Constante con el nombre de la consulta que obtiene en valor de SMMLV por anio
     */
    public static final String CONSULTAR_SMMLV_ANIO = "fovis.consultar.SMMLV.anio";
    
    /**
     * Constante con el nombre de la consulta que obtiene los recursos prioridad esten habilitadas
     */
    public static final String CONSULTAR_RECURSOS_PRIORIDAD = "fovis.consultar.recursos.prioridad";
    
    /**
     * Constante con el nombre de la consulta que obtiene el recurso prioridad con el cual fue asignado el hogar
     */
    public static final String CONSULTAR_RECURSOS_PRIORIDAD_POSTULACION_ASIGNACION = "fovis.consultar.recursos.prioridad.postulacion";

    /**
     * Consulta la informacion del arhivo de cruces
     */
    public static final String STORED_PROCEDURE_FOVIS_CONSULTAR_INFORMACION_CRUCES = "stored.procedure.fovis.consultar.informacionArhivoCruces";

    /**
     * Constante con el nombre de la consulta que obtiene el recurso prioridad con el cual fue asignado el hogar
     */
    public static final String CONSULTAR_TAREAS_HEREDADAS= "fovis.consultar.tareas.heredadas.solicitud";

    /**
     * Constante con el nombre de la consulta que obtiene el recurso prioridad con el cual fue asignado el hogar
     */
    public static final String CONSULTAR_REDIRECCIONAMIENTO_TAREA= "fovis.consultar.redireccionamiento.tarea";

    /**
     * Constante con el nombre de la consulta que obtiene el recurso prioridad con el cual fue asignado el hogar
     */
    public static final String CONSULTAR_TAREAS_HEREDADAS_CRUCE= "fovis.consultar.tareas.heredadas.solicitud.cruce";

    /**
     * Constante con el nombre de la consulta que obtiene el recurso prioridad con el cual fue asignado el hogar
     */
    public static final String CONSULTAR_SOLCITUD= "fovis.consultar.solicitud";

    /**
     * Consulta las postualciones que aplican para envío a control interno.
     */
    public static final String CONSULTA_POSTULACIONES_POR_CICLO = "fovis.consultar.postulaciones.por.ciclo";

    /**
     * Consulta los ciclos de asignación con el estado especificado
     */
    public static final String CONSULTA_CICLOS_FECHA_ASIGNACION = "Parametrizacion.consultar.cicloAsignacion";

    /**
     * Consulta los ciclos de asignación con el estado especificado
     */
    public static final String CONSULTA_CICLOS_FECHA_ASIGNACION_EJECUCION = "Parametrizacion.consultar.cicloAsignacion.ejecucion";

    /**
     * Consulta las postulaciones que tuvieron novedad por rango de fecha
     */
    public static final String CONSULTA_NOVEDAD_RANGO = "fovis.consultar.novedades.rango.fecha";

    /**
     * Consulta las postulaciones que tuvieron novedad por rango de fecha
     */
    public static final String CONSULTAR_FECHA_RESULTADO_EJECUCION = "fovis.consultar.resultado.ejecucion";

    /**
     * Consulta las postulaciones que tuvieron novedad por rango de fecha
     */
    public static final String CONSULTAR_CALIFICACIONES_POSTULACION = "consultar.calificaciones.postulacion";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de hogares que aplican para cómputo de calificación de postulación FOVIS y nunca han sido calificados
     */
    public static final String CONSULTAR_HOGARES_CALIFICACION_POSTULACION_EJECUCION = "Fovis.Consultar.Hogares.Calificacion.Postulacion.ejecucion";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de hogares que aplican para cómputo de calificación de postulación FOVIS y nunca han sido calificados
     */
    public static final String CONSULTAR_HOGARES_CALIFICACION_POSTULACION_RANGO = "Fovis.Consultar.Hogares.Calificacion.Postulacion.Rango";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de hogares que aplican para cómputo de calificación de postulación FOVIS y nunca han sido calificados
     */
    public static final String CONSULTAR_CICLO_ASIGNACION_48HORAS = "Consultar.ciclo.calificado.48horas";

        /**
     * Consulta si la modalidad del hogar postulado existe en algun ciclo vigente
     */
    public static final String CONSULTA_MODALIDAD_EN_CICLO_VIGENTE = "Parametrizacion.consultar.cicloAsignacion.modalidad"; 

    public static final String CONSULTA_POSTULACIONES = "Parametrizacion.PostualcionFOVIS.postulacionesParaControlInterno2";

    /**
     * Consulta la informacion del arhivo de cruces
     */
    public static final String STORED_PROCEDURE_FOVIS_CONSULTAR_INGRESOS_HOGAR = "stored.procedure.fovis.consultar.ingresosHogar";

    public static final String STORED_PROCEDURE_FOVIS_CONSULTAR_NOVEDADES_PENDIENTES = "stored.procedure.fovis.consultar.novedades.pendientes";
}


