package com.asopagos.fovis.cruce.web.load;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.InformacionHojaCruceFovisDTO;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.fovis.constants.FileFieldConstants;
import com.asopagos.util.CalendarUtils;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;

/**
 * <b>Descripcion:</b> <b>Módulo:</b> Asopagos - HU:241 </br>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co"> Jose Arley Correa</a>
 */
public class CruceHojaFechaCortePersistLine implements IPersistLine {

    /**
     * (non-Javadoc)
     * 
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#persistLine(java.util.List,
     *      javax.persistence.EntityManager)
     */
    /*
     * (non-Javadoc)
     * 
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#persistLine(java.util.List, javax.persistence.EntityManager)
     */
    @SuppressWarnings("unchecked")
    public void persistLine(List<LineArgumentDTO> lines, EntityManager em) throws FileProcessingException {
        Map<String, Object> line;
        InformacionHojaCruceFovisDTO informacionHojaCruceFovisDTO = null;
        for (int i = 1; i < lines.size(); i++) {
            LineArgumentDTO lineArgumentDTO = lines.get(i);
            List<ResultadoHallazgosValidacionArchivoDTO> hallazgos = ((List<ResultadoHallazgosValidacionArchivoDTO>) lineArgumentDTO
                    .getContext().get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS));
            if (hallazgos.isEmpty()) {
                try {
                    line = lineArgumentDTO.getLineValues();
                    // Contiene la informacion de la linea del archivo para la asignacion de diferencias
                    informacionHojaCruceFovisDTO = new InformacionHojaCruceFovisDTO();

                    // Se valida el campo No 1 - NIT ENTIDAD
                    if (esCampoValido(line, FileFieldConstants.NIT_ENTIDAD)) {
                        String nitEntidad = getValorCampo(line, FileFieldConstants.NIT_ENTIDAD);
                        informacionHojaCruceFovisDTO.setNitEntidad(nitEntidad);
                    }
                    // Se valida el campo No 2 - ENTIDAD
                    if (esCampoValido(line, FileFieldConstants.ENTIDAD)) {
                        String entidad = getValorCampo(line, FileFieldConstants.ENTIDAD);
                        informacionHojaCruceFovisDTO.setNombreEntidad(entidad);
                    }
                    // Se valida el campo No 3 - TIPO INFORMACION
                    if (esCampoValido(line, FileFieldConstants.TIPO_INFORMACION)) {
                        String tipoInformacion = getValorCampo(line, FileFieldConstants.TIPO_INFORMACION);
                        informacionHojaCruceFovisDTO.setTipoInformacion(tipoInformacion);
                    }
                    // Se valida el campo No 4 - FECHA DE CORTE
                    if (esCampoValido(line, FileFieldConstants.FECHA_CORTE)) {
                        String fechaCorte = getValorCampo(line, FileFieldConstants.FECHA_CORTE);
                        informacionHojaCruceFovisDTO.setFechaCorte(CalendarUtils.darFormatoDDMMYYYYSlashDate(fechaCorte));
                    }
                    // Se valida el campo No 5 - FECHA DE ACTUALIZACION
                    if (esCampoValido(line, FileFieldConstants.FECHA_ACTUALIZACION)) {
                        String fechaActualizacion = getValorCampo(line, FileFieldConstants.FECHA_ACTUALIZACION);
                        informacionHojaCruceFovisDTO.setFechaActualizacion(CalendarUtils.darFormatoDDMMYYYYSlashDate(fechaActualizacion));
                    }

                    ((List<InformacionHojaCruceFovisDTO>) lineArgumentDTO.getContext().get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS))
                            .add(informacionHojaCruceFovisDTO);
                } catch (Exception e) {
                    ((List<InformacionHojaCruceFovisDTO>) lineArgumentDTO.getContext().get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS))
                            .add(informacionHojaCruceFovisDTO);
                }
            }
        }
    }

    /**
     * @param line
     * @param nombreCampo
     * @return
     */
    private Boolean esCampoValido(Map<String, Object> line, String nombreCampo) {
        if (line.get(nombreCampo) != null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * @param line
     * @param nombreCampo
     * @return
     */
    private String getValorCampo(Map<String, Object> line, String nombreCampo) {
        return ((String) line.get(nombreCampo)).trim();
    }

    /**
     * (non-Javadoc)
     * 
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#setRollback(javax.persistence.EntityManager)
     */
    @Override
    public void setRollback(EntityManager em) throws FileProcessingException {

    }
}