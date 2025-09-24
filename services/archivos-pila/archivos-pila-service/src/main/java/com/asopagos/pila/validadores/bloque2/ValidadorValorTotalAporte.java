package com.asopagos.pila.validadores.bloque2;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;
import com.asopagos.enumeraciones.pila.GrupoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.constants.MessagesConstants;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación del campo valor total del aporte<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorValorTotalAporte extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorValorTotalAporte.class);

    private DecimalFormat df = new DecimalFormat();

    /**
     * Metodo se encarga de validar del campo valor total del aporte
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        logger.debug("Inicia validate(LineArgumentDTO)");
        String mensaje = null;

        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(0);
        df.setGroupingUsed(false);

        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = args.getLineValues();

        Object valor = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_VALOR_TOTAL));
        BigDecimal valorTotal = null;
        if (valor != null && !valor.toString().isEmpty()) {
            valorTotal = (BigDecimal) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_VALOR_TOTAL));
        }

        String tipoArchivo = (String) args.getContext().get(getParams().get(ConstantesParametroValidador.LLAVE_TIPO_ARCHIVO));
        TipoArchivoPilaEnum tipoArchivoEnum = TipoArchivoPilaEnum.obtenerTipoArchivoPilaEnum(tipoArchivo);

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        BigDecimal valorAporte = null;
        BigDecimal valorMora = null;

        if (valorTotal != null) {
            if (tipoArchivoEnum != null && !GrupoArchivoPilaEnum.APORTES_PENSIONADOS.equals(tipoArchivoEnum.getGrupo())) {
                // Los archivos de dependientes e independientes traen los valores de aporte y mora en líneas diferentes
                // por esta razón se leen desde el contexto

                valorAporte = (BigDecimal) args.getContext().get(getParams().get(ConstantesParametroValidador.LLAVE_VALOR_APORTE));
                valorMora = (BigDecimal) args.getContext().get(getParams().get(ConstantesParametroValidador.LLAVE_VALOR_MORA));
            }
            else if (tipoArchivoEnum != null && GrupoArchivoPilaEnum.APORTES_PENSIONADOS.equals(tipoArchivoEnum.getGrupo())) {
                // Los archivos de pensionados, tienen todos los valores en la misma línea, por lo cual se leen directamente
                valor = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.LLAVE_VALOR_APORTE));
                if (valor != null && !valor.toString().isEmpty()) {
                    valorAporte = (BigDecimal) valor;
                }

                valor = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.LLAVE_VALOR_MORA));
                if (valor != null && !valor.toString().isEmpty()) {
                    valorMora = (BigDecimal) valor;
                }
            }

            if (valorAporte == null) {
                // Error por falta de valores de aporte
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo,
                        MessagesConstants.VALOR_APORTE_PLANILLA);

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            if (valorMora == null) {
                // Error por falta de valores de mora
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                        ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo,
                        MessagesConstants.VALOR_MORA_PLANILLA);

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            if (valorTotal.compareTo(valorAporte.add(valorMora)) != 0) {
                // Error por diferencia en los valores
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_COINCIDE_CON_VALOR_APORTE_MAS_MORA.getReadableMessage(idCampo,
                        df.format(valorTotal), tipoError, nombreCampo, df.format(valorTotal), df.format(valorAporte.add(valorMora)));

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }
        logger.debug("Finaliza validate(LineArgumentDTO)");
    }

}
