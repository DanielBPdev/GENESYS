package com.asopagos.dto.subsidiomonetario.liquidacion;

import java.io.Serializable;
import java.util.List;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información de los descuentos realizados para un administrador en el proceso de liquidación
 * por fallecimiento
 * <b>Módulo:</b> Asopagos - HU-317-508 <br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class DispersionResultadoDescuentosFallecimientoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * DTO´s con la información de descuentos agrupada por entidad de descuento para un administrador
     */
    private List<DispersionResultadoMedioPagoFallecimientoDTO> detallesDescuentosDTO;

    /**
     * Lista de identificadores de condiciones de personas relacionadas a los descuentos
     */
    private List<Long> identificadoresCondiciones;

    /**
     * @return the detallesDescuentosDTO
     */
    public List<DispersionResultadoMedioPagoFallecimientoDTO> getDetallesDescuentosDTO() {
        return detallesDescuentosDTO;
    }

    /**
     * @param detallesDescuentosDTO
     *        the detallesDescuentosDTO to set
     */
    public void setDetallesDescuentosDTO(List<DispersionResultadoMedioPagoFallecimientoDTO> detallesDescuentosDTO) {
        this.detallesDescuentosDTO = detallesDescuentosDTO;
    }

    /**
     * @return the identificadoresCondiciones
     */
    public List<Long> getIdentificadoresCondiciones() {
        return identificadoresCondiciones;
    }

    /**
     * @param identificadoresCondiciones
     *        the identificadoresCondiciones to set
     */
    public void setIdentificadoresCondiciones(List<Long> identificadoresCondiciones) {
        this.identificadoresCondiciones = identificadoresCondiciones;
    }

}
