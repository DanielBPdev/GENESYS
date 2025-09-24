package com.asopagos.subsidiomonetario.pagos.constants;

import javax.xml.bind.annotation.XmlEnum;

/**
 * Enumeración que lista los posibles tipos de archivos para consignaciones mediante el formato sudameris
 * HU: 317-266 Descargar archivo de personas sin derecho
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
@XmlEnum
public enum TipoArchivoPagoEnum {

    CONSIGNACIONES_BANCOS("Consignaciones a Bancos"),

    PAGOS_JUDICIALES("Consignaciones a Bancos por pagos judiciales");

    /**
     * Descripcion de la enumeración
     */
    private String descripcion;

    /**
     * Método constructor
     * @param descripcion
     */
    private TipoArchivoPagoEnum(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }
}
