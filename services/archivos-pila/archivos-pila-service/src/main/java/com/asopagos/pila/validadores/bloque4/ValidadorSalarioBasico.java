package com.asopagos.pila.validadores.bloque4;

import java.math.BigDecimal;
import java.util.Map;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.pila.GrupoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación del salario basico <br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @version 1.0.0
 */
public class ValidadorSalarioBasico extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorSalarioBasico.class);

    /** Contantes para mensajes */
    private static final String TIPO_ARCHIVO = "tipo de archivo";
    private static final String TIPO_COTIZANTE = "tipo de cotizante";
    private static final String SALARIO = "valor de salario del cotizante";

    /**
     * Metodo se encarga de validar salario basico
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        logger.debug("Inicia validate(LineArgumentDTO)");
        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = args.getLineValues();

        String mensaje = null;

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        BigDecimal salario = (BigDecimal) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_SALARIO));
        
        BigDecimal ibc = (BigDecimal) valoresDeLinea.get("archivoIregistro2campo26"); //TODO GENERARERROR_O_Q

        BigDecimal salarioMinimo = (BigDecimal) args.getContext().get(ConstantesContexto.SALARIO_MINIMO);

        String tipoArchivo = (String) args.getContext().get(getParams().get(ConstantesParametroValidador.LLAVE_TIPO_ARCHIVO));
        TipoArchivoPilaEnum tipoArchivoEnum = TipoArchivoPilaEnum.obtenerTipoArchivoPilaEnum(tipoArchivo);

        if (tipoArchivoEnum == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                    tipoError, nombreCampo, TIPO_ARCHIVO);

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        if (salario == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                    tipoError, nombreCampo, SALARIO);

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        if (GrupoArchivoPilaEnum.APORTES_PENSIONADOS.equals(tipoArchivoEnum.getGrupo())) {
            // en los archivos de pensionados, sólo se verifica que el valor de la mesada sea mayor a cero

            if (salario == null || salario.compareTo(new BigDecimal(0)) <= 0) {
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_ES_VALIDO.getReadableMessage(idCampo, salario.toPlainString(), tipoError,
                        nombreCampo, salario.toPlainString());

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }
        else {
            Integer tipoCotizante = (Integer) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_TIPO_COTIZANTE));
            TipoCotizanteEnum tipoCotizanteEnum = TipoCotizanteEnum.obtenerTipoCotizante(tipoCotizante);

            if (tipoCotizanteEnum == null) {
                mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                        tipoError, nombreCampo, TIPO_COTIZANTE);

                logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }

            // se evaluán los casos en los cuales el salario básico es inferior a 1 SMLMV
            if (salario.compareTo(salarioMinimo) < 0) {
                // en este caso, sólo los cotizantes tipo 02 y 51 son válidos
                // adicionalemnte el cotizante tipo 04 (madre sustituta) no es validado
                if (!TipoCotizanteEnum.TIPO_COTIZANTE_SERVICIO_DOMESTICO.equals(tipoCotizanteEnum)
                        && !TipoCotizanteEnum.TIPO_COTIZANTE_MADRE_SUSTITUTA.equals(tipoCotizanteEnum)
                        && !TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL.equals(tipoCotizanteEnum)) {

                    mensaje = MensajesValidacionEnum.ERROR_CAMPO_SALARIO_INFERIOR_A_SMLMV_PARA_PERIODO.getReadableMessage(idCampo,
                            salario.toPlainString(), tipoError, nombreCampo, salario.toPlainString(), salarioMinimo.toString());

                    logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }
            }
            
         // se evaluán los casos en los cuales el salario básico es mayor al IBC
            if (salario.compareTo(ibc) > 0) {
            	if (!TipoCotizanteEnum.TIPO_COTIZANTE_SERVICIO_DOMESTICO.equals(tipoCotizanteEnum)
                        && !TipoCotizanteEnum.TIPO_COTIZANTE_MADRE_SUSTITUTA.equals(tipoCotizanteEnum)
                        && !TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL.equals(tipoCotizanteEnum)) {
                    mensaje = MensajesValidacionEnum.ERROR_CAMPO_SALARIO_MAYOR_A_IBC.getReadableMessage(idCampo,
                            salario.toPlainString(), tipoError, nombreCampo, salario.toPlainString(), ibc.toString());

                    logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);
            	}
            }
        }

        logger.debug("Finaliza validate(LineArgumentDTO)");
    }

}
