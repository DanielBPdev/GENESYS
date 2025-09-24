/**
 * 
 */
package com.asopagos.dto.modelo;

import java.util.Date;
import java.util.List;
import com.asopagos.dto.DocumentoAdministracionEstadoSolicitudDTO;
import com.asopagos.entidades.ccf.aportes.InformacionFaltanteAportante;
import com.asopagos.enumeraciones.aportes.MedioComunicacionEnum;
import com.asopagos.enumeraciones.aportes.ResponsableInformacionEnum;
import com.asopagos.enumeraciones.aportes.TipoDocumentoGestionEnum;
import com.asopagos.enumeraciones.aportes.TipoGestionEnum;


/**
 * Clase DTO que registra la gestión para completar la información de la solicitud de pago manual de aportes.
 * @author Cristian David Parra Zuluaga criparra@heinsohn.com.co
 */
public class InformacionFaltanteAportanteModeloDTO {

	/**
	 * Código identificador de llave primaria de la información faltante.
	 */
	private Long idInformacionFaltante;
    
    
    /**
     * Referencia a la solicitud de aporte.
     */
	private Long idSolicitud;
	
    /**
     * Tipo de gestión a realizar.
     */ 
    private TipoGestionEnum tipoGestion;

    /**
     * Responsable de la información faltante.
     */
    private ResponsableInformacionEnum responsableInformacion;
	
	/**
	 * Fecha en la que se registra la información faltante.
	 */
	private Long fechaGestion;
	
	/**
	 * Tipo de documento de la gestion faltante.
	 */
	private TipoDocumentoGestionEnum tipoDocumento;
	
	/**
	 * Medio de comunicación.
	 */
	private MedioComunicacionEnum medioComunicacion;
	
	/**
	 * Observaciones a registrar durante el proceso de gestión.
	 */
	private String observaciones;
	
	   /**
     * Fecha de registro de la información faltante.
     */
    private Long fechaRegistro;
    /**
     * Usuuario que suministró la información faltante.
     */
    private String usuario;
    /**
     * Documentos asociados a la información faltante.
     */
    private List<DocumentoAdministracionEstadoSolicitudDTO> documentos;
	
	/**
	 * Método que convierte la entidad a informacionFaltanteAportante.
	 * @param informacionFaltanteAportante representada en forma de entidad.
	 */
	public void convertToDTO(InformacionFaltanteAportante informacionFaltanteAportante){
		
		this.setIdInformacionFaltante(informacionFaltanteAportante.getIdInformacionFaltante());
		this.setIdSolicitud(informacionFaltanteAportante.getIdSolicitud());
		this.setTipoGestion(informacionFaltanteAportante.getTipoGestion());
		this.setResponsableInformacion(informacionFaltanteAportante.getResponsableInformacion());
		if (informacionFaltanteAportante.getFechaGestion() != null) {
			this.setFechaGestion(informacionFaltanteAportante.getFechaGestion().getTime());
		}
		this.setTipoDocumento(informacionFaltanteAportante.getTipoDocumento());
		this.setMedioComunicacion(informacionFaltanteAportante.getMedioComunicacion());
		this.setObservaciones(informacionFaltanteAportante.getObservaciones());
	    if (informacionFaltanteAportante.getFechaGestion() != null) {
	        this.setFechaRegistro(informacionFaltanteAportante.getFechaRegistro().getTime());
	    }
	    this.setUsuario(informacionFaltanteAportante.getUsuario());
		
	}
	
	/**
	 * Método que convierte de DTO a una Entidad.
	 * @return informacionFaltanteAportante convertida.
	 */
	public InformacionFaltanteAportante convertToEntity(){
	
		InformacionFaltanteAportante informacionFaltanteAportante = new InformacionFaltanteAportante();
		informacionFaltanteAportante.setIdInformacionFaltante(this.getIdInformacionFaltante());
		informacionFaltanteAportante.setIdSolicitud(this.getIdSolicitud());
		informacionFaltanteAportante.setTipoGestion(this.getTipoGestion());
		informacionFaltanteAportante.setResponsableInformacion(this.getResponsableInformacion());
		if(this.getFechaGestion()!=null){
			informacionFaltanteAportante.setFechaGestion(new Date(this.getFechaGestion()));
    	}
		informacionFaltanteAportante.setTipoDocumento(this.getTipoDocumento());
		informacionFaltanteAportante.setMedioComunicacion(this.getMedioComunicacion());
		informacionFaltanteAportante.setObservaciones(this.getObservaciones());
		informacionFaltanteAportante.setUsuario(this.getUsuario());
	    if(this.getFechaRegistro()!=null){
	        informacionFaltanteAportante.setFechaRegistro(new Date(this.getFechaRegistro()));
	    }
		return informacionFaltanteAportante;
	}
	
    /**
     * Copia los datos del DTO a la Entidad.
     * @param informacionFaltanteAportante previamente consultada.
     */
	public InformacionFaltanteAportante copyDTOToEntity(InformacionFaltanteAportante informacionFaltanteAportante) {
		if (this.getIdInformacionFaltante() != null) {
			informacionFaltanteAportante.setIdInformacionFaltante(this.getIdInformacionFaltante());
		}
		if (this.getIdSolicitud() != null) {
			informacionFaltanteAportante.setIdSolicitud(this.getIdSolicitud());
		}
		if (this.getTipoGestion() != null) {
			informacionFaltanteAportante.setTipoGestion(this.getTipoGestion());
		}
		if (this.getResponsableInformacion() != null) {
			informacionFaltanteAportante.setResponsableInformacion(this.getResponsableInformacion());
		}
		if (this.getFechaGestion() != null) {
			informacionFaltanteAportante.setFechaGestion(new Date(this.getFechaGestion()));
		}
		if (this.getTipoDocumento() != null) {
			informacionFaltanteAportante.setTipoDocumento(this.getTipoDocumento());
		}
		if (this.getMedioComunicacion() != null) {
			informacionFaltanteAportante.setMedioComunicacion(this.getMedioComunicacion());
		}
		if (this.getObservaciones() != null) {
			informacionFaltanteAportante.setObservaciones(this.getObservaciones());
		}
		if (this.getUsuario() != null) {
		    informacionFaltanteAportante.setUsuario(this.getUsuario());
	    }
		return informacionFaltanteAportante;
		
	}
	
	/**
	 * Método que retorna el valor de idInformacionFaltante.
	 * @return valor de idInformacionFaltante.
	 */
	public Long getIdInformacionFaltante() {
		return idInformacionFaltante;
	}

	/**
	 * Método encargado de modificar el valor de idInformacionFaltante.
	 * @param valor para modificar idInformacionFaltante.
	 */
	public void setIdInformacionFaltante(Long idInformacionFaltante) {
		this.idInformacionFaltante = idInformacionFaltante;
	}

	/**
	 * Método que retorna el valor de idSolicitud.
	 * @return valor de idSolicitud.
	 */
	public Long getIdSolicitud() {
		return idSolicitud;
	}

	/**
	 * Método encargado de modificar el valor de idSolicitud.
	 * @param valor para modificar idSolicitud.
	 */
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	/**
	 * Método que retorna el valor de tipoGestion.
	 * @return valor de tipoGestion.
	 */
	public TipoGestionEnum getTipoGestion() {
		return tipoGestion;
	}

	/**
	 * Método encargado de modificar el valor de tipoGestion.
	 * @param valor para modificar tipoGestion.
	 */
	public void setTipoGestion(TipoGestionEnum tipoGestion) {
		this.tipoGestion = tipoGestion;
	}

	/**
	 * Método que retorna el valor de responsableInformacion.
	 * @return valor de responsableInformacion.
	 */
	public ResponsableInformacionEnum getResponsableInformacion() {
		return responsableInformacion;
	}

	/**
	 * Método encargado de modificar el valor de responsableInformacion.
	 * @param valor para modificar responsableInformacion.
	 */
	public void setResponsableInformacion(ResponsableInformacionEnum responsableInformacion) {
		this.responsableInformacion = responsableInformacion;
	}

	/**
	 * Método que retorna el valor de fechaGestion.
	 * @return valor de fechaGestion.
	 */ 
	public Long getFechaGestion() {
		return fechaGestion;
	}

	/**
	 * Método encargado de modificar el valor de fechaGestion.
	 * @param valor para modificar fechaGestion.
	 */
	public void setFechaGestion(Long fechaGestion) {
		this.fechaGestion = fechaGestion;
	}

	/**
	 * Método que retorna el valor de tipoDocumento.
	 * @return valor de tipoDocumento.
	 */
	public TipoDocumentoGestionEnum getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * Método encargado de modificar el valor de tipoDocumento.
	 * @param valor para modificar tipoDocumento.
	 */
	public void setTipoDocumento(TipoDocumentoGestionEnum tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	/**
	 * Método que retorna el valor de medioComunicacion.
	 * @return valor de medioComunicacion.
	 */
	public MedioComunicacionEnum getMedioComunicacion() {
		return medioComunicacion;
	}

	/**
	 * Método encargado de modificar el valor de medioComunicacion.
	 * @param valor para modificar medioComunicacion.
	 */
	public void setMedioComunicacion(MedioComunicacionEnum medioComunicacion) {
		this.medioComunicacion = medioComunicacion;
	}

	/**
	 * Método que retorna el valor de observaciones.
	 * @return valor de observaciones.
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * Método encargado de modificar el valor de observaciones.
	 * @param valor para modificar observaciones.
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

    /**
     * Método que retorna el valor de fechaRegistro.
     * @return valor de fechaRegistro.
     */
    public Long getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Método encargado de modificar el valor de fechaRegistro.
     * @param valor para modificar fechaRegistro.
     */
    public void setFechaRegistro(Long fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Método que retorna el valor de usuario.
     * @return valor de usuario.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Método encargado de modificar el valor de usuario.
     * @param valor para modificar usuario.
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Método que retorna el valor de documentos.
     * @return valor de documentos.
     */
    public List<DocumentoAdministracionEstadoSolicitudDTO> getDocumentos() {
        return documentos;
    }

    /**
     * Método encargado de modificar el valor de documentos.
     * @param valor para modificar documentos.
     */
    public void setDocumentos(List<DocumentoAdministracionEstadoSolicitudDTO> documentos) {
        this.documentos = documentos;
    }
}
