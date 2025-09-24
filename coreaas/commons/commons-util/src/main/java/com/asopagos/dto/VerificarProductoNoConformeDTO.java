package com.asopagos.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.enumeraciones.afiliaciones.ResultadoGestionProductoNoConformeSubsanableEnum;

/**
 *
 * @author sbrinez
 */
public class VerificarProductoNoConformeDTO implements Serializable {

    private Long idTarea;

    private Long idSolicitudGlobal;

    private Long idRolAfiliado;

    private Long idAfiliado;

    private ResultadoGestionProductoNoConformeSubsanableEnum resultadoAfiliado;

    private List<ResultadoGeneralProductoNoConformeBeneficiarioDTO> resultadoBeneficiarios;
    
    private Boolean empleadorInactivo;

    /**
     * @return the idTarea
     */
    public Long getIdTarea() {
        return idTarea;
    }

    /**
     * @param idTarea
     *        the idTarea to set
     */
    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }

    /**
     * @return the idSolicitudGlobal
     */
    public Long getIdSolicitudGlobal() {
        return idSolicitudGlobal;
    }

    /**
     * @param idSolicitudGlobal
     *        the idSolicitudGlobal to set
     */
    public void setIdSolicitudGlobal(Long idSolicitudGlobal) {
        this.idSolicitudGlobal = idSolicitudGlobal;
    }

    /**
     * @return the idRolAfiliado
     */
    public Long getIdRolAfiliado() {
        return idRolAfiliado;
    }

    /**
     * @param idRolAfiliado
     *        the idRolAfiliado to set
     */
    public void setIdRolAfiliado(Long idRolAfiliado) {
        this.idRolAfiliado = idRolAfiliado;
    }

    /**
     * @return the idAfiliado
     */
    public Long getIdAfiliado() {
        return idAfiliado;
    }

    /**
     * @param idAfiliado
     *        the idAfiliado to set
     */
    public void setIdAfiliado(Long idAfiliado) {
        this.idAfiliado = idAfiliado;
    }

    /**
     * @return the resultadoAfiliado
     */
    public ResultadoGestionProductoNoConformeSubsanableEnum getResultadoAfiliado() {
        return resultadoAfiliado;
    }

    /**
     * @param resultadoAfiliado
     *        the resultadoAfiliado to set
     */
    public void setResultadoAfiliado(ResultadoGestionProductoNoConformeSubsanableEnum resultadoAfiliado) {
        this.resultadoAfiliado = resultadoAfiliado;
    }

    /**
     * @return the resultadoBeneficiarios
     */
    public List<ResultadoGeneralProductoNoConformeBeneficiarioDTO> getResultadoBeneficiarios() {
        return resultadoBeneficiarios;
    }

    /**
     * @param resultadoBeneficiarios
     *        the resultadoBeneficiarios to set
     */
    public void setResultadoBeneficiarios(List<ResultadoGeneralProductoNoConformeBeneficiarioDTO> resultadoBeneficiarios) {
        this.resultadoBeneficiarios = resultadoBeneficiarios;
    }

    /**
     * @return the empleadorInactivo
     */
    public Boolean getEmpleadorInactivo() {
        return empleadorInactivo;
    }

    /**
     * @param empleadorInactivo the empleadorInactivo to set
     */
    public void setEmpleadorInactivo(Boolean empleadorInactivo) {
        this.empleadorInactivo = empleadorInactivo;
    }
}
