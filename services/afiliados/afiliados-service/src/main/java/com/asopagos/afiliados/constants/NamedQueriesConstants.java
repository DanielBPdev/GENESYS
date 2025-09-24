package com.asopagos.afiliados.constants;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * Clase que contiene las constantes con los nombres de los NamedQueries del
 * servicio <b>Módulo:</b> Asopagos - HU <br/>
 * Transversal
 * 
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
public class NamedQueriesConstants {
    
     /**
     * Actualiza un benefeciario masivamente
     */
    public static final String ACTUALIZAR_AFILIADO_MASIVAMENTE = "Afiliados.Actualizar.BeneficiarioMasivamente";


    /**
     * Buscar afiliados tipo y numero documento
     */
    public static final String BUSCAR_AFILIADOS_TIPO_NUMERO_DOCUMENTO = "Afiliados.buscar.tipo.numero.documento";

    /**
     * Buscar afiliados numero documento
     */
    public static final String BUSCAR_AFILIADOS_NUMERO_DOCUMENTO = "Afiliados.buscar.numero.documento";

    /**
     * Buscar afiliados datos fecha
     */
    public static final String BUSCAR_AFILIADOS_DATOS_FECHA = "Afiliados.buscar.datos.fecha";

    /**
     * Buscar afiliados datos fecha
     */
    public static final String BUSCAR_AFILIADOS_DATOS = "Afiliados.buscar.datos";

    /**
     * Buscar afiliados por el id del empledador
     */
    public static final String BUSCAR_TRABAJADORES = "Afiliados.buscar.trabajadores";

    /**
     * Buscar afiliados por el id del empleador y el estado
     */
    public static final String BUSCAR_TRABAJADORES_ESTADO = "Afiliados.buscar.trabajadores.estado";

    /**
     * Buscar rol afiliados
     */
    public static final String BUSCAR_ROL_AFILIADO_TIPO_NUMUMERO_DOCUMENTO = "Afiliados.buscar.rol.afiliado.tipo.numero";

    /**
     * Buscar rol afiliados
     */
    public static final String BUSCAR_ROL_AFILIADO = "Afiliados.buscar.rol.afiliado";

    /**
     * Buscar rol afiliados sucursal
     */
    public static final String BUSCAR_ROL_AFILIADO_SUCURSAL = "Afiliados.buscar.rol.afiliado.sucursal";

    /**
     * Buscar beneficiarios afiliados
     */
    public static final String BUSCAR_BENEFICIARIOS_AFILIADO = "Afiliados.buscar.beneficiarios";
    
    /**
     * Buscar beneficiarios afiliados que no hallan sido desafiliados
     */
    public static final String BUSCAR_BENEFICIARIOS_AFILIADO_SINDESAFILIACION = "Afiliados.buscar.beneficiarios.sindesafiliacion";
    

    /**
     * Buscar beneficiarios del grupo familiar
     */
    public static final String BUSCAR_BENEFICIARIOS_GRUPO_FAMILIAR = "Afiliados.buscar.beneficiarios.por.id.grupo";

    /**
     * Buscar beneficiarios por numero y tipo de documento
     */
    public static final String BUSCAR_BENEFICIARIOS_TIPO_NUMERO_DOCUMENTO = "Afiliados.buscar.beneficiarios.por.tipo.numero.documento";

    /**
     * Buscar beneficiarios afiliado por numero y tipo de documento
     */
    public static final String BUSCAR_BENEFICIARIOS_AFILIADO_TIPO_NUMERO_DOCUMENTO = "Afiliados.buscar.beneficiarios.afiliado.por.tipo.numero.documento";

    /**
     * Busca los beneficiarios asociados al afiliado, clasificacion y tipo y numero documento
     */
    public static final String BUSCAR_BENEFICIARIO_POR_AFILIADO_TIPO_NUMERO_DOCUMENTO_CLASIFICACION = "Afiliados.buscar.beneficiario.por.tipo.numero.documento.clasificacion.afiliado";

    /**
     * Buscar beneficiarios por afiliado y tipo de beneficiario
     */
    public static final String BUSCAR_BENEFICIARIOS_AFILIADO_TIPO_BENEFICIARIO = "Afiliados.buscar.beneficiarios.por.idAfiliado.tipoBeneficiario";

    /**
     * Buscar beneficiarios item chequeo afiliacion
     */
    public static final String BUSCAR_ITEM_CHEQUEO = "Afiliados.buscar.item.chequeo.afiliacion";

    /**
     * Buscar beneficiarios item chequeo afiliacion
     */
    public static final String BUSCAR_ITEM_CHEQUEO_BENEFICIARIO_NOVEDAD = "Afiliados.buscar.item.chequeo.beneficiario.novedad";

    /**
     * Buscar el grupo familiar del afiliado
     */
    public static final String BUSCAR_GRUPO_FAMILIAR_AFILIADO = "Afiliados.buscar.grupo.familiar";

    /**
     * Buscar el grupo familiar por id afiliado y numero de grupo familiar
     */
    public static final String BUSCAR_GRUPO_FAMILIAR_AFILIADO_NUMERO = "Afiliado.buscar.grupo.familiar.numero";

    /**
     * Buscar el tarjetas afiliado
     */
    public static final String BUSCAR_TARJETAS_AFILIADO = "Afiliados.buscar.tarjetas";

    /**
     * Buscar persona por tipo y numero identificacion
     */
    public static final String BUSCAR_PERSONA_TIPO_NUMERO_IDENTIFICACION = "Afiliados.buscar.persona";

    /**
     * Buscar grupo familiar
     */
    public static final String BUSCAR_GRUPO_FAMILIAR = "Afiliado.buscar.grupo.familiar";
    
    /**
     * Buscar rol afiliado
     */
    public static final String BUSCAR_ROL_AFILIADO_ID_TIPO = "Afiliado.buscar.rolAfiliado.id.tipo";

    /**
     * Retorna el numero del grupo familiar
     */
    public static final String RETORNAR_NUMERO_CONSECUTIVO_GRUPO_FAMILIAR = "Afiliado.buscar.numero.grupo.familiar";

    /**
     * Retorna el id del empleador
     */
    public static final String BUSCAR_EMPLEADOR_ID = "Afiliado.buscar.empleador.id";

    /**
     * Actualizar un item chequeo por el id requisito y el id de la persona
     */
    public static final String BUSCAR_ITEM_CHEQUEO_ID_REQUISITO_Y_ID_PERSONA = "Afiliado.buscar.itemChequeo.idRequisito.idPersona";
    
    /**
     * Consulta los item de chequeo de una solicitud para la persona enviada 
     */
    public static final String BUSCAR_ITEM_CHEQUEO_POR_ID_SOLICITUD_ID_PERSONA = "buscar.itemChequeo.idSolicitud.idPersona";

    /**
     * Buscar el estadoRolAfiliado por tipo identificacion, numero
     * identificacion y tipoAfiliado
     */
    public static final String BUSCAR_ROLAFILIADO_TIPO_IDENTIFICACION_NUMERO_TIPOAFILIADO = "Afiliado.buscar.rolAfiliado.tipoIdentificacion.numIdentificacion.tipoAfiliado";

    /**
     * Buscar el estadoRolAfiliado por tipo identificacion, numero
     * identificacion y tipoAfiliado
     */
    public static final String BUSCAR_ROLAFILIADO_TIPO_IDENTIFICACION_NUMERO_TIPOAFILIADO_EMPLEADOR = "Afiliado.buscar.rolAfiliado.tipoIdentificacion.numIdentificacion.tipoAfiliado.idEmpleador";

    /**
     * Buscar el rol afiliado por el id del empleador y los id de las sucursales.
     */
    public static final String BUSCAR_ROLAFILIADO_ID_EMPLEADOR_ID_SUCURSALES = "Afiliado.buscar.rolAfiliado.idEmpleador.idSucursales";
    /**
     * Buscar el rol afiliado por el id del empleador y los id de las sucursal.
     */
    public static final String BUSCAR_ROLAFILIADO_ID_EMPLEADOR_ID_SUCURSAL = "Afiliado.buscar.rolAfiliado.idEmpleador.idSucursal";

    /**
     * Buscar la categoria del afiliado por tipo identificacion, numero
     * identificacion
     */
    public static final String BUSCAR_CATEGORIA_ROLAFILIADO_TIPO_IDENTIFICACION_NUMERO = "Afiliado.buscar.categoria.rolAfiliado.tipoIdentificacion.numIdentificacion";
    /**
     * Buscar la categoria del afiliado por tipo identificacion, numero
     * identificacion
     */
    public static final String BUSCAR_CATEGORIA_SOLICITUD_AFILIADO_PERSONA_TIPO_IDENTIFICACION_NUMERO = "Afiliado.buscar.categoria.solicitudAfiliadoPersona.tipoIdentificacion.numIdentificacion";
    /**
     * Buscar la categoria del afiliado por tipo identificacion, numero
     * identificacion
     */
    public static final String BUSCAR_CATEGORIA_AFILIADO_PERSONA_TIPO_IDENTIFICACION_NUMERO = "Afiliado.buscar.categoria.tipoIdentificacion.numIdentificacion";
    /**
     * Buscar la categoria del afiliado por tipo identificacion, numero
     * identificacion
     */
    public static final String BUSCAR_CATEGORIA_BENEFICIARIO_PERSONA_TIPO_IDENTIFICACION_NUMERO = "Afiliado.buscar.categoria.beneficiario.tipoIdentificacion.numIdentificacion";
    /**
     * Buscar Afiliado por id del afiliado
     */
    public static final String BUSCAR_AFILIADO_ID_AFILIADO = "Afiliado.buscar.afiliado.idAfilado";
    /**
     * Buscar Beneficiario por id del beneficiario
     */
    public static final String BUSCAR_BENEFICIARIO_ID_BENEFICIARIO = "Afiliado.buscar.beneficiario.idBeneficiario";

    /**
     * Buscar Beneficiario por id del beneficiario
     */
    public static final String BUSCAR_BENEFICIARIO_NUMERO_TIPO_IDENTIFICACION_ID_AFILIADO = "Afiliado.buscar.beneficiario.tipoIdentificacion.numIdentificacion.idAfiliado";
    /**
     * Buscar Beneficiario por tipo y numero de identificacion
     */
    public static final String BUSCAR_BENEFICIARIO_TIPO_IDENTIFICACION_NUMERO = "Afiliado.buscar.beneficiario.tipoIdentificacion.numIdentificacion";
    /**
     * Buscar Categoria de un beneficiario por id de Beneficiario
     */
    public static final String BUSCAR_CATEGORIA_BENEFICIARIO_ID_BENEFICIARIO = "Afiliado.buscar.categoria.beneficiario.id.beneficiario";
    
    /**
     * Buscar Categoria asociada al beneficiario si tiene Novedad asociada.
     */
    public static final String BUSCAR_CATEGORIA_BENEFICIARIO_NOVEDAD= "Afiliado.buscar.categoria.novedadAsociada";

    /**
     * Buscar Roles afiliación por tipo y número de identificacion de una persona afiliada y tipo de afiliado.
     */
    public static final String BUSCAR_ROL_AFILIADO_POR_TIPO_NUMERO_PERSONA_TIPO_AFILIADO = "Afiliado.buscar.rolAfiliado.persona.tipoAfiliado";

    /**
     * Buscar Roles afiliación por ids de personas afiliadas y empledor.
     */
    public static final String BUSCAR_ROL_AFILIADO_POR_IDS_PERSONA_EMPLEADOR = "Afiliado.buscar.rolAfiliado.personas.empleador";

    /**
     * Buscar Persona Detalle por el id de una persona
     */
    public static final String BUSCAR_PERSONADETALLE_ID_PERSONA = "Afiliado.buscar.personaDetalle.id.Persona";
    /**
     * Buscar Persona Detalle por el numero y tipo de identificacion de una persona
     */
    public static final String BUSCAR_PERSONADETALLE_TIPO_NUMERO_IDENTIFICACION = "Afiliado.buscar.personaDetalle.tipo.numero.identificacion";
    /**
     * Buscar sucursal por el id de una sucursal
     */
    public static final String BUSCAR_SUCURSAL_ID = "Afiliado.buscar.sucursal.id";
    /**
     * Buscar municipio por le id de un Municipio
     */
    public static final String BUSCAR_MUNICIPIO_ID = "Afiliado.buscar.municipio.id";
    /**
     * Buscar rol afiliado por id
     */
    public static final String BUSCAR_ROL_AFILIADO_ID = "Afiliados.buscar.rol.afiliado.id";
    /**
     * Buscar grupo familiar por id
     */
    public static final String BUSCAR_GRUPO_FAMILIAR_ID = "Afiliado.buscar.grupo.familiar.id";
    /**
     * Buscar grupo familiar por id
     */
    public static final String BUSCAR_ROL_AFILIADO_IDPERSONA_TIPOAFILIADO = "Afiliado.buscar.rol.afiliado.idpersona.tipoafiliado";
    /**
     * Buscar las clasificaciones por tipo y número de identificacion del afiliado.
     */
    public static final String BUSCAR_CLASIFICACIONES_TIPO_IDENTIFICACION_NUMERO_IDENTIFICACION = "Clasificaciones.buscar.clasificaciones.tipoIdentificacion.numeroIdentificacion";
    /**
     * Buscar los roles afiliados a un empleador.
     */
    public static final String BUSCAR_ROLAFILIADO_EMPLEADOR = "RolAfiliado.buscar.idEmpleador";
    /**
     * Buscar los roles afiliados a un empleador y estado afiliado.
     */
    public static final String BUSCAR_ROLAFILIADO_EMPLEADOR_ESTADO = "RolAfiliado.buscar.idEmpleador.estadoAfiliado";

    /**
     * Consulta un Beneficiario por identificación.
     */
    public static final String CONSULTAR_BENEFICIARIO_POR_ID = "Novedades.Beneficiario.consultarBeneficiarioId";

    /**
     * Consulta una Persona Detalle por Id persona
     */
    public static final String CONSULTAR_PERSONA_DETALLE_ID_PERSONA = "Novedades.PersonaDetalle.consultarPorId";

    /**
     * Consulta un Grupo Familiar por identificación.
     */
    public static final String CONSULTAR_GRUPOFAMILIAR_POR_ID = "Novedades.GrupoFamiliar.consultarGrupoFamiliarId";

    /**
     * Consulta un Rol Afiliado por identificación.
     */
    public static final String CONSULTAR_ROLAFILIADO_POR_ID = "Novedades.RolAfiliado.consultarRolAfiliadoId";

    /**
     * Consulta las personas con vencimiento de incapacidades.
     */
    public static final String CONSULTAR_VENCIMIENTO_INCAPACIDADES = "Novedades.Persona.consultarVencimientoIncapacidades";

    /**
     * Consulta las novedades por vencimiento de incapacidad.
     */
    public static final String CONSULTAR_NOVEDADES_INACTIVAR = "Novedades.NovedadPila.consultaNovedadVencimientoIncapacidad";

    /**
     * Consulta un afiliado por tipo y número de Identificación.
     */
    public static final String CONSULTAR_AFILIADO_TIPO_NUMERO_ID = "Novedades.Afiliado.consultaAfiliadoTipoNumId";

    /**
     * Consulta si existen roles en estado diferente a inactivo para el afiliado
     */
    public static final String CONSULTAR_EXISTEN_ROLES_ACTIVOS = "Novedades.RolAfiliado.consultarExistenRolesActivos";

    /**
     * Consulta Si existe un administrador del Subsidio asociado a la persona
     */
    public static final String CONSULTAR_ADMINISTRADOR_DE_SUBSIDIO_POR_PERSONA = "Novedades.AdminSubsidio.consultarPersonaAdmonSubsidio";

    /**
     * Consulta el Administrador del Subsidio asociado al Grupo Familiar.
     */
    public static final String CONSULTAR_ADMONSUBSIDIO_ASOCIADO_A_GRUPOFAMILIAR = "Afiliaciones.AdministradorSubsidio.consultarPorGrupo";

    public static final String AFILIADOS_ACTUALIZAR_TIPOPAGO_MEDIOPAGO = "Afiliados.Actualizar.TipoPago.MedioPago";
    public static final String CONSULTAR_MEDIODEPAGO_PERSONA = "Afiliaciones.MedioDePago.consultarMedioPagoPersona";
    public static final String CONSULTAR_MEDIODEPAGO_GRUPOFAMILIAR = "Afiliaciones.MedioDePago.consultarMedioPagoGrupoFamiliar";
    public static final String CONSULTAR_MEDIODEPAGO_ACTIVO_GRUPOFAMILIAR = "Afiliaciones.MedioDePago.consultarMedioPagoActivoGrupoFamiliar";
    public static final String CONSULTAR_MEDIODEPAGO_PERSONA_ESTADO = "Afiliaciones.MedioDePago.consultarMedioPagoPersonaEstado";
    public static final String CONSULTAR_MEDIODEPAGO_PERSONA_ESTADO_2 = "Afiliaciones.MedioDePago.consultarMedioPagoPersonaEstado.2";
    public static final String CONSULTAR_MEDIODEPAGO_GRUPOFAMILIAR_TIPO = "Afiliaciones.MedioDePago.consultarMedioPagoGrupoFamiliarTipo";
    public static final String CONSULTAR_MEDIODEPAGO_PERSONA_TIPO = "Afiliaciones.MedioDePago.consultarMedioPagoPersonaTipo";
    public static final String CONSULTAR_MEDIODEPAGO_ADMONSUB = "Afiliaciones.MedioDePago.consultarMedioPagoAdminSubsidio";
    public static final String CONSULTAR_MEDIODEPAGO_ADMONSUB_TIPO = "Afiliaciones.MedioDePago.consultarMedioPagoAdminSubsidioTipo";

    public static final String CONSULTAR_MEDIODEPAGO_PERSONA_TARJETA = "Afiliaciones.MedioDePago.consultarMedioPagoTarjetaPersona";

    /**
     * Consulta un rol afiliado en base a los datos del empleador y la persona (potencial afiliado).
     */
    public static final String BUSCAR_ROLAFILIADO_POR_EMPLEADOR_Y_PERSONA = "Novedades.RolAfiliado.buscarAfiliadoPorEmpleadorYPersona";

    /**
     * Consultar un rol afiliado en base a los datos de la persona
     */
    public static final String BUSCAR_ROLAFILIADO_POR_PERSONA = "Novedades.RolAfiliado.buscarAfiliadoPorPersona";

    /**
     * Consultar un rol afiliado en base a los datos del empleador
     */
    public static final String BUSCAR_ROLAFILIADO_POR_EMPLEADOR = "Novedades.RolAfiliado.buscarAfiliadoPorEmpleador";

    /**
     * Consulta los benefeciarios mayores de edad con TarjetaIdentidad y sin reporte de invalidez
     */
    public static final String CONSULTAR_BENEFICIARIOS_MAYOR_EDAD_CON_TI = "Novedades.Beneficiario.consultarBeneficiarioMayorEdadConTI";

    /**
     * Consulta los benefeciarios por identificadores de beneficiario
     */
    public static final String CONSULTAR_BENEFICIARIOS_RETIRO_POR_ID = "Novedades.Beneficiario.consultarBeneficiarioXId";

    /**
     * Consulta los beneficiarios por edad
     */
    public static final String CONSULTAR_BENEFICIARIOS_POR_EDAD = "Novedades.Beneficiario.consultarBeneficiarioXEdad";

    /**
     * Consulta los beneficiarios de X edad para cambio de categoria
     */
    public static final String CONSULTAR_BENEFICIARIOS_CAMBIO_CATEGORIA_POR_EDAD = "Novedades.Beneficiario.consultarBeneficiarioCambioCategoriaXEdad";

    /**
     * Consulta la categoria de los beneficiarios por los identificadores de beneficiario
     */
    public static final String CONSULTAR_CATEGORIA_POR_ID_BENEFICIARIO = "Novedades.Beneficiario.consultarCategoriaXIdBeneficiario";
       /**
     * Consulta la categoria del listado si no existe se crea
     */
    public static final String CONSULTAR_CATEGORIA_BENEFICIARIO_SI_EXISTE_CATEGORIA = "Novedades.Beneficiario.verificarsiexisteencategorias";
       /**
     * Consulta los beneficiarios hijos mayores a 18 y menores de 23 para cambio de categoria 
     */
    public static final String CONSULTAR_BENEFICIARIOS_CAMBIO_CATEGORIA_POR_TECNICA_LABORAL_HEREDADAS = "Novedades.Beneficiario.consultarBeneficiarioCambioCategoriaCircularUnicaHeredadas";
 
    /**
     * Consulta la categoria del listado si no existe se crea
     */
    public static final String CONSULTAR_DATOS_AFILIADO_PARA_CATEGORIA_BENEFICIARIO = "Novedades.Beneficiario.consultar.datos.afiliado.categoria";

    /**
     * consulta de marca para traslado empresas ccf
     */
    public static final String CONSULTAR_MARCA_TRASLADO_EMPRESAS_CCF = "Novedades.Empleador.Consultar.Marca.Traslado.Empresas.Ccf";
    /**
     * actualizacion de marca para traslado empresas ccf
     */
    public static final String ACTUALIZAR_MARCA_TRASLADO_EMPRESAS_CCF = "Novedades.Empleador.Actualizar.Marca.Traslado.Empresas.Ccf";
    /**
     * Consulta la categoria de los beneficiarios por los identificadores de beneficiario
     */
    /**
     * Consulta la categoria de los beneficiarios por los identificadores de beneficiario
     */
    public static final String CONSULTAR_CATEGORIA_POR_ID_BENEFICIARIO_INACTIVOS = "Novedades.Beneficiario.consultar.categorias.inactivos";
     /**
     * Consulta los beneficiarios padres menores a 60 para cambio de categoria
     */
    public static final String CONSULTAR_BENEFICIARIOS_CAMBIO_CATEGORIA_PADRES_MENORES_X_EDAD = "Novedades.Beneficiario.consultarBeneficiarioCambioCategoriaCircularUnicaPadres";
     /**
     * Consulta los beneficiarios hijos mayores a 18 y menores de 23 para cambio de categoria
     */
    public static final String CONSULTAR_BENEFICIARIOS_CAMBIO_CATEGORIA_POR_CERTIFICADO_ESCOLAR = "Novedades.Beneficiario.consultarBeneficiarioCambioCategoriaCircularUnicaHijos";

    /**
     * Buscar los roles afiliados a un empleador y estado afiliado.
     */
    public static final String BUSCAR_ROLAFILIADO_EMPLEADORES_MASIVO = "RolAfiliado.empleador.buscarMasivoRolesAfiliado";

    /**
     * Buscar los aportes asociados a una persona a un periodo determinado.
     */
    public static final String BUSCAR_PERSONAS_MORA_APORTES = "Novedades.Persona.consultarPersonasMoraAportes";

    /**
     * Busca los Roles Afiliado asociados a una lista de personas.
     */
    public static final String BUSCAR_ROLAFILIADO_PERSONAS_TIPOAFILIADO = "RolAfiliado.buscar.listaPersonas.tipoafiliado";

    /**
     * Buscar Beneficiario por id del beneficiario e id de afiliados
     */
    public static final String BUSCAR_BENEFICIARIO_ID_BENEFICIARIO_ID_AFILIADO = "Afiliado.buscar.beneficiario.idBeneficiario.idAfiliado";

    /**
     * Buscar afiliado por tipo y numero de documento del mismo y del empleador
     */
    public static final String BUSCAR_AFILIADO_POR_TIPO_Y_NUMERO_DE_ID_DEL_AFI_Y_EMPLEDOR = "RolAfiliado.buscar.rolAfiliado.por.tipo.numero.afi.y.empleador";

    /**
     * Consulta los beneficiarios por tipo y nro documento de la persona
     */
    public static final String BUSCAR_BENEFICIARIO_TIPO_NRO_DOCUMENTO = "Afiliado.buscar.beneficiario.tipo.nro.identificacion";

    /**
     * Consulta de tipos de infraestructura
     */
    public static final String CONSULTAR_TIPO_INFRAESTRUCTURA = "Afiliado.TipoInfraestructura.ConsultarTiposInfraestructura";

    /**
     * Consulta de infraestructuras
     */
    public static final String CONSULTAR_INFRAESTRUCTURA = "Afiliado.Infraestructura.ConsultarInfraestructuras";

    /**
     * Consulta de sitios de pago
     */
    public static final String CONSULTAR_SITIO_PAGO = "Afiliado.SitioPago.ConsultarSitiosPago";
    /**
     * Consulta el sitio de pago predeterminado
     */
    public static final String CONSULTAR_ID_SITIO_PAGO_PREDETERMINADO = "Afiliado.SitioPago.consultarIdSitioPagoPredeterminado";

    /**
     * Consulta de tipos de tenencia
     */
    public static final String CONSULTAR_TIPO_TENENCIA = "Afiliado.TipoTenencia.ConsultarTiposTenencia";

    /**
     * Consulta el siguiente número consecutivo para el código de la infraestructura
     */
    public static final String CONSULTAR_SIGUIENTE_CONSECUTIVO = "Afiliado.Infraestructura.ConsultarSiguienteConsecutivo";

    /**
     * Consulta infraestructuras de acuerdo a criterios para presentar en pantalla
     */
    public static final String CONSULTAR_INFRAESTRUCTURA_PANTALLA = "Afiliado.Infraestructura.ConsultarInfraestructuraParaPantalla";

    /**
     * Consulta sitios de pago de acuerdo a criterios para presentar en pantalla
     */
    public static final String CONSULTAR_SITIO_PAGO_PANTALLA = "Afiliado.SitioPago.ConsultarSitioPagoParaPantalla";

    /**
     * Consulta del siguiente código de sitio de pago a asignar durante la creación de sitios de pago
     */
    public static final String CONSULTAR_SIGUIENTE_CONSECUTIVO_SITIO_PAGO = "Afiliado.SitioPago.ConsultarSiguienteCodigo";
    /**
     * Control de cambios:0226824
     * Consulta encargada de contar las afiliaciones por estado activo
     */
    public static final String CONTAR_AFILIACIONES_ESTADO_ACTIVO = "Afiliado.contar.afiliaciones.estado.activo";
    /**
     * 
     * Control de cambios:0226824
     * Consultar los beneficiarios de la afiliacion activo
     */
    public static final String BUSCAR_AFILIACIONES_AFILIADO = "Afiliado.beneficiario.afiliacion.estado";
    /**
     * Constante que tiene la llave de la consulta para buscar beneficiarios de una afiliacion
     */
    public static final String BUSCAR_BENEFICIARIOS_POR_AFILIACIONES_AFILIADO = "Afiliado.beneficiario.afiliacion";
    /**
     * Control de cambios:0226824
     * Consulta encargada de consultar los beneficiarios de la afiliacion respecto al afiliado principal
     */
    public static final String BUSCAR_BENEFICIARIOS_AFILIACIONES_AFILIADO_PRINCIPAL = "Afiliado.beneficiario.afiliaciones.afiliadoPrincipal";
    /**
     * Control de cambios:0226824
     * Consulta encargada de buscar beneficiarios por afiliaciones del afiliado principals
     */
    public static final String BUSCAR_BENEFICIARIO_AFILIACIONES_AFILIADO_PRINCIPAL = "Afiliado.beneficiario.afiliaciones.afiliadoPrincipalEmpleador";

    /**
     * Constante con el nombre de la consulta que obtiene el valor de ingresos mensuales de un afiliado a la caja de compensación
     */
    public static final String FOVIS_CONSULTAR_INGRESOMENSUAL_AFILIADO = "Fovis.Consultar.IngresoMensual.Afiliado";

    /**
     * Constante con el nombre de la consulta que obtiene el valor de ingresos mensuales de un Integrante de Hogar
     */
    public static final String FOVIS_CONSULTAR_INGRESOMENSUAL_INTEGRANTE = "Fovis.Consultar.IngresoMensual.IntegranteHogar";

    /**
     * Constante con el nombre de la consulta que obtiene el un beneficiario asociado a un afiliado.
     */
    public static final String FOVIS_CONSULTAR_INGRESO_BENEFICIARIO_ASOCIADO_AFILIADO = "Beneficiario.buscar.por.tipo.nroIdentificacion.estado";

    public static final String CONSULTAR_CONDICION_INVALIDEZ_ID_PERSONA_CONYUGE = "consultar.conficion.invalidez.idPersonaConyuge";

    /**
     * Buscar Beneficiario por id del beneficiario
     */
    public static final String BUSCAR_CONDICION_INVALIDEZ_BENEFICIARIO = "Afiliado.buscar.condicionInvalidez.beneficiario";

    /**
     * Buscar Beneficiario por id del beneficiario
     */
    public static final String BUSCAR_CONDICION_INVALIDEZ_BENEFICIARIO_POR_ID = "Afiliado.buscar.condicionInvalidez.beneficiario.porId";
    /**
     * Buscar beneficiario afiliado
     */
    public static final String BUSCAR_BENEFICIARIOS_AFILIADO_ACTIVO = "Afiliado.beneficiario.afiliacion.activo";
    /**
     * Constante encargada de buscar las afiliaciones por persona
     */
    public static final String BUSCAR_AFILIACIONES_POR_PERSONA = "Afiliado.rolAfiliado.buscarAfiliacionesPersona";
    /**
     * Constante que se encarga de buscar un empleador por tipo y número de identificación para la persona
     */
    public static final String BUSCAR_EMPLEADOR_POR_TIPO_Y_NUMERO_IDENTIFICACION = "Afiliado.empleador.buscarEmpleadorPorTipoYNumeroIdentificacion";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de aportes generales de una persona independiente o pensionada
     */
    public static final String CONSULTAR_APORTE_GENERAL_PERSONA = "Aportes.Consultar.AporteGeneral.Persona";

    /**
     * Constante con el nombre de la consulta que obtiene la lista de aportes detallados de independientes o pensionados, de acuerdo a una
     * lista de ids de aportes general
     */
    public static final String CONSULTAR_APORTE_DETALLADO_IDS_GENERAL_PERSONA = "Aportes.Consultar.AporteDetallado.AporteGeneral.Persona";

    /**
     * Consulta una Persona Detalle por Id persona
     */
    public static final String CONSULTAR_BENEFICIARIO_DETALLE_ID_PERSONADETALLE = "Beneficiario.BeneficiarioDetalle.consultarPorId";

    /**
     * Constante con el nombre de la consulta que obtiene el afiliado o afiliados que esten asociados
     * con los filtros correspondientes.
     */
    public static final String CONSULTAR_AFILIADO_POR_FILTROS = "Afiliado.buscar.afiliados.por.filtros";

    /**
     * Constante con el nombre de la consulta que obtiene el beneficiario o beneficiarios que esten asociados
     * con los filtros correspondientes.
     */
    public static final String CONSULTAR_BENEFICIARIOS_POR_FILTROS = "Beneficiario.buscar.beneficiarios.por.filtros";
    /**
     * Constante con el nombre de la consulta que trae los trabajadores pertenecientes a un empleador
     */
    public static final String BUSCAR_TRABAJADORES_EMPLEADOR = "Afiliado.Empleador.buscarTrabajadoresEmpleador";

    /**
     * Constante con el nombre de la consulta que obtiene un rolAfiliado especifico con empleador
     * 
     */
    public static final String CONSULTAR_ROLAFILIADO_POR_FILTROS_CON_EMPLEADOR = "RolAfiliado.buscar.rolAfiliado.especifico.por.filtros.conEmpleador";

    public static final String CONSULTAR_ROLAFILIADO_POR_EMPRESA = "RolAfiliado.buscar.rolAfiliado.especifico.conEmpresa";

    
    /**
     * Constante con el nombre de la consulta que obtiene un rolAfiliado especifico sin empleador
     * 
     */
    public static final String CONSULTAR_ROLAFILIADO_POR_FILTROS_SIN_EMPLEADOR = "RolAfiliado.buscar.rolAfiliado.especifico.por.filtros.sinEmpleador";

    /**
     * Constante con el nombre de la consulta que trae los beneficiarios asociados al grupo familiar indicado por parametro
     */
    public static final String CONSULTAR_BENEFICIARIOS_POR_GRUPO_FAMILIAR = "Beneficiario.buscar.por.grupo.familiar";

    
    /**
     * Constante para la consulta de una solicitud asociada a una persona
     */
    public static final String CONSULTAR_SOLICITUD_PERSONA = "Afiliados.buscarSolicitudPersonaa.solicitud";
    
    /**
     * Constante para la consulta de la información básica de la persona respecto a la afiliación por afiliado y beneficiarios
     */
    public static final String OBTENER_INFO_BASICA_PERSONA = "Afiliados.Obtener.infoBasicaPersona";
    
    /**
     * Constante para la consulta de la información detallada de la persona respecto a la afiliación por afiliado y beneficiarios
     */
    public static final String OBTENER_INFO_TOTAL_PERSONA = "Afiliados.Obtener.infoTotalPersona";
    
    /**
     * Constante para la consulta de la información detallada restante de la persona respecto a la afiliación por afiliado y beneficiarios
     * que no puede ser obtenida con la consulta com.asopagos.afiliados.constants.NamedQueriesConstants.OBTENER_INFO_TOTAL_PERSONA
     */
    public static final String OBTENER_INFO_TOTAL_RESTANTE_PERSONA = "Afiliados.Obtener.infoTotalRestantePersona";

    /**
     * Constante que obtiene la información detallada de un afiliado cuando este es del tipo pensionado
     */
    public static final String OBTENER_INFO_DETALLADA_AFILIADO_PENSIONADO = "Afiliados.obtener.infoDetaladaAfiliadoPensionado";

    /**
     * Constante que obtiene la información detallada de un afiliado cuando este es del tipo trabajador independiente
     */
    public static final String OBTENER_INFO_DETALLADA_AFILIADO_INDEPENDIENTE = "Afiliados.obtener.infoDetaladaAfiliadoIndependiente";
    
    /**
     * Constante que obtiene las categorías de un beneficiario Conyuge Activo
     */
    public static final String CONSULTAR_CATEGORIA_CONYUGE_AFILIADO = "Afiliados.consultar.categoriaConyugeAfiliado";
    
    /**
     * Consulta los tipos de afiliacion con los que está registrada una persona dada.
     */
    public static final String CONSULTAR_TIPOS_AFILIACION_AFILIADO = "Afiliados.consultar.tiposAfiliacionAfiliado";
    
    /**
     * consulta la información del estado del afiliado dependiente con respecto al empleador dado
     */
    public static final String CONSULTAR_INFO_ESTADO_AFILIADO = "Afiliados.consultar.infoEstadoAfiliado";

    /**
     * consulta la información relacionada a las novedades de vacaciones y/o suspenció del trabajador, dado un afiliado
     */
    public static final String INFO_VACACIONES_Y_SUSPENSION = "Afiliados.consultar.InfoVacacionesYSuspencion";
    
    /**
     * consulta la información relacionada al ultimo aporte hecho por o en nombre del afiliado
     */
    public static final String CONSULTAR_INFO_ULTIMO_APORTE = "Afiliados.consultar.infoUltimoAporte";
    
    /**
     * consulta y obtiene los empleadores relacionados al afiliado que cumplan con los criterios de busqueda (tipo y número de identificación)
     */
    public static final String OBTENER_EMPLEADORES_RELACIONADOS_AFILIADO_TIPO_NUM_ID = "Afiliados.obtener.empleadorRelacionadoAfiliadoPorTipoNumeroId";
    
    /**
     * consulta y obtiene los empleadores relacionados al afiliado que cumplan con los criterios de busqueda (razón social)
     */
    public static final String OBTENER_EMPLEADORES_RELACIONADOS_AFILIADO_RAZON_SOCIAL = "Afiliados.obtener.empleadorRelacionadoAfiliadoPorRazonSocial";
    
    /**
     * consulta y obtiene las categorias actuales del afiliado principal
     */
    public static final String OBTENER_CATEGORIA_ACTUAL_AFI_PRINCIPAL = "Afiliados.obtener.categoriaActualAfiliadoPrincipal";
    
    /**
     * consulta y obtiene las categorias heredadas de la persona como beneficiario del afiliado principal
     */
    public static final String CONSULTAR_CATEGORIA_HEREDADA_AFI_PPAL = "Afiliados.Consultar.categoriaHeredadaAfiPpal";
    
    /**
     * consulta y obtiene la información de la relación laboral entre un afiliado y su empleador dado el id de rol afiliado
     */
    public static final String CONSULTAR_INFO_RELACION_LABORAL = "Afiliados.Consultar.infoRelacionLaboral";
    
    /**
     * consulta y obtiene la información histórica de un afiliado como beneficiario
     */
    public static final String CONSULTAR_HISTORICO_AFILIADO_COMO_BENEFICIARIO = "Afiliados.Consultar.historicoAfiliadoComoBeneficiario";
    
    /**
     * Consulta del estado de afiliación respecto a la caja de una persona
     * */
    public static final String CONSULTAR_ESTADO_AFILIACION_RESPECTO_CCF = "Afiliados.Consultar.estadoAfiliacionCaja";
    
    /**
     * Consulta del estado de afiliación respecto a la caja de una persona dependiente
     * */
    public static final String CONSULTAR_ESTADO_AFILIACION_DEPENDIENTE = "Afiliados.Consultar.estadoAfiliacionCaja.dependiente";
    
    /**
     * Consulta del estado de afiliación respecto a la caja de una persona independiente
     * */
    public static final String CONSULTAR_ESTADO_AFILIACION_INDEPENDIENTE = "Afiliados.Consultar.estadoAfiliacionCaja.independiente";
    
    /**
     * Consulta del estado de afiliación respecto a la caja de una persona pensionada
     * */
    public static final String CONSULTAR_ESTADO_AFILIACION_PENSIONADO = "Afiliados.Consultar.estadoAfiliacionCaja.pensionado";

    /**
     * Consulta de trabajadores dependientes por aportes de un empleador y no formalizados sin afiliación con aportes
     * */
    public static final String CONSULTAR_TRABAJADORES_POR_APORTES = "Afiliados.Consultar.trabajadoresSinAfiliacionConAportes";

    /**
     * Consulta el último certificado escolar registrado asociada a la persona por ID PERSONA
     */
    public static final String CONSULTAR_CERTIFICADO_ESCOLAR_POR_ID_PERSONA = "consultar.ultimo.certificadoEscoloar.by.persona.id";
    
    /**
     * consulta que representa la consulta del estado actual del afiliado en core con respecto al empleador
     */
    public static final String CONSULTAR_ESTADO_AFI_ACTUAL_AFILIADO_RESP_EMPLEADOR = "consulta.afiliado.estadoActualAfiliadoRespectoEmpleador";
    
    /**
     * constante que representa la consulta del estado actual del afilaido en core con respecto al empleador buscando este ultimpo por el id de la empresa
     */
    public static final String CONSULTAR_ESTADO_AFI_ACTUAL_AFILIADO_RESP_ID_EMPRESA = "consulta.afiliado.estadoActualAfiliadoRespectoEmpleadorConIdEmpresa";
    
    /**
     * cnstante que representa la consulta del nombre d ela sucursal asociada a un rol afiliado dado.
     */
    public static final String CONSULTAR_NOMBRE_SUCURSAL = "Consulta.nombreSucursalEmpleadorRolAfiliado";
    
    /**
     * constante que representa la consulta de la relación laboral por aportes de un afiliado con un empleador 
     */
    public static final String CONSULTAR_RELACION_COMO_DEPENDIENTE_POR_APORTES = "Consulta.relacionComoDependientePorAportes";
    
    /**
     * constante que representa la consulta de un empleador relacionado por aportes a un afiliado dado el tipo y numero de identificacion del empleador
     */
    public static final String CONSULTAR_EMPLEADOR_POR_APORTES_CON_TIPO_Y_NUMERO_ID = "Consulta.empleadorPorAportesConTipoYNumeroId";
    
    /**
     * constante que representa la consulta de un empleador relacionado por aportes a un afiliado dada la razón social del empleador
     */
    public static final String CONSULTAR_EMPLEADOR_POR_APORTES_CON_RAZON_SOCIAL = "Consulta.empleadorPorAportesConRazonSocial";
    
    /**
     * constante que representa la consulta de todos los empleadores relacionados por afiliación (no aportes) a un afiliado específico  
     */
    public static final String OBTENER_EMPLEADORES_RELACIONADOS_AFILIADO_SIN_INFO_EMPLEADOR = "Afiliados.obtener.empleadorRelacionadoAfiliadoSinInformacionDelEmpleador";
    
    /**
     * constante que representa la consulta de todos los empleadores relacionados por aportes a un afiliado específico
     */
    public static final String CONSULTAR_EMPLEADOR_POR_APORTES_SIN_INFO_EMPLEADOR = "Consulta.empleadorPorAportesSinInformacionDelEmpleador";
    
    /**
     * Constante con el nombre de la consulta que obtiene el valor de ingresos mensuales de un Beneficiario
     */
    public static final String FOVIS_CONSULTAR_INGRESOMENSUAL_BENEFICIARIO = "Fovis.Consultar.IngresoMensual.Beneficiario";
    
    /**
     * consulta la información relacionada al ultimo aporte hecho por o en nombre del afiliado
     */
    public static final String CONSULTAR_INFO_ULTIMO_APORTE_TIPO_NUMERO_ID = "Afiliados.consultar.infoUltimoAporteTipoNumeroId";

    /**
     * Consulta de trabajadores dependientes por aportes de un empleador y no formalizados sin afiliación con aportes
     * */
    public static final String CONSULTAR_TRABAJADORES_POR_APORTES_CON_ID_EMPRESA = "Afiliados.Consultar.trabajadoresSinAfiliacionConAportes.idEmpresa";

    /**
     * Consulta la información de las solicitudes de afiliación donde esta relacionado el rol de la lista enviada
     */
    public static final String CONSULTAR_SOLICITUD_AFILIACION_PERSONA_POR_ID_ROL = "consultar.info.solicitud.afiliacion.persona.por.rol";

    /**
     * Consulta los grupos familiares donde el afiliado principal es el mismo adminsubsidio y se encuentra activo.
     */
    public static final String CONSULTAR_GRUPOS_FAM_CON_ADMIN_SUBS_IGUAL_DEPEND = "Afiliaciones.MedioDePago.consultar.gruposFamiliarAfiliadoAdminSub";
    
    /**
     * Consulta el id de la empresa en base al id del empleador
     */
    public static final String CONSULTAR_ID_EMPRESA_POR_EMPLEADOR = "Afiliaciones.empleador.consultarIdEmpresa";
    
    /**
     * Constante que representa la consulta del afiliado validando si este aparece registrado como beneficiario 
     * de uno o más grupos familiares. De econtrar coincidencias devuleve dichos registros
     */
    public static final String CONSULTAR_AFILIADO_COMO_BENEFICIARIO = "Afiliaciones.afiliado.consultarAfiliadoComoBeneficiario";
    
    /**
     * Representa la consulta de información de afiliación del jefe de hogar de una postulación
     */
    public static final String CONSULTAR_AFILIACION_JEFE_HOGAR = "afiliados.consultar.afiliacion.jefe.hogar";

    /**
     * Consulta los trabajadores asociados a un empresa incluyendo los asociados por aportes, se puede buscar por id empresa o id empleador
     * o por estado del trabajador respecto al empleador
     */
    public static final String CONSULTAR_TRABAJADORES_EMPRESA_APORTES_BY_EMPRESA_EMPLEADOR_ESTADO = "consultar.trabajadores.aportes.por.empleador.empresa.estado";
    public static final String CONSULTAR_TRABAJADORES_EMPRESA_APORTES_BY_EMPLEADOR_AFILIADOS = "consultar.trabajadores.por.empleador.afiliados";
    public static final String CONSULTAR_TRABAJADORES_EMPRESA_APORTES_BY_EMPLEADOR_AFILIADOS_CONTEO = "consultar.trabajadores.por.empleador.afiliados.conteo";
    public static final String CONSULTAR_TRABAJADORES_EMPRESA_APORTES_BY_EMPLEADOR_AFILIADOS_CONTEO_ACTIVO = "consultar.trabajadores.por.empleador.afiliados.conteo.activo";

    public static final String CONSULTAR_TRABAJADORES_EMPRESA_APORTES_BY_EMPLEADOR_APORTES_NO_AFILIADOS = "consultar.trabajadores.por.empleador.aportes.no.afiliados";

    public static final String STORED_PROCEDURE_CONSULTAR_TRABAJADORES_EMPRESA_APORTES_BY_EMPLEADOR_APORTES_NO_AFILIADOS = "sp.procedure.consultar.trabajadores.por.empleador.aportes.no.afiliados";

    public static final String STORED_PROCEDURE_CONSULTAR_TRABAJADORES_EMPRESA_BY_EMPLEADOR = "sp.procedure.consultar.trabajadores.por.empleador";


    public static final String BUSCAR_ESTADO_AFILIACION_AFILIADO = "consultar.afiliado.estadoAfiliacionAfiliado";

    public static final String CONSULTAR_AFILIADOS_UN_GRUPO_FAMILIAR = "consultar.afiliados.unGrupoFamiliar";
    
    /**
     * Consulta la existencia de sitio pago y si el sitio pago es principal
     */
    public static final String CONSULTAR_EXISTENCIA_SITIO_PAGO = "Afiliados.Consultar.existenciaSitioPago.infraestructura";

    /**
     * Consulta si un beneficiario se encuentra activo con respecto a determinado afiliado
     */
    public static final String CONSULTAR_BENEFICIARIO_ACTIVO_AFILIADO = "Afiliados.Consultar.BeneficiarioActivoAfiliado";

    public static final String CONSULTAR_LISTADO_RESGUARDO = "Afiliados.Consultar.listado.resguardo";

    public static final String CONSULTAR_LISTADO_PUEBLO_INDIGENA = "Afiliados.Consultar.listado.puebloIndigena";

    public static final String CONSULTA_AFLIADO_VETERANIA_FINALIZADA = "Afiliados.Obtener.Afiliados.Periodo.Veterania.Finalizado";
    
    public static final String CONSULTA_FORMULARIO_AFILIACION = "Afiliados.Consultar.Formulario.Afiliacion";

    public static final String CONSULTAR_INFO_PERSONA = "sp.procedure.consultar.info.persona";

    public static final String CONSULTAR_INFO_BENEFICIARIO = "sp.procedure.consultar.info.beneficiario";   

    public static final String CORRECCION_CATEGORIA_AFILIADO_BENEFICIARIO = "sp.procedure.correccion.categoria.afiliado.beneficiario";    

    // ============ MASIVO TRANSFERENCIA
    public static final String CONSULTAR_IDS_CARGUE_MASIVO_TRANSFERENCIA = "consultar.ids.gruposFamiliares.cargueMasivo.transferencia";   

    public static final String CONSULTAR_BENEFICIARIOS_POR_IDS = "Novedades.Beneficiarios.consultarBeneficiariosPorIds"; 
}
