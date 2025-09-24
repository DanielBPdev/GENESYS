package com.asopagos.dto;

import java.io.Serializable;
import java.util.List;
import com.asopagos.enumeraciones.afiliaciones.ResultadoGeneralProductoNoConformeEnum;

/**
 *
 * @author sbrinez
 */
public class VerificarSolicitudAfiliacionPersonaDTO implements Serializable {

    private String numeroRadicado;

    private String numeroIdentificacionAfiliado;

    private ResultadoGeneralProductoNoConformeEnum resultadoGeneralAfiliado;

    private List<ResultadoGeneralProductoNoConformeBeneficiarioDTO> resultadoGeneralBeneficiarios;

    private Long idTarea;
    
    private Long idEmpleador;
    
    private Long idAfiliado;
    
    private Boolean empleadorInactivo;

    public Long getIdAfiliado() {
		return idAfiliado;
	}

	public void setIdAfiliado(Long idAfiliado) {
		this.idAfiliado = idAfiliado;
	}

	/**
     * @return the numeroRadicado
     */
    public String getNumeroRadicado() {
        return numeroRadicado;
    }

    /**
     * @param numeroRadicado
     *        the numeroRadicado to set
     */
    public void setNumeroRadicado(String numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }

    /**
     * @return the resultadoGeneralAfiliado
     */
    public ResultadoGeneralProductoNoConformeEnum getResultadoGeneralAfiliado() {
        return resultadoGeneralAfiliado;
    }

    /**
     * @param resultadoGeneralAfiliado
     *        the resultadoGeneralAfiliado to set
     */
    public void setResultadoGeneralAfiliado(ResultadoGeneralProductoNoConformeEnum resultadoGeneralAfiliado) {
        this.resultadoGeneralAfiliado = resultadoGeneralAfiliado;
    }

    /**
     * @return the resultadoGeneralBeneficiarios
     */
    public List<ResultadoGeneralProductoNoConformeBeneficiarioDTO> getResultadoGeneralBeneficiarios() {
        return resultadoGeneralBeneficiarios;
    }

    /**
     * @param resultadoGeneralBeneficiarios
     *        the resultadoGeneralBeneficiarios to set
     */
    public void setResultadoGeneralBeneficiarios(List<ResultadoGeneralProductoNoConformeBeneficiarioDTO> resultadoGeneralBeneficiarios) {
        this.resultadoGeneralBeneficiarios = resultadoGeneralBeneficiarios;
    }

    /**
     * @return the numeroIdentificacionAfiliado
     */
    public String getNumeroIdentificacionAfiliado() {
        return numeroIdentificacionAfiliado;
    }

    /**
     * @param numeroIdentificacionAfiliado
     *        the numeroIdentificacionAfiliado to set
     */
    public void setNumeroIdentificacionAfiliado(String numeroIdentificacionAfiliado) {
        this.numeroIdentificacionAfiliado = numeroIdentificacionAfiliado;
    }

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
