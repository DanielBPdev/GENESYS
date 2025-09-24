package com.asopagos.dto.cartera;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Descripción: </b> Clase que contiene los aportantes para la consulta de traspasar deuda antigua de personas ya seleccionadas.<br/>
 * 
 * @author <a href="mailto:silopez@heinsohn.com.co">Silvio Alexander López Herrera</a>
 */
@XmlRootElement
public class AportantesTraspasoDeudaDTO implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = 7506275192577149293L;

    /**
     * Lista de aportantes
     */
    private List<AportanteGestionManualDTO> aportantes;
    
    /**
     * Atributo que contiene el total de registros para enviar en el response.
     */
    private Integer totalRecords;
    
    public AportantesTraspasoDeudaDTO() {
        // TODO Auto-generated constructor stub
    }
 
    /**
     * Método que retorna el valor de aportantes.
     * @return valor de aportantes.
     */
    public List<AportanteGestionManualDTO> getAportantes() {
        return aportantes;
    }

    /**
     * Método encargado de modificar el valor de aportantes.
     * @param valor para modificar aportantes.
     */
    public void setAportantes(List<AportanteGestionManualDTO> aportantes) {
        this.aportantes = aportantes;
    }

    /**
     * Método que retorna el valor de totalRecords.
     * @return valor de totalRecords.
     */
    public Integer getTotalRecords() {
        return totalRecords;
    }

    /**
     * Método encargado de modificar el valor de totalRecords.
     * @param valor para modificar totalRecords.
     */
    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }
    

}
