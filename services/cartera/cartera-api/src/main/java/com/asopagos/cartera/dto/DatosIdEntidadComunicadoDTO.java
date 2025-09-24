package com.asopagos.cartera.dto;

/**
 * <b>Descripcion:</b> Contiene la información de los id de la entidad asociada a un comunicado<br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author  <a href="mailto:mamonroy@heinsohn.com.co"> mamonroy</a>
 */

public class DatosIdEntidadComunicadoDTO {
    
    /**
     * Identificador de la persna
     */
    private Long idPersona;
    
    /**
     * Identificador del empleador
     */
    private Long idEmpleador;
    
    public DatosIdEntidadComunicadoDTO() {
    }

    /**
     * @param idPersona
     * @param idEmpleador
     */
    public DatosIdEntidadComunicadoDTO(Long idPersona, Long idEmpleador) {
        this.idPersona = idPersona;
        this.idEmpleador = idEmpleador;
    }

    /**
     * @return the idPersona
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * @param idPersona the idPersona to set
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * @return the idEmpleador
     */
    public Long getIdEmpleador() {
        return idEmpleador;
    }

    /**
     * @param idEmpleador the idEmpleador to set
     */
    public void setIdEmpleador(Long idEmpleador) {
        this.idEmpleador = idEmpleador;
    }
}
