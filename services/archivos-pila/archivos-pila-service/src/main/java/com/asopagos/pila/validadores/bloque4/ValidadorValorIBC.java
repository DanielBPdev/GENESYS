package com.asopagos.pila.validadores.bloque4;

import java.math.BigDecimal;
import java.util.Map;
import com.asopagos.enumeraciones.aportes.TipoCotizanteEnum;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesComunesProcesamientoPILA;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.FuncionesValidador;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación del IBC <br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @version 1.0.0
 */
public class ValidadorValorIBC extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorValorIBC.class);

    /** Constantes para mensajes */
    private static final String IBC = "valor de Ingreso Base de Cotización";
    private static final String SALARIO = "valor del salario del cotizante";
    private static final String NOVEDADES = "listado de novedades a considerar para el IBC";
    private static final String TIPO_ARCHIVO = "tipo de archivo";

    /**
     * Metodo se encarga de validar el IBC
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        logger.debug("Inicia validate(LineArgumentDTO)");

        String mensaje = null;

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = args.getLineValues();

        BigDecimal ibc = (BigDecimal) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_IBC));
        BigDecimal salario = (BigDecimal) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_SALARIO));

        String[] camposNovedades = null;
        String novedades = getParams().get(ConstantesParametroValidador.CAMPOS_NOVEDADES);

        String tipoArchivo = (String) args.getContext().get(getParams().get(ConstantesParametroValidador.LLAVE_TIPO_ARCHIVO));
        TipoArchivoPilaEnum tipoArchivoPilaEnum = TipoArchivoPilaEnum.obtenerTipoArchivoPilaEnum(tipoArchivo);

        Integer tipoCotizante = (Integer) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_TIPO_COTIZANTE));
        TipoCotizanteEnum tipoCotizanteEnum = TipoCotizanteEnum.obtenerTipoCotizante(tipoCotizante);

        String esSalarioIntegral = (String) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_SALARIO_INTEGRAL));
        if (esSalarioIntegral == null)
            esSalarioIntegral = "";

        if (novedades != null) {
            camposNovedades = novedades.split(",");
        }

        if (ibc == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                    tipoError, nombreCampo, IBC);

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        if (salario == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                    tipoError, nombreCampo, SALARIO);

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        if (camposNovedades == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                    tipoError, nombreCampo, NOVEDADES);

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        if (tipoArchivoPilaEnum == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo, ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO,
                    tipoError, nombreCampo, TIPO_ARCHIVO);

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        // se comprueba sí se han presentado novedades 
        boolean hayNovedades = false;

        for (int i = 0; i < camposNovedades.length; i++) {
            String registro = camposNovedades[i].split("-")[0];
            String campo = camposNovedades[i].split("-")[1];

            Object novedad = valoresDeLinea.get(FuncionesValidador.getNombreCampo(tipoArchivoPilaEnum, registro, campo));

            if (novedad != null && novedad instanceof String) {
                // sí el campo String tiene valor, representa una novedad marcada
                hayNovedades = true;
                break;
            }
            else if (novedad != null && novedad instanceof Integer) {
                try {
                    if (!((Integer) novedad).equals(0)) {
                        // el campo de novedad IRL, siempre tiene valor, en este caso se debe comparar con 0
                        hayNovedades = true;
                        break;
                    }
                } catch (Exception e) {
                }
            }
        }

        // cuando el IBC es cero sin novedades
        if (ibc.compareTo(new BigDecimal(0)) == 0 && !hayNovedades) {
            mensaje = MensajesValidacionEnum.ERROR_CAMPO_NO_VALIDO_SIN_HABERSE_MARCADO_NOVEDAD.getReadableMessage(idCampo,
                    ibc.toPlainString(), tipoError, nombreCampo, ibc.toPlainString());

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }
        else {
            // el IBC es diferente a cero

            BigDecimal tope70 = salario.multiply(new BigDecimal(70)).divide(new BigDecimal(100));

            // se evaluán por aparte los cotizantes tipo 02 y 51
            if (TipoCotizanteEnum.TIPO_COTIZANTE_SERVICIO_DOMESTICO.equals(tipoCotizanteEnum)
                    || TipoCotizanteEnum.TIPO_COTIZANTE_TRABAJADOR_TIEMPO_PARCIAL.equals(tipoCotizanteEnum)) {

                // para el tipo de cotizante 02 y 51, el IBC se establece como un % sobre el SMLMV de acuerdo a los días cotizados
                Integer diasCotizados = (Integer) valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_DIAS_COTIZADOS));
                BigDecimal salarioMinimo = (BigDecimal) args.getContext().get(ConstantesContexto.SALARIO_MINIMO);
                BigDecimal ibcComparacion = salarioMinimo;

                // 7 días o menos
                if (diasCotizados.compareTo(7) <= 0) {
                    ibcComparacion = salarioMinimo.multiply(new BigDecimal(25)).divide(new BigDecimal(100));
                }
                else if (diasCotizados.compareTo(14) <= 0) {
                    // 14 días o menos
                    ibcComparacion = salarioMinimo.multiply(new BigDecimal(50)).divide(new BigDecimal(100));
                }
                else if (diasCotizados.compareTo(21) <= 0) {
                    // 21 días o menos
                    ibcComparacion = salarioMinimo.multiply(new BigDecimal(75)).divide(new BigDecimal(100));
                }
                else if (diasCotizados.compareTo(30) == 0) {
                    mensaje = MensajesValidacionEnum.ERROR_CAMPO_VALOR_IBC_REPORTADO_30_DIAS_PARA_COTIZANTE.getReadableMessage(idCampo,
                            ibc.toPlainString(), tipoError, nombreCampo, ibc.toPlainString(), tipoCotizanteEnum.getDescripcion());

                    logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }

                // se compara el IBC en el registro contra el IBC calculado
                if (ibc.compareTo(ibcComparacion) != 0) {
                    mensaje = MensajesValidacionEnum.ERROR_CAMPO_VALOR_IBC_NO_CORRESPONDE_AL_VALOR_DE_DIAS_COTIZADOS.getReadableMessage(
                            idCampo, ibc.toPlainString(), tipoError, nombreCampo, ibc.toPlainString(), ibcComparacion.toPlainString());

                    logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }
            }
            else if (tipoCotizante != null && !tipoCotizante.equals(4)) {
                // sí se ha marcado salario integral sin novedades, el IBC no debe superar al 70% del salario
                if (esSalarioIntegral.equals("X") && !hayNovedades && ibc.compareTo(tope70) > 0) {
                    mensaje = MensajesValidacionEnum.ERROR_VALOR_IBC_SUPERA_AL_70PTO_EN_SALARIO_INTEGRAL_SIN_NOVEDADES
                            .getReadableMessage(idCampo, ibc.toPlainString(), tipoError, nombreCampo, ibc.toPlainString());

                    logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }

                // sí no se ha marcado salario integral y tampoco hay novedades, el IBC debe ser igual al salario redondeado
                BigDecimal redondeo = new BigDecimal(getParams().get(ConstantesParametroValidador.REDONDEO));

                if (!esSalarioIntegral.equals("X") && !hayNovedades
                        && FuncionesValidador.redondearValor(ibc, redondeo).compareTo(salario) != 0) {

                    mensaje = MensajesValidacionEnum.ERROR_VALOR_IBC_DIFERENTE_A_SALARIO_BASICO_SIN_SALARIO_INTEGRAL_NI_NOVEDADES
                            .getReadableMessage(idCampo, ibc.toPlainString(), tipoError, nombreCampo, ibc.toPlainString());

                    logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }
            }
        }

        logger.debug("Finaliza validate(LineArgumentDTO)");
    }

}
