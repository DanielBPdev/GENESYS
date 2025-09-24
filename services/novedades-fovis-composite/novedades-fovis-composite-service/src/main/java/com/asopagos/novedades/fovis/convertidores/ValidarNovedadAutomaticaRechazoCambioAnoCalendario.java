/**
 * 
 */
package com.asopagos.novedades.fovis.convertidores;

import java.util.List;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import com.asopagos.enumeraciones.fovis.ParametroFOVISEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.fovis.clients.ConsultarPostulacionesRechazoAutomatico;
import com.asopagos.novedades.fovis.composite.service.ValidacionAutomaticaFovisCore;
import com.asopagos.novedades.fovis.dto.DatosNovedadAutomaticaFovisDTO;
import com.asopagos.rest.exception.TechnicalException;

/**
 * Clase que contiene la lógica para validar la novedad Suspensión automática por cambio de año calendario
 * 
 * <b>Historia de Usuario:</b> HU 095 Registrar novedades automáticas FOVIS
 * proceso 3.2.5
 * @author Edward Castano <ecastano@heinsohn.com.co>
 *
 */
public class ValidarNovedadAutomaticaRechazoCambioAnoCalendario implements ValidacionAutomaticaFovisCore {

    private final ILogger logger = LogManager.getLogger(ValidarNovedadAutomaticaRechazoCambioAnoCalendario.class);

    /**
     * (non-Javadoc)
     * @see com.asopagos.novedades.fovis.composite.service.ValidacionAutomaticaFovisCore#validar()
     */
    @Override
    public DatosNovedadAutomaticaFovisDTO validar() {
        DatosNovedadAutomaticaFovisDTO datosNovedadAutomaticaDTO = null;
        try {
            logger.debug("Inicia método ValidarNovedadAutomaticaRechazoCambioAnoCalendario.validar()");
            //Se identifican las postulaciones que cumplen con la condicion para generar la novedad automatica de rechazo por cambio de año
            List<PostulacionFOVISModeloDTO> listaPostulacionesRechazo = consultarPostulacionesRechazoAutomatico();
            logger.info("listaPostulacionesRechazo.validar " + listaPostulacionesRechazo.size());
            if (listaPostulacionesRechazo != null && !listaPostulacionesRechazo.isEmpty()) {
                datosNovedadAutomaticaDTO = new DatosNovedadAutomaticaFovisDTO();
                datosNovedadAutomaticaDTO.setListaPostulaciones(listaPostulacionesRechazo);
            }

            logger.info("Finaliza método ValidarNovedadAutomaticaRechazoCambioAnoCalendario.validar()");
        } catch (Exception e) {
            logger.error("Ocurrio un error inesperado en ValidarNovedadAutomaticaRechazoCambioAnoCalendario.validar()", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        return datosNovedadAutomaticaDTO;
    }

    /**
     * Método que invoca el servicio que consulta las postulaciones en que cumplen con las condiciones de la novedad de rechazo automatico
     * por cambio de calendario
     * 
     * @return Objeto <code>List<PostulacionFOVISModeloDTO> </code> con la información de los postulaciones a procesar por la novedad
     */
    private List<PostulacionFOVISModeloDTO> consultarPostulacionesRechazoAutomatico() {
        logger.info("Inicia el método consultarPostulacionesRechazoAutomatico");
        ConsultarPostulacionesRechazoAutomatico service = new ConsultarPostulacionesRechazoAutomatico();
        service.execute();
        logger.info("Finaliza el método consultarPostulacionesRechazoAutomatico");
        return service.getResult();
    }

    /** (non-Javadoc)
     * @see com.asopagos.novedades.fovis.composite.service.ValidacionAutomaticaFovisCore#validar(com.asopagos.enumeraciones.fovis.ParametroFOVISEnum)
     */
    @Override
    public DatosNovedadAutomaticaFovisDTO validar(ParametroFOVISEnum parametro) {
        // TODO Auto-generated method stub
        return null;
    }

}
