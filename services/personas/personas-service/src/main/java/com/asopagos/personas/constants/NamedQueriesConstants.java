package com.asopagos.personas.constants;

/**
 * <b>Descripción:</b> Clase que contiene las constantes con los nombres de los
 * NamedQueries del servicio
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class NamedQueriesConstants {

    /**
     * Busca a un afiliado por empleador y estado
     */
    public static final String BUSCAR_AFILIADO_EMPLEADOR_ESTADO = "Personas.buscar.afiliado.empleador.estado";
    /**
     * Busca a un afiliado por empleador y estado
     */
    public static final String BUSCAR_AFILIADO_ESTADO = "Personas.buscar.afiliado.estado";

    /**
     * Busca a un afiliado
     */
    public static final String BUSCAR_AFILIADO = "Persona.buscar.afiliado";

    /**
     * Buscar una persona por
     */
    public static final String BUSCAR_PERSONA_ID = "Persona.buscar.id";

    /**
     * Buscar unicamente la persona sin detalles
     */
    public static final String BUSCAR_PERSONA_SOLO_ID = "Persona.buscar.solo.id";

    /**
     * Busca persona detalle pro tipo y numero de identificacion de una persona
     */
    public static final String BUSCAR_PERSONADETALLE_TIPO_NUMERO_IDENTIFICACION = "Personas.buscar.personadetalle.tipo.numero.identificacion";

    /**
     * Consulta una Ubicacion por identificación.
     */
    public static final String CONSULTAR_UBICACION_POR_ID = "Novedades.Ubicacion.consultarUbicacionId";

    /**
     * Consulta una Persona y Persona detalle por tipo y identificación.
     */
    public static final String CONSULTAR_PERSONA_TIPO_NUM_IDENTIFICACION = "Novedades.Persona.consultarPorTipoyNumeroId";

    public static final String CONSULTAR_PERSONA_TIPO_NUM_IDENTIFICACION_NATIVA = "Novedades.Persona.consultarPorTipoyNumeroId.nativa";

    /**
     * Consulta una Persona y Persona detalle por número de identificación.
     */
    public static final String CONSULTAR_PERSONA_TIPO_Y_NUM_IDENTIFICACION = "Persona.consultarPorTipoYNumeroId";
    
    /**
     * Consulta una Persona y Persona detalle por número de identificación.
     */
    public static final String CONSULTAR_PERSONA_TIPO_Y_NUM_IDENTIFICACION_NATIVA = "Persona.consultarPorTipoYNumeroId.nativa";
    
    /**
     * Consulta una Persona y Persona detalle por número de identificación.
     */
    public static final String CONSULTAR_PERSONA_TIPO_Y_NUM_IDENTIFICACION_EMP_NATIVA = "Persona.consultarPorTipoYNumeroId.RolAfiliado.nativa";
    
    

    /**
     * Consulta el ID de una Persona y Persona detalle por número de identificación.
     */
    public static final String CONSULTAR_ID_PERSONA_TIPO_Y_NUM_IDENTIFICACION = "Persona.consultarIdPorTipoYNumeroId";

    /**
     * Consulta todos los datos de una persona dentro de la tabla de sumatoria de salarios
     */
    public static final String CONSULTAR_PERSONA_SUMATORIA_SALARIOS_DETALLE = "Persona.consultar.datos.Persona.SumatoriaSalarios.Detalle";


    /**
     * Consulta una Persona y Persona detalle por número de identificación.
     */
    public static final String CONSULTAR_PERSONA_NUM_IDENTIFICACION = "Persona.consultarPorNumeroId";
    
    /**
     * Consulta una Persona y Persona detalle por número de identificación.
     */
    public static final String CONSULTAR_PERSONA_NUM_IDENTIFICACION_NATIVA = "Persona.consultarPorNumeroId.nativa";
    
    /**
     * Consulta una Persona y Persona detalle por número de identificación.
     */
    public static final String CONSULTAR_PERSONA_NUM_IDENTIFICACION_EMP_NATIVA = "Persona.consultarPorNumeroId.RolAfiliado.nativa";
    
    

    /**
     * Consulta una Persona y Persona detalle por número de identificación.
     */
    public static final String CONSULTAR_PERSONA_NOMBRE_APELLIDO = "Persona.consultarPorNombreApellido";

    /**
     * Consulta una Persona y Persona detalle por número de identificación.
     */
    public static final String CONSULTAR_PERSONA_NOMBRE_APELLIDO_FECHA_NACI = "Persona.consultarPorNombreApellidoFechaNacimiento";

    /**
     * Consulta Identificadores de Personas a las que se venció certificado de Escolaridad.
     */
    public static final String CONSULTAR_VENCIMIENTO_CERTIFICADO_ESCOLARIDAD = "Novedades.Persona.consultarVencimientoCertificadoEscolaridad";

    /**
     * Consulta Beneficiarios a Inactivar Certificado Escolaridad
     */
    public static final String CONSULTAR_BENEFICIARIOS_INACTIVAR_CERTIFICADO = "Novedades.Beneficiario.consultarBeneficiariosInactivar";

    /**
     * Consulta las Personas a Inactivar por fallecimiento.
     */
    public static final String CONSULTAR_PERSONA_INACTIVAR_FALLECIMIENTO = "Novedades.Persona.consultarPersonasInactivFallecido";

    /**
     * Consulta las personas a inactivar Cta Web por Retiro o inactivas con servicios sin afiliacion inactivos.
     */
    public static final String CONSULTAR_PERSONAS_RETIRO = "Novedades.Persona.consultarPersonasRetiro";

    /**
     * Consulta tipo y número de Identificación asociado a una serie de Personas.
     */
    public static final String NOVEDADES_PERSONA_CONSULTAR_NOMBRE_USUARIO = "Novedades.Persona.consultarNombreUsuario";

    /**
     * Consulta la condicion de invalidez asociada a una persona por tipo y numero de identificacion.
     */
    public static final String CONSULTAR_CONDICION_INVALIDEZ_POR_PERSONA = "Novedades.Persona.consultarCondicionInvalidezPorPersona";
    /**
     * Consulta la persona detalle por numero y tipo de identificacion
     */
    public static final String CONSULTAR_PERSONA_DETALLE = "Personas.persona.detalle.tipo.numero.identificacion";
    /**
     * Consulta el detalle de una persona solo por el numero de identificacion de la persona
     */
    public static final String CONSULTAR_PERSONA_DETALLE_NUM_IDENTIFICACION = "Persona.detalle.consultarPorNumeroId";
    /**
     * Consulta Persona Detalle por nombre y apellido
     */
    public static final String CONSULTAR_PERSONA_DETALLE_NOMBRE_APELLIDO = "Persona.detalle.consultarPorNombreApellido";

    /**
     * Consulta la persona detalle asociada con el tipo y numero de identificación dado
     */
    public static final String CONSULTAR_PERSONA_DETALLE_TIPO_NUM_IDENTIFICACION = "Novedades.Persona.detalle.consultarPorTipoyNumeroId";

    /**
     * Consulta la fecha de nacimiento de una persona dados su número y tipo de identificación
     */
    public static final String BUSCAR_FECHA_NACIMIENTO_PERSONA = "Persona.buscar.fecha.nacimiento";

    /**
     * Representa la consulta de integrantes de hogar con el salario actualizado asociados a jefe de hogar y postulacion 
     */
    public static final String CONSULTAR_INTEGRANTE_HOGAR_SALARIO_POR_JEFE_POSTULACION = "fovis.consultar.integrantesHogar.salario.info.jefe.postulacion";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de
     * integrantes de un hogar, por identificación del jefe de hogar
     */
    public static final String CONSULTAR_INTEGRANTEHOGAR_POR_JEFEHOGAR = "Fovis.Consultar.IntegranteHogar.JefeHogar";

    /**
     * Constante con el nombre de la consulta que obtiene un jefe de hogar por
     * tipo y número de documento
     */
    public static final String CONSULTAR_JEFEHOGAR_POR_IDENTIFICACION = "Fovis.Consultar.JefeHogar.Identificacion";

    /**
     * Constante con el nombre de la consulta que obtiene un registro de la tabla <code>PersonaDetalle</code>, por identificación de la
     * persona asociada
     */
    public static final String CONSULTAR_PERSONADETALLE = "Fovis.Consultar.PersonaDetalle.Identificacion";

    /**
     * Constante con el nombre de la consulta que obtiene el estado en la caja de la persona
     */
    public static final String CONSULTAR_ESTADO_CAJA_PERSONA_TIPO_NRO_DOC = "Persona.estadoCaja.tipo.numero.identificacion";

    /**
     * Constante con el nombre de la consulta que obtiene el estado en la caja de la persona
     */
    public static final String CONSULTAR_ESTADO_CAJA_PERSONA_TIPO_NRO_DOC_EMPLEADOR = "Persona.estadoCaja.tipo.numero.identificacion.idEmpleador";

    /**
     * Constante para consultar un integrante de Hogar por Persona
     */
    public static final String FOVIS_CONSULTAR_INTEGRANTE_POR_PERSONA = "fovis.IntegranteHogar.consultarExistePorPersona";

    /**
     * Consulta un afiliado por tipo y número de Identificación.
     */
    public static final String CONSULTAR_AFILIADO_TIPO_NUMERO_ID = "Afiliado.consultaAfiliadoTipoNumId";

    /**
     * Consulta los beneficiarios por tipo y nro documento de la persona
     */
    public static final String BUSCAR_BENEFICIARIO_TIPO_NRO_DOCUMENTO_ESTADO_JEFE_HOGAR = "Beneficiario.buscar.por.tipo.nroIdentificacion.estado.y.jefehogar";

    /**
     * Constante con el nombre de la consulta que obtiene la información de una persona jefe de hogar, por identificador del jefe de hogar
     */
    public static final String CONSULTAR_PERSONA_JEFEHOGAR = "Fovis.Consultar.Persona.JefeHogar";

    /**
     * Constante con el nombre de la consulta que obtiene la información de una persona jefe de hogar, por identificador del jefe de hogar
     */
    public static final String CONSULTAR_PERSONA_POR_ID = "Persona.buscar.porID";

    /**
     * Constante con el nombre de la consulta que obtiene la información de una persona jefe de hogar, por identificador del jefe de hogar
     */
    public static final String CONSULTAR_PERSONA_POR_ID_FECHANACIMIENTO = "Persona.buscar.por.id.fechaNacimiento";

    /**
     * Constante con el nombre de la consulta que obtiene la información de una persona jefe de hogar, por identificador del jefe de hogar
     */
    public static final String CONSULTAR_PERSONA_POR_FECHANACIMIENTO = "Persona.buscar.por.fechaNacimiento";

    /**
     * Constante con el nombre de la consulta que obtiene una persona por tipo y número de identificación
     */
    public static final String CONSULTAR_PERSONA_POR_TIPO_NUMERO_IDENTIFICACION = "Persona.buscar.por.tipo.numero.identificacion";
    
    /**
     * Constante con el nombre de la consulta que obtiene una persona por razón social
     */
    public static final String CONSULTAR_PERSONA_POR_RAZONSOCIAL = "Persona.buscar.por.razonSocial";

    /**
     * Constante con el nombre de la consulta que obtiene una beneficiario detalle por el id de la personaDetalle
     */
    public static final String CONSULTAR_BENEFICIARIO_DETALLE_ID_PERSONADETALLE = "Beneficiario.BeneficiarioDetalle.consultarPorId";

    /**
     * Constante con el nombre de la consulta que obtiene el administrador o los administradores de subsidio que este asociados
     * con los filtros correspondientes.
     */
    public static final String CONSULTAR_ADMINISTRADORES_SUBSIDIO = "Persona.buscar.Administradores.Subsidio";

    /**
     * Constante con el nombre de la consulta que trae la ubicación de la persona
     */
    public static final String CONSULTAR_UBICACION_POR_ID_PERSONA = "Ubicacion.persona.consultarUbicacionPorIdPersona";

    /**
     * Busca el salario acumulativo de un afiliado en sus roles y por estado
     */
    public static final String BUSCAR_SALARIO_AFILIADO_ESTADO = "Personas.buscar.salario.afiliado.estado";

    /*
     * busca el beneficiarioDetalle asociado a la persona integrante del hogar
     */
    public static final String BUSCAR_BENEFICIARIO_DETALLE_POR_DATOS_INTEGRANTE_HOGAR_ESTADO = "Beneficiario.buscar.detalle.por.tipo.nroIdentificacion.estado.y.jefehogar";

    /**
     * Consulta que obtiene el jefe del hogar por los datos del integrante del hogar
     */
    public static final String BUSCAR_JEFE_HOGAR_POR_DATOS_PERSONA = "Fovis.Consultar.JefeHogar.por.Datos.Persona";
    
    /**
     * Constante que contiene la consulta para el numero de trabajadores activos del empleador
     */
    public static final String CONSULTAR_NUMERO_TRABAJADORES_EMPLEADOR = "Empleador.consultarTrabajadoresActivos.rolAfiliado";

    /**
     * Constante para la consulta de personas por tipo y lista de numeros identificacion
     */
    public static final String CONSULTAR_PERSONAS_TIPO_LISTA_NRO_IDENTIFICA = "Find.Persona.Detalle.Tipo.ListaNumeroId";
    
    /**
     * Constante para la consulta de la cabecera vista 360 persona
     */
    public static final String CONSULTAR_CABECERA_PERSONA_VISTA360 = "Persona.consultarCabecera.vista360";
    
	/**
	 * Constante para la consulta del id del empleador de una persona
	 */
	public static final String CONSULTAR_ID_EMPLEADOR_PERSONA = "Persona.consultarIdEmpleadorPersona.vista360";
	
	/**
	 * Constante para consultar los afiliados de una persona cuando es beneficiario
	 */
	public static final String CONSULTAR_AFILIADOS_PERSONA_BENEFICIARIO = "Persona.consultarAfiliadosPersonaBeneficiario.vista360";
   
	/**
     * Constante con el nombre de la consulta que obtiene el administrador o los administradores de subsidio que este asociados
     * con los filtros correspondientes, sin importar el medio de pago relacionado.
     */
	public static final String CONSULTAR_ADMINISTRADORES_SUBSIDIO_GENERAL = "Persona.buscar.Administradores.Subsidio.general";
	
	/**
	 * Constante para la consulta de los beneficiarios de un grupo familiar dado
	 */
	public static final String BUSCAR_BENEFICIARIOS_GRUPO_FAMILIAR = "Persona.buscar.beneficiariosGrupoFamiliar";
	
    /**
     * Consulta los grupos familiares relacionados con un afiliado principal dado.
     */
	public static final String OBTENER_GRUPOS_FAMILIARES_AFI_PPAL = "Persona.obtener.gruposFamiliaresAfiliadoPrincipal";

	/**
	 * Consulta la información detallada de un beneficiario dados sus datos de identificacion, el grupo y el parentezco con el afiliado principal 
	 */
	public static final String OBTENER_INFO_DETALLADA_BENEFICIARIO_GRUPO = "Persona.obtener.infoDetalladaBeneficiarioGrupo";
	
	/**
	 * Constante con el nombre de la consulta que obtiene la lista de aportantes empleadores registrados en la caja, de acuerdo a filtros de consulta
	 */
	public static final String CONSULTAR_PERSONA_APORTANTE_EMPLEADOR_CAJA = "Persona.Consultar.Aportante.Empleador.Caja";
	
	/**
	 * Constante con el nombre de la consulta que obtiene la lista de aportantes tipo persona registrados en la caja, de acuerdo a filtros de consulta
	 */
	public static final String CONSULTAR_PERSONA_APORTANTE_PERSONA_CAJA = "Persona.Consultar.Aportante.Persona.Caja";
	
	public static final String CONSULTAR_HISTORICO_ACTIVACION = "Persona.Consultar.Historico.Activacion";
	
	/**
	 * Consulta una Persona por tipo y número de Identificación dado un empleador
	 */
	public static final String CONSULTAR_PERSONA_TIPO_Y_NUM_IDENTIFICACION_VISTA_360_EMPLEADOR = "Persona.consultarPorTipoyNumeroIdParaVista360";
	
	/**
	 * Consulta una Persona por tipo y número de Identificación dado un empleador
	 */
	public static final String CONSULTAR_PERSONA_TIPO_Y_NUM_IDENTIFICACION_VISTA_360_EMPLEADOR_NATIVA = "Persona.consultarPorTipoyNumeroIdParaVista360.nativa";

    /**
     * Constante con el nombre de la consulta que obtiene el administrador o los administradores de subsidio que este asociados
     * con los filtros correspondientes.
     */
    public static final String CONSULTAR_ADMINISTRADORES_SUBSIDIO_MEDIO_PAGO_EFECTIVO = "Persona.buscar.Administradores.Subsidio.medioEfectivo";
    
    /**
     * Consulta una Persona y Persona detalle por número de identificación.
     */
    public static final String CONSULTAR_PERSONA_PARAMETROS = "Persona.consultar.parametros";
    
    /**
     * Consulta una Persona y Persona detalle por número de identificación.
     */
    public static final String CONSULTAR_PERSONA_ROLAFILIADO_PARAMETROS = "Persona.rolafiliado.consultar.parametros";
    
    public static final String CONSULTAR_NOMBRE_PAIS_RESIDENCIA = "Persona.consultar.nombrePaisResidencia";

    public static final String CONSULTAR_NOMBRE_RESGUARDO = "Persona.consultar.resguardo";

    public static final String CONSULTAR_NOMBRE_PUEBLO_INDIGENA = "Persona.consultar.puebloIndigena";
    
    public static final String CONSULTAR_INTEGRANTE_POSTULACION = "Persona.consultar.IntegranteHogarPostulacion";

    public static final String CONSULTAR_NOMBRE_PAIS = "Persona.consultar.nobmre.pais";

    public static final String CONSULTAR_ID_BENEFICIARIO = "Persona.consultar.id.beneficiario";

    public static final String CONSULTAR_PAIS_POR_CODIGO = "Persona.consultar.pais.porCodigo";

    public static final String CONSULTAR_PERSONA_PROCESO_OFFCORE = "Persona.consultar.proceso.offcore";

    public static final String CONSULTAR_DESPLIEGUE_COMPLETO = "Registro.despliegue.ambiente";

    public static final String PERSISTIR_DESPLIEGUE_COMPLETO = "Insercion.registro.despliegue.ambiente";

    public static final String CONSULTAR_ULTIMA_TARJETA_REGISTRADA = "Persona.consultar.ultimatarjetaregistrada";

    public static final String OBTENER_ADMIN_SUBSIDIO_DE_GRUPO_CON_MARCA = "Persona.consultar.admin.subsidio.grupo.con.marca";

    public static final String ACTUALIZAR_TARJETA_GRUPO_FAMILIAR = "Persona.actualizar.tarjeta.grupo.familiar";

    public static final String MOVER_DATA_PERSONA_EXCLUSION_SUMATORIA_SALARIO = "Persona.StoredProcedures.USP_UTIL_PersonaExclusionSumatoriaSalario_CoreToSubsidio";

}
