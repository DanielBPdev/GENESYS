package com.asopagos.entidaddescuento.load.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.entidaddescuento.constants.CamposArchivoConstants;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.util.Interpolator;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripcion:</b> Clase que implementa el validador de linea para el archivo de Valores a pignorar por entidades de descuento <br/>
 * <b>Módulo:</b> Asopagos - HU - 432 <br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class EntidadDescuentoLineValidator extends LineValidator {

    /**
     * Referencia al logger
     */
    private ILogger logger = LogManager.getLogger(EntidadDescuentoLineValidator.class);

    /**
     * Inicializacion de la lista de hallazgos
     */
    private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;

    /**
     * (non-Javadoc)
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator#validate(co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO)
     */
    @Override
    public void validate(LineArgumentDTO arguments) throws FileProcessingException {

        lstHallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
        Map<String, Object> line = arguments.getLineValues();
        
        ((List<Long>) arguments.getContext().get(CamposArchivoConstants.TOTAL_REGISTRO)).add(1L);
        try {
            TipoIdentificacionEnum tipoIdentificacionTrabajador = null;
            try {
                tipoIdentificacionTrabajador = TipoIdentificacionEnum
                        .obtenerTiposIdentificacionPILAEnum(line.get(CamposArchivoConstants.TIPO_IDENTIFICACION_TRABAJADOR).toString());
                if (tipoIdentificacionTrabajador == null) {
                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstants.TIPO_IDENTIFICACION_TRABAJADOR,
                            "El registro no cumple con la estructura de contenido y/o la obligatoriedad de los datos"));
                }
            } catch (Exception e) {
            	e.printStackTrace();
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstants.TIPO_IDENTIFICACION_TRABAJADOR,
                        "El registro no cumple con la estructura de contenido y/o la obligatoriedad de los datos"));
                tipoIdentificacionTrabajador = null;
            }
            
            String codigoReferencia = null;
            try {
            	codigoReferencia = line.get(CamposArchivoConstants.CODIGO_REFERENCIA).toString() ;
                if (codigoReferencia.length() > 20) {
                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstants.CODIGO_REFERENCIA,
                            "El registro no cumple con la estructura de contenido y/o la obligatoriedad de los datos"));
                }
            } catch (Exception e) {
            	e.printStackTrace();
                codigoReferencia = null;
            }
            
/* CC706 No es obligatorio el tipoi de identificación del Admon Subsidio
            TipoIdentificacionEnum tipoIdentificacionAdministrador = null;
            try {
                tipoIdentificacionAdministrador = TipoIdentificacionEnum
                        .obtenerTiposIdentificacionPILAEnum(line.get(CamposArchivoConstants.TIPO_IDENTIFICACION_ADMINISTRADOR).toString());
                if (tipoIdentificacionAdministrador == null) {
                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstants.TIPO_IDENTIFICACION_ADMINISTRADOR,
                            "Tipo de identificación de administrador invalido"));
                }
            } catch (Exception e) {
            	e.printStackTrace();
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), CamposArchivoConstants.TIPO_IDENTIFICACION_ADMINISTRADOR,
                        "Tipo de identificación de administrador invalido"));
                tipoIdentificacionAdministrador = null;
            }
*/
            
/*
            verificarNumeroDocumento(tipoIdentificacionTrabajador, arguments, CamposArchivoConstants.NUMERO_IDENTIFICACION_TRABAJADOR);
            verificarNumeroDocumento(tipoIdentificacionAdministrador, arguments,
                    CamposArchivoConstants.NUMERO_IDENTIFICACION_ADMINISTRADOR);
            validarRegex(arguments, CamposArchivoConstants.MONTO_DESCUENTO, ExpresionesRegularesConstants.VALOR_NUMERICO,
                    "El monto a descontar debe ser un valor numérico", CamposArchivoConstants.MONTO_DESCUENTO);
*/
        } finally {
            ((List<ResultadoHallazgosValidacionArchivoDTO>) arguments.getContext().get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS))
                    .addAll(lstHallazgos);
            
            if (!lstHallazgos.isEmpty()) {
                ((List<Long>) arguments.getContext().get(CamposArchivoConstants.TOTAL_REGISTRO_ERRORES))
                        .add(1L);
            } else {
                ((List<Long>) arguments.getContext().get(CamposArchivoConstants.TOTAL_REGISTRO_VALIDO))
                        .add(1L);
            }
        }
    }

    /**
     * Método quecrea un hallazgo según la información ingresada
     * 
     * @param lineNumber
     * @param campo
     * @param errorMessage
     * @return
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
     * Método que se encarga de realizar la validación para el número de identificación
     * @param tipoIdentificacion
     *        tipo de identificación
     * @param arguments
     *        argumentos de la linea
     */
    private void verificarNumeroDocumento(TipoIdentificacionEnum tipoIdentificacion, LineArgumentDTO arguments,
            String numeroIdentificacionKey) {
        if (tipoIdentificacion != null) {
            if (tipoIdentificacion.equals(TipoIdentificacionEnum.CEDULA_CIUDADANIA)) {
                // se valida el número de identificación
                validarRegex(arguments, numeroIdentificacionKey, ExpresionesRegularesConstants.CEDULA_CIUDADANIA,
                        "La cédula de ciudadanía deber tener 3, 4, 5, 8 o 10 dígitos.", numeroIdentificacionKey);
                return;
            }
            if (tipoIdentificacion.equals(TipoIdentificacionEnum.CEDULA_EXTRANJERIA)) {
                // se valida el número de identificación
                validarRegex(arguments, numeroIdentificacionKey, ExpresionesRegularesConstants.CEDULA_EXTRANJERIA,
                        "La cédula de extranjería debe tener máximo 16 caracteres.", numeroIdentificacionKey);
                return;
            }
            if (tipoIdentificacion.equals(TipoIdentificacionEnum.TARJETA_IDENTIDAD)) {
                // se valida el número de identificación
                validarRegex(arguments, numeroIdentificacionKey, ExpresionesRegularesConstants.TARJETA_IDENTIDAD,
                        "La tarjeta de identidad solo puede ser de 10 u 11 dígitos.", numeroIdentificacionKey);
                return;
            }
            else {
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), numeroIdentificacionKey,
                        "Tipo de identificación invalido para verificar respecto al número de identificación"));
            }
        }
        else {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), numeroIdentificacionKey,
                    "Tipo de identificación invalido para verificar respecto al número de identificación"));

        }
    }

    /**
     * Método encargado de evaluar la validez de un campo frente a una expresión regular
     * @param arguments
     *        argumentos de la linea
     * @param campoVal
     *        valor del campo
     * @param regex
     * @param mensaje
     * @param campoMSG
     */
    private void validarRegex(LineArgumentDTO arguments, String campoKey, String regex, String mensaje, String campoMSG) {
        try {
            Object campoValue = ((Object) ((Map<String, Object>) arguments.getLineValues()).get(campoKey));
            if (campoValue == null || campoValue.equals("")) {
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG, mensaje));
                return;
            }

            if (campoKey.equals(CamposArchivoConstants.MONTO_DESCUENTO)) {
                if (!campoValue.toString().matches(regex)) {
                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG, mensaje));
                }
            }

            if (campoKey.equals(CamposArchivoConstants.NUMERO_IDENTIFICACION_ADMINISTRADOR)
                    || campoKey.equals(CamposArchivoConstants.NUMERO_IDENTIFICACION_TRABAJADOR)) {
                if (!campoValue.toString().matches(regex)) {
                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG, mensaje));
                }
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG, " Valor perteneciente " + campoMSG + " invalido"));
        }
    }

}
