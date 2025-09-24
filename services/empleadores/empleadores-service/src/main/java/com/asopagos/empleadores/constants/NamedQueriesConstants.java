package com.asopagos.empleadores.constants;

/**
 * <b>Descripción:</b> Clase que contiene las constantes con los nombres de los
 * NamedQueries del servicio <b>Historia de Usuario:</b> Transversal
 *
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class NamedQueriesConstants {

    // HU-TRA-329 Consulta a un empleador a partir de la identificación de su
    // persona
    public static final String CONSULTAR_EMPLEADOR_PERSONA = "Empleadores.buscar.persona";

    // HU-TRA-329
    public static final String CONSULTAR_PERSONA_EMPLEADOR = "Empleadores.persona.buscarTodos";

    // HU-TRA-329
    public static final String CONSULTAR_EMPLEADOR = "Empleadores.buscarTodos";

    // HU-TRA-329
    public static final String CONSULTAR_EMPLEADOR_ID = "Empleadores.id.buscarTodos";

    // HU-TRA-329
    public static final String CONSULTAR_EMPLEADOR_POR_RAZON_SOCIAL = "Empleador.razonSocial.buscarTodos";
    public static final String CONSULTAR_EMPLEADOR_POR_RAZON_SOCIAL_PRE = "Empleador.razonSocial.buscarTodos_pre";
    public static final String CONSULTAR_EMPLEADOR_POR_RAZON_SOCIAL_NATIVA = "Empleador.razonSocial.buscarTodos.nativa";

    // HU-TRA-329
    public static final String CONSULTAR_EMPLEADOR_TIPO_INDENTIFICACION_NUMERO = "Empleador.tipoIdentificacion.numIdentificacion.buscarTodos";
    public static final String CONSULTAR_EMPLEADOR_TIPO_INDENTIFICACION_NUMERO_PRE = "Empleador.tipoIdentificacion.numIdentificacion.buscarTodos_pre";

    // HU-TRA-329
    public static final String CONSULTAR_EMPLEADOR_TIPO_INDENTIFICACION_NUMERO_DV = "Empleador.tipoIdentificacion.numIdentificacion.DV.buscarTodos";

    public static final String CONSULTAR_LISTA_EMPLEADORES_EN_RANGO_FECHA = "Empleadores.FechaInicio.FechaFin.Empleadores.GenerarLista";

    //Se encarga de traer todos lo beneficios activos de un empleadro
    public static final String CONSULTAR_LISTA_BENEFICIOS_EMPLEADORES = "Beneficios.empleadores.por.id";

    // HU-111-070
    public static final String CONSULTAR_LISTA_SOCIO_EMPLEADOR = "SocioEmpleador.idEmpleador.buscarTodos";

    // HU-111-070
    public static final String CONSULTAR_LISTA_ROL_CONTACTO_EMPLEADOR = "Empleador.rolesContacto.buscarTodos";

    // HU-111-070
    public static final String CONSULTAR_REPRESENTANTE_LEGAL_EMPLEADOR = "Empleador.representanteLegal.buscarTodos";
    public static final String CONSULTAR_UBICACION_REPRESENTANTE_LEGAL_EMPLEADOR = "Empleador.ubicacionRepresentanteLegal.buscarTodos";

    // HU-111-070
    public static final String CONSULTAR_REPRESENTANTE_LEGAL_SUPL_EMPLEADOR = "Empleador.representanteLegalSupl.buscarTodos";
    public static final String CONSULTAR_UBICACION_REPRESENTANTE_LEGAL_SUPL_EMPLEADOR = "Empleador.ubicacionRepresentanteLegalSupl.buscarTodos";

    // HU-111-070
    public static final String CONSULTAR_SUCURSALES_ROLES_CONTACTO_EMPLEADOR = "Empleador.consulta.sucursalRolContacto.buscarTodos";

    // HU-111-066
//	public static final String ELIMINAR_SOCIOS_EMPLEADOR_NO_PRESENTES = "Empleador.eliminarSociosEmpleador.noPresentes";
    public static final String CONSULTAR_ELIMINAR_SOCIOS_EMPLEADOR_NO_PRESENTES = "Empleador.consultar.eliminarSociosEmpleador.noPresentes";
    public static final String ELIMINAR_PERSONAS_LISTA = "Empleador.eliminarPersonas.listaPersonas";
    public static final String ELIMINAR_UBICACIONES_LISTA = "Empleador.eliminarUbicacion.listaUbicaciones";
//	public static final String ELIMINAR_SOCIOS_EMPLEADOR_POR_EMPLEADOR = "Empleador.eliminarSociosEmpleador.porEmpleador";
    public static final String CONSULTAR_ELIMINAR_SOCIOS_EMPLEADOR_POR_EMPLEADOR = "Empleador.consultar.eliminarSociosEmpleador.porEmpleador";
    public static final String CONSULTAR_SOCIOS_EMPLEADOR_POR_EMPLEADOR_PERSONA = "Empleador.consulta.sociosEmpleador.porEmpleadorPorPersona";

//	public static final String ACTUALIZAR_REPRESENTANTE_LEGAL = "Empleador.actualizar.representante.legal";
//	public static final String ACTUALIZAR_REPRESENTANTE_LEGAL_SUPLENTE = "Empleador.actualizar.representante.legal.suplente";
    // HU-111-066, HU-111-091, HU-111-093
//	public static final String ACTUALIZAR_RESPONSABLE_CAJA_COMPENSACION_EMPLEADOR = "Empleador.actualizar.responsableCajaCompensacion";
    public static final String CONSULTAR_ACTUALIZAR_RESPONSABLE_CAJA_COMPENSACION_EMPLEADOR = "Empleador.consultar.actualizar.responsableCajaCompensacion";
    public static final String CONSULTAR_RESPONSABLES_CAJA_COMPENSACION_EMPLEADOR = "Empleador.consultar.responsableCajaCompensacion";

    public static final String CONSULTAR_INFO_CONTACTO = "Empleador.consultar.informacionContacto";

    // Constante para consulta la clasificación de la última solicitud
    // afiliación empleador radicada
    public static final String CONSULTAR_ULTIMA_CLASIFICACION = "Empleador.consultar.ultimaClasificacion";

    /**
     * HU 496 Constante para consultar los empleadores a Inactivar Beneficio Ley
     * 1429.
     */
    public static final String NOVEDADES_EMPLEADOR_CONSULTAR_EMPLEADORES_INACTIVAR1429 = "Novedades.Empleador.consultarEmpleadoresInactivar1429";

    /**
     * HU 496 Constante para consultar los empleadores a Inactivar Beneficio Ley
     * 590.
     */
    public static final String NOVEDADES_EMPLEADOR_CONSULTAR_EMPLEADORES_INACTIVAR590 = "Novedades.Empleador.consultarEmpleadoresInactivar590";

    /**
     * HU 496 Constante para consultar los Beneficios a inactivar.
     */
    public static final String NOVEDADES_EMPLEADOR_CONSULTAR_BENEFICIOS_INACTIVAR = "Novedades.Empleador.consultarBeneficiosInactivar";

    /**
     * HU 496 Constante para consultar los empleadores que se encuentran
     * inactivos y tienen una fecha de Retiro asociada.
     */
    public static final String NOVEDADES_EMPLEADOR_CONSULTAR_EMPLEADORES_INACTIVARCTAWEB = "Novedades.Empleador.consultarEmpleadoresInactivarCtaWeb";

    /**
     * HU 496 Constante para consultar los datos de tipo y número de
     * identificación de una lista de Empleadores.
     */
    public static final String NOVEDADES_EMPLEADOR_CONSULTAR_TIPO_NUMERO_IDENTIFICACION = "Novedades.Empleador.consultarEmpleadoresTipoNumeroId";

    /**
     * Consulta el codigo CIIU por el id
     */
    public static final String BUSCAR_CODIGOCIIU_ID = "Novedades.Empleador.consultarCodigoCIIU.id";

    /**
     * Consulta la ARL por el id
     */
    public static final String BUSCAR_ARL_ID = "Novedades.Empleador.consultarARL.id";
    /**
     * Consulta los email asociados a los empleadores
     */
    public static final String CONSULTAR_EMAIL_EMPLEADORES = "Novedades.Empleador.consultarEmailEmpleadores";
    /**
     * Consulta por el tipo y número de identificación del empleador
     */
    public static final String CONSULTAR_EMPLEADOR_POR_TIPO_Y_NUMERO_IDENTIFICACION = "Empleador.consultar.por.tipo.identificacion.numero";

    /**
     * Consulta por el tipo y número de identificación del empleador
     */
    public static final String CONSULTAR_EMPLEADOR_POR_TIPO_Y_NUMERO_IDENTIFICACION_NATIVA = "Empleador.consultar.por.tipo.identificacion.numero.nativa";
    /**
     * Consulta por el tipo y número de identificación del empleador con el
     * estado discriminado
     */

    /**
     * Consulta EmpresaDesentralizada
     */
    public static final String CONSULTA_PREREGISTRO_DESCENTRALIZADA = "Empleador.consultar.empresa.descentralizada";
    
    public static final String CONSULTAR_EMPLEADOR_POR_TIPO_Y_NUMERO_IDENTIFICACION_ESTADO_DISCRIMINAR = "Empleador.consultar.por.tipo.identificacion.numero.discriminado";
    /**
     * Consulta por el ide la persona la empresa
     */
    public static final String CONSULTAR_EMPLEADOR_EMPRESA_POR_ID = "Empleador.consultar.empresa.persona.id";

    /**
     * Consulta un Beneficio por Tipo.
     */
    public static final String CONSULTAR_BENEFICIO_POR_TIPO = "Empleador.beneficio.consultarPorTipo";

    /**
     * Consulta si existe un empleador asociado a una empresa dada
     */
    //public static final String CONSULTAR_EMPLEADOR_ASOCIADO_EMPRESA_EXISTE = "Empleador.empleador.asociado.empresa.existe";
    /**
     * Consulta los empleadores que tengan solicitud rechazada, y se encuentren
     * activos.
     */
    public static final String CONSULTAR_EMPLEADOR_ACTIVO_CERO_TRABAJADORES = "Empleador.empleador.consultarActivoCeroTrabajadores";

    /**
     * Consulta por medio de una lista de id los empleadores asociados.
     */
    public static final String CONSULTAR_EMPLEADORES_LISTA_ID = "Empleador.consultarEmpleadoresId";

    /**
     * Consulta un empleador por id
     */
    public static final String CONSULTAR_EMPLEADOR_POR_ID = "Empleador.consultarEmpleadorId";
    /**
     * Consulta un rol contacto por id
     */
    public static final String CONSULTAR_ROL_CONTACTO_POR_ID = "Empleador.consultarRolContactoEmpleador";
    /**
     * Consulta la sucursal de la empresa por el id de forma masiva
     */
    public static final String CONSULTAR_SUCURSAL_EMPLEADOR_ID = "Empresa.consultarSucursalEmpresaEmpleadorId";

    /**
     * Consulta la sucursal de la empresa por el id de forma masiva
     */
    public static final String CONSULTAR_EMPRESA_POR_TIPODOC_NUMERODOC = "Empresa.consultarEmpresaPorPersona";

    /**
     * Consulta por el tipo y número de identificación del empleador
     */
    public static final String CONSULTAR_EMPRESA_POR_TIPO_DOC_NUMERO_DOC = "Empresa.consultar.por.tipo.identificacion.numero";

    /**
     * Consulta por el tipo y número de identificación del empleador
     */
    public static final String CONSULTAR_EMPRESA_POR_TIPO_DOC_NUMERO_DOC_NATIVA = "Empresa.consultar.por.tipo.identificacion.numero.nativa";

    /**
     * Consulta por el tipo y número de identificación del empleador con el
     * estado discriminado
     */
    public static final String CONSULTAR_EMPRESA_POR_TIPO_Y_NUMERO_IDENTIFICACION_ESTADO_DISCRIMINAR = "Empresa.consultar.por.tipo.identificacion.numero.discriminado";

    public static final String CONSULTAR_EMPRESA_EMPLEADOR_POR_RAZON_SOCIAL = "Empresa.razonSocial.buscarTodos";

    public static final String CONSULTAR_EMPRESA_POR_RAZON_SOCIAL_PRE = "Empresa.razonSocial.buscarTodos_pre";

    public static final String CONSULTAR_EMPRESA_EMPLEADOR_POR_RAZON_SOCIAL_NATIVA = "Empresa.razonSocial.buscarTodos.nativa";

    /**
     * Consulta la última clasificación de un empleador
     */
    public static final String CONSULTAR_ULTIMA_CLASIFICACION_EMPLEADOR = "Empleador.consultar.tipo.numero.identificacion.ultimaClasificacion";
    /**
     * Constante que contiene la consultar de la ubicación del representante
     * legal de la empresa
     */
    public static final String CONSULTAR_UBICACION_EMPRESA = "Empleador.consultar.ubicacion.empresa";
    /**
     * Constante que contiene la consulta de la ubicación de los tipo de
     * ubicacion del empleador
     */
    public static final String CONSULTAR_UBICACION_EMPRESA_POR_LISTA_TIPO_UBICACION = "Empleador.consultar.ubicacion.empresa.por.tipo.ubicacion";

    /**
     * Consultar Empresa por Id
     *
     */
    public static final String CONSULTAR_EMPRESA_ID = "Empresa.id.buscarTodos";

    /**
     * Constante que contiene la consulta de la ubicación del rol contacto
     * empleador
     */
    public static final String CONSULTAR_UBICACION_ROL_CONTACTO_EMPLEADOR = "Empleador.consultar.ubicacion.rol.contacto.empleador";

    /**
     * Constante ue contiene la consulta del beneficio que posee el empleador
     * actual
     */
    public static final String CONSULTAR_BENEFICIO_EMPLEADOR = "Empleador.consultarBeneficio.beneficioEmpleador";

    /**
     * Constante que contiene la consulta de los beneficios que ha tenido un
     * empleador
     */
    public static final String CONSULTAR_BENEFICIOS_EMPLEADOR = "Empleador.consultarBeneficios.beneficioEmpleador";

    /**
     * Consulta de las variables adicionalesdel empleador para la cabecera
     */
    public static final String CONSULTAR_VARIABLES_ADICIONALES_CABECERA_VISTA360 = "Empleador.consultarVariablesCabeceraVista360.empleador";

    /**
     * Consulta nativa de solicitudes empleador vista 360 para afiliaciones
     */
    public static final String CONSULTAR_SOLICITUDES_EMPLEADOR_VISTA360_AFILIACIONES = "Empleador.consultarSolicitudesAfiliacion.vista360";

    /**
     * Consulta nativa de solicitudes empleador vista 360 para novedades
     */
    public static final String CONSULTAR_SOLICITUDES_EMPLEADOR_VISTA360_NOVEDADES = "Empleador.consultarSolicitudesNovedad.vista360";

    /**
     * Consulta nativa de solicitudes empleador vista 360 para aportes
     */
    public static final String CONSULTAR_SOLICITUDES_EMPLEADOR_VISTA360_APORTES = "Empleador.consultarSolicitudesAportes.vista360";

    /**
     * Consulta los comunicados asociados a una solicitud del empleador
     */
    public static final String CONSULTAR_COMUNICADOS_SOLICITUD_EMPLEADOR_VISTA360 = "Empleador.consultarComunicadosSolicitudesEmpleador.vista360";

    /**
     * Consulta la cantidad de trabajdores activos que tiene un empleador dado
     * su id
     */
    public static final String CONSULTAR_CANTIDAD_TRABAJADORES_ACTIVOS_EMPLEADOR = "Empleador.consultarCantidadTrabajadoresActivosEmpleador";

    /**
     * Constante que representa la consulta de los valores para la clasificación
     * y el estado de la solicitud de un empleador dado
     */
    public static final String CONSULTAR_VARIABLES_ADICIONALES_RESTANTES_CABECERA_VISTA360 = "Empleador.consultar.variablesAdicionalesRestantesCabeceraVista360";

    /**
     * CONSTANTE QUE REPRESENTA LA CONSULTA DE EMPLEADORES POR TIPO Y NRO
     * DOCUMENTO , ESTADO CAJA O RAZON SOCIAL
     */
    public static final String CONSULTAR_EMPLEADOR_POR_TIPO_NRO_DOC_ESTADO_CAJA_RAZON_SOCIAL = "consultar.empleador.by.estado.caja.tipo.nro.identificacion.razon.social";

    /**
     * Constante que representa la consulta para conocer si tiene una entidad
     * pagadora asociada a una empresa y si es aportante a su vez
     */
    public static final String CONSULTAR_ES_ENTIDAD_PAGADORA = "consultar.entidad.pagadora"; 

    public static final String CONSULTAR_PERSONA_POR_ID_EMPLEADOR = "Empresa.consultar.persona.por.empleador";

    public static final String CONSULTAR_AFILIADOS_CERTIFICADO_MASIVO = "empleador.consultar.afiliados.certificadoMasivo";

    public static final String CONSULTAR_CERTIFIADOS_MASIVOS = "empleador.consultar.certificcados.masivos";

    public static final String CONSULTAR_FECHA_ULTIMA_ACTUALIZACION_AFILIACION = "Empleador.buscar.fecha.actualizacion";

}
