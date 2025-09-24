package com.asopagos.notificaciones.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import com.asopagos.enumeraciones.comunicados.EtiquetaPlantillaComunicadoEnum;

/**
 * DTO con la información que componen un mensaje email y la plantilla
 * parametrizada
 * 
 * @author <a href="mailto:jocampo@heinsohn.com.co">Juan Diego Ocampo Q.</a>
 *
 */
public class NotificacionParametrizadaDTO extends NotificacionDTO implements Serializable {

	/** version serial */
	private static final long serialVersionUID = 1L;

	/** Identificador de la plantilla comunicado */
	@NotNull
	private EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicado;
	
	/** Descripción de la instancia del proceso **/
    private String idInstanciaProceso;

	/** Valida si el envio es apto para enviarse segun parametro global/modulo **/
	private Boolean validoEnvio;
    
	/**
	 * @return the etiquetaPlantillaComunicado
	 */
	public EtiquetaPlantillaComunicadoEnum getEtiquetaPlantillaComunicado() {
		return etiquetaPlantillaComunicado;
	}

	/**
	 * @param etiquetaPlantillaComunicado
	 *            the etiquetaPlantillaComunicado to set
	 */
	public void setEtiquetaPlantillaComunicado(EtiquetaPlantillaComunicadoEnum etiquetaPlantillaComunicado) {
		this.etiquetaPlantillaComunicado = etiquetaPlantillaComunicado;
	}

    /**
     * @return the idInstanciaProceso
     */
    public String getIdInstanciaProceso() {
        return idInstanciaProceso;
    }

	public Boolean getValidoEnvio() {
		return this.validoEnvio;
	}

	public void setValidoEnvio(Boolean validoEnvio) {
		this.validoEnvio = validoEnvio;
	}

    /**
     * @param idInstanciaProceso the idInstanciaProceso to set
     */
    public void setIdInstanciaProceso(String idInstanciaProceso) {
        this.idInstanciaProceso = idInstanciaProceso;
    }
}
