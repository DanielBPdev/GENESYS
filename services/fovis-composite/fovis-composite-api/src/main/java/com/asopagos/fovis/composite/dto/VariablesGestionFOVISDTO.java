package com.asopagos.fovis.composite.dto;

import java.util.List;
import com.asopagos.dto.fovis.ParametrizacionMedioPagoDTO;
import com.asopagos.dto.modelo.CicloAsignacionModeloDTO;
import com.asopagos.dto.modelo.ParametrizacionEjecucionProgramadaModeloDTO;
import com.asopagos.dto.modelo.ParametrizacionFOVISModeloDTO;
import com.asopagos.dto.modelo.ParametrizacionModalidadModeloDTO;

/**
 * 
 * <b>Descripcion:</b> Clase que hace referencia a la parametrización general para FOVIS<br/>
 * <b>Módulo:</b> Asopagos - HU-321-022 <br/>
 *
 * @author <a href="mailto:alquintero@heinsohn.com.co">Alexander Quintero</a>
 */
public class VariablesGestionFOVISDTO {

    /**
     * Lista que contiene la parametrización general de FOVIS
     */
    private List<ParametrizacionFOVISModeloDTO> parametrizacionesFOVIS;

    /**
     * Lista de las modalidades parametrizadas para FOVIS
     */
    private List<ParametrizacionModalidadModeloDTO> modalidadesFOVIS;

    /**
     * Lista de los ciclos de asignacion parametrizados para FOVIS
     */
    private List<CicloAsignacionModeloDTO> cliclosAsignacion;

    /**
     * Parametrizacion de ejecuciones programada Novedad suspension para FOVIS
     */
    private ParametrizacionEjecucionProgramadaModeloDTO programacionNovedadSuspension;

    /**
     * Parametrizacion de ejecuciones programada Novedad rechazo para FOVIS
     */
    private ParametrizacionEjecucionProgramadaModeloDTO programacionNovedadRechazo;

    /**
     * Parametrizacion de ejecuciones programada Novedad Inhabilidad o Sancion para FOVIS
     */
    private ParametrizacionEjecucionProgramadaModeloDTO programacionLevantarInhabilidadSancion;

    /**
     * Lista de los medios de pago que aplican para FOVIS
     */
    private List<ParametrizacionMedioPagoDTO> mediosDePago;

    /**
     * @return the parametrizacionesFOVIS
     */
    public List<ParametrizacionFOVISModeloDTO> getParametrizacionesFOVIS() {
        return parametrizacionesFOVIS;
    }

    /**
     * @param parametrizacionesFOVIS
     *        the parametrizacionesFOVIS to set
     */
    public void setParametrizacionesFOVIS(List<ParametrizacionFOVISModeloDTO> parametrizacionesFOVIS) {
        this.parametrizacionesFOVIS = parametrizacionesFOVIS;
    }

    /**
     * @return the modalidadesFOVIS
     */
    public List<ParametrizacionModalidadModeloDTO> getModalidadesFOVIS() {
        return modalidadesFOVIS;
    }

    /**
     * @param modalidadesFOVIS
     *        the modalidadesFOVIS to set
     */
    public void setModalidadesFOVIS(List<ParametrizacionModalidadModeloDTO> modalidadesFOVIS) {
        this.modalidadesFOVIS = modalidadesFOVIS;
    }

    /**
     * @return the cliclosAsignacion
     */
    public List<CicloAsignacionModeloDTO> getCliclosAsignacion() {
        return cliclosAsignacion;
    }

    /**
     * @param cliclosAsignacion
     *        the cliclosAsignacion to set
     */
    public void setCliclosAsignacion(List<CicloAsignacionModeloDTO> cliclosAsignacion) {
        this.cliclosAsignacion = cliclosAsignacion;
    }

    /**
     * @return the mediosDePago
     */
    public List<ParametrizacionMedioPagoDTO> getMediosDePago() {
        return mediosDePago;
    }

    /**
     * @param mediosDePago
     *        the mediosDePago to set
     */
    public void setMediosDePago(List<ParametrizacionMedioPagoDTO> mediosDePago) {
        this.mediosDePago = mediosDePago;
    }

    /**
     * @return the programacionNovedadSuspension
     */
    public ParametrizacionEjecucionProgramadaModeloDTO getProgramacionNovedadSuspension() {
        return programacionNovedadSuspension;
    }

    /**
     * @param programacionNovedadSuspension
     *        the programacionNovedadSuspension to set
     */
    public void setProgramacionNovedadSuspension(ParametrizacionEjecucionProgramadaModeloDTO programacionNovedadSuspension) {
        this.programacionNovedadSuspension = programacionNovedadSuspension;
    }

    /**
     * @return the programacionNovedadRechazo
     */
    public ParametrizacionEjecucionProgramadaModeloDTO getProgramacionNovedadRechazo() {
        return programacionNovedadRechazo;
    }

    /**
     * @param programacionNovedadRechazo
     *        the programacionNovedadRechazo to set
     */
    public void setProgramacionNovedadRechazo(ParametrizacionEjecucionProgramadaModeloDTO programacionNovedadRechazo) {
        this.programacionNovedadRechazo = programacionNovedadRechazo;
    }

    /**
     * @return the programacionLevantarInhabilidadSancion
     */
    public ParametrizacionEjecucionProgramadaModeloDTO getProgramacionLevantarInhabilidadSancion() {
        return programacionLevantarInhabilidadSancion;
    }

    /**
     * @param programacionLevantarInhabilidadSancion
     *        the programacionLevantarInhabilidadSancion to set
     */
    public void setProgramacionLevantarInhabilidadSancion(
            ParametrizacionEjecucionProgramadaModeloDTO programacionLevantarInhabilidadSancion) {
        this.programacionLevantarInhabilidadSancion = programacionLevantarInhabilidadSancion;
    }

}
