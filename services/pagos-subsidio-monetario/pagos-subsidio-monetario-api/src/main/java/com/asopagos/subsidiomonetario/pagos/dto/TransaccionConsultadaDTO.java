package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoTransaccionSubsidioEnum;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoTransaccionSubsidioMonetarioEnum;

/**
 * <b>Descripcion:</b> Clase DTO que sirve para consultar las transacciones con
 * diferentes filtros.<br/>
 * <b>Módulo:</b> Asopagos - HU - 31 -201 <br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
@XmlRootElement
public class TransaccionConsultadaDTO implements Serializable {

    private static final long serialVersionUID = -1500701557506401547L;

    /**
     * Contiene el tipo de transaccion para realizar la consulta.
     */
    private TipoTransaccionSubsidioMonetarioEnum tipoTransaccion;

    /**
     * Contiene el estado de las transacciones seleccionado para realizar la consulta
     */
    private EstadoTransaccionSubsidioEnum estadoTransaccion;

    /**
     * Contiene el medio de pago seleccionado para realizar la consulta
     */
    private TipoMedioDePagoEnum medioDePago;

    /**
     * Filtro para la fecha de inicio de las transacciones
     */
    private Long fechaInicio;

    /**
     * Filtro para la fecha de fin de las transacciones
     */
    private Long fechaFin;

    /**
     * Filtro para los administradores de subsidio de las transacciones
     */
    private List<Long> listaIdAdminSubsidios;

    /**
     * Contiene Fecha del periodo liquidado para realizar la consulta.
     */
    private Date fechaPeriodoLiquidado;

    /**
     * Contiene identificador de subsidio asignado liquidado para realizar la consulta.
     */
    private Long idSubsidioAsignadoLiquidado;

    /**
     * @return the tipoTransaccion
     */
    public TipoTransaccionSubsidioMonetarioEnum getTipoTransaccion() {
        return tipoTransaccion;
    }

    /**
     * @param tipoTransaccion
     *        the tipoTransaccion to set
     */
    public void setTipoTransaccion(TipoTransaccionSubsidioMonetarioEnum tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    /**
     * @return the estadoTransaccion
     */
    public EstadoTransaccionSubsidioEnum getEstadoTransaccion() {
        return estadoTransaccion;
    }

    /**
     * @param estadoTransaccion
     *        the estadoTransaccion to set
     */
    public void setEstadoTransaccion(EstadoTransaccionSubsidioEnum estadoTransaccion) {
        this.estadoTransaccion = estadoTransaccion;
    }

    /**
     * @return the medioDePago
     */
    public TipoMedioDePagoEnum getMedioDePago() {
        return medioDePago;
    }

    /**
     * @param medioDePago
     *        the medioDePago to set
     */
    public void setMedioDePago(TipoMedioDePagoEnum medioDePago) {
        this.medioDePago = medioDePago;
    }

    /**
     * @return the fechaInicio
     */
    public Long getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio
     *        the fechaInicio to set
     */
    public void setFechaInicio(Long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Long getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin
     *        the fechaFin to set
     */
    public void setFechaFin(Long fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the listaIdAdminSubsidios
     */
    public List<Long> getListaIdAdminSubsidios() {
        return listaIdAdminSubsidios;
    }

    /**
     * @param listaIdAdminSubsidios
     *        the listaIdAdminSubsidios to set
     */
    public void setListaIdAdminSubsidios(List<Long> listaIdAdminSubsidios) {
        this.listaIdAdminSubsidios = listaIdAdminSubsidios;
    }

    /**
     * @return the fechaPeriodoLiquidado
     */
    public Date getFechaPeriodoLiquidado() {
        return fechaPeriodoLiquidado;
    }

    /**
     * @param fechaPeriodoLiquidado
     *        the fechaPeriodoLiquidado to set
     */
    public void setFechaPeriodoLiquidado(Date fechaPeriodoLiquidado) {
        this.fechaPeriodoLiquidado = fechaPeriodoLiquidado;
    }

    /**
     * @return the idSubsidioAsignadoLiquidado
     */
    public Long getIdSubsidioAsignadoLiquidado() {
        return idSubsidioAsignadoLiquidado;
    }

    /**
     * @param idSubsidioAsignadoLiquidado the idSubsidioAsignadoLiquidado to set
     */
    public void setIdSubsidioAsignadoLiquidado(Long idSubsidioAsignadoLiquidado) {
        this.idSubsidioAsignadoLiquidado = idSubsidioAsignadoLiquidado;
    }

}
