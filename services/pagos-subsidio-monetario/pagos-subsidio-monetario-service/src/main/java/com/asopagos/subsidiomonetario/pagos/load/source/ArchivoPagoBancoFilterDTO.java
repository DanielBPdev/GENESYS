package com.asopagos.subsidiomonetario.pagos.load.source;

import java.util.List;
import com.asopagos.subsidiomonetario.pagos.constants.TipoArchivoPagoEnum;
import co.com.heinsohn.lion.filegenerator.dto.QueryFilterInDTO;

/**
 * <b>Descripcion:</b> Clase que se encarga de definir los atributos utilizados como filtros en la generación del archivo de pagos bancarios
 * <br/>
 * <b>Módulo:</b> Asopagos - HU 311-442, HU 311-441<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class ArchivoPagoBancoFilterDTO extends QueryFilterInDTO {

    private static final long serialVersionUID = 1L;

    /**
     * Número de radicación relacionado a la solicitud de liquidación
     */
    private String numeroRadicacion;

    /**
     * Tipo de archivo a generar (consignaciones o pagos judiciales)
     */
    private TipoArchivoPagoEnum tipoArchivoPago;

    /**
     * Lista que sirve para la generación del archivo apartir de los registros nuevos de transacciones
     * que se generan por medio de la HU-31-219
     */
    private List<Long> lstIdCuentasNuevas;

    /**
     * @return the numeroRadicacion
     */
    public String getNumeroRadicacion() {
        return numeroRadicacion;
    }

    /**
     * @param numeroRadicacion
     *        the numeroRadicacion to set
     */
    public void setNumeroRadicacion(String numeroRadicacion) {
        this.numeroRadicacion = numeroRadicacion;
    }

    /**
     * @return the tipoArchivoPago
     */
    public TipoArchivoPagoEnum getTipoArchivoPago() {
        return tipoArchivoPago;
    }

    /**
     * @param tipoArchivoPago
     *        the tipoArchivoPago to set
     */
    public void setTipoArchivoPago(TipoArchivoPagoEnum tipoArchivoPago) {
        this.tipoArchivoPago = tipoArchivoPago;
    }

    /**
     * @return the lstIdCuentasNuevas
     */
    public List<Long> getLstIdCuentasNuevas() {
        return lstIdCuentasNuevas;
    }

    /**
     * @param lstIdCuentasNuevas
     *        the lstIdCuentasNuevas to set
     */
    public void setLstIdCuentasNuevas(List<Long> lstIdCuentasNuevas) {
        this.lstIdCuentasNuevas = lstIdCuentasNuevas;
    }

}
