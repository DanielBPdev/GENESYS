package com.asopagos.pila.validadores.bloque2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.dto.ControlSubgrupoCorreccionDTO;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.FuncionesValidador;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * <b>Descripción:</b> Esta clase realiza la validacion, correccion final<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorCorreccionesFinal extends FieldValidator {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorCorreccionesFinal.class);

    /* Constantes para la adición de inconsistencias */
    private static final Integer INCONSISTENCIA_SALTO_LINEA = 1;
    private static final Integer INCONSISTENCIA_PRIMERA_CORRECCION = 2;
    private static final Integer INCONSISTENCIA_ULTIMA_CORRECCION = 3;
    private static final Integer INCONSISTENCIA_CANTIDAD_REGISTROS = 4;

    private String nombreCampo;
    private String tipoError;
    private String idCampo;
    private RespuestaValidacionDTO erroresResultado;

    /**
     * Validador para la revisión del orden de los registros de una planilla de corrección
     * @param FieldArgumentDTO
     *        objeto con la informacion a valida
     * @exception FileProcessingException
     *            lanzada cuando hay un error
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(FieldArgumentDTO arg0) throws FileProcessingException {
        String firmaMetodo = "ValidadorCorreccionesFinal.validate(FieldArgumentDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        Map<String, Object> contexto = arg0.getContext();

        // se carga el mapa de control de subgrupos del contexto
        Map<String, ControlSubgrupoCorreccionDTO> mapaControl = (Map<String, ControlSubgrupoCorreccionDTO>) contexto
                .get(ConstantesContexto.LISTA_CONTROL_CORRECCIONES);
        
        // se consulta el listado de las líneas vacías del archivo
        Set<Long> lineasVacias  = (Set<Long>) contexto.get(ConstantesContexto.LISTADO_LINEAS_VACIAS);
        if(lineasVacias == null){
            lineasVacias = Collections.emptySet();
        }

        // se consulta el listado de errores para agregar las inconsistencias que se encuentren
        erroresResultado = (RespuestaValidacionDTO) contexto.get(ConstantesContexto.RESULTADO_BLOQUE_VALIDACION_DTO);

        // se validan los subgrupos
        if (mapaControl != null && !mapaControl.isEmpty()) {
            nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
            tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
            idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

            for (ControlSubgrupoCorreccionDTO subgrupo : mapaControl.values()) {
                // se valida que todas las líneas estén en secuencia sin saltos
                validarSaltosDeLinea(subgrupo, lineasVacias);

                // se valida que inicie con un registro A y termine con un registro C
                if (!ConstantesComunesProcesamientoPILA.CORRECCIONES_A.equals(subgrupo.getPrimeraCorreccion())) {
                    agregarInconsistencia(INCONSISTENCIA_PRIMERA_CORRECCION, subgrupo, null);
                }
                if (!ConstantesComunesProcesamientoPILA.CORRECCIONES_C.equals(subgrupo.getUltimaCorreccion())) {
                    agregarInconsistencia(INCONSISTENCIA_ULTIMA_CORRECCION, subgrupo, null);
                }

                // Validación eliminada mediante el control de cambios 553
                // se valida que la cantidad de registros C sea mayor o igual a la cantidad de registros A
//                if (subgrupo.getCantidadA().compareTo(subgrupo.getCantidadC()) > 0) {
//                    agregarInconsistencia(INCONSISTENCIA_CANTIDAD_REGISTROS, subgrupo, null);
//                }
            }

            // se actualiza el mapa de errores
            if (erroresResultado != null && contexto.containsKey(ConstantesContexto.RESULTADO_BLOQUE_VALIDACION_DTO)) {
                contexto.replace(ConstantesContexto.RESULTADO_BLOQUE_VALIDACION_DTO, erroresResultado);
            }
            else if (erroresResultado != null) {
                contexto.put(ConstantesContexto.RESULTADO_BLOQUE_VALIDACION_DTO, erroresResultado);
            }
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * Método encargado de validar lso saltos en la secuencia de números de línea
     * @param subgrupo
     * @param lineasVacias 
     */
    private void validarSaltosDeLinea(ControlSubgrupoCorreccionDTO subgrupo, Set<Long> lineasVacias) {
        String firmaMetodo = "ValidadorCorreccionesFinal.validarSaltosDeLinea(ControlSubgrupoCorreccionDTO, Set<Long>)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        List<Long> numerosDeLinea = new ArrayList<>(subgrupo.getNumerosDeLinea());
        Collections.sort(numerosDeLinea);
        Long valorEvaluado = null;
        for (Long linea : numerosDeLinea) {
            if(valorEvaluado == null){
                valorEvaluado = linea;
            }else{
                valorEvaluado = siguienteEvaluado(valorEvaluado, lineasVacias);
            }
            
            if(linea.compareTo(valorEvaluado) != 0){
                agregarInconsistencia(INCONSISTENCIA_SALTO_LINEA, subgrupo, linea);
                valorEvaluado = null;
            }
        }
        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

    /**
     * @param valorEvaluado
     * @param lineasVacias 
     * @return
     */
    private Long siguienteEvaluado(Long valorEvaluado, Set<Long> lineasVacias) {
        Long valorEvaluadoTemp = valorEvaluado;
        
        do{
            valorEvaluadoTemp++;
        }while(lineasVacias.contains(valorEvaluadoTemp));
        
        return valorEvaluadoTemp;
    }

    /**
     * Método encargado de la adición de inconsistencias
     * @param inconsistenciaSaltoLinea
     * @param subgrupo
     * @param linea
     */
    private void agregarInconsistencia(Integer inconsistenciaSaltoLinea, ControlSubgrupoCorreccionDTO subgrupo, Long linea) {
        String firmaMetodo = "ValidadorCorreccionesFinal.agregarInconsistencia(Integer, ControlSubgrupoCorreccionDTO, Long)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);
        
        Long lineaTemp = linea;

        String mensaje = null;
        switch (inconsistenciaSaltoLinea) {
            case 1:
                mensaje = MensajesValidacionEnum.ERROR_CORRECCION_SUBGRUPO_SEPARADO.getReadableMessage(tipoError,
                        subgrupo.getTipoId().getDescripcion(), subgrupo.getNumId());
                break;
            case 2:
                mensaje = MensajesValidacionEnum.ERROR_CORRECCION_SUBGRUPO_PRIMERA_ULTIMA_CORRECCION.getReadableMessage(idCampo,
                        subgrupo.getPrimeraCorreccion(), tipoError, nombreCampo, "primer", subgrupo.getTipoId().getDescripcion(),
                        subgrupo.getNumId());
                
                lineaTemp = subgrupo.getLineaPrimera();
                break;
            case 3:
                mensaje = MensajesValidacionEnum.ERROR_CORRECCION_SUBGRUPO_PRIMERA_ULTIMA_CORRECCION.getReadableMessage(idCampo,
                        subgrupo.getUltimaCorreccion(), tipoError, nombreCampo, "último", subgrupo.getTipoId().getDescripcion(),
                        subgrupo.getNumId());
                
                lineaTemp = subgrupo.getLineaUltima();
                break;
            case 4:
                mensaje = MensajesValidacionEnum.ERROR_CORRECCION_SUBGRUPO_CANTIDAD_REGISTROS.getReadableMessage(tipoError,
                        subgrupo.getCantidadA().toString(), subgrupo.getCantidadC().toString(), subgrupo.getTipoId().getDescripcion(),
                        subgrupo.getNumId());
                break;
            default:
                break;

        }

        // sí el listado de errores aún no existe, se le crea
        if (erroresResultado == null) {
            erroresResultado = new RespuestaValidacionDTO();
        }

        // se añade la inconsistencia
        erroresResultado.addErrorDetalladoValidadorDTO(FuncionesValidador.prepararError(mensaje, BloqueValidacionEnum.BLOQUE_2_OI, lineaTemp));

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
}
