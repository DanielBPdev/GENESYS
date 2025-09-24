package com.asopagos.pila.validadores.bloque4;

import java.util.Calendar;
import java.util.GregorianCalendar;
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
 * <b>Descripción:</b> Clase que contiene la validación de fecha de pago maxima minima nombre <br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @version 1.0.0
 */
public class ValidadorFechaPagoMaxMinNombre extends FieldValidator {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorFechaPagoMaxMinNombre.class);

    /**
     * Metodo se encarga de validar fecha de pago maxima minima nombre
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(FieldArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicia validate(FieldArgumentDTO) - ");

        String mensaje = null;

        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String nombreCampo = ConstantesComunesProcesamientoPILA.NOMBRE_ARCHIVO_FECHA_RECAUDO;

        // se trae el listado de días festivos del contexto
        List<DiasFestivos> festivos = (List<DiasFestivos>) arg0.getContext().get(ConstantesContexto.FESTIVOS);

        // se trae el valor de parametro con la cantidad de días hábiles a sustraer para la fecha mínima
        Integer diasAtras = Integer.parseInt((String) arg0.getContext().get(ConstantesContexto.MINIMO_DIAS_PAGO));

        // se toma la fecha de pago del contexto
        Calendar fechaPago = null;

        // de acuerdo al tipo de objeto que sea la fecha en el contexto
        if (arg0.getContext().get(ConstantesContexto.NOMBRE_FECHA_PAGO) instanceof String) {
            // se convierte la fecha de pago de String a Calendar
            String fechaPagoString = (String) arg0.getContext().get(ConstantesContexto.NOMBRE_FECHA_PAGO);

            try {
                int anioPago = Integer.parseInt(fechaPagoString.split("-")[0]);
                int mesPago = Integer.parseInt(fechaPagoString.split("-")[1]) - 1;
                int diaPago = Integer.parseInt(fechaPagoString.split("-")[2]);

                fechaPago = new GregorianCalendar(anioPago, mesPago, diaPago);
            } catch (NumberFormatException e) {
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
        else if (arg0.getContext().get(ConstantesContexto.NOMBRE_FECHA_PAGO) instanceof Calendar) {
            fechaPago = (Calendar) arg0.getContext().get(ConstantesContexto.NOMBRE_FECHA_PAGO);
        }

        // con base en la fecha actual, se determinan las fechas máxima y mínima para el pago
        Calendar fechaMinima = FuncionesValidador.modificarFecha(Calendar.getInstance(), diasAtras * -1, festivos);
        Calendar fechaMaxima = FuncionesValidador.modificarFecha(Calendar.getInstance(), 1, festivos);

        String cadenaFechaPago = CalendarUtil.retornarFechaSinHorasMinutosSegundos(fechaPago.getTime());

        // comparo las fechas
        if (fechaPago.compareTo(fechaMinima) < 0) {
            mensaje = MensajesValidacionEnum.ERROR_FECHA_PAGO_APORTE_ANTERIOR_A_FECHA_ACTUAL.getReadableMessage(nombreCampo,
                    cadenaFechaPago, tipoError, cadenaFechaPago, diasAtras.toString(),
                    CalendarUtil.retornarFechaSinHorasMinutosSegundos(fechaMinima.getTime()));

            logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }
        else if (fechaPago.compareTo(fechaMaxima) > 0) {
            mensaje = MensajesValidacionEnum.ERROR_FECHA_PAGO_APORTE_POSTERIOR_A_FECHA_ACTUAL.getReadableMessage(nombreCampo,
                    cadenaFechaPago, tipoError, cadenaFechaPago, CalendarUtil.retornarFechaSinHorasMinutosSegundos(fechaMaxima.getTime()));

            logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        logger.debug("Finaliza validate(FieldArgumentDTO)");
    }
}
