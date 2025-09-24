package com.asopagos.pila.validadores.bloque2;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import com.asopagos.enumeraciones.pila.TipoArchivoPilaEnum;
import com.asopagos.enumeraciones.pila.TipoErrorValidacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.pila.constants.ConstantesContexto;
import com.asopagos.pila.constants.ConstantesParametroValidador;
import com.asopagos.pila.enumeraciones.MensajesValidacionEnum;
import com.asopagos.pila.util.FuncionesValidador;
import co.com.heinsohn.lion.common.util.CalendarUtil;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripción:</b> Clase que contiene la validación de numero de registro<br>
 * <b>Módulo:</b> ArchivosPILAService - HU 391 <br/>
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 * @author <a href="mailto:rarboleda@heinsohn.com.co">Robinson Arboleda V.</a>
 */

public class ValidadorPasoInformacionBloque3 extends LineValidator {
    /**
     * Referencia al logger
     */
    private static final ILogger logger = LogManager.getLogger(ValidadorPasoInformacionBloque3.class);

    /**
     * Metodo se encarga de validar paso de informacion
     * @param LineArgumentDTO
     *        objeto con la informacion a validar
     * @exception FileProcessingException
     *            lanzada cuando hay un error en la validacion
     */
    @Override
    public void validate(LineArgumentDTO args) throws FileProcessingException {
        logger.debug("Inicia validate(LineArgumentDTO)");
        
        String mensaje = null;

        try {
            // Se obtienen los valores de la línea
            Map<String, Object> valoresDeLinea = args.getLineValues();

            Map<String, Object> contexto = args.getContext();

            String tipoArchivo = (String) contexto.get(getParams().get(ConstantesParametroValidador.LLAVE_TIPO_ARCHIVO));
            String nombresCampos = getParams().get(ConstantesParametroValidador.NOMBRES_CAMPOS);
            String llavesCampos = getParams().get(ConstantesParametroValidador.LLAVES_CAMPOS_TRASPASO);
            String numeroPlanilla = (String) contexto.get(getParams().get(ConstantesParametroValidador.LLAVE_NUMERO_PLANILLA));

            if (tipoArchivo != null && !tipoArchivo.isEmpty() && nombresCampos != null && !nombresCampos.isEmpty() && llavesCampos != null
                    && !llavesCampos.isEmpty() && numeroPlanilla != null) {

                String[] arregloNombres = nombresCampos.split(",");
                String[] arregloLlaves = llavesCampos.split(",");

                // se verifica que la cantidad de nombres recibidos sea igual a la cantidad de llaves
                if (arregloLlaves.length != arregloNombres.length) {
                    // en este caso, se debe lanzar un error de tipo técnico
                    
                    mensaje = MensajesValidacionEnum.ERROR_NO_SE_PUEDE_PREPARAR_INFO_PARA_BLOQUE_3_CANTIDAD_CAMPOS_NO_VALIDA
                            .getReadableMessage(TipoErrorValidacionEnum.ERROR_TECNICO.toString());
                    
                    logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                    throw new FileProcessingException(mensaje);
                }
                else {
                    // se prepara un mapa con los campos y sus valores
                    Map<String, String[]> variablesBloque3 = new HashMap<>();

                    for (int i = 0; i < arregloNombres.length; i++) {
                        Object valorCampo = null;

                        // se toma de la llave del campo, el registro y el # de campo
                        String registro = arregloLlaves[i].split("-")[0];
                        String campo = arregloLlaves[i].split("-")[1];
                        
                        String[] entrada = new String[2];

                        try {
                            // se obtiene la etiqueta del campo requerido
                            TipoArchivoPilaEnum tipoArchivoPilaEnum = TipoArchivoPilaEnum.obtenerTipoArchivoPilaEnum(tipoArchivo);
                            String[] codigoEtiqueta = FuncionesValidador.getNombreCampo(tipoArchivoPilaEnum, registro, campo);

                            valorCampo = valoresDeLinea.get(codigoEtiqueta[1]);
                            entrada[0] = codigoEtiqueta[0];

                            if (valorCampo == null) {
                                variablesBloque3.put(arregloNombres[i], null);
                            }
                            else
                            // los valores se convierten a String para la persistencia
                            if (valorCampo instanceof String) {
                                entrada[1] = valorCampo.toString();
                            }
                            else if (valorCampo instanceof Integer) {
                                entrada[1] = Integer.toString((int) valorCampo);
                            }
                            else if (valorCampo instanceof BigDecimal) {
                                entrada[1] = ((BigDecimal) valorCampo).toPlainString();
                            }
                            else if (valorCampo instanceof Date) {
                                Calendar fecha = new GregorianCalendar();
                                fecha.setTimeInMillis(((Date) valorCampo).getTime());

                                entrada[1] = CalendarUtil.retornarFechaSinHorasMinutosSegundos(fecha.getTime());
                            }
                        } catch (NullPointerException e) {
                            // si un valor da error al momento de su lectura, el arreglo se llena con un null
                            entrada[1] = null;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            mensaje = MensajesValidacionEnum.ERROR_CAMPO_AL_TRATAR_DE_DEFINIR_TIPO_REGISTRO_Y_NUMERO_CAMPO
                                    .getReadableMessage(getParams().get(ConstantesParametroValidador.TIPO_ERROR));
                            
                            logger.debug("Finaliza validate(LineArgumentDTO) - " + mensaje);
                            throw new FileProcessingException(mensaje);
                        }
                        
                        variablesBloque3.put(arregloNombres[i], entrada);
                    }

                    contexto.put(ConstantesContexto.INFORMACION_BLOQUE_3, variablesBloque3);
                    args.setContext(contexto);
                }
            }
        } catch (NullPointerException e) {
            logger.debug("Finaliza validate(LineArgumentDTO)");
        }
        logger.debug("Finaliza validate(LineArgumentDTO)");
    }

}
