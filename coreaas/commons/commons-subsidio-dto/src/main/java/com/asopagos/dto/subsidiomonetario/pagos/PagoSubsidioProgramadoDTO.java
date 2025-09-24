package com.asopagos.dto.subsidiomonetario.pagos;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoSubsidioAsignadoEnum;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información de los apgos de subsidios monetarios programados<br/>
 * <b>Módulo:</b> Asopagos - HU-317-508<br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co">Miguel Angel Osorio</a>
 */
@XmlRootElement
public class PagoSubsidioProgramadoDTO implements Serializable {

    private static final long serialVersionUID = 5063990127892729607L;

    /**
     * Periodo para el cual se realizo la liquidación
     */
    private Date periodoLiquidacion;

    /**
     * Estado del pago programado (Pendiente)
     */
    private EstadoSubsidioAsignadoEnum estado;

    /**
     * Fecha en la cual se realizo la patrametrización del pago.
     */
    private Date fechaParametrizadaPago;

    /**
     * Fecha en la cual se realizo la programación del pago.
     */
    private Date fechaProgramadaPago;

    /**
     * @return the periodoLiquidacion
     */
    public Date getPeriodoLiquidacion() {
        return periodoLiquidacion;
    }

    /**
     * @return the estado
     */
    public EstadoSubsidioAsignadoEnum getEstado() {
        return estado;
    }

    /**
     * @return the fechaParametrizadaPago
     */
    public Date getFechaParametrizadaPago() {
        return fechaParametrizadaPago;
    }

    /**
     * @return the fechaProgramadaPago
     */
    public Date getFechaProgramadaPago() {
        return fechaProgramadaPago;
    }

    /**
     * @param periodoLiquidacion
     *        the periodoLiquidacion to set
     */
    public void setPeriodoLiquidacion(Date periodoLiquidacion) {
        this.periodoLiquidacion = periodoLiquidacion;
    }

    /**
     * @param estado
     *        the estado to set
     */
    public void setEstado(EstadoSubsidioAsignadoEnum estado) {
        this.estado = estado;
    }

    /**
     * @param fechaParametrizadaPago
     *        the fechaParametrizadaPago to set
     */
    public void setFechaParametrizadaPago(Date fechaParametrizadaPago) {
        this.fechaParametrizadaPago = fechaParametrizadaPago;
    }

    /**
     * @param fechaProgramadaPago
     *        the fechaProgramadaPago to set
     */
    public void setFechaProgramadaPago(Date fechaProgramadaPago) {
        this.fechaProgramadaPago = fechaProgramadaPago;
    }

}
