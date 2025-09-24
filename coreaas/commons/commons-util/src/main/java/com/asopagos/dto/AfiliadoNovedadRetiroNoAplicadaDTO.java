package com.asopagos.dto;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.asopagos.constants.MensajesGeneralConstants;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionAfiliadoEnum;
import com.asopagos.enumeraciones.core.CanalRecepcionEnum;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

public class AfiliadoNovedadRetiroNoAplicadaDTO implements Serializable{

private static final long serialVersionUID = 1L;

	private Long id;

    private TipoIdentificacionEnum tipoIdentificacionAfiliado;
    
    private String numeroIdentificacionAfiliado;
    
    private TipoIdentificacionEnum tipoIdentificacionEmpleador;
    
    private String tipoNovedad;
    
    private String numeroIdentificacionEmpleador;
    
    private EstadoAfiliadoEnum estadoTrabRespectoEmpl;
    
    private CanalRecepcionEnum canal;
    
    private Date fechaInicioNovedad;
    
    private Date fechaFinNovedad;
    
    private Long idEmpresa;
    
    private Long idPersona;
    
    private Long idRolAfiliado;
    
    private MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion;
    
    public static class AfiliadoNovedadRetiroNoAplicadaDTOBuilder{
    
    	AfiliadoNovedadRetiroNoAplicadaDTO afiliado;

		public AfiliadoNovedadRetiroNoAplicadaDTOBuilder() {
			this.afiliado = new AfiliadoNovedadRetiroNoAplicadaDTO();
		}
		
		public AfiliadoNovedadRetiroNoAplicadaDTO build(){
			return this.afiliado;
		}
		
		/**
		 * @param id the id to set
		 */
		public AfiliadoNovedadRetiroNoAplicadaDTOBuilder setId(Long id) {
			afiliado.setId(id);
			return this;
		}
		
		/**
		 * @param tipoIdentificacionAfiliado the tipoIdentificacionAfiliado to set
		 */
		public AfiliadoNovedadRetiroNoAplicadaDTOBuilder setTipoIdentificacionAfiliado(TipoIdentificacionEnum tipoIdentificacionAfiliado) {
			afiliado.setTipoIdentificacionAfiliado(tipoIdentificacionAfiliado);
			return this;
		}

		/**
		 * @param numeroIdentificacionAfiliado the numeroIdentificacionAfiliado to set
		 */
		public AfiliadoNovedadRetiroNoAplicadaDTOBuilder setNumeroIdentificacionAfiliado(String numeroIdentificacionAfiliado) {
			afiliado.setNumeroIdentificacionAfiliado(numeroIdentificacionAfiliado);
			return this;
		}

		/**
		 * @param tipoIdentificacionEmpleador the tipoIdentificacionEmpleador to set
		 */
		public AfiliadoNovedadRetiroNoAplicadaDTOBuilder setTipoIdentificacionEmpleador(TipoIdentificacionEnum tipoIdentificacionEmpleador) {
			afiliado.setTipoIdentificacionEmpleador(tipoIdentificacionEmpleador);
			return this;
		}

		/**
		 * @param tipoNovedad the tipoNovedad to set
		 */
		public AfiliadoNovedadRetiroNoAplicadaDTOBuilder setTipoNovedad(String tipoNovedad) {
			afiliado.setTipoNovedad(tipoNovedad);
			return this;
		}

		/**
		 * @param numeroIdentificacionEmpleador the numeroIdentificacionEmpleador to set
		 */
		public AfiliadoNovedadRetiroNoAplicadaDTOBuilder setNumeroIdentificacionEmpleador(String numeroIdentificacionEmpleador) {
			afiliado.setNumeroIdentificacionEmpleador(numeroIdentificacionEmpleador);
			return this;
		}

		/**
		 * @param estadoTrabRespectoEmpl the estadoTrabRespectoEmpl to set
		 */
		public AfiliadoNovedadRetiroNoAplicadaDTOBuilder setEstadoTrabRespectoEmpl(EstadoAfiliadoEnum estadoTrabRespectoEmpl) {
			afiliado.setEstadoTrabRespectoEmpl(estadoTrabRespectoEmpl);
			return this;
		}

		/**
		 * @param canal the canal to set
		 */
		public AfiliadoNovedadRetiroNoAplicadaDTOBuilder setCanal(CanalRecepcionEnum canal) {
			afiliado.setCanal(canal);
			return this;
		}

		/**
		 * @param fechaInicioNovedad the fechaInicioNovedad to set
		 */
		public AfiliadoNovedadRetiroNoAplicadaDTOBuilder setFechaInicioNovedad(String fechaInicioNovedad) {
			try {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date fecha = df.parse(fechaInicioNovedad);
				afiliado.setFechaInicioNovedad(fecha);
				return this;
			} catch (ParseException e) {
	            e.printStackTrace();
	            return this;
			}
		}

		/**
		 * @param fechaFinNovedad the fechaFinNovedad to set
		 */
		public AfiliadoNovedadRetiroNoAplicadaDTOBuilder setFechaFinNovedad(String fechaFinNovedad) {
			try {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date fecha = df.parse(fechaFinNovedad);
				afiliado.setFechaInicioNovedad(fecha);
				return this;
			} catch (ParseException e) {
				e.printStackTrace();
	            return this;
			}
		}

		/**
		 * @param idEmpresa the idEmpresa to set
		 */
		public AfiliadoNovedadRetiroNoAplicadaDTOBuilder setIdEmpresa(Long idEmpresa) {
			afiliado.setIdEmpresa(idEmpresa);
			return this;
		}

		/**
		 * @param idPersona the idPersona to set
		 */
		public AfiliadoNovedadRetiroNoAplicadaDTOBuilder setIdPersona(Long idPersona) {
			afiliado.setIdPersona(idPersona);
			return this;
		}

		/**
		 * @param idRolAfiliado the idRolAfiliado to set
		 */
		public AfiliadoNovedadRetiroNoAplicadaDTOBuilder setIdRolAfiliado(Long idRolAfiliado) {
			afiliado.setIdRolAfiliado(idRolAfiliado);
			return this;
		}

		/**
		 * @param motivoDesafiliacion the motivoDesafiliacion to set
		 */
		public AfiliadoNovedadRetiroNoAplicadaDTOBuilder setMotivoDesafiliacion(MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion) {
			afiliado.setMotivoDesafiliacion(motivoDesafiliacion);
			return this;
		}
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
	private void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the tipoIdentificacionAfiliado
	 */
    public TipoIdentificacionEnum getTipoIdentificacionAfiliado() {
		return tipoIdentificacionAfiliado;
	}

	/**
	 * @param tipoIdentificacionAfiliado the tipoIdentificacionAfiliado to set
	 */
	private void setTipoIdentificacionAfiliado(TipoIdentificacionEnum tipoIdentificacionAfiliado) {
		this.tipoIdentificacionAfiliado = tipoIdentificacionAfiliado;
	}

	/**
	 * @return the numeroIdentificacionAfiliado
	 */
	public String getNumeroIdentificacionAfiliado() {
		return numeroIdentificacionAfiliado;
	}

	/**
	 * @param numeroIdentificacionAfiliado the numeroIdentificacionAfiliado to set
	 */
	private void setNumeroIdentificacionAfiliado(String numeroIdentificacionAfiliado) {
		this.numeroIdentificacionAfiliado = numeroIdentificacionAfiliado;
	}

	/**
	 * @return the tipoIdentificacionEmpleador
	 */
	public TipoIdentificacionEnum getTipoIdentificacionEmpleador() {
		return tipoIdentificacionEmpleador;
	}

	/**
	 * @param tipoIdentificacionEmpleador the tipoIdentificacionEmpleador to set
	 */
	private void setTipoIdentificacionEmpleador(TipoIdentificacionEnum tipoIdentificacionEmpleador) {
		this.tipoIdentificacionEmpleador = tipoIdentificacionEmpleador;
	}

	/**
	 * @return the tipoNovedad
	 */
	public String getTipoNovedad() {
		return tipoNovedad;
	}

	/**
	 * @param tipoNovedad the tipoNovedad to set
	 */
	private void setTipoNovedad(String tipoNovedad) {
		this.tipoNovedad = tipoNovedad;
	}

	/**
	 * @return the numeroIdentificacionEmpleador
	 */
	public String getNumeroIdentificacionEmpleador() {
		return numeroIdentificacionEmpleador;
	}

	/**
	 * @param numeroIdentificacionEmpleador the numeroIdentificacionEmpleador to set
	 */
	private void setNumeroIdentificacionEmpleador(String numeroIdentificacionEmpleador) {
		this.numeroIdentificacionEmpleador = numeroIdentificacionEmpleador;
	}

	/**
	 * @return the estadoTrabRespectoEmpl
	 */
	public EstadoAfiliadoEnum getEstadoTrabRespectoEmpl() {
		return estadoTrabRespectoEmpl;
	}

	/**
	 * @param estadoTrabRespectoEmpl the estadoTrabRespectoEmpl to set
	 */
	private void setEstadoTrabRespectoEmpl(EstadoAfiliadoEnum estadoTrabRespectoEmpl) {
		this.estadoTrabRespectoEmpl = estadoTrabRespectoEmpl;
	}

	/**
	 * @return the canal
	 */
	public CanalRecepcionEnum getCanal() {
		return canal;
	}

	/**
	 * @param canal the canal to set
	 */
	private void setCanal(CanalRecepcionEnum canal) {
		this.canal = canal;
	}

	/**
	 * @return the fechaInicioNovedad
	 */
	public Date getFechaInicioNovedad() {
		return fechaInicioNovedad;
	}

	/**
	 * @param fechaInicioNovedad the fechaInicioNovedad to set
	 */
	private void setFechaInicioNovedad(Date fechaInicioNovedad) {
		this.fechaInicioNovedad = fechaInicioNovedad;
	}

	/**
	 * @return the fechaFinNovedad
	 */
	public Date getFechaFinNovedad() {
		return fechaFinNovedad;
	}

	/**
	 * @param fechaFinNovedad the fechaFinNovedad to set
	 */
	private void setFechaFinNovedad(Date fechaFinNovedad) {
		this.fechaFinNovedad = fechaFinNovedad;
	}

	/**
	 * @return the idEmpresa
	 */
	public Long getIdEmpresa() {
		return idEmpresa;
	}

	/**
	 * @param idEmpresa the idEmpresa to set
	 */
	private void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	/**
	 * @return the idPersona
	 */
	public Long getIdPersona() {
		return idPersona;
	}

	/**
	 * @param idPersona the idPersona to set
	 */
	private void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * @return the idRolAfiliado
	 */
	public Long getIdRolAfiliado() {
		return idRolAfiliado;
	}

	/**
	 * @param idRolAfiliado the idRolAfiliado to set
	 */
	private void setIdRolAfiliado(Long idRolAfiliado) {
		this.idRolAfiliado = idRolAfiliado;
	}

	/**
	 * @return the motivoDesafiliacion
	 */
	public MotivoDesafiliacionAfiliadoEnum getMotivoDesafiliacion() {
		return motivoDesafiliacion;
	}

	/**
	 * @param motivoDesafiliacion the motivoDesafiliacion to set
	 */
	private void setMotivoDesafiliacion(MotivoDesafiliacionAfiliadoEnum motivoDesafiliacion) {
		this.motivoDesafiliacion = motivoDesafiliacion;
	}
}
