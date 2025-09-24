package com.asopagos.reportes.constants;

public class NamedQueriesConstants {

    public static final String BUSCAR_REPORTE_TIPO_REPORTE_FRECUENCIA = "Consultar.reportekpi.nombre.reporte.frecuencia";
    
    public static final String CONSULTAR_CATEGORIA_ACTUAL_BENEFICIARIO_RESPECTO_AFI_PPAL = "Consultar.actual.categoriaBeneficiarioRespectoAfiliadoPrincipal";

    public static final String CONSULTAR_CATEGORIA_ACTUAL_PROPIA = "Consultar.actual.categoriaPropia";
    
    public static final String CONSULTAR_CATEGORIA_ACTUAL_BENEFICIARIO_RESPECTO_AFI_SEC = "Consultar.actual.categoriaBeneficiarioRespectoAfiliadoSecundario";

    
    public static final String CONSULTAR_ID_AFILIADO_SECUNDARIO = "Consultar.idAfiliadoSecundario";
    
    public static final String CONSULTAR_FECHA_Y_MOTIVO_ACTUAL_CAMBIO_CATEGORIA = "Consultar.actual.fechaYMotivoCambioCategoria";
    
    public static final String CONSULTAR_FECHA_Y_MOTIVO_ACTUAL_CAMBIO_CATEGORIA_TIPO_NUM_ID = "Consultar.actual.fechaYMotivoCambioCategoriaTipoNumId";
    
    public static final String CONSULTAR_HISTORICO_CATEGORIAS_PROPIAS_AFILIADO = "Consultar.historico.categoriasPropiasAfiliado";
    
    //public static final String CONSULTAR_DATOS_ID_CONYUGE_ACTIVO_AFILIADO = "Consultar.actual.datosIdConyugeActivoAfiliado";
    
    public static final String CONSULTAR_HISTORICO_CATEGORIAS_HEREDADAS_AFI_PRINCIPAL = "Consultar.historico.categoriasHeredadasAfiliadoPrincipal";

    public static final String CONSULTAR_HISTORICO_CATEGORIAS_HEREDADAS_AFI_PRINCIPAL_UNICAMENTE = "Consultar.historico.categoriasHeredadasAfiliadoPrincipalUnicamente";
    
    //public static final String CONSULTAR_INFO_AFILIADO_PRINCIPAL = "Consultar.actual.infoAfiliadoPrincipal";
    
    public static final String CONSULTAR_INFO_AFILIADO_PRINCIPAL_POR_ID_BEN_DET = "Consultar.actual.infoAfiliadoPrincipal.consultarAfiliados";

public static final String CONSULTAR_INFO_AFILIADO_PRINCIPAL_POR_ID_BEN_DET_SECUNDARIO = "Consultar.actual.infoAfiliadoPrincipal.consultarAfiliados.Secundario";

public static final String CONSULTAR_INFO_AFILIADO_PRINCIPAL_POR_ID_BEN_DET_SECUNDARIO_ACTIVOS = "Consultar.actual.infoAfiliadoPrincipal.consultarAfiliados.SecundarioActivos";

public static final String CONSULTAR_INFO_AFILIADO_PRINCIPAL_POR_ID_BEN_DET_SECUNDARIO_ACTIVOS_ID_AFILIADO_SEC = "Consultar.actual.infoAfiliadoPrincipal.consultarAfiliados.SecundarioActivosIdAfiliado";

public static final String STORED_PROCEDURE_CONSULTAR_CATEGORIAS_HEREDADAS = "stored.procedure.reportes.consultar.CategoriasHeredadas";

    public static final String CONSULTAR_HISTORICO_REPORTES_NORMATIVOS_OFICIALES = "Consultar.reportesNormativos.historicos.oficiales";
    
    public static final String VERIFICAR_REPORTES_NORMATIVOS_OFICIALES = "verificar.reportesNormativos.oficiales";
    
    public static final String CONSULTAR_DATOS_PRECARGADOS_FICHA_CONTROL = "Consultar.datos.precargados.ficha.control";
    
    public static final String CONSULTAR_PARAMETRO = "Consultar.parametro";
    
    public static final String CONSULTAR_DATOS_PRECARGADOS_FICHA_CONTROL_ENTIDAD = "Consultar.datos.precargados.ficha.control.entidad";   
    
    //Constantes para las consutas de los servicios transversales 
    
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
     * consulta y obtiene los datos de una persona como afiliado pricipal
     */
    public static final String OBTENER_INFO_AFILIADO_PRINCIPAL_SRV_TRA = "Afiliados.transversal.obtenerInfoAfiliadoPrincipal";
    
    /**
     * consulta y obtiene la información general de los grupos familiares donde la persona parece como afiliado principal
     */
    public static final String CONSULTAR_INFO_GENERAL_GRUPOS_FAMILIARES_ST = "Afiliados.transversal.consultarInfoGeneralGruposFamiliares";
    
    /**
     * consulta y obtiene la información de los beneficiarios de un grupo familiar determinado
     */
    public static final String CONSULTAR_BENEFICIARIOS_GRF_ST = "Afiliados.transversal.consultarBeneficiariosGrupoFamiliar";
    
    /**
     * consulta y obtiene la información de la persona sobre la cual se requiere la información de categoría.
     */
    public static final String CONSULTAR_INFO_PERSONA_CATEGORIA_SRV_TRA = "Afiliados.transversal.consultarInfoPersonaCategoria";

    /**
     * consulta y obtiene la información basica de un empleador determinado.
     */
    public static final String OBTENER_INFO_BASICA_EMPLEADOR_SRV_TRA = "Afiliados.transversal.obtenerInfoBasicaEmpleador";

    /**
     * consulta y obtiene la información total de un empleador determinado. 
     */
    public static final String OBTENER_INFO_TOTAL_EMPLEADOR_SRV_TRA = "Afiliados.transversal.obtenerInfoTotalEmpleador";
    
    /**
     * consulta y obtiene los datos del representante legal de un empleador dado.
     */
    public static final String OBTENER_INFO_REPRESENTANTE_LEGAL_EMPLEADOR_SRV_TRA = "Afiliados.transversal.obtenerInfoRepLegalEmpleador";
    
    /**
     * consulta y obtiene los datos generales de un empleador dado.
     */
    public static final String OBTENER_INFO_EMPLEADOR_PARA_CONTACTOS_SRV_TRA = "Afiliados.transversal.obtenerInfoEmpleadorParaContacto";

    /**
     * consulta los datos de contacto de un empleador dado.
     */
    public static final String OBTENER_DATOS_CONTACTO_EMPLEADOR_SRV_TRA = "Afiliados.transversal.obtenerDatosContactoEmpleador";
    
    /**
     * consulta los datos de contacto de un empleador dado.
     */
    public static final String OBTENER_DATOS_CONTACTO_EMPLEADOR_SRV_TRA_PPL = "Afiliados.transversal.obtenerDatosContactoEmpleadorPpl";
    
    /**
     * consulta la información de los grupos familiares de un afiliado para el método obtenerInfoAfiliado
     * del servicio Cajas Sin Fronteras
     */
    public static final String OBTENER_INFO_AFILIADO_CSF_SRV_TRA = "Afiliados.transversal.obtenerInfoAfiliadoCSF";
    
    /**
     * obtiene la información completa de una persona como beneficiario de un afiliado de la caja
     */
    public static final String OBTENER_INFO_TOTAL_BENEFICIARIO_SRV_TRA = "Afiliados.transversal.obtenerInfoTotalBeneficiario";
    
    /**
     * consulta la categoria actual de una persona para los servicios de integración
     */
    public static final String CONSULTAR_CATEGORIA_ACTUAL_PERSONA_TRA = "Afiliados.transversal.obtenerCategoriaActualPersonaTra";
    
    /**
     * consulta y obtiene los datos de una persona como beneficiario de un grupo familiar
     */
    public static final String OBTENER_INFO_BENEFICIARIO_GRUPO_SRV_TRA = "Afiliados.transversal.obtenerInfoBeneficiarioGrupoFamiliar";
    
    /**
     * consulta y obtiene la información general de los grupos familiares donde la persona parece como beneficiario
     */
    public static final String CONSULTAR_INFO_GENERAL_GRUPOS_FAMILIARES_BENEFICIARIO_ST = "Afiliados.transversal.consultarInfoGeneralGruposFamiliaresBeneficiario";
    
    /**
     * constante que define la consulta que define el tipo de rol que tiene la persona a consultar
     */
    public static final String DEFINIR_PERSONA_CONSULTA_ESTADO_CATEGORIA_SRV_TRA = "Afiliados.transversal.definirPersonaConsultaEstadoCategoriaSrvTra";
    
    /**
     * 
     */
    public static final String CONSULTAR_DATOS_PERSONA_COMO_AFILIADO_SRV_TRA = "Afiliados.transversal.consultarDatosPersonaComoAfiliadoSrvTra";
    
    /**
     * 
     */
    public static final String CONSULTAR_BENEFICIARIOS_AFILIADO_SRV_TRA = "Afiliados.transversal.consultarBeneficiariosAfiliadoSrvTra";
    
    /**
     * 
     */
    public static final String CONSULTAR_SUCURSALES_EMPRESA_SRV_TRA = "Afiliados.transversal.consultarSucursalesEmpresaSrvTra";
    
    /**
     * 
     */
    public static final String CONSULTAR_AFILIADOS_PRINCIPALES_BENEFICIARIO_SRV_TRA = "Afiliados.transversal.consultarAfiliadosPrincipalesBeneficiarioSrvTra";
        
    /**
     * 
     */
    public static final String CONSULTAR_BENEFICIARIOS_AFILIADO_PRINCIPAL_SRV_TRA = "Afiliados.transversal.obtenerBeneficiariosAfiliadoPrincipalSrvTra";
    
    /**
     * 
     */
    public static final String CONSULTAR_AFILIADOS_PRINCIPALES_GRUPO_BENEFICIARIO_SRV_TRA = "Afiliados.transversal.consultarAfiliadosPrincipalesGrupoBeneficiarioSrvTra";
    
    /**
     * 
     */
    public static final String CONSULTAR_EMPLEADORES_AFILIADO_SRV_TRA = "Afiliados.transversal.consultarEmpleadoresAfiliadoSrvTra";
    
    /**
     * 
     */
    public static final String CONSULTAR_SUCURSALES_EMPLEADOR_SRV_TRA = "Afiliados.transversal.consultarSucursalesEmpleadorSrcTra";
    
    /**
     * 
     */
    public static final String CONSULTAR_SUCURSALES_EMPLEADOR_POR_CODIGO_SRV_TRA = "Afiliados.transversal.consultarSucursalesEmpleadorPorCodigoSrvTra";
    
    /**
     * 
     */
    public static final String CONSULTAR_CATEGORIAS_AFILIADO_SRV_TRA = "Afiliados.transversal.consultarCategoriasAfiliadoSrvTra";
    
    /**
     * 
     */
    public static final String CONSULTAR_CATEGORIAS_BENEFICIARIO_SRV_TRA = "Afiliados.transversal.consultarCategoriasBeneficiarioSrvTra";
    
    /**
     * 
     */
    public static final String CONSULTAR_CATEGORIAS_BENEFICIARIOS_TIPO_AFI_SRV_TRA = "Afiliados.transversal.consultarCategoriasBeneficiarioPorTipoAfiliadoSrvTra";
    
    /**
     * 
     */
    public static final String CONSULTAR_AFILIADO_COMO_BENEFICIARIO_SRV_TRA = "Afiliados.transvesal.consultarAfiliadoComoBeneficiarioSrvTra";

    /**
     * Constante que representa la consulta de los ciclos de asignación del periodo enviado por parametro con su respectivas fechas de
     * postulación y asignación
     */
    public static final String CONSULTAR_POSTULACIONES_ASIGNACIONES = "Consultar.reporteNormativo.postulaciones.asignaciones";

    /**
     * Constante con el nombre de la consulta que obtiene los datos del reporte normativo consolidado de cartera
     */
    public static final String CONSULTAR_CONSOLIDADO_CARTERA = "Consultar.reporteNormativo.consolidado.cartera";
    
    /**
     * Constante con el nombre de la consulta que obtiene los datos del reporte normativo consolidado de cartera
     */
    public static final String CONSULTAR_CONSOLIDADO_CARTERA_COUNT = "Consultar.reporteNormativo.consolidado.cartera.count";
    
    /**
     * Constante con el nombre de la consulta que obtiene los datos del reporte normativo consolidado de cartera
     */
    public static final String CONSULTAR_CONSOLIDADO_CARTERA_TOTAL = "Consultar.reporteNormativo.consolidado.cartera.total";
    
    /**
     * Constante con el nombre de la consulta que obtiene los datos del reporte normativo desagregado de cartera por aportante
     */
    public static final String CONSULTAR_DESAGREGADO_CARTERA_APORTANTE = "Consultar.reporteNormativo.desagregado.cartera.aportante";
    
    /**
     * Constante con el nombre de la consulta que obtiene los datos del reporte normativo desagregado de cartera por aportante
     */
    public static final String CONSULTAR_DESAGREGADO_CARTERA_APORTANTE_V_TOTAL = "Consultar.reporteNormativo.desagregado.cartera.aportante.valorTotal";
    
    /**
     * Constante con el nombre de la consulta que obtiene los datos del reporte normativo desagregado de cartera por aportante
     */
    public static final String CONSULTAR_DESAGREGADO_CARTERA_APORTANTE_COUNT = "Consultar.reporteNormativo.desagregado.cartera.aportante.count";
    
    /**
     * Constante con el nombre de la consulta que obtiene los datos del reporte normativo desagregado de cartera por aportante
     */
    public static final String CONSULTAR_EMPRESAS_APORTANTES = "Consultar.reporteNormativo.empresas.aportantes";
    
    /**
     * Constante con el nombre de la consulta que obtiene los datos del reporte normativo desagregado de cartera por aportante
     */
    public static final String CONSULTAR_EMPRESAS_APORTANTES_COUNT = "Consultar.reporteNormativo.empresas.aportantes.count";


    /**
     * Constante con el nombre de la consulta que obtiene los datos del reporte normativo aviso de incumplimiento
     */
	public static final String CONSULTAR_AVISO_INCUMPLIMIENTO = "Consultar.reporteNormativo.aviso.incumplimiento";
	
	/**
     * Constante con el nombre de la consulta que obtiene los datos del reporte normativo aviso de incumplimiento
     */
	public static final String CONSULTAR_AVISO_INCUMPLIMIENTO_COUNT = "Consultar.reporteNormativo.aviso.incumplimiento.count";
	
	/**
	 * Constante con la consulta de personas que fueron beneficiadas con el subsidio para vivienda
	 */
	public static final String CONSULTAR_AFILIADOS_BENEFICIADOS_FOVIS = "Consultar.reporteNormativo.registroAfiliadosFovis";
	
	/**
	 * Constante con la consulta de personas que fueron beneficiadas con el subsidio para vivienda
	 */
	public static final String CONSULTAR_AFILIADOS_BENEFICIADOS_COUNT_FOVIS = "Consultar.reporteNormativo.count.registroAfiliadosFovis";
	
	 /**
     * Constante con el nombre de la consulta que obtiene los datos del reporte normativo aviso de incumplimiento
     */
	public static final String CONSULTAR_AFILIADOS_A_CARGO = "Consultar.reporteNormativo.afiliados.cargo";
	
	/**
     * Constante con el nombre de la consulta que obtiene los datos del reporte normativo aviso de incumplimiento
     */
	public static final String CONSULTAR_AFILIADOS_A_CARGO_COUNT = "Consultar.reporteNormativo.afiliados.cargo.count";
	
	
    /**
     * Constante con el nombre de la consulta que obtiene los datos del reporte normativo numero cuotas
     */
    public static final String CONSULTAR_RM_NUMERO_CUOTAS = "Consultar.reporteNormativo.numeroCuotas";
    
    /**
     * Constante con el nombre de la consulta que obtiene los datos del reporte normativo numero cuotas
     */
    public static final String CONSULTAR_RM_NUMERO_CUOTAS_COUNT = "Consultar.reporteNormativo.numeroCuotas.count";
    
    /**
     * Constante con el nombre de la consulta que obtiene los datos del reporte normativo numero cuotas
     */
    public static final String CONSULTAR_RM_NUMERO_PERSONAS_A_CARGO = "Consultar.reporteNormativo.numeroPersonasACargo";
    
    /**
     * Constante con el nombre de la consulta que obtiene los datos del reporte normativo numero cuotas
     */
    public static final String CONSULTAR_RM_NUMERO_PERSONAS_A_CARGO_COUNT = "Consultar.reporteNormativo.numeroPersonasACargo.count";
    
    /**
     * Constante con el nombre de la consulta que obtiene los datos del reporte aportantesProcesoEnUnidad
     */
    public static final String CONSULTAR_RM_APORTANTES_PROCESO_EN_LA_UNIDAD = "Consultar.reporteNormativo.aportantesProcesoEnUnidad";
    
    /**
     * Constante con el nombre de la consulta que obtiene los datos del reporte aportantesProcesoEnUnidad
     */
    public static final String CONSULTAR_RM_APORTANTES_PROCESO_EN_LA_UNIDAD_V_TOTAL = "Consultar.reporteNormativo.aportantesProcesoEnUnidad.valorTotal";
    
    
    
    /**
     * Constante con el nombre de la consulta que obtiene los datos del reporte normativo numero cuotas
     */
    public static final String CONSULTAR_RM_APORTANTES_PROCESO_EN_LA_UNIDAD_COUNT = "Consultar.reporteNormativo.aportantesProcesoEnUnidad.count";   
	
    /**
     * Constante con el nombre de la consulta que obtiene los datos del reporte normativo numero cuotas
     */
    public static final String CONSULTAR_RM_SEGUIMIENTOS_TRASLADOS_MORA = "Consultar.reporteNormativo.seguimientosTrasladosMora";
    
    /**
     * Constante con el nombre de la consulta que obtiene los datos del reporte normativo numero cuotas
     */
    public static final String CONSULTAR_RM_SEGUIMIENTOS_TRASLADOS_MORA_UPDATE = "Consultar.reporteNormativo.seguimientosTrasladosMora.update.periodosDesagregados";
    
    /**
     * Constante con el nombre de la consulta que obtiene los datos del reporte normativo numero cuotas
     */
    public static final String CONSULTAR_RM_SEGUIMIENTOS_TRASLADOS_MORA_OFICIAL = "Consultar.reporteNormativo.seguimientosTrasladosMora.oficial";
    
    
    /**
     * Constante con el nombre de la consulta que obtiene los datos del reporte normativo numero cuotas
     */
    public static final String CONSULTAR_RM_SEGUIMIENTOS_TRASLADOS_MORA_COUNT = "Consultar.reporteNormativo.seguimientosTrasladosMora.count";
    
    /**
    
    
    
	
	/**
	 * Constante que representa la consulta de la categoria actual del afiliado en core
	 */
	public static final String CATEGORIA_ACTUAL_AFILIADO_CORE = "Consultar.categoria.consultarCategoriaActualAfiliadoEnCore";

	/**
     * Constante que representa la consulta de la categoria actual del afiliado en reportes
     */
    public static final String CATEGORIA_ACTUAL_AFILIADO_REPORTES = "Consultar.categoria.consultarCategoriaActualAfiliadoEnReportes";
    
    /**
     * Constante que representa la consulta de la categoria actual del beneficiario en reportes
     */
    //public static final String CATEGORIA_ACTUAL_BENEFICIARIO_REPORTES = "Consultar.categoria.consultarCategoriaActualBeneficiarioEnReportes";
    
    /**
     * 
     */
    public static final String INSERTAR_DATO_ACTUALIZADO_ESTADO_AFILIADO = "Insertar.categoria.datoActualizadoEstadoAfiliado";
    
    public static final String INSERTAR_DATO_ACTUALIZADO_ESTADO_BENEFICIARIO = "Insertar.categoria.datoActualizadoEstadoBeneficiario";
    
    public static final String CONSULTAR_DATOS_ID_CONYUGE_ACTIVO_AFILIADO_EN_CORE = "Consultar.actual.datosIdConyugeActivoAfiliadoEnCore";
    
    public static final String CONSULTAR_CATEGORIA_ACTUAL_PROPIA_BENEFICIARIO_INACTIVO = "Consultar.actual.categoriaPropiaBeneficiarioInactivo";

    //public static final String CONSULTAR_CATEGORIA_ACTUAL_PROPIA_EN_CORE = "Consultar.actual.categoriaPropiaEnCore";
    
    //public static final String CONSULTAR_CATEGORIA_ACTUAL_CONYUGE_EN_CORE = "Consultar.actual.CategoriaHeredadaConyugeEnCore";
    
    //public static final String CONSULTAR_CATEGORIA_ACTUAL_CONYUGE_EN_REPORTES = "Consultar.actual.categoriaHeredadaConyugeEnReportes";
    
    /**
     * Constante que representa la consulta de permisos asociados al role de un usuario para keycloak
     */
    public static final String BUSCAR_PERMISOS_DASHBOARD_POR_ROL_USARIO = "DashBoardConsultas.buscar.permisos.por.rol.usuario";
  
    public static final String CONSULTAR_EMPLEADORES_GIASS = "Consultar.empleadores.giass";
    
    public static final String CONSULTAR_TRABAJADORES_GIASS = "Consultar.trabajadores.giass";
    
    public static final String CONSULTAR_BENEFICIARIOS_GIASS = "Consultar.beneficiarios.giass";
    
    public static final String CONSULTAR_AFILIACIONES_EMPLEADORES_GIASS = "Consultar.afiliaciones.empleadores.giass";
    
    public static final String CONSULTAR_AFILIACIONES_TRABAJADORES_GIASS = "Consultar.afiliaciones.trabajadores.giass";
    
    public static final String CONSULTAR_AFILIACIONES_BENEFICIARIOS_GIASS = "Consultar.afiliaciones.beneficiarios.giass";
    
    public static final String CONSULTAR_DESAFILIADOS_CAJA = "Consultar.desafiliados.caja";
    
    public static final String CONSULTAR_AFILIACIONES_CAJA_DESAFILIADOS = "Consultar.afiliaciones.caja.desafiliados";

    public static final String CONSULTAR_RELACION_BENEFICIARIO_OTRO_PADRE_BIOLOGICO = "Consultar.relacion.beneficiario.otroPadreBiologico";

    public static final String CONSULTAR_ID_CATEGORIA_AFILIADO = "Consultar.categoria.idCategoriaAfiliado";
    
    public static final String CONSULTAR_IDS_BENEFICIARIOS_AFILIADO = "Consultar.beneficiario.idBeneficiarioAfiliado";
    
    public static final String CONSULTAR_ID_PEROSONA = "Consultar.persona.consultarIdPersona";
    
    public static final String CONSULTAR_IDS_BENEFICIARIO_DETALLE = "Consultar.beneficiario.consultarIdBeneficiarioDetalle";
    
    public static final String CONSULTAR_DATOS_ID_AFILIADO_PPAL = "Consultar.afiliado.datosIdentificacionAfiliadoPrincipal";
    
    public static final String CONSULTAR_ID_AFILIADO_SECUNDARIO_CORE = "Consultar.idAfiliadoSecundarioCore";
    
    public static final String CONSULTAR_ID_AFILIADO_BENEFICIARIO_CONYUGE = "Consultar.idAfiliadoConyugeCore";
    
    public static final String CONSULTAR_HISTORICO_AFILIACION_PERSONA_CORE = "Consultar.HistoricoAfiliacionPersonaEnCore";

    public static final String CONSULTAR_HISTORICO_CATEGORIAS_AFILIADO_BENEFICIARIO = "Consultar.Categorias.afiliado.beneficiario";
 
    public static final String CONSULTAR_HISTORICO_ASIGNACIONES_PAGOS_REINTEGROS_FOVIS= "Consultar.reporteNormativo.AsignacionEntregaPagosFOVIS";
    
    public static final String CONSULTAR_HISTORICO_CONSOLIDADO_ASIGNACIONES_PAGOS_REINTEGROS_FOVIS= "Consultar.reporteNormativo.AsignacionConsolidadoMicroDatoFOVIS";
    
    public static final String CONSULTAR_HISTORICO_CONSOLIDADO_ASIGNACIONES_PAGOS_MICRODATO_FOVIS= "Consultar.reporteNormativo.AsignacionPagoReintegroMicroDatoFOVIS";
    
    public static final String CONSULTAR_ESTADO_BENEFICIARIO = "Consultar.estado.beneficiario";

     public static final String STORED_PROCEDURE_CONSULTAR_CATEGORIAS_BENEFICIARIO = "sp.Consultar.categorias.beneficiario";

     public static final String CONSULTAR_PERSONA_TIENE_FALLECIMIENTO_REPORTADO =  "Consultar.persona.tiene.fallecimiento.reportado";
     
     public static final String CONSULTAR_PERSONA_TIENE_FALLECIMIENTO_REPORTADO_BEN_DETALLE =  "Consultar.persona.tiene.fallecimiento.reportado.bendetalle";
          
}
