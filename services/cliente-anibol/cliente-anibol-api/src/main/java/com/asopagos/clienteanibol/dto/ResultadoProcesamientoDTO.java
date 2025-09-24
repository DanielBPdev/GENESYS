package com.asopagos.clienteanibol.dto;

public class ResultadoProcesamientoDTO {
    
    private boolean exitoso;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String numeroTarjeta;
    private String estado;

    public boolean isExitoso() {
        return exitoso;
    }
    
    public boolean getExitoso() {
        return exitoso;
    }

    public void setExitoso(boolean exitoso) {
        this.exitoso = exitoso;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResultadoProcesamientoDTO [exitoso=" + exitoso + ", tipoIdentificacion=" + tipoIdentificacion
				+ ", numeroIdentificacion=" + numeroIdentificacion + ", numeroTarjeta=" + numeroTarjeta + ", estado="
				+ estado + "]";
	}
    
    
}
