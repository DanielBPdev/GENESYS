package com.asopagos.aportes.composite.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.DocumentoAdministracionEstadoSolicitudDTO;
import com.asopagos.dto.modelo.InformacionFaltanteAportanteModeloDTO;
import com.asopagos.enumeraciones.core.ProcesoEnum;

/**
 * Clase DTO con los datos manejados temporalmente.
 * @author Angélica Toro Murillo <atoro@heinsohn.com.co>
 *
 */
@XmlRootElement
public class GestionInformacionFaltanteDTO implements Serializable{

    /**
	 * Serial
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * Atributo que contiene el id de la solicitud que se esta gestionando.
     */
    private Long idSolicitud;
    /**
     * Lista de la información faltante gestionada.
     */
    private List<InformacionFaltanteAportanteModeloDTO> informacionFaltante;
    
    /**
     * Lista que contiene los documentos adjuntos.
     */
    private List<DocumentoAdministracionEstadoSolicitudDTO> documentos;
    
    /**
     * Atributo que contiene el id de comunicado.
     */
    private Long idComunicado;
    
    /**
     * Indica el proceso de negocio ligado a la solicitud 
     */
    private ProcesoEnum proceso;

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
     * Método que retorna el valor de informacionFaltante.
     * @return valor de informacionFaltante.
     */
    public List<InformacionFaltanteAportanteModeloDTO> getInformacionFaltante() {
        return informacionFaltante;
    }

    /**
     * Método encargado de modificar el valor de informacionFaltante.
     * @param valor para modificar informacionFaltante.
     */
    public void setInformacionFaltante(List<InformacionFaltanteAportanteModeloDTO> informacionFaltante) {
        this.informacionFaltante = informacionFaltante;
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

    /**
     * Método que retorna el valor de idComunicado.
     * @return valor de idComunicado.
     */
    public Long getIdComunicado() {
        return idComunicado;
    }

    /**
     * Método encargado de modificar el valor de idComunicado.
     * @param valor para modificar idComunicado.
     */
    public void setIdComunicado(Long idComunicado) {
        this.idComunicado = idComunicado;
    }

	/**Obtiene el valor de proceso
	 * @return El valor de proceso
	 */
	public ProcesoEnum getProceso() {
		return proceso;
	}

	/** Establece el valor de proceso
	 * @param proceso El valor de proceso por asignar
	 */
	public void setProceso(ProcesoEnum proceso) {
		this.proceso = proceso;
	}
}
