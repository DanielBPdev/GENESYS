package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.asopagos.entidades.subsidiomonetario.pagos.anibol.RegistroArchivoConsumosAnibol;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.EstadoRegistroArchivoConsumoAnibolEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.InconsistenciaResultadoValidacionArchivoConsumoAnibolEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.TipoRegistroArchivoConsumoAnibolEnum;

/**
 * <b>Descripcion:</b> DTO para registrar un candidato para retiro de tarjeta registrado
 * en el archivo de consumo de ANIBOL<br/>
 * <b>Módulo:</b> Asopagos - HU - 31 - ANEXO<br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */

public class TarjetaRetiroCandidatoDTO implements Serializable {

    private static final long serialVersionUID = 3477265321973257740L;

    /**
     * Tipo de registro que representa en el archivo de consumo
     */
    private TipoRegistroArchivoConsumoAnibolEnum tipoRegistroArchivoConsumo;

    /**
     * Bin (Identificador único de la Caja de Compensación)
     */
    private String binCCF;

    /**
     * Campo que identifica el numero de tarjeta que viene en el archivo de consumo.
     */
    private String numeroTarjeta;

    /**
     * Representa en el número nit de la empresa
     */
    private String nitEmpresa;

    /**
     * Cuenta relacionada a la empresa.
     */
    private String cuentaRelacionada;

    /**
     * Dispositivo Origen registrado en el archivo de consumo.
     */
    private String dispositivoOrigen;

    /**
     * Campo que identifica la Descripción del estado de cobro registrada en el archivo de consumo.
     */
    private String descripcionCobroSubsidio;

    /**
     * Campo que identifica la Descripción de la transacción registrada en el archivo de consumo.
     */
    private String descripcionTransaccion;

    /**
     * Valor de la transacción registrado en el archivo de consumo.
     */
    private BigDecimal valorTransaccion;

    /**
     * Valor dispensado registrado en el archivo de consumo.
     */
    private BigDecimal valorDispensando;

    /**
     * Fecha en la cual se realizo la transacción del retiro.
     */
    private String fechaTransaccion;

    /**
     * Campo que identifica el valor a cobrar registrado en el archivo de consumo.
     */
    private BigDecimal valorACobrar;

    /**
     * Valor de los impuestos registrados en el archivo de consumo.
     */
    private BigDecimal valorImpuestos;

    /**
     * Campo que identifica el total a cobrar.
     */
    private BigDecimal totalACobrar;

    /**
     * Campo que identifica el Impuesto de Emergencia económica registrado en el archivo de consumo.
     */
    private BigDecimal impuestoEmergenciaEconomica;

    /**
     * Campo que identifica el indicar de reverso (si es 1 es porque se debe realizar un reverso)
     */
    private Character indicadorReverso;

    /**
     * Campo que identifica la respuesta del autorizador registrado en el archivo de consumo.
     */
    private String respuestaAutorizador;

    /**
     * Campo que identifica la descripción de la respuesta registrada en el archivo de consumo.
     */
    private String descripcionRespuesta;

    /**
     * Código autorización registrado en el archivo de consumo.
     */
    private String codigoAutorizacion;

    /**
     * Subtipo registrado en el archivo de consumo.
     */
    private String subtipo;

    /**
     * Fecha autorizador registrada en el archivo de consumo.
     */
    private String fechaAutorizador;

    /**
     * Campo que identifica la hora del autorizador en el archivo de consumo.
     */
    private String horaAutorizador;

    /**
     * Campo que identifica la hora del dispositivo en el archivo de consumo.
     */
    private String horaDispositivo;

    /**
     * Número de referencia registrada en el archivo de consumo.
     */
    private String numeroReferencia;

    /**
     * Campo que identifica la red registrada en el archivo de consumo.
     */
    private String red;

    /**
     * Campo que identifica el Número de dispositivo registrado en el archivo de consumo.
     */
    private String numeroDispositivo;

    /**
     * Campo que identifica el Código del establecimiento registrado en el archivo de consumo.
     */
    private String codigoEstablecimiento;

    /**
     * Campo que identifica el Código de cuenta (Bolsillo DB) registrado en el archivo de consumo.
     */
    private String codigoCuenta;

    /**
     * Representa el estado del registro frente al procesamiento del archivo de
     * consumos
     */
    private EstadoRegistroArchivoConsumoAnibolEnum estadoRegistro;

    /**
     * Representa el tipo de inconsistencia
     */
    private InconsistenciaResultadoValidacionArchivoConsumoAnibolEnum tipoInconsistenciaResultadoValidacion;

    /**
     * Representa la Fecha de creacion del registro del archivo de consumo
     */
    private Date fechaHoraCreacion;

    /**
     * Representa la Fecha de inicio de ejecucion de las validaciones del registro del
     * archivo
     */
    private Date fechaHoraValidacion;

    /**
     * número de linea que genera el error para asociar el error en lion
     */
    private Long numeroLineaError;

    /**
     * Método que permite convertir el DTO en Entity
     * @return Entity <code>RegistroArchivoConsumosAnibol</code>
     */
    public RegistroArchivoConsumosAnibol convertToEntity() {

        RegistroArchivoConsumosAnibol registroArchivoConsumosAnibol = new RegistroArchivoConsumosAnibol();
        registroArchivoConsumosAnibol.setBinCCF(getBinCCF());
        registroArchivoConsumosAnibol.setNumeroTarjeta(getNumeroTarjeta());
        registroArchivoConsumosAnibol.setNitEmpresa(getNitEmpresa());
        registroArchivoConsumosAnibol.setCuentaRelacionada(getCuentaRelacionada());
        registroArchivoConsumosAnibol.setDispositivoOrigen(getDispositivoOrigen());
        registroArchivoConsumosAnibol.setDescripcionCobroSubsidio(getDescripcionCobroSubsidio());
        registroArchivoConsumosAnibol.setDescripcionTransaccion(getDescripcionTransaccion());
        registroArchivoConsumosAnibol.setValorTransaccion(getValorTransaccion());
        registroArchivoConsumosAnibol.setValorDispensado(getValorDispensando());
        registroArchivoConsumosAnibol.setFechaTransaccion(getFechaTransaccion());
        registroArchivoConsumosAnibol.setValorACobrar(getValorACobrar());
        registroArchivoConsumosAnibol.setValorImpuestos(getValorImpuestos());
        registroArchivoConsumosAnibol.setTotalACobrar(getTotalACobrar());
        registroArchivoConsumosAnibol.setImpuestoEmergenciaEconomica(getImpuestoEmergenciaEconomica());
        registroArchivoConsumosAnibol.setIndicadorReverso(getIndicadorReverso());
        registroArchivoConsumosAnibol.setRespuestaAutorizador(getRespuestaAutorizador());
        registroArchivoConsumosAnibol.setDescripcionRespuesta(getDescripcionRespuesta());
        registroArchivoConsumosAnibol.setCodigoAutorizacion(getCodigoAutorizacion());
        registroArchivoConsumosAnibol.setSubtipo(getSubtipo());
        registroArchivoConsumosAnibol.setFechaAutorizador(getFechaAutorizador());
        registroArchivoConsumosAnibol.setHoraAutorizador(getHoraAutorizador());
        registroArchivoConsumosAnibol.setHoraDispositivo(getHoraDispositivo());
        registroArchivoConsumosAnibol.setNumeroReferencia(getNumeroReferencia());
        registroArchivoConsumosAnibol.setRed(getRed());
        registroArchivoConsumosAnibol.setNumeroDispositivo(getNumeroDispositivo());
        registroArchivoConsumosAnibol.setCodigoEstablecimiento(getCodigoEstablecimiento().replaceFirst("^0+", ""));
        registroArchivoConsumosAnibol.setCodigoCuenta(getCodigoCuenta());
        registroArchivoConsumosAnibol.setEstadoRegistro(getEstadoRegistro());
        registroArchivoConsumosAnibol.setTipoInconsistenciaResultadoValidacion(getTipoInconsistenciaResultadoValidacion());
        registroArchivoConsumosAnibol.setFechaHoraCreacion(getFechaHoraCreacion());
        registroArchivoConsumosAnibol.setFechaHoraValidacion(new Date());
        registroArchivoConsumosAnibol.setTipoRegistroAchivoConsumo(getTipoRegistroArchivoConsumo());

        return registroArchivoConsumosAnibol;
    }

    /**
     * @return the cuentaRelacionada
     */
    public String getCuentaRelacionada() {
        return cuentaRelacionada;
    }

    /**
     * @param cuentaRelacionada
     *        the cuentaRelacionada to set
     */
    public void setCuentaRelacionada(String cuentaRelacionada) {
        this.cuentaRelacionada = cuentaRelacionada;
    }

    /**
     * @return the descripcionEstadoCobro
     */
    public String getDescripcionCobroSubsidio() {
        return descripcionCobroSubsidio;
    }

    /**
     * @param descripcionEstadoCobro
     *        the descripcionEstadoCobro to set
     */
    public void setDescripcionCobroSubsidio(String descripcionEstadoCobro) {
        this.descripcionCobroSubsidio = descripcionEstadoCobro;
    }

    /**
     * @return the descripcionTransaccion
     */
    public String getDescripcionTransaccion() {
        return descripcionTransaccion;
    }

    /**
     * @param descripcionTransaccion
     *        the descripcionTransaccion to set
     */
    public void setDescripcionTransaccion(String descripcionTransaccion) {
        this.descripcionTransaccion = descripcionTransaccion;
    }

    /**
     * @return the valorTransaccion
     */
    public BigDecimal getValorTransaccion() {
        return valorTransaccion;
    }

    /**
     * @param valorTransaccion
     *        the valorTransaccion to set
     */
    public void setValorTransaccion(BigDecimal valorTransaccion) {
        this.valorTransaccion = valorTransaccion;
    }

    /**
     * @return the valorDispensando
     */
    public BigDecimal getValorDispensando() {
        return valorDispensando;
    }

    /**
     * @param valorDispensando
     *        the valorDispensando to set
     */
    public void setValorDispensando(BigDecimal valorDispensando) {
        this.valorDispensando = valorDispensando;
    }

    /**
     * @return the valorImpuestos
     */
    public BigDecimal getValorImpuestos() {
        return valorImpuestos;
    }

    /**
     * @param valorImpuestos
     *        the valorImpuestos to set
     */
    public void setValorImpuestos(BigDecimal valorImpuestos) {
        this.valorImpuestos = valorImpuestos;
    }

    /**
     * @return the impuestoEmergenciaEconomica
     */
    public BigDecimal getImpuestoEmergenciaEconomica() {
        return impuestoEmergenciaEconomica;
    }

    /**
     * @param impuestoEmergenciaEconomica
     *        the impuestoEmergenciaEconomica to set
     */
    public void setImpuestoEmergenciaEconomica(BigDecimal impuestoEmergenciaEconomica) {
        this.impuestoEmergenciaEconomica = impuestoEmergenciaEconomica;
    }

    /**
     * @return the descripcionRespuesta
     */
    public String getDescripcionRespuesta() {
        return descripcionRespuesta;
    }

    /**
     * @param descripcionRespuesta
     *        the descripcionRespuesta to set
     */
    public void setDescripcionRespuesta(String descripcionRespuesta) {
        this.descripcionRespuesta = descripcionRespuesta;
    }

    /**
     * @return the horaAutorizador
     */
    public String getHoraAutorizador() {
        return horaAutorizador;
    }

    /**
     * @param horaAutorizador
     *        the horaAutorizador to set
     */
    public void setHoraAutorizador(String horaAutorizador) {
        this.horaAutorizador = horaAutorizador;
    }

    /**
     * @return the horaDispositivo
     */
    public String getHoraDispositivo() {
        return horaDispositivo;
    }

    /**
     * @param horaDispositivo
     *        the horaDispositivo to set
     */
    public void setHoraDispositivo(String horaDispositivo) {
        this.horaDispositivo = horaDispositivo;
    }

    /**
     * @return the numeroReferencia
     */
    public String getNumeroReferencia() {
        return numeroReferencia;
    }

    /**
     * @param numeroReferencia
     *        the numeroReferencia to set
     */
    public void setNumeroReferencia(String numeroReferencia) {
        this.numeroReferencia = numeroReferencia;
    }

    /**
     * @return the red
     */
    public String getRed() {
        return red;
    }

    /**
     * @param red
     *        the red to set
     */
    public void setRed(String red) {
        this.red = red;
    }

    /**
     * @return the numeroDispositivo
     */
    public String getNumeroDispositivo() {
        return numeroDispositivo;
    }

    /**
     * @param numeroDispositivo
     *        the numeroDispositivo to set
     */
    public void setNumeroDispositivo(String numeroDispositivo) {
        this.numeroDispositivo = numeroDispositivo;
    }

    /**
     * @return the binCCF
     */
    public String getBinCCF() {
        return binCCF;
    }

    /**
     * @param binCCF
     *        the binCCF to set
     */
    public void setBinCCF(String binCCF) {
        this.binCCF = binCCF;
    }

    /**
     * @return the numeroTarjeta
     */
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    /**
     * @param numeroTarjeta
     *        the numeroTarjeta to set
     */
    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    /**
     * @return the nitEmpresa
     */
    public String getNitEmpresa() {
        return nitEmpresa;
    }

    /**
     * @param nitEmpresa
     *        the nitEmpresa to set
     */
    public void setNitEmpresa(String nitEmpresa) {
        this.nitEmpresa = nitEmpresa;
    }

    /**
     * @return the dispositivoOrigen
     */
    public String getDispositivoOrigen() {
        return dispositivoOrigen;
    }

    /**
     * @param dispositivoOrigen
     *        the dispositivoOrigen to set
     */
    public void setDispositivoOrigen(String dispositivoOrigen) {
        this.dispositivoOrigen = dispositivoOrigen;
    }

    /**
     * @return the fechaTransaccion
     */
    public String getFechaTransaccion() {
        return fechaTransaccion;
    }

    /**
     * @param fechaTransaccion
     *        the fechaTransaccion to set
     */
    public void setFechaTransaccion(String fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    /**
     * @return the valorACobrar
     */
    public BigDecimal getValorACobrar() {
        return valorACobrar;
    }

    /**
     * @param valorACobrar
     *        the valorACobrar to set
     */
    public void setValorACobrar(BigDecimal valorACobrar) {
        this.valorACobrar = valorACobrar;
    }

    /**
     * @return the totalACobrar
     */
    public BigDecimal getTotalACobrar() {
        return totalACobrar;
    }

    /**
     * @param totalACobrar
     *        the totalACobrar to set
     */
    public void setTotalACobrar(BigDecimal totalACobrar) {
        this.totalACobrar = totalACobrar;
    }

    /**
     * @return the indicadorReverso
     */
    public Character getIndicadorReverso() {
        return indicadorReverso;
    }

    /**
     * @param indicadorReverso
     *        the indicadorReverso to set
     */
    public void setIndicadorReverso(Character indicadorReverso) {
        this.indicadorReverso = indicadorReverso;
    }

    /**
     * @return the respuestaAutorizador
     */
    public String getRespuestaAutorizador() {
        return respuestaAutorizador;
    }

    /**
     * @param respuestaAutorizador
     *        the respuestaAutorizador to set
     */
    public void setRespuestaAutorizador(String respuestaAutorizador) {
        this.respuestaAutorizador = respuestaAutorizador;
    }

    /**
     * @return the codigoAutorizacion
     */
    public String getCodigoAutorizacion() {
        return codigoAutorizacion;
    }

    /**
     * @param codigoAutorizacion
     *        the codigoAutorizacion to set
     */
    public void setCodigoAutorizacion(String codigoAutorizacion) {
        this.codigoAutorizacion = codigoAutorizacion;
    }

    /**
     * @return the subtipo
     */
    public String getSubtipo() {
        return subtipo;
    }

    /**
     * @param subtipo
     *        the subtipo to set
     */
    public void setSubtipo(String subtipo) {
        this.subtipo = subtipo;
    }

    /**
     * @return the fechaAutorizador
     */
    public String getFechaAutorizador() {
        return fechaAutorizador;
    }

    /**
     * @param fechaAutorizador
     *        the fechaAutorizador to set
     */
    public void setFechaAutorizador(String fechaAutorizador) {
        this.fechaAutorizador = fechaAutorizador;
    }

    /**
     * @return the codigoEstablecimiento
     */
    public String getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    /**
     * @param codigoEstablecimiento
     *        the codigoEstablecimiento to set
     */
    public void setCodigoEstablecimiento(String codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    /**
     * @return the codigoCuenta
     */
    public String getCodigoCuenta() {
        return codigoCuenta;
    }

    /**
     * @param codigoCuenta
     *        the codigoCuenta to set
     */
    public void setCodigoCuenta(String codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    /**
     * @return the estadoRegistro
     */
    public EstadoRegistroArchivoConsumoAnibolEnum getEstadoRegistro() {
        return estadoRegistro;
    }

    /**
     * @param estadoRegistro
     *        the estadoRegistro to set
     */
    public void setEstadoRegistro(EstadoRegistroArchivoConsumoAnibolEnum estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    /**
     * @return the tipoInconsistenciaResultadoValidacion
     */
    public InconsistenciaResultadoValidacionArchivoConsumoAnibolEnum getTipoInconsistenciaResultadoValidacion() {
        return tipoInconsistenciaResultadoValidacion;
    }

    /**
     * @param tipoInconsistenciaResultadoValidacion
     *        the tipoInconsistenciaResultadoValidacion to set
     */
    public void setTipoInconsistenciaResultadoValidacion(
            InconsistenciaResultadoValidacionArchivoConsumoAnibolEnum tipoInconsistenciaResultadoValidacion) {
        this.tipoInconsistenciaResultadoValidacion = tipoInconsistenciaResultadoValidacion;
    }

    /**
     * @return the tipoRegistroArchivoConsumo
     */
    public TipoRegistroArchivoConsumoAnibolEnum getTipoRegistroArchivoConsumo() {
        return tipoRegistroArchivoConsumo;
    }

    /**
     * @param tipoRegistroArchivoConsumo
     *        the tipoRegistroArchivoConsumo to set
     */
    public void setTipoRegistroArchivoConsumo(TipoRegistroArchivoConsumoAnibolEnum tipoRegistroArchivoConsumo) {
        this.tipoRegistroArchivoConsumo = tipoRegistroArchivoConsumo;
    }

    /**
     * @return the fechaHoraCreacion
     */
    public Date getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    /**
     * @param fechaHoraCreacion
     *        the fechaHoraCreacion to set
     */
    public void setFechaHoraCreacion(Date fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    /**
     * @return the fechaHoraValidacion
     */
    public Date getFechaHoraValidacion() {
        return fechaHoraValidacion;
    }

    /**
     * @param fechaHoraValidacion
     *        the fechaHoraValidacion to set
     */
    public void setFechaHoraValidacion(Date fechaHoraValidacion) {
        this.fechaHoraValidacion = fechaHoraValidacion;
    }

    /**
     * @return the numeroLinea
     */
    public Long getNumeroLineaError() {
        return numeroLineaError;
    }

    /**
     * @param numeroLinea
     *        the numeroLinea to set
     */
    public void setNumeroLineaError(Long numeroLinea) {
        this.numeroLineaError = numeroLinea;
    }

}
