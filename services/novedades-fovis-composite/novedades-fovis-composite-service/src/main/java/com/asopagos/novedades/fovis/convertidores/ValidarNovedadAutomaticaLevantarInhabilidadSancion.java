/**
 * 
 */
package com.asopagos.novedades.fovis.convertidores;

import java.util.Date;
import java.util.List;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.dto.modelo.InhabilidadSubsidioFovisModeloDTO;
import com.asopagos.enumeraciones.fovis.ParametroFOVISEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.fovis.clients.ConsultarPersonasInhabilidadSubsidioFovisAutomatica;
import com.asopagos.novedades.fovis.composite.service.ValidacionAutomaticaFovisCore;
import com.asopagos.novedades.fovis.dto.DatosNovedadAutomaticaFovisDTO;
import com.asopagos.rest.exception.TechnicalException;

/**
 * Clase que contiene la lógica para validar la novedad automatica levantar inhabilidad sancion
 * 
 * <b>Historia de Usuario:</b> HU 095 Registrar novedades automáticas FOVIS
 * proceso 3.2.5
 * @author Edward Castano <ecastano@heinsohn.com.co>
 *
 */
public class ValidarNovedadAutomaticaLevantarInhabilidadSancion implements ValidacionAutomaticaFovisCore {

    private final ILogger logger = LogManager.getLogger(ValidarNovedadAutomaticaLevantarInhabilidadSancion.class);

    /**
     * (non-Javadoc)
     * @see com.asopagos.novedades.fovis.composite.service.ValidacionAutomaticaFovisCore#validar()
     */
    @Override
    public DatosNovedadAutomaticaFovisDTO validar() {
        DatosNovedadAutomaticaFovisDTO datosNovedadAutomaticaDTO = null;
        try {
            logger.debug("Inicia método ValidarNovedadAutomaticaLevantarInhabilidadSancion.validar()");
            //Se identifican las personas que cumplen con la condicion para generar la novedad automatica levantar inhabilidad sancion
            List<InhabilidadSubsidioFovisModeloDTO> listaInhabilidades = consultarPersonasInhabilidadSubsidioFovisAutomatica();
            if (listaInhabilidades != null && !listaInhabilidades.isEmpty()) {
                //Se levanta la inhabilidad de los hogares
                for (InhabilidadSubsidioFovisModeloDTO inhabilidad : listaInhabilidades) {
                    inhabilidad.setInhabilitadoParaSubsidio(Boolean.FALSE);
                    inhabilidad.setFechaFinInhabilidad((new Date()).getTime());
                }
                datosNovedadAutomaticaDTO = new DatosNovedadAutomaticaFovisDTO();
                datosNovedadAutomaticaDTO.setListaInhabilidades(listaInhabilidades);
            }

            logger.info("Finaliza método ValidarNovedadAutomaticaLevantarInhabilidadSancion.validar()");
        } catch (Exception e) {
            logger.error("Ocurrio un error inesperado en ValidarNovedadAutomaticaLevantarInhabilidadSancion.validar()", e);
            throw new TechnicalException(MensajesGeneralConstants.ERROR_TECNICO_INESPERADO, e);
        }
        return datosNovedadAutomaticaDTO;
    }

    /**
     * Método que invoca el servicio que consulta las inhabilidades y las personas para la generacion de la novedad de levantar inhabilidad
     * sancion
     * 
     * @return Objeto <code>List<InhabilidadSubsidioFovisModeloDTO> </code> con la información de los postulaciones a procesar por la
     *         novedad
     */
    private List<InhabilidadSubsidioFovisModeloDTO> consultarPersonasInhabilidadSubsidioFovisAutomatica() {
        logger.info("Inicia el método consultarPersonasInhabilidadSubsidioFovisAutomatica");
        ConsultarPersonasInhabilidadSubsidioFovisAutomatica service = new ConsultarPersonasInhabilidadSubsidioFovisAutomatica();
        service.execute();
        logger.info("Finaliza el método consultarPersonasInhabilidadSubsidioFovisAutomatica");
        return service.getResult();
    }

    /**
     * (non-Javadoc)
     * @see com.asopagos.novedades.fovis.composite.service.ValidacionAutomaticaFovisCore#validar(com.asopagos.enumeraciones.fovis.ParametroFOVISEnum)
     */
    @Override
    public DatosNovedadAutomaticaFovisDTO validar(ParametroFOVISEnum parametro) {
        // TODO Auto-generated method stub
        return null;
    }

}
