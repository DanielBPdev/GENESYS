package com.asopagos.fovis.cruce.web.load;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.InformacionHojaCruceFovisDTO;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.fovis.constants.FileFieldConstants;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;

/**
 * <b>Descripcion:</b> <b>Módulo:</b> Asopagos - HU:241 </br>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co"> Jose Arley Correa</a>
 */
public class CruceHojaCatastroCaliPersistLine implements IPersistLine {

    /**
     * (non-Javadoc)
     * 
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#persistLine(java.util.List,
     *      javax.persistence.EntityManager)
     */
    /* (non-Javadoc)
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
                    // Se valida el campo No 2 - NOMBRE ENTIDAD
                    if (esCampoValido(line, FileFieldConstants.NOMBRE_ENTIDAD)) {
                        String nombreEntidad = getValorCampo(line, FileFieldConstants.NOMBRE_ENTIDAD);
                        informacionHojaCruceFovisDTO.setNombreEntidad(nombreEntidad);
                    }
                    // Se valida el campo No 3 - IDENTIFICACIÓN
                    if (esCampoValido(line, FileFieldConstants.IDENTIFICACION)) {
                        String identificacion = getValorCampo(line, FileFieldConstants.IDENTIFICACION);
                        informacionHojaCruceFovisDTO.setIdentificacion(identificacion);
                    }
                    // Se valida el campo No 4 - APELLIDOS_NOMBRES
                    if (esCampoValido(line, FileFieldConstants.APELLIDOS_NOMBRES)) {
                        String apellidosNombres = getValorCampo(line, FileFieldConstants.APELLIDOS_NOMBRES);
                        informacionHojaCruceFovisDTO.setApellidosNombres(apellidosNombres);
                    }
                    // Se valida el campo No 5 - MATRICULA INMOBILIARIA
                    if (esCampoValido(line, FileFieldConstants.MATRICULA_INMOBILIARIA)) {
                        String matricula = getValorCampo(line, FileFieldConstants.MATRICULA_INMOBILIARIA);
                        informacionHojaCruceFovisDTO.setMatriculaInmobiliaria(matricula);
                    }
                    // Se valida el campo No 6 - DEPTO.
                    if (esCampoValido(line, FileFieldConstants.DEPARTAMENTO)) {
                        String depto = getValorCampo(line, FileFieldConstants.DEPARTAMENTO);
                        informacionHojaCruceFovisDTO.setDepartamento(depto);
                    }
                    // Se valida el campo No 7 - MPIO.
                    if (esCampoValido(line, FileFieldConstants.MUNICIPIO)) {
                        String municipio = getValorCampo(line, FileFieldConstants.MUNICIPIO);
                        informacionHojaCruceFovisDTO.setMunicipio(municipio);
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