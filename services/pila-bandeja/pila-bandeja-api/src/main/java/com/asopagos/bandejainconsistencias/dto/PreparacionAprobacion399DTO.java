package com.asopagos.bandejainconsistencias.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.enumeraciones.pila.FasePila2Enum;

/**
 * <b>Descripcion:</b> DTO que contiene los datos para los registros que serán gestionados por arpobación o reproceso
 * por bandeja de iconsistencias de PILA M2<br/>
 * <b>Módulo:</b> Asopagos - HU-211-399 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class PreparacionAprobacion399DTO implements Serializable {
    private static final long serialVersionUID = 1260696516310598422L;
    
    @Override
	public String toString() {
		return "PreparacionAprobacion399DTO [idsRegistrosGenerales=" + idsRegistrosGenerales
				+ ", idsRegistrosDetallados=" + idsRegistrosDetallados + ", idTransaccion=" + idTransaccion
				+ ", usuarioAprobador=" + usuarioAprobador + ", sucess=" + sucess + ", esReproceso=" + esReproceso
				+ ", esSimulado=" + esSimulado + ", fase=" + fase + "]";
	}

	/** Listado de IDs de registros generales seleccionados */
    private List<Long> idsRegistrosGenerales;
    
    /** Listado de IDs de registros detallados seleccionados */
    private List<Long> idsRegistrosDetallados;
    
    /** ID de transacción para los registros generales */
    private Long idTransaccion;
    
    /** Nombre del usuario aprobador */
    private String usuarioAprobador;
    
    /** Marca de proceso exitoso */
    private Boolean sucess;
    
    /** Marca de reproceso */
    private Boolean esReproceso;
    
    /** Marca de proceso simulado */
    private Boolean esSimulado;
    
    /** Fase en la que se realiza la aprobación / reproceso */
    private FasePila2Enum fase;

    /**
     * @return the idsRegistrosGenerales
     */
    public List<Long> getIdsRegistrosGenerales() {
        return idsRegistrosGenerales;
    }

    /**
     * @param idsRegistrosGenerales the idsRegistrosGenerales to set
     */
    public void setIdsRegistrosGenerales(List<Long> idsRegistrosGenerales) {
        this.idsRegistrosGenerales = idsRegistrosGenerales;
    }

    /**
     * @return the idsRegistrosDetallados
     */
    public List<Long> getIdsRegistrosDetallados() {
        return idsRegistrosDetallados;
    }

    /**
     * @param idsRegistrosDetallados the idsRegistrosDetallados to set
     */
    public void setIdsRegistrosDetallados(List<Long> idsRegistrosDetallados) {
        this.idsRegistrosDetallados = idsRegistrosDetallados;
    }

    /**
     * @return the idTransaccion
     */
    public Long getIdTransaccion() {
        return idTransaccion;
    }

    /**
     * @param idTransaccion the idTransaccion to set
     */
    public void setIdTransaccion(Long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    /**
     * @return the usuarioAprobador
     */
    public String getUsuarioAprobador() {
        return usuarioAprobador;
    }

    /**
     * @param usuarioAprobador the usuarioAprobador to set
     */
    public void setUsuarioAprobador(String usuarioAprobador) {
        this.usuarioAprobador = usuarioAprobador;
    }

    /**
     * @return the sucess
     */
    public Boolean getSucess() {
        return sucess;
    }

    /**
     * @param sucess the sucess to set
     */
    public void setSucess(Boolean sucess) {
        this.sucess = sucess;
    }

    /**
     * @return the esReproceso
     */
    public Boolean getEsReproceso() {
        return esReproceso;
    }

    /**
     * @param esReproceso the esReproceso to set
     */
    public void setEsReproceso(Boolean esReproceso) {
        this.esReproceso = esReproceso;
    }

    /**
     * @return the esSimulado
     */
    public Boolean getEsSimulado() {
        return esSimulado;
    }

    /**
     * @param esSimulado the esSimulado to set
     */
    public void setEsSimulado(Boolean esSimulado) {
        this.esSimulado = esSimulado;
    }

    /**
     * @return the fase
     */
    public FasePila2Enum getFase() {
        return fase;
    }

    /**
     * @param fase the fase to set
     */
    public void setFase(FasePila2Enum fase) {
        this.fase = fase;
    }
}
