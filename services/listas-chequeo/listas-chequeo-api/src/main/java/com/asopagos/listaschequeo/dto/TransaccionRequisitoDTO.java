/**
 * 
 */
package com.asopagos.listaschequeo.dto;

import com.asopagos.dto.ElementoListaDTO;
import com.asopagos.entidades.transversal.personas.ISujetoTramite;
import com.asopagos.enumeraciones.core.ClasificacionEnum;

/**
 * Define la información de las transacciones de los procesos configurados para la lista de chequeo
 * @author <a href="mailto:ogiral@heinsohn.com.co">Leonardo Giral</a>
 *
 */
public class TransaccionRequisitoDTO extends ElementoListaDTO {

    /**
     * Default Serial version
     */
    private static final long serialVersionUID = 1L;

    /**
     * Indica si la transacción es aplicable a una clasificación específica o no
     */
    private boolean aplicableClasificacion;

    /**
     * Es la clasificación a la que aplica la transaccion en el caso de que sea una transacción granular.
     */
    private ClasificacionEnum clasificacionObjetivo;

    /**
     * Indica si el requisito es aplicable a una clasificación específica o no
     * @return <code>true</code> cuando la transacción aplica a una clasificación <code>false</code> cuando aplica a uno o varios sujeto
     *         trámite
     */
    public boolean isAplicableClasificacion() {
        return aplicableClasificacion;
    }

    /**
     * Establece si la transacción aplica a una clasificación o no
     * @param aplicableClasificacion
     */
    public void setAplicableClasificacion(boolean aplicableClasificacion) {
        this.aplicableClasificacion = aplicableClasificacion;
    }

    /**
     * Es la clasificación a la que aplica la transacción.
     * @return Clasificacion objetivo de la transacción. <code>null</code> en caso de que la transacción aplique a un sujeto trámite *
     *         {@link ISujetoTramite}
     */
    public ClasificacionEnum getClasificacionObjetivo() {
        return clasificacionObjetivo;
    }

    /**
     * Establece la clasificación para la que aplica la transacción
     * @param clasificacionObjetivo
     */
    public void setClasificacionObjetivo(ClasificacionEnum clasificacionObjetivo) {
        this.clasificacionObjetivo = clasificacionObjetivo;
    }

}
