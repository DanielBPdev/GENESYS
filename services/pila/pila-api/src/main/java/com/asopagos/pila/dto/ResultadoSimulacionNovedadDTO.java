/**
 * 
 */
package com.asopagos.pila.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.dto.modelo.RegistroDetalladoNovedadModeloDTO;

/**
 * <b>Descripcion:</b> DTO que representa la información que se ha de presentar en la pestaña de novedades<br/>
 * <b>Módulo:</b> Asopagos - HU-211-410 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ResultadoSimulacionNovedadDTO implements Serializable {
    private static final long serialVersionUID = -5711432846251473250L;
    
    /**
     * DTO con los datos de cabecera de la pestaña novedades
     * */
    private CabeceraPestanaAporteNovedadDTO cabecera;
    
    /**
     * Listado de los DTO que contienen los datos de las novedades vigentes del cotizante
     * */
    private List<ResumenNovedadVigenteDTO> novedadesVigentes;
    
    /**
     * Listado de los DTO que contienen las novedades encontradas en el registro detallado
     * */
    private List<RegistroDetalladoNovedadModeloDTO> novedadesRegistroDetallado;

    /**
     * @return the cabecera
     */
    public CabeceraPestanaAporteNovedadDTO getCabecera() {
        return cabecera;
    }

    /**
     * @param cabecera the cabecera to set
     */
    public void setCabecera(CabeceraPestanaAporteNovedadDTO cabecera) {
        this.cabecera = cabecera;
    }

    /**
     * @return the novedadesVigentes
     */
    public List<ResumenNovedadVigenteDTO> getNovedadesVigentes() {
        return novedadesVigentes;
    }

    /**
     * @param novedadesVigentes the novedadesVigentes to set
     */
    public void setNovedadesVigentes(List<ResumenNovedadVigenteDTO> novedadesVigentes) {
        this.novedadesVigentes = novedadesVigentes;
    }

    /**
     * @return the novedadesRegistroDetallado
     */
    public List<RegistroDetalladoNovedadModeloDTO> getNovedadesRegistroDetallado() {
        return novedadesRegistroDetallado;
    }

    /**
     * @param novedadesRegistroDetallado the novedadesRegistroDetallado to set
     */
    public void setNovedadesRegistroDetallado(List<RegistroDetalladoNovedadModeloDTO> novedadesRegistroDetallado) {
        this.novedadesRegistroDetallado = novedadesRegistroDetallado;
    }
}
