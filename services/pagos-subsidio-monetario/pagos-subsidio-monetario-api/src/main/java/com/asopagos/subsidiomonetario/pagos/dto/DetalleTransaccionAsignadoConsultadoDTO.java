package com.asopagos.subsidiomonetario.pagos.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.entidades.subsidiomonetario.pagos.ArchivoTransDetaSubsidio;

/**
 * <b>Descripcion:</b> Clase DTO que sirve para consultar los detalles de subsidios
 * asignados a traves de unos filtros.<br/>
 * <b>Módulo:</b> Asopagos - HU - 31 -201<br/>
 *
 * @author <a href="mailto:mosorio@heinsohn.com.co"> Miguel Angel Osorio</a>
 */
@XmlRootElement
public class DetalleTransaccionAsignadoConsultadoDTO extends TransaccionConsultadaDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2829306791096228333L;

    /**
     * Identificadores principales de los empleadoress
     */
    private List<Long> listaIdEmpleadores;

    /**
     * Identificadores de la base de datos de los afiliados principales.
     */
    private List<Long> listaIdAfiliadosPrincipales;

    /**
     * Identificadores de la base de datos de los grupos familiares relacionados con algún afiliado principal.
     */
    private List<Long> listaIdGruposFamiliares;

    /**
     * Identificadores de la base de datos de los beneficiarios.
     */
    private List<Long> listaIdBeneficiarios;
    
    
    
    private Long idArchivoTransacciones;
    
    
    
    private ArchivoTransDetaSubsidio archivoTransDetaSubsidio;
    

    private String tipoConsultaTransaccionesDetalles;


    /**
	 * @return the archivoTransDetaSubsidio
	 */
	public ArchivoTransDetaSubsidio getArchivoTransDetaSubsidio() {
		return archivoTransDetaSubsidio;
	}

	/**
	 * @param archivoTransDetaSubsidio the archivoTransDetaSubsidio to set
	 */
	public void setArchivoTransDetaSubsidio(ArchivoTransDetaSubsidio archivoTransDetaSubsidio) {
		this.archivoTransDetaSubsidio = archivoTransDetaSubsidio;
	}

	/**
	 * @return the idArchivoTransacciones
	 */
	public Long getIdArchivoTransacciones() {
		return idArchivoTransacciones;
	}

	/**
	 * @param idArchivoTransacciones the idArchivoTransacciones to set
	 */
	public void setIdArchivoTransacciones(Long idArchivoTransacciones) {
		this.idArchivoTransacciones = idArchivoTransacciones;
	}

	/**
     * @return the listaIdEmpleadores
     */
    public List<Long> getListaIdEmpleadores() {
        return listaIdEmpleadores;
    }

    /**
     * @param listaIdEmpleadores
     *        the listaIdEmpleadores to set
     */
    public void setListaIdEmpleadores(List<Long> listaIdEmpleadores) {
        this.listaIdEmpleadores = listaIdEmpleadores;
    }

    /**
     * @return the listaIdAfiliadosPrincipales
     */
    public List<Long> getListaIdAfiliadosPrincipales() {
        return listaIdAfiliadosPrincipales;
    }

    /**
     * @param listaIdAfiliadosPrincipales
     *        the listaIdAfiliadosPrincipales to set
     */
    public void setListaIdAfiliadosPrincipales(List<Long> listaIdAfiliadosPrincipales) {
        this.listaIdAfiliadosPrincipales = listaIdAfiliadosPrincipales;
    }

    /**
     * @return the listaIdGruposFamiliares
     */
    public List<Long> getListaIdGruposFamiliares() {
        return listaIdGruposFamiliares;
    }

    /**
     * @param listaIdGruposFamiliares
     *        the listaIdGruposFamiliares to set
     */
    public void setListaIdGruposFamiliares(List<Long> listaIdGruposFamiliares) {
        this.listaIdGruposFamiliares = listaIdGruposFamiliares;
    }

    /**
     * @return the listaIdBeneficiarios
     */
    public List<Long> getListaIdBeneficiarios() {
        return listaIdBeneficiarios;
    }

    /**
     * @param listaIdBeneficiarios
     *        the listaIdBeneficiarios to set
     */
    public void setListaIdBeneficiarios(List<Long> listaIdBeneficiarios) {
        this.listaIdBeneficiarios = listaIdBeneficiarios;
    }

    public String getTipoConsultaTransaccionesDetalles() {
        return tipoConsultaTransaccionesDetalles;
    }

    public void setTipoConsultaTransaccionesDetalles(String tipoConsultaTransaccionesDetalles) {
        this.tipoConsultaTransaccionesDetalles = tipoConsultaTransaccionesDetalles;
    }

    @Override
    public String toString() {
        return "DetalleTransaccionAsignadoConsultadoDTO{" +
                "listaIdEmpleadores=" + listaIdEmpleadores +
                ", listaIdAfiliadosPrincipales=" + listaIdAfiliadosPrincipales +
                ", listaIdGruposFamiliares=" + listaIdGruposFamiliares +
                ", listaIdBeneficiarios=" + listaIdBeneficiarios +
                ", idArchivoTransacciones=" + idArchivoTransacciones +
                ", archivoTransDetaSubsidio=" + archivoTransDetaSubsidio +
                ", tipoConsultaTransaccionesDetalles=" + tipoConsultaTransaccionesDetalles +
                '}';
    }
}
