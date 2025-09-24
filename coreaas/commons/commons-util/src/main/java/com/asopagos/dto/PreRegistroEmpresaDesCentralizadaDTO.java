
package com.asopagos.dto;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.entidades.ccf.afiliaciones.PreRegistroEmpresaDesCentralizada;
import java.io.Serializable;

/**
 *
 * @author Diego Alejandro Garavito Feliciano
 */
public class PreRegistroEmpresaDesCentralizadaDTO implements Serializable {

  

    private Long idDescentralizada;

    private TipoIdentificacionEnum tipoIdentificacion;

    private String numeroIdentificacionSerial;
    
    private String codigoSucursal;
    
    private String  NombreSucursalPila;
    
    private String NumeroIdentificacion;
    
    public static PreRegistroEmpresaDesCentralizadaDTO convertPreRegistroEmpresaDesCentralizadaToDTO(PreRegistroEmpresaDesCentralizada PreRegistroDes) {
        
        PreRegistroEmpresaDesCentralizadaDTO PreregistroDTO = new PreRegistroEmpresaDesCentralizadaDTO();
        PreregistroDTO.setIdDescentralizada(PreRegistroDes.getIdDescentralizada());
        PreregistroDTO.setTipoIdentificacion(PreRegistroDes.getTipoIdentificacion());
        PreregistroDTO.setNumeroIdentificacionSerial(PreRegistroDes.getNumeroIdentificacionSerial());
        PreregistroDTO.setCodigoSucursal(PreRegistroDes.getCodigoSucursal());
        PreregistroDTO.setNombreSucursalPila(PreRegistroDes.getPrdNombreSucursalPila());
        PreregistroDTO.setNumeroIdentificacion(PreRegistroDes.getPrdNumeroIdentificacion());
                
        return PreregistroDTO;
    }

    public Long getIdDescentralizada() {
        return idDescentralizada;
    }

    public void setIdDescentralizada(Long idDescentralizada) {
        this.idDescentralizada = idDescentralizada;
    }

    public TipoIdentificacionEnum getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacionSerial() {
        return numeroIdentificacionSerial;
    }

    public void setNumeroIdentificacionSerial(String numeroIdentificacionSerial) {
        this.numeroIdentificacionSerial = numeroIdentificacionSerial;
    }

    public String getCodigoSucursal() {
        return codigoSucursal;
    }

    public void setCodigoSucursal(String codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }
    
      /**
     * @return the NombreSucursalPila
     */
    public String getNombreSucursalPila() {
        return NombreSucursalPila;
    }

    /**
     * @param NombreSucursalPila the NombreSucursalPila to set
     */
    public void setNombreSucursalPila(String NombreSucursalPila) {
        this.NombreSucursalPila = NombreSucursalPila;
    }

    /**
     * @return the NumeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return NumeroIdentificacion;
    }

    /**
     * @param NumeroIdentificacion the NumeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String NumeroIdentificacion) {
        this.NumeroIdentificacion = NumeroIdentificacion;
    }

    

}
