/**
 *
 */
package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.dto.modelo.SolicitudAporteModeloDTO;
import com.asopagos.dto.modelo.SolicitudCorreccionAporteModeloDTO;
import com.asopagos.dto.modelo.SolicitudDevolucionAporteModeloDTO;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.TipoPlanillaEnum;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoAjusteMovimientoAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoMovimientoRecaudoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EstadoProcesoArchivoEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;

/**
 * Clase que se encarga encarga de tener la informacion de cuenta aporte DTO
 * 
 * @author Julian Andres Sanchez <jusanchez@heinsohn.com.co>
 *
 */
@XmlRootElement
public class CuentaAporteDTO implements Serializable {

    private static final long serialVersionUID = -2289942313714691540L;

    /**
     * Número de Operación
     */
    private String numeroOperacion;

    /**
     * Tipo de movimiento recaudado
     */
    private TipoMovimientoRecaudoAporteEnum tipoMovimientoRecaudo;

    /**
     * Tipo de identificacion del aportante
     */
    private TipoIdentificacionEnum tipoIdentificacionAportante;

    /**
     * Número de Identificación del aportante
     */
    private String numeroIdentificacionAportante;
    
    /**
     * Tipo de identificacion del cotizante
     */
    private TipoIdentificacionEnum tipoIdentificacionCotizante;

    /**
     * Número de Identificación del cotizante
     */
    private String numeroIdentificacionCotizante;
    
    /**
     * Tipo de identificacion del aportante final del proceso de correcciones
     */
    private TipoIdentificacionEnum tipoIdentificacionAportanteCorreccion;

    /**
     * Número de Identificación del aportante final del proceso de correcciones
     */
    private String numeroIdentificacionAportanteCorreccion;

    /**
     * Periodo de pago
     */
    private Long periodoPago;

    /**
     * Fecha de registro
     */
    private Long fechaRegistro;

    /**
     * Fecha de pago del recaudo de aporte
     */
    private Long fechaPago;
    
    /**
     * Número de planilla
     */
    private String numeroPlanilla;

    /**
     * Número de planilla corregida
     */
    private String numeroPlanillaCorregida;

    /**
     * Tipo de Archivo
     */
    private TipoArchivoPilaEnum tipoArhivo;

    /**
     * Aporte relacionado
     */
    private EstadoRegistroAporteEnum aporteRelacionado;

    /**
     * Tipo Planilla
     */
    private TipoPlanillaEnum tipoPlanilla;

    /**
     * Estado de archivo
     */
    private EstadoProcesoArchivoEnum estadoArchivo;

    /**
     * Tipo de cotizante
     */
    private TipoAfiliadoEnum tipoCotizante;

    /**
     * Aporte de registro
     */
    private BigDecimal aporteDeRegistro;

    /**
     * Intereses de aporte
     */
    private BigDecimal interesesAporte;

    /**
     * Total de aportes
     */
    private BigDecimal totalAporte;

    /**
     * Tipo ajuste monetario
     */
    private TipoAjusteMovimientoAporteEnum tipoAjusteMonetario;

    /**
     * Valor del ajuste
     */
    private BigDecimal ajuste;

    /**
     * Total de ajuste
     */
    private BigDecimal totalAjuste;

    /**
     * Interes de ajuste
     */
    private BigDecimal interesesAjuste;

    /**
     * Aporte final de registro
     */
    private BigDecimal aporteFinalRegistro;

    /**
     * Interes final de ajuste
     */
    private BigDecimal interesesFinalAjuste;

    /**
     * Valor total del aporte final
     */
    private BigDecimal totalAporteFinal;

    /**
     * Número de operación nuevo
     */
    private String nuevoNumeroOperacion;

    /**
     * Estado del aporte
     */
    private EstadoAporteEnum estadoAporte;

    /**
     * Identificador del aporte general relacionado con la cuenta de aporte
     */
    private Long idAporteGeneral;

    /**
     * Identificador del aporte detallado relacionado con la cunta de aporte
     */
    private Long idAporteDetallado;

    /**
     * Estado Registro
     */
    private EstadoRegistroAporteEnum estadoRegistro;

    /**
     * Solicitud de devolucion para vista 360 de aportes
     */
    private SolicitudDevolucionAporteModeloDTO solicitudDevolucionAporteModeloDTO;

    /**
     * Solicitud de correcion para vista 360 de aportes
     */
    private List<SolicitudCorreccionAporteModeloDTO> solicitudCorreccionAporteModeloDTO;

    /**
     * Marca de Referencia que indica que el aportante es "pagador por terceros"
     * para el aporte asociado para la vista 360 persona de aporte.
     */
    private Boolean pagadorPorTerceros;

    /**
     * Nombre completo ó razon social para vista 360 de aportes.
     */
    private String nombreCompleto;

    /**
     * Nombre completo ó razon social para vista 360 de aportes.
     */
    private String nombreCompletoAportante;
    
    /**
     * Campo que indica si hay o no detalle para mostrar para vista 360 de aportes.
     */
    private Boolean conDetalle;

    /**
     * Solicitud de aporte para vista 360 de aportes
     */
    private SolicitudAporteModeloDTO solicitudAporteModeloDTO;

    /**
     * Identificador de documento para vista 360 de aportes
     */
    private String identificadorDocumento;

    /**
     * Codigo de la entidad financiera recaudadora o receptora para vista 360 de aportes
     */
    private Short codigoEntidadFinanciera;

    /**
     * Indica si tiene o no modificaciones el archivo que reportó el recaudo para vista 360 de aportes.
     */
    private Boolean tieneModificaciones;
    
    /**
     * Identificador del movimiento del aporte
     */
    private Long idMovimientoAporte;
    
    /**
     * Identificador de registro detallado en PILA
     * */
    private Long idRegistroDetallado;
    
    /**
     * Tipo de identificación del tramitador que se adiciono para la vista 360 persona de aporte.
     */
    private TipoIdentificacionEnum tipoIdentificacionTramitador;
    
    /**
     * Número de identificación del tramitador que se adiciono para la vista 360 persona de aporte.
     */
    private String numeroIdentificacionTramitador;
    
    /**
     * Nombre completo ó razon social para vista 360 de aportes (tramitador).
     */
    private String nombreCompletoTramitador;

    /**
     * Monto como se encuentra el aporte actualmente registrado en core
     */
    private BigDecimal montoAporteActual;
    
    /**
     * Interes como se encuentra el aporte actualmente registrado en core
     */
    private BigDecimal interesAporteActual;
    
    /**
     * Total como se encuentra el aporte actualmente registrado en core
     */
    private BigDecimal totalAporteActual;

    /**
     * Fecha de pago de la planilla
     */
    private Long fechaPagoAporte;
    
    /**
     * RegsitroControl de RegistroGeneral
     */
    private Long registroControl;
    
    private Integer cuentaBancariaRecaudo; 
    
    private String cuentaBancariaRecaudoTexto; 
    
    
    /**
     * Método Constructor
     */
    public CuentaAporteDTO() {
    }

    /**
     * Método Constructor P
     * @param idAporteDetallado
     * @param tipoMovimiento
     */
    public CuentaAporteDTO(TipoMovimientoRecaudoAporteEnum tipoMovimiento, EstadoRegistroAporteEnum estadoRegistroAporte,
            TipoAjusteMovimientoAporteEnum tipoAjuste, BigDecimal valorAporteAjuste, BigDecimal valorMoraAjuste, Long idMovimientoAporte,
            EstadoAporteEnum estadoAporte, Long idAporteGeneral, Long idAporteDetallado, EstadoRegistroAporteEnum estadoRegistro,
            TipoAfiliadoEnum tipoCotizante, Date fechaRegistro, Date fechaRecaudo, Persona cotizante) {
        this.tipoMovimientoRecaudo = tipoMovimiento;
        this.aporteRelacionado = estadoRegistroAporte;
        this.tipoAjusteMonetario = tipoAjuste;
        this.ajuste = valorAporteAjuste != null ? valorAporteAjuste : BigDecimal.ZERO;
        this.interesesAjuste = valorMoraAjuste != null ? valorMoraAjuste : BigDecimal.ZERO;
        this.totalAjuste = this.ajuste.add(this.interesesAjuste);
        this.idMovimientoAporte = idMovimientoAporte;
        this.estadoAporte = estadoAporte;
        this.idAporteGeneral = idAporteGeneral;
        this.idAporteDetallado = idAporteDetallado;
        this.estadoRegistro = estadoRegistro;
        this.tipoCotizante = tipoCotizante;
        if (fechaRegistro != null) {
            this.fechaRegistro = fechaRegistro.getTime();
        }
        if (fechaRecaudo != null) {
            this.fechaPago = fechaRecaudo.getTime();
        }
        this.tipoIdentificacionCotizante = cotizante.getTipoIdentificacion();
        this.numeroIdentificacionCotizante = cotizante.getNumeroIdentificacion();
    }

    /**
     * Método Constructor
     * @param tipoMovimiento
     *        Tipo de movimiento del aporte
     * @param estadoRegistroAporte
     *        Estado de registro del aporte para el aportante
     * @param tipoAjuste
     *        Tipo de ajuste que se realizo al movimiento
     * @param valorAporteAjuste
     *        Valor del aporte ajustado
     * @param valorMoraAjuste
     *        Valor de mora para el aporte ajustado
     * @param idMovimientoAporte
     *        Identificador del movimiento de aporte
     * @param estadoAporte
     *        Estado del aporte
     * @param idAporteGeneral
     *        Identificador del aporte general
     */
    public CuentaAporteDTO(TipoMovimientoRecaudoAporteEnum tipoMovimiento, EstadoRegistroAporteEnum estadoRegistroAporte,
            TipoAjusteMovimientoAporteEnum tipoAjuste, BigDecimal valorAporteAjuste, BigDecimal valorMoraAjuste, Long idMovimientoAporte,
            EstadoAporteEnum estadoAporte, Long idAporteGeneral, Date fechaRegistro, Date fechaRecaudo) {
        this.tipoMovimientoRecaudo = tipoMovimiento;
        this.aporteRelacionado = estadoRegistroAporte;
        this.tipoAjusteMonetario = tipoAjuste;
        this.ajuste = valorAporteAjuste != null ? valorAporteAjuste : BigDecimal.ZERO;
        this.interesesAjuste = valorMoraAjuste != null ? valorMoraAjuste : BigDecimal.ZERO;
        this.totalAjuste = this.ajuste.add(this.interesesAjuste);
        this.idMovimientoAporte = idMovimientoAporte;
        this.estadoAporte = estadoAporte;
        this.idAporteGeneral = idAporteGeneral;
        if (fechaRegistro != null) {
            this.fechaRegistro = fechaRegistro.getTime();
        }
        if (fechaRecaudo != null) {
            this.fechaPago = fechaRecaudo.getTime();
        }
    }

    
    
    
    public Integer getCuentaBancariaRecaudo() {
        return cuentaBancariaRecaudo;
    }

    public void setCuentaBancariaRecaudo(Integer cuentaBancariaRecaudo) {
        this.cuentaBancariaRecaudo = cuentaBancariaRecaudo;
    }

    public String getCuentaBancariaRecaudoTexto() {
        return cuentaBancariaRecaudoTexto;
    }

    /**
     * Método que retorna el valor de numeroOperacion.
     * 
     * @return valor de numeroOperacion.
     */
    public void setCuentaBancariaRecaudoTexto(String cuentaBancariaRecaudoTexto) {
        this.cuentaBancariaRecaudoTexto = cuentaBancariaRecaudoTexto;
    }

    public String getNumeroOperacion() {
        return numeroOperacion;
    }

    /**
     * Método que retorna el valor de tipoMovimientoRecaudo.
     * 
     * @return valor de tipoMovimientoRecaudo.
     */
    public TipoMovimientoRecaudoAporteEnum getTipoMovimientoRecaudo() {
        return tipoMovimientoRecaudo;
    }

    /**
     * Método que retorna el valor de tipoIdentificacionAportante.
     * 
     * @return valor de tipoIdentificacionAportante.
     */
    public TipoIdentificacionEnum getTipoIdentificacionAportante() {
        return tipoIdentificacionAportante;
    }

    /**
     * Método que retorna el valor de periodoPago.
     * 
     * @return valor de periodoPago.
     */
    public Long getPeriodoPago() {
        return periodoPago;
    }

    /**
     * Método que retorna el valor de fechaRegistro.
     * 
     * @return valor de fechaRegistro.
     */
    public Long getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Método que retorna el valor de numeroPlanilla.
     * 
     * @return valor de numeroPlanilla.
     */
    public String getNumeroPlanilla() {
        return numeroPlanilla;
    }

    /**
     * Método que retorna el valor de numeroPlanillaCorregida.
     * 
     * @return valor de numeroPlanillaCorregida.
     */
    public String getNumeroPlanillaCorregida() {
        return numeroPlanillaCorregida;
    }

    /**
     * Método que retorna el valor de tipoArhivo.
     * 
     * @return valor de tipoArhivo.
     */
    public TipoArchivoPilaEnum getTipoArhivo() {
        return tipoArhivo;
    }

    /**
     * Método que retorna el valor de tipoPlanilla.
     * 
     * @return valor de tipoPlanilla.
     */
    public TipoPlanillaEnum getTipoPlanilla() {
        return tipoPlanilla;
    }

    /**
     * Método que retorna el valor de estadoArchivo.
     * 
     * @return valor de estadoArchivo.
     */
    public EstadoProcesoArchivoEnum getEstadoArchivo() {
        return estadoArchivo;
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
     * Método que retorna el valor de aporteDeRegistro.
     * 
     * @return valor de aporteDeRegistro.
     */
    public BigDecimal getAporteDeRegistro() {
        return aporteDeRegistro;
    }

    /**
     * Método que retorna el valor de interesesAporte.
     * 
     * @return valor de interesesAporte.
     */
    public BigDecimal getInteresesAporte() {
        return interesesAporte;
    }

    /**
     * Método que retorna el valor de totalAporte.
     * 
     * @return valor de totalAporte.
     */
    public BigDecimal getTotalAporte() {
        return totalAporte;
    }

    /**
     * Método que retorna el valor de tipoAjusteMonetario.
     * 
     * @return valor de tipoAjusteMonetario.
     */
    public TipoAjusteMovimientoAporteEnum getTipoAjusteMonetario() {
        return tipoAjusteMonetario;
    }

    /**
     * Método que retorna el valor de ajuste.
     * 
     * @return valor de ajuste.
     */
    public BigDecimal getAjuste() {
        return ajuste;
    }

    /**
     * Método que retorna el valor de totalAjuste.
     * 
     * @return valor de totalAjuste.
     */
    public BigDecimal getTotalAjuste() {
        return totalAjuste;
    }

    /**
     * Método que retorna el valor de interesesAjuste.
     * 
     * @return valor de interesesAjuste.
     */
    public BigDecimal getInteresesAjuste() {
        return interesesAjuste;
    }

    /**
     * Método que retorna el valor de aporteFinalRegistro.
     * 
     * @return valor de aporteFinalRegistro.
     */
    public BigDecimal getAporteFinalRegistro() {
        return aporteFinalRegistro;
    }

    /**
     * Método que retorna el valor de interesesFinalAjuste.
     * 
     * @return valor de interesesFinalAjuste.
     */
    public BigDecimal getInteresesFinalAjuste() {
        return interesesFinalAjuste;
    }

    /**
     * Método que retorna el valor de totalAporteFinal.
     * 
     * @return valor de totalAporteFinal.
     */
    public BigDecimal getTotalAporteFinal() {
        return totalAporteFinal;
    }

    /**
     * Método que retorna el valor de estadoAporte.
     * 
     * @return valor de estadoAporte.
     */
    public EstadoAporteEnum getEstadoAporte() {
        return estadoAporte;
    }

    /**
     * Método encargado de modificar el valor de numeroOperacion.
     * 
     * @param valor
     *        para modificar numeroOperacion.
     */
    public void setNumeroOperacion(String numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }

    /**
     * Método encargado de modificar el valor de tipoMovimientoRecaudo.
     * 
     * @param valor
     *        para modificar tipoMovimientoRecaudo.
     */
    public void setTipoMovimientoRecaudo(TipoMovimientoRecaudoAporteEnum tipoMovimientoRecaudo) {
        this.tipoMovimientoRecaudo = tipoMovimientoRecaudo;
    }

    /**
     * Método encargado de modificar el valor de tipoIdentificacionAportante.
     * 
     * @param valor
     *        para modificar tipoIdentificacionAportante.
     */
    public void setTipoIdentificacionAportante(TipoIdentificacionEnum tipoIdentificacionAportante) {
        this.tipoIdentificacionAportante = tipoIdentificacionAportante;
    }

    /**
     * @return the numeroIdentificacionAportante
     */
    public String getNumeroIdentificacionAportante() {
        return numeroIdentificacionAportante;
    }

    /**
     * @param numeroIdentificacionAportante
     *        the numeroIdentificacionAportante to set
     */
    public void setNumeroIdentificacionAportante(String numeroIdentificacionAportante) {
        this.numeroIdentificacionAportante = numeroIdentificacionAportante;
    }

    /**
     * Método encargado de modificar el valor de periodoPago.
     * 
     * @param valor
     *        para modificar periodoPago.
     */
    public void setPeriodoPago(Long periodoPago) {
        this.periodoPago = periodoPago;
    }

    /**
     * Método encargado de modificar el valor de fechaRegistro.
     * 
     * @param valor
     *        para modificar fechaRegistro.
     */
    public void setFechaRegistro(Long fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Método encargado de modificar el valor de numeroPlanilla.
     * 
     * @param valor
     *        para modificar numeroPlanilla.
     */
    public void setNumeroPlanilla(String numeroPlanilla) {
        this.numeroPlanilla = numeroPlanilla;
    }

    /**
     * Método encargado de modificar el valor de numeroPlanillaCorregida.
     * 
     * @param valor
     *        para modificar numeroPlanillaCorregida.
     */
    public void setNumeroPlanillaCorregida(String numeroPlanillaCorregida) {
        this.numeroPlanillaCorregida = numeroPlanillaCorregida;
    }

    /**
     * Método encargado de modificar el valor de tipoArhivo.
     * 
     * @param valor
     *        para modificar tipoArhivo.
     */
    public void setTipoArhivo(TipoArchivoPilaEnum tipoArhivo) {
        this.tipoArhivo = tipoArhivo;
    }

    /**
     * @return the aporteRelacionado
     */
    public EstadoRegistroAporteEnum getAporteRelacionado() {
        return aporteRelacionado;
    }

    /**
     * @param aporteRelacionado
     *        the aporteRelacionado to set
     */
    public void setAporteRelacionado(EstadoRegistroAporteEnum aporteRelacionado) {
        this.aporteRelacionado = aporteRelacionado;
    }

    /**
     * Método encargado de modificar el valor de tipoPlanilla.
     * 
     * @param valor
     *        para modificar tipoPlanilla.
     */
    public void setTipoPlanilla(TipoPlanillaEnum tipoPlanilla) {
        this.tipoPlanilla = tipoPlanilla;
    }

    /**
     * Método encargado de modificar el valor de estadoArchivo.
     * 
     * @param valor
     *        para modificar estadoArchivo.
     */
    public void setEstadoArchivo(EstadoProcesoArchivoEnum estadoArchivo) {
        this.estadoArchivo = estadoArchivo;
    }

    /**
     * Método encargado de modificar el valor de aporteDeRegistro.
     * 
     * @param valor
     *        para modificar aporteDeRegistro.
     */
    public void setAporteDeRegistro(BigDecimal aporteDeRegistro) {
        this.aporteDeRegistro = aporteDeRegistro;
    }

    /**
     * Método encargado de modificar el valor de interesesAporte.
     * 
     * @param valor
     *        para modificar interesesAporte.
     */
    public void setInteresesAporte(BigDecimal interesesAporte) {
        this.interesesAporte = interesesAporte;
    }

    /**
     * Método encargado de modificar el valor de totalAporte.
     * 
     * @param valor
     *        para modificar totalAporte.
     */
    public void setTotalAporte(BigDecimal totalAporte) {
        this.totalAporte = totalAporte;
    }

    /**
     * Método encargado de modificar el valor de tipoAjusteMonetario.
     * 
     * @param valor
     *        para modificar tipoAjusteMonetario.
     */
    public void setTipoAjusteMonetario(TipoAjusteMovimientoAporteEnum tipoAjusteMonetario) {
        this.tipoAjusteMonetario = tipoAjusteMonetario;
    }

    /**
     * Método encargado de modificar el valor de ajuste.
     * 
     * @param valor
     *        para modificar ajuste.
     */
    public void setAjuste(BigDecimal ajuste) {
        this.ajuste = ajuste;
    }

    /**
     * Método encargado de modificar el valor de totalAjuste.
     * 
     * @param valor
     *        para modificar totalAjuste.
     */
    public void setTotalAjuste(BigDecimal totalAjuste) {
        this.totalAjuste = totalAjuste;
    }

    /**
     * Método encargado de modificar el valor de interesesAjuste.
     * 
     * @param valor
     *        para modificar interesesAjuste.
     */
    public void setInteresesAjuste(BigDecimal interesesAjuste) {
        this.interesesAjuste = interesesAjuste;
    }

    /**
     * Método encargado de modificar el valor de aporteFinalRegistro.
     * 
     * @param valor
     *        para modificar aporteFinalRegistro.
     */
    public void setAporteFinalRegistro(BigDecimal aporteFinalRegistro) {
        this.aporteFinalRegistro = aporteFinalRegistro;
    }

    /**
     * Método encargado de modificar el valor de interesesFinalAjuste.
     * 
     * @param valor
     *        para modificar interesesFinalAjuste.
     */
    public void setInteresesFinalAjuste(BigDecimal interesesFinalAjuste) {
        this.interesesFinalAjuste = interesesFinalAjuste;
    }

    /**
     * Método encargado de modificar el valor de totalAporteFinal.
     * 
     * @param valor
     *        para modificar totalAporteFinal.
     */
    public void setTotalAporteFinal(BigDecimal totalAporteFinal) {
        this.totalAporteFinal = totalAporteFinal;
    }

    /**
     * Método encargado de modificar el valor de estadoAporte.
     * 
     * @param valor
     *        para modificar estadoAporte.
     */
    public void setEstadoAporte(EstadoAporteEnum estadoAporte) {
        this.estadoAporte = estadoAporte;
    }

    /**
     * @return the idAporteGeneral
     */
    public Long getIdAporteGeneral() {
        return idAporteGeneral;
    }

    /**
     * @param idAporteGeneral
     *        the idAporteGeneral to set
     */
    public void setIdAporteGeneral(Long idAporteGeneral) {
        this.idAporteGeneral = idAporteGeneral;
    }

    /**
     * @return the idAporteDetallado
     */
    public Long getIdAporteDetallado() {
        return idAporteDetallado;
    }

    /**
     * @param idAporteDetallado
     *        the idAporteDetallado to set
     */
    public void setIdAporteDetallado(Long idAporteDetallado) {
        this.idAporteDetallado = idAporteDetallado;
    }

    /**
     * @return the estadoRegistro
     */
    public EstadoRegistroAporteEnum getEstadoRegistro() {
        return estadoRegistro;
    }

    /**
     * @param estadoRegistro
     *        the estadoRegistro to set
     */
    public void setEstadoRegistro(EstadoRegistroAporteEnum estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    /**
     * Método que retorna el valor de solicitudDevolucionAporteModeloDTO.
     * @return valor de solicitudDevolucionAporteModeloDTO.
     */
    public SolicitudDevolucionAporteModeloDTO getSolicitudDevolucionAporteModeloDTO() {
        return solicitudDevolucionAporteModeloDTO;
    }

    /**
     * Método que retorna el valor de solicitudCorreccionAporteModeloDTO.
     * @return valor de solicitudCorreccionAporteModeloDTO.
     */
    public List<SolicitudCorreccionAporteModeloDTO> getSolicitudCorreccionAporteModeloDTO() {
        return solicitudCorreccionAporteModeloDTO;
    }

    /**
     * Método encargado de modificar el valor de solicitudDevolucionAporteModeloDTO.
     * @param valor
     *        para modificar solicitudDevolucionAporteModeloDTO.
     */
    public void setSolicitudDevolucionAporteModeloDTO(SolicitudDevolucionAporteModeloDTO solicitudDevolucionAporteModeloDTO) {
        this.solicitudDevolucionAporteModeloDTO = solicitudDevolucionAporteModeloDTO;
    }

    /**
     * Método encargado de modificar el valor de solicitudCorreccionAporteModeloDTO.
     * @param valor
     *        para modificar solicitudCorreccionAporteModeloDTO.
     */
    public void setSolicitudCorreccionAporteModeloDTO(List<SolicitudCorreccionAporteModeloDTO> solicitudCorreccionAporteModeloDTO) {
        this.solicitudCorreccionAporteModeloDTO = solicitudCorreccionAporteModeloDTO;
    }

    /**
     * Método que retorna el valor de pagadorPorTerceros.
     * @return valor de pagadorPorTerceros.
     */
    public Boolean getPagadorPorTerceros() {
        return pagadorPorTerceros;
    }

    /**
     * Método que retorna el valor de nombreCompleto.
     * @return valor de nombreCompleto.
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * Método encargado de modificar el valor de pagadorPorTerceros.
     * @param valor
     *        para modificar pagadorPorTerceros.
     */
    public void setPagadorPorTerceros(Boolean pagadorPorTerceros) {
        this.pagadorPorTerceros = pagadorPorTerceros;
    }

    /**
     * Método encargado de modificar el valor de nombreCompleto.
     * @param valor
     *        para modificar nombreCompleto.
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * Método que retorna el valor de conDetalle.
     * @return valor de conDetalle.
     */
    public Boolean getConDetalle() {
        return conDetalle;
    }

    /**
     * Método encargado de modificar el valor de conDetalle.
     * @param valor
     *        para modificar conDetalle.
     */
    public void setConDetalle(Boolean conDetalle) {
        this.conDetalle = conDetalle;
    }

    /**
     * Método que retorna el valor de solicitudAporteModeloDTO.
     * @return valor de solicitudAporteModeloDTO.
     */
    public SolicitudAporteModeloDTO getSolicitudAporteModeloDTO() {
        return solicitudAporteModeloDTO;
    }

    /**
     * Método encargado de modificar el valor de solicitudAporteModeloDTO.
     * @param valor
     *        para modificar solicitudAporteModeloDTO.
     */
    public void setSolicitudAporteModeloDTO(SolicitudAporteModeloDTO solicitudAporteModeloDTO) {
        this.solicitudAporteModeloDTO = solicitudAporteModeloDTO;
    }

    /**
     * Método que retorna el valor de identificacionDocumento.
     * @return valor de identificacionDocumento.
     */
    public String getIdentificadorDocumento() {
        return identificadorDocumento;
    }

    /**
     * Método encargado de modificar el valor de identificacionDocumento.
     * @param valor
     *        para modificar identificacionDocumento.
     */
    public void setIdentificadorDocumento(String identificacionDocumento) {
        this.identificadorDocumento = identificacionDocumento;
    }

    /**
     * Método que retorna el valor de codigoEntidadFinanciera.
     * @return valor de codigoEntidadFinanciera.
     */
    public Short getCodigoEntidadFinanciera() {
        return codigoEntidadFinanciera;
    }

    /**
     * Método encargado de modificar el valor de codigoEntidadFinanciera.
     * @param valor
     *        para modificar codigoEntidadFinanciera.
     */
    public void setCodigoEntidadFinanciera(Short codigoEntidadFinanciera) {
        this.codigoEntidadFinanciera = codigoEntidadFinanciera;
    }

    /**
     * Método que retorna el valor de tieneModificaciones.
     * @return valor de tieneModificaciones.
     */
    public Boolean getTieneModificaciones() {
        return tieneModificaciones;
    }

    /**
     * Método encargado de modificar el valor de tieneModificaciones.
     * @param valor
     *        para modificar tieneModificaciones.
     */
    public void setTieneModificaciones(Boolean tieneModificaciones) {
        this.tieneModificaciones = tieneModificaciones;
    }

    /**
     * @return the nuevoNumeroOperacion
     */
    public String getNuevoNumeroOperacion() {
        return nuevoNumeroOperacion;
    }

    /**
     * @param nuevoNumeroOperacion the nuevoNumeroOperacion to set
     */
    public void setNuevoNumeroOperacion(String nuevoNumeroOperacion) {
        this.nuevoNumeroOperacion = nuevoNumeroOperacion;
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
     * @return the nombreCompletoAportante
     */
    public String getNombreCompletoAportante() {
        return nombreCompletoAportante;
    }

    /**
     * @param nombreCompletoAportante the nombreCompletoAportante to set
     */
    public void setNombreCompletoAportante(String nombreCompletoAportante) {
        this.nombreCompletoAportante = nombreCompletoAportante;
    }

    /**
     * @return the idRegistroDetallado
     */
    public Long getIdRegistroDetallado() {
        return idRegistroDetallado;
    }

    /**
     * @param idRegistroDetallado the idRegistroDetallado to set
     */
    public void setIdRegistroDetallado(Long idRegistroDetallado) {
        this.idRegistroDetallado = idRegistroDetallado;
    }

    /**
     * @return the tipoIdentificacionTramitador
     */
    public TipoIdentificacionEnum getTipoIdentificacionTramitador() {
        return tipoIdentificacionTramitador;
    }

    /**
     * @param tipoIdentificacionTramitador the tipoIdentificacionTramitador to set
     */
    public void setTipoIdentificacionTramitador(TipoIdentificacionEnum tipoIdentificacionTramitador) {
        this.tipoIdentificacionTramitador = tipoIdentificacionTramitador;
    }

    /**
     * @return the numeroIdentificacionTramitador
     */
    public String getNumeroIdentificacionTramitador() {
        return numeroIdentificacionTramitador;
    }

    /**
     * @param numeroIdentificacionTramitador the numeroIdentificacionTramitador to set
     */
    public void setNumeroIdentificacionTramitador(String numeroIdentificacionTramitador) {
        this.numeroIdentificacionTramitador = numeroIdentificacionTramitador;
    }

    /**
     * @return the nombreCompletoTramitador
     */
    public String getNombreCompletoTramitador() {
        return nombreCompletoTramitador;
    }

    /**
     * @param nombreCompletoTramitador the nombreCompletoTramitador to set
     */
    public void setNombreCompletoTramitador(String nombreCompletoTramitador) {
        this.nombreCompletoTramitador = nombreCompletoTramitador;
    }

    /**
     * @return the montoAporteActual
     */
    public BigDecimal getMontoAporteActual() {
        return montoAporteActual;
    }

    /**
     * @param montoAporteActual the montoAporteActual to set
     */
    public void setMontoAporteActual(BigDecimal montoAporteActual) {
        this.montoAporteActual = montoAporteActual;
    }

    /**
     * @return the interesAporteActual
     */
    public BigDecimal getInteresAporteActual() {
        return interesAporteActual;
    }

    /**
     * @param interesAporteActual the interesAporteActual to set
     */
    public void setInteresAporteActual(BigDecimal interesAporteActual) {
        this.interesAporteActual = interesAporteActual;
    }

    /**
     * @return the totalAporteActual
     */
    public BigDecimal getTotalAporteActual() {
        return totalAporteActual;
    }

    /**
     * @param totalAporteActual the totalAporteActual to set
     */
    public void setTotalAporteActual(BigDecimal totalAporteActual) {
        this.totalAporteActual = totalAporteActual;
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
     * @return the tipoIdentificacionAportanteCorreccion
     */
    public TipoIdentificacionEnum getTipoIdentificacionAportanteCorreccion() {
        return tipoIdentificacionAportanteCorreccion;
    }

    /**
     * @param tipoIdentificacionAportanteCorreccion the tipoIdentificacionAportanteCorreccion to set
     */
    public void setTipoIdentificacionAportanteCorreccion(TipoIdentificacionEnum tipoIdentificacionAportanteCorreccion) {
        this.tipoIdentificacionAportanteCorreccion = tipoIdentificacionAportanteCorreccion;
    }

    /**
     * @return the numeroIdentificacionAportanteCorreccion
     */
    public String getNumeroIdentificacionAportanteCorreccion() {
        return numeroIdentificacionAportanteCorreccion;
    }

    /**
     * @param numeroIdentificacionAportanteCorreccion the numeroIdentificacionAportanteCorreccion to set
     */
    public void setNumeroIdentificacionAportanteCorreccion(String numeroIdentificacionAportanteCorreccion) {
        this.numeroIdentificacionAportanteCorreccion = numeroIdentificacionAportanteCorreccion;
    }

    /**
     * @return the fechaPago
     */
    public Long getFechaPago() {
        return fechaPago;
    }

    /**
     * @param fechaPago the fechaPago to set
     */
    public void setFechaPago(Long fechaPago) {
        this.fechaPago = fechaPago;
    }

    /**
     * @return a
     */
    public Long getRegistroControl() {
        return registroControl;
    }

    /**
     * @param registroControl
     */
    public void setRegistroControl(Long registroControl) {
        this.registroControl = registroControl;
    }

    public Long getFechaPagoAporte() {
        return this.fechaPagoAporte;
    }

    public void setFechaPagoAporte(Long fechaPagoAporte) {
        this.fechaPagoAporte = fechaPagoAporte;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "CuentaAporteDTO{" +
                "numeroOperacion='" + numeroOperacion + '\'' +
                ", tipoMovimientoRecaudo=" + tipoMovimientoRecaudo +
                ", tipoIdentificacionAportante=" + tipoIdentificacionAportante +
                ", numeroIdentificacionAportante='" + numeroIdentificacionAportante + '\'' +
                ", tipoIdentificacionCotizante=" + tipoIdentificacionCotizante +
                ", numeroIdentificacionCotizante='" + numeroIdentificacionCotizante + '\'' +
                ", tipoIdentificacionAportanteCorreccion=" + tipoIdentificacionAportanteCorreccion +
                ", numeroIdentificacionAportanteCorreccion='" + numeroIdentificacionAportanteCorreccion + '\'' +
                ", periodoPago=" + periodoPago +
                ", fechaRegistro=" + fechaRegistro +
                ", fechaPago=" + fechaPago +
                ", numeroPlanilla='" + numeroPlanilla + '\'' +
                ", numeroPlanillaCorregida='" + numeroPlanillaCorregida + '\'' +
                ", tipoArhivo=" + tipoArhivo +
                ", aporteRelacionado=" + aporteRelacionado +
                ", tipoPlanilla=" + tipoPlanilla +
                ", estadoArchivo=" + estadoArchivo +
                ", tipoCotizante=" + tipoCotizante +
                ", aporteDeRegistro=" + aporteDeRegistro +
                ", interesesAporte=" + interesesAporte +
                ", totalAporte=" + totalAporte +
                ", tipoAjusteMonetario=" + tipoAjusteMonetario +
                ", ajuste=" + ajuste +
                ", totalAjuste=" + totalAjuste +
                ", interesesAjuste=" + interesesAjuste +
                ", aporteFinalRegistro=" + aporteFinalRegistro +
                ", interesesFinalAjuste=" + interesesFinalAjuste +
                ", totalAporteFinal=" + totalAporteFinal +
                ", nuevoNumeroOperacion='" + nuevoNumeroOperacion + '\'' +
                ", estadoAporte=" + estadoAporte +
                ", idAporteGeneral=" + idAporteGeneral +
                ", idAporteDetallado=" + idAporteDetallado +
                ", estadoRegistro=" + estadoRegistro +
                ", solicitudDevolucionAporteModeloDTO=" + solicitudDevolucionAporteModeloDTO +
                ", solicitudCorreccionAporteModeloDTO=" + solicitudCorreccionAporteModeloDTO +
                ", pagadorPorTerceros=" + pagadorPorTerceros +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", nombreCompletoAportante='" + nombreCompletoAportante + '\'' +
                ", conDetalle=" + conDetalle +
                ", solicitudAporteModeloDTO=" + solicitudAporteModeloDTO +
                ", identificadorDocumento='" + identificadorDocumento + '\'' +
                ", codigoEntidadFinanciera=" + codigoEntidadFinanciera +
                ", tieneModificaciones=" + tieneModificaciones +
                ", idMovimientoAporte=" + idMovimientoAporte +
                ", idRegistroDetallado=" + idRegistroDetallado +
                ", tipoIdentificacionTramitador=" + tipoIdentificacionTramitador +
                ", numeroIdentificacionTramitador='" + numeroIdentificacionTramitador + '\'' +
                ", nombreCompletoTramitador='" + nombreCompletoTramitador + '\'' +
                ", montoAporteActual=" + montoAporteActual +
                ", interesAporteActual=" + interesAporteActual +
                ", totalAporteActual=" + totalAporteActual +
                ", registroControl=" + registroControl +
                '}';
    }
}
