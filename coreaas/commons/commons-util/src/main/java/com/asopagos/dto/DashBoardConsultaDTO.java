package com.asopagos.dto;

import java.io.Serializable;
import javax.ws.rs.FormParam;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.archivos.TipoPropietarioArchivoEnum;

/**
 * Clase que representa la informacion de los registros de Power BI Dashboard
 * 
 * @author Ricardo Hernandez Cediel <a href="mailto:hhernandez@heinsohn.com.co"> </a>
 * 
 */
public class DashBoardConsultaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
    
	/**
     * Id de la tabla DashBoardConsulta
     */
    private Long idDashBoardConsulta;
    
    /**
     * Corresponde al rol definido en el keycloak para accesso al dashboard correspondiente
     */
    private String permiso;
    
    /**
     * Es el codigo del "groups", generado por powerBI enbedded para el dashboard correspondiente
     */
    private String groupsID;
        
    /**
     * Es el codigo del "reports", generado por powerBI enbedded para el dashboard correspondiente
     */
    private String reportsID;
    
    /**
     * Representa el nombre del dashboard legible para seleccion del usuario final
     */
    private String labelUsuario;
    
    /**
     * Es la descripcion de la informacion que presenta el dashboard 
     */
    private String descripcion;
    
	public DashBoardConsultaDTO(Long idDashBoardConsulta, String permiso, String groupsID, String reportsID,
			String labelUsuario, String descripcion) {
		super();
		this.idDashBoardConsulta = idDashBoardConsulta;
		this.permiso = permiso;
		this.groupsID = groupsID;
		this.reportsID = reportsID;
		this.labelUsuario = labelUsuario;
		this.descripcion = descripcion;
	}


	public DashBoardConsultaDTO(){
    	
    }
    
    
    /**
	 * @return the idDashBoardConsulta
	 */
	public Long getIdDashBoardConsulta() {
		return idDashBoardConsulta;
	}


	/**
	 * @param idDashBoardConsulta the idDashBoardConsulta to set
	 */
	public void setIdDashBoardConsulta(Long idDashBoardConsulta) {
		this.idDashBoardConsulta = idDashBoardConsulta;
	}


	/**
	 * @return the permiso
	 */
	public String getPermiso() {
		return permiso;
	}


	/**
	 * @param permiso the permiso to set
	 */
	public void setPermiso(String permiso) {
		this.permiso = permiso;
	}


	/**
	 * @return the groupsID
	 */
	public String getGroupsID() {
		return groupsID;
	}


	/**
	 * @param groupsID the groupsID to set
	 */
	public void setGroupsID(String groupsID) {
		this.groupsID = groupsID;
	}


	/**
	 * @return the reportsID
	 */
	public String getReportsID() {
		return reportsID;
	}


	/**
	 * @param reportsID the reportsID to set
	 */
	public void setReportsID(String reportsID) {
		this.reportsID = reportsID;
	}


	/**
	 * @return the labelUsuario
	 */
	public String getLabelUsuario() {
		return labelUsuario;
	}


	/**
	 * @param labelUsuario the labelUsuario to set
	 */
	public void setLabelUsuario(String labelUsuario) {
		this.labelUsuario = labelUsuario;
	}


	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}


	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	    
}