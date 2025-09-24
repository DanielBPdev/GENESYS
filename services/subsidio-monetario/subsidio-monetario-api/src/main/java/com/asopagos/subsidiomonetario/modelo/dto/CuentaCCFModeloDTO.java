package com.asopagos.subsidiomonetario.modelo.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.asopagos.dto.modelo.BancoModeloDTO;
import com.asopagos.entidades.ccf.core.Banco;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.entidades.subsidiomonetario.liquidacion.CuentaCCF;
import com.asopagos.entidades.subsidiomonetario.liquidacion.Periodo;
import com.asopagos.enumeraciones.personas.TipoCuentaEnum;
import com.asopagos.validacion.GrupoActualizacion;
import com.asopagos.validacion.GrupoCreacion;

/**
 * <b>Descripcion:</b> Clase DTO que representa un periodo <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:rarboleda@heinsohn.com.co"> Robinson A. Arboleda</a>
 */

public class CuentaCCFModeloDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Código identificador de llave primaria del periodo
     */
    private Long idCuentaCCF;

    /**
     * Fecha del periodo
     */
    private TipoCuentaEnum tipoCuenta;

    /**
     * Fecha del periodo
     */
    private String numeroCuenta;
    
    /**
     * @return the idCuentaCCF
     */
	public Long getIdCuentaCCF() {
		return idCuentaCCF;
	}
	
	/**
	 * Referencia a el banco
	 */	
	private Banco banco;  
	
	/**
	 * Lista de bancos para mostrar por pantalla
	 */	
	private List<BancoModeloDTO> bancos;

    /**
     * @param idCuentaCCF
     *        the idCuentaCCF to set
     */
	public void setIdCuentaCCF(Long idCuentaCCF) {
		this.idCuentaCCF = idCuentaCCF;
	}

	 /**
     * @return the tipoCuenta
     */
	public TipoCuentaEnum getTipoCuenta() {
		return tipoCuenta;
	}

    /**
     * @param tipoCuenta
     *        the tipoCuenta to set
     */
	public void setTipoCuenta(TipoCuentaEnum tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	 /**
     * @return the numeroCuenta
     */
	public String getNumeroCuenta() {
		return numeroCuenta;
	}

    /**
     * @param numeroCuenta
     *        the numeroCuenta to set
     */
	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

    
   

    public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}
	
	

	public List<BancoModeloDTO> getBancos() {
		return bancos;
	}

	public void setBancos(List<BancoModeloDTO> bancos) {
		this.bancos = bancos;
	}

	/**
     * Convierte una entidad en DTO
     * @param Entidad
     *        periodo a convertir en DTO
     * @return DTO que representa la entidad
     */
    public void convertToDTO(CuentaCCF cuenta) {
        setIdCuentaCCF(cuenta.getIdCuentaCCF());
        setTipoCuenta(cuenta.getTipoCuenta());
        setNumeroCuenta(cuenta.getNumeroCuenta());
        setBanco(cuenta.getBanco());
    }

    /**
     * Convierte un DTO a una entidad
     * @return entidad Periodo
     */
    public CuentaCCF convertToEntity() {
    	CuentaCCF cuenta = new CuentaCCF();
    	cuenta.setIdCuentaCCF(this.getIdCuentaCCF());
        cuenta.setTipoCuenta(this.getTipoCuenta());
    	cuenta.setNumeroCuenta(this.getNumeroCuenta());
    	cuenta.setBanco(this.getBanco());
        return cuenta;
    }
}