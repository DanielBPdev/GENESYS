/**
 * 
 */
package com.asopagos.pila.dto;

import java.io.Serializable;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import com.asopagos.enumeraciones.aportes.TipoAjusteMovimientoAporteEnum;

/**
 * <b>Descripcion:</b> DTO que representa la gestión del registro de una corrección de aporte por planilla manual<br/>
 * <b>Módulo:</b> Asopagos - HU-211-410 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ResultadoAprobacionCorreccionAporteDTO implements Serializable {
    private static final long serialVersionUID = 1111879984983376772L;

    /**
     * Mensaje de salida del proceso de actualización de estado del archivo
     * */
    private String resultadoValidacion;
    
    /**
     * Aporte Detallado actualizado
     * */
    private AporteDetalladoModeloDTO aporteDetalladoActualizado;
    
    /**
     * Tipo de movimiento de aporte a aplicar en el movimiento creado por la planilla
     * */
    private TipoAjusteMovimientoAporteEnum tipoAjuste;

    /**
     * @return the resultadoValidacion
     */
    public String getResultadoValidacion() {
        return resultadoValidacion;
    }

    /**
     * @param resultadoValidacion the resultadoValidacion to set
     */
    public void setResultadoValidacion(String resultadoValidacion) {
        this.resultadoValidacion = resultadoValidacion;
    }

    /**
     * @return the aporteDetalladoActualizado
     */
    public AporteDetalladoModeloDTO getAporteDetalladoActualizado() {
        return aporteDetalladoActualizado;
    }

    /**
     * @param aporteDetalladoActualizado the aporteDetalladoActualizado to set
     */
    public void setAporteDetalladoActualizado(AporteDetalladoModeloDTO aporteDetalladoActualizado) {
        this.aporteDetalladoActualizado = aporteDetalladoActualizado;
    }

    /**
     * @return the tipoAjuste
     */
    public TipoAjusteMovimientoAporteEnum getTipoAjuste() {
        return tipoAjuste;
    }

    /**
     * @param tipoAjuste the tipoAjuste to set
     */
    public void setTipoAjuste(TipoAjusteMovimientoAporteEnum tipoAjuste) {
        this.tipoAjuste = tipoAjuste;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResultadoAprobacionCorreccionAporteDTO [resultadoValidacion=");
		builder.append(resultadoValidacion);
		builder.append(", aporteDetalladoActualizado=");
		builder.append(aporteDetalladoActualizado);
		builder.append(", tipoAjuste=");
		builder.append(tipoAjuste);
		builder.append("]");
		return builder.toString();
	}
}
