package com.asopagos.pila.validadores.bloque2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.FuncionesValidador;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación del campo valor aporte obligatorio<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorValorAporteObligatorio extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorValorAporteObligatorio.class);

    /** Constantes para mensajes */
    private static final String IBC = "valor de Ingreso Base de Cotización";
    private static final String TARIFA = "porcentaje de tarifa de aporte";
    private static final String REDONDEO = "parámetro de línea base para redondeo";
    
    private DecimalFormat df = new DecimalFormat();

    /**
     * Metodo se encarga de validar del campo valor aporte obligatorio
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @Override
    public void validate(LineArgumentDTO arg0) throws FileProcessingException {
        logger.debug("Inicia validate(LineArgumentDTO)");

        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(0);
        df.setGroupingUsed(false);

        String mensaje = null;

        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = arg0.getLineValues();
        
        Object valor = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_APORTE_OBLIGATORIO));
        
        BigDecimal aporteObligatorio = null;
        if(valor != null && !valor.toString().isEmpty()){
            aporteObligatorio = (BigDecimal) valor;
        }

        if (aporteObligatorio != null) {
            valor = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_IBC));
            BigDecimal ibc = null;
            if(valor != null && !valor.toString().isEmpty()){
                ibc = (BigDecimal) valor;
            }
            
            valor = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_TARIFA));
            BigDecimal tarifa = null;
            if(valor != null && !valor.toString().isEmpty()){
                tarifa = (BigDecimal) valor;
            }
            
            BigDecimal redondeo = null;
            if (getParams().get(ConstantesParametroValidador.REDONDEO) != null) {
                redondeo = new BigDecimal(getParams().get(ConstantesParametroValidador.REDONDEO));
            }

            BigDecimal aporteObligatorioCalculado = null;

            String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
            String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
            String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

            if (ibc == null) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                        tipoError, nombreCampo, IBC);

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            if (tarifa == null) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                        tipoError, nombreCampo, TARIFA);

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            if (redondeo == null) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                        tipoError, nombreCampo, REDONDEO);

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            BigDecimal tarifaDecimal = tarifa.multiply(new BigDecimal(100));

            aporteObligatorioCalculado = ibc.multiply(tarifaDecimal).divide(new BigDecimal(100), 0, RoundingMode.HALF_DOWN);

            // se aplica el redondeo
            aporteObligatorioCalculado = FuncionesValidador.redondearValor(aporteObligatorioCalculado, redondeo);

            // se comparan los aportes obligatorios
            if (aporteObligatorioCalculado.compareTo(aporteObligatorio) != 0) {
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_VALOR_DISTINTO_AL_ESPERADO_DE_ACUERDO_AL_IBC.getReadableMessage(idCampo,
                        df.format(aporteObligatorio), tipoError, nombreCampo, df.format(aporteObligatorio), df.format(ibc),
                        tarifaDecimal.toPlainString(), df.format(aporteObligatorioCalculado));

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }

        logger.debug("Finaliza validate(LineArgumentDTO)");
    }
}
