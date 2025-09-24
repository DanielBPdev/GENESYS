package com.asopagos.pila.validadores.bloque2;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import com.asopagos.enumeraciones.aportes.ClaseAportanteEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.common.util.CalendarUtil;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación de restriccion de clase<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorRestriccionClaseAportanteD extends FieldValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorRestriccionClaseAportanteD.class);
    private static final String CLASE_APORTANTE = "clase aportante válida";

    /**
     * Metodo se encarga de validar restriccion de clase
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @Override
    public void validate(FieldArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicia validate(FieldArgumentDTO)");

        String mensaje = null;

        String claseAportante = (String) arg0.getContext().get(getParams().get(ConstantesParametroValidador.LLAVE_CLASE_APORTANTE));
        ClaseAportanteEnum claseAportanteEnum = ClaseAportanteEnum.obtenerClaseAportanteEnum(claseAportante);

        String periodoPago = (String) arg0.getContext().get(getParams().get(ConstantesParametroValidador.LLAVE_PERIODO_PAGO));
        Date fechaMatricula = (Date) arg0.getContext().get(getParams().get(ConstantesParametroValidador.LLAVE_FECHA_MATRICULA));

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        boolean error = false;

        if (claseAportanteEnum != null && ClaseAportanteEnum.CLASE_D.equals(claseAportanteEnum) && periodoPago != null
                && fechaMatricula != null) {
            Calendar fechaMatriculaCalendar = new GregorianCalendar();
            fechaMatriculaCalendar.setTimeInMillis(fechaMatricula.getTime());

            // primero reviso el período de pago vs la fecha de matrícula mercantíl
            try {
                Integer anioPeriodo = Integer.parseInt(periodoPago.split("-")[0]);
                Integer mesPeriodo = Integer.parseInt(periodoPago.split("-")[1]);

                if (anioPeriodo.compareTo(fechaMatriculaCalendar.get(Calendar.YEAR)) < 0) {
                    error = true;
                }
                else if (anioPeriodo.compareTo(fechaMatriculaCalendar.get(Calendar.YEAR)) == 0) {
                    if (mesPeriodo.compareTo(fechaMatriculaCalendar.get(Calendar.MONTH) + 1) < 0) {
                        error = true;
                    }
                }

                if (error) {
                    mensaje = MensajesValidacionEnum.ERROR_CAMPO_FECHA_MATRICULA_POSTERIOR_PERIODO_PAGO_PLANILLA.getReadableMessage(idCampo,
                            claseAportante, tipoError, nombreCampo, claseAportanteEnum.getDescripcion(),
                            CalendarUtil.retornarFechaSinHorasMinutosSegundos(fechaMatriculaCalendar.getTime()), periodoPago);

                    logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }

            } catch (NumberFormatException e) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                        tipoError, nombreCampo, CLASE_APORTANTE);

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            // finalmente se lee el resultado de la revisión de tarifas y novedades almacenada en el contexto
            if (!(boolean) arg0.getContext().get(ConstantesContexto.TARIFA_CERO_NOVEDAD)) {
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_VALIDO_EN_PLANILLA_COTIZANTES_TARIFA_4PCTO_O_0PCTO
                        .getReadableMessage(idCampo, claseAportante, tipoError, nombreCampo, claseAportante);

                logger.debug("Finaliza validate(FieldArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }
        logger.debug("Finaliza validate(FieldArgumentDTO)");
    }

}
