package com.asopagos.pila.validadores.bloque2;

import java.util.Map;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EtiquetaArchivoIEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.dto.ControlSubgrupoCorreccionDTO;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Esta clase realiza la validacion, correccion especial<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class ValidadorCorreccionesEspecial extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorCorreccionesEspecial.class);

    /** Constantes mensajes */
    private static final String TIPO_ID_ACTUAL = "Tipo de identificación del cotizante";
    private static final String NUM_ID_ACTUAL = "Número de identificación del cotizante";

    /**
     * Validador para el control de ordenamiento de líneas de archivos de corrección (recopilación de datos)
     * @param FieldArgumentDTO
     *        objeto con la informacion a valida
     * @exception FileProcessingException
     *            lanzada cuando hay un error
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        String firmaMetodo = "ValidadorCorreccionesEspecial.validate(LineArgumentDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String mensaje = null;

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = args.getLineValues();
        Object valorCampo = valoresDeLinea.get(EtiquetaArchivoIEnum.I229.getNombreCampo());

        if (valorCampo != null) {
            // se obtiene el contexto
            Map<String, Object> contexto = args.getContext();

            // se leen los campos de tipo y número ID de cotizante y campo de correcciones
            String correccion = valorCampo.toString();

            valorCampo = valoresDeLinea.get(EtiquetaArchivoIEnum.I23.getNombreCampo());

            if (valorCampo == null) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, TIPO_ID_ACTUAL);

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            TipoIdentificacionEnum tipoId = TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(valorCampo.toString());

            if (tipoId == null) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, TIPO_ID_ACTUAL);

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            valorCampo = valoresDeLinea.get(EtiquetaArchivoIEnum.I24.getNombreCampo());

            if (valorCampo == null) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, NUM_ID_ACTUAL);

                logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + " - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            String numId = valorCampo.toString();

            // se compone la clave actual
            String claveActual = tipoId + "-" + numId;

            // se carga el mapa de control de subgrupos del contexto
            Map<String, ControlSubgrupoCorreccionDTO> mapaControl = (Map<String, ControlSubgrupoCorreccionDTO>) contexto
                    .get(ConstantesContexto.LISTA_CONTROL_CORRECCIONES);

            ControlSubgrupoCorreccionDTO subGrupo = mapaControl.get(claveActual);

            // sí el subgrupo no existe, se inicializa
            if (subGrupo == null) {
                subGrupo = new ControlSubgrupoCorreccionDTO();
                subGrupo.setCantidadA(0);
                subGrupo.setCantidadC(0);
                subGrupo.setNumId(numId);
                subGrupo.setTipoId(tipoId);
                subGrupo.setPrimeraCorreccion(correccion);
                subGrupo.setLineaPrimera(args.getLineNumber());
                
                mapaControl.put(claveActual, subGrupo);
            }
            // se actualiza la última corrección y se agrega el número de línea
            subGrupo.setUltimaCorreccion(correccion);
            subGrupo.setLineaUltima(args.getLineNumber());
            subGrupo.getNumerosDeLinea().add(args.getLineNumber());

            // se incrementan los contadores para el subgrupo
            if (correccion != null && ConstantesComunesProcesamientoPILA.CORRECCIONES_C.equals(correccion)) {
                subGrupo.setCantidadC(subGrupo.getCantidadC() + 1);
            }
            else if (correccion != null && ConstantesComunesProcesamientoPILA.CORRECCIONES_A.equals(correccion)) {
                subGrupo.setCantidadA(subGrupo.getCantidadA() + 1);
            }

            // se actualiza el mapa de control en el contexto
            contexto.replace(ConstantesContexto.LISTA_CONTROL_CORRECCIONES, mapaControl);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

}