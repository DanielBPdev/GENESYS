package com.asopagos.dto.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.asopagos.entidades.pila.temporal.TemAporte;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import com.asopagos.enumeraciones.aportes.FormaReconocimientoAporteEnum;
import com.asopagos.enumeraciones.aportes.ModalidadPlanillaEnum;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EstadoRegistroAportesArchivoEnum;
import com.asopagos.enumeraciones.pila.MarcaRegistroAporteArchivoEnum;

/**
 * <b>Descripcion:</b> DTO que representa el entity de la tabla TemAporte<br/>
 * <b>Módulo:</b> Asopagos - HU 211-410<br/> 
 *
 * @author  <a href="mailto:jocampo@heinsohn.com.co"> jocampo</a>
 * @author  <a href="mailto:jocampo@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class TemAporteModeloDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    
    private Long idTransaccion;
    
    private Boolean marcaAporteSimulado;
    
    private Boolean marcaAporteManual;
    
    private Long registroGeneral;
    
    private String periodoAporte;
    
    private BigDecimal valTotalApoObligatorio;
    
    private BigDecimal valorIntMoraGeneral;
    
    private Date fechaRecaudo;
    
    private TipoIdentificacionEnum tipoIdAportante;
    
    private String numeroIdAportante;
    
    private Short codEntidadFinanciera;
    
    private Long operadorInformacion;
    
    private ModalidadPlanillaEnum modalidadPlanilla;
    
    private ModalidadRecaudoAporteEnum modalidadRecaudoAporte;
    
    private Boolean apoConDetalle;
    
    private FormaReconocimientoAporteEnum formaReconocimientoAporte;
    
    private String numeroCuenta;
    
    private Long registroDetallado;
    
    private TipoIdentificacionEnum tipoIdCotizante;
    
    private String numeroIdCotizante;
    
    private Short diasCotizados;
    
    private Short horasLaboradas;
    
    private BigDecimal salarioBasico;
    
    private String municipioLaboral;
    
    private String departamentoLaboral;
    
    private BigDecimal valorIBC;
    
    private BigDecimal tarifa;
    
    private Boolean salarioIntegral;
    
    private BigDecimal aporteObligatorio;
    
    private BigDecimal valorSaldoAporte;
    
    private BigDecimal valorIntMoraDetalle;
    
    private String correcciones;
    
    private Date fechaProcesamiento;
    
    private EstadoAporteEnum estadoAporteRecaudo;
    
    private EstadoAporteEnum estadoAporteAjuste;
    
    private EstadoRegistroAporteEnum estadoRegistroAporte;
    
    private MarcaRegistroAporteArchivoEnum marcaValRegistroAporte;
    
    private EstadoRegistroAportesArchivoEnum estadoValRegistroAporte;
    
    private String usuarioAprobadorAporte;
    
    private Boolean presentaNovedad;
    
    private String numeroPlanillaManual;
    
    private Boolean enProceso;
    
    /**
     * Método que convierte el DTO a la entidad
     * @return
     */
    public TemAporte convertToEntity() {
        TemAporte temAporte = new TemAporte();
        temAporte.setId(this.getId());
        temAporte.setIdTransaccion(this.getIdTransaccion());
        temAporte.setMarcaAporteSimulado(this.getMarcaAporteSimulado());
        temAporte.setMarcaAporteManual(this.getMarcaAporteManual());
        temAporte.setRegistroGeneral(this.getRegistroGeneral());
        temAporte.setPeriodoAporte(this.getPeriodoAporte());
        temAporte.setValTotalApoObligatorio(this.getValTotalApoObligatorio());
        temAporte.setValorIntMoraGeneral(this.getValorIntMoraGeneral());
        temAporte.setFechaRecaudo(this.getFechaRecaudo());
        temAporte.setTipoIdAportante(this.getTipoIdAportante());
        temAporte.setNumeroIdAportante(this.getNumeroIdAportante());
        temAporte.setCodEntidadFinanciera(this.getCodEntidadFinanciera());
        temAporte.setOperadorInformacion(this.getOperadorInformacion());
        temAporte.setModalidadPlanilla(this.getModalidadPlanilla());
        temAporte.setModalidadRecaudoAporte(this.getModalidadRecaudoAporte());
        temAporte.setApoConDetalle(this.getApoConDetalle());
        temAporte.setFormaReconocimientoAporte(this.getFormaReconocimientoAporte());
        temAporte.setNumeroCuenta(this.getNumeroCuenta());
        temAporte.setRegistroDetallado(this.getRegistroDetallado());
        temAporte.setTipoIdCotizante(this.getTipoIdCotizante());
        temAporte.setNumeroIdCotizante(this.getNumeroIdCotizante());
        temAporte.setDiasCotizados(this.getDiasCotizados());
        temAporte.setHorasLaboradas(this.getHorasLaboradas());
        temAporte.setSalarioBasico(this.getSalarioBasico());
        temAporte.setMunicipioLaboral(this.getMunicipioLaboral());
        temAporte.setDepartamentoLaboral(this.getDepartamentoLaboral());
        temAporte.setValorIBC(this.getValorIBC());
        temAporte.setTarifa(this.getTarifa());
        temAporte.setSalarioIntegral(this.getSalarioIntegral());
        temAporte.setAporteObligatorio(this.getAporteObligatorio());
        temAporte.setValorSaldoAporte(this.getValorSaldoAporte());
        temAporte.setValorIntMoraDetalle(this.getValorIntMoraDetalle());
        temAporte.setCorrecciones(this.getCorrecciones());
        temAporte.setFechaProcesamiento(this.getFechaProcesamiento());
        temAporte.setEstadoAporteRecaudo(this.getEstadoAporteRecaudo());
        temAporte.setEstadoAporteAjuste(this.getEstadoAporteAjuste());
        temAporte.setEstadoRegistroAporte(this.getEstadoRegistroAporte());
        temAporte.setMarcaValRegistroAporte(this.getMarcaValRegistroAporte());
        temAporte.setEstadoValRegistroAporte(this.getEstadoValRegistroAporte());
        temAporte.setUsuarioAprobadorAporte(this.getUsuarioAprobadorAporte());
        temAporte.setPresentaNovedad(this.getPresentaNovedad());
        temAporte.setNumeroPlanillaManual(this.getNumeroPlanillaManual());
        temAporte.setEnProceso(this.getEnProceso());
        return temAporte;
    }
    
    /**
     * Método que convierte la entidad a su dto
     * @param temAporte
     */
    public void convertToDTO(TemAporte temAporte) {
        this.setId(temAporte.getId());
        this.setIdTransaccion(temAporte.getIdTransaccion());
        this.setMarcaAporteSimulado(temAporte.getMarcaAporteSimulado());
        this.setMarcaAporteManual(temAporte.getMarcaAporteManual());
        this.setRegistroGeneral(temAporte.getRegistroGeneral());
        this.setPeriodoAporte(temAporte.getPeriodoAporte());
        this.setValTotalApoObligatorio(temAporte.getValTotalApoObligatorio());
        this.setValorIntMoraGeneral(temAporte.getValorIntMoraGeneral());
        this.setFechaRecaudo(temAporte.getFechaRecaudo());
        this.setTipoIdAportante(temAporte.getTipoIdAportante());
        this.setNumeroIdAportante(temAporte.getNumeroIdAportante());
        this.setCodEntidadFinanciera(temAporte.getCodEntidadFinanciera());
        this.setOperadorInformacion(temAporte.getOperadorInformacion());
        this.setModalidadPlanilla(temAporte.getModalidadPlanilla());
        this.setModalidadRecaudoAporte(temAporte.getModalidadRecaudoAporte());
        this.setApoConDetalle(temAporte.getApoConDetalle());
        this.setFormaReconocimientoAporte(temAporte.getFormaReconocimientoAporte());
        this.setNumeroCuenta(temAporte.getNumeroCuenta());
        this.setRegistroDetallado(temAporte.getRegistroDetallado());
        this.setTipoIdCotizante(temAporte.getTipoIdCotizante());
        this.setNumeroIdCotizante(temAporte.getNumeroIdCotizante());
        this.setDiasCotizados(temAporte.getDiasCotizados());
        this.setHorasLaboradas(temAporte.getHorasLaboradas());
        this.setSalarioBasico(temAporte.getSalarioBasico());
        this.setMunicipioLaboral(temAporte.getMunicipioLaboral());
        this.setDepartamentoLaboral(temAporte.getDepartamentoLaboral());
        this.setValorIBC(temAporte.getValorIBC());
        this.setTarifa(temAporte.getTarifa());
        this.setSalarioIntegral(temAporte.getSalarioIntegral());
        this.setAporteObligatorio(temAporte.getAporteObligatorio());
        this.setValorSaldoAporte(temAporte.getValorSaldoAporte());
        this.setValorIntMoraDetalle(temAporte.getValorIntMoraDetalle());
        this.setCorrecciones(temAporte.getCorrecciones());
        this.setFechaProcesamiento(temAporte.getFechaProcesamiento());
        this.setEstadoAporteRecaudo(temAporte.getEstadoAporteRecaudo());
        this.setEstadoAporteAjuste(temAporte.getEstadoAporteAjuste());
        this.setEstadoRegistroAporte(temAporte.getEstadoRegistroAporte());
        this.setMarcaValRegistroAporte(temAporte.getMarcaValRegistroAporte());
        this.setEstadoValRegistroAporte(temAporte.getEstadoValRegistroAporte());
        this.setUsuarioAprobadorAporte(temAporte.getUsuarioAprobadorAporte());
        this.setPresentaNovedad(temAporte.getPresentaNovedad());
        this.setNumeroPlanillaManual(temAporte.getNumeroPlanillaManual());
        this.setEnProceso(temAporte.getEnProceso());
    }
 
    /**
     * Método que copia sus datos no nulos en la entidad enviada por parametro
     * @param temAporte
     * @return
     */
    public TemAporte copyDTOToEntiy(TemAporte temAporte) {
        if(this.getId() != null) { temAporte.setId(this.getId()); }
        if(this.getIdTransaccion() != null) { temAporte.setIdTransaccion(this.getIdTransaccion()); }
        if(this.getMarcaAporteSimulado() != null) { temAporte.setMarcaAporteSimulado(this.getMarcaAporteSimulado()); }
        if(this.getMarcaAporteManual() != null) { temAporte.setMarcaAporteManual(this.getMarcaAporteManual()); }
        if(this.getRegistroGeneral() != null) { temAporte.setRegistroGeneral(this.getRegistroGeneral()); }
        if(this.getPeriodoAporte() != null) { temAporte.setPeriodoAporte(this.getPeriodoAporte()); }
        if(this.getValTotalApoObligatorio() != null) { temAporte.setValTotalApoObligatorio(this.getValTotalApoObligatorio()); }
        if(this.getValorIntMoraGeneral() != null) { temAporte.setValorIntMoraGeneral(this.getValorIntMoraGeneral()); }
        if(this.getFechaRecaudo() != null) { temAporte.setFechaRecaudo(this.getFechaRecaudo()); }
        if(this.getTipoIdAportante() != null) { temAporte.setTipoIdAportante(this.getTipoIdAportante()); }
        if(this.getNumeroIdAportante() != null) { temAporte.setNumeroIdAportante(this.getNumeroIdAportante()); }
        if(this.getCodEntidadFinanciera() != null) { temAporte.setCodEntidadFinanciera(this.getCodEntidadFinanciera()); }
        if(this.getOperadorInformacion() != null) { temAporte.setOperadorInformacion(this.getOperadorInformacion()); }
        if(this.getModalidadPlanilla() != null) { temAporte.setModalidadPlanilla(this.getModalidadPlanilla()); }
        if(this.getModalidadRecaudoAporte() != null) { temAporte.setModalidadRecaudoAporte(this.getModalidadRecaudoAporte()); }
        if(this.getApoConDetalle() != null) { temAporte.setApoConDetalle(this.getApoConDetalle()); }
        if(this.getFormaReconocimientoAporte() != null) { temAporte.setFormaReconocimientoAporte(this.getFormaReconocimientoAporte()); }
        if(this.getNumeroCuenta() != null) { temAporte.setNumeroCuenta(this.getNumeroCuenta()); }
        if(this.getRegistroDetallado() != null) { temAporte.setRegistroDetallado(this.getRegistroDetallado()); }
        if(this.getTipoIdCotizante() != null) { temAporte.setTipoIdCotizante(this.getTipoIdCotizante()); }
        if(this.getNumeroIdCotizante() != null) { temAporte.setNumeroIdCotizante(this.getNumeroIdCotizante()); }
        if(this.getDiasCotizados() != null) { temAporte.setDiasCotizados(this.getDiasCotizados()); }
        if(this.getHorasLaboradas() != null) { temAporte.setHorasLaboradas(this.getHorasLaboradas()); }
        if(this.getSalarioBasico() != null) { temAporte.setSalarioBasico(this.getSalarioBasico()); }
        if(this.getMunicipioLaboral() != null) { temAporte.setMunicipioLaboral(this.getMunicipioLaboral()); }
        if(this.getDepartamentoLaboral() != null) { temAporte.setDepartamentoLaboral(this.getDepartamentoLaboral()); }
        if(this.getValorIBC() != null) { temAporte.setValorIBC(this.getValorIBC()); }
        if(this.getTarifa() != null) { temAporte.setTarifa(this.getTarifa()); }
        if(this.getSalarioIntegral() != null) { temAporte.setSalarioIntegral(this.getSalarioIntegral()); }
        if(this.getAporteObligatorio() != null) { temAporte.setAporteObligatorio(this.getAporteObligatorio()); }
        if(this.getValorSaldoAporte() != null) { temAporte.setValorSaldoAporte(this.getValorSaldoAporte()); }
        if(this.getValorIntMoraDetalle() != null) { temAporte.setValorIntMoraDetalle(this.getValorIntMoraDetalle()); }
        if(this.getCorrecciones() != null) { temAporte.setCorrecciones(this.getCorrecciones()); }
        if(this.getFechaProcesamiento() != null) { temAporte.setFechaProcesamiento(this.getFechaProcesamiento()); }
        if(this.getEstadoAporteRecaudo() != null) { temAporte.setEstadoAporteRecaudo(this.getEstadoAporteRecaudo()); }
        if(this.getEstadoAporteAjuste() != null) { temAporte.setEstadoAporteAjuste(this.getEstadoAporteAjuste()); }
        if(this.getEstadoRegistroAporte() != null) { temAporte.setEstadoRegistroAporte(this.getEstadoRegistroAporte()); }
        if(this.getMarcaValRegistroAporte() != null) { temAporte.setMarcaValRegistroAporte(this.getMarcaValRegistroAporte()); }
        if(this.getEstadoValRegistroAporte() != null) { temAporte.setEstadoValRegistroAporte(this.getEstadoValRegistroAporte()); }
        if(this.getUsuarioAprobadorAporte() != null) { temAporte.setUsuarioAprobadorAporte(this.getUsuarioAprobadorAporte()); }
        if(this.getPresentaNovedad() != null) { temAporte.setPresentaNovedad(this.getPresentaNovedad()); }
        if(this.getNumeroPlanillaManual() != null) { temAporte.setNumeroPlanillaManual(this.getNumeroPlanillaManual()); }
        if(this.getEnProceso() != null) { temAporte.setEnProceso(this.getEnProceso()); }
        return temAporte;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the idTransaccion
     */
    public Long getIdTransaccion() {
        return idTransaccion;
    }

    /**
     * @param idTransaccion the idTransaccion to set
     */
    public void setIdTransaccion(Long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    /**
     * @return the marcaAporteSimulado
     */
    public Boolean getMarcaAporteSimulado() {
        return marcaAporteSimulado;
    }

    /**
     * @param marcaAporteSimulado the marcaAporteSimulado to set
     */
    public void setMarcaAporteSimulado(Boolean marcaAporteSimulado) {
        this.marcaAporteSimulado = marcaAporteSimulado;
    }

    /**
     * @return the marcaAporteManual
     */
    public Boolean getMarcaAporteManual() {
        return marcaAporteManual;
    }

    /**
     * @param marcaAporteManual the marcaAporteManual to set
     */
    public void setMarcaAporteManual(Boolean marcaAporteManual) {
        this.marcaAporteManual = marcaAporteManual;
    }

    /**
     * @return the registroGeneral
     */
    public Long getRegistroGeneral() {
        return registroGeneral;
    }

    /**
     * @param registroGeneral the registroGeneral to set
     */
    public void setRegistroGeneral(Long registroGeneral) {
        this.registroGeneral = registroGeneral;
    }

    /**
     * @return the periodoAporte
     */
    public String getPeriodoAporte() {
        return periodoAporte;
    }

    /**
     * @param periodoAporte the periodoAporte to set
     */
    public void setPeriodoAporte(String periodoAporte) {
        this.periodoAporte = periodoAporte;
    }

    /**
     * @return the valTotalApoObligatorio
     */
    public BigDecimal getValTotalApoObligatorio() {
        return valTotalApoObligatorio;
    }

    /**
     * @param valTotalApoObligatorio the valTotalApoObligatorio to set
     */
    public void setValTotalApoObligatorio(BigDecimal valTotalApoObligatorio) {
        this.valTotalApoObligatorio = valTotalApoObligatorio;
    }

    /**
     * @return the valorIntMoraGeneral
     */
    public BigDecimal getValorIntMoraGeneral() {
        return valorIntMoraGeneral;
    }

    /**
     * @param valorIntMoraGeneral the valorIntMoraGeneral to set
     */
    public void setValorIntMoraGeneral(BigDecimal valorIntMoraGeneral) {
        this.valorIntMoraGeneral = valorIntMoraGeneral;
    }

    /**
     * @return the fechaRecaudo
     */
    public Date getFechaRecaudo() {
        return fechaRecaudo;
    }

    /**
     * @param fechaRecaudo the fechaRecaudo to set
     */
    public void setFechaRecaudo(Date fechaRecaudo) {
        this.fechaRecaudo = fechaRecaudo;
    }

    /**
     * @return the tipoIdAportante
     */
    public TipoIdentificacionEnum getTipoIdAportante() {
        return tipoIdAportante;
    }

    /**
     * @param tipoIdAportante the tipoIdAportante to set
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
     * @param numeroIdAportante the numeroIdAportante to set
     */
    public void setNumeroIdAportante(String numeroIdAportante) {
        this.numeroIdAportante = numeroIdAportante;
    }

    /**
     * @return the codEntidadFinanciera
     */
    public Short getCodEntidadFinanciera() {
        return codEntidadFinanciera;
    }

    /**
     * @param codEntidadFinanciera the codEntidadFinanciera to set
     */
    public void setCodEntidadFinanciera(Short codEntidadFinanciera) {
        this.codEntidadFinanciera = codEntidadFinanciera;
    }

    /**
     * @return the operadorInformacion
     */
    public Long getOperadorInformacion() {
        return operadorInformacion;
    }

    /**
     * @param operadorInformacion the operadorInformacion to set
     */
    public void setOperadorInformacion(Long operadorInformacion) {
        this.operadorInformacion = operadorInformacion;
    }

    /**
     * @return the modalidadPlanilla
     */
    public ModalidadPlanillaEnum getModalidadPlanilla() {
        return modalidadPlanilla;
    }

    /**
     * @param modalidadPlanilla the modalidadPlanilla to set
     */
    public void setModalidadPlanilla(ModalidadPlanillaEnum modalidadPlanilla) {
        this.modalidadPlanilla = modalidadPlanilla;
    }

    /**
     * @return the modalidadRecaudoAporte
     */
    public ModalidadRecaudoAporteEnum getModalidadRecaudoAporte() {
        return modalidadRecaudoAporte;
    }

    /**
     * @param modalidadRecaudoAporte the modalidadRecaudoAporte to set
     */
    public void setModalidadRecaudoAporte(ModalidadRecaudoAporteEnum modalidadRecaudoAporte) {
        this.modalidadRecaudoAporte = modalidadRecaudoAporte;
    }

    /**
     * @return the apoConDetalle
     */
    public Boolean getApoConDetalle() {
        return apoConDetalle;
    }

    /**
     * @param apoConDetalle the apoConDetalle to set
     */
    public void setApoConDetalle(Boolean apoConDetalle) {
        this.apoConDetalle = apoConDetalle;
    }

    /**
     * @return the formaReconocimientoAporte
     */
    public FormaReconocimientoAporteEnum getFormaReconocimientoAporte() {
        return formaReconocimientoAporte;
    }

    /**
     * @param formaReconocimientoAporte the formaReconocimientoAporte to set
     */
    public void setFormaReconocimientoAporte(FormaReconocimientoAporteEnum formaReconocimientoAporte) {
        this.formaReconocimientoAporte = formaReconocimientoAporte;
    }

    /**
     * @return the numeroCuenta
     */
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    /**
     * @param numeroCuenta the numeroCuenta to set
     */
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    /**
     * @return the registroDetallado
     */
    public Long getRegistroDetallado() {
        return registroDetallado;
    }

    /**
     * @param registroDetallado the registroDetallado to set
     */
    public void setRegistroDetallado(Long registroDetallado) {
        this.registroDetallado = registroDetallado;
    }

    /**
     * @return the tipoIdCotizante
     */
    public TipoIdentificacionEnum getTipoIdCotizante() {
        return tipoIdCotizante;
    }

    /**
     * @param tipoIdCotizante the tipoIdCotizante to set
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
     * @param numeroIdCotizante the numeroIdCotizante to set
     */
    public void setNumeroIdCotizante(String numeroIdCotizante) {
        this.numeroIdCotizante = numeroIdCotizante;
    }

    /**
     * @return the diasCotizados
     */
    public Short getDiasCotizados() {
        return diasCotizados;
    }

    /**
     * @param diasCotizados the diasCotizados to set
     */
    public void setDiasCotizados(Short diasCotizados) {
        this.diasCotizados = diasCotizados;
    }

    /**
     * @return the horasLaboradas
     */
    public Short getHorasLaboradas() {
        return horasLaboradas;
    }

    /**
     * @param horasLaboradas the horasLaboradas to set
     */
    public void setHorasLaboradas(Short horasLaboradas) {
        this.horasLaboradas = horasLaboradas;
    }

    /**
     * @return the salarioBasico
     */
    public BigDecimal getSalarioBasico() {
        return salarioBasico;
    }

    /**
     * @param salarioBasico the salarioBasico to set
     */
    public void setSalarioBasico(BigDecimal salarioBasico) {
        this.salarioBasico = salarioBasico;
    }

    /**
     * @return the municipioLaboral
     */
    public String getMunicipioLaboral() {
        return municipioLaboral;
    }

    /**
     * @param municipioLaboral the municipioLaboral to set
     */
    public void setMunicipioLaboral(String municipioLaboral) {
        this.municipioLaboral = municipioLaboral;
    }

    /**
     * @return the departamentoLaboral
     */
    public String getDepartamentoLaboral() {
        return departamentoLaboral;
    }

    /**
     * @param departamentoLaboral the departamentoLaboral to set
     */
    public void setDepartamentoLaboral(String departamentoLaboral) {
        this.departamentoLaboral = departamentoLaboral;
    }

    /**
     * @return the valorIBC
     */
    public BigDecimal getValorIBC() {
        return valorIBC;
    }

    /**
     * @param valorIBC the valorIBC to set
     */
    public void setValorIBC(BigDecimal valorIBC) {
        this.valorIBC = valorIBC;
    }

    /**
     * @return the tarifa
     */
    public BigDecimal getTarifa() {
        return tarifa;
    }

    /**
     * @param tarifa the tarifa to set
     */
    public void setTarifa(BigDecimal tarifa) {
        this.tarifa = tarifa;
    }

    /**
     * @return the salarioIntegral
     */
    public Boolean getSalarioIntegral() {
        return salarioIntegral;
    }

    /**
     * @param salarioIntegral the salarioIntegral to set
     */
    public void setSalarioIntegral(Boolean salarioIntegral) {
        this.salarioIntegral = salarioIntegral;
    }

    /**
     * @return the aporteObligatorio
     */
    public BigDecimal getAporteObligatorio() {
        return aporteObligatorio;
    }

    /**
     * @param aporteObligatorio the aporteObligatorio to set
     */
    public void setAporteObligatorio(BigDecimal aporteObligatorio) {
        this.aporteObligatorio = aporteObligatorio;
    }

    /**
     * @return the valorSaldoAporte
     */
    public BigDecimal getValorSaldoAporte() {
        return valorSaldoAporte;
    }

    /**
     * @param valorSaldoAporte the valorSaldoAporte to set
     */
    public void setValorSaldoAporte(BigDecimal valorSaldoAporte) {
        this.valorSaldoAporte = valorSaldoAporte;
    }

    /**
     * @return the valorIntMoraDetalle
     */
    public BigDecimal getValorIntMoraDetalle() {
        return valorIntMoraDetalle;
    }

    /**
     * @param valorIntMoraDetalle the valorIntMoraDetalle to set
     */
    public void setValorIntMoraDetalle(BigDecimal valorIntMoraDetalle) {
        this.valorIntMoraDetalle = valorIntMoraDetalle;
    }

    /**
     * @return the correcciones
     */
    public String getCorrecciones() {
        return correcciones;
    }

    /**
     * @param correcciones the correcciones to set
     */
    public void setCorrecciones(String correcciones) {
        this.correcciones = correcciones;
    }

    /**
     * @return the fechaProcesamiento
     */
    public Date getFechaProcesamiento() {
        return fechaProcesamiento;
    }

    /**
     * @param fechaProcesamiento the fechaProcesamiento to set
     */
    public void setFechaProcesamiento(Date fechaProcesamiento) {
        this.fechaProcesamiento = fechaProcesamiento;
    }

    /**
     * @return the estadoAporteRecaudo
     */
    public EstadoAporteEnum getEstadoAporteRecaudo() {
        return estadoAporteRecaudo;
    }

    /**
     * @param estadoAporteRecaudo the estadoAporteRecaudo to set
     */
    public void setEstadoAporteRecaudo(EstadoAporteEnum estadoAporteRecaudo) {
        this.estadoAporteRecaudo = estadoAporteRecaudo;
    }

    /**
     * @return the estadoAporteAjuste
     */
    public EstadoAporteEnum getEstadoAporteAjuste() {
        return estadoAporteAjuste;
    }

    /**
     * @param estadoAporteAjuste the estadoAporteAjuste to set
     */
    public void setEstadoAporteAjuste(EstadoAporteEnum estadoAporteAjuste) {
        this.estadoAporteAjuste = estadoAporteAjuste;
    }

    /**
     * @return the estadoRegistroAporte
     */
    public EstadoRegistroAporteEnum getEstadoRegistroAporte() {
        return estadoRegistroAporte;
    }

    /**
     * @param estadoRegistroAporte the estadoRegistroAporte to set
     */
    public void setEstadoRegistroAporte(EstadoRegistroAporteEnum estadoRegistroAporte) {
        this.estadoRegistroAporte = estadoRegistroAporte;
    }

    /**
     * @return the marcaValRegistroAporte
     */
    public MarcaRegistroAporteArchivoEnum getMarcaValRegistroAporte() {
        return marcaValRegistroAporte;
    }

    /**
     * @param marcaValRegistroAporte the marcaValRegistroAporte to set
     */
    public void setMarcaValRegistroAporte(MarcaRegistroAporteArchivoEnum marcaValRegistroAporte) {
        this.marcaValRegistroAporte = marcaValRegistroAporte;
    }

    /**
     * @return the estadoValRegistroAporte
     */
    public EstadoRegistroAportesArchivoEnum getEstadoValRegistroAporte() {
        return estadoValRegistroAporte;
    }

    /**
     * @param estadoValRegistroAporte the estadoValRegistroAporte to set
     */
    public void setEstadoValRegistroAporte(EstadoRegistroAportesArchivoEnum estadoValRegistroAporte) {
        this.estadoValRegistroAporte = estadoValRegistroAporte;
    }

    /**
     * @return the usuarioAprobadorAporte
     */
    public String getUsuarioAprobadorAporte() {
        return usuarioAprobadorAporte;
    }

    /**
     * @param usuarioAprobadorAporte the usuarioAprobadorAporte to set
     */
    public void setUsuarioAprobadorAporte(String usuarioAprobadorAporte) {
        this.usuarioAprobadorAporte = usuarioAprobadorAporte;
    }

    /**
     * @return the presentaNovedad
     */
    public Boolean getPresentaNovedad() {
        return presentaNovedad;
    }

    /**
     * @param presentaNovedad the presentaNovedad to set
     */
    public void setPresentaNovedad(Boolean presentaNovedad) {
        this.presentaNovedad = presentaNovedad;
    }

    /**
     * @return the numeroPlanillaManual
     */
    public String getNumeroPlanillaManual() {
        return numeroPlanillaManual;
    }

    /**
     * @param numeroPlanillaManual the numeroPlanillaManual to set
     */
    public void setNumeroPlanillaManual(String numeroPlanillaManual) {
        this.numeroPlanillaManual = numeroPlanillaManual;
    }

	/**
	 * @return the enProceso
	 */
	public Boolean getEnProceso() {
		return enProceso;
	}

	/**
	 * @param enProceso the enProceso to set
	 */
	public void setEnProceso(Boolean enProceso) {
		this.enProceso = enProceso;
	}    
}
