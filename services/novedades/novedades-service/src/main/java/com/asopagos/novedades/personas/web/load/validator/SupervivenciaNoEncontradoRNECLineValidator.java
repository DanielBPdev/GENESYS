package com.asopagos.novedades.personas.web.load.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.novedades.constants.ArchivoCampoNovedadConstante;
import com.asopagos.util.GetValueUtil;
import com.asopagos.util.Interpolator;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.validator.LineValidator;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU-498 , HU-550 <br/>
 *
 * @author <a href="mailto:jusanchez@heinsohn.com.co"> jusanchez</a>
 */

public class SupervivenciaNoEncontradoRNECLineValidator extends LineValidator {

	 /*
	 * Inicializacion de la lista de hallazgos
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
			if (line.get(ArchivoCampoNovedadConstante.TIPO_REGISTRO_DOS_ARCH) != null) {
				// Tipo identificacion - Registro Tipo 2
				verificarTipoNumeroIdentificacion(arguments, line);
				// Estado ANI - Registro Tipo 2
				verificarEstadoANI(arguments, line);
			}
		} finally {
			((List<Long>) arguments.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO)).add(1L);
			((List<ResultadoHallazgosValidacionArchivoDTO>) arguments.getContext()
					.get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS)).addAll(lstHallazgos);
			if (!lstHallazgos.isEmpty()) {
				((List<Long>) arguments.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO_ERRORES)).add(1L);
			}
		}
	}

    /**
     * Método encargado de verificar el tipo y número de identificación 
     * @param arguments,
     *        arguments que contiene la los datos a validar del tipo y número de identificación 
     * @param line,
     *        linea que sera verificada
     */
    private void verificarTipoNumeroIdentificacion(LineArgumentDTO arguments, Map<String, Object> line) {
        if (line.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_DOS_ARCH) != null) {
        	String tipoIdentificacionConsulta = ((String) line
        			.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_DOS_ARCH)).toUpperCase();
        	if (tipoIdentificacionConsulta.length() > ArchivoMultipleCampoConstants.LONGITUD_2_CARACTERES) {
        		lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),  
        				ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_MSG,
        				ArchivoMultipleCampoConstants.EXCEDE_LA_LONGITUD_MAXIMA_DE_CARACTERES_PERMITIDOS
        						+ ArchivoMultipleCampoConstants.LONGITUD_2_CARACTERES));
        	} else {
        		String tipoIdentificacion = ((String) line
        				.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_DOS_ARCH)).toUpperCase();
        		TipoIdentificacionEnum tipoIdentEnum = TipoIdentificacionEnum
        				.obtenerTiposIdentificacionPILAEnum(tipoIdentificacion);
        		if (tipoIdentEnum == null) {
        			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
        					ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_CONSULTAR_MSG,
        					"Tipo de identificación invalido"));
        		}
        	}
        } else {
        	lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
        			ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_MSG,
        			ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_MSG + ArchivoMultipleCampoConstants.REQUERIDO
        					+ ArchivoCampoNovedadConstante.REGISTRO_TIPO_2));
        }

        // Numero de identificacion - Registro Tipo 2
        if (line.get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_DOS_ARCH) != null) {
        	String numIdentificacionConsulta = ((String) line
        			.get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_DOS_ARCH));
        	if (numIdentificacionConsulta.length() > ArchivoMultipleCampoConstants.LONGITUD_17_CARACTERES) {
        		lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
        				ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_MSG,
        				ArchivoMultipleCampoConstants.EXCEDE_LA_LONGITUD_MAXIMA_DE_CARACTERES_PERMITIDOS
        						+ ArchivoMultipleCampoConstants.LONGITUD_17_CARACTERES));
        	}
        } else {
        	lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
        			ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_MSG,
        			ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_MSG + ArchivoMultipleCampoConstants.REQUERIDO
        					+ ArchivoCampoNovedadConstante.REGISTRO_TIPO_2));
        }
    }

    /**
     * Método encargado de verificar el estado ANI 
     * @param arguments,
     *        arguments que contiene la los datos a validar del estado ANI 
     * @param line,
     *        linea que sera verificada
     */
    private void verificarEstadoANI(LineArgumentDTO arguments, Map<String, Object> line) {
        if (line.get(ArchivoCampoNovedadConstante.ESTADO__DOS_ARCH) != null) {
        	String estadoANI = ((String) line.get(ArchivoCampoNovedadConstante.ESTADO__DOS_ARCH));
        	if (estadoANI.length() > ArchivoMultipleCampoConstants.LONGITUD_60_CARACTERES) {
        		lstHallazgos.add( 
        				crearHallazgo(arguments.getLineNumber(), ArchivoCampoNovedadConstante.ESTADO_ANI_MSG,
        				        ArchivoMultipleCampoConstants.EXCEDE_LA_LONGITUD_MAXIMA_DE_CARACTERES_PERMITIDOS
        								+ ArchivoMultipleCampoConstants.LONGITUD_60_CARACTERES));
        	} else {
        		try {
        			GetValueUtil.getTipoInconsistenciaANIPorDescripcion(estadoANI);
        		} catch (Exception e) {
        			lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
        					ArchivoCampoNovedadConstante.ESTADO_ANI_MSG, "Estado ANI invalido"));
        		}
        	}
        } else {
        	lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
        			ArchivoCampoNovedadConstante.ESTADO_ANI_MSG, ArchivoCampoNovedadConstante.ESTADO_ANI_MSG
        					+ ArchivoMultipleCampoConstants.REQUERIDO + ArchivoCampoNovedadConstante.REGISTRO_TIPO_2));
        }
    }

	/**
	 * Método que crea un hallazgo según la información ingresada
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
}