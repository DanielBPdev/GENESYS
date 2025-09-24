package com.asopagos.novedades.personas.web.load;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.cargaMultiple.InformacionActualizacionNovedadDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.entidades.transversal.personas.GradoAcademico;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.novedades.constants.ArchivoCampoNovedadConstante;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.util.CalendarUtils;
import com.asopagos.util.GetValueUtil;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;

/**
 * <b>Descripcion:</b> <b>Módulo:</b> Asopagos - HU:241 </br>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co"> Jose Arley Correa</a>
 */
public class ActualizacionInfoCertificadoEscorlarBeneficiarioPersistLine implements IPersistLine {

    /**
     * (non-Javadoc)
     * 
     * @see co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#persistLine(java.util.List,
     *      javax.persistence.EntityManager)
     */
    @SuppressWarnings("unchecked")
    public void persistLine(List<LineArgumentDTO> lines, EntityManager em) throws FileProcessingException {
        Map<String, Object> line;
        InformacionActualizacionNovedadDTO informacionActualizacionNovedadDTO = null;
        for (int i = 0; i < lines.size(); i++) {
            LineArgumentDTO lineArgumentDTO = lines.get(i);
            List<ResultadoHallazgosValidacionArchivoDTO> hallazgos = ((List<ResultadoHallazgosValidacionArchivoDTO>) lineArgumentDTO
                    .getContext().get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS));
            if (hallazgos.isEmpty()) {
                try {
                    line = lineArgumentDTO.getLineValues();
                    // Contiene la informacion de la linea del archivo para la asignacion de diferencias
                    informacionActualizacionNovedadDTO = new InformacionActualizacionNovedadDTO();

                    // Informacion basica beneficiario
                    BeneficiarioModeloDTO beneficiarioModeloDTO = new BeneficiarioModeloDTO();

                    // Se valida el campo No 1 - Tipo de identificación del beneficiario 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION)) {
                        String tipoIdentificacion = getValorCampo(line, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION).toUpperCase();
                        TipoIdentificacionEnum tipoIdentEnum = TipoIdentificacionEnum
                                .obtenerTiposIdentificacionPILAEnum(tipoIdentificacion);
                        beneficiarioModeloDTO.setTipoIdentificacion(tipoIdentEnum);
                    }

                    // Se valida el campo No 2 - Número de identificación del beneficiario 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION)) {
                        String numeroIdentificacion = getValorCampo(line, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION);
                        beneficiarioModeloDTO.setNumeroIdentificacion(numeroIdentificacion);
                    }

                    // Se valida el campo No 3 - Fecha inicio certificado escolar
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.FECHA_INICIO_CERTIFICADO_ESCOLAR)) {
                        String fechaInicioCertificadoEscolarText = getValorCampo(line,
                                ArchivoCampoNovedadConstante.FECHA_INICIO_CERTIFICADO_ESCOLAR);
                        Date fechaInicioCertificadoEscolar = CalendarUtils.darFormatoYYYYMMDDGuionDate(fechaInicioCertificadoEscolarText);
                        beneficiarioModeloDTO.setFechaRecepcionCertificadoEscolar(fechaInicioCertificadoEscolar.getTime());
                    }

                    // Se valida el campo No 4 - Fecha fin certificado escolar
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.FECHA_FIN_CERTIFICADO_ESCOLAR)) {
                        String fechaFinCertificadoEscolarText = getValorCampo(line,
                                ArchivoCampoNovedadConstante.FECHA_FIN_CERTIFICADO_ESCOLAR);
                        Date fechaFinCertificadoEscolar = CalendarUtils.darFormatoYYYYMMDDGuionDate(fechaFinCertificadoEscolarText);
                        beneficiarioModeloDTO.setFechaVencimientoCertificadoEscolar(fechaFinCertificadoEscolar.getTime());
                    }

                    // Se valida el campo No 5 - Gardo cursado
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.GRADO_ACADEMICO_CURSADO)) {
                        String strGradoCursado = getValorCampo(line, ArchivoCampoNovedadConstante.GRADO_ACADEMICO_CURSADO);
                        List<GradoAcademico> listGradoCursado = (List<GradoAcademico>) lineArgumentDTO.getContext()
                                .get(ArchivoMultipleCampoConstants.LISTA_GRADOS_ACADEMICOS);
                        GradoAcademico gradoAcademico = GetValueUtil.getGradoAcademico(listGradoCursado, strGradoCursado.toUpperCase());
                        beneficiarioModeloDTO.setGradoAcademicoBeneficiario(gradoAcademico.getIdgradoAcademico());
                        beneficiarioModeloDTO.setNivelEducativo(gradoAcademico.getNivelEducativo());
                    }
                    informacionActualizacionNovedadDTO.setBeneficiario(beneficiarioModeloDTO);

                    ((List<InformacionActualizacionNovedadDTO>) lineArgumentDTO.getContext()
                            .get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS)).add(informacionActualizacionNovedadDTO);
                } catch (Exception e) {
                    throw new TechnicalException(e);
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
        return ((String) line.get(nombreCampo));
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