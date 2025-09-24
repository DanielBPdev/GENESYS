package com.asopagos.bandejainconsistencias.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:rarboleda@heinsohn.com.co"> rarboleda</a>
 */

public class BusquedaPorPersonaRespuestaDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private RegistroGeneralModeloDTO cabecera;
    private List<RegistroDetalladoModeloDTO> detalle;
    private BigDecimal sumatoriaAportes;
    private Long totalCotizantes;
    private TipoArchivoPilaEnum tipoArchivo;
    
    /**
     * @return the cabecera
     */
    public RegistroGeneralModeloDTO getCabecera() {
        return cabecera;
    }
    /**
     * @param cabecera the cabecera to set
     */
    public void setCabecera(RegistroGeneralModeloDTO cabecera) {
        this.cabecera = cabecera;
    }
    /**
     * @return the detalle
     */
    public List<RegistroDetalladoModeloDTO> getDetalle() {
        return detalle;
    }
    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(List<RegistroDetalladoModeloDTO> detalle) {
        this.detalle = detalle;
    }
    /**
     * @return the sumatoriaAportes
     */
    public BigDecimal getSumatoriaAportes() {
        return sumatoriaAportes;
    }
    /**
     * @param sumatoriaAportes the sumatoriaAportes to set
     */
    public void setSumatoriaAportes(BigDecimal sumatoriaAportes) {
        this.sumatoriaAportes = sumatoriaAportes;
    }
    /**
     * @return the totalCotizantes
     */
    public Long getTotalCotizantes() {
        return totalCotizantes;
    }
    /**
     * @param totalCotizantes the totalCotizantes to set
     */
    public void setTotalCotizantes(Long totalCotizantes) {
        this.totalCotizantes = totalCotizantes;
    }
    /**
     * @return the tipoArchivo
     */
    public TipoArchivoPilaEnum getTipoArchivo() {
        return tipoArchivo;
    }
    /**
     * @param tipoArchivo the tipoArchivo to set
     */
    public void setTipoArchivo(TipoArchivoPilaEnum tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }
}
