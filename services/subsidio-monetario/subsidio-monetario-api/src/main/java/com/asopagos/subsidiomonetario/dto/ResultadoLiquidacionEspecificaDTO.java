package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoCumplimientoEnum;

/**
 * <b>Descripcion:</b> DTO que contiene el resultado de la liquidacion especifica<br/>
 * <b>Módulo:</b> Asopagos - Asopagos - HU-311-523<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class ResultadoLiquidacionEspecificaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Nombre del afiliado principal */
    private String nombreAfiliadoPrincipal;

    /** Indicador de cumplimiento */
    private TipoCumplimientoEnum cumpleCondiciones;

    /** Lista de DTO´s con los resultados para la sección de evaluación */
    private List<DetalleResultadoEvaluacionDTO> resultadoEvaluacion;

    /** Lista de DTO´s con los resultador por adminstrador */
    private List<DetalleResultadoPorAdministradorDTO> resultadoPorAdministrador;

    /** Detalle del desembolso de subsidio */
    private DetalleDesembolsoSubsidioDTO detalleDesembolso;

    /**
     * @return the nombreAfiliadoPrincipal
     */
    public String getNombreAfiliadoPrincipal() {
        return nombreAfiliadoPrincipal;
    }

    /**
     * @param nombreAfiliadoPrincipal
     *        the nombreAfiliadoPrincipal to set
     */
    public void setNombreAfiliadoPrincipal(String nombreAfiliadoPrincipal) {
        this.nombreAfiliadoPrincipal = nombreAfiliadoPrincipal;
    }

    /**
     * @return the cumpleCondiciones
     */
    public TipoCumplimientoEnum getCumpleCondiciones() {
        return cumpleCondiciones;
    }

    /**
     * @param cumpleCondiciones
     *        the cumpleCondiciones to set
     */
    public void setCumpleCondiciones(TipoCumplimientoEnum cumpleCondiciones) {
        this.cumpleCondiciones = cumpleCondiciones;
    }

    /**
     * @return the resultadoEvaluacion
     */
    public List<DetalleResultadoEvaluacionDTO> getResultadoEvaluacion() {
        return resultadoEvaluacion;
    }

    /**
     * @param resultadoEvaluacion
     *        the resultadoEvaluacion to set
     */
    public void setResultadoEvaluacion(List<DetalleResultadoEvaluacionDTO> resultadoEvaluacion) {
        this.resultadoEvaluacion = resultadoEvaluacion;
    }

    /**
     * @return the resultadoPorAdministrador
     */
    public List<DetalleResultadoPorAdministradorDTO> getResultadoPorAdministrador() {
        return resultadoPorAdministrador;
    }

    /**
     * @param resultadoPorAdministrador
     *        the resultadoPorAdministrador to set
     */
    public void setResultadoPorAdministrador(List<DetalleResultadoPorAdministradorDTO> resultadoPorAdministrador) {
        this.resultadoPorAdministrador = resultadoPorAdministrador;
    }

    /**
     * @return the detalleDesembolso
     */
    public DetalleDesembolsoSubsidioDTO getDetalleDesembolso() {
        return detalleDesembolso;
    }

    /**
     * @param detalleDesembolso
     *        the detalleDesembolso to set
     */
    public void setDetalleDesembolso(DetalleDesembolsoSubsidioDTO detalleDesembolso) {
        this.detalleDesembolso = detalleDesembolso;
    }

}
