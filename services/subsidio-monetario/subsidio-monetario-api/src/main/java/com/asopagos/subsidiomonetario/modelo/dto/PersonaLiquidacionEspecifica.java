package com.asopagos.subsidiomonetario.modelo.dto;

import java.io.Serializable;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> rarboleda</a>
 */

public class PersonaLiquidacionEspecifica implements Serializable {

    private static final long serialVersionUID = 1164438172473517300L;

    /**
     * Identificador unico asociado
     */
    private Long idPersonaLiquidacionEspecifica;

    /**
     * Identificador de la solicitud de liquidacion de subsidio asociada
     */
    private Long idSolicitudLiquidacionSubsidio;

    /**
     * Identificador del empleador
     */
    private Long idEmpleador;

    /**
     * Identificador del afiliado principal
     */
    private Long idAfiliadoPrincipal;

    /**
     * Identificador del detalle
     */
    private Long idBeneficiarioDetalle;

    /**
     * Identificador del grupo familiar
     */
    private Long idGrupoFamiliar;

    /**
     * Metodo encargado de convertir una entidad a DTO
     * @param personaLiquidacionEspecifica
     *        entidad a convertir
     */
    public void convertToDTO(PersonaLiquidacionEspecifica personaLiquidacionEspecifica) {
        this.setIdPersonaLiquidacionEspecifica(personaLiquidacionEspecifica.getIdPersonaLiquidacionEspecifica());
        this.setIdSolicitudLiquidacionSubsidio(personaLiquidacionEspecifica.getIdSolicitudLiquidacionSubsidio());
        this.setIdEmpleador(personaLiquidacionEspecifica.getIdEmpleador());
        this.setIdAfiliadoPrincipal(personaLiquidacionEspecifica.getIdAfiliadoPrincipal());
        this.setIdBeneficiarioDetalle(personaLiquidacionEspecifica.getIdBeneficiarioDetalle());
        this.setIdGrupoFamiliar(personaLiquidacionEspecifica.getIdGrupoFamiliar());
    }
    
    /**
     * Convierte un DTO a entidad
     * @return La entidad con la informacion del DTO
     */
    public PersonaLiquidacionEspecifica convertToEntity(){
        PersonaLiquidacionEspecifica personaLiquidacionEspecifica = new PersonaLiquidacionEspecifica();
        
        personaLiquidacionEspecifica.setIdPersonaLiquidacionEspecifica(this.getIdPersonaLiquidacionEspecifica());
        personaLiquidacionEspecifica.setIdSolicitudLiquidacionSubsidio(this.getIdSolicitudLiquidacionSubsidio());
        personaLiquidacionEspecifica.setIdEmpleador(this.getIdEmpleador());
        personaLiquidacionEspecifica.setIdAfiliadoPrincipal(this.getIdAfiliadoPrincipal());
        personaLiquidacionEspecifica.setIdBeneficiarioDetalle(this.getIdBeneficiarioDetalle());
        personaLiquidacionEspecifica.setIdGrupoFamiliar(this.getIdGrupoFamiliar());
        
        return personaLiquidacionEspecifica;
    }

    /**
     * @return the idPersonaLiquidacionEspecifica
     */
    public Long getIdPersonaLiquidacionEspecifica() {
        return idPersonaLiquidacionEspecifica;
    }

    /**
     * @param idPersonaLiquidacionEspecifica
     *        the idPersonaLiquidacionEspecifica to set
     */
    public void setIdPersonaLiquidacionEspecifica(Long idPersonaLiquidacionEspecifica) {
        this.idPersonaLiquidacionEspecifica = idPersonaLiquidacionEspecifica;
    }

    /**
     * @return the idSolicitudLiquidacionSubsidio
     */
    public Long getIdSolicitudLiquidacionSubsidio() {
        return idSolicitudLiquidacionSubsidio;
    }

    /**
     * @param idSolicitudLiquidacionSubsidio
     *        the idSolicitudLiquidacionSubsidio to set
     */
    public void setIdSolicitudLiquidacionSubsidio(Long idSolicitudLiquidacionSubsidio) {
        this.idSolicitudLiquidacionSubsidio = idSolicitudLiquidacionSubsidio;
    }

    /**
     * @return the idEmpleador
     */
    public Long getIdEmpleador() {
        return idEmpleador;
    }

    /**
     * @param idEmpleador
     *        the idEmpleador to set
     */
    public void setIdEmpleador(Long idEmpleador) {
        this.idEmpleador = idEmpleador;
    }

    /**
     * @return the idAfiliadoPrincipal
     */
    public Long getIdAfiliadoPrincipal() {
        return idAfiliadoPrincipal;
    }

    /**
     * @param idAfiliadoPrincipal
     *        the idAfiliadoPrincipal to set
     */
    public void setIdAfiliadoPrincipal(Long idAfiliadoPrincipal) {
        this.idAfiliadoPrincipal = idAfiliadoPrincipal;
    }

    /**
     * @return the idBeneficiarioDetalle
     */
    public Long getIdBeneficiarioDetalle() {
        return idBeneficiarioDetalle;
    }

    /**
     * @param idBeneficiarioDetalle
     *        the idBeneficiarioDetalle to set
     */
    public void setIdBeneficiarioDetalle(Long idBeneficiarioDetalle) {
        this.idBeneficiarioDetalle = idBeneficiarioDetalle;
    }

    /**
     * @return the idGrupoFamiliar
     */
    public Long getIdGrupoFamiliar() {
        return idGrupoFamiliar;
    }

    /**
     * @param idGrupoFamiliar
     *        the idGrupoFamiliar to set
     */
    public void setIdGrupoFamiliar(Long idGrupoFamiliar) {
        this.idGrupoFamiliar = idGrupoFamiliar;
    }

}
