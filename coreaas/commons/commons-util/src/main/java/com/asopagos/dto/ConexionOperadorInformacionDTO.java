package com.asopagos.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.asopagos.util.DesEncrypter;

import co.com.heinsohn.lion.common.enums.Protocolo;

@XmlRootElement
public class ConexionOperadorInformacionDTO implements Serializable {

	/**
     * Serialización
     */
    private static final long serialVersionUID = 1L;

    /**
     * Código identificador de llave primaria de la entrada de parámetros de
     * conexión para el Operador de Información
     */
    private Long id;

    /**
     * Nombre de la conexión al operador de información.
     */
    private String nombre;
    
    /**
     * Descripción del protocolo de acceso empleado mediante el uso de FTP (Soportados: FTP, FTPS, SFTP)
     */
    private Protocolo protocolo;

    /**
     * Descripción de la URL de acceso al servidor de archivos
     * (una vez realizada la conexión; la url corresponde a la ubicación
     * en el directorio de trabajo para iniciar la carga de los archivos)
     */
    private String url;

    /**
     * Descripción del puerto de conexión mediante el uso de FTP
     */
    private Short puerto;

    /**
     * Descripción del host para la conexión al servidor de archivos mediante el uso de FTP
     */
    private String host;

    /**
     * Descripción del usuario de acceso para la conexión mediante el uso de FTP
     */
    private String usuario;

    /**
     * Contraseña de acceso para la conexión mediante el uso de FTP
     */
    private String contrasena;

    // información del operador de información

    private Long idOperadorInformacion;

    
    public ConexionOperadorInformacionDTO() {
	}
    
    
    
	public ConexionOperadorInformacionDTO(Long id, String nombre, String protocolo, String url, Short puerto,
			String host, String usuario, String contrasena, Long idOperadorInformacion) {		
		this.id = id;
		this.nombre = nombre;
		this.protocolo = protocolo != null ? Protocolo.valueOf(protocolo) : null;
		this.url = url;
		this.puerto = puerto;
		this.host = host;
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.idOperadorInformacion = idOperadorInformacion;
	}


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the protocolo
	 */
	public Protocolo getProtocolo() {
		return protocolo;
	}

	/**
	 * @param protocolo the protocolo to set
	 */
	public void setProtocolo(Protocolo protocolo) {
		this.protocolo = protocolo;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the puerto
	 */
	public Short getPuerto() {
		return puerto;
	}

	/**
	 * @param puerto the puerto to set
	 */
	public void setPuerto(Short puerto) {
		this.puerto = puerto;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the contrasena
	 */
	public String getContrasena() {
		return contrasena;
	}

	/**
	 * @param contrasena the contrasena to set
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	/**
	 * @return the idOperadorInformacion
	 */
	public Long getIdOperadorInformacion() {
		return idOperadorInformacion;
	}

	/**
	 * @param idOperadorInformacion the idOperadorInformacion to set
	 */
	public void setIdOperadorInformacion(Long idOperadorInformacion) {
		this.idOperadorInformacion = idOperadorInformacion;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
