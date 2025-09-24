package com.asopagos.dto;

import java.io.Serializable;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripción:</b> DTO que define los datos transmitidos en el servicio de 
 * listas genéricas
 * <b>Historia de Usuario:</b> Transversal
 * 
 * @author Sergio Briñez <sbrinez@heinsohn.com.co>
 */
@XmlRootElement
public class ElementoListaDTO implements Serializable {
    
    
    private Integer idLista;    
    
    private Object identificador;
    
    private String valor;
    
    private String key;
    
    private Map<String, Object> atributos;
    

    /**
     * @return the idLista
     */
    public Integer getIdLista() {
        return idLista;
    }

    /**
     * @param idLista the idLista to set
     */
    public void setIdLista(Integer idLista) {
        this.idLista = idLista;
    }

    /**
     * @return the identificador
     */
    public Object getIdentificador() {
        return identificador;
    }

    /**
     * @param identificador the identificador to set
     */
    public void setIdentificador(Object identificador) {
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

    /**
     * @return the atributos
     */
    public Map<String, Object> getAtributos() {
        return atributos;
    }

    /**
     * @param atributos the atributos to set
     */
    public void setAtributos(Map<String, Object> atributos) {
        this.atributos = atributos;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

}

