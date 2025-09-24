package com.asopagos.subsidiomonetario.pagos.load.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.TipoInconsistenciaCampoArchivoConsumosAnibolEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.subsidiomonetario.pagos.constants.CamposArchivoConstants;
import com.asopagos.util.Interpolator;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:mosorio@heinsohn.com.co"> mosorio</a>
 */

public class PagosSubsidioMonetarioTarjetaAnibolLineValidator extends LineValidator {

    /**
     * Referencia al logger
     */
    private ILogger logger = LogManager.getLogger(PagosSubsidioMonetarioLineValidator.class);

    /**
     * Se declara  la lista de hallazgos.
     */
    private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;
    
    /**
     * Lista que contiene en el campo nombreCampo la inconsistencia relacionado y en el mensaje de error el valor del campo
     */
    private List<ResultadoHallazgosValidacionArchivoDTO> lstValorCampoTarjetaErrores;
    
    
    /** (non-Javadoc)
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator#validate(co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void validate(LineArgumentDTO arguments) throws FileProcessingException {
        // TODO Auto-generated method stub
        System.out.println("Inicia la validación de cada linea del archivo del convenio del tercer pagador");

        //((Map<String, Object>) arguments.getLineValues()).forEach((key, value) -> System.out.println(key + ": " + value));

        lstHallazgos = new ArrayList<>();
        lstValorCampoTarjetaErrores = new ArrayList<>();
        
        try {

            validarRegex(arguments, CamposArchivoConstants.IDENTIFICADOR_CAJA_COMPENSACION, ExpresionesRegularesConstants.VALOR_NUMERICO,
                    "El Bin (Identificador único de la Caja de Compensación) débe ser númericos.",
                    CamposArchivoConstants.IDENTIFICADOR_CAJA_COMPENSACION);
//
//            validarRegex(arguments, CamposArchivoConstants.NUMERO_TARJETA, ExpresionesRegularesConstants.VALOR_NUMERICO,
//                    "Los valores de la tarjeta dében ser númericos.", CamposArchivoConstants.NUMERO_TARJETA);

//            //TODO: LA EXPRESION REGULAR DEL NIT (EMPRESA), NO COINCIDE CON EL DEL ARCHIVO DE EJEMPLO
            validarRegex(arguments, CamposArchivoConstants.NIT_EMPRESA, ExpresionesRegularesConstants.VALOR_NUMERICO,
                    "El nit no corresponde con el formato", CamposArchivoConstants.NIT_EMPRESA);

//            validarRegex(arguments, CamposArchivoConstants.CUENTA_RELACIONADA, ExpresionesRegularesConstants.VALOR_NUMERICO,
//                    "La cuenta relacionada debe tener valores númericos", CamposArchivoConstants.CUENTA_RELACIONADA);
//
//            validarRegex(arguments, CamposArchivoConstants.DISPOSITIVO_ORIGEN, ExpresionesRegularesConstants.VALOR_NUMERICO,
//                    "los valores del dispositivo deben ser númericos", CamposArchivoConstants.DISPOSITIVO_ORIGEN);

            //la descripcion estado cobro no se evalua

        //    validarRegex(arguments, CamposArchivoConstants.DESCRIPCION_TRANSACCION, "0  MN COMPRA CUOTA MONETARIA",
        //            "La descripción de transacción no tiene el valor correcto para efectuarse el retiro de la tarjeta",
        //            CamposArchivoConstants.DESCRIPCION_TRANSACCION);
            
            validarRegex(arguments, CamposArchivoConstants.VALOR_TRANSACCION, ExpresionesRegularesConstants.VALOR_NUMERICO,
                    "El valor transacción debe ser númerico", CamposArchivoConstants.VALOR_TRANSACCION);

            validarRegex(arguments, CamposArchivoConstants.VALOR_DISPENSADO, ExpresionesRegularesConstants.VALOR_NUMERICO,
                    "El valor dispensado debe ser númerico", CamposArchivoConstants.VALOR_TRANSACCION);

            validarRegex(arguments, CamposArchivoConstants.FECHA_TRANSACCION, ExpresionesRegularesConstants.VALOR_NUMERICO,
                    "El valor de la fecha de transacción debe ser númerico", CamposArchivoConstants.FECHA_TRANSACCION);
            
            validarRegex(arguments, CamposArchivoConstants.VALOR_A_COBRAR, ExpresionesRegularesConstants.VALOR_NUMERICO,
                    "El valor a cobrar debe ser númerico", CamposArchivoConstants.FECHA_TRANSACCION);
            
            validarRegex(arguments, CamposArchivoConstants.VALOR_IMPUESTOS, ExpresionesRegularesConstants.VALOR_NUMERICO,
                    "El valor de impuestos debe ser númerico", CamposArchivoConstants.VALOR_IMPUESTOS);
            
            validarRegex(arguments, CamposArchivoConstants.TOTAL_A_COBRAR, ExpresionesRegularesConstants.VALOR_NUMERICO,
                    "El valor del total a cobrar debe ser númerico", CamposArchivoConstants.TOTAL_A_COBRAR);
            
            validarRegex(arguments, CamposArchivoConstants.IMPUESTO_EMERGENCIA_ECONOMICA, ExpresionesRegularesConstants.VALOR_NUMERICO,
                    "El valor de impuesto emergencia económica debe ser númerico", CamposArchivoConstants.IMPUESTO_EMERGENCIA_ECONOMICA);
            
//            validarRegex(arguments, CamposArchivoConstants.RESPUESTA_AUTORIZADOR, ExpresionesRegularesConstants.VALOR_NUMERICO,
//                    "El valor de la respuesta del autorizador debe ser númerico", CamposArchivoConstants.RESPUESTA_AUTORIZADOR);
            
        //    validarRegex(arguments, CamposArchivoConstants.DESCRIPCION_RESPUESTA, "TRANSACCION EXITOSA",
        //            "La descripción de respuesta no tiene el valor correcto para efectuarse el retiro de la tarjeta",
        //            CamposArchivoConstants.DESCRIPCION_RESPUESTA);
            
//            validarRegex(arguments, CamposArchivoConstants.CODIGO_AUTORIZADOR, ExpresionesRegularesConstants.VALOR_NUMERICO,
//                    "El valor del código de autorizador debe ser númerico", CamposArchivoConstants.CODIGO_AUTORIZADOR);
//            
//            validarRegex(arguments, CamposArchivoConstants.SUBTIPO, ExpresionesRegularesConstants.VALOR_NUMERICO,
//                    "El valor del subtipo debe ser númerico", CamposArchivoConstants.SUBTIPO);
//            
            validarRegex(arguments, CamposArchivoConstants.FECHA_AUTORIZADOR, ExpresionesRegularesConstants.VALOR_NUMERICO,
                    "El valor de la fecha del autorizador debe ser númerico", CamposArchivoConstants.FECHA_AUTORIZADOR);
            
            validarRegex(arguments, CamposArchivoConstants.HORA_AUTORIZADOR, ExpresionesRegularesConstants.VALOR_NUMERICO,
                    "El valor de la hora del autorizador debe ser númerico", CamposArchivoConstants.HORA_AUTORIZADOR);
            
            validarRegex(arguments, CamposArchivoConstants.HORA_DISPOSITIVO, ExpresionesRegularesConstants.VALOR_NUMERICO,
                    "El valor de la hora del dispositivo debe ser númerico", CamposArchivoConstants.HORA_DISPOSITIVO);
            
            validarRegex(arguments, CamposArchivoConstants.NUMERO_REFERENCIA, ExpresionesRegularesConstants.VALOR_NUMERICO,
                    "El valor del número de referencia debe ser númerico", CamposArchivoConstants.NUMERO_REFERENCIA);
//            
//            //no se valida la red
//            //no se valida el número de dispositivo
//            
            validarRegex(arguments, CamposArchivoConstants.CODIGO_ESTABLECIMIENTO, ExpresionesRegularesConstants.VALOR_NUMERICO,
                    "El valor del código de establecimiento debe ser númerico", CamposArchivoConstants.CODIGO_ESTABLECIMIENTO);
            
//            validarRegex(arguments, CamposArchivoConstants.CODIGO_CUENTA, ExpresionesRegularesConstants.VALOR_NUMERICO,
//                    "El valor del código de cuenta (bolsillo DB) debe ser númerico", CamposArchivoConstants.CODIGO_CUENTA);

        }finally{
            
            ((List<ResultadoHallazgosValidacionArchivoDTO>) arguments.getContext()
                    .get(CamposArchivoConstants.LISTA_HALLAZGOS)).addAll(lstHallazgos);
            
            if (!lstHallazgos.isEmpty()) {
                ((List<Long>) arguments.getContext().get(CamposArchivoConstants.TOTAL_REGISTRO_ERRORES))
                        .add(1L);
                // se agregan los valores correspondientes de cada campo que tienen un error asociados a la linea de registro
                ((List<ResultadoHallazgosValidacionArchivoDTO>) arguments.getContext()
                        .get(CamposArchivoConstants.LISTA_CAMPOS_ERRORES_POR_LINEA)).addAll(lstValorCampoTarjetaErrores);
                
            }else { 
                ((List<Long>) arguments.getContext().get(CamposArchivoConstants.TOTAL_REGISTRO_VALIDO))
                        .add(1L);
            }
        }
        
    }
    
    /**
     * Método que crea un hallazgo según la información ingresada
     * 
     * @param lineNumber
     *            Numero de la linea en que ocurre el hallazgo
     * @param campo
     *            Campo por el cual ocurrio el hallazgo
     * @param errorMessage
     *            menaje de error según el hallazgo
     * @return objeto que contiene los hallazgos encontrados
     */
    private ResultadoHallazgosValidacionArchivoDTO crearHallazgo(Long lineNumber, String campo, String errorMessage) {
        String error = Interpolator.interpolate(ArchivoMultipleCampoConstants.MENSAJE_ERROR_CAMPO, campo, errorMessage);
        ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
        hallazgo.setNumeroLinea(lineNumber);
        hallazgo.setNombreCampo(campo);
        hallazgo.setError(error);
        return hallazgo;
    }
    
  
    /**
     * Método encargado de evaluar la validez de un campo frente a una expresión
     * regular
     * 
     * @param arguments
     *            argumentos de la linea
     * @param campoVal
     *            valor del campo
     * @param regex
     * @param mensaje
     * @param campoMSG
     */
    @SuppressWarnings("unchecked")
    private void validarRegex(LineArgumentDTO arguments, String campoKey, String regex, String mensaje,
            String campoMSG) {
        try {
            String campoValue = null;
            
            if(campoKey.equals(CamposArchivoConstants.VALOR_TRANSACCION) || campoKey.equals(CamposArchivoConstants.VALOR_DISPENSADO) 
                    || campoKey.equals(CamposArchivoConstants.VALOR_A_COBRAR) || campoKey.equals(CamposArchivoConstants.VALOR_IMPUESTOS)
                    || campoKey.equals(CamposArchivoConstants.TOTAL_A_COBRAR) || campoKey.equals(CamposArchivoConstants.IMPUESTO_EMERGENCIA_ECONOMICA)){
                
                campoValue = ((BigDecimal) ((Map<String, Object>) arguments.getLineValues()).get(campoKey)).toString();
            
            }else if(campoKey.equals(CamposArchivoConstants.INDICADOR_REVERSO)){
                
                Character idReverso = ((Character) ((Map<String, Object>) arguments.getLineValues()).get(campoKey)).toString().charAt(0);
                
                campoValue = String.valueOf(idReverso.charValue());
            
            }else{
                
                campoValue = ((String) ((Map<String, Object>) arguments.getLineValues()).get(campoKey)).trim();
            }
            
            if (campoValue == null || campoValue.equals("")) {
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG, mensaje));
                return;
            }
            
            if(campoKey.equals(CamposArchivoConstants.IDENTIFICADOR_CAJA_COMPENSACION) ||campoKey.equals(CamposArchivoConstants.NUMERO_TARJETA)
                    || campoKey.equals(CamposArchivoConstants.CUENTA_RELACIONADA)|| campoKey.equals(CamposArchivoConstants.DISPOSITIVO_ORIGEN)
                    || campoKey.equals(CamposArchivoConstants.VALOR_TRANSACCION) || campoKey.equals(CamposArchivoConstants.FECHA_TRANSACCION)
                    || campoKey.equals(CamposArchivoConstants.VALOR_DISPENSADO) || campoKey.equals(CamposArchivoConstants.VALOR_IMPUESTOS)
                    || campoKey.equals(CamposArchivoConstants.TOTAL_A_COBRAR) || campoKey.equals(CamposArchivoConstants.IMPUESTO_EMERGENCIA_ECONOMICA)
                    || campoKey.equals(CamposArchivoConstants.RESPUESTA_AUTORIZADOR) || campoKey.equals(CamposArchivoConstants.CODIGO_AUTORIZADOR)
                    || campoKey.equals(CamposArchivoConstants.SUBTIPO) || campoKey.equals(CamposArchivoConstants.FECHA_AUTORIZADOR)
                    || campoKey.equals(CamposArchivoConstants.HORA_AUTORIZADOR) || campoKey.equals(CamposArchivoConstants.HORA_DISPOSITIVO)
                    || campoKey.equals(CamposArchivoConstants.NUMERO_REFERENCIA)  || campoKey.equals(CamposArchivoConstants.CODIGO_ESTABLECIMIENTO)
                    || campoKey.equals(CamposArchivoConstants.CODIGO_CUENTA)
                    ){
                if (!campoValue.matches(regex)) {
                    
                    TipoInconsistenciaCampoArchivoConsumosAnibolEnum inconsistenciaCampo = getTipoInconsistenciaCampoArchivo(campoKey);
                    lstValorCampoTarjetaErrores.add(crearHallazgo(arguments.getLineNumber(), inconsistenciaCampo.toString(), campoValue));
                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG, mensaje));
                }
            }
            
            if(campoKey.equals(CamposArchivoConstants.NIT_EMPRESA)){
                if (!campoValue.matches(regex)) {
                    
                    lstValorCampoTarjetaErrores.add(crearHallazgo(arguments.getLineNumber(), TipoInconsistenciaCampoArchivoConsumosAnibolEnum.NIT_EMPRESA.toString(), campoValue));
                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG, mensaje));
                }
            }
 
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG,
                    " Valor perteneciente " + campoMSG + " invalido"));
        }
    }
    
    /**
     * Método que permite obtener la enumeración de la incosistencia descubierta por lion.
     * @param campo
     *        <code>String</code>
     *        valor del campo de la incosistencia.
     * @return Enumeración del Tipo de Inconsistencia encontrada.
     */
    private TipoInconsistenciaCampoArchivoConsumosAnibolEnum getTipoInconsistenciaCampoArchivo(String campo){
        
         TipoInconsistenciaCampoArchivoConsumosAnibolEnum[] inconsistencias = TipoInconsistenciaCampoArchivoConsumosAnibolEnum.values();
         
         for(TipoInconsistenciaCampoArchivoConsumosAnibolEnum inconsistencia : inconsistencias){
             if(inconsistencia.getValorCampo().equals(campo))
                 return inconsistencia;
         }
         return null;
    }
}
