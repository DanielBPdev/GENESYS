package com.asopagos.afiliaciones.wsCajasan.dto;
import java.io.Serializable;

public class BuscarTarjetaOutDTO implements Serializable{
    
    private String numeroTarjeta;

    public BuscarTarjetaOutDTO() {
    }
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TarjetaDTO [numeroTarjeta=");
		builder.append(numeroTarjeta);
		builder.append("]");
		return builder.toString();
	} 
    
}
