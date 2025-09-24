package com.asopagos.pila.composite.service.util;

import java.time.LocalDate;
import java.time.ZoneId;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.rest.exception.TechnicalException;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> abaquero</a>
 */

public class FuncionesUtilitarias {
    /** Referencia al logger */
    private static final ILogger logger = LogManager.getLogger(FuncionesUtilitarias.class);

    /**
     * Método para obtener una fecha en millisegundos a partir de un String
     * @param fechaCadena
     * @return <b>Long</b>
     */
    public static Long obtenerFechaMillis(String fechaCadena) {
        String firmaMetodo = "FuncionesUtilitarias.obtenerFechaMillis(String)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        try {
            int anio = Integer.parseInt(fechaCadena.split("-")[0]);
            int mes = Integer.parseInt(fechaCadena.split("-")[1]);
            int dia = Integer.parseInt(fechaCadena.split("-")[2]);

            LocalDate fecha = LocalDate.of(anio, mes, dia);

            logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
            return fecha.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        } catch (Exception e) {
            logger.error(ConstantesComunes.FIN_LOGGER_ERROR + firmaMetodo, e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
    }
}