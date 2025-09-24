package com.asopagos.pila.dto;

/**
 * DTO para retornar los datos requeridos para la pantalla de la HU-211-387
 * 
 * @author <a href="mailto:abaquero@heinsohn.com.co">Alfonso Baquero E.</a>
 *
 */
public class OperadorInformacionDTO {
	
	
	/**
	 * Este atributo identifica el consecutivo de la tabla
	 * */
	private Long id;
	/**
	 * Este atributo identifica el codigo del operador
	 * */
	
	private String codigo;
	
	/**
	 * Este atributo identifica el nombre del operador
	 * */
	
	private String nombre;
	/**
	 * Este atributo identifica la direccion ftp
	 * */
	
	private String url;
	/**
	 * Este atributo identifica el puerto del host
	 * */
	
	private Short puerto;
	/**
	 * Este atributo identifica el nombre del host
	 * */
	
	private String host;
	/**
	 * Este atributo identifica el nombre del usuario
	 * */
	
	private String usuario;
	/**
	 * Este atributo identifica la contraseña
	 * */
	
	private String pass;
	/**
	 * Este atributo identifica el operador activo
	 * */
	
	private Boolean operadorActivo;

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
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
	 * @return the pass
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * @param pass the pass to set
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * @return the operadorActivo
	 */
	public Boolean getOperadorActivo() {
		return operadorActivo;
	}

	/**
	 * @param operadorActivo the operadorActivo to set
	 */
	public void setOperadorActivo(Boolean operadorActivo) {
		this.operadorActivo = operadorActivo;
	}

}
