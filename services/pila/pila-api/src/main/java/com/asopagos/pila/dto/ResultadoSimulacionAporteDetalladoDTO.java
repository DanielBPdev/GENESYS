package com.asopagos.pila.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.dto.modelo.AporteDetalladoPlanillaDTO;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;

/**
 * <b>Descripcion:</b> DTO en el que se organizan los datos a presentar en la pestaña "Aportes" del
 * procesamiento asistido de planillas de corrección<br/>
 * <b>Módulo:</b> Asopagos - HU-211-410 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ResultadoSimulacionAporteDetalladoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * ID del registro detallado de la correción
     * */
    private Long idRegistroGeneralCorreccion;
    
    /**
     * DTO con los datos de cabecera de la pestaña aportes
     * */
    private CabeceraPestanaAporteNovedadDTO cabecera;

    /**
     * Listado de los aportes de la planilla original relacionados al cotizante
     */
    private List<AporteDetalladoPlanillaDTO> aportesPorPlanilla;

    /**
     * DTO con los datos del registro detallado A en archivo corrección
     */
    private RegistroDetalladoModeloDTO regDetalladoA;

    /**
     * Listado de DTOs con los datos del registro detallado C en archivo corrección
     */
    private RegistroDetalladoModeloDTO regDetalladoC;

    /**
     * @return the aportesPorPlanilla
     */
    public List<AporteDetalladoPlanillaDTO> getAportesPorPlanilla() {
        return aportesPorPlanilla;
    }

    /**
     * @param aportesPorPlanilla
     *        the aportesPorPlanilla to set
     */
    public void setAportesPorPlanilla(List<AporteDetalladoPlanillaDTO> aportesPorPlanilla) {
        this.aportesPorPlanilla = aportesPorPlanilla;
    }

    /**
     * @return the regDetalladoA
     */
    public RegistroDetalladoModeloDTO getRegDetalladoA() {
        return regDetalladoA;
    }

    /**
     * @param regDetalladoA
     *        the regDetalladoA to set
     */
    public void setRegDetalladoA(RegistroDetalladoModeloDTO regDetalladoA) {
        this.regDetalladoA = regDetalladoA;
    }

    /**
     * @return the regDetalladoC
     */
    public RegistroDetalladoModeloDTO getRegDetalladoC() {
        return regDetalladoC;
    }

    /**
     * @param regDetalladoC the regDetalladoC to set
     */
    public void setRegDetalladoC(RegistroDetalladoModeloDTO regDetalladoC) {
        this.regDetalladoC = regDetalladoC;
    }

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
     * @return the idRegistroGeneralCorreccion
     */
    public Long getIdRegistroGeneralCorreccion() {
        return idRegistroGeneralCorreccion;
    }

    /**
     * @param idRegistroGeneralCorreccion the idRegistroGeneralCorreccion to set
     */
    public void setIdRegistroGeneralCorreccion(Long idRegistroGeneralCorreccion) {
        this.idRegistroGeneralCorreccion = idRegistroGeneralCorreccion;
    }
}
