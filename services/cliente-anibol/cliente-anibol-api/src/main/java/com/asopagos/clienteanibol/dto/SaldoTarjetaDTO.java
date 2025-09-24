package com.asopagos.clienteanibol.dto;

import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.TipoProcesoAnibolEnum;

public class SaldoTarjetaDTO {

    private String saldo;
    private String numeroTarjeta;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String tipoProceso;

    public String getSaldo() {
        return saldo;
    }
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }
    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }
    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }
	public String getTipoProceso() {
		return tipoProceso;
	}
	public void setTipoProceso(String tipoProceso) {
		this.tipoProceso = tipoProceso;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SaldoTarjetaDTO [saldo=" + saldo + ", numeroTarjeta=" + numeroTarjeta + ", tipoIdentificacion="
				+ tipoIdentificacion + ", numeroIdentificacion=" + numeroIdentificacion + ", tipoProceso=" + tipoProceso
				+ "]";
	}
}