package com.asopagos.pila.validadores.bloque2;

import java.util.Date;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.enumeraciones.TipoPlanillaEnum;
import com.asopagos.enumeraciones.pila.BloqueValidacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.dto.RespuestaValidacionDTO;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.FuncionesValidador;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.FieldValidator;

/**
 * <b>Descripción:</b> Metodo se encarga de validar la presencia de una fecha de pago de planilla asociada y número de
 * planilla asociada<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class ValidadorFechaNumeroPlanillaRegistro4 extends FieldValidator {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorFechaNumeroPlanillaRegistro4.class);

    /**
     * Metodo se encarga de validar la presencia de una fecha de pago de planilla asociada y número de
     * planilla asociada
     * @param FieldArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error
     */
    @Override
    public void validate(FieldArgumentDTO arg0) throws FileProcessingException {
        String firmaMetodo = "ValidadorFechaNumeroPlanillaRegistro4.validate(FieldArgumentDTO arg0)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String mensaje = null;

        String nombreCampoPlanilla = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO_PLANILLA_ASOCIADA);
        String nombreCampoFecha = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO_FECHA_ASOCIADA);
        String idCampoPlanilla = getParams().get(ConstantesParametroValidador.ID_CAMPO_PLANILLA_ASOCIADA);
        String idCampoFecha = getParams().get(ConstantesParametroValidador.ID_CAMPO_FECHA_ASOCIADA);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);

        // se consultan el tipo de la planilla actual y el número y fecha de pago de planilla asociada del contexto
        Map<String, Object> contexto = arg0.getContext();
        
        // se inicializa la marca de presencia de registro tipo 4
        contexto.put(ConstantesContexto.HAY_REGISTRO_4, true);

        String tipoPlanilla = (String) contexto.get(ConstantesContexto.TIPO_PLANILLA);
        TipoPlanillaEnum tipoPlanillaEnum = TipoPlanillaEnum.obtenerTipoPlanilla(tipoPlanilla);

        if (tipoPlanillaEnum != null) {
            String numeroPlanilla = (String) contexto.get(ConstantesContexto.NUMERO_PLANILLA_ASOCIADA);
            Date fechaPago = (Date) contexto.get(ConstantesContexto.FECHA_PAGO_PLANILLA_ASOCIADA);

            // se consulta el listado de errores para agregar las inconsistencias que se encuentren
            RespuestaValidacionDTO erroresResultado = (RespuestaValidacionDTO) contexto
                    .get(ConstantesContexto.RESULTADO_BLOQUE_VALIDACION_DTO);

            // sí le listado de errores aún no existe, se le crea
            if (erroresResultado == null) {
                erroresResultado = new RespuestaValidacionDTO();
            }

            if (numeroPlanilla != null && (!StringUtils.isNumeric(numeroPlanilla) || Long.parseLong(numeroPlanilla) != 0)) {
            	//Para las planillas O no es necesario definir la planilla asociada
            	if(!TipoPlanillaEnum.OBLIGACIONES.equals(tipoPlanillaEnum) && !TipoPlanillaEnum.PAGO_TERCEROS_UGPP.equals(tipoPlanillaEnum)){
                    mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_EXIGE_QUE_SE_DEFINA_PLANILLA_ASOCIADA.getReadableMessage(idCampoPlanilla,
                            numeroPlanilla, tipoError, nombreCampoPlanilla, tipoPlanillaEnum.getDescripcion());

                    // se añade la inconsistencia
                    erroresResultado
                            .addErrorDetalladoValidadorDTO(FuncionesValidador.prepararError(mensaje, BloqueValidacionEnum.BLOQUE_2_OI, 1L));
            	}
            }

            if (fechaPago != null && !TipoPlanillaEnum.OBLIGACIONES.equals(tipoPlanillaEnum) && !TipoPlanillaEnum.PAGO_TERCEROS_UGPP.equals(tipoPlanillaEnum)) {
                mensaje = MensajesValidacionEnum.ERROR_NO_REQUIERE_FECHA_PAGO.getReadableMessage(idCampoFecha,
                        FuncionesValidador.formatoFecha(fechaPago), tipoError, nombreCampoFecha);

                // se añade la inconsistencia
                erroresResultado
                        .addErrorDetalladoValidadorDTO(FuncionesValidador.prepararError(mensaje, BloqueValidacionEnum.BLOQUE_2_OI, 1L));
            }

            // se actualiza el listado de errores en el contexto
            contexto.replace(ConstantesContexto.RESULTADO_BLOQUE_VALIDACION_DTO, erroresResultado);
        }

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

}
