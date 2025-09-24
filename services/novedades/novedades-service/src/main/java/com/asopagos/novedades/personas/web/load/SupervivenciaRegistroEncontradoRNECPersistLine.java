package com.asopagos.novedades.personas.web.load;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.ResultadoSupervivenciaDTO;
import com.asopagos.enumeraciones.novedades.TipoInconsistenciaANIEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;
import com.asopagos.novedades.constants.ArchivoCampoNovedadConstante;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.GetValueUtil;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;
import com.asopagos.dto.fovis.PostulacionDTO;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.fovis.clients.ConsultarHistoricoPostulaciones;
import com.asopagos.legalizacionfovis.clients.ValidarSolicitudesLegalizacionYDesembolsoCerrado;

/**
 * <b>Descripcion:</b> <b>Módulo:</b> Asopagos - HU:498 </br>
 *
 * @author <a href="mailto:jusanchez@heinsohn.com.co"> jusanchez</a>
 */
public class SupervivenciaRegistroEncontradoRNECPersistLine implements IPersistLine {

	/**
	 * (non-Javadoc)
	 * 
	 * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#persistLine(java.util.List,
	 *      javax.persistence.EntityManager)
	 */
	public void persistLine(List<LineArgumentDTO> lines, EntityManager em) throws FileProcessingException {
		Map<String, Object> line;
		ResultadoSupervivenciaDTO resultadoSupervivenciaDTO = null;
		System.out.println("**__**Inicia SupervivenciaRegistroEncontradoRNECPersistLine ");
		for (int i = 0; i < lines.size(); i++) {
			LineArgumentDTO lineArgumentDTO = lines.get(i);
			try {
				line = lineArgumentDTO.getLineValues();
				System.out.println("**__** SupervivenciaRegistroEncontradoRNECPersistLine " + line.get(i));
				if (line.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_TRES) != null) {
					resultadoSupervivenciaDTO = new ResultadoSupervivenciaDTO();
					if (line.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_CONSULTAR_TRES) != null) {
						String tipoIdentificacion = ((String) line
								.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_CONSULTAR_TRES)).toUpperCase();
						TipoIdentificacionEnum tipoIdentEnum = TipoIdentificacionEnum
								.obtenerTiposIdentificacionPILAEnum(tipoIdentificacion);
						resultadoSupervivenciaDTO.setTipoIdentificacion(tipoIdentEnum);
					}
					if (line.get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_TRES) != null) {
						String numIdentificacion = (String) line
								.get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_TRES);
						resultadoSupervivenciaDTO.setNumeroIdentificacion(numIdentificacion);
					}
					if (line.get(ArchivoCampoNovedadConstante.ESTADO_ANI) != null) {
						try {
							String estadoANI = ((String) line.get(ArchivoCampoNovedadConstante.ESTADO_ANI))
									.toUpperCase();
							TipoInconsistenciaANIEnum tipoInconsistencia = GetValueUtil
									.getTipoInconsistenciaANIPorDescripcion(estadoANI);
							resultadoSupervivenciaDTO.setTipoInconsistencia(tipoInconsistencia);
						} catch (Exception e) {
							resultadoSupervivenciaDTO.setTipoInconsistencia(TipoInconsistenciaANIEnum.OTROS_CASOS);
						}
					}
					/*
					 * 
					 * if (resultadoSupervivenciaDTO.getTipoInconsistencia().equals(
					 * TipoInconsistenciaANIEnum.CANCELADO_POR_MUERTE)
					 * || resultadoSupervivenciaDTO.getTipoInconsistencia().equals(
					 * TipoInconsistenciaANIEnum.CANCELADO_POR_MUERTE_LEY_1365_2009)) {
					 * resultadoSupervivenciaDTO = validarEstadoPostulacionFovis3(line,
					 * resultadoSupervivenciaDTO);
					 * }
					 */

					Date fFechaDefuncionDate = new Date();
					String fDefuncionStr = "";

					if (line.get(ArchivoCampoNovedadConstante.FECHA_DEFUNCION) != null
							&& line.get(ArchivoCampoNovedadConstante.FECHA_DEFUNCION).toString() != "") {
						fDefuncionStr = (String) line.get(ArchivoCampoNovedadConstante.FECHA_DEFUNCION);
						System.out.println("Fecha tipo 3: " + fDefuncionStr);
						fFechaDefuncionDate = CalendarUtils.darFormatoYYYYMMDDGuionDate(fDefuncionStr);
					} else if (line.get(ArchivoCampoNovedadConstante.FECHA_EXPEDICION_MSG) != null
							&& line.get(ArchivoCampoNovedadConstante.FECHA_EXPEDICION_MSG).toString() != "") {
						fDefuncionStr = (String) line.get(ArchivoCampoNovedadConstante.FECHA_EXPEDICION_MSG);
						System.out.println("Fecha tipo 3: " + fDefuncionStr);
						fFechaDefuncionDate = CalendarUtils.darFormatoYYYYMMDDGuionDate(fDefuncionStr);
					}
					if (fFechaDefuncionDate == null) {
						fFechaDefuncionDate = new Date();
					}
					resultadoSupervivenciaDTO.setFechaDefuncion(fFechaDefuncionDate.getTime());
					resultadoSupervivenciaDTO.setIdCargueMultipleSupervivencia(
							(Long) lineArgumentDTO.getContext().get(ArchivoMultipleCampoConstants.ID_CARGUE_MULTIPLE));
					if (!resultadoSupervivenciaDTO.getTipoInconsistencia().equals(TipoInconsistenciaANIEnum.VIGENTE)) {

						((List<ResultadoSupervivenciaDTO>) lineArgumentDTO.getContext()
								.get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS)).add(resultadoSupervivenciaDTO);
					}
				} else if (line.get(ArchivoCampoNovedadConstante.TIPO_REGISTRO_DOS) != null) {
					resultadoSupervivenciaDTO = new ResultadoSupervivenciaDTO();
					Date fFechaDefuncionDate = new Date();
					String fDefuncionStr = "";
					resultadoSupervivenciaDTO.setTipoInconsistencia(TipoInconsistenciaANIEnum.CANCELADO_POR_MUERTE);

					if (line.get(ArchivoCampoNovedadConstante.FECHA_DEFUNCION) != null
							&& line.get(ArchivoCampoNovedadConstante.FECHA_DEFUNCION).toString() != "") {
						fDefuncionStr = (String) line.get(ArchivoCampoNovedadConstante.FECHA_DEFUNCION);
						System.out.println("Fecha tipo 2: " + fDefuncionStr);
						fFechaDefuncionDate = CalendarUtils.darFormatoYYYYMMDDGuionDate(fDefuncionStr);
					} else if (line.get(ArchivoCampoNovedadConstante.FECHA_EXP_REGISTRO_CIVIL) != null
							&& line.get(ArchivoCampoNovedadConstante.FECHA_EXP_REGISTRO_CIVIL).toString() != "") {
						fDefuncionStr = (String) line.get(ArchivoCampoNovedadConstante.FECHA_EXP_REGISTRO_CIVIL);
						System.out.println("Fecha tipo 2: " + fDefuncionStr);
						fFechaDefuncionDate = CalendarUtils.darFormatoYYYYMMDDGuionDate(fDefuncionStr);
					}
					if (fFechaDefuncionDate == null) {
						fFechaDefuncionDate = new Date();
					}
					resultadoSupervivenciaDTO.setFechaDefuncion(fFechaDefuncionDate.getTime());
					if (line.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_CONSULTAR_DOS) != null) {

						String tipoIdentificacion = ((String) line
								.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_CONSULTAR_DOS)).toUpperCase();
						System.out.println("tipoIdentificacion 2: " + tipoIdentificacion);
						TipoIdentificacionEnum tipoIdentEnum = TipoIdentificacionEnum
								.obtenerTiposIdentificacionPILAEnum(tipoIdentificacion);
						resultadoSupervivenciaDTO.setTipoIdentificacion(tipoIdentEnum);
					}
					if (line.get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_DOS) != null) {
						String numIdentificacion = (String) line
								.get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_DOS);
						System.out.println("numIdentificacion 2: " + numIdentificacion);
						resultadoSupervivenciaDTO.setNumeroIdentificacion(numIdentificacion);
					}
					System.out.println("id multiple: " + lineArgumentDTO.getContext()
							.get(ArchivoMultipleCampoConstants.ID_CARGUE_MULTIPLE).toString());
					resultadoSupervivenciaDTO.setIdCargueMultipleSupervivencia(
							(Long) lineArgumentDTO.getContext().get(ArchivoMultipleCampoConstants.ID_CARGUE_MULTIPLE));
					/*
					 * 
					 * resultadoSupervivenciaDTO = verificarEstadoPostulacionFovis2(line,
					 * resultadoSupervivenciaDTO);
					 */
					if (!resultadoSupervivenciaDTO.getTipoInconsistencia().equals(TipoInconsistenciaANIEnum.VIGENTE)) {
						System.out.println("Agrega a la lista de candidatos");
						((List<ResultadoSupervivenciaDTO>) lineArgumentDTO.getContext()
								.get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS)).add(resultadoSupervivenciaDTO);
					}

				}
			} catch (Exception e) {
				System.out.println("Error en SupervivenciaRegistroEncontradoRNECPersistLine " + e.getMessage());
				if (!resultadoSupervivenciaDTO.getTipoInconsistencia().equals(TipoInconsistenciaANIEnum.VIGENTE)) {

					((List<ResultadoSupervivenciaDTO>) lineArgumentDTO.getContext()
							.get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS)).add(resultadoSupervivenciaDTO);
				}
			}
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#setRollback(javax.persistence.EntityManager)
	 */
	@Override
	public void setRollback(EntityManager em) throws FileProcessingException {
		throw new UnsupportedOperationException();
	}

	private ResultadoSupervivenciaDTO validarEstadoPostulacionFovis3(Map<String, Object> line,
			ResultadoSupervivenciaDTO resultadoSupervivenciaDTO) {
		if (line.get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_TRES) != null
				&& line.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_CONSULTAR_TRES) != null) {
			String numeroIdentificacion = (String) line
					.get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_TRES);
			String tipoIdentificacion = ((String) line
					.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_CONSULTAR_TRES)).toUpperCase();
			TipoIdentificacionEnum tipoIdentEnum = TipoIdentificacionEnum
					.obtenerTiposIdentificacionPILAEnum(tipoIdentificacion);

			if (validarEstadoFovis(numeroIdentificacion, tipoIdentEnum)) {
				resultadoSupervivenciaDTO.setTipoInconsistencia(TipoInconsistenciaANIEnum.OTROS_CASOS);
			}
		}
		System.out.println("Finaliza validarEstadoPostulacionFovis3");
		return resultadoSupervivenciaDTO;

	}

	private ResultadoSupervivenciaDTO verificarEstadoPostulacionFovis2(Map<String, Object> line,
			ResultadoSupervivenciaDTO resultadoSupervivenciaDTO) {
		if (line.get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_DOS) != null
				&& line.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_CONSULTAR_DOS) != null) {
			String numeroIdentificacion = (String) line
					.get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_CONSULTAR_DOS);
			String tipoIdentificacion = ((String) line
					.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_CONSULTAR_DOS)).toUpperCase();
			TipoIdentificacionEnum tipoIdentEnum = TipoIdentificacionEnum
					.obtenerTiposIdentificacionPILAEnum(tipoIdentificacion);

			if (validarEstadoFovis(numeroIdentificacion, tipoIdentEnum)) {
				resultadoSupervivenciaDTO.setTipoInconsistencia(TipoInconsistenciaANIEnum.OTROS_CASOS);
			} else {
				resultadoSupervivenciaDTO.setTipoInconsistencia(TipoInconsistenciaANIEnum.CANCELADO_POR_MUERTE);
			}
		}
		System.out.println("Finaliza validarEstadoPostulacionFovis2");
		return resultadoSupervivenciaDTO;
	}

	/**
	 * Valida si tiene alguna postulacion fovis diferente al estado
	 * return : True si existe alguna postulacion vigente o solicitudes de
	 * legalizacion y desembolso
	 * return : False si no tiene ninguna postulacion con esos estados y se puede
	 * readicar la solicitud de fallecimietnio
	 */
	public Boolean validarEstadoFovis(String numeroIdentificacion, TipoIdentificacionEnum tipoIdentificacion) {
		ConsultarHistoricoPostulaciones c = new ConsultarHistoricoPostulaciones(numeroIdentificacion,
				tipoIdentificacion);
		c.execute();
		List<PostulacionDTO> postulaciones = c.getResult();
		if (postulaciones.isEmpty()) {
			return Boolean.FALSE;
		}
		for (PostulacionDTO postulacion : postulaciones) {
			if (!postulacion.getEstadoHogar().equals(EstadoHogarEnum.SUBSIDIO_DESEMBOLSADO)) {
				return Boolean.TRUE;
			}
			// Validar solicitudes de legalizacion y desembolso
			ValidarSolicitudesLegalizacionYDesembolsoCerrado val = new ValidarSolicitudesLegalizacionYDesembolsoCerrado(
					postulacion.getIdPostulacionFovis());
			val.execute();
			// if es true entonces no tiene ninguna solicitud de legalizacion
			if (!val.getResult()) {
				return Boolean.TRUE;

			}
		}
		return Boolean.FALSE;
	}
}