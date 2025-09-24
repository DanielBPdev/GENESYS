/**
 * 
 */
package com.asopagos.novedades.fovis.composite.factories;

import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.fovis.ParametroFOVISEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.fovis.composite.service.ValidacionAutomaticaFovisCore;
import com.asopagos.novedades.fovis.convertidores.ValidarNovedadAutomaticaLevantarInhabilidadSancion;
import com.asopagos.novedades.fovis.convertidores.ValidarNovedadAutomaticaRechazoCambioAnoCalendario;
import com.asopagos.novedades.fovis.convertidores.ValidarNovedadAutomaticaSuspencionCambioAnoCalendario;
import com.asopagos.novedades.fovis.convertidores.ValidarNovedadAutomaticaVencimientoSubsidiosAsignados;

/**
 * <b>Descripción:</b> Fabrica para la construcción de validaciones masivas.
 * <b>Historia de Usuario:</b> HU 496
 * proceso 1.3
 * @author Edward Castaño <ecastano@heinsohn.com.co>
 */
public class ValidacionNovedadAutomaticaFactory {

    private final ILogger logger = LogManager.getLogger(ValidacionNovedadAutomaticaFactory.class);

    /**
     * Instancia Singleton de la clase.
     */
    private static ValidacionNovedadAutomaticaFactory instance;

    /**
     * Método que obtiene la instancia singleton de la clase NovedadAbstractFactory.
     * 
     * @return Instancia Singleton
     * @throws ClassNotFoundException
     *         excepcion lanzada en tiempo de ejecucion
     * @throws InstantiationException
     *         excepcion lanzada en tiempo de ejecucion
     * @throws IllegalAccessException
     *         excepcion lanzada en tiempo de ejecucion
     */
    public static ValidacionNovedadAutomaticaFactory getInstance()
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (instance == null) {
            instance = new ValidacionNovedadAutomaticaFactory();
        }
        return instance;
    }

    /**
     * Método que se encarga de obtener la validación respectiva.
     * @param tipoTransaccion
     *        tipo de transaccion procesada
     * @return factoty con la informacion requerida para procesar los registros de la novedad automatica
     * @throws ClassNotFoundException
     *         excepcion lanzada en tiempo de ejecucion
     * @throws InstantiationException
     *         excepcion lanzada en tiempo de ejecucion
     * @throws IllegalAccessException
     *         excepcion lanzada en tiempo de ejecucion
     */
    public ValidacionAutomaticaFovisCore obtenerServicioNovedad(TipoTransaccionEnum tipoTransaccion, ParametroFOVISEnum parametro)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        logger.debug("Inicia método ValidacionNovedadAutomaticaFactory.obtenerServicioNovedad()");
        ValidacionAutomaticaFovisCore servicio = null;

        switch (tipoTransaccion) {
            case SUSPENSION_CAMBIO_ANIO_CALENDARIO_AUTOMATICA:
                servicio = new ValidarNovedadAutomaticaSuspencionCambioAnoCalendario();
                break;
            case RECHAZO_SOLICITUDES_SUSPENDIDAS_CAMBIO_ANIO_AUTOMATICA:
                servicio = new ValidarNovedadAutomaticaRechazoCambioAnoCalendario();
                break;
            case VENCIMIENTO_SUBSIDIOS_ASIGNADOS_AUTOMATICA:
                servicio = new ValidarNovedadAutomaticaVencimientoSubsidiosAsignados();
                break;
            case LEVANTAR_INHABILIDAD_SANCION_AUTOMATICA:
                servicio = new ValidarNovedadAutomaticaLevantarInhabilidadSancion();
                break;
            default:
                break;

        }
        logger.debug("Inicia método ValidacionNovedadAutomaticaFactory.obtenerServicioNovedad ");
        return servicio;
    }
}
