package com.asopagos.novedades.dto;

import java.io.Serializable;
import com.asopagos.dto.modelo.SolicitudNovedadModeloDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;

/**
 * Contiene la información generada durante la novedad y que será persistida
 * para su posterior revisión por excepción. 
 * @author mamonroy
 *
 */
public class DatosExcepcionNovedadDTO implements Serializable{
    
    private static final long serialVersionUID = 6256234334504349042L;
    
    private SolicitudNovedadDTO solicitudNovedadDTO;
    
    private SolicitudNovedadModeloDTO solicitudNovedadModeloDTO;
    
    public DatosExcepcionNovedadDTO() {
    }
    
    public DatosExcepcionNovedadDTO(SolicitudNovedadDTO solicitudNovedadDTO, SolicitudNovedadModeloDTO solicitudNovedadModeloDTO) {
        super();
        this.solicitudNovedadDTO = solicitudNovedadDTO;
        this.solicitudNovedadModeloDTO = solicitudNovedadModeloDTO;
    }



    /**
     * @return the solicitudNovedadDTO
     */
    public SolicitudNovedadDTO getSolicitudNovedadDTO() {
        return solicitudNovedadDTO;
    }
    
    /**
     * @param solicitudNovedadDTO the solicitudNovedadDTO to set
     */
    public void setSolicitudNovedadDTO(SolicitudNovedadDTO solicitudNovedadDTO) {
        this.solicitudNovedadDTO = solicitudNovedadDTO;
    }

    /**
     * @return the solicitudNovedadModeloDTO
     */
    public SolicitudNovedadModeloDTO getSolicitudNovedadModeloDTO() {
        return solicitudNovedadModeloDTO;
    }

    /**
     * @param solicitudNovedadModeloDTO the solicitudNovedadModeloDTO to set
     */
    public void setSolicitudNovedadModeloDTO(SolicitudNovedadModeloDTO solicitudNovedadModeloDTO) {
        this.solicitudNovedadModeloDTO = solicitudNovedadModeloDTO;
    }
    
}
