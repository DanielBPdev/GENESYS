package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.enumeraciones.TipoPlanillaEnum;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */

public class DetalleDevolucionCotizanteDTO implements Serializable{
    private static final long serialVersionUID = -6118944974932723465L;

    /**
     * Identificador del aporte detallado relacionado con la cuenta de aportes
     */
    private Long idAporteDetallado;
    
    /**
     * Monto de la devolucion relacionada al aporte 
     */
    private BigDecimal montoDevolucion;
    
    /**
     * Intereses de la devolucion relacionada al aporte
     */
    private BigDecimal interesesDevolucion;
    
    /**
     * Total de la devolucion relacionada al aporte
     */
    private BigDecimal totalDevolucion;
    
    /**
     * Tipo de cotizante
     */
    private TipoAfiliadoEnum tipoAfiliado;
    
    /**
     * Tipo de identificacion del cotizante
     */
    private TipoIdentificacionEnum tipoIdentificacionCotizante;
    
    /**
     * Número de Identificación del cotizante
     */
    private String numeroIdentificacionCotizante;
    
    /**
     * Nombre completo del cotizante
     */
    private String nombre;
    
    /**
     * Identificador del movimiento del aporte
     */
    private Long idMovimientoAporte;
    
    /**
     * Tipo de planilla PILA (sí aplica)
     * */
    private TipoPlanillaEnum tipoPlanilla;
    
    /**
     * Estado planilla PILA (sí aplica)
     * */
    private EstadoProcesoArchivoEnum estadoArchivo;
    
    /**
     * ID del registro detallado del aporte
     * */
    private Long idRegDet;
    
    /**
     * Modalidad del recaudo de aporte
     * */
    private ModalidadRecaudoAporteEnum modalidadRecaudo;
    
    /**
     * Método constructor
     */
    public DetalleDevolucionCotizanteDTO() {

    }

    /**
     * @param tipoAfiliado
     * @param tipoIdentificacionCotizante
     * @param numeroIdentificacionCotizante
     * @param nombre
     */
    public DetalleDevolucionCotizanteDTO(TipoAfiliadoEnum tipoAfiliado, TipoIdentificacionEnum tipoIdentificacionCotizante,
            String numeroIdentificacionCotizante, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, 
            Long idRegDet, ModalidadRecaudoAporteEnum modalidadRecaudo, Long idAporteDetallado) {
        this.tipoAfiliado = tipoAfiliado;
        this.tipoIdentificacionCotizante = tipoIdentificacionCotizante;
        this.numeroIdentificacionCotizante = numeroIdentificacionCotizante;
        StringBuilder nombreCompleto = new StringBuilder();
        nombreCompleto.append(primerNombre + " ");
        nombreCompleto.append(segundoNombre != null ? segundoNombre + " " : "");
        nombreCompleto.append(primerApellido + " ");
        nombreCompleto.append(segundoApellido != null ? segundoApellido : "");
        this.nombre = nombreCompleto.toString();
        
        this.idRegDet = idRegDet;
        this.modalidadRecaudo = modalidadRecaudo;
        this.idAporteDetallado = idAporteDetallado;
    }

    /**
     * @return the idAporteDetallado
     */
    public Long getIdAporteDetallado() {
        return idAporteDetallado;
    }

    /**
     * @param idAporteDetallado the idAporteDetallado to set
     */
    public void setIdAporteDetallado(Long idAporteDetallado) {
        this.idAporteDetallado = idAporteDetallado;
    }

    /**
     * @return the montoDevolucion
     */
    public BigDecimal getMontoDevolucion() {
        return montoDevolucion;
    }

    /**
     * @param montoDevolucion the montoDevolucion to set
     */
    public void setMontoDevolucion(BigDecimal montoDevolucion) {
        this.montoDevolucion = montoDevolucion;
    }

    /**
     * @return the interesesDevolucion
     */
    public BigDecimal getInteresesDevolucion() {
        return interesesDevolucion;
    }

    /**
     * @param interesesDevolucion the interesesDevolucion to set
     */
    public void setInteresesDevolucion(BigDecimal interesesDevolucion) {
        this.interesesDevolucion = interesesDevolucion;
    }

    /**
     * @return the totalDevolucion
     */
    public BigDecimal getTotalDevolucion() {
        return totalDevolucion;
    }

    /**
     * @param totalDevolucion the totalDevolucion to set
     */
    public void setTotalDevolucion(BigDecimal totalDevolucion) {
        this.totalDevolucion = totalDevolucion;
    }

    /**
     * @return the tipoAfiliado
     */
    public TipoAfiliadoEnum getTipoAfiliado() {
        return tipoAfiliado;
    }

    /**
     * @param tipoAfiliado the tipoAfiliado to set
     */
    public void setTipoAfiliado(TipoAfiliadoEnum tipoAfiliado) {
        this.tipoAfiliado = tipoAfiliado;
    }

    /**
     * @return the tipoIdentificacionCotizante
     */
    public TipoIdentificacionEnum getTipoIdentificacionCotizante() {
        return tipoIdentificacionCotizante;
    }

    /**
     * @param tipoIdentificacionCotizante the tipoIdentificacionCotizante to set
     */
    public void setTipoIdentificacionCotizante(TipoIdentificacionEnum tipoIdentificacionCotizante) {
        this.tipoIdentificacionCotizante = tipoIdentificacionCotizante;
    }

    /**
     * @return the numeroIdentificacionCotizante
     */
    public String getNumeroIdentificacionCotizante() {
        return numeroIdentificacionCotizante;
    }

    /**
     * @param numeroIdentificacionCotizante the numeroIdentificacionCotizante to set
     */
    public void setNumeroIdentificacionCotizante(String numeroIdentificacionCotizante) {
        this.numeroIdentificacionCotizante = numeroIdentificacionCotizante;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the idMovimientoAporte
     */
    public Long getIdMovimientoAporte() {
        return idMovimientoAporte;
    }

    /**
     * @param idMovimientoAporte the idMovimientoAporte to set
     */
    public void setIdMovimientoAporte(Long idMovimientoAporte) {
        this.idMovimientoAporte = idMovimientoAporte;
    }

    /**
     * @return the tipoPlanilla
     */
    public TipoPlanillaEnum getTipoPlanilla() {
        return tipoPlanilla;
    }

    /**
     * @param tipoPlanilla the tipoPlanilla to set
     */
    public void setTipoPlanilla(TipoPlanillaEnum tipoPlanilla) {
        this.tipoPlanilla = tipoPlanilla;
    }

    /**
     * @return the estadoArchivo
     */
    public EstadoProcesoArchivoEnum getEstadoArchivo() {
        return estadoArchivo;
    }

    /**
     * @param estadoArchivo the estadoArchivo to set
     */
    public void setEstadoArchivo(EstadoProcesoArchivoEnum estadoArchivo) {
        this.estadoArchivo = estadoArchivo;
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

    /**
     * @return the modalidadRecaudo
     */
    public ModalidadRecaudoAporteEnum getModalidadRecaudo() {
        return modalidadRecaudo;
    }

    /**
     * @param modalidadRecaudo the modalidadRecaudo to set
     */
    public void setModalidadRecaudo(ModalidadRecaudoAporteEnum modalidadRecaudo) {
        this.modalidadRecaudo = modalidadRecaudo;
    }
    
}
