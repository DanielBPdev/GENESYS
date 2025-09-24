package com.asopagos.pila.validadores.bloque4;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.asopagos.entidades.transversal.core.DiasFestivos;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.FuncionesValidador;
import co.com.heinsohn.lion.common.util.CalendarUtil;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación de fecha maxima minima<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorFechaPagoMaxMinCampo extends LineValidator {
    /**
     * Referencia al logger
     */

    private static final ILogger logger = LogManager.getLogger(ValidadorFechaPagoMaxMinCampo.class);

    /** Constante mensaje */
    private static final String FECHA_PAGO = "fecha de pago de la planilla";

    /**
     * Metodo se encarga de validar fecha maxima minima
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */

    @SuppressWarnings("unchecked")
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        logger.debug("Inicia validate(LineArgumentDTO)");

        String mensaje = null;

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = args.getLineValues();

        List<DiasFestivos> festivos = (List<DiasFestivos>) args.getContext().get(ConstantesContexto.FESTIVOS);

        Object objFechaPago = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_FECHA_PAGO));

        if (objFechaPago == null || !(objFechaPago instanceof Date)) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                    tipoError, nombreCampo, FECHA_PAGO);

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        Calendar fechaPago = CalendarUtil.toCalendar((Date) objFechaPago);

        Calendar fechaMinima = FuncionesValidador.modificarFecha(Calendar.getInstance(),
                Integer.parseInt(getParams().get(ConstantesParametroValidador.FECHA_ACTUAL_MENOS)) * -1, festivos);
        Calendar fechaMaxima = FuncionesValidador.modificarFecha(Calendar.getInstance(),
                Integer.parseInt(getParams().get(ConstantesParametroValidador.FECHA_ACTUAL_MAS)), festivos);

        String cadenaFecha = CalendarUtil.retornarFechaSinHorasMinutosSegundos(fechaPago.getTime());

        // se compara la fecha de pago con el mínimo y el máximo
        if (fechaPago.compareTo(fechaMinima) < 0) {

            mensaje = MensajesValidacionEnum.ERROR_CAMPO_ES_ANTERIOR_A_FECHA_ACTUAL_MENOS_DIAS_HABILES.getReadableMessage(idCampo,
                    cadenaFecha, tipoError, nombreCampo, cadenaFecha, getParams().get(ConstantesParametroValidador.FECHA_ACTUAL_MENOS),
                    CalendarUtil.retornarFechaSinHorasMinutosSegundos(fechaMinima.getTime()));

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        if (fechaPago.compareTo(fechaMaxima) > 0) {
            mensaje = MensajesValidacionEnum.ERROR_CAMPO_ES_POSTERIOR_A_FECHA_ACTUAL_MAS_DIAS_HABILES.getReadableMessage(idCampo,
                    cadenaFecha, tipoError, nombreCampo, CalendarUtil.retornarFechaSinHorasMinutosSegundos(fechaPago.getTime()),
                    getParams().get(ConstantesParametroValidador.FECHA_ACTUAL_MAS),
                    CalendarUtil.retornarFechaSinHorasMinutosSegundos(fechaMinima.getTime()));

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        logger.debug("Finaliza validate(LineArgumentDTO)");
    }

}
