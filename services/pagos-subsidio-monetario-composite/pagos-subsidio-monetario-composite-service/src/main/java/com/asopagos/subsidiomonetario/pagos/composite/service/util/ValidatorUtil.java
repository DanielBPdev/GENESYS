package com.asopagos.subsidiomonetario.pagos.composite.service.util;

import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.rest.exception.ErrorExcepcion;
import org.apache.commons.lang3.Range;

import java.math.BigDecimal;

public class ValidatorUtil {

    public static void validatNumeroDocumento(String text, TipoIdentificacionEnum tipoDocumento) {
        if (TipoIdentificacionEnum.CEDULA_CIUDADANIA.equals(tipoDocumento) && !text.matches(ExpresionesRegularesConstants.CEDULA_CIUDADANIA)) {
            throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_CEDULA_CIUDADANIA);
        } else if (TipoIdentificacionEnum.NIT.equals(tipoDocumento) && !text.matches(ExpresionesRegularesConstants.NIT)) {
            throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_NIT);
        } else if (TipoIdentificacionEnum.PASAPORTE.equals(tipoDocumento) && !text.matches(ExpresionesRegularesConstants.PASAPORTE)) {
            throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_PASAPORTE);
        } else if (TipoIdentificacionEnum.REGISTRO_CIVIL.equals(tipoDocumento) && !text.matches(ExpresionesRegularesConstants.REGISTRO_CIVIL)) {
            throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_REGISTRO_CIVIL);
        } else if (TipoIdentificacionEnum.TARJETA_IDENTIDAD.equals(tipoDocumento) && !text.matches(ExpresionesRegularesConstants.TARJETA_IDENTIDAD)) {
            throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_TARJETA_IDENTIDAD);
        } else if (TipoIdentificacionEnum.CEDULA_EXTRANJERIA.equals(tipoDocumento) && !text.matches(ExpresionesRegularesConstants.CEDULA_EXTRANJERIA)) {
            throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_CEDULA_EXTRANJERIA);
        } else if (TipoIdentificacionEnum.CARNE_DIPLOMATICO.equals(tipoDocumento) && !text.matches(ExpresionesRegularesConstants.CARNE_DIPLOMATICO_PAGOS)) {
            throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_CARNE_DIPLOMATICO);
        } else if (TipoIdentificacionEnum.SALVOCONDUCTO.equals(tipoDocumento) && !text.matches(ExpresionesRegularesConstants.ALFANUMERICO_DE_16_CARACTERES_PAGOS)) {
            throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_SALVOCONDUCTO);
        } else if (TipoIdentificacionEnum.PERM_ESP_PERMANENCIA.equals(tipoDocumento) && !text.matches(ExpresionesRegularesConstants.PERM_ESP_PERMANENCIA)) {
            throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_PERM_ESP_PERMANENCIA);
        } else if (TipoIdentificacionEnum.PERM_PROT_TEMPORAL.equals(tipoDocumento) && !text.matches(ExpresionesRegularesConstants.PERM_PROT_TEMPORAL_PAGOS)) {
            throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_PERM_PROT_TEMPORAL);
        }
    }

    public static void validarTerceroPagador(String text) {
        if (!text.matches(ExpresionesRegularesConstants.ALFANUMERICO_DE_20_CARACTERES_PAGOS)) {
            throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_TERCERO_PAGADOR);
        }
    }

    public static void validarIdentificadorPuntoCobro(String text) {
        if (!text.matches(ExpresionesRegularesConstants.ALFANUMERICO_DE_20_CARACTERES_PAGOS)) {
            throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_PUNTO_COBRO);
        }
    }

    public static void validarValorSolicitado(BigDecimal valor, BigDecimal minimo, BigDecimal maximo) {
        if (valor.intValue() > maximo.intValue()) {
            throw new ErrorExcepcion(MensajesGeneralConstants.VALOR_SOLICITADO_MAYOR);
        } else if(valor.intValue() < minimo.intValue()){
            throw new ErrorExcepcion(MensajesGeneralConstants.VALOR_SOLICITADO_MENOR);
        }
    }

    public static void compareValorSolicitado(BigDecimal valorSolicitado, BigDecimal saldoActualSubsidio){
        if(valorSolicitado.compareTo(saldoActualSubsidio) == 1){
            throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_VALOR_SOLICITADO_SALDO_SUBSIDIO);
        }
    }

    public static void compareValorSolicitadoValorEntregado(BigDecimal valorSolicitado, BigDecimal valorEntregado){
        if(valorEntregado.compareTo(valorSolicitado) == 1){
            throw new ErrorExcepcion(MensajesGeneralConstants.ERROR_VALOR_SOLICITADO_SALDO_SUBSIDIO);
        }
    }


}
