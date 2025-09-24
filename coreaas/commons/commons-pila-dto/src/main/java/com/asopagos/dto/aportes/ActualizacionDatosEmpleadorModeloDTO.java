package com.asopagos.dto.aportes;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.asopagos.entidades.ccf.aportes.ActualizacionDatosEmpleador;
import com.asopagos.entidades.ccf.personas.Empresa;
import com.asopagos.entidades.ccf.personas.Persona;
import com.asopagos.enumeraciones.aportes.CanalContactoEmpleadoresEnum;
import com.asopagos.enumeraciones.aportes.TipoInconsistenciasDatosEmpleadorEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.pila.EstadoGestionInconsistenciaEnum;

/**
 * <b>Descripcion:</b> Clase que <br/>
 * <b>Módulo:</b> Asopagos - HU <br/>
 *
 * @author <a href="mailto:anbuitrago@heinsohn.com.co"> anbuitrago</a>
 */
@XmlRootElement
public class ActualizacionDatosEmpleadorModeloDTO {
	/**
	 * Serialización
	 */
	private static long serialVersionUID = 1L;

	/**
	 * Código identificador de llave primaria
	 */

	private Long id;

	/**
	 * Código de empresa
	 */

	private Long empresa;

	/**
	 * Descripción de donde fallo la comunicacion con el empleador
	 */

	private TipoInconsistenciasDatosEmpleadorEnum tipoInconsistencia;

	/**
	 * Canal de contacto utilizado
	 */

	private CanalContactoEmpleadoresEnum canalContacto;

	/**
	 * Estado de gestión de la inconsistencia
	 */

	private EstadoGestionInconsistenciaEnum estadoInconsistencia;

	/**
	 * Fecha en la cual se envia a la bandeja 405
	 */

	private Date fechaIngreso;

	/**
	 * Fecha en la cual se establecen las observaciones
	 */

	private Date fechaRespuesta;

	/**
	 * Observaciones sobre el error al contactar al empleador
	 */

	private String observaciones;

	private TipoIdentificacionEnum tipoIdentificacion;

	private String numeroIdentficacion;

	private String nombreEmpleador;

	/**
	 * @param id
	 * @param empresa
	 * @param tipoInconsistencia
	 * @param canalContacto
	 * @param estadoInconsistencia
	 * @param fechaIngreso
	 * @param fechaRespuesta
	 * @param observaciones
	 * @param tipoIdentificacion
	 * @param numeroIdentficacion
	 * @param nombreEmpleador
	 */
	public ActualizacionDatosEmpleadorModeloDTO(Long id, Long empresa,
			TipoInconsistenciasDatosEmpleadorEnum tipoInconsistencia, CanalContactoEmpleadoresEnum canalContacto,
			EstadoGestionInconsistenciaEnum estadoInconsistencia, Date fechaIngreso, Date fechaRespuesta,
			String observaciones, TipoIdentificacionEnum tipoIdentificacion, String numeroIdentficacion,
			String nombreEmpleador) {
		super();
		this.id = id;
		this.empresa = empresa;
		this.tipoInconsistencia = tipoInconsistencia;
		this.canalContacto = canalContacto;
		this.estadoInconsistencia = estadoInconsistencia;
		this.fechaIngreso = fechaIngreso;
		this.fechaRespuesta = fechaRespuesta;
		this.observaciones = observaciones;
		this.tipoIdentificacion = tipoIdentificacion;
		this.numeroIdentficacion = numeroIdentficacion;
		this.nombreEmpleador = nombreEmpleador;
	}
	
	public ActualizacionDatosEmpleadorModeloDTO() {
		super();
	}
	
	/**
	 * Constructor con base en entities
	 * */
	public ActualizacionDatosEmpleadorModeloDTO(ActualizacionDatosEmpleador registro, Empresa emp, Persona per){
	    super();
	    convertToDTO(registro);
	    if(emp != null){
            this.setNumeroIdentficacion(emp.getPersona().getNumeroIdentificacion());
            this.setTipoIdentificacion(emp.getPersona().getTipoIdentificacion());
	    }
	    
        if (per != null && (per.getRazonSocial() == null || per.getRazonSocial().isEmpty())) {
            StringBuilder nombreAportante = new StringBuilder();
            nombreAportante.append(per.getPrimerNombre() + " ");
            nombreAportante.append(per.getSegundoNombre() != null ? per.getSegundoNombre() + " " : "");
            nombreAportante.append(per.getPrimerApellido() + " ");
            nombreAportante.append(per.getSegundoApellido() != null ? per.getSegundoApellido() : "");
            this.setNombreEmpleador(nombreAportante.toString());

        }
        else if(this.getNombreEmpleador() == null && per != null){
            this.setNombreEmpleador(per.getRazonSocial());
        }
	}

	/**
	 * @return the tipoIdentificacion
	 */
	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	/**
	 * @param tipoIdentificacion
	 *            the tipoIdentificacion to set
	 */
	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	/**
	 * @return the numeroIdentficacion
	 */
	public String getNumeroIdentficacion() {
		return numeroIdentficacion;
	}

	/**
	 * @param numeroIdentficacion
	 *            the numeroIdentficacion to set
	 */
	public void setNumeroIdentficacion(String numeroIdentficacion) {
		this.numeroIdentficacion = numeroIdentficacion;
	}

	/**
	 * @return the nombreEmpleador
	 */
	public String getNombreEmpleador() {
		return nombreEmpleador;
	}

	/**
	 * @param nombreEmpleador
	 *            the nombreEmpleador to set
	 */
	public void setNombreEmpleador(String nombreEmpleador) {
		this.nombreEmpleador = nombreEmpleador;
	}

	/**
	 * Método encargado de convertir de DTO a Entidad.
	 * 
	 * @return entidad convertida.
	 */
	public ActualizacionDatosEmpleador convertToEntity() {
		ActualizacionDatosEmpleador actualizacionDatos = new ActualizacionDatosEmpleador();
		actualizacionDatos.setId(this.getId());
		actualizacionDatos.setCanalContacto(this.getCanalContacto());
		actualizacionDatos.setEmpresa(this.getEmpresa());
		actualizacionDatos.setEstadoInconsistencia(this.getEstadoInconsistencia());
		if (this.getFechaIngreso() != null) {
			actualizacionDatos.setFechaIngreso(this.getFechaIngreso());
		}
		if (this.getFechaRespuesta() != null) {
			actualizacionDatos.setFechaRespuesta(this.fechaRespuesta);
		}
		actualizacionDatos.setObservaciones(this.getObservaciones());
		actualizacionDatos.setTipoInconsistencia(this.getTipoInconsistencia());
		return actualizacionDatos;
	}

	/**
	 * Método encargado de convertir de Entidad a DTO.
	 * 
	 * @param actualizacionDatos
	 *            entidad a convertir.
	 */
	public void convertToDTO(ActualizacionDatosEmpleador actualizacionDatos) {
		this.setId(actualizacionDatos.getId());
		this.setCanalContacto(actualizacionDatos.getCanalContacto());
		this.setEmpresa(actualizacionDatos.getEmpresa());
		this.setEstadoInconsistencia(actualizacionDatos.getEstadoInconsistencia());
		this.setFechaIngreso(actualizacionDatos.getFechaIngreso());
		this.setFechaRespuesta(actualizacionDatos.getFechaRespuesta());
		this.setObservaciones(actualizacionDatos.getObservaciones());
		this.setTipoInconsistencia(actualizacionDatos.getTipoInconsistencia());

	}

	/**
	 * Método encargado de copiar un DTO a una Entidad.
	 * 
	 * @param aporteGeneral
	 *            previamente consultado.
	 * @return aporteGeneral aporte modificado con los datos del DTO.
	 */
	public ActualizacionDatosEmpleador copyDTOToEntiy(ActualizacionDatosEmpleador actualizacionDatos) {
		if (this.getId() != null) {
			actualizacionDatos.setId(this.getId());
			actualizacionDatos.setCanalContacto(this.getCanalContacto());
			actualizacionDatos.setEmpresa(this.getEmpresa());
			actualizacionDatos.setEstadoInconsistencia(this.getEstadoInconsistencia());
			actualizacionDatos.setFechaIngreso(this.getFechaIngreso());
			actualizacionDatos.setFechaRespuesta(this.getFechaRespuesta());
			actualizacionDatos.setObservaciones(this.getObservaciones());
			actualizacionDatos.setTipoInconsistencia(this.getTipoInconsistencia());
		}
		return actualizacionDatos;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @param serialversionuid
	 *            the serialversionuid to set
	 */
	public static void setSerialversionuid(long serialversionuid) {
		serialVersionUID = serialversionuid;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the empresa
	 */
	public Long getEmpresa() {
		return empresa;
	}

	/**
	 * @param empresa
	 *            the empresa to set
	 */
	public void setEmpresa(Long empresa) {
		this.empresa = empresa;
	}

	/**
	 * @return the tipoInconsistencia
	 */
	public TipoInconsistenciasDatosEmpleadorEnum getTipoInconsistencia() {
		return tipoInconsistencia;
	}

	/**
	 * @param tipoInconsistencia
	 *            the tipoInconsistencia to set
	 */
	public void setTipoInconsistencia(TipoInconsistenciasDatosEmpleadorEnum tipoInconsistencia) {
		this.tipoInconsistencia = tipoInconsistencia;
	}

	/**
	 * @return the canalContacto
	 */
	public CanalContactoEmpleadoresEnum getCanalContacto() {
		return canalContacto;
	}

	/**
	 * @param canalContacto
	 *            the canalContacto to set
	 */
	public void setCanalContacto(CanalContactoEmpleadoresEnum canalContacto) {
		this.canalContacto = canalContacto;
	}

	/**
	 * @return the estadoInconsistencia
	 */
	public EstadoGestionInconsistenciaEnum getEstadoInconsistencia() {
		return estadoInconsistencia;
	}

	/**
	 * @param estadoInconsistencia
	 *            the estadoInconsistencia to set
	 */
	public void setEstadoInconsistencia(EstadoGestionInconsistenciaEnum estadoInconsistencia) {
		this.estadoInconsistencia = estadoInconsistencia;
	}

	/**
	 * @return the fechaIngreso
	 */
	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	/**
	 * @param fechaIngreso
	 *            the fechaIngreso to set
	 */
	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	/**
	 * @return the fechaRespuesta
	 */
	public Date getFechaRespuesta() {
		return fechaRespuesta;
	}

	/**
	 * @param fechaRespuesta
	 *            the fechaRespuesta to set
	 */
	public void setFechaRespuesta(Date fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}

	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones
	 *            the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

}
