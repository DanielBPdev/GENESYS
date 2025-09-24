package com.asopagos.subsidiomonetario.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author amarin
 *
 */
public class PersonaLiquidacionEspecificaDTO implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador del empleador
     */
    private List<Integer> idEmpleador;
    
    /**
     * Identificador del afiliado principal
     */
    private List<Integer> idAfiliadoPrincipal;

    /**
     * Identificador del detalle
     */
    private List<Integer> idBeneficiarioDetalle;

    /**
     * Identificador del grupo familiar
     */
    private List<Integer> idGrupoFamiliar;
    
    /**
     * Identificador de la Empresa
     */
    private List<Integer> idEmpresa;
    
    /**
     * Identificador para los periodos de la liquidacion
     */
    private List<Long> periodos;

    /**
     * @return the periodos
     */
    public List<Long> getPeriodos() {
        return periodos;
    }

    /**
     * @param periodos the periodos to set
     */
    public void setPeriodos(List<Long> periodos) {
        this.periodos = periodos;
    }

    /**
     * @return the idEmpleador
     */
    public List<Integer> getIdEmpleador() {
        return idEmpleador;
    }

    /**
     * @param idEmpleador the idEmpleador to set
     */
    public void setIdEmpleador(List<Integer> idEmpleador) {
        this.idEmpleador = idEmpleador;
    }

    /**
     * @return the idAfiliadoPrincipal
     */
    public List<Integer> getIdAfiliadoPrincipal() {
        return idAfiliadoPrincipal;
    }

    /**
     * @param idAfiliadoPrincipal the idAfiliadoPrincipal to set
     */
    public void setIdAfiliadoPrincipal(List<Integer> idAfiliadoPrincipal) {
        this.idAfiliadoPrincipal = idAfiliadoPrincipal;
    }

    /**
     * @return the idBeneficiarioDetalle
     */
    public List<Integer> getIdBeneficiarioDetalle() {
        return idBeneficiarioDetalle;
    }

    /**
     * @param idBeneficiarioDetalle the idBeneficiarioDetalle to set
     */
    public void setIdBeneficiarioDetalle(List<Integer> idBeneficiarioDetalle) {
        this.idBeneficiarioDetalle = idBeneficiarioDetalle;
    }

    /**
     * @return the idGrupoFamiliar
     */
    public List<Integer> getIdGrupoFamiliar() {
        return idGrupoFamiliar;
    }

    /**
     * @param idGrupoFamiliar the idGrupoFamiliar to set
     */
    public void setIdGrupoFamiliar(List<Integer> idGrupoFamiliar) {
        this.idGrupoFamiliar = idGrupoFamiliar;
    }

    /**
     * @return the idEmpresa
     */
    public List<Integer> getIdEmpresa() {
        return idEmpresa;
    }

    /**
     * @param idEmpresa the idEmpresa to set
     */
    public void setIdEmpresa(List<Integer> idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

     
}
