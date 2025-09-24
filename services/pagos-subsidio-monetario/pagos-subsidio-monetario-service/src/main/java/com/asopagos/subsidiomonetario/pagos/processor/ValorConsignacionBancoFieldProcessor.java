package com.asopagos.subsidiomonetario.pagos.processor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import com.asopagos.constants.ConstantesComunes;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO;
import co.com.heinsohn.lion.fileCommon.exception.FileCommonException;
import co.com.heinsohn.lion.fileCommon.processors.FieldProcessor;

/**
 * <b>Descripcion:</b> Clase que se encarga procesar el valor de la consignación a banco o pago judicial para ajustarlo al formato definido
 * por sudameris<br/>
 * <b>Módulo:</b> Asopagos - HU 441<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class ValorConsignacionBancoFieldProcessor extends FieldProcessor {

    /**
     * Referencia al logger
     */
    final ILogger logger = LogManager.getLogger(ValorConsignacionBancoFieldProcessor.class);

    /**
     * (non-Javadoc)
     * @see co.com.heinsohn.lion.fileCommon.processors.FieldProcessor#process(co.com.heinsohn.lion.fileCommon.dto.FieldArgumentDTO)
     */
    @Override
    public void process(FieldArgumentDTO arg0) throws FileCommonException {
        String firmaMetodo = "FieldProcessorValorConsignacionBanco.process(FieldArgumentDTO)";
        logger.debug(ConstantesComunes.INICIO_LOGGER + firmaMetodo);

        String valor = arg0.getFieldValue().toString();

        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.HALF_UP);
        BigDecimal d = new BigDecimal(valor);
        String valorDecimal = df.format(d);

        String valorFinal = String.format("%013d", Long.parseLong(valorDecimal));
        
        arg0.setFieldValue(valorFinal);

        logger.debug(ConstantesComunes.FIN_LOGGER + firmaMetodo);
    }

}
