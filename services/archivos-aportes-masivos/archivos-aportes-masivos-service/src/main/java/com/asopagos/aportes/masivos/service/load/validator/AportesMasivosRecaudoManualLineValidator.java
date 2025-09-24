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
import com.asopagos.util.GetValueUtil;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.aportes.masivos.service.constants.ArchivoCampoMasivoConstante;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU-498<br/>
 *
 * @author <a href="mailto:jusanchez@heinsohn.com.co"> jusanchez</a>
 */

public class AportesMasivosRecaudoManualLineValidator extends LineValidator {

	/**
	 * Listado de hallazgos
	 */
	private List<ResultadoHallazgosValidacionArchivoDTO> lstHallazgos = new ArrayList<>();

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
			for (Map.Entry<String, Object> entry : line.entrySet()) {
					System.out.println(entry.getKey() + ":" + entry.getValue());
			}
			if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TIPO_DOC_APORTANTE) != null) {
				String tipoDocumento = ((String) line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TIPO_DOC_APORTANTE))
						.toUpperCase();

				if (verificarTipoDocumentoAportante(tipoDocumento).equals(Boolean.TRUE)){
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"Tipo de documento aportante",
							"Tipo de documento inválido"));

				}

			}
			if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_NUMERO_DOC_APORTANTE) != null) {
				String numeroDocumento = ((String) line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_NUMERO_DOC_APORTANTE))
						.toUpperCase();

				if (verificarNumeroDocumento(numeroDocumento).equals(Boolean.TRUE)) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"Número de aportante",
							"Número de aportante inválido"));
				
				}
			}
			if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TIPO_DOCUMENTO_COTIZANTE) != null) {
				String tipoDocumento = ((String) line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TIPO_DOCUMENTO_COTIZANTE))
						.toUpperCase();

				if (verificarTipoDocumentoCotizante(tipoDocumento).equals(Boolean.TRUE)) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"Tipo de documento cotizante",
							"Tipo de documento inválido"));
				}
			}
			if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_NUMERO_DOCUMENTO_COTIZANTE) != null) {
				String numeroDocumento = ((String) line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_NUMERO_DOCUMENTO_COTIZANTE))
						.toUpperCase();
					
				if (verificarNumeroDocumento(numeroDocumento).equals(Boolean.TRUE)) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"Número de documento cotizante",
							"Número de cotizante inválido"));
				}

			}
			if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_SALARIO_BASICO) != null) {
				String salario = ((String) line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_SALARIO_BASICO))
						.toUpperCase();

				if (validarSalarioBasico(salario) == Boolean.TRUE) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"Salario básico",
							"Salario básico inválido"));
				}

			}
			if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_DIAS_COTIZADOS) != null) {
				String diasCotizados = ((String) line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_DIAS_COTIZADOS))
						.toUpperCase();
				
				if (validarDiasCotizados(diasCotizados) == Boolean.TRUE) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"Días cotizados",
							"Días cotizados inválidos"));
				}

			}
			if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TARIFA) != null) {
				String tarifa = ((String) line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TARIFA))
						.toUpperCase();
				
				if (validarTarifa(tarifa) == Boolean.TRUE) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"Tarifa",
							"Tarifa inválida"));
				}

			}
			if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_NUMERO_DE_HORAS_LABORADAS) != null) {
				String numeroHorasLaboradas = ((String) line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_NUMERO_DE_HORAS_LABORADAS))
						.toUpperCase();

				if (validarNumeroHorasLaboradas(numeroHorasLaboradas) == Boolean.TRUE) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"Horas laboradas",
							"Número de horas laboradas inválido"));
				}

				
			}
			if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_APORTE_OBLIGATORIO) != null) {
				String aporteObligatorio = ((String) line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_APORTE_OBLIGATORIO))
						.toUpperCase();

				if (validarAporteObligatorio(aporteObligatorio) == Boolean.TRUE) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"Valor aporte obligatorio",
							"Aporte obligatorio inválido"));
				}
			}

			if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TOTAL_APORTE) != null) {
				String totalAporte = ((String) line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TOTAL_APORTE))
						.toUpperCase();
				if (validarTotalAporte(totalAporte) == Boolean.TRUE) {
					lstHallazgos.add(crearHallazgo(arguments.getLineNumber(),
							"Total aporte",
							"Total aporte inválido"));
				}
			}
			
			
		} finally {
			((List<ResultadoHallazgosValidacionArchivoDTO>) arguments.getContext()
					.get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS)).addAll(lstHallazgos);
			arguments.getContext().put(ArchivoMultipleCampoConstants.TOTAL_REGISTRO, (long) arguments.getContext().get(ArchivoMultipleCampoConstants.TOTAL_REGISTRO) + 1L);

		}
	}


	private Boolean verificarTipoDocumentoAportante(String tipoDocumento) {

		try {
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
					.obtenerTiposIdentificacionPILAEnum(tipoDocumento);
		} catch (Exception e) {
			return Boolean.TRUE;
		}

		return Boolean.FALSE;

		
	}


	private Boolean verificarTipoDocumentoCotizante(String tipoDocumento) {
		
		try {
			TipoIdentificacionEnum tipoIdentificacion = TipoIdentificacionEnum
					.obtenerTiposIdentificacionPILAEnum(tipoDocumento);
			if (tipoIdentificacion == TipoIdentificacionEnum.NIT) {
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
		
	}


	private Boolean verificarNumeroDocumento(String numeroDocumento) {
		
		if (numeroDocumento.length() > 16) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
		
	}


	private Boolean validarSalarioBasico(String salario) {
		try {
			Double salarioBasico = Double.parseDouble(salario);
			if (salarioBasico < 0) {
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
		
	}



	private Boolean validarDiasCotizados(String dias) {
		try {
			Integer diasCotizados = Integer.parseInt(dias);
			if (diasCotizados < 0 || diasCotizados > 31) {
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
		
	}


	private Boolean validarTarifa(String tarifa) {
		try {
			Double tarifaCotizacion = Double.parseDouble(tarifa);
			if (tarifaCotizacion < 0) {
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
		
		
	}


	private Boolean validarNumeroHorasLaboradas(String horas) {
		try {
			Integer numeroHorasLaboradas = Integer.parseInt(horas);
			if (numeroHorasLaboradas < 0) {
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
		
	}


	private Boolean validarAporteObligatorio(String aporteObligatorio) {
		try {
			Double aporteObligatorioCotizacion = Double.parseDouble(aporteObligatorio);
			if (aporteObligatorioCotizacion < 0) {
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private Boolean validarTotalAporte(String totalAporte) {
		try {
			Double totalAporteCotizacion = Double.parseDouble(totalAporte);
			if (totalAporteCotizacion < 0) {
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}


	private ResultadoHallazgosValidacionArchivoDTO crearHallazgo(Long lineNumber, String campo, String errorMessage) {
		String error = Interpolator.interpolate(ArchivoMultipleCampoConstants.MENSAJE_ERROR_CAMPO, campo, errorMessage);
		ResultadoHallazgosValidacionArchivoDTO hallazgo = new ResultadoHallazgosValidacionArchivoDTO();
		hallazgo.setNumeroLinea(lineNumber);
		hallazgo.setNombreCampo(campo);
		hallazgo.setError(error);
		return hallazgo;
	}




}