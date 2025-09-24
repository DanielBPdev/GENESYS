package com.asopagos.constantes.parametros.dto;

import com.asopagos.entidades.ccf.general.ParametrizacionValorUVT;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.util.Date;

/**
 * <b>Descripción:</b> DTO empleado para obtener la informacion de los valores UVT
 * <b>Historia de Usuario:</b> HU-XXX
 * 
 * @author Guillermo Andres Serrato Ramirez<guillermo.serrato@asopagos.com>
 */

@XmlRootElement
public class ConstantesValorUVTDTO implements Serializable {
	
	/**
	 * Identificador Valor UVT
	 *
	 */
	private Integer idValorUVT;
	
    /**
	 * Año 
	 *
	 */
	private Long anio;
	
	/**
	 * Valor UVT
	 *
	 */
	private Integer valorUVT;

    private Boolean respuesta;
	
    /**
     * Constructor de la clase
     */
    public ConstantesValorUVTDTO(Integer idValorUVT, Date anio, Integer valorUVT) {
        this.idValorUVT = idValorUVT;
        this.anio = anio.getTime();
        this.valorUVT = valorUVT;
    }


    public ConstantesValorUVTDTO(Integer idValorUVT, Long anio, Integer valorUVT) {
        this.idValorUVT = idValorUVT;
        this.anio = anio;
        this.valorUVT = valorUVT;
    }

    public ConstantesValorUVTDTO(){}

    /**
    * @return the idValorUVT
    */
    public Integer getIdValorUVT() {
        return idValorUVT;
    }

    /**
    * @param idValorUVT the idValorUVT to set
    */
    public void setIdValorUVT(Integer idValorUVT) {
        this.idValorUVT = idValorUVT;
    }

    /**
    * @return the anio
    */
    public Long getAnio() {
        return anio;
    }

    /**
    * @param anio the anio to set
    */
    public void setAnio(Long anio) {
        this.anio = anio;
    }

    /**
    * @return the valorUVT
    */
    public Integer getValorUVT() {
        return valorUVT;
    }

    /**
    * @param valorUVT the valorUVT to set
    */
    public void setValorUVT(Integer valorUVT) {
        this.valorUVT = valorUVT;
    }


    public Boolean isRespuesta() {
        return this.respuesta;
    }

    public Boolean getRespuesta() {
        return this.respuesta;
    }

    public void setRespuesta(Boolean respuesta) {
        this.respuesta = respuesta;
    }


    public void convertToDTO(ParametrizacionValorUVT parametrizacionValorUVT) {
        this.setIdValorUVT(parametrizacionValorUVT.getIdValorUVT());
        this.setAnio(parametrizacionValorUVT.getAnio().getTime());
        this.setValorUVT(parametrizacionValorUVT.getValorUVT());
    }

    @Override
    public String toString() {
        return "ConstantesValorUVTDTO{" +
                "idValorUVT='" + idValorUVT + '\'' +
                ", anio=" + anio +
                ", valorUVT=" + valorUVT +
                '}';
    }
}
