package com.asopagos.aportes.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import com.asopagos.entidades.ccf.aportes.AporteDetallado;
import com.asopagos.entidades.ccf.aportes.AporteGeneral;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:clmarin@heinsohn.com.co"> clmarin</a>
 */
@JsonInclude(Include.NON_EMPTY)
public class DetalleDatosCotizanteDTO implements Serializable {

    /**
     * Serial version ID
     */
    private static final long serialVersionUID = 2506454639394026487L;

    /**
     * Indica el tipo de solicitante que realizo el movimiento en aporte
     */
    private TipoSolicitanteMovimientoAporteEnum tipoAportante;
    
    /**
     * Tipo de cotizante.
     */
    private TipoAfiliadoEnum tipoCotizante;

    /**
     * Fecha de ingreso del aportante.
     */
    private String fechaIngreso;

    /**
     * Fecha de retiro del aportante.
     */
    private String fechaRetiro;

    /**
     * Estado del cotizante como afiliado.
     */
    private EstadoAfiliadoEnum estado;

    /**
     * Datos del aportante
     */
    private DatosAportanteDTO aportante;

    /**
     * Período de pago
     */
    private String periodoAporte;

    /**
     * Número de días cotizados
     */
    private Short diasCotizados;

    /**
     * Número de horas laboradas
     */
    private Short horasLaboradas;

    /**
     * Salario básico
     */
    private BigDecimal salarioBasico;

    /**
     * Ingreso Base Cotización (IBC)
     */
    private BigDecimal valorIBC;

    /**
     * Aporte obligatorio del aporte (Con Mora incluida)
     */
    private BigDecimal valorAporteTotal;

    /**
     * Tarifa
     */
    private BigDecimal tarifa;

    /**
     * Descripción del estado del registro a nivel de cotizante
     */
    private EstadoRegistroAporteEnum estadoPeriodo;
    
    /**
     * Fecha de procesamiento del aporte (Sistema al momento de relacionar o
     * registrar)
     */
    private String fechaProcesamiento;

    /**
     * Fecha de recaudo del aporte
     */
    private String fechaRecaudo;
    
    /**
     * Método constructor
     * 
     * @param aporteGeneral
     * @param aporteDetallado
     * @param aportante
     */
    public DetalleDatosCotizanteDTO(AporteGeneral aporteGeneral, AporteDetallado aporteDetallado, Persona aportante) {
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        this.setTipoCotizante(aporteDetallado.getTipoCotizante());
        DatosAportanteDTO datosAportante = new DatosAportanteDTO(aporteGeneral, aportante);
        this.setAportante(datosAportante);
        this.setTipoAportante(aporteGeneral.getTipoSolicitante());
        this.setPeriodoAporte(aporteGeneral.getPeriodoAporte());
        this.setDiasCotizados(aporteDetallado.getDiasCotizados());
        this.setHorasLaboradas(aporteDetallado.getHorasLaboradas());
        this.setSalarioBasico(aporteDetallado.getSalarioBasico());
        this.setValorIBC(aporteDetallado.getValorIBC());
        this.setValorAporteTotal(aporteGeneral.getValorTotalAporteObligatorio()
                .add(aporteGeneral.getValorInteresesMora() != null ? aporteGeneral.getValorInteresesMora() : BigDecimal.ZERO));
        this.setTarifa(aporteDetallado.getTarifa());
        this.setEstadoPeriodo(aporteDetallado.getEstadoRegistroAporteCotizante());
        this.setFechaProcesamiento(aporteGeneral.getFechaProcesamiento()!=null? format.format(aporteGeneral.getFechaProcesamiento()):null);
        this.setFechaRecaudo(aporteGeneral.getFechaRecaudo()!=null? format.format(aporteGeneral.getFechaRecaudo()):null);
//        this.setFechaProcesamiento(aporteGeneral.getFechaProcesamiento().getTime());
//        this.setFechaRecaudo(aporteGeneral.getFechaRecaudo().getTime());
    }

    /**
     * 
     */
    public DetalleDatosCotizanteDTO() {
    }

    /**
     * @return the tipoAportante
     */
    public TipoSolicitanteMovimientoAporteEnum getTipoAportante() {
        return tipoAportante;
    }

    /**
     * @param tipoAportante the tipoAportante to set
     */
    public void setTipoAportante(TipoSolicitanteMovimientoAporteEnum tipoAportante) {
        this.tipoAportante = tipoAportante;
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
     * @return the fechaIngreso
     */
    public String getFechaIngreso() {
        return fechaIngreso;
    }

    /**
     * @param fechaIngreso
     *        the fechaIngreso to set
     */
    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    /**
     * @return the fechaRetiro
     */
    public String getFechaRetiro() {
        return fechaRetiro;
    }

    /**
     * @param fechaRetiro
     *        the fechaRetiro to set
     */
    public void setFechaRetiro(String fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    /**
     * @return the estado
     */
    public EstadoAfiliadoEnum getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(EstadoAfiliadoEnum estado) {
        this.estado = estado;
    }

    /**
     * @return the aportante
     */
    public DatosAportanteDTO getAportante() {
        return aportante;
    }

    /**
     * @param aportante
     *        the aportante to set
     */
    public void setAportante(DatosAportanteDTO aportante) {
        this.aportante = aportante;
    }

    /**
     * @return the periodoAporte
     */
    public String getPeriodoAporte() {
        return periodoAporte;
    }

    /**
     * @param periodoAporte
     *        the periodoAporte to set
     */
    public void setPeriodoAporte(String periodoAporte) {
        this.periodoAporte = periodoAporte;
    }

    /**
     * @return the diasCotizados
     */
    public Short getDiasCotizados() {
        return diasCotizados;
    }

    /**
     * @param diasCotizados
     *        the diasCotizados to set
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
     * @param horasLaboradas
     *        the horasLaboradas to set
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
     * @param salarioBasico
     *        the salarioBasico to set
     */
    public void setSalarioBasico(BigDecimal salarioBasico) {
        this.salarioBasico = salarioBasico;
    }

    /**
     * @return the valorIBC
     */
    public BigDecimal getValorIBC() {
        return valorIBC;
    }

    /**
     * @param valorIBC
     *        the valorIBC to set
     */
    public void setValorIBC(BigDecimal valorIBC) {
        this.valorIBC = valorIBC;
    }

    /**
     * @return the valorAporteTotal
     */
    public BigDecimal getValorAporteTotal() {
        return valorAporteTotal;
    }

    /**
     * @param valorAporteTotal
     *        the valorAporteTotal to set
     */
    public void setValorAporteTotal(BigDecimal valorAporteTotal) {
        this.valorAporteTotal = valorAporteTotal;
    }

    /**
     * @return the tarifa
     */
    public BigDecimal getTarifa() {
        return tarifa;
    }

    /**
     * @param tarifa
     *        the tarifa to set
     */
    public void setTarifa(BigDecimal tarifa) {
        this.tarifa = tarifa;
    }

    /**
     * @return the estadoPeriodo
     */
    public EstadoRegistroAporteEnum getEstadoPeriodo() {
        return estadoPeriodo;
    }

    /**
     * @param estadoPeriodo
     *        the estadoPeriodo to set
     */
    public void setEstadoPeriodo(EstadoRegistroAporteEnum estadoPeriodo) {
        this.estadoPeriodo = estadoPeriodo;
    }

    /**
     * @return the fechaProcesamiento
     */
    public String getFechaProcesamiento() {
        return fechaProcesamiento;
    }

    /**
     * @param fechaProcesamiento the fechaProcesamiento to set
     */
    public void setFechaProcesamiento(String fechaProcesamiento) {
        this.fechaProcesamiento = fechaProcesamiento;
    }

    /**
     * @return the fechaRecaudo
     */
    public String getFechaRecaudo() {
        return fechaRecaudo;
    }

    /**
     * @param fechaRecaudo the fechaRecaudo to set
     */
    public void setFechaRecaudo(String fechaRecaudo) {
        this.fechaRecaudo = fechaRecaudo;
    }
    
}
