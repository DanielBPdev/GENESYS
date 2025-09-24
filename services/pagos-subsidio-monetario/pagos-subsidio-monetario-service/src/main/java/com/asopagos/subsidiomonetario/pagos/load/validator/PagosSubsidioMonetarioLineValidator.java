package com.asopagos.subsidiomonetario.pagos.load.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.constants.ExpresionesRegularesConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.subsidiomonetario.pagos.constants.CamposArchivoConstants;
import com.asopagos.util.Interpolator;

import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripcion:</b> Clase que implementa el validador de linea para el
 * archivo del Convenio del tercer pagador o comercio<br/>
 * <b>Módulo:</b> Asopagos - HU - 31 - 205 <br/>
 * 
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
public class PagosSubsidioMonetarioLineValidator extends LineValidator {

	/**
	 * Referencia al logger
	 */
	private ILogger logger = LogManager.getLogger(PagosSubsidioMonetarioLineValidator.class);

	/**
	 * Inicializacion de la lista de hallazgos
	 */
	private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;

	@Override
	public void validate(LineArgumentDTO arguments) throws FileProcessingException {
		// TODO Auto-generated method stub

		lstHallazgos = new ArrayList<ResultadoHallazgosValidacionArchivoDTO>();
		Map<String, Object> line = arguments.getLineValues();

		((List<Long>) arguments.getContext().get(CamposArchivoConstants.TOTAL_REGISTRO)).add(1L);
		try {

			TipoIdentificacionEnum tipoIdentificacionAdministradorSubsidio = null;
			try {
				tipoIdentificacionAdministradorSubsidio = TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(
						line.get(CamposArchivoConstants.TIPO_IDENTIFICACION_ADMINISTRADOR_SUBSIDIO).toString());
				if (tipoIdentificacionAdministradorSubsidio == null) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							CamposArchivoConstants.TIPO_IDENTIFICACION_ADMINISTRADOR_SUBSIDIO,
							"Tipo de identificación de trabajador invalido"));
				}
			} catch (Exception e) {
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
						CamposArchivoConstants.TIPO_IDENTIFICACION_ADMINISTRADOR_SUBSIDIO,
						"Tipo de identificación de trabajador invalido"));
				tipoIdentificacionAdministradorSubsidio = null;
			}
			validarRegex(arguments, CamposArchivoConstants.IDENTIFICACION_TRANSACCION_TERCERO_PAGADOR,
					ExpresionesRegularesConstants.IDENTIFICACION_TERCERO_PAGADOR_SUBSIDIO_MONETARIO,
					"El identificador de la transacción del tercer pagador debe ser un valor numérico",
					CamposArchivoConstants.IDENTIFICACION_TRANSACCION_TERCERO_PAGADOR);

			verificarNumeroDocumento(tipoIdentificacionAdministradorSubsidio, arguments,
					CamposArchivoConstants.NUMERO_IDENTIFICACION_ADMINISTRADOR_SUBSIDIO);

			validarRegex(arguments, CamposArchivoConstants.VALOR_REAL_TRANSACCION,
					ExpresionesRegularesConstants.VALOR_NUMERICO,
					"El valor real de la transacción debe ser un valor numérico",
					CamposArchivoConstants.VALOR_REAL_TRANSACCION);

			validarRegex(arguments, CamposArchivoConstants.HORA_TRANSACCION, ExpresionesRegularesConstants.HORA,
					"La estructura de la hora no coincide con el formato establecido",
					CamposArchivoConstants.HORA_TRANSACCION);

			validarRegex(arguments, CamposArchivoConstants.DEPARTAMENTO, ExpresionesRegularesConstants.VALOR_NUMERICO,
					"El departamento debe ser un identificador con un valor numérico",
					CamposArchivoConstants.DEPARTAMENTO);

			validarRegex(arguments, CamposArchivoConstants.MUNICIPIO, ExpresionesRegularesConstants.VALOR_NUMERICO,
					"El municipio debe ser un identificador con un valor numérico", CamposArchivoConstants.MUNICIPIO);

			validarRegex(arguments, CamposArchivoConstants.TIPO_SUBSIDIO, "f|F$", "el tipo de subsidio debe ser una f",
					CamposArchivoConstants.TIPO_SUBSIDIO);

		} finally {
			((List<ResultadoHallazgosValidacionArchivoDTO>) arguments.getContext()
					.get(CamposArchivoConstants.LISTA_HALLAZGOS)).addAll(lstHallazgos);
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
		System.out.println("**__**crearHallazgo campo: "+campo+" lineNumber: "+lineNumber+" error:"+error);
		return hallazgo;
	}

	/**
	 * Método que se encarga de realizar la validación para el número de
	 * identificación
	 * 
	 * @param tipoIdentificacion
	 *            tipo de identificación
	 * @param arguments
	 *            argumentos de la linea
	 */
	private void verificarNumeroDocumento(TipoIdentificacionEnum tipoIdentificacion, LineArgumentDTO arguments,
			String numeroIdentificacionKey) {
		if (tipoIdentificacion != null) {
			if (tipoIdentificacion.equals(TipoIdentificacionEnum.CEDULA_CIUDADANIA)) {
				// se valida el número de identificación
				validarRegex(arguments, numeroIdentificacionKey, ExpresionesRegularesConstants.CEDULA_CIUDADANIA,
						"La cédula de ciudadanía deber tener 3, 4, 5, 8 o 10 dígitos.", numeroIdentificacionKey);
				return;
			} else if (tipoIdentificacion.equals(TipoIdentificacionEnum.CEDULA_EXTRANJERIA)) {
				// se valida el número de identificación
				validarRegex(arguments, numeroIdentificacionKey, ExpresionesRegularesConstants.CEDULA_EXTRANJERIA,
						"La cédula de extranjería debe tener máximo 16 caracteres.", numeroIdentificacionKey);
				return;
			} else if (tipoIdentificacion.equals(TipoIdentificacionEnum.TARJETA_IDENTIDAD)) {
				// se valida el número de identificación
				validarRegex(arguments, numeroIdentificacionKey, ExpresionesRegularesConstants.TARJETA_IDENTIDAD,
						"La tarjeta de identidad solo puede ser de 10 u 11 dígitos.", numeroIdentificacionKey);
				return;
			} else if (tipoIdentificacion.equals(TipoIdentificacionEnum.PASAPORTE)) {
				// se valida el número de identificación
				validarRegex(arguments, numeroIdentificacionKey, ExpresionesRegularesConstants.PASAPORTE,
						"El pasaporte solo puede ser de 1 o 16 caracteres.", numeroIdentificacionKey);
				return;
			} else if (tipoIdentificacion.equals(TipoIdentificacionEnum.REGISTRO_CIVIL)) {
				// se valida el número de identificación
				validarRegex(arguments, numeroIdentificacionKey, ExpresionesRegularesConstants.REGISTRO_CIVIL,
						"El registro civil solo puede ser de 8, 10 u 11 caracteres.", numeroIdentificacionKey);
				return;
			} else if (tipoIdentificacion.equals(TipoIdentificacionEnum.CARNE_DIPLOMATICO)) {
				// se valida el número de identificación
				validarRegex(arguments, numeroIdentificacionKey, ExpresionesRegularesConstants.CARNE_DIPLOMATICO,
						"La estructura del Carné diplomático no coincide.", numeroIdentificacionKey);
				return;
			} else if (tipoIdentificacion.equals(TipoIdentificacionEnum.SALVOCONDUCTO)) {
				// se valida el número de identificación
				validarRegex(arguments, numeroIdentificacionKey, ExpresionesRegularesConstants.SALVO_CONDUCTO,
						"El salvoconducto de permanencia solo puede ser de 9 digitos.", numeroIdentificacionKey);
				return;
			} else if (tipoIdentificacion.equals(TipoIdentificacionEnum.PERM_ESP_PERMANENCIA)) {
				// se valida el número de identificación
				validarRegex(arguments, numeroIdentificacionKey, ExpresionesRegularesConstants.PERM_ESP_PERMANENCIA,
						"El permiso especial debe tener 15 dígitos.", numeroIdentificacionKey);
				return;
			}  else if (tipoIdentificacion.equals(TipoIdentificacionEnum.PERM_PROT_TEMPORAL)) {
				// se valida el número de identificación
				validarRegex(arguments, numeroIdentificacionKey, ExpresionesRegularesConstants.PERM_PROT_TEMPORAL,
						"El Permiso por Protección Temporal debe tener máximo 8 dígitos.", numeroIdentificacionKey);
				return;
			}  else {
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), numeroIdentificacionKey,
						"Tipo de identificación invalido para verificar respecto al número de identificación"));
			}
		} else {
			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), numeroIdentificacionKey,
					"Tipo de identificación invalido para verificar respecto al número de identificación"));

		}
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
	private void validarRegex(LineArgumentDTO arguments, String campoKey, String regex, String mensaje,
			String campoMSG) {
		try {
			String campoValue = null;
			if(campoKey.equals(CamposArchivoConstants.VALOR_REAL_TRANSACCION)){
				
				campoValue = ((BigDecimal) ((Map<String, Object>) arguments.getLineValues()).get(campoKey)).toString();
			
			}else if(campoKey.equals(CamposArchivoConstants.IDENTIFICACION_TRANSACCION_TERCERO_PAGADOR)){
				
				campoValue = ((String) ((Map<String, Object>) arguments.getLineValues()).get(campoKey)).trim();
			
			}else if(campoKey.equals(CamposArchivoConstants.FECHA_TRANSACCION)){
				try {
					campoValue = ((Date) ((Map<String, Object>) arguments.getLineValues()).get(campoKey)).toString();
				} catch (Exception e) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG, mensaje));
				}
			
			}else if(campoKey.equals(CamposArchivoConstants.TIPO_SUBSIDIO)){
			    
			    Character tipo = ((Character) ((Map<String, Object>) arguments.getLineValues()).get(campoKey)).toString().charAt(0);
			    
			    campoValue = String.valueOf(tipo.charValue());
			
		    }else{
				
				campoValue = ((String) ((Map<String, Object>) arguments.getLineValues()).get(campoKey)).trim();
			}
			
			if (campoValue == null || campoValue.equals("")) {
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG, mensaje));
				return;
			}
			
			if(campoKey.equals(CamposArchivoConstants.IDENTIFICACION_TRANSACCION_TERCERO_PAGADOR)){
	             if (!campoValue.matches(regex)) {
	                    lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG, mensaje));
	                }
			}

			if (campoKey.equals(CamposArchivoConstants.VALOR_REAL_TRANSACCION)
					|| campoKey.equals(CamposArchivoConstants.DEPARTAMENTO)
					|| campoKey.equals(CamposArchivoConstants.MUNICIPIO)) {
				if (!campoValue.matches(regex)) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG, mensaje));
				}
			}

			if (campoKey.equals(CamposArchivoConstants.NUMERO_IDENTIFICACION_ADMINISTRADOR_SUBSIDIO)) {
				if (!campoValue.matches(regex)) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG, mensaje));
				}
			}

			if (campoKey.equals(CamposArchivoConstants.HORA_TRANSACCION)) {
				if (!campoValue.matches(regex)) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG, mensaje));
				}
			}
			
			if (campoKey.equals(CamposArchivoConstants.TIPO_SUBSIDIO)) {
				if (!campoValue.matches(regex)) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG, mensaje));
				}
			}
			
		} catch (Exception e) {
			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG,
					" Valor perteneciente " + campoMSG + " invalido"));
		}
	}

}
