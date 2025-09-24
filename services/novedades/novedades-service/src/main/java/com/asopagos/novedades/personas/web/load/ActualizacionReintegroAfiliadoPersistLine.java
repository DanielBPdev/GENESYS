package com.asopagos.novedades.personas.web.load;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.persistence.EntityManager;
import com.asopagos.constants.ArchivoMultipleCampoConstants;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import com.asopagos.dto.cargaMultiple.InformacionActualizacionNovedadDTO;
import com.asopagos.dto.modelo.AfiliadoModeloDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.novedades.constants.ArchivoCampoNovedadConstante;
import com.asopagos.util.CalendarUtils;
import co.com.heinsohn.lion.fileCommon.dto.LineArgumentDTO;
import co.com.heinsohn.lion.fileprocessing.exception.FileProcessingException;
import co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;

/**
 * <b>Descripcion:</b> Clase que valida la estructura del archivo de Retiro
 * Trabajadores<br/>
 * <b>Módulo:</b> Asopagos - HU-13-XXX<br/>
 *
 * @author <a href="mailto:maria.cuellar@eprocess.com.co">Maria Cuellar</a>
 */
public class ActualizacionReintegroAfiliadoPersistLine implements IPersistLine {

    /**
     * (non-Javadoc)
     *
     * @see
     * co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#persistLine(java.util.List,
     * javax.persistence.EntityManager)
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
                    EmpleadorModeloDTO empleadorDTO = new EmpleadorModeloDTO();

                    // Se asigna el campo No 1 - Tipo de identificación del empleador
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_EMPLEADOR)) {
                        String tipoIdentificacionEmpleador = getValorCampo(line, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_EMPLEADOR)
                                .toUpperCase();
                        TipoIdentificacionEnum tipoIdentEnum = TipoIdentificacionEnum
                                .obtenerTiposIdentificacionPILAEnum(tipoIdentificacionEmpleador);
                        empleadorDTO.setTipoIdentificacion(tipoIdentEnum);
                    }
                    // Se asigna el campo No 2 - Número de identificación del empleador del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR)) {
                        String numeroIdentificacionEmpleador = getValorCampo(line, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR);
                        empleadorDTO.setNumeroIdentificacion(numeroIdentificacionEmpleador);
                    }
                    //No se valida el campo 3 - serial empleador

                    // Se asigna el campo No 4 - Tipo de identificación del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_AFILIADO)) {
                        String tipoIdentificacion = getValorCampo(line, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_AFILIADO)
                                .toUpperCase();
                        TipoIdentificacionEnum tipoIdentEnum = TipoIdentificacionEnum
                                .obtenerTiposIdentificacionPILAEnum(tipoIdentificacion);
                        afiliadoModeloDTO.setTipoIdentificacion(tipoIdentEnum);
                    }
                    // Se asigna el campo No 5 - Número de identificación del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO)) {
                        String numeroIdentificacion = getValorCampo(line, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO);
                        afiliadoModeloDTO.setNumeroIdentificacion(numeroIdentificacion);
                    }
                    
                    // Se asigna el campo No 6 - Sucursal del trabajador o cabeza de familia 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.SUCURSAL_EMPLEADOR)) {
                        String sucursal = getValorCampo(line, ArchivoCampoNovedadConstante.SUCURSAL_EMPLEADOR);
                        afiliadoModeloDTO.setSucursalAfiliado(sucursal);
                    }
                    
                    // Se asigna el campo No 7 - Fecha de inicio afiliacion del trabajador o cabeza de familia 
                    System.out.println("fecha recepcion documentos: antes de");
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.FECHA_RECEPCION_DOCUMENTOS)) {
                        System.out.println("Fecha recepcion documentos: " + getValorCampo(line, ArchivoCampoNovedadConstante.FECHA_RECEPCION_DOCUMENTOS));
                        String fechaRecepcionDocumentosText = getValorCampo(line, ArchivoCampoNovedadConstante.FECHA_RECEPCION_DOCUMENTOS);
                        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date fechaRecepcionDocumentos = formatoFecha.parse(fechaRecepcionDocumentosText);
                            afiliadoModeloDTO.setFechaInicioAfiliacion(fechaRecepcionDocumentos.getTime());
                        } catch (ParseException e) {
                            System.out.println("Error al parsear la fecha: " + e.getMessage());
                        }
                    }
                    // Se asigna el campo No 8 - valor salario mensual del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.VALOR_SALARIO_MENSUAL)) {
                        String salario = getValorCampo(line, ArchivoCampoNovedadConstante.VALOR_SALARIO_MENSUAL);
                        afiliadoModeloDTO.setSalarioAfiliado(salario);
                    }
                    
                    afiliadoModeloDTO.setEmpleador(empleadorDTO);
                    informacionActualizacionNovedadDTO.setAfiliado(afiliadoModeloDTO);

                    ((List<InformacionActualizacionNovedadDTO>) lineArgumentDTO.getContext()
                            .get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS)).add(informacionActualizacionNovedadDTO);
                } catch (Exception e) {
                    System.out.println("Error ActualizacionRetiroAfiliadoPersistLine validate(" + e.getMessage() + ")");
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
     * @see
     * co.com.heinsohn.lion.fileprocessing.fileloader.loader.IPersistLine#setRollback(javax.persistence.EntityManager)
     */
    @Override
    public void setRollback(EntityManager em) throws FileProcessingException {
        throw new UnsupportedOperationException();
    }
}
