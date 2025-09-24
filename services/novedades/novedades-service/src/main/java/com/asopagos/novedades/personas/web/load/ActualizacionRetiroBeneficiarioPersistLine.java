package com.asopagos.novedades.personas.web.load;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.cargaMultiple.InformacionActualizacionNovedadDTO;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.novedades.constants.ArchivoCampoNovedadConstante;
import com.asopagos.util.CalendarUtils;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;

/**
 * <b>Descripcion:</b> <b>Módulo:</b> Asopagos - HU:241 </br>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co"> Jose Arley Correa</a>
 */
public class ActualizacionRetiroBeneficiarioPersistLine implements IPersistLine {

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
            //Cambio esta validacion para que retorne los registros validos sin importar si hay hallazgos.
            boolean persist = true;
            if (!hallazgos.isEmpty() && hallazgos.stream().anyMatch(iteHallazgo -> 
                    iteHallazgo.getNumeroLinea().equals(lineArgumentDTO.getLineNumber()))) {
                persist = false;
            }
            
            if (persist) {
                try {
                    line = lineArgumentDTO.getLineValues();
                    // Contiene la informacion de la linea del archivo para la asignacion de diferencias
                    informacionActualizacionNovedadDTO = new InformacionActualizacionNovedadDTO();

                    // Informacion basica afiliado
                    AfiliadoModeloDTO afiliadoModeloDTO = new AfiliadoModeloDTO();
                    BeneficiarioModeloDTO beneficiarioDTO = new BeneficiarioModeloDTO();
                    
                    // Se asigna el campo No 1 - Tipo de identificación del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_AFILIADO)) {
                        String tipoIdentificacion = getValorCampo(line, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_AFILIADO)
                                .toUpperCase();
                        TipoIdentificacionEnum tipoIdentEnum = TipoIdentificacionEnum
                                .obtenerTiposIdentificacionPILAEnum(tipoIdentificacion);
                        afiliadoModeloDTO.setTipoIdentificacion(tipoIdentEnum);
                    }
                    // Se asigna el campo No 2 - Número de identificación del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO)) {
                        String numeroIdentificacion = getValorCampo(line, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO);
                        afiliadoModeloDTO.setNumeroIdentificacion(numeroIdentificacion);
                    }
                    // Se asigna el campo No 3 - Tipo de identificación del beneficiario
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_BENEFICIARIO)) {
                        String tipoIdentificacionEmpleador = getValorCampo(line, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_BENEFICIARIO)
                                .toUpperCase();
                        TipoIdentificacionEnum tipoIdentEnum = TipoIdentificacionEnum
                                .obtenerTiposIdentificacionPILAEnum(tipoIdentificacionEmpleador);
                        beneficiarioDTO.setTipoIdentificacion(tipoIdentEnum);
                    }
                    // Se asigna el campo No 4 - Número de identificación del beneficiario del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_BENEFICIARIO)) {
                        String numeroIdentificacionEmpleador = getValorCampo(line, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_BENEFICIARIO);
                        beneficiarioDTO.setNumeroIdentificacion(numeroIdentificacionEmpleador);
                    }                  
                    // Se asigna el campo No 5 - Fecha retiro del trabajador o cabeza de familia 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.FECHA_RETIRO_BENEFICIARIO)) {
                        String fechaRetiroBeneficiarioText = getValorCampo(line, ArchivoCampoNovedadConstante.FECHA_RETIRO_BENEFICIARIO);
                        Date fechaRetiroBeneficiario = CalendarUtils.darFormatoYYYYMMDDGuionDate(fechaRetiroBeneficiarioText);
                        afiliadoModeloDTO.setFechaRetiroAfiliado(fechaRetiroBeneficiario.getTime());
                    }
                    // Se asigna el campo No 6 - Motivo retiro del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.MOTIVO_RETIRO_BENEFICIARIO)) {
                        String motivoRetiroBeneficiario = getValorCampo(line, ArchivoCampoNovedadConstante.MOTIVO_RETIRO_BENEFICIARIO)
                                .toUpperCase();
                        MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacionBeneficiarioEnum = 
                                MotivoDesafiliacionBeneficiarioEnum.valueOf(motivoRetiroBeneficiario);                        
                        afiliadoModeloDTO.setMotivoDesafiliacionBeneficiario(motivoDesafiliacionBeneficiarioEnum);                        
                    }
                    
                    informacionActualizacionNovedadDTO.setBeneficiario(beneficiarioDTO);
                    informacionActualizacionNovedadDTO.setAfiliado(afiliadoModeloDTO);

                    ((List<InformacionActualizacionNovedadDTO>) lineArgumentDTO.getContext()
                            .get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS)).add(informacionActualizacionNovedadDTO);
                } catch (Exception e) {
                    System.out.println("Error ActualizacionRetiroBeneficiarioPersistLine validate("+ e.getMessage() +")");
                    e.printStackTrace();
                    ((List<InformacionActualizacionNovedadDTO>) lineArgumentDTO.getContext()
                            .get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS)).add(informacionActualizacionNovedadDTO);
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