package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.asopagos.entidades.ccf.aportes.AporteGeneral;
import com.asopagos.entidades.ccf.aportes.MovimientoAporte;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:borozco@heinsohn.com.co"> borozco</a>
 */

public class DetalleCorreccionCotizanteNuevoVista360DTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador aporte general
     */
    private Long idAporteGeneral;
    
    /**
     * Identificador aporte detallado
     */
    private Long idAporteDetallado;
    
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
     * Estado del movimiento con respecto al aporte, (VIGENTE, CORREGIDO,
     * ANULADO)
     */
    private EstadoAporteEnum estadoAporte;
    
    /**
     * Indica si el aporte general tiene modificaciones en movimiento aporte
     */
    private Boolean tieneModificaciones;
    
    /**
     * Pago por terceros
     */
    private Boolean pagadorPorTerceros;
    
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
    
    /** Tipo afiliado del cotizante **/
    private TipoAfiliadoEnum tipoAfiliado;
    

    /**
     * 
     */
    public DetalleCorreccionCotizanteNuevoVista360DTO(AporteGeneral a, MovimientoAporte ma, Persona aportante, Persona cotizante,
            Integer tieneModificaciones, Long idAporteDetallado) {
    
        this.tipoIdentificacionCotizante = cotizante.getTipoIdentificacion();
        this.numeroIdentificacionCotizante = cotizante.getNumeroIdentificacion();
        StringBuilder nombreCotizanteBuilder = new StringBuilder();
        nombreCotizanteBuilder.append(cotizante.getPrimerNombre() + " ");
        nombreCotizanteBuilder.append(cotizante.getSegundoNombre() != null ? cotizante.getSegundoNombre() + " " : "");
        nombreCotizanteBuilder.append(cotizante.getPrimerApellido() + " ");
        nombreCotizanteBuilder.append(cotizante.getSegundoApellido() != null ? cotizante.getSegundoApellido() : "");
        this.nombreCompletoCotizante = nombreCotizanteBuilder.toString();
        this.idAporteGeneral = a.getId();
        this.tieneModificaciones = tieneModificaciones != null && tieneModificaciones.equals(1) ? Boolean.TRUE : Boolean.FALSE;
        this.estadoAporte = ma.getEstadoAporte();
        BigDecimal monto = ma.getValorAporte() != null ? ma.getValorAporte() : BigDecimal.ZERO;
        BigDecimal interes = ma.getValorInteres() != null ? ma.getValorInteres() : BigDecimal.ZERO;
        this.valorAporte = monto;
        this.valorInteres =  interes;
        this.totalAporte = monto.add(interes);
        this.pagadorPorTerceros = a.getPagadorPorTerceros();
        if (aportante.getRazonSocial()!=null){
            this.nombreCompletoAportante = aportante.getRazonSocial();    
        } else {
            StringBuilder nombreAportante = new StringBuilder();
            nombreAportante.append(aportante.getPrimerNombre() + " ");
            nombreAportante.append(aportante.getSegundoNombre() != null ? aportante.getSegundoNombre() + " " : "");
            nombreAportante.append(aportante.getPrimerApellido() + " ");
            nombreAportante.append(aportante.getSegundoApellido() != null ? aportante.getSegundoApellido() : "");
            this.nombreCompletoAportante = nombreAportante.toString();
        }
        this.tipoIdentificacionAportante = aportante.getTipoIdentificacion();
        this.numeroIdentificacionAportante = aportante.getNumeroIdentificacion();
        if (TipoSolicitanteMovimientoAporteEnum.EMPLEADOR.equals(a.getTipoSolicitante())) {
            this.tipoAfiliado = TipoAfiliadoEnum.TRABAJADOR_DEPENDIENTE;
        }
        else if (TipoSolicitanteMovimientoAporteEnum.INDEPENDIENTE.equals(a.getTipoSolicitante())) {
            this.tipoAfiliado = TipoAfiliadoEnum.TRABAJADOR_INDEPENDIENTE;
        }
        else if (TipoSolicitanteMovimientoAporteEnum.PENSIONADO.equals(a.getTipoSolicitante())) {
            this.tipoAfiliado = TipoAfiliadoEnum.PENSIONADO;
        }
        
        this.idAporteDetallado = idAporteDetallado;
    }


    public DetalleCorreccionCotizanteNuevoVista360DTO(Long idAporteGeneral, Long idAporteDetallado,
    String tipoIdentificacionCotizante,String numeroIdentificacionCotizante,
    String nombreCompletoCotizante, 
    String estadoAporte, Boolean pagadorPorTerceros,
    BigDecimal valorAporte,BigDecimal valorInteres,BigDecimal totalAporte, 
    String nombreCompletoAportante, String tipoIdentificacionAportante, 
    String numeroIdentificacionAportante,String tipoAfiliado,Boolean tieneModificaciones) {
    
    this.idAporteGeneral = idAporteGeneral;
    this.idAporteDetallado = idAporteDetallado;
    this.tipoIdentificacionCotizante = TipoIdentificacionEnum.valueOf(tipoIdentificacionCotizante);
    this.numeroIdentificacionCotizante = numeroIdentificacionCotizante;
    this.nombreCompletoCotizante = nombreCompletoCotizante;
    this.estadoAporte = EstadoAporteEnum.valueOf(estadoAporte);
    this.pagadorPorTerceros = pagadorPorTerceros;
    this.valorAporte = valorAporte;
    this.valorInteres = valorInteres;
    this.totalAporte = totalAporte;
    this.nombreCompletoAportante = nombreCompletoAportante;
    this.tipoIdentificacionAportante = tipoIdentificacionAportante != null ?TipoIdentificacionEnum.valueOf(tipoIdentificacionAportante):null;
    this.numeroIdentificacionAportante = numeroIdentificacionAportante;
    this.tipoAfiliado = TipoAfiliadoEnum.valueOf(tipoAfiliado);
    this.tieneModificaciones = tieneModificaciones;
}

    /**
     * Método que retorna el valor de idAporteGeneral.
     * @return valor de idAporteGeneral.
     */
    public Long getIdAporteGeneral() {
        return idAporteGeneral;
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
     * Método que retorna el valor de nombreCompletoCotizante.
     * @return valor de nombreCompletoCotizante.
     */
    public String getNombreCompletoCotizante() {
        return nombreCompletoCotizante;
    }

    /**
     * Método que retorna el valor de estadoAporte.
     * @return valor de estadoAporte.
     */
    public EstadoAporteEnum getEstadoAporte() {
        return estadoAporte;
    }

    /**
     * Método que retorna el valor de tieneModificaciones.
     * @return valor de tieneModificaciones.
     */
    public Boolean getTieneModificaciones() {
        return tieneModificaciones;
    }

    /**
     * Método que retorna el valor de pagadorPorTerceros.
     * @return valor de pagadorPorTerceros.
     */
    public Boolean getPagadorPorTerceros() {
        return pagadorPorTerceros;
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
     * Método que retorna el valor de totalAporte.
     * @return valor de totalAporte.
     */
    public BigDecimal getTotalAporte() {
        return totalAporte;
    }

    /**
     * Método que retorna el valor de nombreCompletoAportante.
     * @return valor de nombreCompletoAportante.
     */
    public String getNombreCompletoAportante() {
        return nombreCompletoAportante;
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
     * Método encargado de modificar el valor de idAporteGeneral.
     * @param valor para modificar idAporteGeneral.
     */
    public void setIdAporteGeneral(Long idAporteGeneral) {
        this.idAporteGeneral = idAporteGeneral;
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
     * Método encargado de modificar el valor de nombreCompletoCotizante.
     * @param valor para modificar nombreCompletoCotizante.
     */
    public void setNombreCompletoCotizante(String nombreCompletoCotizante) {
        this.nombreCompletoCotizante = nombreCompletoCotizante;
    }

    /**
     * Método encargado de modificar el valor de estadoAporte.
     * @param valor para modificar estadoAporte.
     */
    public void setEstadoAporte(EstadoAporteEnum estadoAporte) {
        this.estadoAporte = estadoAporte;
    }

    /**
     * Método encargado de modificar el valor de tieneModificaciones.
     * @param valor para modificar tieneModificaciones.
     */
    public void setTieneModificaciones(Boolean tieneModificaciones) {
        this.tieneModificaciones = tieneModificaciones;
    }

    /**
     * Método encargado de modificar el valor de pagadorPorTerceros.
     * @param valor para modificar pagadorPorTerceros.
     */
    public void setPagadorPorTerceros(Boolean pagadorPorTerceros) {
        this.pagadorPorTerceros = pagadorPorTerceros;
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
     * Método encargado de modificar el valor de totalAporte.
     * @param valor para modificar totalAporte.
     */
    public void setTotalAporte(BigDecimal totalAporte) {
        this.totalAporte = totalAporte;
    }

    /**
     * Método encargado de modificar el valor de nombreCompletoAportante.
     * @param valor para modificar nombreCompletoAportante.
     */
    public void setNombreCompletoAportante(String nombreCompletoAportante) {
        this.nombreCompletoAportante = nombreCompletoAportante;
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
     * Método que retorna el valor de tipoAfiliado.
     * @return valor de tipoAfiliado.
     */
    public TipoAfiliadoEnum getTipoAfiliado() {
        return tipoAfiliado;
    }

    /**
     * Método encargado de modificar el valor de tipoAfiliado.
     * @param valor para modificar tipoAfiliado.
     */
    public void setTipoAfiliado(TipoAfiliadoEnum tipoAfiliado) {
        this.tipoAfiliado = tipoAfiliado;
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
    
}
