package com.asopagos.validaciones.fovis.constants;

/**
 * <b>Descripción:</b> Clase que define las constantes usadas en los servicios
 * de validaciones negocio <b>Módulo:</b> Asopagos - HU-TRA<br/>
 * 
 *
 * @author Jorge Leonardo Camargo Cuervo
 *         <a href="mailto:jcamargo@heinsohn.com.co"> jcamargo@heinsohn.com.co
 *         </a>
 */
public class ConstantesValidaciones {

    /**
     * Nombre del bundle de mensajes
     */
    public static final String NOMBRE_BUNDLE_MENSAJES = "mensajesValidaciones";
    /**
     * Nombre del NamedQuery de validaciones de empleadores
     */
    public static final String NAMED_QUERY_VALID_EMPL = "Validaciones.empleadores.validar";
    /**
     * Nombre del parámetro de NamedQuery de proceso
     */
    public static final String NAMED_QUERY_PARAM_PROCESO = "proceso";
    /**
     * Nombre del parámetro Resultado proceso
     */
    public static final String RESULTADO_PROCESO_PARAM = "resultadoProceso";
    /**
     * Nombre del parámetro de NamedQuery de bloque
     */
    public static final String NAMED_QUERY_PARAM_BLOQUE = "bloque";
    /**
     * Nombre del NamedQuery de validaciones de personas
     */
    public static final String NAMED_QUERY_VALID_PERS = "Validaciones.personas.validar";
    /**
     * Nombre del parámetro de NamedQuery del objeto a validar
     */
    public static final String NAMED_QUERY_PARAM_OBJ_VAL = "objetoValidacion";
    /**
     * Nombre del parámetro de NamedQuery del objeto a validar
     */
    public static final String NAMED_QUERY_PARAM_OBJ_VAL_PADRE = "objetoValidacionPadre";
    /**
     * Consulta general JPQK
     */
    public static final String QUERY_VALIDACIONES = "select DISTINCT(obj) from ";
    /**
     * Nombre de la variable a usar en la consulta general
     */
    public static final String OBJ_QUERY_VALID = " obj ";
    /**
     * Cláusula WHERE
     */
    public static final String WHERE_CLAUSE = " WHERE ";
    /**
     * Cláusula AND
     */
    public static final String AND_CLAUSE = " AND ";
    /**
     * Referencia del objeto en la consulta
     */
    public static final String OBJ_REF_QUERY = " obj.";
    /**
     * Cláusula like
     */
    public static final String LIKE_CLAUSE = " like ";
    /**
     * Símbolo igual
     */
    public static final String EQUAL_SYMBOL = " = ";
    /**
     * Cláusula in
     */
    public static final String IN_CLAUSE = " in";
    /**
     * Caracter dos puntos
     */
    public static final String COLON_SYMBOL = ":";
    /**
     * Caracter parentesis abrir.
     */
    public static final String OPEN_PAR_SYMBOL = "(";
    /**
     * Caracter parentesis cerrar.
     */
    public static final String CLOSE_PAR_SYMBOL = ")";
    /**
     * Caracter espacio
     */
    public static final String SPACE = " ";
    /**
     * Claúsula order by
     */
    public static final String ORDER_BY_CLAUSE = " order by obj.";
    /**
     * Campo de ordenamiento
     */
    public static final String ORDER_FIELD = "orden";
    /**
     * Nombre del named query para buscar solicutdes de afiliación por empleador
     */
    public static final String NAMED_QUERY_SOLIC_EMPL = "SolicitudAfiliacionEmpleador.buscarPorPersona";
    /**
     * Nombre del parámetro de tipo de identificación
     */
    public static final String TIPO_ID_PARAM = "tipoIdentificacion";
    /**
     * Nombre del parámetro de tipo de indentificación del beneficiario
     */
    public static final String TIPO_ID_BENEF_PARAM = "tipoIdentificacionBeneficiario";
    /**
     * Nombre del parámetro de tipo de beneficiario con respecto al jefe de hogar
     */
    public static final String PARENTESCO_BENEF_PARAM = "tipoBeneficiario";
    /**
     * Nombre del parámetro de tipo de indentificación al que se desea cambiar
     * @see com.asopagos.validaciones.validadores.novedades.personas.ValidadorCambioTipoDocIdentidad
     */
    public static final String TIPO_ID_CAMBIO_PARAM = "tipoIdentificacionTrabajador";

    /**
     * Nombre del parámetro de numero de identificación al que se desea cambiar
     */
    public static final String NUM_ID_CAMBIO_PARAM = "numeroIdentificacionTrabajador";

    /**
     * Nombre parametro de numero de identificacion beneficiario anterior
     */
    public static final String NUM_ID_BENEF_ANT_PARAM = "numeroIdentificacionBeneficiarioAnterior";

    /**
     * Nombre parametro de tipo identificacion beneficiario anterior
     */
    public static final String TIPO_ID_BENEF_ANT_PARAM = "tipoIdentificacionBeneficiarioAnterior";

    /**
     * Nombre del parametro tipo de novedad
     */
    public static final String TIPO_NOVEDAD_PARAM = "tipoNovedad";

    /**
     * Nombre del parametro estado de la solicitud de novedad
     */
    public static final String ESTADO_SOLICITUD_NOVEDAD = "estadoSolicitudNovedad";

    /**
     * Nombre del parámetro estado entidad pagadora
     */
    public static final String ESTADO_EN_ENTIDAD_PAGADORA = "estadoEnEntidadPagadora";

    /**
     * Nombre del parámetro de razón social
     */
    public static final String RAZON_SOCIAL = "razonSocial";
    
    /**
     * Nombre del parametro fechaRetiro
     */
    public static final String FECHA_RETIRO_PARAM = "fechaRetiro";
    
    /**
     * Nombre del parametro tarjeta
     */
    public static final String TARJETA = "Tarjeta multiservicio";

    /**
     * 
     */
    public static final String TRANSFERENCIA = "Transferencia bancaria";

    /**
     * Nombre del parámetro de número de identificación
     */
    public static final String NUM_ID_PARAM = "numeroIdentificacion";

    /**
     * Nombre del parámetro de número de indentificación del beneficiario
     */
    public static final String NUM_ID_BENEF_PARAM = "numeroIdentificacionBeneficiario";

    /**
     * Nombre del parámetro de número de identificación del empleador destino
     */
    public static final String TIPO_ID_EMPL_DEST_PARAM = "tipoIdentificacionEmpleadorDestino";
    /**
     * Nombre de la novedad sobre la cual se está ejecutando la validación
     */
    public static final String TIPO_TRANSACCION = "tipoTransaccion";
    /**
     * Nombre de la clave para el año del inicio del beneficio.
     */
    public static final String ANIO_INICIO_BENEFICIO_PARAM = "anioInicioBeneficio";
    /**
     * Nombre del parámetro de número de identificación del empleador destino
     */
    public static final String NUM_ID_EMPL_DEST_PARAM = "numeroIdentificacionEmpleadorDestino";
    /**
     * Nombre del parámetro de número de identificación del empleador destino
     */
    public static final String ID_EMPL_DEST_PARAM = "idEmpleadorDestino";
    /**
     * Nombre del parámetro de tipo de identificación representante legal suplente
     */
    public static final String TIPO_ID_PARAM_RLS = "tipoIdentificacionRLS";
    /**
     * Código sucursal
     */
    public static final String CODIGO_SUCURSAL_PARAM = "codigoSucursal";
    /**
     * Nombre sucursal
     */
    public static final String NOMBRE_SUCURSAL_PARAM = "nombreSucursal";
    /**
     * Id sucursal
     */
    public static final String ID_SUCURSAL_PARAM = "idSucursal";
    /**
     * Nombre del parámetro de número de identificación
     */
    public static final String NUM_ID_PARAM_RLS = "numeroIdentificacionRLS";
    /**
     * Nombre del parámetro de estado
     */
    public static final String PARAM_ESTADO = "estadosSolicitud";
    /**
     * Nombre del parámetro misma información de ubicación del afiliado principal (boolean)
     */
    public static final String MISMA_INFO_UBICACION_AFILIADO_PRINCIPAL = "mismaDireccionAfiliadoPrincipalGrupoFam";
    /**
     * 
     */
    public static final String ESTADO_SERVICIOS_SIN_AFILIACION = "serviciosSinAfiliacionTrabajador";
    /**
     * IDs de persanas a validar
     */
    public static final String IDS_PERSONAS = "idsPersonas";
    /**
     * Nombre del campo de beneficio cubierto 590
     */
    public static final String BENEFICIO_CUBIERTO_590 = "beneficioCubierto590";
    /**
     * Nombre del campo de beneficio cubierto 1429
     */
    public static final String BENEFICIO_CUBIERTO_1429 = "beneficioCubierto1429";
    /**
     * Nombre del parámetro de identificador de Postulacion FOVIS
     */
    public static final String ID_POSTULACION = "idPostulacion";
    /**
     * Nombre del parámetro de identificador de Solicitud de Postulacion FOVIS
     */
    public static final String ID_SOLICITUDPOSTULACION = "idSolicitudPostulacion";
    /**
     * Nombre del parámetro de identificador de que persona perdió la vivienda por imposibilidad de pago
     */
    public static final String HOGAR_PERDIO_SUBSIDIO_NO_PAGO = "hogarPerdioSubsidioNoPago";
    /**
     * Nombre del parámetro de ESTADO HOGAR
     */
    public static final String ESTADO_HOGAR_PARAM = "estadoHogar";
    /**
     * Nombre del parámetro de ESTADO HOGAR POSTULACION
     */
    public static final String ESTADO_HOGAR_POS_PARAM = "estadoHogarPostulacion";

    /**
     * Nombre del parametro estado de la solicitud de Postulacion
     */
    public static final String ESTADO_SOLICITUD_POSTULACION = "estadoSolicitudPostulacion";
    /**
     * Nombre del campo requiere inactivación web
     */
    public static final String REQUIERE_INACTIVIACION_WEB = "requiereInactivacionWeb";

    /**
     * Nombre de la lista que posee los estados de hogar validos para determinadas validaciones
     */
    public static final String ESTADOS_HOGAR = "estadosHogar";
    /**
     * Nombre del parametro del estado del integrante del hogar
     */
    public static final String ESTADO_INTEGRANTE_HOGAR = "estadoIntegranteHogar";
    /**
     * Nombre del parametro del tipo de integrante del hogar
     */
    public static final String TIPO_INTEGRANTE_HOGAR = "tipoIntegranteHogar";
    /**
     * Nombre del parametro de la lista de integrante de hogar validos
     */
    public static final String TIPOS_INTEGRANTES_HOGAR = "tiposIntegrantesHogar";
    /**
     * Nombre del parametro del tipo de ahorro
     */
    public static final String TIPO_AHORRO = "tipoAhorro";
    /**
     * Nombre del parametro movilizacion de ahorros
     */
    public static final String AHORROS_MOVILIZADOS = "ahorrosMovilizados";
    /**
     * Nombre del parametro canal de recepcion
     */
    public static final String CANAL_RECEPCION = "canalRecepcion";
    /**
     * Nombre del parametro de la condicion del hogar
     */
    public static final String CONDICION_HOGAR = "condicionHogar";
    /**
     * Nombre del parametro de la condicion especial del jefe hogar
     */
    public static final String CONDICION_ESPECIAL_JEFE_HOGAR = "condicionEspecialJefeHogar";
    /**
     * Nombre del parametro de la condicion especial del miembro del hogar
     */
    public static final String CONDICION_ESPECIAL_MIEMBRO_HOGAR = "condicionEspecialMiembroHogar";
    /**
     * Llave del mensaje de existencia de validación
     */
    public static final String KEY_MENSAJE_EX_SOLIC = "HU-112-110.existeSolicitud";
    /**
     * Llave del mensaje de existencia de validación
     */
    public static final String KEY_MENSAJE_EX_SOLIC_EN_PROCESO = "TRA-305.existeSolicitud";
    /**
     * Llave del mensaje de existencia de validación
     */
    public static final String KEY_MENSAJE_NO_EX_SOLIC = "HU-112-110.noExisteSolicitud";
    /**
     * Llave del mensaje de existencia de validación
     */
    public static final String KEY_MENSAJE_NO_PARAMS = "transversal.faltanParametros";
    /**
     * Llave del mensaje de existencia de validación
     */
    public static final String KEY_MENSAJE_NO_EV = "transversal.noEvaluada";
    /**
     * Llave del mensaje de empleador activo
     */
    public static final String KEY_MENSAJE_EMP_ACTIVO = "HU-112-110.empleadorActivo";
    /**
     * Llave del mensaje de empleador no activo
     */
    public static final String KEY_MENSAJE_EMP_NO_ACTIVO = "HU-112-110.empleadorNoActivo";
    /**
     * Llave del mensaje de empleador moroso
     */
    public static final String KEY_MENSAJE_EMP_MOROSO = "HU-112-110.empleadorMoroso";

    /**
     * Llave del mensaje de empleador moroso
     */
    public static final String KEY_MENSAJE_EMP_OTRO = "HU-112-110.empleadorDesafiliadoOtro";
    /**
     * Llave del mensaje de empleador desafiliado
     */
    public static final String KEY_MENSAJE_EMP_DESAF = "HU-112-110.empleadorDesafiliado";
    /**
     * Llave del mensaje de empleador desafiliado y en listas de especial
     * revisión
     */
    public static final String KEY_MENSAJE_EMP_DESAF_LISTAS = "HU-112-110.empleadorDesafiliadoListas";
    /**
     * Parámetro 0 de mensaje
     */
    public static final String MENSAJE_PARAM_0 = "{0}";
    /**
     * Parámetro 1 de mensaje
     */
    public static final String MENSAJE_PARAM_1 = "{1}";
    /**
     * Parámetro 2 de mensaje
     */
    public static final String MENSAJE_PARAM_2 = "{2}";
    /**
     * Llave del mensaje de validación BD core exitosa
     */
    public static final String KEY_MENSAJE_EMP_DB_EXITO = "HU-112-110.validacionCoreExitosa";
    /**
     * Llave del mensaje de validación BD core empleado no existe y está en
     * listas
     */
    public static final String KEY_MENSAJE_EMP_NO_ACTIVO_EN_LISTAS = "HU-112-110.empladorNoExEnListas";

    /**
     * Llave del mensaje de validación empleado tiene cuenta web activa
     */
    public static final String KEY_MENSAJE_EMP_ACTIVO_CUENTA_WEB_ACTIVA = "HU-112-110.cuentaWebActiva";
    /**
     * Llave del mensaje de validación empleado no tiene cuenta web activa
     */
    public static final String KEY_MENSAJE_NO_CUENTA_WEB_ACTIVA = "HU-112-110.noCuentaWebActiva";

    /* llaves properties Personas */
    /**
     * Llave del mensaje Persona no registrada en el sistema.
     */
    public static final String KEY_PERSONA_NO_EXISTE = "HU-121-339.personaNoExiste";
    /**
     * Llave del mensaje Persona está afiliada (activa) con el mismo empleador
     * relacionado en la solicitud.
     */
    public static final String KEY_PERSONA_AFILIADA = "HU-121-339.personaAfiliada";

    /**
     * Llave del mensaje Persona está afiliada (activa) con el mismo empleador
     * relacionado en la solicitud.
     */
    public static final String KEY_PERSONA_TRABAJADOR_AFILIADA = "HU-121-339.trabajadorAfiliado";
    /**
     * Llave del mensaje Persona si está fallecida.
     */
    public static final String KEY_PERSONA_FALLECIDA = "HU-121-339.personaFallecida";
    /**
     * Llave del mensaje Persona si está fallecida y su registro se realizó por medio de un canal distinto al presencial.
     */
    public static final String KEY_PERSONA_FALLECIDA_NO_PRESENCIAL = "HU-121-339.personaFallecidaNoPresencial";
    /**
     * Llave del mensaje Persona si está fallecida y su registro se realizó por medio de un canal distinto al presencial.
     */
    public static final String KEY_PERSONA_FALLECIDA_PRESENCIAL = "HU-121-339.personaFallecidaPresencial";
    /**
     * 
     * Llave del mensaje Persona mismo empleador.
     */
    public static final String KEY_PERSONA_EMPLEADOR = "HU-121-339.personaEmpleador";
    /**
     * Llave del mensaje Persona registrada como beneficiario hijo.
     */
    public static final String KEY_PERSONA_BENEFICIARIO_HIJO = "HU-121-339.personaBeneficiarioHijo";
    /**
     * Llave del mensaje Persona registrada como beneficiario padre.
     */
    public static final String KEY_PERSONA_BENEFICIARIO_PADRE = "HU-121-339.personaBeneficiarioPadre";
    /**
     * Llave del mensaje Persona registrada como beneficiario conyuge.
     */
    public static final String KEY_PERSONA_BENEFICIARIO_CONYUGE = "HU-121-339.personaBeneficiarioConyuge";
    /**
     * Llave del mensaje Persona registrada como afiliado principal.
     */
    public static final String KEY_PERSONA_AFILIADO_PRINCIPAL = "HU-121-339.personaAfiliadoPrincipal";
    
    public static final String KEY_PERSONA_AFILIADO_PRINCIPAL_CONYUGE = "HU-121-339.personaAfiliadoPrincipalConyuge";
    
    /**
     * Llave del mensaje Persona registrada como pensionado.
     */
    public static final String KEY_PERSONA_PENSIONADO = "HU-121-339.personaPensionado";
    /**
     * Llave del mensaje Persona registrada trabajador dependiente.
     */
    public static final String KEY_PERSONA_TRABJADOR_DEPENDIENTE = "HU-121-339.personaTrabajadorDependiente";
    /**
     * Llave del mensaje Persona registrada independiente.
     */
    public static final String KEY_PERSONA_INDEPENDIENTE = "HU-121-339.personaIndependiente";
    /**
     * Llave del mensaje Persona registrada condición de invalidez.
     */
    public static final String KEY_PERSONA_INVALIDEZ = "HU-121-339.personaInvalidez";
    /**
     * Llave del mensaje Persona registrada como socio de empleador.
     */
    public static final String KEY_PERSONA_SOCIO_EMPLEADOR = "HU-121-339.personaSocioDeEmpleador";
    /**
     * Llave del mensaje Persona registrada como conyuge del socio empleador.
     */
    public static final String KEY_PERSONA_CONYUGE_SOCIO_EMPLEADOR = "HU-121-339.personaConyugeSocioDeEmpleador";
    /**
     * Llave del mensaje Persona registrada como representante legal.
     */
    public static final String KEY_PERSONA_REPRESENTANTE_LEGAL = "HU-121-339.personaRepresentanteLegal";
    /**
     * Llave del mensaje Persona ya tiene 19 o más.
     */
    public static final String KEY_PERSONA_CON_19 = "HU-121-339.personaCon19";
    /**
     * Llave del mensaje Persona ya tiene 19 o más y condicion de invalidez.
     */
    public static final String KEY_PERSONA_CON_19_CONDICION_INVALIDEZ = "HU-121-339.personaCon19CondicionInvalidez";
    /**
     * Llave del mensaje Persona ya tiene 19 o más y certificado estudio.
     */
    public static final String KEY_PERSONA_CON_19_CERTIFICADO_ESTUDIO = "HU-121-339.personaCon19CertificadoEstudio";
    /**
     * Llave del mensaje Persona con pago de aportes.
     */
    public static final String KEY_PERSONA_CON_PAGO_APORTES = "HU-121-339.personaConPagoAportes";
    /**
     * Llave del mensaje Persona con grupo familiar.
     */
    public static final String KEY_PERSONA_CON_GRUPO_FAMILIAR = "HU-121-339.personaGrupoFamiliar";
    /**
     * Llave del mensaje Persona hijo de dos grupos familiares.
     */
    public static final String KEY_PERSONA_HIJO_DOS_GRUPO_FAMILIAR = "HU-121-339.personaHijoDosGrupoFamiliar";
    /**
     * Llave del mensaje Persona hijo mismo genero.
     */
    public static final String KEY_PERSONA_HIJO_MISMO_GENERO = "HU-121-339.personaHijoMismoGenero";
    /**
     * Llave del mensaje Persona no coincide apellidos padres.
     */
    public static final String KEY_PERSONA_NO_COINCIDE_APELLIDOS_PADRES = "HU-121-339.personaNoCoincideApellidosPadres";
    /**
     * Llave del mensaje Persona menor de 60 años.
     */
    public static final String KEY_PERSONA_MENOR_60 = "HU-121-339.personaMenor60";
    /**
     * Llave del mensaje Persona menor de 60 años y condicion invalidez.
     */
    public static final String KEY_PERSONA_MENOR_60_INVALIDEZ = "HU-121-339.personaMenor60Invalidez";
    /**
     * Llave del mensaje Persona no tiene al menos una coincidencia en apellidos
     * con el afiliado principal.
     */
    public static final String KEY_PERSONA_NO_COINCIDE_APELLIDOS_AFILIADO = "HU-121-339.personaNoCoincideApellidosAfiliado";
    /**
     * Llave del mensaje Persona registrada con otro parentesco.
     */
    public static final String KEY_PERSONA_OTRO_PARENTESCO = "HU-121-339.personaOtroParentesco";
    /**
     * Llave del mensaje afiliado principal ya tiene registrado un hermano
     * huérfano.
     */
    public static final String KEY_AFILIADO_PRINCIPAL_HERMANO_HUERFANO = "HU-121-339.afiliadoPrincipalHermanoHuerfano";
    /**
     * Llave del mensaje afiliado principal ya tiene registrado beneficiario
     * padre o madre.
     */
    public static final String KEY_AFILIADO_PRINCIPAL_BENEFICIARIO_PADRES = "HU-121-339.afiliadoPrincipalBeneficiarioPadres";
    /**
     * Llave del mensaje persona menor al afiliado principal.
     */
    public static final String KEY_PERSONA_MENOR_AFILIADO_PRINCIPAL = "HU-121-339.personaMenorAfiliadoPrincipal";
    /**
     * Llave del mensaje persona menor al afiliado principal.
     */
    public static final String KEY_PERSONA_MAYOR_AFILIADO_PRINCIPAL = "HU-121-339.personaMayorAfiliadoPrincipal";
    /**
     * Llave del mensaje fecha de nacimiento de la persona posterior a la fecha
     * de afiliación
     */
    public static final String KEY_FECHA_NACIMIENTO_POSTERIOR_FECHA_AFILIACION = "HU-121-339.fechaNacimientoPosteriorFechaAfiliacion";
    /**
     * Llave del mensaje persona es el mismo empleador.
     */
    public static final String KEY_PERSONA_MISMO_EMPLEADOR = "HU-121-339.personaMismoEmpleador";
    /**
     * Llave del mensaje fecha de inicio de labores con empleador es posterior a
     * la fecha de afiliación.
     */
    public static final String KEY_INICIO_LABORES_POSTERIOR_FECHA_AFILIACION = "HU-121-339.fechaInicioLaboresPosteriorFechaAfiliacion";

    /**
     * Llave del mensaje fecha de nacimiento mayor a la del día de inicio de
     * labores con empleador.
     */
    public static final String KEY_FECHA_NACIMIENTO_MAYOR_FECHA_INICIO_LABORES = "HU-121-339.fechaNacimientoMayorFechaInicioLabores";
    /**
     * Llave del mensaje persona beneficiario padre registrado.
     */
    public static final String KEY_PERSONA_BENEFICIARIO_PADRE_REGISTRADO = "HU-121-339.personaBeneficiarioPadreRegistrado";
    /**
     * Llave del mensaje persona beneficiario madre registrado.
     */
    public static final String KEY_PERSONA_BENEFICIARIO_MADRE_REGISTRADO = "HU-121-339.personaBeneficiarioMadreRegistrado";
    /**
     * Llave del mensaje persona incluída en la misma solicitud.
     */
    public static final String KEY_PERSONA_INCLUIDA_SOLICITUD = "HU-121-339.personaIncluidaSolicitud";
    /**
     * Llave del mensaje persona hijastro grupo familiar.
     */
    public static final String KEY_PERSONA_HIJASTRO_GRUPO_FAMILIAR = "HU-121-339.personaHijastroGrupoFamiliar";
    /**
     * Llave del mensaje persona hijo biológico.
     */
    public static final String KEY_PERSONA_HIJO_BIOLOGICO = "HU-121-339.personaHijoBiologico";
    /**
     * Llave del mensaje persona conyuge empleador.
     */
    public static final String KEY_PERSONA_CONYUGE_EMPLEADOR = "HU-121-339.personaConyugeEmpleador";
    /**
     * Llave del mensaje afiliado principal ya tiene registrado un beneficiario
     * tipo cónyuge (activo).
     */
    public static final String KEY_PERSONA_BENEFICIARIO_CONYUGE_REGISTRADO = "HU-121-339.personaBeneficiarioConyugeRegistrado";
    /**
     * Llave del mensaje persona no es mayor de 15 años.
     */
    public static final String KEY_PERSONA_NO_MAYOR_15 = "HU-121-339.personaNoMayor15";
    /**
     * Llave del mensaje persona no es mayor de 14 años.
     */
    public static final String KEY_PERSONA_NO_MAYOR_14 = "HU-121-339.personaNoMayor14";
    /**
     * Llave del mensaje persona registrada como cooperante.
     */
    public static final String KEY_PERSONA_COOPERANTE = "HU-121-339.personaCooperante";
    /**
     * Llave del mensaje persona registrada con otro tipo y número de documento.
     */
    public static final String KEY_PERSONA_REGISTRADA_OTRO_ID = "HU-121-339.personaRegistradaOtroId";
    /* constantes de campos */
    /**
     * Nombre del parámetro condición de invalidez.
     */
    public static final String CONDICION_INVALIDEZ_PARAM = "condicionInvalidez";

    /**
     * Nombre del parámetro estado condición de invalidez.
     */
    public static final String ESTADO_CONDICION_INVALIDEZ_PARAM = "estadoCondicionInvalidez";

    /**
     * Nombre del parámetro certificado escolar.
     */
    public static final String CERTIFICADO_ESCOLAR_PARAM = "certificadoEscolar";
    /**
     * Nombre del parámetro certificado escolar.
     */
    public static final String CERTIFICADO_ESCOLAR_PARAM2 = "certificadoEscolarHijo";
    /**
     * Nombre dle parámetro fecha de vencimiento del certificado escolar.
     */
    public static final String FECHA_VENCIMIENTO_CERTIFICADO_PARAM = "fechaVencimientoCertificado";
    /**
     * Nombre del parámetro fecha de recepción del certificado escolar.
     */
    public static final String FECHA_RECEPCION_CERTIFICADO_PARAM = "fechaRecepcionCertificado";
    /**
     * Nombre del parámetro estudiante para el trabajo del desarrollo humano.
     */
    public static final String TRABAJO_DESARROLLO_HUMANO_PARAM = "estudianteTrabajoDesarrolloHumano";
    /**
     * Nombre del parámetro estudiante para el trabajo del desarrollo humano.
     */
    public static final String TRABAJO_DESARROLLO_HUMANO_PARAM2 = "beneficioProgramaTrabajoDesarrollo";
    /**
     * Nombre del parámetro fecha nacimiento.
     */
    public static final String FECHA_NACIMIENTO_PARAM = "fechaNacimiento";
    /**
     * Nombre del parámetro primero nombre.
     */
    public static final String PRIMER_NOMBRE_PARAM = "primerNombre";
    /**
     * Nombre del parámetro primero nombre representante legar.
     */
    public static final String PRIMER_NOMBRE_RL_PARAM = "primerNombreRL";
    /**
     * Nombre del parámetro primero nombre representante legal suplente.
     */
    public static final String PRIMER_NOMBRE_RLS_PARAM = "primerNombreRLS";
    /**
     * Nombre del parámetro primero nombre.
     */
    public static final String SEGUNDO_NOMBRE_PARAM = "segundoNombre";
    /**
     * Nombre del parámetro primer apellido.
     */
    public static final String PRIMER_APELLIDO_PARAM = "primerApellido";
    /**
     * Nombre del parámetro primer apellido representante legal.
     */
    public static final String PRIMER_APELLIDO_RL_PARAM = "primerApellidoRL";
    /**
     * Nombre del parámetro primer apellido representante legal suplente.
     */
    public static final String PRIMER_APELLIDO_RLS_PARAM = "primerApellidoRLS";
    /**
     * Nombre del parámetro primer apellido.
     */
    public static final String SEGUNDO_APELLIDO_PARAM = "segundoApellido";
    /**
     * Nombre del parámetro estado persona fallecido
     */
    public static final String ESTADO_PERSONA_FALLECIDO = "fallecido";
    /**
     * Nombre del parámetro de tipo de identificación empleador.
     */
    public static final String TIPO_ID_EMPLEADOR_PARAM = "tipoIdentificacionEmpleador";
    /**
     * Nombre del parámetro de número de identificación empleador.
     */
    public static final String NUM_ID_EMPLEADOR_PARAM = "numeroIdentificacionEmpleador";
    /**
     * Nombre del parámetro del tipo de afiliado.
     */
    public static final String TIPO_AFILIADO_PARAM = "tipoAfiliado";
    /**
     * Nombre del parámetro de los tipos de afiliado.
     */
    public static final String TIPOS_AFILIADO_PARAM = "tiposAfiliado";
    /**
     * Nombre del parámetro estado medio de Pago.
     */
    public static final String ESTADO_MEDIO_PAGO_PARAM = "estadoMedioPago";
    /**
     * Nombre del parámetro afiliado es Admon Subsidio.
     */
    public static final String AFILIADO_ADMON_SUBSIDIO_PARAM = "afiliadoAdmonSubsidio";
    /**
     * Nombre del parámetro tipo medio de Pago.
     */
    public static final String TIPO_MEDIO_PAGO_PARAM = "tipoMedioPago";
    /**
     * Nombre del parámetro del estado afiliado.
     */
    public static final String ESTADO_AFILIADO_PARAM = "estadoAfiliado";
    /**
     * Nombre del parámetro del estado afiliado.
     */
    public static final String ESTADOS_VALIDOS_AFILIADO_PARAM = "estadosValidosAfiliado";
    /**
     * Nombre del parámetro fallecido.
     */
    public static final String FALLECIDO_PARAM = "fallecido";
    /**
     * Nombre del parámetro tipo de beneficiario.
     */
    public static final String TIPO_BENEFICIARIO_PARAM = "tipoBeneficiario";
      /**
     * Nombre del parámetro numeroIdentificacionAfiliado.
     */
    public static final String NUMERO_IDENTIFICACION_AFILIADO = "numeroIdentificacionAfiliado";
      /**
     * Nombre del parámetro tipoIdentificacionAfiliado.
     */
    public static final String TIPO_IDENTIFICACION_AFILIADO = "tipoIdentificacionAfiliado";
    /**
     * Nombre del parámetro tipo beneficio.
     */
    public static final String TIPO_BENEFICIO_PARAM = "tipoBeneficio";
    /**
     * Nombre del parámetro estado beneficiario afiliado.
     */
    public static final String ESTADO_BENEFICIARIO = "estadoBeneficiarioAfiliado";
    /**
     * Nombre del parámetro estado tarjeta
     */
    public static final String ESTADO_TARJETA_PARAM = "estadoTarjeta";
    /**
     * Nombre del parámetro genero
     */
    public static final String GENERO_PARAM = "genero";
    /**
     * Nombre del parámetro Id Afiliado
     */
    public static final String NUM_ID_AFILIADO = "idAfiliado";
    /**
     * Nombre del parámetro estado empleador.
     */
    public static final String ESTADO_EMPLEADOR_PARAM = "estadoEmpleador";
    /**
     * Nombre del parametro estado de la cartera
     */
    public static final String ESTADO_CARTERA_PARAM = "estadoCartera";
    /**
     * Nombre del parametro estado de la operacion de cartera
     */
    public static final String ESTADO_OPERACION_CARTERA_PARAM = "estadoOperacionCartera";
    /**
     * Nombre del parámetro tipo solicitanto de cartera
     */
    public static final String TIPO_SOLCITANTE = "tipoSolicitante";
    /**
     * Nombre del parámetro clasificacion param.
     */
    public static final String CLASIFICACION_PARAM = "clasificacion";
    /**
     * Nombre del parámetro invalidez param.
     */
    public static final String INVALIDEZ_PARAM = "invalidez";
    /**
     * Nombre del parámetro de NamedQuery de tipoAfiliado
     */
    public static final String NAMED_QUERY_PARAM_TIP_AFI = "tipoAfiliado";
    /**
     * Nombre del parámetro de NamedQuery de tipoBeneficiario
     */
    public static final String NAMED_QUERY_PARAM_TIP_BEN = "tipoBeneficiario";
    /**
     * Constantes id grupo familiar.
     */
    public static final String NUM_ID_GRUPOFAMILIAR = "idGrupoFamiliar";

    /**
     * Constantes motivo desafiliacion.
     */
    public static final String MOTIVO_DESAFILIACION = "motivoDesafiliacion";

    /**
     * Llave del mensaje de existencia de solicitud afiliacion persona por cualquier medio
     */
    public static final String KEY_MENSAJE_EXIS_SOLIC_PERS = "HU-123-374.existeSolicitud";

    /**
     * Llave del mensaje de existencia de solicitud afiliacion persona por cualquier medio con correo
     */
    public static final String KEY_MENSAJE_EXIS_SOLIC_MAIL = "HU-123-374.existeSolicitudCorreo";

    /**
     * Llave del mensaje de existencia de solicitud afiliacion persona por cualquier medio con correo
     */
    public static final String KEY_MENSAJE_EXIS_SOLIC_EN_PROCESO = "HU-121-104.existeSolicitudEnProceso";

    /**
     * Llave del mensaje de existencia de solicitud afiliacion persona por cualquier medio con correo
     */
    public static final String KEY_MENSAJE_EXIS_SOLIC_WEB_EN_PROCESO = "HU-123-374.existeSolicitudWebEnProceso";

    /**
     * Llave del mensaje de existencia del afiliado activo
     */
    public static final String KEY_MENSAJE_AFIL_ACTIVO = "HU-123-374.solicitanteActivo";

    /**
     * Llave del mensaje de existencia del afiliado activo
     */
    public static final String KEY_MENSAJE_AFIL_NO_ACTIVO = "HU-123-374.noExisteSolicitud";

    /**
     * Nombre del parámetro de NamedQuery de tipoAfiliado
     */
    public static final String NAMED_QUERY_SOLICITUD_PERSONA = "SolicitudAfiliacion.buscarPorPersona";

    /**
     * Nombre del parámetro de NamedQuery de tipoAfiliado
     */
    public static final String NAMED_QUERY_ROL_AFILIADO = "SolicitudAfiliacion.buscarRolAfiliado";

    /**
     * Nombre del parámetro de tipo de identificación
     */
    public static final String TIPO_ID_AFILIADO_PARAM = "tipoIdentificacionAfiliado";
    /**
     * Nombre del parámetro de número de identificación
     */
    public static final String NUM_ID_AFILIADO_PARAM = "numeroIdentificacionAfiliado";

    /**
     * Nombre del parámetro objeto validación.
     */
    public static final String OBJETO_VALIDACION_PARAM = "objetoValidacion";

    /**
     * Nombre del parámetro de estado del jefe del hogar
     */
    public static final String ESTADO_JEFE_HOGAR = "estadoJefeHogar";

    /**
     * Nombre del parámetro que indica si la persona tiene la marca de ser beneficiario
     * de subsidio de vivienda
     */
    public static final String HA_SIDO_BENEIFICARIO_SUBSIDIO_VIVIENDA = "haSidoBeneficiarioSubsidioVivienda";

    /* constantes mensajes REINTEGRO */
    /**
     * Llave del mensaje fecha retiro supera dias reintegro.
     */
    public static final String KEY_FECHA_REINTEGRO_SUPERADA = "HU-341-TRN.fechaReintegroSuperada";
    /**
     * Llave del mensaje y causal.
     */
    public static final String KEY_ESTADO_CAUSAL = "HU-341-TRN.estadoCausalEmpleador";

    public static final String MENSAJE_EMPL_EXISTE_LISTAS = "HU-111-60.empExisteInfoAport";

    /**
     * Llave del mensaje estado para nueva afiliacion.
     */
    public static final String KEY_ESTADO_NUEVA_AFILIACION = "HU-341-TRN.estadoNuevaAfiliacion";

    /* novedades */

    /**
     * Llave del mensaje cuando una novedad no se habilita
     */
    public static final String KEY_NOVEDAD_NO_HABILITADA = "HU-420-NOV.novedadNoHabilitada";
    /**
     * Constante NAMED_QUERY_PARAM_TIPO_NOVEDAD
     */
    public static final String NAMED_QUERY_PARAM_TIPO_NOVEDAD = "tipoNovedad";

    /**
     * Constante NAMED_QUERY_PARAM_ID_EMPRESA
     */
    public static final String NAMED_QUERY_PARAM_ID_EMPRESA = "idEmpresa";

    /**
     * Constante NAMED_QUERY_PARAM_ID_EMPLEADOR
     */
    public static final String NAMED_QUERY_PARAM_ID_EMPLEADOR = "idEmpleador";

    /**
     * Constante ESTADO_SUCURSAL
     */
    public static final String ESTADO_SUCURSAL = "estadoSucursal";

    // Constantes para validaciones Anexo 1.3, pestaña 40.RNs

    /**
     * Constante ESTADO_AFILIACION_DIFERENTE_ACTIVO_INACTIVO_NO_FORMALIZADO, VALIDACIÓN 4 40.RNs
     */
    public static final String ESTADO_AFILIACION_DIFERENTE_ACTIVO_INACTIVO_NO_FORMALIZADO = "novedades.empleador.estadoAfiliacionDiferenteActivoInactivoNoFormalizado";

    /**
     * Constante TIPO_IDENT_DIFERENTE, VALIDACIÓN 5 40.RNs
     */
    public static final String TIPO_IDENT_DIFERENTE = "novedades.empleador.tipoIdentDiferente";

    /**
     * Constante TRABAJADORES_NO_SON_CERO, VALIDACIÓN 6 40.RNs
     */
    public static final String TRABAJADORES_NO_SON_CERO = "novedades.empleador.trabajadoresNoSonCero";

    /**
     * Constante EMPLEADOR_SUCURSAL_ACTIVA, VALIDACIÓN 7 40.RNs
     */
    public static final String EMPLEADOR_SUCURSAL_ACTIVA = "novedades.empleador.empleadorSucursalActiva";

    /**
     * Constante EMPLEADOR_HA_TENIDO_BENEFICIO_1429_ANIO_2010, VALIDACIÓN 8 40.RNs
     */
    public static final String EMPLEADOR_HA_TENIDO_BENEFICIO_1429_ANIO_2010 = "novedades.empleador.empleadorHaTenidoBeneficio1429Anio2010";

    /**
     * Constante EMPLEADOR_HA_TENIDO_BENEFICIO_590_ANIO_2000, VALIDACIÓN 9 40.RNs
     */
    public static final String EMPLEADOR_HA_TENIDO_BENEFICIO_590_ANIO_2000 = "novedades.empleador.empleadorHaTenidoBeneficio590Anio2000";

    /**
     * Constante EMPLEADOR_CON_MAS_DE_50_TRABAJADORES, VALIDACIÓN 10 40.RNs
     */
    public static final String EMPLEADOR_CON_MAS_DE_50_TRABAJADORES = "novedades.empleador.empleadorConMasDe50Trabajadores";

    /**
     * Constante EMPLEADOR_CON_MAS_DE_200_TRABAJADORES, VALIDACIÓN 11 40.RNs
     */
    public static final String KEY_EMPLEADOR_CON_MAS_DE_200_TRABAJADORES = "novedades.empleador.empleadorConMasDe200Trabajadores";

    /**
     * Constante KEY_EMPLEADOR_NO_TIENE_ACTIVO_BENEFICIO_1429_2010, VALIDACIÓN 12 40.RNs
     */
    public static final String KEY_EMPLEADOR_NO_TIENE_ACTIVO_BENEFICIO_1429_2010 = "novedades.empleador.empleadorNoTieneActivoBeneficio1429Anio2010";

    /**
     * Constante KEY_EMPLEADOR_NO_TIENE_ACTIVO_BENEFICIO_590_2000 , VALIDACIÓN 13 40.RNs
     */
    public static final String KEY_EMPLEADOR_NO_TIENE_ACTIVO_BENEFICIO_590_2000 = "novedades.empleador.empleadorNoTieneActivoBeneficio590Anio2000";

    /**
     * Constante KEY_ESTADO_AFILIACION_DIFERENTE_ACTIVO, VALIDACIÓN 14 40.RNs
     */
    public static final String KEY_ESTADO_AFILIACION_DIFERENTE_ACTIVO = "novedades.empleador.estadoAfiliacionDiferenteActivo";

    /**
     * Constante KEY_EMPLEADOR_NO_TIENE_TRABAJADOR_ACTIVO, VALIDACIÓN 15 40.RNs
     */
    public static final String KEY_EMPLEADOR_NO_TIENE_TRABAJADOR_ACTIVO = "novedades.empleador.empleadorNoTieneTrabajadorActivo";

    /**
     * Constante KEY_EMPLEADOR_REGISTRADO_COMO_MOROSO, VALIDACIÓN 16 40.RNs
     */
    public static final String KEY_EMPLEADOR_REGISTRADO_COMO_MOROSO = "novedades.empleador.empleadorRegistradoComoMoroso";

    /**
     * Constante KEY_EMPLEADOR_TIENE_TRABAJADOR_ACTIVO, VALIDACIÓN 17 40.RNs
     */
    public static final String KEY_EMPLEADOR_TIENE_TRABAJADOR_ACTIVO = "novedades.empleador.empleadorTieneTrabajadorActivo";

    /**
     * Constante KEY_PERSONA_REGISTRADA_COMO_FALLECIDA, VALIDACIÓN 18 40.RNs
     */
    public static final String KEY_PERSONA_REGISTRADA_COMO_FALLECIDA = "novedades.empleador.personaRegistradaComoFallecida";

    /**
     * Constante KEY_VALOR_CAMPO_BENEFICIO_LEY_1429_2010_NO, VALIDACIÓN 19 40.RNs
     */
    public static final String KEY_VALOR_CAMPO_BENEFICIO_LEY_1429_2010_NO = "novedades.empleador.valorCampoBeneficioLey1429Anio2010";

    /**
     * Constante KEY_VALOR_ANIO_BENEFICIO_1429_2010_MENOR, VALIDACIÓN 20 40.RNs
     */
    public static final String KEY_VALOR_ANIO_BENEFICIO_1429_2010_MENOR = "novedades.empleador.valorAnioBeneficio1429Anio2010Menor";

    /**
     * Constante KEY_VALOR_FECHA_CONSTITUCION_INVALIDO, VALIDACIÓN 21 40.RNs
     */
    public static final String KEY_VALOR_FECHA_CONSTITUCION_INVALIDO = "novedades.empleador.valorFechaConstitucionInvalido";

    /**
     * Constante KEY_CAMPO_EMPLEADOR_CUBIERTO_POR_BENEFICIO_LEY_590_2000_EN_NO, VALIDACIÓN 22 40.RNs
     */
    public static final String KEY_CAMPO_EMPLEADOR_CUBIERTO_POR_BENEFICIO_LEY_590_2000_EN_NO = "novedades.empleador.campoEmpleadorCubiertoBeneficioLey590Anio2000EnNo";

    /**
     * Constante KEY_ANIO_INICIO_BENEFICIO_590, VALIDACIÓN 23 40.RNs
     */
    public static final String KEY_ANIO_INICIO_BENEFICIO_590 = "novedades.empleador.anioInicioBeneficio590";

    /**
     * Constante KEY_FECHA_CONSTITUCION_LEY_590_NO_VIGENTE, VALIDACIÓN 24 40.RNs
     */
    public static final String KEY_FECHA_CONSTITUCION_LEY_590_NO_VIGENTE = "novedades.empleador.vigenciaFechaConstitucion";

    /**
     * Constante KEY_EMPLEADOR_CUBIERTO_LEY_1429_2010, VALIDACIÓN 25 40.RNs
     */
    public static final String KEY_EMPLEADOR_CUBIERTO_LEY_1429_2010 = "novedades.empleador.empleadorMarcadoNoBeneficiarioLey1429Anio2010";

    /**
     * Constante KEY_EMPLEADOR_MARCADO_COMO_BENEFICIARIO_LEY_590_2000 , VALIDACIÓN 26 40.RNs
     */
    public static final String KEY_EMPLEADOR_MARCADO_COMO_BENEFICIARIO_LEY_590_2000 = "novedades.empleador.empleadorMarcadoBeneficiarioLey590Anio2000";

    /**
     * Constante KEY_PARA_EMPLEADOR_ORIGEN_CAMPO_DIFERENTE_ACTIVO, VALIDACIÓN 27 40.RNs
     */
    public static final String KEY_PARA_EMPLEADOR_ORIGEN_CAMPO_DIFERENTE_ACTIVO = "novedades.empleador.emplOriginCampDifActivo";

    /**
     * Constante KEY_EMPL_DESTINO_DIFERENTE_ACTIVO, VALIDACIÓN 28 40.RNs
     */
    public static final String KEY_EMPL_DESTINO_DIFERENTE_ACTIVO = "novedades.empleador.empleadorDestinoDiferenteActivo";

    /**
     * Constante KEY_EMPL_OBJETO_NOVEDAD_DIFERENTE_ACTIVO, VALIDACIÓN 29 40.RNs
     */
    public static final String KEY_EMPL_OBJETO_NOVEDAD_DIFERENTE_ACTIVO = "novedades.empleador.empleadorObjetoNovedadDiferenteActivo";

    /**
     * Constante KEY_TRABAJADOR_SELECCIONADO_NO_ACTIVO, VALIDACIÓN 30 40.RNs
     */
    public static final String KEY_TRABAJADOR_SELECCIONADO_NO_ACTIVO = "novedades.empleador.trabajadorSeleccionadoNoActivo";

    /**
     * Constante KEY_EMPLEADOR_DESTINO_ASOCIADO, VALIDACIÓN 32 40.RNs
     */
    public static final String KEY_EMPLEADOR_DESTINO_ASOCIADO = "novedades.empleador.empleadorDestinoAsociado";

    /**
     * Constante KEY_EMPLEADOR_ORIGEN_CON_TRABAJADORES_ACTIVOS, VALIDACIÓN 34 40.RNs
     */
    public static final String KEY_EMPLEADOR_ORIGEN_CON_TRABAJADORES_ACTIVOS = "novedades.empleador.empleadorOrigenContrabajadoresActivos";

    /**
     * Constante KEY_HAY_SUCURSAL_ASOCIADA_EMPLEADOR, VALIDACIÓN 35 40.RNs
     */
    public static final String KEY_HAY_SUCURSAL_ASOCIADA_EMPLEADOR = "novedades.empleador.haySucursalAsociadaAlEmpleador";

    /**
     * Constante KEY_HAY_PERSONA_REGISTRADA, VALIDACIÓN 36 40.RNs
     */
    public static final String KEY_HAY_PERSONA_REGISTRADA = "novedades.empleador.hayPersonaRegistrada";

    /**
     * Constante KEY_OTRO_EMPLEADOR_YA_REGISTRADO_EN_LA_CCF, VALIDACIÓN 37 40.RNs
     */
    public static final String KEY_OTRO_EMPLEADOR_YA_REGISTRADO_EN_LA_CCF = "novedades.empleador.hayOtroEmpleadorRegistradoEnLaCCF";

    /**
     * Constante KEY_EMPLEADOR_HA_EFECTUADO_REPORTE_PLANILLA, VALIDACIÓN 38 40.RNs
     */
    public static final String KEY_EMPLEADOR_HA_EFECTUADO_REPORTE_PLANILLA = "novedades.empleador.empleadorHaEfectuadoReportesDePlanilla";

    /**
     * Constante KEY_EMPLEADOR_TIENE_ASOCIADO_ACTO_ADMINISTRATIVO, VALIDACIÓN 39 40.RNs
     */
    public static final String KEY_EMPLEADOR_TIENE_ASOCIADO_ACTO_ADMINISTRATIVO = "novedades.empleador.empleadorTieneAsociadoActoAdministrativo";

    /**
     * Constante KEY_EMPLEADOR_HA_SIDO_INCLUIDO_EN_ALGUN_REPORTE_NORMATIVO, VALIDACIÓN 40 40.RNs
     */
    public static final String KEY_EMPLEADOR_HA_SIDO_INCLUIDO_EN_ALGUN_REPORTE_NORMATIVO = "novedades.empleador.elEmpleadorhaSidoIncluidoEnAlgunReporteNormativo";

    /**
     * Constante PERIODO_BENEFICIO_LEY_1429_2010_CADUCADO, VALIDACIÓN 41 40.RNs
     */
    public static final String PERIODO_BENEFICIO_LEY_1429_2010_CADUCADO = "novedades.empleador.periodoBeneficioLey1429Caducado";

    /**
     * Constante PERIODO_BENEFICIO_LEY_590_2000_CADUCADO, VALIDACIÓN 42 40.RNs
     */
    public static final String PERIODO_BENEFICIO_LEY_590_2000_CADUCADO = "novedades.empleador.periodoBeneficioLey590Caducado";

    /**
     * Constante KEY_VALOR_CAMPO_REQUIERE_INACTIVACION_CUENTA_WEB, VALIDACIÓN 43 40.RNs
     */
    public static final String KEY_VALOR_CAMPO_REQUIERE_INACTIVACION_CUENTA_WEB = "novedades.empleador.valorCampoRequiereInactivacionCuentaWeb";

    /**
     * Constante para el mensaje de error del validador 48 40.RNs
     */
    public static final String KEY_NOVEDADES_EMPLEADOR_VALIDADOR_48 = "novedades.empleador.validador48";

    /**
     * Constante para el mensaje de error del validador 50 40.RNs
     */
    public static final String KEY_NOVEDADES_EMPLEADOR_VALIDADOR_50 = "novedades.empleador.validador50";
    public static final String KEY_NOVEDADES_EMPLEADOR_VALIDADOR_50_REVERSE = "novedades.empleador.validador50.reverse";

    /**
     * Constante para el mensaje de error del validador 52 40.RNs
     */
    public static final String KEY_NOVEDADES_EMPLEADOR_VALIDADOR_52 = "novedades.empleador.validador52";
    public static final String KEY_NOVEDADES_EMPLEADOR_VALIDADOR_52_REVERSE = "novedades.empleador.validador52.reverse";

    //key's para las validaciones de novedades persona

    /**
     * constante KEY_ACTIVACION_GRUPO_FAMILIAR_INEMBARGABLE
     */
    public static final String KEY_ACTIVACION_GRUPO_FAMILIAR_INEMBARGABLE = "novedades.persona.activacionGrupoFamiliarInembargable";

    /**
     * constante KEY_ACTIVO_MAS_1_5_SM_0_6_POR_CIENTO
     */
    public static final String KEY_ACTIVO_MAS_1_5_SM_0_6_POR_CIENTO = "novedades.persona.activoMas15Sm06PorCiento";

    /**
     * constante KEY_ACTIVO_MAS_1_5_SM_2_POR_CIENTO
     */
    public static final String KEY_ACTIVO_MAS_1_5_SM_2_POR_CIENTO = "novedades.persona.activoMas15Sm2PorCiento";

    /**
     * constante KEY_ACTIVO_RESPECTO_AFILIADO_PPAL
     */
    public static final String KEY_ACTIVO_RESPECTO_AFILIADO_PPAL = "novedades.persona.activoRespectoAfiliadoPpal";

    /**
     * constante KEY_AFILIADO_ACTIVO
     */
    public static final String KEY_AFILIADO_ACTIVO = "novedades.persona.afiliadoActivo";

    /**
     * constante KEY_AFILIADO_ACTIVO_RESPECTO_EMPLEADOR
     */
    public static final String KEY_AFILIADO_ACTIVO_RESPECTO_EMPLEADOR = "novedades.persona.afiliadoActivoRespectoEmpleador";

    /**
     * constante KEY_AFILIADO_TRABAJADOR_DEPENDIENTE
     */
    public static final String KEY_AFILIADO_TRABAJADOR_DEPENDIENTE = "novedades.persona.afiliadoTrabajadorDependiente";

    /**
     * constante KEY_AFILIADO_TRABAJADOR_INDEPENDIENTE
     */
    public static final String KEY_AFILIADO_TRABAJADOR_INDEPENDIENTE = "novedades.persona.afiliadoTrabajadorIndependiente";

    /**
     * constante KEY_AFILIADO_CONYUGE_SOCIO_EMPLEADOR
     */
    public static final String KEY_AFILIADO_CONYUGE_SOCIO_EMPLEADOR = "novedades.persona.afiliadoConyugeSocioEmpleador";

    /**
     * constante KEY_AFILIADO_GRP_FAM_MIN_1_BEN_ACT
     */
    public static final String KEY_AFILIADO_GRP_FAM_MIN_1_BEN_ACT = "novedades.persona.afiliadoGrpFamMin1BenAct";

    /**
     * constante KEY_AFILIADO_REP_LEGAL_EMPLEADOR
     */
    public static final String KEY_AFILIADO_REP_LEGAL_EMPLEADOR = "novedades.persona.afiliadoRepLegalEmpleador";

    /**
     * constante KEY_AFILIADO_SOCIO_EMPLEADOR
     */
    public static final String KEY_AFILIADO_SOCIO_EMPLEADOR = "novedades.persona.afiliadoSocioEmpleador";

    /**
     * constante KEY_AFILIADO_SOLTERO_VIUDO
     */
    public static final String KEY_AFILIADO_SOLTERO_VIUDO = "novedades.persona.afiliadoSolteroViudo";

    /**
     * constante KEY_TRABAJADOR_INDEPENDIENTE_INACTIVO
     */
    public static final String KEY_TRABAJADOR_INDEPENDIENTE_INACTIVO = "novedades.persona.trabajadorIndependienteInactivo";

    /**
     * constante KEY_TRABAJADOR_NO_AFILIADO
     */
    public static final String KEY_TRABAJADOR_NO_AFILIADO = "novedades.persona.trabajadorNoAfiliado";

    
    /**
     * constante KEY_ANULACION_AFILIACION
     */
    public static final String KEY_ANULACION_AFILIACION = "novedades.persona.anulacionAfiliacion";

    /**
     * constante KEY_BENEFICIARIO_ACTIVO_DIF_CONYUGE
     */
    public static final String KEY_BENEFICIARIO_ACTIVO_DIF_CONYUGE = "novedades.persona.beneficiarioActivoDifConyuge";

    /**
     * constante KEY_CAMBIO_TIPO_DOC_IDENTIDAD
     */
    public static final String KEY_CAMBIO_TIPO_DOC_IDENTIDAD = "novedades.persona.cambioTipoDocIdentidad";

    /**
     * constante KEY_CONYUGE_ACTIVO
     */
    public static final String KEY_CONYUGE_ACTIVO = "novedades.persona.conyugeActivo";

    /**
     * constante KEY_DESACTIVACION_BENEFICIARIO
     */
    public static final String KEY_DESACTIVACION_BENEFICIARIO = "novedades.persona.desactivacionBeneficiario";

    /**
     * constante KEY_DESCATIVACION_CUENTA_WEB
     */
    public static final String KEY_DESCATIVACION_CUENTA_WEB = "novedades.persona.descativacionCuentaWeb";

    /**
     * constante KEY_DESCATIVACION_GRUPO_FAM_INEMBARGABLE
     */
    public static final String KEY_DESCATIVACION_GRUPO_FAM_INEMBARGABLE = "novedades.persona.descativacionGrupoFamInembargable";

    /**
     * constante KEY_DESACTIVACION_EMPLEADOR_TRAB_ACT
     */
    public static final String KEY_DESACTIVACION_EMPLEADOR_TRAB_ACT = "novedades.persona.desactivacionEmpleadorTrabAct";

    /**
     * constante KEY_EMPLEADOR_DESTINO_ACT
     */
    public static final String KEY_EMPLEADOR_DESTINO_ACT = "novedades.persona.empleadorDestinoAct";

    /**
     * constante KEY_ESTADO_AFILIADO
     */
    public static final String KEY_ESTADO_AFILIADO = "novedades.persona.estadoAfiliado";

    /**
     * constante KEY_ESTADO_AFILIADO_ACTIVO_INACTIVO
     */
    public static final String KEY_ESTADO_AFILIADO_ACTIVO_INACTIVO = "novedades.persona.estadoAfiliadoActivoInactivo";
    
    /**
     * constante KEY_GRUPO_FAMILIAR_SIN_BENEFICIARIOS
     */
    public static final String KEY_GRUPO_FAMILIAR_SIN_BENEFICIARIOS = "novedades.persona.grupoFamiliarSinBeneficiarios";

    /**
     * constante KEY_ESTADO_CIVIL_AFILIADO
     */
    public static final String KEY_ESTADO_CIVIL_AFILIADO = "novedades.persona.estadoCivilAfiliado";

    /**
     * constante KEY_ESTADO_CONYUGE
     */
    public static final String KEY_ESTADO_CONYUGE = "novedades.persona.estadoConyuge";

    /**
     * constante KEY_ESTADO_HIJO
     */
    public static final String KEY_ESTADO_HIJO = "novedades.persona.estadoHijo";

    /**
     * constante KEY_ESTADO_PADRE
     */
    public static final String KEY_ESTADO_PADRE = "novedades.persona.estadoPadre";

    /**
     * constante KEY_ESTADO_MADRE
     */
    public static final String KEY_ESTADO_MADRE = "novedades.persona.estadoMadre";

    /**
     * constante KEY_ESTADO_RESPECTO_AFILIADO_PPAL
     */
    public static final String KEY_ESTADO_RESPECTO_AFILIADO_PPAL = "novedades.persona.estadoRespectoAfiliadoPpal";

    /**
     * constante KEY_ESTADO_SUBCIDIO_TRABAJADOR
     */
    public static final String KEY_ESTADO_SUBCIDIO_TRABAJADOR = "novedades.persona.estadoSubcidioTrabajador";

    /**
     * constante KEY_FECHA_FIN_INCAPACIDAD
     */
    public static final String KEY_FECHA_FIN_INCAPACIDAD = "novedades.persona.fechaFinIncapacidad";

    /**
     * constante KEY_FECHA_VENCIMIENTO_CERT_ESCOLAR
     */
    public static final String KEY_FECHA_VENCIMIENTO_CERT_ESCOLAR = "novedades.persona.fechaVencimientoCertEscolar";

    /**
     * constante KEY_GRUPO_FAMILIAR_ACTIVO
     */
    public static final String KEY_GRUPO_FAMILIAR_ACTIVO = "novedades.persona.grupoFamiliarActivo";

    /**
     * constante KEY_GRUPO_FAMILIAR_NO_INEMBARGABLE
     */
    public static final String KEY_GRUPO_FAMILIAR_NO_INEMBARGABLE = "novedades.persona.grupoFamiliarNoInembargable";

    /**
     * constante KEY_HIJO_ACTIVO
     */
    public static final String KEY_HIJO_ACTIVO = "novedades.persona.hijoActivo";

    /**
     * constante KEY_HIJO_MENOR_23
     */
    public static final String KEY_HIJO_MENOR_23 = "novedades.persona.hijoMenor23";

    /**
     * constante KEY_MINIMO_UN_HIJO
     */
    public static final String KEY_MINIMO_UN_HIJO = "novedades.persona.minimoUnHijo";

    /**
     * constante KEY_NINGUN_BENEFICIARIO_HIJO
     */
    public static final String KEY_NINGUN_BENEFICIARIO_HIJO = "novedades.persona.ningunBeneficiarioHijo";

    /**
     * constante KEY_NO_DOS_BENEFICIARIO_PADRES_ACT
     */
    public static final String KEY_NO_DOS_BENEFICIARIO_PADRES_ACT = "novedades.persona.noDosBeneficiarioPadresAct";

    /**
     * constante KEY_NO_PENS_FIDELIDAD_25_ANIOS
     */
    public static final String KEY_NO_PENS_FIDELIDAD_25_ANIOS = "novedades.persona.noPensFidelidad25Anios";

    /**
     * constante KEY_NO_PENS_MENOS_0_PORCIENTO
     */
    public static final String KEY_NO_PENS_MENOS_0_PORCIENTO = "novedades.persona.noPensMenos0Porciento";

    /**
     * constante KEY_PADRE_ACTIVO
     */
    public static final String KEY_PADRE_ACTIVO = "novedades.persona.padreActivo";

    /**
     * constante KEY_PAGO_SUBCIDIO_BENEFICIARIO_DEP
     */
    public static final String KEY_PAGO_SUBCIDIO_BENEFICIARIO_DEP = "novedades.persona.pagoSubcidioBeneficiarioDep";

    /**
     * constante KEY_PENSIONADO_DIF_FIDELIDAD_MENOS_O_PORCIENTO
     */
    public static final String KEY_PENSIONADO_DIF_FIDELIDAD_MENOS_O_PORCIENTO = "novedades.persona.pensionadoDifFidelidadMenosOPorciento";

    /**
     * constante KEY_PENSIONADO_MAS_06_PORCIENTO
     */
    public static final String KEY_PENSIONADO_MAS_06_PORCIENTO = "novedades.persona.pensionadoMas06Porciento";

    /**
     * constante KEY_PENSIONADO_MAS_2_PORCIENTO
     */
    public static final String KEY_PENSIONADO_MAS_2_PORCIENTO = "novedades.persona.pensionadoMas2Porciento";

    /**
     * constante KEY_PENSIONADO_MENOS_06_PORCIENTO
     */
    public static final String KEY_PENSIONADO_MENOS_06_PORCIENTO = "novedades.persona.pensionadoMenos06Porciento";

    /**
     * constante KEY_PENSIONADO_MENOS_0_PORCIENTO
     */
    public static final String KEY_PENSIONADO_MENOS_0_PORCIENTO = "novedades.persona.pensionadoMenos0Porciento";

    /**
     * constante KEY_PENSIONADO_MENOS_2_PORCIENTO
     */
    public static final String KEY_PENSIONADO_MENOS_2_PORCIENTO = "novedades.persona.pensionadoMenos2Porciento";

    /**
     * constante KEY_PENSIONADO_PENSION_FAMILIAR
     */
    public static final String KEY_PENSIONADO_PENSION_FAMILIAR = "novedades.persona.pensionadoPensionFamiliar";

    /**
     * constante KEY_MAYOR_18
     */
    public static final String KEY_MAYOR_18 = "novedades.persona.mayor18";

    /**
     * constante KEY_POSTULADO_FOVIS
     */
    public static final String KEY_POSTULADO_FOVIS = "novedades.persona.postuladoFovis";

    /**
     * constante KEY_SERVICIOS_SIN_AFILIACION_INACT
     */
    public static final String KEY_SERVICIOS_SIN_AFILIACION_INACT = "novedades.persona.serviciosSinAfiliacionInact";

    /**
     * constante KEY_SIN_BENEFICIARIO_PADRE
     */
    public static final String KEY_SIN_BENEFICIARIO_PADRE = "novedades.persona.sinBeneficiarioPadre";

    /**
     * constante KEY_TARJETA_ACTIVA
     */
    public static final String KEY_TARJETA_ACTIVA = "novedades.persona.tarjetaActiva";

    /**
     * constante KEY_TARJETA_MEDIO_DE_PAGO
     */
    public static final String KEY_TARJETA_MEDIO_DE_PAGO = "novedades.persona.tarjetaMedioDePago";

    /**
     * constante KEY_TARJETA_PRIMERA_VEZ
     */
    public static final String KEY_TARJETA_PRIMERA_VEZ = "novedades.persona.tarjetaPrimeraVez";

    /**
     * constante KEY_TIEMPO_TRANSC_DESDE_RETIRO
     */
    public static final String KEY_TIEMPO_TRANSC_DESDE_RETIRO = "novedades.persona.tiempoTranscDesdeRetiro";

    /**
     * constante KEY_TRABAJADOR_INHAB_PARA_SUBSIDIO
     */
    public static final String KEY_TRABAJADOR_INHAB_PARA_SUBSIDIO = "novedades.persona.trabajadorInhabParaSubsidio";

    /**
     * constante KEY_TRANSFERENCIA_MEDIO_DE_PAGO
     */
    public static final String KEY_TRANSFERENCIA_MEDIO_DE_PAGO = "novedades.persona.transferenciaMedioDePago";

    /**
     * constante KEY_UBICACION_RESPECTO_AFILIADO_PPAL
     */
    public static final String KEY_UBICACION_RESPECTO_AFILIADO_PPAL = "novedades.persona.ubicacionRespectoAfiliadoPpal";
    public static final String KEY_UBICACION_RESPECTO_AFILIADO_PPAL_REVERSE = "novedades.persona.ubicacionRespectoAfiliadoPpal.reverse";

    /**
     * constante KEY_VALIDACION_ABORTADA_POR_EXCEPCION
     */
    public static final String KEY_VALIDACION_NOVEDAD_ABORTADA_POR_EXCEPCION = "novedades.persona.validacionNovedadAbortadaExcepcion";

    //keys para mensajes de validacion RN novedades persona

    /**
     * contante KEY_PERSONA_MAYOR_IGUAL_19
     */
    public static final String KEY_PERSONA_MAYOR_IGUAL_19 = "novedades.persona.personaMayorIgual19";

    /**
     * contante KEY_PERSONA_APORTES_3_PERIODOS
     */
    public static final String KEY_PERSONA_APORTES_3_PERIODOS = "novedades.persona.personaAportes3periodos";

    /**
     * contante KEY_PER_ACTIVA_GRUPO_AFILIADO
     */
    public static final String KEY_PER_ACTIVA_GRUPO_AFILIADO = "novedades.persona.personaActivaGrupoFamiliarAfiliado";

    /**
     * contante KEY_HIJO_2_GRUPOS_DISTINTOS_AFILIADOS
     */
    public static final String KEY_HIJO_2_GRUPOS_DISTINTOS_AFILIADOS = "novedades.persona.hijoActivo2GruposDeDistintosAfiliados";

    /**
     * contante KEY_HIJO_2_GRUPO_AFILIADO_GENERO
     */
    public static final String KEY_HIJO_2_GRUPO_AFILIADO_GENERO = "novedades.persona.hijoActivoGrupoAfiliadoMismoGenero";

    /**
     * contante KEY_AFILIADO_CON_CONYUGE_ACTIVO
     */
    public static final String KEY_AFILIADO_CON_CONYUGE_ACTIVO = "novedades.persona.afiliadoConConyugeActivo";
    public static final String KEY_AFILIADO_CON_CONYUGE_ACTIVO_REVERSE ="novedades.persona.afiliadoConConyugeActivo.reverse";

    /**
     * contante KEY_PERSONA_AFILIADA_COOPERANTE
     */
    public static final String KEY_PERSONA_AFILIADA_COOPERANTE = "novedades.persona.personaAfiliadaCooperante";

    /**
     * contante KEY_TIPO_DOCUMENTO
     */
    public static final String KEY_TIPO_DOCUMENTO = "novedades.persona.tipoDocumento";

    /**
     * contante KEY_DOCUMENTO_UNICA_PERSONA
     */
    public static final String KEY_DOCUMENTO_UNICA_PERSONA = "novedades.persona.cambioTipoNumeroUnicaPersona";

    /**
     * contante KEY_NOMBRE_APELLIDO_UNICA_PERSONA
     */
    public static final String KEY_NOMBRE_APELLIDO_UNICA_PERSONA = "novedades.persona.cambioNombreApellidoUnicaPersona";

    /**
     * contante KEY_TRABAJADOR_DEP_ASOCIADO_EMPLEADOR
     */
    public static final String KEY_TRABAJADOR_DEP_ASOCIADO_EMPLEADOR = "novedades.persona.trabajadorDependienteAsociadoEmpleador";

    /**
     * contante KEY_PENSIONADO_PAGADOR_APORTES_ACTIVO
     */
    public static final String KEY_PENSIONADO_PAGADOR_APORTES_ACTIVO = "novedades.persona.pensionadoConPagadorAportesActivo";

    /**
     * contante KEY_INDEPENDIENTE_PAGADOR_APORTES_ACTIVO
     */
    public static final String KEY_INDEPENDIENTE_PAGADOR_APORTES_ACTIVO = "novedades.persona.independienteConPagadorAportesActivo";

    /**
     * contante KEY_DEPENDIENTE_ADMIN_SUBSIDIO
     */
    public static final String KEY_DEPENDIENTE_ADMIN_SUBSIDIO = "novedades.persona.dependienteAdministradorDelSubsidio";

    /**
     * contante KEY_DEPENDIENTE_NO_ADMIN_SUBSIDIO_GRUPO
     */
    public static final String KEY_DEPENDIENTE_NO_ADMIN_SUBSIDIO_GRUPO = "novedades.persona.dependienteNoAdminSubsidioGrupoFamiliar";

    /**
     * contante KEY_BENEFICIARIO_HIJO_HUERFANO
     */
    public static final String KEY_BENEFICIARIO_HIJO_HUERFANO = "novedades.persona.personaBeneficiarioHijoHuerfano";
    public static final String KEY_BENEFICIARIO_HIJO_HUERFANO_REVERSE = "novedades.persona.personaBeneficiarioHijoHuerfano.reverse";

    /**
     * contante KEY_BENEFICIARIO_HIJO_ADOPTIVO
     */
    public static final String KEY_BENEFICIARIO_HIJO_ADOPTIVO = "novedades.persona.personaBeneficiarioHijoAdoptivo";
    public static final String KEY_BENEFICIARIO_HIJO_ADOPTIVO_REVERSE = "novedades.persona.personaBeneficiarioHijoAdoptivo.reverse";

    /**
     * contante KEY_BENEFICIARIO_EN_CUSTODIA
     */
    public static final String KEY_BENEFICIARIO_EN_CUSTODIA = "novedades.persona.personaBeneficiarioEnCustodia";
    public static final String KEY_BENEFICIARIO_EN_CUSTODIA_REVERSE = "novedades.persona.personaBeneficiarioEnCustodia.reverse";

    /**
     * contante KEY_BENEFICIARIO_PADRE
     */
    public static final String KEY_BENEFICIARIO_PADRE = "novedades.persona.personaBeneficiarioPadre";
    public static final String KEY_BENEFICIARIO_PADRE_REVERSE = "novedades.persona.personaBeneficiarioPadre.reverse";

    /**
     * contante KEY_BENEFICIARIO_MADRE
     */
    public static final String KEY_BENEFICIARIO_MADRE = "novedades.persona.personaBeneficiarioMadre";
    public static final String KEY_BENEFICIARIO_MADRE_REVERSE = "novedades.persona.personaBeneficiarioMadre.reverse";

    /**
     * contante KEY_NO_PAGO_APORTES_AFILIACION
     */
    public static final String KEY_NO_PAGO_APORTES_AFILIACION = "novedades.persona.noPagoAportesParaAfiliacion";

    /**
     * contante KEY_FECHA_VENCIMIENTO_CERT_ESTUDIOS
     */
    public static final String KEY_FECHA_VENCIMIENTO_CERT_ESTUDIOS = "novedades.persona.fechaVencimientoCertificadoColegioYSuperior";

    /**
     * contante KEY_EMPLEADOR_ACTIVO
     */
    public static final String KEY_EMPLEADOR_ACTIVO = "novedades.persona.empleadorActivo";

    /**
     * contante KEY_EMPLEADOR_TRABAJADORES_AFILIADOS
     */
    public static final String KEY_EMPLEADOR_TRABAJADORES_AFILIADOS = "novedades.persona.empleadorConTrabajadoresAfiliados";

    /**
     * contante KEY_DESAFILIACION_EMPLEADO_EN_CURSO
     */
    public static final String KEY_DESAFILIACION_EMPLEADO_EN_CURSO = "novedades.persona.desafiliacionEmpleadoEnCursoParaEmpleador";

    /**
     * contante KEY_SUSTITUCION_PATRONAL_EN_CURSO
     */
    public static final String KEY_SUSTITUCION_PATRONAL_EN_CURSO = "novedades.persona.sustitucionPatronalEnCursoEmpleadorOrigenDestino";

    /**
     * contante KEY_EMPLEADOR_ORIGEN_ACTIVO
     */
    public static final String KEY_EMPLEADOR_ORIGEN_ACTIVO = "novedades.persona.empleadorOrigenActivo";

    /**
     * contante KEY_EMPLEADOR_DESTINO_ACTIVO
     */
    public static final String KEY_EMPLEADOR_DESTINO_ACTIVO = "novedades.persona.empleadorDestinoActivo";

    /**
     * contante KEY_TRABAJADORES_EMPLEADOR_ORIGEN_ACTIVOS
     */
    public static final String KEY_TRABAJADORES_EMPLEADOR_ORIGEN_ACTIVOS = "novedades.persona.trabajadoresEmpleadorOrigenActivos";

    /**
     * contante KEY_FECHA_EN_TIEMPO_PARAMETRIZADO
     */
    public static final String KEY_FECHA_EN_TIEMPO_PARAMETRIZADO = "novedades.persona.fechaRecepcionEnTiempoParametrizado";

    /**
     * contante KEY_EXPEDICION_REEXPEDICION_TARJETA
     */
    public static final String KEY_EXPEDICION_REEXPEDICION_TARJETA = "novedades.persona.expedicionReexpedicionTarjeta";
    /**
     * 
     */
    public static final String ESTADO_AFILIADO_CAJA = "estadoAfiliadoCaja";
    /**
     * constante KEY_DESACTIVAR_ACTIVAR_GRUPO_FAMILIAR_INEMBARGABLE_DESACTIVACION
     */
    public static final String KEY_DESACTIVAR_ACTIVAR_GRUPO_FAMILIAR_INEMBARGABLE_DESACTIVACION = "novedades.persona.desactivacionActivacionGrupoFamiliarInembargable.desactivacion";
    /**
     * constante KEY_DESACTIVAR_ACTIVAR_GRUPO_FAMILIAR_INEMBARGABLE_ACTIVACION
     */
    public static final String KEY_DESACTIVAR_ACTIVAR_GRUPO_FAMILIAR_INEMBARGABLE_ACTIVACION = "novedades.persona.desactivacionActivacionGrupoFamiliarInembargable.activacion";
    /**
     * constante KEY_AFILIADO_ESTADO_SOLTERO
     */
    public static final String KEY_AFILIADO_ESTADO_SOLTERO = "novedades.persona.afiliadoEstadoSolteroConSoportes";
    /**
     * constante KEY_PERSONA_CONYUGE_ACTIVO
     */
    public static final String KEY_PERSONA_CONYUGE_ACTIVO = "novedades.persona.personaComoConyugeActivo";
    public static final String KEY_PERSONA_CONYUGE_ACTIVO_REVERSE = "novedades.persona.personaComoConyugeActivo.reverse";
    /**
     * constante KEY_PERSONA_PADRE_ACTIVO
     */
    public static final String KEY_PERSONA_PADRE_ACTIVO = "novedades.persona.personaComoPadreActivo";
    public static final String KEY_PERSONA_PADRE_ACTIVO_REVERSE = "novedades.persona.personaComoPadreActivo.reverse";
    /**
     * constante KEY_PERSONA_HIJO_ACTIVO
     */
    public static final String KEY_PERSONA_HIJO_ACTIVO = "novedades.persona.personaComoHijoActivo";
    public static final String KEY_PERSONA_HIJO_ACTIVO_REVERSE = "novedades.persona.personaComoHijoActivo.reverse";
    /**
     * constante KEY_PERSONA_DEPENDIENTE_ACTIVO
     */
    public static final String KEY_PERSONA_DEPENDIENTE_ACTIVO = "novedades.persona.personaComoDependienteActivo";
    public static final String KEY_PERSONA_DEPENDIENTE_ACTIVO_REVERSE = "novedades.persona.personaComoDependienteActivo.reverse";
    /**
     * constante KEY_PERSONA_INDEPENDIENTE_ACTIVO
     */
    public static final String KEY_PERSONA_INDEPENDIENTE_ACTIVO = "novedades.persona.personaComoIndependienteActivo";
    public static final String KEY_PERSONA_INDEPENDIENTE_ACTIVO_REVERSE = "novedades.persona.personaComoIndependienteActivo.reverse";
    /**
     * constante KEY_PERSONA_PENSIONADO_ACTIVO
     */
    public static final String KEY_PERSONA_PENSIONADO_ACTIVO = "novedades.persona.personaComoPensionadoActivo";
    public static final String KEY_PERSONA_PENSIONADO_ACTIVO_REVERSE = "novedades.persona.personaComoPensionadoActivo.reverse";
    /**
     * constante KEY_PERSONA_EMPLEADOR_ACTIVO
     */
    public static final String KEY_PERSONA_EMPLEADOR_ACTIVO = "novedades.persona.personaComoEmpleadorActivo";
    /**
     * constante KEY_TIPO_DOCUMENTO_TARJETA_IDENTIDAD
     */
    public static final String KEY_TIPO_DOCUMENTO_TARJETA_IDENTIDAD = "novedades.persona.tipoDocumentoTarjetaIdentidad";
    /**
     * constante KEY_EMPLEADOR_DOS_SUCURSALES
     */
    public static final String KEY_EMPLEADOR_DOS_SUCURSALES = "novedades.empleador.empleadorDosSucursales";

    /**
     * Constante KEY_AFILIADO_SIN_BENEFIARIO_DIFERENTE_CONYUGE
     */
    public static final String KEY_AFILIADO_SIN_BENEFIARIO_DIFERENTE_CONYUGE = "novedades.persona.afiliadoSinBeneficiarioDiferenteConyuge";

    /**
     * Constante para el mensaje de error de empleador no existe en sistema
     */
    public static final String KEY_EMPLEADOR_NO_EXISTE_SISTEMA = "novedades.empleador.noExisteEnSistema";

    /**
     * Constante para el mensaje de error cuando el estado de afiliacion del empleador es ACTIVO
     */
    public static final String KEY_EMPLEADOR_ESTADO_AFILIACION_ACTIVO = "novedades.empleador.diferenteEstadoActivo";

    /**
     * Constante para el mensaje de error cuando el empleador cuenta con un registro de subsacion de expulsion
     */
    public static final String KEY_EMPLEADOR_EXISTE_REGISTRO_SUBSANACION = "novedades.empleador.registroSubsanacionEmpleador";

    /**
     * Constante para el mensaje de error cuando la perosna no cuenta con un registro de subsacion de expulsion
     */
    public static final String KEY_PERSONA_NO_EXISTE_REGISTRO_SUBSANACION = "novedades.persona.registroSubsanacionPersona";

    /**
     * Llave del mensaje de existencia de validación
     */
    public static final String KEY_MENSAJE_EX_SOLIC_WEB = "HU-112-110.existeSolicitudWeb";

    /**
     * Nombre del parámetro de NamedQuery de tipoAfiliado
     */
    public static final String NAMED_QUERY_SOLICITUD_PERSONA_DEPENDIENTES = "SolicitudAfiliacion.buscarPorPersona.dependiente";

    /**
     * Nombre del parámetro de NamedQuery de tipoAfiliado
     */
    public static final String NAMED_QUERY_SOLICITUD_PERSONA_WEB = "SolicitudAfiliacion.buscarPorPersona.web";

    /**
     * Constante para el mensaje de error cuando el afiliado ya cuenta con LMA vigente
     */
    public static final String KEY_PERSONA_TIENE_LMA_VIGENTE = "novedades.persona.tieneLMAVigente";

    /**
     * Constante para el mensaje de error cuando el afiliado ya cuenta con incapacidad IGE o IRL vigente
     */
    public static final String KEY_PERSONA_TIENE_IGE_IRL_VIGENTE = "novedades.persona.tieneIGEoIRLVigente";

    /**
     * Constante para el mensaje de error cuando el afiliado ya cuenta uno novedad de persona vigente
     */
    public static final String KEY_PERSONA_TIENE_NOVEDAD_VIGENTE = "novedades.persona.tieneNovedadVigente";

    /**
     * Constante para el mensaje de error cuando el afiliado ya cuenta uno novedad de persona vigente
     */
    public static final String KEY_PERSONA_TIENE_ENTIDAD_PAGADORA_ASOCIADA = "novedades.persona.tieneEntidadPagadoraAsociada";

    /**
     * Nombre parametro para el idRolAfiliado como dato para aplicar en validaciones
     */
    public static final String KEY_ID_ROL_AFILIADO = "idRolAfiliado";

    /**
     * Nombre parametro para el idBeneficiario como dato para aplicar en validaciones
     */
    public static final String ID_BENEFICIARIO = "idBeneficiario";

    /**
     * Nombre parametro para el idGrupoFamiliar como dato para aplicar en validaciones
     */
    public static final String ID_GRUPOFAMILIAR = "idGrupoFamiliar";

    /**
     * Nombre del parametro para el estado del ciclo de asignacion
     */
    public static final String ESTADO_CICLO_ASIGNACION = "estadoCicloAsignacion";

    /**
     * Nombre del parametro para la modalidad de la postulacion
     */
    public static final String MODALIDAD_POSTULACION = "modalidadPostulacion";

    /**
     * Nombre del paramtro para el campo Inhabilitado para subsidio
     */
    public static final String INHABILITADO_SUBSIDIO_PARAM = "inhabilitadoSubsidio";

    /**
     * Nombre del parametro para la fecha actual
     */
    public static final String FECHA_ACTUAL_PARAM = "fechaActual";

    /*
     * Constantes de postulaciones FOVIS
     */

    /**
     * Constante para el mensaje de error cuando no se encuentran aportes asociados a un jefe de hogar dependiente.
     */
    public static final String KEY_JEFE_HOGAR_NO_HAY_APORTES_DEPENDIENTE = "Fovis.Postulaciones.Jefe.Hogar.No.Hay.Aportes.Dependiente";
    /**
     * Constante para el mensaje de error cuando no se encuentran aportes asociados a un jefe de hogar independiente.
     */
    public static final String KEY_JEFE_HOGAR_NO_HAY_APORTES_INDEPENDIENTE = "Fovis.Postulaciones.Jefe.Hogar.No.Hay.Aportes.Independiente";
    /**
     * Constante para el mensaje de error cuando no se encuentran aportes asociados a un jefe de hogar que presenta multiafiliacion.
     */
    public static final String KEY_JEFE_HOGAR_NO_HAY_APORTES_MULTIAFILIACION = "Fovis.Postulaciones.Jefe.Hogar.No.Hay.Aportes.Multiafiliacion";
    /**
     * Llave del mensaje persona menor de 16 años.
     */
    public static final String KEY_PERSONA_MENOR_16 = "HU-321.personaMenor16";

    /**
     * Llave del mensaje Jefe de hogar dependiente y empleador registrado como beneficiario ley 1429.
     */
    public static final String KEY_JEFE_HOGAR_DEPENDIENTE_BENEFICIO_1429 = "HU-321.jefeHogarDependienteBeneficio1429";

    //CONSTANTES FOVIS

    /*
     * Constantes de postulaciones FOVIS
     */
    /**
     * Constante para el mendaje de error cuando se encuentra que el jefe de hogar en condicion especial no tiene un conyugue activo
     */
    public static final String KEY_JEFE_HOGAR_CABEZA_FAMILIA_NO_CONYUGUE_ACTIVO = "Fovis.Postulaciones.Jefe.Hogar.Cabeza.Familia.No.Conyuge.Activo";
    /**
     * Constante para el mendaje de error cuando se encuentra que el conyuge del solicitante se encuentra activo como conyuge en otro grupo
     * familiar
     */
    public static final String KEY_CONYUGE_ACTIVO_OTRO_GRUPO_FAMILIAR = "Fovis.Postulaciones.Jefe.Hogar.Cabeza.Familia.No.Conyuge.Activo";
    /**
     * Constante para el mendaje de error cuando se encuentra que el conyuge del solicitante se encuentra activo como conyuge en otro grupo
     * familiar
     */
    public static final String KEY_CONYUGE_ACTIVO_GRUPO_FAMILIAR_JEFE_HOGAR = "Fovis.Postulaciones.Jefe.Hogar.Conyuge.Activo.Grupo.Hogar";
    /**
     * Constante para el mendaje de error cuando se encuentra que el jefe de hogar ha sido beneficiario de otros subsidios de vivienda
     */
    public static final String KEY_JEFE_HOGAR_BENEFICIARIO_OTROS_SUBSIDIOS = "Fovis.Postulaciones.Jefe.Hogar.Cabeza.Familia.Beneficiario.Otros.Subsidios";
    /**
     * Constante para el mendaje de error cuando se encuentra que la persona señala una condicion de invalidez en el formulario FOVIS
     * la cual no se encuentra registrada en base de datos
     */
    public static final String KEY_PERSONA_CONDICION_INVALIDEZ_SIN_REGISTRAR_BD = "Fovis.Postulaciones.Jefe.Hogar.Cabeza.Familia.No.Invalidez.No.Registrado.Bd";
    /**
     * Constante para el mendaje de error cuando se encuentra que el jefe de hogar no señala una condicion de invalidez en el formulario
     * FOVIS
     * la cual si se encuentra registrada en base de datos
     */
    public static final String KEY_PERSONA_CONDICION_INVALIDEZ_SIN_SENALAR_EN_FOVIS = "Fovis.Postulaciones.Jefe.Hogar.Cabeza.Familia.Invalidez.No.Senalada.Bd";
    /**
     * Constante para el mendaje de error cuando se encuentra que la persona señala un hijo biologico en el formulario FOVIS
     * el cual no se encuentra registrada en base de datos como hijo biologico
     */
    public static final String KEY_PERSONA_HIJO_BIOLOGICO_SIN_REGISTRAR_BD = "Fovis.Postulaciones.Jefe.Hogar.Cabeza.Familia.Hijo.Biologico.No.Registrado.Bd";
    /**
     * Constante para el mendaje de error cuando se encuentra que la persona señala un hijastro en el formulario FOVIS
     * el cual no se encuentra registrada en base de datos como hijastro
     */
    public static final String KEY_PERSONA_HIJASTRO_SIN_REGISTRAR_BD = "Fovis.Postulaciones.Jefe.Hogar.Cabeza.Familia.Hijastro.No.Registrado.Bd";
    /**
     * Constante para el mendaje de error cuando se encuentra que la persona señala un hijo adoptivo en el formulario FOVIS
     * el cual no se encuentra registrada en base de datos como hijo adoptivo
     */
    public static final String KEY_PERSONA_HIJO_ADOPTIVO_SIN_REGISTRAR_BD = "Fovis.Postulaciones.Jefe.Hogar.Cabeza.Familia.Hijo.Adoptivo.No.Registrado.Bd";
    /**
     * Constante para el mendaje de error cuando se encuentra que el postulante a subsidio sobrepasa los ingresos totales permitidos
     * para aspirar al subsidio de vivienda
     */
    public static final String KEY_INGRESOS_MAXIMOS_NO_PERMITIDOS = "Fovis.Postulaciones.Persona.Ingresos.Maximos.Invalidos";
    /**
     * Constante para el mendaje de error cuando se encuentra que el hogar postulante con ingresos superiores a 2 SMLV no tiene un ahorro
     * previo
     * del 10% o superior del campo Valor de soluion de la vivienda
     */
    public static final String KEY_AHORRO_PREVIO_INFERIOR = "Fovis.Postulaciones.Hogar.Solicitante.Ahorro.Previo.Inferior";
    /**
     * Constante para el mendaje de error cuando se encuentra que una persona no tiene coincidencia de apellidos con el jefe de hogar o con
     * el
     * conyuge activo del grupo de hogar de existir
     */
    public static final String KEY_APELLIDOS_NO_COINCIDEN = "Fovis.Postulaciones.Persona.Apellidos.No.Coinciden";
    /**
     * Constante para el mendaje de error cuando se encuentra que una persona tiene un parentesco en el formulario FOVIS distinto al
     * relacionado en
     * la base de datos con respecto al jefe de hogar
     */
    public static final String KEY_PARENTESCO_NO_COINCIDE = "Fovis.Postulaciones.Persona.Parentesco.No.Coincide";
    /**
     * Constante para el mendaje de error cuando se encuentra que una persona tiene asociada un subsidio de vivienda de tipo Mejora de
     * vivienda saludable
     * y esta no esta en una de sus modalidades validas
     */
    public static final String KEY_MODALIDAD_MEJORA_VIVIENDA_POSTULACION_INVALIDA = "Fovis.Postulaciones.Persona.Mejora.Vivienda.Postulacion.Invalida";
    /**
     * Constante para el mensaje de error cuando la modalidad de vivienda es adquisicion de vivienda usada rural o urbana
     * y algunos de los valores de los campos Condición del hogar/Tipo postulante, Condición especial- Jefe de hogar o Condición especial- Miembro del hogar no son validos
     */
    public static final String KEY_MODALIDAD_ADQUISICION_VIVIENDA_USADA_INVALIDA = "Fovis.Postulaciones.Persona.Modalidad.Adquisicion.Vivienda.Usada.Postulacion.Invalida";
    /**
     * Constanye para el memsaje de error cuando se encuentra que una persona tiene fecha de nacimietno superior a la fecha de postulacion a
     * subsidio
     * de vivienda
     */
    public static final String KEY_FECHA_NACIMIENTO_SUPERIOR_FECHA_POSTULACION = "Fovis.Postulaciones.Persona.Fecha.Nacimiento.Superior.Fecha.postulacion";
    /**
     * Constante para el mensaje de error cuando se encuentra que una persona NO se enceuntra habilitado para el subsidio de vivienda
     */
    public static final String KEY_NO_HABILITADO_SUBSIDIO = "Fovis.Postulaciones.Persona.No.Habilitado.Subsidio";
    /**
     * Constante para el mensaje de error cuando se encuentra que una persona No existen ciclos de asignacion vigentes con el periodo de
     * postulación abierto
     */
    public static final String KEY_NO_CICLOS_ASIGNACION_VIGENTE = "Fovis.Postulaciones.Persona.No.Ciclo.Asignacion.Vigente";
    /**
     * Constante para el mensaje de error cuando la sumatoria de los valores de ahorro previo ses inferior al minimo parametrizado
     */
    public static final String KEY_SUMATORIA_AHORRO_PREVIO_INFERIOR_MINIMO_PARAMETRIZADO = "Fovis.Postulaciones.Persona.Sumatoria.Ahorro.Previo.Inferior.Minimo.Parametrizado";
    /**
     * Constante para el mensaje de error cuando el jefe de hogar registra un padre en el formulario distinto al que registra en la base de
     * datos
     */
    public static final String KEY_PADRE_FORMULARIO_DISTINTO_BD = "Fovis.Postulaciones.Jefe.Hogar.Padre.Distinto.Bd";
    /**
     * Constante para el mensaje de error cuando el jefe de hogar registra una madre en el formulario distinta a la que registra en la base
     * de datos
     */
    public static final String KEY_MADRE_FORMULARIO_DISTINTA_BD = "Fovis.Postulaciones.Jefe.Hogar.Madre.Distinta.Bd";
    /**
     * Constante para el mensaje de error cuando el jefe de hogar registra un conyuge en el formulario distinto al que registra en la base
     * de datos
     */
    public static final String KEY_CONYUGE_FORMULARIO_DISTINTO_BD = "Fovis.Postulaciones.Jefe.Hogar.Conyuge.Distinto.Bd";
    /**
     * Llave del mensaje persona no es mayor de 15 años en formulario Fovis (No puede ser conyuge).
     */
    public static final String KEY_PERSONA_NO_MAYOR_15_FOVIS = "Fovis.Postulaciones.Persona.PersonaNoMayor15";
    /**
     * Constante para el mensaje de error cuando el jefe de hogar no puede ser cabeza de hogar por ser conyuge activo en uno de sus grupos
     * familiares
     */
    public static final String KEY_JEFE_HOGAR_INVALIDO_CABEZA_FAMILIA = "Fovis.Postulaciones.Jefe.Hogar.Invalido.Cabeza.Familia";
    /**
     * Constante para el mensaje de error cuando el jefe de hogar no puede ser cabeza de hogar por tener un conyuge activo en uno de sus
     * grupos familiares
     */
    public static final String KEY_JEFE_HOGAR_INVALIDO_CABEZA_FAMILIA_CON_CONYUGE = "Fovis.Postulaciones.Jefe.Hogar.Invalido.Cabeza.Familia.Con.Conyuge";
    /**
     * Constante para el mensaje de error cuando se encuentra una Persona previamente registrada con otro tipo / número de documento de
     * identidad
     */
    public static final String KEY_PERSONA_EXISTE_OTROS_PARAMETROS = "Fovis.Postulaciones.Persona.Registrada.No.tipo.Numero";
    /**
     * Constante para el mensaje de error cuando se encuentra que una persona tiene activo un subsidio en un grupo familiar distinto al del
     * jefe de hogar
     */
    public static final String KEY_PERSONA_SUBSIDIO_OTRO_GRUPO_FAMILIAR = "Fovis.Postulaciones.Persona.Subsidio.Activo.Otro.Grupo.Familiar";
    /**
     * Constante para el mensaje de error cuando se encuentra que una persona está asociada a una solicitud en Proceso.
     */
    public static final String KEY_PERSONA_SOLICITUD_EN_PROCESO = "Fovis.Postulaciones.Persona.SolicitudEnProceso";
    /**
     * Constante que representa el nombre del parametro FOVIS
     */
    public static final String NOMBRE_PARAMETRO_FOVIS = "nombreParametroFovis";

    /**
     * Constante para el mensaje de error cuando se intenta registrar la novead sin fechas
     */
    public static final String KEY_NOVEDAD_SIN_FECHAS = "novedades.persona.sinFechas";

    /**
     * Indica el mensaje: <br>
     * No tiene por lo menos un grupo familiar con mas de un beneficiario que en el campo Estado con respecto al afiliado
     * principal tenga el valor Activo.
     */
    public static final String KEY_MSG_GRUPO_FAMILIAR_SIN_BENEF_ACTIVO = "novedades.persona.grupoFamiliarMasUnBeneficiarioActivo";
    /**
     * Constante para el mensaje de error cuando se encuentra que la persona registra mal uso de servicios o subsidios de
     * la caja de compensacion familiar
     */
    public static final String KEY_PERSONA_REGISTRA_MAL_USO_SERVICIOS = "Novedades.fovis.persona.registrosDeMalUsoServicios";
    /**
     * Constante para el mensaje de error cunado se encuentra que la persona ha presentado informacion fraudulenta o
     * inconsistente ante la caja de compensacion
     */
    public static final String KEY_PERSONA_REGISTRA_INFORMACION_FRAUDULENTA = "Novedades.fovis.persona.registrosDeInformacionFraudulenta";
    /**
     * Constante para el mensaje de error cuando se encuentra que la solicitud de novedad de desafiliación del jefe de hogar
     * esta relacionada a la novedad FOVIS en curso
     */
    public static final String KEY_JEFE_HOGAR_DESAFILIACION_EN_NOVEDAD_EN_CURSO = "Novedades.fovis.jefeHogar.DesafiliacionEnNovedadEnCurso";
    /**
     * Constante para el mensaje de error cuando se encuentra que una persona es beneficiario activo de un afiliado principal
     * distinto al referenciado
     */
    public static final String KEY_BENEFICIARIO_ACTIVO_AFILIADO_DISTINTO = "Novedades.fovis.persona.BeneficiarioActivoAfiliado";
    /**
     * Constante para el mensaje de error cuando un hogar no se encuentra en algun estado requerido en determinada validacion
     */
    public static final String KEY_ESTADO_HOGAR_INVALIDO = "Novedades.fovis.hogar.EstadoInvalido";
    /**
     * Constante para el mensaje de error cuando una persona ya se encuentra registrada en sistema
     */
    public static final String KEY_TIPO_NUMERO_IDENTIFICACION_EXISTEN = "Novedades.fovis.persona.tipoNumeroIdentificacionRegistrados";
    /**
     * Constante para el mensaje de error cuando una solicitud de postulacion no se encuentra en estado RECHAZADA
     */
    public static final String KEY_SOLICITUD_POSTULACION_NO_RECHAZADA = "Novedades.fovis.solicitudPostulacion.PostualcionNoRechazada";
    /**
     * Constante para el mensaje de error cuando no se encuentran ciclos de asignacion vigentes en el sistema
     */
    public static final String KEY_NO_EXISTEN_CICLOS_ASIGNACION_VIGENTES = "Novedades.fovis.noExistenCiclosAsignacionVigentes";
    /**
     * Constante para el mensaje de error cuando una persona ha sido beneficiada con subsidio FOVIS
     */
    public static final String KEY_PERSONA_BENEFICIADA_SUBSIDIO_FOVIS = "Novedades.fovis.persona.BeneficiadaSubsidioFOVIS";
    /**
     * Constante para el mensaje de eror cuando la postulacion no tiene la modalidad de "Adquisición vivienda nueva urbana"
     */
    public static final String KEY_MODALIDAD_INVALIDA_FOVIS_64 = "Novedades.fovis.postualcion.ModalidadInvalidaFovis64";
    /**
     * Constante para el mensaje de error cuando una persona (Jefe de hogar o Beneficiario) no se encuentra activa respecto
     * al hogar
     */
    public static final String KEY_PERSONA_NO_ACTIVA_HOGAR = "Novedades.fovis.persona.NoActiva";
    /**
     * Constante para el mensaje de error cuando el hogar no presenta un integrante tipo conyuge en estado activo
     */
    public static final String KEY_HOGAR_NO_TIENE_CONYUGE_ACTIVO = "Novedades.fovis.hogar.NoTieneConyugeActivo";
    /**
     * Constante para el mensaje de error cuando el hogar registra integrantes tipo MADRE o PADRE en cualquier estado
     * respecto al hogar
     */
    public static final String KEY_HOGAR_REGISTRA_MADRE_O_PADRE = "Novedades.fovis.hogar.RegistraMadreOPadre";
    /**
     * Constante para el mensaje de error cuendo el hogar registra mas de un integrante tipo PADRE_MADRE_HOGAR en cualquier estado
     * respecto al hogar
     */
    public static final String KEY_CANTIDAD_PADRES_MADRES_ADOPTANTES_HOGAR_INVALIDA = "Novedades.fovis.hogar.CantidadPadresMadresAdoptantesHogarInvalida";
    /**
     * Constante para el mensaje de error cuando el hogar registra integrantes tipo MADRE en cualquier estado
     * respecto al hogar
     */
    public static final String KEY_HOGAR_REGISTRA_MADRE = "Novedades.fovis.hogar.RegistraMadre";
    /**
     * Constante para el mensaje de error cuando el hogar registra integrantes tipo PADRE en cualquier estado
     * respecto al hogar
     */
    public static final String KEY_HOGAR_REGISTRA_PADRE = "Novedades.fovis.hogar.RegistraPadre";
    /**
     * Constante para el mensaje de error cuando el hogar registra integrantes tipo PADRE_MADRE_ADOPTANTE en cualquier estado
     * respecto al hogar
     */
    public static final String KEY_HOGAR_REGISTRA_PADRE_MADRE_ADOPTANTE = "Novedades.fovis.hogar.RegistraPadreMadreAdoptante";
    /**
     * Constante para el mensaje de error cuando el hogar registra mas de un integrante tipo SUEGRO en cualquier estado
     * respecto al hogar
     */
    public static final String KEY_CANTIDAD_SUEGROS_HOGAR_INVALIDA = "Novedades.fovis.hogar.CantidadSuegrosHogarInvalida";
    /**
     * Constante para el mensaje de error cuando el hogar registra mas de 3 (tres) integrantes tipo ABUELO en cualquier estado
     * respecto al hogar
     */
    public static final String KEY_CANTIDAD_ABUELOS_HOGAR_INVALIDA = "Novedades.fovis.hogar.CantidadAbuelosHogarInvalida";
    /**
     * Constante para el mensaje de error cuando el hogar registra mas de 3 (tres) integrantes tipo BISABUELO en cualquier estado
     * respecto al hogar
     */
    public static final String KEY_CANTIDAD_BISABUELOS_HOGAR_INVALIDA = "Novedades.fovis.hogar.CantidadBisabuelosHogarInvalida";
    /**
     * Constante para el mensaje de error cuando la persona se encuentra como beneficiario activo de cualquier afiliado
     */
    public static final String KEY_BENEFICIARIO_ACTIVO = "Novedades.fovis.persona.BeneficiarioActivo";
    /**
     * Constante para el mensaje de error cuando la persona se encuentra fallecida con una novedad de retiro (Entidades Externas)
     */
    public static final String KEY_PERSONA_FALLECIDA_NOVEDAD_ENTIDADES_EXTERNAS = "Novedades.fovis.persona.FallecidaNovedadEntidadesExternas";
    /**
     * Constante para el mensaje de error cuando la persona se encuentra en estado Inhabilitado para subsidio FOVIS
     */
    public static final String KEY_PERSONA_INHABILITADA_SUBSIDIO_FOVIS = "Novedades.fovis.persona.InhabilitadaSubsidioFovis";
    public static final String KEY_PERSONA_INHABILITADA_SUBSIDIO_FOVIS_REVERSE = "Novedades.fovis.persona.InhabilitadaSubsidioFovis.reverse";
    /**
     * Constante para el mensaje de error cuando los ahorros asociados a una postulacion no se encuentran movilizados
     */
    public static final String KEY_AHORROS_MOVILIZADOS = "Novedades.fovis.postulacion.AhorrosMovilizados";
    /**
     * Contante para el mensaje de error cuando la fecha de asignacion de la solicitud es posterior a 6 de marzo de 2016
     */
    public static final String KEY_FECHA_ASIGNACION_PREVIA_7_MARZO_2016 = "Novedades.fovis.solicitud.FechaAsignacionPrevia07Mar2016";
    /**
     * Constante para el mensaje de error cuando la postulación no presenta ahorros y/o cesantias inmobilizadas
     */
    public static final String KEY_NO_AHORROS_CESANTIAS_INMOVILIZADAS = "Novedades.fovis.postulacion.NoPresentaAhorrosCesantiasInmovilizadas";
    /**
     * Constante para el mensaje de error cuando un hogar no se encuentra en estado : Asignado con primera prórroga, Asignado con segunda
     * prórroga,
     * Pendiente de aprobación prórroga, Subsidio legalizado, Subsidio desembolsado, Hogar renunció a subsidio asignado, Restituido sin
     * sanción, Rechazado,
     * Hogar desistió de la postulación, Subsidio con anticipo desembolsado, Subsidio reembolsado
     */
    public static final String KEY_ESTADO_HOGAR_INVALIDO_FOVIS_71 = "Novedades.fovis.hogar.EstadoInvalido71";
    /**
     * Constante para el mensaje de error cuando un hogar no se encuentra en estado : Subsidio con anticipo desembolsado, Subsidio
     * desembolsado
     */
    public static final String KEY_ESTADO_HOGAR_INVALIDO_FOVIS_58 = "Novedades.fovis.hogar.EstadoInvalido58";
    /**
     * Constante para el mensaje de error cuando un hogar no se encuentra en estado : Asignado sin prórroga, Asignado con primera prórroga,
     * Asignado con segunda prórroga,
     * Pendiente de aprobación prórroga, Subsidio legalizado, Subsidio con anticipo desembolsado, Subsidio desembolsado
     */
    public static final String KEY_ESTADO_HOGAR_INVALIDO_FOVIS_69 = "Novedades.fovis.hogar.EstadoInvalido69";
    /**
     * Constante para el mensaje de error cuando un hogar no se encuentra en estado : Suspendido por cambio de año, Hogar desistió de la
     * postulación, Rechazado
     */
    public static final String KEY_ESTADO_HOGAR_INVALIDO_FOVIS_68 = "Novedades.fovis.hogar.EstadoInvalido68";
    /**
     * Constante para el mensaje de error cuando una persona se encuentra sancionada desde Fovis
     */
    public static final String KEY_PERSONA_SANCIONADA_FOVIS = "Novedades.fovis.persona.SancionadaFovis";
    /**
     * Constante para el mensaje de error cuando el hogar no tiene estado asignado
     */
    public static final String KEY_ESTADO_HOGAR_NO_ASIGNADO = "Novedades.fovis.hogar.NoEstadoAsignado";
    /**
     * Constante para el mensaje de error cuando el hogar no tiene estado : Asignado sin prórroga, Asignado con primera prórroga, Asignado
     * con segunda prórroga, Subsidio legalizado,
     * Subsidio con anticipo desembolsado, Subsidio desembolsado
     */
    public static final String KEY_ESTADO_HOGAR_INVALIDO_FOVIS_56 = "Novedades.fovis.hogar.NoEstadoAsignadoFovis56";
    /**
     * Constante para el mensaje de error cuando el hogar no tiene estado Asignado con segunda prórroga
     */
    public static final String KEY_ESTADO_HOGAR_INVALIDO_FOVIS_55 = "Novedades.fovis.hogar.EstadoInvalido55";
    /**
     * Constante para el mensaje de error cuando el hogar no tiene estado Asignado con primera prórroga
     */
    public static final String KEY_ESTADO_HOGAR_INVALIDO_FOVIS_54 = "Novedades.fovis.hogar.EstadoInvalido54";
    /**
     * Constante para el mensaje de error cuando el hogar no tiene estado Asignado con primera prórroga
     */
    public static final String KEY_ESTADO_HOGAR_INVALIDO_FOVIS_53 = "Novedades.fovis.hogar.EstadoInvalido53";
    /**
     * Constante para el mensaje de error cuando el hogar no tiene estado Postulado
     */
    public static final String KEY_ESTADO_HOGAR_NO_POSTULADO = "Novedades.fovis.hogar.NoPostulado";
    /**
     * Constante para el mensaje de error cuando el hogar no tiene estado : Postulado, Hábil o Hábil segundo año
     */
    public static final String KEY_ESTADO_HOGAR_INVALIDO_FOVIS_48 = "Novedades.fovis.hogar.EstadoInvalido48";
    /**
     * Constante para el mensaje de error cuando el jefe de hogar no se encuentra en estado activo
     */
    public static final String KEY_JEFE_HOGAR_NO_ACTIVO = "Novedades.fovis.jefeHogar.NoActivo";
    public static final String KEY_JEFE_HOGAR_NO_ACTIVO_REVERSE = "Novedades.fovis.jefeHogar.NoActivo.reverse";
    /**
     * Constante para el mensaje de error cuando el hogar se encuentra inhabilitado para subsidio FOVIS
     */
    public static final String KEY_HOGAR_INHABILITADO_SUBSIDIO_FOVIS = "Novedades.fovis.hogar.InhabilitadoSubsidioFovis";
    /**
     * Constante para el mensaje de error cuando la persona no presenta excepciones de validacion (Validaciones Fovis Persona v11)
     */
    public static final String KEY_PERSONA_NO_PRESENTA_EXCEPCIONES_AFILIACION = "Fovis.Postulaciones.Persona.noPresentaExepcionesAfiliacion";
    /**
     * Constante para el mensaje de erro cuando la persona esta registrada como beneficiaria de subsidio de vivienda familiar
     * con la modalidad "Mejoramiento de vivienda saludable
     */
    public static final String KEY_PERSONA_BENEFICIARIA_VIVIENDA_SALUDABLE = "Fovis.Postulaciones.Persona.BeneficiariaViviendaSaludable";
    
    public static final String KEY_PERSONA_BENEFICIARIA_ADQUISICION = "Fovis.Postulaciones.Persona.BeneficiariaAdquisicion";
    
    public static final String KEY_PERSONA_BENEFICIARIA_MEJORAMIENTO = "Fovis.Postulaciones.Persona.BeneficiariaMejoramiento";
    
    /**
     * Constante para el mensaje de error cuando la persona ya cuenta uno novedad de persona en proceso
     */
    public static final String KEY_PERSONA_TIENE_NOVEDAD_PERSONA_EN_CURSO = "novedades.persona.tieneNovedadPersonaEnCurso";   

    public static final String KEY_ESTADO_HOGAR_RECHAZADO_REVERSE = "Novedades.fovis.hogar.estado.rechazado.reverse";
   
    /**
     * Mensaje de error cuando la persona es mayor al jefe de hogar
     */
    public static final String KEY_PERSONA_MAYOR_JEFE = "fovis.postulaciones.persona.mayor.jefe";

    /**
     * Mensaje de error cuando la persona es menor al jefe de hogar
     */
    public static final String KEY_PERSONA_MENOR_JEFE = "fovis.postulaciones.persona.menor.jefe";

    /**
     * Mensaje de error que indica que el hogar validado tiene registrada una novedad de actualizacion de ajuste al valor de SFV
     */
    public static final String KEY_MSG_HOGAR_REGISTRA_NOVEDAD_AJUSTE_SFV = "Novedades.fovis.hogar.registroNovedadActualizaSFV";
    
    /**
     * Mensaje de error que indica que el afiliado no cumple con las restricciones respecto a su estado con respecto a la CCF
     */
    public static final String KEY_MSG_AFILIADO_ESTADO_INVALIDO_CCF = "novedades.persona.estadoInvalidoAfiliado";
    
    /**
     * Mensaje de error que indica que el afiliado tiene una solicitud de afiliación en curso RADICADA
     */
    //public static final String KEY_MSG_SOLICITUD_AFILIACION_RADICADA_EN_CURSO = "Afiliacion.persona.solicitudAfiliacionRadicadaEnCurso";
    
    /**
     * Mensaje de error que indica que el afiliado tiene una solicitud de afiliación en curso PRE-RADICADA
     */
    //public static final String KEY_MSG_SOLICITUD_AFILIACION_PRE_RADICADA_EN_CURSO = "Afiliacion.persona.solicitudAfiliacionPreRadicadaEnCurso";
    
    /**
     * Mensaje de error que indica que el afiliado tiene una solicitud de afiliación en curso RADICADA
     */
    //public static final String KEY_MSG_SOLICITUD_NOVEDAD_RADICADA_EN_CURSO = "Afiliacion.persona.solicitudAfiliacionNovedadEnCurso";
    /**
     * Nombre del parámetro estado beneficiario afiliado activo.
     */
    public static final String ESTADO_BENEFICIARIO_ACTIVO = "estadoBeneficiarioAfiliadoActivo";
    /**
     * Nombre del parámetro estado beneficiario afiliado inactivo.
     */
    public static final String ESTADO_BENEFICIARIO_INACTIVO = "estadoBeneficiarioAfiliadoInactivo";
    
    /**
     * constante KEY_GRUPO_FAMILIAR_SIN_BENEFICIARIOS
     */
    //public static final String KEY_BENEFICIARIO_INACTIVO_FECHA_RETIRO = "novedades.beneficiario.inactivoFechaRetiro";
    
    /**
     * constante KEY_TRABAJADOR_INACTIVO_FECHA_RETIRO
     */
    //public static final String KEY_TRABAJADOR_INACTIVO_FECHA_RETIRO = "novedades.trabajador.inactivoFechaRetiro";

    public static final String KEY_PERSONA_VETERANO = "fovis.postulaciones.persona.veterano";

    public static final String KEY_FECHA_NACIMIENTO_BENEFICIARIO = "fechaNacimientoBeneficiario";

    /**
     * Constante para el mensaje de error cuando el hogar no tiene estado : Asignado sin prórroga Asignado con primera prórroga Asignado con segunda prórroga  Pendiente de aprobación prórroga "
     * 		subsidio o Legalizado Subsidio con anticipo desembolsado
     */
    public static final String KEY_ESTADO_HOGAR_INVALIDO_FOVIS_132 = "Novedades.fovis.hogar.EstadoInvalidoNov132";
}
