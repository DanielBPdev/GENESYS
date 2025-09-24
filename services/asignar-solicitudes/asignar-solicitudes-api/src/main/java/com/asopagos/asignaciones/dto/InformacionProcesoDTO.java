package com.asopagos.asignaciones.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.tareashumanas.dto.TareaDTO;

/**
 * <b>Descripción:</b> DTO que contiene la informacion de un proceso
 * <b>Historia de Usuario:</b> TRA-084
 *
 * @author Luis Arturo Zarate Ayala <lzarate@heinsohn.com.co>
 */
@XmlRootElement
public class InformacionProcesoDTO implements Serializable {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private TareaDTO tareaDTO;

    private String grupo;

    private String grupoDescripcion;

    private String idSede;

    private String nombreSede;
    
    private String nombreUsuario;
    
    private String proceso;

    /**
     * @return the tareaDTO
     */
    public TareaDTO getTareaDTO() {
        return tareaDTO;
    }

    /**
     * @param tareaDTO
     *        the tareaDTO to set
     */
    public void setTareaDTO(TareaDTO tareaDTO) {
        this.tareaDTO = tareaDTO;
    }

    /**
     * @return the grupo
     */
    public String getGrupo() {
        return grupo;
    }

    /**
     * @param grupo
     *        the grupo to set
     */
    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    /**
     * @return the grupoDescripcion
     */
    public String getGrupoDescripcion() {
        return grupoDescripcion;
    }

    /**
     * @param grupoDescripcion the grupoDescripcion to set
     */
    public void setGrupoDescripcion(String grupoDescripcion) {
        this.grupoDescripcion = grupoDescripcion;
    }

    /**
     * @return the idSede
     */
    public String getIdSede() {
        return idSede;
    }

    /**
     * @param idSede the idSede to set
     */
    public void setIdSede(String idSede) {
        this.idSede = idSede;
    }

    /**
     * @return the nombreSede
     */
    public String getNombreSede() {
        return nombreSede;
    }

    /**
     * @param nombreSede the nombreSede to set
     */
    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

	/**
	 * @return the nombreUsuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * @param nombreUsuario the nombreUsuario to set
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * @return the proceso
	 */
	public String getProceso() {
		return proceso;
	}

	/**
	 * @param proceso the proceso to set
	 */
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

}
