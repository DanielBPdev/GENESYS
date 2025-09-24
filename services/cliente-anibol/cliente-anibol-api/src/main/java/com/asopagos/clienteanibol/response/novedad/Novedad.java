
package com.asopagos.clienteanibol.response.novedad;

import com.google.gson.annotations.SerializedName;

public class Novedad {

    @SerializedName("esOperacionExitosa")
    private String esOperacionExitosa;
    
    @SerializedName("mensajeRespuesta")
    private MensajeRespuesta mensajeRespuesta;
    
    @SerializedName("id_proceso")
    private String idProceso;
    
	
    public String getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(String idProceso) {
        this.idProceso = idProceso;
    }

    public String getEsOperacionExitosa() {
		return esOperacionExitosa;
	}

	public void setEsOperacionExitosa(String esOperacionExitosa) {
		this.esOperacionExitosa = esOperacionExitosa;
	}

	public MensajeRespuesta getMensajeRespuesta() {
		return mensajeRespuesta;
	}

	public void setMensajeRespuesta(MensajeRespuesta mensajeRespuesta) {
		this.mensajeRespuesta = mensajeRespuesta;
	}

}