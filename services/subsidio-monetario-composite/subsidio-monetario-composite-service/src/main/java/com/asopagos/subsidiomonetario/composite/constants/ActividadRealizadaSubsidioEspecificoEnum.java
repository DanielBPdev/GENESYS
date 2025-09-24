package com.asopagos.subsidiomonetario.composite.constants;

import javax.xml.bind.annotation.XmlEnum;

/**
 * Enumeración que lista las posibles actividades realizadas sobre una solicitud de liquidación de subsidio específico
 * HU: 317-141 Consultar trazabilidad de subsidio específico
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
@XmlEnum
public enum ActividadRealizadaSubsidioEspecificoEnum {

    INICIAR_VALIDACIONES("Iniciar validaciones para el pago de subsidios"),

    VALIDAR_SUBSIDIO("Validar que subsidio no cumple condiciones para pago"),

    APROBAR_LIQUIDACION_ANALISTA("Aprobar la liquidación - Analista"),

    RECHAZAR_LIQUIDACION_ANALISTA("Rechazar la liquidación - Analista"),

    EN_APROBACION_SUPERVISOR("Aprobar resultados de la liquidación"),

    APROBAR_LIQUIDACION_SUPERVISOR("Aprobar liquidación - Supervisor"),

    RECHAZAR_LIQUIDACION_SUPERVISOR("Rechazar liquidación - Supervisor"),
    
    DISPERSAR_A_MEDIOS_DE_PAGO("Dispersar a medios de pago de subsidio monetario"),

    CERRAR_SOLICITUD_RECHAZADA("Cerrar solicitud rechazada"),

    APROBAR_DISPERSION("Aprobar dispersión"),
    
    ESCALAR_LIQUIDACION("Escalar la liquidación"),
    
    ESPERAR_APORTE_OK("Esperar aporte OK");

    /**
     * Descripcion de la enumeración
     */
    private String descripcion;

    /**
     * Método constructor
     * @param descripcion
     */
    private ActividadRealizadaSubsidioEspecificoEnum(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }
}
