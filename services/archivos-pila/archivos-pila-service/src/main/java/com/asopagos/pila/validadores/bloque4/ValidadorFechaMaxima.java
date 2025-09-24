package com.asopagos.pila.validadores.bloque4;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.asopagos.entidades.transversal.core.DiasFestivos;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.FuncionesValidador;
import co.com.heinsohn.lion.common.util.CalendarUtil;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación de fecha maxima<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorFechaMaxima extends FieldValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorFechaMaxima.class);

    /** Constantes para mensajes */
    private static final String MODIFICADOR_FECHA = "modificador de la fecha";
    private static final String FECHA = "fecha a validar";

    /**
     * Metodo se encarga de validar fecha maxima
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(FieldArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicia validate(FieldArgumentDTO)");

        String mensaje = null;

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        if (arg0.getFieldValue() != null && !arg0.getFieldValue().toString().isEmpty()) {

            // se trae el listado de días festivos del contexto
            List<DiasFestivos> festivos = (List<DiasFestivos>) arg0.getContext().get(ConstantesContexto.FESTIVOS);
            Integer modificadorFecha = null;

            try {
                modificadorFecha = Integer.parseInt(getParams().get(ConstantesParametroValidador.MODIFICADOR_FECHA));
            } catch (Exception e) {
            }

            Calendar campoFecha = CalendarUtil.toCalendar((Date) arg0.getFieldValue());

            if (campoFecha == null) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                        tipoError, nombreCampo, FECHA);

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            if (modificadorFecha == null) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                        tipoError, nombreCampo, MODIFICADOR_FECHA);

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            Calendar fechaReferencia = FuncionesValidador.modificarFecha(Calendar.getInstance(), modificadorFecha, festivos);

            // se comparan las fechas
            if (campoFecha.compareTo(fechaReferencia) != 0) {
                String cadenaFecha = CalendarUtil.retornarFechaSinHorasMinutosSegundos(campoFecha.getTime());
                String cadenaFechaReferencia = CalendarUtil.retornarFechaSinHorasMinutosSegundos(fechaReferencia.getTime());

                mensaje = MensajesValidacionEnum.ERROR_CAMPO_DIFERENTE_FECHA_ACTUAL.getReadableMessage(idCampo, cadenaFecha, tipoError,
                        nombreCampo, cadenaFecha, modificadorFecha.toString(), cadenaFechaReferencia);

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }

        logger.debug("Finaliza validate(FieldArgumentDTO)");
    }

}
