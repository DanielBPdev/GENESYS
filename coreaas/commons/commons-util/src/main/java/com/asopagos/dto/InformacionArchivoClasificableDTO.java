package com.asopagos.dto;

import java.io.Serializable;
import javax.ws.rs.FormParam;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.archivos.TipoPropietarioArchivoEnum;

/**
 * Clase que representa la informacion de un archivo procedente de clasificacion por escaneo masivo
 * 
 * @author Ricardo Hernandez Cediel <a href="mailto:hhernandez@heinsohn.com.co"> </a>
 * 
 */
public class InformacionArchivoClasificableDTO implements Serializable {

	private static final long serialVersionUID = 1L;
    
	/**
     * Id de la solicitud global
     */
    private Long idSolicitud;
    
	/**
     * Representa un tipo de transacción dentro de un proceso.
     */
    private TipoTransaccionEnum tipoTransaccion;
    
    /**
     * Es la instancia del proceso en la que se genera el archivo
     */
    private String idInstanciaProceso;
    
    /**
     * Tipo de identificación del propietario del archivo
     */
    private TipoIdentificacionEnum tipoIdentificacionPropietario;
    
    /**
     * Número de identificación del propietario del archivo
     */
    private String numeroIdentificacionPropietario;
    
    /**
     * Id de la persona propietario del archivo
     */
    private TipoPropietarioArchivoEnum tipoPropietario;
    
    
    public InformacionArchivoClasificableDTO(){
    	
    }

	public InformacionArchivoClasificableDTO(Long idSolicitud, TipoTransaccionEnum tipoTransaccion,
			String idInstanciaProceso, TipoIdentificacionEnum tipoIdentificacionPropietario,
			String numeroIdentificacionPropietario) {
		super();
		this.idSolicitud = idSolicitud;
		this.tipoTransaccion = tipoTransaccion;
		this.idInstanciaProceso = idInstanciaProceso;
		this.tipoIdentificacionPropietario = tipoIdentificacionPropietario;
		this.numeroIdentificacionPropietario = numeroIdentificacionPropietario;
	}

	/**
	 * @return the idSolicitud
	 */
	public Long getIdSolicitud() {
		return idSolicitud;
	}

	/**
	 * @param idSolicitud the idSolicitud to set
	 */
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	/**
	 * @return the tipoTransaccion
	 */
	public TipoTransaccionEnum getTipoTransaccion() {
		return tipoTransaccion;
	}

	/**
	 * @param tipoTransaccion the tipoTransaccion to set
	 */
	public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	/**
	 * @return the idInstanciaProceso
	 */
	public String getIdInstanciaProceso() {
		return idInstanciaProceso;
	}

	/**
	 * @param idInstanciaProceso the idInstanciaProceso to set
	 */
	public void setIdInstanciaProceso(String idInstanciaProceso) {
		this.idInstanciaProceso = idInstanciaProceso;
	}

	/**
	 * @return the tipoIdentificacionPropietario
	 */
	public TipoIdentificacionEnum getTipoIdentificacionPropietario() {
		return tipoIdentificacionPropietario;
	}

	/**
	 * @param tipoIdentificacionPropietario the tipoIdentificacionPropietario to set
	 */
	public void setTipoIdentificacionPropietario(TipoIdentificacionEnum tipoIdentificacionPropietario) {
		this.tipoIdentificacionPropietario = tipoIdentificacionPropietario;
	}

	/**
	 * @return the numeroIdentificacionPropietario
	 */
	public String getNumeroIdentificacionPropietario() {
		return numeroIdentificacionPropietario;
	}

	/**
	 * @param numeroIdentificacionPropietario the numeroIdentificacionPropietario to set
	 */
	public void setNumeroIdentificacionPropietario(String numeroIdentificacionPropietario) {
		this.numeroIdentificacionPropietario = numeroIdentificacionPropietario;
	}

	/**
	 * @return the tipoPropietario
	 */
	public TipoPropietarioArchivoEnum getTipoPropietario() {
		return tipoPropietario;
	}

	/**
	 * @param tipoPropietario the tipoPropietario to set
	 */
	public void setTipoPropietario(TipoPropietarioArchivoEnum tipoPropietario) {
		this.tipoPropietario = tipoPropietario;
	}
    
}