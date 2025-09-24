package com.asopagos.aportes.masivos.service.load.persistence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.aportes.masivos.service.constants.CamposArchivoConstants;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.locator.ResourceLocator;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;

import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;
import com.asopagos.aportes.masivos.dto.AportanteCruceCierreDTO;
import com.asopagos.enumeraciones.aportes.TipoAportanteEnum;
/**
 * <b>Descripcion:</b> Clase que contiene la implementación requerida para realizar la persistencia de la información
 * obtenida del archivo de descuentos
 * <br/>
 * <b>Módulo:</b> Asopagos - HU 311-432<br/>
 *
 * @author <a href="mailto:rlopez@heinsohn.com.co"> Roy López Cardona</a>
 */
public class ArchivoCrucesAportesPersistLine implements IPersistLine {
    
    /**
     * Referencia al logger
     */
    final ILogger logger = LogManager.getLogger(ArchivoCrucesAportesPersistLine.class);
    
    /**
     * Lista de identificadores asociados a los registros del archivo
     */
    private List<Long> identificadoresRegistros = new ArrayList<>();

    /**
     * (non-Javadoc)
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#persistLine(java.util.List, javax.persistence.EntityManager)
     */
    @SuppressWarnings("unchecked")
	@Override
    public void persistLine(List<LineArgumentDTO> arg0, EntityManager emCore) throws FileProcessingException {
        logger.debug("Inicia persistLine(List<LineArgumentDTO>, EntityManager)");
        
        
        for (LineArgumentDTO lineArgumentDTO : arg0) {
            try {
            	
            	 List<ResultadoHallazgosValidacionArchivoDTO> hallazgos = ((List<ResultadoHallazgosValidacionArchivoDTO>) lineArgumentDTO
                         .getContext().get(CamposArchivoConstants.LISTA_HALLAZGOS));

                 boolean errorLinea = false;

                 for (int i = 0; i < hallazgos.size(); i++) {

                     if (lineArgumentDTO.getLineNumber() == hallazgos.get(i).getNumeroLinea().longValue()) {
                         errorLinea = true;
                         break;
                     }

                 }
                if (!errorLinea) {
                	 Map<String, Object> values = lineArgumentDTO.getLineValues();

                     AportanteCruceCierreDTO  aportanteCruceCierreDTO  = new AportanteCruceCierreDTO ();
                     aportanteCruceCierreDTO.setTipoIdentificacion(TipoIdentificacionEnum.obtenerTiposIdentificacionPILAEnum(values.get(CamposArchivoConstants.TIPO_IDENTIFICACION_APORTANTE).toString()));
                     aportanteCruceCierreDTO.setNumeroIdentificacion(values.get(CamposArchivoConstants.NUMERO_IDENTIFICACION_APORTANTE).toString());
                     aportanteCruceCierreDTO.setNombre(values.get(CamposArchivoConstants.RAZON_SOCIAL).toString());
                     aportanteCruceCierreDTO.setTipoAportante(values.get(CamposArchivoConstants.TIPO_APORTANTE).toString());
    ((List<AportanteCruceCierreDTO>) lineArgumentDTO.getContext().get(CamposArchivoConstants.LISTA_APORTANTES_CRUCE_APORTES)).add(aportanteCruceCierreDTO);
                }
            } catch (Exception e) {
                    System.out.println("**__** Error  "+e );
                FileProcessingException e1 = new FileProcessingException("Error en Modelar los datos en  AportanteCruceCierreDTO", e);
                e1.setLineArgumentDTO(lineArgumentDTO);
                throw e;
            }
        }
    }
    	/**
	 * (non-Javadoc)
	 * 
	 * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#setRollback(javax.persistence.EntityManager)
	 */

	public void setRollback(EntityManager em) throws FileProcessingException {
		// TODO Auto-generated method stub
	}


}
