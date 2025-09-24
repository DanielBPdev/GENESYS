package com.asopagos.dto.fovis;

import com.asopagos.dto.modelo.LegalizacionDesembolosoProveedorModeloDTO;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.dto.modelo.ProveedorModeloDTO;

/**
 * <b>Descripción: </b> DTO que representa los datos de un proveedor <br/>
 * 
 * 
 * @author 
 * Lina M.
 */
@XmlRootElement
public class LegalizacionDesembolosoProveedorDTO implements Serializable {
    
    /**
	 * Serial
	 */
	private static final long serialVersionUID = -2674163909685868420L;
        
        
        /**
	 * Información de legalizacionDesembolosoProveedor
	 */
	private LegalizacionDesembolosoProveedorModeloDTO legalizacionDesembolosoProveedor;
        
        /**
	 * Constructor que recibe los datos del modelo de legalizacionDesembolosoProveedor
	 * @param legalizacionDesembolosoProveedor
	 */
	public LegalizacionDesembolosoProveedorDTO(LegalizacionDesembolosoProveedorModeloDTO legalizacionDesembolosoProveedor) {
		
		this.legalizacionDesembolosoProveedor = legalizacionDesembolosoProveedor;
	}
	
	/**
	 * Constructor por defecto
	 */
	public LegalizacionDesembolosoProveedorDTO() {
		
	}

    public LegalizacionDesembolosoProveedorModeloDTO getLegalizacionDesembolosoProveedor() {
        return legalizacionDesembolosoProveedor;
    }

    public void setLegalizacionDesembolosoProveedor(LegalizacionDesembolosoProveedorModeloDTO legalizacionDesembolosoProveedor) {
        this.legalizacionDesembolosoProveedor = legalizacionDesembolosoProveedor;
    }
        
        
    
}
