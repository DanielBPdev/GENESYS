package com.asopagos.historicos.constants;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * Clase que contiene las constantes con los nombres de los NamedQueries del
 * servicio <b>Módulo:</b> Asopagos - Historicos<br/>
 * Transversal
 * 
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 */
public class NamedQueriesConstants {
    
    /**
     * Constante que contiene el nombre del named query que consulta el estado del empleador para un período.
     */
    public static final String HISTORICOS_CONSULTAR_EMPLEADOR_ESTADO_PERIODO = "Historicos.Consultar.Empleador.Estado.Periodo";
    
    /**
     * Constante que contiene el nombre del named query que consulta el estado del independiente para un período.
     */
    public static final String HISTORICOS_CONSULTAR_INDEPENDIENTE_ESTADO_PERIODO = "Historicos.Consultar.Independiente.Estado.Periodo";
    
    /**
     * Constante que contiene el nombre del named query que consulta el estado del pensionado para un período.
     */
    public static final String HISTORICOS_CONSULTAR_PENSIONADO_ESTADO_PERIODO = "Historicos.Consultar.Pensionado.Estado.Periodo";
    
    /**
     * Constante que contiene el nombre del named query que consulta los roles activos para un período.
     */
    public static final String HISTORICOS_CONSULTAR_ROL_AFILIADO_PERIODO_EMPLEADOR_AFILIADO = "Historicos.Consultar.RolAfiliado.Periodo.Empleador.Afiliado";
    /**
     * Constante que contiene el nombre del named query que consulta el estado del rol en un período.
     */
    public static final String HISTORICOS_CONSULTAR_ROL_AFILIADO_PERIODO_ACTIVO = "Historicos.Consultar.RolAfiliado.PeriodoActivo";
    
    /**
     * Constante para la consulta de historicos perteneciente a la ubicación del representante legal del empleador 
     */
    public static final String HISTORICOS_CONSULTAR_UBICACION_REPRESENTANTE_LEGAL_EMPLEADOR="Historicos.Consultar.consultarUbicacionRepresentanteLegalEmpleador";
    /**
     * Constante para la consulta de historicos perteneciente a la ubicación del representante legal de la persona  
     */
    public static final String HISTORICOS_CONSULTAR_UBICACION_REPRESENTANTE_LEGAL_PERSONA="Historicos.Consultar.consultarUbicacionRepresentanteLegalPersona";

    /**
     * Constante para la consulta de historicos perteneciente a la ubicación del representante legal de la persona por tipos de ubicación de la persona 
     */
    public static final String HISTORICOS_CONSULTAR_UBICACION_POR_TIPO_UBICACION_REPRESENTANTE_LEGAL_PERSONA="Historicos.Consultar.consultarUbicacionPorTipoUbicacionRepresentanteLegalPersona";                                                                                               
    
    /**
     * Constante para la consulta de historicos perteneciente a la ubicación del rol contacto empleador 
     */
    public static final String HISTORICOS_CONSULTAR_UBICACION_ROL_CONTACTO_EMPLEADOR="Historicos.Consultar.ubicacion.rol.contacto.empleador";


    /**
     * Consulta el histórico de cambios en los datos de correo electrónico, número fijo y celular de una persona
     */
    public static final String CONSULTAR_HISTORICO_TELEFONOS_Y_CORREOS = "Personas.StoredProcedures.USP_ExecuteConsultarHistoricoContactoPersona";
    
    
    /**
     * constante para la consulta de los grupos familiares relacionados al administrador de subsidio
     */
    public static final String BUSCAR_GRUPOS_FAMILIARES_RELACIONADOS = "Persona.buscar.gruposFamiliaresRelacionados";
    
    /**
     * constante que representa la consulta de los datos historicos de una persona como administrador del subsidio de un grupo familiar
     */
    public static final String CONSULTAR_DATOS_HISTORICO_ADMINISTRADOR_SUBSIDIO_GRUPO = "Persona.buscar.datosHistoricoAdministradorSubsidioGrupo";
    
    /**
     * constante que representa la consulta del afiliado principal asociado a un grupo familiar 
     */
    public static final String CONSULTAR_INFO_AFI_PPAL_GRUPO = "Persona.buscar.infoAfiliadoPrincipalGrupoFamiliar";
    
    /**
     * Constante para la consulta de los beneficiarios de un grupo familiar dado
     */
    public static final String BUSCAR_BENEFICIARIOS_GRUPO_FAMILIAR = "Persona.buscar.beneficiariosGrupoFamiliar";
    
    /**
     * Constante que representa la consulta de las fechas de inicio y fin de la relación entre una administrador de subsidio y un grupo familiar.
     */
    public static final String CONSULTAR_FECHAS_RELACION_ADMIN_SUB_CON_GRUPO = "Persona.buscar.fechasRelacionAdminSbusidioConGrupoFamiliar";
    
    /**
     * Consulta los grupos familiares relacionados con un afiliado principal dado.
     */
    public static final String OBTENER_GRUPOS_FAMILIARES_AFI_PPAL = "Persona.obtener.gruposFamiliaresAfiliadoPrincipal";

    public static final String OBTENER_MEDIO_DE_PAGO ="Historicos.Consultar.medios.de.pagos.activo.por.tipo.transferencia.admin.subsidio";
    
    /**
     * Consulta los beneficiarios asociados a un grupo familiar, asociandoles (de aplicar) uno o varios
     * afiliados secundarios con los que esté en estado ACTIVO
     */
    public static final String BUSCAR_BENEFICIARIOS_GRUPO_FAMILIAR_ACTIVO_AFILIADO_2 = "Persona.buscar.beneficiariosGrupoFamiliarActivoAfiliado2";

    /**
     * Consulta los beneficiarios asociados a un grupo familiar, asociandoles (de aplicar) uno o varios
     * afiliados secundarios con los que esté en estado ACTIVO
     */
    public static final String BUSCAR_BENEFICIARIOS_GRUPO_FAMILIAR_ACTIVO_AFILIADO_3 = "Persona.buscar.beneficiariosIDGrupoFamiliarActivoAfiliado2";

    public static final String PROCEDURE_SCHEDULE_ACTUALIZAR_ESTADISTICAS_GENESYS = "Historicos.storedProcedures.SP_ActualizarEstadisticasGenesys";

    public static final String OBTENER_ESTADISTICAS_GENESYS = "Historicos.Consultar.Estadisticas.Genesys";
}