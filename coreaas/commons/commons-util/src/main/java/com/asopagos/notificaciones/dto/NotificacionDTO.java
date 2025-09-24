package com.asopagos.notificaciones.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.notificaciones.EstadoEnvioNotificacionEnum;

/**
 * DTO con la información que componen un mensaje email
 * 
 * @author Juan Diego Ocampo Q. <jocampo@heinsohn.com.co>
 *
 */
public class NotificacionDTO implements Serializable {

	/** version serial */
	private static final long serialVersionUID = 1L;

	/** lista de destinatarios **/
	private List<String> destinatarioTO;

	/** lista de destinatarios copia **/
	private List<String> destinatarioCC;

	/** lista de destinatarios copia oculta **/
	private List<String> destinatarioBCC;

	/** mensaje del email **/
	private String mensaje;

	/** asunto del email **/
	private String asunto;

	/** lista de archivos adjuntos a envía en el email **/
	private List<ArchivoAdjuntoDTO> archivosAdjuntos = null;

	/** indica si se debe adjuntar como zip **/
	private Boolean archivosAdjuntosZip = Boolean.FALSE;

	/** lista de archivos adjuntos a envía en el email **/
	private List<String> archivosAdjuntosIds = null;

	/** remitente del correo **/
	private String remitente;

	/** evento o proceso que genera la notificación a envíar **/
	private String procesoEvento;

	/** id de la solicitud global asociada al comunicado que se envia */
	private Long idSolicitud;

	/** email del comunicado que se persiste */
	private String email;

	/** texto adicionar del comunicado que se persiste */
	private String textoAdicionar;

	/** parametro a resolver en el cuerpo del mensaje */
	private Map<String, String> params = null;

	/** Tipo de transaccion */
	private TipoTransaccionEnum tipoTx;

	/** replantear destinatario TO */
	private boolean replantearDestinatarioTO;

    /** Enviar adjunto  */
    private boolean noEnviarAdjunto;

    /**
     * DTO con los parametros del comunicado
     */
    private ParametrosComunicadoDTO parametros;
    
    /** identificador del comunicado relacionado  */
    private Long idComunicado;
    
    /** estado del envio de la notificacion  */
    private EstadoEnvioNotificacionEnum estadoEnvioNotificacion;
    
    /** causa de error en el envío */
	private String causaErrorEnvio;
    
	/** Persona a la cual se le envia la notificacion */
    private Long idPersona;
    
    /** Plantilla que se envia en la notificacion */
    private EtiquetaPlantillaComunicadoEnum etiquetaPlantilla;
    
    /**
     * Bandera que indica si la notificación fue o no enviada desde panatallas (Enviada o cancelada)
     */
    private Boolean requiereEnviarse;
    
    /**
     * Empleador involucrado en el envío del comunicado(En caso de que el proceso lo involucre)
     */
    private Long idEmpleador;
    
    /**
     * numero de documento del Empleador involucrado en el envío del comunicado(En caso de que el proceso lo involucre)
     */
	private String numeroIdentificacionEmpleador;

    /**
     * Empresa involucrada en el envío del comunicado(En caso del proceso PILA)
     */
    private Long idEmpresa;
    
    /**
     * Indica si la notificacion requiere el envío del correo(hasta el productor de la cola) 
     * de manera exitosa a la totalidad de sus destinatarios.
     */
    private Boolean envioExitoso;

    /**
     * Indica si la persona destinatario autoriza el envio de correo
     */
    private Boolean autorizaEnvio;
    
    /**
     * Indica si el comunicado fue editado desde la pantalla
     */
    private Boolean comunicadoEditado;
    
	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje
	 *            the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return the destinatarioTO
	 */
	public List<String> getDestinatarioTO() {
		return destinatarioTO;
	}

	/**
	 * @param destinatarioTO
	 *            the destinatarioTO to set
	 */
	public void setDestinatarioTO(List<String> destinatarioTO) {
		this.destinatarioTO = destinatarioTO;
	}

	/**
	 * @return the destinatarioCC
	 */
	public List<String> getDestinatarioCC() {
		return destinatarioCC;
	}

	/**
	 * @param destinatarioCC
	 *            the destinatarioCC to set
	 */
	public void setDestinatarioCC(List<String> destinatarioCC) {
		this.destinatarioCC = destinatarioCC;
	}

	/**
	 * @return the destinatarioBCC
	 */
	public List<String> getDestinatarioBCC() {
		return destinatarioBCC;
	}

	/**
	 * @param destinatarioBCC
	 *            the destinatarioBCC to set
	 */
	public void setDestinatarioBCC(List<String> destinatarioBCC) {
		this.destinatarioBCC = destinatarioBCC;
	}

	/**
	 * @return the asunto
	 */
	public String getAsunto() {
		return asunto;
	}

	/**
	 * @param asunto
	 *            the asunto to set
	 */
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	/**
	 * @return the archivosAdjuntos
	 */
	public List<ArchivoAdjuntoDTO> getArchivosAdjuntos() {
		return archivosAdjuntos;
	}

	/**
	 * @param archivosAdjuntos
	 *            the archivosAdjuntos to set
	 */
	public void setArchivosAdjuntos(List<ArchivoAdjuntoDTO> archivosAdjuntos) {
		this.archivosAdjuntos = archivosAdjuntos;
	}

	/**
	 * @return the archivosAdjuntosZip
	 */
	public Boolean getArchivosAdjuntosZip() {
		return archivosAdjuntosZip;
	}

	/**
	 * @param archivosAdjuntosZip
	 *            the archivosAdjuntosZip to set
	 */
	public void setArchivosAdjuntosZip(Boolean archivosAdjuntosZip) {
		this.archivosAdjuntosZip = archivosAdjuntosZip;
	}

	/**
	 * @return the archivosAdjuntosIds
	 */
	public List<String> getArchivosAdjuntosIds() {
		return archivosAdjuntosIds;
	}

	/**
	 * @param archivosAdjuntosIds
	 *            the archivosAdjuntosIds to set
	 */
	public void setArchivosAdjuntosIds(List<String> archivosAdjuntosIds) {
		this.archivosAdjuntosIds = archivosAdjuntosIds;
	}

	/**
	 * @return the procesoEvento
	 */
	public String getProcesoEvento() {
		return procesoEvento;
	}

	/**
	 * @param procesoEvento
	 *            the procesoEvento to set
	 */
	public void setProcesoEvento(String procesoEvento) {
		this.procesoEvento = procesoEvento;
	}

	/**
	 * @return the remitente
	 */
	public String getRemitente() {
		return remitente;
	}

	/**
	 * @param remitente
	 *            the remitente to set
	 */
	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}

	/**
	 * @return the params
	 */
	public Map<String, String> getParams() {
		return params;
	}

	/**
	 * @param params
	 *            the params to set
	 */
	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	/**
	 * @return the idSolicitud
	 */
	public Long getIdSolicitud() {
		return idSolicitud;
	}

	/**
	 * @param idSolicitud
	 *            the idSolicitud to set
	 */
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the textoAdicionar
	 */
	public String getTextoAdicionar() {
		return textoAdicionar;
	}

	/**
	 * @param textoAdicionar
	 *            the textoAdicionar to set
	 */
	public void setTextoAdicionar(String textoAdicionar) {
		this.textoAdicionar = textoAdicionar;
	}

	/**
	 * @return the tipoTx
	 */
	public TipoTransaccionEnum getTipoTx() {
		return tipoTx;
	}

	/**
	 * @param tipoTx
	 *            the tipoTx to set
	 */
	public void setTipoTx(TipoTransaccionEnum tipoTx) {
		this.tipoTx = tipoTx;
	}

	/**
	 * @return the replantearDestinatarioTO
	 */
	public boolean isReplantearDestinatarioTO() {
		return replantearDestinatarioTO;
	}

	/**
	 * @param replantearDestinatarioTO
	 *            the replantearDestinatarioTO to set
	 */
	public void setReplantearDestinatarioTO(boolean replantearDestinatarioTO) {
		this.replantearDestinatarioTO = replantearDestinatarioTO;
	}

    /**
     * @return the noEnviarAdjunto
     */
    public boolean isNoEnviarAdjunto() {
        return noEnviarAdjunto;
    }

    /**
     * @param noEnviarAdjunto the noEnviarAdjunto to set
     */
    public void setNoEnviarAdjunto(boolean noEnviarAdjunto) {
        this.noEnviarAdjunto = noEnviarAdjunto;
    }

    /**
     * @return the parametros
     */
    public ParametrosComunicadoDTO getParametros() {
        return parametros;
    }

    /**
     * @param parametros the parametros to set
     */
    public void setParametros(ParametrosComunicadoDTO parametros) {
        this.parametros = parametros;
    }

	/**
	 * @return the idComunicado
	 */
	public Long getIdComunicado() {
		return idComunicado;
	}

	/**
	 * @param idComunicado the idComunicado to set
	 */
	public void setIdComunicado(Long idComunicado) {
		this.idComunicado = idComunicado;
	}

	/**
	 * @return the estadoEnvioNotificacion
	 */
	public EstadoEnvioNotificacionEnum getEstadoEnvioNotificacion() {
		return estadoEnvioNotificacion;
	}

	/**
	 * @param estadoEnvioNotificacion the estadoEnvioNotificacion to set
	 */
	public void setEstadoEnvioNotificacion(EstadoEnvioNotificacionEnum estadoEnvioNotificacion) {
		this.estadoEnvioNotificacion = estadoEnvioNotificacion;
	}

	/**
	 * @return the causaErrorEnvio
	 */
	public String getCausaErrorEnvio() {
		return causaErrorEnvio;
	}

	/**
	 * @param causaErrorEnvio the causaErrorEnvio to set
	 */
	public void setCausaErrorEnvio(String causaErrorEnvio) {
		this.causaErrorEnvio = causaErrorEnvio;
	}


		/**
	 * @return the idPersona
	 */
	public Long getIdPersona() {
		return idPersona;
	}

	/**
	 * @param idPersona
	 *            the idPersona to set
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	/**
	 * 
	 * @return the etiquetaPlantilla
	 */
	public EtiquetaPlantillaComunicadoEnum getEtiquetaPlantilla() {
		return etiquetaPlantilla;
	}
	/**
	 * 
	 * @param etiquetaPlantilla
	 */
	public void setEtiquetaPlantilla(EtiquetaPlantillaComunicadoEnum etiquetaPlantilla) {
		this.etiquetaPlantilla = etiquetaPlantilla;
	}

	/**
	 * @return the requiereEnviarse
	 */
	public Boolean getRequiereEnviarse() {
		return requiereEnviarse;
	}

	/**
	 * @param requiereEnviarse the requiereEnviarse to set
	 */
	public void setRequiereEnviarse(Boolean requiereEnviarse) {
		this.requiereEnviarse = requiereEnviarse;
	}

	/**
	 * @return the idEmpleador
	 */
	public Long getIdEmpleador() {
		return idEmpleador;
	}

	/**
	 * @param idEmpleador the idEmpleador to set
	 */
	public void setIdEmpleador(Long idEmpleador) {
		this.idEmpleador = idEmpleador;
	}

	/**
	 * @return the idEmpresa
	 */
	public Long getIdEmpresa() {
		return idEmpresa;
	}

	/**
	 * @param idEmpresa the idEmpresa to set
	 */
	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

    /**
     * @return the envioExitoso
     */
    public Boolean getEnvioExitoso() {
        return envioExitoso;
    }

    /**
     * @param envioExitoso the envioExitoso to set
     */
    public void setEnvioExitoso(Boolean envioExitoso) {
        this.envioExitoso = envioExitoso;
    }

    /**
     * @return the autorizaEnvio
     */
    public Boolean getAutorizaEnvio() {
        return autorizaEnvio;
    }

    /**
     * @param autorizaEnvio the autorizaEnvio to set
     */
    public void setAutorizaEnvio(Boolean autorizaEnvio) {
        this.autorizaEnvio = autorizaEnvio;
    }

    /**
     * @return the comunicadoEditado
     */
    public Boolean getComunicadoEditado() {
        return comunicadoEditado;
    }

    /**
     * @param comunicadoEditado the comunicadoEditado to set
     */
    public void setComunicadoEditado(Boolean comunicadoEditado) {
        this.comunicadoEditado = comunicadoEditado;
    }

	public String getNumeroIdentificacionEmpleador() {
		return this.numeroIdentificacionEmpleador;
	}

	public void setNumeroIdentificacionEmpleador(String numeroIdentificacionEmpleador) {
		this.numeroIdentificacionEmpleador = numeroIdentificacionEmpleador;
	}

	@Override
	public String toString() {
		return "{" +
			" destinatarioTO='" + getDestinatarioTO() + "'" +
			", destinatarioCC='" + getDestinatarioCC() + "'" +
			", destinatarioBCC='" + getDestinatarioBCC() + "'" +
			", mensaje='" + getMensaje() + "'" +
			", asunto='" + getAsunto() + "'" +
			", archivosAdjuntos='" + getArchivosAdjuntos() + "'" +
			", archivosAdjuntosIds='" + getArchivosAdjuntosIds() + "'" +
			", remitente='" + getRemitente() + "'" +
			", procesoEvento='" + getProcesoEvento() + "'" +
			", idSolicitud='" + getIdSolicitud() + "'" +
			", email='" + getEmail() + "'" +
			", textoAdicionar='" + getTextoAdicionar() + "'" +
			", params='" + getParams() + "'" +
			", tipoTx='" + getTipoTx() + "'" +
			", replantearDestinatarioTO='" + isReplantearDestinatarioTO() + "'" +
			", noEnviarAdjunto='" + isNoEnviarAdjunto() + "'" +
			", parametros='" + getParametros() + "'" +
			", idComunicado='" + getIdComunicado() + "'" +
			", estadoEnvioNotificacion='" + getEstadoEnvioNotificacion() + "'" +
			", causaErrorEnvio='" + getCausaErrorEnvio() + "'" +
			", idPersona='" + getIdPersona() + "'" +
			", etiquetaPlantilla='" + getEtiquetaPlantilla() + "'" +
			", idEmpleador='" + getIdEmpleador() + "'" +
			", idEmpresa='" + getIdEmpresa() + "'" +
			", numeroIdentificacionEmpleador='" + getNumeroIdentificacionEmpleador() + "'" +
			"}";
	}
    
}
