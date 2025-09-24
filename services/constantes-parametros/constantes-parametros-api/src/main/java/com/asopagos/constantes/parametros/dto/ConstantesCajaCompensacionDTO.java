package com.asopagos.constantes.parametros.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

/**
 * <b>Descripción:</b> DTO empleado para establecer una constante de la caja
 * <b>Historia de Usuario:</b> HU-073, Transversal
 * 
 * @author Harold Andrés Alzate Betancur <halzate@heinsohn.com.co>
 */

@XmlRootElement
public class ConstantesCajaCompensacionDTO implements Serializable {
	
	/**
	 * Nombre constante
	 *
	 */
	private String nombre;
	
	/**
	 * Identificador Logo
	 *
	 */
	private String identificadorLogo;
	
	/**
	 * Identificador Departamento
	 *
	 */
	private String idDeparatamento;
	
	

	/**
	 * Identificador Municipio
	 *
	 */
	private String idMunicipio;
	
	/**
	 * Dirección
	 *
	 */
	private String direccion;
	
	/**
	 * Teléfono
	 *
	 */
	private String telefono;
	
	/**
	 * Tipo de identificación
	 *
	 */
	private TipoIdentificacionEnum tipoIdentificacion;
	
	/**
	 * Número identificación
	 *
	 */
	private String numeroIdentificacion;
	
	/**
	 * Página web
	 *
	 */
	private String paginaWeb;
	
	/**
	 * Logotipo de la Superintendencia de Servicios
	 *
	 */
	private String logoSuperServicios;
	
	/**
	 * Imagen de la firma del responsable del envío del comunicado en la caja
	 *
	 */
	private String firmaResponsableEnvioComunicadoCaja;
	
	/**
	 * Nombre del responsable del envío del comunicado en la caja
	 *
	 */
	private String responsableEnvioComunicadoCaja;
	
	/**
	 * Cargo del responsable del envío del comunicado en la caja
	 *
	 */
	private String cargoResponsableEnvioComunicadoCaja;
	
	/**
	 * Variable tiempoCaducidadLinkEmpresasWeb
	 */
	private String tiempoCaducidadLinkEmpresasWeb;
	/**
	 * Variable tiempoCaducidadLinkIndPensWeb
	 */
	private String tiempoCaducidadLinkIndPensWeb;	
	
	private String descripcionIndicacion;
	
	/**
     * Variable firmaResponsable Afiliacion empresas
     */
	private String firmaRespAfiEmpresas;
	
	/**
     * Variable firma Responsable Afiliacion personas
     */
    private String firmaRespAfiPersonas;

	/**
	 * Variable correo Caja de compensacion
	 */
	private String correoCaja;

	/**
     * Variable firma Gerente Finaciera
     */
    private String firmagerentefinanciera;

    /**
     * Variable firma Gerente Comercial
     */
    private String firmagerentecomercial;

    /**
     * Variable firma Secretaria General
     */
    private String firmasecretariageneral;

    /**
     * Variable firma Firma del director administrativo principal
     */
	private String FirmaDirectorAdminppalCCF;

    /**
     * Variable firma Firma del director administrativo segundo suplente
     */
	private String FirmaDirectorAdminsplCCF;

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the identificadorLogo
	 */
	public String getIdentificadorLogo() {
		return identificadorLogo;
	}

	/**
	 * @param identificadorLogo the identificadorLogo to set
	 */
	public void setIdentificadorLogo(String identificadorLogo) {
		this.identificadorLogo = identificadorLogo;
	}

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the tipoIdentificacion
	 */
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * @param tipoIdentificacion the tipoIdentificacion to set
	 */
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/**
	 * @return the numeroIdentificacion
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * @param numeroIdentificacion the numeroIdentificacion to set
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	/**
	 * @return the paginaWeb
	 */
	public String getPaginaWeb() {
		return paginaWeb;
	}

	/**
	 * @param paginaWeb the paginaWeb to set
	 */
	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}
	
	/**
	 * @return the idDeparatamento
	 */
	public String getIdDeparatamento() {
		return idDeparatamento;
	}

	/**
	 * @param idDeparatamento the idDeparatamento to set
	 */
	public void setIdDeparatamento(String idDeparatamento) {
		this.idDeparatamento = idDeparatamento;
	}

	/**
	 * @return the idMunicipio
	 */
	public String getIdMunicipio() {
		return idMunicipio;
	}

	/**
	 * @param idMunicipio the idMunicipio to set
	 */
	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	/**
	 * @return the logoSuperServicios
	 */
	public String getLogoSuperServicios() {
		return logoSuperServicios;
	}

	/**
	 * @param logoSuperServicios the logoSuperServicios to set
	 */
	public void setLogoSuperServicios(String logoSuperServicios) {
		this.logoSuperServicios = logoSuperServicios;
	}


	/**
	 * @return the firmaResponsableEnvioComunicadoCaja
	 */
	public String getFirmaResponsableEnvioComunicadoCaja() {
		return firmaResponsableEnvioComunicadoCaja;
	}

	/**
	 * @param firmaResponsableEnvioComunicadoCaja the firmaResponsableEnvioComunicadoCaja to set
	 */
	public void setFirmaResponsableEnvioComunicadoCaja(String firmaResponsableEnvioComunicadoCaja) {
		this.firmaResponsableEnvioComunicadoCaja = firmaResponsableEnvioComunicadoCaja;
	}

	/**
	 * @return the responsableEnvioComunicadoCaja
	 */
	public String getResponsableEnvioComunicadoCaja() {
		return responsableEnvioComunicadoCaja;
	}

	/**
	 * @param responsableEnvioComunicadoCaja the responsableEnvioComunicadoCaja to set
	 */
	public void setResponsableEnvioComunicadoCaja(String responsableEnvioComunicadoCaja) {
		this.responsableEnvioComunicadoCaja = responsableEnvioComunicadoCaja;
	}

	/**
	 * @return the cargoResponsableEnvioComunicadoCaja
	 */
	public String getCargoResponsableEnvioComunicadoCaja() {
		return cargoResponsableEnvioComunicadoCaja;
	}

	/**
	 * @param cargoResponsableEnvioComunicadoCaja the cargoResponsableEnvioComunicadoCaja to set
	 */
	public void setCargoResponsableEnvioComunicadoCaja(String cargoResponsableEnvioComunicadoCaja) {
		this.cargoResponsableEnvioComunicadoCaja = cargoResponsableEnvioComunicadoCaja;
	}

	/**
	 * Método encargado de retornar el valor del campo tiempoCaducidadLinkEmpresasWeb
	 * @return el campo tiempoCaducidadLinkEmpresasWeb
	 */
	public String getTiempoCaducidadLinkEmpresasWeb() {
		return tiempoCaducidadLinkEmpresasWeb;
	}

	/**
	 * Método encargado de asignar el valor al campo tiempoCaducidadLinkEmpresasWeb
	 * @param tiempoCaducidadLinkEmpresasWeb tiempoCaducidadLinkEmpresasWeb a asignar
	 */
	public void setTiempoCaducidadLinkEmpresasWeb(String tiempoCaducidadLinkEmpresasWeb) {
		this.tiempoCaducidadLinkEmpresasWeb = tiempoCaducidadLinkEmpresasWeb;
	}

	/**
	 * Método encargado de retornar el valor del campo tiempoCaducidadLinkIndPensWeb
	 * @return el campo tiempoCaducidadLinkIndPensWeb
	 */
	public String getTiempoCaducidadLinkIndPensWeb() {
		return tiempoCaducidadLinkIndPensWeb;
	}

	/**
	 * Método encargado de asignar el valor al campo tiempoCaducidadLinkIndPensWeb
	 * @param tiempoCaducidadLinkIndPensWeb tiempoCaducidadLinkIndPensWeb a asignar
	 */
	public void setTiempoCaducidadLinkIndPensWeb(String tiempoCaducidadLinkIndPensWeb) {
		this.tiempoCaducidadLinkIndPensWeb = tiempoCaducidadLinkIndPensWeb;
	}

    /**
     * @return the descripcionIndicacion
     */
    public String getDescripcionIndicacion() {
        return descripcionIndicacion;
    }

    /**
     * @param descripcionIndicacion the descripcionIndicacion to set
     */
    public void setDescripcionIndicacion(String descripcionIndicacion) {
        this.descripcionIndicacion = descripcionIndicacion;
    }

    /**
     * Método que retorna el valor de firmaRespAfiEmpresas.
     * @return valor de firmaRespAfiEmpresas.
     */
    public String getFirmaRespAfiEmpresas() {
        return firmaRespAfiEmpresas;
    }

    /**
     * Método encargado de modificar el valor de firmaRespAfiEmpresas.
     * @param valor para modificar firmaRespAfiEmpresas.
     */
    public void setFirmaRespAfiEmpresas(String firmaRespAfiEmpresas) {
        this.firmaRespAfiEmpresas = firmaRespAfiEmpresas;
    }

    /**
     * Método que retorna el valor de firmaRespAfiPersonas.
     * @return valor de firmaRespAfiPersonas.
     */
    public String getFirmaRespAfiPersonas() {
        return firmaRespAfiPersonas;
    }

    /**
     * Método encargado de modificar el valor de firmaRespAfiPersonas.
     * @param valor para modificar firmaRespAfiPersonas.
     */
    public void setFirmaRespAfiPersonas(String firmaRespAfiPersonas) {
        this.firmaRespAfiPersonas = firmaRespAfiPersonas;
    }

	public String getCorreoCaja() {
		return this.correoCaja;
	}
	public void setCorreoCaja(String correoCaja) {
		this.correoCaja = correoCaja;
	}

	public String getFirmagerentefinanciera() {
		return this.firmagerentefinanciera;
	}

	public void setFirmagerentefinanciera(String firmagerentefinanciera) {
		this.firmagerentefinanciera = firmagerentefinanciera;
	}

	public String getFirmagerentecomercial() {
		return this.firmagerentecomercial;
	}

	public void setFirmagerentecomercial(String firmagerentecomercial) {
		this.firmagerentecomercial = firmagerentecomercial;
	}

	public String getFirmasecretariageneral() {
		return this.firmasecretariageneral;
	}

	public void setFirmasecretariageneral(String firmasecretariageneral) {
		this.firmasecretariageneral = firmasecretariageneral;
	}

	public String getFirmaDirectorAdminppalCCF() {
		return this.FirmaDirectorAdminppalCCF;
	}

	public void setFirmaDirectorAdminppalCCF(String FirmaDirectorAdminppalCCF) {
		this.FirmaDirectorAdminppalCCF = FirmaDirectorAdminppalCCF;
	}

	public String getFirmaDirectorAdminsplCCF() {
		return this.FirmaDirectorAdminsplCCF;
	}

	public void setFirmaDirectorAdminsplCCF(String FirmaDirectorAdminsplCCF) {
		this.FirmaDirectorAdminsplCCF = FirmaDirectorAdminsplCCF;
	}
}
