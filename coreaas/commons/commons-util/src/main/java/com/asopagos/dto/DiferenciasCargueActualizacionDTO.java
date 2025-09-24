package com.asopagos.dto;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.novedades.CargueArchivoActualizacion;
import com.asopagos.entidades.ccf.novedades.DiferenciasCargueActualizacion;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;

/**
 * <b>Descripcion:</b> Clase que contiene la informacion del Cargue Archivo de actualizacion<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:jocorrea@heinsohn.com.co"> Jose Arley Correa</a>
 */
@XmlRootElement
public class DiferenciasCargueActualizacionDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador diferencias cargue
     */
    private Long idDiferenciasCargueActualizacion;

    /**
     * Tipo novedad a registrar
     */
    private TipoTransaccionEnum tipoTransaccion;

    /**
     * DTO de novedad a registrar con los datos diferentes
     */
    private String jsonPayload;

    /**
     * Cargue de actualizacion
     */
    private CargueArchivoActualizacionDTO cargueArchivoActualizacionDTO;

    /**
     * Constructor
     */
    public DiferenciasCargueActualizacionDTO() {
        super();
    }

    /**
     * Convierte la entidad en el DTO
     * @param diferenciasCargueActualizacion
     *        Entidad
     * @return DTO
     */
    public static DiferenciasCargueActualizacionDTO convertDiferenciasCargueActualizacionToDTO(
            DiferenciasCargueActualizacion diferenciasCargueActualizacion) {
        DiferenciasCargueActualizacionDTO diferenciasCargueActualizacionDTO = new DiferenciasCargueActualizacionDTO();
        diferenciasCargueActualizacionDTO
                .setIdDiferenciasCargueActualizacion(diferenciasCargueActualizacion.getIdDiferenciasCargueActualizacion());
        diferenciasCargueActualizacionDTO.setTipoTransaccion(diferenciasCargueActualizacion.getTipoTransaccion());
        diferenciasCargueActualizacionDTO.setJsonPayload(diferenciasCargueActualizacion.getJsonPayload());
        if (diferenciasCargueActualizacion.getCargueArchivoActualizacion() != null) {
            CargueArchivoActualizacionDTO dto = CargueArchivoActualizacionDTO
                    .convertCargueArchivoActualizacionToDTO(diferenciasCargueActualizacion.getCargueArchivoActualizacion());
            diferenciasCargueActualizacionDTO.setCargueArchivoActualizacion(dto);
        }
        return diferenciasCargueActualizacionDTO;
    }

    /**
     * Convierte el DTO a entity
     * @return Entidad
     */
    public DiferenciasCargueActualizacion convertToEntity() {
        DiferenciasCargueActualizacion diferenciasCargueActualizacion = new DiferenciasCargueActualizacion();
        diferenciasCargueActualizacion.setCargueArchivoActualizacion(this.getCargueArchivoActualizacion().convertToEntity());
        diferenciasCargueActualizacion.setIdDiferenciasCargueActualizacion(this.getIdDiferenciasCargueActualizacion());
        diferenciasCargueActualizacion.setJsonPayload(this.getJsonPayload());
        diferenciasCargueActualizacion.setTipoTransaccion(this.getTipoTransaccion());
        return diferenciasCargueActualizacion;
    }

    /**
     * @return the idDiferenciasCargueActualizacion
     */
    public Long getIdDiferenciasCargueActualizacion() {
        return idDiferenciasCargueActualizacion;
    }

    /**
     * @param idDiferenciasCargueActualizacion
     *        the idDiferenciasCargueActualizacion to set
     */
    public void setIdDiferenciasCargueActualizacion(Long idDiferenciasCargueActualizacion) {
        this.idDiferenciasCargueActualizacion = idDiferenciasCargueActualizacion;
    }

    /**
     * @return the tipoTransaccion
     */
    public TipoTransaccionEnum getTipoTransaccion() {
        return tipoTransaccion;
    }

    /**
     * @param tipoTransaccion
     *        the tipoTransaccion to set
     */
    public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    /**
     * @return the jsonPayload
     */
    public String getJsonPayload() {
        return jsonPayload;
    }

    /**
     * @param jsonPayload
     *        the jsonPayload to set
     */
    public void setJsonPayload(String jsonPayload) {
        this.jsonPayload = jsonPayload;
    }

    /**
     * @return the cargueArchivoActualizacion
     */
    public CargueArchivoActualizacionDTO getCargueArchivoActualizacion() {
        return cargueArchivoActualizacionDTO;
    }

    /**
     * @param cargueArchivoActualizacion
     *        the cargueArchivoActualizacion to set
     */
    public void setCargueArchivoActualizacion(CargueArchivoActualizacionDTO cargueArchivoActualizacionDTO) {
        this.cargueArchivoActualizacionDTO = cargueArchivoActualizacionDTO;
    }

}
