package com.asopagos.dto.subsidiomonetario.pagos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * <b>Descripcion:</b> Clase DTO que contiene la información del subsidio por afiliado asociado a sus grupos familiares y beneficiarios<br/>
 * <b>Módulo:</b> Asopagos - HU - <br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co">Miguel Angel Osorio</a>
 */
@XmlRootElement
@JsonInclude(Include.NON_EMPTY)
public class SubsidioAfiliadoDTO implements Serializable {

    private static final long serialVersionUID = -8942307006851703397L;

    /**
     * Descripción del tipo de identificación del afiliado
     */
    private TipoIdentificacionEnum tipoIdAfiliado;

    /**
     * Número de identificación del afiliado
     */
    private String numeroIdAfiliado;

    /**
     * Nombre completo del afiliado
     */
    private String nombreCompletoAfiliado;

    /**
     * Id del empleador, solo aplica para Dependientes
     */
    private String numeroIdEmpleador;

    /**
     * tipo de identificación del empelador
     */
    private TipoIdentificacionEnum tipoIdEmpleador;

    /**
     * Razón social del empleador, solo aplica para Dependientes.
     */
    private String razonSocialEmpleador;

    /**
     * Periodo en que se realiza el subsidio
     */
    private String periodo;

    /**
     * Lista de Campos por Grupo Familiar
     */
    private List<ItemSubsidioBeneficiarioDTO> lstBeneficiario;

    /**
     * Información de cada grupo familiar
     */
    private List<InformacionGrupoFamiliarDTO> lstGruposFamiliares;

    /**
     * Información que se devuelve cuando hay un error
     */
    private String mensaje;
    
    /**
     * @return the tipoIdAfiliado
     */
    public TipoIdentificacionEnum getTipoIdAfiliado() {
        return tipoIdAfiliado;
    }

    /**
     * @param tipoIdAfiliado
     *        the tipoIdAfiliado to set
     */
    public void setTipoIdAfiliado(TipoIdentificacionEnum tipoIdAfiliado) {
        this.tipoIdAfiliado = tipoIdAfiliado;
    }

    /**
     * @return the numeroIdAfiliado
     */
    public String getNumeroIdAfiliado() {
        return numeroIdAfiliado;
    }

    /**
     * @param numeroIdAfiliado
     *        the numeroIdAfiliado to set
     */
    public void setNumeroIdAfiliado(String numeroIdAfiliado) {
        this.numeroIdAfiliado = numeroIdAfiliado;
    }

    /**
     * @return the nombreCompletoAfiliado
     */
    public String getNombreCompletoAfiliado() {
        return nombreCompletoAfiliado;
    }

    /**
     * @param nombreCompletoAfiliado
     *        the nombreCompletoAfiliado to set
     */
    public void setNombreCompletoAfiliado(String nombreCompletoAfiliado) {
        this.nombreCompletoAfiliado = nombreCompletoAfiliado;
    }

    /**
     * @return the lstBeneficiario
     */
    public List<ItemSubsidioBeneficiarioDTO> getLstBeneficiario() {
        return lstBeneficiario;
    }

    /**
     * @param lstBeneficiario
     *        the lstBeneficiario to set
     */
    public void setLstBeneficiario(List<ItemSubsidioBeneficiarioDTO> lstBeneficiario) {
        this.lstBeneficiario = lstBeneficiario;
    }

    /**
     * @return the numeroIdEmpleador
     */
    public String getNumeroIdEmpleador() {
        return numeroIdEmpleador;
    }

    /**
     * @param numeroIdEmpleador
     *        the numeroIdEmpleador to set
     */
    public void setNumeroIdEmpleador(String numeroIdEmpleador) {
        this.numeroIdEmpleador = numeroIdEmpleador;
    }

    /**
     * @return the razonSocialEmpleador
     */
    public String getRazonSocialEmpleador() {
        return razonSocialEmpleador;
    }

    /**
     * @param razonSocialEmpleador
     *        the razonSocialEmpleador to set
     */
    public void setRazonSocialEmpleador(String razonSocialEmpleador) {
        this.razonSocialEmpleador = razonSocialEmpleador;
    }

    /**
     * @return the periodo
     */
    public String getPeriodo() {
        return periodo;
    }

    /**
     * @return the lstGruposFamiliares
     */
    public List<InformacionGrupoFamiliarDTO> getLstGruposFamiliares() {
        return lstGruposFamiliares;
    }

    /**
     * @param periodo
     *        the periodo to set
     */
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    /**
     * @param lstGruposFamiliares
     *        the lstGruposFamiliares to set
     */
    public void setLstGruposFamiliares(List<InformacionGrupoFamiliarDTO> lstGruposFamiliares) {
        this.lstGruposFamiliares = lstGruposFamiliares;
    }

    /**
     * @return the tipoIdEmpleador
     */
    public TipoIdentificacionEnum getTipoIdEmpleador() {
        return tipoIdEmpleador;
    }

    /**
     * @param tipoIdEmpleador
     *        the tipoIdEmpleador to set
     */
    public void setTipoIdEmpleador(TipoIdentificacionEnum tipoIdEmpleador) {
        this.tipoIdEmpleador = tipoIdEmpleador;
    }

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
