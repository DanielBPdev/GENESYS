package com.asopagos.novedades.personas.web.load;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.cargaMultiple.InformacionActualizacionNovedadDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.enumeraciones.personas.GeneroEnum;
import com.asopagos.enumeraciones.personas.NivelEducativoEnum;
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
public class ActualizacionInformacionBeneficiariosPersistLine implements IPersistLine {

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
                    // Se asigna el campo No 1 - Tipo de identificación del beneficiario o miembro del grupo familiar  
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_BENEFICIARIO)) {
                        String tipoIdentificacion = getValorCampo(line, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_BENEFICIARIO)
                                .toUpperCase();
                        TipoIdentificacionEnum tipoIdentEnum = TipoIdentificacionEnum
                                .obtenerTiposIdentificacionPILAEnum(tipoIdentificacion);
                        beneficiarioModeloDTO.setTipoIdentificacion(tipoIdentEnum);
                    }
                    // Se asigna el campo No 2 - Número de identificación del beneficiario o miembro del grupo familiar 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_BENEFICIARIO)) {
                        String numeroIdentificacion = getValorCampo(line, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_BENEFICIARIO);
                        beneficiarioModeloDTO.setNumeroIdentificacion(numeroIdentificacion);
                    }
                    // Se asigna el campo No 3 - Primer apellido del beneficiario o miembro del grupo familiar  
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.PRIMER_APELLIDO_BENEFICIARIO)) {
                        String primerApellido = getValorCampo(line, ArchivoCampoNovedadConstante.PRIMER_APELLIDO_BENEFICIARIO);
                        beneficiarioModeloDTO.setPrimerApellido(primerApellido);
                    }
                    // Se asigna el campo No 4 - Segundo apellido del beneficiario o miembro del grupo familiar 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.SEGUNDO_APELLIDO_BENEFICIARIO)) {
                        String segundoApellido = getValorCampo(line, ArchivoCampoNovedadConstante.SEGUNDO_APELLIDO_BENEFICIARIO);
                        beneficiarioModeloDTO.setSegundoApellido(segundoApellido);
                    }
                    // Se asigna el campo No 5 - Primer nombre del beneficiario o miembro del grupo familiar 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.PRIMER_NOMBRE_BENEFICIARIO)) {
                        String primerNombre = getValorCampo(line, ArchivoCampoNovedadConstante.PRIMER_NOMBRE_BENEFICIARIO);
                        beneficiarioModeloDTO.setPrimerNombre(primerNombre);
                    }
                    // Se asigna el campo No 6 - Segundo nombre del beneficiario o miembro del grupo familiar 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.SEGUNDO_NOMBRE_BENEFICIARIO)) {
                        String segundoNombre = getValorCampo(line, ArchivoCampoNovedadConstante.SEGUNDO_NOMBRE_BENEFICIARIO);
                        beneficiarioModeloDTO.setSegundoNombre(segundoNombre);
                    }
                    // Se asigna el campo No 8 - Fecha de nacimiento del beneficiario o miembro del grupo familiar 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.FECHA_NACIMIENTO_BENEFICIARIO)) {
                        String fechaNacimientoText = getValorCampo(line, ArchivoCampoNovedadConstante.FECHA_NACIMIENTO_BENEFICIARIO);
                        Date fechaNacimiento = CalendarUtils.darFormatoYYYYMMDDGuionDate(fechaNacimientoText);
                        beneficiarioModeloDTO.setFechaNacimiento(fechaNacimiento.getTime());
                    }
                    // Se asigna el campo No 9 - Genero del beneficiario o miembro del grupo familiar 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.GENERO_BENEFICIARIO)) {
                        String genero = getValorCampo(line, ArchivoCampoNovedadConstante.GENERO_BENEFICIARIO);
                        GeneroEnum generoEnum = GetValueUtil.getGeneroCodigo(genero);
                        beneficiarioModeloDTO.setGenero(generoEnum);
                    }
                    // Se asigna el campo No 11 - Nivel educativo 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.NIVEL_EDUCATIVO_BENEFICIARIO)) {
                        String nivelEducativo = getValorCampo(line, ArchivoCampoNovedadConstante.NIVEL_EDUCATIVO_BENEFICIARIO);
                        Integer nivelEducativoInt = Integer.parseInt(nivelEducativo);
                        NivelEducativoEnum nivelEducativoEnum = GetValueUtil.getNivelEducativoEnumByCodigoHomologa(nivelEducativoInt);
                        beneficiarioModeloDTO.setNivelEducativo(nivelEducativoEnum);
                    }
                    informacionActualizacionNovedadDTO.setBeneficiario(beneficiarioModeloDTO);

                    // Se asigna el campo No 7 - Condición especial de pago cuota monetaria 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.CONDICION_ESPECIAL_BENEFICIARIO)) {
                        String condicionEspecial = getValorCampo(line, ArchivoCampoNovedadConstante.CONDICION_ESPECIAL_BENEFICIARIO);
                        informacionActualizacionNovedadDTO.setCondicionEspecialPago(condicionEspecial);
                    }
                    // Se asigna el campo No 10 - Nacionalidad del beneficiario o miembro del grupo familiar 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.NACIONALIDAD_BENEFICIARIO)) {
                        String nacionalidad = getValorCampo(line, ArchivoCampoNovedadConstante.NACIONALIDAD_BENEFICIARIO);
                        informacionActualizacionNovedadDTO.setNacionalidad(nacionalidad);
                    }

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