package com.asopagos.pila.dto;

import java.util.Date;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;

/**
 * <b>Descripcion:</b> Clase que contiene los datos que seran mostrados en el
 * detalle de la pestaña de novedades de la HU 401<br/>
 * <b>Módulo:</b> Asopagos - HU 401<br/>
 *
 * @author <a href="mailto:anbuitrago@heinsohn.com.co"> anbuitrago</a>
 */

public class DetallePestanaNovedadesDTO {

    //tipo cotizante
    private TipoAfiliadoEnum tipoCotizante;
    //numero cotizante
    private String numIdentificacionCotizante;
    //fecha radicacion solicitud
    private Date fechaRadicacion;
    //resultado
    private TipoTransaccionEnum resultado;
    //usuario
    private String usuario;
    //registro detallado
    private Long idRegDet;

    /** Constructor por defecto para JSON converter */
    public DetallePestanaNovedadesDTO() {
    }

    /**
     * @param fechaRadicacion
     * @param resultado
     * @param usuario
     */
    public DetallePestanaNovedadesDTO(Date fechaRadicacion, String resultado, String usuario, Long idRegDet) {
        super();
        this.fechaRadicacion = fechaRadicacion;
        this.resultado = TipoTransaccionEnum.valueOf(resultado);
        this.usuario = usuario;
        this.idRegDet = idRegDet;
    }

    /**
     * @param numIdentificacionCotizante
     * @param fechaRadicacion
     * @param resultado
     * @param usuario
     */
    public DetallePestanaNovedadesDTO(String numIdentificacionCotizante, Short tipoCotizante, Date fechaRadicacion,
            TipoTransaccionEnum resultado, String usuario, Long idRegDet) {
        this.numIdentificacionCotizante = numIdentificacionCotizante;
        this.tipoCotizante = tipoCotizante != null ? TipoCotizanteEnum.obtenerTipoCotizante(tipoCotizante.intValue()).getTipoAfiliado()
                : null;
        this.fechaRadicacion = fechaRadicacion;
        this.resultado = resultado;
        this.usuario = usuario;
        this.idRegDet = idRegDet;
    }

    /**
     * @return the resultado
     */
    public TipoTransaccionEnum getResultado() {
        return resultado;
    }

    /**
     * @param resultado
     *        the resultado to set
     */
    public void setResultado(TipoTransaccionEnum resultado) {
        this.resultado = resultado;
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
     * @return the numIdentificacionCotizante
     */
    public String getNumIdentificacionCotizante() {
        return numIdentificacionCotizante;
    }

    /**
     * @param numIdentificacionCotizante
     *        the numIdentificacionCotizante to set
     */
    public void setNumIdentificacionCotizante(String numIdentificacionCotizante) {
        this.numIdentificacionCotizante = numIdentificacionCotizante;
    }

    /**
     * @return the fechaRadicacion
     */
    public Date getFechaRadicacion() {
        return fechaRadicacion;
    }

    /**
     * @param fechaRadicacion
     *        the fechaRadicacion to set
     */
    public void setFechaRadicacion(Date fechaRadicacion) {
        this.fechaRadicacion = fechaRadicacion;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario
     *        the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the idRegDet
     */
    public Long getIdRegDet() {
        return idRegDet;
    }

    /**
     * @param idRegDet the idRegDet to set
     */
    public void setIdRegDet(Long idRegDet) {
        this.idRegDet = idRegDet;
    }

}
