package com.asopagos.aportes.masivos.service.load.persistence;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder.In;

import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.ResultadoSupervivenciaDTO;
import com.asopagos.enumeraciones.novedades.TipoInconsistenciaANIEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.util.GetValueUtil;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;
import com.asopagos.aportes.masivos.dto.DatosRadicacionMasivaDTO;
import com.asopagos.rest.exception.ParametroInvalidoExcepcion;
import com.asopagos.aportes.masivos.service.constants.ArchivoCampoMasivoConstante;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;  
import java.util.Date;

public class AportesMasivosDevolucionPersistLine implements IPersistLine {
    	/**
	 * (non-Javadoc)
	 * 
	 * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#persistLine(java.util.List,
	 *      javax.persistence.EntityManager)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void persistLine(List<LineArgumentDTO> lines, EntityManager em) throws FileProcessingException {
		Map<String, Object> line;
		System.out.println("Inicio de persist line devolucion masivos");
		

		for (int i = 0; i < lines.size(); i++) {
			DatosRadicacionMasivaDTO resValidacion = new DatosRadicacionMasivaDTO();
			LineArgumentDTO lineArgumentDTO = lines.get(i);
			List<ResultadoHallazgosValidacionArchivoDTO> hallazgos = ((List<ResultadoHallazgosValidacionArchivoDTO>) lineArgumentDTO
                    .getContext().get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS));
					
			boolean persist = true;
			for(ResultadoHallazgosValidacionArchivoDTO hallazgo : hallazgos){
				if(hallazgo.getNumeroLinea().equals(lineArgumentDTO.getLineNumber())){
					persist = false;
				}
			}
			if(persist){
			try {

				line = lineArgumentDTO.getLineValues();

				
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TIPO_DOC_APORTANTE) != null) {
					// Parsear TipoIdentificacionEnum
					String tipoIdentificacion = line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TIPO_DOC_APORTANTE).toString();
					resValidacion.setTipoIdentificacion(TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(tipoIdentificacion));
					
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_NUMERO_DOC_APORTANTE) != null) {
					// String
					resValidacion.setNumeroIdentificacion(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_NUMERO_DOC_APORTANTE).toString());
					
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_MOTIVO_RAZON_SOCIAL) != null) {
					resValidacion.setRazonSocialAportante(line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_MOTIVO_RAZON_SOCIAL).toString());
					// String
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_PERIODO_PAGO) != null) {
					
					try {
						Date fecha = new Date();
						String sDate1=line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_PERIODO_PAGO).toString();
						System.out.println("Periodo pago: " + sDate1);
						fecha=new SimpleDateFormat("yyyy-MM").parse(sDate1);
						resValidacion.setPeriodoPago(fecha);

					} catch (Exception e) {
						System.out.println("Error en el campo periodo pago");
					}
				}
				if (line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TIPO_APORTANTE) != null) {
					// Parsear TipoSolicitanteMovimientoAporteEnum
					String tipoSolicitante = line.get(ArchivoCampoMasivoConstante.RECAUDO_MASIVO_TIPO_APORTANTE).toString();
					resValidacion.setTipoAportante(TipoSolicitanteMovimientoAporteEnum.valueOf(tipoSolicitante));
					
				}
				
			((List<DatosRadicacionMasivaDTO>) lineArgumentDTO.getContext()
                            .get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS)).add(resValidacion);
				
			} catch (Exception e) {
 					System.out.println("Error AportesMasivosDevolucionPersistLine validate(" + e.getMessage() + ")");
                    e.printStackTrace();
                   ((List<DatosRadicacionMasivaDTO>) lineArgumentDTO.getContext()
                            .get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS)).add(resValidacion);
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
		System.out.println("Error en la carga de aportes masivos");
	    throw new UnsupportedOperationException();
	}
}
