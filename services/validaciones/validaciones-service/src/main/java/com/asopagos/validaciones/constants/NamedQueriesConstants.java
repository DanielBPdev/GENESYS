package com.asopagos.validaciones.constants;

/**
 * <b>Descripción:</b> Clase que contiene las constantes con los nombres de los
 * NamedQueries del servicio
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
public class NamedQueriesConstants {

    /**
     * Nombre del named query de Empleador
     */
    public static final String NAMED_QUERY_EMPLEADOR = "SolicitudAfiliacionEmpleador.buscarPersona";
    /**
     * Named Query de validación empleado tiene cuenta web activa
     */
    public static final String NAMED_QUERY_CUENTA_ACTIVA = "SolicitudAfiliacionEmpleador.cuentaWeb";

    /**
     * Named query que consulta las novedades asociadas a un proceso.
     */
    public static final String BUSCAR_NOVEDAD_POR_TIPO_TRANSACCION = "Novedad.buscarNovedadInTipoTransaccion";
    /**
     * Named query que consulta las novedades asociadas a un proceso y a un tipo de novedad.
     */
    public static final String BUSCAR_NOVEDAD_POR_TIPO_TRANSACCION_TIPO_NOVEDAD = "Novedad.buscarNovedadInTipoTransaccionTipoNovedad";

    /**
     * Named query que consulta las novedades asociadas a un proceso.
     */
    public static final String BUSCAR_NOVEDAD_POR_PROCESO = "Novedad.buscarNovedadPorProceso";
    /**
     * Named query que consulta las novedades asociadas a un proceso y a un tipo de novedad.
     */
    public static final String BUSCAR_NOVEDAD_POR_TIPO_NOVEDAD_PROCESO = "Novedad.buscarNovedadPorTipoNovedadProceso";

    /**
     * Named query que consulta las validaciones para habilitar la novedad..
     */
    public static final String BUSCAR_NOVEDAD_POR_VALIDACIONES_NOVEDAD = "Novedad.buscarValidacionesNovedad";

    /**
     * Named query que consulta una persona por tipo y número de documento.
     */
    public static final String BUSCAR_PERSONA_POR_TIPO_NUMERO = "Validaciones.Persona.buscarPersonaPorTipoNumero";
    /**
     * Named query que consulta una persona por número de documento.
     */
    public static final String BUSCAR_PERSONA_POR_NUMERO = "Validaciones.Persona.buscarPersonaPorNumero";
    /**
     * Named query que consulta una persona nombre apellido y fecha de
     * nacimiento.
     */
    public static final String BUSCAR_PERSONA_POR_NOMBRE_APELLIDO_FECHA_NACIMIENTO = "Validaciones.Persona.buscarPersonaPorNombreApellidoFechaNacimiento";

    /**
     * Named query que consulta un rol asociado por tipo y número del afiliado,
     * por tipo y número del empleador, tipo de afiliado y estado.
     */
    public static final String BUSCAR_ROL_ASOCIADO_POR_TIPO_NUMERO_AFILIADO_EMPLEADOR_TIPO_ESTADO = "Validaciones.RolAfiliado.buscarRolAfiliadoPorTipoNumeroTipoAfiliacionEstadoEmpleador";

    /**
     * Named query que consulta un rol asociado por tipo y número del afiliado,
     * por tipo y número del empleador, tipo de afiliado y estado.
     */
    public static final String BUSCAR_ROL_ASOCIADO_POR_TIPO_AFILIADO_EMPLEADOR_TIPO_ESTADO = "Validaciones.RolAfiliado.buscarRolAfiliadoPorNumeroTipoAfiliacionEstadoEmpleador";
    /**
     * Named query que consulta una persona por tipo y número de documento
     * fallecido.
     */
    public static final String BUSCAR_PERSONA_POR_TIPO_NUMERO_FALLECIDO = "Validaciones.Persona.buscarPersonaPorTipoNumeroFallecido";
    /**
     * Named query que consulta una persona por tipo y número de documento
     * fallecido.
     */
    public static final String BUSCAR_PERSONA_POR_NUMERO_FALLECIDO = "Validaciones.Persona.buscarPersonaPorNumeroFallecido";
    /**
     * Named query que consulta una persona en empleador por tipo y número.
     */
    public static final String BUSCAR_EMPLEADOR_POR_TIPO_NUMERO_ESTADO = "Validaciones.Empleador.buscarEmpleadorPorTipoNumeroEstado";
    /**
     * Named query que consulta una persona en empleador por número.
     */
    public static final String BUSCAR_EMPLEADOR_POR_NUMERO_ESTADO = "Validaciones.Empleador.buscarEmpleadorPorNumeroEstado";
    /**
     * Named query que consulta un afiliado con un tipo de beneficiario y
     * estado.
     */
    public static final String BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_AFILIADO_TIPO_BENEFICIARIO_ESTADO = "Validaciones.Beneficiario.buscarBeneficiarioPorTipoBeneficiarioEstadoTipoNumeroAfiliado";
    /**
     * Named query que consulta un beneficiario por tipo de documento y numero para validar si ya esta asociado al proceso
     * HERMANO_HUERFANO_DE_PADRES
     */
    public static final String BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_HERMANO_HUERFANO_DE_PADRES = "Beneficiario.buscarBeneficiarioPorTipoNumeroHermanoHuerfano";
    
    /**
     * Named query que consulta un afiliado con un tipo de beneficiario y
     * estado.
     */
    public static final String BUSCAR_BENEFICIARIO_POR_NUMERO_AFILIADO_TIPO_BENEFICIARIO_ESTADO = "Validaciones.Beneficiario.buscarBeneficiarioPorTipoBeneficiarioEstadoTipoAfiliado";
    /**
     * Named query que consulta un afiliado por tipo y número y tipo de
     * afiliacion.
     */
    public static final String BUSCAR_ROL_AFILIADO_TIPO_NUMERO_TIPO_AFILIACION_ESTADO = "Validaciones.RolAfiliado.buscarRolAfiliadoPorTipoNumeroTipoAfiliacionEstado";
    /**
     * Named query que consulta un afiliado por número y tipo de afiliacion.
     */
    public static final String BUSCAR_ROL_AFILIADO_NUMERO_TIPO_AFILIACION_ESTADO = "Validaciones.RolAfiliado.buscarRolAfiliadoPorNumeroTipoAfiliacionEstado";
    /**
     * Named query que consulta beneficiario por número tipo de afiliado y
     * estados.
     */
    public static final String BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_TIPO_ESTADOS = "Validaciones.Beneficiario.buscarBeneficiarioPorTipoNumeroTipoEstados";
    
    public static final String BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_TIPO_ESTADOS_V2 = "Validaciones.Beneficiario.buscarBeneficiarioPorTipoNumeroTipoEstados_v2";
    /**
     * Named query que consulta beneficiario por número de afiliado y estados.
     */
    public static final String BUSCAR_BENEFICIARIO_POR_NUMERO_TIPO_ESTADOS = "Validaciones.Beneficiario.buscarBeneficiarioPorNumeroTipoEstados";

    public static final String BUSCAR_BENEFICIARIO_POR_NUMERO_TIPO_ESTADOS_V2 = "Validaciones.Beneficiario.buscarBeneficiarioPorNumeroTipoEstados_v2";
    /**
     * Named query que consulta un beneficiario por tipo de documento y numero y
     * tipo beneficiario
     */
    public static final String BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_TIPO_BENEFICIARIO_ESTADO = "Validaciones.Beneficiario.buscarBeneficiarioPorTipoNumeroTipoBeneficiarioEstado";
    /**
     * Named query que consulta un beneficiario por numero documento y tipo
     * beneficiario estado
     */
    public static final String BUSCAR_BENEFICIARIO_POR_NUMERO_TIPO_BENEFICIARIO_ESTADO = "Validaciones.Beneficiario.buscarBeneficiarioPorNumeroTipoBeneficiarioEstado";
    /**
     * Named query que consulta un afiliado por Id
     */
    public static final String BUSCAR_AFILIADO_POR_ID = "Validaciones.Afiliado.buscarAfiliadoPorId";
    /**
     * Named query que consulta un afiliado por tipo y numero de identificacion
     */
    public static final String BUSCAR_ROL_AFILIADO_POR_TIPO_Y_NUMERO_IDENTIFICACION = "Validaciones.RolAfiliado.buscarRolAfiliadoPorTipoNumeroIdentificacion";
    /**
     * Named query que consulta un afiliado por numero de identificacion
     */
    public static final String BUSCAR_ROL_AFILIADO_POR_NUMERO_IDENTIFICACION = "Validaciones.RolAfiliado.buscarRolAfiliadoPorNumeroIdentificacion";
    /**
     * Named query que consulta un beneficiario por tipo y numero de
     * identificacion
     */
    public static final String BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_DOCUMENTO = "Validaciones.Beneficiario.buscarBeneficiarioPorTipoNumeroDocumento";
    /**
     * Named query que consulta un socio por tipo y numero de identificacion
     */
    public static final String BUSCAR_SOCIO_EMPLEADOR_POR_SOCIO_EMPLEADOR_TIPO_SOLICITANTE = "Validaciones.SocioEmpleador.buscarSocioEmpleadorPorSocioEmpleadorTipoSolicitante";
    /**
     * Named query que consulta un socio por tipo y numero.
     */
    public static final String BUSCAR_SOCIO_EMPLEADOR_POR_SOCIO_NUMERO_EMPLEADOR_TIPO_SOLICITANTE = "Validaciones.SocioEmpleador.buscarSocioEmpleadorPorSocioNumeroEmpleadorTipoSolicitante";

    /**
     * Named query que consulta un conyuge socio por tipo y numero de identificacion
     */
    public static final String BUSCAR_CONYUGE_SOCIO_EMPLEADOR_POR_SOCIO_EMPLEADOR_TIPO_SOLICITANTE = "Validaciones.SocioEmpleador.buscarConyugeSocioEmpleadorPorSocioEmpleadorTipoSolicitante";
    /**
     * Named query que consulta conyuge un socio por tipo y numero.
     */
    public static final String BUSCAR_CONYUGE_SOCIO_EMPLEADOR_POR_SOCIO_NUMERO_EMPLEADOR_TIPO_SOLICITANTE = "Validaciones.SocioEmpleador.buscarSocioEmpleadorPorSocioNumeroEmpleadorTipoSolicitante";
    /**
     * Named query que consulta un afiliado por tipo y numero de identificacion
     */
    public static final String BUSCAR_AFILIADO_POR_TIPO_Y_NUMERO_IDENTIFICACION = "Validaciones.Afiliado.buscarAfiliadoPorTipoNumeroIdentificacion";
    /**
     * Named query que consulta un afiliado por numero de identificacion
     */
    public static final String BUSCAR_AFILIADO_POR_NUMERO_IDENTIFICACION = "Validaciones.Afiliado.buscarAfiliadoPorNumeroIdentificacion";
    /**
     * Named query que consulta el Conyuge Por Grupo Afiliado
     */
    public static final String BUSCAR_CONYUGE_GRUPO_AFILIADO = "Validaciones.Afiliado.buscarConyugePorGrupoAfiliado";
    /**
     * Named query que consulta un grupo familiar por el afiliado id
     */
    public static final String BUSCAR_GRUPOFAMILIAR_POR_AFILIADO_ID = "Validaciones.GrupoFamiliar.buscarGrupoFamiliarAfiliado";
    /**
     * Named query que se encarga se consultar un beneficiario por grupo
     * familiar, estado afiliacion y tipoBeneficiario
     */
    public static final String BUSCAR_BENEFICIARIO_GRUPOFAMILIAR_ESTADO_AFILIADO_TIPO_BENEFICIARIO = "Validaciones.Beneficiario.buscarBeneficiarioPorGrupoFamiliarEstadoAfiliadoTipoBeneficiario";
    /**
     * Named query que consulta un beneficiario por numero de identificacion
     */
    public static final String BUSCAR_BENEFICIARIO_POR_NUMERO_DOCUMENTO = "Validaciones.Beneficiario.buscarBeneficiarioPorNumeroDocumento";
    /**
     * Named query que se encarga de consultar un afiliado por tipo y numero de doucmento donde el empleador cumpla con un tipo de
     * solicitante.
     */
    public static final String BUSCAR_ROL_AFILIADO_POR_TIPO_NUMERO_CLASIFICACION_EMPLEADOR = "Validaciones.RolAfiliado.buscarPorTipoNumeroAfiliadoClasificacionSolicitudEmpleador";
    /**
     * Named query que se encarga de consultar un afiliado por numero de doucmento donde el empleador cumpla con un tipo de solicitante.
     */
    public static final String BUSCAR_ROL_AFILIADO_POR_NUMERO_CLASIFICACION_EMPLEADOR = "Validaciones.RolAfiliado.buscarPorNumeroAfiliadoClasificacionSolicitudEmpleador";
    /**
     * Named query que se encarga de consultar un beneficiario por tipo y numero de doucmento con invalidez.
     */
    public static final String BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_INVALIDEZ = "Validaciones.Beneficiario.buscarBeneficiarioPorTipoNumeroInvalidez";
    /**
     * Named query que se encarga de consultar un beneficiario por numero de doucmento con invalidez.
     */
    public static final String BUSCAR_BENEFICIARIO_POR_NUMERO_INVALIDEZ = "Validaciones.Beneficiario.buscarBeneficiarioPorNumeroInvalidez";
    /**
     * Named query que se encarga de consultar un afiliado por tipo y numero de doucmento y su parentesco con el empleador.
     */
    public static final String BUSCAR_ROL_AFILIADO_POR_TIPO_NUMERO_AFILIADO_PARENTESCO_EMPLEADOR = "Validaciones.RolAfiliado.buscarRolAfiliadoPorTipoNumeroAfiliadoParentescoEmpleador";
    /**
     * Named query que se encarga de consultar un afiliado por número de doucmento y su parentesco con el empleador.
     */
    public static final String BUSCAR_ROL_AFILIADO_POR_NUMERO_AFILIADO_PARENTESCO_EMPLEADOR = "Validaciones.RolAfiliado.buscarRolAfiliadoPorNumeroAfiliadoParentescoEmpleador";
    /**
     * Named query que se encarga de consultar un afiliado por tipo y numero de doucmento y su representante legal.
     */
    public static final String BUSCAR_ROL_AFILIADO_POR_TIPO_NUMERO_AFILIADO_REPRESENTANTE_LEGAL = "Validaciones.RolAfiliado.buscarPorTipoNumeroAfiliadoClasificacionSolicitudEmpleadorMismoRep";
    /**
     * Named query que se encarga de consultar un afiliado por número de doucmento y su representante legal.
     */
    public static final String BUSCAR_ROL_AFILIADO_POR_NUMERO_AFILIADO_REPRESENTANTE_LEGAL = "Validaciones.RolAfiliado.buscarPorNumeroAfiliadoClasificacionSolicitudEmpleadorMismoRep";
    /**
     * Named query que consulta beneficiario por tipo y número de documento y estado.
     */
    public static final String BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_ESTADO = "Validaciones.Beneficiario.buscarBeneficiarioPorTipoNumeroTipoAfiliacionEstado";
    /**
     * Named query que consulta beneficiario por tipo y número de documento y estado.
     */
    public static final String BUSCAR_BENEFICIARIO_POR_NUMERO_ESTADO = "Validaciones.Beneficiario.buscarBeneficiarioPorNumeroTipoAfiliacionEstado";
    /**
     * Named query que consulta afiliado por tipo y número de documento es mismo empleador.
     */
    public static final String BUSCAR_ROL_AFILIADO_TIPO_NUMERO_MISMO_EMPLEADOR = "Validaciones.RolAfiliado.buscarPorTipoNumeroAfiliadoClasificacionSolicitudMismoEmpleador";
    /**
     * Named query que consulta afiliado por número de documento es mismo empleador.
     */
    public static final String BUSCAR_ROL_AFILIADO_NUMERO_MISMO_EMPLEADOR = "Validaciones.RolAfiliado.buscarPorNumeroAfiliadoClasificacionSolicitudMismoEmpleador";
    /**
     * Named query que consulta la fecha de retiro de un empleador.
     */
    public static final String BUSCAR_FECHA_RETIRO_EMPLEADOR = "Validaciones.Empleador.buscarFechaRetiroEmpleadorPorTipoNumero";
    /**
     * Constante BUSCAR_FECHA_RETIRO_AFILIADO
     */
    public static final String BUSCAR_FECHA_RETIRO_AFILIADO = "Validaciones.Personas.buscarFechaRetiroAfiliadoPorTipoNumero";
    /**
     * Constante BUSCAR_FECHA_RETIRO_AFILIADO_DEPENDIENTE
     */
    public static final String BUSCAR_FECHA_RETIRO_AFILIADO_DEPENDIENTE = "Validaciones.Personas.buscarFechaRetiroAfiliadoPorTipoNumeroIdEmpleador";
    /**
     * Named query que consulta la fecha de retiro de un empleador.
     */
    public static final String BUSCAR_EMPLEADOR = "Validaciones.Empleador.buscarEmpleadorPorTipoNumero";
    /**
     * Named query que consulta la fecha de retiro de un empleador.
     */
    public static final String CONTAR_TRABAJADORES_ACTIVOS_ASOCIADOS_SUCURSAL = "Validaciones.Empleador.contarTrabajadoresActivosAsociadosSucursal";
    /**
     * Named query que consulta la fecha de retiro de un empleador.
     */
    public static final String CONTAR_SUCURSALES_EMPLEADOR = "Validaciones.Empleador.contarSucursalesEmpleador";
    /**
     * Named query que consulta la fecha de retiro de un empleador.
     */
    public static final String CONTAR_SUCURSALES_ACTIVAS_EMPLEADOR = "Validaciones.Empleador.contarSucursalesActivasEmpleador";

    /**
     * Named query que consulta un empleador por su razón social
     */
    public static final String BUSCAR_EMPLEADOR_RAZON_SOCIAL = "Validaciones.Empleador.buscarEmpleadorRazonSocial";

    /**
     * Representa la consulta de sucursal empresa por identificador
     */
    public static final String BUSCAR_SUCURSAL_EMPRESA_BY_ID = "Validaciones.buscar.sucursalEmpresa.id";

    /**
     * Named query que consulta la fecha de retiro de un empleador.
     */
    public static final String BUSCAR_PERSONA = "Validaciones.Empleador.buscarPersona";

    /**
     * 
     */
    public static final String BUSCAR_PERSONA_NATIVA = "Validaciones.Empleador.buscarPersona.fallecida.nativa";

    /**
     * Named query que consulta el empleador por razon social
     */
    public static final String CONSULTAR_EMPLEADOR_POR_RAZON_SOCIAL = "Empleador.razonSocial.buscarTodos";

    /**
     * Named query que consulta el empleador por tipo y numero de identificacion
     */
    public static final String CONSULTAR_EMPLEADOR_TIPO_INDENTIFICACION_NUMERO = "Empleador.tipoIdentificacion.numIdentificacion.buscarTodos";

    /**
     * Named query que consulta el empleador por tipo y numero de identificacion ademas de dv
     */
    public static final String CONSULTAR_EMPLEADOR_TIPO_INDENTIFICACION_NUMERO_DV = "Empleador.tipoIdentificacion.numIdentificacion.DV.buscarTodos";

    /**
     * Named query que consulta el empleador por tipo y numero de identificacion ademas de dv
     */
    public static final String BUSCAR_TRABAJADORES_ACTIVOS = "Validaciones.Empleador.buscarTrabajadoresActivos";
    /**
     * Named query que consulta una sucursal empresa asociada al empleador
     */
    public static final String CONSULTAR_SUCURSAL_ASOCIADA_EMPLEADOR = "Validaciones.Empleador.contarSucursalAsociadaEmpleador";
    /**
     * Named query que consulta el empleador por tipo y numero de identificacion ademas de dv
     */
    public static final String TOTAL_TRABAJADORES_CERO = "Empleadores.total.trabajadores.cero";
    /**
     * Named query que consulta el empleador por tipo y numero de identificacion ademas de dv
     */
    public static final String TOTAL_TRABAJADORES_INACTIVOS_CERO = "Empleadores.total.trabajadores.inactivos.cero";
    
    /**
     * Named query que consulta que el empleador no tenga asociado un acto administrativo de confirmación de su afiliación desde su más
     * reciente activación.
     */
    public static final String CONSULTAR_EMPLEADOR_NO_TIENE_ACTO_ADMINISTRATIVO = "Validaciones.Empleador.consultarEmpleadorNotieneActoAdministrativo";
    /**
     * Named query que consulta el beneficiario por numero y tipo de documento y estado
     */
    public static final String BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_DOCUMENTO_Y_ESTADO = "Validaciones.Beneficiario.buscarBeneficiarioPorNumeroTipoAfiliadoEstado";
    /**
     * 
     */
    public static final String BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_DOCUMENTO_AFILIADO_Y_BENEFICIARIO = "Validaciones.Beneficiario.buscarBeneficiarioPorNumeroTipoAfiliado";
    /**
     * Named query que consulta la persona por tipo de afiliación
     */
    public static final String BUSCAR_PERSONA_TIPO_AFILIACION = "Validaciones.RolAfiliado.buscarPersonaPorTipoAfiliacion";
    /**
     * Named query que consulta el rol afiliado por tipo y numero de identificación y el estado civil del afiliado
     */
    public static final String BUSCAR_ROL_AFILIADO_POR_TIPO_NUMERO_IDENTIFICACION_Y_ESTADO_CIVIL = "Validaciones.RolAfiliado.buscarRolAfiliadoPorTipoNumeroIdentificacionEstadoCivil";
    /**
     * 
     */
    public static final String BUSCAR_AFILIADO_POR_TIPO_NUMERO_IDENTIFICACION = "Validaciones.Afiliado.buscarAfiliadoPorTipoNumeroIdentificacionEstadoCivil";
    /**
     * Named query que consulta el rol afiliado por tipo y numero de identificación y el estado del afiliado
     */
    public static final String BUSCAR_ROL_AFILIADO_POR_TIPO_NUMERO_IDENTIFICACION_Y_ESTADO = "Validaciones.RolAfiliado.buscarRolAfiliadoPorTipoNumeroIdentificacionEstado";
    /**
     * Named query que consulta el afiliado por tipo y numero de identificación y el estado del afiliado
     */
    public static final String BUSCAR_AFILIADO_POR_TIPO_NUMERO_IDENTIFICACION_Y_ESTADO = "Validaciones.Afiliado.buscarAfiliadoPorTipoNumeroIdentificacionEstado";
    /**
     * Named query que consulta el afiliado por tipo y numero de identificación y el estado del afiliado
     */
    public static final String BUSCAR_AFILIADO_POR_TIPO_NUMERO_IDENTIFICACION_SIN_ESTADO = "Validaciones.Afiliado.buscarAfiliadoPorTipoNumeroIdentificacionSinEstado";
    
    /**
     * Named query que consulta el afiliado según el tipo de pensionado que se busque
     */
    public static final String BUSCAR_PENSIONADO_ACTIVO_POR_TIPO = "Validaciones.Persona.buscarAfiliadoPensionadoActivoPorTipo";
    /**
     * Named query que consulta el estado del beneficiario respecto a su afiliado principal
     */
    public static final String BUSCAR_ESTADO_BENEFICIARIO_RESPECTO_AFILIADO_PPAL = "Validaciones.Beneficiario.validarEstadoRespectoAfiliadoPrincipal";
    /**
     * 
     */
    public static final String BUSCAR_BENEFICIARIO_POR_TIPO_Y_NUMERO_BENEFICIARIO_Y_AFILIADO = "Validaciones.Beneficiario.buscarBeneficiarioPorTipoYNumeroIdDeBeneficiarioYAfiliado";
    /**
     * Named query que consulta el estado del afiliado respecto al empleador
     */
    public static final String BUSCAR_ESTADO_AFILIADO_RESPECTO_EMPLEADOR = "Validaciones.RolAfiliado.buscarEstadoAfiliadoRespectoEmpleador";
    /**
     * 
     */
    public static final String BUSCAR_ESTADO_AFILIADO_CAJA_RESPECTO_EMPLEADOR = "Validaciones.Afiliado.buscarEstadoAfiliadoRespectoEmpleador";
    /**
     * Named query que consulta la fecha de nacimiento del beneficiario
     */
    public static final String BUSCAR_FECHA_NACIMIENTO_BENEFICIARIO = "Validaciones.Beneficiario.buscarFechaNacimientoBeneficiario";
    /**
     * Named query que consulta el benefeiciario con estado activo, según el tipo dado
     */
    public static final String BUSCAR_BENEFICIARIO_ACTIVO_POR_TIPO = "Validaciones.Beneficiario.buscarBeneficiarioActivoPorTipo";
      /**
     * Named query que consulta el beneficiario con condicional de afiliado
     */
    public static final String BUSCAR_BENEFICIARIO_ACTIVO_POR_TIPO_Y_AFILIADO = "Validaciones.Beneficiario.buscarBeneficiarioActivoPorTipoyAfiliado";
    /**
     * Named query que consulta la fecha de finalización de la incapacidad
     */
    public static final String BUSCAR_FECHA_FIN_INCAPACIDAD = "Validaciones.Afiliado.buscarFechaFinIncapacidad";
    /**
     * busca la cantidad de beneficiarios por un tipo dado relacionados a un afiliado principal
     */
    public static final String BUSCAR_CANTIDAD_BENEFICIARIOS_POR_TIPO = "Validaciones.Beneficiario.buscarCantidadBeneficiariosPorTipo";
    /**
     * Named query que consulta el afiliado por su tipo, y por su numero y tipo de documento de identidad
     */
    public static final String BUSCAR_AFILIADO_POR_TIPO_AFI_NUMERO_TIPO_DOCUMENTO = "Validaciones.Afiliado.buscarAfiliadoPorTipoAfiliadoNumeroTipoDocumento";
    /**
     * Named query que consulta los beneficiarios tipo hijo del afiliado principal
     */
    public static final String BUSCAR_BENEFICIARIOS_POR_AFILIADO = "Validaciones.Beneficiario.buscarBeneficiariosTipoAfiliadoYNumeroTipoAfiliado";
    /**
     * Named query que consulta la fecha de vencimiento del certificado escolar
     */
    public static final String BUSCAR_FECHA_VENCIMIENTO_CERTIFICADO_ESCOLAR = "Validaciones.Beneficiario.buscarFechaVencimientoCertificadoEscolar";
    /**
     * Named query que consulta el afiliado y su tipo dados su numero y tipo de documento
     */
    public static final String BUSCAR_TIPO_AFILIADO_POR_NUMERO_Y_TIPO_DOCUMENTO = "Validaciones.RolAfiliado.buscarTipoAfiliadoPorNumeroYTipoDocumento";
    /**
     * Named query que consulta todos los tipos de afiliado del afiliado principal dado su numero y tipo de identificacion
     */
    public static final String BUSCAR_TIPOS_ASOCIADOS_AFILIADO_POR_NUMERO_Y_TIPO_DOCUMENTO = "Validaciones.RolAfiliado.buscarTiposAsociadosAfiliadoPorNumeroYTipoDocumento";
    /**
     * Named query que consulta la persona por tipo y numero de documento
     */
    public static final String BUSCAR_PERSONA_TIPO_NUMERO_DOCUMENTO = "Validaciones.Persona.buscarPersonaPorTipoYNumeroDocumento";
    /**
     * Named query que busca los beneficiarios activos que pertencen al mismo grupo familiar del afiliado principal
     */
    public static final String BUSCAR_BENEFICIARIOS_ACTIVOS_GRUPO_FAMILIAR_AFILIADO = "Validaciones.Beneficiario.buscarBeneficiariosActivosGrupoFamiliarAfiliado";
    /**
     * Named query que busca el medio de pago asignado para un Grupo Familiar.
     */
    public static final String BUSCAR_MEDIO_DE_PAGO_GRUPO_FAMILIAR = "Validaciones.GrupoFamiliar.buscarMedioDePago";

    /**
     * Consulta el medio de pago asignado a una persona
     */
    public static final String BUSCAR_MEDIO_DE_PAGO_TRABAJADOR = "Validaciones.Trabajador.buscarMedioDePago";

    /**
     * Named query que busca los beneficiarios activos del afiliado
     */
    public static final String BUSCAR_GRUPO_FAMILIAR_BENEFICIARIOS_ACTIVOS_AFILIADO = "Validaciones.Afiliado.buscarGrupoFamiliarBeneficiariosActivosPorAfiliado";

    /**
     * Consulta los Beneficiarios Activos asociados a un Grupo Familiar.
     */
    public static final String BUSCAR_BENEFICIARIOS_GRUPO_FAMILIAR_ACTIVO = "Validaciones.GrupoFamiliar.buscarBeneficiariosActivo";
    
    /**
     * Consulta los Beneficiarios Activos asociados a un Grupo Familiar.
     */
    public static final String BUSCAR_BENEFICIARIOS_GRUPO_FAMILIAR = "Validaciones.GrupoFamiliar.buscarBeneficiarios";
    
    /**
     * Consulta los Beneficiarios Activos asociados a un Grupo Familiar.
     */
    public static final String BUSCAR_BENEFICIARIOS_AFILIADO_GRUPO_FAMILIAR = "Validaciones.GrupoFamiliar.buscarBeneficiariosAfiliado";
    
    /**
     * Consulta los Beneficiarios Activos asociados a un Grupo Familiar.
     */
    public static final String BUSCAR_BENEFICIARIO_ACTIVO_FECHA_RETIRO = "Validaciones.GrupoFamiliar.BeneficiarioActivoFecha";
    
    /**
     * Consulta los Beneficiarios Activos asociados a un Grupo Familiar.
     */
    public static final String BUSCAR_TRABAJADOR_ACTIVO_FECHA_RETIRO = "Validaciones.Trabajador.ActivoFechaRetiro";
    
    /**
     * Named query que busca si el afiliado principal es el representante legal del empleador
     */
    public static final String BUSCAR_AFILIADO_REPRESENTANTE_LEGAL_EMPLEADOR = "Validaciones.RolAfiliado.buscarAfiliadoRepresentanteLegalEmpleador";
    /**
     * Named query que busca el estado de las afiliaciones del rol afiliado
     */
    public static final String BUSCAR_ESTADO_AFILIACIONES_AFILIADO_POR_TIPO = "Validaciones.Afiliado.BuscarAfiliadoPorTipoYNumeroDocumento";
    /**
     * Named query que busca los beneficiarios por tipo y estado pertenecientes
     * al grupo familiar del afiliado principal dado
     */
    public static final String BUSCAR_BENEFICIARIO_POR_TIPO_Y_AFILIADO_PPAL = "Validaciones.Beneficiario.buscarBeneficiarioPorTipoYAfiliadoPrincipal";
    /**
     * Named query que busca los los socios del empleador dado que son
     * conyuge del afiliado principal (trabajador)
     */
    public static final String BUSCAR_AFILIADO_CONYUGE_SOCIO_EMPLEADOR = "Validaciones.SocioEmpleador.buscarAfiliadoConyugeSocioEmpleador";
    /**
     * Named query que busca el estado de la tarjeta asociada al afiliado
     */
    public static final String BUSCAR_ESTADO_TARJETA_AFILIADO = "Validaciones.Afiliado.buscarEstadoTarjetaAfiliado";

    /**
     * Named query que consulta el beneficio del empleador
     */
    public static final String CONSULTAR_BENEFICIO_EMPLEADOR = "Validaciones.Empleador.consultarBeneficioEmpleador";

    //NAMED QUERY'S PARA RN DE NOVEDADES PERSONA
    /**
     * Named query que busca el(los) grupo(s) familiar(es) al(los) que pertenece el beneficiario
     */
    public static final String BUSCAR_GRUPO_FAMILIAR_BENEFICIARIO_ACTIVO = "Validaciones.GrupoFamiliar.buscarGrupoFamiliarBeneficiarioActivo";
    /**
     * Named query que busca el grupo familiar al que pertenece la persona
     */
    public static final String BUSCAR_GRUPO_FAMILIAR_PERSONA = "Validaciones.GrupoFamiliar.buscarGrupoFamiliarPersona";
    /**
     * Named query que busca el afiliado principal del grupo familiar al que
     * pertenece el beneficiario(activo)
     */
    public static final String BUSCAR_AFILIADO_PPAL_GRUPO_FAMILIAR_BENEFICIARIO_ACTIVO = "Validaciones.GrupoFamiliar.buscarGrupoFamiliarPersona";
    /**
     * Named query que busca una persona dados su(s) nombre(s) y su(s) apellido(s)
     */
    public static final String BUSCAR_PERSONA_POR_TIPO_NOMBRE_Y_APELLIDO = "Validaciones.Persona.buscarPersonaPorTipoYNombre";
    /**
     * Named query que busca el afiliado tipo pensionado activo como pagador de aportes
     */
    public static final String BUSCAR_AFILIADO_PENSIONADO_CON_PAGADOR_APORTES_ACTIVO = "Validaciones.Afiliado.buscarAfiliadoPensionadoPagadorAportesActivo";
    /**
     * Named query que busca si el afiliado principal es, a la vez, el administrador del subsidio en el mismo grupo familiar
     */
    public static final String BUSCAR_AFILIADO_ADMIN_DEL_SUBSIDIO = "Validaciones.Afiliado.buscarAdministradorDelSubsidio";

    /**
     * Named query que verifica si existe y devuelve el afiliado asociado al
     * empleador donde ambos ingresan por parametro a la consulta
     */
    public static final String BUSCAR_AFILIADO_ASOCIADO_AL_EMPLEADOR = "Validaciones.Afiliado.buscarAfiliadoAsociadoAlEmpleador";
    /**
     * Named query que busca si hay novedades de desafiliacion relacionadas al empleador dados su numero y tipo de identificacion
     */
    public static final String BUSCAR_SOLICITUD_NOVEDAD_DESAFILIACION_EN_CURSO = "Validaciones.SolicitudNovedad.BuscarSolicitudDesafiliacionEmpleadoEmpleador";
    /**
     * Named query que busca si hay novedades de desafiliacion relacionadas al empleador dado su id
     */
    public static final String BUSCAR_SOLICITUD_NOVEDAD_DESAFILIACION_EN_CURSO_CON_ID = "Validaciones.SolicitudNovedad.BuscarSolicitudDesafiliacionEmpleadoEmpleadorConId";
    /**
     * Named query que busca si hay una novedades de expedicion/reexpedicion de tarjeta asociada a una persona dada
     */
    public static final String BUSCAR_SOLICITUD_EXPEDICION_REEXPEDICION_TARJETA_PERSONA = "Validaciones.SolicitudNovedadPersona.BuscarSolicitudExpedicionReexpedicionTarjetaPersona";
    /**
     * Named query que busca
     */
    public static final String BUSCAR_BENEFICIARIO_HIJO_AFILIADO_PRINCIPAL_MISMO_GENERO = "Validaciones.Beneficiario.buscarBeneficiarioHijoAfiliadoMismoGenero";
    /**
     * Named query que busca
     */
    public static final String BUSCAR_PERSONA_BENEFICIARIO_HIJO_HUERFANO = "Validaciones.Beneficiario.hijoHuerfano";
    /**
     * Named query que busca
     */
    public static final String BUSCAR_PERSONA_BENEFICIARIO_HIJO_ADOPTIVO = "Validaciones.Beneficiario.hijoAdoptivo";
    /**
     * Named query que busca el estado de afiliacion de los trabajadores de un empleador
     */
    public static final String BUSCAR_EMPLEADOR_CON_TRABAJADORES_AFILIADOS = "Validaciones.RolAfiliado.Empleador.TrabajadoresAfiliados";
    /**
     * Named query que consulta un empleador por id.
     */
    public static final String BUSCAR_EMPLEADOR_POR_ID = "Validaciones.Empleador.buscarEmpleadorId";
    /**
     * Named query que busca el estado de la condicion grupo familiar inembargable para un grupo familiar dado su id
     */
    public static final String BUSCAR_ESTADO_GRUPO_FAMILIAR_INEMBARGABLE_POR_ID = "Validaciones.GrupoFamiliar.buscarEstadoGrupoFamiliarInembargablePorId";

    /**
     * Named query que consulta la persona detalle
     */
    public static final String BUSCAR_PERSONADETALLE_TIPO_NUMERO_IDENTIFICACION = "Validaciones.personadetalle.tipo.numero.identificacion";
    /**
     * Named query que consulta la persona detalle por id presona y numero identificacion
     */
    public static final String BUSCAR_PERSONADETALLE_ID_NUMERO_IDENTIFICACION = "Validaciones.personadetalle.id.numero.identificacion";
    /**
     * 
     */
    public static final String BUSCAR_CESION_RETENCION_SUBSIDIO_GRUPO_FAMILIAR = "Validaciones.GrupoFamiliar.buscarCesionRetencionSubsidioGrupoFamiliar";
    /**
     * 
     */
    public static final String BUSCAR_CESION_RETENCION_SUBSIDIO_AFILIADO = "Validaciones.Afiliado.buscarCesionRentencionSubsidioAfilido";
    /**
     * 
     */
    public static final String BUSCAR_SOLICITUDES_NOVEDAD_POR_TIPO_ASOCIADAS_AFILIADO = "Validaciones.Solicitud.buscarSolicitudesDeNovedadPorTipoAsociadasAlAfiliado";
    /**
     * 
     */
    public static final String BUSCAR_GRUPO_FAMILIAR_POR_ID = "Validaciones.GrupoFamiliar.buscarGrupoFamiliarPorId";
    /**
     * 
     */
    public static final String BUSCAR_FECHA_RETIRO_AFILIADO_TIPO_NUMERO = "Validaciones.Afiliado.buscarFechaRetiroAfiliadoPorTipoYNumero";
    /**
     * 
     */
    public static final String BUSCAR_BENEFICIARIO_POR_AFILIADO = "Validaciones.Beneficiario.buscarBeneficiarioPorTipoEstadoYAfiliado";
    /**
     * 
     */
    public static final String BUSCAR_SUCURSALES_EMPLEADOR = "Validaciones.Empleador.buscarSucursalesPorEmpleador";
    /**
     * Named query que consulta una sucursal asociada a un empleador por el codigo.
     */
    public static final String CONSULTAR_SUCURSAL_EMPRESA_POR_CODIGO_EMPLEADOR = "Validaciones.SucursalEmpresa.consultarSucursalEmpresaPorCodigo";
    /**
     * 
     */
    public static final String BUSCAR_FECHA_NACIMIENTO_PERSONA = "Validaciones.Persona.buscarFechaNacimientoPersona";
    /**
     * 
     */
    public static final String CONSULTAR_PERSONA_SUMATORIA_SALARIOS = "Persona.consultar.SumatoriaDeSalarios";
    /**
     * Consulta la persona para conocer su estado frente a la exclusion en la sumatoria de salarios
     */
    public static final String BUSCAR_AFILIADOS_PRINCIPALES_DE_UN_BENEFICIARIO = "Validaciones.Afiliado.buscarAfiliadosPrincipalesDeUnBeneficiario";
    /**
     * Consulta el ID de una Persona y Persona detalle por número de identificación.
     */
    public static final String CONSULTAR_ID_PERSONA_TIPO_Y_NUM_IDENTIFICACION = "Persona.consultarIdPorTipoYNumeroId";
    /**
     * Consulta el estado del conyuge para verificar si esta activo.
     */
    public static final String CONSULTAR_PERSONA_SUMATORIA_SALARIOS_ESTADO_CONYUGE = "Persona.consultarEstadoConyuge";
    /**
     * 
     */
    public static final String BUSCAR_AFILIADOS_PRINCIPALES_DE_UN_BENEFICIARIO_DADO_AFILIADO = "Validaciones.Afiliado.buscarAfiliadosPrincipalesDeUnBeneficiarioDadoAfiliado";
    /**
     * Named query que consulta un beneficiario por tipo de documento y numero y
     * tipo beneficiario y género
     */
    public static final String BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_TIPO_BENEFICIARIO_ESTADO_GENERO = "Validaciones.Beneficiario.buscarBeneficiarioPorTipoNumeroTipoBeneficiarioEstadoGenero";
    /**
     * Named query que consulta un beneficiario por numero documento y tipo
     * beneficiario estado y género
     */
    public static final String BUSCAR_BENEFICIARIO_POR_NUMERO_TIPO_BENEFICIARIO_ESTADO_GENERO = "Validaciones.Beneficiario.buscarBeneficiarioPorNumeroTipoBeneficiarioEstadoGenero";
    /**
     * Nombre del named query para buscar solicutdes de afiliación por empleador
     */
    public static final String NAMED_QUERY_SOLIC_EMPL_TEMPORAL = "SolicitudAfiliacionEmpleadorTemporal.buscarPorPersona";
    /**
     * Consulta los beneficiarios asociados al Afiliado que son diferentes al tipo enviado
     */
    public static final String BUSCAR_BENEFICIARIOS_DIFERENTE_TIPO = "Validaciones.Beneficiario.buscarBeneficiariosDiferenteTipo";
    /**
     * Named query que consulta el rol afiliado por tipo y numero de identificación y el estado del afiliado
     */
    public static final String CONTAR_ROL_AFILIADO_POR_TIPO_NUMERO_IDENTIFICACION_Y_ESTADO = "Validaciones.RolAfiliado.contarRolAfiliadoPorTipoNumeroIdentificacionEstado";
    /**
     * Consulta la cantidad de afiliados activos que sean dependientes, independiente dos porciento y/o pensionado dos porciento
     */
    public static final String CONTAR_TIPO_AFILIADO_ACTIVO_POR_TIPO_NUMERO_IDENTIFICACION_Y_ESTADO = "Validaciones.Solicitud.contarClasificacionPorTipoNumeroIdentificacionEstado";
    
    /**
     * Consulta los aportes de un afiliado para los últimos 3 meses
     */
    public static final String CONSULTAR_PERIODO_APORTES_AFILIADO = "Validaciones.Afiliado.buscarAportesUltimoPeriodo";
    /**
     * Nombre del named query para buscar solicutdes de afiliación por empleador
     */
    public static final String NAMED_QUERY_SOLIC_EMPL_ESTADO_TEMPORAL = "SolicitudAfiliacionEmpleadorTemporalEstado.buscarPorPersona";

    /**
     * Consulta los aportes de un afiliado para los últimos 3 meses
     */
    public static final String CONSULTAR_AFILIADO_ESTADO_FECHA_RETIRO_MOTIVODESA = "Validaciones.RolAfiliado.buscarRolAfiliadoEstadoFechaRetiroMotivo";

    /**
     * Consutla Solicitud de Novedad por Persona y por tipo de Novedad en un estado determinado
     */
    public static final String CONSULTAR_SOLICITUD_NOVEDAD_ESTADO_TIPOS = "Validaciones.SolicitudNovedad.estadoTipo";

    /**
     * Consulta novedades de aportes para un tipo de cotizante predeterminado.
     */
    public static final String CONSULTAR_NOVEDADES_APORTES = "Validaciones.Aportes.buscarPorCotizante";

    /**
     * Consulta el detalle de novedad de las solicitudes de una Persona
     */
    public static final String CONSULTA_NOVEDAD_DETALLE_VIGENTE_POR_PERSONA_POR_TRANSACCIONES = "Validaciones.Persona.buscarNovedadDetalleVigentePorPersonaPorTipoTransaccion";

    /**
     * Consulta el detalle de novedad de las solicitudes de una Persona
     */
    public static final String CONSULTA_NOVEDAD_DETALLE_VIGENTE_POR_PERSONA = "Validaciones.Persona.buscarNovedadDetalleVigentePorPersona";

    /**
     * Consulta la expulsion subsanada de un empleador
     */
    public static final String CONSULTA_EXPULSION_SUBSANDA_EMPLEADOR = "Validaciones.empleador.buscarExpulsionSubsanada";

    /**
     * Consulta la expulsion subsanada de un rolafiliado
     */
    public static final String CONSULTA_EXPULSION_SUBSANDA_ROL_AFILIADO = "Validaciones.persona.buscarExpulsionSubsanada";

    /**
     * Consulta el rol afiliado por id
     */
    public static final String CONSULTA_ROL_AFILIADO_POR_ID = "Validaciones.persona.buscarRolAfiliadoPorId";

    /**
     * Consulta el rol afiliado por identificacion de persona afiliado e identificacion empleador
     */
    public static final String CONSULTA_ROL_AFILIADO_POR_PERSONA_EMPLEADOR = "Validaciones.Persona.buscarRolAfiliadoPorPersonaEmpleador";

    /**
     * Named query que consulta una persona por tipo y número de documento validando su existencia.
     */
    public static final String VALIDAR_EXISTENCIA_PERSONA_POR_TIPO_NUMERO = "Validaciones.Persona.validarExistenciaPersonaPorTipoNumero";

    /**
     * Constante que representa el nombre de la <b>consulta por tipo de documento y número de documento</b> para saber si un jefe de hogar
     * dependiente tiene aportes al día.
     */
    public static final String VALIDAR_JEFE_HOGAR_APORTES_TIPO_AFILIACION = "Validaciones.JefeHogar.validarJefeHogarAportesTipoAfiliacion";

    /**
     * Constante que representa el nombre de la <b>consulta por tipo de documento y número de documento</b> para saber si un jefe de hogar
     * dependiente tiene aportes al día.
     */
    public static final String VALIDAR_JEFE_HOGAR_APORTES_POR_TIPO_NUMERO = "Validaciones.JefeHogar.validarJefeHogarAportesPorTipoNumero";

    /**
     * Constante que representa el nombre de la consulta <b>consulta por primer nombre, primer apellido y fecha de nacimiento</b> para
     * saber si un jefe de hogar dependiente tiene aportes al día.
     */
    public static final String VALIDAR_JEFE_HOGAR_APORTES_POR_NOMBRE_APELLIDO_FECHA_NACIMIENTO = "Validaciones.JefeHogar.validarJefeHogarAportesPorNombreApellidoFechaNacimiento";

    /**
     * Constante que representa el nombre de la <b>consulta por tipo de documento y número de documento</b> para saber si un jefe de hogar
     * independiente o pensionado tiene aportes al día.
     */
    public static final String VALIDAR_JEFE_HOGAR_APORTES_INDEPENDIENTE_PENSIONADO_POR_TIPO_NUMERO = "Validaciones.JefeHogar.validarJefeHogarAportesIndependientePensionadoPorTipoNumero";

    /**
     * Constante que representa el nombre de la consulta <b>consulta por número de documento</b> para saber si un jefe de hogar
     * independiente o pensionado tiene aportes al día.
     */
    public static final String VALIDAR_JEFE_HOGAR_APORTES_INDEPENDIENTE_PENSIONADO_POR_NUMERO = "Validaciones.JefeHogar.validarJefeHogarAportesIndependientePensionadoPorNumero";

    /**
     * Constante que representa el nombre de la consulta <b>consulta por primer nombre, primer apellido y fecha de nacimiento</b> para
     * saber si un jefe de hogar dependiente tiene aportes al día.
     */
    public static final String VALIDAR_JEFE_HOGAR_APORTES_INDEPENDIENTE_PENSIONADO_NOMBRE_APELLIDO_FECHA_NACIMIENTO = "Validaciones.JefeHogar.validarJefeHogarAportesIndependientePensionadoNombreApellidoFecha";

    /**
     * Constante que se encarga de validar la la solicitud de afiliacion de un empleador por tipo de solicitante
     */
    public static final String VALIDAR_EMPLEADOR_RELACIONADO_EN_LA_SOLICITUD_POR_TIPO_SOLICITANTE = "Validaciones.SolicitudAfiliacionEmpleador.validarEmpleadorRelacionadoEnLaSolicitud";

    /**
     * Constante que se encarga de validar la la solicitud de afiliacion de un empleador por tipo de solicitante
     */
    public static final String VALIDAR_BENEFICIARIO_REALCIONADO_CON_AFILIADO = "Validaciones.beneficiario.relacionadoConAfiliado";

    /**
     * Constante que se encarga de validar si un beneficiario tiene tipo de afiliacion respecto al empleador
     */
    public static final String VALIDAR_BENEFICIARIO_TIPO_AFILIACION_EMPLEADOR = "Validaciones.validarBeneficiarioTipoAfiliacionEmpleador";

    /**
     * Constante que se encarga de buscar beneficiarios por tipo y numero de identificacion y estado por tipo afiliado
     */
    public static final String BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_TIPO_BENEFICIARIO_ESTADO_TIPO_AFILIADO = "Validaciones.Beneficiario.buscarBeneficiarioPorTipoNumeroTipoBeneficiarioEstadoAfiliado";

    /**
     * Constante que se encarga de buscar beneficiarios por numro de identificacion y estado del afiliado
     */
    public static final String BUSCAR_BENEFICIARIO_POR_NUMERO_TIPO_BENEFICIARIO_ESTADO_TIPO_AFILIADO = "Validaciones.Beneficiario.buscarBeneficiarioPorNumeroTipoBeneficiarioEstadoAfiliado";
    
    /**
     * Named query que consulta la persona detalle por id presona y numero identificacion
     */
    public static final String BUSCAR_CONDICIONINVALIDEZ_ID_NUMERO_TIPO_IDENTIFICACION = "Validaciones.condicionInvalidez.id.numero.tipo.identificacion";
    
    
    /**
     * Named query que consulta la Beneficiario detalle por id presona y numero identificacion
     */
    public static final String BUSCAR_BENEFICIARIODETALLE_ID_NUMERO_TIPO_IDENTIFICACION = "Validaciones.beneficiarioDetalle.id.numero.tipo.identificacion";
   
    /**
     * Named query que se encarga de consultar la ultima clasificación asociada a un empleador
     */
    public static final String BUSCAR_ULTIMA_CLASIFICACION_EMPLEADOR = "Validaciones.empleador.consultar.ultimaClasificacion";
 
    //FOVIS

	/**
	 * Constante que representa el nombre de la <b>consulta por tipo de documento y número de documento</b> para saber si un jefe de hogar 
	 * dependiente, el empleador tiene beneficio 1429.
	 */
	public static final String VALIDAR_JEFE_HOGAR_DEPENDIENTE_BENEFICIO_1429="Validaciones.JefeHogar.validarJefeHogarDependienteBeneficio1429";
	/**
	 * Constante que representa el nombre de la <b>consulta por tipo de documento y número de documento</b> para saber si, una persona es
	 * mienbro del grupo familiar del jefe de hogar postulante a subsidio
	 */
	public static final String VALIDAR_BENEFICIARIO_DE_JEFE_HOGAR="Validaciones.JefeHogar.ValidarBeneficiario";
	/**
	 * Constante que representa el nombre de la <b>consulta por tipo de documento y número de documento</b> para saber si, un jefe de hogar 
	 * en situaicon especial mujer/cabeza de familia , es conyugue activo en uno de sus grupos familiares asociados.
	 */
	public static final String VALIDAR_JEFE_HOGAR_CONYUGE_ACTIVO = "Validaciones.JefeHogar.ValidarNoConyugeActivo";
	
	/**
     * Constante que representa el nombre de la <b>consulta por tipo de documento y número de documento</b> para saber si, un jefe de hogar 
     * en situaicon especial mujer/cabeza de familia , es conyugue activo en uno de sus grupos familiares asociados.
     */
    public static final String VALIDAR_JEFE_HOGAR_CONYUGE_OTRO_GRUPO = "Validaciones.JefeHogar.ValidarConyugeActivo";
	/**
	 * Constante que representa el nombre de la <b>consulta por tipo de documento y número de documento</b> para saber si, un jefe de hogar 
	 * en situaicon especial mujer/cabeza de familia , es conyugue activo en uno de sus grupos familiares asociados.
	 */
	public static final String VALIDAR_JEFE_HOGAR_CONYUGE_ACTIVO_OTRO_GRUPO= "Validaciones.JefeHogar.validarConyugeActivoOtroGrupoFamiliar";
	/**
	 * Constante que representa el nombre de la <b>consulta por tipo de documento y número de documento</b> para saber si el conyuge del grupo familiar se encuentra 
	 * en estado Activo
	 */
	public static final String VALIDAR_CONYUGE_ACTIVO_GRUPO_FAMILIAR= "Validaciones.JefeHogar.validarConyugeActivoGrupoFamiliar";
	
	/**
	 * Constante que representa el nombre de la <b>consulta por tipo de documento y número de documento</b> para saber si una persona registra invalidez en formulario FOVIS
	 * y no asi en base de datos.
	 */
	public static final String VALIDAR_PERSONA_INVALIDEZ= "Validaciones.JefeHogar.validarCondcionInvelidezNoRegistradaEnBd";
	/**
	 * Constante que representa el nombre de la <b>consulta por tipo de documento y número de documento</b> para saber si una persona registra hijo biologico en el formulario FOVIS
	 * y asi mismo en base de datos.
	 */
	public static final String VALIDAR_PERSONA_NO_REGISTRA_HIJO_BIOLOGICO_BD_SI_FOVIS= "Validaciones.JefeHogar.validarHijoBiologicoNoRegistradoBd";

	/**
	 * Constante que representa el nombre de la <b>consulta por tipo de documento y número de documento</b> para saber si una persona registra invalidez en base de datos
	 * pero no esta referida en el formulario FOVIS
	 */
	public static final String VALIDAR_PERSONA_NO_REGISTRA_INVALIDEZ_FOVIS_SI_BD= "Validaciones.JefeHogar.validarCondcionInvelidezNoRegistradaEnFovis";
	/**
	 * Constante que representa el nombre de la <b>consulta por tipo de documento y número de documento</b> para saber si una persona registra hijastro en el formulario FOVIS
	 * y asi mismo en la base de datos.
	 */
	public static final String VALIDAR_PERSONA_NO_REGISTRA_HIJASTRO_BD_SI_FOVIS= "Validaciones.JefeHogar.validarHijastroNoRegistradoBd";
	/**
	 * Constante que representa el nombre de la <b>consulta por tipo de documento y número de documento</b> para saber si una persona registra hijo adoptivo en el formulario FOVIS
	 * y asi mismo en la base de datos.
	 */
	public static final String VALIDAR_PERSONA_NO_REGISTRA_HIJO_ADOPTIVO_BD_SI_FOVIS= "Validaciones.JefeHogar.validarHijoAdoptivoNoRegistradoBd";
	/**
	 * Constante que representa el nombre de la <b>consulta por tipo de documento y número de documento</b> para saber si una persona tiene coincidencia de apellidos  
	 * con el jefe de hogar 
	 */
	public static final String VALIDAR_COINCIDENCIA_APELLIDOS_CON_JEFE_HOGAR= "Validaciones.Persona.ApellidosCoincidenConJefeHogar";
	/**
	 * Constante que representa el nombre de la <b>consulta por tipo de documento y número de documento</b> para saber si una persona tiene coincidencia de apellidos  
	 * con el conyuge activo en el grupo de hogar de existir
	 */
	public static final String VALIDAR_COINCIDENCIA_APELLIDOS_CON_CONYUGE= "Validaciones.Persona.ApellidosCoincidenConConyuge";
    /**
     * Constante que representa el nombre de la <b>consulta por tipo de documento y número de documento</b> para saber si una persona tiene
     * conyuge activo en el grupo de hogar.
     */
    public static final String VALIDAR_PERSONA_EXISTENCIA_CONYUGE = "Validaciones.Persona.ExistenciaConyuge";
	/**
	 * Constante que representa el nombre de la <b>consulta por tipo de documento y número de documento</b> para saber si el tipo de parentesco de una persona con el jefe de hogar es el mismo
	 * en el formulario FOVIS y en la base de datos 
	 */
	public static final String VALIDAR_COINCIDENCIA_PARENTESCO_CON_JEFE_HOGAR= "Validaciones.Persona.ParentescoCoincideConJefeHogar";
	
	/**
	 * Constante que representa el nombre de la <b>consulta por tipo de documento y número de documento</b> para saber si una persona se encuentra habilitada para recibir subsidio de vivienda
	 */
	public static final String VALIDAR_HABILITADO_SUBSIDIO_VIVIENDA= "Validaciones.Persona.EsHabilParaSubsidio";
	
	/**
	 * Constante para la consulta de beneficiarios por tipo beneficiario asociados al jefe de hogar 
	 */
	public static final String CONSULTAR_BENEFICIARIO_BY_TIPO_ASOCIADO_JEFE_FOVIS= "Validaciones.JefeHogar.buscarBeneficiarioByTipoBenNroYTipoDocJefe";
	/**
	 * Constante que representa el nombre de la <b>consulta por tipo de documento y número de documento</b> para saber si el jefe de hogar registra un conyuge en el formulario FOVIS
	 * y es la misma registrado en base de datos.
	 */
	public static final String VALIDAR_PERSONA_REGISTRA_CONYUGE_FOVIS_DISTINTO_BD= "Validaciones.JefeHogar.ValidarConyugeFovisDistintoBd";
	/**
	 * Constante que representa el nombre de la <b>consulta por tipo de documento y número de documento</b> para saber si un Jefe de Hogar esta activo en otra postulación
	 */
	public static final String VALIDAR_JEFE_HOGAR_ACTIVO_OTRA_POSTULACION= "Validaciones.JefeHogar.miembroOtraPostulacion";
	/**0
	 * Constante que representa el nombre de la <b>consulta por tipo de documento y número de documento</b> para saber si un Integrante de Hogar esta activo en otra postulación
	 */
	public static final String VALIDAR_INTEGRANTE_HOGAR_ACTIVO_OTRA_POSTULACION= "Validaciones.IntegranteHogar.miembroOtraPostulacion";
	/**
	 * Constante que representa el nombre de la <b>consulta por id de la postulacion</b> para saber si se cumple con el requisito de ahorro previo superior al minimo parametrizado
	 */
	public static final String VALIDAR_AHORRO_MINIMO = "Validaciones.JefeHogar.ValidarAhorroMinimo";
	
	/**
	 * Constante que define la consulta de solicitudes que se encuentran en proceso para un Jefe de Hogar.
	 */
	public static final String VALIDAR_SOLICITUD_POSTULACION_EN_PROCESO_JEFEHOGAR = "Validaciones.JefeHogar.validarSolicitudPostulacionEnProceso";
	
	/**
	 * Constante que define la consulta de solicitudes que se encuentran en proceso para un Integrante de Hogar
	 */
	public static final String VALIDAR_SOLICITUD_POSTULACION_EN_PROCESO_INTEGRANTEHOGAR = "Validaciones.IntegranteHogar.validarSolicitudPostulacionEnProceso";
	
	/**
	 * Constante que representa el nombre de la <b>consulta por tipo de documento y número de documento</b> para saber si un integrante de hogar 
	 * es Jefe de Hogar activo en otra postulación
	 */
	public static final String VALIDAR_INTEGRANTE_ES_JEFE_HOGAR_ACTIVO_OTRA_POSTULACION= "Validaciones.IntegranteHogar.JefeHogarOtraPostulacion";
	/**
	 * Constante que representa el nombre de la <b>consulta por tipo de documento y número de documento</b> para saber si un integrante de hogar 
	 * es Jefe de Hogar activo en otra postulación
	 */
	public static final String VALIDAR_INTEGRANTE_NO_JEFE_ACTIVO_OTRA_POSTULACION= "Validaciones.IntegranteHogar.miembroActivoOtraPostulacion";

    /**
     * Constante para la consulta que permite obtener la postulacion en la que una persona se encuentra en estado ACTIVO como Integrante o
     * Jefe Hogar y la postulación se encuentra aun en proceso
     */
    public static final String CONSULTAR_PERSONA_ASOCIADA_POSTULACION ="validaciones.fovis.consultar.persona.postulacion";

    /**
     * Constante para la consulta que permite obtener la postulacion en la que una persona se encuentra en estado ACTIVO como Integrante o
     * Jefe Hogar
     */
    public static final String CONSULTAR__POSTULACION ="validaciones.fovis.consultar.postulacion";
    
    /**
     * Constante para la consulta de novedades fovis aprobadas por medio de la transaccion y la postulación 
     */
    public static final String CONSULTAR_NOVEDAD_FOVIS_BY_TIPO_TRANSACCION_POSTULACION = "Validaciones.consultar.novedad.fovis.aprobada.tipoTransaccion.postulacion";
    //FIN POSTULACIONES FOVIS

	/**
	 * Consulta los grupos familiares de un afiliado con mas de un beneficiario activo
	 */
	public static final String CONSULTAR_GRUPOS_FAMILIAR_CON_BENEFICIARIOS_ACTIVOS = "Validaciones.Afiliado.ConsultarGrupoFamiliarMasDeUnBeneficiarioActivoPorAfiliado";
	/**
	 * Consulta que el estado de la afiliacion del jefe de hogar sea "ACTIVO"
	 */
	public static final String VALIDAR_JEFE_HOGAR_ACTIVO = "Validaciones.JefeHogar.Activo";
	/**
	 * Consulta si el jefe de hogar tiene registros de mal uso de servicios/subsidios de la caja de compensación
	 */
	public static final String CONSULTAR_MOTIVOS_DESAFILIACION_JEFE_HOGAR = "Validaciones.JefeHogar.ConsultarMotivosDesafiliacion";
	/**
	 * Consulta si un afiliado tiene registros de mal uso de servicios/subsidios de la caja de compensación
	 */
	public static final String CONSULTAR_MOTIVOS_DESAFILIACION_BENEFICIARIO = "Validaciones.Beneficiario.ConsultarMotivosDesafiliacion";
	/**
	 * Consulta las novedades de retiro asociadas a un jefe de hogar
	 */
	public static final String CONSULTAR_NOVEDAD_DESAFILIACION_JEFE_HOGAR = "Validaciones.jefeHogar.RetiroEnNovedadEnCurso";
	/**
	 * Consulta si un beneficiario es beneficiario activo de un afiliado distinto al referenciado
	 */
	public static final String CONSULTAR_AFILIACIONES_ACTIVAS_BENEFICIARIO = "Validaciones.beneficiarioAvtivoDeAfiliadoDistinto";
	/**
	 * Consulta el estado del hogar de una postulacion Fovis
	 */
	public static final String CONSULTAR_ESTADO_HOGAR = "Validaciones.Postulacion.EstadoHogar";
	
	/**
	 * Consulta si una solicitud de postulacion se encuentra en un estado dertermindo
	 */
	public static final String CONSULTAR_ESTADO_SOLICITUD_POSTULACION = "Validaciones.SolicitudPostulacion.EstadoSolicitud";
	/**
	 * Consulta si existe al menos un ciclo de asigancion vigente ene le sistema
	 */
	public static final String CONSULTAR_EXISTE_CICLO_ASIGNACION_VIGENTE = "Validaciones.CicloAsignacionVigente";
	/**
	 * Consulta si una persona beneficiario ha sido beneficiado con un subsidio Fovis
	 */
	public static final String CONSULTAR_BENEFICIARIO_BENEFICIADO_SUBSIDIO_FOVIS = "Validaciones.persona.beneficiario.BeneficioSubsidioFovis";
	/**
	 * Consulta si una persona jefe de hogar ha sido beneficiado con un subsidio Fovis
	 */
	public static final String CONSULTAR_JEFE_HOGAR_BENEFICIADO_SUBSIDIO_FOVIS = "Validaciones.persona.jefeHogar.BeneficioSubsidioFovis";
	/**
	 * Consulta si la modalidad de la postulacion es la referenciada por parametro
	 */
	public static final String CONSULTAR_MODALIDAD_POSTULACION = "Validaciones.postulacion.Modalidad";
	/**
	 * Consulta si un beneficiario se encuentra activo con respecto a un jefe de hogar
	 */
	public static final String VALIDAR_INTEGRANTE_HOGAR_ACTIVO = "Validaciones.integranteHogar.Activo";
	/**
	 * Consulta si el hogar tiene un integrante conyuge en estado activo
	 */
	public static final String CONSULTAR_CONYUGE_ACTIVO_HOGAR = "Validaciones.hogar.ConyugeActivo";
	/**
	 * Consulta la cantidad de integrantes del hogar segun el tipo de integrante
	 */
	public static final String CONTAR_TIPO_INTEGRANTES_HOGAR = "Validaciones.hogar.CantidadTipoIntegrantesHogar";
	/**
	 * Consulta si el hogar registra un integrante tipo MADRE en cualquier estado respecto al hogar
	 */
	public static final String CONSULTAR_HOGAR_REGISTRA_TIPO_INTEGRANTE = "Validaciones.hogar.RegistraTipoIntegrante";
	/**
	 * Consulta si una persona es beneficiario activo de un alfiliado principal distinto al referenciado
	 */
	public static final String CONSULTAR_BENFICIARIO_ACTIVO_AFILIADO = "Validaciones.beneficiario.ActivoAfiliado";
	/**
	 * Consulta si una persona es beneficiario activo de cualquier afiliado principal
	 */
	public static final String CONSULTAR_BENEFICIARIO_ACTIVO = "Validaciones.beneficiario.ActivoIndistintoAfiliado";
	/**
	 * Consulta si una persona se encuentra fallecida con una novedad de retiro asociada (Entidades Externas)
	 */
	public static final String CONSULTAR_PERSONA_FALLECIDA_NOVEDAD_ENTIDADES_EXTERNAS = "Validaciones.persona.FallecidaNovedadEntidadesExternas";
	
	/**
	 * Consulta si un jefe de hogar se encuentra inhabilitado para subsidio FOVIS
	 */
	public static final String CONSULTAR_JEFE_HOGAR_INHABILITADO_SUBSIDIO_FOVIS = "Validaciones.jefeHogar.InhabilitadoSubsidioFovis";
	/**
	 * Consulta si un beneficiario se encuentra habilitado para subsidio FOVSI
	 */
	public static final String CONSULTAR_BENEFICIARIO_INHABILITADO_SUBSIDIO_FOVIS ="Validaciones.benficiario.InhabilitadoSubsidioFovis";
	/**
	 * Consulta si los ahorros asociados a la postulacion se encuentran movilizados
	 */
	public static final String CONSULTAR_MOVILIZACION_AHORROS = "Validaciones.postulacion.AhorrosMovilizados";
	/**
	 * Consulta la fecha de asignación tope parametrizada para la solicitud
	 */
	public static final String CONSULTAR_FECHA_PARAMETRIZADA_ASIGNACION = "Validaciones.solicitudAsignacion.FechaTope";
	/**
	 * Consulta la decha de asignacion de la solicitud de postulación
	 */
	public static final String CONSULTAR_FECHA_ASIGNACION_SOLICITUD_POSTULACION = "Validaciones.solicitudPostulacion.FechaAsignacion";
	/**
	 * Consulta si la postulacion presenta un valor superior a 0 (cero) en alguno de los tipos de ahorro enviados por parametro
	 */
	public static final String CONSULTAR_AHORRO_PRESENTA_VALOR = "Validaciones.postulacion.AhorroConValor";
	/**
	 * Consulta si el afiliado principal se encuentra en estado activo con respecto a la caja de compensación familiar
	 */
	public static final String CONSULTAR_AFILIADO_PRINCIPAL_ACTIVO_CCF = "Validaciones.afiliado.ActivoCCF";

    /**
     * Busca el beneficio por tipo
     */
    public static final String BUSCAR_BENEFICIOS = "constantes.parametros.buscar.beneficio";
    
    /**
     * Consulta el número de integrantes asociados al hogar
     */
    public static final String CONSULTAR_HOGAR_INHABILITADO_SUBSIDIO_FOVIS = "Validaciones.hogar.Inhabilitado";
    /**
     * Consulta si una persona tiene asociada una novedad de conformacion de nuevo hogar
     */
	public static final String VALIDAR_PERSONA_REGISTRA_NOVEDAD_HOGAR = "Validaciones.persona.ConformacionNuevoHogar";
	/**
	 * Consulta si una persona esta asociada a una postulación en determinada modalidad
	 */
	public static final String VALIDAR_MODALIDAD_POSTULACION_PERSONA = "Validaciones.persona.ModalidadPostulacion";
	/**
	 * Consulta si una persona ha sido beneficiaria de subsidio de vivienda en uno de los estados enviados por parametro
	 */
	public static final String VALIDAR_ESTADO_HOGAR_PERSONA = "Validaciones.persona.EstadoHogar";

	/**
	 * Consulta el tipo de integrante ,asociado a un afiliado, y el nombre de una persona por tipo y numero de documento
	 */
    public static final String BUSCAR_TIPO_NOMBRES_PERSONA = "Validaciones.persona.BuscarTipoNombres";
    /**
     * Consulta el tipo de integrante ,asociado a un afiliado, y el nombre de una persona por tipo y numero de documento
     */
    public static final String BUSCAR_NOMBRES_PERSONA_POR_IDENTIFICACION = "Validaciones.persona.BuscarNombresPorIdentificacion";
	/**
	 * Consulta si una persona esta asociada a una postulación en determinada modalidad en la calidad de jefe de hogar 
	 */
	public static final String VALIDAR_MODALIDAD_POSTULACION_JEFE_HOGAR = "Validaciones.jefeHogar.ModalidadPostulacion";
	/**
	 * Consulta si una persona ha sido beneficiaria de subsidio de vivienda en uno de los estados enviados por parametro
	 * en condicion de jefe de hogar 
	 */
	public static final String VALIDAR_ESTADO_HOGAR_JEFE_HOGAR = "Validaciones.jefeHogar.EstadoHogar";
	
	/**
	 * Consulta si una persona ha sido beneficiaria de subsidio de vivienda en uno de los estados enviados por parametro
	 * en condicion de integrante
	 */
	public static final String VALIDAR_ESTADO_HOGAR_INTEGRANTE = "Validaciones.integrante.EstadoHogar";
	/**
	 * Consulta si un jefe de hogar dependiente se encuentra asociado a por lo menos un empleador que este al dia en aportes.
	 */
	public static final String VALIDAR_DEPENDIENTE_CON_EMPLEADOR_AL_DIA_APORTES = "Validaciones.jefehogar.DependienteConEmpleadorAlDiaAportes";
	/**
	 * Consulta si un jefe de hogar en calidad de independiente o pensionado se encuentra con los aportes al dia en cualquiera
	 * de sus afiliaciones activas
	 */
	public static final String VALIDAR_INDEPENDIENTE_PENSIONADO_AL_DIA_APORTES = "Validaciones.jefeHogar.IndependientePensiondoAlDiaAportes";
    /**
     * Consulta si una persona se encuentra inhabilitado para subsidio FOVIS
     */
    public static final String CONSULTAR_PERSONA_INHABILITADA_SUBSIDIO_FOVIS = "Validaciones.Persona.InhabilitadaSubsidioFovis";
    /**
     * Consulta si una persona se encuentra inhabilitado para subsidio FOVIS
     */
    public static final String OBTENER_PROMEDIO_INGRESOS_POR_APORTES_PERSONA = "Validaciones.Persona.consultarPromedioIngresosPorAportes";
    /**
     * Consulta el salario de la persona del hogar
     */
    public static final String OBTENER_SALARIO_PERSONA_HOGAR = "Validaciones.Persona.consultarSalario";
    /**
     * Consulta el valor en pesos del tope maximo de ingresos del hogar
     */
    public static final String OBTENER_TOPE_INGRESOS_HOGAR = "Validaciones.Persona.consultarTopeIngresosHogar";
    /**
     * Consulta que obtiene el total de los ingresos del jefe y de los integrantes de un hogar
     */
    public static final String OBTENER_INGRESOS_HOGAR = "Validaciones.Hogar.consultarIngresosHogar";
    /**
     * Consulta que obtiene registro si el ahorro previo igual o superior al 10% del valor de la solucion de vivienda
     */
    public static final String VALIDAR_AHORRO_PREVIO = "Validaciones.AhorroPrevio.validarContraValorProyectoVivienda";    
    
    /**
     * Consulta si existe novedad persona en curso para la persona
     */
    public static final String CONSULTA_NOVEDAD_PERSONA_EN_CURSO_POR_PERSONA = "Validaciones.Persona.buscarNovedadPersonaEnCursoPorPersona";
    
    /**
     * Consulta si existe novedad persona en curso para la persona, con tipo de transaccion de activación/inactivación de beneficiario
     */
    public static final String CONSULTA_NOVEDAD_PERSONA_EN_CURSO_POR_PERSONA_BENEFICICIARIO = "Validaciones.Persona.buscarNovedadPersonaEnCursoPorPersonaBeneficiario";
    
    /**
     * Consulta si la postulacion se encuentra sancionada por el id
     */
    public static final String CONSULTA_POSTULACION_SANCIONADA_POR_ID = "Validaciones.postulacion.sancionado.by.idPostulacion";
    
    /**
     * Consulta los certificados escolares registrados al beneficiario
     */
    public static final String CONSULTA_CERTIFICADOS_ESCOLARES_POR_TIPO_NUMERO_IDENTIFICACION = "validaciones.certificados.beneficiario.by.numero.tipo.identificacion";
    
    /**
     * Consulta la cantidad de miembros hogar asociados al identificador de postulacion 
     */
    public static final String CONSULTA_CANTIDAD_MIEMBROS_HOGAR = "validaciones.fovis.consultar.cantidad.miembros.hogar";

    /**
     * Consulta la cantidad de trabajadores asociados a la sucursal y el empleador en un estado especifico
     */
    public static final String CONSULTA_CANTIDAD_TRABAJADORES_ASOCIADOS_SUCURSAL_BY_ESTADO = "Validaciones.Empleador.sucursal.contarTrabajadoresActivos";

    /**
     * Constante que representa la consulta de la cantidad de beneficarios, con respecto a un afiliado, a los 
     * que se les ha pagado subsidio.
     */
    public static final String CONTAR_BENEFICIARIOS_AFILIADO_CON_SUBSIDIO = "Validaciones.Afiliado.contarBeneficiariosAfiliadoConSubsidio";
    
    /**
     * Consulta los beneficiarios asociados a un afilido principal por tipo y estado
     */
    public static final String BUSCAR_BENEFICIARIOS_POR_AFILIADO_TIPO_ESTADO = "Validaciones.Beneficiario.TipoAfiliadoYNumeroTipoAfiliado.estadoTipoBeneficiario";
    
    /**
     * Consulta si la persona está registrada como fallecida por canal presencial.
     */
    public static final String BUSCAR_PERSONA_POR_TIPO_NUMERO_FALLECIDO_CANAL_PRESENCIAL = "Validaciones.Persona.buscarPersonaPorTipoNumeroFallecido.canal";
    /**
     * Consulta el estado de la persona con respecto a la CCF
     */
    public static final String CONSULTAR_ESTADO_AFILIADO_CCF = "Validaciones.Afiliado.ConsultarEstadoRespectoCCF";
    
    /**
     * Consulta si la persona tiene al menos una afilación como trabajador dependiente activa
     */
    public static final String CONSULTAR_PERSONA_AFILIACION_DEPENDIENTE_ACTIVA = "Validaciones.Persona.trabajadorDependienteActivo";
    
    /**
     * Consulta si la última afiliación como dependiente de una persona se enecunetra en estado inactiva
     */
    public static final String CONSULTAR_PERSONA_AFILIACION_DEPENDIENTE_INACTIVA = "Validaciones.Pesona.trabajadorDependienteInactivo";
    
    /**
     * Consulta si el afiliado registra aportes
     */
    public static final String CONSULTAR_APORTES_AFILIADO = "Validaciones.Persona.aportesAfiliado";
    
    /**
     * Named query que consulta un beneficiario por tipo de documento y numero y
     * tipo beneficiario asociado a un grupo familiar
     */
    public static final String BUSCAR_BENEFICIARIO_POR_TIPO_NUMERO_TIPO_BENEFICIARIO_ESTADO_GRUP_FAM = "Validaciones.Beneficiario.buscarBeneficiarioPorTipoNumeroTipoBeneficiarioEstadoGrupFam";
    
    /**
     * Named query que consulta un beneficiario por numero documento y tipo
     * beneficiario estado asociado a un grupo familiar
     */
    public static final String BUSCAR_BENEFICIARIO_POR_NUMERO_TIPO_BENEFICIARIO_ESTADO_GRUP_FAM = "Validaciones.Beneficiario.buscarBeneficiarioPorNumeroTipoBeneficiarioEstadoGrupFam";
    
    /**
     * Consulta los id de las solicitudes de novedade que pueda tener asociadas la persona al momento de la consulta
     */
    public static final String CONSULTAR_SOLICITUD_NOVEDAD_EN_CURSO = "Validaciones.consultar.SolicitudNovedadEnCurso";

    /**
     * GLPI 89391 Consulta los tipo de transaciones de las solicitudes de novedades que pueda tener asociadas la persona al momento de la consulta
     */
    public static final String CONSULTAR_TIPO_SOLICITUD_NOVEDAD_EN_CURSO = "Validaciones.consultar.SolicitudTipoNovedadEnCurso";
    
    /**
     * Consulta los estados de las solicitudes de afiliación que pueda tener asociadas la persona al momento de la consulta
     */
    public static final String CONSULTAR_SOLICITUD_AFILIACION_EN_CURSO = "Validaciones.consultar.SolicitudAfiliacionEnCurso";
    
    /**
     * Consulta los beneficiarios tipo madre o padre que se encuentren activos o inactivos por motivo diferente a fallecimiento 
     * por número de identificacion y tipo de identificacion del afiliado
     */
    public static final String CONSULTAR_BENEFICIARIO_POR_TIPO_NUMERO_TIPO_ESTADO_MOTIVO_DESAFILIACION = "Validaciones.Beneficiario.buscarBeneficiarioPorTipoNumeroTipoEstadoMotivoDesafiliacion";
    
    /**
     * Consulta los beneficiarios tipo madre o padre que se encuentren activos o inactivos por motivo diferente a fallecimiento
     * por número de identificacion del afiliado 
     */
    public static final String CONSULTAR_BENEFICIARIO_POR_NUMERO_TIPO_ESTADO_MOTIVO_DESAFILIACION = "Validaciones.Beneficiario.buscarBeneficiarioPorNumeroTipoEstadoMotivoDesafiliacion";

    /**
	 * Constante para la consulta a la registraduria nacional
	 */
    public static final String BUSCAR_CONSULTA_REGISTRADURIA_NACIONAL = "Validaciones.consulta.informacionRegistraduriaNaciona.id";

    /**
	 * Constante para la consulta a la registraduria nacional
	 */
    public static final String EXISTE_REGISTRADURIA_NACIONAL = "Validaciones.consulta.existeRegistraduriaNacional";

    public static final String VALIDACION_FOVIS_PERSONA_VETERANO = "Validaciones.Persona.ValidarAfiliadoVeterano";

    public static final String VALIDAR_CONYUGE_CUIDADOR = "Validaciones.conyugeCuidador";

    /**
     * Constante para la consulta que trae la si la empresa y el trabajador estan en cartera
     */
    public static final String VALIDAR_JEFEHOGAR_DEPENDIENTE_TRABAJADOR_EMPLEADOR_AL_DIA_CARTERA = "Validaciones.jefeHogar.DependientetrabajadorEmpleadorAlDiaCartera";

    /**
     * Constante para la consulta que trae a cuantas empresa esta afiliado el trabajador
     */
    public static final String VALIDAR_JEFEHOGAR_EMPRESAS_AFILIADAS = "Validaciones.jefeHogar.consultarEmpresasAfiliadas";

    public static final String CONSULTAR_ESTADO_HOGAR_CIERRE_FINANCIERO = "Validaciones.Postulacion.EstadoHogar.cierre";

    public static final String ASP_JSON_VALIDACIONES_CARGUE_MULTIPLE = "sp.procedure.ASP_JsonPersona.validaciones.cargue.multiple";

    public static final String CONSULTAR_INGRESOS_HOGAR_FOVIS= "stored.procedure.validaciones.consultar.ingresosHogar";

    public static final String BUSCAR_BENEFICIARIO_TIPO_CONYUGE_HIJOS_COMPARTIDOS = "Validaciones.Persona.buscarBeneficiarioTipoConyugeHijosCompartidos";

}
