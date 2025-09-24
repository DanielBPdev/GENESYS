package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoRegistroCargueArchivoRetiroTerceroPagador;

/**
 * <b>Descripcion:</b> Clase DTO que contiene l información de filtro para obtener los
 * subsidios asignados para anular por perdida de derecho
 * y el filtro para generar informes de retiros de subsidios monetarios
 * <br/>
 * <b>Módulo:</b> Asopagos - HU - 31 - 206 y 225<br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
@XmlRootElement
public class SubsidioPerdidaDerechoInformesConsumoDTO extends SubsidioMonetarioConsultaCambioPagosDTO implements Serializable {

    private static final long serialVersionUID = -2919003007176930584L;

    /**
     * Identificador principal del detalle de subsidio asignado
     */
    private Long identificadorSubsidioAsignado;

    /**
     * Variable que contiene el nombre del convenio seleccionado.
     */
    private String nombreConvenio;

    /**
     * Periodo de liquidación del subsidio monetario a ser anulador por perdida de derecho
     * o para generar informe de retiros
     */
    private Long periodoLiquidado;

    /**
     * Lista de Identificadores de los afiliados principales relacionados a los abonos por medio del detalle.
     */
    private List<Long> listaIdsAfiliadosPrincipalesRelacionados;

    /**
     * Lista de Identificadores de los beneficiarios principales relacionados a los abonos por medio del detalle.
     */
    private List<Long> listaIdsBeneficiariosRelacionados;

    /**
     * Estado para generar los informes de retiro.
     * SOLO SE UTILIZAN CONCILIAR, SIN_CONCILIAR
     */
    private EstadoRegistroCargueArchivoRetiroTerceroPagador estado;

    /**
     * Filtro por el grupo familiar
     */
    private List<Long> listaIdGrupoFamiliar;
    
    /**
     * 
     */
    private Long convenio;


	/**
     * @return the identificadorSubsidioAsignado
     */
    public Long getIdentificadorSubsidioAsignado() {
        return identificadorSubsidioAsignado;
    }

    /**
     * @param identificadorSubsidioAsignado
     *        the identificadorSubsidioAsignado to set
     */
    public void setIdentificadorSubsidioAsignado(Long identificadorSubsidioAsignado) {
        this.identificadorSubsidioAsignado = identificadorSubsidioAsignado;
    }

    /**
     * @return the nombreConvenio
     */
    public String getNombreConvenio() {
        return nombreConvenio;
    }

    /**
     * @param nombreConvenio
     *        the nombreConvenio to set
     */
    public void setNombreConvenio(String nombreConvenio) {
        this.nombreConvenio = nombreConvenio;
    }

    /**
     * @return the periodoLiquidado
     */
    public Long getPeriodoLiquidado() {
        return periodoLiquidado;
    }

    /**
     * @param periodoLiquidado
     *        the periodoLiquidado to set
     */
    public void setPeriodoLiquidado(Long periodoLiquidado) {
        this.periodoLiquidado = periodoLiquidado;
    }

    /**
     * @return the listaIdsAfiliadosPrincipalesRelacionados
     */
    public List<Long> getListaIdsAfiliadosPrincipalesRelacionados() {
        return listaIdsAfiliadosPrincipalesRelacionados;
    }

    /**
     * @param listaIdsAfiliadosPrincipalesRelacionados
     *        the listaIdsAfiliadosPrincipalesRelacionados to set
     */
    public void setIdAfiliadoPrincipalRelacionado(List<Long> listaIdsAfiliadosPrincipalesRelacionados) {
        this.listaIdsAfiliadosPrincipalesRelacionados = listaIdsAfiliadosPrincipalesRelacionados;
    }

    /**
     * @return the listaIdsBeneficiariosRelacionados
     */
    public List<Long> getListaIdsBeneficiariosRelacionados() {
        return listaIdsBeneficiariosRelacionados;
    }

    /**
     * @param listaIdsBeneficiariosRelacionados
     *        the listaIdsBeneficiariosRelacionados to set
     */
    public void setIdBeneficiarioRelacionado(List<Long> listaIdsBeneficiariosRelacionados) {
        this.listaIdsBeneficiariosRelacionados = listaIdsBeneficiariosRelacionados;
    }

    /**
     * @return the estado
     */
    public EstadoRegistroCargueArchivoRetiroTerceroPagador getEstado() {
        return estado;
    }

    /**
     * @param estado
     *        the estado to set
     */
    public void setEstado(EstadoRegistroCargueArchivoRetiroTerceroPagador estado) {
        this.estado = estado;
    }

    /**
     * @return the listaIdGrupoFamiliar
     */
    public List<Long> getListaIdGrupoFamiliar() {
        return listaIdGrupoFamiliar;
    }

    /**
     * @param listaIdGrupoFamiliar
     *        the listaIdGrupoFamiliar to set
     */
    public void setListaIdGrupoFamiliar(List<Long> listaIdGrupoFamiliar) {
        this.listaIdGrupoFamiliar = listaIdGrupoFamiliar;
    }
    
    /**
     * @param 
     *        the listaIdGrupoFamiliar to set
     */
    public Long getConvenio() {
		return convenio;
	}

    /**
     * @param convenio
     *        the convenio to set
     */
	public void setConvenio(Long convenio) {
		this.convenio = convenio;
	}

}
