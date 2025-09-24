package com.asopagos.dto;

import java.util.Date;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionPersona;
import com.asopagos.entidades.ccf.personas.PersonaDetalle;
import com.asopagos.entidades.ccf.personas.RolAfiliado;
import com.asopagos.entidades.ccf.personas.Afiliado;
import com.asopagos.entidades.ccf.general.Solicitud;
import com.asopagos.entidades.ccf.personas.Empleador;
import com.asopagos.entidades.ccf.core.Ubicacion;
import com.asopagos.entidades.transversal.core.Municipio;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.afiliaciones.EstadoSolicitudAfiliacionPersonaEnum;
import com.asopagos.enumeraciones.afiliaciones.FormatoEntregaDocumentoEnum;
import com.asopagos.enumeraciones.afiliaciones.TipoRadicacionEnum;
import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

/**
 * <b>Descripción:</b> DTO para el método consultarSolicitudesAsociacionPersonas
 * del servicio entidades pagadoras <b>Historia de Usuario:</b> HU-133
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class SolicitudAfiliacionPersonaDTO {

	private Long idSolicitudGlobal;

	private EstadoSolicitudAfiliacionPersonaEnum estadoSolicitud;

	private AfiliadoInDTO afiliadoInDTO;

	private TipoTransaccionEnum tipoTransaccion;

	private Long idTipoSolicitante;

	private String numeroRadicacion;

	private ClasificacionEnum clasificacion;

	private String idInstanciaProceso;

	private FormatoEntregaDocumentoEnum metodoEnvio;

	private String usuarioRadicacion;

	private Date fechaRadicacion;

	private TipoRadicacionEnum tipoRadicacion;

	private Long idSolicitudAfiliacionPersona;
	
	private String comentarioSolicitud;
	
	private Boolean anulada;

	public static SolicitudAfiliacionPersonaDTO convertSolicitudAfiliacionPersonaToDTO(
			SolicitudAfiliacionPersona solicitudAfiliacionPersona, PersonaDetalle personaDetalle) {
		SolicitudAfiliacionPersonaDTO dto = new SolicitudAfiliacionPersonaDTO();
		dto.setEstadoSolicitud(solicitudAfiliacionPersona.getEstadoSolicitud());
		dto.setIdSolicitudAfiliacionPersona(solicitudAfiliacionPersona.getIdSolicitudAfiliacionPersona());
		if (solicitudAfiliacionPersona.getRolAfiliado() != null
				&& solicitudAfiliacionPersona.getRolAfiliado().getAfiliado() != null) {
			dto.setAfiliadoInDTO(
					AfiliadoInDTO.convertAfiliadoToDTO(solicitudAfiliacionPersona.getRolAfiliado().getAfiliado(), personaDetalle));
		}
		if (solicitudAfiliacionPersona.getSolicitudGlobal() != null) {
		    dto.getAfiliadoInDTO().setCanalRecepcion(solicitudAfiliacionPersona.getSolicitudGlobal().getCanalRecepcion());
			dto.setClasificacion(solicitudAfiliacionPersona.getSolicitudGlobal().getClasificacion());
			dto.setFechaRadicacion(solicitudAfiliacionPersona.getSolicitudGlobal().getFechaRadicacion());
			dto.setIdInstanciaProceso(solicitudAfiliacionPersona.getSolicitudGlobal().getIdInstanciaProceso());
			dto.setIdSolicitudGlobal(solicitudAfiliacionPersona.getSolicitudGlobal().getIdSolicitud());
			dto.setMetodoEnvio(solicitudAfiliacionPersona.getSolicitudGlobal().getMetodoEnvio());
			dto.setNumeroRadicacion(solicitudAfiliacionPersona.getSolicitudGlobal().getNumeroRadicacion());
			dto.setTipoRadicacion(solicitudAfiliacionPersona.getSolicitudGlobal().getTipoRadicacion());
			dto.setTipoTransaccion(solicitudAfiliacionPersona.getSolicitudGlobal().getTipoTransaccion());
			dto.setUsuarioRadicacion(solicitudAfiliacionPersona.getSolicitudGlobal().getUsuarioRadicacion());
			dto.setComentarioSolicitud(solicitudAfiliacionPersona.getSolicitudGlobal().getObservacion());
			dto.setAnulada(solicitudAfiliacionPersona.getSolicitudGlobal().getAnulada());
		}
		return dto;
	}


	public SolicitudAfiliacionPersonaDTO(
		SolicitudAfiliacionPersona solicitudAfiliacionPersona,
		PersonaDetalle personaDetalle,
		RolAfiliado rolAfiliado,
		Solicitud solicitud,
		Afiliado afi,
		Empleador empleador,
		Persona persona,
		Ubicacion ubicacion,
		Municipio municipio
		) {
		this.estadoSolicitud = solicitudAfiliacionPersona.getEstadoSolicitud();
		this.idSolicitudAfiliacionPersona = solicitudAfiliacionPersona.getIdSolicitudAfiliacionPersona();
		if (rolAfiliado != null
				&& afi != null) {
			this.afiliadoInDTO = AfiliadoInDTO.convertAfiliadoToDTO(personaDetalle,afi,ubicacion,municipio,persona);
			this.afiliadoInDTO.setIdEmpleador(empleador.getIdEmpleador());
			this.afiliadoInDTO.setIdRolAfiliado(rolAfiliado.getIdRolAfiliado());
			this.afiliadoInDTO.setTipoAfiliado(rolAfiliado.getTipoAfiliado());
			this.afiliadoInDTO.setValorSalarioMesada(rolAfiliado.getValorSalarioMesadaIngresos());
		}
		if (solicitud != null) {
		    this.afiliadoInDTO.setCanalRecepcion(solicitud.getCanalRecepcion());
			this.clasificacion = solicitud.getClasificacion();
			this.fechaRadicacion = solicitud.getFechaRadicacion();
			this.idInstanciaProceso = solicitud.getIdInstanciaProceso();
			this.idSolicitudGlobal = solicitud.getIdSolicitud();
			this.metodoEnvio = solicitud.getMetodoEnvio();
			this.numeroRadicacion = solicitud.getNumeroRadicacion();
			this.tipoRadicacion = solicitud.getTipoRadicacion();
			this.tipoTransaccion = solicitud.getTipoTransaccion();
			this.usuarioRadicacion = solicitud.getUsuarioRadicacion();
			this.comentarioSolicitud = solicitud.getObservacion();
			this.anulada = solicitud.getAnulada();
			this.idTipoSolicitante = solicitudAfiliacionPersona.getIdSolicitudAfiliacionPersona();
		}	

	}
	
	public SolicitudAfiliacionPersonaDTO(
		SolicitudAfiliacionPersona solicitudAfiliacionPersona,
		PersonaDetalle personaDetalle,
		RolAfiliado rolAfiliado,
		Solicitud solicitud,
		Afiliado afi,
		Empleador empleador,
		Persona persona
		) {
		this.estadoSolicitud = solicitudAfiliacionPersona.getEstadoSolicitud();
		this.idSolicitudAfiliacionPersona = solicitudAfiliacionPersona.getIdSolicitudAfiliacionPersona();
		if (rolAfiliado != null
				&& afi != null) {
			this.afiliadoInDTO = AfiliadoInDTO.convertAfiliadoToDTO(afi,personaDetalle);
			this.afiliadoInDTO.setIdEmpleador(empleador.getIdEmpleador());
			this.afiliadoInDTO.setIdRolAfiliado(rolAfiliado.getIdRolAfiliado());
			this.afiliadoInDTO.setTipoAfiliado(rolAfiliado.getTipoAfiliado());
			this.afiliadoInDTO.setValorSalarioMesada(rolAfiliado.getValorSalarioMesadaIngresos());
		}
		if (solicitud != null) {
		    this.afiliadoInDTO.setCanalRecepcion(solicitud.getCanalRecepcion());
			this.clasificacion = solicitud.getClasificacion();
			this.fechaRadicacion = solicitud.getFechaRadicacion();
			this.idInstanciaProceso = solicitud.getIdInstanciaProceso();
			this.idSolicitudGlobal = solicitud.getIdSolicitud();
			this.metodoEnvio = solicitud.getMetodoEnvio();
			this.numeroRadicacion = solicitud.getNumeroRadicacion();
			this.tipoRadicacion = solicitud.getTipoRadicacion();
			this.tipoTransaccion = solicitud.getTipoTransaccion();
			this.usuarioRadicacion = solicitud.getUsuarioRadicacion();
			this.comentarioSolicitud = solicitud.getObservacion();
			this.anulada = solicitud.getAnulada();
			this.idTipoSolicitante = solicitudAfiliacionPersona.getIdSolicitudAfiliacionPersona();
		}	

	}

	public SolicitudAfiliacionPersonaDTO(SolicitudAfiliacionPersona solicitudAfiliacionPersona, PersonaDetalle personaDetalle) {
		this.estadoSolicitud = solicitudAfiliacionPersona.getEstadoSolicitud();
		this.idSolicitudAfiliacionPersona = solicitudAfiliacionPersona.getIdSolicitudAfiliacionPersona();
		if (solicitudAfiliacionPersona.getRolAfiliado() != null
				&& solicitudAfiliacionPersona.getRolAfiliado().getAfiliado() != null) {
			this.afiliadoInDTO = AfiliadoInDTO.convertAfiliadoToDTO(solicitudAfiliacionPersona.getRolAfiliado().getAfiliado(), personaDetalle);
			this.afiliadoInDTO.setIdEmpleador(solicitudAfiliacionPersona.getRolAfiliado().getEmpleador().getIdEmpleador());
			this.afiliadoInDTO.setIdRolAfiliado(solicitudAfiliacionPersona.getRolAfiliado().getIdRolAfiliado());
			this.afiliadoInDTO.setTipoAfiliado(solicitudAfiliacionPersona.getRolAfiliado().getTipoAfiliado());
			this.afiliadoInDTO.setValorSalarioMesada(solicitudAfiliacionPersona.getRolAfiliado().getValorSalarioMesadaIngresos());
		}
		if (solicitudAfiliacionPersona.getSolicitudGlobal() != null) {
		    this.afiliadoInDTO.setCanalRecepcion(solicitudAfiliacionPersona.getSolicitudGlobal().getCanalRecepcion());
			this.clasificacion = solicitudAfiliacionPersona.getSolicitudGlobal().getClasificacion();
			this.fechaRadicacion = solicitudAfiliacionPersona.getSolicitudGlobal().getFechaRadicacion();
			this.idInstanciaProceso = solicitudAfiliacionPersona.getSolicitudGlobal().getIdInstanciaProceso();
			this.idSolicitudGlobal = solicitudAfiliacionPersona.getSolicitudGlobal().getIdSolicitud();
			this.metodoEnvio = solicitudAfiliacionPersona.getSolicitudGlobal().getMetodoEnvio();
			this.numeroRadicacion = solicitudAfiliacionPersona.getSolicitudGlobal().getNumeroRadicacion();
			this.tipoRadicacion = solicitudAfiliacionPersona.getSolicitudGlobal().getTipoRadicacion();
			this.tipoTransaccion = solicitudAfiliacionPersona.getSolicitudGlobal().getTipoTransaccion();
			this.usuarioRadicacion = solicitudAfiliacionPersona.getSolicitudGlobal().getUsuarioRadicacion();
			this.comentarioSolicitud = solicitudAfiliacionPersona.getSolicitudGlobal().getObservacion();
			this.anulada = solicitudAfiliacionPersona.getSolicitudGlobal().getAnulada();
			this.idTipoSolicitante = solicitudAfiliacionPersona.getIdSolicitudAfiliacionPersona();
		}
	}

	public SolicitudAfiliacionPersonaDTO() {
	}


	/**
	 * @return the tipoRadicacion
	 */
	public TipoRadicacionEnum getTipoRadicacion() {
		return tipoRadicacion;
	}

	/**
	 * @param tipoRadicacion
	 *            the tipoRadicacion to set
	 */
	public void setTipoRadicacion(TipoRadicacionEnum tipoRadicacion) {
		this.tipoRadicacion = tipoRadicacion;
	}

	/**
	 * @return the idInstanciaProceso
	 */
	public String getIdInstanciaProceso() {
		return idInstanciaProceso;
	}

	/**
	 * @param idInstanciaProceso
	 *            the idInstanciaProceso to set
	 */
	public void setIdInstanciaProceso(String idInstanciaProceso) {
		this.idInstanciaProceso = idInstanciaProceso;
	}

	/**
	 * @return the idSolicitudGlobal
	 */
	public Long getIdSolicitudGlobal() {
		return idSolicitudGlobal;
	}

	/**
	 * @param idSolicitudGlobal
	 *            the idSolicitudGlobal to set
	 */
	public void setIdSolicitudGlobal(Long idSolicitudGlobal) {
		this.idSolicitudGlobal = idSolicitudGlobal;
	}

	/**
	 * @return the estadoSolicitud
	 */
	public EstadoSolicitudAfiliacionPersonaEnum getEstadoSolicitud() {
		return estadoSolicitud;
	}

	/**
	 * @param estadoSolicitud
	 *            the estadoSolicitud to set
	 */
	public void setEstadoSolicitud(EstadoSolicitudAfiliacionPersonaEnum estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	/**
	 * @return the afiliadoInDTO
	 */
	public AfiliadoInDTO getAfiliadoInDTO() {
		return afiliadoInDTO;
	}

	/**
	 * @param afiliadoInDTO
	 *            the afiliadoInDTO to set
	 */
	public void setAfiliadoInDTO(AfiliadoInDTO afiliadoInDTO) {
		this.afiliadoInDTO = afiliadoInDTO;
	}

	/**
	 * @return the tipoTransaccion
	 */
	public TipoTransaccionEnum getTipoTransaccion() {
		return tipoTransaccion;
	}

	/**
	 * @param tipoTransaccion
	 *            the tipoTransaccion to set
	 */
	public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	/**
	 * @return the idTipoSolicitante
	 */
	public Long getIdTipoSolicitante() {
		return idTipoSolicitante;
	}

	/**
	 * @param idTipoSolicitante
	 *            the idTipoSolicitante to set
	 */
	public void setIdTipoSolicitante(Long idTipoSolicitante) {
		this.idTipoSolicitante = idTipoSolicitante;
	}

	/**
	 * @return the numeroRadicacion
	 */
	public String getNumeroRadicacion() {
		return numeroRadicacion;
	}

	/**
	 * @param numeroRadicacion
	 *            the numeroRadicacion to set
	 */
	public void setNumeroRadicacion(String numeroRadicacion) {
		this.numeroRadicacion = numeroRadicacion;
	}

	/**
	 * @return the clasificacion
	 */
	public ClasificacionEnum getClasificacion() {
		return clasificacion;
	}

	/**
	 * @param clasificacion
	 *            the clasificacion to set
	 */
	public void setClasificacion(ClasificacionEnum clasificacion) {
		this.clasificacion = clasificacion;
	}

	/**
	 * @return the metodoEnvio
	 */
	public FormatoEntregaDocumentoEnum getMetodoEnvio() {
		return metodoEnvio;
	}

	/**
	 * @param metodoEnvio
	 *            the metodoEnvio to set
	 */
	public void setMetodoEnvio(FormatoEntregaDocumentoEnum metodoEnvio) {
		this.metodoEnvio = metodoEnvio;
	}

	/**
	 * @return the usuarioRadicacion
	 */
	public String getUsuarioRadicacion() {
		return usuarioRadicacion;
	}

	/**
	 * @param usuarioRadicacion
	 *            the usuarioRadicacion to set
	 */
	public void setUsuarioRadicacion(String usuarioRadicacion) {
		this.usuarioRadicacion = usuarioRadicacion;
	}

	/**
	 * @return the fechaRadicacion
	 */
	public Date getFechaRadicacion() {
		return fechaRadicacion;
	}

	/**
	 * @param fechaRadicacion
	 *            the fechaRadicacion to set
	 */
	public void setFechaRadicacion(Date fechaRadicacion) {
		this.fechaRadicacion = fechaRadicacion;
	}

	/**
	 * @return the idSolicitudAfiliacionPersona
	 */
	public Long getIdSolicitudAfiliacionPersona() {
		return idSolicitudAfiliacionPersona;
	}

	/**
	 * @param idSolicitudAfiliacionPersona
	 *            the idSolicitudAfiliacionPersona to set
	 */
	public void setIdSolicitudAfiliacionPersona(Long idSolicitudAfiliacionPersona) {
		this.idSolicitudAfiliacionPersona = idSolicitudAfiliacionPersona;
	}

	/**
	 * @return the comentarioSolicitud
	 */
	public String getComentarioSolicitud() {
		return comentarioSolicitud;
	}

	/**
	 * @param comentarioSolicitud the comentarioSolicitud to set
	 */
	public void setComentarioSolicitud(String comentarioSolicitud) {
		this.comentarioSolicitud = comentarioSolicitud;
	}

    /**
     * @return the anulada
     */
    public Boolean getAnulada() {
        return anulada;
    }

    /**
     * @param anulada the anulada to set
     */
    public void setAnulada(Boolean anulada) {
        this.anulada = anulada;
    }

}
