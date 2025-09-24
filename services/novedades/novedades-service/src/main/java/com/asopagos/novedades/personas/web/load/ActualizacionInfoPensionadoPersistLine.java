package com.asopagos.novedades.personas.web.load;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.cargaMultiple.InformacionActualizacionNovedadDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.entidades.transversal.personas.AFP;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.novedades.constants.ArchivoCampoNovedadConstante;
import com.asopagos.rest.exception.TechnicalException;
import com.asopagos.util.GetValueUtil;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;

/**
 * <b>Descripcion:</b> <b>Módulo:</b> Asopagos - HU:241 </br>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co"> Jose Arley Correa</a>
 */
public class ActualizacionInfoPensionadoPersistLine implements IPersistLine {

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

                    // Se valida el campo No 3 - Nombres
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.NOMBRES_BENEFICIARIO)) {
                        String nombres = getValorCampo(line, ArchivoCampoNovedadConstante.NOMBRES_BENEFICIARIO);
                        beneficiarioModeloDTO.setPrimerNombre(nombres);
                    }

                    // Se valida el campo No 4 - Apellidos 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.APELLIDOS_BENEFICIARIO)) {
                        String apellidos = getValorCampo(line, ArchivoCampoNovedadConstante.APELLIDOS_BENEFICIARIO);
                        beneficiarioModeloDTO.setPrimerApellido(apellidos);
                    }

                    // Se valida el campo No 5 - Administradora de pensiones 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.ADMINISTRADOR_PENSION)) {
                        String codigoAFP = getValorCampo(line, ArchivoCampoNovedadConstante.ADMINISTRADOR_PENSION);
                        List<AFP> listAFP = ((List<AFP>) lineArgumentDTO.getContext().get(ArchivoMultipleCampoConstants.LISTA_AFP));
                        AFP afp = GetValueUtil.getAFPByCodigoPila(listAFP, codigoAFP);
                        informacionActualizacionNovedadDTO.setAdministradoraPension(afp);
                    }

                    // Se valida el campo No 6 - ¿Persona tiene condición de pensionado? 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.ES_PENSIONADO)) {
                        String esPensionadoStr = getValorCampo(line, ArchivoCampoNovedadConstante.ES_PENSIONADO);
                        boolean esPensionado = false;
                        if (esPensionadoStr.toUpperCase().equals("SI")) {
                            esPensionado = true;
                        }
                        informacionActualizacionNovedadDTO.setPensionado(esPensionado);
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