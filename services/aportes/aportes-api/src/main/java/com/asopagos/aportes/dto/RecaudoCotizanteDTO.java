package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EstadoValidacionRegistroAporteEnum;
import com.asopagos.enumeraciones.pila.TipoNovedadPilaEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class RecaudoCotizanteDTO implements Serializable {

    /**
     * Serial version
     */
    private static final long serialVersionUID = 2872487887321070702L;

    /**
     * Número de registro que esta relacionado con el identificador del aporte detallado
     */
    private Long numeroRegistro;

    /**
     * Tipo de identificación del cotizante
     */
    private TipoIdentificacionEnum tipoIdentificacion;

    /**
     * Número de identificación del cotizante
     */
    private String numeroIdentificacion;

    /**
     * Nombre completo del cotizante
     */
    private String nombres;

    /**
     * Tipo de solicitante de la persona que tiene relacionado el aporte
     */
    private TipoAfiliadoEnum tipoSolicitante;

    /**
     * Monto relacionado al aporte
     */
    private BigDecimal monto;

    /**
     * Intereses relacionado al aporte
     */
    private BigDecimal interes;

    /**
     * Suma del monto e interes registrados en el aporte
     */
    private BigDecimal total;

    /**
     * Razón de no conformidad del recaudo para los aportes NO OK
     */
    private String razonNoConformidad;

    /**
     * Tipo de novedad para los registros que tienen novedades aplicadas o novedades guardadas no procesadas
     */
    private TipoNovedadPilaEnum tipoNovedad;

    /**
     * Método constructor para los recaudos de cotizante con aportes OK
     * @param numeroRegistro
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param primerNombre
     * @param segundoNombre
     * @param primerApellido
     * @param segundoApellido
     * @param tipoSolicitante
     * @param monto
     * @param interes
     */
    public RecaudoCotizanteDTO(Long numeroRegistro, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
            TipoAfiliadoEnum tipoSolicitante, BigDecimal monto, BigDecimal interes) {
        this.numeroRegistro = numeroRegistro;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        StringBuilder nombreCompleto = new StringBuilder();
        nombreCompleto.append(primerNombre + " ");
        nombreCompleto.append(segundoNombre != null ? segundoNombre + " " : "");
        nombreCompleto.append(primerApellido + " ");
        nombreCompleto.append(segundoApellido != null ? segundoApellido : "");
        this.nombres = nombreCompleto.toString();
        this.tipoSolicitante = tipoSolicitante; 
        this.monto = monto;
        this.interes = interes;
        this.total = monto.add(interes != null ? interes : BigDecimal.ZERO);
    }

    /**
     * Método constructor para los recaudos de cotizante con aportes NO_OK
     * @param numeroRegistro
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param primerNombre
     * @param segundoNombre
     * @param primerApellido
     * @param segundoApellido
     * @param tipoSolicitante
     * @param monto
     * @param interes
     * @param razonNoConformidad
     */
    public RecaudoCotizanteDTO(Long numeroRegistro, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
            Short tipoSolicitante, BigDecimal monto, BigDecimal interes, EstadoValidacionRegistroAporteEnum validacion0,
            EstadoValidacionRegistroAporteEnum validacion1, EstadoValidacionRegistroAporteEnum validacion2, 
            EstadoValidacionRegistroAporteEnum validacion3) {
        this.numeroRegistro = numeroRegistro;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        StringBuilder nombreCompleto = new StringBuilder();
        nombreCompleto.append(primerNombre + " ");
        nombreCompleto.append(segundoNombre != null ? segundoNombre + " " : "");
        nombreCompleto.append(primerApellido + " ");
        nombreCompleto.append(segundoApellido != null ? segundoApellido : "");
        this.nombres = nombreCompleto.toString();
        TipoCotizanteEnum tipoCotizante = TipoCotizanteEnum.obtenerTipoCotizante(Integer.parseInt(tipoSolicitante.toString()));
        this.tipoSolicitante =tipoCotizante.getTipoAfiliado(); 
        this.monto = monto;
        this.interes = interes;
        this.total = monto.add(interes != null ? interes : BigDecimal.ZERO);
        StringBuilder razonInconformidad = new StringBuilder();
        razonInconformidad.append("V0:" + validacion0);
        razonInconformidad.append(", V1:" + validacion1);
        razonInconformidad.append(", V2:" + validacion2);
        razonInconformidad.append(", V3:" + validacion3);
        this.razonNoConformidad = razonInconformidad.toString();
    }

    /**
     * Método constructor para los recaudos de cotizante que tienen novedades procesadas aplicadas y novedades no procesadas guardadas
     * @param numeroRegistro
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param primerNombre
     * @param segundoNombre
     * @param primerApellido
     * @param segundoApellido
     * @param tipoSolicitante
     * @param tipoNovedad
     */
    public RecaudoCotizanteDTO(Long numeroRegistro, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion,
            String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
            Short tipoSolicitante, String tipoNovedad) {
        this.numeroRegistro = numeroRegistro;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        StringBuilder nombreCompleto = new StringBuilder();
        nombreCompleto.append(primerNombre + " ");
        nombreCompleto.append(segundoNombre != null ? segundoNombre + " " : "");
        nombreCompleto.append(primerApellido + " ");
        nombreCompleto.append(segundoApellido != null ? segundoApellido : "");
        this.nombres = nombreCompleto.toString();
        TipoCotizanteEnum tipoCotizante = TipoCotizanteEnum.obtenerTipoCotizante(Integer.parseInt(tipoSolicitante.toString()));
        this.tipoSolicitante =tipoCotizante.getTipoAfiliado(); 
        this.tipoNovedad = TipoNovedadPilaEnum.valueOf(tipoNovedad);
    }

    /**
     * Método constructor para los recaudos de cotizante que tienen personas pendiente por afiliar
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param primerNombre
     * @param segundoNombre
     * @param primerApellido
     * @param segundoApellido
     */
    public RecaudoCotizanteDTO(TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion, String primerNombre,
            String segundoNombre, String primerApellido, String segundoApellido) {
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        StringBuilder nombreCompleto = new StringBuilder();
        nombreCompleto.append(primerNombre + " ");
        nombreCompleto.append(segundoNombre != null ? segundoNombre + " " : "");
        nombreCompleto.append(primerApellido + " ");
        nombreCompleto.append(segundoApellido != null ? segundoApellido : "");
        this.nombres = nombreCompleto.toString();
    }

    /**
     * @return the numeroRegistro
     */
    public Long getNumeroRegistro() {
        return numeroRegistro;
    }

    /**
     * @param numeroRegistro
     *        the numeroRegistro to set
     */
    public void setNumeroRegistro(Long numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    /**
     * @return the tipoIdentificacion
     */
    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion
     *        the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion
     *        the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres
     *        the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * @return the tipoSolicitante
     */
    public TipoAfiliadoEnum getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * @param tipoSolicitante the tipoSolicitante to set
     */
    public void setTipoSolicitante(TipoAfiliadoEnum tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    /**
     * @return the monto
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * @param monto
     *        the monto to set
     */
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    /**
     * @return the intereses
     */
    public BigDecimal getInteres() {
        return interes;
    }

    /**
     * @param intereses
     *        the intereses to set
     */
    public void setInteres(BigDecimal interes) {
        this.interes = interes;
    }

    /**
     * @return the total
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @param total
     *        the total to set
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * @return the razonNoConformidad
     */
    public String getRazonNoConformidad() {
        return razonNoConformidad;
    }

    /**
     * @param razonNoConformidad
     *        the razonNoConformidad to set
     */
    public void setRazonNoConformidad(String razonNoConformidad) {
        this.razonNoConformidad = razonNoConformidad;
    }

    /**
     * @return the tipoNovedad
     */
    public TipoNovedadPilaEnum getTipoNovedad() {
        return tipoNovedad;
    }

    /**
     * @param tipoNovedad
     *        the tipoNovedad to set
     */
    public void setTipoNovedad(TipoNovedadPilaEnum tipoNovedad) {
        this.tipoNovedad = tipoNovedad;
    }
}