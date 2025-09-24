package com.asopagos.novedades.personas.web.load;

import java.util.Date;
import java.util.List;
import java.util.Map;
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
import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;

/**
 * <b>Descripcion:</b> Clase que valida la estructura del archivo de Sustitucion Patronal<br/>
 * <b>Módulo:</b> Asopagos - HU-13-XXX<br/>
 *
 * @author <a href="mailto:maria.cuellar@eprocess.com.co">Maria Cuellar</a>
 */

public class SustitucionPatronalPersistLine implements IPersistLine {

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
//            ACTIVAR ESTAS LÍNEAS SI SE DEBEN PROCESAR LOS EXITOSOS E IGNORAR LOS FALLIDOS!!!
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
                // logger.info("Entra persist validacion");
//            LineArgumentDTO lineArgumentDTO = lines.get(i);
//            List<ResultadoHallazgosValidacionArchivoDTO> hallazgos = ((List<ResultadoHallazgosValidacionArchivoDTO>) lineArgumentDTO
//                    .getContext().get(ArchivoMultipleCampoConstants.LISTA_HALLAZGOS));
//            if (hallazgos.isEmpty()) {    
                try {
                    line = lineArgumentDTO.getLineValues();
                    // Contiene la informacion de la linea del archivo para la asignacion de diferencias
                    informacionActualizacionNovedadDTO = new InformacionActualizacionNovedadDTO();

                    // Informacion basica afiliado
                    AfiliadoModeloDTO afiliadoModeloDTO = new AfiliadoModeloDTO();
                    DatosEmpleadorNovedadDTO datosEmpleadorNovedadDTO = new DatosEmpleadorNovedadDTO();
                    
                    // Se asigna el campo No 1 - Tipo de identificación del empleador origen
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_EMPLEADOR_ORIGEN)) {
                        String tipoIdentificacionEmpleador = getValorCampo(line, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_EMPLEADOR_ORIGEN)
                                .toUpperCase();
                        TipoIdentificacionEnum tipoIdentEmpleadorOrigenEnum = TipoIdentificacionEnum
                                .obtenerTiposIdentificacionPILAEnum(tipoIdentificacionEmpleador);
                        datosEmpleadorNovedadDTO.setTipoIdentificacionOrigenSustPatronal(tipoIdentEmpleadorOrigenEnum);
                    }
                    // Se asigna el campo No 2 - Número de identificación del empleador del trabajador o cotizante o cabeza de familia origen
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR_ORIGEN)) {
                        String numeroIdentificacionEmpleadorOrigen = getValorCampo(line, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR_ORIGEN);
                        datosEmpleadorNovedadDTO.setNumeroIdentificacionOrigenSustPatronal(numeroIdentificacionEmpleadorOrigen);
                    }
                    // Se asigna el campo No 3 - Serial empresa
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.SERIAL_EMPRESA)) {
                        String serialEmpresa = getValorCampo(line, ArchivoCampoNovedadConstante.SERIAL_EMPRESA);
                        datosEmpleadorNovedadDTO.setSerialEmpresa(serialEmpresa);
                    }
                    // Se asigna el campo No 4 - Tipo de identificación del empleador destino
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_EMPLEADOR_DESTINO)) {
                        String tipoIdentificacionEmpleadorDestino = getValorCampo(line, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_EMPLEADOR_DESTINO)
                                .toUpperCase();
                        TipoIdentificacionEnum tipoIdentEmpleadorDestinoEnum = TipoIdentificacionEnum
                                .obtenerTiposIdentificacionPILAEnum(tipoIdentificacionEmpleadorDestino);
                        datosEmpleadorNovedadDTO.setTipoIdentificacionDestinoSustPatronal(tipoIdentEmpleadorDestinoEnum);
                    }
                    // Se asigna el campo No 5 - Número de identificación del empleador del trabajador o cotizante o cabeza de familia destino
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR_DESTINO)) {
                        String numeroIdentificacionEmpleadorDestino = getValorCampo(line, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_EMPLEADOR_DESTINO);
                        datosEmpleadorNovedadDTO.setNumeroIdentificacionDestinoSustPatronal(numeroIdentificacionEmpleadorDestino);
                    }
                    // Se asigna el campo No 6 - Sucursal del trabajador o cabeza de familia 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.SUCURSAL_EMPLEADOR)) {
                        String sucursal = getValorCampo(line, ArchivoCampoNovedadConstante.SUCURSAL_EMPLEADOR);
                        afiliadoModeloDTO.setSucursalAfiliado(sucursal);
                    }
                    // Se asigna el campo No 7 - Tipo de identificación del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_AFILIADO)) {
                        String tipoIdentificacion = getValorCampo(line, ArchivoCampoNovedadConstante.TIPO_IDENTIFICACION_AFILIADO)
                                .toUpperCase();
                        TipoIdentificacionEnum tipoIdentEnum = TipoIdentificacionEnum
                                .obtenerTiposIdentificacionPILAEnum(tipoIdentificacion);
                        afiliadoModeloDTO.setTipoIdentificacion(tipoIdentEnum);
                    }
                    // Se asigna el campo No 8 - Número de identificación del trabajador o cotizante o cabeza de familia 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO)) {
                        String numeroIdentificacion = getValorCampo(line, ArchivoCampoNovedadConstante.NUMERO_IDENTIFICACION_AFILIADO);
                        afiliadoModeloDTO.setNumeroIdentificacion(numeroIdentificacion);
                    }
                    // Se asigna el campo No 9 - Fecha fin labores empleador origen
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.FECHA_FIN_LABORES_EMPLEADOR_ORIGEN)) {
                        String fechaFinLaboresEmpleadorOrigenText = getValorCampo(line, ArchivoCampoNovedadConstante.FECHA_FIN_LABORES_EMPLEADOR_ORIGEN);
                        Date fechaFinLaboresEmpleadorOrigen = CalendarUtils.darFormatoYYYYMMDDGuionDate(fechaFinLaboresEmpleadorOrigenText);
                        afiliadoModeloDTO.setFechaFinLaboresEmpleadorOrigen(fechaFinLaboresEmpleadorOrigen.getTime());
                    }
                    // Se asigna el campo No 9 - Fecha de inicio afiliacion del trabajador o cabeza de familia 
                    if (esCampoValido(line, ArchivoCampoNovedadConstante.FECHA_INICIO_LABORES_EMPLEADOR_DESTINO)) {
                        String fechaInicioLaboresEmpleadorDestinoText = getValorCampo(line, ArchivoCampoNovedadConstante.FECHA_INICIO_LABORES_EMPLEADOR_DESTINO);
                        Date fechaInicioLaboresEmpleadorDestino = CalendarUtils.darFormatoYYYYMMDDGuionDate(fechaInicioLaboresEmpleadorDestinoText);
                        afiliadoModeloDTO.setFechaInicioLaboresEmpleadorDestino(fechaInicioLaboresEmpleadorDestino.getTime());
                    }
                    
                    informacionActualizacionNovedadDTO.setAfiliado(afiliadoModeloDTO);
                    informacionActualizacionNovedadDTO.setDatosEmpleadorNovedadDTO(datosEmpleadorNovedadDTO);

                    ((List<InformacionActualizacionNovedadDTO>) lineArgumentDTO.getContext()
                            .get(ArchivoMultipleCampoConstants.LISTA_CANDIDATOS)).add(informacionActualizacionNovedadDTO);
                } catch (Exception e) {
                    System.out.println("Error SustitucionPatronalPersistLine validate("+ e.getMessage() +")");
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