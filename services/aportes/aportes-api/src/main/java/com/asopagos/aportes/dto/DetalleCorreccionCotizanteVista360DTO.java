package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.entidades.ccf.aportes.AporteDetallado;
import com.asopagos.entidades.ccf.aportes.AporteGeneral;
import com.asopagos.entidades.ccf.aportes.MovimientoAporte;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:borozco@heinsohn.com.co"> borozco</a>
 */

public class DetalleCorreccionCotizanteVista360DTO implements Serializable{
    
    /**
     * Tipo Identificación del cotizante
     */
    private TipoIdentificacionEnum tipoIdentificacionCotizante;
    /**
     * Numero Identificación del cotizante
     */
    private String numeroIdentificacionCotizante;
    /**
     * Nombre del cotizante
     */
    private String nombreCompletoCotizante;
    /**
     * Indica si el aporte general tiene modificaciones en movimiento aporte
     */
    private Boolean tieneModificaciones;
    
    /**
     * Estado del movimiento con respecto al aporte, (VIGENTE, CORREGIDO,
     * ANULADO)
     */
    private EstadoAporteEnum estadoAporte;

    /**
     * Movimiento del aporte.
     */
    private BigDecimal valorAporte;

    /**
     * Movimiento del inter�s.
     */
    private BigDecimal valorInteres;
    
    /**
     * Movimiento del inter�s.
     */
    private BigDecimal totalAporte;
    /**
     * Identificador aporte detallado
     */
    private Long idAporteDetallado;
    /**
     * Pago por terceros
     */
    private Boolean pagadorPorTerceros;
    /**
     * Nombre del aportante
     */
    private String nombreCompletoAportante;
    
    /**
     * Tipo Identificación del aportante
     */
    private TipoIdentificacionEnum tipoIdentificacionAportante;
    /**
     * Numero Identificación del aportante
     */
    private String numeroIdentificacionAportante;
    
    /**
     * Estado del afiliado.
     */
    private EstadoAfiliadoEnum estadoAfiliado;
    
    /**
     * Fecha de ingreso al sistema.
     */
    private Long fechaIngreso;
    
    /**
     * Fecha de retiro, si la tiene.
     */
    private Long fechaRetiro;
    /**
     * Tipo de afiliado.
     */
    private TipoAfiliadoEnum tipoAfiliado;
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la clase
     */
    public DetalleCorreccionCotizanteVista360DTO(Persona p, MovimientoAporte ma, AporteGeneral ag, AporteDetallado ad, 
            String nombreCompletoAportante) {
        this.tipoIdentificacionCotizante= p.getTipoIdentificacion() != null ? p.getTipoIdentificacion() : null;
        this.numeroIdentificacionCotizante = p.getNumeroIdentificacion() != null ? p.getNumeroIdentificacion() : null;
        StringBuilder nombreCotizanteBuilder = new StringBuilder();
        nombreCotizanteBuilder.append(p.getPrimerNombre() + " ");
        nombreCotizanteBuilder
                .append(p.getSegundoNombre() != null ? p.getSegundoNombre() + " " : "");
        nombreCotizanteBuilder.append(p.getPrimerApellido() + " ");
        nombreCotizanteBuilder
                .append(p.getSegundoApellido() != null ? p.getSegundoApellido() : "");
        this.nombreCompletoCotizante = nombreCotizanteBuilder.toString();
        this.idAporteDetallado = ad.getId();
        this.tieneModificaciones = true;
        this.estadoAporte = ma.getEstadoAporte() != null ? ma.getEstadoAporte() : null;
        BigDecimal monto = ma.getValorAporte() != null ? ma.getValorAporte() : BigDecimal.ZERO;
        BigDecimal interes = ma.getValorInteres() != null ? ma.getValorInteres() : BigDecimal.ZERO;
        this.valorAporte = monto;
        this.valorInteres = interes;
        this.totalAporte = monto.add(interes);
        this.pagadorPorTerceros = ag.getPagadorPorTerceros();
        this.nombreCompletoAportante = nombreCompletoAportante;
    }

    /**
     * Método que retorna el valor de tipoIdentificacionCotizante.
     * @return valor de tipoIdentificacionCotizante.
     */
    public TipoIdentificacionEnum getTipoIdentificacionCotizante() {
        return tipoIdentificacionCotizante;
    }

    /**
     * Método que retorna el valor de numeroIdentificacionCotizante.
     * @return valor de numeroIdentificacionCotizante.
     */
    public String getNumeroIdentificacionCotizante() {
        return numeroIdentificacionCotizante;
    }

    /**
     * Método que retorna el valor de nombreCompleto.
     * @return valor de nombreCompleto.
     */
    public String getNombreCompletoCotizante() {
        return nombreCompletoCotizante;
    }

    /**
     * Método que retorna el valor de tieneModificaciones.
     * @return valor de tieneModificaciones.
     */
    public Boolean getTieneModificaciones() {
        return tieneModificaciones;
    }

    /**
     * Método que retorna el valor de estadoAporte.
     * @return valor de estadoAporte.
     */
    public EstadoAporteEnum getEstadoAporte() {
        return estadoAporte;
    }

    /**
     * Método que retorna el valor de valorAporte.
     * @return valor de valorAporte.
     */
    public BigDecimal getValorAporte() {
        return valorAporte;
    }

    /**
     * Método que retorna el valor de valorInteres.
     * @return valor de valorInteres.
     */
    public BigDecimal getValorInteres() {
        return valorInteres;
    }

    /**
     * Método que retorna el valor de idAporteDetallado.
     * @return valor de idAporteDetallado.
     */
    public Long getIdAporteDetallado() {
        return idAporteDetallado;
    }

    /**
     * Método que retorna el valor de pagadorPorTerceros.
     * @return valor de pagadorPorTerceros.
     */
    public Boolean getPagadorPorTerceros() {
        return pagadorPorTerceros;
    }

    /**
     * Método encargado de modificar el valor de tipoIdentificacionCotizante.
     * @param valor para modificar tipoIdentificacionCotizante.
     */
    public void setTipoIdentificacionCotizante(TipoIdentificacionEnum tipoIdentificacionCotizante) {
        this.tipoIdentificacionCotizante = tipoIdentificacionCotizante;
    }

    /**
     * Método encargado de modificar el valor de numeroIdentificacionCotizante.
     * @param valor para modificar numeroIdentificacionCotizante.
     */
    public void setNumeroIdentificacionCotizante(String numeroIdentificacionCotizante) {
        this.numeroIdentificacionCotizante = numeroIdentificacionCotizante;
    }

    /**
     * Método encargado de modificar el valor de nombreCompleto.
     * @param valor para modificar nombreCompleto.
     */
    public void setNombreCompletoCotizante(String nombreCompleto) {
        this.nombreCompletoCotizante = nombreCompleto;
    }

    /**
     * Método encargado de modificar el valor de tieneModificaciones.
     * @param valor para modificar tieneModificaciones.
     */
    public void setTieneModificaciones(Boolean tieneModificaciones) {
        this.tieneModificaciones = tieneModificaciones;
    }

    /**
     * Método encargado de modificar el valor de estadoAporte.
     * @param valor para modificar estadoAporte.
     */
    public void setEstadoAporte(EstadoAporteEnum estadoAporte) {
        this.estadoAporte = estadoAporte;
    }

    /**
     * Método encargado de modificar el valor de valorAporte.
     * @param valor para modificar valorAporte.
     */
    public void setValorAporte(BigDecimal valorAporte) {
        this.valorAporte = valorAporte;
    }

    /**
     * Método encargado de modificar el valor de valorInteres.
     * @param valor para modificar valorInteres.
     */
    public void setValorInteres(BigDecimal valorInteres) {
        this.valorInteres = valorInteres;
    }

    /**
     * Método encargado de modificar el valor de idAporteDetallado.
     * @param valor para modificar idAporteDetallado.
     */
    public void setIdAporteDetallado(Long idAporteDetallado) {
        this.idAporteDetallado = idAporteDetallado;
    }

    /**
     * Método encargado de modificar el valor de pagadorPorTerceros.
     * @param valor para modificar pagadorPorTerceros.
     */
    public void setPagadorPorTerceros(Boolean pagadorPorTerceros) {
        this.pagadorPorTerceros = pagadorPorTerceros;
    }

    /**
     * Método que retorna el valor de totalAporte.
     * @return valor de totalAporte.
     */
    public BigDecimal getTotalAporte() {
        return totalAporte;
    }

    /**
     * Método encargado de modificar el valor de totalAporte.
     * @param valor para modificar totalAporte.
     */
    public void setTotalAporte(BigDecimal totalAporte) {
        this.totalAporte = totalAporte;
    }

    /**
     * Método que retorna el valor de nombreCompletoAportante.
     * @return valor de nombreCompletoAportante.
     */
    public String getNombreCompletoAportante() {
        return nombreCompletoAportante;
    }

    /**
     * Método encargado de modificar el valor de nombreCompletoAportante.
     * @param valor para modificar nombreCompletoAportante.
     */
    public void setNombreCompletoAportante(String nombreCompletoAportante) {
        this.nombreCompletoAportante = nombreCompletoAportante;
    }

    /**
     * Método que retorna el valor de tipoIdentificacionAportante.
     * @return valor de tipoIdentificacionAportante.
     */
    public TipoIdentificacionEnum getTipoIdentificacionAportante() {
        return tipoIdentificacionAportante;
    }

    /**
     * Método que retorna el valor de numeroIdentificacionAportante.
     * @return valor de numeroIdentificacionAportante.
     */
    public String getNumeroIdentificacionAportante() {
        return numeroIdentificacionAportante;
    }

    /**
     * Método encargado de modificar el valor de tipoIdentificacionAportante.
     * @param valor para modificar tipoIdentificacionAportante.
     */
    public void setTipoIdentificacionAportante(TipoIdentificacionEnum tipoIdentificacionAportante) {
        this.tipoIdentificacionAportante = tipoIdentificacionAportante;
    }

    /**
     * Método encargado de modificar el valor de numeroIdentificacionAportante.
     * @param valor para modificar numeroIdentificacionAportante.
     */
    public void setNumeroIdentificacionAportante(String numeroIdentificacionAportante) {
        this.numeroIdentificacionAportante = numeroIdentificacionAportante;
    }

    /**
     * Método que retorna el valor de estadoAfiliado.
     * @return valor de estadoAfiliado.
     */
    public EstadoAfiliadoEnum getEstadoAfiliado() {
        return estadoAfiliado;
    }

    /**
     * Método que retorna el valor de fechaIngreso.
     * @return valor de fechaIngreso.
     */
    public Long getFechaIngreso() {
        return fechaIngreso;
    }

    /**
     * Método que retorna el valor de fechaRetiro.
     * @return valor de fechaRetiro.
     */
    public Long getFechaRetiro() {
        return fechaRetiro;
    }

    /**
     * Método que retorna el valor de tipoAfiliado.
     * @return valor de tipoAfiliado.
     */
    public TipoAfiliadoEnum getTipoAfiliado() {
        return tipoAfiliado;
    }

    /**
     * Método encargado de modificar el valor de estadoAfiliado.
     * @param valor para modificar estadoAfiliado.
     */
    public void setEstadoAfiliado(EstadoAfiliadoEnum estadoAfiliado) {
        this.estadoAfiliado = estadoAfiliado;
    }

    /**
     * Método encargado de modificar el valor de fechaIngreso.
     * @param valor para modificar fechaIngreso.
     */
    public void setFechaIngreso(Long fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    /**
     * Método encargado de modificar el valor de fechaRetiro.
     * @param valor para modificar fechaRetiro.
     */
    public void setFechaRetiro(Long fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    /**
     * Método encargado de modificar el valor de tipoAfiliado.
     * @param valor para modificar tipoAfiliado.
     */
    public void setTipoAfiliado(TipoAfiliadoEnum tipoAfiliado) {
        this.tipoAfiliado = tipoAfiliado;
    }
    

}
