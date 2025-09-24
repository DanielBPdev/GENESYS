package com.asopagos.pila.validadores.bloque2;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.enumeraciones.aportes.ClaseAportanteEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.common.util.CalendarUtil;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Metodo se encarga de validar la fecha de matricula<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorFechaMatricula extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorFechaMatricula.class);

    /** Constantes para mensajes */
    private static final String FECHA_MATRICULA = "fecha de matrícula para aportante clase D";

    /**
     * Metodo se encarga de validar la fecha de matricula
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        String firmaMetodo = "validate(LineArgumentDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String mensaje = null;

        // Se obtienen los valores de la linea
        Map<String, Object> valoresDeLinea = args.getLineValues();
        Map<String, Object> contexto = args.getContext();

        String claseAportante = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_CLASE_APORTANTE));
        ClaseAportanteEnum claseAportanteEnum = ClaseAportanteEnum.obtenerClaseAportanteEnum(claseAportante);

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        if (claseAportanteEnum != null && ClaseAportanteEnum.CLASE_D.equals(claseAportanteEnum)) {
            try {
                Calendar fechaMatricula = Calendar.getInstance();
                Calendar fechaInicio1429 = Calendar.getInstance();
                Calendar fechaFin1429 = Calendar.getInstance();

                Object valor = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_MATRICULA));
                
                Date fechaMatriculaDate = null;
                if(valor != null && !valor.toString().isEmpty()){
                    fechaMatriculaDate = (Date) valor;
                }

                if (fechaMatriculaDate != null) {

                    fechaMatricula.setTimeInMillis(fechaMatriculaDate.getTime());
                    fechaInicio1429.setTimeInMillis(((Date) contexto.get(ConstantesContexto.INICIO_1429)).getTime());
                    fechaFin1429.setTimeInMillis(((Date) contexto.get(ConstantesContexto.FIN_1429)).getTime());
                    
                    fechaFin1429.set(Calendar.HOUR_OF_DAY, 23);
                    fechaFin1429.set(Calendar.MINUTE, 59);
                    fechaFin1429.set(Calendar.SECOND, 59);
                    fechaFin1429.set(Calendar.MILLISECOND, 999);

                    // sí la fecha de la matrícula es anterior al inicio de la Ley 1429
                    if (fechaMatricula.compareTo(fechaInicio1429) < 0) {
                        mensaje = " - " + MensajesValidacionEnum.ERROR_CAMPO_FECHA_ES_ANTERIOR_AL_INICIO_LEY_1429.getReadableMessage(
                                idCampo, CalendarUtil.retornarFechaSinHorasMinutosSegundos(fechaMatricula.getTime()), tipoError,
                                nombreCampo, CalendarUtil.retornarFechaSinHorasMinutosSegundos(fechaMatricula.getTime()));

                        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
                        throw new FileProcessingException(mensaje);
                    }

                    // sí la fecha de la matrícula es posterior al fin de la Ley 1429
                    if (fechaMatricula.compareTo(fechaFin1429) > 0) {
                        mensaje = " - " + MensajesValidacionEnum.ERROR_CAMPO_FECHA_ES_POSTERIOR_AL_FIN_LEY_1429.getReadableMessage(idCampo,
                                CalendarUtil.retornarFechaSinHorasMinutosSegundos(fechaMatricula.getTime()), tipoError, nombreCampo,
                                CalendarUtil.retornarFechaSinHorasMinutosSegundos(fechaMatricula.getTime()));

                        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo + mensaje);
                        throw new FileProcessingException(mensaje);
                    }
                }
            } catch (NullPointerException e) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                        tipoError, nombreCampo, FECHA_MATRICULA);

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }
}
