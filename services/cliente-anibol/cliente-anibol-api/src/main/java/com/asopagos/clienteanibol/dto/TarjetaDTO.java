package com.asopagos.clienteanibol.dto;

public class TarjetaDTO {
    
    private String numeroTarjeta;

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
