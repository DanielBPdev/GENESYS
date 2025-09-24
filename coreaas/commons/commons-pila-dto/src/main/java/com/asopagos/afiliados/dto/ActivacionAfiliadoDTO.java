package com.asopagos.afiliados.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import com.asopagos.dto.modelo.RegistroDetalladoModeloDTO;
import com.asopagos.dto.modelo.SucursalEmpresaModeloDTO;

/**
 * <b>Descripcion:</b> DTO que contiene los datos necesarios para llevar a cabo un 
 * reintegro de afiliado por aportes <br/>
 * <b>Módulo:</b> Asopagos - HU-211-397 - HU-211-398 <br/>
 *
 * @author  <a href="mailto:abaquero@heinsohn.com.co"> Alfonso Baquero E.</a>
 */

public class ActivacionAfiliadoDTO implements Serializable {
    private static final long serialVersionUID = 3020065212560358257L;

    /** Tipo de documento del afiliado */
    private TipoIdentificacionEnum tipoIdAfiliado;
    
    /** Número de documento del afiliado */
    private String numeroIdAfiliado;
    
    /** Tipo de afiliado */
    private TipoAfiliadoEnum tipoAfiliado;

    /** Tipo de documento del aportante (para dependientes) */
    private TipoIdentificacionEnum tipoIdAportante;

    /** Número de documento del aportante (para dependientes) */
    private String numeroIdAportante;
    
    /** Datos del registro detallado del aporte */
    private RegistroDetalladoModeloDTO datosAfiliado;
    
    /** DTO datos empleador (para dependientes)*/
    private EmpleadorModeloDTO empleador;
    
    /** DTO datos sucursal (para dependientes)*/
    private SucursalEmpresaModeloDTO sucursal;
    
    /** Canal de recepción del reintegro */
    private CanalRecepcionEnum canalRecepcion;

    /**
     * @return the tipoIdAfiliado
     */
    public TipoIdentificacionEnum getTipoIdAfiliado() {
        return tipoIdAfiliado;
    }

    /**
     * @param tipoIdAfiliado the tipoIdAfiliado to set
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
     * @param numeroIdAfiliado the numeroIdAfiliado to set
     */
    public void setNumeroIdAfiliado(String numeroIdAfiliado) {
        this.numeroIdAfiliado = numeroIdAfiliado;
    }

    /**
     * @return the tipoAfiliado
     */
    public TipoAfiliadoEnum getTipoAfiliado() {
        return tipoAfiliado;
    }

    /**
     * @param tipoAfiliado the tipoAfiliado to set
     */
    public void setTipoAfiliado(TipoAfiliadoEnum tipoAfiliado) {
        this.tipoAfiliado = tipoAfiliado;
    }

    /**
     * @return the tipoIdAportante
     */
    public TipoIdentificacionEnum getTipoIdAportante() {
        return tipoIdAportante;
    }

    /**
     * @param tipoIdAportante the tipoIdAportante to set
     */
    public void setTipoIdAportante(TipoIdentificacionEnum tipoIdAportante) {
        this.tipoIdAportante = tipoIdAportante;
    }

    /**
     * @return the numeroIdAportante
     */
    public String getNumeroIdAportante() {
        return numeroIdAportante;
    }

    /**
     * @param numeroIdAportante the numeroIdAportante to set
     */
    public void setNumeroIdAportante(String numeroIdAportante) {
        this.numeroIdAportante = numeroIdAportante;
    }

    /**
     * @return the datosAfiliado
     */
    public RegistroDetalladoModeloDTO getDatosAfiliado() {
        return datosAfiliado;
    }

    /**
     * @param datosAfiliado the datosAfiliado to set
     */
    public void setDatosAfiliado(RegistroDetalladoModeloDTO datosAfiliado) {
        this.datosAfiliado = datosAfiliado;
    }

    /**
     * @return the empleador
     */
    public EmpleadorModeloDTO getEmpleador() {
        return empleador;
    }

    /**
     * @param empleador the empleador to set
     */
    public void setEmpleador(EmpleadorModeloDTO empleador) {
        this.empleador = empleador;
    }

    /**
     * @return the sucursal
     */
    public SucursalEmpresaModeloDTO getSucursal() {
        return sucursal;
    }

    /**
     * @param sucursal the sucursal to set
     */
    public void setSucursal(SucursalEmpresaModeloDTO sucursal) {
        this.sucursal = sucursal;
    }

    /**
     * @return the canalRecepcion
     */
    public CanalRecepcionEnum getCanalRecepcion() {
        return canalRecepcion;
    }

    /**
     * @param canalRecepcion the canalRecepcion to set
     */
    public void setCanalRecepcion(CanalRecepcionEnum canalRecepcion) {
        this.canalRecepcion = canalRecepcion;
    }
}
