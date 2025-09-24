package com.asopagos.database;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;

/**
 * <b>Descripcion:</b> Clase que gestiona los valores de secuencia para los guardados en batch<br/>
 * <b>Módulo:</b> Asopagos - HU Transversal<br/>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co"> jocorrea</a>
 */

public class SecuenciaUtil {

    /**
     * Procedimiento para la obtención de valores de una secuencia
     */
    private static final String PROCEDURE_USP_GET_GESTORVALORSECUENCIA = "dbo.USP_GET_GestorValorSecuencia";

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(SecuenciaUtil.class);

    /**
     * Evita la instanciación de la clase
     */
    private SecuenciaUtil() {
        super();
    }

    /**
     * Consulta el siguiente valor de la secuencia y actualiza el valor de acuerdo a la cantidad indicada
     * @param entityManager
     *        Contexto de persistencia para la consulta
     * @param nombreSecuencia
     *        Nombre de la secuencia a consultar
     * @param cantidad
     *        Cantidad de registros que se requieren de la consulta
     * @return Siguiente numero de la secuencia
     */
    public static Long getNextValue(EntityManager entityManager, String nombreSecuencia, Integer cantidad) {
        logger.debug("getNextValue(EntityManager, " + nombreSecuencia + ", " + cantidad + ")");

        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(PROCEDURE_USP_GET_GESTORVALORSECUENCIA);
        query.registerStoredProcedureParameter("iCantidadValores", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("sNombreSecuencia", String.class, ParameterMode.IN);
        query.setParameter("iCantidadValores", cantidad);
        query.setParameter("sNombreSecuencia", nombreSecuencia);

        query.registerStoredProcedureParameter("iPrimerValor", Long.class, ParameterMode.OUT);
        query.execute();

        return (Long) query.getOutputParameterValue("iPrimerValor");
    }
}
