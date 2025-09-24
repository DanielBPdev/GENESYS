package com.asopagos.aportes.masivos.service.business.interfaces;


import com.asopagos.aportes.dto.DatosConsultaSubsidioPagadoDTO;
import com.asopagos.aportes.dto.SolicitanteDTO;
import com.asopagos.aportes.masivos.dto.ArchivoAporteMasivoDTO;
import com.asopagos.aportes.masivos.dto.ArchivoDevolucionDTO;
import com.asopagos.dto.EmpresaDTO;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import com.asopagos.dto.modelo.EmpresaModeloDTO;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.dto.modelo.SolicitudAporteModeloDTO;
import com.asopagos.entidades.ccf.aportes.AporteGeneral;
import com.asopagos.entidades.ccf.aportes.PilaEstadoTransitorio;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.pila.masivos.MasivoArchivo;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.rest.security.dto.UserDTO;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import com.asopagos.dto.DefinicionCamposCargaDTO;
import com.asopagos.dto.aportes.CorreccionAportanteDTO;

public interface IConsultasModeloCore {

    public List<SolicitanteDTO> consultarPersonaSolicitanteAporteGeneral(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String periodo);

    public List<SolicitanteDTO> consultarEmpresaSolicitanteAporteGeneral(TipoIdentificacionEnum tipoIdentificacion, 
    String numeroIdentificacion, String periodo);
    
    /**
	 * Consulta el dto de persona por tipo y número de identificación
	 * 
	 * @param tipoIdentificacion
	 *            Tipo de identificación relacionado a una persona
	 * @param numeroIdentificacion
	 *            Número de identificación relacionado a una persona
	 * @return DTO de Persona
	 */
	public PersonaDTO consultarPersonaTipoNumeroIdentificacion(TipoIdentificacionEnum tipoIdentificacion,
			String numeroIdentificacion);
        
        
    /**
	 * 
	 * @return
	 */
	public CriteriaBuilder obtenerCriteriaBuilder();
        
        
        /**
	 * Consulta la empresa por el identificador de la persona
	 * 
	 * @param idPersona
	 *            Identificador de la persona relacionado a la empresa
	 * @return El DTO de Empresa
	 */
	public EmpresaDTO consultarEmpresa(Long idPersona);
        
        
        /**
	 * 
	 * @param c
	 * @return
	 */
	public List<AporteGeneral> obtenerListaAportes(CriteriaQuery<AporteGeneral> c);
        
        /**
	 * Método que consulta la cantidad de movimientos relacionados a un aporte
	 * donde si tipo de ajuste sea DEVOLUCION o CORECCION_A_LA_BAJA
	 * 
	 * @param idsAporte
	 * @return
	 */
	public List<AporteGeneralModeloDTO> consultarAporteYMovimiento(List<Long> idsAporte);
        
        /**
	 * @param ids
	 * @return
	 */
	public List<EmpresaModeloDTO> consultarEmpresasPorIdsPersonas(List<Long> ids);
        
        /**
	 * @param ids
	 * @return
	 */
	public List<EmpresaModeloDTO> consultarEmpresasPorIds(List<Long> ids);
        
    /**
	 * consulta el estado del procesamiento de la planilla 
	 * @param idAporteGeneral
	 * @return
	 */
	public List<PilaEstadoTransitorio> consultarEstadoProcesamientoPlanilla(Long idAporteGeneral);


	/**
	 * consulta el estado del procesamiento de la planilla 
	 * @param idSolicitud
	 * @return
	 */
	public List<Long> consultarsolicitudesGlobales(Long idSolicitud);
        
        /**
	 * @param idsRegistroGeneral
	 * @return
	 */
	public Map<Long, SolicitudAporteModeloDTO> consultarSolicitudesDevolucionListaIds(List<Long> idsRegistroGeneral);
        
        /**
	 * @param datosCotizantes
	 * @return
	 */
	public List<DatosConsultaSubsidioPagadoDTO> consultarPagoSubsidioCotizantes(
			List<DatosConsultaSubsidioPagadoDTO> datosCotizantes);
        
        
        /**
	 * @param idsPersona
	 * @param desdeEmpresa
	 * @return
	 */
	public Map<Long, PersonaModeloDTO> consultarPersonasPorListadoIds(List<Long> idsPersona, Boolean desdeEmpresa);
        
        
	/**
	 * Método encargado de determinar sí un cotizante a causado subsidio
	 * monetario
	 * 
	 * @param tipoIdentificacionCotizante
	 *            Tipo de identificación del cotizante a consultar
	 * @param numeroIdentificacionCotizante
	 *            Número de identificación del cotizante a consultar
	 * @param periodoAporte
	 *            Período de aporte para el cual se hace la consulta
	 * @return <b>Boolean</b> Indica sí el cotizante causó o no subsidio para el
	 *         período
	 */
	public Boolean cotizanteConSubsidioPeriodo(TipoIdentificacionEnum tipoIdentificacionCotizante,
			String numeroIdentificacionCotizante, String periodoAporte);

	public List<ArchivoAporteMasivoDTO> popularDatosMasivoArchivoAportes(List<MasivoArchivo> archivosMasivos);

	public Solicitud consultarSolicitudGlobal(Long idSolicitud);

	public Solicitud consultarSolicitudGlobalPorRadicado(String numeroRadicacion);

	public List<DefinicionCamposCargaDTO> consultarCamposDelArchivo(Long fileLoadedId);

	public Long consultarIdConsolaAporte(Long idCargue);

	public CorreccionAportanteDTO popularCorreccionAportante(CorreccionAportanteDTO correccionAportanteDTO);

	public void actualizarSolicitudDevolucionMasiva(Solicitud solicitud);

	public void simularDevolucionMasivoCore(String numeroRadicado,Long idMedioPag);

}
