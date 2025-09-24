package com.asopagos.reportes.business.interfaces;

import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.UriInfo;

import com.asopagos.reportes.dto.CategoriaAfiliadoDTO;
import com.asopagos.reportes.dto.CategoriaBeneficiarioDTO;
import com.asopagos.reportes.dto.CategoriaDTO;
import com.asopagos.reportes.dto.CategoriasComoAfiliadoPrincipalDTO;
import com.asopagos.reportes.dto.CategoriasComoBeneficiarioDTO;
import com.asopagos.dto.DashBoardConsultaDTO;
import com.asopagos.entidades.ccf.core.DatosFichaControl;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoBeneficiarioEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.reportes.FrecuenciaMetaEnum;
import com.asopagos.enumeraciones.reportes.ReporteKPIEnum;
import com.asopagos.enumeraciones.reportes.ReporteNormativoEnum;
import com.asopagos.reportes.dto.DatosIdentificadorGrupoReporteDTO;
import com.asopagos.reportes.dto.DetalleBeneficiarioDTO;
import com.asopagos.reportes.dto.FichaControlDTO;
import com.asopagos.reportes.dto.GeneracionReporteNormativoDTO;

/**
 * <b>Descripcion:</b> Interfaz que define las funciones para la consulta de información en
 * el modelo de datos Core <br/>
 * <b>Módulo:</b> Asopagos - HU-KPI <br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */

@Local
public interface IConsultasModeloCore {

    /**
     * Servicio que consulta los reportes por el nombre y frecuencia
     * @param parametrizacionMetasDTO
     *        el objeto con la parametrización a utilizar
     */
    @GET
    @Path("/consultarIdentificadorReporte")
    public List<DatosIdentificadorGrupoReporteDTO> consultarIdentificadorReporte(
            @NotNull @QueryParam("nombreReporte") ReporteKPIEnum nombreReporte,
            @NotNull @QueryParam("frecuenciaReporte") FrecuenciaMetaEnum frecuenciaReporte);
    /**
     * Método encargado de guardar la información de los reportes normativos
     * 
     * @param reporteNormativoDTO
     *        <code>GeneracionReporteNormativoDTO</code>
     *        DTO que contiene toda la información relevante del reporte normativo para ser almacenada 
     */
    public void guardarReporteNormativo(GeneracionReporteNormativoDTO reporteNormativoDTO);
    
    /**
     * Metodo que lista todos los reportes normativos oficiales de un reporte especifico
     * @param generacionReporteDTO
     *        <code>GeneracionReporteNormativoDTO</code>
     *        DTO que contiene los filtros necesarios para obtener los registros de los reportes oficiales
     * @return Lista de los reportes oficiales de un tipo de reporte normativo en especifico.
     */
    public List<GeneracionReporteNormativoDTO> consultarHistoricosReportesOficiales(GeneracionReporteNormativoDTO generacionReporteDTO,
            UriInfo uri, HttpServletResponse response, Boolean sinFechas);
    /**
     * Metodo que verifica si se genero o no un reporte normativo en un mismo periodo.
     * @param fechaInicio
     *        <code>Long</code>
     *        Fecha del periodo o fecha inicial en la cual se generara el reporte.
     * @param fechaFin
     *        <code>Long</code>
     *        Fecha final en el cual se genera el reporte.
     * @param reporteNormativo
     *        <code>ReporteNormativoEnum</code>
     *        Tipo de reporte normativo que se desea generar.
     * @return True si hay un reporte eventualmente generado para ese periodo, False de lo contrario.
     */
    public Boolean verificarReporteNormativo(Long fechaInicio, Long fechaFin, ReporteNormativoEnum reporteNormativo);
    
    public CategoriaDTO obtenerCategoriaActual(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado, TipoAfiliadoEnum tipoAfiliado, Boolean isAfiliadoPrincipal);
    //public List<CategoriaAfiliadoDTO> obtenerCategoriaPropiaActual(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado, TipoAfiliadoEnum tipoAfiliado);
    //public List<CategoriaBeneficiarioDTO> obtenerCategoriaActualConyugeAfiliado(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado, TipoAfiliadoEnum tipoAfiliado, List<Object[]> datosIdConyuge);
    public List<Object[]> consultarDatosIdConyuge(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado);
    //public List<Object[]> consultarCategoriaActualAfiliado(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado);
    public List<String> obtenerIdsBeneficarioDetalle(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado);
    public List<Object[]> obtenerInfoDetalladaAfiliadosPpales(List<Object[]> infoAfiPpales);
    
    public List<DetalleBeneficiarioDTO> consultarIdBeneficiarios(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado);
    
    public String obtenerIdAfiliadoConyuge(TipoIdentificacionEnum tipoIdBeneficiario, String numeroIdBeneficiario);
    public List<BigInteger> obtenerIdAfiliadoSecundario(TipoIdentificacionEnum tipoIdBeneficiario, String numeroIdBeneficiario, Long idAfiliado);
    
    /**
     * Método encargado de consultar los permisos parametrizados en DashBoardConsultas
     * 
     * @author <a href="mailto:hhernandez@heinsohn.com.co> Ricardo Hernandez Cediel</a>
     * 
     * @param rolUsuario
     *        <code>String></code>
     *        El rol asociado al usuario y que corresponde con los parametrizados en DashBoardConsultas
     * 
     * @return <code>DashBoardConsultaDTO</code>
     * 	      El permiso parametrizado en dashboard  
     */
    public DashBoardConsultaDTO consultarPermisos(String rolUsuario);
    
    public Long consultarIdPersona(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion);
    
    /**
     * Servicio encargado de consultar las categorias propias del afiliado principal para
     * las vistas 360.
     * 
     * @param tipoIdAfiliado
     *        tipo de identificación del afiliado principal.
     * 
     * @param numeroIdAfiliado
     *        número de identificación del afiliado principal.
     * 
     * @param tipoAfiliado
     *        tipo del afiliado principal (TRABAJADOR_DEPENDIENTE, TRABAJADOR_INDEPENDIENTE
     *        o PENSIONADO).
     * 
     * @return CategoriasComoAfiliadoPrincipalDTO con las categorias
     *         actuales del afiliado y el histórico de las mismas.
     * 
     * @author squintero
     */
    CategoriasComoAfiliadoPrincipalDTO obtenerCategoriasPropiasAfiliado(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado,
            TipoAfiliadoEnum tipoAfiliado);
    
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
    CategoriasComoAfiliadoPrincipalDTO obtenerCategoriasConyugeAfiliado(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado, TipoAfiliadoEnum tipoAfiliado);
 
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
    CategoriasComoBeneficiarioDTO obtenerCategoriasHeredadas(TipoIdentificacionEnum tipoIdAfiliado, String numeroIdAfiliado, Boolean isAfiliadoPrincipal);
    
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
	 * Consulta los datos precargados del formulario de ficha de control de reportes normativos
	 * @param reporteNormativo reorte normativo	
	 * @return datos precargados de la tabla DatosFichaControl
	 */
	public FichaControlDTO consultarValoresPrecargadosFichaControl(ReporteNormativoEnum reporteNormativo);
	
	/**
	 * Guarda DatosFichaControl
	 * @param DatosFichaControl datos	
	 */
	public void guardarDatosFichaControl(DatosFichaControl datosFichaControl);
	
	/**
	 * Consulta los datos precargados del formulario de ficha de control de reportes normativos
	 * @param reporteNormativo reorte normativo	
	 * @return datos precargados de la tabla DatosFichaControl
	 */
	public DatosFichaControl consultarValoresPrecargadosFichaControlEntidad(ReporteNormativoEnum reporteNormativo);
    
	/**
	 * Consulta las categorias del afiliado principal que hereda su beneficiario
     * @param idAfiliado
     * @param idBenDetalle
     * @return
     */
    public List<CategoriaDTO> consultarCategoriasPropiasAfiliadoBeneficiario(Long idAfiliado, Long idBenDetalle);
    
    
    public String consultarParametro(String nombreParametro);

    public List<CategoriaDTO> consultarCategoriaBeneficiarioStoredProcedure(TipoIdentificacionEnum tipoIdBeneficiario, String numeroIdBeneficiario,
    TipoBeneficiarioEnum tipoBeneficiario, Long idAfiliado, Long idBenDetalle, List<BigInteger> idAfiliadoSecundario,
    String idAfiliadoConyuge);
}
