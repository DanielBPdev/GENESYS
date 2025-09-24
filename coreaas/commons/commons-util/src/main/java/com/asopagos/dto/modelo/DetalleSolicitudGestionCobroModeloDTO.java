package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.cartera.DetalleSolicitudGestionCobro;
import com.asopagos.entidades.ccf.core.DocumentoSoporte;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.cartera.EstadoTareaGestionCobroEnum;
import com.asopagos.enumeraciones.cartera.ResultadoEntregaEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * DTO que contiene los campos del detalle de una solicitud de gestion de cobro.
 * Para el caso de primera remision son N detalles, para una primera remisión es un solo detalle.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
@XmlRootElement
public class DetalleSolicitudGestionCobroModeloDTO  implements Serializable {
    
    /**
	 * Serial
	 */
	private static final long serialVersionUID = 1587854545454L;

	/**
     * Atributo que indica el id del DetalleSolicitudGestionCobro.
     */
    private Long idDetalleSolicitudGestionCobro;
    
    /**
     * Atributo que indica el estado de la solicitud de gestión de cobro.
     */
    private EstadoTareaGestionCobroEnum estadoSolicitud;
    /**
     * Atributo que contiene las observaciones de primera remisión.
     */
    private String observacionPrimeraRemision;
    /**
     * Atributo que indica si se envió o no el comunicado de primera remisión.
     */
    private Boolean enviarPrimeraRemision;
    /**
     * Atributo que contiene las observaciones de segunda remisión.
     */
    private String observacionSegundaRemision;
    /**
     * Atributo que indica si se envió o no el comunicado de segunda remisión.
     */    
    private Boolean enviarSegundaRemision;
    /**
     * Atributo que indica el id de la primera solicitud de remisión.
     */
    private Long idPrimeraSolicitudRemision;
    /**
     * Atributo que indica el id de la segunda solicitud de remisión.
     */
    private Long idSegundaSolicitudRemision;
    /**
     * Atributo con la fecha de entrega de la primera entrega.
     */
    private Long fechaPrimeraEntrega;
    /**
     * Atributo con las observaciones de la primera entrega.
     */
    private String observacionPrimeraEntrega;
   
    /**
     * Atributo con la fecha de entrega de la segunda entrega.
     */
    private Long fechaSegundaEntrega;
    /**
     * Atributo con las observaciones de la segunda entrega.
     */
    private String observacionSegundaEntrega;
    /**
     * Identificador del documento asociado a la solicitud. Referencia a la
     * tabla <code>DocumentoCartera</code>
     */
    private DocumentoSoporteModeloDTO documentoPrimeraRemision;
    /**
     * Identificador del documento asociado a la solicitud. Referencia a la
     * tabla <code>DocumentoCartera</code>
     */
    private DocumentoSoporteModeloDTO documentoSegundaRemision;

    
    /**
     * Atributo con la fecha de entrega de la segunda entrega.
     */
    private String fechaPrimeraEntregaString;
    
    /**
     * Atributo con la fecha de entrega de la segunda entrega.
     */
    private String fechaSegundaEntregaString;
    
    /**
     * Atributo que representa el id.
     */
    private Long idCartera;
    
    /**
     * Atributo de tipo enum relacionada al resultado de primera entrega
     */
    private ResultadoEntregaEnum resultadoPrimeraEntrega;
    
    /**
     * Atributo de tipo enum relacionada al resultado de segunda entrega
     */
    private ResultadoEntregaEnum resultadoSegundaEntrega;
    
    /**
     * Atributo que tiene el tipo de identificacion de la persona.
     */
    private TipoIdentificacionEnum tipoIdentificacion;
    
    /**
     * Atributo que tiene el tipo de identificacion de la persona.
     */
    private String numeroIdentificacion;
    
    
    // INICIO AJUSTE RENDIMIENTO
    // se crean variables para optimizar ejecución pero estás variables no hace parte del modelo de datos
    /**
     * Atributo que tiene el identificador del registro de la persona
     */
    private Long idPersona;
    
    /**
     * Atributo que tiene el numero de operación
     */
    private Long numeroOperacion;
    
    /**
     * Atributo que tiene el valor del tipo de solicitante
     */
    private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
    // FIN AJUSTE RENDIMIENTO
    
    /**
     * Método encargado de construir un Detalle de Solicitud de gestión de cobro
     * @param detalleSolicitudGestion, Detalle de solicitud de gestión de cobro
     * @param persona, persona asociada al detalle.
     */
    public DetalleSolicitudGestionCobroModeloDTO(DetalleSolicitudGestionCobro detalleSolicitudGestion,Persona persona, Long numeroOperacion){
    	this.setIdPersona(persona.getIdPersona());
        this.setTipoIdentificacion(persona.getTipoIdentificacion());
        this.setNumeroIdentificacion(persona.getNumeroIdentificacion());
        this.convertToDTO(detalleSolicitudGestion);
        this.setNumeroOperacion(numeroOperacion);
    }
    
    /**
     * Método encargado de construir un Detalle de Solicitud de gestión de cobro
     * @param detalleSolicitudGestion, Detalle de solicitud de gestión de cobro
     * @param persona, persona asociada al detalle.
     */
    public DetalleSolicitudGestionCobroModeloDTO(DetalleSolicitudGestionCobro detalleSolicitudGestion,Persona persona){
        this.setIdPersona(persona.getIdPersona());
        this.setTipoIdentificacion(persona.getTipoIdentificacion());
        this.setNumeroIdentificacion(persona.getNumeroIdentificacion());
        this.convertToDTO(detalleSolicitudGestion);
    }
    
    /**
     * Método encargado de construir un Detalle de Solicitud de gestión de cobro
     * @param detalleSolicitudGestion, Detalle de solicitud de gestión de cobro
     * @param persona, persona asociada al detalle.
     */
    public DetalleSolicitudGestionCobroModeloDTO(DetalleSolicitudGestionCobro detalleSolicitudGestion,Persona persona, Long numeroOperacion, TipoSolicitanteMovimientoAporteEnum tipoSolicitante){
    	this.setIdPersona(persona.getIdPersona());
        this.setTipoIdentificacion(persona.getTipoIdentificacion());
        this.setNumeroIdentificacion(persona.getNumeroIdentificacion());
        this.convertToDTO(detalleSolicitudGestion);
        
        this.numeroOperacion = numeroOperacion;
        this.tipoSolicitante = tipoSolicitante;
    }
    
    /**
     * Contructor del detalle
     */
    public DetalleSolicitudGestionCobroModeloDTO(){
        
    }
    
    /**
     * Método encargado de convertir de DTO a entidad
     * @return detalleSolicitudGestionCobro
     */
    public DetalleSolicitudGestionCobro convertToEntity() {
        DetalleSolicitudGestionCobro detalleSolicitudGestionCobro = new DetalleSolicitudGestionCobro();
        detalleSolicitudGestionCobro.setId(this.getIdDetalleSolicitudGestionCobro());
        detalleSolicitudGestionCobro.setEstado(this.getEstadoSolicitud());
        detalleSolicitudGestionCobro.setObservacionPrimeraRemision(this.getObservacionPrimeraRemision());
        detalleSolicitudGestionCobro.setEnviarPrimeraRemision(this.getEnviarPrimeraRemision());
        detalleSolicitudGestionCobro.setObservacionSegundaRemision(this.getObservacionSegundaRemision());
        detalleSolicitudGestionCobro.setEnviarSegundaRemision(this.getEnviarSegundaRemision());
        detalleSolicitudGestionCobro.setSolicitudPrimeraRemision(this.getIdPrimeraSolicitudRemision());
        detalleSolicitudGestionCobro.setSolicitudSegundaRemision(this.getIdSegundaSolicitudRemision());
        detalleSolicitudGestionCobro.setFechaPrimeraRemision(this.getFechaPrimeraEntrega()!=null ? new Date(this.getFechaPrimeraEntrega()):null);
        detalleSolicitudGestionCobro.setObservacionPrimeraEntrega(this.getObservacionPrimeraEntrega());
        detalleSolicitudGestionCobro.setFechaSegundaRemision(this.getFechaSegundaEntrega()!=null ? new Date(this.getFechaSegundaEntrega()):null);
        detalleSolicitudGestionCobro.setObservacionSegundaEntrega(this.getObservacionSegundaEntrega());
        detalleSolicitudGestionCobro.setIdCartera(this.getIdCartera());
        detalleSolicitudGestionCobro.setResultadoPrimeraEntrega(this.getResultadoPrimeraEntrega());
        detalleSolicitudGestionCobro.setResultadoSegundaEntrega(this.getResultadoSegundaEntrega());
        
        if (this.getDocumentoPrimeraRemision() != null) {
            DocumentoSoporte documento = this.documentoPrimeraRemision.convertToEntity();
            detalleSolicitudGestionCobro.setDocumentoPrimeraRemision(documento != null ? documento : null);
        }
        
        if (this.getDocumentoSegundaRemision() != null) {
            DocumentoSoporte documento = this.documentoSegundaRemision.convertToEntity();
            detalleSolicitudGestionCobro.setDocumentoSegundaRemision(documento != null ? documento : null);
        }
        return detalleSolicitudGestionCobro;
    }

    /**
     * Método encargado de convertir de Entidad a DTO.
     * 
     * @param DetalleSolicitudGestionCobro
     *        entidad a convertir.
     */
    public void convertToDTO(DetalleSolicitudGestionCobro detalleSolicitudGestionCobro) {
        this.setIdDetalleSolicitudGestionCobro(detalleSolicitudGestionCobro.getId());
        this.setEstadoSolicitud(detalleSolicitudGestionCobro.getEstado());
        this.setObservacionPrimeraRemision(detalleSolicitudGestionCobro.getObservacionPrimeraRemision());
        this.setEnviarPrimeraRemision(detalleSolicitudGestionCobro.getEnviarPrimeraRemision());
        this.setObservacionSegundaRemision(detalleSolicitudGestionCobro.getObservacionSegundaRemision());
        this.setEnviarSegundaRemision(detalleSolicitudGestionCobro.getEnviarSegundaRemision());
        this.setIdPrimeraSolicitudRemision(detalleSolicitudGestionCobro.getSolicitudPrimeraRemision());
        this.setIdSegundaSolicitudRemision(detalleSolicitudGestionCobro.getSolicitudSegundaRemision());
        this.setFechaPrimeraEntrega(detalleSolicitudGestionCobro.getFechaPrimeraRemision()!=null ? detalleSolicitudGestionCobro.getFechaPrimeraRemision().getTime() :null);
        this.setObservacionPrimeraEntrega(detalleSolicitudGestionCobro.getObservacionPrimeraEntrega());
        this.setFechaSegundaEntrega(detalleSolicitudGestionCobro.getFechaSegundaRemision()!=null ? detalleSolicitudGestionCobro.getFechaSegundaRemision().getTime() :null);
        this.setObservacionSegundaEntrega(detalleSolicitudGestionCobro.getObservacionSegundaEntrega());
        this.setIdCartera(detalleSolicitudGestionCobro.getIdCartera());
        this.setResultadoPrimeraEntrega(detalleSolicitudGestionCobro.getResultadoPrimeraEntrega());
        this.setResultadoSegundaEntrega(detalleSolicitudGestionCobro.getResultadoSegundaEntrega());
        
        if (detalleSolicitudGestionCobro.getDocumentoPrimeraRemision() != null) {
            DocumentoSoporteModeloDTO documentoSoporteModeloDTO = new DocumentoSoporteModeloDTO();
            documentoSoporteModeloDTO.convertToDTO(detalleSolicitudGestionCobro.getDocumentoPrimeraRemision());
            this.setDocumentoPrimeraRemision(documentoSoporteModeloDTO);
        }
        
        if (detalleSolicitudGestionCobro.getDocumentoSegundaRemision() != null) {
            DocumentoSoporteModeloDTO documentoSoporteModeloDTO = new DocumentoSoporteModeloDTO();
            documentoSoporteModeloDTO.convertToDTO(detalleSolicitudGestionCobro.getDocumentoSegundaRemision());
            this.setDocumentoSegundaRemision(documentoSoporteModeloDTO);
        }
    } 
    
    /**
     * Método encargado de convertir de DTO a entidad
     * @return detalleSolicitudGestionCobro
     */
    public void setNoNullValuesToEntity(DetalleSolicitudGestionCobro detalleSolicitudGestionCobro) {
    	if(this.getIdDetalleSolicitudGestionCobro() != null)
	        detalleSolicitudGestionCobro.setId(this.getIdDetalleSolicitudGestionCobro());
    	
    	if(this.getEstadoSolicitud() != null)
	        detalleSolicitudGestionCobro.setEstado(this.getEstadoSolicitud());
    	
    	if(this.getObservacionPrimeraRemision() != null)
	        detalleSolicitudGestionCobro.setObservacionPrimeraRemision(this.getObservacionPrimeraRemision());
    
    	if(this.getEnviarPrimeraRemision() != null)
	        detalleSolicitudGestionCobro.setEnviarPrimeraRemision(this.getEnviarPrimeraRemision());
    	
    	if(this.getObservacionSegundaRemision() != null)
	        detalleSolicitudGestionCobro.setObservacionSegundaRemision(this.getObservacionSegundaRemision());
    	
    	if(this.getEnviarSegundaRemision() != null)
	        detalleSolicitudGestionCobro.setEnviarSegundaRemision(this.getEnviarSegundaRemision());
    	
    	if(this.getIdPrimeraSolicitudRemision() != null)
	        detalleSolicitudGestionCobro.setSolicitudPrimeraRemision(this.getIdPrimeraSolicitudRemision());
    	
    	if(this.getIdSegundaSolicitudRemision() != null)
	        detalleSolicitudGestionCobro.setSolicitudSegundaRemision(this.getIdSegundaSolicitudRemision());
    	
    	if(this.getFechaPrimeraEntrega() != null)
	        detalleSolicitudGestionCobro.setFechaPrimeraRemision(this.getFechaPrimeraEntrega()!=null ? new Date(this.getFechaPrimeraEntrega()):null);
    	
    	if(this.getObservacionPrimeraEntrega() != null)
	        detalleSolicitudGestionCobro.setObservacionPrimeraEntrega(this.getObservacionPrimeraEntrega());
    	
    	if(this.getFechaSegundaEntrega() != null)
	        detalleSolicitudGestionCobro.setFechaSegundaRemision(this.getFechaSegundaEntrega()!=null ? new Date(this.getFechaSegundaEntrega()):null);
    	
    	if(this.getObservacionSegundaEntrega() != null)
	        detalleSolicitudGestionCobro.setObservacionSegundaEntrega(this.getObservacionSegundaEntrega());
    	
    	if(this.getIdCartera() != null)
	        detalleSolicitudGestionCobro.setIdCartera(this.getIdCartera());
    	
    	if(this.getResultadoPrimeraEntrega() != null)
	        detalleSolicitudGestionCobro.setResultadoPrimeraEntrega(this.getResultadoPrimeraEntrega());
    	
    	if(this.getResultadoSegundaEntrega() != null)
	        detalleSolicitudGestionCobro.setResultadoSegundaEntrega(this.getResultadoSegundaEntrega());
        
        if (this.getDocumentoPrimeraRemision() != null) {
            DocumentoSoporte documento = this.documentoPrimeraRemision.convertToEntity();
            detalleSolicitudGestionCobro.setDocumentoPrimeraRemision(documento != null ? documento : null);
        }
        
        if (this.getDocumentoSegundaRemision() != null) {
            DocumentoSoporte documento = this.documentoSegundaRemision.convertToEntity();
            detalleSolicitudGestionCobro.setDocumentoSegundaRemision(documento != null ? documento : null);
        }
    }

    /**
     * Método que retorna el valor de idDetalleSolicitudGestionCobro.
     * @return valor de idDetalleSolicitudGestionCobro.
     */
    public Long getIdDetalleSolicitudGestionCobro() {
        return idDetalleSolicitudGestionCobro;
    }

    /**
     * Método encargado de modificar el valor de idDetalleSolicitudGestionCobro.
     * @param valor para modificar idDetalleSolicitudGestionCobro.
     */
    public void setIdDetalleSolicitudGestionCobro(Long idDetalleSolicitudGestionCobro) {
        this.idDetalleSolicitudGestionCobro = idDetalleSolicitudGestionCobro;
    }

    /**
     * Método que retorna el valor de estadoSolicitud.
     * @return valor de estadoSolicitud.
     */
    public EstadoTareaGestionCobroEnum getEstadoSolicitud() {
        return estadoSolicitud;
    }

    /**
     * Método encargado de modificar el valor de estadoSolicitud.
     * @param valor para modificar estadoSolicitud.
     */
    public void setEstadoSolicitud(EstadoTareaGestionCobroEnum estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    /**
     * Método que retorna el valor de observacionPrimeraRemision.
     * @return valor de observacionPrimeraRemision.
     */
    public String getObservacionPrimeraRemision() {
        return observacionPrimeraRemision;
    }

    /**
     * Método encargado de modificar el valor de observacionPrimeraRemision.
     * @param valor para modificar observacionPrimeraRemision.
     */
    public void setObservacionPrimeraRemision(String observacionPrimeraRemision) {
        this.observacionPrimeraRemision = observacionPrimeraRemision;
    }

    /**
     * Método que retorna el valor de enviarPrimeraRemision.
     * @return valor de enviarPrimeraRemision.
     */
    public Boolean getEnviarPrimeraRemision() {
        return enviarPrimeraRemision;
    }

    /**
     * Método encargado de modificar el valor de enviarPrimeraRemision.
     * @param valor para modificar enviarPrimeraRemision.
     */
    public void setEnviarPrimeraRemision(Boolean enviarPrimeraRemision) {
        this.enviarPrimeraRemision = enviarPrimeraRemision;
    }

    /**
     * Método que retorna el valor de observacionSegundaRemision.
     * @return valor de observacionSegundaRemision.
     */
    public String getObservacionSegundaRemision() {
        return observacionSegundaRemision;
    }

    /**
     * Método encargado de modificar el valor de observacionSegundaRemision.
     * @param valor para modificar observacionSegundaRemision.
     */
    public void setObservacionSegundaRemision(String observacionSegundaRemision) {
        this.observacionSegundaRemision = observacionSegundaRemision;
    }

    /**
     * Método que retorna el valor de enviarSegundaRemision.
     * @return valor de enviarSegundaRemision.
     */
    public Boolean getEnviarSegundaRemision() {
        return enviarSegundaRemision;
    }

    /**
     * Método encargado de modificar el valor de enviarSegundaRemision.
     * @param valor para modificar enviarSegundaRemision.
     */
    public void setEnviarSegundaRemision(Boolean enviarSegundaRemision) {
        this.enviarSegundaRemision = enviarSegundaRemision;
    }

    /**
     * Método que retorna el valor de idPrimeraSolicitudRemision.
     * @return valor de idPrimeraSolicitudRemision.
     */
    public Long getIdPrimeraSolicitudRemision() {
        return idPrimeraSolicitudRemision;
    }

    /**
     * Método encargado de modificar el valor de idPrimeraSolicitudRemision.
     * @param valor para modificar idPrimeraSolicitudRemision.
     */
    public void setIdPrimeraSolicitudRemision(Long idPrimeraSolicitudRemision) {
        this.idPrimeraSolicitudRemision = idPrimeraSolicitudRemision;
    }

    /**
     * Método que retorna el valor de idSegundaSolicitudRemision.
     * @return valor de idSegundaSolicitudRemision.
     */
    public Long getIdSegundaSolicitudRemision() {
        return idSegundaSolicitudRemision;
    }

    /**
     * Método encargado de modificar el valor de idSegundaSolicitudRemision.
     * @param valor para modificar idSegundaSolicitudRemision.
     */
    public void setIdSegundaSolicitudRemision(Long idSegundaSolicitudRemision) {
        this.idSegundaSolicitudRemision = idSegundaSolicitudRemision;
    }

    /**
     * Método que retorna el valor de fechaPrimeraEntrega.
     * @return valor de fechaPrimeraEntrega.
     */
    public Long getFechaPrimeraEntrega() {
        return fechaPrimeraEntrega;
    }

    /**
     * Método encargado de modificar el valor de fechaPrimeraEntrega.
     * @param valor para modificar fechaPrimeraEntrega.
     */
    public void setFechaPrimeraEntrega(Long fechaPrimeraEntrega) {
        this.fechaPrimeraEntrega = fechaPrimeraEntrega;
    }

    /**
     * Método que retorna el valor de observacionPrimeraEntrega.
     * @return valor de observacionPrimeraEntrega.
     */
    public String getObservacionPrimeraEntrega() {
        return observacionPrimeraEntrega;
    }

    /**
     * Método encargado de modificar el valor de observacionPrimeraEntrega.
     * @param valor para modificar observacionPrimeraEntrega.
     */
    public void setObservacionPrimeraEntrega(String observacionPrimeraEntrega) {
        this.observacionPrimeraEntrega = observacionPrimeraEntrega;
    }

    /**
     * Método que retorna el valor de fechaSegundaEntrega.
     * @return valor de fechaSegundaEntrega.
     */
    public Long getFechaSegundaEntrega() {
        return fechaSegundaEntrega;
    }

    /**
     * Método encargado de modificar el valor de fechaSegundaEntrega.
     * @param valor para modificar fechaSegundaEntrega.
     */
    public void setFechaSegundaEntrega(Long fechaSegundaEntrega) {
        this.fechaSegundaEntrega = fechaSegundaEntrega;
    }

    /**
     * Método que retorna el valor de observacionSegundaEntrega.
     * @return valor de observacionSegundaEntrega.
     */
    public String getObservacionSegundaEntrega() {
        return observacionSegundaEntrega;
    }

    /**
     * Método encargado de modificar el valor de observacionSegundaEntrega.
     * @param valor para modificar observacionSegundaEntrega.
     */
    public void setObservacionSegundaEntrega(String observacionSegundaEntrega) {
        this.observacionSegundaEntrega = observacionSegundaEntrega;
    }

    /**
     * Método que retorna el valor de documentoPrimeraRemision.
     * @return valor de documentoPrimeraRemision.
     */
    public DocumentoSoporteModeloDTO getDocumentoPrimeraRemision() {
        return documentoPrimeraRemision;
    }

    /**
     * Método encargado de modificar el valor de documentoPrimeraRemision.
     * @param valor para modificar documentoPrimeraRemision.
     */
    public void setDocumentoPrimeraRemision(DocumentoSoporteModeloDTO documentoPrimeraRemision) {
        this.documentoPrimeraRemision = documentoPrimeraRemision;
    }

    /**
     * Método que retorna el valor de documentoSegundaRemision.
     * @return valor de documentoSegundaRemision.
     */
    public DocumentoSoporteModeloDTO getDocumentoSegundaRemision() {
        return documentoSegundaRemision;
    }

    /**
     * Método encargado de modificar el valor de documentoSegundaRemision.
     * @param valor para modificar documentoSegundaRemision.
     */
    public void setDocumentoSegundaRemision(DocumentoSoporteModeloDTO documentoSegundaRemision) {
        this.documentoSegundaRemision = documentoSegundaRemision;
    }

    /**
     * Método que retorna el valor de idCartera.
     * @return valor de idCartera.
     */
    public Long getIdCartera() {
        return idCartera;
    }

    /**
     * Método encargado de modificar el valor de idCartera.
     * @param valor para modificar idCartera.
     */
    public void setIdCartera(Long idCartera) {
        this.idCartera = idCartera;
    }

    /**
     * Método que retorna el valor de resultadoPrimeraEntrega.
     * @return valor de resultadoPrimeraEntrega.
     */
    public ResultadoEntregaEnum getResultadoPrimeraEntrega() {
        return resultadoPrimeraEntrega;
    }

    /**
     * Método encargado de modificar el valor de resultadoPrimeraEntrega.
     * @param valor para modificar resultadoPrimeraEntrega.
     */
    public void setResultadoPrimeraEntrega(ResultadoEntregaEnum resultadoPrimeraEntrega) {
        this.resultadoPrimeraEntrega = resultadoPrimeraEntrega;
    }

    /**
     * Método que retorna el valor de resultadoSegundaEntrega.
     * @return valor de resultadoSegundaEntrega.
     */
    public ResultadoEntregaEnum getResultadoSegundaEntrega() {
        return resultadoSegundaEntrega;
    }

    /**
     * Método encargado de modificar el valor de resultadoSegundaEntrega.
     * @param valor para modificar resultadoSegundaEntrega.
     */
    public void setResultadoSegundaEntrega(ResultadoEntregaEnum resultadoSegundaEntrega) {
        this.resultadoSegundaEntrega = resultadoSegundaEntrega;
    }

    /**
     * Método que retorna el valor de tipoIdentificacion.
     * @return valor de tipoIdentificacion.
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de tipoIdentificacion.
     * @param valor para modificar tipoIdentificacion.
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * Método que retorna el valor de numeroIdentificacion.
     * @return valor de numeroIdentificacion.
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * Método encargado de modificar el valor de numeroIdentificacion.
     * @param valor para modificar numeroIdentificacion.
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

	/**
	 * @return the idPersona
	 */
	public Long getIdPersona() {
		return idPersona;
	}

	/**
	 * @param idPersona the idPersona to set
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * @return the numeroOperacion
	 */
	public Long getNumeroOperacion() {
		return numeroOperacion;
	}

	/**
	 * @param numeroOperacion the numeroOperacion to set
	 */
	public void setNumeroOperacion(Long numeroOperacion) {
		this.numeroOperacion = numeroOperacion;
	}

	/**
	 * @return the tipoSolicitante
	 */
	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante() {
		return tipoSolicitante;
	}

	/**
	 * @param tipoSolicitante the tipoSolicitante to set
	 */
	public void setTipoSolicitante(TipoSolicitanteMovimientoAporteEnum tipoSolicitante) {
		this.tipoSolicitante = tipoSolicitante;
	}

    public String getFechaPrimeraEntregaString() {
        return fechaPrimeraEntregaString;
    }

    public void setFechaPrimeraEntregaString(String fechaPrimeraEntregaString) {
        this.fechaPrimeraEntregaString = fechaPrimeraEntregaString;
    }

    public String getFechaSegundaEntregaString() {
        return fechaSegundaEntregaString;
    }

    public void setFechaSegundaEntregaString(String fechaSegundaEntregaString) {
        this.fechaSegundaEntregaString = fechaSegundaEntregaString;
    }
	
	

}
