package com.asopagos.reportes.business.interfaces;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.reportes.FrecuenciaMetaEnum;
import com.asopagos.enumeraciones.reportes.MetaEnum;
import com.asopagos.enumeraciones.reportes.PeriodicidadMetaEnum;
import com.asopagos.reportes.dto.CategoriaAfiliadoDTO;
import com.asopagos.reportes.dto.CategoriaBeneficiarioDTO;
import com.asopagos.reportes.dto.CategoriaDTO;
import com.asopagos.reportes.dto.CategoriasComoAfiliadoPrincipalDTO;
import com.asopagos.reportes.dto.CategoriasComoBeneficiarioDTO;
import com.asopagos.reportes.dto.DatosParametrizacionMetaDTO;
import com.asopagos.reportes.dto.DetalleBeneficiarioDTO;
import com.asopagos.reportes.dto.ParametrizacionMetaDTO;

/**
 * <b>Descripcion:</b> Interfaz que define las funciones para la consulta de información en
 * el modelo de datos de Reportes <br/>
 * <b>Módulo:</b> Asopagos - HU-KPI <br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */

@Local
public interface IConsultasModeloReportes {

    /**
     * Servicio que consulta la parametrización de una determinada configuración de metas
     * @param parametrizacionMetasDTO
     *        el objeto con los filtros escogidos
     * @return lista con los valores de la parametrización
     */
    @GET
    @Path("/consultarParametrizacionMeta")
    public List<DatosParametrizacionMetaDTO> consultarParametrizacionMeta(@NotNull @QueryParam("meta") MetaEnum meta,
            @NotNull @QueryParam("periodicidad") PeriodicidadMetaEnum periodicidad, @NotNull @QueryParam("periodo") String periodo,
            @NotNull @QueryParam("frecuencia") FrecuenciaMetaEnum frecuencia);

    /**
     * Servicio que actualiza la parametrizacion de una determinada configuración de metas
     * @param parametrizacionMetasDTO
     *        el objeto con la parametrización a actualizar
     */
    @POST
    @Path("/actualizarParametrizacionMeta")
    public void actualizarParametrizacionMeta(@NotNull ParametrizacionMetaDTO parametrizacionMetasDTO);

	/**
	 * Consulta el estado respecto a la CCF que tenía un aportante en una fecha dada
	 * @param tipoIdentificacion Tipo de identificación del aportante
	 * @param numeroIdentificacion Número de identificación del aportante
	 * @param tipoAportante Tipo de aportante
	 * @param fecha Fecha a evaluar
	 * @return El estado del aportante. Se generaliza tipo de retorno <code>String</code> para mapear la enumeración correspondiente, dependiendo del tipo de aportante: 
	 * <code>EstadoAfiliadoEnum</code> ó <code>EstadoEmpleadorEnum</code>
	 */    
	String consultarEstadoAportanteFecha(Long idPersona, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoSolicitanteMovimientoAporteEnum tipoAportante, Long fecha);

	/**
	 * Consulta el estado que tenía un afiliado respecto a su empleador, en una fecha dada
	 * @param tipoIdentificacion Tipo de identificación del trabajador
	 * @param numeroIdentificacion Número de identificación del trabajador
	 * @param tipoAfiliado Tipo de afiliado
	 * @param tipoIdentificacionEmpleador Tipo de identificación del empleador
	 * @param numeroIdentificacionEmpleador Número de identificación del empleador
	 * @param fecha Fecha de evaluación
	 * @return El estado del afiliado. Se generaliza tipo de retorno <code>String</code>, dado que la consulta puede retornar el valor SIN_INFORMACION, que corresponde al estado del afiliado que presenta estado <code>null</code>
	 */
	String consultarEstadoAfiliadoFecha(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, TipoAfiliadoEnum tipoAfiliado, TipoIdentificacionEnum tipoIdentificacionEmpleador, String numeroIdentificacionEmpleador, Long fecha);
	
	
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
	List<CategoriaDTO> consultarCategoriaBeneficiario(TipoIdentificacionEnum tipoIdBeneficiario, String numeroIdBeneficiario, TipoBeneficiarioEnum tipoBeneficiario, Long idAfiliado, Long idBenDetalle, List<BigInteger> idAfiliadoSecundario, String idAfiliadoConyuge);
	
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
    CategoriasComoBeneficiarioDTO obtenerCategoriasHeredadas(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado, Boolean isAfiliadoPrincipal, List<String> idsBenDetalle, List<Object[]> infoDetalladaAfiliadosPpales);
    
    void actualizarCategoriasAfiliadoYBeneficiarios(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado, List<CategoriaAfiliadoDTO> categoriasActuales, List<DetalleBeneficiarioDTO> idBeneficiarioDetalleAfiliado);
}
