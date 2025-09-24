/**
 * 
 */
package com.asopagos.bandejainconsistencias.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;

/**
 * @author rarboleda
 *
 */
public class ConsultaCanalDeRetiroEmpleadorDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long idRolAfiliado;
    private CanalRecepcionEnum canalRecepcion;
    
    /**
     * @param idRolAfiliado
     * @param canalRecepcion
     */
    public ConsultaCanalDeRetiroEmpleadorDTO(Long idRolAfiliado, CanalRecepcionEnum canalRecepcion) {
        this.idRolAfiliado = idRolAfiliado;
        this.canalRecepcion = canalRecepcion;
    }    
    
    /**
     * @return the idRolAfiliado
     */
    public Long getIdRolAfiliado() {
        return idRolAfiliado;
    }
    /**
     * @param idRolAfiliado the idRolAfiliado to set
     */
    public void setIdRolAfiliado(Long idRolAfiliado) {
        this.idRolAfiliado = idRolAfiliado;
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
    
    
    public ConsultaCanalDeRetiroEmpleadorDTO() {}
}
