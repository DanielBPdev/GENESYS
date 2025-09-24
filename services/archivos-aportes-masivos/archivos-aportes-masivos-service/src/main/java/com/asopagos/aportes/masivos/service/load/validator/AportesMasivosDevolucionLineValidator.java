package com.asopagos.aportes.masivos.service.load.validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.GetValueUtil;
import com.asopagos.util.Interpolator;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.aportes.clients.*;
import com.asopagos.aportes.masivos.service.constants.ArchivoCampoMasivoConstante;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.constants.ExpresionesRegularesConstants;
import java.text.SimpleDateFormat;  




public class AportesMasivosDevolucionLineValidator extends LineValidator{
    /**
	 * Listado de hallazgos
	 */
	private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos;

	/**
	 * (non-Javadoc)
	 * 
	 * @see co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator#validate(co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO)
	 */
	@Override
	public void validate(LineArgumentDTO arguments) throws FileProcessingException {
		lstHallazgos = new ArrayList<>();
		Map<String, Object> line = arguments.getLineValues();
		try {
			String numeroDocumento = ((String) line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_NUMERO_DOC_APORTANTE));
			String tipoDocumento = ((String) line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TIPO_DOC_APORTANTE));
			String tipoAportante = ((String) line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TIPO_APORTANTE));
            TipoIdentificacionEnum tipoDocumentoAportante = null;
			try {
                 tipoDocumentoAportante = GetValueUtil
                        .getTipoIdentificacionByPila(((String)line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TIPO_DOC_APORTANTE)));
                if (tipoDocumentoAportante == null) {
                     throw new Exception("Invalido");
                }
            } catch (Exception e) {
                lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), "Tipo de identificación del aportante",
                        "Tipo de identificación invalido"));
                tipoDocumento = null;
            }
            
            verificarNumeroDocumento(tipoDocumentoAportante, ArchivoCampoMasivoConstante.RECAUDO_MASIVO_NUMERO_DOC_APORTANTE, 
                    "Número de identificación del aportante",arguments);

			if (numeroDocumento != null && tipoDocumento != null) {
				ValidarExistenciaSolicitudDevolucion existenciaSolicitud = new ValidarExistenciaSolicitudDevolucion(numeroDocumento, TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(tipoDocumento));
				existenciaSolicitud.execute();
				Boolean existe = existenciaSolicitud.getResult();

				if (existe) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"Aportante",
							"Aportante tiene una solicitud de devolucion en estado pendiente"));
				
				}
			}
			if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_PERIODO_PAGO) != null) {
				try {
					Date fecha = new Date();
					String sDate1=line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_PERIODO_PAGO).toString();
					fecha=new SimpleDateFormat("yyyy-MM").parse(sDate1);

				} catch (Exception e) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"periodo ",
							"Periodo de pago no valido"));				
				}
			}

			try{
				TipoSolicitanteMovimientoAporteEnum.valueOf(tipoAportante);
			}
			catch(Exception e){
				lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"tipo Aportante",
							"Tiene un tipo aportante no valido"));
			}
			

			
		} finally {
			arguments.getContext().put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO, (long) arguments.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO) + 1L);
			((List<ResultadoHallazgosValidacionArchivoDTO>) arguments.getContext()
					.get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS)).addAll(lstHallazgos);
			if (!lstHallazgos.isEmpty()) {
				((List<Long>) arguments.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES)).add(1L);
			}
		}
	}
		private ResultadoHallazgosValidacionArchivoDTO crearHallazgo(Long lineNumber, String campo, String errorMessage) {
		String error = Interpolator.interpolate(ArchivoMultipleCampoConstants.MENSAJE_ERROR_CAMPO, campo, errorMessage);
		ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
		hallazgo.setNumeroLinea(lineNumber);
		hallazgo.setNombreCampo(campo);
		hallazgo.setError(error);
		return hallazgo;
	}
	public void verificarNumeroDocumento(TipoIdentificacionEnum tipoIdentificacion, String constanteNumeroIdentificacion, String msgNumIdentificacion, LineArgumentDTO arguments) {
        
        if (tipoIdentificacion != null) {
            if (tipoIdentificacion.equals(TipoIdentificacionEnum.CEDULA_CIUDADANIA)) {
                validarRegex(arguments, constanteNumeroIdentificacion, ExpresionesRegularesConstants.CEDULA_CIUDADANIA,
                        "La cédula de ciudadanía deber tener 3, 4, 5, 8 o 10 dígitos.",
                        msgNumIdentificacion);
                return;
            }
            if (tipoIdentificacion.equals(TipoIdentificacionEnum.CEDULA_EXTRANJERIA)) {
                validarRegex(arguments, constanteNumeroIdentificacion,
                        ExpresionesRegularesConstants.CEDULA_EXTRANJERIA, "La cédula de extranjería debe tener máximo 16 caracteres.",
                        msgNumIdentificacion);
                return;
            }
            if (tipoIdentificacion.equals(TipoIdentificacionEnum.TARJETA_IDENTIDAD)) {
                validarRegex(arguments, constanteNumeroIdentificacion, ExpresionesRegularesConstants.TARJETA_IDENTIDAD,
                        "La tarjeta de identidad solo puede ser de 10 u 11 dígitos.",
                        msgNumIdentificacion);
                return;
            }
			if (tipoIdentificacion.equals(TipoIdentificacionEnum.NIT)) {
                validarRegex(arguments, constanteNumeroIdentificacion, ExpresionesRegularesConstants.NIT,
                        "El nit solo puede hasta 10.",
                        msgNumIdentificacion);
                return;
            }
        }
        else {
            validarRegex(arguments, constanteNumeroIdentificacion, ExpresionesRegularesConstants.CEDULA_CIUDADANIA,
                    "El número de identificación debe de tener un valor valido", msgNumIdentificacion);

        }
    }
            private void validarRegex(LineArgumentDTO arguments, String campoVal, String regex, String mensaje, String campoMSG) {
        try {
            ResultadoHallazgosValidacionArchivoDTO hallazgo = null;
            String valorCampo = ((String) (arguments.getLineValues()).get(campoVal)).trim();

            if (valorCampo != null && !(valorCampo.matches(regex))) {
                hallazgo = crearHallazgo(arguments.getLineNumber(), campoMSG, mensaje);

            }
            if (hallazgo != null) {
                lstHallazgos.add(hallazgo);
                hallazgo = null;
            }
        } catch (Exception e) {
            lstHallazgos.add(crearHallazgo(arguments.getLineNumber(), campoMSG, " Valor perteneciente " + campoMSG + " invalido"));
        }
    }
}
