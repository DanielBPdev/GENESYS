package com.asopagos.pila.validadores.bloque2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.math.NumberUtils;
import com.asopagos.enumeraciones.aportes.ClaseAportanteEnum;
import com.asopagos.enumeraciones.aportes.TipoAportanteEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Esta clase realiza la validacion de la clase de aportante<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */
public class ValidadorClaseTipoAportante extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorClaseTipoAportante.class);

    /** Constantes para mensajes */
    private static final String CLASE_APORTANTE = "clase de aportante válida";
    private static final String TIPO_APORTANTE = "tipo de aportante válido";

    /**
     * Metodo se encarga de validar la clase aportante vs el tipo aportante
     * @param LineArgumentDTO
     *        objeto con la informacion a valida
     * @exception FileProcessingException
     *            lanzada cuando hay un eero
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        logger.debug("Inicia validate(LineArgumentDTO)");

        String mensaje = null;
        String nombreCampo = getParams().get(ConstantesParametroValidador.NOMBRE_CAMPO);
        String tipoError = getParams().get(ConstantesParametroValidador.TIPO_ERROR);
        String idCampo = getParams().get(ConstantesParametroValidador.ID_CAMPO);

        // Se obtiene los valores de la linea
        Map<String, Object> valoresDeLinea = args.getLineValues();
        
        Object valorCampo = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.CLASE_APORTANTE));

        String claseAportante = null;
        if(valorCampo instanceof String){
            claseAportante = (String) valorCampo;
        }
        ClaseAportanteEnum claseAportanteEnum = ClaseAportanteEnum.obtenerClaseAportanteEnum(claseAportante);

        valorCampo = valoresDeLinea.get(getParams().get(ConstantesParametroValidador.TIPO_APORTANTE));
        Integer tipoAportante = null;
        if (valorCampo instanceof String && NumberUtils.isParsable((String) valorCampo)) {
            tipoAportante = Integer.parseInt((String) valorCampo);
        }
        else if(valorCampo instanceof Integer){
            tipoAportante = (Integer) valorCampo;
        }

        if (tipoAportante == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS
                    .getReadableMessage(idCampo, "", tipoError, nombreCampo, TIPO_APORTANTE);

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        TipoAportanteEnum tipoAportanteEnum = TipoAportanteEnum.obtenerTipoAportante(tipoAportante);

        if (claseAportanteEnum == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS
                    .getReadableMessage(idCampo, "", tipoError, nombreCampo, CLASE_APORTANTE);

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        if (tipoAportanteEnum == null) {
            mensaje = MensajesValidacionEnum.ERROR_FALTA_PARAMETROS
                    .getReadableMessage(idCampo, "", tipoError, nombreCampo, TIPO_APORTANTE);

            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }

        // se valida que la clase de aportante sea coherente de acuerdo a lo definido por la Resolución
        
        List<ClaseAportanteEnum> clasesAportanteValidas = definirClasesAportanteValidas(tipoAportanteEnum);
        
        if(!clasesAportanteValidas.contains(claseAportanteEnum)){
            mensaje = MensajesValidacionEnum.ERROR_CLASE_TIPO_APORTANTE.getReadableMessage(
                    idCampo, claseAportanteEnum.getDescripcion(), tipoError, nombreCampo, claseAportanteEnum.getDescripcion(),
                    tipoAportanteEnum.getDescripcion(), claseAportanteEnum.getDescripcion());

            logger.debug("Finaliza validate(LineArgumentDTO ) - " + mensaje);
            throw new FileProcessingException(mensaje);
        }
        
        logger.debug("Finaliza validate(LineArgumentDTO)");
    }

    /**
     * Método para determinar las clases de aportante válidas de acuerdo a cada tipo de aportante
     * 
     * @param tipoAportanteEnum
     *        Tipo de aportante evaluado
     * @return <b>List<ClaseAportanteEnum></b>
     *         Listado de clases de aportante válidas
     */
    private List<ClaseAportanteEnum> definirClasesAportanteValidas(TipoAportanteEnum tipoAportanteEnum) {
        logger.debug("Inicia definirClasesAportanteValidas(TipoAportanteEnum)");
        
        List<ClaseAportanteEnum> result = null;
        
        if(tipoAportanteEnum != null){
            result = new ArrayList<>();
            
            switch(tipoAportanteEnum){
                case EMPLEADOR:
                    result.add(ClaseAportanteEnum.CLASE_A);
                    result.add(ClaseAportanteEnum.CLASE_B);
                    result.add(ClaseAportanteEnum.CLASE_C);
                    result.add(ClaseAportanteEnum.CLASE_D);
                    break;
                case INDEPENDIENTE:
                case TRABAJADOR_PAGO_APORTE_FALTANTE:
                    result.add(ClaseAportanteEnum.CLASE_I);
                    break;
                case UNIVERSIDADES_PUBLICAS:
                case AGREMIACIONES:
                case CTA:
                case MISION_DIPLOMATICA:
                case ADMINISTRADORAS_HOGARES_BIENESTAR:
                case PAGADOR_SINDICAL:
                case PAGADOR_PROGRAMA_REINCORPORACION:
                case PAGADOR_PRESTACION_HUMANITARIA:
                case PAGADOR_SUBSISTEMA_NACIONAL_VOLUNTARIOS:
                    result.add(ClaseAportanteEnum.CLASE_A);
                    result.add(ClaseAportanteEnum.CLASE_B);
                    break;
                case PAGADOR_CONSEJALES:
                    result.add(ClaseAportanteEnum.CLASE_B);
                    break;
                case PAGADOR_APORTES_PARAFISCALES_MAGISTERIO:
                    result.add(ClaseAportanteEnum.CLASE_A);
                    break;
                case CONTRATANTE:
                    result.add(ClaseAportanteEnum.CLASE_A);
                    result.add(ClaseAportanteEnum.CLASE_B);
                    result.add(ClaseAportanteEnum.CLASE_C);
                    result.add(ClaseAportanteEnum.CLASE_I);
                    break;
                case PAGADOR_PROMOTOR_DEL_SERVICIO_PARA_LA_PAZ:
                    result.add(ClaseAportanteEnum.CLASE_A);
                    break;
                default:
                    break;
            }
        }

        logger.debug("Finaliza definirClasesAportanteValidas(TipoAportanteEnum)");        
        return result;
    }

}
