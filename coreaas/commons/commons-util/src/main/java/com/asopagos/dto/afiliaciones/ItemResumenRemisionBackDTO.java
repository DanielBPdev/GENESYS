package com.asopagos.dto.afiliaciones;

import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

/**
 * <b>Descripción:</b> DTO para los item de resumen del DTO RemisionBackDTO 
 * del servicio generarListadoSolicitudesRemisionBack HU
 * <b>Historia de Usuario:</b> HU-086
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */
public class ItemResumenRemisionBackDTO {
	
	private String sedeDestinatario;
	
	private String destinatario;
	
	private Integer cantidadDocumentos;
	
	private TipoTransaccionEnum tipoTransaccion;
	
	private String observaciones;
	
	private String idInstanciaProceso;

	/**
	 * @return the sedeDestinatario
	 */
	public String getSedeDestinatario() {
		return sedeDestinatario;
	}

	/**
	 * @param sedeDestinatario the sedeDestinatario to set
	 */
	public void setSedeDestinatario(String sedeDestinatario) {
		this.sedeDestinatario = sedeDestinatario;
	}

	/**
	 * @return the destinatario
	 */
	public String getDestinatario() {
		return destinatario;
	}

	/**
	 * @param destinatario the destinatario to set
	 */
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	/**
	 * @return the cantidadDocumentos
	 */
	public Integer getCantidadDocumentos() {
		return cantidadDocumentos;
	}

	/**
	 * @param cantidadDocumentos the cantidadDocumentos to set
	 */
	public void setCantidadDocumentos(Integer cantidadDocumentos) {
		this.cantidadDocumentos = cantidadDocumentos;
	}

	/**
	 * @return the tipoTransaccion
	 */
	public TipoTransaccionEnum getTipoTransaccion() {
		return tipoTransaccion;
	}

	/**
	 * @param tipoTransaccion the tipoTransaccion to set
	 */
	public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

    /**
     * @return the idInstanciaProceso
     */
    public String getIdInstanciaProceso() {
        return idInstanciaProceso;
    }

    /**
     * @param idInstanciaProceso the idInstanciaProceso to set
     */
    public void setIdInstanciaProceso(String idInstanciaProceso) {
        this.idInstanciaProceso = idInstanciaProceso;
    }

    @Override
    public String toString() {
        return "ItemResumenRemisionBackDTO [sedeDestinatario=" + sedeDestinatario + ", destinatario=" + destinatario
                + ", cantidadDocumentos=" + cantidadDocumentos + ", tipoTransaccion=" + tipoTransaccion + ", observaciones=" + observaciones
                + ", idInstanciaProceso=" + idInstanciaProceso + "]";
    }
    
    
}
