package com.asopagos.novedades.personas.web.load;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.ResultadoSupervivenciaDTO;
import com.asopagos.enumeraciones.novedades.TipoInconsistenciaANIEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.novedades.constants.ArchivoCampoNovedadConstante;
import com.asopagos.util.GetValueUtil;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;

/**
 * <b>Descripcion:</b> <b>Módulo:</b> Asopagos - HU:498 </br>
 *
 * @author <a href="mailto:jusanchez@heinsohn.com.co"> jusanchez</a>
 */
public class SupervivenciaRegistroNoEncontradoRNECPersistLine implements IPersistLine {

	/**
	 * (non-Javadoc)
	 * 
	 * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#persistLine(java.util.List,
	 *      javax.persistence.EntityManager)
	 */
	@Override
	public void persistLine(List<LineArgumentDTO> lines, EntityManager em) throws FileProcessingException {
		LineArgumentDTO lineArgumentDTO;
		Map<String, Object> line;
		ResultadoSupervivenciaDTO resultadoSupervivenciaDTO = null;
		for (int i = 0; i < lines.size(); i++) {
			lineArgumentDTO = lines.get(i);
			try {
				line = lineArgumentDTO.getLineValues();
				if (line.get(ArchivoCampoNovedadConstante.TIPO_REGISTRO_DOS_ARCH) != null) {
					resultadoSupervivenciaDTO = new ResultadoSupervivenciaDTO();
					if (line.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_DOS_ARCH) != null) {
						String tipoIdentificacion = ((String) line
								.get(ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_DOS_ARCH)).toUpperCase();
						TipoIdentificacionEnum tipoIdentEnum = TipoIdentificacionEnum
								.obtenerTiposIdentificacionPILAEnum(tipoIdentificacion);
						resultadoSupervivenciaDTO.setTipoIdentificacion(tipoIdentEnum);
					}
					if (line.get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_DOS_ARCH) != null) {
						String numIdentificacion = (String) line
								.get(ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_DOS_ARCH);
						resultadoSupervivenciaDTO.setNumeroIdentificacion(numIdentificacion);
					}
					if (line.get(ArchivoCampoNovedadConstante.ESTADO_ANI) != null) {
						String estadoANI = (line.get(ArchivoCampoNovedadConstante.ESTADO_ANI).toString()).toUpperCase();
						TipoInconsistenciaANIEnum tipoInconsistencia = GetValueUtil.getTipoInconsistenciaANIPorDescripcion(estadoANI);
						resultadoSupervivenciaDTO.setTipoInconsistencia(tipoInconsistencia);
					} else {
						resultadoSupervivenciaDTO.setTipoInconsistencia(TipoInconsistenciaANIEnum.NO_ENCONTRADO);
					}
					resultadoSupervivenciaDTO.setIdCargueMultipleSupervivencia(
							(Long) lineArgumentDTO.getContext().get(ArchivoMultipleCampoConstants.ID_CARGUE_MULTIPLE));
					((List<ResultadoSupervivenciaDTO>) lineArgumentDTO.getContext()
							.get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS)).add(resultadoSupervivenciaDTO);
				}
			} catch (Exception e) {
				((List<ResultadoSupervivenciaDTO>) lineArgumentDTO.getContext()
						.get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS)).add(resultadoSupervivenciaDTO);
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
}