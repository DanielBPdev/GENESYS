package com.asopagos.dto.afiliaciones;

import javax.validation.constraints.NotNull;
import com.asopagos.enumeraciones.afiliaciones.EstadoDocumentacionEnum;

/**
 * <b>Descripción:</b> DTO para información del destinatario de la caja de 
 * correspondencia
 * <b>Historia de Usuario:</b> 111-071
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class RecepcionSolicitudDTO {
    
    @NotNull
    private EstadoDocumentacionEnum estadoDocumento;

    private String numeroCustodia;
	/**
	 * @return the estadoDocumento
	 */
	public EstadoDocumentacionEnum getEstadoDocumento() {
		return estadoDocumento;
	}
	/**
	 * @param estadoDocumento the estadoDocumento to set
	 */
	public void setEstadoDocumento(EstadoDocumentacionEnum estadoDocumento) {
		this.estadoDocumento = estadoDocumento;
	}
	/**
	 * @return the numeroCustodia
	 */
	public String getNumeroCustodia() {
		return numeroCustodia;
	}
	/**
	 * @param numeroCustodia the numeroCustodia to set
	 */
	public void setNumeroCustodia(String numeroCustodia) {
		this.numeroCustodia = numeroCustodia;
	}
}
