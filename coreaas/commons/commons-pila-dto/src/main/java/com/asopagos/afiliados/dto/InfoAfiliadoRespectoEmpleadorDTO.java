package com.asopagos.afiliados.dto;

import java.io.Serializable;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class InfoAfiliadoRespectoEmpleadorDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private InfoEstadoAfiliadoDTO infoEstadoAfiliado; 
    private InfoVacionesYSuspensionAfiliadoDTO infoVacacionesYSuspension;    
    private InfoUltimoAporteDTO infoUltimoAporte; 

    /**
     * 
     */
    public InfoAfiliadoRespectoEmpleadorDTO() {
    }

    /**
     * @return the infoEstadoAfiliado
     */
    public InfoEstadoAfiliadoDTO getInfoEstadoAfiliado() {
        return infoEstadoAfiliado;
    }

    /**
     * @param infoEstadoAfiliado the infoEstadoAfiliado to set
     */
    public void setInfoEstadoAfiliado(InfoEstadoAfiliadoDTO infoEstadoAfiliado) {
        this.infoEstadoAfiliado = infoEstadoAfiliado;
    }

    /**
     * @return the infoVacacionesYSuspension
     */
    public InfoVacionesYSuspensionAfiliadoDTO getInfoVacacionesYSuspension() {
        return infoVacacionesYSuspension;
    }

    /**
     * @param infoVacacionesYSuspension the infoVacacionesYSuspension to set
     */
    public void setInfoVacacionesYSuspension(InfoVacionesYSuspensionAfiliadoDTO infoVacacionesYSuspension) {
        this.infoVacacionesYSuspension = infoVacacionesYSuspension;
    }

    /**
     * @return the infoUltimoAporte
     */
    public InfoUltimoAporteDTO getInfoUltimoAporte() {
        return infoUltimoAporte;
    }

    /**
     * @param infoUltimoAporte the infoUltimoAporte to set
     */
    public void setInfoUltimoAporte(InfoUltimoAporteDTO infoUltimoAporte) {
        this.infoUltimoAporte = infoUltimoAporte;
    }   
}