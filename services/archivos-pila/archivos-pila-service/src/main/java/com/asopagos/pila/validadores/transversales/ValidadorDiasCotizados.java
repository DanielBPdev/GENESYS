package com.asopagos.pila.validadores.transversales;

import java.util.List;
import java.util.Map;

import com.asopagos.enumeraciones.TipoPlanillaEnum;
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
 * <b>Descripción:</b> Validador del contenido de dias cotizados<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorDiasCotizados extends LineValidator {

    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorDiasCotizados.class);

    /** Constantes mensajes */
    private static final String DIAS_COTIZADOS = "cantidad de días cotizados";
    //private static final String VALIDAR_CERO_DIAS = "indicador de método de validación";
    private static final String TIPO_COTIZANTE = "tipo cotizante";
    private static final String TIPO_ARCHIVO = "tipo archivo";
    private static final String TIPO_ERROR_CERO = "TIPO_0";

    /**
     * Metodo que se encargará de realizar la validacion
     * @param LineArgumentDTO
     *        objeto que contiene la informacion a validar
     * @throws FileProcessingException
     *         es lazado cuando hay error en la validacion
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        logger.debug("Inicio validate(LineArgumentDTO)");

        String mensaje = null;

        // Se obtienen los valores de la línea
        Map<String, Object> valoresDeLinea = args.getLineValues();
        
        Object valor = null;

        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        boolean error = false;

        valor = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CAMPO_DIAS_COTIZADOS));
        Integer diasCotizados = null;
        if(valor !=null && !valor.toString().isEmpty()){
            diasCotizados = (Integer) valor;
        }

        // se comprueba que se reciba el dato
        if (diasCotizados != null) {
            Map<String, Object> valoresContexto = args.getContext();
            String tipoPlanillaTexto = (String) valoresContexto.get(ConstantesParametroValidador.TIPO_PLANILLA_NOMBRE);
            TipoPlanillaEnum tipoPlanillaEnum = TipoPlanillaEnum.obtenerTipoPlanilla(tipoPlanillaTexto);
            
            if(diasCotizados == 0 && TipoPlanillaEnum.OBLIGACIONES.equals(tipoPlanillaEnum)) {
            	mensaje = MensajesValidacionEnum.ERROR_CAMPO_VALOR_DEBE_SER_MAYOR_CERO.getReadableMessage(idCampo,
                        diasCotizados.toString(), TIPO_ERROR_CERO, nombreCampo, diasCotizados.toString());

                logger.debug("Inicio validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        	
        	
            // primero se compara el valor máximo de días
            if (diasCotizados.compareTo(30) > 0) {
                mensaje = MensajesValidacionEnum.ERROR_CAMPO_SUPERA_VALOR_MAXIMO_DEFINIDO.getReadableMessage(idCampo,
                        diasCotizados.toString(), tipoError, nombreCampo, diasCotizados.toString());

                logger.debug("Inicio validate(LineArgumentDTO) - " + mensaje);
                throw new FileProcessingException(mensaje);
            }
        }
        else {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS.getReadableMessage(idCampo,
                    ConstantesComunesProcesamientoPILA.VALOR_FALTA_PARAMETRO, tipoError, nombreCampo, DIAS_COTIZADOS);

            logger.debug("Inicio validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        logger.debug("Inicio validate(LineArgumentDTO)");
    }

    /**
     * @param valor
     * @return
     */
    private Boolean esCadenaCero(String valor) {
        try {
            if (Integer.valueOf(valor).equals(0)) {
                return Boolean.TRUE;
            }
        } catch (NumberFormatException e) {
            return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }

}
