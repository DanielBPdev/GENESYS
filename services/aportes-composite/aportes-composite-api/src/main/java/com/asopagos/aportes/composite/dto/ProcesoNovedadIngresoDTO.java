package com.asopagos.aportes.composite.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> DTO que contiene la información requerida para el procesamiento de una novedad de
 * ingreso desde aportes<br/>
 * <b>Módulo:</b> Asopagos - HU-211, 212, 213, 214 <br/>
 *
 * @author <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ProcesoNovedadIngresoDTO implements Serializable {
    private static final long serialVersionUID = -7846271837064283909L;
    
    private TipoAfiliadoEnum tipoCotizante;
    private TipoIdentificacionEnum tipoIdAportante;
    private String numeroIdAportante;
    private TipoIdentificacionEnum tipoIdCotizante;
    private String numeroIdCotizante;
    private Boolean esReintegrable;
    private Long idRegistroDetallado;
    private Long idRegistroGeneral;
    private Boolean esEmpleadorReintegrable;
    private CanalRecepcionEnum canalRecepcion;
    private Long fechaIngreso;
    private Long tenNovedadId;
    /**
     * Constructor a base de campos
     * @param tipoCotizante 
     * @param tipoIdAportante 
     * @param numeroIdAportante 
     * @param tipoIdCotizante 
     * @param numeroIdCotizante 
     * @param esReintegrable 
     * @param idRegistroDetallado 
     * @param idRegistroGeneral 
     * @param esEmpleadorReintegrable 
     * @param canalRecepcion 
     * @param fechaIngreso 
     * @param tenIdNovedad 
     * */
    public ProcesoNovedadIngresoDTO(TipoAfiliadoEnum tipoCotizante, TipoIdentificacionEnum tipoIdAportante, String numeroIdAportante,
            TipoIdentificacionEnum tipoIdCotizante, String numeroIdCotizante, Boolean esReintegrable, Long idRegistroDetallado,
            Long idRegistroGeneral, Boolean esEmpleadorReintegrable, CanalRecepcionEnum canalRecepcion, Long fechaIngreso) {
        super();
        this.tipoCotizante = tipoCotizante;
        this.tipoIdAportante = tipoIdAportante;
        this.numeroIdAportante = numeroIdAportante;
        this.tipoIdCotizante = tipoIdCotizante;
        this.numeroIdCotizante = numeroIdCotizante;
        this.esReintegrable = esReintegrable;
        this.idRegistroDetallado = idRegistroDetallado;
        this.idRegistroGeneral = idRegistroGeneral;
        this.esEmpleadorReintegrable = esEmpleadorReintegrable;
        this.canalRecepcion = canalRecepcion;
        this.fechaIngreso = fechaIngreso;        
    }
    
    /**
     * Constructor a base de campos
     * @param tipoCotizante 
     * @param tipoIdAportante 
     * @param numeroIdAportante 
     * @param tipoIdCotizante 
     * @param numeroIdCotizante 
     * @param esReintegrable 
     * @param idRegistroDetallado 
     * @param idRegistroGeneral 
     * @param esEmpleadorReintegrable 
     * @param canalRecepcion 
     * @param fechaIngreso 
     * @param tenIdNovedad 
     * */
    public ProcesoNovedadIngresoDTO(TipoAfiliadoEnum tipoCotizante, TipoIdentificacionEnum tipoIdAportante, String numeroIdAportante,
            TipoIdentificacionEnum tipoIdCotizante, String numeroIdCotizante, Boolean esReintegrable, Long idRegistroDetallado,
            Long idRegistroGeneral, Boolean esEmpleadorReintegrable, CanalRecepcionEnum canalRecepcion, Long fechaIngreso, Long tenIdNovedad) {
        super();
        this.tipoCotizante = tipoCotizante;
        this.tipoIdAportante = tipoIdAportante;
        this.numeroIdAportante = numeroIdAportante;
        this.tipoIdCotizante = tipoIdCotizante;
        this.numeroIdCotizante = numeroIdCotizante;
        this.esReintegrable = esReintegrable;
        this.idRegistroDetallado = idRegistroDetallado;
        this.idRegistroGeneral = idRegistroGeneral;
        this.esEmpleadorReintegrable = esEmpleadorReintegrable;
        this.canalRecepcion = canalRecepcion;
        this.fechaIngreso = fechaIngreso;
        this.tenNovedadId = tenIdNovedad;
    }
    
    /**
     * Constructor vacío
     * */
    public ProcesoNovedadIngresoDTO() {
    }

    /**
     * @return the tipoCotizante
     */
    public TipoAfiliadoEnum getTipoCotizante() {
        return tipoCotizante;
    }

    /**
     * @param tipoCotizante
     *        the tipoCotizante to set
     */
    public void setTipoCotizante(TipoAfiliadoEnum tipoCotizante) {
        this.tipoCotizante = tipoCotizante;
    }

    /**
     * @return the tipoIdAportante
     */
    public TipoIdentificacionEnum getTipoIdAportante() {
        return tipoIdAportante;
    }

    /**
     * @param tipoIdAportante
     *        the tipoIdAportante to set
     */
    public void setTipoIdAportante(TipoIdentificacionEnum tipoIdAportante) {
        this.tipoIdAportante = tipoIdAportante;
    }

    /**
     * @return the numeroIdAportante
     */
    public String getNumeroIdAportante() {
        return numeroIdAportante;
    }

    /**
     * @param numeroIdAportante
     *        the numeroIdAportante to set
     */
    public void setNumeroIdAportante(String numeroIdAportante) {
        this.numeroIdAportante = numeroIdAportante;
    }

    /**
     * @return the tipoIdCotizante
     */
    public TipoIdentificacionEnum getTipoIdCotizante() {
        return tipoIdCotizante;
    }

    /**
     * @param tipoIdCotizante
     *        the tipoIdCotizante to set
     */
    public void setTipoIdCotizante(TipoIdentificacionEnum tipoIdCotizante) {
        this.tipoIdCotizante = tipoIdCotizante;
    }

    /**
     * @return the numeroIdCotizante
     */
    public String getNumeroIdCotizante() {
        return numeroIdCotizante;
    }

    /**
     * @param numeroIdCotizante
     *        the numeroIdCotizante to set
     */
    public void setNumeroIdCotizante(String numeroIdCotizante) {
        this.numeroIdCotizante = numeroIdCotizante;
    }

    /**
     * @return the esReintegrable
     */
    public Boolean getEsReintegrable() {
        return esReintegrable;
    }

    /**
     * @param esReintegrable
     *        the esReintegrable to set
     */
    public void setEsReintegrable(Boolean esReintegrable) {
        this.esReintegrable = esReintegrable;
    }

    /**
     * @return the idRegistroDetallado
     */
    public Long getIdRegistroDetallado() {
        return idRegistroDetallado;
    }

    /**
     * @param idRegistroDetallado
     *        the idRegistroDetallado to set
     */
    public void setIdRegistroDetallado(Long idRegistroDetallado) {
        this.idRegistroDetallado = idRegistroDetallado;
    }

    /**
     * @return the idRegistroGeneral
     */
    public Long getIdRegistroGeneral() {
        return idRegistroGeneral;
    }

    /**
     * @param idRegistroGeneral
     *        the idRegistroGeneral to set
     */
    public void setIdRegistroGeneral(Long idRegistroGeneral) {
        this.idRegistroGeneral = idRegistroGeneral;
    }

    /**
     * @return the esEmpleadorReintegrable
     */
    public Boolean getEsEmpleadorReintegrable() {
        return esEmpleadorReintegrable;
    }

    /**
     * @param esEmpleadorReintegrable
     *        the esEmpleadorReintegrable to set
     */
    public void setEsEmpleadorReintegrable(Boolean esEmpleadorReintegrable) {
        this.esEmpleadorReintegrable = esEmpleadorReintegrable;
    }

    /**
     * @return the canalRecepcion
     */
    public CanalRecepcionEnum getCanalRecepcion() {
        return canalRecepcion;
    }

    /**
     * @param canalRecepcion
     *        the canalRecepcion to set
     */
    public void setCanalRecepcion(CanalRecepcionEnum canalRecepcion) {
        this.canalRecepcion = canalRecepcion;
    }

    /**
     * @return the fechaIngreso
     */
    public Long getFechaIngreso() {
        return fechaIngreso;
    }

    /**
     * @param fechaIngreso
     *        the fechaIngreso to set
     */
    public void setFechaIngreso(Long fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    /**
     * @return tenNovedadId
     */
    public Long getTenNovedadId() {
        return tenNovedadId;
    }

    /**
     * @param tenNovedadId
     */
    public void setTenNovedadId(Long tenNovedadId) {
        this.tenNovedadId = tenNovedadId;
    }
    
    
}
