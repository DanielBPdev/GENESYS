package com.asopagos.reportes.service;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.reportes.FrecuenciaMetaEnum;
import com.asopagos.enumeraciones.reportes.MetaEnum;
import com.asopagos.enumeraciones.reportes.PeriodicidadMetaEnum;
import com.asopagos.enumeraciones.reportes.ReporteKPIEnum;
import com.asopagos.reportes.dto.CategoriaDTO;
import com.asopagos.reportes.dto.CategoriasComoAfiliadoPrincipalDTO;
import com.asopagos.reportes.dto.CategoriasComoBeneficiarioDTO;
import com.asopagos.reportes.dto.DatosIdentificadorGrupoReporteDTO;
import com.asopagos.reportes.dto.DatosParametrizacionMetaDTO;
import com.asopagos.reportes.dto.ParametrizacionMetaDTO;

/**
 * <b>Descripción:</b> Interface que define los métodos de negocio relacionados
 * con la gestión de Reportes <b>Historia de Usuario:</b> Proceso ...
 * 
 * @author Alexander Quintero <alquintero@heinsohn.com.co>
 */
@Path("reportes")
@Consumes("application/json; charset=UTF-8")
@Produces("application/json; charset=UTF-8")
public interface ReportesService {

	/**
	 * Servicio que consulta la parametrización de una determinada configuración
	 * de metas
	 * 
	 * @param parametrizacionMetasDTO
	 *            el objeto con los filtros escogidos
	 * @return lista con los valores de la parametrización
	 */
	@GET
	@Path("/consultarParametrizacionMeta")
	public List<DatosParametrizacionMetaDTO> consultarParametrizacionMeta(
			@NotNull @QueryParam("meta") MetaEnum meta,
				@NotNull @QueryParam("periodicidad") PeriodicidadMetaEnum periodicidad,
				@NotNull @QueryParam("periodo") String periodo,
				@NotNull @QueryParam("frecuencia") FrecuenciaMetaEnum frecuencia);

	/**
	 * Servicio que actualiza la parametrizacion de una determinada
	 * configuración de metas
	 * 
	 * @param parametrizacionMetasDTO
	 *            el objeto con la parametrización a actualizar
	 */
	@POST
	@Path("/actualizarParametrizacionMeta")
	public void actualizarParametrizacionMeta(@NotNull ParametrizacionMetaDTO parametrizacionMetasDTO);

	/**
	 * Servicio que consulta los reportes por el nombre y frecuencia
	 * 
	 * @param parametrizacionMetasDTO
	 *            el objeto con la parametrización a utilizar
	 */
	@GET
	@Path("/consultarIdentificadorReporte")
	public List<DatosIdentificadorGrupoReporteDTO> consultarIdentificadorReporte(@NotNull @QueryParam("nombreReporte") ReporteKPIEnum nombreReporte, @NotNull @QueryParam("frecuenciaReporte") FrecuenciaMetaEnum frecuenciaReporte);

	/**
	 * Servicio que consulta el estado respecto a la CCF que tenía un aportante
	 * en una fecha dada
	 * 
	 * @param tipoIdentificacion
	 *            Tipo de identificación del aportante
	 * @param numeroIdentificacion
	 *            Número de identificación del aportante
	 * @param tipoAportante
	 *            Tipo de aportante
	 * @param fecha
	 *            Fecha a evaluar
	 * @return El estado del aportante. Se generaliza tipo de retorno
	 *         <code>String</code> para mapear la enumeración correspondiente,
	 *         dependiendo del tipo de aportante:
	 *         <code>EstadoAfiliadoEnum</code> ó
	 *         <code>EstadoEmpleadorEnum</code>
	 */
	@GET
	@Path("/consultarEstadoAportanteFecha")
	String consultarEstadoAportanteFecha(@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion, @QueryParam("numeroIdentificacion") String numeroIdentificacion, @QueryParam("tipoAportante") TipoSolicitanteMovimientoAporteEnum tipoAportante, @QueryParam("fecha") Long fecha);

	/**
	 * Servicio que consulta el estado que tenía un afiliado respecto a su
	 * aportante, en una fecha dada
	 * 
	 * @param tipoIdentificacion
	 *            Tipo de identificación del afiliado
	 * @param numeroIdentificacion
	 *            Número de identificación del afiliado
	 * @param tipoAfiliado
	 *            Tipo de afiliado
	 * @param tipoIdentificacionEmpleador
	 *            Tipo de identificación del empleador, si
	 *            tipoAfiliado=TRABAJADOR_DEPENDIENTE
	 * @param numeroIdentificacionEmpleador
	 *            Número de identificación del empleador, si
	 *            tipoAfiliado=TRABAJADOR_DEPENDIENTE
	 * @param fecha
	 *            Fecha a evaluar
	 * @return El estado del afiliado. Se generaliza tipo de retorno
	 *         <code>String</code>, dado que la consulta puede retornar el valor
	 *         SIN_INFORMACION, que corresponde al estado del afiliado que
	 *         presenta estado <code>null</code>
	 */
	@GET
	@Path("/consultarEstadoAfiliadoFecha")
	String consultarEstadoAfiliadoFecha(
			@QueryParam("tipoIdentificacion") TipoIdentificacionEnum tipoIdentificacion,
				@QueryParam("numeroIdentificacion") String numeroIdentificacion,
				@QueryParam("tipoAfiliado") TipoAfiliadoEnum tipoAfiliado,
				@QueryParam("tipoIdentificacionEmpleador") TipoIdentificacionEnum tipoIdentificacionEmpleador,
				@QueryParam("numeroIdentificacionEmpleador") String numeroIdentificacionEmpleador,
				@QueryParam("fecha") Long fecha);
	
	/**
	 * Servicio que consulta las categorias de un beneficiario
	 * 
	 * @param tipoIdBeneficiario
	 *             tipo de identificación del beneficiario.
	 *             
	 * @param numeroIdbeneficiario            
	 *             número de identificación del beneficiario.
	 *             
	 * @param tipoBeneficiario
	 *             tipo de beneficiario (CONYUGE, PADRES, HIJO).
	 *              
	 * @param idAfiliado
	 *             identificador en bdat del afiliado principal.
	 * 
	 * @return List<CategoriaBeneficiarioDTO> con las categorías del beneficiario 
	 *         respecto al afiliado principal si el beneficiario es PADRES y las propias 
	 *         o del afiliado secundario si el beneficiario es CONYUGE o HIJO respectivamente.
	 *         
	 * @author squintero        
	 */
	@GET
	@Path("/consultarCategoriaBeneficiario")
	public List<CategoriaDTO> consultarCategoriaBeneficiario(
	        @QueryParam("tipoIdBeneficiario")TipoIdentificacionEnum tipoIdBeneficiario,
	        @QueryParam("numeroIdBeneficiario")String numeroIdBeneficiario,
	        @QueryParam("tipoBeneficiario")TipoBeneficiarioEnum tipoBeneficiario,
	        @QueryParam("idAfiliado")Long idAfiliado,
	        @QueryParam("idBenDetalle") Long idBenDetalle
	        ); 
	
	/**
     * Servicio encargado de consultar las categorias propias del afiliado principal para 
     * las vistas 360.
     * 
     * @param tipoIdAfiliado
     *          tipo de identificación del afiliado principal.
     *          
     * @param numeroIdAfiliado
     *          número de identificación del afiliado principal.
     *          
     * @param tipoAfiliado
     *          tipo del afiliado principal (TRABAJADOR_DEPENDIENTE, TRABAJADOR_INDEPENDIENTE 
     *          o PENSIONADO).
     * 
     * @return CategoriasComoAfiliadoPrincipalDTO con las categorias 
     * actuales del afiliado y el histórico de las mismas.
     * 
     * @author squintero
     */
    @GET
    @Path("/obtenerCategoriasPropiasAfiliado")
    public CategoriasComoAfiliadoPrincipalDTO obtenerCategoriasPropiasAfiliado(
            @QueryParam("tipoIdAfiliado")TipoIdentificacionEnum tipoIdAfiliado,
            @QueryParam("numeroIdAfiliado")String numeroIdAfiliado,
            @QueryParam("tipoAfiliado")TipoAfiliadoEnum tipoAfiliado
            ); 
    
    /**
     * Servicio encargado de consultar las categorias del conyuge del afiliado principal para 
     * las vistas 360.
     * 
     * @param tipoIdAfiliado
     *          tipo de identificación del afiliado principal.
     *          
     * @param numeroIdAfiliado
     *          número de identificación del afiliado principal.
     *          
     * @param tipoAfiliado
     *          tipo del afiliado principal (TRABAJADOR_DEPENDIENTE, TRABAJADOR_INDEPENDIENTE 
     *          o PENSIONADO).
     * 
     * @return CategoriasComoAfiliadoPrincipalDTO con las categorias 
     * actuales del conyuge del afiliado y el histórico de las mismas.
     * 
     * @author squintero
     */
    @GET
    @Path("/obtenerCategoriasConyugeAfiliado")
    public CategoriasComoAfiliadoPrincipalDTO obtenerCategoriasConyugeAfiliado(
            @QueryParam("tipoIdAfiliado")TipoIdentificacionEnum tipoIdAfiliado,
            @QueryParam("numeroIdAfiliado")String numeroIdAfiliado,
            @QueryParam("tipoAfiliado")TipoAfiliadoEnum tipoAfiliado
            );
    
    /**
     * Servicio encargado de consultar las categorias heredadas del afiliado principal o del 
     * afiliado secundario.
     * 
     * @param tipoIdAfiliado
     *          tipo de identificacion del afiliado que en este caso, se busca como un 
     *          beneficiario.
     *          
     * @param numeroIdAfiliado
     *          número de identificacion del afiliado que en este caso, se busca como un 
     *          beneficiario.
     * 
     * @param isAfiliadoPrincipal
     *          
     *          
     * @return CategoriasComoBeneficiarioDTO con la información del afiliado principal o 
     *         secundario, las categorias heredadas actuales y el histórico de categorías 
     *         heredadas.
     *         
     * @author squintero
     * 
     */
    @GET
    @Path("/obtenerCategoriasHeredadas")
    public CategoriasComoBeneficiarioDTO obtenerCategoriasHeredadas(
            @QueryParam("tipoIdAfiliado")TipoIdentificacionEnum tipoIdAfiliado,
            @QueryParam("numeroIdAfiliado")String numeroIdAfiliado,
            @QueryParam("isAfiliadoPrincipal")Boolean isAfiliadoPrincipal);
    
    /**
     * Servicio encargado de actualizar los registros de afiliación y categoría de un afiliado y sus beneficiarios
     * desde la bdat de core a la bdat de reportes.
     * 
     * @author squintero
     */
    @GET
    @Path("/actualizarHistoricosAfiliacionYCategoria")
    public void actualizarHistoricosAfiliacionYCategoria(
            @QueryParam("tipoIdAfiliado") TipoIdentificacionEnum tipoIdAfiliado,
            @QueryParam("numeroIdAfiliado") String numeroIdAfiliado,
            @QueryParam("tipoAfiliado") TipoAfiliadoEnum tipoAfiliado);
}
