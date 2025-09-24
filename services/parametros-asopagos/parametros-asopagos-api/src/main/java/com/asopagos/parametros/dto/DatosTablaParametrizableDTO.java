package com.asopagos.parametros.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripcion:</b> Clase que representara los datos de una entidad <br/>
 * <b>Módulo:</b> Asopagos - HU transversal<br/>
 *
 * @author  <a href="mailto:jmunoz@heinsohn.com.co"> jmunoz</a>
 */
@XmlRootElement
public class DatosTablaParametrizableDTO implements Serializable{

    private String identificador;
    private String valor;
    
    /**
     * @param identificador
     * @param valor
     */
    public DatosTablaParametrizableDTO(String identificador, String valor) {
        this.identificador = identificador;
        this.valor = valor;
    }

    public DatosTablaParametrizableDTO(){
        
    }

    /**
     * @return the identificador
     */
    public String getIdentificador() {
        return identificador;
    }
    /**
     * @param identificador the identificador to set
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }
    /**
     * @return the valor
     */
    public String getValor() {
        return valor;
    }
    /**
     * @param valor the valor to set
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    
    
}
